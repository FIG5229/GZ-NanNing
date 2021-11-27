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
import com.thinkgem.jeesite.common.web.UploadController;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairHomeVisit;
import com.thinkgem.jeesite.modules.affair.service.AffairHomeVisitService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 心谈家访Controller
 * @author daniel.liu
 * @version 2020-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairHomeVisit")
public class AffairHomeVisitController extends BaseController {

	@Autowired
	private AffairHomeVisitService affairHomeVisitService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairHomeVisit get(@RequestParam(required=false) String id) {
		AffairHomeVisit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairHomeVisitService.get(id);
		}
		if (entity == null){
			entity = new AffairHomeVisit();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairHomeVisit:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairHomeVisit affairHomeVisit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairHomeVisit> page = affairHomeVisitService.findPage(new Page<AffairHomeVisit>(request, response), affairHomeVisit); 
		model.addAttribute("page", page);
		return "modules/affair/affairHomeVisitList";
	}

	@RequiresPermissions("affair:affairHomeVisit:view")
	@RequestMapping(value = "form")
	public String form(AffairHomeVisit affairHomeVisit, Model model) {
		model.addAttribute("affairHomeVisit", affairHomeVisit);

		return "modules/affair/affairHomeVisitForm";
	}
	@RequiresPermissions("affair:affairHomeVisit:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairHomeVisit affairHomeVisit, Model model) {
		model.addAttribute("affairHomeVisit", affairHomeVisit);
		if(affairHomeVisit.getFilepath() != null && affairHomeVisit.getFilepath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairHomeVisit.getFilepath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairHomeVisitFormDetail";
	}

	@RequiresPermissions("affair:affairHomeVisit:edit")
	@RequestMapping(value = "save")
	public String save(AffairHomeVisit affairHomeVisit, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairHomeVisit)){
			return form(affairHomeVisit, model);
		}
		affairHomeVisitService.save(affairHomeVisit);
		addMessage(redirectAttributes, "保存心谈家访成功");

		request.setAttribute("saveResult","success");
		return "modules/affair/affairHomeVisitForm";
	}
	
	@RequiresPermissions("affair:affairHomeVisit:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairHomeVisit affairHomeVisit, RedirectAttributes redirectAttributes) {
		affairHomeVisitService.delete(affairHomeVisit);
		addMessage(redirectAttributes, "删除心谈家访成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairHomeVisit/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairHomeVisit:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairHomeVisitService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairHomeVisit homeVisit, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairHomeVisit> page = null;
			if(flag == true){
				page = affairHomeVisitService.findPage(new Page<AffairHomeVisit>(request, response), homeVisit);
			}else {
				page = affairHomeVisitService.findPage(new Page<AffairHomeVisit>(request, response,-1), homeVisit);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairHomeVisit.class);
			exportExcelNew.setWb(wb);
			List<AffairHomeVisit> list =page.getList();

			for (AffairHomeVisit affairHomeVisit:list){
				affairHomeVisit.setContent(StringUtils.replaceHtml(affairHomeVisit.getContent()));
			}
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
		return "redirect:" + adminPath + "/affair/AffairHomeVisit/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairHomeVisit> list = ei.getDataList(AffairHomeVisit.class);
			for (AffairHomeVisit homeVisit : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(homeVisit.getUnit());
					if(orgId != null){
						homeVisit.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, homeVisit);
					affairHomeVisitService.save(homeVisit);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(更新者:"+homeVisit.getUpdateBy().getId()+")"+" 导入失败："+ex.getMessage());
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