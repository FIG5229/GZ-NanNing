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
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityManage;
import com.thinkgem.jeesite.modules.affair.service.AffairGhActivityManageService;
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
 * 工会活动管理Controller
 * @author mason.xv
 * @version 2020-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGhActivityManage")
public class AffairGhActivityManageController extends BaseController {

	@Autowired
	private AffairGhActivityManageService affairGhActivityManageService;
	@Autowired
	private DictDao dictDao;
	@Autowired
    private UploadController uploadController;
	
	@ModelAttribute
	public AffairGhActivityManage get(@RequestParam(required=false) String id) {
		AffairGhActivityManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGhActivityManageService.get(id);
		}
		if (entity == null){
			entity = new AffairGhActivityManage();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairGhActivityManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairGhActivityManage affairGhActivityManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairGhActivityManage> page = affairGhActivityManageService.findPage(new Page<AffairGhActivityManage>(request, response), affairGhActivityManage); 
		model.addAttribute("page", page);
		return "modules/affair/affairGhActivityManageList";
	}

	@RequiresPermissions("affair:affairGhActivityManage:view")
	@RequestMapping(value = "form")
	public String form(AffairGhActivityManage affairGhActivityManage, Model model) {
		model.addAttribute("affairGhActivityManage", affairGhActivityManage);
		return "modules/affair/affairGhActivityManageForm";
	}

	@RequiresPermissions("affair:affairGhActivityManage:edit")
	@RequestMapping(value = "save")
	public String save(AffairGhActivityManage affairGhActivityManage, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairGhActivityManage)){
			return form(affairGhActivityManage, model);
		}
		affairGhActivityManageService.save(affairGhActivityManage);
		addMessage(redirectAttributes, "保存工会活动管理成功");
        request.setAttribute("saveResult","success");
		return "modules/affair/affairGhActivityManageForm";
	}
	
	@RequiresPermissions("affair:affairGhActivityManage:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGhActivityManage affairGhActivityManage, RedirectAttributes redirectAttributes) {
		affairGhActivityManageService.delete(affairGhActivityManage);
		addMessage(redirectAttributes, "删除工会活动管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGhActivityManage/?repage";
	}
	/**
	 * 详情
	 * @param affairGhActivityManage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGhActivityManage:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairGhActivityManage affairGhActivityManage, Model model) {
        if (affairGhActivityManage.getFilePath() != null && affairGhActivityManage.getFilePath().length() > 0) {
            List<Map<String, String>> filePathList = uploadController.filePathHandle(affairGhActivityManage.getFilePath());
            model.addAttribute("filePathList", filePathList);
        }
		model.addAttribute("affairGhActivityManage", affairGhActivityManage);
		return "modules/affair/affairGhActivityManageFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairGhActivityManage:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGhActivityManageService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairGhActivityManage:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairGhActivityManageCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairGhActivityManage:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairGhActivityManage affairGhActivityManage, HttpServletRequest request) {
		if (StringUtils.isNotBlank(affairGhActivityManage.getTwoCheckId())){
			affairGhActivityManage.setTwoCheckMan(dictDao.findLabelByValue(affairGhActivityManage.getTwoCheckId()));
		}
		affairGhActivityManageService.save(affairGhActivityManage);
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
		List<AffairGhActivityManage> list = affairGhActivityManageService.findByIds(userList);
		for (AffairGhActivityManage affairGhActivityManage: list){
			affairGhActivityManage.setCheckType("2");
			affairGhActivityManage.setOneCheckMan(oneCheckMan);
			affairGhActivityManage.setOneCheckId(oneCheckId);
			affairGhActivityManage.setSubmitId(user.getId());
			affairGhActivityManage.setSubmitMan(user.getName());
			affairGhActivityManageService.save(affairGhActivityManage);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairGhActivityManage/list";
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
	public String exportExcelByTemplate(AffairGhActivityManage affairGhActivityManage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairGhActivityManage> page = null;
			if(flag == true){
				page = affairGhActivityManageService.findPage(new Page<AffairGhActivityManage>(request, response), affairGhActivityManage);
			}else {
				page = affairGhActivityManageService.findPage(new Page<AffairGhActivityManage>(request, response,-1), affairGhActivityManage);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGhActivityManage.class);
			exportExcelNew.setWb(wb);
			List<AffairGhActivityManage> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairGhActivityManage/?repage";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairGhActivityManage> list = ei.getDataList(AffairGhActivityManage.class);
			for (AffairGhActivityManage affairGhActivityManage : list){
				try{
					BeanValidators.validateWithException(validator, affairGhActivityManage);
					affairGhActivityManageService.save(affairGhActivityManage);
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