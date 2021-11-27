/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairTeacherClass;
import com.thinkgem.jeesite.modules.affair.service.AffairTeacherClassService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 班主任培训班管理Controller
 * @author alan.wu
 * @version 2020-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTeacherClass")
public class AffairTeacherClassController extends BaseController {

	@Autowired
	private AffairTeacherClassService affairTeacherClassService;

	@Autowired
	private IdGen idGen;

	@ModelAttribute
	public AffairTeacherClass get(@RequestParam(required=false) String id) {
		AffairTeacherClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTeacherClassService.get(id);
		}
		if (entity == null){
			entity = new AffairTeacherClass();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTeacherClass:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTeacherClass affairTeacherClass, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTeacherClass> page = affairTeacherClassService.findPage(new Page<AffairTeacherClass>(request, response), affairTeacherClass); 
		model.addAttribute("page", page);
		return "modules/affair/affairTeacherClassList";
	}

	@RequiresPermissions("affair:affairTeacherClass:view")
	@RequestMapping(value = "form")
	public String form(AffairTeacherClass affairTeacherClass, Model model) {
		model.addAttribute("affairTeacherClass", affairTeacherClass);
		return "modules/affair/affairTeacherClassForm";
	}

	@RequiresPermissions("affair:affairTeacherClass:edit")
	@RequestMapping(value = "save")
	public String save(AffairTeacherClass affairTeacherClass, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTeacherClass)){
			return form(affairTeacherClass, model);
		}
		String classId = affairTeacherClass.getClassId();
		if (StringUtils.isBlank(classId)){
			affairTeacherClass.setClassId(idGen.getNextId());
		}
		affairTeacherClassService.save(affairTeacherClass);
		addMessage(redirectAttributes, "保存班主任培训班管理成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairTeacherClassForm";
	}
	
	@RequiresPermissions("affair:affairTeacherClass:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTeacherClass affairTeacherClass, RedirectAttributes redirectAttributes) {
		affairTeacherClassService.delete(affairTeacherClass);
		addMessage(redirectAttributes, "删除班主任培训班管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTeacherClass/?repage";
	}

	/**
	 * 详情
	 *
	 * @param affairTeacherClass
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTeacherClass:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTeacherClass affairTeacherClass, Model model) {
		model.addAttribute("affairTeacherClass", affairTeacherClass);
		return "modules/affair/affairTeacherClassFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTeacherClass:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTeacherClassService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTeacherClass affairTeacherClass, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			affairTeacherClass.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairTeacherClass> page = null;
			if(flag == true){
				page = affairTeacherClassService.findPage(new Page<AffairTeacherClass>(request, response), affairTeacherClass);
			}else {
				page = affairTeacherClassService.findPage(new Page<AffairTeacherClass>(request, response,-1), affairTeacherClass);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTeacherClass.class);
			exportExcelNew.setWb(wb);
			List<AffairTeacherClass> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairLearnDaily?repage";
	}


	//导入
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTeacherClass> list = ei.getDataList(AffairTeacherClass.class);
			for (AffairTeacherClass affairTeacherClass : list){
				try{

					affairTeacherClass.setClassId(idGen.getNextId());
					BeanValidators.validateWithException(validator, affairTeacherClass);
					affairTeacherClassService.save(affairTeacherClass);
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