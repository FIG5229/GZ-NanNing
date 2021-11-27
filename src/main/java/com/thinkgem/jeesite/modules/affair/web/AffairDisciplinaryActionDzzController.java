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
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryActionDzz;
import com.thinkgem.jeesite.modules.affair.service.AffairDisciplinaryActionDzzService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党组织纪律处分Controller
 * @author cecil.li
 * @version 2020-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDisciplinaryActionDzz")
public class AffairDisciplinaryActionDzzController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairDisciplinaryActionDzzService affairDisciplinaryActionDzzService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairDisciplinaryActionDzz get(@RequestParam(required=false) String id) {
		AffairDisciplinaryActionDzz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDisciplinaryActionDzzService.get(id);
		}
		if (entity == null){
			entity = new AffairDisciplinaryActionDzz();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDisciplinaryActionDzz:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("6".equals(UserUtils.getUser().getOffice().getId())){
			affairDisciplinaryActionDzz.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairDisciplinaryActionDzz.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairDisciplinaryActionDzz.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairDisciplinaryActionDzz.setBeginDate(null);
			affairDisciplinaryActionDzz.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairDisciplinaryActionDzz.getBeginDate()==null && affairDisciplinaryActionDzz.getEndDate() == null){
				String year = DateUtils.getYear();//获取当前年
				affairDisciplinaryActionDzz.setOtherYear(year);
			}else{
				affairDisciplinaryActionDzz.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairDisciplinaryActionDzz.setOtherYear(year);
			affairDisciplinaryActionDzz.setBeginDate(null);
			affairDisciplinaryActionDzz.setEndDate(null);
		}
		Page<AffairDisciplinaryActionDzz> page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response), affairDisciplinaryActionDzz);
		model.addAttribute("page", page);
		/*if(affairDisciplinaryActionDzz.getBeginDate() != null || affairDisciplinaryActionDzz.getEndDate() != null)
		{
			Page<AffairDisciplinaryActionDzz> page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response), affairDisciplinaryActionDzz);
			model.addAttribute("page", page);
		}else if (affairDisciplinaryActionDzz.getBeginDate() == null && affairDisciplinaryActionDzz.getEndDate() == null) {
			if ("".equals(affairDisciplinaryActionDzz.getOtherYear()) || affairDisciplinaryActionDzz.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairDisciplinaryActionDzz.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairDisciplinaryActionDzz> page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response), affairDisciplinaryActionDzz);
				model.addAttribute("page", page);
			}

		}else if (affairDisciplinaryActionDzz.getOtherYear() != null && !"".equals(affairDisciplinaryActionDzz.getOtherYear())){
			Page<AffairDisciplinaryActionDzz> page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response), affairDisciplinaryActionDzz);
			model.addAttribute("page", page);
		}
*/
		return "modules/affair/affairDisciplinaryActionDzzList";
	}

	@RequiresPermissions("affair:affairDisciplinaryActionDzz:add")
	@RequestMapping(value = "form")
	public String form(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz, Model model) {
		model.addAttribute("affairDisciplinaryActionDzz", affairDisciplinaryActionDzz);
		return "modules/affair/affairDisciplinaryActionDzzForm";
	}

	@RequiresPermissions("affair:affairDisciplinaryActionDzz:edit")
	@RequestMapping(value = "save")
	public String save(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDisciplinaryActionDzz)){
			return form(affairDisciplinaryActionDzz, model);
		}
		affairDisciplinaryActionDzzService.save(affairDisciplinaryActionDzz);
		addMessage(redirectAttributes, "保存党组织纪律处分成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDisciplinaryActionDzzForm";
	}
	
	@RequiresPermissions("affair:affairDisciplinaryActionDzz:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz, RedirectAttributes redirectAttributes) {
		affairDisciplinaryActionDzzService.delete(affairDisciplinaryActionDzz);
		addMessage(redirectAttributes, "删除党组织纪律处分成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDisciplinaryActionDzz/?repage";
	}

	/**
	 * 详情
	 * @param affairDisciplinaryActionDzz
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairDisciplinaryActionDzz:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz, Model model) {
		model.addAttribute("affairDisciplinaryActionDzz", affairDisciplinaryActionDzz);
		if(affairDisciplinaryActionDzz.getAnnex() != null && affairDisciplinaryActionDzz.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairDisciplinaryActionDzz.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairDisciplinaryActionDzzFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDisciplinaryActionDzz:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDisciplinaryActionDzzService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if ("6".equals(UserUtils.getUser().getOffice().getId())){
				affairDisciplinaryActionDzz.setQxUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairDisciplinaryActionDzz.setQxUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairDisciplinaryActionDzz.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairDisciplinaryActionDzz> page = null;
			String reason = request.getParameter("reason");//2 全部 3 其他年份
			if("3".equals(reason)){
				affairDisciplinaryActionDzz.setBeginDate(null);
				affairDisciplinaryActionDzz.setEndDate(null);
			}else if("2".equals(reason)){
				//全部
				if(affairDisciplinaryActionDzz.getBeginDate()==null && affairDisciplinaryActionDzz.getEndDate() == null){
					String year = DateUtils.getYear();//获取当前年
					affairDisciplinaryActionDzz.setOtherYear(year);
				}else{
					affairDisciplinaryActionDzz.setOtherYear(null);
				}
			}else{
				//默认显示当前年度数据
				String year =DateUtils.getYear();//获取当前年
				affairDisciplinaryActionDzz.setOtherYear(year);
				affairDisciplinaryActionDzz.setBeginDate(null);
				affairDisciplinaryActionDzz.setEndDate(null);
			}
			if(flag == true){
				page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response), affairDisciplinaryActionDzz);
			}else{
				page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response,-1), affairDisciplinaryActionDzz);
			}

			/*if(flag == true){
				if(affairDisciplinaryActionDzz.getBeginDate() != null || affairDisciplinaryActionDzz.getEndDate() != null)
				{
					page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response), affairDisciplinaryActionDzz);
				}else if (affairDisciplinaryActionDzz.getBeginDate() == null && affairDisciplinaryActionDzz.getEndDate() == null) {
					if ("".equals(affairDisciplinaryActionDzz.getOtherYear()) || affairDisciplinaryActionDzz.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairDisciplinaryActionDzz.setStartYear(yearDate);
						page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response), affairDisciplinaryActionDzz);
					}

				}else if (affairDisciplinaryActionDzz.getOtherYear() != null && !"".equals(affairDisciplinaryActionDzz.getOtherYear())){
					page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response), affairDisciplinaryActionDzz);
				}
			}else {
				if(affairDisciplinaryActionDzz.getBeginDate() != null || affairDisciplinaryActionDzz.getEndDate() != null)
				{
					page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response,-1), affairDisciplinaryActionDzz);
				}else if (affairDisciplinaryActionDzz.getBeginDate() == null && affairDisciplinaryActionDzz.getEndDate() == null) {
					if ("".equals(affairDisciplinaryActionDzz.getOtherYear()) || affairDisciplinaryActionDzz.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairDisciplinaryActionDzz.setStartYear(yearDate);
						page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response,-1), affairDisciplinaryActionDzz);
					}

				}else if (affairDisciplinaryActionDzz.getOtherYear() != null && !"".equals(affairDisciplinaryActionDzz.getOtherYear())){
					page = affairDisciplinaryActionDzzService.findPage(new Page<AffairDisciplinaryActionDzz>(request, response,-1), affairDisciplinaryActionDzz);
				}
			}*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairDisciplinaryActionDzz.class);
			exportExcelNew.setWb(wb);
			List<AffairDisciplinaryActionDzz> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairDisciplinaryActionDzz?repage";
	}

	/**
	 * 导入
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairDisciplinaryActionDzz> list = ei.getDataList(AffairDisciplinaryActionDzz.class);
			for (AffairDisciplinaryActionDzz affairDisciplinaryActionDzz : list){
				try{
					affairDisciplinaryActionDzz.setOrgId(officeService.findByName(affairDisciplinaryActionDzz.getOrgId()));
					BeanValidators.validateWithException(validator, affairDisciplinaryActionDzz);
					affairDisciplinaryActionDzzService.save(affairDisciplinaryActionDzz);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("导入失败："+ex.getMessage());
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