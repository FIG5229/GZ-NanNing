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
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairMediaManagement;
import com.thinkgem.jeesite.modules.affair.service.AffairMediaManagementService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 刊用情况Controller
 * @author cecil.li
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairMediaManagement")
public class AffairMediaManagementController extends BaseController {

	@Autowired
	private AffairMediaManagementService affairMediaManagementService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairMediaManagement get(@RequestParam(required=false) String id) {
		AffairMediaManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairMediaManagementService.get(id);
		}
		if (entity == null){
			entity = new AffairMediaManagement();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairMediaManagement:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairMediaManagement affairMediaManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairMediaManagement> page = affairMediaManagementService.findPage(new Page<AffairMediaManagement>(request, response), affairMediaManagement); 
		model.addAttribute("page", page);
		return "modules/affair/affairMediaManagementList";
	}

	@RequiresPermissions("affair:affairMediaManagement:view")
	@RequestMapping(value = "form")
	public String form(AffairMediaManagement affairMediaManagement, Model model) {
		model.addAttribute("affairMediaManagement", affairMediaManagement);
		return "modules/affair/affairMediaManagementForm";
	}

	@RequiresPermissions("affair:affairMediaManagement:edit")
	@RequestMapping(value = "save")
	public String save(AffairMediaManagement affairMediaManagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairMediaManagement)){
			return form(affairMediaManagement, model);
		}
		affairMediaManagementService.save(affairMediaManagement);
		addMessage(redirectAttributes, "保存刊用情况成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairMediaManagementForm";
	}
	
	@RequiresPermissions("affair:affairMediaManagement:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairMediaManagement affairMediaManagement, RedirectAttributes redirectAttributes) {
		affairMediaManagementService.delete(affairMediaManagement);
		addMessage(redirectAttributes, "删除团组织换届成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairMediaManagement/?repage";
	}

	/**
	 * 详情
	 * @param affairMediaManagement
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMediaManagement:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairMediaManagement affairMediaManagement, Model model) {
		model.addAttribute("affairMediaManagement", affairMediaManagement);
		if(affairMediaManagement.getAnnex() != null && affairMediaManagement.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairMediaManagement.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairMediaManagementFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairMediaManagement:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairMediaManagementService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 审核
	 * @return
	 */
	@RequiresPermissions("affair:affairMediaManagement:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog(String ids,Model model) {
		model.addAttribute("ids",ids);
		return "modules/affair/affairMediaManagementCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairMediaManagement:edit")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairMediaManagement affairMediaManagement,HttpServletRequest request) {
		String opinion = affairMediaManagement.getOpinion();
		String Status = affairMediaManagement.getStatus();
		String idsStr = request.getParameter("ids");
		String[] idsArray = idsStr.split(",");
		List<String> userList = new ArrayList<String>();
		Collections.addAll(userList,idsArray);
		List <AffairMediaManagement> list = affairMediaManagementService.findByIds(userList);
		for (AffairMediaManagement  affairMediaManagementFromDb:list){
			affairMediaManagementFromDb.setOpinion(opinion);
			affairMediaManagementFromDb.setStatus(Status);
			affairMediaManagementService.save(affairMediaManagementFromDb);
		}
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
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
	public String exportExcelByTemplate(AffairMediaManagement affairMediaManagement, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairMediaManagement> page = null;
			if(flag == true){
				page = affairMediaManagementService.findPage(new Page<AffairMediaManagement>(request, response), affairMediaManagement);
			}else{
				page = affairMediaManagementService.findPage(new Page<AffairMediaManagement>(request, response,-1), affairMediaManagement);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairMediaManagement.class);
			exportExcelNew.setWb(wb);
			List<AffairMediaManagement> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairMediaManagement/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairMediaManagement> list = ei.getDataList(AffairMediaManagement.class);
			for (AffairMediaManagement affairMediaManagement : list){
				try{
					BeanValidators.validateWithException(validator, affairMediaManagement);
					affairMediaManagementService.save(affairMediaManagement);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("导入失败："+ex.getMessage());
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