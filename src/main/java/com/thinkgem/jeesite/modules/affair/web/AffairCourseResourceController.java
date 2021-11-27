/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.xml.crypto.Data;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseClassDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseTeacherDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseUnitDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.*;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 课程资源Controller
 * @author alan.wu
 * @version 2020-07-31
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCourseResource")
public class AffairCourseResourceController extends BaseController {

	@Autowired
	private AffairCourseResourceService affairCourseResourceService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private IdGen idGen;

	@Autowired
	private AffairCourseTeacherService affairCourseTeacherService;

	@Autowired
	private AffairCourseDocumentService affairCourseDocumentService;

	@Autowired
	private AffairDocManagementService affairDocManagementService;

	@Autowired
	private AffairCourseClassDao affairCourseClassDao;
	
	@ModelAttribute
	public AffairCourseResource get(@RequestParam(required=false) String id) {
		AffairCourseResource entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCourseResourceService.get(id);
		}
		if (entity == null){
			entity = new AffairCourseResource();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCourseResource affairCourseResource, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCourseResource> page = affairCourseResourceService.findPage(new Page<AffairCourseResource>(request, response), affairCourseResource); 
		model.addAttribute("page", page);
		return "modules/affair/affairCourseResourceList";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "form")
	public String form(AffairCourseResource affairCourseResource, Model model,String type) {

		affairCourseResource.setType(type);
		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "SwfForm")
	public String SwfForm(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceSwfForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "GXForm")
	public String GXForm(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceGXForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "JXForm")
	public String JXform(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceJXForm";
	}
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "KQForm")
	public String KQForm(AffairCourseResource affairCourseResource, Model model,HttpSession session ,HttpServletRequest request, HttpServletResponse response) {
		String classId = affairCourseResource.getId();
		affairCourseResource.setClassId(classId);

		Object classId1 = session.getAttribute("classId");
		String ci1 = String.valueOf(classId1);

		if (StringUtils.isBlank(affairCourseResource.getClassId())){
			affairCourseResource.setClassId(ci1);
		}
		Page<AffairCourseResource> page = affairCourseResourceService.findPage(new Page<AffairCourseResource>(request, response), affairCourseResource);

		List<AffairCourseResource> list = page.getList();
		for (int y = 0;y < list.size(); y++){
			AffairCourseResource aff = list.get(y);
			aff.setClassId(classId);
		}

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceKQForm";
	}
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "FDForm")
	public String FDForm(AffairCourseResource affairCourseResource, Model model ) {

		String ci = affairCourseResource.getId();
		List<AffairCourseTeacher> affairCourseTeacher =  affairCourseTeacherService.selectBean(ci);

		model.addAttribute("affairCourseTeacher", affairCourseTeacher);
		return "modules/affair/affairCourseResourceFDForm";
	}
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "KHForm")
	public String KHform(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceKHForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "ZSForm")
	public String ZSForm(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceZSForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "XKForm")
	public String XKForm(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceXKForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "CKForm")
	public String CKForm(AffairCourseResource affairCourseResource, Model model ) {

		String id =  affairCourseResource.getId();

		List<AffairCourseDocument> list = affairCourseDocumentService.selectByClassId(id);

		model.addAttribute("list", list);
		return "modules/affair/affairCourseResourceCKForm";
	}
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "HDForm")
	public String HDForm(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceHDForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "KJForm")
	public String KJForm(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceKJForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "SPForm")
	public String SPForm(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceSPForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "KCForm")
	public String KCForm(AffairCourseResource affairCourseResource, Model model,HttpSession session) {

		String id = affairCourseResource.getId();
		affairCourseResource.setClassId(id);
		List<AffairCourseClass> list = affairCourseClassDao.selectBeanById(id);

		model.addAttribute("list", list);
		return "modules/affair/affairCourseClassList";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "formDe")
	public String formDe(AffairCourseResource affairCourseResource, Model model ) {

		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceFormDetail";
	}

	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "save")
	public String save(AffairCourseResource affairCourseResource, Model model, RedirectAttributes redirectAttributes,String type) {

		String id = affairCourseResource.getId();
		if (StringUtils.isNotBlank(type)){
			affairCourseResource.setType(type);
		}else{
			if (StringUtils.isNotBlank(id)){
				String tp = affairCourseResourceService.findType(id);
				affairCourseResource.setType(tp);
			}
		}


		String classId = affairCourseResource.getClassId();
		String claId = affairCourseResourceService.findClassId(id);
		if (StringUtils.isNotBlank(claId)){
			affairCourseResource.setClassId(claId);
			if (StringUtils.isBlank(classId)){
				String ci = idGen.getNextId();
				affairCourseResource.setClassId(ci);
			}
		}


		if (!beanValidator(model, affairCourseResource)){
			return form(affairCourseResource, model,type);
		}
		affairCourseResourceService.save(affairCourseResource);
		addMessage(redirectAttributes, "保存课程资源成功");
		model.addAttribute("saveResult","success");

		return "modules/affair/affairCourseResourceForm";
	}
	
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCourseResource affairCourseResource, RedirectAttributes redirectAttributes) {
		affairCourseResourceService.delete(affairCourseResource);
		addMessage(redirectAttributes, "删除课程资源成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCourseResource/?repage";
	}



	@ResponseBody
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCourseResourceService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = {"addByIds"})
	public Result addByIds(@RequestParam("ids[]") List<String> ids, HttpSession session) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){


			for (int i =0;i < ids.size();i++){
				String id = ids.get(i);
				AffairDocManagement affairDocManagement =  affairDocManagementService.selectBeanById(id);
				String name = affairDocManagement.getDocName();
				User creart = affairDocManagement.getCreateBy();
				Date time = affairDocManagement.getCreateDate();
				String state = affairDocManagement.getIsdownload();
				Object claId = session.getAttribute("classId");
				String classId = String.valueOf(claId);

				AffairCourseDocument affairCourseDocument = new AffairCourseDocument();
				affairCourseDocument.setClassId(classId);
				affairCourseDocument.setDocumentName(name);
				affairCourseDocument.setCreateBy(creart);
				affairCourseDocument.setCreateDate(time);
				affairCourseDocument.setState(state);

				affairCourseDocumentService.save(affairCourseDocument);
			}

			result.setSuccess(true);
			result.setMessage("添加成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要添加的文本");
		}
		return result;
	}


	/**
	 * 详情
	 *
	 * @param affairCourseResource
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCourseResource affairCourseResource, Model model) {
		model.addAttribute("affairCourseResource", affairCourseResource);
		return "modules/affair/affairCourseResourceFormDetail";
	}


	/**
	 * 导出excel格式数据
	 * @param affairCourseResource
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairCourseResource affairCourseResource, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairCourseResource.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairCourseResource> page = null;
			if (flag == true) {
				page = affairCourseResourceService.findPage(new Page<AffairCourseResource>(request, response), affairCourseResource);
			} else {
				page = affairCourseResourceService.findPage(new Page<AffairCourseResource>(request, response, -1), affairCourseResource);
			}

			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream) {
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairCourseResource.class);
			exportExcelNew.setWb(wb);
			List<AffairCourseResource> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/affair/affairCourseResource?repage";
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
			List<AffairCourseResource> list = ei.getDataList(AffairCourseResource.class);
			for (AffairCourseResource affairCourseResource : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairCourseResource.getUnit());
					if(orgId != null){
						affairCourseResource.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, affairCourseResource);
					String classId = idGen.getNextId();
					affairCourseResource.setClassId(classId);
					affairCourseResourceService.save(affairCourseResource);
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