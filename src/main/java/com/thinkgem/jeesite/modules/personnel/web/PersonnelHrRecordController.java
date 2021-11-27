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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelHrRecord;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelHrRecordService;
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
 * 人事档案管理信息集Controller
 * @author cecil.li
 * @version 2019-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelHrRecord")
public class PersonnelHrRecordController extends BaseController {

	@Autowired
	private PersonnelHrRecordService personnelHrRecordService;
	
	@ModelAttribute
	public PersonnelHrRecord get(@RequestParam(required=false) String id) {
		PersonnelHrRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelHrRecordService.get(id);
		}
		if (entity == null){
			entity = new PersonnelHrRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelHrRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelHrRecord personnelHrRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelHrRecord.setIdNumber(request.getParameter("idNumber"));
		}
		Page<PersonnelHrRecord> page = personnelHrRecordService.findPage(new Page<PersonnelHrRecord>(request, response), personnelHrRecord);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType", request.getParameter("mType"));
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelHrRecordList";
	}

	@RequiresPermissions("personnel:personnelHrRecord:view")
	@RequestMapping(value = "form")
	public String form(PersonnelHrRecord personnelHrRecord, Model model, HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelHrRecord.setIdNumber(request.getParameter("idNumber"));
		}
		model.addAttribute("personnelHrRecord", personnelHrRecord);
		return "modules/personnel/personnelHrRecordForm";
	}

	@RequiresPermissions("personnel:personnelHrRecord:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelHrRecord personnelHrRecord, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelHrRecord)){
			return form(personnelHrRecord, model, request);
		}
		personnelHrRecordService.save(personnelHrRecord);
		addMessage(redirectAttributes, "保存人事档案管理信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelHrRecordForm";
	}
	
	@RequiresPermissions("personnel:personnelHrRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelHrRecord personnelHrRecord, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		personnelHrRecordService.delete(personnelHrRecord);
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelHrRecord/?repage&idNumber="+personnelHrRecord.getIdNumber();
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType", request.getParameter("mType"));
			 url = "redirect:"+Global.getAdminPath()+"/personnel/personnelHrRecord/?repage&mType="+ request.getParameter("mType");

		}
		addMessage(redirectAttributes, "删除人事档案管理信息成功");
		return url;
	}

	/**
	 * 详情
	 * @param personnelHrRecord
	 * @param model
	 * @return
	 */
	@RequiresPermissions("personnel:personnelHrRecord:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelHrRecord personnelHrRecord, Model model) {
		model.addAttribute("personnelHrRecord", personnelHrRecord);
		return "modules/personnel/personnelHrRecordFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("personnel:personnelHrRecord:view")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelHrRecordService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelHrRecord personnelHrRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<PersonnelHrRecord> page = null;
			if(flag == true){
				page = personnelHrRecordService.findPage(new Page<PersonnelHrRecord>(request, response), personnelHrRecord);
			}else{
				page = personnelHrRecordService.findPage(new Page<PersonnelHrRecord>(request, response,-1), personnelHrRecord);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelHrRecord.class);
			exportExcelNew.setWb(wb);
			List<PersonnelHrRecord> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelHrRecord?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelHrRecord> list = ei.getDataList(PersonnelHrRecord.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(item -> {
					if(item.getIdNumber()!=null){
						idNumbers.add(item.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelHrRecordService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelHrRecord personnelHrRecord : list){
				try{
					BeanValidators.validateWithException(validator, personnelHrRecord);
					personnelHrRecordService.save(personnelHrRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelHrRecord.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelHrRecord&isCover="+isCover;
	}

}