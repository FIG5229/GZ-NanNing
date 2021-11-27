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
import com.thinkgem.jeesite.modules.affair.entity.AffairItemReport;
import com.thinkgem.jeesite.modules.affair.service.AffairItemReportService;
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
 * 个人事项报告表Controller
 * @author mason.xv
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairItemReport")
public class AffairItemReportController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairItemReportService affairItemReportService;
	
	@ModelAttribute
	public AffairItemReport get(@RequestParam(required=false) String id) {
		AffairItemReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairItemReportService.get(id);
		}
		if (entity == null){
			entity = new AffairItemReport();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairItemReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairItemReport affairItemReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairItemReport> page = affairItemReportService.findPage(new Page<AffairItemReport>(request, response), affairItemReport); 
		model.addAttribute("page", page);
		return "modules/affair/affairItemReportList";
	}

	@RequiresPermissions("affair:affairItemReport:view")
	@RequestMapping(value = "form")
	public String form(AffairItemReport affairItemReport, Model model) {
		model.addAttribute("affairItemReport", affairItemReport);
		return "modules/affair/affairItemReportForm";
	}

	@RequiresPermissions("affair:affairItemReport:edit")
	@RequestMapping(value = "save")
	public String save(AffairItemReport affairItemReport, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairItemReport)){
			return form(affairItemReport, model);
		}
		affairItemReportService.save(affairItemReport);
		addMessage(redirectAttributes, "保存个人事项报告表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairItemReportForm";
	}
	
	@RequiresPermissions("affair:affairItemReport:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairItemReport affairItemReport, RedirectAttributes redirectAttributes) {
		affairItemReportService.delete(affairItemReport);
		addMessage(redirectAttributes, "删除个人事项报告表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairItemReport/?repage";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairItemReport:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairItemReportService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairItemReport:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairItemReportShenHeDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairItemReport:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairItemReport affairItemReport) {
		affairItemReportService.shenHe(affairItemReport);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@RequiresPermissions("affair:affairItemReport:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairItemReport affairItemReport, Model model) {
		model.addAttribute("affairItemReport", affairItemReport);
		return "modules/affair/affairItemReportFormDetail";
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
	public String exportExcelByTemplate(AffairItemReport affairItemReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairItemReport> page = null;
			if(flag == true){
				page = affairItemReportService.findPage(new Page<AffairItemReport>(request, response), affairItemReport);
			}else{
				page = affairItemReportService.findPage(new Page<AffairItemReport>(request, response,-1), affairItemReport);
			}
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairItemReport.class);
			exportExcelNew.setWb(wb);
			List<AffairItemReport> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairItemReport";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairItemReport> list = ei.getDataList(AffairItemReport.class);
			for (AffairItemReport affairItemReport : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairItemReport.getUnit());
					if(orgId != null){
						affairItemReport.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, affairItemReport);
					affairItemReportService.save(affairItemReport);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairItemReport.getName()+"(身份证号码:"+affairItemReport.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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