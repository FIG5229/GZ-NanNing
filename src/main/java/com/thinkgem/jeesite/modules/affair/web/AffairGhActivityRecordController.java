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
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityRecord;
import com.thinkgem.jeesite.modules.affair.service.AffairGhActivityRecordService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
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
 * 工会活动记录Controller
 * @author cecil.li
 * @version 2019-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGhActivityRecord")
public class AffairGhActivityRecordController extends BaseController {

	@Autowired
	private DictDao dictDao;
	@Autowired
	private AffairGhActivityRecordService affairGhActivityRecordService;
	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairGhActivityRecord get(@RequestParam(required=false) String id) {
		AffairGhActivityRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGhActivityRecordService.get(id);
		}
		if (entity == null){
			entity = new AffairGhActivityRecord();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairGhActivityRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairGhActivityRecord affairGhActivityRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairGhActivityRecord> page = affairGhActivityRecordService.findPage(new Page<AffairGhActivityRecord>(request, response), affairGhActivityRecord);
		model.addAttribute("page", page);
		return "modules/affair/affairGhActivityRecordList";
	}

	@RequiresPermissions("affair:affairGhActivityRecord:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairGhActivityRecord affairGhActivityRecord, Model model, String type) {
		//
		Category category = new Category();
		category.setId(affairGhActivityRecord.getId());
		//
		ArticleData articleData = new ArticleData();
		articleData.setContent(affairGhActivityRecord.getContent());
		//
		Article article = new Article();
		article.setCategory(category);
		article.setArticleData(articleData);
		article.setTitle(affairGhActivityRecord.getTitle());
		article.setCreateDate(affairGhActivityRecord.getCreateDate());
		article.setAppendfile("/politics"+affairGhActivityRecord.getFilePath());
		//
		model.addAttribute("article", article);
		model.addAttribute("type",type);
		return "modules/affair/affairGhActivityRecordPropelling";
	}

	@RequiresPermissions("affair:affairGhActivityRecord:view")
	@RequestMapping(value = "form")
	public String form(AffairGhActivityRecord affairGhActivityRecord, Model model) {
		model.addAttribute("affairGhActivityRecord", affairGhActivityRecord);
		return "modules/affair/affairGhActivityRecordForm";
	}

	/**
	 * 详情
	 * @param affairGhActivityRecord
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGhActivityRecord:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairGhActivityRecord affairGhActivityRecord, Model model) {
		model.addAttribute("affairGhActivityRecord", affairGhActivityRecord);
		if(affairGhActivityRecord.getFilePath() != null && affairGhActivityRecord.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairGhActivityRecord.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairGhActivityRecordFormDetail";
	}

	@RequiresPermissions("affair:affairGhActivityRecord:edit")
	@RequestMapping(value = "save")
	public String save(AffairGhActivityRecord affairGhActivityRecord, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairGhActivityRecord)){
			return form(affairGhActivityRecord, model);
		}
		affairGhActivityRecordService.save(affairGhActivityRecord);
		addMessage(redirectAttributes, "保存工会活动记录成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairGhActivityRecordForm";
	}

	@RequiresPermissions("affair:affairGhActivityRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGhActivityRecord affairGhActivityRecord, RedirectAttributes redirectAttributes) {
		affairGhActivityRecordService.delete(affairGhActivityRecord);
		addMessage(redirectAttributes, "删除工会活动记录成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGhActivityRecord/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairGhActivityRecord:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGhActivityRecordService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
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
		if (StringUtils.isNotBlank(request.getParameter("oneCheckId"))) {
			String oneCheckId = request.getParameter("oneCheckId");
			String oneCheckMan = dictDao.findLabelByValue(oneCheckId);
			Collections.addAll(userList, idsArray);
			List<AffairGhActivityRecord> list = affairGhActivityRecordService.findByIds(userList);
			for (AffairGhActivityRecord affairGhActivityRecord : list) {
				affairGhActivityRecord.setCheckType("2");
				affairGhActivityRecord.setOneCheckMan(oneCheckMan);
				affairGhActivityRecord.setOneCheckId(oneCheckId);
				affairGhActivityRecord.setSubmitId(user.getId());
				affairGhActivityRecord.setSubmitMan(user.getName());
				affairGhActivityRecordService.save(affairGhActivityRecord);
			}
		} else if (StringUtils.isNotBlank(request.getParameter("twoCheckId"))) {
			{
				String twoCheckId = request.getParameter("twoCheckId");
				String twoCheckMan = dictDao.findLabelByValue(twoCheckId);
				Collections.addAll(userList, idsArray);
				List<AffairGhActivityRecord> list = affairGhActivityRecordService.findByIds(userList);
				for (AffairGhActivityRecord affairGhActivityRecord : list) {
					affairGhActivityRecord.setCheckType("3");
					affairGhActivityRecord.setTwoCheckId(twoCheckId);
					affairGhActivityRecord.setTwoCheckMan(twoCheckMan);
					affairGhActivityRecordService.save(affairGhActivityRecord);
				}
			}
		}
		return "redirect:" + Global.getAdminPath() + "/affair/affairGhActivityRecord/list";
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
	public String exportExcelByTemplate(AffairGhActivityRecord affairGhActivityRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairGhActivityRecord> page = null;
			if(flag == true){
				page = affairGhActivityRecordService.findPage(new Page<AffairGhActivityRecord>(request, response), affairGhActivityRecord);
			}else{
				page = affairGhActivityRecordService.findPage(new Page<AffairGhActivityRecord>(request, response,-1), affairGhActivityRecord);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGhActivityRecord.class);
			exportExcelNew.setWb(wb);
			List<AffairGhActivityRecord> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairGhActivityRecord/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairGhActivityRecord> list = ei.getDataList(AffairGhActivityRecord.class);
			for (AffairGhActivityRecord affairGhActivityRecord : list){
				try{
					BeanValidators.validateWithException(validator, affairGhActivityRecord);
					affairGhActivityRecordService.save(affairGhActivityRecord);
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

	@RequestMapping(value = "activityDetail")
	public String activityDetail(AffairGhActivityRecord record,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairGhActivityRecord> page = affairGhActivityRecordService.findActivityDetailPage(new Page<AffairGhActivityRecord>(request,response),record);
		model.addAttribute("page",page);
		return "modules/charts/chartGhActivityList";
	}
}