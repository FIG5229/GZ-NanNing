/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamily;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceFamilyService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ????????????Controller
 * @author daniel.liu
 * @version 2020-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPoliceFamily")
public class PersonnelPoliceFamilyController extends BaseController {

	@Autowired
	private PersonnelPoliceFamilyService personnelPoliceFamilyService;
	@Autowired
	private PersonnelBaseService personnelBaseService;

	@Autowired
	private DictDao dictDao;
	
	@ModelAttribute
	public PersonnelPoliceFamily get(@RequestParam(required=false) String id) {
		PersonnelPoliceFamily entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPoliceFamilyService.get(id);
		}
		if (entity == null){
			entity = new PersonnelPoliceFamily();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPoliceFamily:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPoliceFamily personnelPoliceFamily, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonnelPoliceFamily> page = personnelPoliceFamilyService.findPage(new Page<PersonnelPoliceFamily>(request, response), personnelPoliceFamily); 
		model.addAttribute("page", page);
		return "modules/personnel/personnelPoliceFamilyList";
	}

	@RequiresPermissions("personnel:personnelPoliceFamily:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPoliceFamily personnelPoliceFamily, Model model) {
		String isAdd = personnelPoliceFamily.getAdd();
		model.addAttribute("add",isAdd);
		//???????????????????????? ??????????????????
		if (StringUtils.isEmpty(personnelPoliceFamily.getStep())){
			personnelPoliceFamily.setStep("0");		//??????????????????
		}else {

		}
		//????????????
//		if (isAdd != null && isAdd.equals("add") && personnelPoliceFamily.getStep() != null){
//			personnelPoliceFamily.setStep(String.valueOf(Integer.valueOf(personnelPoliceFamily.getStep())+1));
//		}
		String loginName = UserUtils.getUser().getLoginName();
		PersonnelBase userMessage = personnelBaseService.findUserMessage(loginName);
		/*String sex = userMessage.getSex();
		if("1".equals(sex)){
			sex = "???";
		}else if("2".equals(sex)){
			sex = "???";
		}
		model.addAttribute("sex",sex);*/

		/*Dict dict = new Dict();
		dict.setType("sex");
		dict.setValue(userMessage.getSex());
		List<String> typeList = dictDao.findTypeList(dict);
		for (String s : typeList) {
			System.out.println(s);
		}*/

		String sex = DictUtils.getDictLabel(userMessage.getSex(), "sex", "");
		String politicsFace = DictUtils.getDictLabel(userMessage.getPoliticsFace(), "political_status", "");
		String nation = DictUtils.getDictLabel(userMessage.getNation(), "nation", "");
		model.addAttribute("sex",sex);
		model.addAttribute("politicsFace",politicsFace);
		model.addAttribute("nation",nation);
		model.addAttribute("userMessage",userMessage);
		model.addAttribute("personnelPoliceFamily", personnelPoliceFamily);
		return "modules/personnel/personnelPoliceFamilyForm";
	}
	@RequiresPermissions("personnel:personnelPoliceFamily:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelPoliceFamily personnelPoliceFamily, Model model) {
		model.addAttribute("personnelPoliceFamily", personnelPoliceFamily);
		return "modules/personnel/personnelPoliceFamilyFormDetail";
	}

	@RequiresPermissions("personnel:personnelPoliceFamily:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPoliceFamily personnelPoliceFamily, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPoliceFamily)){
			return form(personnelPoliceFamily, model);
		}
		personnelPoliceFamilyService.save(personnelPoliceFamily);
		addMessage(redirectAttributes, "????????????????????????");
		String step = personnelPoliceFamily.getStep() == null ? "0" : personnelPoliceFamily.getStep();
		switch (step) {
			case "0":
				//?????????????????????  ??????????????????
				personnelPoliceFamily.setStep("1");
				break;
			case "1":
				//?????????????????????  ??????????????????
				if (personnelPoliceFamily.isHasMarried()) {
					personnelPoliceFamily.setStep("2");
				} else if (personnelPoliceFamily.isHasBrother()) {
					personnelPoliceFamily.setStep("5");
				} else if (personnelPoliceFamily.isHasChild()) {
					personnelPoliceFamily.setStep("6");
				} else {
					//??????
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyForm";
				}
				break;
			case "2":
				//?????????????????????  ????????????????????????
				personnelPoliceFamily.setStep("3");
				break;
			case "3":
				//???????????????????????????  ????????????????????????
				personnelPoliceFamily.setStep("4");
				break;
			case "4":
				//??????????????????????????? ??????????????? ???????????????  ?????????
				if (personnelPoliceFamily.isHasBrother()) {
					personnelPoliceFamily.setStep("5");
				} else if (personnelPoliceFamily.isHasChild()) {
					personnelPoliceFamily.setStep("6");
				} else {
					//??????
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyForm";
				}
				break;
			case "5":
				//????????????????????????
				if (personnelPoliceFamily.isHasBrother()) {
					personnelPoliceFamily.setStep("5");
				} else if (personnelPoliceFamily.isHasChild()) {
					//?????????????????????  ???????????????  ?????????
					personnelPoliceFamily.setStep("6");
				} else {
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyForm";
					//??????
				}
				break;
			case "6":
				//??????????????????	???????????????????????????
				if (personnelPoliceFamily.isHasChildInLow()) {
					personnelPoliceFamily.setStep("7");
				} else {
					//??????
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyForm";
				}
				break;
			case "7":
				//?????????????????????????????? ??????????????????
				if (personnelPoliceFamily.isHasChild()) {
					personnelPoliceFamily.setStep("6");
				} else {
					//??????
					request.setAttribute("saveResult", "success");
					return "modules/personnel/personnelPoliceFamilyForm";
				}
				break;
		}

		//?????????????????????
		if ("true".equals(request.getParameter("isComplete"))){
			request.setAttribute("saveResult","success");
			return "modules/personnel/personnelPoliceFamilyForm";
		}
		//???????????????

		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceFamily/form?repage&add="+personnelPoliceFamily.getAdd()
				+"&step="+personnelPoliceFamily.getStep()+"&idNumber="+personnelPoliceFamily.getIdNumber();

	}
	
	@RequiresPermissions("personnel:personnelPoliceFamily:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPoliceFamily personnelPoliceFamily, RedirectAttributes redirectAttributes) {
		personnelPoliceFamilyService.delete(personnelPoliceFamily);
		addMessage(redirectAttributes, "????????????????????????");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceFamily/?repage";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelPoliceFamily:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPoliceFamilyService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("????????????");
		}else{
			result.setSuccess(false);
			result.setMessage("??????????????????????????????");
		}
		return result;
	}

	/**
	 * ??????excel????????????
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(PersonnelPoliceFamily personnelFamily, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelPoliceFamily> page = null;
			if(flag == true){
				page = personnelPoliceFamilyService.findPage(new Page<PersonnelPoliceFamily>(request, response), personnelFamily);
			}else{
				page = personnelPoliceFamilyService.findPage(new Page<PersonnelPoliceFamily>(request, response,-1), personnelFamily);
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
			//??????
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelPoliceFamily.class);
			exportExcelNew.setWb(wb);
			List<PersonnelPoliceFamily> list =page.getList();
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
			addMessage(redirectAttributes, "????????????????????????????????????"+ex);
		}
		//??????
		return "redirect:" + adminPath + "/personnel/personnelPoliceFamily/?repage";
	}
	//?????????controller
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelPoliceFamily> list = ei.getDataList(PersonnelPoliceFamily.class);
			for (PersonnelPoliceFamily personnelFamily : list){
				try{
					BeanValidators.validateWithException(validator, personnelFamily);
					personnelPoliceFamilyService.save(personnelFamily);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelFamily.getName()+"(???????????????:"+personnelFamily.getIdNumber()+")"+" ???????????????"+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "????????? "+failureNum+" ???????????????????????????");
			}
			addMessage(redirectAttributes, "??????????????? "+successNum+" ???"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "??????????????????????????????"+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}

	/**
	 * ????????????
	 * @param request
	 * @return

	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//????????????????????????ids
		String idsStr = request.getParameter("ids");
		//??????????????????
		String[] idsArray = idsStr.split(",");
		//???????????????
		List<String> userList = new ArrayList<String>();
		String chuCheckId = request.getParameter("chuCheckId");
		String chuCheckMan = dictDao.findLabelByValue(chuCheckId);
		Collections.addAll(userList,idsArray);
		/*String companyId = UserUtils.getUser().getId();
		List <PersonnelPoliceFamily> list = personnelPoliceFamilyService.findByIds(userList);
		for (PersonnelPoliceFamily personnelPoliceFamily: list){
			AffairActivityMien.setCheckType("2");
			AffairActivityMien.setChuCheckMan(chuCheckMan);
			AffairActivityMien.setChuCheckId(chuCheckId);
			AffairActivityMien.setSubmitId(user.getId());
			AffairActivityMien.setSubmitMan(user.getName());
			personnelPoliceFamilyService.save(AffairActivityMien);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/personnelPoliceFamily/list";
	}
*/

}