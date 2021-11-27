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
import com.thinkgem.jeesite.modules.affair.entity.AffairAssess;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.entity.AffairInstructorLibrary;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairAdminStatistics;
import com.thinkgem.jeesite.modules.affair.service.AffairAdminStatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 管理员信息统计Controller
 * @author alan.wu
 * @version 2020-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAdminStatistics")
public class AffairAdminStatisticsController extends BaseController {

	@Autowired
	private AffairAdminStatisticsService affairAdminStatisticsService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairAdminStatistics get(@RequestParam(required=false) String id) {
		AffairAdminStatistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairAdminStatisticsService.get(id);
		}
		if (entity == null){
			entity = new AffairAdminStatistics();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairAdminStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairAdminStatistics affairAdminStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairAdminStatistics> page = affairAdminStatisticsService.findPage(new Page<AffairAdminStatistics>(request, response), affairAdminStatistics); 
		model.addAttribute("page", page);
		return "modules/affair/affairAdminStatisticsList";
	}

	@RequiresPermissions("affair:affairAdminStatistics:view")
	@RequestMapping(value = "form")
	public String form(AffairAdminStatistics affairAdminStatistics, Model model) {
		model.addAttribute("affairAdminStatistics", affairAdminStatistics);
		return "modules/affair/affairAdminStatisticsForm";
	}

	@RequiresPermissions("affair:affairAdminStatistics:edit")
	@RequestMapping(value = "save")
	public String save(AffairAdminStatistics affairAdminStatistics, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairAdminStatistics)){
			return form(affairAdminStatistics, model);
		}
		affairAdminStatisticsService.save(affairAdminStatistics);
		model.addAttribute("saveResult", "sucess");
		return "modules/affair/affairAdminStatisticsForm";
	}
	
	@RequiresPermissions("affair:affairAdminStatistics:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairAdminStatistics affairAdminStatistics, RedirectAttributes redirectAttributes) {
		affairAdminStatisticsService.delete(affairAdminStatistics);
		addMessage(redirectAttributes, "删除管理员信息统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairAdminStatistics/?repage";
	}


	@ResponseBody
	@RequiresPermissions("affair:affairAdminStatistics:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairAdminStatisticsService.deleteByIds(ids);
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
	 *
	 * @param affairAdminStatistics
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairAdminStatistics:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairAdminStatistics affairAdminStatistics, Model model) {
		model.addAttribute("affairAdminStatistics", affairAdminStatistics);
		return "modules/affair/affairAdminStatisticsFormDetail";
	}


	/**
	 * 导出excel格式数据
	 * @param affairAdminStatistics
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairAdminStatistics affairAdminStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairAdminStatistics.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairAdminStatistics> page = null;
			if (flag == true) {
				page = affairAdminStatisticsService.findPage(new Page<AffairAdminStatistics>(request, response), affairAdminStatistics);
			} else {
				page = affairAdminStatisticsService.findPage(new Page<AffairAdminStatistics>(request, response, -1), affairAdminStatistics);
			}

			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream) {
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairAdminStatistics.class);
			exportExcelNew.setWb(wb);
			List<AffairAdminStatistics> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/affair/affairAdminStatistics?repage";
	}



	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairAdminStatistics> list = ei.getDataList(AffairAdminStatistics.class);
			for (AffairAdminStatistics affairAdminStatistics : list) {
				try {

					BeanValidators.validateWithException(validator, affairAdminStatistics);

					affairAdminStatisticsService.save(affairAdminStatistics);
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





}