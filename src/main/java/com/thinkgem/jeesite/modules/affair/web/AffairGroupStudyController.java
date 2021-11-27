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
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairGroupStudy;
import com.thinkgem.jeesite.modules.affair.service.AffairGroupStudyService;
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
import java.util.List;
import java.util.Map;

/**
 * 党委中心组学习Controller
 * @author cecil.li
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGroupStudy")
public class AffairGroupStudyController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairGroupStudyService affairGroupStudyService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairGroupStudy get(@RequestParam(required=false) String id) {
		AffairGroupStudy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGroupStudyService.get(id);
		}
		if (entity == null){
			entity = new AffairGroupStudy();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairGroupStudy:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairGroupStudy affairGroupStudy, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("5".equals(UserUtils.getUser().getOffice().getId())){
			affairGroupStudy.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairGroupStudy.setUnitId(UserUtils.getUser().getOffice().getId());
		}

		affairGroupStudy.setCreateBy(UserUtils.getUser());
		Page<AffairGroupStudy> page = affairGroupStudyService.findPage(new Page<AffairGroupStudy>(request, response), affairGroupStudy);
		model.addAttribute("page", page);
		return "modules/affair/affairGroupStudyList";
	}

	@RequiresPermissions("affair:affairGroupStudy:view")
	@RequestMapping(value = "form")
	public String form(AffairGroupStudy affairGroupStudy, Model model) {
		model.addAttribute("affairGroupStudy", affairGroupStudy);
		return "modules/affair/affairGroupStudyForm";
	}

	@RequiresPermissions("affair:affairGroupStudy:edit")
	@RequestMapping(value = "save")
	public String save(AffairGroupStudy affairGroupStudy, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairGroupStudy)){
			return form(affairGroupStudy, model);
		}
		affairGroupStudyService.save(affairGroupStudy);
		addMessage(redirectAttributes, "保存党委中心组学习成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairGroupStudyForm";
	}
	
	@RequiresPermissions("affair:affairGroupStudy:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGroupStudy affairGroupStudy, RedirectAttributes redirectAttributes) {
		affairGroupStudyService.delete(affairGroupStudy);
		addMessage(redirectAttributes, "删除党委中心组学习成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGroupStudy/?repage";
	}

	/**
	 * 详情
	 * @param affairGroupStudy
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGroupStudy:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairGroupStudy affairGroupStudy, Model model) {
		model.addAttribute("affairGroupStudy", affairGroupStudy);
		if(affairGroupStudy.getFilePath() != null && affairGroupStudy.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairGroupStudy.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairGroupStudyFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairGroupStudy:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGroupStudyService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairGroupStudy affairGroupStudy, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairGroupStudy.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairGroupStudy.setUnitId(UserUtils.getUser().getOffice().getId());
			}

			affairGroupStudy.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairGroupStudy> page = null;
			if(flag == true){
				page = affairGroupStudyService.findPage(new Page<AffairGroupStudy>(request, response), affairGroupStudy);
			}else {
				page = affairGroupStudyService.findPage(new Page<AffairGroupStudy>(request, response,-1), affairGroupStudy);
			}
/*
			Page<AffairGroupStudy> page = affairGroupStudyService.findPage(new Page<AffairGroupStudy>(request, response,-1), affairGroupStudy);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGroupStudy.class);
			exportExcelNew.setWb(wb);
			List<AffairGroupStudy> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairGroupStudy?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairGroupStudy> list = ei.getDataList(AffairGroupStudy.class);
			for (AffairGroupStudy affairGroupStudy : list){
				try{
					affairGroupStudy.setOrganizationId(officeService.findByName(affairGroupStudy.getOrganization()));
					BeanValidators.validateWithException(validator, affairGroupStudy);
					affairGroupStudyService.save(affairGroupStudy);
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

	@RequestMapping(value = {"partyStudyDetail"})
	public String partyStudyDetail(AffairGroupStudy affairGroupStudy, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<AffairGroupStudy> page = affairGroupStudyService.findPartyStudyPage(new Page<AffairGroupStudy>(request, response), affairGroupStudy);
		model.addAttribute("page", page);
		model.addAttribute("dateType",affairGroupStudy.getDateType());
		model.addAttribute("month",affairGroupStudy.getMonth());
		model.addAttribute("year",affairGroupStudy.getYear());
		model.addAttribute("label",affairGroupStudy.getLabel());
		return "modules/charts/chartGroupStudyList";
	}

}