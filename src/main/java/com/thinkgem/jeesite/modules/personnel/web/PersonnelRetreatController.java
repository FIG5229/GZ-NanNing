/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRetreat;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelRetreatService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 离退信息集Controller
 * @author cecil.li
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelRetreat")
public class PersonnelRetreatController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private PersonnelRetreatService personnelRetreatService;
	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public PersonnelRetreat get(@RequestParam(required=false) String id) {
		PersonnelRetreat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelRetreatService.get(id);
		}
		if (entity == null){
			entity = new PersonnelRetreat();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelRetreat:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelRetreat personnelRetreat, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelRetreat.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelRetreat> page = personnelRetreatService.findPage(new Page<PersonnelRetreat>(request, response), personnelRetreat);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		//处理离退休材料的文件名
		if(page != null && page.getList() != null && page.getList().size() > 0){
			for (PersonnelRetreat p: page.getList()) {
				if(p.getFilePath() != null && p.getFilePath().length() > 0){
					List<Map<String, String>> filePathList = uploadController.filePathHandle(p.getFilePath());
					String fileName = "";
					for (Map<String, String> filepath:filePathList) {
						fileName += filepath.get("fileName")+"、";
					}
					p.setFileName(fileName.substring(0,fileName.length() - 1));
				}
			}
		}
		return "modules/personnel/personnelRetreatList";
	}

	@RequiresPermissions("personnel:personnelRetreat:view")
	@RequestMapping(value = "form")
	public String form(PersonnelRetreat personnelRetreat, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelRetreat.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelRetreat", personnelRetreat);
		return "modules/personnel/personnelRetreatForm";
	}

	@RequiresPermissions("personnel:personnelRetreat:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelRetreat personnelRetreat, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelRetreat)){
			return form(personnelRetreat, model,request);
		}
		personnelRetreatService.save(personnelRetreat);
		addMessage(redirectAttributes, "保存离退信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelRetreatForm";
	}

	@RequiresPermissions("personnel:personnelRetreat:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelRetreat personnelRetreat, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelRetreat/?repage&idNumber="+personnelRetreat.getIdNumber();
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelRetreat/?repage&mType="+request.getParameter("mType").toString();
		}
		personnelRetreatService.delete(personnelRetreat);
		addMessage(redirectAttributes, "删除离退信息成功");
		return url;
	}
	@RequiresPermissions("personnel:personnelRetreat:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelRetreat personnelRetreat, Model model) {
		model.addAttribute("personnelRetreat", personnelRetreat);
		if(personnelRetreat.getFilePath() != null && personnelRetreat.getFilePath().length() > 0){
			List<Map<String, String>> RdygxsFileList = uploadController.filePathHandle(personnelRetreat.getFilePath());
			model.addAttribute("filePathList", RdygxsFileList);
		}
		return "modules/personnel/personnelRetreatFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelRetreat:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelRetreatService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("personnel:personnelRetreat:manage")
	@RequestMapping(value = {"manage"})
	public String manageList(PersonnelRetreat personnelRetreat, HttpServletRequest request, HttpServletResponse response, Model model) {
		personnelRetreat.setIsManage("1");//用于判断是否是审核人员
		Page<PersonnelRetreat> page = personnelRetreatService.findPage(new Page<PersonnelRetreat>(request, response), personnelRetreat);
		model.addAttribute("page", page);
		//处理离退休材料的文件名
		if(page != null && page.getList() != null && page.getList().size() > 0){
			for (PersonnelRetreat p: page.getList()) {
				if(p.getFilePath() != null && p.getFilePath().length() > 0){
					List<Map<String, String>> filePathList = uploadController.filePathHandle(p.getFilePath());
					String fileName = "";
					for (Map<String, String> filepath:filePathList) {
						fileName += filepath.get("fileName")+"、";
					}
					p.setFileName(fileName.substring(0,fileName.length() - 1));
				}
			}
		}
		return "modules/personnel/personnelRetreatManageList";
	}
	//这是用来跳部门审核dialog页面的
	@RequiresPermissions("personnel:personnelRetreat:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/personnel/personnelRetreatCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelRetreat:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(PersonnelRetreat personnelRetreat) {
		personnelRetreatService.shenHe(personnelRetreat);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
	/**
	 * 批量提交
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("personnel:personnelRetreat:edit")
	@RequestMapping(value = {"submitByIds"})
	public Result submitByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelRetreatService.submitByIds(ids);
			result.setSuccess(true);
			result.setMessage("提交成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要提交的内容");
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
	public String exportExcelByTemplate(PersonnelRetreat personnelRetreat, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelRetreat> page = null;
			if(flag == true){
				page = personnelRetreatService.findPage(new Page<PersonnelRetreat>(request, response), personnelRetreat);
			}else{
				page = personnelRetreatService.findPage(new Page<PersonnelRetreat>(request, response,-1), personnelRetreat);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelRetreat.class);
			exportExcelNew.setWb(wb);
			List<PersonnelRetreat> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelRetreat/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(HttpServletRequest request, MultipartFile file, RedirectAttributes redirectAttributes) {
		String isCover = request.getParameter("isCover");

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelRetreat> list = ei.getDataList(PersonnelRetreat.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelResume -> {
					if(personnelResume.getIdNumber()!=null){
						idNumbers.add(personnelResume.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelRetreatService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelRetreat personnelRetreat : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(personnelRetreat.getNowUnitName());
					if(orgId != null){
						personnelRetreat.setNowUnitNameId(orgId);
					}
					String nextId = officeService.findByName(personnelRetreat.getPayOrgName());
					if(orgId != null){
						personnelRetreat.setPayOrgNameId(nextId);
					}
					BeanValidators.validateWithException(validator, personnelRetreat);
					personnelRetreatService.save(personnelRetreat);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelRetreat.getName()+"(身份证号码:"+personnelRetreat.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	 * 离退休上传弹窗
	 * @param personnelRetreat
	 * @param model
	 * @param request
	 * @return
	 */
	@RequiresPermissions("personnel:personnelRetreat:manage")
	@RequestMapping(value = "uploadDialog")
	public String uploadDialog(PersonnelRetreat personnelRetreat, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelRetreat.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelRetreat", personnelRetreat);
		return "modules/personnel/personnelRetreatUploadForm";
	}

	@RequiresPermissions("personnel:personnelRetreat:manage")
	@RequestMapping(value = "upload")

	/**
	 * 上传离退休信息
	 */
	public String upload(PersonnelRetreat personnelRetreat, Model model,HttpServletRequest request) {
		if (!beanValidator(model, personnelRetreat)){
			return form(personnelRetreat, model,request);
		}
		personnelRetreatService.upload(personnelRetreat);
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelRetreatUploadForm";
	}
}