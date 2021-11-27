/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.xml.crypto.Data;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.entity.AffairAdminStatistics;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnDaily;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStatistics;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganzationStatistics;
import com.thinkgem.jeesite.modules.affair.service.AffairOrganzationStatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 机构部门学习统计Controller
 * @author alan.wu
 * @version 2020-07-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOrganzationStatistics")
public class AffairOrganzationStatisticsController extends BaseController {

	@Autowired
	private AffairOrganzationStatisticsService affairOrganzationStatisticsService;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairOrganzationStatistics get(@RequestParam(required=false) String id) {
		AffairOrganzationStatistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOrganzationStatisticsService.get(id);
		}
		if (entity == null){
			entity = new AffairOrganzationStatistics();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairOrganzationStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairOrganzationStatistics affairOrganzationStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairOrganzationStatistics> page = affairOrganzationStatisticsService.findPage(new Page<AffairOrganzationStatistics>(request, response), affairOrganzationStatistics); 
		model.addAttribute("page", page);
		return "modules/affair/affairOrganzationStatisticsList";
	}

	@RequiresPermissions("affair:affairOrganzationStatistics:view")
	@RequestMapping(value = "form")
	public String form(AffairOrganzationStatistics affairOrganzationStatistics, Model model) {
		model.addAttribute("affairOrganzationStatistics", affairOrganzationStatistics);
		return "modules/affair/affairOrganzationStatisticsForm";
	}

	@RequiresPermissions("affair:affairOrganzationStatistics:edit")
	@RequestMapping(value = "save")
	public String save(AffairOrganzationStatistics affairOrganzationStatistics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairOrganzationStatistics)){
			return form(affairOrganzationStatistics, model);
		}

		Date data = null;
		if ("".equals(affairOrganzationStatistics.getBeginTime()) || null == affairOrganzationStatistics.getBeginTime()){
			affairOrganzationStatistics.setBeginTime(data);
		}
		if ("".equals(affairOrganzationStatistics.getEndTime()) || null == affairOrganzationStatistics.getEndTime()){
			affairOrganzationStatistics.setEndTime(data);
		}

		affairOrganzationStatisticsService.save(affairOrganzationStatistics);
		addMessage(redirectAttributes, "保存机构部门学习统计成功");
		model.addAttribute("saveResult", "success");
		return "modules/affair/affairOrganzationStatisticsForm";
	}
	
	@RequiresPermissions("affair:affairOrganzationStatistics:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOrganzationStatistics affairOrganzationStatistics, RedirectAttributes redirectAttributes) {
		affairOrganzationStatisticsService.delete(affairOrganzationStatistics);
		addMessage(redirectAttributes, "删除机构部门学习统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOrganzationStatistics/?repage";
	}


	@ResponseBody
	@RequiresPermissions("affair:affairOrganzationStatistics:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairOrganzationStatisticsService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}


	/**
	 * 详情
	 *
	 * @param affairOrganzationStatistics
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairOrganzationStatistics:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairOrganzationStatistics affairOrganzationStatistics, Model model) {
		model.addAttribute("affairOrganzationStatistics", affairOrganzationStatistics);
		return "modules/affair/affairOrganzationStatisticsFormDetail";
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
	public String exportExcelByTemplate(AffairOrganzationStatistics affairOrganzationStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairOrganzationStatistics.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairOrganzationStatistics> page = null;
			if(flag == true){
				page = affairOrganzationStatisticsService.findPage(new Page<AffairOrganzationStatistics>(request, response), affairOrganzationStatistics);
			}else {
				page = affairOrganzationStatisticsService.findPage(new Page<AffairOrganzationStatistics>(request, response,-1), affairOrganzationStatistics);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairOrganzationStatistics.class);
			exportExcelNew.setWb(wb);
			List<AffairOrganzationStatistics> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairOrganzationStatistics?repage";
	}



	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairOrganzationStatistics> list = ei.getDataList(AffairOrganzationStatistics.class);
			for (AffairOrganzationStatistics affairOrganzationStatistics : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairOrganzationStatistics.getUnit());
					if(orgId != null){
						affairOrganzationStatistics.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, affairOrganzationStatistics);
					affairOrganzationStatisticsService.save(affairOrganzationStatistics);
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