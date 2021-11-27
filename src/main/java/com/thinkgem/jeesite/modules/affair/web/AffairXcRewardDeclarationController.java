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
import com.thinkgem.jeesite.modules.affair.entity.AffairXcRewardDeclaration;
import com.thinkgem.jeesite.modules.affair.entity.AffairXcUnitReward;
import com.thinkgem.jeesite.modules.affair.service.AffairXcRewardDeclarationService;
import com.thinkgem.jeesite.modules.affair.service.AffairXcUnitRewardService;
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
 * 单位集体奖励申报Controller
 * @author cecil.li
 * @version 2019-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairXcRewardDeclaration")
public class AffairXcRewardDeclarationController extends BaseController {

	@Autowired
	private AffairXcRewardDeclarationService affairXcRewardDeclarationService;
	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairXcUnitRewardService affairXcUnitRewardService;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairXcRewardDeclaration get(@RequestParam(required=false) String id) {
		AffairXcRewardDeclaration entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairXcRewardDeclarationService.get(id);
		}
		if (entity == null){
			entity = new AffairXcRewardDeclaration();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairXcRewardDeclaration:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairXcRewardDeclaration affairXcRewardDeclaration, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairXcRewardDeclaration> page = affairXcRewardDeclarationService.findPage(new Page<AffairXcRewardDeclaration>(request, response), affairXcRewardDeclaration);
		model.addAttribute("page", page);
		return "modules/affair/affairXcRewardDeclarationList";
	}

	@RequiresPermissions("affair:affairXcRewardDeclaration:view")
	@RequestMapping(value = "form")
	public String form(AffairXcRewardDeclaration affairXcRewardDeclaration, Model model) {
		model.addAttribute("affairXcRewardDeclaration", affairXcRewardDeclaration);
		return "modules/affair/affairXcRewardDeclarationForm";
	}

	@RequiresPermissions("affair:affairXcRewardDeclaration:edit")
	@RequestMapping(value = "save")
	public String save(AffairXcRewardDeclaration affairXcRewardDeclaration, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairXcRewardDeclaration)){
			return form(affairXcRewardDeclaration, model);
		}
		affairXcRewardDeclarationService.save(affairXcRewardDeclaration);
		addMessage(redirectAttributes, "保存单位集体奖励申报成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairXcRewardDeclarationForm";
	}
	
	@RequiresPermissions("affair:affairXcRewardDeclaration:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairXcRewardDeclaration affairXcRewardDeclaration, RedirectAttributes redirectAttributes) {
		affairXcRewardDeclarationService.delete(affairXcRewardDeclaration);
		addMessage(redirectAttributes, "删除单位集体奖励申报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairXcRewardDeclaration/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairXcRewardDeclaration:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairXcRewardDeclarationService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairXcRewardDeclaration:manage")
	@RequestMapping(value = "manage")
	public String manageList(AffairXcRewardDeclaration affairXcRewardDeclaration, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairXcRewardDeclaration> page = affairXcRewardDeclarationService.findPage(new Page<AffairXcRewardDeclaration>(request, response), affairXcRewardDeclaration);
		model.addAttribute("page", page);
		return "modules/affair/affairXcRewardDeclarationList";
	}

	@RequiresPermissions("affair:affairXcRewardDeclaration:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairXcRewardDeclarationCheckDialog";
	}


	@ResponseBody
	@RequiresPermissions("affair:affairXcRewardDeclaration:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairXcRewardDeclaration affairXcRewardDeclaration, HttpServletRequest request) {
		affairXcRewardDeclarationService.save(affairXcRewardDeclaration);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		if (affairXcRewardDeclaration.getSbType().equals("2")){
			AffairXcUnitReward affairXcUnitReward =new AffairXcUnitReward();
			affairXcUnitReward.setUnit(affairXcRewardDeclaration.getUnit());
			affairXcUnitReward.setUnitId(affairXcRewardDeclaration.getUnitId());
			affairXcUnitReward.setName(affairXcRewardDeclaration.getName());
			affairXcUnitReward.setDeedsMaterial(affairXcRewardDeclaration.getRemark());
			affairXcUnitReward.setDate(new Date());
			affairXcUnitRewardService.save(affairXcUnitReward);
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
		List <AffairXcRewardDeclaration> list = affairXcRewardDeclarationService.findByIds(userList);
		for (AffairXcRewardDeclaration affairXcRewardDeclaration: list){
			affairXcRewardDeclaration.setSbType("4");
			affairXcRewardDeclaration.setChuCheckMan(chuCheckMan);
			affairXcRewardDeclaration.setChuCheckId(chuCheckId);
			affairXcRewardDeclaration.setSubmitId(user.getId());
			affairXcRewardDeclaration.setSubmitMan(user.getName());
			affairXcRewardDeclarationService.save(affairXcRewardDeclaration);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairXcRewardDeclaration/list";
	}

	/**
	 * 详情
	 * @param affairXcRewardDeclaration
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairXcRewardDeclaration:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairXcRewardDeclaration affairXcRewardDeclaration, Model model) {
		model.addAttribute("affairXcRewardDeclaration", affairXcRewardDeclaration);
		if(affairXcRewardDeclaration.getFilePath() != null && affairXcRewardDeclaration.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairXcRewardDeclaration.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairXcRewardDeclarationFormDetail";
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
	public String exportExcelByTemplate(AffairXcRewardDeclaration affairXcRewardDeclaration, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairXcRewardDeclaration> page = null;
			if(flag == true){
				page = affairXcRewardDeclarationService.findPage(new Page<AffairXcRewardDeclaration>(request, response), affairXcRewardDeclaration);
			}else{
				page = affairXcRewardDeclarationService.findPage(new Page<AffairXcRewardDeclaration>(request, response,-1), affairXcRewardDeclaration);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairXcRewardDeclaration.class);
			exportExcelNew.setWb(wb);
			List<AffairXcRewardDeclaration> list =page.getList();
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
			addMessage(redirectAttributes, "导出单位集体奖励申报信息失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairXcRewardDeclaration/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairXcRewardDeclaration> list = ei.getDataList(AffairXcRewardDeclaration.class);
			for (AffairXcRewardDeclaration xcRewardDeclaration : list){
				try{
					//获取申报单位名称
					String unit = xcRewardDeclaration.getUnit();
					//设置申报单位id
					xcRewardDeclaration.setUnitId(officeService.findByName(unit)==null ? "":officeService.findByName(unit));
					//获取审核单位名称
					String approvalUnit = xcRewardDeclaration.getApprovalUnit();
					//设置审核单位id   从user表取id
					xcRewardDeclaration.setApprovalUnitId(officeService.findUserByName(approvalUnit)==null ? "":officeService.findUserByName(approvalUnit));
					BeanValidators.validateWithException(validator, xcRewardDeclaration);
					affairXcRewardDeclarationService.save(xcRewardDeclaration);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureNum++;
					failureMsg.append("<br>奖励名称:"+xcRewardDeclaration.getName()+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_XcRewardDeclaration";
	}

}