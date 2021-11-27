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
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.CORBA.PRIVATE_MEMBER;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkRecord;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkRecordService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 民警记实功能Controller
 * @author tom.fu
 * @version 2020-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkRecord")
public class AffairWorkRecordController extends BaseController {

	@Autowired
	private AffairWorkRecordService affairWorkRecordService;

	@Autowired
	private UploadController uploadController;


	@ModelAttribute
	public AffairWorkRecord get(@RequestParam(required=false) String id) {
		AffairWorkRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkRecordService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWorkRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkRecord affairWorkRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWorkRecord> page = affairWorkRecordService.findPage(new Page<AffairWorkRecord>(request, response), affairWorkRecord); 
		model.addAttribute("page", page);
		return "modules/affair/affairWorkRecordList";
	}

	@RequiresPermissions("affair:affairWorkRecord:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkRecord affairWorkRecord, Model model) {
		model.addAttribute("affairWorkRecord", affairWorkRecord);

		return "modules/affair/affairWorkRecordForm";
	}

	@RequiresPermissions("affair:affairWorkRecord:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkRecord affairWorkRecord, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairWorkRecord)){
			return form(affairWorkRecord, model);
		}

		affairWorkRecordService.save(affairWorkRecord);
		addMessage(redirectAttributes, "保存民警记实功能成功");
        request.setAttribute("saveResult","success");
		return "modules/affair/affairWorkRecordForm";
	}


	@RequiresPermissions("affair:affairWorkRecord:fuzeren")
	@RequestMapping(value = "formSaveUnit")
	public String formSaveUnit(AffairWorkRecord affairWorkRecord, Model model) {
		model.addAttribute("affairWorkRecord", affairWorkRecord);

		return "modules/affair/affairWorkRecordsaveUnitForm";
	}


	@RequiresPermissions("affair:affairWorkRecord:fuzeren")
	@RequestMapping(value = "saveUnit")
	public String saveUnit(AffairWorkRecord affairWorkRecord, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request,@ModelAttribute(value = "affairWorkRecord") AffairWorkRecord affairWorkRecordSaveUnit) {
		if (!beanValidator(model, affairWorkRecord)){
			return form(affairWorkRecord, model);
		}



		affairWorkRecordService.save(affairWorkRecord);
		addMessage(redirectAttributes, "保存民警记实功能成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairWorkRecordsaveUnitForm";
	}
	
	@RequiresPermissions("affair:affairWorkRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkRecord affairWorkRecord, RedirectAttributes redirectAttributes) {
		affairWorkRecordService.delete(affairWorkRecord);
		addMessage(redirectAttributes, "删除民警记实功能成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkRecord/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairActivityMien:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkRecordService.deleteByIds(ids);
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
	 * @param affairWorkRecord
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkRecord:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkRecord affairWorkRecord, Model model) {
		model.addAttribute("affairWorkRecord", affairWorkRecord);
		if(affairWorkRecord.getAppendfile() != null && affairWorkRecord.getAppendfile().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkRecord.getAppendfile());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkRecordFormDetail";
	}

	/**
	 * 批量提交
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> idList = new ArrayList<String>();
		String chuCheckId = request.getParameter("chuCheckId");
		String chuCheckMan = request.getParameter("chuCheckMan");
		Collections.addAll(idList,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List <AffairWorkRecord> list = affairWorkRecordService.findByIds(idList);
		for (AffairWorkRecord affairWorkRecord: list){
			affairWorkRecord.setCheckType("2");
			affairWorkRecord.setChuCheckMan(chuCheckMan);
			affairWorkRecord.setChuCheckId(chuCheckId);
			affairWorkRecord.setSubmitId(user.getId());
			affairWorkRecord.setSubmitMan(user.getName());
			affairWorkRecordService.save(affairWorkRecord);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkRecord/?repage";
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
			List<AffairWorkRecord> list = ei.getDataList(AffairWorkRecord.class);
			for (AffairWorkRecord affairWorkRecord : list){
				try{

					BeanValidators.validateWithException(validator, affairWorkRecord);

					affairWorkRecordService.save(affairWorkRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(民警纪实:"+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/download/view?repage";
	}
	/**
	 * 导出excel格式数据
	 * @param affairWorkRecord
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairWorkRecord affairWorkRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairWorkRecord> page = null;
			if(flag == true){
				page = affairWorkRecordService.findPage(new Page<AffairWorkRecord>(request, response), affairWorkRecord);
			}else{
				page = affairWorkRecordService.findPage(new Page<AffairWorkRecord>(request, response,-1), affairWorkRecord);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairWorkRecord.class);
			exportExcelNew.setWb(wb);
			List<AffairWorkRecord> list =page.getList();
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
			addMessage(redirectAttributes, "导出民警纪实列表失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairWorkRecord?repage";
	}

	/**
	 * 审核页面
	 * @return
	 */
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {

		return "modules/affair/affairWorkRecordCheckDialog";
	}

	@ResponseBody
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairWorkRecord affairWorkRecord, HttpServletRequest request) {
		if("0".equals(affairWorkRecord.getEvaluate())){
			affairWorkRecord.setEvaluate(null);
		}
		affairWorkRecordService.save(affairWorkRecord);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
    /*统计分析*/
    @RequestMapping(value = "workRecordStatistic")
    public String workRecordStatistic(){
	    /*未完成*/
	    return "modules/charts/workRecordStatistic";
    }


}