/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

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
import com.thinkgem.jeesite.modules.affair.entity.AffairMedicalInsurance;
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryGather;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelEndowmentInsuranceDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelEndowmentInsurance;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelEndowmentInsuranceService;
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
import java.util.List;
import java.util.Map;

//import sun.java2d.pipe.AAShapePipe;

/**
 * 养老保险Controller
 * @author alan.wu
 * @version 2020-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelEndowmentInsurance")
public class PersonnelEndowmentInsuranceController extends BaseController {

	@Autowired
	private PersonnelEndowmentInsuranceService personnelEndowmentInsuranceService;

	@Autowired
	private PersonnelEndowmentInsuranceDao personnelEndowmentInsuranceDao;

	@Autowired
	private AffairLaborDao affairLaborDao;

	@Autowired
	private AffairLaborOfficeService affairLaborOfficeService;

	@Autowired
	private PersonnelBaseService personnelBaseService;

	
	@ModelAttribute
	public PersonnelEndowmentInsurance get(@RequestParam(required=false) String id) {
		PersonnelEndowmentInsurance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelEndowmentInsuranceService.get(id);
		}
		if (entity == null){
			entity = new PersonnelEndowmentInsurance();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelEndowmentInsurance:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelEndowmentInsurance personnelEndowmentInsurance, HttpServletRequest request, HttpServletResponse response, Model model) {

		/*String ye = personnelEndowmentInsurance.getTimeYear();
		//所有的人的身份证号
		List<AffairLabor> affairLaborList = affairLaborDao.selectMessage();
		if (StringUtils.isNotBlank(ye)){
			int y = Integer.parseInt(ye);
			int yer = y - 1;
			String year = String.valueOf(yer);
			for (int i =0;i < affairLaborList.size();i++){
				//个人基础信息
				AffairLabor affairLabor = affairLaborList.get(i);

				//身份证号
				String idn = affairLabor.getIdNumber();

				//姓名
				String name = affairLabor.getName();

				//根据身份证号和年份统计对应的缴费基数及缴费金额
				AffairLabor affairLabor1 = affairLaborDao.selectJFJS(idn,year);

				if (affairLabor1 != null){

				//缴费基数总和
				double jfjs = affairLabor1.getJbSalary()
						+ affairLabor1.getJbGradeSalary()
						+ affairLabor1.getGuifanSum()
						+ affairLabor1.getJingxianAllowance()
						+ affairLabor1.getJkbyAllowance()
						+ affairLabor1.getYearEndAwards()
						+ affairLabor1.getAnquanAllowance();

				personnelEndowmentInsurance.setTimeYear(year);
				personnelEndowmentInsurance.setName(name);
				personnelEndowmentInsurance.setIdNumber(idn);
				personnelEndowmentInsurance.setCardinalNumber(jfjs);

				//养老保险个人缴费比例
				double ygrbl = 0.08;
				double ygr = ygrbl * 100;

				//养老保险单位缴费比例
				double ydwjfbl = 0.16;
				double ydw = ydwjfbl * 100;

				//职业年金个人缴费比例
				double zgrbl = 0.04;
				double zgr = zgrbl * 100;

				//职业年金单位缴费比例
				double zdwjfbl = 0.08;
				double zdw = zdwjfbl * 100;

				//养老保险个人月缴费
				double ygryjf = jfjs * ygrbl;

				//养老保险单位月缴费
				double ydwyjf = jfjs * ydwjfbl;

				//职业年金个人月缴费
				double zgryjf = jfjs * zgrbl;

				//职业年金单位月缴费
				double zdwyjf = jfjs * zdwjfbl;

				personnelEndowmentInsurance.setTimeYear(year);
				personnelEndowmentInsurance.setCardinalNumber(jfjs);

				personnelEndowmentInsurance.setIndividualProportion(ygr);
				personnelEndowmentInsurance.setIndividualPayment(ygryjf);

				personnelEndowmentInsurance.setUnitProportion(ydw);
				personnelEndowmentInsurance.setUnitPayment(ydwyjf);

				personnelEndowmentInsurance.setOaIndividualProportion(zgr);
				personnelEndowmentInsurance.setOaIndividualPayment(zgryjf);

				personnelEndowmentInsurance.setOaUnitProportion(zdw);
				personnelEndowmentInsurance.setOaUnitPayment(zdwyjf);

				PersonnelEndowmentInsurance personnelEndowmentInsurance1 = personnelEndowmentInsuranceDao.selectByIy(idn,year);

				if (personnelEndowmentInsurance1 == null){
					personnelEndowmentInsuranceService.save(personnelEndowmentInsurance);
				}
				personnelEndowmentInsuranceDao.update(personnelEndowmentInsurance);
				}

			}
		}*/


		Page<PersonnelEndowmentInsurance> page = personnelEndowmentInsuranceService.findPage(new Page<PersonnelEndowmentInsurance>(request, response,-1), personnelEndowmentInsurance);
  		model.addAttribute("page", page);
		return "modules/personnel/personnelEndowmentInsuranceList";
	}

	@RequiresPermissions("personnel:personnelEndowmentInsurance:view")
	@RequestMapping(value = "form")
	public String form(PersonnelEndowmentInsurance personnelEndowmentInsurance, Model model) {
		model.addAttribute("personnelEndowmentInsurance", personnelEndowmentInsurance);
		return "modules/personnel/personnelEndowmentInsuranceForm";
	}

	@RequiresPermissions("personnel:personnelEndowmentInsurance:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelEndowmentInsurance personnelEndowmentInsurance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, personnelEndowmentInsurance)){
			return form(personnelEndowmentInsurance, model);
		}
		personnelEndowmentInsuranceService.save(personnelEndowmentInsurance);
		addMessage(redirectAttributes, "保存养老保险成功");


		model.addAttribute("saveResult","sucess");
		return "modules/personnel/personnelEndowmentInsuranceForm";
	}
	
	@RequiresPermissions("personnel:personnelEndowmentInsurance:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelEndowmentInsurance personnelEndowmentInsurance, RedirectAttributes redirectAttributes) {
		personnelEndowmentInsuranceService.delete(personnelEndowmentInsurance);
		addMessage(redirectAttributes, "删除养老保险成功");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelEndowmentInsurance/?repage";
	}
	/**
	 * 详情
	 *
	 * @param personnelEndowmentInsurance
	 * @param model
	 * @return
	 */
	@RequiresPermissions("personnel:personnelEndowmentInsurance:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelEndowmentInsurance personnelEndowmentInsurance, Model model) {
		model.addAttribute("personnelEndowmentInsurance", personnelEndowmentInsurance);
		return "modules/personnel/personnelEndowmentInsuranceFormDetail";
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("personnel:personnelEndowmentInsurance:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			personnelEndowmentInsuranceService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}


	/**
	 * 导出excel格式数据
	 *
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(PersonnelEndowmentInsurance personnelEndowmentInsurance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			personnelEndowmentInsurance.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName");
			}
			Page<PersonnelEndowmentInsurance> page = null;
			if (flag == true) {
				page = personnelEndowmentInsuranceService.findPage(new Page<PersonnelEndowmentInsurance>(request, response), personnelEndowmentInsurance);
			} else {
				page = personnelEndowmentInsuranceService.findPage(new Page<PersonnelEndowmentInsurance>(request, response, -1), personnelEndowmentInsurance);
			}

 			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream) {
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelEndowmentInsurance.class);
			exportExcelNew.setWb(wb);
			List<PersonnelEndowmentInsurance> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/personnel/PersonnelEndowmentInsurance?repage";
	}

	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelEndowmentInsurance> list = ei.getDataList(PersonnelEndowmentInsurance.class);
			for (PersonnelEndowmentInsurance affairLearnPower : list) {
				try {
					affairLearnPower.setUnitId(affairLaborOfficeService.findByName(affairLearnPower.getUnit()));
					BeanValidators.validateWithException(validator, affairLearnPower);
					personnelEndowmentInsuranceService.save(affairLearnPower);
					successNum++;
				} catch (ConstraintViolationException ex) {
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append(" 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result", "success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}


	/*
	*
	*	生成按钮点击
	* */
	/*@RequestMapping(value = "findLastYearMessage", method = RequestMethod.POST)
	public String findLastYearMessage(PersonnelEndowmentInsurance personnelEndowmentInsurance,String timeYear, HttpServletRequest request, HttpServletResponse response, Model model){

		int ye = Integer.parseInt(timeYear);
		int yea = ye - 1;

		//年份减一
		String year1 = String.valueOf(yea);

		Page<PersonnelEndowmentInsurance> page = personnelEndowmentInsuranceService.findPage(new Page<PersonnelEndowmentInsurance>(request, response), personnelEndowmentInsurance);

		//所有的人员基本信息
		List<AffairLabor> affairLaborList = affairLaborDao.selectMessage();
		for (int i = 0;i < affairLaborList.size(); i++){
			 AffairLabor affairLabor = affairLaborList.get(i);

			 //获得身份证号
			 String idNumber = affairLabor.getIdNumber();

			 //根据身份证号查询缴费基数
			AffairLabor affairLabor1 = affairLaborDao.selectJFJS(idNumber,year1);

			if (affairLabor1 != null){
				double jfjs = affairLabor1.getJbSalary()
						+ affairLabor1.getJbGradeSalary()
						+ affairLabor1.getGuifanSum()
						+ affairLabor1.getJingxianAllowance()
						+ affairLabor1.getJkbyAllowance()
						+ affairLabor1.getYearEndAwards()
						+ affairLabor1.getAnquanAllowance();


				//养老保险个人缴费比例
				double ygrbl = 0.08;
				double ygr = ygrbl * 100;

				//养老保险单位缴费比例
				double ydwjfbl = 0.16;
				double ydw = ydwjfbl * 100;

				//职业年金个人缴费比例
				double zgrbl = 0.04;
				double zgr = zgrbl * 100;

				//职业年金单位缴费比例
				double zdwjfbl = 0.08;
				double zdw = zdwjfbl * 100;

				//养老保险个人月缴费
				double ygryjf = jfjs * ygrbl;

				//养老保险单位月缴费
				double ydwyjf = jfjs * ydwjfbl;

				//职业年金个人月缴费
				double zgryjf = jfjs * zgrbl;

				//职业年金单位月缴费
				double zdwyjf = jfjs * zdwjfbl;

				personnelEndowmentInsurance.setIndividualProportion(ygr);
				personnelEndowmentInsurance.setIndividualPayment(ygryjf);

				personnelEndowmentInsurance.setUnitProportion(ydw);
				personnelEndowmentInsurance.setUnitPayment(ydwyjf);

				personnelEndowmentInsurance.setOaIndividualProportion(zgr);
				personnelEndowmentInsurance.setOaIndividualPayment(zgryjf);

				personnelEndowmentInsurance.setOaUnitProportion(zdw);
				personnelEndowmentInsurance.setOaUnitPayment(zdwyjf);

				personnelEndowmentInsuranceService.save(personnelEndowmentInsurance);

			}
		}

		model.addAttribute("page", page);
		return "modules/personnel/personnelEndowmentInsuranceList";

	}*/


	/*
	* 查询按钮--点击查询输入年份的上一年数据
	* */
	@RequestMapping(value = "selectByYear", method = RequestMethod.POST)
	public String selectByYear(PersonnelEndowmentInsurance personnelEndowmentInsurance, HttpServletRequest request, HttpServletResponse response, Model model,String timeYear){


		Page<PersonnelEndowmentInsurance> page = personnelEndowmentInsuranceService.findPage(new Page<PersonnelEndowmentInsurance>(request, response), personnelEndowmentInsurance);

		List<String> idList = affairLaborDao.selectAllId();
		for (int i = 0; i < idList.size();i++){
			
			String idn = idList.get(i);
			int y = Integer.parseInt(timeYear);
			int ye = y - 1;
			String year = String.valueOf(ye);

			AffairLabor affairLabor = affairLaborDao.selectJFJS(idn,year);
			if (affairLabor != null) {
				//缴费基数
				double jfjs = affairLabor.getJbSalary()
						+ affairLabor.getJbGradeSalary()
						+ affairLabor.getGuifanSum()
						+ affairLabor.getJingxianAllowance()
						+ affairLabor.getJkbyAllowance()
						+ affairLabor.getYearEndAwards()
						+ affairLabor.getAnquanAllowance();

				//养老保险个人缴费比例
				double ygrbl = 0.08;
				double ygr = ygrbl * 100;

				//养老保险单位缴费比例
				double ydwjfbl = 0.16;
				double ydw = ydwjfbl * 100;

				//职业年金个人缴费比例
				double zgrbl = 0.04;
				double zgr = zgrbl * 100;

				//职业年金单位缴费比例
				double zdwjfbl = 0.08;
				double zdw = zdwjfbl * 100;

				//养老保险个人月缴费
				double ygryjf = jfjs * ygrbl;

				//养老保险单位月缴费
				double ydwyjf = jfjs * ydwjfbl;

				//职业年金个人月缴费
				double zgryjf = jfjs * zgrbl;

				//职业年金单位月缴费
				double zdwyjf = jfjs * zdwjfbl;

				personnelEndowmentInsurance.setTimeYear(year);
				personnelEndowmentInsurance.setCardinalNumber(jfjs);

				personnelEndowmentInsurance.setIndividualProportion(ygr);
				personnelEndowmentInsurance.setIndividualPayment(ygryjf);

				personnelEndowmentInsurance.setUnitProportion(ydw);
				personnelEndowmentInsurance.setUnitPayment(ydwyjf);

				personnelEndowmentInsurance.setOaIndividualProportion(zgr);
				personnelEndowmentInsurance.setOaIndividualPayment(zgryjf);

				personnelEndowmentInsurance.setOaUnitProportion(zdw);
				personnelEndowmentInsurance.setOaUnitPayment(zdwyjf);
			}

		}

		model.addAttribute("page", page);
		return "modules/personnel/personnelEndowmentInsuranceList";
	}

	@RequestMapping(value = "shengCheng")
	public String shengCheng(PersonnelEndowmentInsurance personnelEndowmentInsurance, Model model) {
		model.addAttribute("personnelEndowmentInsurance", personnelEndowmentInsurance);
		return "modules/personnel/personnelEndowmentInsuranceListDetail";
	}

	@RequestMapping(value = "generate")
	public String generate(PersonnelEndowmentInsurance personnelEndowmentInsurance, Model model, HttpServletRequest request, @RequestParam("individualProportion") Double individualProportion, @RequestParam("unitProportion") Double unitProportion, @RequestParam("oaIndividualProportion") Double oaIndividualProportion, @RequestParam("oaUnitProportion") Double oaUnitProportion, @RequestParam("timeYear") String timeYear){
		Integer lastYeat = Integer.parseInt(timeYear) - 1;
		//获取上年所有的工资汇总
		String userOffice = UserUtils.getUser().getCompany().getId();
		if ("34".equals(userOffice)){
			userOffice = "777";
		}else if ("95".equals(userOffice)){
			userOffice = "888";
		}else if ("156".equals(userOffice)){
			userOffice = "999";
		}
		List<AffairSalaryGather> list  = affairLaborDao.selectAsgList(String.valueOf(lastYeat),"","","",userOffice);
		list.forEach(item->{
			PersonnelEndowmentInsurance person = new PersonnelEndowmentInsurance();
			Map<String, String> unitByIdNumber = personnelBaseService.findUnitByIdNumber(String.valueOf(item.getIdNumber()));
			Double base = null;
			Double jbSalary = null;
			Double jbGradeSalary = null;
			Double guifanSum = null;
			Double jingxianAllowance = null;
			Double jkbyAllowance = null;
			Double yearEndAwards = null;
			Double anquanAllowance = null;

			if (item.getJbSalary() == null){
				jbSalary = 0.0;
			}else {
				jbSalary = item.getJbSalary();
			}
			if (item.getJbGradeSalary() == null){
				jbGradeSalary = 0.0;
			}else {
				jbGradeSalary = item.getJbGradeSalary();
			}
			if (item.getGuifanSum() == null){
				guifanSum = 0.0;
			}else {
				guifanSum = item.getGuifanSum();
			}
			if (item.getJingxianAllowance() == null){
				jingxianAllowance = 0.0;
			}else {
				jingxianAllowance = item.getJingxianAllowance();
			}
			if (item.getJkbyAllowance() == null){
				jkbyAllowance = 0.0;
			}else {
				jkbyAllowance = item.getJkbyAllowance();
			}
			if (item.getYearEndAwards() == null){
				yearEndAwards = 0.0;
			}else {
				yearEndAwards = item.getYearEndAwards();
			}
			if (item.getAnquanAllowance() == null){
				anquanAllowance = 0.0;
			}else {
				anquanAllowance = item.getAnquanAllowance();
			}
			base = Double.valueOf((jbSalary + jbGradeSalary + guifanSum + jingxianAllowance + jkbyAllowance + (yearEndAwards / 12) + (anquanAllowance / 12)) / 12);
			if (base > personnelEndowmentInsurance.getMaxNumber()){
				base = personnelEndowmentInsurance.getMaxNumber();
			}
			//养老保险个人月缴费
			Double ygryjf = base * (individualProportion / 100);
			//养老保险单位月缴费
			Double ydwyjf = base * (unitProportion / 100);
			//职业年金个人月缴费
			Double zgryjf = base * (oaIndividualProportion / 100);
			//职业年金单位月缴费
			Double zdwyjf = base * (oaUnitProportion / 100);
			person.setUnit(unitByIdNumber.get("unit"));
			person.setUnitId(unitByIdNumber.get("unitId"));
			person.setTimeYear(timeYear);
			person.setName(item.getName());
			person.setIdNumber(item.getIdNumber());
			person.setCardinalNumber(base);
			person.setIndividualProportion(individualProportion);
			person.setIndividualPayment(ygryjf);
			person.setUnitProportion(unitProportion);
			person.setUnitPayment(ydwyjf);
			person.setOaIndividualProportion(oaIndividualProportion);
			person.setOaIndividualPayment(zgryjf);
			person.setOaUnitProportion(oaUnitProportion);
			person.setOaUnitPayment(zdwyjf);
			personnelEndowmentInsuranceService.save(person);
		});
		request.setAttribute("saveResult","sucess");
		return "modules/personnel/personnelEndowmentInsuranceForm";
	}

}