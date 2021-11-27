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
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairInstructorSpecStatistics;
import com.thinkgem.jeesite.modules.affair.service.AffairInstructorSpecStatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 教官特长统计报表Controller
 * @author kevin.jia
 * @version 2020-08-10
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairInstructorSpecStatistics")
public class AffairInstructorSpecStatisticsController extends BaseController {

	@Autowired
	private AffairInstructorSpecStatisticsService affairInstructorSpecStatisticsService;

	@Autowired
	OfficeService officeService;
	
	@ModelAttribute
	public AffairInstructorSpecStatistics get(@RequestParam(required=false) String id) {
		AffairInstructorSpecStatistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairInstructorSpecStatisticsService.get(id);
		}
		if (entity == null){
			entity = new AffairInstructorSpecStatistics();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairInstructorSpecStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairInstructorSpecStatistics affairInstructorSpecStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairInstructorSpecStatistics> page = affairInstructorSpecStatisticsService.findPage(new Page<AffairInstructorSpecStatistics>(request, response), affairInstructorSpecStatistics); 
		model.addAttribute("page", page);
		return "modules/affair/affairInstructorSpecStatisticsList";
	}

	@RequiresPermissions("affair:affairInstructorSpecStatistics:view")
	@RequestMapping(value = "form")
	public String form(AffairInstructorSpecStatistics affairInstructorSpecStatistics, Model model) {
		model.addAttribute("affairInstructorSpecStatistics", affairInstructorSpecStatistics);
		return "modules/affair/affairInstructorSpecStatisticsForm";
	}

	@RequiresPermissions("affair:affairInstructorSpecStatistics:edit")
	@RequestMapping(value = "save")
	public String save(AffairInstructorSpecStatistics affairInstructorSpecStatistics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairInstructorSpecStatistics)){
			return form(affairInstructorSpecStatistics, model);
		}
		affairInstructorSpecStatisticsService.save(affairInstructorSpecStatistics);
		addMessage(redirectAttributes, "保存教官特长统计报表成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairInstructorSpecStatisticsForm";
	}
	
	@RequiresPermissions("affair:affairInstructorSpecStatistics:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairInstructorSpecStatistics affairInstructorSpecStatistics, RedirectAttributes redirectAttributes) {
		affairInstructorSpecStatisticsService.delete(affairInstructorSpecStatistics);
		addMessage(redirectAttributes, "删除教官特长统计报表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairInstructorSpecStatistics/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairInstructorSpecStatistics:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairInstructorSpecStatisticsService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairInstructorSpecStatistics affairInstructorSpecStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			affairInstructorSpecStatistics.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairInstructorSpecStatistics> page = null;
			if(flag == true){
				page = affairInstructorSpecStatisticsService.findPage(new Page<AffairInstructorSpecStatistics>(request, response), affairInstructorSpecStatistics);
			}else {
				page = affairInstructorSpecStatisticsService.findPage(new Page<AffairInstructorSpecStatistics>(request, response,-1), affairInstructorSpecStatistics);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairInstructorSpecStatistics.class);
			exportExcelNew.setWb(wb);
			List<AffairInstructorSpecStatistics> list =page.getList();
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
			addMessage(redirectAttributes, "导出教官特长统计报表失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairInstructorSpecStatistics?repage";
	}


	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairInstructorSpecStatistics:edit")
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairInstructorSpecStatistics> list = ei.getDataList(AffairInstructorSpecStatistics.class);
			for (AffairInstructorSpecStatistics affairInstructorSpecStatistics : list){
				try{

					BeanValidators.validateWithException(validator, affairInstructorSpecStatistics);
					if (!StringUtils.isEmpty(affairInstructorSpecStatistics.getUnitName())){
						String unitId = officeService.findByName(affairInstructorSpecStatistics.getUnitName());
						affairInstructorSpecStatistics.setUnitId(unitId);
					}
					affairInstructorSpecStatisticsService.save(affairInstructorSpecStatistics);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(教官姓名为:"+affairInstructorSpecStatistics.getName()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/download/view?repage";
	}
}