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
import com.thinkgem.jeesite.modules.affair.dao.AffairWentiTalentDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalAward;
import com.thinkgem.jeesite.modules.affair.entity.AffairWentiTalent;
import com.thinkgem.jeesite.modules.affair.service.AffairPersonalAwardService;
import com.thinkgem.jeesite.modules.affair.service.AffairWentiTalentService;
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
 * 文体人才库Controller
 * @author cecil.lia'f
 * @version 2019-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWentiTalent")
public class AffairWentiTalentController extends BaseController {

	@Autowired
	private AffairPersonalAwardService affairPersonalAwardService;
	@Autowired
	private AffairWentiTalentService affairWentiTalentService;
	@Autowired
	private AffairWentiTalentDao affairWentiTalentDao;
	@Autowired
	private OfficeService officeService;
	@ModelAttribute
	public AffairWentiTalent get(@RequestParam(required=false) String id) {
		AffairWentiTalent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWentiTalentService.get(id);
		}
		if (entity == null){
			entity = new AffairWentiTalent();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWentiTalent:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWentiTalent affairWentiTalent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairWentiTalent> page = affairWentiTalentService.findPage(new Page<AffairWentiTalent>(request, response), affairWentiTalent); 
		model.addAttribute("page", page);
		return "modules/affair/affairWentiTalentList";
	}

	@RequiresPermissions("affair:affairWentiTalent:view")
	@RequestMapping(value = "form")
	public String form(AffairWentiTalent affairWentiTalent, Model model) {
		model.addAttribute("affairWentiTalent", affairWentiTalent);
		return "modules/affair/affairWentiTalentForm";
	}

	@RequiresPermissions("affair:affairWentiTalent:edit")
	@RequestMapping(value = "save")
	public String save(AffairWentiTalent affairWentiTalent, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairWentiTalent)){
			return form(affairWentiTalent, model);
		}
		affairWentiTalentService.save(affairWentiTalent);
		addMessage(redirectAttributes, "保存文体人才库成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairWentiTalentForm";
	}

	/**
	 *
	 * 推送页面
	 * @return
	 */
	@RequiresPermissions("affair:affairWentiTalent:edit")
	@RequestMapping(value = {"pushForm"})
	public String pushForm(@RequestParam("id") String id, Model model, AffairWentiTalent affairWentiTalent, AffairPersonalAward affairPersonalAward) {

		affairPersonalAward = affairPersonalAwardService.get(id);
		affairWentiTalent.setIdNumber(affairPersonalAward.getIdNumber());
		affairWentiTalent.setName(affairPersonalAward.getPoliceName());
		model.addAttribute("affairWentiTalent", affairWentiTalent);
		return "modules/affair/affairWentiTalentPushForm";
	}
	
	@RequiresPermissions("affair:affairWentiTalent:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWentiTalent affairWentiTalent, RedirectAttributes redirectAttributes) {
		affairWentiTalentService.delete(affairWentiTalent);
		addMessage(redirectAttributes, "删除文体人才库成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWentiTalent/?repage";
	}
	/**
	 * 详情
	 * @param affairWentiTalent
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWentiTalent:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWentiTalent affairWentiTalent, Model model) {
		model.addAttribute("affairWentiTalent", affairWentiTalent);
		return "modules/affair/affairWentiTalentFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWentiTalent:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWentiTalentService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairWentiTalent affairWentiTalent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairWentiTalent> page = null;
			if(flag == true){
				page = affairWentiTalentService.findPage(new Page<AffairWentiTalent>(request, response), affairWentiTalent);
			}else {
				page = affairWentiTalentService.findPage(new Page<AffairWentiTalent>(request, response,-1), affairWentiTalent);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairWentiTalent.class);
			exportExcelNew.setWb(wb);
			List<AffairWentiTalent> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairWentiTalent/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairWentiTalent> list = ei.getDataList(AffairWentiTalent.class);
			for (AffairWentiTalent affairWentiTalent : list){
				try{
					BeanValidators.validateWithException(validator, affairWentiTalent);
					if (affairWentiTalent.getUnit()!=null&&!"".equals(affairWentiTalent.getUnit())){
						affairWentiTalent.setUnitId(officeService.findByName(affairWentiTalent.getUnit()));
					}
					affairWentiTalentService.save(affairWentiTalent);
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