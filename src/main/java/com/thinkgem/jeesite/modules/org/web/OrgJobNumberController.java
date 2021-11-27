/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.org.entity.OrgJobNumber;
import com.thinkgem.jeesite.modules.org.service.OrgJobNumberService;
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
 * 单位职数信息Controller
 * @author eav.liu
 * @version 2019-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/org/orgJobNumber")
public class OrgJobNumberController extends BaseController {

	@Autowired
	private OrgJobNumberService orgJobNumberService;
	
	@ModelAttribute
	public OrgJobNumber get(@RequestParam(required=false) String id) {
		OrgJobNumber entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orgJobNumberService.get(id);
		}
		if (entity == null){
			entity = new OrgJobNumber();
		}
		return entity;
	}
	
	@RequiresPermissions("org:orgJobNumber:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrgJobNumber orgJobNumber, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrgJobNumber> page = new Page<> ();
		if(StringUtils.isNotBlank(orgJobNumber.getOrgId())){
			page = orgJobNumberService.findPage(new Page<OrgJobNumber>(request, response), orgJobNumber);
		}
		model.addAttribute("page", page);
		return "modules/org/orgJobNumberList";
	}

	@RequiresPermissions("org:orgJobNumber:view")
	@RequestMapping(value = "form")
	public String form(OrgJobNumber orgJobNumber, Model model) {
		model.addAttribute("orgJobNumber", orgJobNumber);
		return "modules/org/orgJobNumberForm";
	}

	@RequiresPermissions("org:orgJobNumber:edit")
	@RequestMapping(value = "save")
	public String save(OrgJobNumber orgJobNumber, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, orgJobNumber)){
			return form(orgJobNumber, model);
		}
		orgJobNumberService.save(orgJobNumber);
		addMessage(redirectAttributes, "保存单位职数信息成功");
		request.setAttribute("saveResult","sucess");
		return "modules/org/orgJobNumberForm";
	}
	
	@RequiresPermissions("org:orgJobNumber:edit")
	@RequestMapping(value = "delete")
	public String delete(OrgJobNumber orgJobNumber, RedirectAttributes redirectAttributes) {
		orgJobNumberService.delete(orgJobNumber);
		addMessage(redirectAttributes, "删除单位职数信息成功");
		return "redirect:"+Global.getAdminPath()+"/org/orgJobNumber/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("org:orgJobNumber:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			orgJobNumberService.deleteByIds(ids);
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
	 * @param orgJobNumber
	 * @param model
	 * @return
	 */
	@RequiresPermissions("org:orgJobNumber:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(OrgJobNumber orgJobNumber, Model model) {
		model.addAttribute("orgJobNumber", orgJobNumber);
		return "modules/org/orgJobNumberFormDetail";
	}

	/**
	 * 导入excel
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, String orgId) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<OrgJobNumber> list = ei.getDataList(OrgJobNumber.class);
			for (OrgJobNumber orgJobNumber : list){
				try{
					orgJobNumber.setOrgId(orgId);
					BeanValidators.validateWithException(validator, orgJobNumber);
					orgJobNumberService.save(orgJobNumber);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(批准时间:"+orgJobNumber.getDate()+"的数据)"+" 导入失败："+ex.getMessage());
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

	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(OrgJobNumber orgJobNumber, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<OrgJobNumber> page = null;
			if(flag == true){
				page = orgJobNumberService.findPage(new Page<OrgJobNumber>(request, response,-1), orgJobNumber);
			}else{
				page = orgJobNumberService.findPage(new Page<OrgJobNumber>(request, response,-1), orgJobNumber);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, OrgJobNumber.class);
			exportExcelNew.setWb(wb);
			List<OrgJobNumber> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/orgJobNumber?repage";
	}
}