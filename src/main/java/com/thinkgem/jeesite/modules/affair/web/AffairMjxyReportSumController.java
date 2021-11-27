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
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReport;
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReportSum;
import com.thinkgem.jeesite.modules.affair.service.AffairMjxyReportSumService;
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
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 民警休养汇总Controller
 * @author mason.xv
 * @version 2020-04-14
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairMjxyReportSum")
public class AffairMjxyReportSumController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairMjxyReportSumService affairMjxyReportSumService;
	
	@ModelAttribute
	public AffairMjxyReportSum get(@RequestParam(required=false) String id) {
		AffairMjxyReportSum entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairMjxyReportSumService.get(id);
		}
		if (entity == null){
			entity = new AffairMjxyReportSum();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairMjxyReportSum:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairMjxyReportSum affairMjxyReportSum, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairMjxyReportSum> page = affairMjxyReportSumService.findPage(new Page<AffairMjxyReportSum>(request, response), affairMjxyReportSum);
		model.addAttribute("page", page);
		return "modules/affair/affairMjxyReportSumList";
	}

	@RequiresPermissions("affair:affairMjxyReportSum:view")
	@RequestMapping(value = "form")
	public String form(AffairMjxyReportSum affairMjxyReportSum, Model model) {
		model.addAttribute("affairMjxyReportSum", affairMjxyReportSum);
		return "modules/affair/affairMjxyReportSumForm";
	}

	@RequiresPermissions("affair:affairMjxyReportSum:edit")
	@RequestMapping(value = "save")
	public String save(AffairMjxyReportSum affairMjxyReportSum, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairMjxyReportSum)){
			return form(affairMjxyReportSum, model);
		}
		affairMjxyReportSumService.save(affairMjxyReportSum);
        addMessage(redirectAttributes, "保存休养信息集成功");
        request.setAttribute("saveResult","success");
        return "modules/affair/affairMjxyReportSumForm";
	}
	
	@RequiresPermissions("affair:affairMjxyReportSum:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairMjxyReportSum affairMjxyReportSum, RedirectAttributes redirectAttributes) {
		affairMjxyReportSumService.delete(affairMjxyReportSum);
		addMessage(redirectAttributes, "删除民警休养汇总成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairMjxyReportSum/?repage";
	}

	/**
	 * 详情
	 * @param affairMjxyReportSum
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMjxyReportSum:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairMjxyReportSum affairMjxyReportSum, Model model) {
		model.addAttribute("affairMjxyReportSum", affairMjxyReportSum);
		return "modules/affair/affairMjxyReportSumFormDetail";
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
	public String exportExcelByTemplate(AffairMjxyReportSum affairMjxyReportSum, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			List<AffairMjxyReportSum> list = null;
			if(flag == true){
				list = affairMjxyReportSumService.findList(affairMjxyReportSum);
			}else {
				list = affairMjxyReportSumService.findList(affairMjxyReportSum);
			}
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			//修改
			ExportExcelNew exportExcelNew = new ExportExcelNew(4, AffairMjxyReportSum.class);
			exportExcelNew.setWb(wb);
			/*List<AffairMjxyReportSum> list =page.getList();*/
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
		//修改
		return "redirect:" + adminPath + "/affair/affairMjxyReportSum/?repage";
	}
/*	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairMjxyReportSum> list = ei.getDataList(AffairMjxyReportSum.class);
			for (AffairMjxyReportSum affairMjxyReportSum : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairMjxyReportSum.getUnit());
					if(orgId != null){
						affairMjxyReportSum.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, affairMjxyReportSum);
					affairMjxyReportSumService.save(affairMjxyReportSum);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairMjxyReportSum.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	}*/
}