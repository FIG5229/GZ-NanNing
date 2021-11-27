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
import com.thinkgem.jeesite.modules.affair.entity.AffairCommercialInsurance;
import com.thinkgem.jeesite.modules.affair.service.AffairCommercialInsuranceService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 商业保险Controller
 * @author alan.wu
 * @version 2020-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCommercialInsurance")
public class AffairCommercialInsuranceController extends BaseController {

	@Autowired
	private AffairCommercialInsuranceService affairCommercialInsuranceService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairCommercialInsurance get(@RequestParam(required=false) String id) {
		AffairCommercialInsurance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCommercialInsuranceService.get(id);
		}
		if (entity == null){
			entity = new AffairCommercialInsurance();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCommercialInsurance:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCommercialInsurance affairCommercialInsurance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCommercialInsurance> page = affairCommercialInsuranceService.findPage(new Page<AffairCommercialInsurance>(request, response), affairCommercialInsurance); 
		model.addAttribute("page", page);
		return "modules/affair/affairCommercialInsuranceList";
	}

	@RequiresPermissions("affair:affairCommercialInsurance:view")
	@RequestMapping(value = "form")
	public String form(AffairCommercialInsurance affairCommercialInsurance, Model model) {
		model.addAttribute("affairCommercialInsurance", affairCommercialInsurance);
		return "modules/affair/affairCommercialInsuranceForm";
	}

	@RequiresPermissions("affair:affairCommercialInsurance:edit")
	@RequestMapping(value = "save")
	public String save(AffairCommercialInsurance affairCommercialInsurance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairCommercialInsurance)){
			return form(affairCommercialInsurance, model);
		}
		affairCommercialInsuranceService.save(affairCommercialInsurance);
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairCommercialInsuranceForm";
	}
	
	@RequiresPermissions("affair:affairCommercialInsurance:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCommercialInsurance affairCommercialInsurance, RedirectAttributes redirectAttributes) {
		affairCommercialInsuranceService.delete(affairCommercialInsurance);
		addMessage(redirectAttributes, "删除商业保险成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCommercialInsurance/?repage";
	}


	/**
	 * 详情
	 *
	 * @param affairCommercialInsurance
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairCommercialInsurance:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCommercialInsurance affairCommercialInsurance, Model model) {
		model.addAttribute("affairCommercialInsurance", affairCommercialInsurance);
		if (affairCommercialInsurance.getAdjunct() != null && affairCommercialInsurance.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairCommercialInsurance.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairCommercialInsuranceFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairCommercialInsurance:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCommercialInsuranceService.deleteByIds(ids);
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
	 *
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairCommercialInsurance affairCommercialInsurance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {
			affairCommercialInsurance.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairCommercialInsurance> page = null;
			if (flag == true) {
				page = affairCommercialInsuranceService.findPage(new Page<AffairCommercialInsurance>(request, response), affairCommercialInsurance);
			} else {
				page = affairCommercialInsuranceService.findPage(new Page<AffairCommercialInsurance>(request, response, -1), affairCommercialInsurance);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream) {
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairCommercialInsurance.class);
			exportExcelNew.setWb(wb);
			List<AffairCommercialInsurance> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/affair/affairCommercialInsurance?repage";
	}

	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairCommercialInsurance> list = ei.getDataList(AffairCommercialInsurance.class);
			for (AffairCommercialInsurance affairLearnPower : list) {
				try {

					BeanValidators.validateWithException(validator, affairLearnPower);
					affairCommercialInsuranceService.save(affairLearnPower);
					successNum++;
				} catch (ConstraintViolationException ex) {
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append(" 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result", "success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}


}