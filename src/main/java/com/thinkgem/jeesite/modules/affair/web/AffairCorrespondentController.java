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
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsNameDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairNews;
import com.thinkgem.jeesite.modules.affair.service.AffairNewsService;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairCorrespondent;
import com.thinkgem.jeesite.modules.affair.service.AffairCorrespondentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通讯员管理Controller
 * @author Alan.wu
 * @version 2020-06-18
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCorrespondent")
public class AffairCorrespondentController extends BaseController {

	@Autowired
	private AffairCorrespondentService affairCorrespondentService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairNewsDao affairNewsDao;

	@Autowired
	private AffairNewsNameDao affairNewsNameDao;
	
	@ModelAttribute
	public AffairCorrespondent get(@RequestParam(required=false) String id) {
		AffairCorrespondent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCorrespondentService.get(id);
		}
		if (entity == null){
			entity = new AffairCorrespondent();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCorrespondent:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCorrespondent affairCorrespondent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCorrespondent> page = affairCorrespondentService.findPage(new Page<AffairCorrespondent>(request, response), affairCorrespondent); 
		model.addAttribute("page", page);
		return "modules/affair/affairCorrespondentList";
	}

	@RequiresPermissions("affair:affairCorrespondent:view")
	@RequestMapping(value = "form")
	public String form(AffairCorrespondent affairCorrespondent, Model model) {
		model.addAttribute("affairCorrespondent", affairCorrespondent);
		return "modules/affair/affairCorrespondentForm";
	}

	@RequiresPermissions("affair:affairCorrespondent:edit")
	@RequestMapping(value = "save")
	public String save(AffairCorrespondent affairCorrespondent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairCorrespondent)){
			return form(affairCorrespondent, model);
		}
		affairCorrespondentService.save(affairCorrespondent);
		addMessage(redirectAttributes, "保存通讯员管理成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairCorrespondentForm";
	}
	
	@RequiresPermissions("affair:affairCorrespondent:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCorrespondent affairCorrespondent, RedirectAttributes redirectAttributes) {
		affairCorrespondentService.delete(affairCorrespondent);
		addMessage(redirectAttributes, "删除通讯员管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCorrespondent/?repage";
	}


	/**
	 * 详情
	 *
	 * @param affairCorrespondent
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairLearnPower:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCorrespondent affairCorrespondent, Model model) {


		String idNumber = affairCorrespondent.getIdNumber();

		//所有的稿件主表id
		List<String> newsId = affairNewsNameDao.selectAllNewsId(idNumber);

		List<AffairNews> affairNews = new ArrayList<>();
		for (int i = 0; i < newsId.size(); i++){
			String newId = newsId.get(i);
			AffairNews affairNews1 = affairNewsDao.selectBeenByUserId(newId);
			affairNews.add(affairNews1);

			if (affairCorrespondent.getAdjunct() != null && affairCorrespondent.getAdjunct().length() > 0){

				List<Map<String, String>> filePathList = uploadController.filePathHandle(affairCorrespondent.getAdjunct());
				model.addAttribute("filePathList", filePathList);
			}
		}
		if (affairNews.size() > 0){
			model.addAttribute("affairNews",affairNews);
		}
		model.addAttribute("affairCorrespondent", affairCorrespondent);
		return "modules/affair/affairCorrespondentFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLearnPower:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCorrespondentService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairCorrespondent affairCorrespondent,
										HttpServletRequest request, HttpServletResponse response,
										RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairCorrespondent.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairCorrespondent.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairCorrespondent.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairCorrespondent> page = null;
			if(flag == true){
				page = affairCorrespondentService.findPage(new Page<AffairCorrespondent>(request, response), affairCorrespondent);
			}else {
				page = affairCorrespondentService.findPage(new Page<AffairCorrespondent>(request, response,-1), affairCorrespondent);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
			//主表信息（导出表）
			List<AffairCorrespondent> affairCorrespondentList = page.getList();
			for (int i = 0; i < affairCorrespondentList.size(); i++){
				AffairCorrespondent affairCorrespondent1 = affairCorrespondentList.get(i);
				String idNumber = affairCorrespondent1.getIdNumber();
				List<String> list = affairNewsDao.selectContributionByUserId(idNumber);
				if (list.size() > 0){
					affairCorrespondent1.setAffairNews(list);
				}

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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairCorrespondent.class);
			exportExcelNew.setWb(wb);
			exportExcelNew.setDataList(affairCorrespondentList);
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
		return "redirect:" + adminPath + "/affair/affairCorrespondent?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairCorrespondent> list = ei.getDataList(AffairCorrespondent.class);
			for (AffairCorrespondent affairCorrespondent : list){
				try{
					affairCorrespondent.setUnitId(officeService.findByName(affairCorrespondent.getUnit()));
					BeanValidators.validateWithException(validator, affairCorrespondent);
					affairCorrespondentService.save(affairCorrespondent);
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


}