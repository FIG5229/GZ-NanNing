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
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyRewardPunish;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyMemberService;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyRewardPunishService;
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
 * 党员奖惩信息Controller
 * @author eav.liu
 * @version 2019-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPartyRewardPunish")
public class AffairPartyRewardPunishController extends BaseController {

	@Autowired
	private AffairPartyRewardPunishService affairPartyRewardPunishService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@Autowired
	private AffairPartyMemberService affairPartyMemberService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairPartyRewardPunish get(@RequestParam(required=false) String id) {
		AffairPartyRewardPunish entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPartyRewardPunishService.get(id);
		}
		if (entity == null){
			entity = new AffairPartyRewardPunish();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPartyRewardPunish:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPartyRewardPunish affairPartyRewardPunish, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairPartyRewardPunish.getTreeId());
		model.addAttribute("pmId", affairPartyRewardPunish.getIdNumber());
		Page<AffairPartyRewardPunish> page = affairPartyRewardPunishService.findPage(new Page<AffairPartyRewardPunish>(request, response), affairPartyRewardPunish);
		model.addAttribute("page", page);
		return "modules/affair/affairPartyRewardPunishList";
	}

	@RequiresPermissions("affair:affairPartyRewardPunish:view")
	@RequestMapping(value = "form")
	public String form(AffairPartyRewardPunish affairPartyRewardPunish, Model model) {
		if (!StringUtils.isEmpty(affairPartyRewardPunish.getIdNumber())){
			AffairPartyMember partyMember=new AffairPartyMember();
			partyMember.setCardNum(affairPartyRewardPunish.getIdNumber());
			/*使用身份证号 通过党员名册获取相关数据*/
			partyMember=affairPartyMemberService.byIdNumber(partyMember);
			if(partyMember!=null){
				affairPartyRewardPunish.setName(partyMember.getName());
				affairPartyRewardPunish.setSex(partyMember.getSex());
				affairPartyRewardPunish.setIdNumber(partyMember.getCardNum());
				affairPartyRewardPunish.setPartyOrganization(partyMember.getPartyBranch());
				affairPartyRewardPunish.setPartyOrganizationId(partyMember.getPartyBranchId());
			}
		}
		model.addAttribute("affairPartyRewardPunish", affairPartyRewardPunish);
		return "modules/affair/affairPartyRewardPunishForm";
	}

	@RequiresPermissions("affair:affairPartyRewardPunish:edit")
	@RequestMapping(value = "save")
	public String save(AffairPartyRewardPunish affairPartyRewardPunish, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPartyRewardPunish)){
			return form(affairPartyRewardPunish, model);
		}
		affairPartyRewardPunishService.save(affairPartyRewardPunish);
		addMessage(redirectAttributes, "保存党员奖惩信息成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairPartyRewardPunishForm";
	}
	
	@RequiresPermissions("affair:affairPartyRewardPunish:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPartyRewardPunish affairPartyRewardPunish, RedirectAttributes redirectAttributes) {
		affairPartyRewardPunishService.delete(affairPartyRewardPunish);
		addMessage(redirectAttributes, "删除党员奖惩信息成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyRewardPunish/list?repage&treeId="+affairPartyRewardPunish.getTreeId()+"&idNumber="+affairPartyRewardPunish.getIdNumber();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPartyRewardPunish:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPartyRewardPunishService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 * @param affairPartyRewardPunish
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("affair:affairPartyRewardPunish:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPartyRewardPunish affairPartyRewardPunish, Model model) {
		model.addAttribute("affairPartyRewardPunish", affairPartyRewardPunish);
		return "modules/affair/affairPartyRewardPunishFormDetail";
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
	public String exportExcelByTemplate(AffairPartyRewardPunish affairPartyRewardPunish, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairPartyRewardPunish> page = null;
			if(flag == true){
				page = affairPartyRewardPunishService.findPage(new Page<AffairPartyRewardPunish>(request, response), affairPartyRewardPunish);
			}else{
				page = affairPartyRewardPunishService.findPage(new Page<AffairPartyRewardPunish>(request, response,-1), affairPartyRewardPunish);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPartyRewardPunish.class);
			exportExcelNew.setWb(wb);
			List<AffairPartyRewardPunish> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairPartyRewardPunish/list?repage&treeId="+affairPartyRewardPunish.getTreeId()+"&idNumber="+affairPartyRewardPunish.getIdNumber();
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
			List<AffairPartyRewardPunish> list = ei.getDataList(AffairPartyRewardPunish.class);
			for (AffairPartyRewardPunish affairPartyRewardPunish : list){
				try{
					BeanValidators.validateWithException(validator, affairPartyRewardPunish);
					//绑定批准党委id
					//String approvalOrgId = affairGeneralSituationService.findByName(affairPartyRewardPunish.getApprovalOrg());
					//affairPartyRewardPunish.setApprovalOrgId(approvalOrgId);
					//党组织要绑定党组织id
					String partyOrganizationId = affairGeneralSituationService.findByName(affairPartyRewardPunish.getPartyOrganization());
					affairPartyRewardPunish.setPartyOrganizationId(partyOrganizationId);
					affairPartyRewardPunish.setTypeFlag("2");
					affairPartyRewardPunishService.save(affairPartyRewardPunish);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairPartyRewardPunish.getName()+"(身份证号码:"+affairPartyRewardPunish.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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