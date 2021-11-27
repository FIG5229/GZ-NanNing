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
import com.thinkgem.jeesite.modules.affair.entity.AffairCasualtyReport;
import com.thinkgem.jeesite.modules.affair.entity.AffairCasualtyReportStatistic;
import com.thinkgem.jeesite.modules.affair.service.AffairCasualtyReportService;
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
import java.util.Map;

/**
 * 抚恤申报Controller
 * @author mason.xv
 * @version 2019-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCasualtyReport")
public class AffairCasualtyReportController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairCasualtyReportService affairCasualtyReportService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairCasualtyReport get(@RequestParam(required = false) String id) {
		AffairCasualtyReport entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = affairCasualtyReportService.get(id);
		}
		if (entity == null) {
			entity = new AffairCasualtyReport();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairCasualtyReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCasualtyReport affairCasualtyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCasualtyReport> page = affairCasualtyReportService.findPage(new Page<AffairCasualtyReport>(request, response), affairCasualtyReport);
		model.addAttribute("page", page);
		return "modules/affair/affairCasualtyReportList";
	}

	@RequiresPermissions("affair:affairCasualtyReport:view")
	@RequestMapping(value = "form")
	public String form(AffairCasualtyReport affairCasualtyReport, Model model) {
		model.addAttribute("affairCasualtyReport", affairCasualtyReport);
		return "modules/affair/affairCasualtyReportForm";
	}

	@RequiresPermissions("affair:affairCasualtyReport:edit")
	@RequestMapping(value = "save")
	public String save(AffairCasualtyReport affairCasualtyReport, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairCasualtyReport)) {
			return form(affairCasualtyReport, model);
		}
		affairCasualtyReportService.save(affairCasualtyReport);
		addMessage(redirectAttributes, "保存抚恤申报成功");
		request.setAttribute("saveResult", "success");
		return "modules/affair/affairCasualtyReportForm";
	}

	@RequiresPermissions("affair:affairCasualtyReport:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCasualtyReport affairCasualtyReport, RedirectAttributes redirectAttributes) {
		affairCasualtyReportService.delete(affairCasualtyReport);
		addMessage(redirectAttributes, "删除抚恤申报成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairCasualtyReport/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCasualtyReport:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairCasualtyReportService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 批量提交
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairCasualtyReport:edit")
	@RequestMapping(value = {"submitByIds"})
	public Result submitByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCasualtyReportService.submitByIds(ids);
			result.setSuccess(true);
			result.setMessage("提交成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要提交的内容");
		}
		return result;
	}
	
	//这是用来跳转manage页面的controller
	@RequiresPermissions("affair:affairCasualtyReport:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairCasualtyReport affairCasualtyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairCasualtyReport.setHasAuth("1");
		Page<AffairCasualtyReport> page = affairCasualtyReportService.findPage(new Page<AffairCasualtyReport>(request, response), affairCasualtyReport);
		model.addAttribute("page", page);
		return "modules/affair/affairCasualtyReportManage";
	}

	//这是用来跳转dialog页面的controller
	@RequiresPermissions("affair:affairCasualtyReport:manage")
	@RequestMapping(value = {"Dialog"})
	public String shenHeDialog() {
		return "modules/affair/affairCasualtyReportDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCasualtyReport:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairCasualtyReport affairCasualtyReport) {
		affairCasualtyReportService.shenHe(affairCasualtyReport);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
	@RequiresPermissions("affair:affairCasualtyReport:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCasualtyReport affairCasualtyReport, Model model) {
		model.addAttribute("affairCasualtyReport", affairCasualtyReport);
		if(affairCasualtyReport.getRdygxsFile() != null && affairCasualtyReport.getRdygxsFile().length() > 0){
			List<Map<String, String>> RdygxsFileList = uploadController.filePathHandle(affairCasualtyReport.getRdygxsFile());
			model.addAttribute("filePathList", RdygxsFileList);
		}
		if(affairCasualtyReport.getBmfhrdFile() != null && affairCasualtyReport.getBmfhrdFile().length() > 0){
			List<Map<String, String>> BmfhrdFileList = uploadController.filePathHandle(affairCasualtyReport.getBmfhrdFile());
			model.addAttribute("filePathList1", BmfhrdFileList);
		}
		if(affairCasualtyReport.getLspfFile() != null && affairCasualtyReport.getLspfFile().length() > 0){
			List<Map<String, String>> LspfFileList = uploadController.filePathHandle(affairCasualtyReport.getLspfFile());
			model.addAttribute("filePathList2", LspfFileList);
		}
		if(affairCasualtyReport.getCertificatePath() != null && affairCasualtyReport.getCertificatePath().length() > 0){
			List<Map<String, String>> CertificatePath = uploadController.filePathHandle(affairCasualtyReport.getCertificatePath());
			model.addAttribute("filePathList3", CertificatePath);
		}
		return "modules/affair/affairCasualtyReportFormDetail";
	}

	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairCasualtyReport> list = ei.getDataList(AffairCasualtyReport.class);
			for (AffairCasualtyReport affairCasualtyReport : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairCasualtyReport.getAffirmDep());
					if(orgId != null){
						affairCasualtyReport.setAffirmDepId(orgId);
					}
					String nextId = officeService.findByName(affairCasualtyReport.getDepName());
					if(orgId != null){
						affairCasualtyReport.setDepNameId(nextId);
					}
					BeanValidators.validateWithException(validator, affairCasualtyReport);
					affairCasualtyReportService.save(affairCasualtyReport);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairCasualtyReport.getName()+"(身份证号码:"+affairCasualtyReport.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	public String exportExcelByTemplate(AffairCasualtyReport affairCasualtyReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairCasualtyReport> page = affairCasualtyReportService.findPage(new Page<AffairCasualtyReport>(request, response,-1), affairCasualtyReport);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairCasualtyReport.class);
			exportExcelNew.setWb(wb);
			List<AffairCasualtyReport> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairCasualtyReport/?repage";
	}
	/**
	 * 伤亡信息查询初始页面
	 * @param affairCasualtyReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairCasualtyReport:view")
	@RequestMapping(value = {"statistic"})
	public String statistic(AffairCasualtyReport affairCasualtyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairCasualtyReportStatistic> statistic = affairCasualtyReportService.statistic(affairCasualtyReport);
		model.addAttribute("statistic", statistic);
		return "modules/affair/affairCasualtyReportListSum";
	}

	/**
	 * 伤亡信息第二层统计汇总   点单位弹窗/条件查询
	 * @param affairCasualtyReportStatistic
	 * @param affairCasualtyReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = {"findByUnitId"})
	public String findByUnitId(AffairCasualtyReportStatistic affairCasualtyReportStatistic,AffairCasualtyReport affairCasualtyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairCasualtyReportStatistic> statistic = affairCasualtyReportService.findByUnitId(affairCasualtyReportStatistic,affairCasualtyReport);
		model.addAttribute("statistic", statistic);
		model.addAttribute("unitId", affairCasualtyReportStatistic.getUnitId());
		return "modules/affair/affairCasualtyReportListSum2";
	}

	/**
	 * 伤亡信息第二层统计汇总   点种类数量出现的弹窗
	 * @param affairCasualtyReportStatistic
	 * @param affairCasualtyReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = {"findOneTypeByUnitId"})
	public String findOneTypeByUnitId(AffairCasualtyReportStatistic affairCasualtyReportStatistic,AffairCasualtyReport affairCasualtyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairCasualtyReportStatistic> statistic = affairCasualtyReportService.findOneTypeByUnitId(affairCasualtyReportStatistic,affairCasualtyReport);
		Integer sum = affairCasualtyReportService.sum(statistic);
		model.addAttribute("statistic", statistic);
		model.addAttribute("typeName", affairCasualtyReportStatistic.getTypeName());
		model.addAttribute("type", affairCasualtyReport.getType());
		model.addAttribute("sum", sum);
		model.addAttribute("unitId", affairCasualtyReportStatistic.getUnitId());
		return "modules/affair/affairCasualtyReportListSum3";
	}

	/**
	 * 伤亡信息细页面
	 * @param affairCasualtyReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = {"mingXi"})
	public String mingXi(AffairCasualtyReport affairCasualtyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCasualtyReport> page = affairCasualtyReportService.findPage(new Page<AffairCasualtyReport>(request, response), affairCasualtyReport);
		model.addAttribute("page", page);
		return "modules/affair/affairCasualtyReportMingXiList";
	}

}



