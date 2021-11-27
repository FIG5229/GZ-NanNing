package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairDcwtLibrary;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwPersonalAward;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import com.thinkgem.jeesite.modules.affair.service.AffairDcwtLibraryService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwPersonalAwardService;
import com.thinkgem.jeesite.modules.affair.service.AffairTwUnitAwardService;
import com.thinkgem.jeesite.modules.exam.dao.ExamAutoEvaluationDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoEvaluation;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Stream;

/**
 * 自动考评 -- 推送奖惩信息库Service
 * @author kevin.jia,
 * @version 2021/1/17
 */

@Service
@Transactional(readOnly = true)
public class ExamAutoEvaluationPushService extends CrudService<ExamAutoEvaluationDao, ExamAutoEvaluation> {

    @Autowired
    private ExamStandardTemplateDataService examStandardTemplateDataService;

    @Autowired
    private ExamAutoEvaluationService examAutoEvaluationService;

    @Autowired
    private AffairTwUnitAwardService affairTwUnitAwardService;

    @Autowired
    private AffairTwBaseService affairTwBaseService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ExamStandardBaseInfoService examStandardBaseInfoService;

    @Autowired
    private AffairTwPersonalAwardService affairTwPersonalAwardService;

    @Autowired
    private AffairDcwtLibraryService affairDcwtLibraryService;

    //推送奖惩库功能，保存库调整为自动考评库
    @Transactional(readOnly = false)
    public void savePushInfo(String awardId, ExamAutoEvaluation examAutoEvaluation, HttpServletRequest request) {
        String pushFrom = examAutoEvaluation.getPushFrom();
        if(StringUtils.isNotBlank(pushFrom)){
            //模板
            ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(examAutoEvaluation.getModelId());
            examAutoEvaluation.setModel(examStandardBaseInfo.getName());
            //选择项
            examAutoEvaluation.setOption( examStandardTemplateDataService.get(examAutoEvaluation.getOptionId()).getItemValue());
            String details = "";
            String cycle = examStandardBaseInfo.getCycle();//周期1：月 2：年
            examAutoEvaluation.setFromSys("智慧政工");
            switch (cycle){
                case "1":
                    examAutoEvaluation.setPeriod("1");
                    examAutoEvaluation.setMonth(DateUtils.getMonth());
                    examAutoEvaluation.setYear(DateUtils.getYear());
                    break;
                case "2":
                    examAutoEvaluation.setPeriod("2");
                    examAutoEvaluation.setYear(DateUtils.getYear());
                    break;
                default:
                    examAutoEvaluation.setPeriod("1");
                    examAutoEvaluation.setMonth(DateUtils.getMonth());
                    examAutoEvaluation.setYear(DateUtils.getYear());
                    break;
            }
            List<User> userList = new ArrayList<>();
            switch (pushFrom){
                case "团内奖惩单位":
                    AffairTwUnitAward affairTwUnitAward = affairTwUnitAwardService.get(awardId);
                    details = affairTwUnitAward.getGetAwardParty()+"于"+ DateUtils.formatDate(affairTwUnitAward.getDate())+"获得"+ DictUtils.getDictLabels(affairTwUnitAward.getType(),"affair_tw_reward_punish","")+"奖励，奖励名称为："+affairTwUnitAward.getName();
                    if("0".equals(examAutoEvaluation.getType())){
                        //人
                        examAutoEvaluation.setType("2");
                        userList =  userDao.getUserByNo(examAutoEvaluation.getIdNumber());
                    }else{
                        if(StringUtils.isNotBlank(examAutoEvaluation.getUnitId())&&!examAutoEvaluation.getUnitId().contains("null")){
                            List<String> unitIds = new ArrayList<>();
                            Collections.addAll(unitIds,examAutoEvaluation.getUnitId().split(","));
                            userList = userDao.getUsersByOfficeIds(unitIds);

                        } else{
                            String tParty = affairTwUnitAward.getGetAwardParty();//团支部名称
                            userList = userDao.selUserBytPartyName(tParty);
                        }
                    }
                    if(userList!=null && userList.size()>0){
                        affairTwUnitAward.setPushType("1");//null是未推送,1是已推送
                        affairTwUnitAwardService.save(affairTwUnitAward);
                    }
                    break;
                case "团内奖惩个人" :
                    AffairTwPersonalAward affairTwPersonalAward = affairTwPersonalAwardService.get(awardId);
                    details = affairTwPersonalAward.getName()+"于"+ DateUtils.formatDate(affairTwPersonalAward.getDate())+"获得"+ DictUtils.getDictLabels(affairTwPersonalAward.getType(),"affair_tw_reward_punish","")+"奖励，奖励名称为："+affairTwPersonalAward.getRewardName();
                    if("0".equals(examAutoEvaluation.getType())){
                        //人
                        examAutoEvaluation.setType("2");
                        userList =  userDao.getUserByNo(examAutoEvaluation.getIdNumber());
                    }else{
                        List<String> unitIds = new ArrayList<>();
                        Collections.addAll(unitIds,examAutoEvaluation.getUnitId().split(","));
                        userList = userDao.getUsersByOfficeIds(unitIds);
                    }
                    if(userList!=null && userList.size()>0){
                        affairTwPersonalAward.setPushType("1");//null是未推送,1是已推送
                        affairTwPersonalAwardService.save(affairTwPersonalAward);
                    }
                    break;
                case "督察问题库":
                    AffairDcwtLibrary affairDcwtLibrary = affairDcwtLibraryService.get(awardId);
                    details = DictUtils.getDictLabels(affairDcwtLibrary.getSupervisoryUnit(),"affair_jdunit","")+"于"+ DateUtils.formatDate(affairDcwtLibrary.getTime(),"yyyy-MM-dd HH:mm:ss")+"---"+DateUtils.formatDate(affairDcwtLibrary.getFinishDate(),"yyyy-MM-dd HH:mm:ss")+"发现"+DictUtils.getDictLabels(affairDcwtLibrary.getProblemCategory(),"affair_wtlb","")+"存在不足，问题概述："+affairDcwtLibrary.getFoundProblem();
                    if("0".equals(examAutoEvaluation.getType())){
                        //人
                        examAutoEvaluation.setType("2");
                        userList =  userDao.getUserByNo(examAutoEvaluation.getIdNumber());
                    }else{
                        List<String> unitIds = new ArrayList<>();
                        Collections.addAll(unitIds,examAutoEvaluation.getUnitId().split(","));
                        userList = userDao.getUsersByOfficeIds(unitIds);
                    }
                    /*if(userList!=null && userList.size()>0){
                        affairDcwtLibrary.setPushType("1");//null是未推送,1是已推送
                        affairDcwtLibraryService.save(affairDcwtLibrary);
                    }*/
                    break;
                default:
                    break;
            }
            examAutoEvaluation.setDetails(details);//详情
            if(userList!= null && userList.size()>0){
                for (User user : userList){
                    examAutoEvaluation.setEvaluationId(user.getId());
                    if("0".equals(examAutoEvaluation.getType())){
                        examAutoEvaluation.setName(user.getName());
                        examAutoEvaluation.setIdNumber(user.getNo());
                    }else{
                        examAutoEvaluation.setUnit(user.getName());
                        examAutoEvaluation.setUnitId(user.getOffice().getId());
                    }
                    examAutoEvaluation.setEvaluation(user.getName());
                    ExamAutoEvaluation infoByMore = examAutoEvaluationService.getInfoByMore(examAutoEvaluation);
                    if(infoByMore!=null){
                        examAutoEvaluation.setDetails(details+";"+infoByMore.getDetails());
                        Double score = 0.0;
                        Double oldScore = 0.0;
                        try {
                            score = Double.valueOf(examAutoEvaluation.getScore());
                        }catch (Exception e){
                            e.printStackTrace();
                            logger.error(e.toString());
                        }
                        try {
                            oldScore = Double.valueOf(infoByMore.getScore());
                        }catch (Exception e){
                            e.printStackTrace();
                            logger.error(e.toString());
                        }
                        examAutoEvaluation.setScore(String.valueOf(score+oldScore));
                        ExamAutoEvaluation evaluation = new ExamAutoEvaluation();
                        evaluation.setId(infoByMore.getId());
                        evaluation.setDelFlag("1");
                        examAutoEvaluationService.delete(evaluation);
                    }
                    examAutoEvaluation.setId("");
                    examAutoEvaluation.setIsNewRecord(false);
                    examAutoEvaluationService.save(examAutoEvaluation);
                }
            }
        }
    }
}
