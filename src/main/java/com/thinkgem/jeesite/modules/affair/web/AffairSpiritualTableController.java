/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.service.AffairSpiritualTableService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 复查表Controller
 * @author alan.wu
 * @version 2020-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSpiritualTable")
public class AffairSpiritualTableController extends BaseController {

	@Autowired
	private AffairSpiritualTableService affairSpiritualTableService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairSpiritualTable get(@RequestParam(required=false) String id) {
		AffairSpiritualTable entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSpiritualTableService.get(id);
		}
		if (entity == null){
			entity = new AffairSpiritualTable();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSpiritualTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSpiritualTable affairSpiritualTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSpiritualTable> page = affairSpiritualTableService.findPage(new Page<AffairSpiritualTable>(request, response), affairSpiritualTable); 
		model.addAttribute("page", page);
		return "modules/affair/affairSpiritualTableList";
	}

	@RequiresPermissions("affair:affairSpiritualTable:view")
	@RequestMapping(value = "form")
	public String form(AffairSpiritualTable affairSpiritualTable, Model model) {
		model.addAttribute("affairSpiritualTable", affairSpiritualTable);
		return "modules/affair/affairSpiritualTableForm";
	}

	@RequiresPermissions("affair:affairSpiritualTable:edit")
	@RequestMapping(value = "save")
	public String save(AffairSpiritualTable affairSpiritualTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairSpiritualTable)){
			return form(affairSpiritualTable, model);
		}
		affairSpiritualTableService.save(affairSpiritualTable);
		addMessage(redirectAttributes, "保存复查表成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairSpiritualTableForm";
	}
	
	@RequiresPermissions("affair:affairSpiritualTable:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSpiritualTable affairSpiritualTable, RedirectAttributes redirectAttributes) {
		affairSpiritualTableService.delete(affairSpiritualTable);
		addMessage(redirectAttributes, "删除复查表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSpiritualTable/?repage";
	}


	/**
	 * 详情
	 *
	 * @param affairSpiritualTable
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairSpiritualTable:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairSpiritualTable affairSpiritualTable, Model model) {
		model.addAttribute("affairSpiritualTable", affairSpiritualTable);
		if (affairSpiritualTable.getAdjunct() != null && affairSpiritualTable.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairSpiritualTable.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairSpiritualTableFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairSpiritualTable:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSpiritualTableService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}



	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairSpiritualTable> list = ei.getDataList(AffairSpiritualTable.class);
			for (AffairSpiritualTable affairSpiritualTable : list){
				try{
					affairSpiritualTable.setUnitId(officeService.findByName(affairSpiritualTable.getUnit()));
					BeanValidators.validateWithException(validator, affairSpiritualTable);
					affairSpiritualTableService.save(affairSpiritualTable);
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


	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairSpiritualTable affairSpiritualTable, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairSpiritualTable> page = null;
			if(flag == true){
				page = affairSpiritualTableService.findPage(new Page<AffairSpiritualTable>(request, response), affairSpiritualTable);
			}else {
				page = affairSpiritualTableService.findPage(new Page<AffairSpiritualTable>(request, response,-1), affairSpiritualTable);
			}
			//主表信息（导出表）
			List<AffairSpiritualTable> affairSpiritualTableList = page.getList();


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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairSpiritualTable.class);
			exportExcelNew.setWb(wb);
			exportExcelNew.setDataList(affairSpiritualTableList);
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
		return "redirect:" + adminPath + "/affair/affairSpiritualTable/?repage";
	}





}