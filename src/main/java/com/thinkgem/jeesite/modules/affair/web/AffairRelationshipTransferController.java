/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairRelationshipTransfer;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyMemberService;
import com.thinkgem.jeesite.modules.affair.service.AffairRelationshipTransferService;
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
 * 系统内组织关系移交转接Controller
 * @author eav.liu
 * @version 2019-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairRelationshipTransfer")
public class AffairRelationshipTransferController extends BaseController {

	@Autowired
	private AffairRelationshipTransferService affairRelationshipTransferService;
	@Autowired
	private AffairPartyMemberService affairPartyMemberService;
	
	@ModelAttribute
	public AffairRelationshipTransfer get(@RequestParam(required=false) String id) {
		AffairRelationshipTransfer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairRelationshipTransferService.get(id);
		}
		if (entity == null){
			entity = new AffairRelationshipTransfer();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairRelationshipTransfer:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairRelationshipTransfer affairRelationshipTransfer, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairRelationshipTransfer.getTreeId());
		model.addAttribute("pmId", affairRelationshipTransfer.getIdNumber());
		affairRelationshipTransfer.setHasManageAuth(false);
		Page<AffairRelationshipTransfer> page = affairRelationshipTransferService.findPage(new Page<AffairRelationshipTransfer>(request, response), affairRelationshipTransfer); 
		model.addAttribute("page", page);
		return "modules/affair/affairRelationshipTransferList";
	}

	@RequiresPermissions("affair:affairRelationshipTransfer:view")
	@RequestMapping(value = "form")
	public String form(AffairRelationshipTransfer affairRelationshipTransfer, Model model) {
		if (!StringUtils.isEmpty(affairRelationshipTransfer.getIdNumber())){
			AffairPartyMember partyMember=new AffairPartyMember();
			partyMember.setCardNum(affairRelationshipTransfer.getIdNumber());
			/*使用身份证号 通过党员名册获取相关数据*/
			partyMember=affairPartyMemberService.byIdNumber(partyMember);
			affairRelationshipTransfer.setName(partyMember.getName());
			affairRelationshipTransfer.setIdNumber(partyMember.getCardNum());
			affairRelationshipTransfer.setOldOrganization(partyMember.getPartyBranch());
			affairRelationshipTransfer.setOldOrganizationId(partyMember.getPartyBranchId());
		}
		model.addAttribute("affairRelationshipTransfer", affairRelationshipTransfer);
		return "modules/affair/affairRelationshipTransferForm";
	}

	@RequiresPermissions("affair:affairRelationshipTransfer:edit")
	@RequestMapping(value = "save")
	public String save(AffairRelationshipTransfer affairRelationshipTransfer, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairRelationshipTransfer)){
			return form(affairRelationshipTransfer, model);
		}
		if(affairRelationshipTransfer.getNowOrganization().indexOf(",")==0){
			affairRelationshipTransfer.setNowOrganization(affairRelationshipTransfer.getNowOrganization().replace(",",""));
		}
		affairRelationshipTransferService.save(affairRelationshipTransfer);
		addMessage(redirectAttributes, "保存系统内组织关系移交转接成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairRelationshipTransferForm";
	}
	
	@RequiresPermissions("affair:affairRelationshipTransfer:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairRelationshipTransfer affairRelationshipTransfer, RedirectAttributes redirectAttributes) {
		affairRelationshipTransferService.delete(affairRelationshipTransfer);
		addMessage(redirectAttributes, "删除系统内组织关系移交转接成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairRelationshipTransfer/?repage&treeId="+affairRelationshipTransfer.getTreeId()+"&idNumber="+affairRelationshipTransfer.getIdNumber();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairRelationshipTransfer:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairRelationshipTransferService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 管理审核页面
	 * @param affairRelationshipTransfer
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairRelationshipTransfer:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairRelationshipTransfer affairRelationshipTransfer, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairRelationshipTransfer.getTreeId());
		model.addAttribute("pmId", affairRelationshipTransfer.getIdNumber());
		affairRelationshipTransfer.setHasManageAuth(true);
		Page<AffairRelationshipTransfer> page = affairRelationshipTransferService.findPage(new Page<AffairRelationshipTransfer>(request, response), affairRelationshipTransfer);
		model.addAttribute("page", page);
		return "modules/affair/affairRelationshipTransferManage";
	}

	@RequiresPermissions("affair:affairRelationshipTransfer:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairRelationshipTransferShenHeDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairRelationshipTransfer:manage")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairRelationshipTransfer affairRelationshipTransfer) {
		affairRelationshipTransferService.shenHe(affairRelationshipTransfer);
		affairRelationshipTransferService.updateInfo(affairRelationshipTransfer);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	/**
	 * 党员名册-详情查看-组织关系转接
	 * @param affairRelationshipTransfer
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "partyMemberForm")
	public String partyMemberForm(AffairRelationshipTransfer affairRelationshipTransfer, Model model) {
		model.addAttribute("affairRelationshipTransfer", affairRelationshipTransfer);
		return "modules/affair/affairPartyMemberTransferForm";
	}
}