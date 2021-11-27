/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamOtherDataChild;
import com.thinkgem.jeesite.modules.exam.service.ExamOtherDataChildService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowDefineService;
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

/**
 * 其他数据子表Controller
 *
 * @author eav.liu
 * @version 2020-03-11
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examOtherDataChild")
public class ExamOtherDataChildController extends BaseController {

    @Autowired
    private ExamOtherDataChildService examOtherDataChildService;

    @Autowired
    private ExamWorkflowDefineService examWorkflowDefineService;

    @ModelAttribute
    public ExamOtherDataChild get(@RequestParam(required = false) String id) {
        ExamOtherDataChild entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = examOtherDataChildService.get(id);
        }
        if (entity == null) {
            entity = new ExamOtherDataChild();
        }
        return entity;
    }

    @RequiresPermissions("exam:examOtherDataChild:view")
    @RequestMapping(value = {"list", ""})
    public String list(ExamOtherDataChild examOtherDataChild, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExamOtherDataChild> page = examOtherDataChildService.findPage(new Page<ExamOtherDataChild>(request, response), examOtherDataChild);
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("page", page);
        return "modules/exam/examOtherDataChildList";
    }

    @RequiresPermissions("exam:examOtherDataChild:view")
    @RequestMapping(value = "form")
    public String form(ExamOtherDataChild examOtherDataChild, Model model) {
        model.addAttribute("examOtherDataChild", examOtherDataChild);
        return "modules/exam/examCheckChildForm";
    }

    @RequiresPermissions("exam:examOtherDataChild:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(ExamOtherDataChild examOtherDataChild, Model model) {
        model.addAttribute("examOtherDataChild", examOtherDataChild);
        return "modules/exam/examOtherDataChildFormDetail";
    }

    @RequiresPermissions("exam:examOtherDataChild:edit")
    @RequestMapping(value = "save")
    public String save(ExamOtherDataChild examOtherDataChild, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, examOtherDataChild)) {
            return form(examOtherDataChild, model);
        }
        examOtherDataChildService.save(examOtherDataChild);
        addMessage(redirectAttributes, "保存其他数据成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examOtherDataChild/?repage";
    }

    @RequiresPermissions("exam:examOtherDataChild:edit")
    @RequestMapping(value = "delete")
    public String delete(ExamOtherDataChild examOtherDataChild, RedirectAttributes redirectAttributes) {
        examOtherDataChildService.delete(examOtherDataChild);
        addMessage(redirectAttributes, "删除问题录入成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examOtherDataChild/?repage";
    }

}