/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairCreateWork;
import com.thinkgem.jeesite.modules.affair.service.AffairCreateWorkService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 创建工作Controller
 * @author daniel.liu
 * @version 2020-07-06
 * 改为台账形式 单标crud
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCreateWork")
public class AffairCreateWorkController extends BaseController {

	@Autowired
	private AffairCreateWorkService affairCreateWorkService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairCreateWork get(@RequestParam(required=false) String id) {
		AffairCreateWork entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCreateWorkService.get(id);
		}
		if (entity == null){
			entity = new AffairCreateWork();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCreateWork:view")
	@RequestMapping(value = {""})
	public String index(AffairCreateWork affairCreateWork, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*改为台账形式*/
		/*		String id = UserUtils.getUser().getId();
		//id依次为南宁局宣传教育管理  南宁局政治部宣教
		if (id.equals("66937439b2124f328d1521968fab06db") || id.equals("8a6819768aef40968e8f289842613276") || id.equals("d154234ecb35470e84fb95e53726866b") ||
				id.equals("d30e324c8f73492d9b74103374fbc689") || id.equals("e3ac8381fb3247e0b64fd6e3c48bddc1")) {
			//未上报不能查看
//			affairCreateWork.setStatus("1");
			//数据过滤使用 查看公司及以下数据
			affairCreateWork.setHasAuth(true);
			String idN = UserUtils.getUser().getId();
			if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
				affairCreateWork.setClassify("3");
			}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
				affairCreateWork.setClassify("2");
			}else{
				affairCreateWork.setClassify("1");
			}*/
			/*Page<AffairCreateWork> page = affairCreateWorkService.findPage(new Page<AffairCreateWork>(request, response), affairCreateWork);
			model.addAttribute("page", page);
			model.addAttribute("userId", id);
			return "modules/affair/affairCreateWorkSignList";
		} else {
			String idN = UserUtils.getUser().getId();
			if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
				affairCreateWork.setClassify("3");
			}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
				affairCreateWork.setClassify("2");
			}else{
				affairCreateWork.setClassify("1");
			}*/
			Page<AffairCreateWork> page = affairCreateWorkService.findPage(new Page<AffairCreateWork>(request, response), affairCreateWork);
			model.addAttribute("page", page);
//			model.addAttribute("userId", id);
			return "modules/affair/affairCreateWorkList";
//		}
	}

	//上报
	@RequiresPermissions("affair:affairCreateWork:view")
	@RequestMapping(value = {"list"})
	public String list(AffairCreateWork affairCreateWork, HttpServletRequest request, HttpServletResponse response, Model model) {
	/*	String idN = UserUtils.getUser().getId();
		if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
			affairCreateWork.setClassify("3");
		}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
			affairCreateWork.setClassify("2");
		}else{
			affairCreateWork.setClassify("1");
		}*/
		Page<AffairCreateWork> page = affairCreateWorkService.findPage(new Page<AffairCreateWork>(request, response), affairCreateWork);
		model.addAttribute("page", page);
	/*	String id=UserUtils.getUser().getId();
		model.addAttribute("userId", id);*/
		return "modules/affair/affairCreateWorkList";
	}

	//签收
	@RequiresPermissions("affair:affairCreateWork:view")
	@RequestMapping(value = {"sign"})
	public String sign(AffairCreateWork affairCreateWork, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*不能查询未上报的数据*/
//		affairCreateWork.setStatus("1");
	/*	affairCreateWork.setHasAuth(true);
		String idN = UserUtils.getUser().getId();
		if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
			affairCreateWork.setClassify("3");
		}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
			affairCreateWork.setClassify("2");
		}else{
			affairCreateWork.setClassify("1");
		}*/
		Page<AffairCreateWork> page = affairCreateWorkService.findPage(new Page<AffairCreateWork>(request, response), affairCreateWork);
		//不能签收自己上报的数据 页面中设置
		model.addAttribute("page", page);
		String id=UserUtils.getUser().getId();
		model.addAttribute("userId", id);
		return "modules/affair/affairCreateWorkSignList";
	}

	@RequiresPermissions("affair:affairCreateWork:view")
	@RequestMapping(value = "form")
	public String form(AffairCreateWork affairCreateWork, Model model) {
		model.addAttribute("affairCreateWork", affairCreateWork);
		return "modules/affair/affairCreateWorkForm";
	}
	@RequiresPermissions("affair:affairCreateWork:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCreateWork affairCreateWork, Model model) {
		model.addAttribute("affairCreateWork", affairCreateWork);
		if (affairCreateWork.getFilePath() != null && affairCreateWork.getFilePath().length() > 0) {
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairCreateWork.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairCreateWorkFormDetail";
	}

	@RequiresPermissions("affair:affairCreateWork:edit")
	@RequestMapping(value = "save")
	public String save(AffairCreateWork affairCreateWork, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairCreateWork)){
			return form(affairCreateWork, model);
		}
		//保存时 状态为空 则未提交
		if (StringUtils.isEmpty(affairCreateWork.getStatus())){
			affairCreateWork.setStatus("1");
		}
		String isSign = request.getParameter("sign");
		//审核时 保存审核人id和姓名
		if (!StringUtils.isEmpty(isSign)){
			affairCreateWork.setCheckMan(UserUtils.getUser().getName());
			affairCreateWork.setCheckId(UserUtils.getUser().getId());
		}

		/*String idN = UserUtils.getUser().getId();
		if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
			affairCreateWork.setClassify("3");
		}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
			affairCreateWork.setClassify("2");
		}else{
			affairCreateWork.setClassify("1");
		}*/

		affairCreateWorkService.save(affairCreateWork);
		addMessage(redirectAttributes, "保存创建工作成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairCreateWorkForm";
	}
	@RequiresPermissions("affair:affairCreateWork:edit")
	@RequestMapping(value = "report")
	public String report(AffairCreateWork affairCreateWork, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairCreateWork)){
			return form(affairCreateWork, model);
		}
		//进行上报
		//更改状态
		affairCreateWork.setStatus("2");
		//保存提交人姓名和提交人Id
		affairCreateWork.setSubmitMan(UserUtils.getUser().getName());
		affairCreateWork.setSubmitId(UserUtils.getUser().getId());
		affairCreateWork.setReportTime(new Date());
		affairCreateWorkService.save(affairCreateWork);
		addMessage(redirectAttributes, "保存创建工作成功");
		request.setAttribute("saveResult","success");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCreateWork/list?repage";
//		return "modules/affair/affairCreateWorkForm";
	}
	@RequiresPermissions("affair:affairCreateWork:edit")
	@RequestMapping(value = "manage")
	public String manage(AffairCreateWork affairCreateWork, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		model.addAttribute("affairCreateWork", affairCreateWork);
		return "modules/affair/affairCreateWorkSign";
	}
	
	@RequiresPermissions("affair:affairCreateWork:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCreateWork affairCreateWork, RedirectAttributes redirectAttributes) {
		affairCreateWorkService.delete(affairCreateWork);
		addMessage(redirectAttributes, "删除创建工作成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCreateWork/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCreateWork:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCreateWorkService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairCreateWork affairCreateWork, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairCreateWork> page = null;
			if(flag == true){
				page = affairCreateWorkService.findPage(new Page<AffairCreateWork>(request, response), affairCreateWork);
			}else {
				page = affairCreateWorkService.findPage(new Page<AffairCreateWork>(request, response,-1), affairCreateWork);
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
			//修改
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairCreateWork.class);
			exportExcelNew.setWb(wb);
			List<AffairCreateWork> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairCreateWork/?id="+UserUtils.getUser().getId();
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairCreateWork> list = ei.getDataList(AffairCreateWork.class);
			for (AffairCreateWork helpEducate : list){
				try{
					//单位绑定单位id
					/*String orgId = officeService.findByName(helpEducate.getUnit());
					if(orgId != null){
						helpEducate.setUnitId(orgId);
					}*/
					BeanValidators.validateWithException(validator, helpEducate);
					/*String idN = UserUtils.getUser().getId();
					if (idN.equals("66937439b2124f328d1521968fab06db") || idN.equals("8a6819768aef40968e8f289842613276")){
						helpEducate.setClassify("3");
					}else if (idN.equals("e3ac8381fb3247e0b64fd6e3c48bddc1") || idN.equals("d30e324c8f73492d9b74103374fbc689") || idN.equals("d154234ecb35470e84fb95e53726866b")){
						helpEducate.setClassify("2");
					}else{
						helpEducate.setClassify("1");
					}*/
					affairCreateWorkService.save(helpEducate);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(更新者:"+helpEducate.getUpdateBy().getLoginName()+")"+" 导入失败："+ex.getMessage());
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