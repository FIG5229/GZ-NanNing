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
import com.thinkgem.jeesite.modules.affair.entity.AffairAdvancedPerson;
import com.thinkgem.jeesite.modules.affair.entity.AffairBookCatalog;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairAdvancedCollective;
import com.thinkgem.jeesite.modules.affair.service.AffairAdvancedCollectiveService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 读书先进--集体Controller
 * @author alan.wu
 * @version 2020-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAdvancedCollective")
public class AffairAdvancedCollectiveController extends BaseController {

	@Autowired
	private AffairAdvancedCollectiveService affairAdvancedCollectiveService;
	
	@ModelAttribute
	public AffairAdvancedCollective get(@RequestParam(required=false) String id) {
		AffairAdvancedCollective entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairAdvancedCollectiveService.get(id);
		}
		if (entity == null){
			entity = new AffairAdvancedCollective();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairAdvancedCollective:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairAdvancedCollective affairAdvancedCollective, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairAdvancedCollective> page = affairAdvancedCollectiveService.findPage(new Page<AffairAdvancedCollective>(request, response), affairAdvancedCollective); 
		model.addAttribute("page", page);
		return "modules/affair/affairAdvancedCollectiveList";
	}

	@RequiresPermissions("affair:affairAdvancedCollective:view")
	@RequestMapping(value = "form")
	public String form(AffairAdvancedCollective affairAdvancedCollective, Model model) {
		model.addAttribute("affairAdvancedCollective", affairAdvancedCollective);
		return "modules/affair/affairAdvancedCollectiveForm";
	}

	@RequiresPermissions("affair:affairAdvancedCollective:edit")
	@RequestMapping(value = "save")
	public String save(AffairAdvancedCollective affairAdvancedCollective, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairAdvancedCollective)){
			return form(affairAdvancedCollective, model);
		}
		affairAdvancedCollectiveService.save(affairAdvancedCollective);
		addMessage(redirectAttributes, "保存读书先进--集体成功");
		request.setAttribute("saveResult", "sucess");
		return "modules/affair/affairAdvancedCollectiveForm";
	}
	
	@RequiresPermissions("affair:affairAdvancedCollective:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairAdvancedCollective affairAdvancedCollective, RedirectAttributes redirectAttributes) {
		affairAdvancedCollectiveService.delete(affairAdvancedCollective);
		addMessage(redirectAttributes, "删除读书先进--集体成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairAdvancedCollective/?repage";
	}


	@RequiresPermissions("affair:affairAdvancedPerson:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairAdvancedCollective affairAdvancedCollective, Model model) {
		model.addAttribute("affairAdvancedCollective", affairAdvancedCollective);
		return "modules/affair/affairAdvancedCollectiveFormDetail";
	}


	/**
	 * 导出excel格式数据
	 * @param affairAdvancedCollective
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairAdvancedCollective affairAdvancedCollective, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairAdvancedCollective.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairAdvancedCollective> page = null;
			if (flag == true) {
				page = affairAdvancedCollectiveService.findPage(new Page<AffairAdvancedCollective>(request, response), affairAdvancedCollective);
			} else {
				page = affairAdvancedCollectiveService.findPage(new Page<AffairAdvancedCollective>(request, response, -1), affairAdvancedCollective);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairAdvancedCollective.class);
			exportExcelNew.setWb(wb);
			List<AffairAdvancedCollective> list = page.getList();
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
		return "redirect:" + adminPath + "/affair/affairAdvancedCollective?repage";
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
			List<AffairAdvancedCollective> list = ei.getDataList(AffairAdvancedCollective.class);
			for (AffairAdvancedCollective affairAdvancedCollective : list){
				try{

					BeanValidators.validateWithException(validator, affairAdvancedCollective);
					affairAdvancedCollectiveService.save(affairAdvancedCollective);
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