/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceCount;
import com.thinkgem.jeesite.modules.affair.service.AffairAttendanceCountService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

/**
 * 考勤数据统计Controller
 * @author cecil.li
 * @version 2020-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAttendanceCount")
public class AffairAttendanceCountController extends BaseController {

	@Autowired
	private AffairAttendanceCountService affairAttendanceCountService;
	
	@ModelAttribute
	public AffairAttendanceCount get(@RequestParam(required=false) String id) {
		AffairAttendanceCount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairAttendanceCountService.get(id);
		}
		if (entity == null){
			entity = new AffairAttendanceCount();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairAttendanceCount:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairAttendanceCount affairAttendanceCount, HttpServletRequest request, HttpServletResponse response, Model model) {

		Integer ye = affairAttendanceCount.getYearDate();
		if ("".equals(ye) || null == ye){

			Calendar calendar = Calendar.getInstance();
			 int year = calendar.get(Calendar.YEAR);
			 int month = calendar.get(Calendar.MONTH)+1;
			 affairAttendanceCount.setYearDate(year);
			 affairAttendanceCount.setDate(month);
		}
		Page<AffairAttendanceCount> page = affairAttendanceCountService.findPage(new Page<AffairAttendanceCount>(request, response,-1), affairAttendanceCount);
		model.addAttribute("page", page);
		return "modules/affair/affairAttendanceCountList";
	}

	@RequiresPermissions("affair:affairAttendanceCount:view")
	@RequestMapping(value = "form")
	public String form(AffairAttendanceCount affairAttendanceCount, Model model) {
		model.addAttribute("affairAttendanceCount", affairAttendanceCount);
		return "modules/affair/affairAttendanceCountForm";
	}

	@RequiresPermissions("affair:affairAttendanceCount:edit")
	@RequestMapping(value = "save")
	public String save(AffairAttendanceCount affairAttendanceCount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairAttendanceCount)){
			return form(affairAttendanceCount, model);
		}
		affairAttendanceCountService.save(affairAttendanceCount);
		addMessage(redirectAttributes, "保存考勤数据统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairAttendanceCount/?repage";
	}
	
	@RequiresPermissions("affair:affairAttendanceCount:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairAttendanceCount affairAttendanceCount, RedirectAttributes redirectAttributes) {
		affairAttendanceCountService.delete(affairAttendanceCount);
		addMessage(redirectAttributes, "删除考勤数据统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairAttendanceCount/?repage";
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
	public String exportExcelByTemplate(AffairAttendanceCount affairAttendanceCount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairAttendanceCount.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairAttendanceCount> page = null;
			if(flag == true){
				page = affairAttendanceCountService.findPage(new Page<AffairAttendanceCount>(request, response), affairAttendanceCount);
			}else{
				page = affairAttendanceCountService.findPage(new Page<AffairAttendanceCount>(request, response,-1), affairAttendanceCount);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairAttendanceCount.class);
			exportExcelNew.setWb(wb);
			List<AffairAttendanceCount> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairAttendanceCount/?repage";
	}

}