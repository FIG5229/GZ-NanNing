/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamWeights;
import com.thinkgem.jeesite.modules.exam.entity.ExamWeightsMain;
import com.thinkgem.jeesite.modules.exam.service.ExamWeightsMainService;
import com.thinkgem.jeesite.modules.exam.service.ExamWeightsService;
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
import java.util.List;

/**
 * 权重Controller
 * @author cecil.li
 * @version 2020-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWeights")
public class ExamWeightsController extends BaseController {

	@Autowired
	private ExamWeightsService examWeightsService;

	@Autowired
	private ExamWeightsMainService examWeightsMainService;
	
	@ModelAttribute
	public ExamWeights get(@RequestParam(required=false) String id) {
		ExamWeights entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examWeightsService.get(id);
		}
		if (entity == null){
			entity = new ExamWeights();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examWeights:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamWeights examWeights, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamWeights> page = examWeightsService.findPage(new Page<ExamWeights>(request, response), examWeights); 
		model.addAttribute("page", page);
		return "modules/exam/examWeightsList";
	}

	@RequiresPermissions("exam:examWeights:view")
	@RequestMapping(value = "form")
	public String form(ExamWeights examWeights, Model model) {
		model.addAttribute("examWeights", examWeights);
		return "modules/exam/examWeightsForm";
	}

	@RequiresPermissions("exam:examWeights:edit")
	@RequestMapping(value = "save")
	public String save(ExamWeights examWeights, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, examWeights)){
			return form(examWeights, model);
		}
		examWeightsService.save(examWeights);
		addMessage(redirectAttributes, "保存权重成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examWeightsForm";
	}
	
	@RequiresPermissions("exam:examWeights:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamWeights examWeights, RedirectAttributes redirectAttributes) {
		examWeightsService.delete(examWeights);
		examWeightsMainService.deleteByMainId(examWeights.getId());
		addMessage(redirectAttributes, "删除权重成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWeights/?repage";
	}

	/**
	 * 详情
	 * @param examWeights
	 * @param examWeightsMain
	 * @param model
	 * @return
	 */
	@RequiresPermissions("exam:examWeights:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(ExamWeights examWeights, ExamWeightsMain examWeightsMain, Model model) {
		examWeightsMain.setEwId(examWeights.getId());
		model.addAttribute("examWeights", examWeights);
		//model.addAttribute("affairOneHelpOneMainList", affairOneHelpOneMainList);
		return "modules/exam/examWeightsFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("exam:examWeights:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examWeightsService.deleteByIds(ids);
			examWeightsMainService.deleteByMainIds(ids);
			/*if(ids != null && ids.size() > 0) {
				for (String id : ids) {
					affairOneHelpOneMainService.deleteByMainId(id);
				}
			}*/
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}