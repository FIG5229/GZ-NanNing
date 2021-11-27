/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateIssue;
import com.thinkgem.jeesite.modules.affair.service.AffairCertificateIssueService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 颁发证书Controller
 * @author jack.xu
 * @version 2020-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCertificateIssue")
public class AffairCertificateIssueController extends BaseController {

	@Autowired
	private AffairCertificateIssueService affairCertificateIssueService;
	
	@ModelAttribute
	public AffairCertificateIssue get(@RequestParam(required=false) String id) {
		AffairCertificateIssue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCertificateIssueService.get(id);
		}
		if (entity == null){
			entity = new AffairCertificateIssue();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCertificateIssue:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCertificateIssue affairCertificateIssue, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCertificateIssue> page = affairCertificateIssueService.findPage(new Page<AffairCertificateIssue>(request, response), affairCertificateIssue); 
		model.addAttribute("page", page);
		return "modules/affair/affairCertificateIssueList";
	}

	@RequiresPermissions("affair:affairCertificateIssue:view")
	@RequestMapping(value = "form")
	public String form(AffairCertificateIssue affairCertificateIssue, Model model) {
		model.addAttribute("affairCertificateIssue", affairCertificateIssue);
		return "modules/affair/affairCertificateIssueForm";
	}

	@RequiresPermissions("affair:affairCertificateIssue:edit")
	@RequestMapping(value = "save")
	public String save(AffairCertificateIssue affairCertificateIssue, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairCertificateIssue)){
			return form(affairCertificateIssue, model);
		}
		affairCertificateIssueService.save(affairCertificateIssue);
		addMessage(redirectAttributes, "保存颁发证书成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairCertificateIssueForm";
	}
	
	@RequiresPermissions("affair:affairCertificateIssue:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCertificateIssue affairCertificateIssue, RedirectAttributes redirectAttributes) {
		affairCertificateIssueService.delete(affairCertificateIssue);
		addMessage(redirectAttributes, "删除颁发证书成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCertificateIssue/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCertificateIssue:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCertificateIssueService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairCertificateIssue:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCertificateIssue affairCertificateIssue, Model model) {
		model.addAttribute("affairCertificateIssue", affairCertificateIssue);
		return "modules/affair/affairCertificateIssueFormDetail";
	}

}