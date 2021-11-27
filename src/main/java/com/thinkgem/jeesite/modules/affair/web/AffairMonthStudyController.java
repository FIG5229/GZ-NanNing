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
import com.thinkgem.jeesite.modules.affair.entity.AffairMonthStudy;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairMonthStudyService;
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
 * 月度学习计划Controller
 * @author mason.xv
 * @version 2019-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairMonthStudy")
public class AffairMonthStudyController extends BaseController {

	@Autowired
	private AffairMonthStudyService affairMonthStudyService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;
	
	@ModelAttribute
	public AffairMonthStudy get(@RequestParam(required=false) String id) {
		AffairMonthStudy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairMonthStudyService.get(id);
		}
		if (entity == null){
			entity = new AffairMonthStudy();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairMonthStudy:view")
	@RequestMapping(value = {""})
	public String index(AffairMonthStudy affairMonthStudy) {
		return "modules/affair/affairMonthStudyIndex";
	}

	@RequiresPermissions("affair:affairMonthStudy:view")
	@RequestMapping(value = {"list"})
	public String list(AffairMonthStudy affairMonthStudy, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairMonthStudy.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		Page<AffairMonthStudy> page = affairMonthStudyService.findPage(new Page<AffairMonthStudy>(request, response), affairMonthStudy);
		model.addAttribute("page", page);
		return "modules/affair/affairMonthStudyList";
	}

	@RequiresPermissions("affair:affairMonthStudy:view")
	@RequestMapping(value = "form")
	public String form(AffairMonthStudy affairMonthStudy, Model model) {
		model.addAttribute("affairMonthStudy", affairMonthStudy);
		return "modules/affair/affairMonthStudyForm";
	}

	@RequiresPermissions("affair:affairMonthStudy:edit")
	@RequestMapping(value = "save")
	public String save(AffairMonthStudy affairMonthStudy, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairMonthStudy)){
			return form(affairMonthStudy, model);
		}
		/*审核状态为空时 默认设置为 未上报*/
		if (StringUtils.isBlank(affairMonthStudy.getExamineStatus())){
			affairMonthStudy.setExamineStatus("1");
		}
		affairMonthStudyService.save(affairMonthStudy);
		addMessage(redirectAttributes, "保存月度学习计划成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairMonthStudyForm";
	}
	
	@RequiresPermissions("affair:affairMonthStudy:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairMonthStudy affairMonthStudy, RedirectAttributes redirectAttributes) {
		affairMonthStudyService.delete(affairMonthStudy);
		addMessage(redirectAttributes, "删除月度学习计划成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairMonthStudy/list?repage&treeId="+affairMonthStudy.getTreeId();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairMonthStudy:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairMonthStudyService.deleteByIds(ids);
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
	 * @param affairMonthStudy
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairMonthStudy:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairMonthStudy affairMonthStudy, Model model) {
		model.addAttribute("affairMonthStudy", affairMonthStudy);
		if (affairMonthStudy.getFilePath() != null && affairMonthStudy.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairMonthStudy.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairMonthStudyFormDetail";
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
	public String exportExcelByTemplate(AffairMonthStudy affairMonthStudy, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairMonthStudy> page = null;
			if(flag == true){
				page = affairMonthStudyService.findPage(new Page<AffairMonthStudy>(request, response), affairMonthStudy);
			}else{
				page = affairMonthStudyService.findPage(new Page<AffairMonthStudy>(request, response,-1), affairMonthStudy);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairMonthStudy.class);
			exportExcelNew.setWb(wb);
			List<AffairMonthStudy> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairMonthStudy/list?repage&treeId="+affairMonthStudy.getTreeId();
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairMonthStudy> list = ei.getDataList(AffairMonthStudy.class);
			for (AffairMonthStudy affairMonthStudy : list){
				try{
					BeanValidators.validateWithException(validator, affairMonthStudy);
					//党支部要绑定党支部id
					String partyOrganizationId = affairGeneralSituationService.findByName(affairMonthStudy.getPartyOrganization());
					affairMonthStudy.setPartyOrganizationId(partyOrganizationId);
					affairMonthStudyService.save(affairMonthStudy);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(affairMonthStudy.getPartyOrganization()+"(计划名称:"+affairMonthStudy.getTitle()+")"+" 导入失败："+ex.getMessage());
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
	 * 审核页面
	 * @return
	 */
	@RequestMapping("examineView")
	public String examineView(){
		return "modules/affair/affairMonthStudyExamineDialog";
	}

	/*审核*/
	@RequiresPermissions("affair:affairMonthStudy:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairMonthStudy affairMonthStudy, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairMonthStudy)){
			return form(affairMonthStudy, model);
		}
		affairMonthStudyService.save(affairMonthStudy);
		addMessage(redirectAttributes, "审核月度学习计划成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairMonthStudyForm";
	}

	@RequiresPermissions("affair:affairMonthStudy:edit")
	@RequestMapping(value = "report")
	public String report(AffairMonthStudy affairMonthStudy, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairMonthStudy)){
			return form(affairMonthStudy, model);
		}
		affairMonthStudy.setExamineStatus("2");
		affairMonthStudyService.save(affairMonthStudy);
		addMessage(redirectAttributes, "上报月度学习计划成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairMonthStudy/list?repage&treeId="+affairMonthStudy.getTreeId();
	}
}