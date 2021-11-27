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
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairBrandManagement;
import com.thinkgem.jeesite.modules.affair.service.AffairBrandManagementService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 品牌创建Controller
 * @author cecil.li
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairBrandManagement")
public class AffairBrandManagementController extends BaseController {

	@Autowired
	private DictDao dictDao;

	@Autowired
	private AffairBrandManagementService affairBrandManagementService;

	@Autowired
	private OfficeService officeService;
	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairTwBaseService affairTwBaseService;
	
	@ModelAttribute
	public AffairBrandManagement get(@RequestParam(required=false) String id) {
		AffairBrandManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairBrandManagementService.get(id);
		}
		if (entity == null){
			entity = new AffairBrandManagement();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairBrandManagement:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairBrandManagement affairBrandManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairBrandManagement> page = affairBrandManagementService.findPage(new Page<AffairBrandManagement>(request, response), affairBrandManagement); 
		model.addAttribute("page", page);
		return "modules/affair/affairBrandManagementList";
	}

	@RequiresPermissions("affair:affairBrandManagement:view")
	@RequestMapping(value = "form")
	public String form(AffairBrandManagement affairBrandManagement, Model model) {
		model.addAttribute("affairBrandManagement", affairBrandManagement);
		return "modules/affair/affairBrandManagementForm";
	}

	@RequiresPermissions("affair:affairBrandManagement:edit")
	@RequestMapping(value = "save")
	public String save(AffairBrandManagement affairBrandManagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairBrandManagement)){
			return form(affairBrandManagement, model);
		}
		affairBrandManagementService.save(affairBrandManagement);
		addMessage(redirectAttributes, "保存品牌创建成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairBrandManagementForm";
	}
	
	@RequiresPermissions("affair:affairBrandManagement:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairBrandManagement affairBrandManagement, RedirectAttributes redirectAttributes) {
		affairBrandManagementService.delete(affairBrandManagement);
		addMessage(redirectAttributes, "删除品牌创建成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairBrandManagement/?repage";
	}

	/**
	 * 详情
	 * @param affairBrandManagement
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairBrandManagement:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairBrandManagement affairBrandManagement, Model model) {
		model.addAttribute("affairBrandManagement", affairBrandManagement);
		if(affairBrandManagement.getAnnex() != null && affairBrandManagement.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairBrandManagement.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairBrandManagementFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairBrandManagement:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairBrandManagementService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairBrandManagement affairBrandManagement, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairBrandManagement> page = null;
			if(flag == true){
				page = affairBrandManagementService.findPage(new Page<AffairBrandManagement>(request, response), affairBrandManagement);
			}else{
				page = affairBrandManagementService.findPage(new Page<AffairBrandManagement>(request, response,-1), affairBrandManagement);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairBrandManagement.class);
			exportExcelNew.setWb(wb);
			List<AffairBrandManagement> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairBrandManagement/?repage";
	}


	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairBrandManagement> list = ei.getDataList(AffairBrandManagement.class);
			for (AffairBrandManagement affairBrandManagement : list){
				try{
					BeanValidators.validateWithException(validator, affairBrandManagement);
					affairBrandManagement.setUnitId(affairTwBaseService.findIdByName(affairBrandManagement.getUnit()));
					affairBrandManagementService.save(affairBrandManagement);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("导入失败："+ex.getMessage());
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

	/**
	 * 批量提交
	 * @param
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
		List<String> userList = new ArrayList<String>();

		String oneCheckId = request.getParameter("oneCheckId");
		String oneCheckMan = dictDao.findLabelByValue(oneCheckId);
		Collections.addAll(userList,idsArray);
		List <AffairBrandManagement> list = affairBrandManagementService.findByIds(userList);
		for (AffairBrandManagement affairBrandManagement: list){
			affairBrandManagement.setCheckType("2");
			affairBrandManagement.setOneCheckMan(oneCheckMan);
			affairBrandManagement.setOneCheckId(oneCheckId);
			affairBrandManagement.setSubmitId(user.getId());
			affairBrandManagement.setSubmitMan(user.getName());
			affairBrandManagementService.save(affairBrandManagement);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairBrandManagement/list";
	}
	@RequiresPermissions("affair:affairBrandManagement:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairBrandManagementCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairBrandManagement:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairBrandManagement affairBrandManagement, HttpServletRequest request) {
		if (StringUtils.isNotBlank(affairBrandManagement.getTwoCheckId())){
			affairBrandManagement.setTwoCheckMan(dictDao.findLabelByValue(affairBrandManagement.getTwoCheckId()));
		}
		affairBrandManagementService.save(affairBrandManagement);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}