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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkSummary;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkSummaryService;
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
 * 工委工作总结Controller
 * @author cecil.li
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkSummary")
public class AffairWorkSummaryController extends BaseController {

	@Autowired
	private AffairWorkSummaryService affairWorkSummaryService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairWorkSummary get(@RequestParam(required=false) String id) {
		AffairWorkSummary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkSummaryService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkSummary();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWorkSummary:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkSummary affairWorkSummary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWorkSummary> page = affairWorkSummaryService.findPage(new Page<AffairWorkSummary>(request, response), affairWorkSummary); 
		model.addAttribute("page", page);
		return "modules/affair/affairWorkSummaryList";
	}

	@RequiresPermissions("affair:affairWorkSummary:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkSummary affairWorkSummary, Model model) {
		model.addAttribute("affairWorkSummary", affairWorkSummary);
		return "modules/affair/affairWorkSummaryForm";
	}

	@RequiresPermissions("affair:affairWorkSummary:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkSummary affairWorkSummary, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairWorkSummary)){
			return form(affairWorkSummary, model);
		}
		affairWorkSummaryService.save(affairWorkSummary);
		addMessage(redirectAttributes, "保存工委工作总结成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairWorkSummaryForm";
	}
	
	@RequiresPermissions("affair:affairWorkSummary:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkSummary affairWorkSummary, RedirectAttributes redirectAttributes) {
		affairWorkSummaryService.delete(affairWorkSummary);
		addMessage(redirectAttributes, "删除工委工作总结成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkSummary/?repage";
	}

	/**
	 * 详情
	 * @param affairWorkSummary
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkSummary:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkSummary affairWorkSummary, Model model) {
        model.addAttribute("affairWorkSummary", affairWorkSummary);
		if(affairWorkSummary.getAnnex() != null && affairWorkSummary.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkSummary.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkSummaryFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWorkSummary:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkSummaryService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairWorkSummary affairWorkSummary, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairWorkSummary> page = null;
			if(flag == true){
				page = affairWorkSummaryService.findPage(new Page<AffairWorkSummary>(request, response), affairWorkSummary);
			}else{
				page = affairWorkSummaryService.findPage(new Page<AffairWorkSummary>(request, response,-1), affairWorkSummary);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairWorkSummary.class);
			exportExcelNew.setWb(wb);
			List<AffairWorkSummary> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairWorkSummary/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairWorkSummary> list = ei.getDataList(AffairWorkSummary.class);
			for (AffairWorkSummary affairWorkSummary : list){
				try{
					BeanValidators.validateWithException(validator, affairWorkSummary);
					affairWorkSummaryService.save(affairWorkSummary);
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