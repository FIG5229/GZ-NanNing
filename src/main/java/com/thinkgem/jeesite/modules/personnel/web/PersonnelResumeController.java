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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelResume;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelResumeService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 履历信息集Controller
 * @author cecil.li
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelResume")
public class PersonnelResumeController extends BaseController {
    @Autowired
	private OfficeService officeService;
	@Autowired
	private PersonnelResumeService personnelResumeService;
	
	@ModelAttribute
	public PersonnelResume get(@RequestParam(required=false) String id) {
		PersonnelResume entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelResumeService.get(id);
		}
		if (entity == null){
			entity = new PersonnelResume();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelResume:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelResume personnelResume, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelResume.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelResume> page = personnelResumeService.findPage(new Page<PersonnelResume>(request, response), personnelResume);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelResumeList";
	}

	@RequiresPermissions("personnel:personnelResume:view")
	@RequestMapping(value = "form")
	public String form(PersonnelResume personnelResume, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelResume.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelResume", personnelResume);
		return "modules/personnel/personnelResumeForm";
	}

	@RequiresPermissions("personnel:personnelResume:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelResume personnelResume, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelResume)){
			return form(personnelResume, model ,request);
		}
		personnelResumeService.save(personnelResume);
		addMessage(redirectAttributes, "保存履历信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelResumeForm";
	}
	
	@RequiresPermissions("personnel:personnelResume:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelResume personnelResume, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		personnelResumeService.delete(personnelResume);
		String url =  "redirect:"+Global.getAdminPath()+"/personnel/personnelResume/?repage&idNumber="+personnelResume.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelResume/?repage&mType="+request.getParameter("mType").toString();

		}
		addMessage(redirectAttributes, "删除履历信息成功");
        return url;
 	}
	@RequiresPermissions("personnel:personnelResume:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelResume personnelResume, Model model) {
		model.addAttribute("personnelResume", personnelResume);
		return "modules/personnel/personnelResumeFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelResume:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelResumeService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelResume personnelResume, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelResume> page = null;
			if(flag == true){
				page = personnelResumeService.findPage(new Page<PersonnelResume>(request, response), personnelResume);
			}else{
				page = personnelResumeService.findPage(new Page<PersonnelResume>(request, response,-1), personnelResume);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelResume.class);
			exportExcelNew.setWb(wb);
			List<PersonnelResume> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelResume/?repage";
	}

	//导入的controller
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelResume> list = ei.getDataList(PersonnelResume.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelResume -> {
					if(personnelResume.getIdNumber()!=null){
						idNumbers.add(personnelResume.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
				personnelResumeService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelResume personnelResume : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(personnelResume.getUnit());
					if(orgId != null){
						personnelResume.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, personnelResume);
					personnelResumeService.save(personnelResume);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelResume.getIdNumber()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelResume&isCover="+isCover;
	}
}