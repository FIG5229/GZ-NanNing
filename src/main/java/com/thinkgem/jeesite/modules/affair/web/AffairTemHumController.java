/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTemHum;
import com.thinkgem.jeesite.modules.affair.service.AffairTemHumService;
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
 * 库房温湿度测试记录Controller
 * @author mason.xv
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTemHum")
public class AffairTemHumController extends BaseController {

	@Autowired
	private AffairTemHumService affairTemHumService;
	
	@ModelAttribute
	public AffairTemHum get(@RequestParam(required=false) String id) {
		AffairTemHum entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTemHumService.get(id);
		}
		if (entity == null){
			entity = new AffairTemHum();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTemHum:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTemHum affairTemHum, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTemHum> page = affairTemHumService.findPage(new Page<AffairTemHum>(request, response), affairTemHum); 
		model.addAttribute("page", page);
		return "modules/affair/affairTemHumList";
	}

	@RequiresPermissions("affair:affairTemHum:view")
	@RequestMapping(value = "form")
	public String form(AffairTemHum affairTemHum, Model model) {
		model.addAttribute("affairTemHum", affairTemHum);
		return "modules/affair/affairTemHumForm";
	}

	@RequiresPermissions("affair:affairTemHum:edit")
	@RequestMapping(value = "save")
	public String save(AffairTemHum affairTemHum, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request) {
		if (!beanValidator(model, affairTemHum)){
			return form(affairTemHum, model);
		}
		affairTemHumService.save(affairTemHum);
		addMessage(redirectAttributes, "保存库房温湿度测试记录成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTemHumForm";
	}
	
	@RequiresPermissions("affair:affairTemHum:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTemHum affairTemHum, RedirectAttributes redirectAttributes) {
		affairTemHumService.delete(affairTemHum);
		addMessage(redirectAttributes, "删除库房温湿度测试记录成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTemHum/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTemHum:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTemHumService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairTemHum:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTemHum affairTemHum, Model model) {
		model.addAttribute("affairTemHum", affairTemHum);
		return "modules/affair/affairTemHumFormDetail";
	}
}