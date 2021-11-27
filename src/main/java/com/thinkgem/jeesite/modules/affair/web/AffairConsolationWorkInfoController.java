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
import com.thinkgem.jeesite.modules.affair.entity.AffairConsolationWorkInfo;
import com.thinkgem.jeesite.modules.affair.service.AffairConsolationWorkInfoService;
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
 * 慰问工作管理Controller
 * @author cecil.li
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairConsolationWorkInfo")
public class AffairConsolationWorkInfoController extends BaseController {

	@Autowired
	private AffairConsolationWorkInfoService affairConsolationWorkInfoService;
	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairConsolationWorkInfo get(@RequestParam(required=false) String id) {
		AffairConsolationWorkInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairConsolationWorkInfoService.get(id);
		}
		if (entity == null){
			entity = new AffairConsolationWorkInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairConsolationWorkInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairConsolationWorkInfo affairConsolationWorkInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairConsolationWorkInfo> page = affairConsolationWorkInfoService.findPage(new Page<AffairConsolationWorkInfo>(request, response), affairConsolationWorkInfo); 
		model.addAttribute("page", page);
		return "modules/affair/affairConsolationWorkInfoList";
	}

	@RequiresPermissions("affair:affairConsolationWorkInfo:view")
	@RequestMapping(value = "form")
	public String form(AffairConsolationWorkInfo affairConsolationWorkInfo, Model model) {
		model.addAttribute("affairConsolationWorkInfo", affairConsolationWorkInfo);
		return "modules/affair/affairConsolationWorkInfoForm";
	}

	@RequiresPermissions("affair:affairConsolationWorkInfo:edit")
	@RequestMapping(value = "save")
	public String save(AffairConsolationWorkInfo affairConsolationWorkInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairConsolationWorkInfo)){
			return form(affairConsolationWorkInfo, model);
		}
		affairConsolationWorkInfoService.save(affairConsolationWorkInfo);
		addMessage(redirectAttributes, "保存一般慰问成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairConsolationWorkInfoForm";
	}
	
	@RequiresPermissions("affair:affairConsolationWorkInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairConsolationWorkInfo affairConsolationWorkInfo, RedirectAttributes redirectAttributes) {
		affairConsolationWorkInfoService.delete(affairConsolationWorkInfo);
		addMessage(redirectAttributes, "删除一般慰问成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairConsolationWorkInfo/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairConsolationWorkInfo:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairConsolationWorkInfoService.deleteByIds(ids);
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
	 * @param affairConsolationWorkInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairConsolationWorkInfo:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairConsolationWorkInfo affairConsolationWorkInfo, Model model) {
		model.addAttribute("affairConsolationWorkInfo", affairConsolationWorkInfo);
		if(affairConsolationWorkInfo.getFilePath() != null && affairConsolationWorkInfo.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairConsolationWorkInfo.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairConsolationWorkInfoFormDetail";
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
	public String exportExcelByTemplate(AffairConsolationWorkInfo affairConsolationWorkInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairConsolationWorkInfo> page = null;
			if(flag == true){
				page = affairConsolationWorkInfoService.findPage(new Page<AffairConsolationWorkInfo>(request, response), affairConsolationWorkInfo);
			}else {
				page = affairConsolationWorkInfoService.findPage(new Page<AffairConsolationWorkInfo>(request, response,-1), affairConsolationWorkInfo);
			}
/*
			Page<AffairGz> page = affairGzService.findPage(new Page<AffairGz>(request, response,-1), affairGz);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairConsolationWorkInfo.class);
			exportExcelNew.setWb(wb);
			List<AffairConsolationWorkInfo> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairConsolationWorkInfo/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			//修改
			List<AffairConsolationWorkInfo> list = ei.getDataList(AffairConsolationWorkInfo.class);
			for (AffairConsolationWorkInfo affairConsolationWorkInfo : list){
				try{
					BeanValidators.validateWithException(validator, affairConsolationWorkInfo);
					affairConsolationWorkInfoService.save(affairConsolationWorkInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairConsolationWorkInfo.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping("sympathyDetail")
	public String sympathyDetail(AffairConsolationWorkInfo consolationWorkInfo,HttpServletResponse response,HttpServletRequest request,Model model){
		Page page = affairConsolationWorkInfoService.findSympathyDetailPage(new Page<AffairConsolationWorkInfo>(request,response),consolationWorkInfo);
		model.addAttribute("page", page);
		return "modules/charts/chartConsolationWorkInfoList";
	}
}