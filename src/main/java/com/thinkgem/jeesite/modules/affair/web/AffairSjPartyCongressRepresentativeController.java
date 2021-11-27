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
import com.thinkgem.jeesite.modules.affair.entity.AffairSjPartyCongressRepresentative;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairSjPartyCongressRepresentativeService;
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

/**
 * 上级党代会代表Controller
 * @author cecil.li
 * @version 2020-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSjPartyCongressRepresentative")
public class AffairSjPartyCongressRepresentativeController extends BaseController {

	@Autowired
	private AffairSjPartyCongressRepresentativeService affairSjPartyCongressRepresentativeService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;
	
	@ModelAttribute
	public AffairSjPartyCongressRepresentative get(@RequestParam(required=false) String id) {
		AffairSjPartyCongressRepresentative entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSjPartyCongressRepresentativeService.get(id);
		}
		if (entity == null){
			entity = new AffairSjPartyCongressRepresentative();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairSjPartyCongressRepresentative:view")
	@RequestMapping(value = {""})
	public String index(AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative) {
		return "modules/affair/affairSjPartyCongressRepresentativeIndex";
	}

	@RequiresPermissions("affair:affairSjPartyCongressRepresentative:view")
	@RequestMapping(value = {"list"})
	public String list(AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairSjPartyCongressRepresentative.getTreeId());
		Page<AffairSjPartyCongressRepresentative> page = affairSjPartyCongressRepresentativeService.findPage(new Page<AffairSjPartyCongressRepresentative>(request, response), affairSjPartyCongressRepresentative);
		model.addAttribute("page", page);
		return "modules/affair/affairSjPartyCongressRepresentativeList";
	}

	@RequiresPermissions("affair:affairSjPartyCongressRepresentative:view")
	@RequestMapping(value = "form")
	public String form(AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative, Model model) {
		model.addAttribute("affairSjPartyCongressRepresentative", affairSjPartyCongressRepresentative);
		return "modules/affair/affairSjPartyCongressRepresentativeForm";
	}

	@RequiresPermissions("affair:affairSjPartyCongressRepresentative:edit")
	@RequestMapping(value = "save")
	public String save(AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairSjPartyCongressRepresentative)){
			return form(affairSjPartyCongressRepresentative, model);
		}
		affairSjPartyCongressRepresentativeService.save(affairSjPartyCongressRepresentative);
		addMessage(redirectAttributes, "保存上级党代会代表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairSjPartyCongressRepresentativeForm";
	}
	
	@RequiresPermissions("affair:affairSjPartyCongressRepresentative:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative, RedirectAttributes redirectAttributes) {
		affairSjPartyCongressRepresentativeService.delete(affairSjPartyCongressRepresentative);
		addMessage(redirectAttributes, "删除上级党代会代表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSjPartyCongressRepresentative/list?repage&treeId="+affairSjPartyCongressRepresentative.getTreeId();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairSjPartyCongressRepresentative:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSjPartyCongressRepresentativeService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairSjPartyCongressRepresentative personnelPensionRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairSjPartyCongressRepresentative> page = null;
			if(flag == true){
				page = affairSjPartyCongressRepresentativeService.findPage(new Page<AffairSjPartyCongressRepresentative>(request, response), personnelPensionRecord);
			}else{
				page = affairSjPartyCongressRepresentativeService.findPage(new Page<AffairSjPartyCongressRepresentative>(request, response,-1), personnelPensionRecord);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairSjPartyCongressRepresentative.class);
			exportExcelNew.setWb(wb);
			List<AffairSjPartyCongressRepresentative> list =page.getList();
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
		return "redirect:"+Global.getAdminPath()+"/affair/affairSjPartyCongressRepresentative/list?repage&treeId="+personnelPensionRecord.getTreeId();
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairSjPartyCongressRepresentative> list = ei.getDataList(AffairSjPartyCongressRepresentative.class);
			for (AffairSjPartyCongressRepresentative personnelPensionRecord : list){
				try{
					BeanValidators.validateWithException(validator, personnelPensionRecord);
					//单位绑定单位id
					String orgId = affairGeneralSituationService.findByName(personnelPensionRecord.getUnit());
					personnelPensionRecord.setUnitId(orgId);
					affairSjPartyCongressRepresentativeService.save(personnelPensionRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(姓名:"+personnelPensionRecord.getName()+")"+" 导入失败："+ex.getMessage());
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