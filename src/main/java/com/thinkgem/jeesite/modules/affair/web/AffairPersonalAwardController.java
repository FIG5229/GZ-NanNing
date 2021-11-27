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
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalAward;
import com.thinkgem.jeesite.modules.affair.service.AffairPersonalAwardService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
 * 工会个人表彰Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPersonalAward")
public class AffairPersonalAwardController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairPersonalAwardService affairPersonalAwardService;
	
	@ModelAttribute
	public AffairPersonalAward get(@RequestParam(required=false) String id) {
		AffairPersonalAward entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPersonalAwardService.get(id);
		}
		if (entity == null){
			entity = new AffairPersonalAward();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPersonalAward:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPersonalAward affairPersonalAward, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPersonalAward> page = affairPersonalAwardService.findPage(new Page<AffairPersonalAward>(request, response), affairPersonalAward); 
		model.addAttribute("page", page);
		return "modules/affair/affairPersonalAwardList";
	}

	@RequiresPermissions("affair:affairPersonalAward:view")
	@RequestMapping(value = "form")
	public String form(AffairPersonalAward affairPersonalAward, Model model) {
		model.addAttribute("affairPersonalAward", affairPersonalAward);
		return "modules/affair/affairPersonalAwardForm";
	}

	@RequiresPermissions("affair:affairPersonalAward:edit")
	@RequestMapping(value = "save")
	public String save(AffairPersonalAward affairPersonalAward, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPersonalAward)){
			return form(affairPersonalAward, model);
		}
		affairPersonalAwardService.save(affairPersonalAward);
		addMessage(redirectAttributes, "保存工会个人表彰成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPersonalAwardForm";
	}
	
	@RequiresPermissions("affair:affairPersonalAward:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPersonalAward affairPersonalAward, RedirectAttributes redirectAttributes) {
		affairPersonalAwardService.delete(affairPersonalAward);
		addMessage(redirectAttributes, "删除工会个人表彰成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPersonalAward/?repage";
	}

	/**
	 * 详情
	 * @param affairPersonalAward
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("affair:affairPersonalAward:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPersonalAward affairPersonalAward, Model model) {
		model.addAttribute("affairPersonalAward", affairPersonalAward);
		return "modules/affair/affairPersonalAwardFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPersonalAward:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPersonalAwardService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairPersonalAward affairPersonalAward, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairPersonalAward> page = null;
			if(flag == true){
				page = affairPersonalAwardService.findPage(new Page<AffairPersonalAward>(request, response), affairPersonalAward);
			}else {
				page = affairPersonalAwardService.findPage(new Page<AffairPersonalAward>(request, response,-1), affairPersonalAward);
			}
/*
			Page<AffairPersonalAward> page = affairPersonalAwardService.findPage(new Page<AffairPersonalAward>(request, response,-1), affairPersonalAward);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPersonalAward.class);
			exportExcelNew.setWb(wb);
			List<AffairPersonalAward> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairPersonalAward/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairPersonalAward> list = ei.getDataList(AffairPersonalAward.class);
			for (AffairPersonalAward affairPersonalAward : list){
				try{
					if (affairPersonalAward.getUnit()!=null&&!"".equals(affairPersonalAward.getUnit())){
						affairPersonalAward.setUnitId(officeService.findByName(affairPersonalAward.getUnit()));
					}
					affairPersonalAward.setTypeFlag("2");
					BeanValidators.validateWithException(validator, affairPersonalAward);
					affairPersonalAwardService.save(affairPersonalAward);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairPersonalAward.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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