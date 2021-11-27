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
import com.thinkgem.jeesite.modules.affair.entity.AffairStudyEducation;
import com.thinkgem.jeesite.modules.affair.service.AffairStudyEducationService;
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
import java.util.Map;

/**
 * 学习教育Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairStudyEducation")
public class AffairStudyEducationController extends BaseController {

	@Autowired
	private AffairStudyEducationService affairStudyEducationService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairStudyEducation get(@RequestParam(required=false) String id) {
		AffairStudyEducation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairStudyEducationService.get(id);
		}
		if (entity == null){
			entity = new AffairStudyEducation();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairStudyEducation:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairStudyEducation affairStudyEducation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairStudyEducation> page = affairStudyEducationService.findPage(new Page<AffairStudyEducation>(request, response), affairStudyEducation); 
		model.addAttribute("page", page);
		return "modules/affair/affairStudyEducationList";
	}

	@RequiresPermissions("affair:affairStudyEducation:view")
	@RequestMapping(value = "form")
	public String form(AffairStudyEducation affairStudyEducation, Model model) {
		model.addAttribute("affairStudyEducation", affairStudyEducation);
		return "modules/affair/affairStudyEducationForm";
	}

	@RequiresPermissions("affair:affairStudyEducation:edit")
	@RequestMapping(value = "save")
	public String save(AffairStudyEducation affairStudyEducation, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairStudyEducation)){
			return form(affairStudyEducation, model);
		}
		affairStudyEducationService.save(affairStudyEducation);
		addMessage(redirectAttributes, "保存学习教育成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairStudyEducationForm";
	}
	
	@RequiresPermissions("affair:affairStudyEducation:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairStudyEducation affairStudyEducation, RedirectAttributes redirectAttributes) {
		affairStudyEducationService.delete(affairStudyEducation);
		addMessage(redirectAttributes, "删除学习教育成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairStudyEducation/?repage";
	}

	/**
	 * 详情
	 * @param affairStudyEducation
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairStudyEducation:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairStudyEducation affairStudyEducation, Model model) {
		model.addAttribute("affairStudyEducation", affairStudyEducation);
		if(affairStudyEducation.getFilePath() != null && affairStudyEducation.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairStudyEducation.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairStudyEducationFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairStudyEducation:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairStudyEducationService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairStudyEducation affairStudyEducation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairStudyEducation> page = null;
			if(flag == true){
				page = affairStudyEducationService.findPage(new Page<AffairStudyEducation>(request, response), affairStudyEducation);
			}else {
				page = affairStudyEducationService.findPage(new Page<AffairStudyEducation>(request, response,-1), affairStudyEducation);
			}
/*
			Page<AffairStudyEducation> page = affairStudyEducationService.findPage(new Page<AffairStudyEducation>(request, response,-1), affairStudyEducation);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairStudyEducation.class);
			exportExcelNew.setWb(wb);
			List<AffairStudyEducation> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairStudyEducation/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairStudyEducation> list = ei.getDataList(AffairStudyEducation.class);
			for (AffairStudyEducation affairStudyEducation : list){
				try{
					BeanValidators.validateWithException(validator, affairStudyEducation);
					affairStudyEducationService.save(affairStudyEducation);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append( "导入失败："+ex.getMessage());
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