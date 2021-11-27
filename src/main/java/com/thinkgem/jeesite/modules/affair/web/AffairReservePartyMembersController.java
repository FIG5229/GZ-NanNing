package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyMemberService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/affair/affairReservePartyMembers")
public class AffairReservePartyMembersController extends BaseController {

    @Autowired
    private DictDao dictDao;

    @Autowired
    private AffairPartyMemberService affairPartyMemberService;

    @ModelAttribute
    public AffairPartyMember get(@RequestParam(required=false) String id) {
        AffairPartyMember entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = affairPartyMemberService.get(id);
        }
        if (entity == null){
            entity = new AffairPartyMember();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairPartyMember:view")
    @RequestMapping(value = {"zhuanzheng"})
    public String countList (AffairPartyMember affairPartyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("treeId", affairPartyMember.getTreeId());
        String partyBranchId = affairPartyMember.getTreeId();
        Page<AffairPartyMember> pageCount = affairPartyMemberService.zhuanzheng(new Page<AffairPartyMember>(request, response), affairPartyMember);
        model.addAttribute("page", pageCount);
        return "modules/affair/affairReservePartyMembers";
    }

    @RequiresPermissions("affair:affairPartyMember:view")
    @RequestMapping(value = "form")
    public String form(AffairPartyMember affairPartyMember, Model model) {
        model.addAttribute("affairPartyMember", affairPartyMember);
        return "modules/affair/affairPartyMemberForm";
    }

    @RequiresPermissions("affair:affairPartyMember:edit")
    @RequestMapping(value = "save")
    public String save(AffairPartyMember affairPartyMember, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, affairPartyMember)){
            return form(affairPartyMember, model);
        }
        affairPartyMemberService.save(affairPartyMember);
        addMessage(redirectAttributes, "保存党员信息成功");
        request.setAttribute("saveResult","sucess");
        return "modules/affair/affairReservePartyMembers";
    }

    @RequiresPermissions("affair:affairPartyMember:edit")
    @RequestMapping(value = "deletePerson")
    public String delete(AffairPartyMember affairPartyMember, RedirectAttributes redirectAttributes) {
        affairPartyMemberService.delete(affairPartyMember);
        addMessage(redirectAttributes, "删除党员信息成功");
        return "redirect:"+ Global.getAdminPath()+"/affair/affairReservePartyMembers/zhuanzheng?repage&treeId="+affairPartyMember.getTreeId();
    }

    @RequiresPermissions("affair:affairPartyMember:manage")
    @RequestMapping(value = {"dialog"})
    public String dialog(AffairPartyMember affairPartyMember, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/affair/affairReservePartyMembersCheckDialog";
    }

    @RequiresPermissions("affair:affairPartyMember:manage")
    @RequestMapping(value = "shenHe")
    public String shenHe(AffairPartyMember affairPartyMember, RedirectAttributes redirectAttributes) {
        affairPartyMemberService.shenHe(affairPartyMember);
        addMessage(redirectAttributes, "审核党组织换届选举成功");
        return "modules/affair/affairReservePartyMembersCheckDialog";
    }
}
