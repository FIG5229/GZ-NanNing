/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTeamDisciplineSign;
import com.thinkgem.jeesite.modules.affair.service.AffairTeamDisciplineSignService;
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
 * 纪律作风教育整顿Controller
 * @author cecil.li
 * @version 2020-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTeamDisciplineSign")
public class AffairTeamDisciplineSignController extends BaseController {

	@Autowired
	private AffairTeamDisciplineSignService affairTeamDisciplineSignService;
	
	@ModelAttribute
	public AffairTeamDisciplineSign get(@RequestParam(required=false) String id) {
		AffairTeamDisciplineSign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTeamDisciplineSignService.get(id);
		}
		if (entity == null){
			entity = new AffairTeamDisciplineSign();
		}
		return entity;
	}
	
//	@RequiresPermissions("affair:affairTeamDisciplineSign:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTeamDisciplineSign affairTeamDisciplineSign, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairTeamDisciplineSign> signList =affairTeamDisciplineSignService.findSign(affairTeamDisciplineSign);
		List<AffairTeamDisciplineSign> notSignList =affairTeamDisciplineSignService.findNotSign(affairTeamDisciplineSign);
		model.addAttribute("signList", signList);
		model.addAttribute("notSignList", notSignList);
		model.addAttribute("noticeId",affairTeamDisciplineSign.getNoticeId());
		return "modules/affair/affairTeamDisciplineSignList";
	}

	@RequiresPermissions("affair:affairTeamDisciplineSign:view")
	@RequestMapping(value = "form")
	public String form(AffairTeamDisciplineSign affairTeamDisciplineSign, Model model) {
		model.addAttribute("affairTeamDisciplineSign", affairTeamDisciplineSign);
		return "modules/affair/affairTeamDisciplineSignForm";
	}

	@RequiresPermissions("affair:affairTeamDisciplineSign:edit")
	@RequestMapping(value = "save")
	public String save(AffairTeamDisciplineSign affairTeamDisciplineSign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTeamDisciplineSign)){
			return form(affairTeamDisciplineSign, model);
		}
		affairTeamDisciplineSignService.save(affairTeamDisciplineSign);
		addMessage(redirectAttributes, "保存纪律作风教育整顿成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTeamDisciplineSign/?repage";
	}
	
	@RequiresPermissions("affair:affairTeamDisciplineSign:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTeamDisciplineSign affairTeamDisciplineSign, RedirectAttributes redirectAttributes) {
		affairTeamDisciplineSignService.delete(affairTeamDisciplineSign);
		addMessage(redirectAttributes, "删除纪律作风教育整顿成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTeamDisciplineSign/?repage";
	}

	/**
	 * 催办(权限为affair:affairFileNotice:manage，不是affair:affairFileNoticeSign:manage)
	 * @param affairTeamDisciplineSign
	 * @param redirectAttributes
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = "urge")
	public String urge(AffairTeamDisciplineSign affairTeamDisciplineSign, RedirectAttributes redirectAttributes) {
		affairTeamDisciplineSignService.urge(affairTeamDisciplineSign);
		addMessage(redirectAttributes, "催办成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTeamDisciplineSign/?repage&noticeId="+affairTeamDisciplineSign.getNoticeId();
	}

	@RequestMapping(value = "oneKeyUrge")
	public String oneKeyUrge(AffairTeamDisciplineSign affairTeamDisciplineSign, RedirectAttributes redirectAttributes){
		List<AffairTeamDisciplineSign> notSignList =affairTeamDisciplineSignService.findNotSign(affairTeamDisciplineSign);

		for (AffairTeamDisciplineSign sign:notSignList){
			affairTeamDisciplineSignService.urge(sign);
		}
		addMessage(redirectAttributes, "一键催办成功");

		return "redirect:"+Global.getAdminPath()+"/affair/affairTeamDisciplineSign/?repage&noticeId="+affairTeamDisciplineSign.getNoticeId();

	}

	/**
	 * 批量签收
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTeamDisciplineSign:edit")
	@RequestMapping(value = {"sign"})
	public Result sign(@RequestParam("ids[]") List<String> ids, RedirectAttributes redirectAttributes) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTeamDisciplineSignService.sign(ids);
			addMessage(redirectAttributes, "签收成功");
			result.setSuccess(true);
			result.setMessage("签收成功");
		}else{
			addMessage(redirectAttributes, "请选择签收内容");
		}
		return result;
	}

	/**
	 * 单条签收
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTeamDisciplineSign:edit")
	@RequestMapping(value = {"signOne"})
	public Result signOne(String id) {
		affairTeamDisciplineSignService.signOne(id);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("签收成功");
		return result;
	}

}