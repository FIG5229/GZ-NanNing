/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairYearThreeOne;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.service.AffairYearThreeOneService;
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
import java.util.Map;

/**
 * &ldquo;三会一课&rdquo;录入Controller
 * @author eav.liu
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairYearThreeOne")
public class AffairYearThreeOneController extends BaseController {

	@Autowired
	private AffairYearThreeOneService affairYearThreeOneService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;
	
	@ModelAttribute
	public AffairYearThreeOne get(@RequestParam(required=false) String id) {
		AffairYearThreeOne entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairYearThreeOneService.get(id);
		}
		if (entity == null){
			entity = new AffairYearThreeOne();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairYearThreeOne:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairYearThreeOne affairYearThreeOne, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairYearThreeOne.getTreeId());
		affairYearThreeOne.setHasAuth(false);
		Page<AffairYearThreeOne> page = affairYearThreeOneService.findPage(new Page<AffairYearThreeOne>(request, response), affairYearThreeOne);
		model.addAttribute("page", page);
		return "modules/affair/affairYearThreeOneList";
	}

	@RequiresPermissions("affair:affairYearThreeOne:view")
	@RequestMapping(value = "form")
	public String form(AffairYearThreeOne affairYearThreeOne, Model model, HttpServletRequest request) {
		String treeId = request.getParameter("treeId");
		if (StringUtils.isNotBlank(treeId)){
            String partyOrganization = affairGeneralSituationService.selectPartyOrganizationById(treeId);
            affairYearThreeOne.setPartyOrganization(partyOrganization);
            affairYearThreeOne.setPartyOrganizationId(treeId);
        }
		model.addAttribute("affairYearThreeOne", affairYearThreeOne);
		return "modules/affair/affairYearThreeOneForm";
	}

	@RequiresPermissions("affair:affairYearThreeOne:edit")
	@RequestMapping(value = "save")
	public String save(AffairYearThreeOne affairYearThreeOne, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairYearThreeOne)){
			return form(affairYearThreeOne, model,request);
		}
		affairYearThreeOneService.save(affairYearThreeOne);
		addMessage(redirectAttributes, "保存&ldquo;三会一课&rdquo;录入成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairYearThreeOneForm";
	}
	
	@RequiresPermissions("affair:affairYearThreeOne:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairYearThreeOne affairYearThreeOne, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		affairYearThreeOneService.delete(affairYearThreeOne);
		addMessage(redirectAttributes, "删除&ldquo;三会一课&rdquo;录入成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairYearThreeOne/?repage&treeId="+affairYearThreeOne.getTreeId();
	}

	/**
	 * 管理页面
	 * @param affairYearThreeOne
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairYearThreeOne:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairYearThreeOne affairYearThreeOne, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairYearThreeOne.getTreeId());
		affairYearThreeOne.setHasAuth(true);
		Page<AffairYearThreeOne> page = affairYearThreeOneService.findPage(new Page<AffairYearThreeOne>(request, response), affairYearThreeOne);
		model.addAttribute("page", page);
		return "modules/affair/affairYearThreeOneManage";
	}

	@RequiresPermissions("affair:affairYearThreeOne:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairYearThreeOneDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairYearThreeOne:manage")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairYearThreeOne affairYearThreeOne) {
		affairYearThreeOneService.shenHeSave(affairYearThreeOne);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairYearThreeOne:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairYearThreeOneService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 * @param affairYearThreeOne
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairYearThreeOne:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairYearThreeOne affairYearThreeOne, Model model) {
		model.addAttribute("affairYearThreeOne", affairYearThreeOne);
		if (affairYearThreeOne.getFilePath() != null && affairYearThreeOne.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairYearThreeOne.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairYearThreeOneFormDetail";
	}
}