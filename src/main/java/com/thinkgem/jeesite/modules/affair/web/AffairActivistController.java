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
import com.thinkgem.jeesite.modules.affair.entity.AffairActivist;
import com.thinkgem.jeesite.modules.affair.entity.AffairDevelopObject;
import com.thinkgem.jeesite.modules.affair.service.AffairActivistService;
import com.thinkgem.jeesite.modules.affair.service.AffairDevelopObjectService;
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

/**
 * 入党积极分子Controller
 * @author eav.liu
 * @version 2019-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairActivist")
public class AffairActivistController extends BaseController {

	@Autowired
	private AffairActivistService affairActivistService;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;

	@Autowired
	private AffairDevelopObjectService affairDevelopObjectService;
	
	@ModelAttribute
	public AffairActivist get(@RequestParam(required=false) String id) {
		AffairActivist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairActivistService.get(id);
		}
		if (entity == null){
			entity = new AffairActivist();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairActivist:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairActivist affairActivist, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairActivist.getTreeId());
		Page<AffairActivist> page = affairActivistService.findPage(new Page<AffairActivist>(request, response), affairActivist); 
		model.addAttribute("page", page);
		return "modules/affair/affairActivistList";
	}

	@RequiresPermissions("affair:affairActivist:view")
	@RequestMapping(value = "form")
	public String form(AffairActivist affairActivist, Model model) {
		model.addAttribute("affairActivist", affairActivist);
		return "modules/affair/affairActivistForm";
	}

	@RequiresPermissions("affair:affairActivist:edit")
	@RequestMapping(value = "save")
	public String save(AffairActivist affairActivist, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairActivist)){
			return form(affairActivist, model);
		}
		affairActivistService.save(affairActivist);
		addMessage(redirectAttributes, "保存入党积极分子成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairActivistForm";
	}
	
	@RequiresPermissions("affair:affairActivist:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairActivist affairActivist, RedirectAttributes redirectAttributes) {
		affairActivistService.delete(affairActivist);
		addMessage(redirectAttributes, "删除入党积极分子成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairActivist/?repage&treeId="+affairActivist.getTreeId();
	}

	@RequestMapping(value = "push")
	public String push(AffairActivist affairActivist, RedirectAttributes redirectAttributes) {
		AffairDevelopObject affairDevelopObject = new AffairDevelopObject();
		affairDevelopObject.setName(affairActivist.getName());
		affairDevelopObject.setPoliceNo(affairActivist.getPoliceNo());
		affairDevelopObject.setIdNumber(affairActivist.getIdNumber());
		affairDevelopObject.setSex(affairActivist.getSex());
		affairDevelopObject.setPartyBranch(affairActivist.getPartyBranch());
		affairDevelopObject.setPartyBranchId(affairActivist.getPartyBranchId());
		affairDevelopObject.setApprovalDate(affairActivist.getApprovalDate());
		affairDevelopObject.setEnterDate(affairActivist.getEnterDate());
		affairDevelopObject.setStatus("0");
		affairDevelopObjectService.save(affairDevelopObject);
		affairActivist.setStatus("1");
		affairActivistService.save(affairActivist);
		addMessage(redirectAttributes, "推送至入党发展对象成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairActivist/?repage&treeId="+affairActivist.getTreeId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairActivist:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairActivistService.deleteByIds(ids);
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
	 * @param affairActivist
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairActivist:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairActivist affairActivist, Model model) {
		model.addAttribute("affairActivist", affairActivist);
		return "modules/affair/affairActivistFormDetail";
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
	public String exportExcelByTemplate(AffairActivist affairActivist, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairActivist> page = null;
			if(flag == true){
				page = affairActivistService.findPage(new Page<AffairActivist>(request, response), affairActivist);
			}else{
				page = affairActivistService.findPage(new Page<AffairActivist>(request, response,-1), affairActivist);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairActivist.class);
			exportExcelNew.setWb(wb);
			List<AffairActivist> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairActivist?repage&treeId="+affairActivist.getTreeId();
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
			List<AffairActivist> list = ei.getDataList(AffairActivist.class);
			for (AffairActivist affairActivist : list){
				try{
					//根据身份证号去重
					if(StringUtils.isNotBlank(affairActivist.getIdNumber())){
						List<String> oldList = affairActivistService.findListByIdNo(affairActivist.getIdNumber());
						if(oldList !=null && oldList.size() >0){
							affairActivistService.deleteByIdNo(affairActivist.getIdNumber());
						}
						//党支部要绑定党支部id
						String partyBranchId = affairGeneralSituationService.findByName(affairActivist.getPartyBranch());
						affairActivist.setPartyBranchId(partyBranchId);
						BeanValidators.validateWithException(validator, affairActivist);
						affairActivistService.save(affairActivist);
						successNum++;
					}
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairActivist.getName()+"(身份证号码:"+affairActivist.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
	 * 统计分析查看入党积极分子明细
	 * @param affairActivist
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("partyActivistDetail")
	public String partyObjectDetail(AffairActivist affairActivist,HttpServletRequest request,HttpServletResponse response,Model model){
		//借用name属性  查看的明细类型
		Page<AffairActivist> page = affairActivistService.findActivistPage(new Page<>(request,response),affairActivist);
		model.addAttribute("page",page);
		return "modules/charts/chartActivistList";
	}
}