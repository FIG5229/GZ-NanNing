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
import com.thinkgem.jeesite.modules.affair.entity.AffairLedgerEntryTimes;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairOnlineLearning;
import com.thinkgem.jeesite.modules.affair.service.AffairOnlineLearningService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 线上学习Controller
 * @author cecil.li
 * @version 2020-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOnlineLearning")
public class AffairOnlineLearningController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairOnlineLearningService affairOnlineLearningService;
	
	@ModelAttribute
	public AffairOnlineLearning get(@RequestParam(required=false) String id) {
		AffairOnlineLearning entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOnlineLearningService.get(id);
		}
		if (entity == null){
			entity = new AffairOnlineLearning();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairOnlineLearning:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairOnlineLearning affairOnlineLearning, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairOnlineLearning> page = affairOnlineLearningService.findPage(new Page<AffairOnlineLearning>(request, response), affairOnlineLearning); 
		model.addAttribute("page", page);
		return "modules/affair/affairOnlineLearningList";
	}

	@RequiresPermissions("affair:affairOnlineLearning:view")
	@RequestMapping(value = "form")
	public String form(AffairOnlineLearning affairOnlineLearning, Model model) {
		model.addAttribute("affairOnlineLearning", affairOnlineLearning);
		return "modules/affair/affairOnlineLearningForm";
	}

	@RequiresPermissions("affair:affairOnlineLearning:edit")
	@RequestMapping(value = "save")
	public String save(AffairOnlineLearning affairOnlineLearning, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairOnlineLearning)){
			return form(affairOnlineLearning, model);
		}
		affairOnlineLearningService.save(affairOnlineLearning);
		addMessage(redirectAttributes, "保存线上学习成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairOnlineLearningForm";
	}
	
	@RequiresPermissions("affair:affairOnlineLearning:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOnlineLearning affairOnlineLearning, RedirectAttributes redirectAttributes) {
		affairOnlineLearningService.delete(affairOnlineLearning);
		addMessage(redirectAttributes, "删除线上学习成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOnlineLearning/?repage";
	}

	@RequiresPermissions("affair:affairOnlineLearning:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairOnlineLearning affairOnlineLearning, Model model) {
		model.addAttribute("affairOnlineLearning", affairOnlineLearning);
		return "modules/affair/affairOnlineLearningFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairOnlineLearning:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairOnlineLearningService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairOnlineLearning affairOnlineLearning, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairOnlineLearning> page = null;
			if(flag == true){
				page = affairOnlineLearningService.findPage(new Page<AffairOnlineLearning>(request, response), affairOnlineLearning);
			}else{
				page = affairOnlineLearningService.findPage(new Page<AffairOnlineLearning>(request, response,-1), affairOnlineLearning);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairOnlineLearning.class);
			exportExcelNew.setWb(wb);
			List<AffairOnlineLearning> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairOnlineLearning?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairOnlineLearning> list = ei.getDataList(AffairOnlineLearning.class);
			for (AffairOnlineLearning affairOnlineLearning : list){
				try{
					BeanValidators.validateWithException(validator, affairOnlineLearning);
					affairOnlineLearning.setUnitId(officeService.findByName(affairOnlineLearning.getUnit()));
					affairOnlineLearningService.save(affairOnlineLearning);
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