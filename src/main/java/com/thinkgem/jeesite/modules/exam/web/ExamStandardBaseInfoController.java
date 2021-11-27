/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardBaseInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * 考评标准管理Controller
 * @author bradley.zhao
 * @version 2019-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examStandardBaseInfo")
public class ExamStandardBaseInfoController extends BaseController {

	@Autowired
	private ExamStandardBaseInfoService examStandardBaseInfoService;
	
	@ModelAttribute
	public ExamStandardBaseInfo get(@RequestParam(required=false) String id) {
		ExamStandardBaseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examStandardBaseInfoService.get(id);
		}
		if (entity == null){
			entity = new ExamStandardBaseInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examStandardBaseInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamStandardBaseInfo examStandardBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamStandardBaseInfo> pageParam =new Page<ExamStandardBaseInfo>(request, response,-1);
		pageParam.setOrderBy("obj_type,kp_type,cycle");
		Page<ExamStandardBaseInfo> page = examStandardBaseInfoService.findPage(pageParam, examStandardBaseInfo);
		Page<ExamStandardBaseInfo> pageParam2 =new Page<ExamStandardBaseInfo>(request, response);
		Page<ExamStandardBaseInfo> page2 = examStandardBaseInfoService.findPage(pageParam2, examStandardBaseInfo);
		List<ExamStandardBaseInfo> list = page.getList();
		List<ExamStandardBaseInfo> resultList = new ArrayList<ExamStandardBaseInfo>();
		if(null != list){
			int firstNum = 0;
			int endNum = 0;
			firstNum = (page2.getPageNo()-1)*page2.getPageSize();
			endNum = page2.getPageNo()* page2.getPageSize();
			if(endNum > list.size()){
				endNum = list.size();
			}
			for(int i = firstNum; i< endNum; i++){
				resultList.add(list.get(i));
			}
		}
		page2.setList(resultList);
		model.addAttribute("page", page2);
		return "modules/exam/examStandardBaseInfoList";
	}

	@RequestMapping("getStandardList")
	@ResponseBody
	public Result getStandardList(ExamStandardBaseInfo examStandardBaseInfo){
		examStandardBaseInfo.setCreateBy(new User(UserUtils.getUser().getId()));
		List<ExamStandardBaseInfo> infoList = examStandardBaseInfoService.findList(examStandardBaseInfo);
		Result result = new Result();
		result.setSuccess(true);
		result.setResult(infoList);
		return result;
	}

	/**
	 * 自动考评事项-考评标准关联
	 */
	@RequestMapping("getStandardListBeta")
	@ResponseBody
	public Result getStandardListBeta(ExamStandardBaseInfo examStandardBaseInfo){
		List<ExamStandardBaseInfo> infoList = examStandardBaseInfoService.findList(examStandardBaseInfo);
		Result result = new Result();
		result.setSuccess(true);
		result.setResult(infoList);
		return result;
	}

	@RequiresPermissions("exam:examStandardBaseInfo:view")
	@RequestMapping(value = "form")
	public String form(ExamStandardBaseInfo examStandardBaseInfo, Model model) {
		model.addAttribute("examStandardBaseInfo", examStandardBaseInfo);
		return "modules/exam/examStandardBaseInfoForm";
	}

	@RequiresPermissions("exam:examStandardBaseInfo:edit")
	@RequestMapping(value = "save")
	public String save(ExamStandardBaseInfo examStandardBaseInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, examStandardBaseInfo)){
			return form(examStandardBaseInfo, model);
		}
		examStandardBaseInfo.setIsEnable("1");
		examStandardBaseInfoService.save(examStandardBaseInfo);
		addMessage(redirectAttributes, "保存考评标准管理成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examStandardBaseInfoForm";
	}

	@RequiresPermissions("exam:examStandardBaseInfo:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(ExamStandardBaseInfo examStandardBaseInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		examStandardBaseInfo.setIsEnable(request.getParameter("status"));
		examStandardBaseInfoService.save(examStandardBaseInfo);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examStandardBaseInfo/?repage";
	}
	
	@RequiresPermissions("exam:examStandardBaseInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamStandardBaseInfo examStandardBaseInfo, RedirectAttributes redirectAttributes) {
		examStandardBaseInfoService.delete(examStandardBaseInfo);
		addMessage(redirectAttributes, "删除考评标准管理成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examStandardBaseInfo/?repage";
	}

}