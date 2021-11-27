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
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairOtherPartyRepresentative;
import com.thinkgem.jeesite.modules.affair.service.AffairOtherPartyRepresentativeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 其他党代表会Controller
 * @author daniel.liu
 * @version 2020-06-30
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOtherPartyRepresentative")
public class AffairOtherPartyRepresentativeController extends BaseController {

	@Autowired
	private AffairOtherPartyRepresentativeService affairOtherPartyRepresentativeService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;
	
	@ModelAttribute
	public AffairOtherPartyRepresentative get(@RequestParam(required=false) String id) {
		AffairOtherPartyRepresentative entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOtherPartyRepresentativeService.get(id);
		}
		if (entity == null){
			entity = new AffairOtherPartyRepresentative();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairOtherPartyRepresentative:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairOtherPartyRepresentative affairOtherPartyRepresentative, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairOtherPartyRepresentative.getTreeId());
		Page<AffairOtherPartyRepresentative> page = affairOtherPartyRepresentativeService.findPage(new Page<AffairOtherPartyRepresentative>(request, response), affairOtherPartyRepresentative); 
		model.addAttribute("page", page);
		return "modules/affair/affairOtherPartyRepresentativeList";
	}

	@RequiresPermissions("affair:affairOtherPartyRepresentative:view")
	@RequestMapping(value = "form")
	public String form(AffairOtherPartyRepresentative affairOtherPartyRepresentative, Model model) {
		model.addAttribute("affairOtherPartyRepresentative", affairOtherPartyRepresentative);
		return "modules/affair/affairOtherPartyRepresentativeForm";
	}

	@RequiresPermissions("affair:affairOtherPartyRepresentative:edit")
	@RequestMapping(value = "save")
	public String save(AffairOtherPartyRepresentative affairOtherPartyRepresentative, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairOtherPartyRepresentative)){
			return form(affairOtherPartyRepresentative, model);
		}
		affairOtherPartyRepresentativeService.save(affairOtherPartyRepresentative);
		request.setAttribute("saveResult","success");
		addMessage(redirectAttributes, "保存其他党代表会成功");
		return "modules/affair/affairOtherPartyRepresentativeForm";
	}
	
	@RequiresPermissions("affair:affairOtherPartyRepresentative:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOtherPartyRepresentative affairOtherPartyRepresentative, RedirectAttributes redirectAttributes) {
		affairOtherPartyRepresentativeService.delete(affairOtherPartyRepresentative);
		addMessage(redirectAttributes, "删除其他党代表会成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOtherPartyRepresentative/list?treeId="+affairOtherPartyRepresentative.getTreeId();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairOtherPartyRepresentative:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairOtherPartyRepresentativeService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairOtherPartyRepresentative personnelPensionRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairOtherPartyRepresentative> page = null;
			if(flag == true){
				page = affairOtherPartyRepresentativeService.findPage(new Page<AffairOtherPartyRepresentative>(request, response), personnelPensionRecord);
			}else{
				page = affairOtherPartyRepresentativeService.findPage(new Page<AffairOtherPartyRepresentative>(request, response,-1), personnelPensionRecord);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairOtherPartyRepresentative.class);
			exportExcelNew.setWb(wb);
			List<AffairOtherPartyRepresentative> list =page.getList();
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
		return "redirect:"+Global.getAdminPath()+"/affair/affairOtherPartyRepresentative/list?treeId="+personnelPensionRecord.getTreeId();
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairOtherPartyRepresentative> list = ei.getDataList(AffairOtherPartyRepresentative.class);
			for (AffairOtherPartyRepresentative personnelPensionRecord : list){
				try{
					BeanValidators.validateWithException(validator, personnelPensionRecord);
					//单位绑定单位id
					String orgId = affairGeneralSituationService.findByName(personnelPensionRecord.getUnit());
					personnelPensionRecord.setUnitId(orgId);
					affairOtherPartyRepresentativeService.save(personnelPensionRecord);
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

	@RequestMapping(value = "partyRepresentativeDetail")
	public String partyRepresentativeDetail(AffairOtherPartyRepresentative affairOtherPartyRepresentative,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairOtherPartyRepresentative> page = affairOtherPartyRepresentativeService.findRepresentativePage(new Page<AffairOtherPartyRepresentative>(request,response),affairOtherPartyRepresentative);
		model.addAttribute("page",page);
		return "modules/charts/chartPartyRepresentativeList";
	}
}