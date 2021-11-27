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
import com.thinkgem.jeesite.modules.affair.entity.AffairAssess;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkBuild;
import com.thinkgem.jeesite.modules.affair.service.AffairClassMemberService;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
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
 * 班子成员Controller
 * @author eav.liu
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairClassMember")
public class AffairClassMemberController extends BaseController {

	@Autowired
	private AffairClassMemberService affairClassMemberService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairClassMember get(@RequestParam(required=false) String id) {
		AffairClassMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairClassMemberService.get(id);
		}
		if (entity == null){
			entity = new AffairClassMember();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairClassMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairClassMember affairClassMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairClassMember.getTreeId());
		Page<AffairClassMember> page = affairClassMemberService.findPage(new Page<AffairClassMember>(request, response), affairClassMember); 
		model.addAttribute("page", page);
		return "modules/affair/affairClassMemberList";
	}

	@RequiresPermissions("affair:affairClassMember:view")
	@RequestMapping(value = "form")
	public String form(AffairClassMember affairClassMember, Model model) {
		model.addAttribute("affairClassMember", affairClassMember);
		return "modules/affair/affairClassMemberForm";
	}

	@RequiresPermissions("affair:affairClassMember:edit")
	@RequestMapping(value = "save")
	public String save(AffairClassMember affairClassMember, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairClassMember)){
			return form(affairClassMember, model);
		}

		String status = affairClassMember.getStatus();
		if (StringUtils.isBlank(status)){
			affairClassMember.setStatus("1");
		}

		affairClassMemberService.save(affairClassMember);
		addMessage(redirectAttributes, "保存班子成员成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairClassMemberForm";
	}
	
	@RequiresPermissions("affair:affairClassMember:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairClassMember affairClassMember, RedirectAttributes redirectAttributes) {
		affairClassMemberService.delete(affairClassMember);
		addMessage(redirectAttributes, "删除班子成员成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassMember/list?repage&treeId="+affairClassMember.getTreeId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairClassMember:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairClassMemberService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * form表单详情
	 * @param affairClassMember
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairClassMember:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairClassMember affairClassMember, Model model) {
		model.addAttribute("affairClassMember", affairClassMember);
		if (affairClassMember.getFilePath() != null && affairClassMember.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairClassMember.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairClassMemberFormDetail";
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
	public String exportExcelByTemplate(AffairClassMember affairClassMember, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairClassMember> page = null;
			if(flag == true){
				page = affairClassMemberService.findPage(new Page<AffairClassMember>(request, response,-1), affairClassMember);
			}else{
				page = affairClassMemberService.findPage(new Page<AffairClassMember>(request, response,-1), affairClassMember);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairClassMember.class);
			exportExcelNew.setWb(wb);
			List<AffairClassMember> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairClassMember?repage&treeId="+affairClassMember.getTreeId();
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
			List<AffairClassMember> list = ei.getDataList(AffairClassMember.class);
			for (AffairClassMember affairClassMember : list){
				try{
					//根据党组织名称去重(目前党组织名称为全称，只根据党组织名称去重即可)
					if(StringUtils.isNotBlank(affairClassMember.getPartyOrganization())){
						List<String> oldList = affairClassMemberService.findListByPartyOrganization(affairClassMember.getPartyOrganization());
						if(oldList !=null && oldList.size() >0){
							affairClassMemberService.deleteByPartyOrganization(affairClassMember.getPartyOrganization());
						}
						//绑定党支部id
						String partyBranchId = affairGeneralSituationService.findByName(affairClassMember.getPartyOrganization());
						affairClassMember.setPartyOrganizationId(partyBranchId);
						BeanValidators.validateWithException(validator, affairClassMember);
						affairClassMemberService.save(affairClassMember);
						successNum++;
					}
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("党组织名称:"+affairClassMember.getPartyOrganization()+""+" 导入失败："+ex.getMessage());
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

	/*审核*/
	@RequiresPermissions("affair:affairWorkBuild:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairClassMember affairClassMember, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairClassMember)){
			return form(affairClassMember, model);
		}
		affairClassMemberService.save(affairClassMember);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairClassMemberForm";
	}

	@RequiresPermissions("affair:affairClassMember:edit")
	@RequestMapping(value = "report")
	public String report(AffairClassMember affairClassMember, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairClassMember)){
			return form(affairClassMember, model);
		}
		affairClassMember.setStatus("2");
		affairClassMemberService.save(affairClassMember);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassMember/list?repage&treeId="+affairClassMember.getTreeId();
	}

	@RequiresPermissions("affair:affairClassMember:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairClassMemberCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairClassMember:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairClassMember affairClassMember) {
		affairClassMemberService.shenHeSave(affairClassMember);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}