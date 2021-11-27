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
import com.thinkgem.jeesite.modules.affair.entity.AffairXcUnitReward;
import com.thinkgem.jeesite.modules.affair.service.AffairXcUnitRewardService;
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
 * 单位集体奖励表Controller
 * @author cecil.li
 * @version 2019-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairXcUnitReward")
public class AffairXcUnitRewardController extends BaseController {

	@Autowired
	private AffairXcUnitRewardService affairXcUnitRewardService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairXcUnitReward get(@RequestParam(required=false) String id) {
		AffairXcUnitReward entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairXcUnitRewardService.get(id);
		}
		if (entity == null){
			entity = new AffairXcUnitReward();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairXcUnitReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairXcUnitReward affairXcUnitReward, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*if("1".equals( UserUtils.getUser().getCompany().getId())){
			if ("1".equals(UserUtils.getUser().getOffice().getId())){
				affairXcUnitReward.setUnitId(UserUtils.getUser().getOffice().getId());
			}else {
				affairXcUnitReward.setUnitId(UserUtils.getUser().getCompany().getId());
			}
		} else {
			if ("1".equals(UserUtils.getUser().getOffice().getId())){
				affairXcUnitReward.setUnitId(UserUtils.getUser().getOffice().getId());
			}else {
				affairXcUnitReward.setUnitId(UserUtils.getUser().getCompany().getId());
			}
		}*/
		if ("5".equals(UserUtils.getUser().getOffice().getId())){
			affairXcUnitReward.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairXcUnitReward.setUnitId(UserUtils.getUser().getOffice().getId());
		}

		affairXcUnitReward.setCreateBy(UserUtils.getUser());
		Page<AffairXcUnitReward> page = affairXcUnitRewardService.findPage(new Page<AffairXcUnitReward>(request, response), affairXcUnitReward);
//		model.addAttribute("reward_type", reward_type);
		model.addAttribute("page", page);
		return "modules/affair/affairXcUnitRewardList";
	}

	@RequiresPermissions("affair:affairXcUnitReward:add")
	@RequestMapping(value = "form")
	public String form(AffairXcUnitReward affairXcUnitReward, Model model) {
		model.addAttribute("affairXcUnitReward", affairXcUnitReward);
		return "modules/affair/affairXcUnitRewardForm";
	}

	@RequiresPermissions("affair:affairXcUnitReward:add")
	@RequestMapping(value = "save")
	public String save(AffairXcUnitReward affairXcUnitReward, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairXcUnitReward)){
			return form(affairXcUnitReward, model);
		}
		affairXcUnitRewardService.save(affairXcUnitReward);
		addMessage(redirectAttributes, "保存单位集体奖励成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairXcUnitRewardForm";
	}
	
	@RequiresPermissions("affair:affairXcUnitReward:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairXcUnitReward affairXcUnitReward, RedirectAttributes redirectAttributes) {
		affairXcUnitRewardService.delete(affairXcUnitReward);
		addMessage(redirectAttributes, "删除单位集体奖励成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairXcUnitReward/?repage";
	}

	/**
	 * 详情
	 * @param affairXcUnitReward
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("affair:affairXcUnitReward:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairXcUnitReward affairXcUnitReward, Model model) {
		model.addAttribute("affairXcUnitReward", affairXcUnitReward);
		if(affairXcUnitReward.getFilePath() != null && affairXcUnitReward.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairXcUnitReward.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairXcUnitRewardFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairXcUnitReward:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairXcUnitRewardService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairXcUnitReward affairXcUnitReward, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairXcUnitReward.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairXcUnitReward.setUnitId(UserUtils.getUser().getOffice().getId());
			}

			affairXcUnitReward.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairXcUnitReward> page = null;
			if(flag == true){
				page = affairXcUnitRewardService.findPage(new Page<AffairXcUnitReward>(request, response), affairXcUnitReward);
			}else{
				page = affairXcUnitRewardService.findPage(new Page<AffairXcUnitReward>(request, response,-1), affairXcUnitReward);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairXcUnitReward.class);
			exportExcelNew.setWb(wb);
			List<AffairXcUnitReward> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairXcUnitReward/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairXcUnitReward> list = ei.getDataList(AffairXcUnitReward.class);
			for (AffairXcUnitReward affairXcUnitReward : list){
				try{
					BeanValidators.validateWithException(validator, affairXcUnitReward);
					affairXcUnitReward.setUnitId(officeService.findByName(affairXcUnitReward.getUnit()));
					affairXcUnitReward.setApprovalUnitId(officeService.findByName(affairXcUnitReward.getApprovalUnit()));
					affairXcUnitReward.setReUnitId(officeService.findByName(affairXcUnitReward.getReUnit()));
					affairXcUnitReward.setTypeFlag("1");
					affairXcUnitRewardService.save(affairXcUnitReward);
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

	@RequestMapping(value = "findUnitRewardInfoDetail")
	public String findUnitRewardInfoDetail(AffairXcUnitReward affairXcUnitReward, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairXcUnitReward> page;
		page = affairXcUnitRewardService.findDetailInfoByRewardName(new Page<AffairXcUnitReward>(request, response), affairXcUnitReward);
		model.addAttribute("page", page);
		model.addAttribute("affairXcUnitReward", affairXcUnitReward);
		return "modules/charts/publicityUnitRewardDetail";
	}

	@RequestMapping(value = "findOtherUnitRewardInfoDetail")
	public String findOtherUnitRewardInfoDetail(AffairXcUnitReward affairXcUnitReward, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairXcUnitReward> page;
		page = affairXcUnitRewardService.findOtherDetailInfoByRewardName(new Page<AffairXcUnitReward>(request, response), affairXcUnitReward);
		model.addAttribute("page", page);
		model.addAttribute("affairXcUnitReward", affairXcUnitReward);
		return "modules/charts/publicityUnitRewardDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairXcUnitReward:view")
	@RequestMapping(value = "findCodeName")
	public List<String> findCodeName(@RequestParam(value = "nameCode") String nameCode,Model model){

		List<String> codeName = affairXcUnitRewardService.findCodeName(nameCode);

		model.addAttribute("codeName",codeName);

		return codeName;
	}


}