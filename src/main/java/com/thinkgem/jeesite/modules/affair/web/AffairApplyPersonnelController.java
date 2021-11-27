/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairApplyPersonnel;
import com.thinkgem.jeesite.modules.affair.service.AffairApplyPersonnelService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 报名人员Controller
 * @author alan.wu
 * @version 2020-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairApplyPersonnel")
public class AffairApplyPersonnelController extends BaseController {

	@Autowired
	private AffairApplyPersonnelService affairApplyPersonnelService;

	@Autowired
	private OfficeService officeService;

	
	@ModelAttribute
	public AffairApplyPersonnel get(@RequestParam(required=false) String id) {
		AffairApplyPersonnel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairApplyPersonnelService.get(id);
		}
		if (entity == null){
			entity = new AffairApplyPersonnel();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairApplyPersonnel:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairApplyPersonnel affairApplyPersonnel, HttpServletRequest request, HttpServletResponse response, Model model,HttpSession session) {
		Page<AffairApplyPersonnel> page = affairApplyPersonnelService.findPage(new Page<AffairApplyPersonnel>(request, response), affairApplyPersonnel);
		model.addAttribute("page", page);
		String classId = request.getParameter("classId");
		session.setAttribute("classId",classId);
		String name = affairApplyPersonnel.getName();
		String applyState = affairApplyPersonnel.getApplyState();
		if (StringUtils.isNotBlank(classId) && StringUtils.isBlank(name)){
			List<AffairApplyPersonnel> affairApplyPersonnelList = affairApplyPersonnelService.selectAllPersonnelByClassId(classId);
			model.addAttribute("AffairApplyPersonnel",affairApplyPersonnelList);
		}

		if (StringUtils.isNotBlank(classId) && StringUtils.isNotBlank(name) || StringUtils.isNotBlank(applyState)){
			List<AffairApplyPersonnel> list = affairApplyPersonnelService.findLike(classId,name,applyState);
			model.addAttribute("AffairApplyPersonnel",list);
		}

		model.addAttribute("classId", classId);
		return "modules/affair/affairApplyPersonnelList";
	}

	@RequiresPermissions("affair:affairApplyPersonnel:view")
	@RequestMapping(value = "form")
	public String form(AffairApplyPersonnel affairApplyPersonnel, Model model,HttpServletRequest request) {
		String classId = request.getParameter("classId");

		model.addAttribute("affairApplyPersonnel", affairApplyPersonnel);
		model.addAttribute("classId",classId);
		return "modules/affair/affairApplyPersonnelForm";
	}

	@RequiresPermissions("affair:affairApplyPersonnel:edit")
	@RequestMapping(value = "save")
	public String save(AffairApplyPersonnel affairApplyPersonnel, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairApplyPersonnel)){
			return form(affairApplyPersonnel, model,request);
		}
		affairApplyPersonnelService.save(affairApplyPersonnel);
		addMessage(redirectAttributes, "保存报名人员成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairApplyPersonnel/?repage";
	}

	/*
	* 保存报名信息
	* */
	@RequiresPermissions("affair:affairApplyPersonnel:edit")
	@RequestMapping(value = "applySave")
	public String applySave(AffairApplyPersonnel affairApplyPersonnel,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairApplyPersonnel)){
			return form(affairApplyPersonnel, model,request);
		}
		String classId = request.getParameter("classId");

		affairApplyPersonnel.setClassId(classId);
		affairApplyPersonnelService.save(affairApplyPersonnel);
		addMessage(redirectAttributes, "保存成功");

		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairApplyPersonnelForm";
	}

	@RequiresPermissions("affair:affairApplyPersonnel:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairApplyPersonnel affairApplyPersonnel, RedirectAttributes redirectAttributes) {
		affairApplyPersonnelService.delete(affairApplyPersonnel);
		addMessage(redirectAttributes, "删除报名人员成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairApplyPersonnel/?repage";
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairApplyPersonnel:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairApplyPersonnelService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 *
	 * @param affairApplyPersonnel
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairApplyPersonnel:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairApplyPersonnel affairApplyPersonnel, Model model) {
		model.addAttribute("affairApplyPersonnel", affairApplyPersonnel);
		return "modules/affair/affairApplyPersonnelFormDetail";
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
	public String exportExcelByTemplate(AffairApplyPersonnel affairApplyPersonnel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairApplyPersonnel.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairApplyPersonnel.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairApplyPersonnel.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairApplyPersonnel> page = null;
			if(flag == true){
				page = affairApplyPersonnelService.findPage(new Page<AffairApplyPersonnel>(request, response), affairApplyPersonnel);
			}else {
				page = affairApplyPersonnelService.findPage(new Page<AffairApplyPersonnel>(request, response,-1), affairApplyPersonnel);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairApplyPersonnel.class);
			exportExcelNew.setWb(wb);
			List<AffairApplyPersonnel> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairApplyPersonnel?repage";
	}


	/*
	 *
	 *  导入
	 * */
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpSession httpSession) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairApplyPersonnel> list = ei.getDataList(AffairApplyPersonnel.class);
			for (AffairApplyPersonnel affairApplyPersonnel : list){
				try{
					String classId = httpSession.getAttribute("classId").toString();
					affairApplyPersonnel.setClassId(classId);
					affairApplyPersonnel.setUnitId(officeService.findByName(affairApplyPersonnel.getUnit()));
					BeanValidators.validateWithException(validator, affairApplyPersonnel);
					affairApplyPersonnelService.save(affairApplyPersonnel);
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