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
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairCjCompassionate;
import com.thinkgem.jeesite.modules.affair.service.AffairCjCompassionateService;
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
import java.util.Map;

/**
 * 参公人员抚恤Controller
 * @author cecil.li
 * @version 2020-07-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCjCompassionate")
public class AffairCjCompassionateController extends BaseController {

	@Autowired
	private AffairCjCompassionateService affairCjCompassionateService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairCjCompassionate get(@RequestParam(required=false) String id) {
		AffairCjCompassionate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCjCompassionateService.get(id);
		}
		if (entity == null){
			entity = new AffairCjCompassionate();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCjCompassionate:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCjCompassionate affairCjCompassionate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCjCompassionate> page = affairCjCompassionateService.findPage(new Page<AffairCjCompassionate>(request, response), affairCjCompassionate); 
		model.addAttribute("page", page);
		return "modules/affair/affairCjCompassionateList";
	}

	@RequiresPermissions("affair:affairCjCompassionate:view")
	@RequestMapping(value = "form")
	public String form(AffairCjCompassionate affairCjCompassionate, Model model) {
		model.addAttribute("affairCjCompassionate", affairCjCompassionate);
		return "modules/affair/affairCjCompassionateForm";
	}

	@RequiresPermissions("affair:affairCjCompassionate:edit")
	@RequestMapping(value = "save")
	public String save(AffairCjCompassionate affairCjCompassionate, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairCjCompassionate)){
			return form(affairCjCompassionate, model);
		}
		affairCjCompassionateService.save(affairCjCompassionate);
		addMessage(redirectAttributes, "保存参公人员抚恤成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairCjCompassionateForm";
	}
	
	@RequiresPermissions("affair:affairCjCompassionate:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCjCompassionate affairCjCompassionate, RedirectAttributes redirectAttributes) {
		affairCjCompassionateService.delete(affairCjCompassionate);
		addMessage(redirectAttributes, "删除参公人员抚恤成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCjCompassionate/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCjCompassionate:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCjCompassionateService.deleteByIds(ids);
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
	 * @param affairCjCompassionate
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairCjCompassionate:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCjCompassionate affairCjCompassionate, Model model) {
		model.addAttribute("affairCjCompassionate", affairCjCompassionate);
		if(affairCjCompassionate.getAnnex() != null && affairCjCompassionate.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairCjCompassionate.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairCjCompassionateFormDetail";
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
	public String exportExcelByTemplate(AffairCjCompassionate affairCjCompassionate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairCjCompassionate.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairCjCompassionate> page = null;
			if(flag == true){
				page = affairCjCompassionateService.findPage(new Page<AffairCjCompassionate>(request, response), affairCjCompassionate);
			}else{
				page = affairCjCompassionateService.findPage(new Page<AffairCjCompassionate>(request, response,-1), affairCjCompassionate);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairCjCompassionate.class);
			exportExcelNew.setWb(wb);
			List<AffairCjCompassionate> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairCjCompassionate/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairCjCompassionate> list = ei.getDataList(AffairCjCompassionate.class);
			for (AffairCjCompassionate affairCjCompassionate : list){
				try{
					BeanValidators.validateWithException(validator, affairCjCompassionate);
					affairCjCompassionateService.save(affairCjCompassionate);
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