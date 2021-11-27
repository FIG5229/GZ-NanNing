/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReport;
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReportStatistic;
import com.thinkgem.jeesite.modules.affair.service.AffairMjxyReportService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
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

/**
 * 民警休养申报Controller
 * @author cecil.li
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairMjxyReport")
public class AffairMjxyReportController extends BaseController {

	@Autowired
	private DictDao dictDao;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairMjxyReportService affairMjxyReportService;

	@ModelAttribute
	public AffairMjxyReport get(@RequestParam(required=false) String id) {
		AffairMjxyReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairMjxyReportService.get(id);
		}
		if (entity == null){
			entity = new AffairMjxyReport();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = {"index"})
	public String index(AffairMjxyReport affairMjxyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		//查看根据时间
		if (affairMjxyReport.getStartDate()!=null){
			//开始休养时间
			affairMjxyReport.setBeginStartDate(affairMjxyReport.getStartDate());
		}
		if (affairMjxyReport.getStartDate()!=null){
			//开始休养截止时间
			affairMjxyReport.setFinishStartDate(affairMjxyReport.getStartDate());
		}
		if (affairMjxyReport.getEndDate()!=null){
			//休养结束开始时间
			affairMjxyReport.setBeginDate(affairMjxyReport.getEndDate());
		}
		if (affairMjxyReport.getEndDate()!=null){
			//休养结束截止时间
			affairMjxyReport.setFinishDate(affairMjxyReport.getEndDate());
		}
		if (affairMjxyReport.getPlace() != null){
			affairMjxyReport.setPlace(affairMjxyReport.getPlace());
		}
		Page<AffairMjxyReport> page = affairMjxyReportService.findPage(new Page<AffairMjxyReport>(request, response), affairMjxyReport);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType", request.getParameter("mType"));
		}
		model.addAttribute("page", page);
		return "modules/affair/affairMjxyReportIndex";
	}

	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairMjxyReport affairMjxyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			affairMjxyReport.setIdNumber(request.getParameter("idNumber"));
		}
		Page<AffairMjxyReport> page = affairMjxyReportService.getList(new Page<AffairMjxyReport>(request, response), affairMjxyReport);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType", request.getParameter("mType"));
		}
		model.addAttribute("page", page);
		return "modules/affair/affairMjxyReportList";
	}

	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = "form")
	public String form(AffairMjxyReport affairMjxyReport, Model model, HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			affairMjxyReport.setIdNumber(request.getParameter("idNumber"));
		}
		model.addAttribute("affairMjxyReport", affairMjxyReport);
		return "modules/affair/affairMjxyReportForm";
	}

	@RequiresPermissions("affair:affairMjxyReport:edit")
	@RequestMapping(value = "save")
	public String save(AffairMjxyReport affairMjxyReport, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairMjxyReport)){
			return form(affairMjxyReport, model,request);
		}
		affairMjxyReportService.save(affairMjxyReport);
		addMessage(redirectAttributes, "保存休养信息集成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairMjxyReportIndex";
	}

	@RequiresPermissions("affair:affairMjxyReport:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairMjxyReport affairMjxyReport, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url =  "redirect:"+Global.getAdminPath()+"/affair/affairMjxyReport/?repage&idNumber="+affairMjxyReport.getIdNumber();
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType", request.getParameter("mType"));
			url = "redirect:"+Global.getAdminPath()+"/affair/affairMjxyReport/?repage&mType="+ request.getParameter("mType");

		}
		affairMjxyReportService.delete(affairMjxyReport);
		addMessage(redirectAttributes, "删除休养信息集成功");
		return url;
	}
	/**
	 * 审核
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReport:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairMjxyReportCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairMjxyReport:edit")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairMjxyReport affairMjxyReport) {
		if(StringUtils.isNotBlank(affairMjxyReport.getTwoCheckId())){
			affairMjxyReport.setTwoCheckMan(dictDao.findLabelByValue(affairMjxyReport.getTwoCheckId()));
		}
		affairMjxyReportService.save(affairMjxyReport);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
	/**
	 * 批量提交
	 * @param
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();
		String oneCheckId = request.getParameter("oneCheckId");
		//根据字典值去查label值（理论上应该从前台都能接到）可优化
		String oneCheckMan = dictDao.findLabelByValue(oneCheckId);
		Collections.addAll(userList,idsArray);
		List <AffairMjxyReport> list = affairMjxyReportService.findByIds(userList);
		for (AffairMjxyReport affairMjxyReport: list){
			affairMjxyReport.setCheckType("2");
			affairMjxyReport.setOneCheckMan(oneCheckMan);
			affairMjxyReport.setOneCheckId(oneCheckId);
			affairMjxyReport.setSubmitId(user.getId());
			affairMjxyReport.setSubmitMan(user.getName());
			affairMjxyReportService.save(affairMjxyReport);
		}
		request.setAttribute("saveResult","success");
		return "modules/affair/affairMjxyReportIndex";
	}
	/**
	 * 详情
	 * @param affairMjxyReport
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairMjxyReport affairMjxyReport, Model model) {
		model.addAttribute("affairMjxyReport", affairMjxyReport);
		return "modules/affair/affairMjxyReportFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairMjxyReport:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairMjxyReportService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairMjxyReport:edit")
	@RequestMapping(value = {"deleteIndex"})
	public String deleteIndex(String type, String unit, String startDate, String endDate,String mType,String place) {
		affairMjxyReportService.deleteByType(type,unit, DateUtils.parseDate(startDate),DateUtils.parseDate(endDate),place);
		return "redirect:"+Global.getAdminPath()+"/affair/affairMjxyReport/?repage&mType="+ mType;
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
	public String exportExcelByTemplate(AffairMjxyReport affairMjxyReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairMjxyReport> page = null;
			if(flag == true){
				page = affairMjxyReportService.findPage(new Page<AffairMjxyReport>(request, response), affairMjxyReport);
			}else {
				page = affairMjxyReportService.findPage(new Page<AffairMjxyReport>(request, response,-1), affairMjxyReport);
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
			//修改
			ExportExcelNew exportExcelNew = new ExportExcelNew(4, AffairMjxyReport.class);
			exportExcelNew.setWb(wb);
			List<AffairMjxyReport> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairMjxyReport/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(HttpServletRequest request, MultipartFile file, RedirectAttributes redirectAttributes) {
		String isCover = request.getParameter("isCover");

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 4, 0);
			//修改
			List<AffairMjxyReport> list = ei.getDataList(AffairMjxyReport.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelResume -> {
					if(personnelResume.getIdNumber()!=null){
						idNumbers.add(personnelResume.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					affairMjxyReportService.deleteByIdNumbers(idNumbers);
			}
			for (AffairMjxyReport affairMjxyReport : list){
				try{

					BeanValidators.validateWithException(validator, affairMjxyReport);
					if (!StringUtils.isEmpty(affairMjxyReport.getUnit())){
						String unitId = officeService.findByName(affairMjxyReport.getUnit());
						affairMjxyReport.setUnitId(unitId);
					}
					/*if (affairMjxyReport.getStartDate()!=null
							&&affairMjxyReport.getEndDate()!=null
							&&StringUtils.isNotBlank(affairMjxyReport.getType())
							&&StringUtils.isNotBlank(affairMjxyReport.getPlace())
					){*/
						affairMjxyReportService.save(affairMjxyReport);
						successNum++;
					/*}else{
						throw new Exception();
					}*/

				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairMjxyReport.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	 * 统计汇总初始页面
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = "affairMjxyReportListSum")
	public String sum(AffairMjxyReport affairMjxyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairMjxyReportStatistic> statistic = affairMjxyReportService.statistic(affairMjxyReport);
		model.addAttribute("statistic", statistic);
		return "modules/affair/affairMjxyReportListSum";
	}

	/**
	 * 第二层统计汇总 点单位弹窗
	 * @param affairMjxyReportStatistic
	 * @param affairMjxyReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = {"findByUnitId"})
	public String findByUnitId(AffairMjxyReportStatistic affairMjxyReportStatistic,AffairMjxyReport affairMjxyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairMjxyReportStatistic> statistic = affairMjxyReportService.findByUnitId(affairMjxyReportStatistic,affairMjxyReport);
        model.addAttribute("statistic", statistic);
		return "modules/affair/affairMjxyReportListSum2";
	}

	/**
	 * 休养明细页面
	 * @param affairMjxyReport
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReport:view")
	@RequestMapping(value = {"mingXi"})
	public String mingXi(AffairMjxyReport affairMjxyReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairMjxyReport> page = affairMjxyReportService.tongJiMingXi(new Page<AffairMjxyReport>(request, response), affairMjxyReport);
		model.addAttribute("page", page);
		return "modules/affair/affairMjxyReportMingXiList";
	}
}