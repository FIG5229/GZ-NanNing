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
import com.thinkgem.jeesite.modules.affair.service.AffairPersonalRewardService;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoEvaluation;
import com.thinkgem.jeesite.modules.exam.service.ExamAutoEvaluationService;
import com.thinkgem.jeesite.modules.exam.service.ExamAutoPeopleInfoService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
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
import java.util.List;
import java.util.Map;

/**
 * 个人奖励Controller
 * @author cecil.li
 * @version 2019-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPersonalReward")
public class AffairPersonalRewardController extends BaseController {

	@Autowired
	private AffairPersonalRewardService affairPersonalRewardService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private ExamAutoEvaluationService examAutoEvaluationService;

	@Autowired
	private ExamAutoPeopleInfoService examAutoPeopleInfoService;
	
	@ModelAttribute
	public AffairPersonalReward get(@RequestParam(required=false) String id) {
		AffairPersonalReward entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPersonalRewardService.get(id);
		}
		if (entity == null){
			entity = new AffairPersonalReward();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPersonalReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPersonalReward affairPersonalReward, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*if("1".equals( UserUtils.getUser().getCompany().getId())){
			if ("1".equals(UserUtils.getUser().getOffice().getId())){
				affairPersonalReward.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairPersonalReward.setUnitId(UserUtils.getUser().getOffice().getId());
			}
		} else {
			if ("1".equals(UserUtils.getUser().getOffice().getId())){
				affairPersonalReward.setUnitId(UserUtils.getUser().getOffice().getId());
			}else {
				affairPersonalReward.setUnitId(UserUtils.getUser().getCompany().getId());
			}
		}*/
		List<Role> roleList = UserUtils.getUser().getRoleList();
		for (int i = 0; i < roleList.size(); i++) {
			if (roleList.get(i).getId().equals("3f1198e36a534b52a671bae729a0dca9")){
				affairPersonalReward.setIsPeople(UserUtils.getUser().getName());
			}
		}
		if ("5".equals(UserUtils.getUser().getOffice().getId())){
			affairPersonalReward.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairPersonalReward.setUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairPersonalReward.setCreateBy(UserUtils.getUser());
		Page<AffairPersonalReward> page = affairPersonalRewardService.findPage(new Page<AffairPersonalReward>(request, response), affairPersonalReward);
		model.addAttribute("page", page);
		return "modules/affair/affairPersonalRewardList";
	}

	@RequiresPermissions("affair:affairPersonalReward:add")
	@RequestMapping(value = "form")
	public String form(AffairPersonalReward affairPersonalReward, Model model) {

		model.addAttribute("affairPersonalReward", affairPersonalReward);
		return "modules/affair/affairPersonalRewardForm";
	}

	@RequiresPermissions("affair:affairPersonalReward:edit")
	@RequestMapping(value = "save")
	public String save(AffairPersonalReward affairPersonalReward, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPersonalReward)) {
			return form(affairPersonalReward, model);
		}
		affairPersonalRewardService.save(affairPersonalReward);
		addMessage(redirectAttributes, "保存个人奖励成功");
		request.setAttribute("saveResult","sucess");
//		return "redirect:"+Global.getAdminPath()+"/affair/affairPersonalReward/?repage&rewardType="+affairPersonalReward.getRewardType();
		return "modules/affair/affairPersonalRewardForm";

	}
	
	@RequiresPermissions("affair:affairPersonalReward:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairPersonalReward affairPersonalReward, RedirectAttributes redirectAttributes) {
		affairPersonalRewardService.delete(affairPersonalReward);
		addMessage(redirectAttributes, "删除个人奖励成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPersonalReward/?repage";
	}

	/**
	 * 详情
	 * @param affairPersonalReward
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("affair:affairPersonalReward:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPersonalReward affairPersonalReward, Model model) {
		if (affairPersonalReward.getUnit().equals("5")){
			affairPersonalReward.setUnit(affairPersonalReward.getInputUnit());
		}
		if (affairPersonalReward.getRewardName().equals("21")){
			affairPersonalReward.setRewardName(affairPersonalReward.getInputReward());
		}
		model.addAttribute("affairPersonalReward", affairPersonalReward);
		if(affairPersonalReward.getFilePath() != null && affairPersonalReward.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPersonalReward.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPersonalRewardFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPersonalReward:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPersonalRewardService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairPersonalReward affairPersonalReward, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairPersonalReward.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairPersonalReward.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairPersonalReward.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairPersonalReward> page = null;
			if(flag == true){
				page = affairPersonalRewardService.findPage(new Page<AffairPersonalReward>(request, response), affairPersonalReward);
			}else{
				page = affairPersonalRewardService.findPage(new Page<AffairPersonalReward>(request, response,-1), affairPersonalReward);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPersonalReward.class);
			exportExcelNew.setWb(wb);
			List<AffairPersonalReward> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairPersonalReward/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPersonalReward> list = ei.getDataList(AffairPersonalReward.class);
			for (AffairPersonalReward affairPersonalReward : list){
				try{
					affairPersonalReward.setUnitId(officeService.findByName(affairPersonalReward.getUnit()));
					affairPersonalReward.setApprovalUnitId(officeService.findByName(affairPersonalReward.getApprovalUnit()));
					affairPersonalReward.setTypeFlag("2");
					BeanValidators.validateWithException(validator, affairPersonalReward);
					affairPersonalRewardService.save(affairPersonalReward);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(" 导入失败："+ex.getMessage());
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

	@RequestMapping(value = "findPersonnelRewardInfoDetail")
	public String findUnitRewardInfoDetail(AffairPersonalReward affairPersonalReward, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairPersonalReward> page;
		page = affairPersonalRewardService.findDetailInfoByRewardName(new Page<AffairPersonalReward>(request, response), affairPersonalReward);
		model.addAttribute("page", page);
		model.addAttribute("affairXcUnitReward", affairPersonalReward);
		return "modules/charts/publicityPersonnelRewardDetail";
	}

	@RequestMapping(value = "findOtherPersonnelRewardInfoDetail")
	public String findOtherUnitRewardInfoDetail(AffairPersonalReward affairPersonalReward, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairPersonalReward> page;
		page = affairPersonalRewardService.findOtherDetailInfoByRewardName(new Page<AffairPersonalReward>(request, response), affairPersonalReward);
		model.addAttribute("page", page);
		model.addAttribute("affairXcUnitReward", affairPersonalReward);
		return "modules/charts/publicityPersonnelRewardDetail";
	}

}