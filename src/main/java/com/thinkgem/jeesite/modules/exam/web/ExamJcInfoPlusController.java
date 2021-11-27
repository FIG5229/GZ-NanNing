package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.*;
import com.thinkgem.jeesite.modules.exam.entity.ExamJcInfo;
import com.thinkgem.jeesite.modules.exam.service.ExamJcInfoService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "${adminPath}/exam/examJcInfoPlus")
public class ExamJcInfoPlusController extends BaseController{
    @Autowired
    private ExamWorkflowDefineService examWorkflowDefineService;
    @Autowired
    private AffairTwPersonalAwardService affairTwPersonalAwardService;
    @Autowired
    private AffairPersonalRewardService affairPersonalRewardService;
    @Autowired
    private AffairXcUnitRewardService affairXcUnitRewardService;
    @Autowired
    private AffairTwUnitAwardService affairTwUnitAwardService;
    @Autowired
    private AffairDisciplinaryActionService affairDisciplinaryActionService;
    @Autowired
    private ExamJcInfoService examJcInfoService;
    @Autowired
    private AffairPartyRewardPunishService affairPartyRewardPunishService;
    @Autowired
    private AffairOrgRewardPunishService affairOrgRewardPunishService;
    @Autowired
    private AffairPersonalAwardService affairPersonalAwardService;
    @Autowired
    private AffairCollectiveAwardService affairCollectiveAwardService;
    @Autowired
    private AffairDisciplinaryActionDzzService affairDisciplinaryActionDzzService;
    @Autowired
    private AffairDcwtLibraryService affairDcwtLibraryService;
    @ModelAttribute
    public ExamJcInfo get(@RequestParam(required=false) String id) {
        ExamJcInfo entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = examJcInfoService.get(id);
        }
        if (entity == null){
            entity = new ExamJcInfo();
        }
        return entity;
    }

    @RequestMapping(value = "form")
    public String form(ExamJcInfo examJcInfo, Model model) {
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("examJcInfo", examJcInfo);
        return "modules/exam/examJcInfoPlusForm";
    }

    //纪检监察-纪律处分
    @RequestMapping(value = "disciplinaryActionform")
    public String disciplinaryActionform(@RequestParam("id") String id, ExamJcInfo examJcInfo, Model model, AffairDisciplinaryAction affairDisciplinaryAction ) {
        affairDisciplinaryAction = affairDisciplinaryActionService.get(id);
            if (StringUtils.isNotBlank(affairDisciplinaryAction.getName())){
                examJcInfo.setJcObjectPersonnel(affairDisciplinaryAction.getName());
                examJcInfo.setIdNumber(affairDisciplinaryAction.getIdNumber());
                examJcInfo.setJcObject("0");
            }
            if (StringUtils.isNotBlank(affairDisciplinaryAction.getUnit())){
                examJcInfo.setJcObjectUnit(affairDisciplinaryAction.getUnit());
                examJcInfo.setJcObjectUnitId(affairDisciplinaryAction.getUnitId());
            }
            examJcInfo.setFileNum(affairDisciplinaryAction.getFileNum());
            examJcInfo.setPushNum("1");
            examJcInfo.setJcType("1");
            model.addAttribute("examJcInfo", examJcInfo);
           model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        return "modules/exam/examJcInfoPlusForm";
    }
    //纪检监察-党组织
    @RequestMapping(value = "disciplinaryActionformDzz")
    public String disciplinaryActionDzzform(@RequestParam("id") String id, ExamJcInfo examJcInfo, Model model, AffairDisciplinaryActionDzz affairDisciplinaryActionDzz ) {
        affairDisciplinaryActionDzz = affairDisciplinaryActionDzzService.get(id);
        if (StringUtils.isNotBlank(affairDisciplinaryActionDzz.getOrg())){
            examJcInfo.setJcOrg(affairDisciplinaryActionDzz.getOrg());
            examJcInfo.setJcOrgId(affairDisciplinaryActionDzz.getOrgId());
            examJcInfo.setJcObject("1");
        }
        examJcInfo.setFileNum(affairDisciplinaryActionDzz.getFileNum());
        examJcInfo.setPushNum("2");
        examJcInfo.setJcType("1");
        model.addAttribute("examJcInfo", examJcInfo);
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        return "modules/exam/examJcInfoPlusForm";
    }
    //工团-团委单位奖励
    @RequestMapping(value = "twUnitAwardForm")
    public String twUnitAwardForm(@RequestParam("id") String id,ExamJcInfo examJcInfo, Model model, AffairTwUnitAward affairTwUnitAward) {
        affairTwUnitAward = affairTwUnitAwardService.get(id);
          if (StringUtils.isNotBlank(affairTwUnitAward.getUnit())) {
              examJcInfo.setJcObjectUnit(affairTwUnitAward.getUnit());
              examJcInfo.setJcObjectUnitId(affairTwUnitAward.getUnitId());
              examJcInfo.setJcObject("1");
          }
          examJcInfo.setFileNum(affairTwUnitAward.getFileNo());
          examJcInfo.setJcTypeName(affairTwUnitAward.getName());
          examJcInfo.setPushNum("3");
          examJcInfo.setMyUseModel("3");
          examJcInfo.setJcType("0");
          model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
          model.addAttribute("examJcInfo", examJcInfo);
        return "modules/exam/examJcInfoPlusForm";
    }
    //工团-团委个人奖励
    @RequestMapping(value = "affairTwPersonalAwardForm")
    public String affairTwPersonalAwardForm(@RequestParam("id") String id, ExamJcInfo examJcInfo, Model model, AffairTwPersonalAward affairTwPersonalAward ) {
        affairTwPersonalAward = affairTwPersonalAwardService.get(id);
            if (StringUtils.isNotBlank(affairTwPersonalAward.getName())){
                examJcInfo.setJcObject("0");
                examJcInfo.setJcObjectPersonnel(affairTwPersonalAward.getName());
                examJcInfo.setIdNumber(affairTwPersonalAward.getIdNumber());
            }
            if (StringUtils.isNotBlank(affairTwPersonalAward.getUnit())){
                examJcInfo.setJcObjectUnit(affairTwPersonalAward.getUnit());
                examJcInfo.setJcObjectUnitId(affairTwPersonalAward.getUnitId());
            }
            examJcInfo.setFileNum(affairTwPersonalAward.getFileNo());
            examJcInfo.setJcTypeName(affairTwPersonalAward.getRewardName());
            examJcInfo.setPushNum("4");
            examJcInfo.setJcType("0");
            model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
            model.addAttribute("examJcInfo", examJcInfo);
             return "modules/exam/examJcInfoPlusForm";
    }
    //宣传教育-各级表彰-单位表彰
    @RequestMapping(value = "affairXcUnitRewardform")
    public String affairXcUnitRewardfom(@RequestParam("id") String id, ExamJcInfo examJcInfo, Model model, AffairXcUnitReward affairXcUnitReward ) {
        affairXcUnitReward = affairXcUnitRewardService.get(id);
        if (StringUtils.isNotBlank(affairXcUnitReward.getUnit())){
            examJcInfo.setJcObjectUnit(affairXcUnitReward.getUnit());
            examJcInfo.setJcObjectUnitId(affairXcUnitReward.getUnitId());
            examJcInfo.setJcObject("1");
        }
        examJcInfo.setFileNum(affairXcUnitReward.getFileNo());
        examJcInfo.setJcTypeName(affairXcUnitReward.getName());
        examJcInfo.setPushNum("5");
        examJcInfo.setJcType("0");
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("examJcInfo", examJcInfo);
        model.addAttribute("isAward", "true");
        return "modules/exam/examAwardInfoPlusForm";
    }

    //宣传教育-各级表彰-个人表彰
    @RequestMapping(value = "affairPersonalRewardForm")
    public String affairPersonalRewardForm(@RequestParam("id") String id, ExamJcInfo examJcInfo, Model model, AffairPersonalReward affairPersonalReward ) {
        affairPersonalReward = affairPersonalRewardService.get(id);
        if (StringUtils.isNotBlank(affairPersonalReward.getName())){
            examJcInfo.setJcObject("0");
            examJcInfo.setJcObjectPersonnel(affairPersonalReward.getName());
            examJcInfo.setIdNumber(affairPersonalReward.getIdNumber());
        }
        else if(StringUtils.isNotBlank(affairPersonalReward.getUnit())){
            examJcInfo.setJcObjectUnit(affairPersonalReward.getUnit());
            examJcInfo.setJcObjectUnitId(affairPersonalReward.getUnitId());
        }
        examJcInfo.setFileNum(affairPersonalReward.getFileNo());
        examJcInfo.setJcTypeName(affairPersonalReward.getRewardName());
        examJcInfo.setPushNum("6");
        examJcInfo.setJcType("0");
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("isAward", "true");
        return "modules/exam/examAwardInfoPlusForm";
    }

    //党建-党员奖惩
    @RequestMapping(value = "partyRewardPunishForm")
    public String partyRewardPunishForm(@RequestParam("id") String id,ExamJcInfo examJcInfo, Model model, AffairPartyRewardPunish affairPartyRewardPunish) {
        affairPartyRewardPunish = affairPartyRewardPunishService.get(id);
        if (StringUtils.isNotBlank(affairPartyRewardPunish.getName())){
            examJcInfo.setJcObject("0");
            examJcInfo.setJcTypeName(affairPartyRewardPunish.getTitle());
            examJcInfo.setJcObjectPersonnel(affairPartyRewardPunish.getName());
            examJcInfo.setIdNumber(affairPartyRewardPunish.getIdNumber());
        }
        examJcInfo.setFileNum(affairPartyRewardPunish.getFileNo());
        examJcInfo.setPushNum("7");
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("examJcInfo", examJcInfo);
        return "modules/exam/examJcInfoPlusForm";
    }
    //党建-组织奖惩
    @RequestMapping(value = "OrgRewardPunishForm")
    public String OrgRewardPunishForm(@RequestParam("id") String id, ExamJcInfo examJcInfo, Model model, AffairOrgRewardPunish affairOrgRewardPunish ) {
        affairOrgRewardPunish = affairOrgRewardPunishService.get(id);
        examJcInfo.setJcTypeName(affairOrgRewardPunish.getTitle());
        examJcInfo.setFileNum(affairOrgRewardPunish.getFileNo());
        examJcInfo.setPushNum("8");
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("examJcInfo", examJcInfo);
        return "modules/exam/examJcInfoPlusForm";
    }

    //工团-工会个人奖励
    @RequestMapping(value = "personalAwardForm")
    public String personalAwardForm(@RequestParam("id") String id,ExamJcInfo examJcInfo, Model model, AffairPersonalAward affairPersonalAward) {
        affairPersonalAward = affairPersonalAwardService.get(id);
        if (StringUtils.isNotBlank(affairPersonalAward.getPoliceName())){
            examJcInfo.setJcObject("0");
            examJcInfo.setJcObjectPersonnel(affairPersonalAward.getPoliceName());
            examJcInfo.setIdNumber(affairPersonalAward.getIdNumber());
        }
        if (StringUtils.isNotBlank(affairPersonalAward.getUnit())){
            examJcInfo.setJcObjectUnit(affairPersonalAward.getUnit());
            examJcInfo.setJcObjectUnitId(affairPersonalAward.getUnitId());
        }
        examJcInfo.setJcTypeName(affairPersonalAward.getAwardName());
        examJcInfo.setPushNum("9");
        examJcInfo.setJcType("0");
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("examJcInfo", examJcInfo);
        model.addAttribute("isAward", "true");
        return "modules/exam/examAwardInfoPlusForm";
    }
    //工团-团委单位奖励

    @RequestMapping(value = "collectiveAwardForm")
    public String collectiveAwardForm(@RequestParam("id") String id,ExamJcInfo examJcInfo, Model model, AffairCollectiveAward affairCollectiveAward) {
        affairCollectiveAward = affairCollectiveAwardService.get(id);
        if (StringUtils.isNotBlank(affairCollectiveAward.getUnit())) {
            examJcInfo.setJcObjectUnit(affairCollectiveAward.getUnit());
            examJcInfo.setJcObjectUnitId(affairCollectiveAward.getUnitId());
            examJcInfo.setJcObject("1");
        }
        examJcInfo.setJcTypeName(affairCollectiveAward.getAwardName());
        examJcInfo.setPushNum("10");
        examJcInfo.setMyUseModel("3");
        examJcInfo.setJcType("0");
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("examJcInfo", examJcInfo);
        model.addAttribute("isAward", "true");
        return "modules/exam/examAwardInfoPlusForm";
    }
//前面的controller加个数字带过来,是几就执行哪条修改pushTy pepersonalAwardForm
    @RequestMapping(value = "save")
    public String save(@RequestParam("id")String id, ExamJcInfo examJcInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if("1".equals(examJcInfo.getPushNum())){
            AffairDisciplinaryAction affairDisciplinaryAction = affairDisciplinaryActionService.get(id);
            affairDisciplinaryAction.setPushType("1");//null是未推送,1是已推送
            affairDisciplinaryActionService.save(affairDisciplinaryAction);
        }else if("2".equals(examJcInfo.getPushNum())){
            AffairDisciplinaryActionDzz affairDisciplinaryActionDzz = affairDisciplinaryActionDzzService.get(id);
            affairDisciplinaryActionDzz.setPushType("1");
            affairDisciplinaryActionDzzService.save(affairDisciplinaryActionDzz);
        } else if("3".equals(examJcInfo.getPushNum())){
            examJcInfo.setJcType("0");
            AffairTwUnitAward affairTwUnitAward = affairTwUnitAwardService.get(id);
            affairTwUnitAward.setPushType("1");//null是未推送,1是已推送
            affairTwUnitAwardService.save(affairTwUnitAward);
        }else if("4".equals(examJcInfo.getPushNum())){
            examJcInfo.setJcType("1");
            AffairTwPersonalAward affairTwPersonalAward =affairTwPersonalAwardService.get(id);
            affairTwPersonalAward.setPushType("1");//null是未推送,1是已推送
            affairTwPersonalAwardService.save(affairTwPersonalAward);
        }else if("5".equals(examJcInfo.getPushNum())){
            examJcInfo.setJcType("0");
            AffairXcUnitReward affairXcUnitReward = affairXcUnitRewardService.get(id);
            affairXcUnitReward.setPushType("1");//null是未推送,1是已推送
            affairXcUnitRewardService.save(affairXcUnitReward);
        }else if("6".equals(examJcInfo.getPushNum())){
            examJcInfo.setJcType("1");
            AffairPersonalReward affairPersonalReward = affairPersonalRewardService.get(id);
            affairPersonalReward.setPushType("1");//null是未推送,1是已推送
            affairPersonalRewardService.save(affairPersonalReward);
        }else if("7".equals(examJcInfo.getPushNum())){
            AffairPartyRewardPunish affairPartyRewardPunish = affairPartyRewardPunishService.get(id);
            affairPartyRewardPunish.setPushType("1");//null是未推送,1是已推送
            affairPartyRewardPunishService.save(affairPartyRewardPunish);
        }else if("8".equals(examJcInfo.getPushNum())){
            AffairOrgRewardPunish affairOrgRewardPunish = affairOrgRewardPunishService.get(id);
            affairOrgRewardPunish.setPushType("1");//null是未推送,1是已推送
            affairOrgRewardPunishService.save(affairOrgRewardPunish);
        }else if("9".equals(examJcInfo.getPushNum())){
            AffairPersonalAward affairPersonalAward = affairPersonalAwardService.get(id);
            affairPersonalAward.setPushType("1");//null是未推送,1是已推送
            affairPersonalAwardService.save(affairPersonalAward);
        }else if("10".equals(examJcInfo.getPushNum())){
            AffairCollectiveAward affairCollectiveAward = affairCollectiveAwardService.get(id);
            affairCollectiveAward.setPushType("1");//null是未推送,1是已推送
            affairCollectiveAwardService.save(affairCollectiveAward);
        }
        examJcInfo.setId(null);//这个改到Save方法里也许有效
        examJcInfoService.save(examJcInfo);
        addMessage(redirectAttributes, "保存奖惩信息库成功");
        request.setAttribute("saveResult","success");
        return "modules/exam/examJcInfoPlusForm";
    }

    //警务督查－督查问题库－督查记录
    @RequestMapping(value = "affairDcwtLibraryform")
    public String affairDcwtLibraryform(@RequestParam("id") String id, ExamJcInfo examJcInfo, Model model, AffairDcwtLibrary affairDcwtLibrary ) {
        affairDcwtLibrary = affairDcwtLibraryService.get(id);
        if (StringUtils.isNotBlank(affairDcwtLibrary.getResponsibleUnit())){
            examJcInfo.setJcObjectUnit(affairDcwtLibrary.getResponsibleUnit());
            examJcInfo.setJcObjectUnitId(affairDcwtLibrary.getResponsibleUnitId());
            examJcInfo.setJcObject("1");
        }
        examJcInfo.setJcTypeName(affairDcwtLibrary.getFoundProblem());
        examJcInfo.setPushNum("11");
        examJcInfo.setJcType("1");
        model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
        model.addAttribute("examJcInfo", examJcInfo);
        model.addAttribute("isAward", "true");
        return "modules/exam/examAwardInfoPlusForm";
    }
}
