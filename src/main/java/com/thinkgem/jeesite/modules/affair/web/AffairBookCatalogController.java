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
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairBookCatalog;
import com.thinkgem.jeesite.modules.affair.service.AffairBookCatalogService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 历年书目Controller
 * @author alan.wu
 * @version 2020-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairBookCatalog")
public class AffairBookCatalogController extends BaseController {

	@Autowired
	private AffairBookCatalogService affairBookCatalogService;
	
	@ModelAttribute
	public AffairBookCatalog get(@RequestParam(required=false) String id) {
		AffairBookCatalog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairBookCatalogService.get(id);
		}
		if (entity == null){
			entity = new AffairBookCatalog();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairBookCatalog:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairBookCatalog affairBookCatalog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairBookCatalog> page = affairBookCatalogService.findPage(new Page<AffairBookCatalog>(request, response), affairBookCatalog); 
		model.addAttribute("page", page);
		return "modules/affair/affairBookCatalogList";
	}

	@RequiresPermissions("affair:affairBookCatalog:view")
	@RequestMapping(value = "form")
	public String form(AffairBookCatalog affairBookCatalog, Model model) {
		model.addAttribute("affairBookCatalog", affairBookCatalog);
		return "modules/affair/affairBookCatalogForm";
	}

	@RequiresPermissions("affair:affairBookCatalog:edit")
	@RequestMapping(value = "save")
	public String save(AffairBookCatalog affairBookCatalog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairBookCatalog)){
			return form(affairBookCatalog, model);
		}
		affairBookCatalogService.save(affairBookCatalog);
		model.addAttribute("saveResult", "sucess");
		return "modules/affair/affairBookCatalogForm";
	}
	
	@RequiresPermissions("affair:affairBookCatalog:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairBookCatalog affairBookCatalog, RedirectAttributes redirectAttributes) {
		affairBookCatalogService.delete(affairBookCatalog);
		addMessage(redirectAttributes, "删除历年书目成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairBookCatalog/?repage";
	}


	@ResponseBody
	@RequiresPermissions("affair:affairBookCatalog:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairBookCatalogService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairBookCatalog:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairBookCatalog affairBookCatalog, Model model) {
		model.addAttribute("affairBookCatalog", affairBookCatalog);
		return "modules/affair/affairBookCatalogFormDetail";
	}


	/**
	 * 导出excel格式数据
	 * @param affairBookCatalog
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairBookCatalog affairBookCatalog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairBookCatalog.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairBookCatalog> page = null;
			if (flag == true) {
				page = affairBookCatalogService.findPage(new Page<AffairBookCatalog>(request, response), affairBookCatalog);
			} else {
				page = affairBookCatalogService.findPage(new Page<AffairBookCatalog>(request, response, -1), affairBookCatalog);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairBookCatalog.class);
			exportExcelNew.setWb(wb);
			List<AffairBookCatalog> list = page.getList();
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
		return "redirect:" + adminPath + "/affair/AffairBookCatalog?repage";
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
			List<AffairBookCatalog> list = ei.getDataList(AffairBookCatalog.class);
			for (AffairBookCatalog affairBookCatalog : list){
				try{

					BeanValidators.validateWithException(validator, affairBookCatalog);
					affairBookCatalogService.save(affairBookCatalog);
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