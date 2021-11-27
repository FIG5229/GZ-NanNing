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
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalReward;
import com.thinkgem.jeesite.modules.affair.entity.AffairRewardDeclaration;
import com.thinkgem.jeesite.modules.affair.service.AffairPersonalRewardService;
import com.thinkgem.jeesite.modules.affair.service.AffairRewardDeclarationService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import java.util.*;

/**
 * 个人奖励申报Controller
 * @author cecil.li
 * @version 2019-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairRewardDeclaration")
public class AffairRewardDeclarationController extends BaseController {

	@Autowired
	private AffairRewardDeclarationService affairRewardDeclarationService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairPersonalRewardService affairPersonalRewardService;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairRewardDeclaration get(@RequestParam(required=false) String id) {
		AffairRewardDeclaration entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairRewardDeclarationService.get(id);
		}
		if (entity == null){
			entity = new AffairRewardDeclaration();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairRewardDeclaration:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairRewardDeclaration affairRewardDeclaration, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairRewardDeclaration> page = affairRewardDeclarationService.findPage(new Page<AffairRewardDeclaration>(request, response), affairRewardDeclaration); 
		model.addAttribute("page", page);
		return "modules/affair/affairRewardDeclarationList";
	}

	@RequiresPermissions("affair:affairRewardDeclaration:view")
	@RequestMapping(value = "form")
	public String form(AffairRewardDeclaration affairRewardDeclaration, Model model) {
		model.addAttribute("affairRewardDeclaration", affairRewardDeclaration);
		return "modules/affair/affairRewardDeclarationForm";
	}

	@RequiresPermissions("affair:affairRewardDeclaration:edit")
	@RequestMapping(value = "save")
	public String save(AffairRewardDeclaration affairRewardDeclaration, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairRewardDeclaration)){
			return form(affairRewardDeclaration, model);
		}
		affairRewardDeclarationService.save(affairRewardDeclaration);
		addMessage(redirectAttributes, "保存个人奖励申报成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairRewardDeclarationForm";
	}
	
	@RequiresPermissions("affair:affairRewardDeclaration:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairRewardDeclaration affairRewardDeclaration, RedirectAttributes redirectAttributes) {
		affairRewardDeclarationService.delete(affairRewardDeclaration);
		addMessage(redirectAttributes, "删除个人奖励申报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairRewardDeclaration/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairRewardDeclaration:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairRewardDeclarationService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairRewardDeclaration:manage")
	@RequestMapping(value = "manage")
	public String manageList(AffairRewardDeclaration affairRewardDeclaration, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairRewardDeclaration> page = affairRewardDeclarationService.findPage(new Page<AffairRewardDeclaration>(request, response), affairRewardDeclaration);
		model.addAttribute("page", page);
		return "modules/affair/affairRewardDeclarationList";
	}

	@RequiresPermissions("affair:affairRewardDeclaration:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairRewardDeclarationCheckDialog";
	}


	@ResponseBody
	@RequiresPermissions("affair:affairRewardDeclaration:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairRewardDeclaration affairRewardDeclaration, HttpServletRequest request) {
		affairRewardDeclarationService.save(affairRewardDeclaration);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		/*申报通过后 保存到荣誉管理*/
		if (affairRewardDeclaration.getSbType().equals("2")){
			AffairPersonalReward affairPersonalReward = new AffairPersonalReward();
			affairPersonalReward.setApprovalUnit(affairRewardDeclaration.getUnit());
			affairPersonalReward.setApprovalUnitId(affairRewardDeclaration.getUnitId());
			affairPersonalReward.setName(affairRewardDeclaration.getName());
			affairPersonalReward.setRewardName(affairRewardDeclaration.getRewardName());
			affairPersonalReward.setDeedsMaterial(affairRewardDeclaration.getRemark());
			affairPersonalReward.setDate(new Date());
			affairPersonalRewardService.save(affairPersonalReward);
		}
		return result;
	}

	@RequestMapping(value = "personnelChoose")
	public String personnelChoose(String ids,HttpServletRequest request, Model model) {
		model.addAttribute("ids", ids);
		return "modules/affair/personnelChoose";
	}

	/**
	 * 批量提交
	 * @param
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();
		String chuCheckMan = request.getParameter("chuCheckMan");
		String chuCheckId = request.getParameter("chuCheckId");
		Collections.addAll(userList,idsArray);
		/*String companyId = UserUtils.getUser().getId();*/
		List <AffairRewardDeclaration> list = affairRewardDeclarationService.findByIds(userList);
		for (AffairRewardDeclaration affairRewardDeclaration: list){
			affairRewardDeclaration.setSbType("4");
			affairRewardDeclaration.setChuCheckMan(chuCheckMan);
			affairRewardDeclaration.setChuCheckId(chuCheckId);
			affairRewardDeclaration.setSubmitId(user.getId());
			affairRewardDeclaration.setSubmitMan(user.getName());
			affairRewardDeclarationService.save(affairRewardDeclaration);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairRewardDeclaration/list";
	}

	/**
	 * 详情
	 * @param affairRewardDeclaration
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairRewardDeclaration:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairRewardDeclaration affairRewardDeclaration, Model model) {
		model.addAttribute("affairRewardDeclaration", affairRewardDeclaration);
		if(affairRewardDeclaration.getFilePath() != null && affairRewardDeclaration.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairRewardDeclaration.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairRewardDeclarationFormDetail";
	}

	/**
	 * 导出excel格式数据
	 * @param affairRewardDeclaration
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairRewardDeclaration affairRewardDeclaration, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairRewardDeclaration> page = null;
			if(flag == true){
				page = affairRewardDeclarationService.findPage(new Page<AffairRewardDeclaration>(request, response), affairRewardDeclaration);
			}else{
				page = affairRewardDeclarationService.findPage(new Page<AffairRewardDeclaration>(request, response,-1), affairRewardDeclaration);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairRewardDeclaration.class);
			exportExcelNew.setWb(wb);
			List<AffairRewardDeclaration> list =page.getList();
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
		catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出个人奖励申报信息失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairRewardDeclaration/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairRewardDeclaration> list = ei.getDataList(AffairRewardDeclaration.class);
			for (AffairRewardDeclaration affairRewardDeclaration : list){
				try{
					//获取申报单位名称
					String unit = affairRewardDeclaration.getUnit();
					//设置申报单位id
					affairRewardDeclaration.setUnitId(officeService.findByName(unit)==null ? "":officeService.findByName(unit));
					//获取审核单位名称
					String approvalUnit = affairRewardDeclaration.getApprovalUnit();
					//设置审核单位id  从user表取id
					affairRewardDeclaration.setApprovalUnitId(officeService.findUserByName(approvalUnit)==null ? "":officeService.findUserByName(approvalUnit));
					BeanValidators.validateWithException(validator, affairRewardDeclaration);
					affairRewardDeclarationService.save(affairRewardDeclaration);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureNum++;
					failureMsg.append("<br>姓名:"+affairRewardDeclaration.getName()+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_rewardDeclaration";
	}


}