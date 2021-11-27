/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairLdgblzFile;
import com.thinkgem.jeesite.modules.affair.service.AffairLdgblzFileService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 领导廉政干部档案表Controller
 * @author eav.liu
 * @version 2020-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLdgblzFile")
public class AffairLdgblzFileController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairLdgblzFileService affairLdgblzFileService;

	@Autowired
	private PersonnelBaseService personnelBaseService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairLdgblzFile get(@RequestParam(required=false) String id) {
		AffairLdgblzFile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLdgblzFileService.get(id);
		}
		if (entity == null){
			entity = new AffairLdgblzFile();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLdgblzFile:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLdgblzFile affairLdgblzFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("6".equals(UserUtils.getUser().getOffice().getId())){
			affairLdgblzFile.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairLdgblzFile.setUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairLdgblzFile.setCreateBy(UserUtils.getUser());
		Page<AffairLdgblzFile> page = affairLdgblzFileService.findPage(new Page<AffairLdgblzFile>(request, response), affairLdgblzFile);
		model.addAttribute("page", page);
		return "modules/affair/affairLdgblzFileList";
	}

	@RequiresPermissions("affair:affairLdgblzFile:edit")
	@RequestMapping(value = "form")
	public String form(AffairLdgblzFile affairLdgblzFile, Model model) {
		model.addAttribute("affairLdgblzFile", affairLdgblzFile);
		return "modules/affair/affairLdgblzFileForm";
	}

	@RequiresPermissions("affair:affairLdgblzFile:edit")
	@RequestMapping(value = "save")
	public String save(AffairLdgblzFile affairLdgblzFile, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairLdgblzFile)){
			return form(affairLdgblzFile, model);
		}
		affairLdgblzFileService.save(affairLdgblzFile);
		addMessage(redirectAttributes, "保存领导廉政干部档案成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairLdgblzFileForm";
	}
	
	@RequiresPermissions("affair:affairLdgblzFile:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairLdgblzFile affairLdgblzFile, RedirectAttributes redirectAttributes) {
		affairLdgblzFileService.delete(affairLdgblzFile);
		addMessage(redirectAttributes, "删除领导廉政干部档案成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLdgblzFile/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLdgblzFile:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLdgblzFileService.deleteByIds(ids);
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
	 * @param affairLdgblzFile
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairLdgblzFile:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLdgblzFile affairLdgblzFile, Model model) {
		model.addAttribute("affairLdgblzFile", affairLdgblzFile);
		if(affairLdgblzFile.getAnnex() != null && affairLdgblzFile.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairLdgblzFile.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairLdgblzFileFormDetail";
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
	public String exportExcelByTemplate(AffairLdgblzFile affairLdgblzFile, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			if ("6".equals(UserUtils.getUser().getOffice().getId())){
				affairLdgblzFile.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairLdgblzFile.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairLdgblzFile.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairLdgblzFile> page = null;
			if(flag == true){
				page = affairLdgblzFileService.findPage(new Page<AffairLdgblzFile>(request, response), affairLdgblzFile);
			}else{
				page = affairLdgblzFileService.findPage(new Page<AffairLdgblzFile>(request, response,-1), affairLdgblzFile);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLdgblzFile.class);
			exportExcelNew.setWb(wb);
			List<AffairLdgblzFile> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairLdgblzFile/?repage";
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
			List<AffairLdgblzFile> list = ei.getDataList(AffairLdgblzFile.class);
			for (AffairLdgblzFile affairLdgblzFile : list){
				try{
					affairLdgblzFile.setUnitId(officeService.findByName(affairLdgblzFile.getUnit()));

					BeanValidators.validateWithException(validator, affairLdgblzFile);
					//根据身份证号同步政工人员基本信息数据
					if(StringUtils.isNotBlank(affairLdgblzFile.getIdNumber())){
						PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(affairLdgblzFile.getIdNumber());
						if(personnelBase != null){
							affairLdgblzFile.setName(personnelBase.getName());
							affairLdgblzFile.setSex(personnelBase.getSex());
							//出生年月
							if(personnelBase.getBirthday() != null){
								DateFormat format1 = new SimpleDateFormat("yyyy-MM");
								String s = format1.format(personnelBase.getBirthday());
								Date date = DateUtils.parseDate(s);
								affairLdgblzFile.setBirthday(date);
							}
							affairLdgblzFile.setNativePlace(personnelBase.getNativePlace());
							affairLdgblzFile.setWorkUnitJob(personnelBase.getWorkunitName()+" "+personnelBase.getJobAbbreviation());
						}
						affairLdgblzFileService.save(affairLdgblzFile);
					}
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairLdgblzFile.getName()+"(身份证号码:"+affairLdgblzFile.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	 * 根据身份证号去人员基本信息表中查信息
	 * @param idNumber
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLdgblzFile:edit")
	@RequestMapping(value = "findInfoByIdNumber")
	public Result findInfoByIdNumber(String idNumber) {
		Result result = new Result();
		PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(idNumber);
		result.setSuccess(true);
		result.setResult(personnelBase);
		return result;
	}
}