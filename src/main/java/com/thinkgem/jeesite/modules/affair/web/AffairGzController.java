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
import com.thinkgem.jeesite.modules.affair.entity.AffairGz;
import com.thinkgem.jeesite.modules.affair.service.AffairGzService;
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
 * 固资管理Controller
 * @author cecil.li
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGz")
public class AffairGzController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairGzService affairGzService;
	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairGz get(@RequestParam(required=false) String id) {
		AffairGz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGzService.get(id);
		}
		if (entity == null){
			entity = new AffairGz();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairGz:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairGz affairGz, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("4c40b4dd2aee459286a37538978e6261".equals(UserUtils.getUser().getId())){

		if (!StringUtils.isNotBlank(affairGz.getTabFlag()) ){
			//打开tab页为用户所在公司
//				affairZyInfo.setTabFlag(UserUtils.getUser().getCompany().getId());
			//打开tab页为第一个tab 南宁
			affairGz.setTabFlag("34");
		}
		}
		Page<AffairGz> page = affairGzService.findPage(new Page<AffairGz>(request, response), affairGz);
		model.addAttribute("page", page);
		model.addAttribute("tabFlag", affairGz.getTabFlag());
		return "modules/affair/affairGzList";
	}

	@RequiresPermissions("affair:affairGz:view")
	@RequestMapping(value = "form")
	public String form(AffairGz affairGz, Model model) {
		model.addAttribute("affairGz", affairGz);
		return "modules/affair/affairGzForm";
	}

	@RequiresPermissions("affair:affairGz:edit")
	@RequestMapping(value = "save")
	public String save(AffairGz affairGz, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairGz)){
			return form(affairGz, model);
		}
		affairGzService.save(affairGz);
		addMessage(redirectAttributes, "保存固资管理成功");
			request.setAttribute("saveResult","success");
		request.setAttribute("tabFlag",affairGz.getTabFlag());
		return "modules/affair/affairGzList";
	}
	
	@RequiresPermissions("affair:affairGz:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGz affairGz, RedirectAttributes redirectAttributes) {
		affairGzService.delete(affairGz);
		addMessage(redirectAttributes, "删除固资管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGz/?repage";
	}

	/**
	 * 详情
	 * @param affairGz
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGz:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairGz affairGz, Model model) {
		model.addAttribute("affairGz", affairGz);
		if(affairGz.getFilePath() != null && affairGz.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairGz.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairGzFormDetail";
	}
	//这是用来跳部门审核dialog页面的
	@RequiresPermissions("affair:affairGz:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairGzCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairGz:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairGz affairGz) {
		affairGzService.shenHe(affairGz);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@ResponseBody
	@RequiresPermissions("affair:affairGz:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGzService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairGz affairGz, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairGz> page = null;
			if(flag == true){
				page = affairGzService.findPage(new Page<AffairGz>(request, response), affairGz);
			}else {
				page = affairGzService.findPage(new Page<AffairGz>(request, response,-1), affairGz);
			}
/*
			Page<AffairGz> page = affairGzService.findPage(new Page<AffairGz>(request, response,-1), affairGz);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGz.class);
			exportExcelNew.setWb(wb);
			List<AffairGz> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairGz/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairGz> list = ei.getDataList(AffairGz.class);
			for (AffairGz affairGz : list){
				try{
					if (affairGz.getUnit()!=null&&!"".equals(affairGz.getUnit())){
						affairGz.setUnitId(officeService.findByName(affairGz.getUnit()));
					}
					BeanValidators.validateWithException(validator, affairGz);
					affairGzService.save(affairGz);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(固资名称:"+affairGz.getName()+")"+" 导入失败："+ex.getMessage());
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