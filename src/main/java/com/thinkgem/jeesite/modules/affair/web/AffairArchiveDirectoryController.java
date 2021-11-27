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
import com.thinkgem.jeesite.modules.affair.entity.AffairArchiveDirectory;
import com.thinkgem.jeesite.modules.affair.service.AffairArchiveDirectoryService;
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
 * 档案目录Controller
 * @author mason.xv
 * @version 2019-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairArchiveDirectory")
public class AffairArchiveDirectoryController extends BaseController {

	@Autowired
	private AffairArchiveDirectoryService affairArchiveDirectoryService;
	
	@ModelAttribute
	public AffairArchiveDirectory get(@RequestParam(required=false) String id) {
		AffairArchiveDirectory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairArchiveDirectoryService.get(id);
		}
		if (entity == null){
			entity = new AffairArchiveDirectory();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairArchiveDirectory:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairArchiveDirectory affairArchiveDirectory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairArchiveDirectory> page = affairArchiveDirectoryService.findPage(new Page<AffairArchiveDirectory>(request, response), affairArchiveDirectory); 
		model.addAttribute("page", page);
		return "modules/affair/affairArchiveDirectoryList";
	}

	@RequiresPermissions("affair:affairArchiveDirectory:view")
	@RequestMapping(value = "form")
	public String form(AffairArchiveDirectory affairArchiveDirectory, Model model) {
		model.addAttribute("affairArchiveDirectory", affairArchiveDirectory);
		return "modules/affair/affairArchiveDirectoryForm";
	}

	@RequiresPermissions("affair:affairArchiveDirectory:edit")
	@RequestMapping(value = "save")
	public String save(AffairArchiveDirectory affairArchiveDirectory, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairArchiveDirectory)){
			return form(affairArchiveDirectory, model);
		}
		affairArchiveDirectoryService.save(affairArchiveDirectory);
		addMessage(redirectAttributes, "保存档案目录成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairArchiveDirectoryForm";
	}
	
	@RequiresPermissions("affair:affairArchiveDirectory:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairArchiveDirectory affairArchiveDirectory, RedirectAttributes redirectAttributes) {
		affairArchiveDirectoryService.delete(affairArchiveDirectory);
		addMessage(redirectAttributes, "删除档案目录成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairArchiveDirectory/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairArchiveDirectory:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairArchiveDirectoryService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairArchiveDirectory:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairArchiveDirectory affairArchiveDirectory, Model model) {
		model.addAttribute("affairArchiveDirectory", affairArchiveDirectory);
		return "modules/affair/affairArchiveDirectoryFormDetail";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairArchiveDirectory> list = ei.getDataList(AffairArchiveDirectory.class);
			for (AffairArchiveDirectory affairArchiveDirectory : list){
				try{
					BeanValidators.validateWithException(validator, affairArchiveDirectory);
					affairArchiveDirectoryService.save(affairArchiveDirectory);
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
	public String exportExcelByTemplate(AffairArchiveDirectory affairArchiveDirectory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairArchiveDirectory> page = affairArchiveDirectoryService.findPage(new Page<AffairArchiveDirectory>(request, response,-1), affairArchiveDirectory);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairArchiveDirectory.class);
			exportExcelNew.setWb(wb);
			List<AffairArchiveDirectory> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairArchiveDirectory";
	}
}