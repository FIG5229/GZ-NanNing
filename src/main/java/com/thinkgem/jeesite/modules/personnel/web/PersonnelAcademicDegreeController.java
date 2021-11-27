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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelAcademicDegree;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelAcademicDegreeService;
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
 * 学位信息集Controller
 * @author cecil.li
 * @version 2019-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelAcademicDegree")
public class PersonnelAcademicDegreeController extends BaseController {

	@Autowired
	private PersonnelAcademicDegreeService personnelAcademicDegreeService;

	@ModelAttribute
	public PersonnelAcademicDegree get(@RequestParam(required=false) String id) {
		PersonnelAcademicDegree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelAcademicDegreeService.get(id);
		}
		if (entity == null){
			entity = new PersonnelAcademicDegree();
		}
		return entity;
	}

	@RequiresPermissions("personnel:personnelAcademicDegree:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelAcademicDegree personnelAcademicDegree, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelAcademicDegree.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelAcademicDegree> page = personnelAcademicDegreeService.findPage(new Page<PersonnelAcademicDegree>(request, response), personnelAcademicDegree);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelAcademicDegreeList";
	}

	@RequiresPermissions("personnel:personnelAcademicDegree:view")
	@RequestMapping(value = "form")
	public String form(PersonnelAcademicDegree personnelAcademicDegree, Model model, HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelAcademicDegree.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelAcademicDegree", personnelAcademicDegree);
		return "modules/personnel/personnelAcademicDegreeForm";
	}

	@RequiresPermissions("personnel:personnelAcademicDegree:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelAcademicDegree personnelAcademicDegree, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelAcademicDegree)){
			return form(personnelAcademicDegree, model, request);
		}
		personnelAcademicDegreeService.save(personnelAcademicDegree);
		addMessage(redirectAttributes, "保存学位信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelAcademicDegreeForm";
	}

	@RequiresPermissions("personnel:personnelAcademicDegree:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelAcademicDegree personnelAcademicDegree, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String url =  "redirect:"+Global.getAdminPath()+"/personnel/personnelAcademicDegree/?repage&idNumber="+personnelAcademicDegree.getIdNumber();
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelAcademicDegree/?repage&mType="+request.getParameter("mType").toString();
		}
		personnelAcademicDegreeService.delete(personnelAcademicDegree);
		addMessage(redirectAttributes, "删除学位信息成功");
		return  url;
	}
	@RequiresPermissions("personnel:personnelAcademicDegree:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelAcademicDegree personnelAcademicDegree, Model model) {
		model.addAttribute("personnelAcademicDegree", personnelAcademicDegree);
		return "modules/personnel/personnelAcademicDegreeFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelAcademicDegree:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelAcademicDegreeService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelAcademicDegree personnelAcademicDegree, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelAcademicDegree> page = null;
			if(flag == true){
				page = personnelAcademicDegreeService.findPage(new Page<PersonnelAcademicDegree>(request, response), personnelAcademicDegree);
			}else{
				page = personnelAcademicDegreeService.findPage(new Page<PersonnelAcademicDegree>(request, response,-1), personnelAcademicDegree);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelAcademicDegree.class);
			exportExcelNew.setWb(wb);
			List<PersonnelAcademicDegree> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelAcademicDegree?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelAcademicDegree> list = ei.getDataList(PersonnelAcademicDegree.class);
			//选择覆盖模式，须先将改身份证下相关信息集删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(item -> {
					if(item.getIdNumber()!=null){
						idNumbers.add(item.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelAcademicDegreeService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelAcademicDegree personnelAcademicDegree : list){
				if(StringUtils.isNotBlank(personnelAcademicDegree.getIdNumber())){
					if(StringUtils.isNotBlank(personnelAcademicDegree.getName())){
						try{
							String name = personnelAcademicDegree.getName();
							String explain = personnelAcademicDegree.getExplain();
							if (name.contains("学士")){
								personnelAcademicDegree.setLevel(1);
							}else if (name.contains("硕士")){
								personnelAcademicDegree.setLevel(2);
							}else if (name.contains("博士")){
								personnelAcademicDegree.setLevel(3);
							}else {
								personnelAcademicDegree.setLevel(0);
							}
							if (explain.contains("全日制")){
								personnelAcademicDegree.setType("1");
							}else if (explain.contains("在职")){
								personnelAcademicDegree.setType("2");
							}else {
								personnelAcademicDegree.setType("0");
							}
							BeanValidators.validateWithException(validator, personnelAcademicDegree);
							personnelAcademicDegreeService.save(personnelAcademicDegree);
							successNum++;
						}catch(ConstraintViolationException ex){
							List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
							for (String message : messageList){
								failureMsg.append(message+"; ");
								failureNum++;
							}
						}catch (Exception ex) {
							failureMsg.append("(身份证号码:"+personnelAcademicDegree.getIdNumber()+")"+" 导入失败："+ex.getMessage());
						}
					}else{
						failureMsg.append("(身份证号码:"+personnelAcademicDegree.getIdNumber()+")"+" 导入失败：学位名称不能为空");
					}
				}else{
					failureMsg.append("导入失败，身份证号码不能为空");
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
//		redirectAttributes.addFlashAttribute("result","success");
//		return "redirect:" + adminPath + "/file/template/download/view";
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelAcademicDegree&isCover="+isCover;
	}
}
