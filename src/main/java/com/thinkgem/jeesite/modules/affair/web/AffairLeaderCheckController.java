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
import com.thinkgem.jeesite.modules.affair.entity.AffairLeaderCheck;
import com.thinkgem.jeesite.modules.affair.service.AffairLeaderCheckService;
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
 * 领导干部年度考核表Controller
 * @author mason.xv
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLeaderCheck")
public class AffairLeaderCheckController extends BaseController {
    @Autowired
	private OfficeService officeService;
	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairLeaderCheckService affairLeaderCheckService;
	
	@ModelAttribute
	public AffairLeaderCheck get(@RequestParam(required=false) String id) {
		AffairLeaderCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLeaderCheckService.get(id);
		}
		if (entity == null){
			entity = new AffairLeaderCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLeaderCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLeaderCheck affairLeaderCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairLeaderCheck> page = affairLeaderCheckService.findPage(new Page<AffairLeaderCheck>(request, response), affairLeaderCheck); 
		model.addAttribute("page", page);
		return "modules/affair/affairLeaderCheckList";
	}

	@RequiresPermissions("affair:affairLeaderCheck:view")
	@RequestMapping(value = "form")
	public String form(AffairLeaderCheck affairLeaderCheck, Model model) {
		model.addAttribute("affairLeaderCheck", affairLeaderCheck);
		return "modules/affair/affairLeaderCheckForm";
	}

	@RequiresPermissions("affair:affairLeaderCheck:edit")
	@RequestMapping(value = "save")
	public String save(AffairLeaderCheck affairLeaderCheck, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairLeaderCheck)){
			return form(affairLeaderCheck, model);
		}
		affairLeaderCheckService.save(affairLeaderCheck);
		addMessage(redirectAttributes, "保存领导干部年度考核表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairLeaderCheckForm";
	}
	
	@RequiresPermissions("affair:affairLeaderCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLeaderCheck affairLeaderCheck, RedirectAttributes redirectAttributes) {
		affairLeaderCheckService.delete(affairLeaderCheck);
		addMessage(redirectAttributes, "删除领导干部年度考核表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLeaderCheck/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairLeaderCheck:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLeaderCheckService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairLeaderCheck:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairLeaderCheck affairLeaderCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairLeaderCheck.setHasAuth("1");
		Page<AffairLeaderCheck> page = affairLeaderCheckService.findPage(new Page<AffairLeaderCheck>(request, response), affairLeaderCheck);
		model.addAttribute("page", page);
		return "modules/affair/affairLeaderCheckManage";
	}
	//这是用来跳转dialog页面的controller
	@RequiresPermissions("affair:affairLeaderCheck:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairLeaderCheckShenHeDialog";
	}

	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairLeaderCheck:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairLeaderCheck affairLeaderCheck) {
		affairLeaderCheckService.shenHe(affairLeaderCheck);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
	/**
	 * 批量提交
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLeaderCheck:edit")
	@RequestMapping(value = {"submitByIds"})
	public Result submitByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLeaderCheckService.submitByIds(ids);
			result.setSuccess(true);
			result.setMessage("提交成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要提交的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairLeaderCheck:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLeaderCheck affairLeaderCheck, Model model) {
		model.addAttribute("affairLeaderCheck", affairLeaderCheck);
		if(affairLeaderCheck.getMaterialPath() != null && affairLeaderCheck.getMaterialPath().length() > 0){
			List<Map<String, String>> materialPathList = uploadController.filePathHandle(affairLeaderCheck.getMaterialPath());
			model.addAttribute("filePathList",materialPathList );
		}
		return "modules/affair/affairLeaderCheckFormDetail";
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
	public String exportExcelByTemplate(AffairLeaderCheck affairLeaderCheck, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairLeaderCheck> page = null;
			if(flag == true){
				page = affairLeaderCheckService.findPage(new Page<AffairLeaderCheck>(request, response), affairLeaderCheck);
			}else{
				page = affairLeaderCheckService.findPage(new Page<AffairLeaderCheck>(request, response,-1), affairLeaderCheck);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLeaderCheck.class);
			exportExcelNew.setWb(wb);
			List<AffairLeaderCheck> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairLeaderCheck";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairLeaderCheck> list = ei.getDataList(AffairLeaderCheck.class);
			for (AffairLeaderCheck affairLeaderCheck : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairLeaderCheck.getUnit());
					if(orgId != null){
						affairLeaderCheck.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, affairLeaderCheck);
					affairLeaderCheckService.save(affairLeaderCheck);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairLeaderCheck.getName()+" 导入失败："+ex.getMessage());
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