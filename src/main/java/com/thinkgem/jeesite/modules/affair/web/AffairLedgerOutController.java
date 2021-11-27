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
import com.thinkgem.jeesite.modules.affair.entity.AffairLedgerOut;
import com.thinkgem.jeesite.modules.affair.service.AffairLedgerOutService;
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

/**
 * 档案台账转出Controller
 * @author mason.xv
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLedgerOut")
public class AffairLedgerOutController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairLedgerOutService affairLedgerOutService;
	
	@ModelAttribute
	public AffairLedgerOut get(@RequestParam(required=false) String id) {
		AffairLedgerOut entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLedgerOutService.get(id);
		}
		if (entity == null){
			entity = new AffairLedgerOut();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLedgerOut:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLedgerOut affairLedgerOut, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairLedgerOut> page = affairLedgerOutService.findPage(new Page<AffairLedgerOut>(request, response), affairLedgerOut); 
		model.addAttribute("page", page);
		return "modules/affair/affairLedgerOutList";
	}

	@RequiresPermissions("affair:affairLedgerOut:view")
	@RequestMapping(value = "form")
	public String form(AffairLedgerOut affairLedgerOut, Model model) {
		model.addAttribute("affairLedgerOut", affairLedgerOut);
		return "modules/affair/affairLedgerOutForm";
	}

	@RequiresPermissions("affair:affairLedgerOut:edit")
	@RequestMapping(value = "save")
	public String save(AffairLedgerOut affairLedgerOut, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairLedgerOut)){
			return form(affairLedgerOut, model);
		}
		affairLedgerOutService.save(affairLedgerOut);
		addMessage(redirectAttributes, "保存档案台账转出成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairLedgerOutForm";
	}
	
	@RequiresPermissions("affair:affairLedgerOut:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLedgerOut affairLedgerOut, RedirectAttributes redirectAttributes) {
		affairLedgerOutService.delete(affairLedgerOut);
		addMessage(redirectAttributes, "删除档案台账转出成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLedgerOut/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairLedgerOut:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLedgerOutService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairLedgerOut:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLedgerOut affairLedgerOut, Model model) {
		model.addAttribute("affairLedgerOut", affairLedgerOut);
		return "modules/affair/affairLedgerOutFormDetail";
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
	public String exportExcelByTemplate(AffairLedgerOut affairLedgerOut, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairLedgerOut.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairLedgerOut> page = null;
			if(flag == true){
				page = affairLedgerOutService.findPage(new Page<AffairLedgerOut>(request, response), affairLedgerOut);
			}else{
				page = affairLedgerOutService.findPage(new Page<AffairLedgerOut>(request, response,-1), affairLedgerOut);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairLedgerOut.class);
			exportExcelNew.setWb(wb);
			List<AffairLedgerOut> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairLedgerOut/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 2, 0);
			List<AffairLedgerOut> list = ei.getDataList(AffairLedgerOut.class);
			for (AffairLedgerOut affairLedgerOut : list){
				try{
					affairLedgerOut.setOldUnitId(officeService.findByName(affairLedgerOut.getOldUnitId()));
					BeanValidators.validateWithException(validator, affairLedgerOut);
					affairLedgerOutService.save(affairLedgerOut);
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

}