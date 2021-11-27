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
import com.thinkgem.jeesite.modules.affair.entity.AffairBelongTo;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.service.AffairBelongToService;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyMemberService;
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
 * 党员组织隶属Controller
 * @author eav.liu
 * @version 2019-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairBelongTo")
public class AffairBelongToController extends BaseController {

	@Autowired
	private AffairBelongToService affairBelongToService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@Autowired
	AffairPartyMemberService affairPartyMemberService;
	
	@ModelAttribute
	public AffairBelongTo get(@RequestParam(required=false) String id) {
		AffairBelongTo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairBelongToService.get(id);
		}
		if (entity == null){
			entity = new AffairBelongTo();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairBelongTo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairBelongTo affairBelongTo, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairBelongTo.getTreeId());
		model.addAttribute("pmId", affairBelongTo.getIdNumber());
		Page<AffairBelongTo> page = affairBelongToService.findPage(new Page<AffairBelongTo>(request, response), affairBelongTo); 
		model.addAttribute("page", page);
		return "modules/affair/affairBelongToList";
	}

	@RequiresPermissions("affair:affairBelongTo:view")
	@RequestMapping(value = "form")
	public String form(AffairBelongTo affairBelongTo, Model model) {
		if (!StringUtils.isEmpty(affairBelongTo.getIdNumber())){
			AffairPartyMember partyMember=new AffairPartyMember();
			partyMember.setCardNum(affairBelongTo.getIdNumber());
			/*使用身份证号 通过党员名册获取相关数据*/
			partyMember=affairPartyMemberService.byIdNumber(partyMember);
			affairBelongTo.setName(partyMember.getName());
			affairBelongTo.setSex(partyMember.getSex());
			affairBelongTo.setIdNumber(partyMember.getCardNum());
			affairBelongTo.setPartyBranch(partyMember.getPartyBranch());
			affairBelongTo.setPartyBranchId(partyMember.getPartyBranchId());
		}
		model.addAttribute("affairBelongTo", affairBelongTo);
		return "modules/affair/affairBelongToForm";
	}

	@RequiresPermissions("affair:affairBelongTo:edit")
	@RequestMapping(value = "save")
	public String save(AffairBelongTo affairBelongTo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairBelongTo)){
			return form(affairBelongTo, model);
		}
		affairBelongToService.save(affairBelongTo);
		addMessage(redirectAttributes, "保存党员组织隶属成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairBelongToForm";
	}
	
	@RequiresPermissions("affair:affairBelongTo:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairBelongTo affairBelongTo, RedirectAttributes redirectAttributes) {
		affairBelongToService.delete(affairBelongTo);
		addMessage(redirectAttributes, "删除党员组织隶属成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairBelongTo/?repage&treeId="+affairBelongTo.getTreeId()+"&idNumber="+affairBelongTo.getIdNumber();
	}

	/**
	 * 详情
	 * @param affairBelongTo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairBelongTo:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairBelongTo affairBelongTo, Model model) {
		model.addAttribute("affairBelongTo", affairBelongTo);
		return "modules/affair/affairBelongToFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairBelongTo:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairBelongToService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairBelongTo affairBelongTo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairBelongTo> page = null;
			if(flag == true){
				page = affairBelongToService.findPage(new Page<AffairBelongTo>(request, response,-1), affairBelongTo);
			}else{
				page = affairBelongToService.findPage(new Page<AffairBelongTo>(request, response,-1), affairBelongTo);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairBelongTo.class);
			exportExcelNew.setWb(wb);
			List<AffairBelongTo> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairBelongTo?repage&treeId="+affairBelongTo.getTreeId()+"&idNumber="+affairBelongTo.getIdNumber();
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
			List<AffairBelongTo> list = ei.getDataList(AffairBelongTo.class);
			for (AffairBelongTo affairBelongTo : list){
				try{
					//绑定党支部id
					String partyBranchId = affairGeneralSituationService.findByName(affairBelongTo.getPartyBranch());
					affairBelongTo.setPartyBranchId(partyBranchId);
					BeanValidators.validateWithException(validator, affairBelongTo);
					affairBelongToService.save(affairBelongTo);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairBelongTo.getName()+"(身份证号码:"+affairBelongTo.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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