/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

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
import com.thinkgem.jeesite.modules.affair.entity.AffairEvaluationCriteria;
import com.thinkgem.jeesite.modules.affair.service.AffairEvaluationCriteriaService;
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
import java.util.List;
import java.util.Map;

/**
 * 测评标准Controller
 * @author daniel.liu
 * @version 2020-07-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairEvaluationCriteria")
public class AffairEvaluationCriteriaController extends BaseController {

	@Autowired
	private AffairEvaluationCriteriaService affairEvaluationCriteriaService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairEvaluationCriteria get(@RequestParam(required=false) String id) {
		AffairEvaluationCriteria entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairEvaluationCriteriaService.get(id);
		}
		if (entity == null){
			entity = new AffairEvaluationCriteria();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairEvaluationCriteria:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairEvaluationCriteria affairEvaluationCriteria, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairEvaluationCriteria> page = affairEvaluationCriteriaService.findPage(new Page<AffairEvaluationCriteria>(request, response), affairEvaluationCriteria); 
		model.addAttribute("page", page);
		return "modules/affair/affairEvaluationCriteriaList";
	}

	@RequiresPermissions("affair:affairEvaluationCriteria:view")
	@RequestMapping(value = "form")
	public String form(AffairEvaluationCriteria affairEvaluationCriteria, Model model) {
		model.addAttribute("affairEvaluationCriteria", affairEvaluationCriteria);
		return "modules/affair/affairEvaluationCriteriaForm";
	}
	@RequiresPermissions("affair:affairEvaluationCriteria:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairEvaluationCriteria affairEvaluationCriteria, Model model) {
		model.addAttribute("affairEvaluationCriteria", affairEvaluationCriteria);
		if(affairEvaluationCriteria.getFilePath() != null && affairEvaluationCriteria.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairEvaluationCriteria.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairEvaluationCriteriaFormDetail";
	}

	@RequiresPermissions("affair:affairEvaluationCriteria:edit")
	@RequestMapping(value = "save")
	public String save(AffairEvaluationCriteria affairEvaluationCriteria, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairEvaluationCriteria)){
			return form(affairEvaluationCriteria, model);
		}
		affairEvaluationCriteriaService.save(affairEvaluationCriteria);
		request.setAttribute("saveResult","success");
		addMessage(redirectAttributes, "保存测评标准成功");
		return "modules/affair/affairEvaluationCriteriaForm";
	}
	
	@RequiresPermissions("affair:affairEvaluationCriteria:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairEvaluationCriteria affairEvaluationCriteria, RedirectAttributes redirectAttributes) {
		affairEvaluationCriteriaService.delete(affairEvaluationCriteria);
		addMessage(redirectAttributes, "删除测评标准成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairEvaluationCriteria/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairEvaluationCriteria:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairEvaluationCriteriaService.deleteByIds(ids);
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
	 * @param affairEvaluationCriteria
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairEvaluationCriteria affairEvaluationCriteria, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairEvaluationCriteria> page = null;
			if(flag == true){
				page = affairEvaluationCriteriaService.findPage(new Page<AffairEvaluationCriteria>(request, response), affairEvaluationCriteria);
			}else{
				page = affairEvaluationCriteriaService.findPage(new Page<AffairEvaluationCriteria>(request, response,-1), affairEvaluationCriteria);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairEvaluationCriteria.class);
			exportExcelNew.setWb(wb);
			List<AffairEvaluationCriteria> list =page.getList();
			/*对富文本编辑的内容进行html标签替换*/
			/*for (AffairEvaluationCriteria item: list ) {
				item.setContent(StringUtils.replaceHtml(item.getContent()));
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
		return "redirect:" + adminPath + "/affair/affairEvaluationCriteria/?repage";
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
			List<AffairEvaluationCriteria> list = ei.getDataList(AffairEvaluationCriteria.class);
			for (AffairEvaluationCriteria evaluationCriteria : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(evaluationCriteria.getUnit());
					if(orgId != null){
						evaluationCriteria.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, evaluationCriteria);
					affairEvaluationCriteriaService.save(evaluationCriteria);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(evaluationCriteria.getTitle()+"(专题名称:"+")"+" 导入失败："+ex.getMessage());
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