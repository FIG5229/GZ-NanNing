/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.dao.AffairInteriorInstructorLibraryDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActive;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairInteriorInstructorLibrary;
import com.thinkgem.jeesite.modules.affair.service.AffairInteriorInstructorLibraryService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 内部教官Controller
 * @author alan.wu
 * @version 2020-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairInteriorInstructorLibrary")
public class AffairInteriorInstructorLibraryController extends BaseController {

	@Autowired
	private AffairInteriorInstructorLibraryService affairInteriorInstructorLibraryService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairInteriorInstructorLibraryDao affairInteriorInstructorLibraryDao;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairInteriorInstructorLibrary get(@RequestParam(required=false) String id) {
		AffairInteriorInstructorLibrary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairInteriorInstructorLibraryService.get(id);
		}
		if (entity == null){
			entity = new AffairInteriorInstructorLibrary();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairInteriorInstructorLibrary:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairInteriorInstructorLibrary> page = affairInteriorInstructorLibraryService.findPage(new Page<AffairInteriorInstructorLibrary>(request, response), affairInteriorInstructorLibrary); 
		model.addAttribute("page", page);
		return "modules/affair/affairInteriorInstructorLibraryList";
	}

	@RequiresPermissions("affair:affairInteriorInstructorLibrary:view")
	@RequestMapping(value = "form")
	public String form(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary, Model model) {
		model.addAttribute("affairInteriorInstructorLibrary", affairInteriorInstructorLibrary);
		return "modules/affair/affairInteriorInstructorLibraryForm";
	}

	@RequiresPermissions("affair:affairInteriorInstructorLibrary:edit")
	@RequestMapping(value = "save")
	public String save(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairInteriorInstructorLibrary)){
			return form(affairInteriorInstructorLibrary, model);
		}
		affairInteriorInstructorLibrary.setScope("内部教官");
		affairInteriorInstructorLibraryService.save(affairInteriorInstructorLibrary);
		addMessage(redirectAttributes, "保存内部教官成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairInteriorInstructorLibraryForm";
	}
	
	@RequiresPermissions("affair:affairInteriorInstructorLibrary:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary, RedirectAttributes redirectAttributes) {
		affairInteriorInstructorLibraryService.delete(affairInteriorInstructorLibrary);
		addMessage(redirectAttributes, "删除内部教官成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairInteriorInstructorLibrary/?repage";
	}

	/**
	 * 详情
	 *
	 * @param affairInteriorInstructorLibrary
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairInteriorInstructorLibrary:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary, Model model) {
		model.addAttribute("affairInteriorInstructorLibrary", affairInteriorInstructorLibrary);
		if (affairInteriorInstructorLibrary.getAdjunct() != null && affairInteriorInstructorLibrary.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairInteriorInstructorLibrary.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairInteriorInstructorLibraryFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairInteriorInstructorLibrary:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairInteriorInstructorLibraryService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			affairInteriorInstructorLibrary.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairInteriorInstructorLibrary> page = null;
			if(flag == true){
				page = affairInteriorInstructorLibraryService.findPage(new Page<AffairInteriorInstructorLibrary>(request, response), affairInteriorInstructorLibrary);
			}else {
				page = affairInteriorInstructorLibraryService.findPage(new Page<AffairInteriorInstructorLibrary>(request, response,-1), affairInteriorInstructorLibrary);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairInteriorInstructorLibrary.class);
			exportExcelNew.setWb(wb);
			List<AffairInteriorInstructorLibrary> list =page.getList();

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
		return "redirect:" + adminPath + "/affair/affairInteriorInstructorLibrary?repage";
	}


	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairInteriorInstructorLibrary> list = ei.getDataList(AffairInteriorInstructorLibrary.class);
			for (AffairInteriorInstructorLibrary affairInteriorInstructorLibrary : list) {
				try {
					if (affairInteriorInstructorLibrary.getUnit()!=null&&!"".equals(affairInteriorInstructorLibrary.getUnit())) {
						affairInteriorInstructorLibrary.setUnitId(officeService.findByName(affairInteriorInstructorLibrary.getUnit()));
					}
					BeanValidators.validateWithException(validator, affairInteriorInstructorLibrary);
					affairInteriorInstructorLibrary.setScope("内部教官");
					affairInteriorInstructorLibraryService.save(affairInteriorInstructorLibrary);
					successNum++;
				} catch (ConstraintViolationException ex) {
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append(" 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result", "success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}

	// 教官库统计分析
	public List<Map<String, String>> countInstructor() {
		return affairInteriorInstructorLibraryDao.countInstructor();
	}

	@RequestMapping(value = {"jiaoGuanDetail"})
	public String readBookDetail(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("label",request.getParameter("label"));
		Page<AffairInteriorInstructorLibrary> page = affairInteriorInstructorLibraryService.findJiaoGuanPage(new Page<AffairInteriorInstructorLibrary>(request, response), affairInteriorInstructorLibrary);
		model.addAttribute("page", page);
		return "modules/charts/jiaoGuanList";
	}

}


