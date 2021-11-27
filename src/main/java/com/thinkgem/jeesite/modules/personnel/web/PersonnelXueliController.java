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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelXueli;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelXueliService;
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

/**
 * 学历信息集Controller
 * @author cecil.li
 * @version 2019-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelXueli")
public class PersonnelXueliController extends BaseController {

	@Autowired
	private PersonnelXueliService personnelXueliService;
	
	@ModelAttribute
	public PersonnelXueli get(@RequestParam(required=false) String id) {
		PersonnelXueli entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelXueliService.get(id);
		}
		if (entity == null){
			entity = new PersonnelXueli();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelXueli:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelXueli personnelXueli, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelXueli.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelXueli> page = personnelXueliService.findPage(new Page<PersonnelXueli>(request, response), personnelXueli);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelXueliList";
	}

	@RequiresPermissions("personnel:personnelXueli:view")
	@RequestMapping(value = "form")
	public String form(PersonnelXueli personnelXueli, Model model, HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelXueli.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelXueli", personnelXueli);
		return "modules/personnel/personnelXueliForm";
	}

	@RequiresPermissions("personnel:personnelXueli:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelXueli personnelXueli, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelXueli)){
			return form(personnelXueli, model, request);
		}
		personnelXueliService.save(personnelXueli);
		addMessage(redirectAttributes, "保存学历信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelXueliForm";
	}
	
	@RequiresPermissions("personnel:personnelXueli:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelXueli personnelXueli, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelXueli/?repage&idNumber="+personnelXueli.getIdNumber();

		personnelXueliService.delete(personnelXueli);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelXueli/?repage&mType="+request.getParameter("mType").toString();

		}
		addMessage(redirectAttributes, "删除学历信息成功");
        return  url;
	}

	@RequiresPermissions("personnel:personnelXueli:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelXueli personnelXueli, Model model) {
		model.addAttribute("personnelXueli", personnelXueli);
		return "modules/personnel/personnelXueliFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelXueli:view")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelXueliService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelXueli personnelXueli, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelXueli> page = null;
			if(flag == true){
				page = personnelXueliService.findPage(new Page<PersonnelXueli>(request, response), personnelXueli);
			}else {
				page = personnelXueliService.findPage(new Page<PersonnelXueli>(request, response,-1), personnelXueli);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelXueli.class);
			exportExcelNew.setWb(wb);
			List<PersonnelXueli> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelXueli?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(HttpServletRequest request, MultipartFile file, RedirectAttributes redirectAttributes) {
		String isCover = request.getParameter("isCover");

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelXueli> list = ei.getDataList(PersonnelXueli.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelResume -> {
					if(personnelResume.getIdNumber()!=null){
						idNumbers.add(personnelResume.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelXueliService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelXueli personnelXueli : list){
				try{
					String name = personnelXueli.getName();
					if (name.contains("小学")){
						personnelXueli.setLevel(1);
					}else if (name.contains("初中")){
						personnelXueli.setLevel(2);
					}else if (name.contains("中专") || name.contains("高中")){
						personnelXueli.setLevel(3);
					}else if (name.contains("专科")){
						personnelXueli.setLevel(4);
					}else if (name.contains("本科")){
						personnelXueli.setLevel(5);
					}else if (name.contains("硕士")){
						personnelXueli.setLevel(6);
					}else if (name.contains("博士")){
						personnelXueli.setLevel(7);
					}else {
						personnelXueli.setLevel(0);
					}
					BeanValidators.validateWithException(validator, personnelXueli);
					personnelXueliService.save(personnelXueli);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelXueli.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	 * 全日制统计分析明细
	 * @param personnelXueli
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"fullTimeEducationList"})
	public String fullTimeEducationDetail(PersonnelXueli personnelXueli, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelXueli.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelXueli> page = personnelXueliService.findFullTimePage(new Page<PersonnelXueli>(request, response), personnelXueli);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/charts/chartsXueliList";
	}

	/**
	 * 最高学历明细
	 * @param personnelXueli
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"maxEducationList"})
	public String maxEducationDetail(PersonnelXueli personnelXueli, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelXueli.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelXueli> page = personnelXueliService.findMaxPage(new Page<PersonnelXueli>(request, response), personnelXueli);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/charts/chartsXueliMaxList";
	}



}