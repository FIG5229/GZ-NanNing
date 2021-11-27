/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

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
import com.thinkgem.jeesite.modules.affair.service.*;
import com.thinkgem.jeesite.modules.exam.entity.ExamJcInfo;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.exam.service.ExamJcInfoService;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardBaseInfoService;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateDefineService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowDefineService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奖惩信息库Controller
 * @author mason.xv
 * @version 2020-01-03
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examJcInfo")
public class ExamJcInfoController extends BaseController {

	@Autowired
	private AffairTwPersonalAwardService affairTwPersonalAwardService;
	@Autowired
	private AffairPersonalRewardService affairPersonalRewardService;
	@Autowired
	private AffairXcUnitRewardService affairXcUnitRewardService;
	@Autowired
	private AffairTwUnitAwardService affairTwUnitAwardService;
	@Autowired
	private UploadController uploadController;
	@Autowired
	private AffairDisciplinaryActionService affairDisciplinaryActionService;
	@Autowired
	private ExamJcInfoService examJcInfoService;
	@Autowired
	private ExamWorkflowDefineService examWorkflowDefineService;
	@Autowired
	private ExamStandardTemplateDefineService examStandardTemplateDefineService;
	@Autowired
	private ExamStandardBaseInfoService examStandardBaseInfoService;
	
	@ModelAttribute
	public ExamJcInfo get(@RequestParam(required=false) String id) {
		ExamJcInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examJcInfoService.get(id);
		}
		if (entity == null){
			entity = new ExamJcInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examJcInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamJcInfo examJcInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamJcInfo> page = examJcInfoService.findPage(new Page<ExamJcInfo>(request, response), examJcInfo); 
		model.addAttribute("page", page);
		return "modules/exam/examJcInfoList";
	}

	@RequiresPermissions("exam:examJcInfo:view")
	@RequestMapping(value = "form")
	public String form(ExamJcInfo examJcInfo, Model model) {
		model.addAttribute("examJcInfo", examJcInfo);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
		return "modules/exam/examJcInfoForm";
	}

	@RequiresPermissions("exam:examJcInfo:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam("id")String id,ExamJcInfo examJcInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, examJcInfo)){
			return form(examJcInfo, model);
		}
		//设置模板名称
		ExamStandardBaseInfo examStandardBaseInfo= examStandardBaseInfoService.get(examJcInfo.getMyUseModel());
		examJcInfo.setMyUseModelName(examStandardBaseInfo.getName());
		examJcInfoService.save(examJcInfo);
		addMessage(redirectAttributes, "保存奖惩信息库成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examJcInfoForm";
	}
	
	@RequiresPermissions("exam:examJcInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamJcInfo examJcInfo, RedirectAttributes redirectAttributes) {
		examJcInfoService.delete(examJcInfo);
		addMessage(redirectAttributes, "删除奖惩信息库成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examJcInfo/?repage";
	}

	@ResponseBody
	@RequiresPermissions("exam:examJcInfo:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examJcInfoService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("exam:examJcInfo:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(ExamJcInfo examJcInfo, Model model) {
		model.addAttribute("examJcInfo", examJcInfo);
		if(examJcInfo.getFilePath() != null && examJcInfo.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(examJcInfo.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/exam/examJcInfoFormDetail";
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
	public String exportExcelByTemplate(ExamJcInfo examJcInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<ExamJcInfo> page = null;
			if(flag == true){
				page = examJcInfoService.findPage(new Page<ExamJcInfo>(request, response), examJcInfo);
			}else{
				page = examJcInfoService.findPage(new Page<ExamJcInfo>(request, response,-1), examJcInfo);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, ExamJcInfo.class);
			exportExcelNew.setWb(wb);
			List<ExamJcInfo> list =page.getList();
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
		return "redirect:" + adminPath + "/exam/examJcInfo/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<ExamJcInfo> list = ei.getDataList(ExamJcInfo.class);
			for (ExamJcInfo examJcInfo : list){
				try{
					BeanValidators.validateWithException(validator, examJcInfo);
					//根据模板名称查询对应的模板id
					String myUseModelId = examStandardBaseInfoService.findIdByName(examJcInfo.getMyUseModel());
					examJcInfo.setMyUseModel(myUseModelId);
					examJcInfoService.save(examJcInfo);
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

	/**
	 * 根据使用模板的值查找选择项所对应的内容
	 * @param useModel
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("exam:examJcInfo:edit")
	@RequestMapping(value = {"findChangeTypeByUseModel"})
	public Result findByType(String useModel) {
		Map<String,String> param = new HashMap<>();
		param.put("examStardardId",useModel);
		List<Map<String,String>> list=examStandardTemplateDefineService.findTemplateDatas(param);
		List<String> optionList = new ArrayList<>();
		if(list != null && list.size() > 0){
			for (Map<String,String> m:list) {
				//评分标准
				if("1".equals(m.get("column_type"))){
					optionList.add(m.get("item_value"));
				}
			}
		}
		Result result = new Result();
		result.setSuccess(true);
		result.setResult(optionList);
		return result;
	}
}