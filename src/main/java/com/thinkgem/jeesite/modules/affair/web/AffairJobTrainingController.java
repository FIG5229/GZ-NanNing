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
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenhua;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.openxml4j.util.ZipSecureFile;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairJobTraining;
import com.thinkgem.jeesite.modules.affair.service.AffairJobTrainingService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 岗位练兵功能Controller
 * @author tom.fu
 * @version 2020-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairJobTraining")
public class AffairJobTrainingController extends BaseController {

	@Autowired
	private AffairJobTrainingService affairJobTrainingService;
	@Autowired
	private OfficeService officeService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairJobTraining get(@RequestParam(required=false) String id) {
		AffairJobTraining entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairJobTrainingService.get(id);
		}
		if (entity == null){
			entity = new AffairJobTraining();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairJobTraining:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/affair/affairJobTrainingIndex";
	}

	@RequiresPermissions("affair:affairJobTraining:view")
	@RequestMapping(value = "list")
	public String list(AffairJobTraining affairJobTraining, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairJobTraining> page = affairJobTrainingService.findPage(new Page<AffairJobTraining>(request, response), affairJobTraining);
		model.addAttribute("page", page);
		model.addAttribute("organization",affairJobTraining.getOrganization());
		model.addAttribute("organizationId",affairJobTraining.getOrganizationId());
		return "modules/affair/affairJobTrainingList";
	}

	@RequiresPermissions("affair:affairJobTraining:view")
	@RequestMapping(value = "form")
	public String form(AffairJobTraining affairJobTraining, Model model) {
		model.addAttribute("affairJobTraining", affairJobTraining);
		return "modules/affair/affairJobTrainingForm";
	}

	@RequiresPermissions("affair:affairJobTraining:edit")
	@RequestMapping(value = "save")
	public String save(AffairJobTraining affairJobTraining, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairJobTraining)){
			return form(affairJobTraining, model);
		}
		affairJobTrainingService.save(affairJobTraining);
		addMessage(redirectAttributes, "保存岗位练兵功能成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairJobTrainingForm";
	}

	@RequiresPermissions("affair:affairJobTraining:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairJobTraining affairJobTraining, RedirectAttributes redirectAttributes,String organizationId) {
		affairJobTrainingService.delete(affairJobTraining);
		addMessage(redirectAttributes, "删除岗位练兵功能成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairJobTraining/list?organizationId="+organizationId;
	}
	@ResponseBody
	@RequiresPermissions("affair:affairJobTraining:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairJobTrainingService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 查看详情
	 * @param affairJobTraining
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairJobTraining:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairJobTraining affairJobTraining, Model model) {
		model.addAttribute("affairJobTraining", affairJobTraining);
		if(affairJobTraining.getAppendfile() != null && affairJobTraining.getAppendfile().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairJobTraining.getAppendfile());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairJobTrainingFormDetail";
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairJobTraining:edit")
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairJobTraining> list = ei.getDataList(AffairJobTraining.class);
			for (AffairJobTraining affairJobTraining : list){
				try{
					BeanValidators.validateWithException(validator, affairJobTraining);
					affairJobTrainingService.save(affairJobTraining);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairJobTraining.getName()+"(您的信息:"+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_affairJobTraining";
	}
	/**
	 * 导出excel格式数据
	 * @param affairJobTraining
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairJobTraining affairJobTraining, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairJobTraining> page = null;
			if(flag == true){
				page = affairJobTrainingService.findPage(new Page<AffairJobTraining>(request, response), affairJobTraining);
			}else{
				page = affairJobTrainingService.findPage(new Page<AffairJobTraining>(request, response,-1), affairJobTraining);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairJobTraining.class);
			exportExcelNew.setWb(wb);
			List<AffairJobTraining> list =page.getList();
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
			addMessage(redirectAttributes, "导出岗位练兵列表失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairJobTraining?repage";
	}
}