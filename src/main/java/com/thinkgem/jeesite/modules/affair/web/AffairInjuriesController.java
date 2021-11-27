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
import com.thinkgem.jeesite.modules.affair.entity.AffairInjuries;
import com.thinkgem.jeesite.modules.affair.service.AffairInjuriesService;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
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
 * 因公负伤Controller
 * @author cecil.li
 * @version 2020-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairInjuries")
public class AffairInjuriesController extends BaseController {

	@Autowired
	private AffairInjuriesService affairInjuriesService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairLaborOfficeService affairLaborOfficeService;
	
	@ModelAttribute
	public AffairInjuries get(@RequestParam(required=false) String id) {
		AffairInjuries entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairInjuriesService.get(id);
		}
		if (entity == null){
			entity = new AffairInjuries();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairInjuries:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairInjuries affairInjuries, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairInjuries> page = affairInjuriesService.findPage(new Page<AffairInjuries>(request, response), affairInjuries); 
		model.addAttribute("page", page);
		return "modules/affair/affairInjuriesList";
	}

	@RequiresPermissions("affair:affairInjuries:view")
	@RequestMapping(value = "form")
	public String form(AffairInjuries affairInjuries, Model model) {
		model.addAttribute("affairInjuries", affairInjuries);
		return "modules/affair/affairInjuriesForm";
	}

	@RequiresPermissions("affair:affairInjuries:edit")
	@RequestMapping(value = "save")
	public String save(AffairInjuries affairInjuries, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairInjuries)){
			return form(affairInjuries, model);
		}
		affairInjuriesService.save(affairInjuries);
		addMessage(redirectAttributes, "保存因公负伤成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairInjuriesForm";
	}
	
	@RequiresPermissions("affair:affairInjuries:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairInjuries affairInjuries, RedirectAttributes redirectAttributes) {
		affairInjuriesService.delete(affairInjuries);
		addMessage(redirectAttributes, "删除因公负伤成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairInjuries/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairInjuries:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairInjuriesService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 * @param affairInjuries
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairInjuries:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairInjuries affairInjuries, Model model) {
		model.addAttribute("affairInjuries", affairInjuries);
		if(affairInjuries.getAnnex() != null && affairInjuries.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairInjuries.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairInjuriesFormDetail";
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
	public String exportExcelByTemplate(AffairInjuries affairInjuries, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairInjuries.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairInjuries> page = null;
			if(flag == true){
				page = affairInjuriesService.findPage(new Page<AffairInjuries>(request, response), affairInjuries);
			}else{
				page = affairInjuriesService.findPage(new Page<AffairInjuries>(request, response,-1), affairInjuries);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairInjuries.class);
			exportExcelNew.setWb(wb);
			List<AffairInjuries> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairInjuries/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairInjuries> list = ei.getDataList(AffairInjuries.class);
			for (AffairInjuries affairInjuries : list){
				try{
					affairInjuries.setRdUnitId(affairLaborOfficeService.findByName(affairInjuries.getRdUnit()));
					affairInjuries.setUnitId(affairLaborOfficeService.findByName(affairInjuries.getUnit()));
					BeanValidators.validateWithException(validator, affairInjuries);
					affairInjuriesService.save(affairInjuries);
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