/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairGroupFee;
import com.thinkgem.jeesite.modules.affair.service.AffairGroupFeeService;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 团费收支Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGroupFee")
public class AffairGroupFeeController extends BaseController {

	@Autowired
	private AffairGroupFeeService affairGroupFeeService;
	
	@ModelAttribute
	public AffairGroupFee get(@RequestParam(required=false) String id) {
		AffairGroupFee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGroupFeeService.get(id);
		}
		if (entity == null){
			entity = new AffairGroupFee();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairGroupFee:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairGroupFee affairGroupFee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairGroupFee> page = affairGroupFeeService.findPage(new Page<AffairGroupFee>(request, response), affairGroupFee); 
		model.addAttribute("page", page);
		return "modules/affair/affairGroupFeeList";
	}

	@RequiresPermissions("affair:affairGroupFee:view")
	@RequestMapping(value = "form")
	public String form(AffairGroupFee affairGroupFee, Model model) {
		model.addAttribute("affairGroupFee", affairGroupFee);
		return "modules/affair/affairGroupFeeForm";
	}

	@RequiresPermissions("affair:affairGroupFee:edit")
	@RequestMapping(value = "save")
	public String save(AffairGroupFee affairGroupFee, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairGroupFee)){
			return form(affairGroupFee, model);
		}
		affairGroupFeeService.save(affairGroupFee);
		addMessage(redirectAttributes, "保存团费收支成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairGroupFeeForm";
	}
	
	@RequiresPermissions("affair:affairGroupFee:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGroupFee affairGroupFee, RedirectAttributes redirectAttributes) {
		affairGroupFeeService.delete(affairGroupFee);
		addMessage(redirectAttributes, "删除团费收支成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGroupFee/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairGroupFee:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGroupFeeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairGroupFee affairGroupFee, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairGroupFee> page = null;
			if(flag == true){
				page = affairGroupFeeService.findPage(new Page<AffairGroupFee>(request, response), affairGroupFee);
			}else {
				page = affairGroupFeeService.findPage(new Page<AffairGroupFee>(request, response,-1), affairGroupFee);
			}
/*
			Page<AffairGroupFee> page = affairGroupFeeService.findPage(new Page<AffairGroupFee>(request, response,-1), affairGroupFee);
*/
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			//修改
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGroupFee.class);
			exportExcelNew.setWb(wb);
			List<AffairGroupFee> list =page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息："+ex);
		}
		//修改
		return "redirect:" + adminPath + "/affair/affairGroupFee/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairGroupFee> list = ei.getDataList(AffairGroupFee.class);
			for (AffairGroupFee affairGroupFee : list){
				try{
					BeanValidators.validateWithException(validator, affairGroupFee);
					affairGroupFeeService.save(affairGroupFee);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(经办人身份证号码:"+affairGroupFee.getManagerNum()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}
}