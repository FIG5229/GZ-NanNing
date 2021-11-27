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
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairXcRewardDeclaration;
import com.thinkgem.jeesite.modules.affair.entity.AffairXcUnitReward;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelFamily;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamily;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyTwo;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelFamilyService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceFamilyTwoService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
import java.util.*;

/**
 * 民警家庭Controller
 * @author tomfu
 * @version 2020-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPoliceFamilyTwo")
public class PersonnelPoliceFamilyTwoController extends BaseController {

	@Autowired
	private PersonnelPoliceFamilyTwoService personnelPoliceFamilyTwoService;
	@Autowired
	private PersonnelBaseService personnelBaseService;
	@Autowired
	private PersonnelFamilyService personnelFamilyService;

	@Autowired
	private DictDao dictDao;
	static String unitCheckMan;

	static String JuChuCheckMan;

	static String JuChuCheckId;


	
	@ModelAttribute
	public PersonnelPoliceFamilyTwo get(@RequestParam(required=false) String id) {
		PersonnelPoliceFamilyTwo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPoliceFamilyTwoService.get(id);
		}
		if (entity == null){
			entity = new PersonnelPoliceFamilyTwo();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPoliceFamily:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonnelPoliceFamilyTwo> page = personnelPoliceFamilyTwoService.findPage(new Page<PersonnelPoliceFamilyTwo>(request, response), personnelPoliceFamilyTwo);
		model.addAttribute("page", page);
		String id = UserUtils.getUser().getId();

		String userIsNull = personnelPoliceFamilyTwoService.findUserIsNull(id);
		String name = "";
		String userUnitId = "";
		if(!"".equals(userIsNull) && null != userIsNull){
			String officeId = UserUtils.getUser().getOffice().getId();
			List<Map<String, String>> userUnit = personnelBaseService.findUserUnit(officeId);

			String code = "";
			for (int i = 0; i < userUnit.size(); i++) {
				name = userUnit.get(i).get("name");
				code = userUnit.get(i).get("code");
			}
			userUnitId = personnelBaseService.findUserUnitId(code);
		}else {
			String unitId = UserUtils.getUser().getOffice().getId();
			String companyId = UserUtils.getUser().getCompany().getId();
			if (("34".equals(unitId) || "34".equals(companyId))) {//&&!unitId.equals("33")
				userUnitId = "c5869e138911485cb80b172567e64789";
				name = "南宁处政治处组织干部信息管理";
			}
			if (("156".equals(unitId) || "156".equals(companyId))) {//&&!unitId.equals("268")
				userUnitId = "a417f6a0d4b948398413d82448b77b86";
				name = "北海处政治处组织干部信息管理";
			}
			if (("95".equals(unitId) || "95".equals(companyId))) {//&&!unitId.equals("148")
				userUnitId = "0921d686251848d5911e8a753cd50090";
				name = "柳州处政治处组织干部信息管理";
			}
			if (("1".equals(unitId) || "1".equals(companyId) && !"95".equals(unitId) && !"156".equals(unitId) && !"34".equals(unitId))) {//||unitId.equals("148")||unitId.equals("268")||unitId.equals("33"))
				userUnitId = "bfdf74f010c9466dba12c1589ecab7f3";
				name = "南宁局政治部组织干部处";
			}
		}
		unitCheckMan=name;
		JuChuCheckMan=name;
		JuChuCheckId=userUnitId;
		if(null != name){
			model.addAttribute("name",name);
		}
		if(null != userUnitId){
			model.addAttribute("userUnitId",userUnitId);
		}
		return "modules/personnel/personnelPoliceFamilyTwoList";
	}

	@RequiresPermissions("personnel:personnelPoliceFamily:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo, Model model) {
		String isAdd = personnelPoliceFamilyTwo.getAdd();
		model.addAttribute("add",isAdd);
		//第一次添加为空时 添加父亲信息
		if (StringUtils.isEmpty(personnelPoliceFamilyTwo.getStep())){
			personnelPoliceFamilyTwo.setStep("0");		//填写父亲信息
		}else {

		}
		//继续添加
//		if (isAdd != null && isAdd.equals("add") && personnelPoliceFamily.getStep() != null){
//			personnelPoliceFamily.setStep(String.valueOf(Integer.valueOf(personnelPoliceFamily.getStep())+1));
//		}
		String loginName = UserUtils.getUser().getLoginName();
		PersonnelBase userMessage = personnelBaseService.findUserMessage(loginName);
		if(null != userMessage) {
			if (!"".equals(userMessage.getSex())) {
				String sex = DictUtils.getDictLabel(userMessage.getSex(), "sex", "");
				model.addAttribute("sex", sex);
			}
			if (!"".equals(userMessage.getPoliticsFace())) {
				String politicsFace = DictUtils.getDictLabel(userMessage.getPoliticsFace(), "political_status", "");
				model.addAttribute("politicsFace", politicsFace);
			}
			if (!"".equals(userMessage.getNation())) {
				String nation = DictUtils.getDictLabel(userMessage.getNation(), "nation", "");
				model.addAttribute("nation", nation);
			}
		}
		model.addAttribute("userMessage",userMessage);
		model.addAttribute("personnelPoliceFamily", personnelPoliceFamilyTwo);
		return "modules/personnel/personnelPoliceFamilyTwoForm";
	}
	@RequiresPermissions("personnel:personnelPoliceFamily:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo, Model model) {
		model.addAttribute("personnelPoliceFamilyTwo", personnelPoliceFamilyTwo);
		return "modules/personnel/personnelPoliceFamilyTwoFormDetail";
	}

	@RequiresPermissions("personnel:personnelPoliceFamily:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPoliceFamilyTwo)){
			return form(personnelPoliceFamilyTwo, model);
		}
		personnelPoliceFamilyTwoService.save(personnelPoliceFamilyTwo);
		addMessage(redirectAttributes, "保存民警家庭成功");
		String step = personnelPoliceFamilyTwo.getStep() == null ? "0" : personnelPoliceFamilyTwo.getStep();
		switch (step) {
			case "0":
				//填写完父亲信息  填写母亲信息
				personnelPoliceFamilyTwo.setStep("1");
				break;
			case "1":
				//父母信息填写完  填写配偶信息
				if (personnelPoliceFamilyTwo.isHasMarried()) {
					personnelPoliceFamilyTwo.setStep("2");
				} else if (personnelPoliceFamilyTwo.isHasBrother()) {
					personnelPoliceFamilyTwo.setStep("5");
				} else if (personnelPoliceFamilyTwo.isHasChild()) {
					personnelPoliceFamilyTwo.setStep("6");
				} else {
					//完成
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyTwoForm";
				}
				break;
			case "2":
				//配偶信息填写完  填写配偶父亲信息
				personnelPoliceFamilyTwo.setStep("3");
				break;
			case "3":
				//配偶父亲信息填写完  填写配偶母亲信息
				personnelPoliceFamilyTwo.setStep("4");
				break;
			case "4":
				//配偶母亲信息填写完 是否有兄弟 是否有孩子  有则写
				if (personnelPoliceFamilyTwo.isHasBrother()) {
					personnelPoliceFamilyTwo.setStep("5");
				} else if (personnelPoliceFamilyTwo.isHasChild()) {
					personnelPoliceFamilyTwo.setStep("6");
				} else {
					//完成
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyTwoForm";
				}
				break;
			case "5":
				//是否还有兄弟姐妹
				if (personnelPoliceFamilyTwo.isHasBrother()) {
					personnelPoliceFamilyTwo.setStep("5");
				} else if (personnelPoliceFamilyTwo.isHasChild()) {
					//兄弟姐妹填写完  是否有子女  有则写
					personnelPoliceFamilyTwo.setStep("6");
				} else {
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyTwoForm";
					//完成
				}
				break;
			case "6":
				//子女是否已婚	已婚则填写配偶信息
				if (personnelPoliceFamilyTwo.isHasChildInLow()) {
					personnelPoliceFamilyTwo.setStep("7");
				} else {
					//结束
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyTwoForm";
				}
				break;
			case "7":
				//子女配偶信息填写完成 是否还有子女
				if (personnelPoliceFamilyTwo.isHasChild()) {
					personnelPoliceFamilyTwo.setStep("6");
				} else {
					//结束
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyTwoForm";
				}
				break;
		}

		//添加完不在添加
		if ("true".equals(request.getParameter("isComplete"))){
			request.setAttribute("saveResult","success");
			return "modules/personnel/personnelPoliceFamilyTwoForm";
		}
		//继续添加时

		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceFamilyTwo/form?repage&add="+personnelPoliceFamilyTwo.getAdd()
				+"&step="+personnelPoliceFamilyTwo.getStep()+"&idNumber="+personnelPoliceFamilyTwo.getIdNumber();

	}
	
	@RequiresPermissions("personnel:personnelPoliceFamily:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo, RedirectAttributes redirectAttributes) {
		personnelPoliceFamilyTwoService.delete(personnelPoliceFamilyTwo);
		addMessage(redirectAttributes, "删除民警家庭成功");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceFamilyTwo/?repage";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelPoliceFamily:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPoliceFamilyTwoService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelPoliceFamilyTwo> page = null;
			if(flag == true){
				page = personnelPoliceFamilyTwoService.findPage(new Page<PersonnelPoliceFamilyTwo>(request, response), personnelPoliceFamilyTwo);
			}else{
				page = personnelPoliceFamilyTwoService.findPage(new Page<PersonnelPoliceFamilyTwo>(request, response,-1), personnelPoliceFamilyTwo);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelPoliceFamilyTwo.class);
			exportExcelNew.setWb(wb);
			List<PersonnelPoliceFamilyTwo> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelPoliceFamilyTwo/?repage";
	}
	//导入的controller
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelPoliceFamilyTwo> list = ei.getDataList(PersonnelPoliceFamilyTwo.class);
			for (PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo : list){
				try{
					BeanValidators.validateWithException(validator, personnelPoliceFamilyTwo);
					personnelPoliceFamilyTwoService.save(personnelPoliceFamilyTwo);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelPoliceFamilyTwo.getName()+"(身份证号码:"+personnelPoliceFamilyTwo.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	 * 批量提交
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {

		//String unitID = personnelPoliceFamilyTwoService.findUnitID();
		String id = UserUtils.getUser().getId();
		String userIsNull = personnelPoliceFamilyTwoService.findUserIsNull(id);
		//if(UserUtils.getUser().getId() == unitID || unitID == null || unitID == "") {
		if(!"".equals(userIsNull) && null != userIsNull){
			User user = UserUtils.getUser();
			//接受前台传过来的ids
			String idsStr = request.getParameter("ids");
			//字符串转数组
			String[] idsArray = idsStr.split(",");
			//数组转集合
			List<String> userList = new ArrayList<String>();
			String unitCheckId = request.getParameter("unitCheckId");
			Collections.addAll(userList, idsArray);
			/*String companyId = UserUtils.getUser().getId();*/
			List<PersonnelPoliceFamilyTwo> list = personnelPoliceFamilyTwoService.findByIds(userList);
			for (PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo : list) {
				personnelPoliceFamilyTwo.setCheckType("2");
				personnelPoliceFamilyTwo.setUnitCheckMan(unitCheckMan);
				personnelPoliceFamilyTwo.setUnitCheckId(unitCheckId);
				personnelPoliceFamilyTwo.setSubmitId(user.getId());
				personnelPoliceFamilyTwo.setSubmitMan(user.getName());
				personnelPoliceFamilyTwoService.save(personnelPoliceFamilyTwo);
			}
		}else {
			User user = UserUtils.getUser();
			//接受前台传过来的ids
			String idsStr = request.getParameter("ids");
			//字符串转数组
			String[] idsArray = idsStr.split(",");
			//数组转集合
			List<String> userList = new ArrayList<String>();
			Collections.addAll(userList, idsArray);
			/*String companyId = UserUtils.getUser().getId();*/
			List<PersonnelPoliceFamilyTwo> list = personnelPoliceFamilyTwoService.findByIds(userList);
			for (PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo : list) {
				personnelPoliceFamilyTwo.setCheckType("6");
				personnelPoliceFamilyTwo.setJuChuCheckMan(JuChuCheckMan);
				personnelPoliceFamilyTwo.setJuChuCheckId(JuChuCheckId);
				personnelPoliceFamilyTwoService.save(personnelPoliceFamilyTwo);
			}
		}
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceFamilyTwo/list";
	}

	/*
	 * 审核页面
	 * */
	@RequiresPermissions("personnel:personnelPoliceFamily:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog(Model model) {
		// 34 南宁处  95  柳州处  156 北海处  1 南宁局
		String unitId = UserUtils.getUser().getOffice().getId();
		String companyId = UserUtils.getUser().getCompany().getId();
		String id = "";
		String label = "";
		if(("34".equals(unitId)||"34".equals(companyId))){//&&!unitId.equals("33")
			id = "c5869e138911485cb80b172567e64789";
			label = "南宁处政治处组织干部信息管理";
		}
		if(("156".equals(unitId)||"156".equals(companyId))){//&&!unitId.equals("268")
			id = "a417f6a0d4b948398413d82448b77b86";
			label = "北海处政治处组织干部信息管理";
		}
		if(("95".equals(unitId)||"95".equals(companyId))){//&&!unitId.equals("148")
			id = "0921d686251848d5911e8a753cd50090";
			label = "柳州处政治处组织干部信息管理";
		}
		if(("1".equals(unitId)||"1".equals(companyId)&& !"95".equals(unitId) && !"156".equals(unitId) && !"34".equals(unitId))){//||unitId.equals("148")||unitId.equals("268")||unitId.equals("33"))
			id = "bfdf74f010c9466dba12c1589ecab7f3";
			label = "南宁局政治部组织干部处";
		}
		model.addAttribute("lable",label);
		model.addAttribute("idValue",id);
		return "modules/personnel/personnelPoliceFamilyTwoCheckDialog";
	}

	/*
	 * 审核
	 * @param personnelPoliceFamilyTwo
	 * @param request
	 * @return
	 * */
	@ResponseBody
	//@RequiresPermissions("affair:personnelPoliceFamily:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo, HttpServletRequest request,Model model) {

		personnelPoliceFamilyTwoService.save(personnelPoliceFamilyTwo);

		String checkType = personnelPoliceFamilyTwoService.findCheckType(personnelPoliceFamilyTwo.getId());
		//局处审核完毕之后归档至人员信息

		if("4".equals(checkType) && null != checkType && "" != checkType){
			PersonnelFamily personnelFamily = new PersonnelFamily();
			personnelFamily.setIdNumber(personnelPoliceFamilyTwo.getIdNumber());
			personnelFamily.setName(personnelPoliceFamilyTwo.getName());
			personnelFamily.setRelationship(personnelPoliceFamilyTwo.getRelationship());
			personnelFamily.setSex(personnelPoliceFamilyTwo.getSex());
			personnelFamily.setBirthday(personnelPoliceFamilyTwo.getBirthday());
			personnelFamily.setPoliticsFace(personnelPoliceFamilyTwo.getPoliticsFace());
			personnelFamily.setStatus(personnelPoliceFamilyTwo.getStatus());
			personnelFamily.setUnitNameJob(personnelPoliceFamilyTwo.getUnitNameJob());
			personnelFamily.setUnitArea(personnelPoliceFamilyTwo.getUnitArea());
			personnelFamily.setNationality(personnelPoliceFamilyTwo.getNationality());
			personnelFamily.setNation(personnelPoliceFamilyTwo.getNation());
			personnelFamily.setEducation(personnelPoliceFamilyTwo.getEducation());
			personnelFamily.setIdentity(personnelPoliceFamilyTwo.getIdentity());
			personnelFamily.setIdentityJob(personnelPoliceFamilyTwo.getIdentityJob());
			personnelFamily.setJobLevel(personnelPoliceFamilyTwo.getJobLevel());
			personnelFamily.setContactMethod(personnelPoliceFamilyTwo.getContactMethod());
			personnelFamily.setAddress(personnelPoliceFamilyTwo.getAddress());
			personnelFamily.setRemark(personnelPoliceFamilyTwo.getRemark());
			personnelFamilyService.save(personnelFamily);
		}

		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

}