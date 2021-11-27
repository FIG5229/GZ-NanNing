/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelHometownAddress;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelHometownAddressService;
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
 * 籍贯地址Controller
 * @author cecil.li
 * @version 2020-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelHometownAddress")
public class PersonnelHometownAddressController extends BaseController {

	@Autowired
	private PersonnelHometownAddressService personnelHometownAddressService;
	
	@ModelAttribute
	public PersonnelHometownAddress get(@RequestParam(required=false) String id) {
		PersonnelHometownAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelHometownAddressService.get(id);
		}
		if (entity == null){
			entity = new PersonnelHometownAddress();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelHometownAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelHometownAddress personnelHometownAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonnelHometownAddress> page = personnelHometownAddressService.findPage(new Page<PersonnelHometownAddress>(request, response), personnelHometownAddress); 
		model.addAttribute("page", page);
		return "modules/personnel/personnelHometownAddressList";
	}

	@RequiresPermissions("personnel:personnelHometownAddress:view")
	@RequestMapping(value = "form")
	public String form(PersonnelHometownAddress personnelHometownAddress, Model model) {
		model.addAttribute("personnelHometownAddress", personnelHometownAddress);
		return "modules/personnel/personnelHometownAddressForm";
	}

	@RequiresPermissions("personnel:personnelHometownAddress:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelHometownAddress personnelHometownAddress, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelHometownAddress)){
			return form(personnelHometownAddress, model);
		}
		personnelHometownAddressService.save(personnelHometownAddress);
		addMessage(redirectAttributes, "保存籍贯地址成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelHometownAddressForm";
	}
	
	@RequiresPermissions("personnel:personnelHometownAddress:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelHometownAddress personnelHometownAddress, RedirectAttributes redirectAttributes) {
		personnelHometownAddressService.delete(personnelHometownAddress);
		addMessage(redirectAttributes, "删除籍贯地址成功");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelHometownAddress/?repage";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelHometownAddress:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelHometownAddressService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}