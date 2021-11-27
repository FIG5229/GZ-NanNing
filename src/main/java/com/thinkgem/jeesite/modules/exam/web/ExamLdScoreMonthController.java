/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
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
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScoreMonth;
import com.thinkgem.jeesite.modules.exam.service.ExamLdScoreMonthService;

import java.util.List;

/**
 * 月度领导考核评分Controller
 * @author cecil.li
 * @version 2020-02-12
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examLdScoreMonth")
public class ExamLdScoreMonthController extends BaseController {

	@Autowired
	private ExamLdScoreMonthService examLdScoreMonthService;
	
	@ModelAttribute
	public ExamLdScoreMonth get(@RequestParam(required=false) String id) {
		ExamLdScoreMonth entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examLdScoreMonthService.get(id);
		}
		if (entity == null){
			entity = new ExamLdScoreMonth();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examLdScoreMonth:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamLdScoreMonth examLdScoreMonth, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamLdScoreMonth> page = examLdScoreMonthService.findPage(new Page<ExamLdScoreMonth>(request, response), examLdScoreMonth); 
		model.addAttribute("page", page);
		return "modules/exam/examLdScoreMonthList";
	}
	/*民警绩效考核全局公示显示，不应设置权限*/
	@RequestMapping(value = {"exam"})
	public String exam(ExamLdScoreMonth examLdScoreMonth, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamLdScoreMonth> page = examLdScoreMonthService.findPage(new Page<ExamLdScoreMonth>(request, response), examLdScoreMonth);
		model.addAttribute("page", page);
		return "modules/exam/examLdScoreMonthListBeta";
	}

	@RequiresPermissions("exam:examLdScoreMonth:view")
	@RequestMapping(value = "form")
	public String form(ExamLdScoreMonth examLdScoreMonth, Model model) {
		model.addAttribute("examLdScoreMonth", examLdScoreMonth);
		return "modules/exam/examLdScoreMonthForm";
	}

	@RequiresPermissions("exam:examLdScoreMonth:edit")
	@RequestMapping(value = "save")
	public String save(ExamLdScoreMonth examLdScoreMonth, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examLdScoreMonth)){
			return form(examLdScoreMonth, model);
		}
		examLdScoreMonthService.save(examLdScoreMonth);
		addMessage(redirectAttributes, "保存月度领导考核评分成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examLdScoreMonth/?repage";
	}

	/**
	 * 最终结果展示 等次评定
	 * @param examLdScoreMonth
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "saveBeta")
	public String saveBeta(ExamLdScoreMonth examLdScoreMonth, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examLdScoreMonth)){
			return form(examLdScoreMonth, model);
		}
		examLdScoreMonthService.save(examLdScoreMonth);
		addMessage(redirectAttributes, "保存月度领导考核评分成功");
		return "modules/exam/examLdScoreMonthGradesBeta";
	}
	
	@RequiresPermissions("exam:examLdScoreMonth:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamLdScoreMonth examLdScoreMonth, RedirectAttributes redirectAttributes) {
		examLdScoreMonthService.delete(examLdScoreMonth);
		addMessage(redirectAttributes, "删除月度领导考核评分成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examLdScoreMonth/?repage";
	}

	/*批量判定 挨个查询后  挨个更新*/
	@RequiresPermissions("exam:examLdScoreMonth:edit")
	@RequestMapping(value = {"judgeGrades"})
	public String judgeGradesById(ExamLdScoreMonth examLdScoreMonth, HttpServletRequest request, HttpServletResponse response,Model model) {
		List<ExamLdScoreMonth> page=examLdScoreMonthService.findList(examLdScoreMonth);
		for(ExamLdScoreMonth scoreMonth:page){
			if (StringUtils.isEmpty(scoreMonth.getScore())){
				scoreMonth.setGrades("1");
				examLdScoreMonthService.save(scoreMonth);
				continue;
			}
			float score= Float.parseFloat(scoreMonth.getScore());
			if (score<60){
				scoreMonth.setGrades("2");
			}else if(score<70){
				scoreMonth.setGrades("3");
			}
			examLdScoreMonthService.save(scoreMonth);
		}
//		Page<ExamLdScoreMonth> page = examLdScoreMonthService.findPage(new Page<ExamLdScoreMonth>(request, response), examLdScoreMonth);
		model.addAttribute("page", page);
		return "redirect:"+Global.getAdminPath()+"/exam/examLdScoreMonth/?repage";

	}

	@RequiresPermissions("exam:examLdScoreMonth:edit")
	@RequestMapping(value = {"judgeGradesById"})
	public String judgeGrades(ExamLdScoreMonth examLdScoreMonth, HttpServletRequest request, HttpServletResponse response,Model model) {
		model.addAttribute("examLdScoreMonth", examLdScoreMonth);
		return "modules/exam/examLdScoreMonthGrades";

	}

	@RequestMapping(value = {"judgeGradesByIdBeta"})
	public String judgeGradesBeta(ExamLdScoreMonth examLdScoreMonth, HttpServletRequest request, HttpServletResponse response,Model model) {
		model.addAttribute("examLdScoreMonth", examLdScoreMonth);
		return "modules/exam/examLdScoreMonthGradesBeta";

	}

}