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
import com.thinkgem.jeesite.modules.affair.entity.AffairThemeActivity;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairThemeActivityService;
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
 * 党内主题实践活动Controller
 * @author eav.liu
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairThemeActivity")
public class AffairThemeActivityController extends BaseController {

	@Autowired
	private AffairThemeActivityService affairThemeActivityService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;
	
	@ModelAttribute
	public AffairThemeActivity get(@RequestParam(required=false) String id) {
		AffairThemeActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairThemeActivityService.get(id);
		}
		if (entity == null){
			entity = new AffairThemeActivity();
		}
		return entity;
	}

	/**
	 * 废弃
	 * @param affairThemeActivity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairThemeActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairThemeActivity affairThemeActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairThemeActivity> page = affairThemeActivityService.findPage(new Page<AffairThemeActivity>(request, response), affairThemeActivity); 
		model.addAttribute("page", page);
		return "modules/affair/affairThemeActivityList";
	}

	@RequiresPermissions("affair:affairThemeActivity:view")
	@RequestMapping(value = "form")
	public String form(AffairThemeActivity affairThemeActivity, Model model) {
		model.addAttribute("affairThemeActivity", affairThemeActivity);
		return "modules/affair/affairThemeActivityForm";
	}

	@RequiresPermissions("affair:affairThemeActivity:manage")
	@RequestMapping(value = "save")
	public String save(AffairThemeActivity affairThemeActivity, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairThemeActivity)){
			return form(affairThemeActivity, model);
		}
		affairThemeActivityService.save(affairThemeActivity);
		addMessage(redirectAttributes, "保存党内主题实践活动成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairThemeActivityForm";
	}
	
	@RequiresPermissions("affair:affairThemeActivity:manage")
	@RequestMapping(value = "delete")
	public String delete(AffairThemeActivity affairThemeActivity, RedirectAttributes redirectAttributes) {
		affairThemeActivityService.delete(affairThemeActivity);
		addMessage(redirectAttributes, "删除党内主题实践活动成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairThemeActivity/?repage";
	}

	/**
	 * 管理页面
	 * @param affairThemeActivity
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairThemeActivity:view")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairThemeActivity affairThemeActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairThemeActivity.getTreeId());
		Page<AffairThemeActivity> page = affairThemeActivityService.findPage(new Page<AffairThemeActivity>(request, response), affairThemeActivity);
		model.addAttribute("page", page);
		return "modules/affair/affairThemeActivityManage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairThemeActivity:manage")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairThemeActivityService.deleteByIds(ids);
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
	 * @param affairThemeActivity
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairThemeActivity:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairThemeActivity affairThemeActivity, Model model) {
		model.addAttribute("affairThemeActivity", affairThemeActivity);
		if (affairThemeActivity.getMaterialPath() != null && affairThemeActivity.getMaterialPath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairThemeActivity.getMaterialPath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairThemeActivityFormDetail";
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
	public String exportExcelByTemplate(AffairThemeActivity affairThemeActivity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairThemeActivity> page = null;
			if(flag == true){
				page = affairThemeActivityService.findPage(new Page<AffairThemeActivity>(request, response), affairThemeActivity);
			}else{
				page = affairThemeActivityService.findPage(new Page<AffairThemeActivity>(request, response,-1), affairThemeActivity);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairThemeActivity.class);
			exportExcelNew.setWb(wb);
			List<AffairThemeActivity> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairThemeActivity?repage";
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairThemeActivity> list = ei.getDataList(AffairThemeActivity.class);
			for (AffairThemeActivity affairThemeActivity : list){
				try{
					BeanValidators.validateWithException(validator, affairThemeActivity);
					//党支部要绑定党支部id
					String partyOrganizationId = affairGeneralSituationService.findByName(affairThemeActivity.getPartyOrganizationName());
					affairThemeActivity.setPartyOrganizationId(partyOrganizationId);
					//单位绑定举办单位id
					String orgId = officeService.findByName(affairThemeActivity.getHoldUnitName());
					affairThemeActivity.setHoldUnitId(orgId);
					affairThemeActivityService.save(affairThemeActivity);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairThemeActivity.getHoldUnitName()+"(活动名称:"+affairThemeActivity.getName()+")"+" 导入失败："+ex.getMessage());
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