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
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryRegulation;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryRegulationReceive;
import com.thinkgem.jeesite.modules.affair.service.AffairDisciplinaryRegulationReceiveService;
import com.thinkgem.jeesite.modules.affair.service.AffairDisciplinaryRegulationService;
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
 * 纪律规定Controller
 * @author cecil.li
 * @version 2019-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDisciplinaryRegulation")
public class AffairDisciplinaryRegulationController extends BaseController {

	@Autowired
	private AffairDisciplinaryRegulationService affairDisciplinaryRegulationService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairDisciplinaryRegulationReceiveService affairDisciplinaryRegulationReceiveService;
	
	@ModelAttribute
	public AffairDisciplinaryRegulation get(@RequestParam(required=false) String id) {
		AffairDisciplinaryRegulation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDisciplinaryRegulationService.get(id);
		}
		if (entity == null){
			entity = new AffairDisciplinaryRegulation();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDisciplinaryRegulation:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDisciplinaryRegulation affairDisciplinaryRegulation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDisciplinaryRegulation> page = affairDisciplinaryRegulationService.findPage(new Page<AffairDisciplinaryRegulation>(request, response), affairDisciplinaryRegulation); 
		model.addAttribute("page", page);
		return "modules/affair/affairDisciplinaryRegulationList";
	}

	@RequiresPermissions("affair:affairDisciplinaryRegulation:add")
	@RequestMapping(value = "form")
	public String form(AffairDisciplinaryRegulation affairDisciplinaryRegulation, Model model) {
		model.addAttribute("affairDisciplinaryRegulation", affairDisciplinaryRegulation);
		return "modules/affair/affairDisciplinaryRegulationForm";
	}

	@RequiresPermissions("affair:affairDisciplinaryRegulation:edit")
	@RequestMapping(value = "save")
	public String save(AffairDisciplinaryRegulation affairDisciplinaryRegulation, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDisciplinaryRegulation)){
			return form(affairDisciplinaryRegulation, model);
		}
		affairDisciplinaryRegulationService.save(affairDisciplinaryRegulation);
		addMessage(redirectAttributes, "保存纪律规定成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDisciplinaryRegulationForm";
	}
	
	@RequiresPermissions("affair:affairDisciplinaryRegulation:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairDisciplinaryRegulation affairDisciplinaryRegulation, RedirectAttributes redirectAttributes) {
		affairDisciplinaryRegulationService.delete(affairDisciplinaryRegulation);
		addMessage(redirectAttributes, "删除纪律规定成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDisciplinaryRegulation/?repage";
	}
	/**
	 * 详情
	 * @param affairDisciplinaryRegulation
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairDisciplinaryRegulation:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDisciplinaryRegulation affairDisciplinaryRegulation, Model model) {
		model.addAttribute("affairDisciplinaryRegulation", affairDisciplinaryRegulation);
		if(affairDisciplinaryRegulation.getFilePath() != null && affairDisciplinaryRegulation.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairDisciplinaryRegulation.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairDisciplinaryRegulationFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDisciplinaryRegulation:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDisciplinaryRegulationService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairDisciplinaryRegulation affairDisciplinaryRegulation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairDisciplinaryRegulation> page = null;
			if(flag == true){
				page = affairDisciplinaryRegulationService.findPage(new Page<AffairDisciplinaryRegulation>(request, response), affairDisciplinaryRegulation);
			}else{
				page = affairDisciplinaryRegulationService.findPage(new Page<AffairDisciplinaryRegulation>(request, response,-1), affairDisciplinaryRegulation);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairDisciplinaryRegulation.class);
			exportExcelNew.setWb(wb);
			List<AffairDisciplinaryRegulation> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairDisciplinaryRegulation/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairDisciplinaryRegulation> list = ei.getDataList(AffairDisciplinaryRegulation.class);
			for (AffairDisciplinaryRegulation affairDisciplinaryRegulation : list){
				try{
					BeanValidators.validateWithException(validator, affairDisciplinaryRegulation);
					affairDisciplinaryRegulationService.save(affairDisciplinaryRegulation);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(" 导入失败："+ex.getMessage());
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

	@RequiresPermissions("affair:affairDisciplinaryRegulation:edit")
	@RequestMapping(value = "updateOrderId")
	public String updateOrderId(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive, Model model, String id) {
		if (affairDisciplinaryRegulationService.isExist(UserUtils.getUser().getOffice().getId(), id) != null){
			affairDisciplinaryRegulationService.updateOrderId(id, UserUtils.getUser().getOffice().getId());
		}else {
			affairDisciplinaryRegulationReceive.setUnitId(id);
			affairDisciplinaryRegulationReceive.setDisRegId(UserUtils.getUser().getOffice().getId());
			affairDisciplinaryRegulationReceive.setOrderId("1");
			affairDisciplinaryRegulationReceiveService.save(affairDisciplinaryRegulationReceive);
		}
		model.addAttribute("affairDisciplinaryRegulationReceive", affairDisciplinaryRegulationReceive);
		return "redirect:"+Global.getAdminPath()+"/affair/affairDisciplinaryRegulation/?repage";
//		return "modules/affair/affairDisciplinaryRegulationList";
	}

	@RequiresPermissions("affair:affairDisciplinaryRegulation:edit")
	@RequestMapping(value = "reUpdateOrderId")
	public String reUpdateOrderId(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive, Model model, String id) {
		affairDisciplinaryRegulationService.reUpdateOrderId(id, UserUtils.getUser().getOffice().getId());
		model.addAttribute("affairDisciplinaryRegulationReceive", affairDisciplinaryRegulationReceive);
		return "redirect:"+Global.getAdminPath()+"/affair/affairDisciplinaryRegulation/?repage";
//		return "modules/affair/affairDisciplinaryRegulationList";
	}
}