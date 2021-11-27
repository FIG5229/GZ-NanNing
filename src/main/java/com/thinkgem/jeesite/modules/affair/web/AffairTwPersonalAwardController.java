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
import com.thinkgem.jeesite.modules.affair.entity.AffairTwPersonalAward;
import com.thinkgem.jeesite.modules.affair.service.AffairTwPersonalAwardService;
import com.thinkgem.jeesite.modules.affair.service.AffairYouthTalentService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 团委个人表彰Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTwPersonalAward")
public class AffairTwPersonalAwardController extends BaseController {

	@Autowired
	private AffairYouthTalentService affairYouthTalentService;

	@Autowired
	private AffairTwPersonalAwardService affairTwPersonalAwardService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairTwPersonalAward get(@RequestParam(required=false) String id) {
		AffairTwPersonalAward entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTwPersonalAwardService.get(id);
		}
		if (entity == null){
			entity = new AffairTwPersonalAward();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTwPersonalAward:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTwPersonalAward affairTwPersonalAward, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTwPersonalAward> page = affairTwPersonalAwardService.findPage(new Page<AffairTwPersonalAward>(request, response), affairTwPersonalAward); 
		model.addAttribute("page", page);
		return "modules/affair/affairTwPersonalAwardList";
	}

	@RequiresPermissions("affair:affairTwPersonalAward:view")
	@RequestMapping(value = "form")
	public String form(AffairTwPersonalAward affairTwPersonalAward, Model model) {
		model.addAttribute("affairTwPersonalAward", affairTwPersonalAward);
		return "modules/affair/affairTwPersonalAwardForm";
	}

	@RequiresPermissions("affair:affairTwPersonalAward:edit")
	@RequestMapping(value = "save")
	public String save(AffairTwPersonalAward affairTwPersonalAward, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTwPersonalAward)){
			return form(affairTwPersonalAward, model);
		}
		affairTwPersonalAwardService.save(affairTwPersonalAward);
		addMessage(redirectAttributes, "保存团委个人表彰成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTwPersonalAwardForm";
	}
	
	@RequiresPermissions("affair:affairTwPersonalAward:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTwPersonalAward affairTwPersonalAward, RedirectAttributes redirectAttributes) {
		affairTwPersonalAwardService.delete(affairTwPersonalAward);
		addMessage(redirectAttributes, "删除团委个人表彰成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTwPersonalAward/?repage";
	}

	/**
	 * 详情
	 * @param affairTwPersonalAward
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("affair:affairTwPersonalAward:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTwPersonalAward affairTwPersonalAward, Model model) {
		model.addAttribute("affairTwPersonalAward", affairTwPersonalAward);
		if(affairTwPersonalAward.getFilePath() != null && affairTwPersonalAward.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTwPersonalAward.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairTwPersonalAwardFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTwPersonalAward:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTwPersonalAwardService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 审核
	 * @return
	 */
	@RequiresPermissions("affair:affairTwPersonalAward:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog(String ids,Model model) {
		model.addAttribute("ids", ids);
		return "modules/affair/affairTwPersonalAwardCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairTwPersonalAward:edit")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairTwPersonalAward affairTwPersonalAward,HttpServletRequest request) {
		String opinion = affairTwPersonalAward.getOpinion();
		String Status = affairTwPersonalAward.getStatus();
		String idsStr = request.getParameter("ids");
		String[] idsArray = idsStr.split(",");
		List<String> userList = new ArrayList<String>();
		Collections.addAll(userList,idsArray);
		List <AffairTwPersonalAward> list = affairTwPersonalAwardService.findByIds(userList);
		for (AffairTwPersonalAward  affairTwPersonalAwardFromDb:list){
			affairTwPersonalAwardFromDb.setOpinion(opinion);
			affairTwPersonalAwardFromDb.setStatus(Status);
			affairTwPersonalAwardService.save(affairTwPersonalAwardFromDb);
		}
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
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
	public String exportExcelByTemplate(AffairTwPersonalAward affairTwPersonalAward, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairTwPersonalAward> page = null;
			if(flag == true){
				page = affairTwPersonalAwardService.findPage(new Page<AffairTwPersonalAward>(request, response), affairTwPersonalAward);
			}else{
				page = affairTwPersonalAwardService.findPage(new Page<AffairTwPersonalAward>(request, response,-1), affairTwPersonalAward);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTwPersonalAward.class);
			exportExcelNew.setWb(wb);
			List<AffairTwPersonalAward> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTwPersonalAward/?repage";
	}


	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTwPersonalAward> list = ei.getDataList(AffairTwPersonalAward.class);
			for (AffairTwPersonalAward affairTwPersonalAward : list){
				try{
					affairTwPersonalAward.setUnitId(officeService.findByName(affairTwPersonalAward.getUnit()));
					affairTwPersonalAward.setTypeFlag("2");
					BeanValidators.validateWithException(validator, affairTwPersonalAward);
					affairTwPersonalAwardService.save(affairTwPersonalAward);
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