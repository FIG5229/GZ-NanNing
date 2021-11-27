/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScore;
import com.thinkgem.jeesite.modules.exam.service.ExamLdScoreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 绩效等次Controller
 * @author daniel.liu
 * @version 2020-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examLdScore")
public class ExamLdScoreController extends BaseController {

	@Autowired
	private ExamLdScoreService examLdScoreService;
	
	@ModelAttribute
	public ExamLdScore get(@RequestParam(required=false) String id) {
		ExamLdScore entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examLdScoreService.get(id);
		}
		if (entity == null){
			entity = new ExamLdScore();
		}
		return entity;
	}
	//2
	String notPass=DictUtils.getDictValue("不达标","performance_grade","不达标");
	//3
	String basicPass=DictUtils.getDictValue("基本达标","performance_grade","基本达标");
	//4
	String pass=DictUtils.getDictValue("达标","performance_grade","达标");
	//5
	String excellent=DictUtils.getDictValue("优秀","performance_grade","优秀");
	//1
	String unDone=DictUtils.getDictValue("未评定","performance_grade","未评定");
//	@RequiresPermissions("exam:examLdScore:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamLdScore examLdScore, HttpServletRequest request, HttpServletResponse response, Model model) {
		String url="modules/exam/examLdScoreList";
		/*ABCD为绩效等级 A为优秀页面 B为达标页面 C为基本达标页面 D为不达标页面*/
		if (StringUtils.isEmpty(examLdScore.getGrades())){
			examLdScore.setGrades(basicPass);
		}
		if (examLdScore.getGrades().equals(basicPass)){
			url+="C";
		}
		if (examLdScore.getGrades().equals(notPass)){
			url+="D";
		}
		if (examLdScore.getGrades().equals(excellent)){
			url+="A";
		}
		if (examLdScore.getGrades().equals(pass)){
			url+="B";
		}
		Page<ExamLdScore> page = examLdScoreService.findPage(new Page<ExamLdScore>(request, response), examLdScore);
		model.addAttribute("page", page);
		model.addAttribute("workflowId",examLdScore.getWorkflowId());
		return url;
//		return "modules/exam/examLdScoreList";
	}

	@RequestMapping(value = {"exam"})
	public String exam(ExamLdScore examLdScore, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<ExamLdScore> page = examLdScoreService.findPage(new Page<ExamLdScore>(request, response), examLdScore);
		model.addAttribute("page", page);
		return "modules/exam/examLdScoreListBeta";
//		return "modules/exam/examLdScoreList";
	}

	@RequiresPermissions("exam:examLdScore:view")
	@RequestMapping(value = "form")
	public String form(ExamLdScore examLdScore, Model model) {
		model.addAttribute("examLdScore", examLdScore);
		return "modules/exam/examLdScoreForm";
	}

	@RequestMapping(value = "judgeGradesById")
	public String judgeGradesById(ExamLdScore examLdScore, Model model) {
		model.addAttribute("examLdScore", examLdScore);
		return "modules/exam/examLdScoreFormBeta";
	}
	/*批量添加优秀页面*/
//	@RequiresPermissions("exam:examLdScore:view")
	@RequestMapping(value = "formExcellent")
	public String formExcellent(ExamLdScore examLdScore, HttpServletRequest request, HttpServletResponse response, Model model) {
		examLdScore.setGrades(pass);
		Page<ExamLdScore> page = examLdScoreService.findPage(new Page<ExamLdScore>(request, response,-1), examLdScore);
		model.addAttribute("page", page);
		return "modules/exam/examLdScoreListToA";
	}

//	@RequiresPermissions("exam:examLdScore:edit")
	@RequestMapping(value = "save")
	public String save(ExamLdScore examLdScore, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examLdScore)){
			return form(examLdScore, model);
		}
		examLdScoreService.save(examLdScore);
		addMessage(redirectAttributes, "保存绩效等次成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examLdScore/?repage";
	}

//	@RequiresPermissions("exam:examLdScore:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamLdScore examLdScore, RedirectAttributes redirectAttributes) {
		examLdScoreService.delete(examLdScore);
		addMessage(redirectAttributes, "删除绩效等次成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examLdScore/?repage";
	}

//	@RequiresPermissions("exam:examLdScore:edit")
	@RequestMapping(value = "excellent")
	public String Excellent(ExamLdScore examLdScore, RedirectAttributes redirectAttributes){
		String excellent=DictUtils.getDictValue("优秀","performance_grade","优秀");
		examLdScore.setGrades(excellent);
		examLdScoreService.excellent(examLdScore);
		addMessage(redirectAttributes, "评定为优秀成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examLdScore/formExcellent?repage";
	}

	/**
	 * 批量添加为优秀
	 * @param ids
	 * @return
	 */
	@ResponseBody
//	@RequiresPermissions("exam:examLdScore:edit")
	@RequestMapping(value = {"excellentByIds"})
	public Result excellentByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examLdScoreService.excellentByIds(ids);
			result.setSuccess(true);
			result.setMessage("评定成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要评定的内容");
		}
		return result;
	}

	/*批量判定 挨个查询后  挨个更新*/
//	@RequiresPermissions("exam:examLdScoreMonth:edit")
	@RequestMapping(value = {"judgeGrades"})
	public String judgeGradesById(ExamLdScore examLdScore, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ExamLdScore> list=examLdScoreService.findList(examLdScore);
		for(ExamLdScore score:list){
			if (StringUtils.isEmpty(score.getSumScore())){
				score.setGrades(unDone);
				examLdScoreService.save(score);
				continue;
			}
			float s= Float.parseFloat(score.getSumScore());
			if (s<60){
				score.setGrades(notPass);
			}else if(s<70){
				score.setGrades(basicPass);
			}else{
				score.setGrades(pass);
			}
			examLdScoreService.save(score);
		}
		return "redirect:"+Global.getAdminPath()+"/exam/examLdScore/?repage";

	}
}