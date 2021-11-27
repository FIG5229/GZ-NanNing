/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.dao.AffairGhActivityEnrollChildrenDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityEnroll;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityEnrollChildren;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHomeChild;
import com.thinkgem.jeesite.modules.affair.service.AffairGhActivityEnrollService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
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

/**
 * 工会活动报名Controller
 * @author cecil.li
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGhActivityEnroll")
public class AffairGhActivityEnrollController extends BaseController {

	@Autowired
	private DictDao dictDao;
	@Autowired
	private AffairGhActivityEnrollService affairGhActivityEnrollService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AffairGhActivityEnrollChildrenDao enrollChildrenDao;
	@Autowired
	private OfficeService officeService;
	@ModelAttribute
	public AffairGhActivityEnroll get(@RequestParam(required=false) String id) {
		AffairGhActivityEnroll entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGhActivityEnrollService.get(id);
		}
		if (entity == null){
			entity = new AffairGhActivityEnroll();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairGhActivityEnroll:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairGhActivityEnroll affairGhActivityEnroll, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairGhActivityEnroll> page = affairGhActivityEnrollService.findPage(new Page<AffairGhActivityEnroll>(request, response), affairGhActivityEnroll); 
		model.addAttribute("page", page);
		return "modules/affair/affairGhActivityEnrollList";
	}

	@RequiresPermissions("affair:affairGhActivityEnroll:view")
	@RequestMapping(value = "form")
	public String form(AffairGhActivityEnroll affairGhActivityEnroll, Model model) {
		if (!StringUtils.isEmpty(affairGhActivityEnroll.getId())){
			List<AffairGhActivityEnrollChildren> list=enrollChildrenDao.findByGhActivityEnrollId(affairGhActivityEnroll.getId());
			affairGhActivityEnroll.setActivityEnrollChildrenList(list);
			model.addAttribute("id",affairGhActivityEnroll.getId());
			affairGhActivityEnroll.setIsAdd("false");
			model.addAttribute("affairGhActivityEnroll", affairGhActivityEnroll);
			return "modules/affair/affairGhActivityEnrollFormUpdate";
		}else{
			//导入子表时  先生成主表id 然后在保存
			/*导入子表时使用 ，不导入则无用*/
			IdGen idGen=new IdGen();
			String id = idGen.getNextId();
			model.addAttribute("id",id);
			affairGhActivityEnroll.setIsAdd("true");
			model.addAttribute("affairGhActivityEnroll", affairGhActivityEnroll);
			return "modules/affair/affairGhActivityEnrollForm";
		}

	}

	/*
	*
	* 补充人员信息
	* */
	@RequiresPermissions("affair:affairGhActivityEnroll:view")
	@RequestMapping(value = "addPeople")
	public String addPeople(AffairGhActivityEnroll affairGhActivityEnroll, Model model) {
		//导入子表时  先生成主表id 然后在保存
		/*导入子表时使用 ，不导入则无用*/
		affairGhActivityEnroll.setId(null);
		IdGen idGen=new IdGen();
		String id = idGen.getNextId();
		model.addAttribute("id",id);
		affairGhActivityEnroll.setIsAdd("true");
		model.addAttribute("affairGhActivityEnroll", affairGhActivityEnroll);
		return "modules/affair/affairGhActivityEnrollForm";


	}

	@RequiresPermissions("affair:affairGhActivityEnroll:edit")
	@RequestMapping(value = "save")
	public String save(AffairGhActivityEnroll affairGhActivityEnroll, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request
	) {
		if (!beanValidator(model, affairGhActivityEnroll)){
			return form(affairGhActivityEnroll, model);
		}
		if(affairGhActivityEnroll.getId()!= null && !affairGhActivityEnroll.getId().isEmpty()){
			affairGhActivityEnrollService.save(affairGhActivityEnroll);
			addMessage(redirectAttributes, "保存工会活动报名成功");
			request.setAttribute("saveResult","success");
			return "modules/affair/affairGhActivityEnrollFormUpdate";
		}
		/*不为空则是导入子表*/
		String isImport = affairGhActivityEnroll.getIsImport();
		/*genId生成的Id*/
		String genId = request.getParameter("genId");
		if (!StringUtils.isEmpty(isImport) && isImport.equals("true")&&affairGhActivityEnroll.getIsAdd().equals("true")){
			affairGhActivityEnroll.setIsNewRecord(true);
			if (!StringUtils.isEmpty(genId)){
				affairGhActivityEnroll.setId(genId);
			}
		}
		affairGhActivityEnrollService.addSave(affairGhActivityEnroll);
		addMessage(redirectAttributes, "保存工会活动报名成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairGhActivityEnrollForm";
	}
	
	@RequiresPermissions("affair:affairGhActivityEnroll:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGhActivityEnroll affairGhActivityEnroll, RedirectAttributes redirectAttributes) {
		affairGhActivityEnrollService.delete(affairGhActivityEnroll);
		addMessage(redirectAttributes, "删除工会活动报名成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGhActivityEnroll/?repage";
	}

	@RequiresPermissions("affair:affairGhActivityEnroll:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairGhActivityEnrollCheckDialog";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairGhActivityEnroll:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairGhActivityEnroll affairGhActivityEnroll,HttpServletRequest request) {
		if(StringUtils.isNotBlank(affairGhActivityEnroll.getTwoCheckId())){
			affairGhActivityEnroll.setTwoCheckMan(dictDao.findLabelByValue(affairGhActivityEnroll.getTwoCheckId()));
		}
		affairGhActivityEnrollService.save(affairGhActivityEnroll);
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
		List <AffairGhActivityEnroll> list = affairGhActivityEnrollService.findByIds(userList);
        for (AffairGhActivityEnroll affairGhActivityEnroll: list){
            affairGhActivityEnroll.setCheckType("2");
            affairGhActivityEnroll.setOneCheckMan(oneCheckMan);
            affairGhActivityEnroll.setOneCheckId(oneCheckId);
            affairGhActivityEnroll.setSubmitId(user.getId());
            affairGhActivityEnroll.setSubmitMan(user.getName());
            affairGhActivityEnrollService.save(affairGhActivityEnroll);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairGhActivityEnroll/list";
	}
	/**
	 * 详情
	 * @param affairGhActivityEnroll
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairGhActivityEnroll:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairGhActivityEnroll affairGhActivityEnroll, Model model) {
		model.addAttribute("affairGhActivityEnroll", affairGhActivityEnroll);
		return "modules/affair/affairGhActivityEnrollFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairGhActivityEnroll:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairGhActivityEnrollService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairGhActivityEnroll affairGhActivityEnroll, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairGhActivityEnroll> page = null;
			if(flag == true){
				page = affairGhActivityEnrollService.findPage(new Page<AffairGhActivityEnroll>(request, response), affairGhActivityEnroll);
			}else{
				page = affairGhActivityEnrollService.findPage(new Page<AffairGhActivityEnroll>(request, response,-1), affairGhActivityEnroll);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairGhActivityEnroll.class);
			exportExcelNew.setWb(wb);
			List<AffairGhActivityEnroll> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairGhActivityEnroll/?repage";
	}


	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairGhActivityEnroll> list = ei.getDataList(AffairGhActivityEnroll.class);
			for (AffairGhActivityEnroll affairGhActivityEnroll : list){
				try{
					BeanValidators.validateWithException(validator, affairGhActivityEnroll);

					affairGhActivityEnrollService.save(affairGhActivityEnroll);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairGhActivityEnroll.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

	//打开导入页面
	@RequestMapping(value = "template/import")
	public String download(HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String id ="";
		if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
			id=request.getParameter("id").toString();
			request.setAttribute("id",id);
		}
		if(null != request.getParameter("fileName") && !"".equals(request.getParameter("fileName"))){
			String template=request.getParameter("fileName").toString();
			request.setAttribute("template",template);
		}
		request.setAttribute("url","/affair/affairGhActivityEnroll/importChildRecordFile");
		return "modules/affair/child_template_import";
	}
	/**
	 * 导入子表
	 * @param file
	 * @param redirectAttributes
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "importChildRecordFile", method= RequestMethod.POST)
	public String importChildRecordFile(MultipartFile file, RedirectAttributes redirectAttributes,String id,Model model) {

		AffairGhActivityEnroll activityEnroll = new AffairGhActivityEnroll();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairGhActivityEnrollChildren> list = ei.getDataList(AffairGhActivityEnrollChildren.class);
			activityEnroll.setActivityEnrollChildrenList(list);
			for (AffairGhActivityEnrollChildren children : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(children.getUnit());
					if(orgId != null){
						children.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, children);
					children.setGhActivityEnrollId(id);
					children.preInsert();
					enrollChildrenDao.insert(children);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(人员:"+children.getName()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		addMessage(redirectAttributes, "已成功导入 ");
		redirectAttributes.addFlashAttribute("result","success");
		redirectAttributes.addFlashAttribute("activityEnroll",activityEnroll);
		model.addAttribute("result","success");
//		return "redirect:" + adminPath + "/affair/affairTypicalPerson/template/import";
		model.addAttribute("url","/affair/affairGhActivityEnroll/importChildRecordFile");
		return "modules/affair/child_template_import";
	}

	@RequestMapping("getChildById")
	@ResponseBody
	public Result getChildById(String id){
		Result result= new Result();
		try {
			List<AffairGhActivityEnrollChildren> list = enrollChildrenDao.findByGhActivityEnrollId(id);
			result.setResult(list);
			result.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
}