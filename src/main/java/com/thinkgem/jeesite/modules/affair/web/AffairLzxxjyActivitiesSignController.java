/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivitiesSign;
import com.thinkgem.jeesite.modules.affair.service.AffairLzxxjyActivitiesSignService;
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
 * 廉政教育管理Controller
 * @author cecil.li
 * @version 2020-06-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLzxxjyActivitiesSign")
public class AffairLzxxjyActivitiesSignController extends BaseController {

	@Autowired
	private AffairLzxxjyActivitiesSignService affairLzxxjyActivitiesSignService;
	
	@ModelAttribute
	public AffairLzxxjyActivitiesSign get(@RequestParam(required=false) String id) {
		AffairLzxxjyActivitiesSign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLzxxjyActivitiesSignService.get(id);
		}
		if (entity == null){
			entity = new AffairLzxxjyActivitiesSign();
		}
		return entity;
	}
	
//	@RequiresPermissions("affair:affairLzxxjyActivitiesSign:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AffairLzxxjyActivitiesSign> signList =affairLzxxjyActivitiesSignService.findSign(affairLzxxjyActivitiesSign);
		List<AffairLzxxjyActivitiesSign> notSignList =affairLzxxjyActivitiesSignService.findNotSign(affairLzxxjyActivitiesSign);
		model.addAttribute("signList", signList);
		model.addAttribute("notSignList", notSignList);
		model.addAttribute("noticeId",affairLzxxjyActivitiesSign.getNoticeId());
		return "modules/affair/affairLzxxjyActivitiesSignList";
	}

	@RequiresPermissions("affair:affairLzxxjyActivitiesSign:view")
	@RequestMapping(value = "form")
	public String form(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign, Model model) {
		model.addAttribute("affairLzxxjyActivitiesSign", affairLzxxjyActivitiesSign);
		return "modules/affair/affairLzxxjyActivitiesSignForm";
	}

	@RequiresPermissions("affair:affairLzxxjyActivitiesSign:edit")
	@RequestMapping(value = "save")
	public String save(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairLzxxjyActivitiesSign)){
			return form(affairLzxxjyActivitiesSign, model);
		}
		affairLzxxjyActivitiesSignService.save(affairLzxxjyActivitiesSign);
		addMessage(redirectAttributes, "保存廉政教育管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLzxxjyActivitiesSign/?repage";
	}
	
	@RequiresPermissions("affair:affairLzxxjyActivitiesSign:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign, RedirectAttributes redirectAttributes) {
		affairLzxxjyActivitiesSignService.delete(affairLzxxjyActivitiesSign);
		addMessage(redirectAttributes, "删除廉政教育管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLzxxjyActivitiesSign/?repage";
	}

	/**
	 * 催办(权限为affair:affairFileNotice:manage，不是affair:affairFileNoticeSign:manage)
	 * @param affairLzxxjyActivitiesSign
	 * @param redirectAttributes
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = "urge")
	public String urge(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign, RedirectAttributes redirectAttributes) {
		affairLzxxjyActivitiesSignService.urge(affairLzxxjyActivitiesSign);
		addMessage(redirectAttributes, "催办成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLzxxjyActivitiesSign/?repage&noticeId="+affairLzxxjyActivitiesSign.getNoticeId();
	}

	@RequestMapping(value = "oneKeyUrge")
	public String oneKeyUrge(AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign, RedirectAttributes redirectAttributes){
		List<AffairLzxxjyActivitiesSign> notSignList =affairLzxxjyActivitiesSignService.findNotSign(affairLzxxjyActivitiesSign);

		for (AffairLzxxjyActivitiesSign sign:notSignList){
			affairLzxxjyActivitiesSignService.urge(sign);
		}
		addMessage(redirectAttributes, "一键催办成功");

		return "redirect:"+Global.getAdminPath()+"/affair/affairLzxxjyActivitiesSign/?repage&noticeId="+affairLzxxjyActivitiesSign.getNoticeId();

	}

	/**
	 * 批量签收
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLzxxjyActivitiesSign:edit")
	@RequestMapping(value = {"sign"})
	public Result sign(@RequestParam("ids[]") List<String> ids, RedirectAttributes redirectAttributes) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLzxxjyActivitiesSignService.sign(ids);
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
	@RequiresPermissions("affair:affairLzxxjyActivitiesSign:edit")
	@RequestMapping(value = {"signOne"})
	public Result signOne(String id) {
		affairLzxxjyActivitiesSignService.signOne(id);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("签收成功");
		return result;
	}

}