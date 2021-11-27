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
import com.thinkgem.jeesite.modules.affair.entity.AffairDcwtLibrary;
import com.thinkgem.jeesite.modules.affair.service.AffairDcwtLibraryService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
 * 督察问题管理Controller
 * @author cecil.li
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDcwtLibrary")
public class AffairDcwtLibraryController extends BaseController {
	@Autowired
	private AffairDcwtLibraryService affairDcwtLibraryService;
	@Autowired
	private UploadController uploadController;
	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairDcwtLibrary get(@RequestParam(required=false) String id) {
		AffairDcwtLibrary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDcwtLibraryService.get(id);
		}
		if (entity == null){
			entity = new AffairDcwtLibrary();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDcwtLibrary:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		//68  南宁处督察    284 北海处督察  18 南宁局警务督察处 单位id 183  柳州处督察
		/*if("1".equals(UserUtils.getUser().getCompany().getId()) && "18".equals(UserUtils.getUser().getOffice().getId()) && "0000".equals(UserUtils.getUser().getNo())){
			//确认当前用户为 南宁局督察信息管理账号
			affairDcwtLibrary.setResponsibleUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairDcwtLibrary.setResponsibleUnitId(UserUtils.getUser().getOffice().getId());
		}*/
//		affairDcwtLibrary.setResponsibleUnitId(UserUtils.getUser().getOffice().getId());
//		affairDcwtLibrary.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");
		if ("1".equals(reason) || null == reason || "".equals( reason )){
			Page<AffairDcwtLibrary> page = affairDcwtLibraryService.findPage(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
			model.addAttribute("page", page);
			model.addAttribute("reasons", reason);
		}else{
			Page<AffairDcwtLibrary> page = affairDcwtLibraryService.findListByDatePage(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
			model.addAttribute("page", page);
			model.addAttribute("reasons", reason);
		}
			return "modules/affair/affairDcwtLibraryList";
	}

	@RequiresPermissions("affair:affairDcwtLibrary:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairDcwtLibrary affairDcwtLibrary, Model model) {
		//
		Category category = new Category();
		category.setId(affairDcwtLibrary.getId());
		//
//		ArticleData articleData = new ArticleData();
//		articleData.setContent(affairDcwtLibrary.getProcessingSituation());
		//
		Article article = new Article();
		article.setCategory(category);
//		article.setArticleData(articleData);
		article.setTitle(affairDcwtLibrary.getResponsibleUnit()+"问题整改");
		ArticleData articleData = new ArticleData();
		articleData.setContent(affairDcwtLibrary.getProblemCategory()+"\n"+affairDcwtLibrary.getFoundProblem()+"\n"+affairDcwtLibrary.getRectification());
		article.setArticleData(articleData);
		article.setDescription(affairDcwtLibrary.getFoundProblem());
		article.setAppendfile("/politics"+affairDcwtLibrary.getAnnex());
		article.setCreateDate(affairDcwtLibrary.getCreateDate());
		//
		model.addAttribute("article", article);
		return "modules/affair/affairDcwtLibraryPropelling";
	}

	@RequiresPermissions("affair:affairDcwtLibrary:view")
	@RequestMapping(value = "form")
	public String form(AffairDcwtLibrary affairDcwtLibrary, Model model) {
		if (affairDcwtLibrary.getProblemCategory() != null && affairDcwtLibrary.getProblemCategory().length() >0){
			String[] problemCategoryArr = affairDcwtLibrary.getProblemCategory().split(",");
			affairDcwtLibrary.setProblemCategoryArr(problemCategoryArr);
		}
		String ofi=UserUtils.getUser().getOffice().getId();
		model.addAttribute("affairDcwtLibrary", affairDcwtLibrary);
		return "modules/affair/affairDcwtLibraryForm";
	}

	/**
	 * 详情
	 * @param affairDcwtLibrary
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairDcwtLibrary:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDcwtLibrary affairDcwtLibrary, Model model) {
		model.addAttribute("affairDcwtLibrary", affairDcwtLibrary);
		if(affairDcwtLibrary.getAnnex() != null && affairDcwtLibrary.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairDcwtLibrary.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairDcwtLibraryFormDetail";
	}

	@RequiresPermissions("affair:affairDcwtLibrary:view")
	@RequestMapping(value = "affairDcwtLibraryListSum")
	public String Sum() {
		return "modules/affair/affairDcwtLibraryListSum";
	}

	//@RequiresPermissions("affair:affairDcwtLibrary:edit")
	@RequestMapping(value = "save")
	public String save(AffairDcwtLibrary affairDcwtLibrary, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDcwtLibrary)){
			return form(affairDcwtLibrary, model);
		}

		if(null != affairDcwtLibrary.getProblemCategoryArr()){
			affairDcwtLibrary.setProblemCategory(StringUtils.join(affairDcwtLibrary.getProblemCategoryArr(),","));
		}
		affairDcwtLibraryService.save(affairDcwtLibrary);
		addMessage(redirectAttributes, "保存督察问题库成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDcwtLibraryForm";
	}
	
	@RequiresPermissions("affair:affairDcwtLibrary:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairDcwtLibrary affairDcwtLibrary, RedirectAttributes redirectAttributes) {
		affairDcwtLibraryService.delete(affairDcwtLibrary);
		addMessage(redirectAttributes, "删除督察问题库成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDcwtLibrary/?repage";
	}


	@ResponseBody
	@RequiresPermissions("affair:affairDcwtLibrary:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDcwtLibraryService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if("1".equals(UserUtils.getUser().getCompany().getId())){
				affairDcwtLibrary.setResponsibleUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairDcwtLibrary.setResponsibleUnitId(UserUtils.getUser().getOffice().getId());
			}
//		affairDcwtLibrary.setResponsibleUnitId(UserUtils.getUser().getOffice().getId());
			affairDcwtLibrary.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairDcwtLibrary> page = null;
			if(flag == true){
				page = affairDcwtLibraryService.findPage(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
			}else {
				page = affairDcwtLibraryService.findPage(new Page<AffairDcwtLibrary>(request, response,-1), affairDcwtLibrary);
			}
/*
			Page<AffairDcwtLibrary> page = affairDcwtLibraryService.findPage(new Page<AffairDcwtLibrary>(request, response,-1), affairDcwtLibrary);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairDcwtLibrary.class);
			exportExcelNew.setWb(wb);
			List<AffairDcwtLibrary> list =page.getList();
			list.forEach(item->{
				if (item.getProblemCategory() != null && item.getProblemCategory().length() >0){
					String[] problemCategoryArr = item.getProblemCategory().split(",");
					for (int i = 0; i < problemCategoryArr.length; i++) {
						String wtlb = "";
						String label = DictUtils.getDictLabel(problemCategoryArr[i], "affair_wtlb", "");
						wtlb = wtlb+","+ label;
						item.setType(wtlb);
					}
				}
			});
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
		return "redirect:" + adminPath + "/affair/affairDcwtLibrary?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairDcwtLibrary> list = ei.getDataList(AffairDcwtLibrary.class);
			for (AffairDcwtLibrary affairDcwtLibrary : list){
				try{
					if (affairDcwtLibrary.getType() != null){
						String[] problemCategoryArr = affairDcwtLibrary.getType().split(",");
						for (int i = 0; i < problemCategoryArr.length; i++) {
							if (StringUtils.isNotBlank(problemCategoryArr[i])){
								String wtlb = "";
								String label = DictUtils.getDictValue(problemCategoryArr[i], "affair_wtlb", "");
								wtlb = wtlb+","+ label;
								affairDcwtLibrary.setProblemCategory(wtlb);
							}else {
								affairDcwtLibrary.setProblemCategory("");
							}
						}
					}
					affairDcwtLibrary.setResponsibleUnitId(officeService.findByName(affairDcwtLibrary.getResponsibleUnit()));
					BeanValidators.validateWithException(validator, affairDcwtLibrary);
					affairDcwtLibraryService.save(affairDcwtLibrary);
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

	@RequestMapping(value = "findAllDetailInfoList")
	public String findAllDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findAllDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorAllDetailInfo";
	}

	/*@RequestMapping(value = "findAllDetailInfoList")
	public String findAllDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
//		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findAllDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> newPage = affairDcwtLibraryService.findAllDetailInfoList(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		page.setPageSize(newPage.getPageSize());
		page.setPageNo(newPage.getPageNo());
		page.setOrderBy(newPage.getOrderBy());
		page.setCount(newPage.getCount());
		page.setFuncName(newPage.getFuncName());
		page.setFuncParam(newPage.getFuncParam());
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newPage.getList()){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
			page.setList(list);
		}
		model.addAttribute("page", page);
		return "modules/charts/inspectorAllDetailInfo";
	}*/

	@RequestMapping(value = "findAllPieDetailInfoList")
	public String findAllPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findAllPieDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorAllDetailInfo";
	}

	/*@RequestMapping(value = "findAllPieDetailInfoList")
	public String findAllPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDcwtLibrary> newPage = affairDcwtLibraryService.findAllPieDetailInfoList(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		page.setPageSize(newPage.getPageSize());
		page.setPageNo(newPage.getPageNo());
		page.setOrderBy(newPage.getOrderBy());
		page.setCount(newPage.getCount());
		page.setFuncName(newPage.getFuncName());
		page.setFuncParam(newPage.getFuncParam());
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newPage.getList()){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
			page.setList(list);
		}
		model.addAttribute("page", page);
		return "modules/charts/inspectorAllPieDetailInfo";
	}*/

	@RequestMapping(value = "findJuDetailInfoList")
	public String findJuDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findJuDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorJuDetailInfo";
	}

	/*@RequestMapping(value = "findJuDetailInfoList")
	public String findJuDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDcwtLibrary> newPage = affairDcwtLibraryService.findJuDetailInfoList(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		page.setPageSize(newPage.getPageSize());
		page.setPageNo(newPage.getPageNo());
		page.setOrderBy(newPage.getOrderBy());
		page.setCount(newPage.getCount());
		page.setFuncName(newPage.getFuncName());
		page.setFuncParam(newPage.getFuncParam());
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newPage.getList()){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
			page.setList(list);
		}
		model.addAttribute("page", page);
		return "modules/charts/inspectorJuDetailInfo";
	}*/

	@RequestMapping(value = "findJuPieDetailInfoList")
	public String findJuPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findJuPieDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorJuDetailInfo";
	}

	/*@RequestMapping(value = "findJuPieDetailInfoList")
	public String findJuPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDcwtLibrary> newPage = affairDcwtLibraryService.findJuPieDetailInfoList(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		page.setPageSize(newPage.getPageSize());
		page.setPageNo(newPage.getPageNo());
		page.setOrderBy(newPage.getOrderBy());
		page.setCount(newPage.getCount());
		page.setFuncName(newPage.getFuncName());
		page.setFuncParam(newPage.getFuncParam());
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newPage.getList()){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
			page.setList(list);
		}
		model.addAttribute("page", page);
		return "modules/charts/inspectorJuPieDetailInfo";
	}*/

	@RequestMapping(value = "findNncDetailInfoList")
	public String findNncDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findNncDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorNncDetailInfo";
	}

	/*@RequestMapping(value = "findNncDetailInfoList")
	public String findNncDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDcwtLibrary> newPage = affairDcwtLibraryService.findNncDetailInfoList(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		page.setPageSize(newPage.getPageSize());
		page.setPageNo(newPage.getPageNo());
		page.setOrderBy(newPage.getOrderBy());
		page.setCount(newPage.getCount());
		page.setFuncName(newPage.getFuncName());
		page.setFuncParam(newPage.getFuncParam());
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newPage.getList()){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
			page.setList(list);
		}
		model.addAttribute("page", page);
		return "modules/charts/inspectorNncDetailInfo";
	}*/

	@RequestMapping(value = "findNncPieDetailInfoList")
	public String findNncPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findNncPieDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorNncDetailInfo";
	}

	/*@RequestMapping(value = "findNncPieDetailInfoList")
	public String findNncPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDcwtLibrary> newPage = affairDcwtLibraryService.findNncPieDetailInfoList(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		page.setPageSize(newPage.getPageSize());
		page.setPageNo(newPage.getPageNo());
		page.setOrderBy(newPage.getOrderBy());
		page.setCount(newPage.getCount());
		page.setFuncName(newPage.getFuncName());
		page.setFuncParam(newPage.getFuncParam());
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newPage.getList()){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
			page.setList(list);
		}
		model.addAttribute("page", page);
		return "modules/charts/inspectorNncPieDetailInfo";
	}*/

	@RequestMapping(value = "findLzcDetailInfoList")
	public String findLzcDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findLzcDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorLzcDetailInfo";
	}

	@RequestMapping(value = "findLzcPieDetailInfoList")
	public String findLzcPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findLzcPieDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorLzcDetailInfo";
	}

	@RequestMapping(value = "findBhcDetailInfoList")
	public String findBhcDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findBhcDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorBhcDetailInfo";
	}

	@RequestMapping(value = "findBhcPieDetailInfoList")
	public String findBhcPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairDcwtLibrary> newList = affairDcwtLibraryService.findBhcPieDetailInfoList(affairDcwtLibrary);
		Page<AffairDcwtLibrary> page = new Page<>();
		List<AffairDcwtLibrary> list = new ArrayList<AffairDcwtLibrary>();
		for (AffairDcwtLibrary a: newList){
			String[] catagorys = a.getProblemCategory().split(",");
			Set catSet = new HashSet();
			for(String catagory :catagorys){
				catSet.add(catagory);
			}
			if (catSet.contains(affairDcwtLibrary.getFlag())){
				list.add(a);
			}
		}
		model.addAttribute("page", list);
		return "modules/charts/inspectorBhcDetailInfo";
	}

	@RequestMapping(value = "findAllDcLibrary")
	public String findAllDcLibrary(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDcwtLibrary> page = affairDcwtLibraryService.findAllDcLibrary(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
		model.addAttribute("page", page);
		return "modules/charts/inspectorAllDcDetailInfo";
	}

	@RequestMapping(value = "findAllDcLibraryByJdUnit")
	public String findAllDcLibraryByJdUnit(AffairDcwtLibrary affairDcwtLibrary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDcwtLibrary> page = affairDcwtLibraryService.findAllDcLibraryByJdUnit(new Page<AffairDcwtLibrary>(request, response), affairDcwtLibrary);
		model.addAttribute("page", page);
		return "modules/charts/inspectorAllDcDetailInfo";
	}

}