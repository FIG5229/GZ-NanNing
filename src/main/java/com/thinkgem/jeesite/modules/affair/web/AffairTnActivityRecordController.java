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
import com.thinkgem.jeesite.modules.affair.entity.AffairTnActivityRecord;
import com.thinkgem.jeesite.modules.affair.service.AffairTnActivityRecordService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseService;
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
import java.util.*;

/**
 * 团内活动记录Controller
 * @author cecil.li
 * @version 2019-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTnActivityRecord")
public class AffairTnActivityRecordController extends BaseController {

	@Autowired
	private AffairTnActivityRecordService affairTnActivityRecordService;

	@Autowired
	private UploadController uploadController;
	@Autowired
	private DictDao dictDao;

	@Autowired
	private AffairTwBaseService affairTwBaseService;
	
	@ModelAttribute
	public AffairTnActivityRecord get(@RequestParam(required=false) String id) {
		AffairTnActivityRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTnActivityRecordService.get(id);
		}
		if (entity == null){
			entity = new AffairTnActivityRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTnActivityRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTnActivityRecord affairTnActivityRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTnActivityRecord> page = affairTnActivityRecordService.findPage(new Page<AffairTnActivityRecord>(request, response), affairTnActivityRecord); 
		model.addAttribute("page", page);
		return "modules/affair/affairTnActivityRecordList";
	}

	@RequiresPermissions("affair:affairTnActivityRecord:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairTnActivityRecord affairTnActivityRecord, Model model) {
		//
		Category category = new Category();
		category.setId(affairTnActivityRecord.getId());
		//
		ArticleData articleData = new ArticleData();
		articleData.setContent(affairTnActivityRecord.getContent());
		//
		Article article = new Article();
		article.setCategory(category);
		article.setArticleData(articleData);
		article.setTitle(affairTnActivityRecord.getTitle());
		article.setCreateDate(affairTnActivityRecord.getCreateDate());
		article.setAppendfile("/politics"+affairTnActivityRecord.getFilePath());
		//
		model.addAttribute("article", article);
		model.addAttribute("affairTnActivityRecordId", affairTnActivityRecord.getId());
		return "modules/affair/affairTnActivityRecordPropelling";
	}

	@RequiresPermissions("affair:affairTnActivityRecord:view")
	@RequestMapping(value = "form")
	public String form(AffairTnActivityRecord affairTnActivityRecord, Model model) {
		model.addAttribute("affairTnActivityRecord", affairTnActivityRecord);
		return "modules/affair/affairTnActivityRecordForm";
	}
	/**
	 * 详情
	 * @param affairTnActivityRecord
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTnActivityRecord:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTnActivityRecord affairTnActivityRecord, Model model) {
		model.addAttribute("affairTnActivityRecord", affairTnActivityRecord);
		if(affairTnActivityRecord.getFilePath() != null && affairTnActivityRecord.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTnActivityRecord.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairTnActivityRecordFormDetail";
	}

	@RequiresPermissions("affair:affairTnActivityRecord:edit")
	@RequestMapping(value = "save")
	public String save(AffairTnActivityRecord affairTnActivityRecord, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTnActivityRecord)){
			return form(affairTnActivityRecord, model);
		}
		affairTnActivityRecordService.save(affairTnActivityRecord);
		addMessage(redirectAttributes, "保存团内活动记录成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTnActivityRecordForm";
	}
	
	@RequiresPermissions("affair:affairTnActivityRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTnActivityRecord affairTnActivityRecord, RedirectAttributes redirectAttributes) {
		affairTnActivityRecordService.delete(affairTnActivityRecord);
		addMessage(redirectAttributes, "删除团内活动记录成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTnActivityRecord/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTnActivityRecord:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTnActivityRecordService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairTnActivityRecord:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairTnActivityRecordCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairTnActivityRecord:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairTnActivityRecord affairTnActivityRecord, HttpServletRequest request) {
		if (StringUtils.isNotBlank(affairTnActivityRecord.getTwoCheckId())){
			affairTnActivityRecord.setTwoCheckMan(dictDao.findLabelByValue(affairTnActivityRecord.getTwoCheckId()));
		}
		if("ff7f9fe2597b40429ded58f8b76a2f65".equals(UserUtils.getUser().getId())){
			affairTnActivityRecord.setJuShTime(new Date());
		}else{
			affairTnActivityRecord.setChuShTime(new Date());
		}
		if("3".equals(affairTnActivityRecord.getCheckType())){
			//转送上一级
			affairTnActivityRecord.setJcSbTime(new Date());
			affairTnActivityRecord.setChuSbTime(new Date());
		}
		affairTnActivityRecordService.save(affairTnActivityRecord);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
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
		String oneCheckId = request.getParameter("oneCheckId");
		String oneCheckMan = dictDao.findLabelByValue(oneCheckId);
		Collections.addAll(userList,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List <AffairTnActivityRecord> list = affairTnActivityRecordService.findByIds(userList);
		for (AffairTnActivityRecord affairTnActivityRecord: list){
			affairTnActivityRecord.setCheckType("2");
			affairTnActivityRecord.setOneCheckMan(oneCheckMan);
			affairTnActivityRecord.setOneCheckId(oneCheckId);
			affairTnActivityRecord.setSubmitId(user.getId());
			affairTnActivityRecord.setSubmitMan(user.getName());
			affairTnActivityRecord.setJcSbTime(new Date());
			affairTnActivityRecordService.save(affairTnActivityRecord);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairTnActivityRecord/list";
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
	public String exportExcelByTemplate(AffairTnActivityRecord affairTnActivityRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairTnActivityRecord> page = null;
			if(flag == true){
				page = affairTnActivityRecordService.findPage(new Page<AffairTnActivityRecord>(request, response), affairTnActivityRecord);
			}else{
				page = affairTnActivityRecordService.findPage(new Page<AffairTnActivityRecord>(request, response,-1), affairTnActivityRecord);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTnActivityRecord.class);
			exportExcelNew.setWb(wb);
			List<AffairTnActivityRecord> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTnActivityRecord/?repage";
	}


	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTnActivityRecord> list = ei.getDataList(AffairTnActivityRecord.class);
			for (AffairTnActivityRecord affairTnActivityRecord : list){
				try{
					BeanValidators.validateWithException(validator, affairTnActivityRecord);
					//绑定团组织id
					String partyBranchId = affairTwBaseService.findIdByName(affairTnActivityRecord.getPartyBranch());
					affairTnActivityRecord.setPartyBranchId(partyBranchId);
					affairTnActivityRecordService.save(affairTnActivityRecord);
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

	/**
	 * 公司下团内活动明细
	 * @param affairTnActivityRecord
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("activityDetail")
	public String activityDetail(AffairTnActivityRecord affairTnActivityRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTnActivityRecord> page = affairTnActivityRecordService.findActivityDetailPage(new Page<AffairTnActivityRecord>(request, response), affairTnActivityRecord);
		model.addAttribute("page", page);
		return "modules/charts/chartTnActivityRecordList";
	}

	/**
	 * 公司集团内活动明细
	 * @param affairTnActivityRecord
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("topActivityDetail")
	public String topActivityDetail(AffairTnActivityRecord affairTnActivityRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTnActivityRecord> page = affairTnActivityRecordService.findTopActivityDetailPage(new Page<AffairTnActivityRecord>(request, response), affairTnActivityRecord);
		model.addAttribute("page", page);
		model.addAttribute("isTop","top");
		return "modules/charts/chartTnActivityRecordList";
	}
}