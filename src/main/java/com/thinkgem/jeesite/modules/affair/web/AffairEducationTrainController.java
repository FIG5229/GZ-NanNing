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
import com.thinkgem.jeesite.modules.affair.entity.AffairEducationTrain;
import com.thinkgem.jeesite.modules.affair.service.AffairEducationTrainService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 教育训练培训计划Controller
 * @author jack.xu
 * @version 2020-07-01
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairEducationTrain")
public class AffairEducationTrainController extends BaseController {

	@Autowired
	private AffairEducationTrainService affairEducationTrainService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairEducationTrain get(@RequestParam(required=false) String id) {
		AffairEducationTrain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairEducationTrainService.get(id);
		}
		if (entity == null){
			entity = new AffairEducationTrain();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairEducationTrain:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairEducationTrain affairEducationTrain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairEducationTrain> page = affairEducationTrainService.findPage(new Page<AffairEducationTrain>(request, response), affairEducationTrain); 
		model.addAttribute("page", page);
		return "modules/affair/affairEducationTrainList";
	}

	@RequiresPermissions("affair:affairEducationTrain:view")
	@RequestMapping(value = "form")
	public String form(AffairEducationTrain affairEducationTrain, Model model) {
		model.addAttribute("affairEducationTrain", affairEducationTrain);
		return "modules/affair/affairEducationTrainForm";
	}

	@RequiresPermissions("affair:affairEducationTrain:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairEducationTrain affairEducationTrain, Model model) {
		model.addAttribute("affairEducationTrain", affairEducationTrain);
		if(affairEducationTrain.getFilePath() != null && affairEducationTrain.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairEducationTrain.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairEducationTrainFormDetail";
	}
//	@RequiresPermissions("affair:affairEducationTrain:view")
//	@RequestMapping(value = "approvedFormDetail")
//	public String approvedFormDetail(AffairEducationTrain affairEducationTrain, Model model) {
//		model.addAttribute("affairEducationTrain", affairEducationTrain);
//		if(affairEducationTrain.getFilePath() != null && affairEducationTrain.getFilePath().length() > 0){
//			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairEducationTrain.getFilePath());
//			model.addAttribute("filePathList", filePathList);
//		}
//		return "modules/affair/affairEducationTrainApprovedFormDetail";
//	}

	@RequiresPermissions("affair:affairEducationTrain:edit")
	@RequestMapping(value = "save")
	public String save(AffairEducationTrain affairEducationTrain, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairEducationTrain)){
			return form(affairEducationTrain, model);
		}
		affairEducationTrainService.save(affairEducationTrain);
		addMessage(redirectAttributes, "保存教育训练培训模块成功");
		request.setAttribute("saveResult","success");
//		return "redirect:"+Global.getAdminPath()+"/affair/affairEducationTrain/?repage";
		return "modules/affair/affairEducationTrainForm";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairEducationTrain:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairEducationTrainService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairEducationTrain:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairEducationTrain affairEducationTrain, RedirectAttributes redirectAttributes) {
		affairEducationTrainService.delete(affairEducationTrain);
		addMessage(redirectAttributes, "删除教育训练培训模块成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairEducationTrain/?repage";
	}

	/**
	 * 审核
	 * @return
	 */
	@RequiresPermissions("affair:affairEducationTrain:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog(String ids,Model model) {
		model.addAttribute("ids", ids);
		return "modules/affair/affairEducationTrainCheckDialog";
	}

//	这是用来实现审核功能的controller
	@ResponseBody
	@RequiresPermissions("affair:affairEducationTrain:edit")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairEducationTrain affairEducationTrain, HttpServletRequest request) {
		String remarks = affairEducationTrain.getRemarks();
		String approveResult = affairEducationTrain.getApproveResult();
		String idsStr = request.getParameter("ids");
		String[] idsArray = idsStr.split(",");
		List<String> userList = new ArrayList<String>();
		Collections.addAll(userList,idsArray);
		List <AffairEducationTrain> list = affairEducationTrainService.findByIds(userList);
		for (AffairEducationTrain  affairEducationTrainFromDb:list){
			affairEducationTrainFromDb.setRemarks(remarks);
			affairEducationTrainFromDb.setApproveResult(approveResult);
			affairEducationTrainService.save(affairEducationTrainFromDb);
		}
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
	/**
	 * 导出excel格式数据
	 * @param affairEducationTrain
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairEducationTrain affairEducationTrain, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairEducationTrain.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairEducationTrain> page = null;
			if (flag == true) {
				page = affairEducationTrainService.findPage(new Page<AffairEducationTrain>(request, response), affairEducationTrain);
			} else {
				page = affairEducationTrainService.findPage(new Page<AffairEducationTrain>(request, response, -1), affairEducationTrain);
			}

			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream) {
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairEducationTrain.class);
			exportExcelNew.setWb(wb);
			List<AffairEducationTrain> list = page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/affair/affairEducationTrain?repage";
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
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AffairEducationTrain> list = ei.getDataList(AffairEducationTrain.class);
			for (AffairEducationTrain thoughtAnalysis : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(thoughtAnalysis.getUnit());
					if(orgId != null){
						thoughtAnalysis.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, thoughtAnalysis);
					affairEducationTrainService.save(thoughtAnalysis);
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