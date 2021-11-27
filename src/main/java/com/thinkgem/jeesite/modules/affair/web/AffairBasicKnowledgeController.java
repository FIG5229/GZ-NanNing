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
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicKnowledge;
import com.thinkgem.jeesite.modules.affair.entity.AffairHealthCheckup;
import com.thinkgem.jeesite.modules.affair.service.AffairBasicKnowledgeService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
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
import java.util.Calendar;
import java.util.List;

/**
 * 基本知识成绩Controller
 * @author cecil.li
 * @version 2020-12-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairBasicKnowledge")
public class AffairBasicKnowledgeController extends BaseController {

	@Autowired
	private AffairBasicKnowledgeService affairBasicKnowledgeService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairBasicKnowledge get(@RequestParam(required=false) String id) {
		AffairBasicKnowledge entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairBasicKnowledgeService.get(id);
		}
		if (entity == null){
			entity = new AffairBasicKnowledge();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairBasicKnowledge:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/affair/affairBasicKnowledgeIndex";
	}

	@RequiresPermissions("affair:affairBasicKnowledge:view")
	@RequestMapping(value = {"list"})
	public String list(AffairBasicKnowledge affairBasicKnowledge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH )+1);
		String monthCopy = "";
		if (month.length() == 1){
			monthCopy = "0" + month;
		}
		String yearMonth = year + "-" +monthCopy;
		if (affairBasicKnowledge.getYearMonth() == null || "".equals(affairBasicKnowledge.getYearMonth())) {
			affairBasicKnowledge.setYearMonth(yearMonth);
		}
		Page<AffairBasicKnowledge> page = affairBasicKnowledgeService.findPage(new Page<AffairBasicKnowledge>(request, response), affairBasicKnowledge);
		model.addAttribute("yearMonth", affairBasicKnowledge.getYearMonth());
		model.addAttribute("page", page);
		model.addAttribute("unit",affairBasicKnowledge.getUnit());
		model.addAttribute("unitId",affairBasicKnowledge.getUnitId());
		return "modules/affair/affairBasicKnowledgeList";
	}

	@RequiresPermissions("affair:affairBasicKnowledge:view")
	@RequestMapping(value = "form")
	public String form(AffairBasicKnowledge affairBasicKnowledge, Model model) {
		model.addAttribute("affairBasicKnowledge", affairBasicKnowledge);
		return "modules/affair/affairBasicKnowledgeForm";
	}

	@RequiresPermissions("affair:affairBasicKnowledge:edit")
	@RequestMapping(value = "save")
	public String save(AffairBasicKnowledge affairBasicKnowledge, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairBasicKnowledge)){
			return form(affairBasicKnowledge, model);
		}
		affairBasicKnowledgeService.save(affairBasicKnowledge);
		addMessage(redirectAttributes, "保存基本知识成绩成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairBasicKnowledgeForm";
	}
	
	@RequiresPermissions("affair:affairBasicKnowledge:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairBasicKnowledge affairBasicKnowledge, RedirectAttributes redirectAttributes) {
		affairBasicKnowledgeService.delete(affairBasicKnowledge);
		addMessage(redirectAttributes, "删除基本知识成绩成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairBasicKnowledge/list/?repage";
	}

	@RequiresPermissions("affair:affairBasicKnowledge:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairBasicKnowledge affairBasicKnowledge, Model model) {
		model.addAttribute("affairBasicKnowledge", affairBasicKnowledge);
		return "modules/affair/affairBasicKnowledgeFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairBasicKnowledge:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairBasicKnowledgeService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairBasicKnowledge affairBasicKnowledge, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairBasicKnowledge> page = null;
			if(flag == true){
				page = affairBasicKnowledgeService.findPage(new Page<AffairBasicKnowledge>(request, response), affairBasicKnowledge);
			}else{
				page = affairBasicKnowledgeService.findPage(new Page<AffairBasicKnowledge>(request, response,-1), affairBasicKnowledge);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairBasicKnowledge.class);
			exportExcelNew.setWb(wb);
			List<AffairBasicKnowledge> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairBasicKnowledge/list/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairBasicKnowledge> list = ei.getDataList(AffairBasicKnowledge.class);
			for (AffairBasicKnowledge affairBasicKnowledge : list){
				try{
					BeanValidators.validateWithException(validator, affairBasicKnowledge);
					affairBasicKnowledge.setUnitId(officeService.findByName(affairBasicKnowledge.getUnit()));
					if (StringUtils.isNotBlank(affairBasicKnowledge.getUnitId())){
						affairBasicKnowledgeService.save(affairBasicKnowledge);
						successNum++;
					}
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

	@RequestMapping("basicKnoledgeDetail")
	public String basicKnoledgeDetail(AffairBasicKnowledge affairBasicKnowledge, HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("status",request.getParameter("status"));
		Page<AffairBasicKnowledge> page = affairBasicKnowledgeService.findBasicKnoledgePage(new Page<AffairBasicKnowledge>(request,response),affairBasicKnowledge);
		model.addAttribute("page", page);
		return "modules/charts/basicKnoledgeList";
	}

}