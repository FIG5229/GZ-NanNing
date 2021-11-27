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
import com.thinkgem.jeesite.modules.affair.entity.AffairLeaguePunishment;
import com.thinkgem.jeesite.modules.affair.service.AffairLeaguePunishmentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 团内惩罚Controller
 * @author daniel.liu
 * @version 2020-05-19
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLeaguePunishment")
public class AffairLeaguePunishmentController extends BaseController {

	@Autowired
	private AffairLeaguePunishmentService affairLeaguePunishmentService;

    @Autowired
    private UploadController uploadController;

    @Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairLeaguePunishment get(@RequestParam(required=false) String id) {
		AffairLeaguePunishment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLeaguePunishmentService.get(id);
		}
		if (entity == null){
			entity = new AffairLeaguePunishment();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLeaguePunishment:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLeaguePunishment affairLeaguePunishment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairLeaguePunishment> page = affairLeaguePunishmentService.findPage(new Page<AffairLeaguePunishment>(request, response), affairLeaguePunishment); 
		model.addAttribute("page", page);
		return "modules/affair/affairLeaguePunishmentList";
	}

	@RequiresPermissions("affair:affairLeaguePunishment:view")
	@RequestMapping(value = "form")
	public String form(AffairLeaguePunishment affairLeaguePunishment, Model model) {
		model.addAttribute("affairLeaguePunishment", affairLeaguePunishment);
		return "modules/affair/affairLeaguePunishmentForm";
	}

	@RequiresPermissions("affair:affairLeaguePunishment:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLeaguePunishment affairLeaguePunishment, Model model) {
		model.addAttribute("affairLeaguePunishment", affairLeaguePunishment);
        if(affairLeaguePunishment.getFilePath() != null && affairLeaguePunishment.getFilePath().length() > 0){
            List<Map<String, String>> filePathList = uploadController.filePathHandle(affairLeaguePunishment.getFilePath());
            model.addAttribute("filePathList", filePathList);
        }
		return "modules/affair/affairLeaguePunishmentFormDetail";
	}

	@RequiresPermissions("affair:affairLeaguePunishment:edit")
	@RequestMapping(value = "save")
	public String save(AffairLeaguePunishment affairLeaguePunishment, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairLeaguePunishment)){
			return form(affairLeaguePunishment, model);
		}
		affairLeaguePunishmentService.save(affairLeaguePunishment);
		addMessage(redirectAttributes, "保存团内惩罚成功");
        request.setAttribute("saveResult","success");
        return "modules/affair/affairLeaguePunishmentForm";
	}
	
	@RequiresPermissions("affair:affairLeaguePunishment:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLeaguePunishment affairLeaguePunishment, RedirectAttributes redirectAttributes) {
		affairLeaguePunishmentService.delete(affairLeaguePunishment);
		addMessage(redirectAttributes, "删除团内惩罚成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLeaguePunishment/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairLeaguePunishment:edit")
	@RequestMapping(value = "deleteByIds")
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result=new Result();
		if(ids != null && ids.size() > 0){
			affairLeaguePunishmentService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairLeaguePunishment punishment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairLeaguePunishment> page = null;
			if(flag == true){
				page = affairLeaguePunishmentService.findPage(new Page<AffairLeaguePunishment>(request, response), punishment);
			}else {
				page = affairLeaguePunishmentService.findPage(new Page<AffairLeaguePunishment>(request, response,-1), punishment);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLeaguePunishment.class);
			exportExcelNew.setWb(wb);
			List<AffairLeaguePunishment> list =page.getList();

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
		return "redirect:" + adminPath + "/affair/affairLeaguePunishment/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairLeaguePunishment> list = ei.getDataList(AffairLeaguePunishment.class);
			for (AffairLeaguePunishment leaguePunishment : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(leaguePunishment.getUnit());
					if(orgId != null){
						leaguePunishment.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, leaguePunishment);
					affairLeaguePunishmentService.save(leaguePunishment);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(更新者:"+leaguePunishment.getUpdateBy().getLoginName()+")"+" 导入失败："+ex.getMessage());
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