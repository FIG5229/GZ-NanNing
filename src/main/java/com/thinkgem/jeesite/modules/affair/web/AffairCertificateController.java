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
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificate;
import com.thinkgem.jeesite.modules.affair.service.AffairCertificateService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
 * 证书Controller
 * @author jack.xu
 * @version 2020-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCertificate")
public class AffairCertificateController extends BaseController {

	@Autowired
	private AffairCertificateService affairCertificateService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairCertificate get(@RequestParam(required=false) String id) {
		AffairCertificate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCertificateService.get(id);
		}
		if (entity == null){
			entity = new AffairCertificate();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCertificate:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCertificate affairCertificate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCertificate> page = affairCertificateService.findPage(new Page<AffairCertificate>(request, response), affairCertificate); 
		model.addAttribute("page", page);
		return "modules/affair/affairCertificateList";
	}

	@RequiresPermissions("affair:affairCertificate:view")
	@RequestMapping(value = "form")
	public String form(AffairCertificate affairCertificate, Model model) {
		model.addAttribute("affairCertificate", affairCertificate);
		return "modules/affair/affairCertificateForm";
	}

	@RequiresPermissions("affair:affairCertificate:edit")
	@RequestMapping(value = "save")
	public String save(AffairCertificate affairCertificate, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairCertificate)){
			return form(affairCertificate, model);
		}
		affairCertificateService.save(affairCertificate);
		request.setAttribute("saveResult","success");
		return "modules/affair/affairCertificateForm";
	}
	
	@RequiresPermissions("affair:affairCertificate:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCertificate affairCertificate, RedirectAttributes redirectAttributes) {
		affairCertificateService.delete(affairCertificate);
		addMessage(redirectAttributes, "删除证书成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCertificate/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCertificate:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCertificateService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairCertificate:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCertificate affairCertificate, Model model) {
		model.addAttribute("affairCertificate", affairCertificate);
		return "modules/affair/affairCertificateFormDetail";
	}

	/**
	 * 导出excel格式数据
	 * @param affairCertificate
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairCertificate affairCertificate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairCertificate.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairCertificate> page = null;
			if (flag == true) {
				page = affairCertificateService.findPage(new Page<AffairCertificate>(request, response), affairCertificate);
			} else {
				page = affairCertificateService.findPage(new Page<AffairCertificate>(request, response, -1), affairCertificate);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairCertificate.class);
			exportExcelNew.setWb(wb);
			List<AffairCertificate> list = page.getList();
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
		return "redirect:" + adminPath + "/affair/affairCertificate?repage";
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
			List<AffairCertificate> list = ei.getDataList(AffairCertificate.class);
			for (AffairCertificate thoughtAnalysis : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(thoughtAnalysis.getUnit());
					if(orgId != null){
						thoughtAnalysis.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, thoughtAnalysis);
					affairCertificateService.save(thoughtAnalysis);
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