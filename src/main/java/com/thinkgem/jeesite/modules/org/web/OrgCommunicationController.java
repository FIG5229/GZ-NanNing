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
import com.thinkgem.jeesite.modules.org.entity.OrgCommunication;
import com.thinkgem.jeesite.modules.org.service.OrgCommunicationService;
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
 * 单位通讯信息Controller
 * @author eav.liu
 * @version 2019-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/org/orgCommunication")
public class OrgCommunicationController extends BaseController {

	@Autowired
	private OrgCommunicationService orgCommunicationService;
	
	@ModelAttribute
	public OrgCommunication get(@RequestParam(required=false) String id) {
		OrgCommunication entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orgCommunicationService.get(id);
		}
		if (entity == null){
			entity = new OrgCommunication();
		}
		return entity;
	}
	
	@RequiresPermissions("org:orgCommunication:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrgCommunication orgCommunication, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrgCommunication> page = new Page<> ();
		if(StringUtils.isNotBlank(orgCommunication.getOrgId())){
			page = orgCommunicationService.findPage(new Page<OrgCommunication>(request, response), orgCommunication);
		}
		model.addAttribute("page", page);
		return "modules/org/orgCommunicationList";
	}

	@RequiresPermissions("org:orgCommunication:view")
	@RequestMapping(value = "form")
	public String form(OrgCommunication orgCommunication, Model model) {
		model.addAttribute("orgCommunication", orgCommunication);
		return "modules/org/orgCommunicationForm";
	}

	@RequiresPermissions("org:orgCommunication:edit")
	@RequestMapping(value = "save")
	public String save(OrgCommunication orgCommunication, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, orgCommunication)){
			return form(orgCommunication, model);
		}
		orgCommunicationService.save(orgCommunication);
		addMessage(redirectAttributes, "保存单位通讯信息成功");
		request.setAttribute("saveResult","sucess");
		return "modules/org/orgCommunicationForm";
	}
	
	@RequiresPermissions("org:orgCommunication:edit")
	@RequestMapping(value = "delete")
	public String delete(OrgCommunication orgCommunication, RedirectAttributes redirectAttributes) {
		orgCommunicationService.delete(orgCommunication);
		addMessage(redirectAttributes, "删除单位通讯信息成功");
		return "redirect:"+Global.getAdminPath()+"/org/orgCommunication/?orgId="+orgCommunication.getOrgId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("org:orgCommunication:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			orgCommunicationService.deleteByIds(ids);
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
	 * @param orgCommunication
	 * @param model
	 * @return
	 */
	@RequiresPermissions("org:orgCommunication:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(OrgCommunication orgCommunication, Model model) {
		model.addAttribute("orgCommunication", orgCommunication);
		return "modules/org/orgCommunicationFormDetail";
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
			List<OrgCommunication> list = ei.getDataList(OrgCommunication.class);
			for (OrgCommunication orgCommunication : list){
				try{
					orgCommunication.setOrgId(orgId);
					BeanValidators.validateWithException(validator, orgCommunication);
					orgCommunicationService.save(orgCommunication);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("导入失败："+ex.getMessage());
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
	public String exportExcelByTemplate(OrgCommunication orgCommunication, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<OrgCommunication> page = null;
			if(flag == true){
				page = orgCommunicationService.findPage(new Page<OrgCommunication>(request, response,-1), orgCommunication);
			}else{
				page = orgCommunicationService.findPage(new Page<OrgCommunication>(request, response,-1), orgCommunication);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, OrgCommunication.class);
			exportExcelNew.setWb(wb);
			List<OrgCommunication> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/orgCommunication?repage";
	}
}