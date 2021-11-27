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
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairApplyManage;
import com.thinkgem.jeesite.modules.affair.service.AffairApplyManageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 报名管理Controller
 * @author alan.wu
 * @version 2020-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairApplyManage")
public class AffairApplyManageController extends BaseController {

	@Autowired
	private AffairApplyManageService affairApplyManageService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private IdGen idGen;
	
	@ModelAttribute
	public AffairApplyManage get(@RequestParam(required=false) String id) {
		AffairApplyManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairApplyManageService.get(id);
		}
		if (entity == null){
			entity = new AffairApplyManage();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairApplyManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairApplyManage affairApplyManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairApplyManage> page = affairApplyManageService.findPage(new Page<AffairApplyManage>(request, response), affairApplyManage);
		request.setAttribute("classId",affairApplyManage.getClass());

		model.addAttribute("page", page);
		return "modules/affair/affairApplyManageList";
	}

	@RequiresPermissions("affair:affairApplyManage:view")
	@RequestMapping(value = "form")
	public String form(AffairApplyManage affairApplyManage, Model model) {
		model.addAttribute("affairApplyManage", affairApplyManage);
		return "modules/affair/affairApplyManageForm";
	}

	@RequiresPermissions("affair:affairApplyManage:edit")
	@RequestMapping(value = "save")
	public String save(AffairApplyManage affairApplyManage, Model model, RedirectAttributes redirectAttributes) {

		//班级id
		String cid = affairApplyManage.getClassId();

		if (StringUtils.isBlank(cid)){
			String classId = idGen.getNextId();
			affairApplyManage.setClassId(classId);
		}

		if (!beanValidator(model, affairApplyManage)){
			return form(affairApplyManage, model);
		}
		affairApplyManageService.save(affairApplyManage);

		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairApplyManageForm";
	}


	@RequiresPermissions("affair:affairApplyManage:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairApplyManage affairApplyManage, RedirectAttributes redirectAttributes) {
		affairApplyManageService.delete(affairApplyManage);
		addMessage(redirectAttributes, "删除报名管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairApplyManage/?repage";
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairApplyManage:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairApplyManageService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 *
	 * @param affairApplyManage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairApplyManage:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairApplyManage affairApplyManage, Model model) {
		model.addAttribute("affairApplyManage", affairApplyManage);
		if (affairApplyManage.getAdjunct() != null && affairApplyManage.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairApplyManage.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairApplyManageFormDetail";
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
	public String exportExcelByTemplate(AffairApplyManage affairApplyManage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairApplyManage.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairApplyManage.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairApplyManage.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairApplyManage> page = null;
			if(flag == true){
				page = affairApplyManageService.findPage(new Page<AffairApplyManage>(request, response), affairApplyManage);
			}else {
				page = affairApplyManageService.findPage(new Page<AffairApplyManage>(request, response,-1), affairApplyManage);
			}
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairApplyManage.class);
			exportExcelNew.setWb(wb);
			List<AffairApplyManage> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairApplyManage?repage";
	}



	/*
	 *
	 *  导入
	 * */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;

			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairApplyManage> list = ei.getDataList(AffairApplyManage.class);
			for (AffairApplyManage affairCommentators : list) {
				try {
					affairCommentators.setUnitId(officeService.findByName(affairCommentators.getUnit()));
					BeanValidators.validateWithException(validator, affairCommentators);
					affairCommentators.setClassId(idGen.getNextId());
					affairApplyManageService.save(affairCommentators);
					successNum++;
				} catch (ConstraintViolationException ex) {
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append(" 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result", "success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}


}