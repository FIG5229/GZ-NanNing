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
import com.thinkgem.jeesite.modules.sys.entity.Office;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairTraining;
import com.thinkgem.jeesite.modules.affair.service.AffairTrainingService;

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
@RequestMapping(value = "${adminPath}/affair/affairTraining")
public class AffairTrainingController extends BaseController {

	@Autowired
	private AffairTrainingService affairTrainingService;
	
	@ModelAttribute
	public AffairTraining get(@RequestParam(required=false) String id) {
		AffairTraining entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTrainingService.get(id);
		}
		if (entity == null){
			entity = new AffairTraining();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairTraining:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/affair/affairTrainingIndex";
	}
	
	@RequiresPermissions("affair:affairTraining:view")
	@RequestMapping(value = {"list"})
	public String list(AffairTraining affairTraining, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTraining> page = affairTrainingService.findPage(new Page<AffairTraining>(request, response), affairTraining); 
		model.addAttribute("page", page);
		model.addAttribute("unit",affairTraining.getUnit());
		model.addAttribute("unitId",affairTraining.getUnitId());
		return "modules/affair/affairTrainingList";
	}

	@RequiresPermissions("affair:affairTraining:view")
	@RequestMapping(value = "form")
	public String form(AffairTraining affairTraining, Model model) {
		model.addAttribute("affairTraining", affairTraining);
		return "modules/affair/affairTrainingForm";
	}

	@RequiresPermissions("affair:affairTraining:edit")
	@RequestMapping(value = "save")
	public String save(AffairTraining affairTraining, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTraining)){
			return form(affairTraining, model);
		}
		affairTrainingService.save(affairTraining);
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairTrainingForm";
	}
	
	@RequiresPermissions("affair:affairTraining:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTraining affairTraining, RedirectAttributes redirectAttributes,String unitId) {
		affairTrainingService.delete(affairTraining);
		addMessage(redirectAttributes, "删除练兵比武成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTraining/list?unitId="+unitId;
	}

	/**
	 * 详情
	 *
	 * @param affairTraining
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTeacherClass:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTraining affairTraining, Model model) {
		model.addAttribute("affairTraining", affairTraining);
		return "modules/affair/affairTrainingFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTraining:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTrainingService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTraining affairTraining, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			affairTraining.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairTraining> page = null;
			if(flag == true){
				page = affairTrainingService.findPage(new Page<AffairTraining>(request, response), affairTraining);
			}else {
				page = affairTrainingService.findPage(new Page<AffairTraining>(request, response,-1), affairTraining);
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
			List<AffairTraining> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTraining?repage";
	}


	//导入
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTraining> list = ei.getDataList(AffairTraining.class);
			for (AffairTraining affairTraining : list){
				try{

					BeanValidators.validateWithException(validator, affairTraining);
					affairTrainingService.save(affairTraining);
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