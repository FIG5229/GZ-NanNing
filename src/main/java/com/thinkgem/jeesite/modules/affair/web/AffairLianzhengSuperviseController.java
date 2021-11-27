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
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairLianzhengSupervise;
import com.thinkgem.jeesite.modules.affair.service.AffairLianzhengSuperviseService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 廉政监督Controller
 * @author cecil.li
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLianzhengSupervise")
public class AffairLianzhengSuperviseController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairLianzhengSuperviseService affairLianzhengSuperviseService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairLianzhengSupervise get(@RequestParam(required=false) String id) {
		AffairLianzhengSupervise entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLianzhengSuperviseService.get(id);
		}
		if (entity == null){
			entity = new AffairLianzhengSupervise();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairLianzhengSupervise:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLianzhengSupervise affairLianzhengSupervise, HttpServletRequest request, HttpServletResponse response, Model model) {

		if ("6".equals(UserUtils.getUser().getOffice().getId())){
			affairLianzhengSupervise.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairLianzhengSupervise.setUnitId(UserUtils.getUser().getOffice().getId());
		}

		affairLianzhengSupervise.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairLianzhengSupervise.setStartDate(null);
			affairLianzhengSupervise.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairLianzhengSupervise.getStartDate()==null && affairLianzhengSupervise.getEndDate() == null){
				String year = DateUtils.getYear();//获取当前年
				affairLianzhengSupervise.setOtherYear(year);
			}else{
				affairLianzhengSupervise.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairLianzhengSupervise.setOtherYear(year);
			affairLianzhengSupervise.setStartDate(null);
			affairLianzhengSupervise.setEndDate(null);
		}
		Page<AffairLianzhengSupervise> page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
		model.addAttribute("page", page);
		/*if (affairLianzhengSupervise.getStartDate() != null || affairLianzhengSupervise.getEndDate() != null){
			Page<AffairLianzhengSupervise> page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
			model.addAttribute("page", page);
		}else if (affairLianzhengSupervise.getStartDate() == null && affairLianzhengSupervise.getEndDate() == null){
			if ("".equals(affairLianzhengSupervise.getOtherYear()) || affairLianzhengSupervise.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairLianzhengSupervise.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairLianzhengSupervise> page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
				model.addAttribute("page", page);
			}

		}else if (affairLianzhengSupervise.getOtherYear() != null && !"".equals(affairLianzhengSupervise.getOtherYear())){
			Page<AffairLianzhengSupervise> page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
			model.addAttribute("page", page);
		}*/

		return "modules/affair/affairLianzhengSuperviseList";
	}

	@RequiresPermissions("affair:affairLianzhengSupervise:add")
	@RequestMapping(value = "form")
	public String form(AffairLianzhengSupervise affairLianzhengSupervise, Model model) {
		model.addAttribute("affairLianzhengSupervise", affairLianzhengSupervise);
		return "modules/affair/affairLianzhengSuperviseForm";
	}

	@RequiresPermissions("affair:affairLianzhengSupervise:edit")
	@RequestMapping(value = "save")
	public String save(AffairLianzhengSupervise affairLianzhengSupervise, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairLianzhengSupervise)){
			return form(affairLianzhengSupervise, model);
		}
		affairLianzhengSuperviseService.save(affairLianzhengSupervise);
		addMessage(redirectAttributes, "保存廉政监督成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairLianzhengSuperviseForm";
	}
	
	@RequiresPermissions("affair:affairLianzhengSupervise:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairLianzhengSupervise affairLianzhengSupervise, RedirectAttributes redirectAttributes) {
		affairLianzhengSuperviseService.delete(affairLianzhengSupervise);
		addMessage(redirectAttributes, "删除廉政监督成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLianzhengSupervise/?repage";
	}

	/**
	 * 详情
	 * @param affairLianzhengSupervise
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairLianzhengSupervise:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLianzhengSupervise affairLianzhengSupervise, Model model) {
		model.addAttribute("affairLianzhengSupervise", affairLianzhengSupervise);
		if(affairLianzhengSupervise.getAnnex() != null && affairLianzhengSupervise.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairLianzhengSupervise.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairLianzhengSuperviseFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairLianzhengSupervise:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLianzhengSuperviseService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairLianzhengSupervise affairLianzhengSupervise, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if ("6".equals(UserUtils.getUser().getOffice().getId())){
				affairLianzhengSupervise.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairLianzhengSupervise.setUnitId(UserUtils.getUser().getOffice().getId());
			}

			affairLianzhengSupervise.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairLianzhengSupervise> page = null;
			String reason = request.getParameter("reason");//2 全部 3 其他年份
			if("3".equals(reason)){
				affairLianzhengSupervise.setStartDate(null);
				affairLianzhengSupervise.setEndDate(null);
			}else if("2".equals(reason)){
				//全部
				if(affairLianzhengSupervise.getStartDate()==null && affairLianzhengSupervise.getEndDate() == null){
					String year = DateUtils.getYear();//获取当前年
					affairLianzhengSupervise.setOtherYear(year);
				}else{
					affairLianzhengSupervise.setOtherYear(null);
				}
			}else{
				//默认显示当前年度数据
				String year =DateUtils.getYear();//获取当前年
				affairLianzhengSupervise.setOtherYear(year);
				affairLianzhengSupervise.setStartDate(null);
				affairLianzhengSupervise.setEndDate(null);
			}
			if(flag == true){
				page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
			}else{
				page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response,-1), affairLianzhengSupervise);
			}
			/*if(flag == true){
				if (affairLianzhengSupervise.getStartDate() != null || affairLianzhengSupervise.getEndDate() != null){
					page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
				}else if (affairLianzhengSupervise.getStartDate() == null && affairLianzhengSupervise.getEndDate() == null){
					if ("".equals(affairLianzhengSupervise.getOtherYear()) || affairLianzhengSupervise.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairLianzhengSupervise.setStartYear(yearDate);
						 page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
					}

				}else if (affairLianzhengSupervise.getOtherYear() != null && !"".equals(affairLianzhengSupervise.getOtherYear())){
					page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
				}
			}else {
				if (affairLianzhengSupervise.getStartDate() != null || affairLianzhengSupervise.getEndDate() != null){
					page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response,-1), affairLianzhengSupervise);
				}else if (affairLianzhengSupervise.getStartDate() == null && affairLianzhengSupervise.getEndDate() == null){
					if ("".equals(affairLianzhengSupervise.getOtherYear()) || affairLianzhengSupervise.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairLianzhengSupervise.setStartYear(yearDate);
						page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response,-1), affairLianzhengSupervise);
					}

				}else if (affairLianzhengSupervise.getOtherYear() != null && !"".equals(affairLianzhengSupervise.getOtherYear())){
					page = affairLianzhengSuperviseService.findPage(new Page<AffairLianzhengSupervise>(request, response,-1), affairLianzhengSupervise);
				}
			}*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairLianzhengSupervise.class);
			exportExcelNew.setWb(wb);
			List<AffairLianzhengSupervise> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairDcwtLibrary?repage";
	}

	@RequiresPermissions("affair:affairLianzhengSupervise:add")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairLianzhengSupervise> list = ei.getDataList(AffairLianzhengSupervise.class);
			for (AffairLianzhengSupervise affairLianzhengSupervise : list){
				try{
					affairLianzhengSupervise.setZbUnitId(officeService.findByName(affairLianzhengSupervise.getZbUnit()));
					BeanValidators.validateWithException(validator, affairLianzhengSupervise);
					affairLianzhengSuperviseService.save(affairLianzhengSupervise);
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

	@RequestMapping(value = {"findDetailInfo"})
	public String findDetailInfo(AffairLianzhengSupervise affairLianzhengSupervise, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairLianzhengSupervise> page;
		page = affairLianzhengSuperviseService.findDetailInfo(new Page<AffairLianzhengSupervise>(request, response), affairLianzhengSupervise);
		model.addAttribute("page", page);
		model.addAttribute("affairLianzhengSupervise", affairLianzhengSupervise);
		return "modules/charts/monitorJdFirstDetail";
	}

}