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
import com.thinkgem.jeesite.modules.affair.entity.AffairGroupManagement;
import com.thinkgem.jeesite.modules.affair.service.AffairGroupManagementService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysOfficesService;
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
 * 团费收缴Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGroupManagement")
public class AffairGroupManagementController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private SysOfficesService sysOfficesService;

	@Autowired
	private AffairGroupManagementService affairGroupManagementService;
	
	@ModelAttribute
	public AffairGroupManagement get(@RequestParam(required=false) String id) {
		AffairGroupManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGroupManagementService.get(id);
		}
		if (entity == null){
			entity = new AffairGroupManagement();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairGroupManagement:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairGroupManagement affairGroupManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairGroupManagement> page = affairGroupManagementService.findPage(new Page<AffairGroupManagement>(request, response), affairGroupManagement); 
		model.addAttribute("page", page);
		return "modules/affair/affairGroupManagementList";
	}

	@RequiresPermissions("affair:affairGroupManagement:view")
	@RequestMapping(value = "form")
	public String form(AffairGroupManagement affairGroupManagement, Model model) {
		model.addAttribute("affairGroupManagement", affairGroupManagement);
		return "modules/affair/affairGroupManagementForm";
	}

//	@RequiresPermissions("affair:affairGroupManagement:view")
	@RequestMapping(value = "save")
	public String save(AffairGroupManagement affairGroupManagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairGroupManagement)){
			return form(affairGroupManagement, model);
		}
		affairGroupManagementService.save(affairGroupManagement);
		addMessage(redirectAttributes, "保存团费收缴成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairGroupManagementForm";
	}
	
	@RequiresPermissions("affair:affairGroupManagement:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGroupManagement affairGroupManagement, RedirectAttributes redirectAttributes) {
		affairGroupManagementService.delete(affairGroupManagement);
		addMessage(redirectAttributes, "删除团费收缴成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGroupManagement/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairGroupFee:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGroupManagementService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairGroupManagement affairGroupManagement, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairGroupManagement> page = null;
			if(flag == true){
				page = affairGroupManagementService.findPage(new Page<AffairGroupManagement>(request, response), affairGroupManagement);
			}else {
				page = affairGroupManagementService.findPage(new Page<AffairGroupManagement>(request, response,-1), affairGroupManagement);
			}
/*
			Page<AffairGroupManagement> page = affairGroupManagementService.findPage(new Page<AffairGroupManagement>(request, response,-1), affairGroupManagement);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGroupManagement.class);
			exportExcelNew.setWb(wb);
			List<AffairGroupManagement> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairGroupManagement/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairGroupManagement> list = ei.getDataList(AffairGroupManagement.class);
			for (AffairGroupManagement affairGroupManagement : list){
				try{
					if (affairGroupManagement.getUnit()!=null&&!"".equals(affairGroupManagement.getUnit())){
						affairGroupManagement.setUnitId(officeService.findByName(affairGroupManagement.getUnit()));
					}
					if (affairGroupManagement.getGroup1()!=null&&!"".equals(affairGroupManagement.getGroup1())){
						affairGroupManagement.setGroupId(sysOfficesService.findByGroupName(affairGroupManagement.getGroup1()));
					}
					BeanValidators.validateWithException(validator, affairGroupManagement);
					affairGroupManagementService.save(affairGroupManagement);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(缴费人身份证号码:"+affairGroupManagement.getPayerNum()+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping("feeDetail")
	public String feeDetail(AffairGroupManagement groupManagement,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairGroupManagement> page = affairGroupManagementService.findFeeDetailPage(new Page<AffairGroupManagement>(request,response),groupManagement);
		model.addAttribute("page", page);
		return "modules/charts/chartGroupManagementList";
	}

}