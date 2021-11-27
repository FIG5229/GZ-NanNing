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
import com.thinkgem.jeesite.modules.affair.entity.AffairLiveFire;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairLedgerEntryTimes;
import com.thinkgem.jeesite.modules.affair.service.AffairLedgerEntryTimesService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

/**
 * 台账录入次数Controller
 * @author cecil.li
 * @version 2020-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLedgerEntryTimes")
public class AffairLedgerEntryTimesController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairLedgerEntryTimesService affairLedgerEntryTimesService;
	
	@ModelAttribute
	public AffairLedgerEntryTimes get(@RequestParam(required=false) String id) {
		AffairLedgerEntryTimes entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLedgerEntryTimesService.get(id);
		}
		if (entity == null){
			entity = new AffairLedgerEntryTimes();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLedgerEntryTimes:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLedgerEntryTimes affairLedgerEntryTimes, HttpServletRequest request, HttpServletResponse response, Model model) {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH )+1);
		String monthCopy = "";
	/*	if (month.length() == 1){
			monthCopy = "0" + month;
		}*/
//		String yearMonth = year + "-" +monthCopy;
		String yearMonth = year;
		if (affairLedgerEntryTimes.getYearMonth() == null || "".equals(affairLedgerEntryTimes.getYearMonth())) {
			affairLedgerEntryTimes.setYearMonth(yearMonth);
		}
		Page<AffairLedgerEntryTimes> page = affairLedgerEntryTimesService.findPage(new Page<AffairLedgerEntryTimes>(request, response), affairLedgerEntryTimes);
		model.addAttribute("yearMonth", affairLedgerEntryTimes.getYearMonth());
		model.addAttribute("page", page);
		return "modules/affair/affairLedgerEntryTimesList";
	}

	@RequiresPermissions("affair:affairLedgerEntryTimes:view")
	@RequestMapping(value = "form")
	public String form(AffairLedgerEntryTimes affairLedgerEntryTimes, Model model) {
		model.addAttribute("affairLedgerEntryTimes", affairLedgerEntryTimes);
		return "modules/affair/affairLedgerEntryTimesForm";
	}

	@RequiresPermissions("affair:affairLedgerEntryTimes:edit")
	@RequestMapping(value = "save")
	public String save(AffairLedgerEntryTimes affairLedgerEntryTimes, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairLedgerEntryTimes)){
			return form(affairLedgerEntryTimes, model);
		}
		affairLedgerEntryTimesService.save(affairLedgerEntryTimes);
		addMessage(redirectAttributes, "保存台账录入次数成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairLedgerEntryTimesForm";
	}
	
	@RequiresPermissions("affair:affairLedgerEntryTimes:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLedgerEntryTimes affairLedgerEntryTimes, RedirectAttributes redirectAttributes) {
		affairLedgerEntryTimesService.delete(affairLedgerEntryTimes);
		addMessage(redirectAttributes, "删除台账录入次数成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLedgerEntryTimes/?repage";
	}

	@RequiresPermissions("affair:affairLedgerEntryTimes:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLedgerEntryTimes affairLedgerEntryTimes, Model model) {
		model.addAttribute("affairLedgerEntryTimes", affairLedgerEntryTimes);
		return "modules/affair/affairLedgerEntryTimesFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLedgerEntryTimes:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLedgerEntryTimesService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairLedgerEntryTimes affairLedgerEntryTimes, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairLedgerEntryTimes> page = null;
			if(flag == true){
				page = affairLedgerEntryTimesService.findPage(new Page<AffairLedgerEntryTimes>(request, response), affairLedgerEntryTimes);
			}else{
				page = affairLedgerEntryTimesService.findPage(new Page<AffairLedgerEntryTimes>(request, response,-1), affairLedgerEntryTimes);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLedgerEntryTimes.class);
			exportExcelNew.setWb(wb);
			List<AffairLedgerEntryTimes> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairLedgerEntryTimes?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		AffairLedgerEntryTimes alt = new AffairLedgerEntryTimes();
		List<AffairLedgerEntryTimes> list1 = affairLedgerEntryTimesService.findList(alt);
		for (int i = 0; i < list1.size(); i++) {
			String unitId = list1.get(i).getUnitId();
			String yearMonth = list1.get(i).getYearMonth();
			affairLedgerEntryTimesService.deleteAllInfo(yearMonth, unitId);
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairLedgerEntryTimes> list = ei.getDataList(AffairLedgerEntryTimes.class);
			for (AffairLedgerEntryTimes affairLedgerEntryTimes : list){
				try{
					BeanValidators.validateWithException(validator, affairLedgerEntryTimes);
					affairLedgerEntryTimes.setUnitId(officeService.findByName(affairLedgerEntryTimes.getUnit()));
					affairLedgerEntryTimesService.save(affairLedgerEntryTimes);
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