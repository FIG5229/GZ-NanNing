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
import com.thinkgem.jeesite.modules.affair.entity.AffairTraining;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainingManage;
import com.thinkgem.jeesite.modules.affair.service.AffairTrainingManageService; 
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
 * 练兵比武Controller
 * @author alan.wu
 * @version 2020-07-15
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTrainingManage")
public class AffairTrainingManageController extends BaseController {

	@Autowired
	private AffairTrainingManageService affairTrainingManageService;
	
	@ModelAttribute
	public AffairTrainingManage get(@RequestParam(required=false) String id) {
		AffairTrainingManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTrainingManageService.get(id);
		}
		if (entity == null){
			entity = new AffairTrainingManage();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:AffairTrainingManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTrainingManage affairTrainingManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTrainingManage> page = affairTrainingManageService.findPage(new Page<AffairTrainingManage>(request, response), affairTrainingManage);
		model.addAttribute("page", page);
		return "modules/affair/affairTrainingManageList";
	}

	@RequiresPermissions("affair:AffairTrainingManage:view")
	@RequestMapping(value = "form")
	public String form(AffairTrainingManage affairTrainingManage, Model model) {
		model.addAttribute("affairTrainingManage", affairTrainingManage);
		return "modules/affair/affairTrainingManageForm";
	}

	@RequiresPermissions("affair:affairTrainingManage:edit")
	@RequestMapping(value = "save")
	public String save(AffairTrainingManage affairTrainingManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTrainingManage)){
			return form(affairTrainingManage, model);
		}
		affairTrainingManageService.save(affairTrainingManage);
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairTrainingManageForm";
	}
	
	@RequiresPermissions("affair:affairTrainingManage:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTrainingManage affairTrainingManage, RedirectAttributes redirectAttributes) {
		affairTrainingManageService.delete(affairTrainingManage);
		addMessage(redirectAttributes, "删除成绩管理员练兵比武成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTrainingManage/?repage";
	}

	/**
	 * 详情
	 *
	 * @param affairTrainingManage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTrainingManage:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTrainingManage affairTrainingManage, Model model) {
		model.addAttribute("affairTrainingManage", affairTrainingManage);
		return "modules/affair/affairTrainingManageFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTrainingManage:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTrainingManageService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTrainingManage affairTrainingManage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			affairTrainingManage.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairTrainingManage> page = null;
			if(flag == true){
				page = affairTrainingManageService.findPage(new Page<AffairTrainingManage>(request, response), affairTrainingManage);
			}else {
				page = affairTrainingManageService.findPage(new Page<AffairTrainingManage>(request, response,-1), affairTrainingManage);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTraining.class);
			exportExcelNew.setWb(wb);
			List<AffairTrainingManage> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTrainingManage?repage";
	}


	//导入
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTrainingManage> list = ei.getDataList(AffairTrainingManage.class);
			for (AffairTrainingManage affairTrainingManage : list){
				try{

					BeanValidators.validateWithException(validator, affairTrainingManage);
					affairTrainingManageService.save(affairTrainingManage);
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