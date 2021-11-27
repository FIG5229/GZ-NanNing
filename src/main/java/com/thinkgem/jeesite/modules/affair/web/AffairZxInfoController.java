/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.dao.AffairZxInfoChildDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairZxInfo;
import com.thinkgem.jeesite.modules.affair.entity.AffairZxInfoChild;
import com.thinkgem.jeesite.modules.affair.service.AffairZxInfoService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 助学管理Controller
 * @author cecil.li
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairZxInfo")
public class AffairZxInfoController extends BaseController {

	@Autowired
	private UploadController uploadController;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairZxInfoService affairZxInfoService;

	@Autowired
	private AffairZxInfoChildDao affairZxInfoChildDao;


	@ModelAttribute
	public AffairZxInfo get(@RequestParam(required=false) String id) {
		AffairZxInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairZxInfoService.get(id);
		}
		if (entity == null){
			entity = new AffairZxInfo();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairZxInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairZxInfo affairZxInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairZxInfo> page = affairZxInfoService.findPage(new Page<AffairZxInfo>(request, response), affairZxInfo);
		/*	信息全部保存到主表，子表只临时存储使用模板导入时的数据
		if (page.getList() != null && page.getList().size() > 0){
			for (AffairZxInfo a: page.getList()) {
				List<AffairZxInfoChild> childList = new ArrayList<>();
				childList = affairZxInfoChildDao.findSomeByZxInfoId(a.getId());
				a.setAffairZxInfoChildList(childList);
			}
		}*/
		model.addAttribute("page", page);
		return "modules/affair/affairZxInfoList";
	}

	@RequiresPermissions("affair:affairZxInfo:view")
	@RequestMapping(value = "form")
	public String form(AffairZxInfo affairZxInfo, Model model) {
		if(affairZxInfo.getFilePath() != null && affairZxInfo.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairZxInfo.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		if (StringUtils.isNotBlank(affairZxInfo.getId())){
			AffairZxInfoChild affairZxInfoChild = new AffairZxInfoChild();
			affairZxInfoChild.setZxInfoId(affairZxInfo.getId());
			List<AffairZxInfoChild> childList = affairZxInfoChildDao.findList(affairZxInfoChild);
			affairZxInfo.setAffairZxInfoChildList(childList);
		}else {
			//导入字表时  先生成主表id 然后在保存
			/*导入子表时使用 ，不导入则无用*/
			IdGen idGen=new IdGen();
			String id = idGen.getNextId();
			model.addAttribute("id",id);
			model.addAttribute("alter",false);
		}
		model.addAttribute("affairZxInfo", affairZxInfo);
		return "modules/affair/affairZxInfoForm";
	}

	@RequiresPermissions("affair:affairZxInfo:view")
	@RequestMapping(value = "add")
	public String add(AffairZxInfo affairZxInfo, Model model) {

		//导入字表时  先生成主表id 然后在保存
		/*导入子表时使用 ，不导入则无用*/
		IdGen idGen = new IdGen();
		String id = idGen.getNextId();
		model.addAttribute("id", id);
		model.addAttribute("alter", false);

		model.addAttribute("affairZxInfo", affairZxInfo);
		if (affairZxInfo.isHandleAdd()) {
			/*再操作里添加子女*/
			return "modules/affair/affairZxInfoHandleAdd";
		} else {
			return "modules/affair/affairZxInfoAdd";
		}
	}

	@RequiresPermissions("affair:affairZxInfo:edit")
	@RequestMapping(value = "save")
	public String save(AffairZxInfo affairZxInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairZxInfo)){
			return form(affairZxInfo, model);
		}
		affairZxInfoService.save(affairZxInfo);
		addMessage(redirectAttributes, "保存助学管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairZxInfoForm";
	}

	/*添加保存*/
	@RequiresPermissions("affair:affairZxInfo:edit")
	@RequestMapping(value = "addSave")
	public String addSave(AffairZxInfo affairZxInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairZxInfo)){
			return form(affairZxInfo, model);
		}
		affairZxInfoService.addSave(affairZxInfo);
		addMessage(redirectAttributes, "保存助学管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairZxInfoForm";
	}

	@RequiresPermissions("affair:affairZxInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairZxInfo affairZxInfo, RedirectAttributes redirectAttributes) {
		affairZxInfoService.delete(affairZxInfo);
		affairZxInfoChildDao.deleteByMainId(affairZxInfo.getId());
		addMessage(redirectAttributes, "删除助学管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairZxInfo/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairZxInfo:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairZxInfoService.deleteByIds(ids);
			affairZxInfoChildDao.deleteByMainIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 * @param affairZxInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairZxInfo:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairZxInfo affairZxInfo, Model model) {
		if(affairZxInfo.getFilePath() != null && affairZxInfo.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairZxInfo.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		AffairZxInfoChild affairZxInfoChild = new AffairZxInfoChild();
		affairZxInfoChild.setZxInfoId(affairZxInfo.getId());
		List<AffairZxInfoChild> childList = affairZxInfoChildDao.findList(affairZxInfoChild);
		affairZxInfo.setAffairZxInfoChildList(childList);
		model.addAttribute("affairZxInfo", affairZxInfo);
		return "modules/affair/affairZxInfoFormDetail";
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
	public String exportExcelByTemplate(AffairZxInfo affairZxInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairZxInfo> page = null;
			if(flag == true){
				page = affairZxInfoService.findPage(new Page<AffairZxInfo>(request, response), affairZxInfo);
			}else {
				page = affairZxInfoService.findPage(new Page<AffairZxInfo>(request, response,-1), affairZxInfo);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(3, AffairZxInfo.class);
			exportExcelNew.setWb(wb);
			/*主表数据*/
			List<AffairZxInfo> list =page.getList();
//			List<AffairZxInfoChild> childList =new ArrayList<>();
//			List<AffairZxInfoChild> resultdList =new ArrayList<>();
		/*	for (AffairZxInfo item : list){
				AffairZxInfoChild affairZxInfoChild = new AffairZxInfoChild();
				affairZxInfoChild.setZxInfoId(item.getId());
				childList = affairZxInfoChildDao.findList(affairZxInfoChild);
				if (childList != null && childList.size()>0){
					for (AffairZxInfoChild child : childList){
						child.setDate(item.getDate());
						child.setUnit(item.getUnit());
						child.setPoliceName(item.getName());
						child.setIdNumber(item.getIdNumber());
						child.setPoliceSex(item.getSex());
						child.setPoliceType(item.getType());
//						child.setMoney(item.getMoney());
						resultdList.add(child);
					}

				}else {
					AffairZxInfoChild child = new AffairZxInfoChild();
					child.setDate(item.getDate());
					child.setUnit(item.getUnit());
					if (StringUtils.isNotBlank(item.getUnit())){
						String unitId = officeService.findByName(item.getUnit());
						child.setUnitId(unitId);
					}
					child.setPoliceName(item.getName());
					child.setIdNumber(item.getIdNumber());
					child.setPoliceSex(item.getSex());
					child.setPoliceType(item.getType());
//					child.setMoney(item.getMoney());
					resultdList.add(child);
				}

//				resultdList.addAll(childList);

			}*/

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
		//修改
		return "redirect:" + adminPath + "/affair/affairZxInfo/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			String unitId;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 3, 0);
			//修改
			List<AffairZxInfo> list = ei.getDataList(AffairZxInfo.class);
			for (AffairZxInfo affairZxInfo : list){
				try{
					BeanValidators.validateWithException(validator, affairZxInfo);
					if(affairZxInfo.getUnit()!=null&&!"".equals(affairZxInfo.getUnit())){
						affairZxInfo.setUnitId(officeService.findByName(affairZxInfo.getUnit()));
					}
					affairZxInfoService.save(affairZxInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairZxInfo.getUnit()+")"+" 导入失败："+ex.getMessage());
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

	/*
	* 	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			String unitId;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 3, 0);
			//修改
			List<AffairZxInfo> list = ei.getDataList(AffairZxInfo.class);
			for (AffairZxInfo affairZxInfo : list){
				try{
					BeanValidators.validateWithException(validator, affairZxInfo);
					if(affairZxInfo.getUnit()!=null&&!"".equals(affairZxInfo.getUnit())){
						affairZxInfo.setUnitId(officeService.findByName(affairZxInfo.getUnit()));
					}
					AffairZxInfo zxInfo = new AffairZxInfo();
//					zxInfo.setIdNumber(affairZxInfo.getIdNumber());
					zxInfo.setUnit(affairZxInfo.getUnit());
					zxInfo.setType(affairZxInfo.getType());
					zxInfo.setName(affairZxInfo.getName());
					List<AffairZxInfo> affairZxInfos = affairZxInfoService.findList(zxInfo);

					AffairZxInfoChild affairZxInfoChild = new AffairZxInfoChild();
					if (affairZxInfos != null && affairZxInfos.size()>0){
						affairZxInfo.setId(affairZxInfos.get(0).getId());
						affairZxInfoChild.setName(affairZxInfo.getChildName());
						affairZxInfoChild.setSex(affairZxInfo.getChildSex());
						affairZxInfoChild.setBirthday(affairZxInfo.getChildBirthday());
						affairZxInfoChild.setSchoolMajor(affairZxInfo.getChildSchoolMajor());
						affairZxInfoChild.setMajor(affairZxInfo.getChildMajor());
						affairZxInfoChild.setType(affairZxInfo.getChildSchoolType());
						affairZxInfoChild.setYearSystem(affairZxInfo.getChildYearSystem());
						affairZxInfoChild.setGrade(affairZxInfo.getChildGrade());
						affairZxInfoChild.setMoney(affairZxInfo.getMoney());
						affairZxInfoChild.setRemarks(affairZxInfo.getRemarks());
					}
					List<AffairZxInfoChild> childList = new ArrayList<>();
					childList.add(affairZxInfoChild);
					affairZxInfo.setAffairZxInfoChildList(childList);
					affairZxInfoService.save(affairZxInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairZxInfo.getUnit()+")"+" 导入失败："+ex.getMessage());
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
	}*/

	@RequestMapping(value = "exportChild")
	public String exportChild (AffairZxInfo affairZxInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		List<AffairZxInfoChild> childList = Arrays.asList();
		if (StringUtils.isNotBlank(affairZxInfo.getId())) {

			AffairZxInfoChild affairZxInfoChild = new AffairZxInfoChild();
			affairZxInfoChild.setZxInfoId(affairZxInfo.getId());
			childList = affairZxInfoChildDao.findList(affairZxInfoChild);
			for (AffairZxInfoChild item : childList) {
				item.setDate(affairZxInfo.getDate());
				item.setUnit(affairZxInfo.getUnit());
				item.setPoliceType(affairZxInfo.getType());
				item.setShun(affairZxInfo.getShun());
				item.setPoliceName(affairZxInfo.getName());
				item.setMedicaNumber(affairZxInfo.getMedicaNumber());
				item.setPoliceName(affairZxInfo.getName());
			}
		}
		try {
			XSSFWorkbook wb = null;
			String fileName = "金秋助学.xlsx";
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
//修改
			ExportExcelNew exportExcelNew = new ExportExcelNew(3, AffairZxInfoChild.class);
			exportExcelNew.setWb(wb);
			exportExcelNew.setDataList(childList);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();

		}
		catch (Exception e){
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return null;
	}

	@RequestMapping(value = "child/import")
	public String childDownload(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String id ="";
		if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
			id=request.getParameter("id").toString();
			request.setAttribute("id",id);
		}
		if(null != request.getParameter("fileName") && !"".equals(request.getParameter("fileName"))){
			String template=request.getParameter("fileName").toString();
			request.setAttribute("template",template);
		}
		request.setAttribute("url","/affair/affairZxInfo/importChild");
		return "modules/affair/child_template_import";
	}

	@RequestMapping(value = "importChild", method=RequestMethod.POST)
	public String importChildFile(MultipartFile file, RedirectAttributes redirectAttributes,String id,Model model) {

		AffairZxInfo affairZxInfo = new AffairZxInfo();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 3, 0);
			List<AffairZxInfoChild> list = ei.getDataList(AffairZxInfoChild.class);
			affairZxInfo.setAffairZxInfoChildList(list);
			for (AffairZxInfoChild zxInfoChild : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(zxInfoChild.getUnit());
					if(orgId != null){
						zxInfoChild.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, zxInfoChild);
					zxInfoChild.setZxInfoId(id);
					zxInfoChild.preInsert();
					affairZxInfoChildDao.insert(zxInfoChild);
					successNum++;
				}catch(ConstraintViolationException ex){
					ex.printStackTrace();
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					ex.printStackTrace();
					failureMsg.append(zxInfoChild.getCreateBy().getOffice().getName()+"(姓名:"+zxInfoChild.getCreateBy().getName()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		addMessage(redirectAttributes, "已成功导入 ");
		redirectAttributes.addFlashAttribute("result","success");
		redirectAttributes.addFlashAttribute("affairZxInfo",affairZxInfo);
		model.addAttribute("result","success");
		model.addAttribute("url","/affair/affairZxInfo/importChild");
		return "modules/affair/child_template_import";
	}

	@RequestMapping("getChildById")
	@ResponseBody
	public Result getChildById(String id){
		Result result= new Result();
		AffairZxInfoChild affairZxInfoChild  = new AffairZxInfoChild();
		affairZxInfoChild.setZxInfoId(id);
		try {
			List<AffairZxInfoChild> list = affairZxInfoChildDao.findList(affairZxInfoChild);
			result.setResult(list);
			result.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "studyAidDetail")
	public String studyAid(AffairZxInfo affairZxInfo,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairZxInfo> page = affairZxInfoService.findStudyAidPage(new Page<AffairZxInfo>(request,response),affairZxInfo);
		model.addAttribute("page",page);
		return "modules/charts/chartZxInfoList";
	}
}