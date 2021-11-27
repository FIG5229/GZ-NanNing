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
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceThoughtAnalysis;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliceThoughtAnalysisService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 局宣教
 		公安局党委（自己保存）	局机关党委（查看局机关上报数据） 公安处党委（查看三个处上报） 局机关党支部（查看）
 处宣教
 		公安处党委（三个处上报） 基层党支部（查看三个处上报）
 基层
 		基层党支部（基层进行上报）
 局机关党委
 		局机关党委（局机关进行上报）
 局机关党支部
 		局机关党支部 进行上报

 处党委		三个处宣教账号   共六个
 局党委		局宣教账号
 局机关党委  局宣教账号
 局机关党支部	局机关账号
 基层党支部

 每个页面的classify
	 公安局党委	5
	 局机关党委	4
	 公安处党委	3
	 局机关支部	2
	 基层党支部	1
 * @author daniel.liu
 * @version 2020-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPoliceThoughtAnalysis")
public class AffairPoliceThoughtAnalysisController extends BaseController {

	@Autowired
	private AffairPoliceThoughtAnalysisService affairPoliceThoughtAnalysisService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public AffairPoliceThoughtAnalysis get(@RequestParam(required=false) String id) {
		AffairPoliceThoughtAnalysis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPoliceThoughtAnalysisService.get(id);
		}
		if (entity == null){
			entity = new AffairPoliceThoughtAnalysis();
		}
		return entity;
	}

	//普通账号进行添加 上报
	@RequiresPermissions("affair:affairIdeology:view")
	@RequestMapping(value = {""})
	public String index(AffairPoliceThoughtAnalysis analysis, HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = UserUtils.getUser().getId();
		//id依次为南宁局宣传思想管理  南宁局政治部宣传教育处  柳州处政治处宣传教育室（民警训练基地）  南宁处政治处宣传教育室（民警训练基地）  北海处政治处宣传教育室（民警训练基地）
		if (id.equals("66937439b2124f328d1521968fab06db") || id.equals("8a6819768aef40968e8f289842613276") || id.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") ||
				id.equals("d30e324c8f73492d9b74103374fbc689") || id.equals("d154234ecb35470e84fb95e53726866b")) {
			//9.2 问题反馈， 未上报可查看  不能签收
//			analysis.setReportType("1");
			analysis.setSign(true);
			String idN = UserUtils.getUser().getId();
			if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
				analysis.setClassify("3");
			}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
				analysis.setClassify("2");
			}else{
				analysis.setClassify("1");
			}
			Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), analysis);
	/*		for (int i=0;i<page.getList().size();i++) {
				String userId=UserUtils.getUser().getId();
				if (page.getList().get(i).getCreateBy().getId().equals(userId)){
					page.getList().remove(i);
				}
			}*/
			model.addAttribute("page", page);
			model.addAttribute("userId", id);
			return "modules/affair/affairPoliceThoughtAnalysisList";
		} else {
			String idN = UserUtils.getUser().getId();
			if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
				analysis.setClassify("3");
			}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
				analysis.setClassify("2");
			}else{
				analysis.setClassify("1");
			}
			Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), analysis);
			model.addAttribute("page", page);
			model.addAttribute("userId", id);
			return "modules/affair/affairPoliceThoughtAnalysisListManage";
		}
//		Page<AffairIdeology> page = affairIdeologyService.findPage(new Page<AffairIdeology>(request, response), affairIdeology);
//		model.addAttribute("page", page);
//		return "modules/affair/affairIdeologyList";
	}

	/**
	 * 请求数据列表
	 * 上级查看下级报送的数据页面
	 * @param affairPoliceThoughtAnalysis
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = {"list"})
	public String list(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {

		String userId = UserUtils.getUser().getId();
		String companyId = UserUtils.getUser().getCompany().getId();
		String officeId = UserUtils.getUser().getOffice().getId();
		String targetUrl = "modules/affair/affairPoliceThoughtAnalysisList";
		if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("南宁局政治部宣传教育处")){
			/*公安局党委页面	*/
			targetUrl = "modules/affair/affairPoliceThoughtAnalysisju";
			affairPoliceThoughtAnalysis.setClassify("5");
		}else if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("8a6819768aef40968e8f289842613276")/*||
				userId.equals("d154234ecb35470e84fb95e53726866b") || userId.equals("a4e40825e3b34c37895ee2ed168f24a0") ||
				userId.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || userId.equals("82f8e9ba1cd7446b99c084d5b4b5c038")||
				userId.equals("94ccb0bdb21441f997c72268b4d882db") || userId.equals("d30e324c8f73492d9b74103374fbc689")*/){
			/*局机关党委页面*/
			affairPoliceThoughtAnalysis.setClassify("4");
			targetUrl = "modules/affair/affairPoliceThoughtAnalysisjujiguan";
		}else if (userId.equals("d154234ecb35470e84fb95e53726866b") || userId.equals("南宁处政治处宣传教育室（民警训练基地）") ||
				userId.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || userId.equals("北海处政治处宣传教育室（民警训练基地）")||
				userId.equals("柳州处政治处宣传教育室（民警训练基地）") || userId.equals("d30e324c8f73492d9b74103374fbc689")){
			/*公安处党委页面*/
			affairPoliceThoughtAnalysis.setClassify("3");
			targetUrl = "modules/affair/affairPoliceThoughtAnalysischu";
		}else if (companyId.equals("1") && !officeId.equals("34")&& !officeId.equals("95")&& !officeId.equals("156")){
			/*局机关党支部页面*/
			affairPoliceThoughtAnalysis.setClassify("2");
			targetUrl = "modules/affair/affairPoliceThoughtAnalysisjuzhibu";
		}else {
			/*基层党支部页面*/
			affairPoliceThoughtAnalysis.setClassify("1");
			targetUrl = "modules/affair/affairPoliceThoughtAnalysisjiceng";
		}
		Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), affairPoliceThoughtAnalysis);

		model.addAttribute("page", page);
		String id=UserUtils.getUser().getId();
		model.addAttribute("userId", id);

		return targetUrl;
	}

	/**
	 * 请求数据列表
	 * 上级查看下级报送的数据页面
	 * @param affairPoliceThoughtAnalysis
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = {"ju"})
	public String judangwei(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairPoliceThoughtAnalysis.setClassify("5");
		Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), affairPoliceThoughtAnalysis);
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceThoughtAnalysisju";
	}
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = {"jujiguan"})
	public String jujiguandangwei(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairPoliceThoughtAnalysis.setClassify("4");
		Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), affairPoliceThoughtAnalysis);
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceThoughtAnalysisjujiguan";
	}
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = {"chu"})
	public String chudangwei(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairPoliceThoughtAnalysis.setClassify("3");
		/*已在findPage 中处理*/
	/*	String userId = UserUtils.getUser().getId();
		if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("8a6819768aef40968e8f289842613276")){
			*//*局查看时 查看已经上报的	*//*
			affairPoliceThoughtAnalysis.setReportStatus("2");
		}*/
		Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), affairPoliceThoughtAnalysis);
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceThoughtAnalysischu";
	}
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = {"jiguanzhibu"})
	public String juzhibu(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairPoliceThoughtAnalysis.setClassify("2");
		/*已在findPage 中处理*/
/*		String userId = UserUtils.getUser().getId();
		if (userId.equals("66937439b2124f328d1521968fab06db") || userId.equals("8a6819768aef40968e8f289842613276")){
			*//*局查看时 查看已经上报的	*//*
			affairPoliceThoughtAnalysis.setReportStatus("2");
		}*/
		Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), affairPoliceThoughtAnalysis);
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceThoughtAnalysisjuzhibu";
	}
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = {"jiceng"})
	public String jiceng(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairPoliceThoughtAnalysis.setClassify("1");
		/*已在findPage 中处理*/
	/*	String userId = UserUtils.getUser().getId();
		if (userId.equals("d154234ecb35470e84fb95e53726866b") || userId.equals("a4e40825e3b34c37895ee2ed168f24a0") ||
				userId.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || userId.equals("82f8e9ba1cd7446b99c084d5b4b5c038")||
				userId.equals("94ccb0bdb21441f997c72268b4d882db") || userId.equals("d30e324c8f73492d9b74103374fbc689")){
			*//*公安处查看基层时，只查看已上报数据*//*
			affairPoliceThoughtAnalysis.setReportStatus("2");
		}*/
		Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), affairPoliceThoughtAnalysis);
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceThoughtAnalysisjiceng";
	}

	/**
	 * 管理员请求数据列表
	 * 可新增数据进行上报
	 * @param affairPoliceThoughtAnalysis
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = "manage")
	public String manageList(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {



		String idN = UserUtils.getUser().getId();
		if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
			affairPoliceThoughtAnalysis.setClassify("3");
		}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
			affairPoliceThoughtAnalysis.setClassify("2");
		}else{
			affairPoliceThoughtAnalysis.setClassify("1");
		}
		Page<AffairPoliceThoughtAnalysis> page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), affairPoliceThoughtAnalysis);
		model.addAttribute("page", page);
		String id=UserUtils.getUser().getId();
		model.addAttribute("userId", id);
		return "modules/affair/affairPoliceThoughtAnalysisListManage";
	}

	/**
	 * 添加 修改数据页面
	 * @param affairPoliceThoughtAnalysis
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = "form")
	public String form(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, Model model) {
		model.addAttribute("affairPoliceThoughtAnalysis", affairPoliceThoughtAnalysis);
		return "modules/affair/affairPoliceThoughtAnalysisForm";
	}

	/**
	 *
	 * @param affairPoliceThoughtAnalysis
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = "jiCengForm")
	public String jiCengForm(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, Model model) {
		model.addAttribute("affairPoliceThoughtAnalysis", affairPoliceThoughtAnalysis);
		return "modules/affair/affairPoliceThoughtAnalysisFormJiCeng";
	}
	/**
	 * 基层党支部查看详情
	 * @param affairPoliceThoughtAnalysis
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = "jiCengformDetail")
	public String jiCengformDetail(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, Model model) {
		affairPoliceThoughtAnalysis.setContent(StringEscapeUtils.unescapeHtml4(affairPoliceThoughtAnalysis.getContent()));
		model.addAttribute("affairPoliceThoughtAnalysis", affairPoliceThoughtAnalysis);
		if(affairPoliceThoughtAnalysis.getFiles() != null && affairPoliceThoughtAnalysis.getFiles().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPoliceThoughtAnalysis.getFiles());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPoliceThoughtAnalysisFormDetailJiCeng";
	}



	/**
	 * 查看详情
	 * @param affairPoliceThoughtAnalysis
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, Model model) {
		affairPoliceThoughtAnalysis.setContent(StringEscapeUtils.unescapeHtml4(affairPoliceThoughtAnalysis.getContent()));
		model.addAttribute("affairPoliceThoughtAnalysis", affairPoliceThoughtAnalysis);
		if(affairPoliceThoughtAnalysis.getFiles() != null && affairPoliceThoughtAnalysis.getFiles().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPoliceThoughtAnalysis.getFiles());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPoliceThoughtAnalysisFormDetail";
	}

	/**
	 * 保存数据
	 * @param affairPoliceThoughtAnalysis
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:edit")
	@RequestMapping(value = "save")
	public String save(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPoliceThoughtAnalysis)){
			return form(affairPoliceThoughtAnalysis, model);
		}
		/*if (StringUtils.isEmpty(affairPoliceThoughtAnalysis.getReportStatus())){
			affairPoliceThoughtAnalysis.setReportStatus("1");
		}

		String idN = UserUtils.getUser().getId();
		String id = UserUtils.getUser().getOffice().getId();
		if (id.equals("1") && !id.equals("34") && !id.equals("95") && !id.equals("156")){
			affairPoliceThoughtAnalysis.setClassify("3");
		}
		*//*if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
			affairPoliceThoughtAnalysis.setClassify("3");
		}*//*else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
			affairPoliceThoughtAnalysis.setClassify("2");
		}else{
			affairPoliceThoughtAnalysis.setClassify("1");
		}*/
		affairPoliceThoughtAnalysisService.save(affairPoliceThoughtAnalysis);
		addMessage(redirectAttributes, "保存民警思想动态分析成功");

		request.setAttribute("saveResult","sucess");

		return "modules/affair/affairPoliceThoughtAnalysisForm";

	}

	/**
	 * 删除数据
	 * @param affairPoliceThoughtAnalysis
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, RedirectAttributes redirectAttributes) {
		affairPoliceThoughtAnalysisService.delete(affairPoliceThoughtAnalysis);
		addMessage(redirectAttributes, "删除民警思想动态分析成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceThoughtAnalysis/list?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPoliceThoughtAnalysisService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:edit")
	@RequestMapping(value = "report")
	public String report(AffairPoliceThoughtAnalysis affairPoliceThoughtAnalysis, RedirectAttributes redirectAttributes) {
		affairPoliceThoughtAnalysis.setReportStatus("2");
		affairPoliceThoughtAnalysis.setReportTime(new Date());
		affairPoliceThoughtAnalysisService.save(affairPoliceThoughtAnalysis);
		addMessage(redirectAttributes, "上报民警思想动态分析成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceThoughtAnalysis/list?repage";
	}

	@RequiresPermissions("affair:affairPoliceThoughtAnalysis:edit")
	@RequestMapping(value = "sign")
	public String sign(AffairPoliceThoughtAnalysis analysis,Model model){
		analysis.setSignInTime(new Date());
		affairPoliceThoughtAnalysisService.save(analysis);
		model.addAttribute("analysis", analysis);
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceThoughtAnalysis/list?repage";
	}


	/**
	 * 导出excel格式数据
	 * @param analysis
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairPoliceThoughtAnalysis analysis, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairPoliceThoughtAnalysis> page = null;
			String id = UserUtils.getUser().getId();
			if(flag == true){
		/*		//id依次为南宁局宣传教育管理  南宁局政治部宣教  南宁出宣教  柳州处宣教  北海处宣教
				if (id.equals("66937439b2124f328d1521968fab06db") || id.equals("8a6819768aef40968e8f289842613276") || id.equals("94ccb0bdb21441f997c72268b4d882db") ||
						id.equals("a4e40825e3b34c37895ee2ed168f24a0") || id.equals("82f8e9ba1cd7446b99c084d5b4b5c038")) {
					//9.2 问题反馈， 未上报可查看  不能签收
//			analysis.setReportType("1");
					analysis.setSign(true);
					page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response), analysis);
				} else {
					page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response), analysis);
				}

			}else{
				if (id.equals("66937439b2124f328d1521968fab06db") || id.equals("8a6819768aef40968e8f289842613276") || id.equals("94ccb0bdb21441f997c72268b4d882db") ||
						id.equals("a4e40825e3b34c37895ee2ed168f24a0") || id.equals("82f8e9ba1cd7446b99c084d5b4b5c038")) {
					//9.2 问题反馈， 未上报可查看  不能签收
//			analysis.setReportType("1");
					analysis.setSign(true);
					page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response), analysis);
				} else {
					page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response), analysis);
				}*/
				page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response), analysis);
			}else {
				page = affairPoliceThoughtAnalysisService.findPage(new Page<AffairPoliceThoughtAnalysis>(request, response,-1), analysis);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPoliceThoughtAnalysis.class);
			exportExcelNew.setWb(wb);
			List<AffairPoliceThoughtAnalysis> list =page.getList();
			/*对富文本编辑的内容进行html标签替换*/
			for (AffairPoliceThoughtAnalysis policeThoughtAnalysis: list ) {
				policeThoughtAnalysis.setContent(delHTMLTag(policeThoughtAnalysis.getContent()));
				if (policeThoughtAnalysis.getClassify().equals("1")){
					policeThoughtAnalysis.setReportType(DictUtils.getDictLabel(policeThoughtAnalysis.getReportType(),"warn_month",policeThoughtAnalysis.getReportType()));
				}else {
					policeThoughtAnalysis.setReportType(DictUtils.getDictLabel(policeThoughtAnalysis.getReportType(),"report_type",policeThoughtAnalysis.getReportType()));
				}
			}
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
		return "redirect:" + adminPath + "/affair/affairPoliceThoughtAnalysis/?repage";
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "importJu", method= RequestMethod.POST)
	public String importJu(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPoliceThoughtAnalysis> list = ei.getDataList(AffairPoliceThoughtAnalysis.class);
			for (AffairPoliceThoughtAnalysis thoughtAnalysis : list){
				try{

					BeanValidators.validateWithException(validator, thoughtAnalysis);
					if (!StringUtils.isEmpty(thoughtAnalysis.getUnit())){
						String unitId = officeService.findByName(thoughtAnalysis.getUnit());
						thoughtAnalysis.setUnitId(unitId);
					}
					thoughtAnalysis.setClassify("5");
					thoughtAnalysis.setReportType(DictUtils.getDictValue(thoughtAnalysis.getReportType(),"report_type",thoughtAnalysis.getReportType()));
					affairPoliceThoughtAnalysisService.save(thoughtAnalysis);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(thoughtAnalysis.getPersonName()+"(专题名称:"+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping(value = "importJiGuanDangWei", method= RequestMethod.POST)
	public String importJiGuanDangWei(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPoliceThoughtAnalysis> list = ei.getDataList(AffairPoliceThoughtAnalysis.class);
			for (AffairPoliceThoughtAnalysis thoughtAnalysis : list){
				try{

					BeanValidators.validateWithException(validator, thoughtAnalysis);
					if (!StringUtils.isEmpty(thoughtAnalysis.getUnit())){
						String unitId = officeService.findByName(thoughtAnalysis.getUnit());
						thoughtAnalysis.setUnitId(unitId);
					}
					thoughtAnalysis.setClassify("4");
					thoughtAnalysis.setReportType(DictUtils.getDictValue(thoughtAnalysis.getReportType(),"report_type",thoughtAnalysis.getReportType()));
					affairPoliceThoughtAnalysisService.save(thoughtAnalysis);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(thoughtAnalysis.getPersonName()+"(专题名称:"+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping(value = "importChu", method= RequestMethod.POST)
	public String importChu(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPoliceThoughtAnalysis> list = ei.getDataList(AffairPoliceThoughtAnalysis.class);
			for (AffairPoliceThoughtAnalysis thoughtAnalysis : list){
				try{

					BeanValidators.validateWithException(validator, thoughtAnalysis);
					if (!StringUtils.isEmpty(thoughtAnalysis.getUnit())){
						String unitId = officeService.findByName(thoughtAnalysis.getUnit());
						thoughtAnalysis.setUnitId(unitId);
					}
					thoughtAnalysis.setClassify("3");
					thoughtAnalysis.setReportType(DictUtils.getDictValue(thoughtAnalysis.getReportType(),"report_type",thoughtAnalysis.getReportType()));
					affairPoliceThoughtAnalysisService.save(thoughtAnalysis);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(thoughtAnalysis.getPersonName()+"(专题名称:"+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping(value = "importJiGuanZhiBu", method= RequestMethod.POST)
	public String importJiGuanZhiBu(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPoliceThoughtAnalysis> list = ei.getDataList(AffairPoliceThoughtAnalysis.class);
			for (AffairPoliceThoughtAnalysis thoughtAnalysis : list){
				try{

					BeanValidators.validateWithException(validator, thoughtAnalysis);
					if (!StringUtils.isEmpty(thoughtAnalysis.getUnit())){
						String unitId = officeService.findByName(thoughtAnalysis.getUnit());
						thoughtAnalysis.setUnitId(unitId);
					}
					thoughtAnalysis.setClassify("2");
					thoughtAnalysis.setReportType(DictUtils.getDictValue(thoughtAnalysis.getReportType(),"report_type",thoughtAnalysis.getReportType()));
					affairPoliceThoughtAnalysisService.save(thoughtAnalysis);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(thoughtAnalysis.getPersonName()+"(专题名称:"+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping(value = "importJiCeng", method= RequestMethod.POST)
	public String importJiCeng(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPoliceThoughtAnalysis> list = ei.getDataList(AffairPoliceThoughtAnalysis.class);
			for (AffairPoliceThoughtAnalysis thoughtAnalysis : list){
				try{

					BeanValidators.validateWithException(validator, thoughtAnalysis);
					if (!StringUtils.isEmpty(thoughtAnalysis.getUnit())){
						String unitId = officeService.findByName(thoughtAnalysis.getUnit());
						thoughtAnalysis.setUnitId(unitId);
					}
					thoughtAnalysis.setClassify("1");
					thoughtAnalysis.setReportType(DictUtils.getDictValue(thoughtAnalysis.getReportType(),"warn_month",thoughtAnalysis.getReportType()));
					affairPoliceThoughtAnalysisService.save(thoughtAnalysis);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(thoughtAnalysis.getPersonName()+"(专题名称:"+")"+" 导入失败："+ex.getMessage());
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
	 * 替换富文本编辑框中的html标签，以便于导出
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr){
		String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
		String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
		String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

		Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
		Matcher m_script=p_script.matcher(htmlStr);
		htmlStr=m_script.replaceAll(""); //过滤script标签

		Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
		Matcher m_style=p_style.matcher(htmlStr);
		htmlStr=m_style.replaceAll(""); //过滤style标签

		Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
		Matcher m_html=p_html.matcher(htmlStr);
		htmlStr=m_html.replaceAll(""); //过滤html标签

		return htmlStr.trim(); //返回文本字符串
	}

}