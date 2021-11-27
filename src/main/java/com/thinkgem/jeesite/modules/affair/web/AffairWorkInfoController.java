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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkInfo;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkInfoService;
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
 * 工委工作信息Controller
 * @author cecil.li
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkInfo")
public class AffairWorkInfoController extends BaseController {

	@Autowired
	private AffairWorkInfoService affairWorkInfoService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairWorkInfo get(@RequestParam(required=false) String id) {
		AffairWorkInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkInfoService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWorkInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkInfo affairWorkInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWorkInfo> page = affairWorkInfoService.findPage(new Page<AffairWorkInfo>(request, response), affairWorkInfo); 
		model.addAttribute("page", page);
		return "modules/affair/affairWorkInfoList";
	}

	@RequiresPermissions("affair:affairWorkInfo:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkInfo affairWorkInfo, Model model) {
		model.addAttribute("affairWorkInfo", affairWorkInfo);
		return "modules/affair/affairWorkInfoForm";
	}

	@RequiresPermissions("affair:affairWorkInfo:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkInfo affairWorkInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairWorkInfo)){
			return form(affairWorkInfo, model);
		}
		affairWorkInfoService.save(affairWorkInfo);
		addMessage(redirectAttributes, "保存工委工作信息成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairWorkInfoForm";
	}
	
	@RequiresPermissions("affair:affairWorkInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkInfo affairWorkInfo, RedirectAttributes redirectAttributes) {
		affairWorkInfoService.delete(affairWorkInfo);
		addMessage(redirectAttributes, "删除工委工作信息成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkInfo/?repage";
	}

	/**
	 * 详情
	 * @param affairWorkInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkInfo:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkInfo affairWorkInfo, Model model) {
		model.addAttribute("affairWorkInfo", affairWorkInfo);
		if(affairWorkInfo.getAnnex() != null && affairWorkInfo.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkInfo.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkInfoFormDetail";
	}


	@ResponseBody
	@RequiresPermissions("affair:affairWorkInfo:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkInfoService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairWorkInfo affairWorkInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairWorkInfo> page = null;
			if(flag == true){
				page = affairWorkInfoService.findPage(new Page<AffairWorkInfo>(request, response), affairWorkInfo);
			}else{
				page = affairWorkInfoService.findPage(new Page<AffairWorkInfo>(request, response,-1), affairWorkInfo);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairWorkInfo.class);
			exportExcelNew.setWb(wb);
			List<AffairWorkInfo> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairWorkInfo/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairWorkInfo> list = ei.getDataList(AffairWorkInfo.class);
			for (AffairWorkInfo affairWorkInfo : list){
				try{
					BeanValidators.validateWithException(validator, affairWorkInfo);
					affairWorkInfoService.save(affairWorkInfo);
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