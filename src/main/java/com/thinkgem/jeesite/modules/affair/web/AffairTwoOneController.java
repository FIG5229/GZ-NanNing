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
import com.thinkgem.jeesite.modules.affair.entity.AffairTwoOne;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwoOneService;
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
import java.util.Map;

/**
 * &quot;两学一做&quot;专题学习Controller
 * @author mason.xv
 * @version 2019-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTwoOne")
public class AffairTwoOneController extends BaseController {

	@Autowired
	private AffairTwoOneService affairTwoOneService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@ModelAttribute
	public AffairTwoOne get(@RequestParam(required=false) String id) {
		AffairTwoOne entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTwoOneService.get(id);
		}
		if (entity == null){
			entity = new AffairTwoOne();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTwoOne:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTwoOne affairTwoOne, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairTwoOne.getTreeId());
		Page<AffairTwoOne> page = affairTwoOneService.findPage(new Page<AffairTwoOne>(request, response), affairTwoOne); 
		model.addAttribute("page", page);
		return "modules/affair/affairTwoOneList";
	}

	@RequiresPermissions("affair:affairTwoOne:view")
	@RequestMapping(value = "form")
	public String form(AffairTwoOne affairTwoOne, Model model) {
		model.addAttribute("affairTwoOne", affairTwoOne);
		return "modules/affair/affairTwoOneForm";
	}

	@RequiresPermissions("affair:affairTwoOne:edit")
	@RequestMapping(value = "save")
	public String save(AffairTwoOne affairTwoOne, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairTwoOne)){
			return form(affairTwoOne, model);
		}
		/*审核状态为空时 默认设置为 未上报*/
		if (StringUtils.isBlank(affairTwoOne.getExamineStatus())){
			affairTwoOne.setExamineStatus("1");
		}
		affairTwoOneService.save(affairTwoOne);
		addMessage(redirectAttributes, "保存&quot;两学一做&quot;专题学习成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairTwoOneForm";
	}
	
	@RequiresPermissions("affair:affairTwoOne:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTwoOne affairTwoOne, RedirectAttributes redirectAttributes) {
		affairTwoOneService.delete(affairTwoOne);
		addMessage(redirectAttributes, "删除&quot;两学一做&quot;专题学习成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTwoOne/list?repage&treeId="+affairTwoOne.getTreeId();
	}

	/**
	 * 详情
	 * @param affairTwoOne
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTwoOne:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTwoOne affairTwoOne, Model model) {
		model.addAttribute("affairTwoOne", affairTwoOne);
		if (affairTwoOne.getFilePath() != null && affairTwoOne.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTwoOne.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairTwoOneFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTwoOne:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTwoOneService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTwoOne affairTwoOne, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairTwoOne> page = null;
			if(flag == true){
				page = affairTwoOneService.findPage(new Page<AffairTwoOne>(request, response), affairTwoOne);
			}else{
				page = affairTwoOneService.findPage(new Page<AffairTwoOne>(request, response,-1), affairTwoOne);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTwoOne.class);
			exportExcelNew.setWb(wb);
			List<AffairTwoOne> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTwoOne?repage&treeId="+affairTwoOne.getTreeId();
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
			List<AffairTwoOne> list = ei.getDataList(AffairTwoOne.class);
			for (AffairTwoOne affairTwoOne : list){
				try{

					BeanValidators.validateWithException(validator, affairTwoOne);
					//党支部要绑定党支部id
					String partyOrganizationId = affairGeneralSituationService.findByName(affairTwoOne.getPartyOrganization());
					affairTwoOne.setPartyOrganizationId(partyOrganizationId);
					affairTwoOneService.save(affairTwoOne);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairTwoOne.getPartyOrganization()+"(专题名称:"+affairTwoOne.getName()+")"+" 导入失败："+ex.getMessage());
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
	 * 审核页面
	 * @return
	 */
	@RequestMapping("examineView")
	public String examineView(){
		return "modules/affair/affairTwoOneExamineDialog";
	}

	/*审核*/
	@RequiresPermissions("affair:affairTwoOne:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairTwoOne affairTwoOne, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairTwoOne)){
			return form(affairTwoOne, model);
		}
		affairTwoOneService.save(affairTwoOne);
		addMessage(redirectAttributes, "审核两学一做专题成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairTwoOneForm";
	}

	@RequiresPermissions("affair:affairTwoOne:edit")
	@RequestMapping(value = "report")
	public String report(AffairTwoOne affairMonthStudy, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairMonthStudy)){
			return form(affairMonthStudy, model);
		}
		affairMonthStudy.setExamineStatus("2");
		affairTwoOneService.save(affairMonthStudy);
		addMessage(redirectAttributes, "上报两学一做专题成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTwoOne/list?repage&treeId="+affairMonthStudy.getTreeId();
	}
}