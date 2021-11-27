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
import com.thinkgem.jeesite.modules.affair.dao.AffairZyInfoDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairZyInfo;
import com.thinkgem.jeesite.modules.affair.service.AffairZyInfoService;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 助医管理Controller
 * @author cecil.li
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairZyInfo")
public class AffairZyInfoController extends BaseController {

	@Autowired
	private AffairZyInfoDao affairZyInfoDao;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairZyInfoService affairZyInfoService;
	
	@ModelAttribute
	public AffairZyInfo get(@RequestParam(required=false) String id) {
		AffairZyInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairZyInfoService.get(id);
		}
		if (entity == null){
			entity = new AffairZyInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairZyInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairZyInfo affairZyInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		if("1".equals(UserUtils.getUser().getCompany().getId())){
			if (StringUtils.isBlank(affairZyInfo.getTabFlag()) ){
				//打开tab页为用户所在公司
//				affairZyInfo.setTabFlag(UserUtils.getUser().getCompany().getId());
				//打开tab页为第一个tab 南宁
				affairZyInfo.setTabFlag("34");
			}
		}else {
			affairZyInfo.setTabFlag(UserUtils.getUser().getCompany().getId());
		}

		//金额小计
		Float sumMoney = affairZyInfoService.getSumMoney(affairZyInfo);
		model.addAttribute("sumMoney", sumMoney);
		model.addAttribute("tabFlag", affairZyInfo.getTabFlag());
		Page<AffairZyInfo> page = affairZyInfoService.findPage(new Page<AffairZyInfo>(request, response), affairZyInfo);
		model.addAttribute("page", page);
		return "modules/affair/affairZyInfoList";
	}

	@RequiresPermissions("affair:affairZyInfo:view")
	@RequestMapping(value = "form")
	public String form(AffairZyInfo affairZyInfo, Model model) {
		model.addAttribute("affairZyInfo", affairZyInfo);
		return "modules/affair/affairZyInfoForm";
	}

	//详情页面的添加  待完善
	@RequiresPermissions("affair:affairZyInfo:edit")
	@RequestMapping(value = "formInDetail")
	public String formInDetail(/*AffairZyInfo affairZyInfo, Model model*/) {
		/*AffairZyInfo affairZyInfoBack = new AffairZyInfo();
		affairZyInfoBack.setDate(affairZyInfo.getDate());
		affairZyInfoBack.setUnit(affairZyInfo.getUnit());
		affairZyInfoBack.setUnitId(affairZyInfo.getUnitId());
		affairZyInfoBack.setBzJigou(affairZyInfo.getBzJigou());
		affairZyInfoBack.setType(affairZyInfo.getType());
		model.addAttribute("affairZyInfo", affairZyInfoBack);*/
		return "modules/affair/affairZyInfoInDetailForm";
	}
	
	@RequiresPermissions("affair:affairZyInfo:edit")
	@RequestMapping(value = "save")
	public String save(AffairZyInfo affairZyInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairZyInfo)){
			return form(affairZyInfo, model);
		}
		affairZyInfoService.save(affairZyInfo);
		addMessage(redirectAttributes, "保存助医管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairZyInfoForm";
	}
	
	@RequiresPermissions("affair:affairZyInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairZyInfo affairZyInfo, RedirectAttributes redirectAttributes) {
		affairZyInfoService.delete(affairZyInfo);
		addMessage(redirectAttributes, "删除助医管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairZyInfo/list?repage&tabFlag="+affairZyInfo.getTabFlag();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairZyInfo:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairZyInfoService.deleteByIds(ids);
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
	 * @param affairZyInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairZyInfo:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairZyInfo affairZyInfo, Model model) {
		model.addAttribute("affairZyInfo", affairZyInfo);
		return "modules/affair/affairZyInfoFormDetail";
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
	public String exportExcelByTemplate(AffairZyInfo affairZyInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairZyInfo> page = null;
			if(flag == true){
				page = affairZyInfoService.findPage(new Page<AffairZyInfo>(request, response), affairZyInfo);
			}else {
				page = affairZyInfoService.findPage(new Page<AffairZyInfo>(request, response,-1), affairZyInfo);
			}
			/*if(flag == true){
				list = affairZyInfoService.findList(affairZyInfo);
			}else {
				list = affairZyInfoService.findList(affairZyInfo);
			}*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairZyInfo.class);
			exportExcelNew.setWb(wb);
			List<AffairZyInfo> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairZyInfo/?repage&tabFlag="+affairZyInfo.getTabFlag();
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			String unitId;
			//修改
			List<AffairZyInfo> list = ei.getDataList(AffairZyInfo.class);
			for (AffairZyInfo affairZyInfo : list){
				try{
					BeanValidators.validateWithException(validator, affairZyInfo);
					if(StringUtils.isNotBlank(affairZyInfo.getUnit()))
					{
						unitId =  officeService.findByName(affairZyInfo.getUnit());
						if (StringUtils.isNotBlank(unitId)){
							affairZyInfo.setUnitId(unitId);
							affairZyInfoService.save(affairZyInfo);
							successNum++;
						}else{
							throw new Exception(affairZyInfo.getUnit());
						}
					}else{
						throw new Exception();
					}
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(单位名:"+affairZyInfo.getUnit()+")"+" 导入失败："+ex.getMessage());
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
	 * 统计分析明细
	 * @param affairZyInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("medicalAidDetail")
	public String medicalAidDetail(AffairZyInfo affairZyInfo,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairZyInfo> page = affairZyInfoService.findMedicalAidPage(new Page<AffairZyInfo>(request,response),affairZyInfo);
		/*
		* int sumMoney = page.getList().stream().mapToInt(AffairZyInfo::getMoney).sum();
		* AffairZyInfo::getMoney	空指针不知如何处理*/
		Page<AffairZyInfo> moneyPage = affairZyInfoService.findMedicalAidPage(new Page<AffairZyInfo>(request,response,-1),affairZyInfo);
//		int sumMoney = moneyPage.getList().stream().mapToInt(item-> Objects.isNull(item.getMoney())?0:item.getMoney()).sum();
		List<AffairZyInfo> list = moneyPage.getList();
		Float sumMoney = 0.0F;
		for (AffairZyInfo zyInfo : list) {
            Float money = zyInfo.getMoney();
            if(money==null){
            	sumMoney+=0;
			}else{
				sumMoney += money;
			}
		}
		model.addAttribute("sumMoney", sumMoney);
		model.addAttribute("page", page);
		return "modules/charts/chartZyInfoList";
	}
}