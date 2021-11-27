/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.*;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.exam.entity.*;
import com.thinkgem.jeesite.modules.exam.service.*;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.tw.entity.TFeedback;
import com.thinkgem.jeesite.modules.tw.entity.TPoliceMessage;
import com.thinkgem.jeesite.modules.tw.service.TFeedbackService;
import com.thinkgem.jeesite.modules.tw.service.TPoliceMessageService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.tw.entity.*;
import com.thinkgem.jeesite.modules.tw.service.*;
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
import java.util.*;

/**
 * 自动考评Controller
 *
 * @author alan.wu
 * @version 2020-08-24
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examAutoEvaluation")
public class ExamAutoEvaluationController extends BaseController {

    @Autowired
    private ExamAutoEvaluationService examAutoEvaluationService;

    @Autowired
    private ExamAutoEvaluationPushService examAutoEvaluationPushService;

    @Autowired
    private AffairTwUnitAwardService affairTwUnitAwardService;

    @Autowired
    private AffairTwPersonalAwardService affairTwPersonalAwardService;

    @Autowired
    private ExamWorkflowDefineService examWorkflowDefineService;

    @Autowired
    private AffairDcwtLibraryService affairDcwtLibraryService;

    @ModelAttribute
    public ExamAutoEvaluation get(@RequestParam(required = false) String id) {
        ExamAutoEvaluation entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = examAutoEvaluationService.get(id);
        }
        if (entity == null) {
            entity = new ExamAutoEvaluation();
        }
        return entity;
    }

    @RequiresPermissions("exam:examAutoEvaluation:view")
    @RequestMapping(value = {"list", ""})
    public String list(ExamAutoEvaluation examAutoEvaluation, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isBlank(examAutoEvaluation.getEvalType())){
            examAutoEvaluation.setEvalType("1");
        }
        if (StringUtils.isBlank(examAutoEvaluation.getPeriod())){
            examAutoEvaluation.setPeriod("1");
        }
        Page<ExamAutoEvaluation> page = examAutoEvaluationService.findPage(new Page<ExamAutoEvaluation>(request, response), examAutoEvaluation);
        model.addAttribute("page", page);
        return "modules/exam/examAutoEvaluationList";
    }

//    http://localhost:8080/politics/a/exam/examAutoEvaluation/view

    /**
     * 自动考评有效数据
     * @param examAutoEvaluation
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("exam:examAutoEvaluation:view")
    @RequestMapping(value = {"view"})
    public String view(ExamAutoEvaluation examAutoEvaluation, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExamAutoEvaluation> page = examAutoEvaluationService.findPage(new Page<ExamAutoEvaluation>(request, response), examAutoEvaluation);
        model.addAttribute("page", page);
        return "modules/exam/examAutoEvaluationListView";
    }

    //    http://localhost:8080/politics/a/exam/examAutoEvaluation/problem

    /**
     * 自动考评有效数据
     * @param examAutoEvaluation
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"problem"})
    public String problem(ExamAutoEvaluation examAutoEvaluation, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExamAutoEvaluation> page = examAutoEvaluationService.findProblemPage(new Page<ExamAutoEvaluation>(request, response), examAutoEvaluation);
        model.addAttribute("page", page);
        return "modules/exam/examAutoEvaluationListProblem";
    }


    @RequiresPermissions("exam:examAutoEvaluation:view")
    @RequestMapping(value = "form")
    public String form(ExamAutoEvaluation examAutoEvaluation, Model model) {
        model.addAttribute("examAutoEvaluation", examAutoEvaluation);
        return "modules/exam/examAutoEvaluationForm";
    }

    @RequiresPermissions("exam:examAutoEvaluation:edit")
    @RequestMapping(value = "save")
    public String save(ExamAutoEvaluation examAutoEvaluation, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, examAutoEvaluation)) {
            return form(examAutoEvaluation, model);
        }
        examAutoEvaluationService.save(examAutoEvaluation);
        addMessage(redirectAttributes, "保存自动考评成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examAutoEvaluation/?repage";
    }

    @RequiresPermissions("exam:examAutoEvaluation:edit")
    @RequestMapping(value = "delete")
    public String delete(ExamAutoEvaluation examAutoEvaluation, RedirectAttributes redirectAttributes) {
        examAutoEvaluationService.delete(examAutoEvaluation);
        addMessage(redirectAttributes, "删除自动考评成功");
        return "redirect:" + Global.getAdminPath() + "/exam/examAutoEvaluation/?repage";
    }

    @RequestMapping(value = "formDetail")
    public String formDetail(ExamAutoEvaluation examAutoEvaluation, Model model) {
        model.addAttribute("examAutoEvaluation", examAutoEvaluation);
        return "modules/exam/examAutoEvaluationFormDetail";
    }

//    http://localhost:8080/politics/a/exam/examAutoEvaluation/model

    /**
     * 自动考评查询模板行号页面
     * @param examAutoEvaluation
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"index"})
    public String index(ExamAutoEvaluation examAutoEvaluation, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ExamAutoEvaluation> page = examAutoEvaluationService.findPage(new Page<ExamAutoEvaluation>(request, response), examAutoEvaluation);
        model.addAttribute("page", page);
        return "modules/exam/examAutoEvaluationHelp";
    }
    @RequestMapping(value = {"model"})
    public String model(ExamAutoEvaluation examAutoEvaluation, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<ExamStandardBaseInfo> list = examAutoEvaluationService.findModelByOptionList(examAutoEvaluation);
        model.addAttribute("list", list);
        model.addAttribute("option",examAutoEvaluation.getOption());
        return "modules/exam/examAutoEvaluationHelp";
    }

    @RequestMapping(value = {"optionDetail"})
    public String optionDetail(ExamAutoEvaluation examAutoEvaluation, HttpServletRequest request, HttpServletResponse response, Model model) {
        String standardBaseInfoId = request.getParameter("standardBaseInfoId");

        List<ExamStandardTemplateData> list = examAutoEvaluationService.findOptionListByModel(examAutoEvaluation.getOption(),standardBaseInfoId);
        model.addAttribute("list", list);
        return "modules/exam/examAutoEvaluationOptionList";
    }


    //推送奖惩库 团内奖惩单位、个人；督察问题库
    @RequestMapping(value = "pushUnitRewardsLibrary")
    public String twUnitAwardForm(@RequestParam("id") String id,ExamAutoEvaluation examAutoEvaluation, Model model,@RequestParam("pushFrom") String pushFrom) {
        if(pushFrom!=null){
            switch (pushFrom){
                case "团内奖惩单位":
                    AffairTwUnitAward affairTwUnitAward = affairTwUnitAwardService.get(id);
                    if (StringUtils.isNotBlank(affairTwUnitAward.getUnit())) {
                        examAutoEvaluation.setUnit(affairTwUnitAward.getUnit());
                        examAutoEvaluation.setUnitId(affairTwUnitAward.getUnitId());
                        examAutoEvaluation.setType("1");// 考评对象类别（1单位，2民警）
                    }
                    examAutoEvaluation.setFileNo(affairTwUnitAward.getFileNo());//奖惩文号
                    examAutoEvaluation.setJcTypeName(affairTwUnitAward.getName());
                    examAutoEvaluation.setPushFrom("团内奖惩单位");//推送来源
                    examAutoEvaluation.setTime(affairTwUnitAward.getDate());
                    //model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
                    break;
                case "团内奖惩个人" :
                    examAutoEvaluation.setType("0");// 奖励对象 0 -人 1 -单位  自动考评字典-考评对象类别（1单位，2民警），
                    AffairTwPersonalAward affairTwPersonalAward = affairTwPersonalAwardService.get(id);
                    if (StringUtils.isNotBlank(affairTwPersonalAward.getUnit())) {
                        examAutoEvaluation.setUnit(affairTwPersonalAward.getUnit());
                        examAutoEvaluation.setUnitId(affairTwPersonalAward.getUnitId());
                    }
                    examAutoEvaluation.setFileNo(affairTwPersonalAward.getFileNo());//奖惩文号
                    examAutoEvaluation.setJcTypeName(affairTwPersonalAward.getRewardName());
                    examAutoEvaluation.setIdNumber(affairTwPersonalAward.getIdNumber());
                    examAutoEvaluation.setName(affairTwPersonalAward.getName());
                    examAutoEvaluation.setPushFrom("团内奖惩个人");//推送来源
                    examAutoEvaluation.setTime(affairTwPersonalAward.getDate());
                    break;
                case "督察问题库":
                    examAutoEvaluation.setType("1");// 奖励对象 0 -人 1 -单位  自动考评字典-考评对象类别（1单位，2民警），
                    AffairDcwtLibrary affairDcwtLibrary = affairDcwtLibraryService.get(id);
                    if (StringUtils.isNotBlank(affairDcwtLibrary.getResponsibleUnitId())) {
                        examAutoEvaluation.setUnit(affairDcwtLibrary.getResponsibleUnit());
                        examAutoEvaluation.setUnitId(affairDcwtLibrary.getResponsibleUnitId());
                    }
                    examAutoEvaluation.setHappenTime(affairDcwtLibrary.getTime());
                    examAutoEvaluation.setTime(affairDcwtLibrary.getFinishDate());
                    examAutoEvaluation.setDetails(affairDcwtLibrary.getFoundProblem());
                    examAutoEvaluation.setRemark(affairDcwtLibrary.getRemark());
                    examAutoEvaluation.setPushFrom("督察问题库");//推送来源
                    model.addAttribute("examAutoEvaluation", examAutoEvaluation);
                    model.addAttribute("awardId", id);
                    return "modules/exam/examAutoEvaluationPlusDCForm";
                default:
                    if(examAutoEvaluation==null){
                        examAutoEvaluation = new ExamAutoEvaluation();
                    }
                    break;
            }
        }
        model.addAttribute("examAutoEvaluation", examAutoEvaluation);
        model.addAttribute("awardId", id);
        return "modules/exam/examAutoEvaluationPlusForm";
    }


    //推送奖惩库 -  保存来源团内奖惩推送
    @RequestMapping(value = "savePushInfo")
    public String savePushInfo(@RequestParam("awardId") String awardId,ExamAutoEvaluation examAutoEvaluation, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(StringUtils.isBlank(examAutoEvaluation.getUnitId()) || examAutoEvaluation.getUnitId().contains("null")) {
            model.addAttribute("message", "推送失败,单位选择有问题，请重新选择");
            model.addAttribute("content", "推送失败，单位选择有问题，请重新选择");
            return "modules/exam/examAutoEvaluationPlusForm";
        }
        try {
            examAutoEvaluationPushService.savePushInfo(awardId,examAutoEvaluation,request);
            model.addAttribute("message", "推送奖惩信息库成功");
            model.addAttribute("content", "推送奖惩信息库成功");
            request.setAttribute("saveResult","success");
        }catch (Exception e){
            model.addAttribute("message", "推送奖惩信息库失败");
            model.addAttribute("content", "推送奖惩信息库失败");
            request.setAttribute("saveResult","fail");
            logger.error("推送奖惩信息库发生错误，错误信息："+e.toString());
        }
        model.addAttribute("awardId", awardId);
        String pushFrom = examAutoEvaluation.getPushFrom();//推送来源
        if("督察问题库".equals(pushFrom)){
            return "modules/exam/examAutoEvaluationPlusDCForm";
        }
        return "modules/exam/examAutoEvaluationPlusForm";
    }

    @RequestMapping(value = {"jkcCjYear"})
    @ResponseBody
    public String jkcCjYear(String year) {
        examAutoEvaluationService.jkcCjYear(year);
        return year;
    }
    @RequestMapping(value = {"jkcCjMonth"})
    @ResponseBody
    public String jkcCjMonth(String checkTime) {
        examAutoEvaluationService.jkcCjMonth(checkTime);
        return checkTime;
    }
    @RequestMapping(value = {"jkjCjYear"})
    @ResponseBody
    public String jkjCjYear(String year) {
        examAutoEvaluationService.jkjCjYear(year);
        return year;
    }
    @RequestMapping(value = {"jkjCjMonth"})
    @ResponseBody
    public String jkjCjMonth(String checkTime) {
        examAutoEvaluationService.jkjCjMonth(checkTime);
        return checkTime;
    }
    @RequestMapping(value = {"ckcCjYear"})
    @ResponseBody
    public String ckcCjYear(String year) {
        examAutoEvaluationService.ckcCjYear(year);
        return year;
    }
    @RequestMapping(value = {"ckcCjMonth"})
    @ResponseBody
    public String ckcCjMonth(String checkTime) {
        examAutoEvaluationService.ckcCjMonth(checkTime);
        return checkTime;
    }
    @RequestMapping(value = {"ckpcsCjYear"})
    @ResponseBody
    public String ckpcsCjYear(String year) {
        examAutoEvaluationService.ckpcsCjYear(year);
        return year;
    }
    @RequestMapping(value = {"ckpcsCjMonth"})
    @ResponseBody
    public String ckpcsCjMonth(String checkTime) {
        examAutoEvaluationService.ckpcsCjMonth(checkTime);
        return checkTime;
    }
//-------------------------------------
    @RequestMapping(value = {"grjkcYear"})
    @ResponseBody
    public String grjkcYear(String year) {
        examAutoEvaluationService.grjkcYear(year);
        return year;
    }
    @RequestMapping(value = {"grjkcMonth"})
    @ResponseBody
    public String grjkcMonth(String checkTime) {
        examAutoEvaluationService.grjkcMonth(checkTime);
        return checkTime;
    }

    @RequestMapping(value = {"grjkjjgYear"})
    @ResponseBody
    public String grjkjjgYear(String year) {
        examAutoEvaluationService.grjkjjgYear(year);
        return year;
    }
    @RequestMapping(value = {"grjkjjgMonth"})
    @ResponseBody
    public String grjkjjgMonth(String checkTime) {
        examAutoEvaluationService.grjkjjgMonth(checkTime);
        return checkTime;
    }
    @RequestMapping(value = {"grckcjgYear"})
    @ResponseBody
    public String grckcjgYear(String year) {
        examAutoEvaluationService.grckcjgYear(year);
        return year;
    }

    @RequestMapping(value = {"grckcjgMonth"})
    @ResponseBody
    public String grckcjgMonth(String checkTime) {
        examAutoEvaluationService.grckcjgMonth(checkTime);
        return checkTime;
    }
    @RequestMapping(value = {"grckpcsYear"})
    @ResponseBody
    public String grckpcsYear(String year) {
        examAutoEvaluationService.grckpcsYear(year);
        return year;
    }

    @RequestMapping(value = {"grckpcsMonth"})
    @ResponseBody
    public String grckpcsMonth(String checkTime) {
        examAutoEvaluationService.grckpcsMonth(checkTime);
        return checkTime;
    }


}
