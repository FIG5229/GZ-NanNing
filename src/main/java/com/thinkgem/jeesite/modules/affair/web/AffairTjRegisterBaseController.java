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
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegisterBase;
import com.thinkgem.jeesite.modules.affair.service.AffairTjRegisterBaseService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseService;
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
 * 团籍注册Controller
 * @author mason.xv
 * @version 2020-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTjRegisterBase")
public class AffairTjRegisterBaseController extends BaseController {

	@Autowired
	private AffairTjRegisterBaseService affairTjRegisterBaseService;

	@Autowired
	private AffairTwBaseService affairTwBaseService;


	@ModelAttribute
	public AffairTjRegisterBase get(@RequestParam(required=false) String id) {
		AffairTjRegisterBase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTjRegisterBaseService.get(id);
		}
		if (entity == null){
			entity = new AffairTjRegisterBase();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairTjRegisterBase:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairTjRegisterBaseIndex";
	}


	@RequiresPermissions("affair:affairTjRegisterBase:view")
	@RequestMapping(value = {"list"})
	public String list(AffairTjRegisterBase affairTjRegisterBase, HttpServletRequest request, HttpServletResponse response, Model model) {
//		model.addAttribute("partyBranchId", affairTjRegisterBase.getPartyBranchId());
		Page<AffairTjRegisterBase> page = affairTjRegisterBaseService.findPage(new Page<AffairTjRegisterBase>(request, response), affairTjRegisterBase);
		model.addAttribute("page", page);
		return "modules/affair/affairTjRegisterBaseList";
	}

	@RequiresPermissions("affair:affairTjRegisterBase:view")
	@RequestMapping(value = "form")
	public String form(AffairTjRegisterBase affairTjRegisterBase, Model model) {
		model.addAttribute("affairTjRegisterBase", affairTjRegisterBase);
		return "modules/affair/affairTjRegisterBaseForm";
	}

	@RequiresPermissions("affair:affairTjRegisterBase:edit")
	@RequestMapping(value = "save")
	public String save(AffairTjRegisterBase affairTjRegisterBase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTjRegisterBase)){
			return form(affairTjRegisterBase, model);
		}
		affairTjRegisterBaseService.save(affairTjRegisterBase);
		addMessage(redirectAttributes, "保存团籍注册成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairTjRegisterBaseForm";
		//return "redirect:"+Global.getAdminPath()+"/affair/affairTjRegisterBase/?repage";
	}
	
	@RequiresPermissions("affair:affairTjRegisterBase:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTjRegisterBase affairTjRegisterBase, RedirectAttributes redirectAttributes, String partyBranchId) {
		affairTjRegisterBaseService.delete(affairTjRegisterBase);
		addMessage(redirectAttributes, "删除团籍注册成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTjRegisterBase/list?partyBranchId="+partyBranchId;
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTjRegisterBase:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTjRegisterBaseService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTjRegisterBase affairTjRegisterBase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairTjRegisterBase> page = null;
			if(flag == true){
				page = affairTjRegisterBaseService.findPage(new Page<AffairTjRegisterBase>(request, response), affairTjRegisterBase);
			}else {
				page = affairTjRegisterBaseService.findPage(new Page<AffairTjRegisterBase>(request, response,-1), affairTjRegisterBase);
			}
/*
			Page<affairTjRegisterBase> page = affairTjRegisterBaseService.findPage(new Page<affairTjRegisterBase>(request, response,-1), affairTjRegisterBase);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTjRegisterBase.class);
			exportExcelNew.setWb(wb);
			List<AffairTjRegisterBase> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTjRegisterBase/list?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairTjRegisterBase> list = ei.getDataList(AffairTjRegisterBase.class);
			for (AffairTjRegisterBase affairTjRegisterBase : list){
				try{
					//根据身份证号去重
					if(StringUtils.isNotBlank(affairTjRegisterBase.getIdNumber())){
						List<String> oldList = affairTjRegisterBaseService.findListByIdNo(affairTjRegisterBase.getIdNumber());
						if(oldList != null && oldList.size() >0){
							affairTjRegisterBaseService.deleteByIdNo(affairTjRegisterBase.getIdNumber());
						}
						String partyBranchId = affairTwBaseService.findByName(affairTjRegisterBase.getPartyBranch());
						if(partyBranchId==null){
							//如果表格填写的 团支部名称 在系统中不存在，则不存储该用户
							throw new NullPointerException("所在团支部"+affairTjRegisterBase.getPartyBranch()+"，在该系统中未发现;");
						}else{
							affairTjRegisterBase.setPartyBranchId(partyBranchId);
							BeanValidators.validateWithException(validator, affairTjRegisterBase);
							affairTjRegisterBaseService.save(affairTjRegisterBase);
							successNum++;
						}
					}else{
						throw new NullPointerException("该条数据，身份证信息为空");
					}
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureNum++;
					failureMsg.append("<br>姓名:"+affairTjRegisterBase.getName()+"(身份证号码:"+affairTjRegisterBase.getIdNumber()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "从文件中读取到"+list.size()+"条数据，已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		//redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_affairTjRegisterBase";
	}

}