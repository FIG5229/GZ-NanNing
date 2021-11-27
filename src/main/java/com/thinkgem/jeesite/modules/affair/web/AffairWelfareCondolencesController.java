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
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceThoughtAnalysis;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairWelfareCondolences;
import com.thinkgem.jeesite.modules.affair.service.AffairWelfareCondolencesService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 福利慰问下的会员福利Controller
 * @author daniel.liu
 * @version 2020-05-12
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWelfareCondolences")
public class AffairWelfareCondolencesController extends BaseController {

	@Autowired
	private AffairWelfareCondolencesService affairWelfareCondolencesService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairWelfareCondolences get(@RequestParam(required=false) String id) {
		AffairWelfareCondolences entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWelfareCondolencesService.get(id);
		}
		if (entity == null){
			entity = new AffairWelfareCondolences();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWelfareCondolences:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWelfareCondolences affairWelfareCondolences, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWelfareCondolences> page = affairWelfareCondolencesService.findPage(new Page<AffairWelfareCondolences>(request, response), affairWelfareCondolences); 
		model.addAttribute("page", page);
		return "modules/affair/affairWelfareCondolencesList";
	}

	@RequiresPermissions("affair:affairWelfareCondolences:view")
	@RequestMapping(value = "form")
	public String form(AffairWelfareCondolences affairWelfareCondolences, Model model) {
		model.addAttribute("affairWelfareCondolences", affairWelfareCondolences);
		return "modules/affair/affairWelfareCondolencesForm";
	}


	@RequiresPermissions("affair:affairWelfareCondolences:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWelfareCondolences affairWelfareCondolences, Model model) {
		model.addAttribute("affairWelfareCondolences", affairWelfareCondolences);
		if(affairWelfareCondolences.getFilePath() != null && affairWelfareCondolences.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWelfareCondolences.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWelfareCondolencesFormDetail";
	}


	@RequiresPermissions("affair:affairWelfareCondolences:edit")
	@RequestMapping(value = "save")
	public String save(AffairWelfareCondolences affairWelfareCondolences, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairWelfareCondolences)){
			return form(affairWelfareCondolences, model);
		}
		affairWelfareCondolencesService.save(affairWelfareCondolences);
		addMessage(redirectAttributes, "保存福利慰问成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairWelfareCondolencesList";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairConsolationWorkInfo:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWelfareCondolencesService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairWelfareCondolences:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWelfareCondolences affairWelfareCondolences, RedirectAttributes redirectAttributes) {
		affairWelfareCondolencesService.delete(affairWelfareCondolences);
		addMessage(redirectAttributes, "删除福利慰问成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWelfareCondolences/?repage";
	}

	/**
	 * 导出excel格式数据
	 * @param welfareCondolences
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairWelfareCondolences welfareCondolences, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairWelfareCondolences> page = null;
			if(flag == true){
				page = affairWelfareCondolencesService.findPage(new Page<AffairWelfareCondolences>(request, response), welfareCondolences);
			}else{
				page = affairWelfareCondolencesService.findPage(new Page<AffairWelfareCondolences>(request, response,-1), welfareCondolences);
			}
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
//			String filePath= Global.getUserfilesBaseDir()+"\\userfiles\\template\\";
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairWelfareCondolences.class);
			exportExcelNew.setWb(wb);
			List<AffairWelfareCondolences> list =page.getList();
			/*对富文本编辑的内容进行html标签替换*/
			for (AffairWelfareCondolences item: list ) {
				item.setContent(StringUtils.replaceHtml(item.getContent()));
			}
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
		return "redirect:" + adminPath + "/affair/affairWelfareCondolences/?repage";
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
			List<AffairWelfareCondolences> list = ei.getDataList(AffairWelfareCondolences.class);
			for (AffairWelfareCondolences thoughtAnalysis : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(thoughtAnalysis.getUnit());
					if(orgId != null){
						thoughtAnalysis.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, thoughtAnalysis);
					affairWelfareCondolencesService.save(thoughtAnalysis);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(thoughtAnalysis.getName()+"(专题名称:"+")"+" 导入失败："+ex.getMessage());
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