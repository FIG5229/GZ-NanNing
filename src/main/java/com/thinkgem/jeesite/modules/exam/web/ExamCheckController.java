/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheck;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheckChild;
import com.thinkgem.jeesite.modules.exam.service.*;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查法相情况录入Controller
 * @author mason.xv
 * @version 2019-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examCheck")
public class ExamCheckController extends BaseController {

	@Autowired
	private ExamCheckService examCheckService;

	@Autowired
	private ExamWorkflowDefineService examWorkflowDefineService;

	@Autowired
	private ExamStandardTemplateDefineService examStandardTemplateDefineService;

	@Autowired
	private ExamCheckChildService examCheckChildService;
	
	@ModelAttribute
	public ExamCheck get(@RequestParam(required=false) String id) {
		ExamCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examCheckService.get(id);
		}
		if (entity == null){
			entity = new ExamCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamCheck examCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamCheck> page = examCheckService.findPage(new Page<ExamCheck>(request, response), examCheck); 
		model.addAttribute("page", page);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile(true));
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		if (page.getList() != null && page.getList().size() > 0) {
			for (ExamCheck a : page.getList()) {
				List<ExamCheckChild> childList = examCheckChildService.findSomeByExamCheckId(a.getId());
				a.setExamCheckChildList(childList);
			}
		}
		String userId = UserUtils.getUser().getId();
		//北海处绩效办、柳州处绩效办
		if("46c521bf67e24db28772b3eac52dc454".equals(userId)||"6af0e615e9b0477da82eff06ff532c8b".equals(userId) || "978958003ea44a4bba3eed8ee6ceff3c".equals(userId) || "cca66e1339f14799b01f6db43ed16e16".equals(userId)){
			model.addAttribute("isJxb","isJxb");
		}else{
			model.addAttribute("isJxb","noJxb");
		}
		return "modules/exam/examCheckList";
	}

	@RequiresPermissions("exam:examCheck:view")
	@RequestMapping(value = "form")
	public String form(ExamCheck examCheck, Model model) {
		model.addAttribute("examCheck", examCheck);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile(true));
		List<ExamCheckChild> childList = examCheck.getExamCheckChildList();
		return "modules/exam/examCheckForm";
	}

	@RequiresPermissions("exam:examCheck:edit")
	@RequestMapping(value = "save")
	public String save(ExamCheck examCheck, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, examCheck)){
			return form(examCheck, model);
		}
		examCheckService.save(examCheck);
		addMessage(redirectAttributes, "保存检查法相情况录入成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examCheckForm";
	}
	
	@RequiresPermissions("exam:examCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamCheck examCheck, RedirectAttributes redirectAttributes) {
		examCheckService.delete(examCheck);
		addMessage(redirectAttributes, "删除检查法相情况录入成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examCheck/?repage";
	}

	@ResponseBody
	@RequiresPermissions("exam:examCheck:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examCheckService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情页面
	 * @param examCheck
	 * @param model
	 * @return
	 */
	@RequiresPermissions("exam:examCheck:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(ExamCheck examCheck, Model model) {
		model.addAttribute("examCheck", examCheck);
		return "modules/exam/examCheckFormDetail";
	}

	/**
	 * 根据使用模板的值查找选择项所对应的内容
	 * @param useModel
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("exam:examCheck:edit")
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

	/**
	 * 根据使用模板的值查找选择项所对应的内容
	 * @param useModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"findChooseOptionsByUseModel2"})
	public Result findByType2(String useModel) {
		Map<String,String> param = new HashMap<>();
		param.put("examStardardId",useModel);
		List<Map<String,String>> list=examStandardTemplateDefineService.findTemplateDatas(param);
		List<Map<String,String>> optionList = new ArrayList<>();
		if(list != null && list.size() > 0){
			for (Map<String,String> m:list) {
				//评分标准
				if("1".equals(m.get("column_type"))){
					Map<String,String> tempMap = new HashMap<>();
					tempMap.put("itemValue",m.get("item_value"));
					tempMap.put("optionId",m.get("id"));
					optionList.add(tempMap);
				}
			}
		}
		Result result = new Result();
		result.setSuccess(true);
		result.setResult(optionList);
		return result;
	}
}