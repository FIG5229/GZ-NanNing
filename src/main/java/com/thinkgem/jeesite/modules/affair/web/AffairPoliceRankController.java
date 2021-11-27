/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceRank;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliceRankService;
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
 * 警衔管理表Controller
 * @author mason.xv
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPoliceRank")
public class AffairPoliceRankController extends BaseController {

	@Autowired
	private AffairPoliceRankService affairPoliceRankService;
	
	@ModelAttribute
	public AffairPoliceRank get(@RequestParam(required=false) String id) {
		AffairPoliceRank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPoliceRankService.get(id);
		}
		if (entity == null){
			entity = new AffairPoliceRank();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPoliceRank:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPoliceRank affairPoliceRank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPoliceRank> page = affairPoliceRankService.findPage(new Page<AffairPoliceRank>(request, response), affairPoliceRank); 
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceRankList";
	}

	@RequiresPermissions("affair:affairPoliceRank:view")
	@RequestMapping(value = "form")
	public String form(AffairPoliceRank affairPoliceRank, Model model) {
		model.addAttribute("affairPoliceRank", affairPoliceRank);
		return "modules/affair/affairPoliceRankForm";
	}

	@RequiresPermissions("affair:affairPoliceRank:edit")
	@RequestMapping(value = "save")
	public String save(AffairPoliceRank affairPoliceRank, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPoliceRank)){
			return form(affairPoliceRank, model);
		}
		affairPoliceRankService.save(affairPoliceRank);
		addMessage(redirectAttributes, "保存警衔管理表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPoliceRankForm";
	}
	
	@RequiresPermissions("affair:affairPoliceRank:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPoliceRank affairPoliceRank, RedirectAttributes redirectAttributes) {
		affairPoliceRankService.delete(affairPoliceRank);
		addMessage(redirectAttributes, "删除警衔管理表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceRank/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPoliceRank:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPoliceRankService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairPoliceRank:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPoliceRank affairPoliceRank, Model model) {
		model.addAttribute("affairPoliceRank", affairPoliceRank);
		return "modules/affair/affairPoliceRankFormDetail";
	}
}