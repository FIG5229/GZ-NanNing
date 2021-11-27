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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelFamily;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelFamilyService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
 * 家庭成员及社会关系信息集Controller
 * @author cecil.li
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelFamily")
public class PersonnelFamilyController extends BaseController {

	@Autowired
	private PersonnelFamilyService personnelFamilyService;
	
	@ModelAttribute
	public PersonnelFamily get(@RequestParam(required=false) String id) {
		PersonnelFamily entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelFamilyService.get(id);
		}
		if (entity == null){
			entity = new PersonnelFamily();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelFamily:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelFamily personnelFamily, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelFamily.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelFamily> page = personnelFamilyService.findPage(new Page<PersonnelFamily>(request, response), personnelFamily);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelFamilyList";
	}

	@RequiresPermissions("personnel:personnelFamily:view")
	@RequestMapping(value = "form")
	public String form(PersonnelFamily personnelFamily, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelFamily.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelFamily", personnelFamily);
		return "modules/personnel/personnelFamilyForm";
	}

	@RequiresPermissions("personnel:personnelFamily:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelFamily personnelFamily, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelFamily)){
			return form(personnelFamily, model,request);
		}
		personnelFamilyService.save(personnelFamily);
		addMessage(redirectAttributes, "保存家庭成员及社会关系信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelFamilyForm";
	}
	
	@RequiresPermissions("personnel:personnelFamily:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelFamily personnelFamily, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelFamily/?repage&idNumber="+personnelFamily.getIdNumber();
		personnelFamilyService.delete(personnelFamily);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url =  "redirect:"+Global.getAdminPath()+"/personnel/personnelFamily/?repage&mType="+request.getParameter("mType");

		}
		addMessage(redirectAttributes, "删除家庭成员及社会关系信息成功");
		return url;
	}
	@RequiresPermissions("personnel:personnelFamily:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelFamily personnelFamily, Model model) {
		model.addAttribute("personnelFamily", personnelFamily);
		return "modules/personnel/personnelFamilyFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("personnel:personnelFamily:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelFamilyService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelFamily personnelFamily, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelFamily> page = null;
			if(flag == true){
				page = personnelFamilyService.findPage(new Page<PersonnelFamily>(request, response), personnelFamily);
			}else{
				page = personnelFamilyService.findPage(new Page<PersonnelFamily>(request, response,-1), personnelFamily);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelFamily.class);
			exportExcelNew.setWb(wb);
			List<PersonnelFamily> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelFamily/?repage";
	}
	//导入的controller
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelFamily> list = ei.getDataList(PersonnelFamily.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(item -> {
					if(item.getIdNumber()!=null){
						idNumbers.add(item.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelFamilyService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelFamily personnelFamily : list){
				try{
					BeanValidators.validateWithException(validator, personnelFamily);
					personnelFamilyService.save(personnelFamily);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelFamily.getName()+"(身份证号码:"+personnelFamily.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelFamily&isCover="+isCover;
	}

	@RequestMapping(value = {"getFamilyByIdNumber"})
	@ResponseBody
	public Result findFamilyByIdNumber(String idNumber){

		PersonnelFamily param = new PersonnelFamily();
		param.setIdNumber(idNumber);
		List<PersonnelFamily> list = personnelFamilyService.findFamilyByIdNumer(param);
		list.forEach(personnelFamily -> {
			personnelFamily.setNation(DictUtils.getDictLabel(personnelFamily.getNation(),"nation",""));
			personnelFamily.setPoliticsFace(DictUtils.getDictLabel(personnelFamily.getPoliticsFace(),"political_status",""));
		});
		Result result = new Result();
		if(list != null && list.size()> 0){
			result.setResult(list);
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}
}