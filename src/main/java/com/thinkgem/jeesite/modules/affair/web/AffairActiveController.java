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
import com.thinkgem.jeesite.modules.affair.entity.AffairAdminStatistics;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairActive;
import com.thinkgem.jeesite.modules.affair.service.AffairActiveService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 活动情况Controller
 * @author alan.wu
 * @version 2020-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairActive")
public class AffairActiveController extends BaseController {

	@Autowired
	private AffairActiveService affairActiveService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairActive get(@RequestParam(required=false) String id) {
		AffairActive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairActiveService.get(id);
		}
		if (entity == null){
			entity = new AffairActive();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairActive:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairActive affairActive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairActive> page = affairActiveService.findPage(new Page<AffairActive>(request, response), affairActive); 
		model.addAttribute("page", page);
		return "modules/affair/affairActiveList";
	}

	@RequiresPermissions("affair:affairActive:view")
	@RequestMapping(value = "form")
	public String form(AffairActive affairActive, Model model) {
		model.addAttribute("affairActive", affairActive);
		return "modules/affair/affairActiveForm";
	}

	@RequiresPermissions("affair:affairActive:edit")
	@RequestMapping(value = "save")
	public String save(AffairActive affairActive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairActive)){
			return form(affairActive, model);
		}
		affairActiveService.save(affairActive);
		addMessage(redirectAttributes, "保存活动情况成功");
		model.addAttribute("saveResult", "success");
		return "modules/affair/affairActiveForm";
	}
	
	@RequiresPermissions("affair:affairActive:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairActive affairActive, RedirectAttributes redirectAttributes) {
		affairActiveService.delete(affairActive);
		addMessage(redirectAttributes, "删除活动情况成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairActive/?repage";
	}



	@ResponseBody
	@RequiresPermissions("affair:affairActive:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairActiveService.deleteByIds(ids);
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
	 *
	 * @param affairActive
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairActive:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairActive affairActive, Model model) {
		model.addAttribute("affairActive", affairActive);
		if(affairActive.getAdjunct() != null && affairActive.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairActive.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairActiveFormDetail";
	}


	/**
	 * 导出excel格式数据
	 * @param affairActive
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairActive affairActive, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairActive.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairActive> page = null;
			if (flag == true) {
				page = affairActiveService.findPage(new Page<AffairActive>(request, response), affairActive);
			} else {
				page = affairActiveService.findPage(new Page<AffairActive>(request, response, -1), affairActive);
			}

			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream) {
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairActive.class);
			exportExcelNew.setWb(wb);
			List<AffairActive> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/affair/affairActive?repage";
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
			List<AffairActive> list = ei.getDataList(AffairActive.class);
			for (AffairActive affairActive : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairActive.getUnit());
					if(orgId != null){
						affairActive.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, affairActive);
					affairActiveService.save(affairActive);
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

	@RequestMapping(value = {"readBookDetail"})
	public String readBookDetail(AffairActive affairActive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairActive> page = affairActiveService.findReadBookPage(new Page<AffairActive>(request, response), affairActive);
		model.addAttribute("page", page);
		return "modules/charts/chartActiveList";
	}

}