/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentsTask;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateDefineService;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateItemDefineService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowSegmentsTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateDataService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 模板项数据Controller
 * @author bradley.zhao
 * @version 2019-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examStandardTemplateData")
public class ExamStandardTemplateDataController extends BaseController {

	@Autowired
	private ExamStandardTemplateDataService examStandardTemplateDataService;

	@Autowired
	private ExamStandardTemplateItemDefineService examStandardTemplateItemDefineService;

	@Autowired
	private ExamStandardTemplateDefineService examStandardTemplateDefineService;

	@Autowired
	private ExamWorkflowSegmentsTaskService taskService;

	@ModelAttribute
	public ExamStandardTemplateData get(@RequestParam(required=false) String id) {
		ExamStandardTemplateData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examStandardTemplateDataService.get(id);
		}
		if (entity == null){
			entity = new ExamStandardTemplateData();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examStandardTemplateData:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamStandardTemplateData examStandardTemplateData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamStandardTemplateData> page = examStandardTemplateDataService.findPage(new Page<ExamStandardTemplateData>(request, response), examStandardTemplateData); 
		model.addAttribute("page", page);
		return "modules/exam/examStandardTemplateDataList";
	}

	/*使用主表的权限就可以*/
//	@RequiresPermissions("exam:examStandardTemplateData:view")
	@RequestMapping(value = "form")
	public String form(ExamStandardTemplateData examStandardTemplateData, Model model) {
		model.addAttribute("examStandardTemplateData", examStandardTemplateData);
		List<Map<String, String>> list = examStandardTemplateDataService.findRow(examStandardTemplateData);
		model.addAttribute("list", list);
		return "modules/exam/examStandardTemplateDataForm";
	}

	/*使用主表的权限就可以*/
	//添加页面
    //@RequiresPermissions("exam:examStandardTemplateData:view")
	@RequestMapping(value = "addForm")
	public String addForm(@RequestParam("examStardardId") String examStardardId,@RequestParam("templateId") String templateId, Model model) {
		model.addAttribute("examStardardId",examStardardId);
		model.addAttribute("templateId",templateId);
		ExamStandardTemplateData examStandardTemplateData = new ExamStandardTemplateData();
		model.addAttribute("examStandardTemplateData", examStandardTemplateData);
		List<Map<String, String>> list = examStandardTemplateItemDefineService.findByTemplateId(templateId);
		model.addAttribute("list", list);
		return "modules/exam/examStandardTemplateDataAddForm";
	}
	//添加保存方法
	@RequestMapping(value = "addSave")
	public String addSave(Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String examStardardId = request.getParameter("examStardardId");
		String templateId = request.getParameter("templateId");
		try {
			/*列数*/
			int columnNum  = Integer.parseInt(request.getParameter("columnNum"));
			for (int i = 0; i<columnNum;i++){
				String itemValue = request.getParameter("itemValue"+i);//获取字段值
				String itemId = request.getParameter("itemId"+i);//获取字段id
				ExamStandardTemplateData standardTemplateData = new ExamStandardTemplateData();
				standardTemplateData.setItemId(itemId);//设置模板项目ID
				standardTemplateData.setItemValue(itemValue);//设置数据项值
				Integer maxRowNum = examStandardTemplateDataService.getMaxRowNum(itemId);
				if(maxRowNum==null){
					maxRowNum = 1;
				}
				standardTemplateData.setRowNum(maxRowNum+1);//设置行号
				examStandardTemplateDataService.save(standardTemplateData);
			}
			/*更新模板的外键  考评标准与考评标准模板一一对应（一对一关系）*/
			examStandardTemplateDefineService.updateExamStardardId(templateId,examStardardId);
			/*清空此缓存*/
			CacheUtils.put("template_"+examStardardId, null);
			addMessage(redirectAttributes, "保存模板项数据管理成功");
			request.setAttribute("saveResult","success");
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			addMessage(redirectAttributes, "保存模板项数据管理失败");
		}
		return "modules/exam/examStandardTemplateDataAddForm";
	}

	/*使用主表的权限就可以*/
//	@RequiresPermissions("exam:examStandardTemplateData:edit")
	@RequestMapping(value = "save")
	public String save(ExamStandardTemplateData examStandardTemplateData, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, examStandardTemplateData)){
			return form(examStandardTemplateData, model);
		}
		/*行号*/
		String rowNum =request.getParameter("rowNum");
		/*列数*/
		int columnNum  = Integer.parseInt(request.getParameter("columnNum"));
		for (int i = 0; i<columnNum;i++){
			String columnValue = request.getParameter("column"+i);
			String id = request.getParameter("id"+i);
			String itemId = request.getParameter("itemId"+i);
			ExamStandardTemplateData standardTemplateData = new ExamStandardTemplateData();
			standardTemplateData.setRowNum(Integer.valueOf(rowNum));
			/*根据考评标准管理Id和行号更新值*/
			standardTemplateData.setItemValue(columnValue);
			standardTemplateData.setId(id);
			standardTemplateData.setItemId(itemId);
			String updateTime = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//			格式化之后的值是 2018-09-25
			standardTemplateData.setUpdateDate(new Date());
			/*模板列的序号不从0开始，导入有问题，修改时则会报错*/
			try {
				examStandardTemplateDataService.save(standardTemplateData);
			}catch (Exception e){
				addMessage(redirectAttributes, "查看格式的列顺序序号是否从0开始");
			}
		}
		String examStardardId = request.getParameter("examStardardId");
		/*清空此缓存*/
		CacheUtils.put("template_"+examStardardId, null);
//		examStandardTemplateDataService.save(examStandardTemplateData);
		addMessage(redirectAttributes, "保存模板项数据管理成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/template_items";
//		return "redirect:"+Global.getAdminPath()+"/exam/examStandardTemplateDefine/items";
	}

	/*使用主表的权限就可以*/
//	@RequiresPermissions("exam:examStandardTemplateData:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamStandardTemplateData examStandardTemplateData, RedirectAttributes redirectAttributes,HttpServletRequest request) {
//		examStandardTemplateDataService.delete(examStandardTemplateData);
		examStandardTemplateDataService.deleteLine(examStandardTemplateData);
		/*此itemId 为考评标准Id*/
		CacheUtils.put("template_"+examStandardTemplateData.getItemId(), null);
		addMessage(redirectAttributes, "删除模板项数据管理成功");
		redirectAttributes.addAttribute("standard_id",examStandardTemplateData.getItemId());
		String objType = request.getParameter("objType");
		String kpType = request.getParameter("kpType");
		String cycle = request.getParameter("cycle");
		redirectAttributes.addAttribute("objType",objType);
		redirectAttributes.addAttribute("kpType",kpType );
		redirectAttributes.addAttribute("cycle",cycle);
		/*考评项目删除时，同时要删除任务分配里的数据，不然会造成任务分配完成情况异常*/
		ExamWorkflowSegmentsTask segmentsTask = new ExamWorkflowSegmentsTask();
		segmentsTask.setStandardId(examStandardTemplateData.getItemId());
		segmentsTask.setRowNum(examStandardTemplateData.getRowNum());
//		segmentsTask.s
		/**/
		if (StringUtils.isNotBlank(segmentsTask.getStandardId()) && segmentsTask.getRowNum() != null){
			taskService.deleteByStandardIdAndNum(segmentsTask);
		}

		return "redirect:"+Global.getAdminPath()+"/exam/examStandardTemplateDefine/items";
//		return "redirect:"+Global.getAdminPath()+"/exam/examStandardTemplateData/?repage";
	}

	/**
	 * 通过标准Id和行号获取考评标准
	 * @param standard
	 * @param rowId
	 * @return
	 */
	@RequestMapping(value = "getStandardOption")
	@ResponseBody
	public Result getStandardOption(@RequestParam(value = "standard")String standard,@RequestParam(value = "rowId")String rowId){
		Result result = new Result();
		try {
			ExamStandardTemplateDefine examStandardTemplateDefine = new ExamStandardTemplateDefine();
			examStandardTemplateDefine.setExamStardardId(standard);
			List<ExamStandardTemplateDefine> list = examStandardTemplateDefineService.findList(examStandardTemplateDefine);
			String templateId = "";
			if (list != null && list.size()>0){
				templateId = list.get(0).getId();
			}
			List<ExamStandardTemplateData> standardTemplateData = examStandardTemplateDataService.getStandardRow(templateId,rowId);
			ExamStandardTemplateData templateData = standardTemplateData.stream().filter(item -> item.getColumnType().equals("1")).findFirst().get();
			result.setSuccess(true);
			result.setResult(templateData);
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

}