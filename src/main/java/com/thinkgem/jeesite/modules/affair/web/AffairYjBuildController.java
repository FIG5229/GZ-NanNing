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
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairYjBuild;
import com.thinkgem.jeesite.modules.affair.service.AffairYjBuildService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 硬件建设Controller
 * @author kevin.jia
 * @version 2020-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairYjBuild")
public class AffairYjBuildController extends BaseController {

	@Autowired
	private AffairYjBuildService affairYjBuildService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private DictDao dictDao;
	
	@ModelAttribute
	public AffairYjBuild get(@RequestParam(required=false) String id) {
		AffairYjBuild entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairYjBuildService.get(id);
		}
		if (entity == null){
			entity = new AffairYjBuild();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairYjBuild:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairYjBuild affairYjBuild, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairYjBuild> page = affairYjBuildService.findPage(new Page<AffairYjBuild>(request, response), affairYjBuild); 
		model.addAttribute("page", page);
		return "modules/affair/affairYjBuildList";
	}

	@RequiresPermissions("affair:affairYjBuild:view")
	@RequestMapping(value = "form")
	public String form(AffairYjBuild affairYjBuild, Model model) {
		model.addAttribute("affairYjBuild", affairYjBuild);
		return "modules/affair/affairYjBuildForm";
	}

	/**
	 * 查看详情
	 * @param affairYjBuild
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairYjBuild:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairYjBuild affairYjBuild, Model model) {
		model.addAttribute("affairYjBuild", affairYjBuild);
		if(affairYjBuild.getAppendfile() != null && affairYjBuild.getAppendfile().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairYjBuild.getAppendfile());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairYjBuildFormDetail";
	}

	@RequiresPermissions("affair:affairYjBuild:edit")
	@RequestMapping(value = "save")
	public String save(AffairYjBuild affairYjBuild, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairYjBuild)){
			return form(affairYjBuild, model);
		}
		affairYjBuildService.save(affairYjBuild);
		addMessage(redirectAttributes, "保存硬件建设成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairYjBuildForm";
	}
	
	@RequiresPermissions("affair:affairYjBuild:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairYjBuild affairYjBuild, RedirectAttributes redirectAttributes) {
		affairYjBuildService.delete(affairYjBuild);
		addMessage(redirectAttributes, "删除硬件建设成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairYjBuild/?repage";
	}

	@RequiresPermissions("affair:affairYjBuild:edit")
	@RequestMapping(value = {"deleteByIds"})
	@ResponseBody
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairYjBuildService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return  result;
	}

	/**
	 * 导出excel格式数据
	 * @param affairYjBuild
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairYjBuild affairYjBuild, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairYjBuild> page = null;
			if(flag == true){
				page = affairYjBuildService.findPage(new Page<AffairYjBuild>(request, response), affairYjBuild);
			}else{
				page = affairYjBuildService.findPage(new Page<AffairYjBuild>(request, response,-1), affairYjBuild);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairYjBuild.class);
			exportExcelNew.setWb(wb);
			List<AffairYjBuild> list =page.getList();
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
			addMessage(redirectAttributes, "导出硬件建设列表失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairYjBuild?repage";
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
			List<AffairYjBuild> list = ei.getDataList(AffairYjBuild.class);
			for (AffairYjBuild affairYjBuild : list){
				try{

					BeanValidators.validateWithException(validator, affairYjBuild);
					affairYjBuildService.save(affairYjBuild);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(项目名称:"+affairYjBuild.getProName()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/download/view?repage";
	}

	/**
	 * 批量提交
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> ids = new ArrayList<String>();
		String oneCheckId = request.getParameter("oneCheckId");
		String oneCheckMan = dictDao.findLabelByValue(oneCheckId);
		Collections.addAll(ids,idsArray);
		List <AffairYjBuild> list = affairYjBuildService.findByIds(ids);
		for (AffairYjBuild affairYjBuild: list){
			affairYjBuild.setCheckType("2");
			affairYjBuild.setOneCheckMan(oneCheckMan);
			affairYjBuild.setOneCheckId(oneCheckId);
			affairYjBuild.setSubmitId(user.getId());
			affairYjBuild.setSubmitMan(user.getName());
			affairYjBuildService.save(affairYjBuild);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairYjBuild/list";
	}

	/*
	 * 审核页面
	 * */
	@RequiresPermissions("affair:affairYjBuild:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairYjBuildCheckDialog";
	}

	/*
	 * 审核
	 * @param affairYjBuild
	 * @param request
	 * @return
	 * */
	@ResponseBody
	@RequiresPermissions("affair:affairYjBuild:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairYjBuild affairYjBuild, HttpServletRequest request) {
		if (!affairYjBuild.getTwoCheckId().isEmpty()) {
			//如果审核方式为转为上一级,则通过twoCheckId,twoCheckMan
			String twoCheckMan = dictDao.findLabelByValue(affairYjBuild.getTwoCheckId());
			affairYjBuild.setTwoCheckMan(twoCheckMan);
		}
		affairYjBuildService.save(affairYjBuild);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}