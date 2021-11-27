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
import com.thinkgem.jeesite.modules.affair.entity.AffairDevelopObject;
import com.thinkgem.jeesite.modules.affair.entity.AffairProbationaryMember;
import com.thinkgem.jeesite.modules.affair.service.AffairDevelopObjectService;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairProbationaryMemberService;
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
 * 发展党员Controller
 * @author eav.liu
 * @version 2019-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDevelopObject")
public class AffairDevelopObjectController extends BaseController {

	@Autowired
	private AffairDevelopObjectService affairDevelopObjectService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@Autowired
	private AffairProbationaryMemberService affairProbationaryMemberService;
	
	@ModelAttribute
	public AffairDevelopObject get(@RequestParam(required=false) String id) {
		AffairDevelopObject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDevelopObjectService.get(id);
		}
		if (entity == null){
			entity = new AffairDevelopObject();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDevelopObject:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDevelopObject affairDevelopObject, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairDevelopObject.getTreeId());
		Page<AffairDevelopObject> page = affairDevelopObjectService.findPage(new Page<AffairDevelopObject>(request, response), affairDevelopObject); 
		model.addAttribute("page", page);
		return "modules/affair/affairDevelopObjectList";
	}

	@RequiresPermissions("affair:affairDevelopObject:view")
	@RequestMapping(value = "form")
	public String form(AffairDevelopObject affairDevelopObject, Model model) {
		model.addAttribute("affairDevelopObject", affairDevelopObject);
		return "modules/affair/affairDevelopObjectForm";
	}

	@RequiresPermissions("affair:affairDevelopObject:edit")
	@RequestMapping(value = "save")
	public String save(AffairDevelopObject affairDevelopObject, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDevelopObject)){
			return form(affairDevelopObject, model);
		}
		affairDevelopObjectService.save(affairDevelopObject);
		addMessage(redirectAttributes, "保存发展对象成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairDevelopObjectForm";
	}
	
	@RequiresPermissions("affair:affairDevelopObject:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDevelopObject affairDevelopObject, RedirectAttributes redirectAttributes) {
		affairDevelopObjectService.delete(affairDevelopObject);
		addMessage(redirectAttributes, "删除发展对象成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDevelopObject/?repage&treeId="+affairDevelopObject.getTreeId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairDevelopObject:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDevelopObjectService.deleteByIds(ids);
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
	 * @param affairDevelopObject
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairDevelopObject:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairDevelopObject affairDevelopObject, Model model) {
		model.addAttribute("affairDevelopObject", affairDevelopObject);
		return "modules/affair/affairDevelopObjectFormDetail";
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
	public String exportExcelByTemplate(AffairDevelopObject affairDevelopObject, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairDevelopObject> page = null;
			if(flag == true){
				page = affairDevelopObjectService.findPage(new Page<AffairDevelopObject>(request, response), affairDevelopObject);
			}else{
				page = affairDevelopObjectService.findPage(new Page<AffairDevelopObject>(request, response,-1), affairDevelopObject);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairDevelopObject.class);
			exportExcelNew.setWb(wb);
			List<AffairDevelopObject> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairDevelopObject?repage&treeId="+affairDevelopObject.getTreeId();
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairDevelopObject> list = ei.getDataList(AffairDevelopObject.class);
			for (AffairDevelopObject affairDevelopObject : list){
				try{
					//根据身份证号去重
					if(StringUtils.isNotBlank(affairDevelopObject.getIdNumber())){
						List<String> oldList = affairDevelopObjectService.findListByIdNo(affairDevelopObject.getIdNumber());
						if(oldList != null && oldList.size() >0){
							affairDevelopObjectService.deleteByIdNo(affairDevelopObject.getIdNumber());
						}
						//党支部要绑定党支部id
						String partyBranchId = affairGeneralSituationService.findByName(affairDevelopObject.getPartyBranch());
						affairDevelopObject.setPartyBranchId(partyBranchId);
						BeanValidators.validateWithException(validator, affairDevelopObject);
						affairDevelopObjectService.save(affairDevelopObject);
						successNum++;
					}

				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairDevelopObject.getName()+"(身份证号码:"+affairDevelopObject.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping("partyDevelopDetail")
	public String partyDevelopDetail(AffairDevelopObject affairDevelopObject,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairDevelopObject> page = affairDevelopObjectService.findDevelopPage(new Page<>(request,response),affairDevelopObject);
		model.addAttribute("page",page);
		return "modules/charts/chartDevelopObjectList";
	}

	@RequestMapping(value = "push")
	public String push(AffairDevelopObject affairDevelopObject, RedirectAttributes redirectAttributes) {
		AffairProbationaryMember affairProbationaryMember = new AffairProbationaryMember();
		affairProbationaryMember.setName(affairDevelopObject.getName());
		affairProbationaryMember.setPoliceNo(affairDevelopObject.getPoliceNo());
		affairProbationaryMember.setIdNumber(affairDevelopObject.getIdNumber());
		affairProbationaryMember.setSex(affairDevelopObject.getSex());
		affairProbationaryMember.setUnit(affairDevelopObject.getPartyBranch());
		affairProbationaryMember.setUnitId(affairDevelopObject.getPartyBranchId());
		affairProbationaryMember.setStatus("0");
		affairProbationaryMemberService.save(affairProbationaryMember);
		affairDevelopObject.setStatus("1");
		affairDevelopObjectService.save(affairDevelopObject);
		addMessage(redirectAttributes, "推送至预备党员成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDevelopObject/?repage&treeId="+affairDevelopObject.getTreeId();
	}
}