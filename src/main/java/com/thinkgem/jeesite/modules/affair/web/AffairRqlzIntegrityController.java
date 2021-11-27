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
import com.thinkgem.jeesite.modules.affair.entity.AffairRqlzIntegrity;
import com.thinkgem.jeesite.modules.affair.service.AffairRqlzIntegrityService;
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
 * 任前廉政鉴定Controller
 * @author cecil.li
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairRqlzIntegrity")
public class AffairRqlzIntegrityController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairRqlzIntegrityService affairRqlzIntegrityService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairRqlzIntegrity get(@RequestParam(required=false) String id) {
		AffairRqlzIntegrity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairRqlzIntegrityService.get(id);
		}
		if (entity == null){
			entity = new AffairRqlzIntegrity();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairRqlzIntegrity:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairRqlzIntegrity affairRqlzIntegrity, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("6".equals(UserUtils.getUser().getOffice().getId())){
			affairRqlzIntegrity.setUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairRqlzIntegrity.setUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairRqlzIntegrity.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairRqlzIntegrity.setStartDate(null);
			affairRqlzIntegrity.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairRqlzIntegrity.getStartDate()==null && affairRqlzIntegrity.getEndDate() == null){
				String year = DateUtils.getYear();//获取当前年
				affairRqlzIntegrity.setOtherYear(year);
			}else{
				affairRqlzIntegrity.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairRqlzIntegrity.setOtherYear(year);
			affairRqlzIntegrity.setStartDate(null);
			affairRqlzIntegrity.setEndDate(null);
		}
		Page<AffairRqlzIntegrity> page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
		model.addAttribute("page", page);
		/*if (affairRqlzIntegrity.getStartDate() != null || affairRqlzIntegrity.getEndDate() != null){
			Page<AffairRqlzIntegrity> page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
			model.addAttribute("page", page);
		}else if (affairRqlzIntegrity.getStartDate() == null && affairRqlzIntegrity.getEndDate() == null){
			if ("".equals(affairRqlzIntegrity.getOtherYear()) || affairRqlzIntegrity.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairRqlzIntegrity.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairRqlzIntegrity> page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
				model.addAttribute("page", page);
			}

		}else if (affairRqlzIntegrity.getOtherYear() != null && !"".equals(affairRqlzIntegrity.getOtherYear())){
			Page<AffairRqlzIntegrity> page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
			model.addAttribute("page", page);
		}
*/
		return "modules/affair/affairRqlzIntegrityList";
	}

	@RequiresPermissions("affair:affairRqlzIntegrity:add")
	@RequestMapping(value = "form")
	public String form(AffairRqlzIntegrity affairRqlzIntegrity, Model model) {
		model.addAttribute("affairRqlzIntegrity", affairRqlzIntegrity);
		return "modules/affair/affairRqlzIntegrityForm";
	}

	@RequiresPermissions("affair:affairRqlzIntegrity:edit")
	@RequestMapping(value = "save")
	public String save(AffairRqlzIntegrity affairRqlzIntegrity, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairRqlzIntegrity)){
			return form(affairRqlzIntegrity, model);
		}
		affairRqlzIntegrityService.save(affairRqlzIntegrity);
		addMessage(redirectAttributes, "保存任前廉政鉴定成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairRqlzIntegrityForm";
	}
	
	@RequiresPermissions("affair:affairRqlzIntegrity:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairRqlzIntegrity affairRqlzIntegrity, RedirectAttributes redirectAttributes) {
		affairRqlzIntegrityService.delete(affairRqlzIntegrity);
		addMessage(redirectAttributes, "删除任前廉政鉴定成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairRqlzIntegrity/?repage";
	}

	/**
	 * 详情
	 * @param affairRqlzIntegrity
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairRqlzIntegrity:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairRqlzIntegrity affairRqlzIntegrity, Model model) {
		model.addAttribute("affairRqlzIntegrity", affairRqlzIntegrity);
		if(affairRqlzIntegrity.getAnnex() != null && affairRqlzIntegrity.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairRqlzIntegrity.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairRqlzIntegrityFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairRqlzIntegrity:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairRqlzIntegrityService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairRqlzIntegrity affairRqlzIntegrity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if ("6".equals(UserUtils.getUser().getOffice().getId())){
				affairRqlzIntegrity.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairRqlzIntegrity.setUnitId(UserUtils.getUser().getOffice().getId());
			}

			affairRqlzIntegrity.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairRqlzIntegrity> page = null;
			String reason = request.getParameter("reason");//2 全部 3 其他年份
			if("3".equals(reason)){
				affairRqlzIntegrity.setStartDate(null);
				affairRqlzIntegrity.setEndDate(null);
			}else if("2".equals(reason)){
				//全部
				if(affairRqlzIntegrity.getStartDate()==null && affairRqlzIntegrity.getEndDate() == null){
					String year = DateUtils.getYear();//获取当前年
					affairRqlzIntegrity.setOtherYear(year);
				}else{
					affairRqlzIntegrity.setOtherYear(null);
				}
			}else{
				//默认显示当前年度数据
				String year =DateUtils.getYear();//获取当前年
				affairRqlzIntegrity.setOtherYear(year);
				affairRqlzIntegrity.setStartDate(null);
				affairRqlzIntegrity.setEndDate(null);
			}
			if(flag == true){
				page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
			}else{
				page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response,-1), affairRqlzIntegrity);
			}
			/*if(flag == true){
				if (affairRqlzIntegrity.getStartDate() != null || affairRqlzIntegrity.getEndDate() != null){
					page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
				}else if (affairRqlzIntegrity.getStartDate() == null && affairRqlzIntegrity.getEndDate() == null){
					if ("".equals(affairRqlzIntegrity.getOtherYear()) || affairRqlzIntegrity.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairRqlzIntegrity.setStartYear(yearDate);
						page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
					}

				}else if (affairRqlzIntegrity.getOtherYear() != null && !"".equals(affairRqlzIntegrity.getOtherYear())){
					page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
				}
			}else {
				if (affairRqlzIntegrity.getStartDate() != null || affairRqlzIntegrity.getEndDate() != null){
					page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response,-1), affairRqlzIntegrity);
				}else if (affairRqlzIntegrity.getStartDate() == null && affairRqlzIntegrity.getEndDate() == null){
					if ("".equals(affairRqlzIntegrity.getOtherYear()) || affairRqlzIntegrity.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairRqlzIntegrity.setStartYear(yearDate);
						page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response,-1), affairRqlzIntegrity);
					}

				}else if (affairRqlzIntegrity.getOtherYear() != null && !"".equals(affairRqlzIntegrity.getOtherYear())){
					page = affairRqlzIntegrityService.findPage(new Page<AffairRqlzIntegrity>(request, response,-1), affairRqlzIntegrity);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairRqlzIntegrity.class);
			exportExcelNew.setWb(wb);
			List<AffairRqlzIntegrity> list =page.getList();
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

	@RequiresPermissions("affair:affairRqlzIntegrity:add")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairRqlzIntegrity> list = ei.getDataList(AffairRqlzIntegrity.class);
			for (AffairRqlzIntegrity affairRqlzIntegrity : list){
				try{
					affairRqlzIntegrity.setLzUnitId(officeService.findByName(affairRqlzIntegrity.getLzUnit()));

					BeanValidators.validateWithException(validator, affairRqlzIntegrity);
					affairRqlzIntegrityService.save(affairRqlzIntegrity);
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

	@RequestMapping(value = {"findDetailInfoByType"})
	public String findDetailInfoByType(AffairRqlzIntegrity affairRqlzIntegrity, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairRqlzIntegrity> page;
		page = affairRqlzIntegrityService.findDetailInfoByType(new Page<AffairRqlzIntegrity>(request, response), affairRqlzIntegrity);
		model.addAttribute("page", page);
		model.addAttribute("affairRqlzIntegrity", affairRqlzIntegrity);
		return "modules/charts/monitorLzFirstDetail";
	}
}