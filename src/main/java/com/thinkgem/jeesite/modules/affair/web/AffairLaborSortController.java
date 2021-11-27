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
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborSort;
import com.thinkgem.jeesite.modules.affair.entity.AffairLiveFire;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborSortService;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliceHomeService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
 * 考勤人员排序Controller
 * @author cecil.li
 * @version 2020-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLaborSort")
public class AffairLaborSortController extends BaseController {

	@Autowired
	private AffairLaborSortService affairLaborSortService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairLaborOfficeService affairLaborOfficeService;

	@Autowired
	private AffairPoliceHomeService affairPoliceHomeService;
	
	@ModelAttribute
	public AffairLaborSort get(@RequestParam(required=false) String id) {
		AffairLaborSort entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLaborSortService.get(id);
		}
		if (entity == null){
			entity = new AffairLaborSort();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLaborSort:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLaborSort affairLaborSort, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairLaborSort> page = affairLaborSortService.findPage(new Page<AffairLaborSort>(request, response, -1), affairLaborSort);
		model.addAttribute("page", page);
		return "modules/affair/affairLaborSortList";
	}

	@RequiresPermissions("affair:affairLaborSort:view")
	@RequestMapping(value = "form")
	public String form(AffairLaborSort affairLaborSort, Model model) {
		model.addAttribute("affairLaborSort", affairLaborSort);
		return "modules/affair/affairLaborSortForm";
	}

	@RequiresPermissions("affair:affairLaborSort:edit")
	@RequestMapping(value = "save")
	public String save(AffairLaborSort affairLaborSort, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairLaborSort)){
			return form(affairLaborSort, model);
		}
		affairLaborSortService.save(affairLaborSort);
		addMessage(redirectAttributes, "保存考勤人员排序成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairLaborSortForm";
	}
	
	@RequiresPermissions("affair:affairLaborSort:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLaborSort affairLaborSort, RedirectAttributes redirectAttributes) {
		affairLaborSortService.delete(affairLaborSort);
		addMessage(redirectAttributes, "删除考勤人员排序成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLaborSort/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLaborSort:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLaborSortService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairLaborSort affairLaborSort, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairLaborSort> page = null;
			if(flag == true){
				page = affairLaborSortService.findPage(new Page<AffairLaborSort>(request, response), affairLaborSort);
			}else{
				page = affairLaborSortService.findPage(new Page<AffairLaborSort>(request, response,-1), affairLaborSort);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLaborSort.class);
			exportExcelNew.setWb(wb);
			List<AffairLaborSort> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairLaborSort?repage";
	}

	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairLaborSort> list = ei.getDataList(AffairLaborSort.class);
			for (AffairLaborSort affairLaborSort : list){
				try{
					affairLaborSort.setUnitId(affairLaborOfficeService.findByName(affairLaborSort.getUnit()));
					BeanValidators.validateWithException(validator, affairLaborSort);
					affairLaborSortService.save(affairLaborSort);
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