/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainPlan;
import com.thinkgem.jeesite.modules.affair.service.AffairTrainPlanService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

/**
 * 培训计划报表Controller
 * @author alan.wu
 * @version 2020-07-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTrainPlan")
public class AffairTrainPlanController extends BaseController {

	@Autowired
	private AffairTrainPlanService affairTrainPlanService;

	@Autowired
	private OfficeService officeService;

	
	@ModelAttribute
	public AffairTrainPlan get(@RequestParam(required=false) String id) {
		AffairTrainPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTrainPlanService.get(id);
		}
		if (entity == null){
			entity = new AffairTrainPlan();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTrainPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTrainPlan affairTrainPlan, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) {

		String year = affairTrainPlan.getYear();
		if (StringUtils.isNotBlank(year)){
			session.setAttribute("year",year);
		}
		if (StringUtils.isBlank(year)){
			Calendar calendar = Calendar.getInstance();
			int ye = calendar.get(Calendar.YEAR);
			String yea = String.valueOf(ye);
			session.setAttribute("year",yea);
			affairTrainPlan.setYear(yea);
		}
		Page<AffairTrainPlan> page = affairTrainPlanService.findPage(new Page<AffairTrainPlan>(request, response), affairTrainPlan);
		model.addAttribute("page", page);
		return "modules/affair/affairTrainPlanList";
	}

	@RequiresPermissions("affair:affairTrainPlan:view")
	@RequestMapping(value = "form")
	public String form(AffairTrainPlan affairTrainPlan, Model model) {
		model.addAttribute("affairTrainPlan", affairTrainPlan);
		return "modules/affair/affairTrainPlanForm";
	}

	@RequiresPermissions("affair:affairTrainPlan:edit")
	@RequestMapping(value = "save")
	public String save(AffairTrainPlan affairTrainPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTrainPlan)){
			return form(affairTrainPlan, model);
		}
		affairTrainPlanService.save(affairTrainPlan);
		addMessage(redirectAttributes, "保存培训计划报表成功");
		model.addAttribute("saveResult", "success");
		return "modules/affair/affairTrainPlanForm";
	}
	
	@RequiresPermissions("affair:affairTrainPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTrainPlan affairTrainPlan, RedirectAttributes redirectAttributes) {
		affairTrainPlanService.delete(affairTrainPlan);
		addMessage(redirectAttributes, "删除培训计划报表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTrainPlan/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTrainPlan:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTrainPlanService.deleteByIds(ids);
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
	 * @param affairTrainPlan
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTrainPlan:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTrainPlan affairTrainPlan, Model model) {
		model.addAttribute("affairTrainPlan", affairTrainPlan);
		return "modules/affair/affairTrainPlanFormDetail";
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
	public String exportExcelByTemplate(AffairTrainPlan affairTrainPlan, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairTrainPlan.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}


			Page<AffairTrainPlan> page = null;
			if(flag == true){
				page = affairTrainPlanService.findPage(new Page<AffairTrainPlan>(request, response), affairTrainPlan);
			}else {
				page = affairTrainPlanService.findPage(new Page<AffairTrainPlan>(request, response,-1), affairTrainPlan);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairTrainPlan.class);
			exportExcelNew.setWb(wb);
			List<AffairTrainPlan> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTrainPlan?repage";
	}



	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session ) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AffairTrainPlan> list = ei.getDataList(AffairTrainPlan.class);
			for (AffairTrainPlan affairTrainPlan : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairTrainPlan.getUnit());
					if(orgId != null){
						affairTrainPlan.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, affairTrainPlan);

					/*Object year = session.getAttribute("year");
					String ye = String.valueOf(year);
					affairTrainPlan.setYear(ye);*/

					String str = affairTrainPlan.getName();
					String year = str.substring(0,4);
					affairTrainPlan.setYear(year);


					affairTrainPlanService.save(affairTrainPlan);
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