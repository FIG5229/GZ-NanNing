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
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairStandard;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyMemberService;
import com.thinkgem.jeesite.modules.affair.service.AffairStandardService;
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
 * 个人合格党员标准Controller
 * @author mason.xv
 * @version 2019-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairStandard")
public class AffairStandardController extends BaseController {

	@Autowired
	private AffairStandardService affairStandardService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairPartyMemberService affairPartyMemberService;
	
	@ModelAttribute
	public AffairStandard get(@RequestParam(required=false) String id) {
		AffairStandard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairStandardService.get(id);
		}
		if (entity == null){
			entity = new AffairStandard();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairStandard:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairStandard affairStandard, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairStandard.getTreeId());
		model.addAttribute("pmId", affairStandard.getIdNumber());
		affairStandard.setHasAuth(false);
		Page<AffairStandard> page = affairStandardService.findPage(new Page<AffairStandard>(request, response), affairStandard); 
		model.addAttribute("page", page);
		return "modules/affair/affairStandardList";
	}

	@RequiresPermissions("affair:affairStandard:view")
	@RequestMapping(value = "form")
	public String form(AffairStandard affairStandard, Model model) {
		if (!StringUtils.isEmpty(affairStandard.getIdNumber())){
			AffairPartyMember partyMember=new AffairPartyMember();
			partyMember.setCardNum(affairStandard.getIdNumber());
			/*使用身份证号 通过党员名册获取相关数据*/
			partyMember=affairPartyMemberService.byIdNumber(partyMember);
			affairStandard.setName(partyMember.getName());
			affairStandard.setIdNumber(partyMember.getCardNum());
			affairStandard.setPartyOrganization(partyMember.getPartyBranch());
			affairStandard.setPartyOrganizationId(partyMember.getPartyBranchId());
		}
		model.addAttribute("affairStandard", affairStandard);
		return "modules/affair/affairStandardForm";
	}

	@RequiresPermissions("affair:affairStandard:edit")
	@RequestMapping(value = "save")
	public String save(AffairStandard affairStandard, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairStandard)){
			return form(affairStandard, model);
		}
		affairStandardService.save(affairStandard);
		addMessage(redirectAttributes, "保存个人合格党员标准成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairStandardForm";
	}
	
	@RequiresPermissions("affair:affairStandard:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairStandard affairStandard, RedirectAttributes redirectAttributes) {
		affairStandardService.delete(affairStandard);
		addMessage(redirectAttributes, "删除个人合格党员标准成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairStandard/list?repage&treeId="+affairStandard.getTreeId()+"&idNumber="+affairStandard.getIdNumber();
	}

	/**
	 * 审核管理页面
	 * @param affairStandard
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairStandard:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairStandard affairStandard, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairStandard.getTreeId());
		model.addAttribute("pmId", affairStandard.getIdNumber());
		affairStandard.setHasAuth(true);
		Page<AffairStandard> page = affairStandardService.findPage(new Page<AffairStandard>(request, response), affairStandard);
		model.addAttribute("page", page);
		return "modules/affair/affairStandardManage";
	}

	/**
	 * 打开审核的弹窗
	 * @return
	 */
	@RequiresPermissions("affair:affairStandard:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairStandardShenHeDialog";
	}

	/**
	 * 审核
	 * @param affairStandard
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairStandard:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairStandard affairStandard, RedirectAttributes redirectAttributes) {
		affairStandardService.shenHe(affairStandard);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	/**
	 * 详情
	 * @param affairStandard
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairStandard:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairStandard affairStandard, Model model) {
		model.addAttribute("affairStandard", affairStandard);
		if (affairStandard.getMaterialPath() != null && affairStandard.getMaterialPath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairStandard.getMaterialPath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairStandardFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairStandard:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairStandardService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}