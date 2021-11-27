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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelCommunication;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelCommunicationService;
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
 * 交流信息集Controller
 * @author cecil.li
 * @version 2019-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelCommunication")
public class PersonnelCommunicationController extends BaseController {

	@Autowired
	private PersonnelCommunicationService personnelCommunicationService;
	
	@ModelAttribute
	public PersonnelCommunication get(@RequestParam(required=false) String id) {
		PersonnelCommunication entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelCommunicationService.get(id);
		}
		if (entity == null){
			entity = new PersonnelCommunication();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelCommunication:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelCommunication personnelCommunication, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelCommunication.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelCommunication> page = personnelCommunicationService.findPage(new Page<PersonnelCommunication>(request, response), personnelCommunication);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelCommunicationList";
	}

	@RequiresPermissions("personnel:personnelCommunication:view")
	@RequestMapping(value = "form")
	public String form(PersonnelCommunication personnelCommunication, Model model, HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelCommunication.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelCommunication", personnelCommunication);
		return "modules/personnel/personnelCommunicationForm";
	}

	@RequiresPermissions("personnel:personnelCommunication:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelCommunication personnelCommunication, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelCommunication)){
			return form(personnelCommunication, model, request);
		}
		personnelCommunicationService.save(personnelCommunication);
		addMessage(redirectAttributes, "保存交流信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelCommunicationForm";
	}
	
	@RequiresPermissions("personnel:personnelCommunication:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelCommunication personnelCommunication, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelCommunication/?repage&idNumber="+personnelCommunication.getIdNumber();
		personnelCommunicationService.delete(personnelCommunication);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelCommunication/?repage&mType="+request.getParameter("mType").toString();
		}
		addMessage(redirectAttributes, "删除交流信息成功");
		return url;
	}

	/**
	 * 详情
	 * @param personnelCommunication
	 * @param model
	 * @return
	 */
	@RequiresPermissions("personnel:personnelCommunication:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelCommunication personnelCommunication, Model model) {
		model.addAttribute("personnelCommunication", personnelCommunication);
		return "modules/personnel/personnelCommunicationFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelCommunication:view")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelCommunicationService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelCommunication personnelCommunication, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelCommunication> page = null;
			if(flag == true){
				page = personnelCommunicationService.findPage(new Page<PersonnelCommunication>(request, response), personnelCommunication);
			}else{
				page = personnelCommunicationService.findPage(new Page<PersonnelCommunication>(request, response,-1), personnelCommunication);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelCommunication.class);
			exportExcelNew.setWb(wb);
			List<PersonnelCommunication> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelCommunication?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelCommunication> list = ei.getDataList(PersonnelCommunication.class);
			//选择覆盖模式，须先将改身份证下相关信息集删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(item -> {
					if(item.getIdNumber()!=null){
						idNumbers.add(item.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelCommunicationService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelCommunication personnelCommunication : list){
				try{
					BeanValidators.validateWithException(validator, personnelCommunication);
					personnelCommunicationService.save(personnelCommunication);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelCommunication.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelCommunication&isCover="+isCover;
	}
}