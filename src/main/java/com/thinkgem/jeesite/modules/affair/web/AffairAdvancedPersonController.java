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
import com.thinkgem.jeesite.modules.affair.entity.AffairBookCatalog;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairAdvancedPerson;
import com.thinkgem.jeesite.modules.affair.service.AffairAdvancedPersonService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 读书先进--个人Controller
 * @author alan.wu
 * @version 2020-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAdvancedPerson")
public class AffairAdvancedPersonController extends BaseController {

	@Autowired
	private AffairAdvancedPersonService affairAdvancedPersonService;
	
	@ModelAttribute
	public AffairAdvancedPerson get(@RequestParam(required=false) String id) {
		AffairAdvancedPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairAdvancedPersonService.get(id);
		}
		if (entity == null){
			entity = new AffairAdvancedPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairAdvancedPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairAdvancedPerson affairAdvancedPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairAdvancedPerson> page = affairAdvancedPersonService.findPage(new Page<AffairAdvancedPerson>(request, response), affairAdvancedPerson); 
		model.addAttribute("page", page);
		return "modules/affair/affairAdvancedPersonList";
	}

	@RequiresPermissions("affair:affairAdvancedPerson:view")
	@RequestMapping(value = "form")
	public String form(AffairAdvancedPerson affairAdvancedPerson, Model model) {
		model.addAttribute("affairAdvancedPerson", affairAdvancedPerson);
		return "modules/affair/affairAdvancedPersonForm";
	}

	@RequiresPermissions("affair:affairAdvancedPerson:edit")
	@RequestMapping(value = "save")
	public String save(AffairAdvancedPerson affairAdvancedPerson, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairAdvancedPerson)){
			return form(affairAdvancedPerson, model);
		}
		affairAdvancedPersonService.save(affairAdvancedPerson);
		addMessage(redirectAttributes, "保存读书先进--个人成功");
		request.setAttribute("saveResult", "sucess");
		return "modules/affair/affairAdvancedPersonForm";
	}
	
	@RequiresPermissions("affair:affairAdvancedPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairAdvancedPerson affairAdvancedPerson, RedirectAttributes redirectAttributes) {
		affairAdvancedPersonService.delete(affairAdvancedPerson);
		addMessage(redirectAttributes, "删除读书先进--个人成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairAdvancedPerson/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairAdvancedPerson:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairAdvancedPersonService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairAdvancedPerson:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairAdvancedPerson affairAdvancedPerson, Model model) {
		model.addAttribute("affairAdvancedPerson", affairAdvancedPerson);
		return "modules/affair/affairAdvancedPersonFormDetail";
	}


	/**
	 * 导出excel格式数据
	 * @param affairAdvancedPerson
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairAdvancedPerson affairAdvancedPerson, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairAdvancedPerson.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairAdvancedPerson> page = null;
			if (flag == true) {
				page = affairAdvancedPersonService.findPage(new Page<AffairAdvancedPerson>(request, response), affairAdvancedPerson);
			} else {
				page = affairAdvancedPersonService.findPage(new Page<AffairAdvancedPerson>(request, response, -1), affairAdvancedPerson);
			}

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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairAdvancedPerson.class);
			exportExcelNew.setWb(wb);
			List<AffairAdvancedPerson> list = page.getList();
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
		return "redirect:" + adminPath + "/affair/affairAdvancedPerson?repage";
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
			List<AffairAdvancedPerson> list = ei.getDataList(AffairAdvancedPerson.class);
			for (AffairAdvancedPerson affairAdvancedPerson : list){
				try{

					BeanValidators.validateWithException(validator, affairAdvancedPerson);
					affairAdvancedPersonService.save(affairAdvancedPerson);
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