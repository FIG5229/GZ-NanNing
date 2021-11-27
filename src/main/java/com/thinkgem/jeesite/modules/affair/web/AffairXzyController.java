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
import com.thinkgem.jeesite.modules.affair.entity.AffairXzy;
import com.thinkgem.jeesite.modules.affair.service.AffairXzyService;
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
import java.util.Map;

/**
 * 小种养Controller
 * @author cecil.li
 * @version 2019-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairXzy")
public class AffairXzyController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairXzyService affairXzyService;
	
	@ModelAttribute
	public AffairXzy get(@RequestParam(required=false) String id) {
		AffairXzy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairXzyService.get(id);
		}
		if (entity == null){
			entity = new AffairXzy();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairXzy:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairXzy affairXzy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairXzy> page = affairXzyService.findPage(new Page<AffairXzy>(request, response), affairXzy); 
		model.addAttribute("page", page);
		return "modules/affair/affairXzyList";
	}

	@RequiresPermissions("affair:affairXzy:view")
	@RequestMapping(value = "form")
	public String form(AffairXzy affairXzy, Model model) {
		model.addAttribute("affairXzy", affairXzy);
		return "modules/affair/affairXzyForm";
	}

	@RequiresPermissions("affair:affairXzy:edit")
	@RequestMapping(value = "save")
	public String save(AffairXzy affairXzy, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairXzy)){
			return form(affairXzy, model);
		}
		affairXzyService.save(affairXzy);
		addMessage(redirectAttributes, "保存小种养成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairXzyForm";
	}
	
	@RequiresPermissions("affair:affairXzy:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairXzy affairXzy, RedirectAttributes redirectAttributes) {
		affairXzyService.delete(affairXzy);
		addMessage(redirectAttributes, "删除小种养成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairXzy/?repage";
	}
	/**
	 * 详情
	 * @param affairXzy
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairXzy:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairXzy affairXzy, Model model) {
		model.addAttribute("affairXzy", affairXzy);
		if(affairXzy.getFilePath() != null && affairXzy.getFilePath().length() > 0){
			List<Map<String, String>> materialPathList = uploadController.filePathHandle(affairXzy.getFilePath());
			model.addAttribute("filePathList",materialPathList );
		}
		return "modules/affair/affairXzyFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairXzy:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairXzyService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairXzy affairXzy, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairXzy> page = null;
			if(flag == true){
				page = affairXzyService.findPage(new Page<AffairXzy>(request, response), affairXzy);
			}else {
				page = affairXzyService.findPage(new Page<AffairXzy>(request, response,-1), affairXzy);
			}
/*
			Page<AffairThreeOne> page = affairThreeOneService.findPage(new Page<AffairThreeOne>(request, response,-1), affairThreeOne);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairXzy.class);
			exportExcelNew.setWb(wb);
			List<AffairXzy> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairXzy/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 2, 0);
			//修改
			List<AffairXzy> list = ei.getDataList(AffairXzy.class);
			for (AffairXzy affairXzy : list){
				try{
					if (affairXzy.getUnit()!=null&&!"".equals(affairXzy.getUnit())) {
						affairXzy.setUnitId(officeService.findByName(affairXzy.getUnit()));
					}
					BeanValidators.validateWithException(validator, affairXzy);
					affairXzyService.save(affairXzy);
					successNum++;

				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(单位:"+affairXzy.getUnit()+")"+" 导入失败："+ex.getMessage());
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