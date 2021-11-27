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
import com.thinkgem.jeesite.modules.affair.dao.AffairYouthTalentDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwPersonalAward;
import com.thinkgem.jeesite.modules.affair.entity.AffairYouthTalent;
import com.thinkgem.jeesite.modules.affair.service.AffairTwPersonalAwardService;
import com.thinkgem.jeesite.modules.affair.service.AffairYouthTalentService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
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
 * 青年人才库Controller
 * @author cecil.li
 * @version 2019-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairYouthTalent")
public class AffairYouthTalentController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairYouthTalentDao affairYouthTalentDao;
	@Autowired
	private AffairTwPersonalAwardService affairTwPersonalAwardService;

	@Autowired
	private AffairYouthTalentService affairYouthTalentService;

	@Autowired
	private PersonnelBaseService personnelBaseService;
	
	@ModelAttribute
	public AffairYouthTalent get(@RequestParam(required=false) String id) {
		AffairYouthTalent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairYouthTalentService.get(id);
		}
		if (entity == null){
			entity = new AffairYouthTalent();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairYouthTalent:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairYouthTalent affairYouthTalent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairYouthTalent> page = affairYouthTalentService.findPage(new Page<AffairYouthTalent>(request, response), affairYouthTalent); 
		model.addAttribute("page", page);
		return "modules/affair/affairYouthTalentList";
	}

	@RequiresPermissions("affair:affairYouthTalent:view")
	@RequestMapping(value = "form")
	public String form(AffairYouthTalent affairYouthTalent, Model model) {
		model.addAttribute("affairYouthTalent", affairYouthTalent);
		return "modules/affair/affairYouthTalentForm";
	}

	@RequiresPermissions("affair:affairYouthTalent:edit")
	@RequestMapping(value = "save")
	public String save(AffairYouthTalent affairYouthTalent, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairYouthTalent)){
			return form(affairYouthTalent, model);
		}
		/*//创建一个不带任何信息的容器
		AffairYouthTalent affairYouthTalent0 = new AffairYouthTalent();
		//根据身份证号查重,一旦重复就删除数据库内数据,从新添加
		List<AffairYouthTalent> list= affairYouthTalentDao.findAllList(affairYouthTalent0);
		if (list.size() != 0 ){
			for(AffairYouthTalent affairYouthTalent1 : list){
				if (!affairYouthTalent1.getIdNumber().equals(affairYouthTalent.getIdNumber())){
					affairYouthTalentService.save(affairYouthTalent);
				}else{
					//这个地方要加友好提示
					affairYouthTalent.setId(affairYouthTalent1.getId());
					affairYouthTalentService.save(affairYouthTalent);
				}
			}}
		else{
			affairYouthTalentService.save(affairYouthTalent);
		}*/
		affairYouthTalentService.save(affairYouthTalent);
		addMessage(redirectAttributes, "保存青年人才库成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairYouthTalentForm";
	}
	
	@RequiresPermissions("affair:affairYouthTalent:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairYouthTalent affairYouthTalent, RedirectAttributes redirectAttributes) {
		affairYouthTalentService.delete(affairYouthTalent);
		addMessage(redirectAttributes, "删除青年人才库成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairYouthTalent/?repage";
	}

	/**
	 * 详情
	 * @param affairYouthTalent
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairYouthTalent:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairYouthTalent affairYouthTalent, Model model) {
		model.addAttribute("affairYouthTalent", affairYouthTalent);
		return "modules/affair/affairYouthTalentFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairYouthTalent:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairYouthTalentService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairYouthTalent affairYouthTalent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairYouthTalent> page = null;
			if(flag == true){
				page = affairYouthTalentService.findPage(new Page<AffairYouthTalent>(request, response), affairYouthTalent);
			}else {
				page = affairYouthTalentService.findPage(new Page<AffairYouthTalent>(request, response,-1), affairYouthTalent);
			}
/*
			Page<AffairYouthTalent> page = affairYouthTalentService.findPage(new Page<AffairYouthTalent>(request, response,-1), affairYouthTalent);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairYouthTalent.class);
			exportExcelNew.setWb(wb);
			List<AffairYouthTalent> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairYouthTalent/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,AffairYouthTalent affairYouthTalent0) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairYouthTalent> list = ei.getDataList(AffairYouthTalent.class);
			//先从数据库查数据

			/*List<AffairYouthTalent> list1= affairYouthTalentDao.findAllList(affairYouthTalent0);*/
			for (AffairYouthTalent affairYouthTalent : list){
				try{

					BeanValidators.validateWithException(validator, affairYouthTalent);
                if (affairYouthTalent.getUnit()!=null&&!"".equals(affairYouthTalent.getUnit())){
	                   affairYouthTalent.setUnitId(officeService.findByName(affairYouthTalent.getUnit()));
					}
					affairYouthTalentService.save(affairYouthTalent);
					successNum++;

				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+affairYouthTalent.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

	/**
	 * 根据身份证号去人员基本信息表中查信息
	 * @param idNumber
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairYouthTalent:edit")
	@RequestMapping(value = "findInfoByIdNumber")
	public Result findInfoByIdNumber(String idNumber) {
		Result result = new Result();
		PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(idNumber);
		result.setSuccess(true);
		result.setResult(personnelBase);
		return result;
	}

	/**
	 *
	 * 推送页面
	 * @return
	 */
	@RequiresPermissions("affair:affairYouthTalent:edit")
	@RequestMapping(value = {"pushForm"})
	public String pushForm(@RequestParam("id") String id,Model model,AffairYouthTalent affairYouthTalent,AffairTwPersonalAward affairTwPersonalAward) {

		affairTwPersonalAward = affairTwPersonalAwardService.get(id);
		affairYouthTalent.setIdNumber(affairTwPersonalAward.getIdNumber());
		affairYouthTalent.setName(affairTwPersonalAward.getName());
		model.addAttribute("affairYouthTalent", affairYouthTalent);
		return "modules/affair/affairYouthTalentPushForm";
	}

	/**
	 * 根据姓名去人员基本信息表中查信息
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairYouthTalent:edit")
	@RequestMapping(value = "findInfoByName")
	public Result findInfoByName(String name) {
		Result result = new Result();
		PersonnelBase personnelBase = personnelBaseService.findInfoByName(name);
		result.setSuccess(true);
		result.setResult(personnelBase);
		return result;
	}
}