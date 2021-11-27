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
import com.thinkgem.jeesite.modules.affair.entity.AffairDeath;
import com.thinkgem.jeesite.modules.affair.service.AffairDeathService;
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
 * 死亡干部档案登记花名册Controller
 * @author mason.xv
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDeath")
public class AffairDeathController extends BaseController {

	@Autowired
	private AffairDeathService affairDeathService;
	
	@ModelAttribute
	public AffairDeath get(@RequestParam(required=false) String id) {
		AffairDeath entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDeathService.get(id);
		}
		if (entity == null){
			entity = new AffairDeath();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDeath:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDeath affairDeath, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDeath> page = affairDeathService.findPage(new Page<AffairDeath>(request, response), affairDeath); 
		model.addAttribute("page", page);
		return "modules/affair/affairDeathList";
	}

	@RequiresPermissions("affair:affairDeath:view")
	@RequestMapping(value = "form")
	public String form(AffairDeath affairDeath, Model model) {
		model.addAttribute("affairDeath", affairDeath);
		return "modules/affair/affairDeathForm";
	}

	@RequiresPermissions("affair:affairDeath:edit")
	@RequestMapping(value = "save")
	public String save(AffairDeath affairDeath, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request) {
		if (!beanValidator(model, affairDeath)){
			return form(affairDeath, model);
		}
		affairDeathService.save(affairDeath);
		addMessage(redirectAttributes, "保存死亡干部档案登记花名册成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDeathForm";
	}
	
	@RequiresPermissions("affair:affairDeath:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDeath affairDeath, RedirectAttributes redirectAttributes) {
		affairDeathService.delete(affairDeath);
		addMessage(redirectAttributes, "删除死亡干部档案登记花名册成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDeath/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDeath:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDeathService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairDeath:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDeath affairDeath, Model model) {
		model.addAttribute("affairDeath", affairDeath);
		return "modules/affair/affairDeathFormDetail";
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
	public String exportExcelByTemplate(AffairDeath affairDeath, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairDeath.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairDeath> page = null;
			if(flag == true){
				page = affairDeathService.findPage(new Page<AffairDeath>(request, response), affairDeath);
			}else{
				page = affairDeathService.findPage(new Page<AffairDeath>(request, response,-1), affairDeath);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairDeath.class);
			exportExcelNew.setWb(wb);
			List<AffairDeath> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairDeath/?repage";
	}

}