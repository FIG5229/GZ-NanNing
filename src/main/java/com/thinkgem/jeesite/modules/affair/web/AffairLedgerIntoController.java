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
import com.thinkgem.jeesite.modules.affair.entity.AffairLedgerInto;
import com.thinkgem.jeesite.modules.affair.service.*;
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
 * 档案台账转入Controller
 * @author mason.xv
 * @version 2019-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLedgerInto")
public class AffairLedgerIntoController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairLedgerIntoService affairLedgerIntoService;
	@Autowired
	private AffairLedgerOutService affairLedgerOutService;
	@Autowired
	private AffairConsultService affairConsultService;
	@Autowired
	private AffairBorrowService affairBorrowService;
/* 	@Autowired
	private AffairArchiveApprovalService affairArchiveApprovalService;*/
    @Autowired
	private AffairArchiveRegisterService affairArchiveRegisterService;
    @Autowired
	private AffairRetireService affairRetireService;
    @Autowired
	private AffairDeathService affairDeathService;
    @Autowired
	private AffairMaterialService affairMaterialService;
    @Autowired
	private AffairTemHumService affairTemHumService;
    @Autowired
	private AffairCheckService affairCheckService;
    @Autowired
	private AffairSecurityCheckService affairSecurityCheckService;
    @Autowired
	private AffairDestroyMeterialService affairDestroyMeterialService;
    @Autowired
	private AffairCopyService affairCopyService;
	@ModelAttribute
	public AffairLedgerInto get(@RequestParam(required=false) String id) {
		AffairLedgerInto entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLedgerIntoService.get(id);
		}
		if (entity == null){
			entity = new AffairLedgerInto();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLedgerInto:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLedgerInto affairLedgerInto, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairLedgerInto> page = affairLedgerIntoService.findPage(new Page<AffairLedgerInto>(request, response), affairLedgerInto); 
		model.addAttribute("page", page);
		return "modules/affair/affairLedgerIntoList";
	}

	@RequiresPermissions("affair:affairLedgerInto:view")
	@RequestMapping(value = "form")
	public String form(AffairLedgerInto affairLedgerInto, Model model) {
		model.addAttribute("affairLedgerInto", affairLedgerInto);
		return "modules/affair/affairLedgerIntoForm";
	}

	@RequiresPermissions("affair:affairLedgerInto:edit")
	@RequestMapping(value = "save")
	public String save(AffairLedgerInto affairLedgerInto, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairLedgerInto)){
			return form(affairLedgerInto, model);
		}
		affairLedgerIntoService.save(affairLedgerInto);
		addMessage(redirectAttributes, "保存档案台账转入成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairLedgerIntoForm";
	}
	
	@RequiresPermissions("affair:affairLedgerInto:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLedgerInto affairLedgerInto, RedirectAttributes redirectAttributes) {
		affairLedgerIntoService.delete(affairLedgerInto);
		addMessage(redirectAttributes, "删除档案台账转入成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLedgerInto/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairLedgerInto:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLedgerIntoService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairLedgerInto:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLedgerInto affairLedgerInto, Model model) {
		model.addAttribute("affairLedgerInto", affairLedgerInto);
		return "modules/affair/affairLedgerIntoFormDetail";
	}

	//这是用来跳转批量导出的controller
	@RequiresPermissions("affair:affairLedgerInto:edit")
	@RequestMapping(value = {"accountExport"})
	public String accountExport(HttpServletRequest request, Model model) {
		String fileUnit = request.getParameter("fileUnit");
		model.addAttribute("fileUnit",fileUnit);
		return "modules/affair/accountExport";
	}

	//这是用来跳转批量导入的controller
	@RequiresPermissions("affair:affairLedgerInto:edit")
	@RequestMapping(value = {"accountImport"})
	public String accountImport() {
		return "modules/affair/accountImport";
	}

	/**
	 * 批量导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	/*@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairLedgerInto affairLedgerInto, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		XSSFWorkbook wb = null;
		try
		{
            String fileName = "";
            if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
                fileName= request.getParameter("fileName").toString();
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
		    String[] values=request.getParameterValues("myCheckBox");

            AffairLedgerOut affairLedgerOut = new AffairLedgerOut();

			AffairConsult affairConsult = new AffairConsult();

			AffairBorrow affairBorrow = new AffairBorrow();

			*//*AffairArchiveApproval affairArchiveApproval = new AffairArchiveApproval();*//*

			AffairArchiveRegister affairArchiveRegister = new AffairArchiveRegister();

			AffairRetire affairRetire = new AffairRetire();

			AffairMaterial affairMaterial= new AffairMaterial();

			AffairDeath affairDeath = new AffairDeath();

			AffairTemHum affairTemHum = new AffairTemHum();

			AffairCheck affairCheck = new AffairCheck();

			AffairDestroyMeterial affairDestroyMeterial = new AffairDestroyMeterial();

			AffairSecurityCheck affairSecurityCheck = new AffairSecurityCheck();

			AffairCopy affairCopy = new AffairCopy();
			if(values != null && !"".equals(values)){
			    for (String i:values){
			        if(i.equals("0")){
                        ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairLedgerInto.class);
                        Page<AffairLedgerInto> page = affairLedgerIntoService.findPage(new Page<AffairLedgerInto>(request, response,-1), affairLedgerInto);
                        List<AffairLedgerInto> list =page.getList();
                        exportExcelNew.setWb(wb,0);
                        exportExcelNew.setDataList(list);
                    }else if (i.equals("1")){
                        ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairLedgerOut.class);
                        Page<AffairLedgerOut> page = affairLedgerOutService.findPage(new Page<AffairLedgerOut>(request, response,-1), affairLedgerOut);
                        List<AffairLedgerOut> list =page.getList();
                        exportExcelNew.setWb(wb,1);
                        exportExcelNew.setDataList(list);
                    }else if (i.equals("2")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairConsult.class);
						Page<AffairConsult> page = affairConsultService.findPage(new Page<AffairConsult>(request, response,-1), affairConsult);
						List<AffairConsult> list =page.getList();
						exportExcelNew.setWb(wb,2);
						exportExcelNew.setDataList(list);
					}else if (i.equals("3")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairBorrow.class);
						Page<AffairBorrow> page = affairBorrowService.findPage(new Page<AffairBorrow>(request, response,-1), affairBorrow);
						List<AffairBorrow> list =page.getList();
						exportExcelNew.setWb(wb,3);
						exportExcelNew.setDataList(list);
					}*//*else if (i.equals("4")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairArchiveApproval.class);
						Page<AffairArchiveApproval> page = affairArchiveApprovalService.findPage(new Page<AffairArchiveApproval>(request, response,-1),affairArchiveApproval);
						List<AffairArchiveApproval> list =page.getList();
						exportExcelNew.setWb(wb,4);
						exportExcelNew.setDataList(list);
					}*//*
					else if (i.equals("5")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairArchiveRegister.class);
						Page<AffairArchiveRegister> page = affairArchiveRegisterService.findPage(new Page<AffairArchiveRegister>(request, response,-1), affairArchiveRegister);
						List<AffairArchiveRegister> list =page.getList();
						exportExcelNew.setWb(wb,5);
						exportExcelNew.setDataList(list);
					}else if (i.equals("6")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairRetire.class);
						Page<AffairRetire> page = affairRetireService.findPage(new Page<AffairRetire>(request, response,-1), affairRetire);
						List<AffairRetire> list =page.getList();
						exportExcelNew.setWb(wb,6);
						exportExcelNew.setDataList(list);
					}else if (i.equals("7")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairDeath.class);
						Page<AffairDeath> page = affairDeathService.findPage(new Page<AffairDeath>(request, response,-1), affairDeath);
						List<AffairDeath> list =page.getList();
						exportExcelNew.setWb(wb,7);
						exportExcelNew.setDataList(list);
					}else if (i.equals("8")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairMaterial.class);
						Page<AffairMaterial> page = affairMaterialService.findPage(new Page<AffairMaterial>(request, response,-1), affairMaterial);
						List<AffairMaterial> list =page.getList();
						exportExcelNew.setWb(wb,8);
						exportExcelNew.setDataList(list);
					}else if (i.equals("9")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairTemHum.class);
						Page<AffairTemHum> page = affairTemHumService.findPage(new Page<AffairTemHum>(request, response,-1), affairTemHum);
						List<AffairTemHum> list =page.getList();
						exportExcelNew.setWb(wb,9);
						exportExcelNew.setDataList(list);
					}else if (i.equals("10")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairCheck.class);
						Page<AffairCheck> page = affairCheckService.findPage(new Page<AffairCheck>(request, response,-1), affairCheck);
						List<AffairCheck> list =page.getList();
						exportExcelNew.setWb(wb,10);
						exportExcelNew.setDataList(list);
					}else if (i.equals("11")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairSecurityCheck.class);
						Page<AffairSecurityCheck> page = affairSecurityCheckService.findPage(new Page<AffairSecurityCheck>(request, response,-1), affairSecurityCheck);
						List<AffairSecurityCheck> list =page.getList();
						exportExcelNew.setWb(wb,11);
						exportExcelNew.setDataList(list);
					}else if (i.equals("12")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(1, AffairDestroyMeterial.class);
						Page<AffairDestroyMeterial> page = affairDestroyMeterialService.findPage(new Page<AffairDestroyMeterial>(request, response,-1), affairDestroyMeterial);
						List<AffairDestroyMeterial> list =page.getList();
						exportExcelNew.setWb(wb,12);
						exportExcelNew.setDataList(list);
					}else if (i.equals("13")){
						ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairCopy.class);
						Page<AffairCopy> page = affairCopyService.findPage(new Page<AffairCopy>(request, response,-1), affairCopy);
						List<AffairCopy> list =page.getList();
						exportExcelNew.setWb(wb,13);
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
		return "redirect:" + adminPath + "modules/affair/accountExport";
	}*/
	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairLedgerInto affairLedgerInto, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairLedgerInto.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairLedgerInto> page = null;
			if(flag == true){
				page = affairLedgerIntoService.findPage(new Page<AffairLedgerInto>(request, response), affairLedgerInto);
			}else{
				page = affairLedgerIntoService.findPage(new Page<AffairLedgerInto>(request, response,-1), affairLedgerInto);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairLedgerInto.class);
			exportExcelNew.setWb(wb);
			List<AffairLedgerInto> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairLedgerInto/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 2, 0);
			List<AffairLedgerInto> list = ei.getDataList(AffairLedgerInto.class);
			for (AffairLedgerInto affairLedgerInto : list){
				try{
					affairLedgerInto.setNowUnitId(officeService.findByName(affairLedgerInto.getNowUnitId()));
					BeanValidators.validateWithException(validator, affairLedgerInto);
					affairLedgerIntoService.save(affairLedgerInto);
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