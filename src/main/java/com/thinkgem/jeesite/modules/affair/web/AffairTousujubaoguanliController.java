/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTousujubaoguanli;
import com.thinkgem.jeesite.modules.affair.service.AffairTousujubaoguanliService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 投诉举报Controller
 * @author cecil.li
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTousujubaoguanli")
public class AffairTousujubaoguanliController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairTousujubaoguanliService affairTousujubaoguanliService;
	
	@ModelAttribute
	public AffairTousujubaoguanli get(@RequestParam(required=false) String id) {
		AffairTousujubaoguanli entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTousujubaoguanliService.get(id);
		}
		if (entity == null){
			entity = new AffairTousujubaoguanli();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTousujubaoguanli:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTousujubaoguanli affairTousujubaoguanli, HttpServletRequest request, HttpServletResponse response, Model model) {

		if ("6".equals(UserUtils.getUser().getOffice().getId())){
			affairTousujubaoguanli.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairTousujubaoguanli.setUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairTousujubaoguanli.setCreateBy(UserUtils.getUser());

		if("2".equals(affairTousujubaoguanli.getIscheck())){
			affairTousujubaoguanli.setSubOption(affairTousujubaoguanli.getBjType());
		}

		String reason = request.getParameter("reason");//数据范围 0 空  1 其他年份  2 全部
		model.addAttribute("reason",reason);
		if("1".equals(reason)){
			affairTousujubaoguanli.setStartDate(null);
			affairTousujubaoguanli.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairTousujubaoguanli.getStartDate()==null && affairTousujubaoguanli.getEndDate() == null){
				Integer year = Integer.parseInt(DateUtils.getYear());//获取当前年
				affairTousujubaoguanli.setYear(year);
			}else{
				affairTousujubaoguanli.setYear(null);
			}
		}else{
			//默认显示当前年度数据
			Integer year = Integer.parseInt(DateUtils.getYear());//获取当前年
			affairTousujubaoguanli.setYear(year);
			affairTousujubaoguanli.setStartDate(null);
			affairTousujubaoguanli.setEndDate(null);
		}
		Page<AffairTousujubaoguanli> page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response), affairTousujubaoguanli);
		model.addAttribute("page", page);
		//  11.13  kevin.jia
	/*	if (affairTousujubaoguanli.getStartDate() != null || affairTousujubaoguanli.getEndDate() != null){

			Page<AffairTousujubaoguanli> page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response), affairTousujubaoguanli);

			model.addAttribute("page", page);
		}else if (affairTousujubaoguanli.getStartDate() == null && affairTousujubaoguanli.getEndDate() == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			String nowDate = sdf.format(date);
			Date yearDate = null;
			try {
				yearDate = sdf.parse(nowDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}*/

		/*if("6".equals(UserUtils.getUser().getOffice().getId())){//南宁铁路公安局纪检监察处
			List<String> roleIdList = UserUtils.getUser().getRoleIdList();
			for (String roleId:roleIdList) {
				if("c1030a6707e04b8eaf90582c0da22e20".equals(roleId)){//南宁铁路公安局纪检监察管理员角色
					model.addAttribute("isJiJianAdmin", "1");
					break;
				}
			}
		}*/
		return "modules/affair/affairTousujubaoguanliList";
	}

	@RequiresPermissions("affair:affairTousujubaoguanli:add")
	@RequestMapping(value = "form")
	public String form(AffairTousujubaoguanli affairTousujubaoguanli, Model model) {
		model.addAttribute("affairTousujubaoguanli", affairTousujubaoguanli);
		return "modules/affair/affairTousujubaoguanliForm";
	}

	@RequiresPermissions("affair:affairTousujubaoguanli:edit")
	@RequestMapping(value = "save")
	public String save(AffairTousujubaoguanli affairTousujubaoguanli, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTousujubaoguanli)){
			return form(affairTousujubaoguanli, model);
		}
		if("2".equals(affairTousujubaoguanli.getIscheck())){
			affairTousujubaoguanli.setSubOption(affairTousujubaoguanli.getBjType());
		}
		affairTousujubaoguanliService.save(affairTousujubaoguanli);
		addMessage(redirectAttributes, "保存投诉举报成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTousujubaoguanliForm";
	}
	
	@RequiresPermissions("affair:affairTousujubaoguanli:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairTousujubaoguanli affairTousujubaoguanli, RedirectAttributes redirectAttributes) {
		affairTousujubaoguanliService.delete(affairTousujubaoguanli);
		addMessage(redirectAttributes, "删除投诉举报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTousujubaoguanli/?repage";
	}

	/**
	 * 详情
	 * @param affairTousujubaoguanli
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTousujubaoguanli:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTousujubaoguanli affairTousujubaoguanli, Model model) {
		model.addAttribute("affairTousujubaoguanli", affairTousujubaoguanli);
		return "modules/affair/affairTousujubaoguanliFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTousujubaoguanli:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTousujubaoguanliService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
/*	@RequiresPermissions("affair:affairTousujubaoguanli:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairTousujubaoguanli affairTousujubaoguanli, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairTousujubaoguanli.setHasAuth(true);
		Page<AffairTousujubaoguanli> page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response), affairTousujubaoguanli);
		model.addAttribute("page", page);
		return "modules/affair/affairTousujubaoguanliManage";
	}
   //这是用来跳转DiaLog页面的

	@RequiresPermissions("affair:affairTousujubaoguanli:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairTousujubaoguanliShenHeDialog";
	}

	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairTousujubaoguanli:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairTousujubaoguanli affairTousujubaoguanli) {
		affairTousujubaoguanliService.shenHe(affairTousujubaoguanli);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}*/

	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairTousujubaoguanli affairTousujubaoguanli, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if ("6".equals(UserUtils.getUser().getOffice().getId())){
				affairTousujubaoguanli.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairTousujubaoguanli.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairTousujubaoguanli.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairTousujubaoguanli> page = null;
			String reason = request.getParameter("reason");//数据范围 0 空  1 其他年份  2 全部
			if("1".equals(reason)){
				affairTousujubaoguanli.setStartDate(null);
				affairTousujubaoguanli.setEndDate(null);
			}else if("2".equals(reason)){
				//全部
				if(affairTousujubaoguanli.getStartDate()==null && affairTousujubaoguanli.getEndDate() == null){
					Integer year = Integer.parseInt(DateUtils.getYear());//获取当前年
					affairTousujubaoguanli.setYear(year);
				}else{
					affairTousujubaoguanli.setYear(null);
				}
			}else{
				//默认显示当前年度数据
				Integer year = Integer.parseInt(DateUtils.getYear());//获取当前年
				affairTousujubaoguanli.setYear(year);
				affairTousujubaoguanli.setStartDate(null);
				affairTousujubaoguanli.setEndDate(null);
			}
			if(flag == true){
				page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response), affairTousujubaoguanli);

			}else{
				page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response,-1), affairTousujubaoguanli);
			}
			/*if(flag == true){
				if (affairTousujubaoguanli.getStartDate() != null || affairTousujubaoguanli.getEndDate() != null){
					page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response), affairTousujubaoguanli);
				}else if (affairTousujubaoguanli.getStartDate() == null && affairTousujubaoguanli.getEndDate() == null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
					Date date = new Date();
					String nowDate = sdf.format(date);
					Date yearDate = null;
					try {
						yearDate = sdf.parse(nowDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					affairTousujubaoguanli.setStartYear(yearDate);
					page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response), affairTousujubaoguanli);
				}
			}else {
				if (affairTousujubaoguanli.getStartDate() != null || affairTousujubaoguanli.getEndDate() != null){
					page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response,-1), affairTousujubaoguanli);
				}else if (affairTousujubaoguanli.getStartDate() == null && affairTousujubaoguanli.getEndDate() == null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
					Date date = new Date();
					String nowDate = sdf.format(date);
					Date yearDate = null;
					try {
						yearDate = sdf.parse(nowDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					affairTousujubaoguanli.setStartYear(yearDate);
					page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response,-1), affairTousujubaoguanli);
				}
			}*/
/*
			Page<AffairTousujubaoguanli> page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response,-1), affairTousujubaoguanli);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTousujubaoguanli.class);
			exportExcelNew.setWb(wb);
			List<AffairTousujubaoguanli> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTousujubaoguanli?repage";
	}

	/**
	 * 投诉举报问题归类页面
	 * @param affairTousujubaoguanli
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTousujubaoguanli:view")
	@RequestMapping(value = {"statistic"})
	public String statistic(AffairTousujubaoguanli affairTousujubaoguanli, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/affair/affairTousujubaoguanliStatistic";
	}

/*	//这是用来跳转办dialog页面的
	@RequiresPermissions("affair:affairTousujubaoguanli:turn")
	@RequestMapping(value = {"zhuanbanDialog"})
	public String zhuanbanDialog() {
		return "modules/affair/affairTousujubaoguanliZhuanban";
	}
	//这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairTousujubaoguanli:edit")
	@RequestMapping(value = "zhuanban")
	public Result zhuanban(AffairTousujubaoguanli affairTousujubaoguanli, String zbUnitId, String zbUnit) {
		zbUnit = affairTousujubaoguanli.getZbUnit();
		zbUnitId = affairTousujubaoguanli.getZbUnitId();
		affairTousujubaoguanliService.zhuanban(affairTousujubaoguanli, zbUnitId, zbUnit);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("转办成功");
		return result;
	}*/

	/**
	 * 办结功能（需求：点击办结按钮后公安处层面将再无法修改，只有公安局纪检监察管理员才能修改）
	 * @param affairTousujubaoguanli
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairTousujubaoguanli:settle")
	@RequestMapping(value = "banJie")
	public String banJie(AffairTousujubaoguanli affairTousujubaoguanli, RedirectAttributes redirectAttributes) {
		affairTousujubaoguanliService.banJie(affairTousujubaoguanli);
		addMessage(redirectAttributes, "办结投诉举报成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTousujubaoguanli/?repage";
	}

	@RequiresPermissions("affair:affairTousujubaoguanli:manage")
	@RequestMapping(value = "manage")
	public String manageList(AffairTousujubaoguanli affairTousujubaoguanli, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTousujubaoguanli> page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response), affairTousujubaoguanli);
		model.addAttribute("page", page);
		return "modules/affair/affairTousujubaoguanliList";
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
		List <AffairTousujubaoguanli> list = affairTousujubaoguanliService.findByIds(userList);
		for (AffairTousujubaoguanli affairTousujubaoguanli: list){
			affairTousujubaoguanli.setZbStatus("1");
			affairTousujubaoguanli.setChuCheckMan(chuCheckMan);
			affairTousujubaoguanli.setChuCheckId(chuCheckId);
			affairTousujubaoguanli.setSubmitId(user.getId());
			affairTousujubaoguanli.setSubmitMan(user.getName());
			affairTousujubaoguanliService.save(affairTousujubaoguanli);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairTousujubaoguanli/list";
	}

	@RequestMapping(value = {"findIsCheckInfoDetail"})
	public String findIsCheckInfoDetail(AffairTousujubaoguanli affairTousujubaoguanli, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairTousujubaoguanli> page;
		if ("4".equals(affairTousujubaoguanli.getCheckType())){
			affairTousujubaoguanli.setCheckType("3");
			page = affairTousujubaoguanliService.findIsCheckInfoDetail(new Page<AffairTousujubaoguanli>(request,response), affairTousujubaoguanli);
		}else {
			page = affairTousujubaoguanliService.findBjTypeInfoDetail(new Page<AffairTousujubaoguanli>(request,response), affairTousujubaoguanli);
		}
		model.addAttribute("page", page);
		model.addAttribute("affairPartyMember", affairTousujubaoguanli);
		return "modules/charts/monitorTouSuDetail";
	}
	/*@RequestMapping(value = {"findBjTypeInfoDetail"})
	public String findBjTypeInfoDetail(AffairTousujubaoguanli affairTousujubaoguanli, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairTousujubaoguanli> page = affairTousujubaoguanliService.findBjTypeInfoDetail(new Page<AffairTousujubaoguanli>(request,response), affairTousujubaoguanli);
		model.addAttribute("page", page);
		model.addAttribute("affairPartyMember", affairTousujubaoguanli);
		return "modules/charts/monitorTouSuDetail";
	}*/

	@RequestMapping(value = {"findQuestionTypeInfoDetail"})
	public String findQuestionTypeInfoDetail(AffairTousujubaoguanli affairTousujubaoguanli, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairTousujubaoguanli> page;
		if ("1".equals(affairTousujubaoguanli.getCheckType())){
			page = affairTousujubaoguanliService.findZjInfoDetail(new Page<AffairTousujubaoguanli>(request,response), affairTousujubaoguanli);
		}else if ("2".equals(affairTousujubaoguanli.getCheckType())) {
			page = affairTousujubaoguanliService.findSfInfoDetail(new Page<AffairTousujubaoguanli>(request,response), affairTousujubaoguanli);
		}else {
			page = affairTousujubaoguanliService.findJjInfoDetail(new Page<AffairTousujubaoguanli>(request,response), affairTousujubaoguanli);
		}
		model.addAttribute("page", page);
		model.addAttribute("affairPartyMember", affairTousujubaoguanli);
		return "modules/charts/monitorTouSuDetail";
	}

	/**
	 * 导入
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
			List<AffairTousujubaoguanli> list = ei.getDataList(AffairTousujubaoguanli.class);
			for (AffairTousujubaoguanli affairTousujubaoguanli : list){
				try{
					affairTousujubaoguanli.setInformerUnitId(officeService.findByName(affairTousujubaoguanli.getInformerUnitId()));
					affairTousujubaoguanli.setRepoterUnitId(officeService.findByName(affairTousujubaoguanli.getRepoterUnitId()));
					affairTousujubaoguanli.setZbUnitId(officeService.findByName(affairTousujubaoguanli.getZbUnitId()));
					BeanValidators.validateWithException(validator, affairTousujubaoguanli);
					affairTousujubaoguanliService.save(affairTousujubaoguanli);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("导入失败："+ex.getMessage());
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

}