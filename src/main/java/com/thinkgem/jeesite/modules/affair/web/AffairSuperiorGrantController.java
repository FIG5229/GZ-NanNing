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
import com.thinkgem.jeesite.modules.affair.entity.AffairSuperiorGrant;
import com.thinkgem.jeesite.modules.affair.service.AffairSuperiorGrantService;
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
 * 上级拨款Controller
 * @author cecil.li
 * @version 2019-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSuperiorGrant")
public class AffairSuperiorGrantController extends BaseController {

	@Autowired
	private AffairSuperiorGrantService affairSuperiorGrantService;
	
	@ModelAttribute
	public AffairSuperiorGrant get(@RequestParam(required=false) String id) {
		AffairSuperiorGrant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSuperiorGrantService.get(id);
		}
		if (entity == null){
			entity = new AffairSuperiorGrant();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSuperiorGrant:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSuperiorGrant affairSuperiorGrant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSuperiorGrant> page = affairSuperiorGrantService.findPage(new Page<AffairSuperiorGrant>(request, response), affairSuperiorGrant); 
		model.addAttribute("page", page);
		return "modules/affair/affairSuperiorGrantList";
	}

	@RequiresPermissions("affair:affairSuperiorGrant:view")
	@RequestMapping(value = "form")
	public String form(AffairSuperiorGrant affairSuperiorGrant, Model model) {
		model.addAttribute("affairSuperiorGrant", affairSuperiorGrant);
		return "modules/affair/affairSuperiorGrantForm";
	}

	@RequiresPermissions("affair:affairSuperiorGrant:edit")
	@RequestMapping(value = "save")
	public String save(AffairSuperiorGrant affairSuperiorGrant, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairSuperiorGrant)){
			return form(affairSuperiorGrant, model);
		}
		affairSuperiorGrantService.save(affairSuperiorGrant);
		addMessage(redirectAttributes, "保存上级拨款成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairSuperiorGrantForm";
	}
	
	@RequiresPermissions("affair:affairSuperiorGrant:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSuperiorGrant affairSuperiorGrant, RedirectAttributes redirectAttributes) {
		affairSuperiorGrantService.delete(affairSuperiorGrant);
		addMessage(redirectAttributes, "删除上级拨款成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSuperiorGrant/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairSuperiorGrant:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSuperiorGrantService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairSuperiorGrant affairSuperiorGrant, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairSuperiorGrant> page = null;
			if(flag == true){
				page = affairSuperiorGrantService.findPage(new Page<AffairSuperiorGrant>(request, response), affairSuperiorGrant);
			}else {
				page = affairSuperiorGrantService.findPage(new Page<AffairSuperiorGrant>(request, response,-1), affairSuperiorGrant);
			}
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new  XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairSuperiorGrant.class);
			exportExcelNew.setWb(wb);
			List<AffairSuperiorGrant> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairSuperiorGrant?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairSuperiorGrant> list = ei.getDataList(AffairSuperiorGrant.class);
			for (AffairSuperiorGrant affairSuperiorGrant : list){
				try{
					BeanValidators.validateWithException(validator, affairSuperiorGrant);
					affairSuperiorGrantService.save(affairSuperiorGrant);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(" 导入失败："+ex.getMessage());
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