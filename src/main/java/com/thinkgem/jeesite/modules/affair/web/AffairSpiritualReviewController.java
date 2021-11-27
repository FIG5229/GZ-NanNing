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
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.formula.functions.Now;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairSpiritualReview;
import com.thinkgem.jeesite.modules.affair.service.AffairSpiritualReviewService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 复查情况报告Controller
 * @author Alan.wu
 * @version 2020-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSpiritualReview")
public class AffairSpiritualReviewController extends BaseController {

	@Autowired
	private AffairSpiritualReviewService affairSpiritualReviewService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private DictDao dictDao;
	
	@ModelAttribute
	public AffairSpiritualReview get(@RequestParam(required=false) String id) {
		AffairSpiritualReview entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSpiritualReviewService.get(id);
		}
		if (entity == null){
			entity = new AffairSpiritualReview();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSpiritualReview:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSpiritualReview affairSpiritualReview, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSpiritualReview> page = affairSpiritualReviewService.findPage(new Page<AffairSpiritualReview>(request, response), affairSpiritualReview); 
		model.addAttribute("page", page);
		return "modules/affair/affairSpiritualReviewList";
	}

	@RequiresPermissions("affair:affairSpiritualReview:view")
	@RequestMapping(value = "form")
	public String form(AffairSpiritualReview affairSpiritualReview, Model model) {
		model.addAttribute("affairSpiritualReview", affairSpiritualReview);
		return "modules/affair/affairSpiritualReviewForm";
	}

	@RequiresPermissions("affair:affairSpiritualReview:edit")
	@RequestMapping(value = "save")
	public String save(AffairSpiritualReview affairSpiritualReview, Model model, RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, affairSpiritualReview)){
			return form(affairSpiritualReview, model);
		}
		affairSpiritualReviewService.save(affairSpiritualReview);
		addMessage(redirectAttributes, "保存复查情况报告成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairSpiritualReviewForm";
	}
	
	@RequiresPermissions("affair:affairSpiritualReview:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSpiritualReview affairSpiritualReview, RedirectAttributes redirectAttributes) {
		affairSpiritualReviewService.delete(affairSpiritualReview);
		addMessage(redirectAttributes, "删除复查情况报告成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSpiritualReview/?repage";
	}


	/**
	 * 详情
	 *
	 * @param affairSpiritualReview
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairSpiritualReview:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairSpiritualReview affairSpiritualReview, Model model) {
		model.addAttribute("affairSpiritualReview", affairSpiritualReview);
		if (affairSpiritualReview.getAdjunct() != null && affairSpiritualReview.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairSpiritualReview.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairSpiritualReviewFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairSpiritualReview:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSpiritualReviewService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/*
	* 推送
	* */
	@RequiresPermissions("affair:affairSpiritualReview:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairSpiritualReview affairSpiritualReview, Model model) {
		//
		Category category = new Category();
		category.setId(affairSpiritualReview.getId());
		//
		ArticleData articleData = new ArticleData();
		articleData.setContent(affairSpiritualReview.getDescription());
		//
		Article article = new Article();
		article.setCategory(category);
		article.setArticleData(articleData);
		article.setTitle(affairSpiritualReview.getTitle());
		article.setCreateDate(affairSpiritualReview.getCreateDate());
		//
		model.addAttribute("article", article);
		return "modules/affair/affairSpiritualReviewPropelling";
	}

	@RequiresPermissions("affair:affairSpiritualReview:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairSpiritualReviewDialog";
	}

	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairSpiritualReview:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairSpiritualReview affairSpiritualReview, HttpServletRequest request) {

		affairSpiritualReviewService.update(affairSpiritualReview.getId(),affairSpiritualReview.getState(),affairSpiritualReview.getOneCheckMan(),affairSpiritualReview.getOneCheckId());

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

		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();
		String oneCheckId = request.getParameter("oneCheckId");
		String oneCheckMan = dictDao.findLabelByValue(oneCheckId);
		Collections.addAll(userList,idsArray);
		List <AffairSpiritualReview> list = affairSpiritualReviewService.findByIds(userList);
		for (AffairSpiritualReview affairSpiritualReview: list){
			for (int i = 0 ; i < idsArray.length;i++){
				String state = "2";
				affairSpiritualReviewService.update(idsArray[i],state,oneCheckId,oneCheckMan);
			}
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairSpiritualReview/list";
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
	public String exportExcelByTemplate(AffairSpiritualReview affairSpiritualReview, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairSpiritualReview.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairSpiritualReview.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairSpiritualReview.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairSpiritualReview> page = null;
			if(flag == true){
				page = affairSpiritualReviewService.findPage(new Page<AffairSpiritualReview>(request, response), affairSpiritualReview);
			}else {
				page = affairSpiritualReviewService.findPage(new Page<AffairSpiritualReview>(request, response,-1), affairSpiritualReview);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairSpiritualReview.class);
			exportExcelNew.setWb(wb);
			List<AffairSpiritualReview> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairSpiritualReview?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairSpiritualReview> list = ei.getDataList(AffairSpiritualReview.class);
			for (AffairSpiritualReview affairLearnPower : list){
				try{
					affairLearnPower.setUnitId(officeService.findByName(affairLearnPower.getUnit()));
					BeanValidators.validateWithException(validator, affairLearnPower);
					affairSpiritualReviewService.save(affairLearnPower);
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