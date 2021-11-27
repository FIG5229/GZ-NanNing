/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheck1;
import com.thinkgem.jeesite.modules.exam.service.ExamCheckService1;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowDefineService;
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
import java.util.List;

/**
 * 检查法相情况录入Controller
 * @author mason.xv
 * @version 2019-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examCheck1")
public class ExamCheckController1 extends BaseController {

	@Autowired
	private ExamCheckService1 examCheckService1;

	@Autowired
	private ExamWorkflowDefineService examWorkflowDefineService;
	
	@ModelAttribute
	public ExamCheck1 get(@RequestParam(required=false) String id) {
		ExamCheck1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examCheckService1.get(id);
		}
		if (entity == null){
			entity = new ExamCheck1();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examCheck1:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamCheck1 examCheck1, HttpServletRequest request, HttpServletResponse response, Model model) {
		examCheck1.setHasAuth(false);

		//单位id
		String unitId = UserUtils.getUser().getOffice().getId();
//		examCheck1.setDutyUnitId(unitId);

		Page<ExamCheck1> page = examCheckService1.findPage(new Page<ExamCheck1>(request, response), examCheck1);
		model.addAttribute("page", page);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
		return "modules/exam/examCheckList2";
	}

	@RequiresPermissions("exam:examCheck1:view")
	@RequestMapping(value = "form")
	public String form(ExamCheck1 examCheck1, Model model) {
		model.addAttribute("examCheck1", examCheck1);
		return "modules/exam/examCheckReport1";
	}

	@RequiresPermissions("exam:examCheck1:edit")
	@RequestMapping(value = "save")
	public String save(ExamCheck1 examCheck1, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, examCheck1)){
			return form(examCheck1, model);
		}
		examCheckService1.save(examCheck1);
		addMessage(redirectAttributes, "保存检查法相情况录入成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examCheckForm1";
	}
	
	@RequiresPermissions("exam:examCheck1:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamCheck1 examCheck1, RedirectAttributes redirectAttributes) {
		examCheckService1.delete(examCheck1);
		addMessage(redirectAttributes, "删除检查法相情况录入成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examCheck1/?repage";
	}
	/**
	 * 管理页面
	 * @param examCheck1
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("exam:examCheck1:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(ExamCheck1 examCheck1, HttpServletRequest request, HttpServletResponse response, Model model) {
		examCheck1.setHasAuth(true);
		//单位id
		String unitId = UserUtils.getUser().getOffice().getId();
		examCheck1.setDutyUnitId(unitId);
		Page<ExamCheck1> page = examCheckService1.findPage(new Page<ExamCheck1>(request, response), examCheck1);
		model.addAttribute("page", page);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
		return "modules/exam/examCheckList1";
	}

	@RequiresPermissions("exam:examCheck1:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/exam/examCheckDialog1";
	}

	@ResponseBody
	@RequiresPermissions("exam:examCheck1:manage")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(ExamCheck1 examCheck1) {
		examCheckService1.shenHeSave(examCheck1);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}



	/**
	 * 详情
	 * @param examCheck1
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairThreeOne:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(ExamCheck1 examCheck1, Model model) {
		model.addAttribute("examCheck1", examCheck1);
		return "modules/exam/examCheckFormDetail1";
	}

	@ResponseBody
	@RequiresPermissions("exam:examCheck1:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examCheckService1.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
}