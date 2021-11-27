/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceChild;
import com.thinkgem.jeesite.modules.affair.service.AffairAttendanceChildService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
 * 考勤记录子表Controller
 *
 * @author eav.liu
 * @version 2020-03-18
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAttendanceChild")
public class AffairAttendanceChildController extends BaseController {

    @Autowired
    private AffairAttendanceChildService affairAttendanceChildService;

    @ModelAttribute
    public AffairAttendanceChild get(@RequestParam(required = false) String id) {
        AffairAttendanceChild entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairAttendanceChildService.get(id);
        }
        if (entity == null) {
            entity = new AffairAttendanceChild();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairAttendanceChild:view")
    @RequestMapping(value = {"list", ""})
    public String list(AffairAttendanceChild affairAttendanceChild, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairAttendanceChild> page = affairAttendanceChildService.findPage(new Page<AffairAttendanceChild>(request, response), affairAttendanceChild);
        model.addAttribute("page", page);
        return "modules/affair/affairAttendanceChildList";
    }

    @RequiresPermissions("affair:affairAttendanceChild:view")
    @RequestMapping(value = "form")
    public String form(AffairAttendanceChild affairAttendanceChild, Model model) {
        model.addAttribute("affairAttendanceChild", affairAttendanceChild);
        return "modules/affair/affairAttendanceChildForm";
    }

    @RequiresPermissions("affair:affairAttendanceChild:edit")
    @RequestMapping(value = "save")
    public String save(AffairAttendanceChild affairAttendanceChild, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, affairAttendanceChild)) {
            return form(affairAttendanceChild, model);
        }
        affairAttendanceChildService.save(affairAttendanceChild);
        addMessage(redirectAttributes, "保存考勤记录成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairAttendanceChild/?repage";
    }

    /**
     * 删除 权限为父权限affair:affairAttendance:edit
     *
     * @param affairAttendanceChild
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("affair:affairAttendance:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairAttendanceChild affairAttendanceChild, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        String unitId = request.getParameter("unitId");
        affairAttendanceChildService.delete(affairAttendanceChild);
        addMessage(redirectAttributes, "删除考勤记录成功");
        redirectAttributes.addAttribute("unitId",unitId);
        return "redirect:" + Global.getAdminPath() + "/affair/affairAttendance/list?repage";
    }

    /**
     * 详情回显 权限为affair:affairAttendance:view
     *
     * @param affairAttendanceChild
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairAttendance:view")
    @RequestMapping(value = {"detail"})
    public String detail(AffairAttendanceChild affairAttendanceChild, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("affairAttendanceChild", affairAttendanceChild);
        return "modules/affair/affairAttendanceFormDetail";
    }

    /**
     * 批量删除 权限为affair:affairAttendance:edit
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequiresPermissions("affair:affairAttendance:edit")
    @RequestMapping(value = {"deleteByIds"})
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairAttendanceChildService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;
    }

//    @RequiresPermissions("affair:affairAttendance:manage")
    @RequestMapping(value = {"unlock"})
    public String unlock(String unitId, HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String userOffice = UserUtils.getUser().getCompany().getId();
        if ("34".equals(userOffice)){
            userOffice = "777";
        }else if ("95".equals(userOffice)){
            userOffice = "888";
        }else if ("156".equals(userOffice)){
            userOffice = "999";
        }
        affairAttendanceChildService.unlock(unitId, year, month, userOffice);
        return "redirect:" + Global.getAdminPath() + "/affair/affairAttendance/list?repage&unitId="+unitId;
    }

//    @RequiresPermissions("affair:affairAttendance:manage")
    @RequestMapping(value = {"locked"})
    public String locked(String unitId, HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String userOffice = UserUtils.getUser().getCompany().getId();
        if ("34".equals(userOffice)){
            userOffice = "777";
        }else if ("95".equals(userOffice)){
            userOffice = "888";
        }else if ("156".equals(userOffice)){
            userOffice = "999";
        }
        affairAttendanceChildService.locked(unitId, year, month, userOffice);
        return "redirect:" + Global.getAdminPath() + "/affair/affairAttendance/list?repage&unitId="+unitId;
    }
}