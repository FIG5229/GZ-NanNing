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
import com.thinkgem.jeesite.modules.affair.entity.AffairInformationReport;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairTeamPerformance;
import com.thinkgem.jeesite.modules.affair.service.AffairTeamPerformanceService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 比武团体成绩Controller
 * @author cecil.li
 * @version 2020-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTeamPerformance")
public class AffairTeamPerformanceController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairTeamPerformanceService affairTeamPerformanceService;
	
	@ModelAttribute
	public AffairTeamPerformance get(@RequestParam(required=false) String id) {
		AffairTeamPerformance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTeamPerformanceService.get(id);
		}
		if (entity == null){
			entity = new AffairTeamPerformance();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTeamPerformance:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTeamPerformance affairTeamPerformance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTeamPerformance> page = affairTeamPerformanceService.findPage(new Page<AffairTeamPerformance>(request, response), affairTeamPerformance); 
		model.addAttribute("page", page);
		return "modules/affair/affairTeamPerformanceList";
	}

	@RequiresPermissions("affair:affairTeamPerformance:view")
	@RequestMapping(value = "form")
	public String form(AffairTeamPerformance affairTeamPerformance, Model model) {
		model.addAttribute("affairTeamPerformance", affairTeamPerformance);
		return "modules/affair/affairTeamPerformanceForm";
	}

	@RequiresPermissions("affair:affairTeamPerformance:edit")
	@RequestMapping(value = "save")
	public String save(AffairTeamPerformance affairTeamPerformance, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairTeamPerformance)){
			return form(affairTeamPerformance, model);
		}
		affairTeamPerformanceService.save(affairTeamPerformance);
		addMessage(redirectAttributes, "保存比武团体成绩成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTeamPerformanceForm";
	}
	
	@RequiresPermissions("affair:affairTeamPerformance:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTeamPerformance affairTeamPerformance, RedirectAttributes redirectAttributes) {
		affairTeamPerformanceService.delete(affairTeamPerformance);
		addMessage(redirectAttributes, "删除比武团体成绩成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTeamPerformance/?repage";
	}

	@RequiresPermissions("affair:affairTeamPerformance:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTeamPerformance affairTeamPerformance, Model model) {
		model.addAttribute("affairTeamPerformance", affairTeamPerformance);
		return "modules/affair/affairTeamPerformanceFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTeamPerformance:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTeamPerformanceService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTeamPerformance affairTeamPerformance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairTeamPerformance> page = null;
			if(flag == true){
				page = affairTeamPerformanceService.findPage(new Page<AffairTeamPerformance>(request, response), affairTeamPerformance);
			}else{
				page = affairTeamPerformanceService.findPage(new Page<AffairTeamPerformance>(request, response,-1), affairTeamPerformance);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTeamPerformance.class);
			exportExcelNew.setWb(wb);
			List<AffairTeamPerformance> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTeamPerformance?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTeamPerformance> list = ei.getDataList(AffairTeamPerformance.class);
			for (AffairTeamPerformance affairTeamPerformance : list){
				try{
					BeanValidators.validateWithException(validator, affairTeamPerformance);
					affairTeamPerformance.setUnitId(officeService.findByName(affairTeamPerformance.getUnit()));
					affairTeamPerformanceService.save(affairTeamPerformance);
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