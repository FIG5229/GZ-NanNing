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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceWork2;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceWork2Service;
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
 * 警务技术(专业技术)任职资格信息集Controller
 * @author cecil.li
 * @version 2019-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPoliceWork2")
public class PersonnelPoliceWork2Controller extends BaseController {

	@Autowired
	private PersonnelPoliceWork2Service personnelPoliceWork2Service;
	
	@ModelAttribute
	public PersonnelPoliceWork2 get(@RequestParam(required=false) String id) {
		PersonnelPoliceWork2 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPoliceWork2Service.get(id);
		}
		if (entity == null){
			entity = new PersonnelPoliceWork2();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPoliceWork2:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPoliceWork2 personnelPoliceWork2, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceWork2.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelPoliceWork2> page = personnelPoliceWork2Service.findPage(new Page<PersonnelPoliceWork2>(request, response), personnelPoliceWork2);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelPoliceWork2List";
	}

	@RequiresPermissions("personnel:personnelPoliceWork2:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPoliceWork2 personnelPoliceWork2, Model model, HttpServletRequest request) {
		model.addAttribute("personnelPoliceWork2", personnelPoliceWork2);
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceWork2.setIdNumber(request.getParameter("idNumber").toString());
		}
		return "modules/personnel/personnelPoliceWork2Form";
	}

	@RequiresPermissions("personnel:personnelPoliceWork2:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPoliceWork2 personnelPoliceWork2, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPoliceWork2)){
			return form(personnelPoliceWork2, model, request);
		}
		personnelPoliceWork2Service.save(personnelPoliceWork2);
		addMessage(redirectAttributes, "保存警务技术(专业技术)任职资格信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelPoliceWork2Form";
	}
	
	@RequiresPermissions("personnel:personnelPoliceWork2:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPoliceWork2 personnelPoliceWork2, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		personnelPoliceWork2Service.delete(personnelPoliceWork2);
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceWork2/?repage&idNumber="+personnelPoliceWork2.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceWork2/?repage&mType="+request.getParameter("mType").toString();

		}
		addMessage(redirectAttributes, "删除警务技术(专业技术)任职资格信息成功");
		return  url;
	}

	/**
	 * 详情
	 * @param personnelPoliceWork2
	 * @param model
	 * @return
	 */
	@RequiresPermissions("personnel:personnelPoliceWork2:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelPoliceWork2 personnelPoliceWork2, Model model) {
		model.addAttribute("personnelPoliceWork2", personnelPoliceWork2);
		return "modules/personnel/personnelPoliceWork2FormDetail";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelPoliceWork2:view")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPoliceWork2Service.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelPoliceWork2 personnelPoliceWork2, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelPoliceWork2> page = null;
			if(flag == true){
				page = personnelPoliceWork2Service.findPage(new Page<PersonnelPoliceWork2>(request, response), personnelPoliceWork2);
			}else{
				page = personnelPoliceWork2Service.findPage(new Page<PersonnelPoliceWork2>(request, response,-1), personnelPoliceWork2);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelPoliceWork2.class);
			exportExcelNew.setWb(wb);
			List<PersonnelPoliceWork2> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelPoliceWork2?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelPoliceWork2> list = ei.getDataList(PersonnelPoliceWork2.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(item -> {
					if(item.getIdNumber()!=null){
						idNumbers.add(item.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelPoliceWork2Service.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelPoliceWork2 personnelPoliceWork2 : list){
				try{
					BeanValidators.validateWithException(validator, personnelPoliceWork2);
					personnelPoliceWork2Service.save(personnelPoliceWork2);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelPoliceWork2.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelPoliceWork2&isCover="+isCover;
	}

}