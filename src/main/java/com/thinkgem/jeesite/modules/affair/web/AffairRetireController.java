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
import com.thinkgem.jeesite.modules.affair.entity.AffairRetire;
import com.thinkgem.jeesite.modules.affair.service.AffairRetireService;
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
 * 离退干部档案登记花名册Controller
 * @author mason.xv
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairRetire")
public class AffairRetireController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairRetireService affairRetireService;
	
	@ModelAttribute
	public AffairRetire get(@RequestParam(required=false) String id) {
		AffairRetire entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairRetireService.get(id);
		}
		if (entity == null){
			entity = new AffairRetire();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairRetire:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairRetire affairRetire, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairRetire> page = affairRetireService.findPage(new Page<AffairRetire>(request, response), affairRetire); 
		model.addAttribute("page", page);
		return "modules/affair/affairRetireList";
	}

		@RequiresPermissions("affair:affairRetire:view")
	@RequestMapping(value = "form")
	public String form(AffairRetire affairRetire, Model model) {
		model.addAttribute("affairRetire", affairRetire);
		return "modules/affair/affairRetireForm";
	}

	@RequiresPermissions("affair:affairRetire:edit")
	@RequestMapping(value = "save")
	public String save(AffairRetire affairRetire, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairRetire)){
			return form(affairRetire, model);
		}
		affairRetireService.save(affairRetire);
		addMessage(redirectAttributes, "保存离退干部档案登记花名册成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairRetireForm";
	}
	
	@RequiresPermissions("affair:affairRetire:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairRetire affairRetire, RedirectAttributes redirectAttributes) {
		affairRetireService.delete(affairRetire);
		addMessage(redirectAttributes, "删除离退干部档案登记花名册成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairRetire/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairRetire:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairRetireService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairRetire:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairRetire affairRetire, Model model) {
		model.addAttribute("affairRetire", affairRetire);
		return "modules/affair/affairRetireFormDetail";
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
	public String exportExcelByTemplate(AffairRetire affairRetire, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairRetire.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairRetire> page = null;
			if(flag == true){
				page = affairRetireService.findPage(new Page<AffairRetire>(request, response), affairRetire);
			}else{
				page = affairRetireService.findPage(new Page<AffairRetire>(request, response,-1), affairRetire);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairRetire.class);
			exportExcelNew.setWb(wb);
			List<AffairRetire> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairRetire/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairRetire> list = ei.getDataList(AffairRetire.class);
			for (AffairRetire affairRetire : list){
				try{
					affairRetire.setUnitId(officeService.findByName(affairRetire.getUnit()));
					BeanValidators.validateWithException(validator, affairRetire);
					affairRetireService.save(affairRetire);
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