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
import com.thinkgem.jeesite.modules.affair.entity.AffairJobSalaryStandard;
import com.thinkgem.jeesite.modules.affair.service.AffairJobSalaryStandardService;
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
import java.util.Calendar;
import java.util.List;

/**
 * 职务工资标准Controller
 * @author cecil.li
 * @version 2020-07-01
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairJobSalaryStandard")
public class AffairJobSalaryStandardController extends BaseController {

	@Autowired
	private AffairJobSalaryStandardService affairJobSalaryStandardService;
	
	@ModelAttribute
	public AffairJobSalaryStandard get(@RequestParam(required=false) String id) {
		AffairJobSalaryStandard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairJobSalaryStandardService.get(id);
		}
		if (entity == null){
			entity = new AffairJobSalaryStandard();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairJobSalaryStandard:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairJobSalaryStandard affairJobSalaryStandard, HttpServletRequest request, HttpServletResponse response, Model model) {

		Calendar calendar = Calendar.getInstance();
		Integer ye = calendar.get(Calendar.YEAR);
		String yea = String.valueOf(ye);

		String year = affairJobSalaryStandard.getYear();
		if (StringUtils.isBlank(year)){
			affairJobSalaryStandard.setYear(yea);
		}

		Page<AffairJobSalaryStandard> page = affairJobSalaryStandardService.findPage(new Page<AffairJobSalaryStandard>(request, response), affairJobSalaryStandard); 
		model.addAttribute("page", page);
		return "modules/affair/affairJobSalaryStandardList";
	}

	@RequiresPermissions("affair:affairJobSalaryStandard:view")
	@RequestMapping(value = "form")
	public String form(AffairJobSalaryStandard affairJobSalaryStandard, Model model) {
		model.addAttribute("affairJobSalaryStandard", affairJobSalaryStandard);
		return "modules/affair/affairJobSalaryStandardForm";
	}

	@RequiresPermissions("affair:affairJobSalaryStandard:edit")
	@RequestMapping(value = "save")
	public String save(AffairJobSalaryStandard affairJobSalaryStandard, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairJobSalaryStandard)){
			return form(affairJobSalaryStandard, model);
		}
		affairJobSalaryStandardService.save(affairJobSalaryStandard);
		addMessage(redirectAttributes, "保存职务工资标准成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairJobSalaryStandardForm";
	}
	
	@RequiresPermissions("affair:affairJobSalaryStandard:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairJobSalaryStandard affairJobSalaryStandard, RedirectAttributes redirectAttributes) {
		affairJobSalaryStandardService.delete(affairJobSalaryStandard);
		addMessage(redirectAttributes, "删除职务工资标准成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairJobSalaryStandard/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
//	@RequiresPermissions("affair:affairJobSalaryStandard:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairJobSalaryStandardService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairJobSalaryStandard affairJobSalaryStandard, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairJobSalaryStandard.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairJobSalaryStandard> page = null;
			if(flag == true){
				page = affairJobSalaryStandardService.findPage(new Page<AffairJobSalaryStandard>(request, response), affairJobSalaryStandard);
			}else{
				page = affairJobSalaryStandardService.findPage(new Page<AffairJobSalaryStandard>(request, response,-1), affairJobSalaryStandard);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairJobSalaryStandard.class);
			exportExcelNew.setWb(wb);
			List<AffairJobSalaryStandard> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairJobSalaryStandard/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairJobSalaryStandard> list = ei.getDataList(AffairJobSalaryStandard.class);
			for (AffairJobSalaryStandard affairJobSalaryStandard : list){
				try{
					BeanValidators.validateWithException(validator, affairJobSalaryStandard);
					affairJobSalaryStandardService.save(affairJobSalaryStandard);
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