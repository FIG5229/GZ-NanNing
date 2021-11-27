/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairArchiveApprovalService;
import com.thinkgem.jeesite.modules.affair.service.AffairCdObjectService;
import com.thinkgem.jeesite.modules.affair.service.AffairCdPersonService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 查(借)阅审批Controller
 * @author mason.xv
 * @version 2019-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairArchiveApproval")
public class AffairArchiveApprovalController extends BaseController {

	@Autowired
	private AffairArchiveApprovalService affairArchiveApprovalService;
    @Autowired
	private AffairCdPersonService affairCdPersonService;
    @Autowired
	private AffairCdObjectService affairCdObjectService;

    @ModelAttribute
	public AffairArchiveApproval get(@RequestParam(required=false) String id) {
		AffairArchiveApproval entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairArchiveApprovalService.get(id);
		}
		if (entity == null){
			entity = new AffairArchiveApproval();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairArchiveApproval:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairArchiveApproval affairArchiveApproval, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairArchiveApproval> page = affairArchiveApprovalService.findPage(new Page<AffairArchiveApproval>(request, response), affairArchiveApproval);
		if (page.getList() != null && page.getList().size() > 0){
			for (AffairArchiveApproval a: page.getList()) {
				List<AffairCdPerson> cdPersonList = new ArrayList<>();
				cdPersonList = affairCdPersonService.findSomeByApprovelId(a.getId());
				a.setAffairCdPersonList(cdPersonList);
			}
		}
		if (page.getList() != null && page.getList().size() > 0){
			for (AffairArchiveApproval a: page.getList()) {
				List<AffairCdObject> cdObjectList = new ArrayList<>();
				cdObjectList = affairCdObjectService.findSomeByApprovelId(a.getId());
				a.setAffairCdObjectList(cdObjectList);
			}
		}
		model.addAttribute(page);
		return "modules/affair/affairArchiveApprovalList";
	}

	@RequiresPermissions("affair:affairArchiveApproval:view")
	@RequestMapping(value = "form")
	public String form(AffairArchiveApproval affairArchiveApproval, Model model) {
		AffairCdObject affairCdObject = new AffairCdObject();
		AffairCdPerson affairCdPerson = new AffairCdPerson();
		affairCdObject.setApprovalId(affairArchiveApproval.getId());
		affairCdPerson.setApprovalId(affairArchiveApproval.getId());
		model.addAttribute("affairArchiveApproval", affairArchiveApproval);
		return "modules/affair/affairArchiveApprovalForm";
	}

	@RequiresPermissions("affair:affairArchiveApproval:edit")
	@RequestMapping(value = "save")
	public String save(AffairArchiveApproval affairArchiveApproval, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairArchiveApproval)){
			return form(affairArchiveApproval, model);
		}
		affairArchiveApprovalService.save(affairArchiveApproval);
		addMessage(redirectAttributes, "保存查(借)阅审批成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairArchiveApprovalForm";
	}
	
	@RequiresPermissions("affair:affairArchiveApproval:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairArchiveApproval affairArchiveApproval, RedirectAttributes redirectAttributes) {
		affairArchiveApprovalService.delete(affairArchiveApproval);
		affairCdObjectService.delete(new AffairCdObject(affairArchiveApproval.getId()));
		affairCdPersonService.delete(new AffairCdPerson(affairArchiveApproval.getId()));
		addMessage(redirectAttributes, "删除查(借)阅审批成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairArchiveApproval/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPoliceRank:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairArchiveApprovalService.deleteByIds(ids);
			affairCdPersonService.deleteByApprovelIds(ids);
			affairCdObjectService.deleteByApprovelIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
}