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
import com.thinkgem.jeesite.modules.affair.entity.AffairExamManagement;
import com.thinkgem.jeesite.modules.affair.service.AffairExamManagementService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 考核成绩Controller
 * @author cecil.li
 * @version 2020-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairExamManagement")
public class AffairExamManagementController extends BaseController {

	@Autowired
	private AffairExamManagementService affairExamManagementService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairExamManagement get(@RequestParam(required=false) String id) {
		AffairExamManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairExamManagementService.get(id);
		}
		if (entity == null){
			entity = new AffairExamManagement();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairExamManagement:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairExamManagement affairExamManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("5".equals(UserUtils.getUser().getOffice().getId())){
			affairExamManagement.setQxUnitId(UserUtils.getUser().getCompany().getId());
			affairExamManagement.setLookId("1");
		}else if ("34".equals(UserUtils.getUser().getCompany().getId())) {
			affairExamManagement.setQxUnitId(UserUtils.getUser().getOffice().getId());
			affairExamManagement.setLookId("28");
		}else if("95".equals(UserUtils.getUser().getCompany().getId())) {
			affairExamManagement.setQxUnitId(UserUtils.getUser().getOffice().getId());
			affairExamManagement.setLookId("143");
		}else if ("156".equals(UserUtils.getUser().getCompany().getId())) {
			affairExamManagement.setQxUnitId(UserUtils.getUser().getOffice().getId());
			affairExamManagement.setLookId("265");
		}else {
				affairExamManagement.setQxUnitId(UserUtils.getUser().getOffice().getId());
			}
		affairExamManagement.setCreateBy(UserUtils.getUser());
		Page<AffairExamManagement> page = affairExamManagementService.findPage(new Page<AffairExamManagement>(request, response), affairExamManagement);
		model.addAttribute("page", page);
		return "modules/affair/affairExamManagementList";
	}

	@RequiresPermissions("affair:affairExamManagement:add")
	@RequestMapping(value = "form")
	public String form(AffairExamManagement affairExamManagement, Model model) {
		model.addAttribute("affairExamManagement", affairExamManagement);
		return "modules/affair/affairExamManagementForm";
	}

	@RequiresPermissions("affair:affairExamManagement:edit")
	@RequestMapping(value = "save")
	public String save(AffairExamManagement affairExamManagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		Office office = officeService.get(affairExamManagement.getUnitId());
		Set examOrgSet = new HashSet();
		examOrgSet.add("1");
		examOrgSet.add("34");
		examOrgSet.add("95");
		examOrgSet.add("156");
        String examOrg = "";
        if(examOrgSet.contains(office.getParentId())){
			examOrg = office.getParentId();
		}else if(examOrgSet.contains(office.getParent().getParentId())){
			examOrg = office.getParent().getParentId();
		}
        String checkUnit ="";
        switch (examOrg){
			case "1":
				checkUnit = "1";
				break;
			case "34":
				checkUnit = "28";
				break;
			case "95":
				checkUnit = "143";
				break;
			case "156":
				checkUnit = "265";
				break;
		}
		affairExamManagement.setLookId(checkUnit);
		if (!beanValidator(model, affairExamManagement)){
			return form(affairExamManagement, model);
		}
		affairExamManagementService.save(affairExamManagement);
		addMessage(redirectAttributes, "保存考核成绩成功");
		//在请求里添加saveRequest用于Form表单关闭页面
		request.setAttribute("saveResult","success");
		return "modules/affair/affairExamManagementForm";
	}
	
	@RequiresPermissions("affair:affairExamManagement:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairExamManagement affairExamManagement, RedirectAttributes redirectAttributes) {
		affairExamManagementService.delete(affairExamManagement);
		addMessage(redirectAttributes, "删除考核成绩成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairExamManagement/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairExamManagement:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairExamManagementService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * form表单详情
	 * @param affairExamManagement
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairExamManagement:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairExamManagement affairExamManagement, Model model) {
		model.addAttribute("affairExamManagement", affairExamManagement);
		return "modules/affair/affairExamManagementFormDetail";
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
	public String exportExcelByTemplate(AffairExamManagement affairExamManagement, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairExamManagement> page = null;
			if(flag == true){
				page = affairExamManagementService.findPage(new Page<AffairExamManagement>(request, response), affairExamManagement);
			}else {
				page = affairExamManagementService.findPage(new Page<AffairExamManagement>(request, response,-1), affairExamManagement);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairExamManagement.class);
			exportExcelNew.setWb(wb);
			List<AffairExamManagement> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairFocusStudy?repage";
	}

	@RequiresPermissions("affair:affairExamManagement:add")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairExamManagement> list = ei.getDataList(AffairExamManagement.class);
			for (AffairExamManagement affairExamManagement : list){
				try{
					BeanValidators.validateWithException(validator, affairExamManagement);
					affairExamManagementService.save(affairExamManagement);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(" 导入失败："+ex.getMessage());
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

}