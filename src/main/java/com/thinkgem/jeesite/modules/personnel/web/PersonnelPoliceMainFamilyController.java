/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTemporaryPolice;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceFamilyInfoDao;
import com.thinkgem.jeesite.modules.personnel.entity.*;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelFamilyService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceMainFamilyService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 民警家庭Controller
 * @author cecil.li
 * @version 2020-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPoliceMainFamily")
public class PersonnelPoliceMainFamilyController extends BaseController {

	@Autowired
	private PersonnelPoliceMainFamilyService personnelPoliceMainFamilyService;

	@Autowired
	private PersonnelBaseService personnelBaseService;

	@Autowired
	private PersonnelPoliceFamilyInfoDao personnelPoliceFamilyInfoDao;

	@Autowired
	private PersonnelFamilyService personnelFamilyService;

	static String unitCheckMan;

	static String JuChuCheckMan;

	static String JuChuCheckId;


	@ModelAttribute
	public PersonnelPoliceMainFamily get(@RequestParam(required=false) String id) {
		PersonnelPoliceMainFamily entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPoliceMainFamilyService.get(id);
		}
		if (entity == null){
			entity = new PersonnelPoliceMainFamily();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPoliceMainFamily:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPoliceMainFamily personnelPoliceMainFamily, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonnelPoliceMainFamily> page = personnelPoliceMainFamilyService.findPage(new Page<PersonnelPoliceMainFamily>(request, response), personnelPoliceMainFamily); 
		model.addAttribute("page", page);
		String id = UserUtils.getUser().getNo();

		String userIsNull = personnelPoliceMainFamilyService.findUserIsNull(id);
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
		return "modules/personnel/personnelPoliceMainFamilyList";
	}

	@RequiresPermissions("personnel:personnelPoliceMainFamily:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPoliceMainFamily personnelPoliceMainFamily, Model model,HttpServletRequest request){
//		String isAdd =request.getParameter("isAdd");
		String isAdd = personnelPoliceMainFamily.getIsAdd();
		model.addAttribute("add",isAdd);
		if (StringUtils.isEmpty(personnelPoliceMainFamily.getStatus())){
			personnelPoliceMainFamily.setStatus("0");
		}
		String loginName = UserUtils.getUser().getLoginName();
		PersonnelBase userMessage = personnelBaseService.findUserMessage(loginName);
		if(null != userMessage) {
			personnelPoliceMainFamily.setName(userMessage.getName());
			personnelPoliceMainFamily.setUnit(userMessage.getWorkunitName());
			personnelPoliceMainFamily.setUnitId(userMessage.getWorkunitId());
			personnelPoliceMainFamily.setIdNumber(userMessage.getIdNumber());
			personnelPoliceMainFamily.setPoliceNum(userMessage.getPoliceIdNumber());
		}
		PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo = new PersonnelPoliceFamilyInfo();
		personnelPoliceFamilyInfo.setStatus(personnelPoliceMainFamily.getStatus());
		personnelPoliceFamilyInfo.setPfId(personnelPoliceMainFamily.getId());
		List<PersonnelPoliceFamilyInfo> childList = personnelPoliceFamilyInfoDao.findList(personnelPoliceFamilyInfo);
		personnelPoliceMainFamily.setPersonnelPoliceFamilyInfoList(childList);
		model.addAttribute("mId",personnelPoliceMainFamily.getId());
		model.addAttribute("personnelPoliceMainFamily", personnelPoliceMainFamily);
		return "modules/personnel/personnelPoliceMainFamilyForm";
	}

	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelPoliceMainFamily personnelPoliceMainFamily, Model model) {
		PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo = new PersonnelPoliceFamilyInfo();
		personnelPoliceFamilyInfo.setStatus(personnelPoliceMainFamily.getStatus());
		personnelPoliceFamilyInfo.setPfId(personnelPoliceMainFamily.getId());
		List<PersonnelPoliceFamilyInfo> childList = personnelPoliceFamilyInfoDao.findList(personnelPoliceFamilyInfo);
		personnelPoliceMainFamily.setPersonnelPoliceFamilyInfoList(childList);
		model.addAttribute("personnelPoliceMainFamily", personnelPoliceMainFamily);
		return "modules/personnel/personnelPoliceMainFamilyFormDetail";
	}

	@RequiresPermissions("personnel:personnelPoliceMainFamily:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPoliceMainFamily personnelPoliceMainFamily, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPoliceMainFamily)){
			return form(personnelPoliceMainFamily, model,request);
		}
		String status = personnelPoliceMainFamily.getStatus() == null ? "0" : personnelPoliceMainFamily.getStatus();
		switch(status){
			case "0":
				//婚姻状况填报
				personnelPoliceMainFamily.setStatus("1");
				break;
			case "1":
				if ("2".equals(personnelPoliceMainFamily.getHasMarried()) || "3".equals(personnelPoliceMainFamily.getHasMarried()) || "4".equals(personnelPoliceMainFamily.getHasMarried()) || "5".equals(personnelPoliceMainFamily.getHasMarried()) || "7".equals(personnelPoliceMainFamily.getHasMarried())){
					personnelPoliceMainFamily.setStatus("2");
				}else {
					personnelPoliceMainFamily.setStatus("4");
				}
				break;
			case "2":
				personnelPoliceMainFamily.setStatus("3");
				break;
			case "3":
				personnelPoliceMainFamily.setStatus("4");
				break;
			case "4":
				personnelPoliceMainFamily.setStatus("5");
				break;
			case "5":
				if ("1".equals(personnelPoliceMainFamily.getHasBrother())){
					personnelPoliceMainFamily.setStatus("6");
				}else {
					personnelPoliceMainFamily.setStatus("7");
				}
				break;
			case "6":
				personnelPoliceMainFamily.setStatus("7");
				break;
			case "7":
				if ( "1".equals(personnelPoliceMainFamily.getHasChild())){
					personnelPoliceMainFamily.setStatus("8");
				}else {
					personnelPoliceMainFamily.setStatus("66");
				}
				break;
			case "8":
				personnelPoliceMainFamily.setStatus("9");
				break;
			case "9":
				if ("1".equals(personnelPoliceMainFamily.getHasChildInLow())){
					personnelPoliceMainFamily.setStatus("10");
				}else {
					personnelPoliceMainFamily.setStatus("66");
				}
				break;
			case "10":
				personnelPoliceMainFamily.setStatus("66");
				break;


		}
		personnelPoliceMainFamily.setCheckType("1");
		String mId = request.getParameter("mId");
		if (StringUtils.isNotEmpty(mId)){
			personnelPoliceMainFamily.setId(request.getParameter("mId"));
		}else {
			personnelPoliceMainFamily.setId("");
		}
		personnelPoliceMainFamilyService.save(personnelPoliceMainFamily);
		addMessage(redirectAttributes, "保存民警家庭成功");
		if ("true".equals(request.getParameter("over")) || personnelPoliceMainFamily.getStatus().equals("66")){
			request.setAttribute("saveResult","success");
			return "modules/personnel/personnelPoliceMainFamilyForm";
		}
		/*request.setAttribute("saveResult","success");
		return "modules/personnel/personnelPoliceMainFamilyForm";*/
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceMainFamily/form?repage&isAdd="+personnelPoliceMainFamily.getIsAdd()
				+"&status="+personnelPoliceMainFamily.getStatus()+"&idNumber="+personnelPoliceMainFamily.getIdNumber()+"&id="+personnelPoliceMainFamily.getId();
	}
	
	@RequiresPermissions("personnel:personnelPoliceMainFamily:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPoliceMainFamily personnelPoliceMainFamily, RedirectAttributes redirectAttributes) {
		personnelPoliceMainFamilyService.delete(personnelPoliceMainFamily);
		personnelPoliceFamilyInfoDao.deleteByMainId(personnelPoliceMainFamily.getId());
		addMessage(redirectAttributes, "删除民警家庭成功");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceMainFamily/?repage";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelPoliceMainFamily:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPoliceMainFamilyService.deleteByIds(ids);
			personnelPoliceFamilyInfoDao.deleteByMainIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequestMapping(value = "updateInfo")
	public String updateInfo(PersonnelPoliceMainFamily personnelPoliceMainFamily, Model model){
		PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo = new PersonnelPoliceFamilyInfo();
		personnelPoliceFamilyInfo.setStatus(personnelPoliceMainFamily.getStatus());
		personnelPoliceFamilyInfo.setPfId(personnelPoliceMainFamily.getId());
		List<PersonnelPoliceFamilyInfo> childList = personnelPoliceFamilyInfoDao.findList(personnelPoliceFamilyInfo);
		personnelPoliceMainFamily.setPersonnelPoliceFamilyInfoList(childList);
		model.addAttribute("hasMarried",personnelPoliceMainFamily.getHasMarried());
		model.addAttribute("hasBrother",personnelPoliceMainFamily.getHasBrother());
		model.addAttribute("hasChild",personnelPoliceMainFamily.getHasChild());
		model.addAttribute("hasChildInLow",personnelPoliceMainFamily.getHasChildInLow());
		model.addAttribute("personnelPoliceMainFamily", personnelPoliceMainFamily);
		return "modules/personnel/personnelPoliceMainFamilyUpdateForm";
	}

	@RequestMapping(value = "saveInfo")
	public String saveInfo(PersonnelPoliceMainFamily personnelPoliceMainFamily, Model model, RedirectAttributes redirectAttributes , HttpServletRequest request) {
		if (!beanValidator(model, personnelPoliceMainFamily)){
			return updateInfo(personnelPoliceMainFamily, model);
		}
		personnelPoliceMainFamilyService.saveInfo(personnelPoliceMainFamily);
		addMessage(redirectAttributes, "保存民警家庭成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelPoliceMainFamilyUpdateForm";
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
		String userIsNull = personnelPoliceMainFamilyService.findUserIsNull(id);
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
			List<PersonnelPoliceMainFamily> list = personnelPoliceMainFamilyService.findByIds(userList);
			for (PersonnelPoliceMainFamily personnelPoliceMainFamily : list) {
				personnelPoliceMainFamily.setCheckType("2");
				personnelPoliceMainFamily.setUnitCheckMan(unitCheckMan);
				personnelPoliceMainFamily.setUnitCheckId(unitCheckId);
				personnelPoliceMainFamily.setSubmitId(user.getId());
				personnelPoliceMainFamily.setSubmitMan(user.getName());
				personnelPoliceMainFamilyService.save(personnelPoliceMainFamily);
			}
			try {
				personnelPoliceMainFamilyService.creatPoliceFamilyWarning(user.getName(),user.getNo(),userList,unitCheckId);
			}catch (Exception e){
				logger.error("创建民警家庭预警，发生错误"+e.getCause().toString());
				e.printStackTrace();
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
			List<PersonnelPoliceMainFamily> list = personnelPoliceMainFamilyService.findByIds(userList);
			for (PersonnelPoliceMainFamily personnelPoliceMainFamily : list) {
				personnelPoliceMainFamily.setCheckType("6");
				personnelPoliceMainFamily.setJuChuCheckMan(JuChuCheckMan);
				personnelPoliceMainFamily.setJuChuCheckId(JuChuCheckId);
				personnelPoliceMainFamilyService.save(personnelPoliceMainFamily);
			}
		}
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceMainFamily/list";
	}

	/*
	 * 审核页面
	 * */
	//@RequiresPermissions("personnel:personnelPoliceMainFamily:shenhe")
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
		return "modules/personnel/personnelPoliceMainFamilyCheckDialog";
	}

	/*
	 * 审核
	 * @param
	 * @param request
	 * @return
	 * */
	@ResponseBody
	//@RequiresPermissions("affair:personnelPoliceFamily:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(PersonnelPoliceMainFamily personnelPoliceMainFamily, HttpServletRequest request,Model model) {
		personnelPoliceMainFamilyService.save(personnelPoliceMainFamily);
		String checkType = personnelPoliceMainFamilyService.findCheckType(personnelPoliceMainFamily.getId());
		//局处审核完毕之后归档至人员信息
		List<PersonnelFamily> isPersonel = personnelPoliceMainFamilyService.findIsPersonel(personnelPoliceMainFamily.getIdNumber());
		if ("4".equals(checkType) && null != checkType && "" != checkType) {
			if (isPersonel != null && isPersonel.size() > 0) {
				for (int i = 0; i < isPersonel.size(); i++) {
					PersonnelFamily personnelFamily = isPersonel.get(i);
					personnelFamily.setDelFlag("1");
					personnelFamilyService.updateDelFlag(personnelFamily);
				}
				List<PersonnelPoliceFamilyInfo> sonTable = personnelPoliceMainFamilyService.getSonTable(personnelPoliceMainFamily.getId());
				for (int i = 0; i < sonTable.size(); i++) {
					PersonnelFamily personnelFamily = new PersonnelFamily();
					personnelFamily.setIdNumber(personnelPoliceMainFamily.getIdNumber());
					if (sonTable != null) {
						personnelFamily.setName(sonTable.get(i).getName());
						personnelFamily.setRelationship(sonTable.get(i).getRelationship());
						personnelFamily.setSex(sonTable.get(i).getSex());
						personnelFamily.setBirthday(sonTable.get(i).getBirthday());
						personnelFamily.setPoliticsFace(sonTable.get(i).getPoliticalStatus());
						personnelFamily.setStatus(sonTable.get(i).getStatusQuo());
						personnelFamily.setUnitNameJob(sonTable.get(i).getJob());
						personnelFamily.setUnitArea(sonTable.get(i).getSteer());
						personnelFamily.setNationality(sonTable.get(i).getNationality());
						personnelFamily.setNation(sonTable.get(i).getNation());
						personnelFamily.setEducation(sonTable.get(i).getEducation());
						personnelFamily.setIdentity(sonTable.get(i).getIdentity());
						personnelFamily.setIdentityJob(sonTable.get(i).getPosition());
						personnelFamily.setJobLevel(sonTable.get(i).getJobLevel());
						personnelFamily.setContactMethod(sonTable.get(i).getContact());
						personnelFamily.setAddress(sonTable.get(i).getAddress());
						personnelFamily.setRemark(sonTable.get(i).getRemark());
					}
					personnelFamilyService.save(personnelFamily);
				}
			} else {
				List<PersonnelPoliceFamilyInfo> sonTable = personnelPoliceMainFamilyService.getSonTable(personnelPoliceMainFamily.getId());
				for (int i = 0; i < sonTable.size(); i++) {
					PersonnelFamily personnelFamily = new PersonnelFamily();
					personnelFamily.setIdNumber(personnelPoliceMainFamily.getIdNumber());
					if (sonTable != null) {
						personnelFamily.setName(sonTable.get(i).getName());
						personnelFamily.setRelationship(sonTable.get(i).getRelationship());
						personnelFamily.setSex(sonTable.get(i).getSex());
						personnelFamily.setBirthday(sonTable.get(i).getBirthday());
						personnelFamily.setPoliticsFace(sonTable.get(i).getPoliticalStatus());
						personnelFamily.setStatus(sonTable.get(i).getStatusQuo());
						personnelFamily.setUnitNameJob(sonTable.get(i).getJob());
						personnelFamily.setUnitArea(sonTable.get(i).getSteer());
						personnelFamily.setNationality(sonTable.get(i).getNationality());
						personnelFamily.setNation(sonTable.get(i).getNation());
						personnelFamily.setEducation(sonTable.get(i).getEducation());
						personnelFamily.setIdentity(sonTable.get(i).getIdentity());
						personnelFamily.setIdentityJob(sonTable.get(i).getPosition());
						personnelFamily.setJobLevel(sonTable.get(i).getJobLevel());
						personnelFamily.setContactMethod(sonTable.get(i).getContact());
						personnelFamily.setAddress(sonTable.get(i).getAddress());
						personnelFamily.setRemark(sonTable.get(i).getRemark());
					}
					personnelFamilyService.save(personnelFamily);
				}
			}
		}
			Result result = new Result();
			result.setSuccess(true);
			result.setMessage("审核成功");
			return result;
		}

	/**
	 * 撤销审核
	 * @param personnelPoliceMainFamily
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "revocation")
	public String revocation(PersonnelPoliceMainFamily personnelPoliceMainFamily, RedirectAttributes redirectAttributes, @RequestParam("flag") String flag) {
		String checkType = null;
		if("1".equals(flag)){
			if("2".equals(personnelPoliceMainFamily.getCheckType())){
				checkType = "1";
			}
		}else if("2".equals(flag)){
			if("3".equals(personnelPoliceMainFamily.getCheckType())){
				checkType = "2";
			}else if("6".equals(personnelPoliceMainFamily.getCheckType())){
				checkType = "2";
			}
		}else if("3".equals(flag)){
			if("4".equals(personnelPoliceMainFamily.getCheckType())){
				checkType = "6";
				//局处组干审核完成后，保存至人员信息管理中的民警家庭中，这里撤销审核将保存的信息删除
				List<PersonnelFamily> isPersonel = personnelPoliceMainFamilyService.findIsPersonel(personnelPoliceMainFamily.getIdNumber());
					if (isPersonel != null && isPersonel.size() > 0) {
						for (int i = 0; i < isPersonel.size(); i++) {
							PersonnelFamily personnelFamily = isPersonel.get(i);
							personnelFamily.setDelFlag("1");
							personnelFamilyService.updateDelFlag(personnelFamily);
					}
				}
			}
		}
		personnelPoliceMainFamilyService.revocation(personnelPoliceMainFamily.getId(),checkType);
		addMessage(redirectAttributes, "撤销民警家庭管理成功");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceMainFamily";
	}


}