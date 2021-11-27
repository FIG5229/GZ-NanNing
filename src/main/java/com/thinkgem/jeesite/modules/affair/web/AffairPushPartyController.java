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
import com.thinkgem.jeesite.modules.affair.entity.AffairPushParty;
import com.thinkgem.jeesite.modules.affair.service.AffairPushPartyService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 推优入党Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPushParty")
public class AffairPushPartyController extends BaseController {

	@Autowired
	private DictDao dictDao;
	@Autowired
	private AffairPushPartyService affairPushPartyService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairPushParty get(@RequestParam(required=false) String id) {
		AffairPushParty entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPushPartyService.get(id);
		}
		if (entity == null){
			entity = new AffairPushParty();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPushParty:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPushParty affairPushParty, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPushParty> page = affairPushPartyService.findPage(new Page<AffairPushParty>(request, response), affairPushParty); 
		model.addAttribute("page", page);
		return "modules/affair/affairPushPartyList";
	}

	@RequiresPermissions("affair:affairPushParty:view")
	@RequestMapping(value = "form")
	public String form(AffairPushParty affairPushParty, Model model) {
		model.addAttribute("affairPushParty", affairPushParty);
		return "modules/affair/affairPushPartyForm";
	}

	@RequiresPermissions("affair:affairPushParty:edit")
	@RequestMapping(value = "save")
	public String save(AffairPushParty affairPushParty, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPushParty)){
			return form(affairPushParty, model);
		}
		affairPushPartyService.save(affairPushParty);
		addMessage(redirectAttributes, "保存推优入党成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPushPartyForm";
	}
	
	@RequiresPermissions("affair:affairPushParty:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPushParty affairPushParty, RedirectAttributes redirectAttributes) {
		affairPushPartyService.delete(affairPushParty);
		addMessage(redirectAttributes, "删除推优入党成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPushParty/?repage";
	}

	/**
	 * 详情
	 * @param affairPushParty
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPushParty:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPushParty affairPushParty, Model model) {
		model.addAttribute("affairPushParty", affairPushParty);
		if(affairPushParty.getFilePath() != null && affairPushParty.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPushParty.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPushPartyFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPushParty:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPushPartyService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairPushParty:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairPushPartyCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairPushParty:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairPushParty affairPushParty, HttpServletRequest request) {
		if (StringUtils.isNotBlank(affairPushParty.getTwoCheckId())){
			affairPushParty.setTwoCheckMan(affairPushParty.getTwoCheckId());
		}
		affairPushPartyService.save(affairPushParty);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
	/**
	 * 批量提交
	 * @param
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
		List<String> userList = new ArrayList<String>();
		String oneCheckId = request.getParameter("oneCheckId");
		String oneCheckMan = dictDao.findLabelByValue(oneCheckId);
		Collections.addAll(userList,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List<AffairPushParty> list = affairPushPartyService.findByIds(userList);
		for (AffairPushParty affairPushParty: list){
			affairPushParty.setCheckType("2");
			affairPushParty.setOneCheckMan(oneCheckMan);
			affairPushParty.setOneCheckId(oneCheckId);
			affairPushParty.setSubmitId(user.getId());
			affairPushParty.setSubmitMan(user.getName());
			affairPushPartyService.save(affairPushParty);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairPushParty/list";
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
	public String exportExcelByTemplate(AffairPushParty affairPushParty, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairPushParty> page = null;
			if(flag == true){
				page = affairPushPartyService.findPage(new Page<AffairPushParty>(request, response), affairPushParty);
			}else {
				page = affairPushPartyService.findPage(new Page<AffairPushParty>(request, response,-1), affairPushParty);
			}
/*
			Page<AffairPushParty> page = affairPushPartyService.findPage(new Page<AffairPushParty>(request, response,-1), affairPushParty);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPushParty.class);
			exportExcelNew.setWb(wb);
			List<AffairPushParty> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairPushParty/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairPushParty> list = ei.getDataList(AffairPushParty.class);
			for (AffairPushParty affairPushParty : list){
				try{
					BeanValidators.validateWithException(validator, affairPushParty);
					affairPushPartyService.save(affairPushParty);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairPushParty.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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