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
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceFlag;
import com.thinkgem.jeesite.modules.affair.service.AffairAttendanceFlagService;
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
 * 节假日Controller
 * @author cecil.li
 * @version 2020-06-30
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAttendanceFlag")
public class AffairAttendanceFlagController extends BaseController {

	@Autowired
	private AffairAttendanceFlagService affairAttendanceFlagService;
	
	@ModelAttribute
	public AffairAttendanceFlag get(@RequestParam(required=false) String id) {
		AffairAttendanceFlag entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairAttendanceFlagService.get(id);
		}
		if (entity == null){
			entity = new AffairAttendanceFlag();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairAttendanceFlag:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairAttendanceFlag affairAttendanceFlag, HttpServletRequest request, HttpServletResponse response, Model model) {
		Calendar calendar = Calendar.getInstance();
		if (affairAttendanceFlag.getYear() == null || "".equals(affairAttendanceFlag.getYear())) {
			affairAttendanceFlag.setYear(calendar.get(Calendar.YEAR));
		}
		Page<AffairAttendanceFlag> page = affairAttendanceFlagService.findPage(new Page<AffairAttendanceFlag>(request, response), affairAttendanceFlag); 
		model.addAttribute("page", page);
		return "modules/affair/affairAttendanceFlagList";
	}

	@RequiresPermissions("affair:affairAttendanceFlag:view")
	@RequestMapping(value = "form")
	public String form(AffairAttendanceFlag affairAttendanceFlag, Model model) {
		Calendar calendar = Calendar.getInstance();
		if (affairAttendanceFlag.getYear() == null || "".equals(affairAttendanceFlag.getYear())) {
			affairAttendanceFlag.setYear(calendar.get(Calendar.YEAR));
		}
		model.addAttribute("affairAttendanceFlag", affairAttendanceFlag);
		return "modules/affair/affairAttendanceFlagForm";
	}

	@RequiresPermissions("affair:affairAttendanceFlag:edit")
	@RequestMapping(value = "save")
	public String save(AffairAttendanceFlag affairAttendanceFlag, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairAttendanceFlag)){
			return form(affairAttendanceFlag, model);
		}
		affairAttendanceFlagService.save(affairAttendanceFlag);
		addMessage(redirectAttributes, "保存节假日成功");
		request.setAttribute("saveResult", "success");
		return "modules/affair/affairAttendanceFlagForm";
	}
	
	@RequiresPermissions("affair:affairAttendanceFlag:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairAttendanceFlag affairAttendanceFlag, RedirectAttributes redirectAttributes) {
		affairAttendanceFlagService.delete(affairAttendanceFlag);
		addMessage(redirectAttributes, "删除节假日成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairAttendanceFlag/?repage";
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairAttendanceFlag:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairAttendanceFlagService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
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
	public String exportExcelByTemplate(AffairAttendanceFlag affairAttendanceFlag, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairAttendanceFlag.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairAttendanceFlag> page = null;
			if(flag == true){
				page = affairAttendanceFlagService.findPage(new Page<AffairAttendanceFlag>(request, response), affairAttendanceFlag);
			}else{
				page = affairAttendanceFlagService.findPage(new Page<AffairAttendanceFlag>(request, response,-1), affairAttendanceFlag);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairAttendanceFlag.class);
			exportExcelNew.setWb(wb);
			List<AffairAttendanceFlag> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairAttendanceFlag/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairAttendanceFlag> list = ei.getDataList(AffairAttendanceFlag.class);
			for (AffairAttendanceFlag affairAttendanceFlag : list){
				try{
					BeanValidators.validateWithException(validator, affairAttendanceFlag);
					affairAttendanceFlagService.save(affairAttendanceFlag);
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