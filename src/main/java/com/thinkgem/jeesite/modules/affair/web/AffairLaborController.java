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
import com.thinkgem.jeesite.modules.affair.dao.AffairLaborDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLabor;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborService;
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
import java.util.Calendar;
import java.util.List;

/**
 * 劳资Controller
 * @author cecil.li
 * @version 2020-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLabor")
public class AffairLaborController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairLaborService affairLaborService;

	@Autowired
	private AffairLaborDao affairLaborDao;

	@Autowired
	private AffairLaborOfficeService affairLaborOfficeService;

	@ModelAttribute
	public AffairLabor get(@RequestParam(required=false) String id) {
		AffairLabor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLaborService.get(id);
		}
		if (entity == null){
			entity = new AffairLabor();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairLabor:view")
	@RequestMapping(value = {""})
	public String index(AffairLabor affairLabor) {
		return "modules/affair/affairLaborIndex";
	}

	@RequiresPermissions("affair:affairLabor:view")
	@RequestMapping(value = {"list"})
	public String list(AffairLabor affairLabor, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairLabor.getTreeId());
		String ye = affairLabor.getYear();
		if (StringUtils.isBlank(ye)){
			Calendar date = Calendar.getInstance();
			String year = String.valueOf(date.get(Calendar.YEAR));
			String month = String.valueOf(date.get(Calendar.MONTH)+1);
			affairLabor.setYear(year);
			affairLabor.setMonth(month);
		}
		Page<AffairLabor> page = affairLaborService.findPage(new Page<AffairLabor>(request, response,-1), affairLabor);
		model.addAttribute("page", page);
		return "modules/affair/affairLaborList";
	}

	@RequiresPermissions("affair:affairLabor:view")
	@RequestMapping(value = "form")
	public String form(AffairLabor affairLabor, Model model) {
		model.addAttribute("affairLabor", affairLabor);
		return "modules/affair/affairLaborForm";
	}

	@RequiresPermissions("affair:affairLabor:edit")
	@RequestMapping(value = "save")
	public String save(AffairLabor affairLabor, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairLabor)){
			return form(affairLabor, model);
		}
		Double defult = 0.0;
		Calendar calendar = Calendar.getInstance();
		if (affairLabor.getYear() == null || "".equals(affairLabor.getYear())) {
			affairLabor.setYear(String.valueOf(calendar.get(Calendar.YEAR)));
		}

		//国家统一津贴补贴合计
		if (affairLabor.getJiabanAllowance() == null || affairLabor.getJiabanAllowance().equals("")) {
			affairLabor.setJiabanAllowance(defult);
		}if (affairLabor.getJingxianAllowance() == null || affairLabor.getJingxianAllowance().equals("")) {
			affairLabor.setJingxianAllowance(defult);
		}if (affairLabor.getZhiqinAllowance() == null || affairLabor.getZhiqinAllowance().equals("")){
			affairLabor.setZhiqinAllowance(defult);
		}if (affairLabor.getJkbyAllowance() == null || affairLabor.getJkbyAllowance().equals("")) {
			affairLabor.setJkbyAllowance(defult);
		}if (affairLabor.getTelephoneFee() == null || affairLabor.getTelephoneFee().equals("")) {
			affairLabor.setTelephoneFee(defult);
		}if (affairLabor.getMobileFee() == null || affairLabor.getMobileFee().equals("")) {
			affairLabor.setMobileFee(defult);
		}if (affairLabor.getJszrAllowance() == null || affairLabor.getJszrAllowance().equals("")) {
			affairLabor.setJszrAllowance(defult);
		}if (affairLabor.getNvHygieneFee() == null || affairLabor.getNvHygieneFee().equals("")) {
			affairLabor.setNvHygieneFee(defult);
		}if (affairLabor.getOnlyChildAllowance() == null || affairLabor.getOnlyChildAllowance().equals("")) {
			affairLabor.setOnlyChildAllowance(defult);
		}if (affairLabor.getFangshuAllowance() == null || affairLabor.getFangshuAllowance().equals("")) {
			affairLabor.setFangshuAllowance(defult);
		}if (affairLabor.getXinfangAllowance() == null || affairLabor.getXinfangAllowance().equals("")) {
			affairLabor.setXinfangAllowance(defult);
		}
		if (affairLabor.getGonggai1993Allowance() == null || affairLabor.getGonggai1993Allowance().equals("")) {
			affairLabor.setGonggai1993Allowance(defult);
		}if (affairLabor.getXizangAllowance() == null || affairLabor.getXizangAllowance().equals("")) {
			affairLabor.setXizangAllowance(defult);
		}if (affairLabor.getHighAltitudeAllowance() == null || affairLabor.getHighAltitudeAllowance().equals("")) {
			affairLabor.setHighAltitudeAllowance(defult);
		}if (affairLabor.getXinjiangAllowance() == null || affairLabor.getXinjiangAllowance().equals("")) {
			affairLabor.setXinjiangAllowance(defult);
		}if (affairLabor.getSarAllowance() == null || affairLabor.getSarAllowance().equals("")) {
			affairLabor.setSarAllowance(defult);
		}if (affairLabor.getTownshipAllowance() == null || affairLabor.getTownshipAllowance().equals("")) {
			affairLabor.setTownshipAllowance(defult);
		}if (affairLabor.getLinshiAllowance() == null || affairLabor.getLinshiAllowance().equals("")) {
			affairLabor.setLinshiAllowance(defult);
		}

		Double gjSum = affairLabor.getJiabanAllowance() +
				affairLabor.getJingxianAllowance() +
				affairLabor.getZhiqinAllowance() +
				affairLabor.getJkbyAllowance() +
				affairLabor.getTelephoneFee() +
				affairLabor.getMobileFee() +
				affairLabor.getJszrAllowance() +
				affairLabor.getNvHygieneFee() +
				affairLabor.getOnlyChildAllowance() +
				affairLabor.getFangshuAllowance() +
				affairLabor.getXinfangAllowance() +
				affairLabor.getGonggai1993Allowance() +
				affairLabor.getXizangAllowance() +
				affairLabor.getHighAltitudeAllowance() +
				affairLabor.getXinjiangAllowance() +
				affairLabor.getSarAllowance() +
				affairLabor.getTownshipAllowance() +
				affairLabor.getLinshiAllowance();

		affairLabor.setGjSum(gjSum);

		if (affairLabor.getWorkingAllowance() == null || affairLabor.getWorkingAllowance().equals("")){
			affairLabor.setWorkingAllowance(defult);
		}if (affairLabor.getLivingAllowance() == null || affairLabor.getLivingAllowance().equals("")){
			affairLabor.setLivingAllowance(defult);
		}


		//规范津贴补贴合计
		Double guifanSum = affairLabor.getWorkingAllowance() +
				affairLabor.getLivingAllowance();
		affairLabor.setGuifanSum(guifanSum);


		if (affairLabor.getZhuzhaiAllowance() == null || affairLabor.getZhuzhaiAllowance().equals("")){
			affairLabor.setZhuzhaiAllowance(defult);
		}if (affairLabor.getZhufangAllowance() == null || affairLabor.getZhufangAllowance().equals("")){
			affairLabor.setZhufangAllowance(defult);
		}if (affairLabor.getWuyeAllowance() == null || affairLabor.getWuyeAllowance().equals("")){
			affairLabor.setWuyeAllowance(defult);
		}if (affairLabor.getJiaotongAllowance() == null || affairLabor.getJiaotongAllowance().equals("")){
			affairLabor.setJiaotongAllowance(defult);
		}if (affairLabor.getGaigeAllowance() == null || affairLabor.getGaigeAllowance().equals("")){
			affairLabor.setGaigeAllowance(defult);
		}

		//改革性补贴合计
		Double gaigeSum = affairLabor.getZhuzhaiAllowance() +
				affairLabor.getZhufangAllowance() +
				affairLabor.getWuyeAllowance() +
				affairLabor.getJiaotongAllowance() +
				affairLabor.getGaigeAllowance();
		affairLabor.setGaigeSum(gaigeSum);

		if (affairLabor.getChengwuAllowance() == null || affairLabor.getChengwuAllowance().equals("")){
			affairLabor.setChengwuAllowance(defult);
		}if (affairLabor.getXianluAllowance() == null || affairLabor.getXianluAllowance().equals("")){
			affairLabor.setXianluAllowance(defult);
		}if (affairLabor.getAnquanAllowance() == null || affairLabor.getAnquanAllowance().equals("")){
			affairLabor.setAnquanAllowance(defult);
		}if (affairLabor.getJingshenAllowance() == null || affairLabor.getJingshenAllowance().equals("")){
			affairLabor.setJingshenAllowance(defult);
		}if (affairLabor.getJingchaAllowance() == null || affairLabor.getJingchaAllowance().equals("")){
			affairLabor.setJingchaAllowance(defult);
		}if (affairLabor.getGongwuyuanAllowance() == null || affairLabor.getGongwuyuanAllowance().equals("")){
			affairLabor.setGongwuyuanAllowance(defult);
		}if (affairLabor.getJianyuanAllowance() == null || affairLabor.getJianyuanAllowance().equals("")){
			affairLabor.setJianyuanAllowance(defult);
		}if (affairLabor.getJiangkeAllowance() == null || affairLabor.getJiangkeAllowance().equals("")){
			affairLabor.setJiangkeAllowance(defult);
		}if (affairLabor.getGongziAllowance() == null || affairLabor.getGongziAllowance().equals("")){
			affairLabor.setGongziAllowance(defult);
		}if (affairLabor.getYearEndAwards() == null || affairLabor.getYearEndAwards().equals("")){
			affairLabor.setYearEndAwards(defult);
		}



		//奖励性补贴合计
		Double rewardSum = affairLabor.getChengwuAllowance()+
				affairLabor.getXianluAllowance() +
				affairLabor.getAnquanAllowance() +
				affairLabor.getJingshenAllowance() +
				affairLabor.getJingchaAllowance() +
				affairLabor.getGongwuyuanAllowance() +
				affairLabor.getJianyuanAllowance() +
				affairLabor.getJiangkeAllowance() +
				affairLabor.getGongziAllowance();

		affairLabor.setRewardSum(rewardSum);


		//当月应发工资合计
		Double sum = affairLabor.getJbSalary() +
				affairLabor.getJbGradeSalary() +
				affairLabor.getGjSum() +
				affairLabor.getGuifanSum() +
				affairLabor.getGaigeSum() +
				affairLabor.getRewardSum() +
				affairLabor.getYearEndAwards();

				affairLabor.setSum(sum);


		if (affairLabor.getBaseYanglaoAllowance() == null || affairLabor.getBaseYanglaoAllowance().equals("")){
			affairLabor.setBaseYanglaoAllowance(defult);
		}if (affairLabor.getZhiyeAllowance() == null || affairLabor.getZhiyeAllowance().equals("")){
			affairLabor.setZhiyeAllowance(defult);
		}if (affairLabor.getBaseYiliaoAllowance() == null || affairLabor.getBaseYiliaoAllowance().equals("")){
			affairLabor.setBaseYiliaoAllowance(defult);
		}if (affairLabor.getBuchongYiliaoAllowance() == null || affairLabor.getBuchongYiliaoAllowance().equals("")){
			affairLabor.setBuchongYiliaoAllowance(defult);
		}if (affairLabor.getShengyuAllowance() == null || affairLabor.getShengyuAllowance().equals("")){
			affairLabor.setShengyuAllowance(defult);
		}if (affairLabor.getGongjijin() == null || affairLabor.getGongjijin().equals("")){
			affairLabor.setGongjijin(defult);
		}if (affairLabor.getPersonalIncomeTax() == null || affairLabor.getPersonalIncomeTax().equals("")){
			affairLabor.setPersonalIncomeTax(defult);
		}

		//保险类合计
		Double paySum = affairLabor.getBaseYanglaoAllowance() +
				affairLabor.getZhiyeAllowance() +
				affairLabor.getBaseYiliaoAllowance() +
				affairLabor.getBuchongYiliaoAllowance() +
				affairLabor.getShengyuAllowance() +
				affairLabor.getGongjijin()+
				affairLabor.getPersonalIncomeTax();

		affairLabor.setBaoxianSum(paySum);

		affairLaborService.save(affairLabor);

		addMessage(redirectAttributes, "保存劳资成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairLaborForm";
	}
	
	@RequiresPermissions("affair:affairLabor:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLabor affairLabor, RedirectAttributes redirectAttributes) {
		affairLaborService.delete(affairLabor);
		addMessage(redirectAttributes, "删除劳资成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLabor/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairLabor:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLaborService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairLabor affairLabor, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		String treeId = affairLabor.getTreeId();

		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairLabor> page = null;
			if(flag == true){
				page = affairLaborService.findPage(new Page<AffairLabor>(request, response), affairLabor);
			}else {
				page = affairLaborService.findPage(new Page<AffairLabor>(request, response,-1), affairLabor);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(3, AffairLabor.class);
			exportExcelNew.setWb(wb);
			List<AffairLabor> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairLabor/list?repage&treeId="+treeId;
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 3, 0);
			//修改
			List<AffairLabor> list = ei.getDataList(AffairLabor.class);
			for (AffairLabor affairLabor : list){
				try{
					if (affairLabor.getUnit()!=null&&!"".equals(affairLabor.getUnit())) {
						affairLabor.setUnitId(affairLaborOfficeService.findByName(affairLabor.getUnit()));
					}
					BeanValidators.validateWithException(validator, affairLabor);
					affairLaborService.save(affairLabor);
					successNum++;

				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(单位:"+affairLabor.getUnit()+")"+" 导入失败："+ex.getMessage());
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
	 * 详情
	 *
	 * @param affairLabor
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairLabor:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLabor affairLabor, Model model) {


		List<AffairLabor> al = affairLaborDao.selectBeanByIdNumber(affairLabor.getIdNumber(), affairLabor.getYear());


		model.addAttribute("affairLabor", al);

		return "modules/affair/affairSalaryGatherFormDetail";
	}


}