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
import com.thinkgem.jeesite.modules.affair.entity.AffairOrgRewardPunish;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairOrgRewardPunishService;
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
 * 组织奖惩信息Controller
 * @author cecil.li
 * @version 2019-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOrgRewardPunish")
public class AffairOrgRewardPunishController extends BaseController {

	@Autowired
	private AffairOrgRewardPunishService affairOrgRewardPunishService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@ModelAttribute
	public AffairOrgRewardPunish get(@RequestParam(required=false) String id) {
		AffairOrgRewardPunish entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOrgRewardPunishService.get(id);
		}
		if (entity == null){
			entity = new AffairOrgRewardPunish();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairOrgRewardPunish:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairOrgRewardPunishIndex";
	}

	@RequiresPermissions("affair:affairOrgRewardPunish:view")
	@RequestMapping(value = {"list"})
	public String list(AffairOrgRewardPunish affairOrgRewardPunish, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairOrgRewardPunish.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		Page<AffairOrgRewardPunish> page = affairOrgRewardPunishService.findPage(new Page<AffairOrgRewardPunish>(request, response), affairOrgRewardPunish);
		model.addAttribute("page", page);
		return "modules/affair/affairOrgRewardPunishList";
	}

	@RequiresPermissions("affair:affairOrgRewardPunish:view")
	@RequestMapping(value = "form")
	public String form(AffairOrgRewardPunish affairOrgRewardPunish, Model model) {
		model.addAttribute("affairOrgRewardPunish", affairOrgRewardPunish);
		return "modules/affair/affairOrgRewardPunishForm";
	}

	@RequiresPermissions("affair:affairOrgRewardPunish:edit")
	@RequestMapping(value = "save")
	public String save(AffairOrgRewardPunish affairOrgRewardPunish, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairOrgRewardPunish)){
			return form(affairOrgRewardPunish, model);
		}
		affairOrgRewardPunishService.save(affairOrgRewardPunish);
		addMessage(redirectAttributes, "保存组织奖惩信息成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairOrgRewardPunishForm";
	}
	
	@RequiresPermissions("affair:affairOrgRewardPunish:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOrgRewardPunish affairOrgRewardPunish, RedirectAttributes redirectAttributes) {
		affairOrgRewardPunishService.delete(affairOrgRewardPunish);
		addMessage(redirectAttributes, "删除组织奖惩信息成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairOrgRewardPunish/list?repage&treeId="+affairOrgRewardPunish.getTreeId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairOrgRewardPunish:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairOrgRewardPunishService.deleteByIds(ids);
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
	 * @param affairOrgRewardPunish
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("affair:affairOrgRewardPunish:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairOrgRewardPunish affairOrgRewardPunish, Model model) {
		model.addAttribute("affairOrgRewardPunish", affairOrgRewardPunish);
		return "modules/affair/affairOrgRewardPunishFormDetail";
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
	public String exportExcelByTemplate(AffairOrgRewardPunish affairOrgRewardPunish, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairOrgRewardPunish> page = null;
			if(flag == true){
				page = affairOrgRewardPunishService.findPage(new Page<AffairOrgRewardPunish>(request, response), affairOrgRewardPunish);
			}else{
				page = affairOrgRewardPunishService.findPage(new Page<AffairOrgRewardPunish>(request, response,-1), affairOrgRewardPunish);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairOrgRewardPunish.class);
			exportExcelNew.setWb(wb);
			List<AffairOrgRewardPunish> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairOrgRewardPunish/list?repage&treeId="+affairOrgRewardPunish.getTreeId();
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
			List<AffairOrgRewardPunish> list = ei.getDataList(AffairOrgRewardPunish.class);
			for (AffairOrgRewardPunish affairOrgRewardPunish : list){
				try{
					//根据奖惩文号去重
					if(StringUtils.isNotBlank(affairOrgRewardPunish.getFileNo())){
						List<String> oldList = affairOrgRewardPunishService.findListByFileNo(affairOrgRewardPunish.getFileNo());
						if(oldList != null && oldList.size() >0){
							affairOrgRewardPunishService.deleteByFileNo(affairOrgRewardPunish.getFileNo());
						}
						//党支部要绑定党支部id
						String partyOrganizationId = affairGeneralSituationService.findByName(affairOrgRewardPunish.getPartyOrganization());
						affairOrgRewardPunish.setPartyOrganizationId(partyOrganizationId);
						affairOrgRewardPunish.setTypeFlag("1");
						BeanValidators.validateWithException(validator, affairOrgRewardPunish);
						affairOrgRewardPunishService.save(affairOrgRewardPunish);
						successNum++;
					}
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairOrgRewardPunish.getPartyOrganization()+"(奖惩标题:"+affairOrgRewardPunish.getTitle()+")"+" 导入失败："+ex.getMessage());
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