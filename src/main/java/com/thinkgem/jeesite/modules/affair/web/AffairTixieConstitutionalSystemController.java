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
import com.thinkgem.jeesite.modules.affair.entity.AffairTixieConstitutionalSystem;
import com.thinkgem.jeesite.modules.affair.service.AffairTixieConstitutionalSystemService;
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
 * 体协工作章程制度Controller
 * @author cecil.li
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTixieConstitutionalSystem")
public class AffairTixieConstitutionalSystemController extends BaseController {

	@Autowired
	private AffairTixieConstitutionalSystemService affairTixieConstitutionalSystemService;
	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairTixieConstitutionalSystem get(@RequestParam(required=false) String id) {
		AffairTixieConstitutionalSystem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTixieConstitutionalSystemService.get(id);
		}
		if (entity == null){
			entity = new AffairTixieConstitutionalSystem();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTixieConstitutionalSystem:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTixieConstitutionalSystem> page = affairTixieConstitutionalSystemService.findPage(new Page<AffairTixieConstitutionalSystem>(request, response), affairTixieConstitutionalSystem); 
		model.addAttribute("page", page);
		return "modules/affair/affairTixieConstitutionalSystemList";
	}

	@RequiresPermissions("affair:affairTixieConstitutionalSystem:view")
	@RequestMapping(value = "form")
	public String form(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem, Model model) {
		model.addAttribute("affairTixieConstitutionalSystem", affairTixieConstitutionalSystem);
		return "modules/affair/affairTixieConstitutionalSystemForm";
	}

	@RequiresPermissions("affair:affairTixieConstitutionalSystem:edit")
	@RequestMapping(value = "save")
	public String save(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTixieConstitutionalSystem)){
			return form(affairTixieConstitutionalSystem, model);
		}
		affairTixieConstitutionalSystemService.save(affairTixieConstitutionalSystem);
		addMessage(redirectAttributes, "保存章程制度成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairTixieConstitutionalSystemForm";
	}
	
	@RequiresPermissions("affair:affairTixieConstitutionalSystem:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem, RedirectAttributes redirectAttributes) {
		affairTixieConstitutionalSystemService.delete(affairTixieConstitutionalSystem);
		addMessage(redirectAttributes, "删除章程制度成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTixieConstitutionalSystem/?repage";
	}

	/**
	 * 详情
	 * @param affairTixieConstitutionalSystem
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTixieConstitutionalSystem:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem, Model model) {
		model.addAttribute("affairTixieConstitutionalSystem", affairTixieConstitutionalSystem);
		if(affairTixieConstitutionalSystem.getAnnex() != null && affairTixieConstitutionalSystem.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTixieConstitutionalSystem.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairTixieConstitutionalSystemFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTixieConstitutionalSystem:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTixieConstitutionalSystemService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTixieConstitutionalSystem affairTixieConstitutionalSystem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairTixieConstitutionalSystem> page = null;
			if(flag == true){
				page = affairTixieConstitutionalSystemService.findPage(new Page<AffairTixieConstitutionalSystem>(request, response), affairTixieConstitutionalSystem);
			}else{
				page = affairTixieConstitutionalSystemService.findPage(new Page<AffairTixieConstitutionalSystem>(request, response,-1), affairTixieConstitutionalSystem);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTixieConstitutionalSystem.class);
			exportExcelNew.setWb(wb);
			List<AffairTixieConstitutionalSystem> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTixieConstitutionalSystem/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTixieConstitutionalSystem> list = ei.getDataList(AffairTixieConstitutionalSystem.class);
			for (AffairTixieConstitutionalSystem affairTixieConstitutionalSystem : list){
				try{
					BeanValidators.validateWithException(validator, affairTixieConstitutionalSystem);
					affairTixieConstitutionalSystemService.save(affairTixieConstitutionalSystem);
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