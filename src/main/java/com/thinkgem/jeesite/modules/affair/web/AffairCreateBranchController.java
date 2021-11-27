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
import com.thinkgem.jeesite.modules.affair.entity.AffairCreateBranch;
import com.thinkgem.jeesite.modules.affair.service.AffairCreateBranchService;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
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
import java.util.Map;

/**
 * 党内创品牌活动Controller
 * @author eav.liu
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCreateBranch")
public class AffairCreateBranchController extends BaseController {

	@Autowired
	private AffairCreateBranchService affairCreateBranchService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;
	
	@ModelAttribute
	public AffairCreateBranch get(@RequestParam(required=false) String id) {
		AffairCreateBranch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCreateBranchService.get(id);
		}
		if (entity == null){
			entity = new AffairCreateBranch();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairCreateBranch:view")
	@RequestMapping(value = {""})
	public String index(AffairCreateBranch affairCreateBranch) {
		return "modules/affair/affairCreateBranchIndex";
	}
	
	@RequiresPermissions("affair:affairCreateBranch:view")
	@RequestMapping(value = {"list"})
	public String list(AffairCreateBranch affairCreateBranch, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairCreateBranch.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		Page<AffairCreateBranch> page = affairCreateBranchService.findPage(new Page<AffairCreateBranch>(request, response), affairCreateBranch);
		model.addAttribute("page", page);
		return "modules/affair/affairCreateBranchList";
	}

	@RequiresPermissions("affair:affairCreateBranch:view")
	@RequestMapping(value = "form")
	public String form(AffairCreateBranch affairCreateBranch, Model model) {
		model.addAttribute("affairCreateBranch", affairCreateBranch);
		return "modules/affair/affairCreateBranchForm";
	}

	@RequiresPermissions("affair:affairCreateBranch:edit")
	@RequestMapping(value = "save")
	public String save(AffairCreateBranch affairCreateBranch, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairCreateBranch)){
			return form(affairCreateBranch, model);
		}
		/*审核状态为空时 默认设置为 未上报*/
		if (StringUtils.isBlank(affairCreateBranch.getExamineStatus())){
			affairCreateBranch.setExamineStatus("1");
		}
		affairCreateBranchService.save(affairCreateBranch);
		addMessage(redirectAttributes, "保存党内创品牌活动成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairCreateBranchForm";
	}
	
	@RequiresPermissions("affair:affairCreateBranch:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCreateBranch affairCreateBranch, RedirectAttributes redirectAttributes) {
		affairCreateBranchService.delete(affairCreateBranch);
		addMessage(redirectAttributes, "删除党内创品牌活动成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCreateBranch/list?repage&treeId="+affairCreateBranch.getTreeId();
	}

	/**
	 * 详情
	 * @param affairCreateBranch
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairCreateBranch:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCreateBranch affairCreateBranch, Model model) {
		model.addAttribute("affairCreateBranch", affairCreateBranch);
		if(affairCreateBranch.getMaterialPath() != null && affairCreateBranch.getMaterialPath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairCreateBranch.getMaterialPath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairCreateBranchFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairCreateBranch:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCreateBranchService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairCreateBranch affairCreateBranch, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairCreateBranch> page = null;
			if(flag == true){
				page = affairCreateBranchService.findPage(new Page<AffairCreateBranch>(request, response,-1), affairCreateBranch);
			}else{
				page = affairCreateBranchService.findPage(new Page<AffairCreateBranch>(request, response,-1), affairCreateBranch);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairCreateBranch.class);
			exportExcelNew.setWb(wb);
			List<AffairCreateBranch> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairCreateBranch/list?repage&treeId="+affairCreateBranch.getTreeId();
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
			List<AffairCreateBranch> list = ei.getDataList(AffairCreateBranch.class);
			for (AffairCreateBranch affairCreateBranch : list){
				try{
					BeanValidators.validateWithException(validator, affairCreateBranch);
					//党支部要绑定党支部id
					String partyOrganizationId = affairGeneralSituationService.findByName(affairCreateBranch.getPartyBranch().trim());
					affairCreateBranch.setPartyBranchId(partyOrganizationId);
					//单位绑定举办单位id
					String orgId = officeService.findByName(affairCreateBranch.getHoldUnit());
					affairCreateBranch.setHoldUnitId(orgId);
					affairCreateBranchService.save(affairCreateBranch);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairCreateBranch.getHoldUnit()+"(活动名称:"+affairCreateBranch.getName()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_affairCreateBranch";
	}

	/**
	 * 审核页面
	 * @return
	 */
	@RequestMapping("examineView")
	public String examineView(){
		return "modules/affair/affairCreateBranchExamineDialog";
	}

	/*审核*/
	@RequiresPermissions("affair:affairCreateBranch:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairCreateBranch affairCreateBranch, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairCreateBranch)){
			return form(affairCreateBranch, model);
		}
		affairCreateBranchService.save(affairCreateBranch);
		addMessage(redirectAttributes, "审核品牌创建成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairCreateBranchForm";
	}

	@RequiresPermissions("affair:affairCreateBranch:edit")
	@RequestMapping(value = "report")
	public String report(AffairCreateBranch affairCreateBranch, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairCreateBranch)){
			return form(affairCreateBranch, model);
		}
		affairCreateBranch.setExamineStatus("2");
		affairCreateBranchService.save(affairCreateBranch);
		addMessage(redirectAttributes, "上报品牌创建成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCreateBranch/list?repage&treeId="+affairCreateBranch.getTreeId();
	}
}