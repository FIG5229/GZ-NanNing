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
import com.thinkgem.jeesite.modules.affair.entity.PersonnelGoAbroad;
import com.thinkgem.jeesite.modules.affair.entity.PersonnelGoAbroadTwo;
import com.thinkgem.jeesite.modules.affair.service.PersonnelGoAbroadService;
import com.thinkgem.jeesite.modules.affair.service.PersonnelGoAbroadTwoService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 领导干部出国管理表Controller
 * @author mason.xv
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/personnelGoAbroadTwo")
public class PersonnelGoAbroadTwoController extends BaseController {

	@Autowired
	private PersonnelGoAbroadTwoService personnelGoAbroadTwoService;

	@Autowired
	private PersonnelGoAbroadService personnelGoAbroadService;
	@Autowired
	private DictDao dictDao;

	static String JuChuCheckMan;
	static String JuChuCheckId;

	@ModelAttribute
	public PersonnelGoAbroadTwo get(@RequestParam(required=false) String id) {
		PersonnelGoAbroadTwo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelGoAbroadTwoService.get(id);
		}
		if (entity == null){
			entity = new PersonnelGoAbroadTwo();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:personnelGoAbroad:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelGoAbroadTwo personnelGoAbroadTwo, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelGoAbroadTwo.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelGoAbroadTwo> page = personnelGoAbroadTwoService.findPage(new Page<PersonnelGoAbroadTwo>(request, response), personnelGoAbroadTwo);
		model.addAttribute("page", page);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}

		String userUnitId= "";
		String userUnitName= "";
		String unitId = UserUtils.getUser().getOffice().getId();
		String companyId = UserUtils.getUser().getCompany().getId();
		if (("34".equals(unitId) || "34".equals(companyId))) {//&&!unitId.equals("33")
			userUnitId = "c5869e138911485cb80b172567e64789";
			userUnitName = "南宁处政治处组织干部信息管理";
		}
		if (("156".equals(unitId) || "156".equals(companyId))) {//&&!unitId.equals("268")
			userUnitId = "a417f6a0d4b948398413d82448b77b86";
			userUnitName = "北海处政治处组织干部信息管理";
		}
		if (("95".equals(unitId) || "95".equals(companyId))) {//&&!unitId.equals("148")
			userUnitId = "0921d686251848d5911e8a753cd50090";
			userUnitName = "柳州处政治处组织干部信息管理";
		}
		if (("1".equals(unitId) || "1".equals(companyId) && !"95".equals(unitId) && !"156".equals(unitId) && !"34".equals(unitId))) {//||unitId.equals("148")||unitId.equals("268")||unitId.equals("33"))
			userUnitId = "bfdf74f010c9466dba12c1589ecab7f3";
			userUnitName = "南宁局政治部组织干部处";
		}
		JuChuCheckMan=userUnitName;
		JuChuCheckId=userUnitId;
		model.addAttribute("userUnitId",userUnitId);
		model.addAttribute("userUnitName",userUnitName);

		return "modules/affair/personnelGoAbroadTwoList";
	}

	@RequiresPermissions("affair:personnelGoAbroad:view")
	@RequestMapping(value = "form")
	public String form(PersonnelGoAbroadTwo personnelGoAbroadTwo, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelGoAbroadTwo.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelGoAbroadTwo", personnelGoAbroadTwo);
		return "modules/affair/personnelGoAbroadTwoForm";
	}

	@RequiresPermissions("affair:personnelGoAbroad:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelGoAbroadTwo personnelGoAbroadTwo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelGoAbroadTwo)){
			return form(personnelGoAbroadTwo, model,request);
		}
		personnelGoAbroadTwoService.save(personnelGoAbroadTwo);
		addMessage(redirectAttributes, "保存领导干部出国管理表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/personnelGoAbroadTwoForm";
	}
	
	@RequiresPermissions("affair:personnelGoAbroad:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelGoAbroadTwo personnelGoAbroadTwo, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url ="redirect:"+Global.getAdminPath()+"/affair/personnelGoAbroadTwo?repage&idNumber="+personnelGoAbroadTwo.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/affair/personnelGoAbroadTwo/?repage&mType="+request.getParameter("mType").toString();
		}
		personnelGoAbroadTwoService.delete(personnelGoAbroadTwo);
		addMessage(redirectAttributes, "删除领导干部出国管理表成功");
        return  url;
	}

	@ResponseBody
	@RequiresPermissions("affair:personnelGoAbroad:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelGoAbroadTwoService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:personnelGoAbroad:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelGoAbroadTwo personnelGoAbroadTwo, Model model) {
		model.addAttribute("personnelGoAbroadTwo", personnelGoAbroadTwo);
		return "modules/affair/personnelGoAbroadTwoFormDetail";
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
	public String exportExcelByTemplate(PersonnelGoAbroadTwo personnelGoAbroadTwo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<PersonnelGoAbroadTwo> page = null;
			if(flag == true){
				page = personnelGoAbroadTwoService.findPage(new Page<PersonnelGoAbroadTwo>(request, response), personnelGoAbroadTwo);
			}else{
				page = personnelGoAbroadTwoService.findPage(new Page<PersonnelGoAbroadTwo>(request, response,-1), personnelGoAbroadTwo);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelGoAbroadTwo.class);
			exportExcelNew.setWb(wb);
			List<PersonnelGoAbroadTwo> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/personnelGoAbroadTwo?repage";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<PersonnelGoAbroadTwo> list = ei.getDataList(PersonnelGoAbroadTwo.class);
			for (PersonnelGoAbroadTwo personnelGoAbroadTwo : list){
				try{
					BeanValidators.validateWithException(validator, personnelGoAbroadTwo);
					personnelGoAbroadTwoService.save(personnelGoAbroadTwo);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelGoAbroadTwo.getName()+"(身份证号码:"+personnelGoAbroadTwo.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();
		String chuCheckId = request.getParameter("jUhuCheckId");
		//String chuCheckMan = dictDao.findLabelByValue(chuCheckId);
		Collections.addAll(userList,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List <PersonnelGoAbroadTwo> list = personnelGoAbroadTwoService.findByIds(userList);
		for (PersonnelGoAbroadTwo personnelGoAbroadTwo: list){
			personnelGoAbroadTwo.setCheckType("2");
			personnelGoAbroadTwo.setJuChuCheckMan(JuChuCheckMan);
			personnelGoAbroadTwo.setJuChuCheckId(JuChuCheckId);
			personnelGoAbroadTwo.setSubmitId(user.getId());
			personnelGoAbroadTwo.setSubmitMan(user.getName());
			personnelGoAbroadTwoService.save(personnelGoAbroadTwo);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/personnelGoAbroadTwo/list";
	}

	/**
	 * 审核页面
	 * */
	//@RequiresPermissions("affair:personnelGoAbroad:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/personnelGoAbroadTwoCheckDialog";
	}

	/**
	 * 审核
	 * @param personnelGoAbroadTwo
	 * @param request
	 * @return
	 * */
	@ResponseBody
	//@RequiresPermissions("affair:personnelGoAbroad:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(PersonnelGoAbroadTwo personnelGoAbroadTwo, HttpServletRequest request) {
		String qw = "1212";
		/*if (!affairActivityMien.getJuCheckId().isEmpty()) {
			//如果审核方式为转为上一级,则通过juCheckId,为juCheckMan字段赋值
			String juCheckMan = dictDao.findLabelByValue(affairActivityMien.getJuCheckId());
			affairActivityMien.setJuCheckMan(juCheckMan);
		}*/
		personnelGoAbroadTwoService.save(personnelGoAbroadTwo);

		if("4".equals(personnelGoAbroadTwo.getCheckType())){
			PersonnelGoAbroad personnelGoAbroad = new PersonnelGoAbroad();
			personnelGoAbroad.setName(personnelGoAbroadTwo.getName());
			personnelGoAbroad.setIdNumber(personnelGoAbroadTwo.getIdNumber());
			personnelGoAbroad.setUnitJob(personnelGoAbroadTwo.getUnitJob());
			personnelGoAbroad.setIdentity(personnelGoAbroadTwo.getIdentity());
			personnelGoAbroad.setCharacter(personnelGoAbroadTwo.getCharacter());
			personnelGoAbroad.setReason(personnelGoAbroadTwo.getReason());
			personnelGoAbroad.setHandinDate(personnelGoAbroadTwo.getHandinDate());
			personnelGoAbroad.setReceiveDate(personnelGoAbroadTwo.getReceiveDate());
			personnelGoAbroad.setGoAbroadDate(personnelGoAbroadTwo.getGoAbroadDate());
			personnelGoAbroad.setReturnDate(personnelGoAbroadTwo.getReturnDate());
			personnelGoAbroad.setToPlace(personnelGoAbroadTwo.getToPlace());
			personnelGoAbroad.setGroupName(personnelGoAbroadTwo.getGroupName());
			personnelGoAbroad.setApprovalUnitName(personnelGoAbroadTwo.getApprovalUnitName());
			personnelGoAbroad.setApprovalUnitCode(personnelGoAbroadTwo.getApprovalUnitCode());
			personnelGoAbroad.setApprovalFileNo(personnelGoAbroadTwo.getApprovalFileNo());
			personnelGoAbroad.setAssignUnit(personnelGoAbroadTwo.getAssignUnit());
			personnelGoAbroad.setAssignUnitCode(personnelGoAbroadTwo.getAssignUnitCode());
			personnelGoAbroad.setToUnit(personnelGoAbroadTwo.getToUnit());
			personnelGoAbroad.setFundsSource(personnelGoAbroadTwo.getFundsSource());
			personnelGoAbroad.setPassportNo(personnelGoAbroadTwo.getPassportNo());
			personnelGoAbroad.setAnnex(personnelGoAbroadTwo.getAnnex());
			personnelGoAbroad.setSuggestion(personnelGoAbroadTwo.getSuggestion());
			personnelGoAbroad.setBeizhu(personnelGoAbroadTwo.getBeizhu());
			personnelGoAbroadService.save(personnelGoAbroad);
		}
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

}