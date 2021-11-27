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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPartyMember;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPartyMemberService;
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
import java.util.List;

/**
 * 党员信息集Controller
 * @author cecil.li
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPartyMember")
public class PersonnelPartyMemberController extends BaseController {

	@Autowired
	private PersonnelPartyMemberService personnelPartyMemberService;
	
	@ModelAttribute
	public PersonnelPartyMember get(@RequestParam(required=false) String id) {
		PersonnelPartyMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPartyMemberService.get(id);
		}
		if (entity == null){
			entity = new PersonnelPartyMember();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPartyMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPartyMember personnelPartyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPartyMember.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelPartyMember> page = personnelPartyMemberService.findPage(new Page<PersonnelPartyMember>(request, response), personnelPartyMember);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelPartyMemberList";
	}

	@RequiresPermissions("personnel:personnelPartyMember:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPartyMember personnelPartyMember, Model model, HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPartyMember.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelPartyMember", personnelPartyMember);
		return "modules/personnel/personnelPartyMemberForm";
	}

	@RequiresPermissions("personnel:personnelPartyMember:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPartyMember personnelPartyMember, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPartyMember)){
			return form(personnelPartyMember, model, request);
		}
		personnelPartyMemberService.save(personnelPartyMember);
		addMessage(redirectAttributes, "保存党员信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelPartyMemberForm";
	}
	
	@RequiresPermissions("personnel:personnelPartyMember:view")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPartyMember personnelPartyMember, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPartyMember/?repage&idNumber="+personnelPartyMember.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPartyMember/?repage&mType="+request.getParameter("mType").toString();

		}
		personnelPartyMemberService.delete(personnelPartyMember);
		addMessage(redirectAttributes, "删除党员信息成功");
		return url;
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelPartyMember:view")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPartyMemberService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelPartyMember personnelPartyMember, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelPartyMember> page = null;
			if(flag == true){
				page = personnelPartyMemberService.findPage(new Page<PersonnelPartyMember>(request, response), personnelPartyMember);
			}else{
				page = personnelPartyMemberService.findPage(new Page<PersonnelPartyMember>(request, response,-1), personnelPartyMember);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelPartyMember.class);
			exportExcelNew.setWb(wb);
			List<PersonnelPartyMember> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelPartyMember?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelPartyMember> list = ei.getDataList(PersonnelPartyMember.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelPartyMember -> {
					if(personnelPartyMember.getIdNumber()!=null){
						idNumbers.add(personnelPartyMember.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelPartyMemberService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelPartyMember personnelPartyMember : list){
				try{
					BeanValidators.validateWithException(validator, personnelPartyMember);
					personnelPartyMemberService.save(personnelPartyMember);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelPartyMember.getIdNumber()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
//		redirectAttributes.addFlashAttribute("result","success");
//		return "redirect:" + adminPath + "/file/template/download/view";
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelPartyMember&isCover="+isCover;
	}

}