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
import com.thinkgem.jeesite.modules.affair.dao.AffairMedicalInsuranceDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMedicalInsurance;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
import com.thinkgem.jeesite.modules.affair.service.AffairMedicalInsuranceService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 医疗保险Controller
 * @author jack.xu
 * @version 2020-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairMedicalInsurance")
public class AffairMedicalInsuranceController extends BaseController {

	@Autowired
	private AffairMedicalInsuranceService affairMedicalInsuranceService;

	@Autowired
	private AffairMedicalInsuranceDao affairMedicalInsuranceDao;

	@Autowired
	private AffairLaborOfficeService affairLaborOfficeService;

	@ModelAttribute
	public AffairMedicalInsurance get(@RequestParam(required=false) String id) {
		AffairMedicalInsurance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairMedicalInsuranceService.get(id);
		}
		if (entity == null){
			entity = new AffairMedicalInsurance();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairMedicalInsurance:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairMedicalInsurance affairMedicalInsurance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairMedicalInsurance> page = affairMedicalInsuranceService.findPage(new Page<AffairMedicalInsurance>(request, response,-1), affairMedicalInsurance);
		model.addAttribute("page", page);
		return "modules/affair/affairMedicalInsuranceList";
	}

	@RequiresPermissions("affair:affairMedicalInsurance:view")
	@RequestMapping(value = "form")
	public String form(AffairMedicalInsurance affairMedicalInsurance, Model model) {
		model.addAttribute("affairMedicalInsurance", affairMedicalInsurance);
		return "modules/affair/affairMedicalInsuranceForm";
	}

	@RequiresPermissions("affair:affairMedicalInsurance:edit")
	@RequestMapping(value = "save")
	public String save(AffairMedicalInsurance affairMedicalInsurance, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairMedicalInsurance)){
			return form(affairMedicalInsurance, model);
		}
		affairMedicalInsuranceService.save(affairMedicalInsurance);
		addMessage(redirectAttributes, "保存医疗保险成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairMedicalInsuranceForm";
	}
	
	@RequiresPermissions("affair:affairMedicalInsurance:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairMedicalInsurance affairMedicalInsurance, RedirectAttributes redirectAttributes) {
		affairMedicalInsuranceService.delete(affairMedicalInsurance);
		addMessage(redirectAttributes, "删除医疗保险成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairMedicalInsurance/?repage";
	}
	/**
	 * 详情
	 *
	 * @param affairMedicalInsurance
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMedicalInsurance:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairMedicalInsurance affairMedicalInsurance, Model model,HttpServletRequest request) {
		model.addAttribute("affairMedicalInsurance", affairMedicalInsurance);
		request.setAttribute("saveResult","success");
		return "modules/affair/affairMedicalInsuranceFormDetail";
	}

	/**
	 * 生成详情
	 *
	 * @param affairMedicalInsurance
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMedicalInsurance:view")
	@RequestMapping(value = "shengCheng")
	public String shengCheng(AffairMedicalInsurance affairMedicalInsurance, Model model) {
		model.addAttribute("affairMedicalInsurance", affairMedicalInsurance);
		return "modules/affair/affairMedicalInsuranceListDetail";
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairMedicalInsurance:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairMedicalInsuranceService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairMedicalInsurance affairMedicalInsurance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairMedicalInsurance.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName");
			}
			Page<AffairMedicalInsurance> page = null;
			if (flag == true) {
				page = affairMedicalInsuranceService.findPage(new Page<AffairMedicalInsurance>(request, response), affairMedicalInsurance);
			} else {
				page = affairMedicalInsuranceService.findPage(new Page<AffairMedicalInsurance>(request, response, -1), affairMedicalInsurance);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairMedicalInsurance.class);
			exportExcelNew.setWb(wb);
			List<AffairMedicalInsurance> list = page.getList();
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
		return "redirect:" + adminPath + "/affair/affairMedicalInsurance?repage";
	}

	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairMedicalInsurance> list = ei.getDataList(AffairMedicalInsurance.class);
			for (AffairMedicalInsurance affairLearnPower : list) {
				try {
					affairLearnPower.setUnitId(affairLaborOfficeService.findByName(affairLearnPower.getUnit()));
					BeanValidators.validateWithException(validator, affairLearnPower);
					affairMedicalInsuranceService.save(affairLearnPower);
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

	@RequiresPermissions("affair:affairMedicalInsurance:view")
	@RequestMapping(value = "generate")
	public String generate(AffairMedicalInsurance affairMedicalInsurance, Model model, HttpServletRequest request,@RequestParam("timeYear") String timeYear,@RequestParam("individualProportion") Double individualProportion,@RequestParam("unitProportion") Double unitProportion,@RequestParam("averageSalary") Double averageSalary,@RequestParam("monthAccount") Double monthAccount,@RequestParam("addition") Double addition,@RequestParam("annualProportion") Double annualProportion,@RequestParam("annualProportionOver") Double annualProportionOver) throws ParseException {
		Integer lastYeat = Integer.parseInt(timeYear) - 1;
		String userOffice = UserUtils.getUser().getCompany().getId();
		if ("34".equals(userOffice)){
			userOffice = "777";
		}else if ("95".equals(userOffice)){
			userOffice = "888";
		}else if ("156".equals(userOffice)){
			userOffice = "999";
		}
		List<Map<String, String>> baseList = affairMedicalInsuranceService.findBase(String.valueOf(lastYeat), userOffice);
		//获得今年年份
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		String birth = null;
		for (int i = 0; i < baseList.size(); i++) {
			AffairMedicalInsurance a = new AffairMedicalInsurance();
			a.setTimeYear(timeYear);
			a.setIndividualProportion(individualProportion);
			a.setUnitProportion(unitProportion);
			a.setAverageSalary(averageSalary);
			a.setUnit(baseList.get(i).get("unit"));
			a.setUnitId(baseList.get(i).get("unitId"));
			String name = baseList.get(i).get("names");
			a.setName(name);
			String idNumber = baseList.get(i).get("idnumber");
			a.setIdNumber(idNumber);
			String sex = baseList.get(i).get("sex");
			a.setSex(sex);
			String jishu = String.valueOf(baseList.get(i).get("sum"));
			if (Double.valueOf(jishu) > affairMedicalInsurance.getMaxNumber()){
				jishu = String.valueOf(affairMedicalInsurance.getMaxNumber());
			}
			a.setCardinalNumber(Double.valueOf(jishu));
			String jbgradesalary = String.valueOf(baseList.get(i).get("jbgradesalary"));
			String birthday = String.valueOf(baseList.get(i).get("birthday"));
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			Date date = ft.parse(birthday);
			a.setBirthday(date);
			birth = birthday.substring(0,4);
			int age = nowYear - Integer.valueOf(birth);
			a.setAge(age);
			//月个人缴费
			Double individualPayment = Double.valueOf(jishu) * individualProportion / 100.00;
			a.setIndividualPayment(individualPayment);
			//月单位缴费
			Double unitPayment = Double.valueOf(jishu) * unitProportion / 100.00;
			a.setUnitPayment(unitPayment);
			//月个账划入
			/*Double monthAccountDelimit = Double.valueOf(jishu) * 0.03;
			a.setMonthAccountDelimit(monthAccountDelimit);*/
			a.setMonthAccount(monthAccount);
			Double monthAccountDelimit = Double.valueOf(jishu) * monthAccount / 100.00;
			a.setMonthAccountDelimit(monthAccountDelimit);
			//补充资金月个账划入
			/*Double additionFunds = Double.valueOf(jishu) * 0.05;
			a.setAdditionFunds(additionFunds);*/
			a.setAddition(addition);
			Double additionFunds = Double.valueOf(jishu) * addition / 100.00;
			a.setAdditionFunds(additionFunds);

			if (age <= 45){
				a.setAnnualProportion(annualProportion);
				Double annualAccountDelimit = Double.valueOf(jishu) * annualProportion / 100.00;
				a.setAnnualAccountDelimit(annualAccountDelimit);
			}else if (age > 45){
				a.setAnnualProportionOver(annualProportionOver);
				Double annualAccountDelimit = Double.valueOf(jishu) * annualProportionOver / 100.00;
				a.setAnnualAccountDelimit(annualAccountDelimit);
			}

			/*if (Double.valueOf(jbgradesalary) > 2530.00){
				a.setAnnualProportion(3.00);
				Double annualAccountDelimit = averageSalary * 0.03;
				a.setAnnualAccountDelimit(annualAccountDelimit);
			}else {
				if ("1".equals(sex)){
					if (age < 45){
						//年度补助比例
						a.setAnnualProportion(2.20);
						//年度个账划入
						Double annualAccountDelimit = averageSalary * 0.022;
						a.setAnnualAccountDelimit(annualAccountDelimit);

					}else if (age >= 45 && age < 60){
						//年度补助比例
						a.setAnnualProportion(2.50);
						//年度个账划入
						Double annualAccountDelimit = averageSalary * 0.025;
						a.setAnnualAccountDelimit(annualAccountDelimit);

					}else if (age >= 60){
						//年度补助比例
						a.setAnnualProportion(2.80);
						//年度个账划入
						Double annualAccountDelimit = averageSalary * 0.028;
						a.setAnnualAccountDelimit(annualAccountDelimit);

					}
				}else if ("2".equals(sex)){
					if (age < 45){
						//年度补助比例
						a.setAnnualProportion(2.20);
						//年度个账划入
						Double annualAccountDelimit = averageSalary * 0.022;
						a.setAnnualAccountDelimit(annualAccountDelimit);

					}else if (age >= 45 && age < 55){
						//年度补助比例
						a.setAnnualProportion(2.50);
						//年度个账划入
						Double annualAccountDelimit = averageSalary * 0.025;
						a.setAnnualAccountDelimit(annualAccountDelimit);

					}else if (age >= 55){
						//年度补助比例
						a.setAnnualProportion(2.80);
						//年度个账划入
						Double annualAccountDelimit = averageSalary * 0.028;
						a.setAnnualAccountDelimit(annualAccountDelimit);

					}
				}
			}*/
			affairMedicalInsuranceService.saveGenerate(a);

		}

		request.setAttribute("saveResult","success");
//		return "redirect:"+Global.getAdminPath()+"/affair/affairMedicalInsurance/?repage";
		return "modules/affair/affairMedicalInsuranceForm";
	}
}