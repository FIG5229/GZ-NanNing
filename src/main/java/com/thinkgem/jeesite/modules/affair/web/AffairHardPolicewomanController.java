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
import com.thinkgem.jeesite.modules.affair.entity.AffairHardPolicewoman;
import com.thinkgem.jeesite.modules.affair.service.AffairHardPolicewomanService;
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
 * 困难女民警申报Controller
 * @author cecil.li
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairHardPolicewoman")
public class AffairHardPolicewomanController extends BaseController {

	@Autowired
	private UploadController uploadController;
	@Autowired
	private AffairHardPolicewomanService affairHardPolicewomanService;
	
	@ModelAttribute
	public AffairHardPolicewoman get(@RequestParam(required=false) String id) {
		AffairHardPolicewoman entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairHardPolicewomanService.get(id);
		}
		if (entity == null){
			entity = new AffairHardPolicewoman();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairHardPolicewoman:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairHardPolicewoman affairHardPolicewoman, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairHardPolicewoman> page = affairHardPolicewomanService.findPage(new Page<AffairHardPolicewoman>(request, response), affairHardPolicewoman); 
		model.addAttribute("page", page);
		return "modules/affair/affairHardPolicewomanList";
	}

	@RequiresPermissions("affair:affairHardPolicewoman:view")
	@RequestMapping(value = "form")
	public String form(AffairHardPolicewoman affairHardPolicewoman, Model model) {
		model.addAttribute("affairHardPolicewoman", affairHardPolicewoman);
		return "modules/affair/affairHardPolicewomanForm";
	}

	@RequiresPermissions("affair:affairHardPolicewoman:edit")
	@RequestMapping(value = "save")
	public String save(AffairHardPolicewoman affairHardPolicewoman, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairHardPolicewoman)){
			return form(affairHardPolicewoman, model);
		}
		affairHardPolicewomanService.save(affairHardPolicewoman);
		addMessage(redirectAttributes, "保存困难女民警申报成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairHardPolicewomanForm";
	}
	
	@RequiresPermissions("affair:affairHardPolicewoman:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairHardPolicewoman affairHardPolicewoman, RedirectAttributes redirectAttributes) {
		affairHardPolicewomanService.delete(affairHardPolicewoman);
		addMessage(redirectAttributes, "删除困难女民警申报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairHardPolicewoman/?repage";
	}

	/**
	 * 详情
	 * @param affairHardPolicewoman
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairHardPolicewoman:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairHardPolicewoman affairHardPolicewoman, Model model) {
		model.addAttribute("affairHardPolicewoman", affairHardPolicewoman);
		if(affairHardPolicewoman.getFilePath() != null && affairHardPolicewoman.getFilePath().length() > 0){
			List<Map<String, String>> materialPathList = uploadController.filePathHandle(affairHardPolicewoman.getFilePath());
			model.addAttribute("filePathList",materialPathList );
		}
		return "modules/affair/affairHardPolicewomanFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairHardPolicewoman:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairHardPolicewomanService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairHardPolicewoman affairHardPolicewoman, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairHardPolicewoman> page = null;
			if(flag == true){
				page = affairHardPolicewomanService.findPage(new Page<AffairHardPolicewoman>(request, response), affairHardPolicewoman);
			}else{
				page = affairHardPolicewomanService.findPage(new Page<AffairHardPolicewoman>(request, response,-1), affairHardPolicewoman);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairHardPolicewoman.class);
			exportExcelNew.setWb(wb);
			List<AffairHardPolicewoman> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairHardPolicewoman/?repage";
	}


	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairHardPolicewoman> list = ei.getDataList(AffairHardPolicewoman.class);
			for (AffairHardPolicewoman affairHardPolicewoman : list){
				try{
					BeanValidators.validateWithException(validator, affairHardPolicewoman);
					affairHardPolicewomanService.save(affairHardPolicewoman);
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