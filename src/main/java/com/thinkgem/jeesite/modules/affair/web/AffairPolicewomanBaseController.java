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
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanBase;
import com.thinkgem.jeesite.modules.affair.service.AffairPolicewomanBaseService;
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

/**
 * 女警基本情况Controller
 * @author cecil.li
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPolicewomanBase")
public class AffairPolicewomanBaseController extends BaseController {

	@Autowired
	private AffairPolicewomanBaseService affairPolicewomanBaseService;
	
	@ModelAttribute
	public AffairPolicewomanBase get(@RequestParam(required=false) String id) {
		AffairPolicewomanBase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPolicewomanBaseService.get(id);
		}
		if (entity == null){
			entity = new AffairPolicewomanBase();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPolicewomanBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPolicewomanBase affairPolicewomanBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPolicewomanBase> page = affairPolicewomanBaseService.findPage(new Page<AffairPolicewomanBase>(request, response), affairPolicewomanBase);
		model.addAttribute("page", page);
		return "modules/affair/affairPolicewomanBaseList";
	}

	@RequiresPermissions("affair:affairPolicewomanBase:view")
	@RequestMapping(value = "form")
	public String form(AffairPolicewomanBase affairPolicewomanBase, Model model) {
		model.addAttribute("affairPolicewomanBase", affairPolicewomanBase);
		return "modules/affair/affairPolicewomanBaseForm";
	}

	@RequiresPermissions("affair:affairPolicewomanBase:edit")
	@RequestMapping(value = "save")
	public String save(AffairPolicewomanBase affairPolicewomanBase, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPolicewomanBase)){
			return form(affairPolicewomanBase, model);
		}
		affairPolicewomanBaseService.save(affairPolicewomanBase);
		addMessage(redirectAttributes, "保存女警基本情况成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPolicewomanBaseForm";
	}
	
	@RequiresPermissions("affair:affairPolicewomanBase:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPolicewomanBase affairPolicewomanBase, RedirectAttributes redirectAttributes) {
		affairPolicewomanBaseService.delete(affairPolicewomanBase);
		addMessage(redirectAttributes, "删除女警基本情况成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPolicewomanBase/?repage";
	}

	/**
	 * 详情
	 * @param affairPolicewomanBase
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPolicewomanBase:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPolicewomanBase affairPolicewomanBase, Model model) {
		model.addAttribute("affairPolicewomanBase", affairPolicewomanBase);
		return "modules/affair/affairPolicewomanBaseFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPolicewomanBase:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPolicewomanBaseService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairPolicewomanBase affairPolicewomanBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairPolicewomanBase> page = null;
			if(flag == true){
				page = affairPolicewomanBaseService.findPage(new Page<AffairPolicewomanBase>(request, response), affairPolicewomanBase);
			}else{
				page = affairPolicewomanBaseService.findPage(new Page<AffairPolicewomanBase>(request, response,-1), affairPolicewomanBase);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPolicewomanBase.class);
			exportExcelNew.setWb(wb);
			List<AffairPolicewomanBase> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/affairPolicewomanBase/?repage";
	}


	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPolicewomanBase> list = ei.getDataList(AffairPolicewomanBase.class);
			for (AffairPolicewomanBase affairPolicewomanBase : list){
				try{
					BeanValidators.validateWithException(validator, affairPolicewomanBase);
					affairPolicewomanBaseService.save(affairPolicewomanBase);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairPolicewomanBase.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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