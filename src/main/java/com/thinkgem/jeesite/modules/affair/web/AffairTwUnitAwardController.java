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
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import com.thinkgem.jeesite.modules.affair.service.AffairTwUnitAwardService;
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
 * 团委集体表彰Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTwUnitAward")
public class AffairTwUnitAwardController extends BaseController {

	@Autowired
	private AffairTwUnitAwardService affairTwUnitAwardService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairTwUnitAward get(@RequestParam(required=false) String id) {
		AffairTwUnitAward entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTwUnitAwardService.get(id);
		}
		if (entity == null){
			entity = new AffairTwUnitAward();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTwUnitAward:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTwUnitAward affairTwUnitAward, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTwUnitAward> page = affairTwUnitAwardService.findPage(new Page<AffairTwUnitAward>(request, response), affairTwUnitAward);
		model.addAttribute("page", page);
		return "modules/affair/affairTwUnitAwardList";
	}

	@RequiresPermissions("affair:affairTwUnitAward:view")
	@RequestMapping(value = "form")
	public String form(AffairTwUnitAward affairTwUnitAward, Model model) {
		model.addAttribute("affairTwUnitAward", affairTwUnitAward);
		return "modules/affair/affairTwUnitAwardForm";
	}

	@RequiresPermissions("affair:affairTwUnitAward:edit")
	@RequestMapping(value = "save")
	public String save(AffairTwUnitAward affairTwUnitAward, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTwUnitAward)){
			return form(affairTwUnitAward, model);
		}
		affairTwUnitAwardService.save(affairTwUnitAward);
		addMessage(redirectAttributes, "保存团委集体表彰成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTwUnitAwardForm";
	}
	
	@RequiresPermissions("affair:affairTwUnitAward:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTwUnitAward affairTwUnitAward, RedirectAttributes redirectAttributes) {
		affairTwUnitAwardService.delete(affairTwUnitAward);
		addMessage(redirectAttributes, "删除团委集体表彰成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTwUnitAward/?repage";
	}

	/**
	 * 详情
	 * @param affairTwUnitAward
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("affair:affairTwUnitAward:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTwUnitAward affairTwUnitAward, Model model) {
		model.addAttribute("affairTwUnitAward", affairTwUnitAward);
		if(affairTwUnitAward.getFilePath() != null && affairTwUnitAward.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTwUnitAward.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairTwUnitAwardFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTwUnitAward:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTwUnitAwardService.deleteByIds(ids);
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
	@RequiresPermissions("affair:affairTwUnitAward:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog(String ids,Model model) {
		model.addAttribute("ids", ids);
		return "modules/affair/affairTwUnitAwardCheckDialog";
	}

	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairTwUnitAward:edit")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairTwUnitAward affairTwUnitAward,HttpServletRequest request) {
		String opinion = affairTwUnitAward.getOpinion();
		String Status = affairTwUnitAward.getStatus();
		String idsStr = request.getParameter("ids");
		String[] idsArray = idsStr.split(",");
		List<String> userList = new ArrayList<String>();
		Collections.addAll(userList,idsArray);
		List <AffairTwUnitAward> list = affairTwUnitAwardService.findByIds(userList);
		for (AffairTwUnitAward  affairTwUnitAwardFromDb:list){
			affairTwUnitAwardFromDb.setOpinion(opinion);
			affairTwUnitAwardFromDb.setStatus(Status);
			affairTwUnitAwardService.save(affairTwUnitAwardFromDb);
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
	public String exportExcelByTemplate(AffairTwUnitAward affairTwUnitAward, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairTwUnitAward> page = null;
			if(flag == true){
				page = affairTwUnitAwardService.findPage(new Page<AffairTwUnitAward>(request, response), affairTwUnitAward);
			}else{
				page = affairTwUnitAwardService.findPage(new Page<AffairTwUnitAward>(request, response,-1), affairTwUnitAward);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTwUnitAward.class);
			exportExcelNew.setWb(wb);
			List<AffairTwUnitAward> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTwUnitAward/?repage";
	}


	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTwUnitAward> list = ei.getDataList(AffairTwUnitAward.class);
			for (AffairTwUnitAward affairTwUnitAward : list){
				try{
					if (affairTwUnitAward.getUnit().length() >0 && affairTwUnitAward.getUnit() != null){
						String[] split = affairTwUnitAward.getUnit().split(",");
						String uid = "";
						if (split.length > 1){
							for (int i = 0; i < split.length; i++) {
								String byName = officeService.findByName(split[i]);
								if ("".equals(uid)){
									uid = byName;
								}else {
									uid = uid + "," +  byName;
								}
								affairTwUnitAward.setUnitId(uid);
							}
						}else {
							String byName = officeService.findByName(split[0]);
							uid = byName;
							affairTwUnitAward.setUnitId(uid);
						}

					}
					affairTwUnitAward.setTypeFlag("1");
					BeanValidators.validateWithException(validator, affairTwUnitAward);
					affairTwUnitAwardService.save(affairTwUnitAward);
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