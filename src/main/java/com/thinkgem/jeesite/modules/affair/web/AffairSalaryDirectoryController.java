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
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryDirectory;
import com.thinkgem.jeesite.modules.affair.service.AffairSalaryDirectoryService;
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
 * 工资目录Controller
 * @author mason.xv
 * @version 2019-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSalaryDirectory")
public class AffairSalaryDirectoryController extends BaseController {

	@Autowired
	private AffairSalaryDirectoryService affairSalaryDirectoryService;
	
	@ModelAttribute
	public AffairSalaryDirectory get(@RequestParam(required=false) String id) {
		AffairSalaryDirectory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSalaryDirectoryService.get(id);
		}
		if (entity == null){
			entity = new AffairSalaryDirectory();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSalaryDirectory:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSalaryDirectory affairSalaryDirectory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSalaryDirectory> page = affairSalaryDirectoryService.findPage(new Page<AffairSalaryDirectory>(request, response), affairSalaryDirectory); 
		model.addAttribute("page", page);
		return "modules/affair/affairSalaryDirectoryList";
	}

	@RequiresPermissions("affair:affairSalaryDirectory:view")
	@RequestMapping(value = "form")
	public String form(AffairSalaryDirectory affairSalaryDirectory, Model model) {
		model.addAttribute("affairSalaryDirectory", affairSalaryDirectory);
		return "modules/affair/affairSalaryDirectoryForm";
	}

	@RequiresPermissions("affair:affairSalaryDirectory:edit")
	@RequestMapping(value = "save")
	public String save(AffairSalaryDirectory affairSalaryDirectory, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairSalaryDirectory)){
			return form(affairSalaryDirectory, model);
		}
		affairSalaryDirectoryService.save(affairSalaryDirectory);
		addMessage(redirectAttributes, "保存工资目录成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairSalaryDirectoryForm";
	}
	
	@RequiresPermissions("affair:affairSalaryDirectory:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSalaryDirectory affairSalaryDirectory, RedirectAttributes redirectAttributes) {
		affairSalaryDirectoryService.delete(affairSalaryDirectory);
		addMessage(redirectAttributes, "删除工资目录成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSalaryDirectory/?repage";
	}
	@RequiresPermissions("affair:affairSalaryDirectory:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairSalaryDirectory affairSalaryDirectory, Model model) {
		model.addAttribute("affairSalaryDirectory", affairSalaryDirectory);
		return "modules/affair/affairSalaryDirectoryFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairSalaryDirectory:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSalaryDirectoryService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairSalaryDirectory> list = ei.getDataList(AffairSalaryDirectory.class);
			for (AffairSalaryDirectory affairSalaryDirectory : list){
				try{
					BeanValidators.validateWithException(validator, affairSalaryDirectory);
					affairSalaryDirectoryService.save(affairSalaryDirectory);
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
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairSalaryDirectory affairSalaryDirectory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairSalaryDirectory> page = affairSalaryDirectoryService.findPage(new Page<AffairSalaryDirectory>(request, response,-1), affairSalaryDirectory);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairSalaryDirectory.class);
			exportExcelNew.setWb(wb);
			List<AffairSalaryDirectory> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairSalaryDirectory";
	}
}