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
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyCostDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyCost;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyCostService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 党费管理Controller
 * @author eav.liu
 * @version 2019-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPartyCost")
public class AffairPartyCostController extends BaseController {

	@Autowired
	private AffairPartyCostService affairPartyCostService;

	@Autowired
	private AffairPartyCostDao affairPartyCostDao;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@ModelAttribute
	public AffairPartyCost get(@RequestParam(required=false) String id) {
		AffairPartyCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPartyCostService.get(id);
		}
		if (entity == null){
			entity = new AffairPartyCost();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairPartyCost:view")
	@RequestMapping(value = {""})
	public String index(Model model) {
		model.addAttribute("year", DateUtils.getYear());
		SimpleDateFormat sdf = new SimpleDateFormat("M");
		Date date = new Date();
		String month = sdf.format(date);
		model.addAttribute("month",month);
		return "modules/affair/affairPartyCostIndex";
	}

	
	@RequiresPermissions("affair:affairPartyCost:view")
	@RequestMapping(value = {"list"})
	public String list(AffairPartyCost affairPartyCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairPartyCost.getTreeId());
		String treeName = affairPartyCostDao.findTreeNameByTreeId(affairPartyCost.getTreeId());
		model.addAttribute("treeName", treeName);
		affairPartyCost.setYear(request.getParameter("year"));
		affairPartyCost.setMonth(request.getParameter("month"));
		String sumMoney = affairPartyCostDao.findSumMoney(affairPartyCost.getTreeId(),affairPartyCost.getYear(),affairPartyCost.getMonth());
		model.addAttribute("sumMoney",sumMoney);
//		Page<AffairPartyCost> page = affairPartyCostService.findPage(new Page<AffairPartyCost>(request, response), affairPartyCost);
			List<AffairPartyCost> list = affairPartyCostService.findList(affairPartyCost);
		model.addAttribute("page", list);
		return "modules/affair/affairPartyCostList";
	}

	@RequiresPermissions("affair:affairPartyCost:view")
	@RequestMapping(value = "form")
	public String form(AffairPartyCost affairPartyCost, Model model) {
		model.addAttribute("affairPartyCost", affairPartyCost);
		return "modules/affair/affairPartyCostForm";
	}

	@RequiresPermissions("affair:affairPartyCost:edit")
	@RequestMapping(value = "save")
	public String save(AffairPartyCost affairPartyCost, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPartyCost)){
			return form(affairPartyCost, model);
		}
		affairPartyCostService.save(affairPartyCost);
		addMessage(redirectAttributes, "保存党费管理成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairPartyCostForm";
	}
	
	@RequiresPermissions("affair:affairPartyCost:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPartyCost affairPartyCost, RedirectAttributes redirectAttributes) {
		affairPartyCostService.delete(affairPartyCost);
		addMessage(redirectAttributes, "删除党费管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyCost/list?repage&treeId="+affairPartyCost.getTreeId()+"&year="+affairPartyCost.getYear()+"&month="+affairPartyCost.getMonth();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPartyCost:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPartyCostService.deleteByIds(ids);
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
	 * @param affairPartyCost
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPartyCost:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPartyCost affairPartyCost, Model model) {
		model.addAttribute("affairPartyCost", affairPartyCost);
		return "modules/affair/affairPartyCostFormDetail";
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
	public String exportExcelByTemplate(AffairPartyCost affairPartyCost, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairPartyCost> page = null;
			if(flag == true){
				page = affairPartyCostService.findPage(new Page<AffairPartyCost>(request, response), affairPartyCost);
			}else{
				page = affairPartyCostService.findPage(new Page<AffairPartyCost>(request, response,-1), affairPartyCost);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPartyCost.class);
			exportExcelNew.setWb(wb);
			List<AffairPartyCost> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairPartyCost/list?repage&treeId="+affairPartyCost.getTreeId();
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
			List<AffairPartyCost> list = ei.getDataList(AffairPartyCost.class);
			for (AffairPartyCost affairPartyCost : list){
				try{
					BeanValidators.validateWithException(validator, affairPartyCost);
					//处理缴费月份
					affairPartyCostService.save(affairPartyCost);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairPartyCost.getName()+"(身份证号码:"+affairPartyCost.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	 * 提取党员名册数据插入党费表
	 * @return
	 */
	@ResponseBody
	//@RequiresPermissions("affair:affairPartyCost:edit")
	@RequestMapping(value = {"getPMDatas"})
	public Result getPMDatas(HttpServletRequest request) {
		Result result = new Result();
		try {
			String partBranchId = request.getParameter("treeId");//党支部id
			String month = request.getParameter("month");
			String year = request.getParameter("year");
			affairPartyCostService.insertPMDatas(partBranchId,year,month);
			result.setSuccess(true);
			result.setMessage("提取成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("提取失败");
		}
		return result;
	}
}