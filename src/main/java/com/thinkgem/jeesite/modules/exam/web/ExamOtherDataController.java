/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.exam.entity.ExamOtherData;
import com.thinkgem.jeesite.modules.exam.entity.ExamOtherDataChild;
import com.thinkgem.jeesite.modules.exam.service.ExamOtherDataChildService;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateDefineService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowDefineService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.thinkgem.jeesite.modules.exam.service.ExamOtherDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 其他数据Controller
 * @author kevin.jia
 * @version 2020-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examOtherData")
public class ExamOtherDataController extends BaseController {

	@Autowired
	private ExamOtherDataService examOtherDataService;

	@Autowired
	private ExamWorkflowDefineService examWorkflowDefineService;

	@Autowired
	private ExamStandardTemplateDefineService examStandardTemplateDefineService;

	@Autowired
	private ExamOtherDataChildService examOtherDataChildService;

	@ModelAttribute
	public ExamOtherData get(@RequestParam(required=false) String id) {
		ExamOtherData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examOtherDataService.get(id);
		}
		if (entity == null){
			entity = new ExamOtherData();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examOtherData:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamOtherData examOtherData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamOtherData> page = examOtherDataService.findPage(new Page<ExamOtherData>(request, response), examOtherData);
		model.addAttribute("page", page);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		if (page.getList() != null && page.getList().size() > 0) {
			for (ExamOtherData a : page.getList()) {
				ExamOtherDataChild examOtherDataChild = new ExamOtherDataChild();
				examOtherDataChild.setOtherId(a.getId());
				List<ExamOtherDataChild> childList = examOtherDataChildService.findList(examOtherDataChild);
				a.setExamOtherDataList(childList);
			}
		}
		return "modules/exam/examOtherDataList";
	}

	@RequiresPermissions("exam:examOtherData:view")
	@RequestMapping(value = "form")
	public String form(ExamOtherData examOtherData, Model model) {
		model.addAttribute("examOtherData", examOtherData);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
		List<ExamOtherDataChild> childList = examOtherData.getExamOtherDataList();
		return "modules/exam/examOtherDataForm";
	}

	@RequiresPermissions("exam:examOtherData:edit")
	@RequestMapping(value = "save")
	public String save(ExamOtherData examOtherData, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, examOtherData)){
			return form(examOtherData, model);
		}
		examOtherDataService.save(examOtherData);
		addMessage(redirectAttributes, "保存其他数据成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examOtherDataForm";
	}
	
	@RequiresPermissions("exam:examOtherData:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamOtherData examOtherData, RedirectAttributes redirectAttributes) {
		examOtherDataService.delete(examOtherData);
		addMessage(redirectAttributes, "删除其他数据成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examOtherData/?repage";
	}

	@ResponseBody
	@RequiresPermissions("exam:examOtherData:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examOtherDataService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}


	/**
	 * 根据使用模板的值查找选择项所对应的内容
	 * @param useModel
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("exam:examOtherData:edit")
	@RequestMapping(value = {"findChooseOptionsByUseModel"})
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