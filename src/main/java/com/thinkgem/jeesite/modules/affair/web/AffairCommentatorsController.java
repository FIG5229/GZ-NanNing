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
import com.thinkgem.jeesite.modules.affair.dao.AffairCommentatorsDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairCommentatorsDeputyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCommentatorsDeputy;
import com.thinkgem.jeesite.modules.affair.service.AffairCommentatorsDeputyService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.record.AutoFilterInfoRecord;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.elasticsearch.search.query.QuerySearchRequest;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairCommentators;
import com.thinkgem.jeesite.modules.affair.service.AffairCommentatorsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 网评员管理Controller
 * @author alan.wu
 * @version 2020-06-19
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCommentators")
public class AffairCommentatorsController extends BaseController {

	@Autowired
	private AffairCommentatorsDao affairCommentatorsDao;

	@Autowired
	private AffairCommentatorsService affairCommentatorsService;

	@Autowired
	private AffairCommentatorsDeputyService affairCommentatorsDeputyService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairCommentatorsDeputyDao affairCommentatorsDeputyDao;

	@ModelAttribute
	public AffairCommentators get(@RequestParam(required = false) String id) {
		AffairCommentators entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = affairCommentatorsService.get(id);
		}
		if (entity == null) {
			entity = new AffairCommentators();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairCommentators:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCommentators affairCommentators, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCommentators> page = affairCommentatorsService.findPage(new Page<AffairCommentators>(request, response), affairCommentators);
		model.addAttribute("page", page);
		return "modules/affair/affairCommentatorsList";
	}

	@RequiresPermissions("affair:affairCommentators:view")
	@RequestMapping(value = "form")
	public String form(AffairCommentators affairCommentators, Model model) {

		String idNumber = affairCommentators.getIdNumber();
		List<AffairCommentatorsDeputy> list = affairCommentatorsDeputyDao.selectDeputyById(idNumber);
		if (list.size() > 0) {
			affairCommentators.setAffairCommentatorsDeputies(list);
		}

		model.addAttribute("affairCommentators", affairCommentators);
		return "modules/affair/affairCommentatorsForm";
	}

	@RequiresPermissions("affair:affairCommentators:edit")
	@RequestMapping(value = "save")
	public String save(AffairCommentators affairCommentators, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairCommentators)) {
			return form(affairCommentators, model);
		}
		affairCommentatorsService.save(affairCommentators);
		addMessage(redirectAttributes, "保存网评员管理成功");
		model.addAttribute("saveResult", "sucess");
		return "modules/affair/affairCommentatorsForm";
	}

	@RequiresPermissions("affair:affairCommentators:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCommentators affairCommentators, RedirectAttributes redirectAttributes) {
		affairCommentatorsService.delete(affairCommentators);
		addMessage(redirectAttributes, "删除网评员管理成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairCommentators/?repage";
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairCommentators:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairCommentatorsService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 *
	 * @param affairCommentators
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairCommentators:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCommentators affairCommentators, Model model) {

		String idNumber = affairCommentators.getIdNumber();
		List<AffairCommentatorsDeputy> list = affairCommentatorsDeputyDao.selectDeputyById(idNumber);
		if (list.size() > 0) {
			affairCommentators.setAffairCommentatorsDeputies(list);
		}

		model.addAttribute("affairCommentators", affairCommentators);

		return "modules/affair/affairCommentatorsFormDetail";
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
	public String exportExcelByTemplate(AffairCommentators affairCommentators, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			List<AffairCommentators> pageList = null;
			Page<AffairCommentators> page = null;
			if(flag == true){
				page = affairCommentatorsService.findPage(new Page<AffairCommentators>(request, response), affairCommentators);
				pageList=page.getList();
			}else {
				page = affairCommentatorsService.findPage(new Page<AffairCommentators>(request, response,-1), affairCommentators);
				pageList=page.getList();
			}
			/*//主表信息（导出表）
			List<AffairCommentators> affairCommentatorsList = page.getList();

			for (int i = 0; i < affairCommentatorsList.size(); i++) {
				AffairCommentators affairCommentators1 = affairCommentatorsList.get(i);
				String idNumber = affairCommentators1.getIdNumber();
				List<String> list = affairCommentatorsDeputyDao.selectPlatFormById(idNumber);
				List<String> list1 = affairCommentatorsDeputyDao.selectAccountById(idNumber);

				if (list.size() > 0){
					affairCommentators1.setPlatformAccount(list);
			}
				if (list1.size() > 0){
					affairCommentators1.setPlatform(list1);
				}

			}*/

			//根据主表查询子表
			List<AffairCommentatorsDeputy> policeHomeList = new ArrayList<>();
			//int num = 1;
			for(AffairCommentators item : pageList){
				List<AffairCommentatorsDeputy> childList = affairCommentatorsDeputyDao.findByParentId(item.getIdNumber());
				if (childList==null || childList.size()<1){
					AffairCommentatorsDeputy affairCommentatorsDeputy = new AffairCommentatorsDeputy();
					affairCommentatorsDeputy.setName(item.getName());
					affairCommentatorsDeputy.setUnit(item.getUnit());
					affairCommentatorsDeputy.setDuty(item.getDuty());
					affairCommentatorsDeputy.setPhone(item.getPhone());
					affairCommentatorsDeputy.setDescription(item.getDescription());
					policeHomeList.add(affairCommentatorsDeputy);
				}else{
					for(AffairCommentatorsDeputy affairCommentatorsDeputy : childList){
						affairCommentatorsDeputy.setName(item.getName());
						affairCommentatorsDeputy.setUnit(item.getUnit());
						affairCommentatorsDeputy.setDuty(item.getDuty());
						affairCommentatorsDeputy.setPhone(item.getPhone());
						affairCommentatorsDeputy.setDescription(item.getDescription());
						policeHomeList.add(affairCommentatorsDeputy);
					}
				}

			}


			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			//修改
			ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairCommentatorsDeputy.class);
			exportExcelNew.setWb(wb);
			/*List<affairOneHelpOne> list =page.getList();*/
			exportExcelNew.setDataList(policeHomeList);
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
		//修改
		return "redirect:" + adminPath + "/affair/affairCommentators/?repage";

	}



	/*
	 *
	 *  导入
	 * */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;

			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairCommentators> list = ei.getDataList(AffairCommentators.class);
			for (AffairCommentators affairCommentators : list) {
				try {
					affairCommentators.setUnitId(officeService.findByName(affairCommentators.getUnit()));
					BeanValidators.validateWithException(validator, affairCommentators);
					affairCommentatorsService.save(affairCommentators);
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