/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairBorrow;
import com.thinkgem.jeesite.modules.affair.service.AffairBorrowService;
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
 * 档案台账查阅表Controller
 * @author mason.xv
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairBorrow")
public class AffairBorrowController extends BaseController {

	@Autowired
	private AffairBorrowService affairBorrowService;
	
	@ModelAttribute
	public AffairBorrow get(@RequestParam(required=false) String id) {
		AffairBorrow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairBorrowService.get(id);
		}
		if (entity == null){
			entity = new AffairBorrow();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairBorrow:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairBorrow affairBorrow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairBorrow> page = affairBorrowService.findPage(new Page<AffairBorrow>(request, response), affairBorrow); 
		model.addAttribute("page", page);
		return "modules/affair/affairBorrowList";
	}

	@RequiresPermissions("affair:affairBorrow:view")
	@RequestMapping(value = "form")
	public String form(AffairBorrow affairBorrow, Model model) {
		model.addAttribute("affairBorrow", affairBorrow);
		return "modules/affair/affairBorrowForm";
	}

	@RequiresPermissions("affair:affairBorrow:edit")
	@RequestMapping(value = "save")
	public String save(AffairBorrow affairBorrow, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairBorrow)){
			return form(affairBorrow, model);
		}
		affairBorrowService.save(affairBorrow);
		addMessage(redirectAttributes, "保存档案台账借阅表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairBorrowForm";
	}
	
	@RequiresPermissions("affair:affairBorrow:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairBorrow affairBorrow, RedirectAttributes redirectAttributes) {
		affairBorrowService.delete(affairBorrow);
		addMessage(redirectAttributes, "删除档案台账借阅表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairBorrow/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairBorrow:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairBorrowService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairBorrow:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairBorrow affairBorrow, Model model) {
		model.addAttribute("affairBorrow", affairBorrow);
		return "modules/affair/affairBorrowFormDetail";
	}
}