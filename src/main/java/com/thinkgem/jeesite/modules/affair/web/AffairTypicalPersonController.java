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
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsNameDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalPersonVisitDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairNewsService;
import com.thinkgem.jeesite.modules.affair.service.AffairRewardInquireService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.service.AffairTypicalPersonService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 典型人物Controller
 * @author daniel.liu
 * @version 2020-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTypicalPerson")
public class AffairTypicalPersonController extends BaseController {

	@Autowired
	private AffairTypicalPersonService affairTypicalPersonService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UploadController uploadController;

	/*子表*/
	@Autowired
	private AffairTypicalPersonVisitDao newsService;

	@Autowired
	private AffairNewsService affairNewsService;

	@Autowired
	private AffairTypicalPersonVisitDao affairTypicalPersonVisitDao;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AffairRewardInquireService affairRewardInquireService;

	@Autowired
	private AffairNewsNameDao affairNewsNameDao;

	@ModelAttribute
	public AffairTypicalPerson get(@RequestParam(required=false) String id) {
		AffairTypicalPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTypicalPersonService.get(id);
		}
		if (entity == null){
			entity = new AffairTypicalPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTypicalPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTypicalPerson affairTypicalPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTypicalPerson> page = affairTypicalPersonService.findPage(new Page<AffairTypicalPerson>(request, response), affairTypicalPerson);

		String id = UserUtils.getUser().getOffice().getId();

		String id1 = UserUtils.getUser().getId();

		List<AffairTypicalPerson> list = page.getList();
		for (int i = 0; i < list.size(); i++){
			AffairTypicalPerson affairTypicalPerson1 = list.get(i);
			String psDepartmentId = affairTypicalPerson1.getPsDepartmentId();
			String id2 = affairTypicalPerson1.getCreateBy().getId();
			if (id.equals(psDepartmentId) || id1.equals(id2)){
				affairTypicalPerson1.setChangeType("1");
			}
		}

		model.addAttribute("page", page);
		return "modules/affair/affairTypicalPersonList";
	}
	
 	@RequiresPermissions("affair:affairTypicalPerson:view")
	@RequestMapping(value = "form")
	public String form(AffairTypicalPerson affairTypicalPerson, Model model) {
		//修改时查询子表数据
		if (!StringUtils.isEmpty(affairTypicalPerson.getId())){
			AffairTypicalPersonVisit affairTypicalPersonNews=new AffairTypicalPersonVisit();
			affairTypicalPersonNews.setTypicalPersonId(affairTypicalPerson.getId());
			List<AffairTypicalPersonVisit> list=newsService.findList(affairTypicalPersonNews);
			affairTypicalPerson.setPersonNewsList(list);
			model.addAttribute("alter",true);
		}else {
			//导入字表时  先生成主表id 然后在保存
			/*导入子表时使用 ，不导入则无用*/
			IdGen idGen=new IdGen();
			String id = idGen.getNextId();
			model.addAttribute("id",id);
			model.addAttribute("alter",false);
		}
		if (StringUtils.isBlank(affairTypicalPerson.getPsDepartment())){
			affairTypicalPerson.setPsDepartment(UserUtils.getUser().getOffice().getName());
			affairTypicalPerson.setPsDepartmentId(UserUtils.getUser().getOffice().getId());
		}
		model.addAttribute("affairTypicalPerson", affairTypicalPerson);
		return "modules/affair/affairTypicalPersonForm";
	}
	@RequiresPermissions("affair:affairTypicalPerson:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTypicalPerson affairTypicalPerson, Model model) {
		/*通过典型培树主键查询子表事迹材料*/
		AffairTypicalPersonVisit personNews=new AffairTypicalPersonVisit();
		personNews.setTypicalPersonId(affairTypicalPerson.getId());
		List<AffairTypicalPersonVisit> list=newsService.findList(personNews);
		//查询到的事迹材料添加到 典型培树中
		affairTypicalPerson.setPersonNewsList(list);
		model.addAttribute("affairTypicalPerson", affairTypicalPerson);
		if(affairTypicalPerson.getMaterials() != null && affairTypicalPerson.getMaterials().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTypicalPerson.getMaterials());
			model.addAttribute("materialsFileList", filePathList);
		}
		if(affairTypicalPerson.getPsProgramme() != null && affairTypicalPerson.getPsProgramme().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTypicalPerson.getPsProgramme());
			model.addAttribute("programmeFileList", filePathList);
		}
		if(affairTypicalPerson.getFilePath() != null && affairTypicalPerson.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTypicalPerson.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}

		/*9.2问题反馈需求
			2、典型人物查看自动获取曾获荣誉、宣传报道、专题片（根据关键词添加标题）、简报（根据关键词添加标题），表格显示
		*/
		/*奖励查询*/
		AffairRewardInquire affairRewardInquire = new AffairRewardInquire();
		affairRewardInquire.setName(affairTypicalPerson.getName());
		List<AffairRewardInquire> rewardInquireList = affairRewardInquireService.findList(affairRewardInquire);
		model.addAttribute("rewardInquireList", rewardInquireList);
		//查找相关的刊搞宣传材料
		//通过身份证
		AffairNews affairNews=new AffairNews();
		affairNews.setIdNumber(affairTypicalPerson.getIdNumber());
		//刊搞查询时必须有单位
		//单位为典型人物的单位
		affairNews.setUnitId(affairTypicalPerson.getUnitId());
		AffairNewsName affairNewsName = new AffairNewsName();
		affairNewsName.setNewsIdNumber(affairTypicalPerson.getIdNumber());
		List<AffairNewsName> newsNameList = affairNewsNameDao.findList(affairNewsName);
		newsNameList = newsNameList.stream().collect(
				Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getNewsId()))), ArrayList::new));

		List<AffairNews> newsList=new ArrayList<>();
		newsNameList.stream().forEach(item -> {
			newsList.add(affairNewsService.get(item.getNewsId()));
		});
//		newsList.stream().filter(item -> item.getIdNumber()==affairTypicalPerson.getIdNumber()).collect(Collectors.toList());
		model.addAttribute("newsList", newsList);

		/*专题片 简报*/
		Article article = new Article();
		article.setKeywords(affairTypicalPerson.getName());
		List<Article> articleList = articleService.findList(article);
		model.addAttribute("articleList", articleList);
		return "modules/affair/affairTypicalPersonFormDetail";
	}

/*
	@RequiresPermissions("affair:affairTypicalPerson:edit")
*/
	@RequestMapping(value = "save")
	public String save(AffairTypicalPerson affairTypicalPerson, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTypicalPerson)){
			return form(affairTypicalPerson, model);
		}
		/*不为空则是导入子表*/
		String isImport = affairTypicalPerson.getIsImport();
		/*genId生成的Id*/
		String genId = request.getParameter("genId");
		if (!StringUtils.isEmpty(isImport) && isImport.equals("true")){

			affairTypicalPerson.setIsNewRecord(true);
			if (!StringUtils.isEmpty(genId)){
				affairTypicalPerson.setId(genId);
			}

		}
		//如果是导入字表时生成的Id  则强行插入
		/*personnelPoliceCertificate.setIsNewRecord(true);*/
		//保存典型人物时 保存事迹材料子表
		affairTypicalPersonService.save(affairTypicalPerson);
		addMessage(redirectAttributes, "保存典型人物成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTypicalPersonForm";
	}
	
	@RequiresPermissions("affair:affairTypicalPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTypicalPerson affairTypicalPerson, RedirectAttributes redirectAttributes) {
		affairTypicalPersonService.delete(affairTypicalPerson);
		newsService.deleteByParentId(affairTypicalPerson.getId());
		addMessage(redirectAttributes, "删除典型人物成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTypicalPerson/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTypicalPerson:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTypicalPersonService.deleteByIds(ids);
			newsService.deleteByParentIds(ids);
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
	public String exportExcelByTemplate(AffairTypicalPerson affairTypicalPerson, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairTypicalPerson> page = null;
			if(flag == true){
				page = affairTypicalPersonService.findPage(new Page<AffairTypicalPerson>(request, response), affairTypicalPerson);
			}else{
				page = affairTypicalPersonService.findPage(new Page<AffairTypicalPerson>(request, response,-1), affairTypicalPerson);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTypicalPerson.class);
			exportExcelNew.setWb(wb);
			List<AffairTypicalPerson> list =page.getList();
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
		return "redirect:"+Global.getAdminPath()+"/affair/affairTypicalPerson/?repage";
	}

	/*导出子表模板*/
	@RequestMapping(value = "exportVisitRecordFile")
	public String exportVisitRecordFile(AffairTypicalPerson affairTypicalPerson,HttpServletResponse response) {
		AffairTypicalPersonVisit personVisit = new AffairTypicalPersonVisit();
		personVisit.setTypicalPersonId(affairTypicalPerson.getId());
		List<AffairTypicalPersonVisit> list = newsService.findList(personVisit);
		if (StringUtils.isBlank(affairTypicalPerson.getId())){
			list = new ArrayList<>();
		}

		String fileSeperator = File.separator;
		String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
		String fileName = "典型培树-典型个人-走访记录.xlsx";
		String path = filePath+fileName;
		XSSFWorkbook wb = null;
		try
		{
			InputStream inputStream = new FileInputStream(path);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTypicalPersonVisit.class);
			exportExcelNew.setWb(wb);
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
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairTypicalPerson/?repage";
	}

	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTypicalPerson> list = ei.getDataList(AffairTypicalPerson.class);
			for (AffairTypicalPerson affairTypicalPerson : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairTypicalPerson.getUnit());
					if(orgId != null){
						affairTypicalPerson.setUnitId(orgId);
					}
					String psDepartmentId = officeService.findByName(affairTypicalPerson.getPsDepartment());
					if(psDepartmentId != null){
						affairTypicalPerson.setPsDepartmentId(psDepartmentId);
					}
					String pushOrganizationId = officeService.findByName(affairTypicalPerson.getPushOrganization());
					if(pushOrganizationId != null){
						affairTypicalPerson.setPushOrganizationId(orgId);
					}
					BeanValidators.validateWithException(validator, affairTypicalPerson);
					affairTypicalPersonService.save(affairTypicalPerson);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairTypicalPerson.getCreateBy().getOffice().getName()+"(姓名:"+affairTypicalPerson.getCreateBy().getName()+")"+" 导入失败："+ex.getMessage());
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
	 * 导入子表
	 * @param file
	 * @param redirectAttributes
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "importVisitRecordFile", method= RequestMethod.POST)
	public String importVisitRecordFile(MultipartFile file, RedirectAttributes redirectAttributes,String id,Model model) {

		AffairTypicalPerson affairTypicalPerson = new AffairTypicalPerson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTypicalPersonVisit> list = ei.getDataList(AffairTypicalPersonVisit.class);
			affairTypicalPerson.setPersonNewsList(list);
			for (AffairTypicalPersonVisit typicalPersonVisit : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(typicalPersonVisit.getUnit());
					if(orgId != null){
						typicalPersonVisit.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, typicalPersonVisit);
					typicalPersonVisit.setTypicalPersonId(id);
					typicalPersonVisit.preInsert();
					affairTypicalPersonVisitDao.insert(typicalPersonVisit);
//					affairTypicalPersonService.save(typicalPersonVisit);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(typicalPersonVisit.getCreateBy().getOffice().getName()+"(姓名:"+typicalPersonVisit.getCreateBy().getName()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		addMessage(redirectAttributes, "已成功导入 ");
		redirectAttributes.addFlashAttribute("result","success");
		redirectAttributes.addFlashAttribute("affairTypicalPerson",affairTypicalPerson);
		model.addAttribute("result","success");
//		return "redirect:" + adminPath + "/affair/affairTypicalPerson/template/import";
		model.addAttribute("url","/affair/affairTypicalPerson/importVisitRecordFile");
		return "modules/affair/child_template_import";
	}

	@RequestMapping(value = "template/import")
	public String download(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String id ="";
		if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
			id=request.getParameter("id").toString();
			request.setAttribute("id",id);
		}
		if(null != request.getParameter("fileName") && !"".equals(request.getParameter("fileName"))){
			String template=request.getParameter("fileName").toString();
			request.setAttribute("template",template);
		}
		request.setAttribute("url","/affair/affairTypicalPerson/importVisitRecordFile");
		return "modules/affair/child_template_import";
	}

	@RequestMapping("getChildById")
	@ResponseBody
	public Result getChildById(String id){
		Result result= new Result();
		AffairTypicalPersonVisit typicalPersonVisit  = new AffairTypicalPersonVisit();
		typicalPersonVisit.setTypicalPersonId(id);
		try {
			List<AffairTypicalPersonVisit> list = newsService.findList(typicalPersonVisit);
			result.setResult(list);
			result.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "typicalDetail")
	public String typicalDetail(AffairTypicalPerson affairTypicalPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTypicalPerson> page = affairTypicalPersonService.findTypicalDetailPage(new Page<AffairTypicalPerson>(request, response), affairTypicalPerson);
		model.addAttribute("page", page);
		return "modules/charts/chartTypicalPersonList";
	}
}