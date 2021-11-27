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
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStatistics;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliceStatisticsService;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStudyStatistics;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliceStudyStatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 民警学习统计报表Controller
 * @author kevin.jia
 * @version 2020-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPoliceStudyStatistics")
public class AffairPoliceStudyStatisticsController extends BaseController {

	@Autowired
	private AffairPoliceStudyStatisticsService affairPoliceStudyStatisticsService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairPoliceStatisticsService affairPoliceStatisticsService;
	
	@ModelAttribute
	public AffairPoliceStudyStatistics get(@RequestParam(required=false) String id) {
		AffairPoliceStudyStatistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPoliceStudyStatisticsService.get(id);
		}
		if (entity == null){
			entity = new AffairPoliceStudyStatistics();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPoliceStudyStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPoliceStudyStatistics affairPoliceStudyStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPoliceStudyStatistics> page = affairPoliceStudyStatisticsService.findPage(new Page<AffairPoliceStudyStatistics>(request, response), affairPoliceStudyStatistics); 
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceStudyStatisticsList";
	}

	@RequiresPermissions("affair:affairPoliceStudyStatistics:view")
	@RequestMapping(value = "form")
	public String form(AffairPoliceStudyStatistics affairPoliceStudyStatistics, Model model) {
		model.addAttribute("affairPoliceStudyStatistics", affairPoliceStudyStatistics);
		return "modules/affair/affairPoliceStudyStatisticsForm";
	}

	@RequiresPermissions("affair:affairPoliceStudyStatistics:edit")
	@RequestMapping(value = "save")
	public String save(AffairPoliceStudyStatistics affairPoliceStudyStatistics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairPoliceStudyStatistics)){
			return form(affairPoliceStudyStatistics, model);
		}
		affairPoliceStudyStatisticsService.save(affairPoliceStudyStatistics);
		addMessage(redirectAttributes, "保存民警学习统计报表成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairPoliceStudyStatisticsForm";
	}
	
	@RequiresPermissions("affair:affairPoliceStudyStatistics:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPoliceStudyStatistics affairPoliceStudyStatistics, RedirectAttributes redirectAttributes) {
		affairPoliceStudyStatisticsService.delete(affairPoliceStudyStatistics);
		addMessage(redirectAttributes, "删除民警学习统计报表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceStudyStatistics/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPoliceStudyStatistics:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPoliceStudyStatisticsService.deleteByIds(ids);
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
	 * @param affairPoliceStudyStatistics
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairPoliceStudyStatistics affairPoliceStudyStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairPoliceStudyStatistics.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairPoliceStudyStatistics> page = null;
			if(flag == true){
				page = affairPoliceStudyStatisticsService.findPage(new Page<AffairPoliceStudyStatistics>(request, response), affairPoliceStudyStatistics);
			}else {
				page = affairPoliceStudyStatisticsService.findPage(new Page<AffairPoliceStudyStatistics>(request, response,-1), affairPoliceStudyStatistics);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPoliceStudyStatistics.class);
			exportExcelNew.setWb(wb);
			List<AffairPoliceStudyStatistics> list =page.getList();
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
			addMessage(redirectAttributes, "导出民警学习统计信息失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affairPoliceStudyStatistics/?repage";
	}

	/*
	 * 导入
	 *
	 * */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPoliceStudyStatistics> list = ei.getDataList(AffairPoliceStudyStatistics.class);
			for (AffairPoliceStudyStatistics affairPoliceStudyStatistics : list) {
				try {

					BeanValidators.validateWithException(validator, affairPoliceStudyStatistics);
					if (!StringUtils.isEmpty(affairPoliceStudyStatistics.getUnitName())){
						String unitId = officeService.findByName(affairPoliceStudyStatistics.getUnitName());
						affairPoliceStudyStatistics.setUnitId(unitId);
					}
					affairPoliceStudyStatisticsService.save(affairPoliceStudyStatistics);
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

	/**
	 * 课程详情页面
	 * @param affairPoliceStudyStatistics
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"formDetail"})
	public String courseDetail(AffairPoliceStudyStatistics affairPoliceStudyStatistics, Model model){
		List<AffairPoliceStatistics> list = affairPoliceStatisticsService.findByPoliceIdNumber(affairPoliceStudyStatistics);
		model.addAttribute("list",list);
		return "modules/affair/affairPoliceStudyStatisticsFormDetail";
	}

}