/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.entity.ExamKpPersonRelation;
import com.thinkgem.jeesite.modules.exam.service.ExamKpPersonRelationService;

import java.util.List;

import static com.thinkgem.jeesite.common.service.BaseService.dataScopeFilter;

/**
 * 考评初审人员关系表Controller
 * @author kevin.jia
 * @version 2020-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examKpPersonRelation")
public class ExamKpPersonRelationController extends BaseController {

	@Autowired
	private ExamKpPersonRelationService examKpPersonRelationService;
	
	@ModelAttribute
	public ExamKpPersonRelation get(@RequestParam(required=false) String id) {
		ExamKpPersonRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examKpPersonRelationService.get(id);
		}
		if (entity == null){
			entity = new ExamKpPersonRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examKpPersonRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamKpPersonRelation examKpPersonRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		examKpPersonRelation.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(), "o", "u"));
		Page<ExamKpPersonRelation> page = examKpPersonRelationService.findPage(new Page<ExamKpPersonRelation>(request, response), examKpPersonRelation); 
		model.addAttribute("page", page);
		return "modules/exam/examKpPersonRelationList";
	}

	@RequiresPermissions("exam:examKpPersonRelation:view")
	@RequestMapping(value = "form")
	public String form(ExamKpPersonRelation examKpPersonRelation, Model model) {
		model.addAttribute("examKpPersonRelation", examKpPersonRelation);
		return "modules/exam/examKpPersonRelationForm";
	}

	@RequiresPermissions("exam:examKpPersonRelation:edit")
	@RequestMapping(value = "save")
	public String save(ExamKpPersonRelation examKpPersonRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examKpPersonRelation)){
			return form(examKpPersonRelation, model);
		}
		examKpPersonRelationService.save(examKpPersonRelation);
		addMessage(redirectAttributes, "保存考评初审人员关系表成功");
		model.addAttribute("saveResult","success");
		return "modules/exam/examKpPersonRelationForm";
	}
	
	@RequiresPermissions("exam:examKpPersonRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamKpPersonRelation examKpPersonRelation, RedirectAttributes redirectAttributes) {
		examKpPersonRelationService.delete(examKpPersonRelation);
		addMessage(redirectAttributes, "删除考评初审人员关系表成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examKpPersonRelation/?repage";
	}

	@ResponseBody
	@RequiresPermissions("exam:examKpPersonRelation:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examKpPersonRelationService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}