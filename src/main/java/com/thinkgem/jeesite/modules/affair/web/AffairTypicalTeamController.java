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
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsUnitDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTypicalTeamVisitDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairNewsService;
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
import com.thinkgem.jeesite.modules.affair.service.AffairTypicalTeamService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 典型集体Controller
 * @author daniel.liu
 * @version 2020-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTypicalTeam")
public class AffairTypicalTeamController extends BaseController {

	@Autowired
	private AffairTypicalTeamService affairTypicalTeamService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairTypicalTeamVisitDao affairTypicalTeamChildDao;
	//添加走访记录字表
//	private AffairTypicalTeamChildDao affairTypicalTeamChildDao;

	@Autowired
	private AffairNewsService affairNewsService;

	@Autowired
	private AffairNewsUnitDao affairNewsUnitDao;

	@ModelAttribute
	public AffairTypicalTeam get(@RequestParam(required=false) String id) {
		AffairTypicalTeam entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTypicalTeamService.get(id);
		}
		if (entity == null){
			entity = new AffairTypicalTeam();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTypicalTeam:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTypicalTeam affairTypicalTeam, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTypicalTeam> page = affairTypicalTeamService.findPage(new Page<AffairTypicalTeam>(request, response), affairTypicalTeam);
		String id = UserUtils.getUser().getOffice().getId();

		String id1 = UserUtils.getUser().getId();

		List<AffairTypicalTeam> list = page.getList();
		for (int i = 0; i < list.size(); i++){
			AffairTypicalTeam affairTypicalTeam1 = list.get(i);
			String psDepartmentId = affairTypicalTeam1.getPsDepartmentId();
			String creatId = affairTypicalTeam1.getCreateBy().getId();
			if (id.equals(psDepartmentId) || id1.equals(creatId)){
				affairTypicalTeam1.setChangeType("1");
			}
		}
		model.addAttribute("page", page);
		return "modules/affair/affairTypicalTeamList";
	}

	@RequiresPermissions("affair:affairTypicalTeam:view")
	@RequestMapping(value = "form")
	public String form(AffairTypicalTeam affairTypicalTeam, Model model) {
		//修改时查询子表数据
		if (!StringUtils.isEmpty(affairTypicalTeam.getId())){
			AffairTypicalTeamVisit typicalTeamChild=new AffairTypicalTeamVisit();
			typicalTeamChild.setTypicalTeamId(affairTypicalTeam.getId());
			List<AffairTypicalTeamVisit> list=affairTypicalTeamChildDao.findList(typicalTeamChild);
			affairTypicalTeam.setTypicalTeamChildList(list);
			model.addAttribute("alter",true);
		}else {
			//导入字表时  先生成主表id 然后在保存
			/*导入子表时使用 ，不导入则无用*/
			IdGen idGen=new IdGen();
			String id = idGen.getNextId();
			model.addAttribute("id",id);
			model.addAttribute("alter",false);
		}
		if (StringUtils.isBlank(affairTypicalTeam.getPsDepartment())){
            affairTypicalTeam.setPsDepartment(UserUtils.getUser().getOffice().getName());
            affairTypicalTeam.setPsDepartmentId(UserUtils.getUser().getOffice().getId());
        }

		model.addAttribute("affairTypicalTeam", affairTypicalTeam);
		return "modules/affair/affairTypicalTeamForm";
	}
	@RequiresPermissions("affair:affairTypicalTeam:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTypicalTeam affairTypicalTeam, Model model) {

		//根据主键id查询子表数据
		AffairTypicalTeamVisit typicalTeamChild=new AffairTypicalTeamVisit();
		typicalTeamChild.setTypicalTeamId(affairTypicalTeam.getId());
		List<AffairTypicalTeamVisit> teamChildList=affairTypicalTeamChildDao.findList(typicalTeamChild);

		//根据单位Id 查询相关联的刊搞稿件
//		AffairNews affairNews=new AffairNews();
//		affairNews.setUnitId(affairTypicalTeam.getUnitId());
		AffairNewsUnit affairNewsUnit = new AffairNewsUnit();
		affairNewsUnit.setNewsUnitId(affairTypicalTeam.getUnitId());
		List<AffairNewsUnit> affairNewsUnitList = affairNewsUnitDao.findList(affairNewsUnit);
		affairNewsUnitList = affairNewsUnitList.stream().collect(
				Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getNewsId()))), ArrayList::new));

		List<AffairNews> newsList=new ArrayList<>();
		List<String> newsIdList = affairNewsUnitList.stream().map(item -> item.getNewsId()).collect(Collectors.toList());
		if (affairNewsUnitList != null && affairNewsUnitList.size()>0){
			newsList = affairNewsService.findLintInIds(newsIdList);
		}
//		affairNewsUnitList.stream().forEach(item -> {
//			newsList.add(affairNewsService.get(item.getNewsId()));
//		});
		model.addAttribute("newsList", newsList);

		affairTypicalTeam.setTypicalTeamChildList(teamChildList);
		model.addAttribute("affairTypicalTeam", affairTypicalTeam);
		if(affairTypicalTeam.getFilePath() != null && affairTypicalTeam.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTypicalTeam.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		if(affairTypicalTeam.getProgramme() != null && affairTypicalTeam.getProgramme().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTypicalTeam.getProgramme());
			model.addAttribute("programmeFileList", filePathList);
		}
		if(affairTypicalTeam.getMaterials() != null && affairTypicalTeam.getMaterials().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTypicalTeam.getMaterials());
			model.addAttribute("materialsFileList", filePathList);
		}
		return "modules/affair/affairTypicalTeamFormDetail";
	}

/*
	@RequiresPermissions("affair:affairTypicalTeam:edit")
*/
	@RequestMapping(value = "save")
	public String save(AffairTypicalTeam affairTypicalTeam, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTypicalTeam)){
			return form(affairTypicalTeam, model);
		}
		/*不为空则是导入子表*/
		String isImport = affairTypicalTeam.getIsImport();
		/*genId生成的Id*/
		String genId = request.getParameter("genId");
		if (!StringUtils.isEmpty(isImport) && isImport.equals("true")){

			affairTypicalTeam.setIsNewRecord(true);
			if (!StringUtils.isEmpty(genId)){
				affairTypicalTeam.setId(genId);
			}

		}
		affairTypicalTeamService.save(affairTypicalTeam);
		addMessage(redirectAttributes, "保存典型集体成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTypicalTeamForm";
	}
	
	@RequiresPermissions("affair:affairTypicalTeam:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTypicalTeam affairTypicalTeam, RedirectAttributes redirectAttributes) {
		affairTypicalTeamService.delete(affairTypicalTeam);
		//子表相关数据同时删除
		affairTypicalTeamChildDao.deleteById(affairTypicalTeam.getId());
		addMessage(redirectAttributes, "删除典型集体成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTypicalTeam/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTypicalTeam:edit")
	@RequestMapping(value = "deleteByIds")
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTypicalTeamService.deleteByIds(ids);
			//子表数据同时删除
			affairTypicalTeamChildDao.deleteByParentIds(ids);
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
	public String exportExcelByTemplate(AffairTypicalTeam affairTypicalTeam, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairTypicalTeam> page = null;
			if(flag == true){
				page = affairTypicalTeamService.findPage(new Page<AffairTypicalTeam>(request, response), affairTypicalTeam);
			}else{
				page = affairTypicalTeamService.findPage(new Page<AffairTypicalTeam>(request, response,-1), affairTypicalTeam);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTypicalTeam.class);
			exportExcelNew.setWb(wb);
			List<AffairTypicalTeam> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelExpert/?repage";
	}

	/*导出子表模板*/
	@RequestMapping(value = "exportVisitRecordFile")
	public boolean exportVisitRecordFile(AffairTypicalTeam affairTypicalTeam,HttpServletResponse response) {

		AffairTypicalTeamVisit typicalTeamVisit = new AffairTypicalTeamVisit();
		typicalTeamVisit.setTypicalTeamId(affairTypicalTeam.getId());
		List<AffairTypicalTeamVisit> list = affairTypicalTeamChildDao.findList(typicalTeamVisit);
		String fileSeperator = File.separator;
		String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
		String fileName = "典型培树-典型集体-走访记录.xlsx";
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTypicalTeamVisit.class);
			exportExcelNew.setWb(wb);
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTypicalTeam> list = ei.getDataList(AffairTypicalTeam.class);
			for (AffairTypicalTeam affairTypicalTeam : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairTypicalTeam.getUnit());
					if(orgId != null){
						affairTypicalTeam.setUnitId(orgId);
					}
					String psDepartmentId = officeService.findByName(affairTypicalTeam.getPsDepartment());
					if(psDepartmentId != null){
						affairTypicalTeam.setPsDepartmentId(psDepartmentId);
					}
					String pushOrganizationId = officeService.findByName(affairTypicalTeam.getPushOrganization());
					if(orgId != null){
						affairTypicalTeam.setPushOrganizationId(pushOrganizationId);
					}
					BeanValidators.validateWithException(validator, affairTypicalTeam);
					affairTypicalTeamService.save(affairTypicalTeam);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairTypicalTeam.getCreateBy().getOffice().getName()+"(姓名:"+affairTypicalTeam.getCreateBy().getName()+")"+" 导入失败："+ex.getMessage());
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

    @RequestMapping(value = "importVisitRecordFile", method= RequestMethod.POST)
	public String importVisitRecordFile(MultipartFile file, RedirectAttributes redirectAttributes,String id,Model model) {

		AffairTypicalTeam affairTypicalPerson = new AffairTypicalTeam();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTypicalTeamVisit> list = ei.getDataList(AffairTypicalTeamVisit.class);
			affairTypicalPerson.setTypicalTeamChildList(list);
			for (AffairTypicalTeamVisit typicalTeamVisit : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(typicalTeamVisit.getUnit());
					if(orgId != null){
						typicalTeamVisit.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, typicalTeamVisit);
					typicalTeamVisit.setTypicalTeamId(id);
					typicalTeamVisit.preInsert();
					affairTypicalTeamChildDao.insert(typicalTeamVisit);
//					affairTypicalPersonService.save(typicalPersonVisit);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(typicalTeamVisit.getCreateBy().getOffice().getName()+"(姓名:"+typicalTeamVisit.getCreateBy().getName()+")"+" 导入失败："+ex.getMessage());
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
		model.addAttribute("result","success");
		model.addAttribute("url","/affair/affairTypicalTeam/importVisitRecordFile");
		return "modules/affair/child_template_import";
	}

	/**
	 * 子表导入
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
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
		request.setAttribute("url","/affair/affairTypicalTeam/importVisitRecordFile");
		return "modules/affair/child_template_import";
	}

	/**
	 * 查询子表信息
	 * @param id	外键
	 * @return
	 */
	@RequestMapping("getChildById")
	@ResponseBody
	public Result getChildById(String id){
		Result result= new Result();
		AffairTypicalTeamVisit typicalPersonVisit  = new AffairTypicalTeamVisit();
		typicalPersonVisit.setTypicalTeamId(id);
		try {
			List<AffairTypicalTeamVisit> list = affairTypicalTeamChildDao.findList(typicalPersonVisit);
			result.setResult(list);
			result.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = {"typicalDetail"})
	public String typicalDetailPage(AffairTypicalTeam affairTypicalTeam, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTypicalTeam> page = affairTypicalTeamService.findTypicalDetailPage(new Page<AffairTypicalTeam>(request, response), affairTypicalTeam);
		model.addAttribute("page", page);
		return "modules/charts/chartTypicalTeamList";
	}
}