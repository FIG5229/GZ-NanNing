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
import com.thinkgem.jeesite.modules.affair.entity.AffairConstitutionalSystem;
import com.thinkgem.jeesite.modules.affair.service.AffairConstitutionalSystemService;
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
 * 警官艺术团章程制度Controller
 * @author cecil.li
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairConstitutionalSystem")
public class AffairConstitutionalSystemController extends BaseController {

	@Autowired
	private AffairConstitutionalSystemService affairConstitutionalSystemService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairConstitutionalSystem get(@RequestParam(required=false) String id) {
		AffairConstitutionalSystem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairConstitutionalSystemService.get(id);
		}
		if (entity == null){
			entity = new AffairConstitutionalSystem();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairConstitutionalSystem:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairConstitutionalSystem affairConstitutionalSystem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairConstitutionalSystem> page = affairConstitutionalSystemService.findPage(new Page<AffairConstitutionalSystem>(request, response), affairConstitutionalSystem); 
		model.addAttribute("page", page);
		return "modules/affair/affairConstitutionalSystemList";
	}

	@RequiresPermissions("affair:affairConstitutionalSystem:view")
	@RequestMapping(value = "form")
	public String form(AffairConstitutionalSystem affairConstitutionalSystem, Model model) {
		model.addAttribute("affairConstitutionalSystem", affairConstitutionalSystem);
		return "modules/affair/affairConstitutionalSystemForm";
	}

	@RequiresPermissions("affair:affairConstitutionalSystem:edit")
	@RequestMapping(value = "save")
	public String save(AffairConstitutionalSystem affairConstitutionalSystem, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairConstitutionalSystem)){
			return form(affairConstitutionalSystem, model);
		}
		affairConstitutionalSystemService.save(affairConstitutionalSystem);
		addMessage(redirectAttributes, "保存章程制度成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairConstitutionalSystemForm";
	}
	
	@RequiresPermissions("affair:affairConstitutionalSystem:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairConstitutionalSystem affairConstitutionalSystem, RedirectAttributes redirectAttributes) {
		affairConstitutionalSystemService.delete(affairConstitutionalSystem);
		addMessage(redirectAttributes, "删除章程制度成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairConstitutionalSystem/?repage";
	}

	/**
	 * 详情
	 * @param affairConstitutionalSystem
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairConstitutionalSystem:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairConstitutionalSystem affairConstitutionalSystem, Model model) {
		model.addAttribute("affairConstitutionalSystem", affairConstitutionalSystem);
		if(affairConstitutionalSystem.getAnnec() != null && affairConstitutionalSystem.getAnnec().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairConstitutionalSystem.getAnnec());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairConstitutionalSystemFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairConstitutionalSystem:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairConstitutionalSystemService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairConstitutionalSystem affairConstitutionalSystem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairConstitutionalSystem> page = null;
			if(flag == true){
				page = affairConstitutionalSystemService.findPage(new Page<AffairConstitutionalSystem>(request, response), affairConstitutionalSystem);
			}else{
				page = affairConstitutionalSystemService.findPage(new Page<AffairConstitutionalSystem>(request, response,-1), affairConstitutionalSystem);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairConstitutionalSystem.class);
			exportExcelNew.setWb(wb);
			List<AffairConstitutionalSystem> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairConstitutionalSystem/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairConstitutionalSystem> list = ei.getDataList(AffairConstitutionalSystem.class);
			for (AffairConstitutionalSystem affairConstitutionalSystem : list){
				try{
					BeanValidators.validateWithException(validator, affairConstitutionalSystem);
					affairConstitutionalSystemService.save(affairConstitutionalSystem);
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