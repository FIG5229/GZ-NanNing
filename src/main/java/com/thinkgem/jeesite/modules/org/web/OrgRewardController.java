/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.web;

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
import com.thinkgem.jeesite.modules.affair.entity.AffairCollectiveAward;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import com.thinkgem.jeesite.modules.affair.entity.AffairXcUnitReward;
import com.thinkgem.jeesite.modules.affair.service.AffairCollectiveAwardService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwUnitAwardService;
import com.thinkgem.jeesite.modules.affair.service.AffairXcUnitRewardService;
import com.thinkgem.jeesite.modules.org.entity.OrgReward;
import com.thinkgem.jeesite.modules.org.service.OrgRewardService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceRank;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
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
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单位奖励信息Controller
 * @author eav.liu
 * @version 2019-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/org/orgReward")
public class OrgRewardController extends BaseController {

	@Autowired
	private OrgRewardService orgRewardService;

	/*
	* 宣传思想-理工创模-荣誉管理-集体奖励
	* */
	@Autowired
	private AffairXcUnitRewardService affairXcUnitRewardService;

	/*
	* 工会保障-表彰奖励-集体
	* */
	@Autowired
	private AffairCollectiveAwardService affairCollectiveAwardService;

	/*
	* 青年团建-团内奖惩-单位获奖
	* */
	@Autowired
	private AffairTwUnitAwardService affairTwUnitAwardService;

	@Autowired
	private UploadController uploadController;


	@ModelAttribute
	public OrgReward get(@RequestParam(required=false) String id) {
		OrgReward entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orgRewardService.get(id);
		}
		if (entity == null){
			entity = new OrgReward();
		}
		return entity;
	}
	
	@RequiresPermissions("org:orgReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrgReward orgReward, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession httpSession) {
		Page<OrgReward> page = new Page<> ();

		//奖励类型
		List<String> rewardType = new ArrayList<>();

		Object unitId = httpSession.getAttribute("unitId");
		String unid = String.valueOf(unitId);
		if(StringUtils.isNotBlank(unid)){
			page = orgRewardService.findPage(new Page<OrgReward>(request, response), orgReward);
			//宣传思想-立功创模-荣誉管理-集体奖励
			List<AffairXcUnitReward> affairXcUnitRewardServiceList = affairXcUnitRewardService.selectUnitReward(unid);
			for (int xc = 0; xc < affairXcUnitRewardServiceList.size(); xc++){
				AffairXcUnitReward affairXcUnitReward = affairXcUnitRewardServiceList.get(xc);

				OrgReward orgReward1 = new OrgReward();
				orgReward1.setId(affairXcUnitReward.getId());
				orgReward1.setWinUnitName(affairXcUnitReward.getUnit());
				orgReward1.setWinUnitCode(affairXcUnitReward.getUnitCode());
				orgReward1.setSign(affairXcUnitReward.getFlag());
				orgReward1.setRewardName(affairXcUnitReward.getName());
				orgReward1.setRewardNameCode(affairXcUnitReward.getNameCode());
				orgReward1.setApprovalUnit(affairXcUnitReward.getApprovalUnit());
				orgReward1.setApprovalUnitLevel(affairXcUnitReward.getApprovalUnitLevel());
				orgReward1.setApprovalDate(affairXcUnitReward.getDate());
				orgReward1.setFileNo(affairXcUnitReward.getFileNo());
				orgReward1.setFileName(affairXcUnitReward.getFileName());
				orgReward1.setApprovalOrgType(affairXcUnitReward.getApprovalUnitType());
				orgReward1.setCancelDate(affairXcUnitReward.getReDate());
				orgReward1.setCancelApprovalUnit(affairXcUnitReward.getReUnit());
				orgReward1.setRemark(affairXcUnitReward.getRemark());
				orgReward1.setMaterial(affairXcUnitReward.getDeedsMaterial());


				page.getList().add(orgReward1);
			}

			//工会保障-表彰奖励-集体
			List<AffairCollectiveAward> affairCollectiveAwardList = new ArrayList<>();
			affairCollectiveAwardList = affairCollectiveAwardService.selectList(unid);

			for (int ca = 0; ca < affairCollectiveAwardList.size(); ca++){

				AffairCollectiveAward affairCollectiveAward = affairCollectiveAwardList.get(ca);
				OrgReward orgReward2 = new OrgReward();
				orgReward2.setId(affairCollectiveAward.getId());
				orgReward2.setWinUnitName(affairCollectiveAward.getUnit());
				orgReward2.setWinUnitCode(affairCollectiveAward.getUnitId());
				orgReward2.setRewardName(affairCollectiveAward.getAwardName());
				orgReward2.setApprovalDate(affairCollectiveAward.getDate());
				orgReward2.setFileNo(affairCollectiveAward.getFileNo());
				orgReward2.setRemark(affairCollectiveAward.getRemark());


				page.getList().add(orgReward2);
			}


			//青年团建-团内奖惩-单位获奖
			List<AffairTwUnitAward> affairTwUnitAwardList = new ArrayList<>();
			affairTwUnitAwardList = affairTwUnitAwardService.selectList(unid);

			for (int tw = 0; tw < affairTwUnitAwardList.size(); tw++){
				AffairTwUnitAward affairTwUnitAward = affairTwUnitAwardList.get(tw);
				OrgReward orgReward3 = new OrgReward();
				orgReward3.setId(affairTwUnitAward.getId());
				orgReward3.setWinUnitName(affairTwUnitAward.getUnit());
				orgReward3.setWinUnitCode(affairTwUnitAward.getUnitId());
				orgReward3.setRewardName(affairTwUnitAward.getName());
				orgReward3.setApprovalUnit(affairTwUnitAward.getApprovalUnit());
				orgReward3.setApprovalDate(affairTwUnitAward.getDate());
				orgReward3.setFileNo(affairTwUnitAward.getFileNo());
				orgReward3.setRemark(affairTwUnitAward.getRemark());


				page.getList().add(orgReward3);

			}

		}
		httpSession.setAttribute("rewardType",rewardType);
		model.addAttribute("page", page);
		return "modules/org/orgRewardList";
	}

	@RequiresPermissions("org:orgReward:view")
	@RequestMapping(value = "form")
	public String form(OrgReward orgReward, Model model) {
		model.addAttribute("orgReward", orgReward);
		return "modules/org/orgRewardForm";
	}

	@RequiresPermissions("org:orgReward:edit")
	@RequestMapping(value = "save")
	public String save(OrgReward orgReward, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, orgReward)){
			return form(orgReward, model);
		}
		orgRewardService.save(orgReward);
		addMessage(redirectAttributes, "保存单位奖励信息成功");
		request.setAttribute("saveResult","sucess");
		return "modules/org/orgRewardForm";
	}
	
	@RequiresPermissions("org:orgReward:edit")
	@RequestMapping(value = "delete")
	public String delete(OrgReward orgReward, RedirectAttributes redirectAttributes) {
		orgRewardService.delete(orgReward);
		addMessage(redirectAttributes, "删除单位奖励信息成功");
		return "redirect:"+Global.getAdminPath()+"/org/orgReward/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("org:orgReward:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			orgRewardService.deleteByIds(ids);
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
	 * @param orgReward
	 * @param model
	 * @return
	 */
	@RequiresPermissions("org:orgReward:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(OrgReward orgReward, Model model,HttpSession httpSession){

		String id = orgReward.getId();
		AffairXcUnitReward affairXcUnitReward = new AffairXcUnitReward();
		affairXcUnitReward = affairXcUnitRewardService.selectBean(id);
		AffairCollectiveAward affairCollectiveAward = new AffairCollectiveAward();
		affairCollectiveAward = affairCollectiveAwardService.selectBean(id);
		AffairTwUnitAward affairTwUnitAward = new AffairTwUnitAward();
		affairTwUnitAward = affairTwUnitAwardService.selectBean(id);


		if (affairXcUnitReward != null){
			model.addAttribute("affairXcUnitReward", affairXcUnitReward);
			if(affairXcUnitReward.getFilePath() != null && affairXcUnitReward.getFilePath().length() > 0){
				List<Map<String, String>> filePathList = uploadController.filePathHandle(affairXcUnitReward.getFilePath());
				model.addAttribute("filePathList", filePathList);
			}
			return "modules/affair/affairXcUnitRewardFormDetail";
		}
		if (affairCollectiveAward != null){

			model.addAttribute("affairCollectiveAward", affairCollectiveAward);
			return "modules/affair/affairCollectiveAwardFormDetail";

		}
		if (affairTwUnitAward != null){
			model.addAttribute("affairTwUnitAward", affairTwUnitAward);
			if(affairTwUnitAward.getFilePath() != null && affairTwUnitAward.getFilePath().length() > 0){
				List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTwUnitAward.getFilePath());
				model.addAttribute("filePathList", filePathList);
			}
			return "modules/affair/affairTwUnitAwardFormDetail";
		}
		return null;
	}

	/**
	 * 导入excel
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, String orgId) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<OrgReward> list = ei.getDataList(OrgReward.class);
			for (OrgReward orgReward : list){
				try{
					orgReward.setOrgId(orgId);
					BeanValidators.validateWithException(validator, orgReward);
					orgRewardService.save(orgReward);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(受奖单位名称:"+orgReward.getWinUnitName()+"的数据)"+" 导入失败："+ex.getMessage());
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
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(OrgReward orgReward, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<OrgReward> page = null;
			if(flag == true){
				page = orgRewardService.findPage(new Page<OrgReward>(request, response,-1), orgReward);
			}else{
				page = orgRewardService.findPage(new Page<OrgReward>(request, response,-1), orgReward);
			}

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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, OrgReward.class);
			exportExcelNew.setWb(wb);
			List<OrgReward> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/orgReward?repage";
	}
}