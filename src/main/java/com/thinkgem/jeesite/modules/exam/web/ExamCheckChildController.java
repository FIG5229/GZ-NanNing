/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheckChild;
import com.thinkgem.jeesite.modules.exam.service.ExamCheckChildService;
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
import java.util.List;
import java.util.Map;

/**
 * 检查发现情况录入子表Controller
 *
 * @author eav.liu
 * @version 2020-03-11
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examCheckChild")
public class ExamCheckChildController extends BaseController {

    @Autowired
    private ExamCheckChildService examCheckChildService;

    @Autowired
    private ExamWorkflowDefineService examWorkflowDefineService;

    @Autowired
    private UploadController uploadController;

    @ModelAttribute
    public ExamCheckChild get(@RequestParam(required = false) String id) {
        ExamCheckChild entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = examCheckChildService.get(id);
        }
        if (entity == null) {
            entity = new ExamCheckChild();
        }
        return entity;
    }

    @RequiresPermissions("exam:examCheckChild:view")
    @RequestMapping(value = {"list", ""})
    public String list(ExamCheckChild examCheckChild, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExamCheckChild> page = examCheckChildService.findPage(new Page<ExamCheckChild>(request, response), examCheckChild);
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("page", page);
        return "modules/exam/examCheckChildList";
    }

    @RequiresPermissions("exam:examCheckChild:view")
    @RequestMapping(value = "form")
    public String form(ExamCheckChild examCheckChild, Model model) {
        model.addAttribute("examCheckChild", examCheckChild);
        return "modules/exam/examCheckChildForm";
    }

    @RequiresPermissions("exam:examCheckChild:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(ExamCheckChild examCheckChild, Model model) {
        model.addAttribute("examCheckChild", examCheckChild);
        if(examCheckChild.getAppendfile() != null && examCheckChild.getAppendfile().length() > 0){
            List<Map<String, String>> filePathList = uploadController.filePathHandle(examCheckChild.getAppendfile());
            model.addAttribute("filePathList", filePathList);
        }
        return "modules/exam/examCheckChildFormDetail";
    }

    /*@RequiresPermissions("exam:examCheckChild:edit")*/
    @RequestMapping(value = "save")
    public String save(ExamCheckChild examCheckChild, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, examCheckChild)) {
            return form(examCheckChild, model);
        }
        examCheckChildService.save(examCheckChild);
        addMessage(redirectAttributes, "保存问题录入成功");
        model.addAttribute("saveResult","success");
        return "modules/exam/examCheckChildForm";
    }

    @RequiresPermissions("exam:examCheckChild:edit")
    @RequestMapping(value = "delete")
    public String delete(ExamCheckChild examCheckChild, RedirectAttributes redirectAttributes) {
        examCheckChildService.delete(examCheckChild);
        addMessage(redirectAttributes, "删除问题录入成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examCheckChild/?repage";
    }

}