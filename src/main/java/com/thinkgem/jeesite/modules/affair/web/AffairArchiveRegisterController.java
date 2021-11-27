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
import com.thinkgem.jeesite.modules.affair.entity.AffairArchiveRegister;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeath;
import com.thinkgem.jeesite.modules.affair.entity.AffairRetire;
import com.thinkgem.jeesite.modules.affair.service.AffairArchiveRegisterService;
import com.thinkgem.jeesite.modules.affair.service.AffairDeathService;
import com.thinkgem.jeesite.modules.affair.service.AffairRetireService;
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

/**
 * 在职干部档案登记花名册Controller
 * @author mason.xv
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairArchiveRegister")
public class AffairArchiveRegisterController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairArchiveRegisterService affairArchiveRegisterService;

	@Autowired
	private AffairRetireService  affairRetireService;

	@Autowired
	private AffairDeathService affairDeathService;
	
	@ModelAttribute
	public AffairArchiveRegister get(@RequestParam(required=false) String id) {
		AffairArchiveRegister entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairArchiveRegisterService.get(id);
		}
		if (entity == null){
			entity = new AffairArchiveRegister();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairArchiveRegister:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairArchiveRegister affairArchiveRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairArchiveRegister> page = affairArchiveRegisterService.findPage(new Page<AffairArchiveRegister>(request, response), affairArchiveRegister); 
		model.addAttribute("page", page);
		return "modules/affair/affairArchiveRegisterList";
	}

	@RequestMapping(value = "taizhang")
	public String taizhang() {
		return "modules/affair/affairLedgerList";
	}

	@RequiresPermissions("affair:affairArchiveRegister:view")
	@RequestMapping(value = "form")
	public String form(AffairArchiveRegister affairArchiveRegister, Model model) {
		model.addAttribute("affairArchiveRegister", affairArchiveRegister);
		return "modules/affair/affairArchiveRegisterForm";
	}

	@RequiresPermissions("affair:affairArchiveRegister:edit")
	@RequestMapping(value = "save")
	public String save(AffairArchiveRegister affairArchiveRegister, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request) {
		if (!beanValidator(model, affairArchiveRegister)){
			return form(affairArchiveRegister, model);
		}
		affairArchiveRegisterService.save(affairArchiveRegister);
		addMessage(redirectAttributes, "保存在职干部档案登记花名册成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairArchiveRegisterForm";
	}
	
	@RequiresPermissions("affair:affairArchiveRegister:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairArchiveRegister affairArchiveRegister, RedirectAttributes redirectAttributes) {
		affairArchiveRegisterService.delete(affairArchiveRegister);
		addMessage(redirectAttributes, "删除在职干部档案登记花名册成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairArchiveRegister/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairArchiveRegister:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairArchiveRegisterService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairArchiveRegister:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairArchiveRegister affairArchiveRegister, Model model) {
		model.addAttribute("affairArchiveRegister", affairArchiveRegister);
		return "modules/affair/affairArchiveRegisterFormDetail";
	}

	/**
	 * 批量导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		XSSFWorkbook wb = null;
		try
		{
            String fileName = "";
            if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
                fileName= request.getParameter("fileName");
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
//		    String[] values={"1","2","3"};
			String[] values=request.getParameterValues("myCheckBox");
			AffairArchiveRegister affairArchiveRegister = new AffairArchiveRegister();

			AffairRetire affairRetire = new AffairRetire();

			AffairDeath affairDeath = new AffairDeath();
			String fileUnit = request.getParameter("fileUnit");
			if(values != null && !"".equals(values)){
			    for (String i:values){
			        if (i.equals("1")){
			        	affairArchiveRegister.setFileUnit(fileUnit);
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairArchiveRegister.class);
						Page<AffairArchiveRegister> page = affairArchiveRegisterService.findPage(new Page<AffairArchiveRegister>(request, response,-1), affairArchiveRegister);
						List<AffairArchiveRegister> list =page.getList();
						exportExcelNew.setWb(wb,0);
						exportExcelNew.setDataList(list);
					}else if (i.equals("2")){
			        	affairRetire.setFileUnit(fileUnit);
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairRetire.class);
						Page<AffairRetire> page = affairRetireService.findPage(new Page<AffairRetire>(request, response,-1), affairRetire);
						List<AffairRetire> list =page.getList();
						exportExcelNew.setWb(wb,1);
						exportExcelNew.setDataList(list);
					}else if (i.equals("3")){
			        	affairDeath.setFileUnit(fileUnit);
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairDeath.class);
						Page<AffairDeath> page = affairDeathService.findPage(new Page<AffairDeath>(request, response,-1), affairDeath);
						List<AffairDeath> list =page.getList();
						exportExcelNew.setWb(wb,2);
						exportExcelNew.setDataList(list);
					}
			        else{
                        addMessage(redirectAttributes, "导出失败");
                    }
                }
            }

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
		return "redirect:" + adminPath + "modules/affair/affairLedgerList";
	}


	/*@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairArchiveRegister affairArchiveRegister, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairArchiveRegister.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairArchiveRegister> page = null;
			if(flag == true){
				page = affairArchiveRegisterService.findPage(new Page<AffairArchiveRegister>(request, response), affairArchiveRegister);
			}else{
				page = affairArchiveRegisterService.findPage(new Page<AffairArchiveRegister>(request, response,-1), affairArchiveRegister);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairArchiveRegister.class);
			exportExcelNew.setWb(wb);
			List<AffairArchiveRegister> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairArchiveRegister/?repage";
	}*/


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairArchiveRegister> list = ei.getDataList(AffairArchiveRegister.class);
			for (AffairArchiveRegister affairArchiveRegister : list){
				try{
					affairArchiveRegister.setUnitId(officeService.findByName(affairArchiveRegister.getUnit()));
					BeanValidators.validateWithException(validator, affairArchiveRegister);
					affairArchiveRegisterService.save(affairArchiveRegister);
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