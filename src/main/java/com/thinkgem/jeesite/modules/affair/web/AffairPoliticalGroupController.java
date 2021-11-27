/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliticalGroup;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliticalGroupService;

import java.util.List;

/**
 * 党小组Controller
 *
 * @author daniel.liu
 * @version 2020-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPoliticalGroup")
public class AffairPoliticalGroupController extends BaseController {

    @Autowired
    private AffairPoliticalGroupService affairPoliticalGroupService;

    @Autowired
    private AffairGeneralSituationService affairGeneralSituationService;

    @ModelAttribute
    public AffairPoliticalGroup get(@RequestParam(required = false) String id) {
        AffairPoliticalGroup entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairPoliticalGroupService.get(id);
        }
        if (entity == null) {
            entity = new AffairPoliticalGroup();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairPoliticalGroup:view")
    @RequestMapping(value = {"list", ""})
    public String list(AffairPoliticalGroup affairPoliticalGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
        String treeId = request.getParameter("treeId");
        //左侧数目录传过来的parentId参数数parentIds 其他页面为正常的parentId
        String parentId = request.getParameter("parentId");
        if (StringUtils.isEmpty(parentId))
            parentId = request.getParameter("parentIds");
        affairPoliticalGroup.setParentId(parentId);
        affairPoliticalGroup.setTreeId(treeId);
        Page<AffairPoliticalGroup> page = affairPoliticalGroupService.findPage(new Page<AffairPoliticalGroup>(request, response), affairPoliticalGroup);
        model.addAttribute("page", page);
        model.addAttribute("treeId", treeId);
        model.addAttribute("parentId", parentId);
        return "modules/affair/affairPoliticalGroupList";
    }

    @RequiresPermissions("affair:affairPoliticalGroup:view")
    @RequestMapping(value = "form")
    public String form(AffairPoliticalGroup affairPoliticalGroup, Model model, HttpServletRequest request) {
        String parentId = request.getParameter("parentId");
        String parentIds = request.getParameter("parentIds")+parentId+",";
        model.addAttribute("parentId", parentId);
        model.addAttribute("parentIds", parentIds);
        model.addAttribute("affairPoliticalGroup", affairPoliticalGroup);
        return "modules/affair/affairPoliticalGroupForm";
    }

    @RequiresPermissions("affair:affairPoliticalGroup:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(AffairPoliticalGroup affairPoliticalGroup, Model model) {
        model.addAttribute("affairPoliticalGroup", affairPoliticalGroup);
        return "modules/affair/affairPoliticalGroupFormDetail";
    }

    @RequiresPermissions("affair:affairPoliticalGroup:edit")
    @RequestMapping(value = "save")
    public String save(AffairPoliticalGroup affairPoliticalGroup, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, affairPoliticalGroup)) {
            return form(affairPoliticalGroup, model, request);
        }
        //检查是否存在相同名字的党小组
        String hasName=affairGeneralSituationService.findByName(affairPoliticalGroup.getGroupName());
        if (!StringUtils.isEmpty(hasName)){
            request.setAttribute("saveResult", "faile");
            return "modules/affair/affairPoliticalGroupForm";
        }
        //生成党小组目录信息
        String parentId = request.getParameter("parentId");
        AffairGeneralSituation generalSituation = new AffairGeneralSituation();
        generalSituation.setParentId(parentId);
        generalSituation.setOfPartyOrgId(parentId);
        generalSituation.setFoundDate(affairPoliticalGroup.getTime());
        generalSituation.setPartyOrganization(affairPoliticalGroup.getGroupName());
        //保存党小组树级目录
        generalSituation.setParentIds(affairPoliticalGroup.getParentIds());
        affairGeneralSituationService.save(generalSituation);
        addMessage(redirectAttributes, "保存党小组成功");
        //获取保存的党小组treeId保存到党小组信息中 便于查询数据
        String treeId=affairGeneralSituationService.findByName(affairPoliticalGroup.getGroupName());
        //保存党小组
        affairPoliticalGroup.setTreeId(treeId);
        affairPoliticalGroupService.save(affairPoliticalGroup);
        request.setAttribute("saveResult", "sucess");
        request.setAttribute("parentId", parentId);
        return "modules/affair/affairGeneralSituationList";
//		return "redirect:"+Global.getAdminPath()+"/affair/affairGeneralSituation/list?treeId="+treeId;
    }

    @RequiresPermissions("affair:affairPoliticalGroup:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairPoliticalGroup affairPoliticalGroup, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        affairPoliticalGroupService.delete(affairPoliticalGroup);
        AffairGeneralSituation ags=new AffairGeneralSituation();
        ags.setId(affairPoliticalGroup.getTreeId());
        affairGeneralSituationService.delete(ags);
        addMessage(redirectAttributes, "删除党小组成功");
        String treeId = request.getParameter("treeId");
        return "redirect:" + Global.getAdminPath() + "/affair/affairPoliticalGroup/?repage&parentId="+treeId;
    }

    @ResponseBody
    @RequiresPermissions("affair:affairPoliticalGroup:edit")
    @RequestMapping(value = "deleteByIds")
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {

        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairPoliticalGroupService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;

    }

}