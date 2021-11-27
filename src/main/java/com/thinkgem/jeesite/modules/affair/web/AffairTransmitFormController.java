/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.dao.AffairTransmitPersonDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTransmitForm;
import com.thinkgem.jeesite.modules.affair.entity.AffairTransmitPerson;
import com.thinkgem.jeesite.modules.affair.service.AffairTransmitFormService;
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
 * 传递单Controller
 * @author mason.xv
 * @version 2019-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTransmitForm")
public class AffairTransmitFormController extends BaseController {

	@Autowired
	private AffairTransmitFormService affairTransmitFormService;

	@Autowired
	private AffairTransmitPersonDao affairTransmitPersonDao;

	@ModelAttribute
	public AffairTransmitForm get(@RequestParam(required=false) String id) {
		AffairTransmitForm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTransmitFormService.get(id);
		}
		if (entity == null){
			entity = new AffairTransmitForm();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTransmitForm:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTransmitForm affairTransmitForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTransmitForm> page = affairTransmitFormService.findPage(new Page<AffairTransmitForm>(request, response), affairTransmitForm); 
		model.addAttribute("page", page);
		return "modules/affair/affairTransmitFormList";
	}

	@RequiresPermissions("affair:affairTransmitForm:view")
	@RequestMapping(value = "form")
	public String form(AffairTransmitForm affairTransmitForm, Model model) {
		AffairTransmitPerson affairTransmitPerson = new AffairTransmitPerson();
		affairTransmitPerson.setFormId(affairTransmitForm.getId());
		model.addAttribute("affairTransmitForm", affairTransmitForm);
		return "modules/affair/affairTransmitFormForm";
	}

	@RequiresPermissions("affair:affairTransmitForm:edit")
	@RequestMapping(value = "save")
	public String save(AffairTransmitForm affairTransmitForm, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTransmitForm)){
			return form(affairTransmitForm, model);
		}
		affairTransmitFormService.save(affairTransmitForm);
		addMessage(redirectAttributes, "保存传递单成功");
        request.setAttribute("saveResult","success");
        return "modules/affair/affairTransmitFormForm";
	}
	
	@RequiresPermissions("affair:affairTransmitForm:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTransmitForm affairTransmitForm, RedirectAttributes redirectAttributes) {
		affairTransmitPersonDao.delete(new AffairTransmitPerson(affairTransmitForm.getId()));
		affairTransmitFormService.delete(affairTransmitForm);
		addMessage(redirectAttributes, "删除传递单成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTransmitForm/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTransmitForm:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
		/*	affairTransmitPersonDao.deleteByIds(ids);*/
			affairTransmitFormService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}