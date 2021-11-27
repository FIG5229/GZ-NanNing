package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.*;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.*;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.exam.dao.ExamAutoEvaluationDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamStandardTemplateDataDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoEvaluation;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDefine;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelReward;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.tw.entity.Ren001;
import com.thinkgem.jeesite.modules.tw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class exam1 extends CrudService<ExamAutoEvaluationDao, ExamAutoEvaluation> {

    @Autowired
    IdGen idGen;

    @Autowired
    private ExamWorkflowSegmentsDefineService examWorkflowSegmentsDefineService;

    @Autowired
    private ExamAutoEvaluationDao examAutoEvaluationDao;

    @Autowired
    private AffairGeneralSituationDao affairGeneralSituationDao;

    @Autowired
    private AffairThreeOneDao affairThreeOneDao;

    @Autowired
    private AffairPartyMemberService affairPartyMemberService;

    //考勤记录表
    @Autowired
    private AffairAttendanceService affairAttendanceService;

    //志愿服务
    @Autowired
    private AffairVolunteerServiceService affairVolunteerServiceService;

    //党信息
    @Autowired
    private AffairGeneralSituationService affairGeneralSituationService;

    //考勤记录子表
    @Autowired
    private AffairAttendanceChildService affairAttendanceChildService;

    @Autowired
    private AffairElectionService affairElectionService;

    @Autowired
    private AffairPartyDayActivitiesService affairPartyDayActivitiesService;

    @Autowired
    private AffairAssessService affairAssessService;

    @Autowired
    private AffairIdeaAnalysisService affairIdeaAnalysisService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AffairYearThreeOneService affairYearThreeOneService;

    @Autowired
    private AffairTwoOneService affairTwoOneService;

    @Autowired
    private AffairEducationCommentService affairEducationCommentService;

    @Autowired
    private AffairThreeOneService affairThreeOneService;

    @Autowired
    private AffairGroupStudyService affairGroupStudyService;

    @Autowired
    private AffairLearnDailyService affairLearnDailyService;

    @Autowired
    private AffairLearnPowerService affairLearnPowerService;

    @Autowired
    private AffairTalkHeartService affairTalkHeartService;

    @Autowired
    private AffairPoliceThoughtAnalysisService affairPoliceThoughtAnalysisService;

    @Autowired
    private AffairXcUnitRewardService affairXcUnitRewardService;

    @Autowired
    private AffairActiveService affairActiveService;

    @Autowired
    private AffairYjGoOutReportService affairYjGoOutReportService;

    @Autowired
    private AffairTwBaseService affairTwBaseService;

    @Autowired
    private AffairTwBaseSignService affairTwBaseSignService;

    @Autowired
    private AffairPushPartyService affairPushPartyService;

    @Autowired
    private AffairTnActivityRecordService affairTnActivityRecordService;

    @Autowired
    private AffairLzxxjyActivitiesService affairLzxxjyActivitiesService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AffairPersonalRewardService affairPersonalRewardService;

    @Autowired
    private AffairIdeologyService affairIdeologyService;

    @Autowired
    private AffairOrgRewardPunishService affairOrgRewardPunishService;

    @Autowired
    private TFeedbackService tFeedbackService;

    @Autowired
    private TPoliceMessageService tPoliceMessageService;

    @Autowired
    private ClockexceptioninforService clockexceptioninforService;

    @Autowired
    private DutywarningService dutywarningService;

    @Autowired
    private AcMobileDataService acMobileDataService;

    @Autowired
    private PunchingpointinforService punchingpointinforService;

    @Autowired
    private UserPViewService userPViewService;

    @Autowired
    private AcReportService acReportService;

    @Autowired
    private PatrolrecordService patrolrecordService;

    @Autowired
    private AcSaftyAuditService acSaftyAuditService;

    @Autowired
    private Ren001Service ren001Service;

    @Autowired
    private RenKz016Service renKz016Service;

    @Autowired
    private TAcVisitLinkRenService tAcVisitLinkRenService;

    @Autowired
    private ExamStandardTemplateDataService examStandardTemplateDataService;

    @Autowired
    private ExamStandardTemplateDataDao examStandardTemplateDataDao;

    @Autowired
    private PersonnelBaseDao personnelBaseDao;

    @Autowired
    private AffairApplicantService affairApplicantService;

    @Autowired
    private AffairThoughtAnalysisService affairThoughtAnalysisService;

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private AffairHealthCheckupService affairHealthCheckupService;

    @Autowired
    private AffairWorkInfoService affairWorkInfoService;

    @Autowired
    private AffairWorkSummaryService affairWorkSummaryService;

    @Autowired
    private AffairOneHelpOneService affairOneHelpOneService;
    @Autowired
    private AffairGhActivityRecordService affairGhActivityRecordService;

    @Autowired
    private AffairWelfareCondolencesService affairWelfareCondolencesService;

    @Autowired
    private AffairConsolationWorkInfoService affairConsolationWorkInfoService;

    @Autowired
    private OfficeService officeService;
    @Autowired
    private AffairNewsDao affairNewsDao;

    @Autowired
    private AffairLzxxjyActivitiesDao affairLzxxjyActivitiesDao;

    @Autowired
    private AffairPartyRewardPunishService affairPartyRewardPunishService;

    @Autowired
    private AffairTalkManagementService affairTalkManagementService;

    @Autowired
    private AffairDisciplinaryActionService affairDisciplinaryActionService;

    @Autowired
    private AffairPartyRewardPunishDao affairPartyRewardPunishDao;
    @Autowired
    private ExamWorkflowDefineService examWorkflowDefineService;
    @Autowired
    private ExamWorkflowService examWorkflowService;
    @Autowired
    private ExamWorkflowSegmentsService examWorkflowSegmentsService;
    @Autowired
    private ExamWorkflowDatasService examWorkflowDatasService;
    @Autowired
    private ExamStandardBaseInfoService examStandardBaseInfoService;
    @Autowired
    private ExamStandardTemplateDefineService examStandardTemplateDefineService;
    @Autowired
    private ExamStandardTemplateItemDefineService examStandardTemplateItemDefineService;

    @Autowired
    private ExamAutoConfigService examAutoConfigService;

    @Autowired
    private AffairLifeMeetDao affairLifeMeetDao;

    @Autowired
    private AffairCommentDao affairCommentDao;

    @Override
    @Transactional(readOnly = false)
    public void save(ExamAutoEvaluation examAutoEvaluation) {
        // FIXME: 2020/9/18 有的可能会在一条记录上面累加分数，这里有点问题
		/*if (examAutoEvaluationDao.findCountById(examAutoEvaluation.getId()) == 0){
			super.save(examAutoEvaluation);
		}else {
			int yearPartyDayCount = examAutoEvaluationDao.findYearPartyDayCount(examAutoEvaluation.getYear(), examAutoEvaluation.getType(), examAutoEvaluation.getEvalType(), examAutoEvaluation.getPeriod(), examAutoEvaluation.getUnit(), examAutoEvaluation.getUnitId(), examAutoEvaluation.getModelId(), examAutoEvaluation.getOptionId());
			examAutoEvaluation.setScore(String.valueOf(yearPartyDayCount * (-2)));
			examAutoEvaluationDao.update(examAutoEvaluation);
		}*/
        /*所有的自动考评保存的时候 保存行号*/
        ExamStandardTemplateData standardTemplateData = examStandardTemplateDataService.get(examAutoEvaluation.getOptionId());
        if (standardTemplateData != null && standardTemplateData.getRowNum() != null) {
            examAutoEvaluation.setRowNum(String.valueOf(standardTemplateData.getRowNum()));
        }
        if (StringUtils.isNotBlank(examAutoEvaluation.getOptionId())) {

            String row = this.selectRow(examAutoEvaluation.getOptionId());
            examAutoEvaluation.setRowNum(row);
        }

        String month = examAutoEvaluation.getMonth();
        if (StringUtils.isNotBlank(month)) {
            int m = Integer.valueOf(month);
            String newMonth = String.valueOf(m);
            examAutoEvaluation.setMonth(newMonth);
        }


        super.save(examAutoEvaluation);
    }

    public String selectRow(String optionId) {
        return examStandardTemplateDataDao.selectRowById(optionId);
    }

    /*
    * 三会一课，两学一做
    *
    * （党总支党员大会，党总支党课）
    *
    * 局考处
    *
    * 半年一次
    *
    * */
/*    public void jkcShyk(String checkTime){

        String year = checkTime.substring(0, 4);
        Integer y = Integer.valueOf(year);
        String month = checkTime.substring(checkTime.length() - 2);
        Integer m = Integer.valueOf(month);
        Integer lastMonth = m - 1;
        Integer lastYear = y - 1;
        String yearL = null;
        if (m == 1){
            yearL = lastYear + "-12-26";
        }else {
            yearL = year + "-" + lastMonth + "-26";
        }
        String yearT = year + "-" + month +  "-25";

        List<AffairGeneralSituation> dzzNameList = new ArrayList<>();
        dzzNameList = affairGeneralSituationDao.selectdzzName();

        int numSum = 0;

        StringBuilder details = new StringBuilder();
        for (int d = 0; d < dzzNameList.size(); d++) {
            AffairGeneralSituation affairGeneralSituation = dzzNameList.get(d);
            String unitName = affairGeneralSituation.getPartyOrganization();
            String unitId = affairGeneralSituation.getId();
            String type = "1";

            Integer number = affairYearThreeOneService.selectHuiyiNumber(unitId,yearL,yearT,type);
            if (number <= 0) {
                numSum++;
                details.append(",").append(unitName);
            }
        }
        if (numSum > 0) {
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("1");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(details  + year + "年" + month + "月" + "党员大会未按时进行");

            examAutoEvaluation.setUnit("公安局政治部组织干部处");
            examAutoEvaluation.setUnitId("4");
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年月度公安局对公安处扣分标准(业务部分26项)");
            examAutoEvaluation.setModelId("aef99f1b42054342ba71c25634d0f9fc");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("69040de171b64154a3026ab7f48c05be");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore("3");
            examAutoEvaluation.setEvaluationId("4");
            this.save(examAutoEvaluation);
        }

    }*/
    /*
    * 三会一课
    *
    * 局考处
    *
    * 月度
    * */
    public void jkcShyk(String checkTime){

        String year = checkTime.substring(0, 4);
        Integer y = Integer.valueOf(year);
        String month = checkTime.substring(checkTime.length() - 2);
        Integer m = Integer.valueOf(month);

        List<AffairGeneralSituation> nncPartyList = affairGeneralSituationService.getChuPartyBranch("34", "1");
        List<AffairGeneralSituation> lzcPartyList = affairGeneralSituationService.getChuPartyBranch("95", "1");
        List<AffairGeneralSituation> bhcPartyList = affairGeneralSituationService.getChuPartyBranch("156", "1");

        //会议类型
        String type = null;
        //南宁处数量
        Integer nncNum = 0;
        //南宁处详情
        StringBuilder details = new StringBuilder();
        //柳州处数量
        Integer lzcNum = 0;
        //柳州处详情
        StringBuilder lzcdetails = new StringBuilder();
        //北海处数量
        Integer bhcNum = 0;
        //北海处详情
        StringBuilder bhcdetails = new StringBuilder();

        for (int n = 0; n < nncPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = nncPartyList.get(n);
            String name = affairGeneralSituation.getPartyOrganization();
            String id = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && name.contains("党总支")){

                    Integer lastYear = y - 1;
                    String yearL = null;
                    String yearT = null;
                    if (m == 6){
                        yearL = lastYear + "-12-26";
                        yearT = year + "-06-25";
                    }if (m == 12){
                        yearT = year + "-12-25";
                        yearL = year + "-06-26";
                    }
                    //党员大会
                    type = "1";
                    Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                    if (dydhNumber < 1){
                        nncNum++;
                        if (m == 6){
                            details.append(",").append(name).append(year).append("年").append("上半年，党员大会未按时进行");
                        }
                        if (m == 12){
                            details.append(",").append(name).append(year).append("年").append("下半年，党员大会未按时进行");
                        }
                    }

                    //党课
                    type = "5";
                    Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                    if (dkNumber < 1){
                        nncNum++;
                        if (m == 6){
                            details.append(",").append(name).append(year).append("年").append("上半年，党课未按时进行");
                        }
                        if (m == 12){
                            details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                        }
                    }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && name.contains("党支部")){
                    Map<String, String> time = jdTime(checkTime);
                    String yearL = time.get("yearL");
                    String yearT = time.get("yearT");
                    String jd = time.get("jd");

                    //党员大会
                    type = "1";
                    Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                    if (dydhNumber < 1){
                        nncNum++;
                        details.append(",").append(name).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                    }

                    //党课
                    type = "5";
                    Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                    if (dkNumber < 1){
                        nncNum++;
                        details.append(",").append(name).append(year).append("年").append(jd).append("季度，党课未按时进行");
                    }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 1){
                nncNum++;
                details.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 1){
                nncNum++;
                details.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (nncNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("1");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(details+",");

            examAutoEvaluation.setUnit("南宁处政治处组织干部室");
            examAutoEvaluation.setUnitId("27");
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年月度公安局对公安处扣分标准(业务部分26项)");
            examAutoEvaluation.setModelId("aef99f1b42054342ba71c25634d0f9fc");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("69040de171b64154a3026ab7f48c05be");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(nncNum * 3));
            examAutoEvaluation.setEvaluationId("34e8d855cf6b4b1ab5e7e23e7aaba658");
            this.save(examAutoEvaluation);

        }
        for (int n = 0; n < lzcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = lzcPartyList.get(n);
            String name = affairGeneralSituation.getPartyOrganization();
            String id = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && name.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    lzcNum++;
                    if (m == 6){
                        lzcdetails.append(",").append(name).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        lzcdetails.append(",").append(name).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    lzcNum++;
                    if (m == 6){
                        lzcdetails.append(",").append(name).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        lzcdetails.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && name.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    lzcNum++;
                    lzcdetails.append(",").append(name).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    lzcNum++;
                    lzcdetails.append(",").append(name).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 1){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 1){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (lzcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("1");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(lzcdetails+",");

            examAutoEvaluation.setUnit("柳州处政治处组织干部室");
            examAutoEvaluation.setUnitId("142");
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年月度公安局对公安处扣分标准(业务部分26项)");
            examAutoEvaluation.setModelId("aef99f1b42054342ba71c25634d0f9fc");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("69040de171b64154a3026ab7f48c05be");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(lzcNum * 3));
            examAutoEvaluation.setEvaluationId("ec3ba2efdd404f2faa520f6e8a71ec4c");
            this.save(examAutoEvaluation);

        }
        for (int n = 0; n < bhcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = bhcPartyList.get(n);
            String name = affairGeneralSituation.getPartyOrganization();
            String id = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && name.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    bhcNum++;
                    if (m == 6){
                        bhcdetails.append(",").append(name).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        bhcdetails.append(",").append(name).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    bhcNum++;
                    if (m == 6){
                        bhcdetails.append(",").append(name).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        bhcdetails.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && name.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    bhcNum++;
                    bhcdetails.append(",").append(name).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    bhcNum++;
                    bhcdetails.append(",").append(name).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 1){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 1){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (bhcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("1");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(bhcdetails+",");

            examAutoEvaluation.setUnit("北海处政治处组织干部室");
            examAutoEvaluation.setUnitId("264");
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年月度公安局对公安处扣分标准(业务部分26项)");
            examAutoEvaluation.setModelId("aef99f1b42054342ba71c25634d0f9fc");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("69040de171b64154a3026ab7f48c05be");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(bhcNum * 3));
            examAutoEvaluation.setEvaluationId("c90918faf2614baa8fa85230482bd43e");
            this.save(examAutoEvaluation);

        }
    }

    /*
    * 三会一课
    *
    * 局考处
    *
    * 年度
    * */
    public void jkcShykYear(String year){

        Map<String, String> map = yearTime(year);
        String yearL = map.get("yearL");
        String yearT = map.get("yearT");

        List<AffairGeneralSituation> nncPartyList = affairGeneralSituationService.getChuPartyBranch("34", "1");
        List<AffairGeneralSituation> lzcPartyList = affairGeneralSituationService.getChuPartyBranch("95", "1");
        List<AffairGeneralSituation> bhcPartyList = affairGeneralSituationService.getChuPartyBranch("156", "1");

        //会议类型
        String type = null;
        //南宁处数量
        Integer nncNum = 0;
        //南宁处详情
        StringBuilder details = new StringBuilder();
        //柳州处数量
        Integer lzcNum = 0;
        //柳州处详情
        StringBuilder lzcdetails = new StringBuilder();
        //北海处数量
        Integer bhcNum = 0;
        //北海处详情
        StringBuilder bhcdetails = new StringBuilder();

        for (int n = 0; n < nncPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = nncPartyList.get(n);
            String name = affairGeneralSituation.getPartyOrganization();
            String id = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (name.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 2){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 2){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (name.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 4){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 4){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 12){
                nncNum++;
                details.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 12){
                nncNum++;
                details.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (nncNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("1");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(details+",");

            examAutoEvaluation.setUnit("南宁处政治处组织干部室");
            examAutoEvaluation.setUnitId("27");
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年年度公安局对公安处绩效考核扣分标准(业务部分26项)");
            examAutoEvaluation.setModelId("a959d8865ea74bf68cf818adc710c75c");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("924dd81816a34620915683d19cc749c1");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(nncNum * 2));
            examAutoEvaluation.setEvaluationId("34e8d855cf6b4b1ab5e7e23e7aaba658");
            this.save(examAutoEvaluation);
        }
        for (int n = 0; n < lzcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = lzcPartyList.get(n);
            String name = affairGeneralSituation.getPartyOrganization();
            String id = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (name.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 2){
                    lzcNum++;
                    lzcdetails.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 2){
                    lzcNum++;
                    lzcdetails.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (name.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 4){
                    lzcNum++;
                    lzcdetails.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 4){
                    lzcNum++;
                    lzcdetails.append(",").append(name).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 12){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 12){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (lzcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("1");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(lzcdetails+",");

            examAutoEvaluation.setUnit("柳州处政治处组织干部室");
            examAutoEvaluation.setUnitId("142");
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年年度公安局对公安处绩效考核扣分标准(业务部分26项)");
            examAutoEvaluation.setModelId("a959d8865ea74bf68cf818adc710c75c");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("924dd81816a34620915683d19cc749c1");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(lzcNum * 2));
            examAutoEvaluation.setEvaluationId("ec3ba2efdd404f2faa520f6e8a71ec4c");
            this.save(examAutoEvaluation);
        }
        for (int n = 0; n < bhcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = bhcPartyList.get(n);
            String name = affairGeneralSituation.getPartyOrganization();
            String id = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (name.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 2){
                    bhcNum++;
                    bhcdetails.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 2){
                    bhcNum++;
                    bhcdetails.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (name.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 4){
                    bhcNum++;
                    bhcdetails.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 4){
                    bhcNum++;
                    bhcdetails.append(",").append(name).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 12){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 12){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (bhcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("1");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(bhcdetails+",");

            examAutoEvaluation.setUnit("北海处政治处组织干部室");
            examAutoEvaluation.setUnitId("264");
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年年度公安局对公安处绩效考核扣分标准(业务部分26项)");
            examAutoEvaluation.setModelId("a959d8865ea74bf68cf818adc710c75c");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("924dd81816a34620915683d19cc749c1");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(bhcNum * 2));
            examAutoEvaluation.setEvaluationId("c90918faf2614baa8fa85230482bd43e");
            this.save(examAutoEvaluation);
        }
    }

    /*
     * 三会一课
     *
     * 局考局
     *
     * 月度
     * */
    public void jkjShyk(String checkTime){

        String year = checkTime.substring(0, 4);
        Integer y = Integer.valueOf(year);
        String month = checkTime.substring(checkTime.length() - 2);
        Integer m = Integer.valueOf(month);

        List<AffairGeneralSituation> PartyList = affairGeneralSituationService.getJJGPartyBranch();

        //会议类型
        String type = null;


        for (int n = 0; n < PartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = PartyList.get(n);
            String name = affairGeneralSituation.getPartyOrganization();
            String id = affairGeneralSituation.getId();
            StringBuilder details = new StringBuilder();

            Integer Num = 0;
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && name.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    Num++;
                    if (m == 6){
                        details.append(",").append(name).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        details.append(",").append(name).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    Num++;
                    if (m == 6){
                        details.append(",").append(name).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && name.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    Num++;
                    details.append(",").append(name).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    Num++;
                    details.append(",").append(name).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 1){
                Num++;
                details.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 1){
                Num++;
                details.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }
            if (Num > 0){
                ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
                examAutoEvaluation.setType("1");
                examAutoEvaluation.setEvalType("2");
                examAutoEvaluation.setPeriod("1");
                examAutoEvaluation.setYear(year);
                examAutoEvaluation.setMonth(month);
                examAutoEvaluation.setDetails(details+",");

                examAutoEvaluation.setUnit("南宁处政治处组织干部室");
                examAutoEvaluation.setUnitId("27");
                examAutoEvaluation.setAssess("");
                examAutoEvaluation.setModel("2020年公安局对局机关各处室及直属支队月度绩效考核公共扣分标准");
                examAutoEvaluation.setModelId("e8aa72ffe54a4118aeae9077ff7898ec");
                examAutoEvaluation.setOption("“三会一课”未达到规定要求，每缺一会或一课的");
                examAutoEvaluation.setOptionId("0858a666bf7c41a6b629cd575a8e1b48");
                examAutoEvaluation.setFromSys("智慧政工");
                examAutoEvaluation.setScore(String.valueOf(Num * 2));
                examAutoEvaluation.setEvaluationId("34e8d855cf6b4b1ab5e7e23e7aaba658");
                this.save(examAutoEvaluation);

            }
        }
    }

    /*
     * 三会一课
     *
     * 局考局
     *
     * 年度
     * */
    public void jkjShykYear(String year){

        Map<String, String> map = yearTime(year);
        String yearL = map.get("yearL");
        String yearT = map.get("yearT");

        List<AffairGeneralSituation> PartyList = affairGeneralSituationService.getJJGPartyBranch();

        //会议类型
        String type = null;
        //南宁处数量
        //南宁处详情
        String name = null;
        String id = null;
        for (int n = 0; n < PartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = PartyList.get(n);
            name = affairGeneralSituation.getPartyOrganization();
            id = affairGeneralSituation.getId();
            StringBuilder details = new StringBuilder();

            Integer nncNum = 0;
            //   党总支党员大会、党总支党课
            if (name.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 2){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 2){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (name.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 4){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 4){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 12){
                nncNum++;
                details.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 12){
                nncNum++;
                details.append(",").append(year).append("年").append("党小组会未按时进行");
            }
            if (nncNum > 0){
                ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
                examAutoEvaluation.setType("1");
                examAutoEvaluation.setEvalType("2");
                examAutoEvaluation.setPeriod("2");
                examAutoEvaluation.setYear(year);
                examAutoEvaluation.setDetails(details+",");

                examAutoEvaluation.setUnit(name);
                examAutoEvaluation.setUnitId(id);
                examAutoEvaluation.setAssess("");
                examAutoEvaluation.setModel("2020年公安局对局机关各处室及直属支队年度绩效考核公共扣分标准");
                examAutoEvaluation.setModelId("653c088f3e754adcaa659a3706d304b9");
                examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
                examAutoEvaluation.setOptionId("cf91bc7ae6144488bbf7653fc3032273");
                examAutoEvaluation.setFromSys("智慧政工");
                examAutoEvaluation.setScore(String.valueOf(nncNum));
                examAutoEvaluation.setEvaluationId("34e8d855cf6b4b1ab5e7e23e7aaba658");
                this.save(examAutoEvaluation);
            }
        }
    }
    /*
     * 三会一课
     *
     * 处考处
     *
     * 月度
     * */
    public void ckcShyk(String checkTime){

        String year = checkTime.substring(0, 4);
        Integer y = Integer.valueOf(year);
        String month = checkTime.substring(checkTime.length() - 2);
        Integer m = Integer.valueOf(month);

        List<AffairGeneralSituation> nncPartyList = affairGeneralSituationService.getChuPartyBranch("34", "1");
        List<AffairGeneralSituation> lzcPartyList = affairGeneralSituationService.getChuPartyBranch("95", "1");
        List<AffairGeneralSituation> bhcPartyList = affairGeneralSituationService.getChuPartyBranch("156", "1");

        //会议类型
        String type = null;
        //南宁处数量
        Integer nncNum = 0;
        //南宁处详情
        StringBuilder details = new StringBuilder();
        //柳州处数量
        Integer lzcNum = 0;
        //柳州处详情
        StringBuilder lzcdetails = new StringBuilder();
        //北海处数量
        Integer bhcNum = 0;
        //北海处详情
        StringBuilder bhcdetails = new StringBuilder();

        String name = null;
        String id = null;
        String lzcname = null;
        String lzcid = null;
        String bhcname = null;
        String bhcid = null;
        for (int n = 0; n < nncPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = nncPartyList.get(n);
            name = affairGeneralSituation.getPartyOrganization();
            id = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && name.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    nncNum++;
                    if (m == 6){
                        details.append(",").append(name).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        details.append(",").append(name).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    nncNum++;
                    if (m == 6){
                        details.append(",").append(name).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && name.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 1){
                nncNum++;
                details.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 1){
                nncNum++;
                details.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (nncNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("1");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(details+",");

            examAutoEvaluation.setUnit(name);
            examAutoEvaluation.setUnitId(id);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年月度南宁处对组干室扣分标准");
            examAutoEvaluation.setModelId("385c673d86714b1e93a136255162b60a");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("093e26435a9e4d179f492fc94793e5e0");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(nncNum * 2));
            examAutoEvaluation.setEvaluationId("34e8d855cf6b4b1ab5e7e23e7aaba658");
            this.save(examAutoEvaluation);

        }
        for (int n = 0; n < lzcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = lzcPartyList.get(n);
            lzcname = affairGeneralSituation.getPartyOrganization();
            lzcid = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && lzcname.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dydhNumber < 1){
                    lzcNum++;
                    if (m == 6){
                        lzcdetails.append(",").append(lzcname).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        lzcdetails.append(",").append(lzcname).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dkNumber < 1){
                    lzcNum++;
                    if (m == 6){
                        lzcdetails.append(",").append(lzcname).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        lzcdetails.append(",").append(lzcname).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && lzcname.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dydhNumber < 1){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dkNumber < 1){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
            if (wyhNumber < 1){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
            if (dxzhNumber < 1){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (lzcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("4");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(lzcdetails+",");

            examAutoEvaluation.setUnit(lzcname);
            examAutoEvaluation.setUnitId(lzcid);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("柳州处对组干室月度绩效考核评分标准(业务部分)");
            examAutoEvaluation.setModelId("0b0e11f1ada24e51a040c464450aaaca");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("ef029c2de6d149938b4367866fa3dd9c");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(lzcNum * 2));
            examAutoEvaluation.setEvaluationId("ec3ba2efdd404f2faa520f6e8a71ec4c");
            this.save(examAutoEvaluation);

        }
        for (int n = 0; n < bhcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = bhcPartyList.get(n);
            bhcname = affairGeneralSituation.getPartyOrganization();
            bhcid = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && bhcname.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dydhNumber < 1){
                    bhcNum++;
                    if (m == 6){
                        bhcdetails.append(",").append(bhcname).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        bhcdetails.append(",").append(bhcname).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dkNumber < 1){
                    bhcNum++;
                    if (m == 6){
                        bhcdetails.append(",").append(bhcname).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        bhcdetails.append(",").append(bhcname).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && bhcname.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dydhNumber < 1){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dkNumber < 1){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
            if (wyhNumber < 1){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
            if (dxzhNumber < 1){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (bhcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("4");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(bhcdetails+",");

            examAutoEvaluation.setUnit(bhcname);
            examAutoEvaluation.setUnitId(bhcid);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年月度北海处对机关科室扣分标准(业务部分)");
            examAutoEvaluation.setModelId("9d566eb5263843daa3be3ae3eb562f58");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("ea882b67b95549e1ada5dbb6b1247efa");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(bhcNum * 2));
            examAutoEvaluation.setEvaluationId("c90918faf2614baa8fa85230482bd43e");
            this.save(examAutoEvaluation);

        }
    }

    /*
     * 三会一课
     *
     * 处考处
     *
     * 年度
     * */
    public void ckcShykYear(String year){

        Map<String, String> map = yearTime(year);
        String yearL = map.get("yearL");
        String yearT = map.get("yearT");

        List<AffairGeneralSituation> nncPartyList = affairGeneralSituationService.getChuPartyBranch("34", "1");
        List<AffairGeneralSituation> lzcPartyList = affairGeneralSituationService.getChuPartyBranch("95", "1");
        List<AffairGeneralSituation> bhcPartyList = affairGeneralSituationService.getChuPartyBranch("156", "1");

        //会议类型
        String type = null;
        //南宁处数量
        Integer nncNum = 0;
        //南宁处详情
        StringBuilder details = new StringBuilder();
        //柳州处数量
        Integer lzcNum = 0;
        //柳州处详情
        StringBuilder lzcdetails = new StringBuilder();
        //北海处数量
        Integer bhcNum = 0;
        //北海处详情
        StringBuilder bhcdetails = new StringBuilder();

        String name = null;
        String id = null;
        String lzcname = null;
        String lzcid = null;
        String bhcname = null;
        String bhcid = null;
        for (int n = 0; n < nncPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = nncPartyList.get(n);
            name = affairGeneralSituation.getPartyOrganization();
            id = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (name.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 2){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 2){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (name.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 4){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 4){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 12){
                nncNum++;
                details.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 12){
                nncNum++;
                details.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (nncNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("4");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(details+",");

            examAutoEvaluation.setUnit(name);
            examAutoEvaluation.setUnitId(id);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年年度南宁处对各单位（部门）绩效考核扣分标准(队伍建设)");
            examAutoEvaluation.setModelId("13ea683844d644cb8aa15e00fb07f825");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("c203e5be96c44df382280fd482cb1160");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(nncNum));
            examAutoEvaluation.setEvaluationId("34e8d855cf6b4b1ab5e7e23e7aaba658");
            this.save(examAutoEvaluation);
        }
        for (int n = 0; n < lzcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = lzcPartyList.get(n);
            lzcname = affairGeneralSituation.getPartyOrganization();
            lzcid = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (lzcname.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dydhNumber < 2){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dkNumber < 2){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (lzcname.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dydhNumber < 4){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dkNumber < 4){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
            if (wyhNumber < 12){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
            if (dxzhNumber < 12){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (lzcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("4");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(lzcdetails+",");

            examAutoEvaluation.setUnit(lzcname);
            examAutoEvaluation.setUnitId(lzcid);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("柳州处对组干室年度绩效考核评分标准(业务部分)");
            examAutoEvaluation.setModelId("b4080d783c5f48ffa709b8fba0b0eee6");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("5969ad1be2b44ea0a50796f37c103113");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(lzcNum));
            examAutoEvaluation.setEvaluationId("ec3ba2efdd404f2faa520f6e8a71ec4c");
            this.save(examAutoEvaluation);
        }
        for (int n = 0; n < bhcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = bhcPartyList.get(n);
            bhcname = affairGeneralSituation.getPartyOrganization();
            bhcid = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (bhcname.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dydhNumber < 2){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dkNumber < 2){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (bhcname.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dydhNumber < 4){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dkNumber < 4){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
            if (wyhNumber < 12){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
            if (dxzhNumber < 12){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (bhcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("4");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(bhcdetails+",");

            examAutoEvaluation.setUnit(bhcname);
            examAutoEvaluation.setUnitId(bhcid);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年年度北海处对机关科室绩效考核扣分标准(业务部分)");
            examAutoEvaluation.setModelId("01bcee76bf194541b89220959b83b09a");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("7fa705c9c0de4de6b6947fd48c15e243");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(bhcNum));
            examAutoEvaluation.setEvaluationId("c90918faf2614baa8fa85230482bd43e");
            this.save(examAutoEvaluation);
        }
    }

    /*
     * 三会一课
     *
     * 初考队所
     *
     * 月度
     * */
    public void ckpcsShyk(String checkTime){

        String year = checkTime.substring(0, 4);
        Integer y = Integer.valueOf(year);
        String month = checkTime.substring(checkTime.length() - 2);
        Integer m = Integer.valueOf(month);

        List<AffairGeneralSituation> nncPartyList = affairGeneralSituationService.getChuPartyBranch("34", "2");
        List<AffairGeneralSituation> lzcPartyList = affairGeneralSituationService.getChuPartyBranch("95", "2");
        List<AffairGeneralSituation> bhcPartyList = affairGeneralSituationService.getChuPartyBranch("156", "2");

        //会议类型
        String type = null;
        //南宁处数量
        Integer nncNum = 0;
        //南宁处详情
        StringBuilder details = new StringBuilder();
        //柳州处数量
        Integer lzcNum = 0;
        //柳州处详情
        StringBuilder lzcdetails = new StringBuilder();
        //北海处数量
        Integer bhcNum = 0;
        //北海处详情
        StringBuilder bhcdetails = new StringBuilder();

        String name = null;
        String id = null;
        String lzcname = null;
        String lzcid = null;
        String bhcname = null;
        String bhcid = null;
        for (int n = 0; n < nncPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = nncPartyList.get(n);
            name = affairGeneralSituation.getPartyOrganization();
            id = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && name.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    nncNum++;
                    if (m == 6){
                        details.append(",").append(name).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        details.append(",").append(name).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    nncNum++;
                    if (m == 6){
                        details.append(",").append(name).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && name.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 1){
                nncNum++;
                details.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 1){
                nncNum++;
                details.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (nncNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("3");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(details+",");

            examAutoEvaluation.setUnit(name);
            examAutoEvaluation.setUnitId(id);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("南宁公安处月度对派出所2020年度绩效考核评分标准（队伍建设）");
            examAutoEvaluation.setModelId("ddc6d6dd210e4f428b3a5361f27389fa");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("da96bf7943c449e6acd2a768d76884ac");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(nncNum * 2));
            examAutoEvaluation.setEvaluationId("34e8d855cf6b4b1ab5e7e23e7aaba658");
            this.save(examAutoEvaluation);

        }
        for (int n = 0; n < lzcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = lzcPartyList.get(n);
            lzcname = affairGeneralSituation.getPartyOrganization();
            lzcid = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && lzcname.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dydhNumber < 1){
                    lzcNum++;
                    if (m == 6){
                        lzcdetails.append(",").append(lzcname).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        lzcdetails.append(",").append(lzcname).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dkNumber < 1){
                    lzcNum++;
                    if (m == 6){
                        lzcdetails.append(",").append(lzcname).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        lzcdetails.append(",").append(lzcname).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && lzcname.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dydhNumber < 1){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dkNumber < 1){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
            if (wyhNumber < 1){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
            if (dxzhNumber < 1){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (lzcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("3");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(lzcdetails+",");

            examAutoEvaluation.setUnit(lzcname);
            examAutoEvaluation.setUnitId(lzcid);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("柳州处对派出所2020年月度队伍建设绩效考核扣分标准");
            examAutoEvaluation.setModelId("f38535fc63a2418b8ed37c2e18f75ad6");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("4b6acca104a54a54a0643c7593ea48af");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(lzcNum * 2));
            examAutoEvaluation.setEvaluationId("ec3ba2efdd404f2faa520f6e8a71ec4c");
            this.save(examAutoEvaluation);

        }
        for (int n = 0; n < bhcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = bhcPartyList.get(n);
            bhcname = affairGeneralSituation.getPartyOrganization();
            bhcid = affairGeneralSituation.getId();
            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && bhcname.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dydhNumber < 1){
                    bhcNum++;
                    if (m == 6){
                        bhcdetails.append(",").append(bhcname).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        bhcdetails.append(",").append(bhcname).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dkNumber < 1){
                    bhcNum++;
                    if (m == 6){
                        bhcdetails.append(",").append(bhcname).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        bhcdetails.append(",").append(bhcname).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && bhcname.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dydhNumber < 1){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dkNumber < 1){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }
            //每月    党支部（总支）委员会、党小组会

            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
            if (wyhNumber < 1){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
            if (dxzhNumber < 1){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

        }
        if (bhcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("3");
            examAutoEvaluation.setPeriod("1");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setMonth(month);
            examAutoEvaluation.setDetails(bhcdetails+",");

            examAutoEvaluation.setUnit(bhcname);
            examAutoEvaluation.setUnitId(bhcid);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("");
            examAutoEvaluation.setModelId("");
            examAutoEvaluation.setOption("");
            examAutoEvaluation.setOptionId("");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(bhcNum * 2));
            examAutoEvaluation.setEvaluationId("c90918faf2614baa8fa85230482bd43e");
            this.save(examAutoEvaluation);

        }
    }

    /*
     * 三会一课
     *
     * 处考队所
     *
     * 年度
     * */
    public void ckpcsShykYear(String year){

        Map<String, String> map = yearTime(year);
        String yearL = map.get("yearL");
        String yearT = map.get("yearT");

        List<AffairGeneralSituation> nncPartyList = affairGeneralSituationService.getChuPartyBranch("34", "2");
        List<AffairGeneralSituation> lzcPartyList = affairGeneralSituationService.getChuPartyBranch("95", "2");
        List<AffairGeneralSituation> bhcPartyList = affairGeneralSituationService.getChuPartyBranch("156", "2");

        //会议类型
        String type = null;
        //南宁处数量
        Integer nncNum = 0;
        //南宁处详情
        StringBuilder details = new StringBuilder();
        //柳州处数量
        Integer lzcNum = 0;
        //柳州处详情
        StringBuilder lzcdetails = new StringBuilder();
        //北海处数量
        Integer bhcNum = 0;
        //北海处详情
        StringBuilder bhcdetails = new StringBuilder();

        String name = null;
        String id = null;
        String lzcname = null;
        String lzcid = null;
        String bhcname = null;
        String bhcid = null;
        for (int n = 0; n < nncPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = nncPartyList.get(n);
            name = affairGeneralSituation.getPartyOrganization();
            id = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (name.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 2){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 2){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (name.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 4){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 4){
                    nncNum++;
                    details.append(",").append(name).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 12){
                nncNum++;
                details.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 12){
                nncNum++;
                details.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (nncNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("3");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(details+",");

            examAutoEvaluation.setUnit(name);
            examAutoEvaluation.setUnitId(id);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年年度南宁处对派出所绩效考核扣分标准(队伍建设)");
            examAutoEvaluation.setModelId("f43cb06efe6b42acb625d2cef27bad1e");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("ef3765179f4946eea71e58b46bbe6ab5");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(nncNum));
            examAutoEvaluation.setEvaluationId("34e8d855cf6b4b1ab5e7e23e7aaba658");
            this.save(examAutoEvaluation);
        }
        for (int n = 0; n < lzcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = lzcPartyList.get(n);
            lzcname = affairGeneralSituation.getPartyOrganization();
            lzcid = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (lzcname.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dydhNumber < 2){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dkNumber < 2){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (lzcname.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dydhNumber < 4){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
                if (dkNumber < 4){
                    lzcNum++;
                    lzcdetails.append(",").append(lzcname).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
            if (wyhNumber < 12){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(lzcid,yearL,yearT,type);
            if (dxzhNumber < 12){
                lzcNum++;
                lzcdetails.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (lzcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("3");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(lzcdetails+",");

            examAutoEvaluation.setUnit(lzcname);
            examAutoEvaluation.setUnitId(lzcid);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("柳州处对派出所2020年年度队伍建设绩效考核扣分标准");
            examAutoEvaluation.setModelId("1ccfeca0f49543d49f8833feb21735e3");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("930123cc09d24ced8350a5fef0a0f6cc");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(lzcNum));
            examAutoEvaluation.setEvaluationId("ec3ba2efdd404f2faa520f6e8a71ec4c");
            this.save(examAutoEvaluation);
        }
        for (int n = 0; n < bhcPartyList.size(); n++){

            AffairGeneralSituation affairGeneralSituation = bhcPartyList.get(n);
            bhcname = affairGeneralSituation.getPartyOrganization();
            bhcid = affairGeneralSituation.getId();
            //   党总支党员大会、党总支党课
            if (bhcname.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dydhNumber < 2){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dkNumber < 2){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //    党支部党员大会、党支部党课
            if (bhcname.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dydhNumber < 4){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
                if (dkNumber < 4){
                    bhcNum++;
                    bhcdetails.append(",").append(bhcname).append(year).append("年").append("党课未按时进行");
                }
            }
            //    党支部（总支）委员会、党小组会

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
            if (wyhNumber < 12){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(bhcid,yearL,yearT,type);
            if (dxzhNumber < 12){
                bhcNum++;
                bhcdetails.append(",").append(year).append("年").append("党小组会未按时进行");
            }
        }
        if (bhcNum > 0){
            ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
            examAutoEvaluation.setType("1");
            examAutoEvaluation.setEvalType("3");
            examAutoEvaluation.setPeriod("2");
            examAutoEvaluation.setYear(year);
            examAutoEvaluation.setDetails(bhcdetails+",");

            examAutoEvaluation.setUnit(bhcname);
            examAutoEvaluation.setUnitId(bhcid);
            examAutoEvaluation.setAssess("");
            examAutoEvaluation.setModel("2020年年度北海处对派出所、刑警三大队绩效考核扣分标准(队伍建设部分)");
            examAutoEvaluation.setModelId("9844385c36c246f1aaceda6d0611e5d3");
            examAutoEvaluation.setOption("公安处及管内单位党建活动组织不力，被上级检查发现的");
            examAutoEvaluation.setOptionId("dd26ac0fd27a4c778d00f96aacda2be3");
            examAutoEvaluation.setFromSys("智慧政工");
            examAutoEvaluation.setScore(String.valueOf(bhcNum));
            examAutoEvaluation.setEvaluationId("c90918faf2614baa8fa85230482bd43e");
            this.save(examAutoEvaluation);
        }
    }

    /*
     * 三会一课
     *
     * 中基层
     *
     * 月度
     *
     * */
    public void zjcShyk(String checkTime){

        String year = checkTime.substring(0, 4);
        Integer y = Integer.valueOf(year);
        String month = checkTime.substring(checkTime.length() - 2);
        Integer m = Integer.valueOf(month);

        //所有党组织
        List<AffairGeneralSituation> affairGeneralSituationList = affairGeneralSituationDao.selectAllParty();

        for (int a = 0; a < affairGeneralSituationList.size(); a++){
            AffairGeneralSituation affairGeneralSituation = affairGeneralSituationList.get(a);
            String id = affairGeneralSituation.getId();
            String name = affairGeneralSituation.getPartyOrganization();
            String shuji = affairGeneralSituation.getShuji();
            String idNumber = userDao.selectUserIdNumber(shuji);

            //缺少的数量
            int Num = 0;

            String type = null;
            StringBuilder details = new StringBuilder();


            //半年  党总支党员大会、党总支党课
            if (m == 6 || m == 12 && name.contains("党总支")){

                Integer lastYear = y - 1;
                String yearL = null;
                String yearT = null;
                if (m == 6){
                    yearL = lastYear + "-12-26";
                    yearT = year + "-06-25";
                }if (m == 12){
                    yearT = year + "-12-25";
                    yearL = year + "-06-26";
                }
                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    Num++;
                    if (m == 6){
                        details.append(",").append(name).append(year).append("年").append("上半年，党员大会未按时进行");
                    }
                    if (m == 12){
                        details.append(",").append(name).append(year).append("年").append("下半年，党员大会未按时进行");
                    }
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    Num++;
                    if (m == 6){
                        details.append(",").append(name).append(year).append("年").append("上半年，党课未按时进行");
                    }
                    if (m == 12){
                        details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                    }
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (m == 3 || m == 6 || m == 9 || m == 12 && name.contains("党支部")){
                Map<String, String> time = jdTime(checkTime);
                String yearL = time.get("yearL");
                String yearT = time.get("yearT");
                String jd = time.get("jd");

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 1){
                    Num++;
                    details.append(",").append(name).append(year).append("年").append(jd).append("季度，党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 1){
                    Num++;
                    details.append(",").append(name).append(year).append("年").append(jd).append("季度，党课未按时进行");
                }
            }

            //每月    党支部（总支）委员会、党小组会
            Map<String, String> time = monthTime(checkTime);
            String yearL = time.get("yearL");
            String yearT = time.get("yearT");

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 1){
                Num++;
                details.append(",").append(year).append("年").append(month).append("月,委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 1){
                Num++;
                details.append(",").append(year).append("年").append(month).append("月,党小组会未按时进行");
            }

            //党日活动
            Integer drhdNumber = affairPartyDayActivitiesService.selectNumber(id,yearL,yearT);
            if (drhdNumber < 1){
                Num++;
                details.append(",").append(year).append("年").append(month).append("月,党日活动未按时进行");
            }

            //组织生活会
            Integer zzshhNumber = affairLifeMeetDao.selectNumber(id,yearL,yearT);
            if (zzshhNumber < 1){
                Num++;
                details.append(",").append(year).append("年").append(month).append("月,组织生活会未按时进行");
            }

            //民主评议
            Integer mzpyNumber = affairCommentDao.selectNumber(id,yearL,yearT);
            if (mzpyNumber < 1){
                Num++;
                details.append(",").append(year).append("年").append(month).append("月,组织生活会未按时进行");
            }
            if (Num > 0){
                ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
                examAutoEvaluation.setType("2");
                examAutoEvaluation.setEvalType("6");
                examAutoEvaluation.setPeriod("1");
                examAutoEvaluation.setYear(year);
                examAutoEvaluation.setMonth(month);
                examAutoEvaluation.setDetails(details + ",");
                examAutoEvaluation.setName(name);
                examAutoEvaluation.setIdNumber(idNumber);
                examAutoEvaluation.setAssess("");
                examAutoEvaluation.setFromSys("智慧政工");
                examAutoEvaluation.setAssessId("");
                examAutoEvaluation.setEvaluationId(examAutoEvaluationDao.selectNoUserid(idNumber));
                examAutoEvaluation.setModel("2020年月度对全局中层和基层领导干部考核公共扣分标准 (公安处领导班子成员除外)");
                examAutoEvaluation.setModelId("d498bf52ce334e3da66851fc57143639");
                examAutoEvaluation.setOption("支部“三会一课”、组织生活会、民主评议党员、两学一做、党日活动等制度未按规定落实的,每缺一项次");
                examAutoEvaluation.setOptionId("bca583d38c29442fa7c73749b1848aa5");
                examAutoEvaluation.setScore(String.valueOf(Num * 5));
            }
        }
    }

    /*
     * 三会一课
     *
     * 中基层
     *
     * 年度
     *
     * */
    public void zjcShykYear(String year){

        Map<String, String> map = yearTime(year);
        String yearL = map.get("yearL");
        String yearT = map.get("yearT");

        //所有党组织
        List<AffairGeneralSituation> affairGeneralSituationList = affairGeneralSituationDao.selectAllParty();

        for (int a = 0; a < affairGeneralSituationList.size(); a++){
            AffairGeneralSituation affairGeneralSituation = affairGeneralSituationList.get(a);
            String id = affairGeneralSituation.getId();
            String name = affairGeneralSituation.getPartyOrganization();
            String shuji = affairGeneralSituation.getShuji();
            String idNumber = userDao.selectUserIdNumber(shuji);

            //缺少的数量
            int Num = 0;

            String type = null;
            StringBuilder details = new StringBuilder();


            //半年  党总支党员大会、党总支党课
            if (name.contains("党总支")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 2){
                    Num++;
                     details.append(",").append(name).append(year).append("年").append("党员大会未按时进行");

                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 2){
                    Num++;
                    details.append(",").append(name).append(year).append("年").append("下半年，党课未按时进行");
                }
            }
            //每季度   党支部党员大会、党支部党课
            if (name.contains("党支部")){

                //党员大会
                type = "1";
                Integer dydhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dydhNumber < 4){
                    Num++;
                    details.append(",").append(name).append(year).append("年"). append("党员大会未按时进行");
                }

                //党课
                type = "5";
                Integer dkNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
                if (dkNumber < 4){
                    Num++;
                    details.append(",").append(name).append(year).append("年").append("党课未按时进行");
                }
            }

            //委员会
            type = "3";

            Integer wyhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (wyhNumber < 12){
                Num++;
                details.append(",").append(year).append("年").append("委员会未按时进行");
            }
            //党小组会
            type = "4";
            Integer dxzhNumber = affairYearThreeOneService.selectHuiyiNumber(id,yearL,yearT,type);
            if (dxzhNumber < 12){
                Num++;
                details.append(",").append(year).append("年").append("党小组会未按时进行");
            }

            //党日活动
            Integer drhdNumber = affairPartyDayActivitiesService.selectNumber(id,yearL,yearT);
            if (drhdNumber < 12){
                Num++;
                details.append(",").append(year).append("年").append("党日活动未按时进行");
            }

            //组织生活会
            Integer zzshhNumber = affairLifeMeetDao.selectNumber(id,yearL,yearT);
            if (zzshhNumber < 12){
                Num++;
                details.append(",").append(year).append("年").append("组织生活会未按时进行");
            }

            //民主评议
            Integer mzpyNumber = affairCommentDao.selectNumber(id,yearL,yearT);
            if (mzpyNumber < 12){
                Num++;
                details.append(",").append(year).append("年").append("月,组织生活会未按时进行");
            }
            if (Num > 0){
                ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
                examAutoEvaluation.setType("2");
                examAutoEvaluation.setEvalType("6");
                examAutoEvaluation.setPeriod("2");
                examAutoEvaluation.setYear(year);
                examAutoEvaluation.setDetails(details + ",");
                examAutoEvaluation.setName(name);
                examAutoEvaluation.setIdNumber(idNumber);
                examAutoEvaluation.setAssess("");
                examAutoEvaluation.setFromSys("智慧政工");
                examAutoEvaluation.setAssessId("");
                examAutoEvaluation.setEvaluationId(examAutoEvaluationDao.selectNoUserid(idNumber));
                examAutoEvaluation.setModel("2020年年度全局中层和基层领导干部考核公共扣分标准(公安处领导班子成员除外)");
                examAutoEvaluation.setModelId("c00cebbbc62c48d888316206d38e02c7");
                examAutoEvaluation.setOption("支部“三会一课”、组织生活会、民主评议党员、两学一做、党日活动等制度未按规定落实的,每缺一项次");
                examAutoEvaluation.setOptionId("5a27b0e8434f4e91ba40071685399a3f");
                examAutoEvaluation.setScore(String.valueOf(Num * 2));
            }
        }
    }

    /*
    * 团支部三会一课
    *
    * 处考派出所
    *
    * 月度
    * */
    public void tyShyk(String checkTime) {

        List<AffairTwBase> nncPartyList = affairTwBaseService.getChuPartyBranch("34", "2");
        List<AffairTwBase> lzcPartyList = affairTwBaseService.getChuPartyBranch("95", "2");
        List<AffairTwBase> bhcPartyList = affairTwBaseService.getChuPartyBranch("156", "2");

        String year = checkTime.substring(0, 4);
        Integer y = Integer.valueOf(year);
        String month = checkTime.substring(checkTime.length() - 2);
        Integer m = Integer.valueOf(month);

        Map<String, String> map = monthTime(checkTime);
        String yearL = map.get("yearL");
        String yearT = map.get("yearT");

        //支委会
        String zwh = "1";

        //团员大会
        String tydh = "2";

        //团课
        String tk = "4";

        for (int p = 0; p < nncPartyList.size(); p++) {
                AffairTwBase affairTwBase = nncPartyList.get(p);

                //团委名称
                String twName = affairTwBase.getName();
                //团委id
                String twId = affairTwBaseService.selectId(twName);
                //团委下设角色数量
                Integer number = affairTwBaseSignService.selectNumber(twId);
                StringBuilder details = new StringBuilder();
                int numSum = 0;
                if (number < 3) {
                    //角色数量小于3是不设支委的团支部
                    Integer tydhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tydh);
                    if (tydhNumber < 1) {
                        numSum++;
                        details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团员大会未按时进行");
                    }
                    Integer tkNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tk);
                    if (tkNumber < 1) {
                        numSum++;
                        details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团课未按时进行");
                    }
                }else {
                    Integer zwhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, zwh);
                    if (zwhNumber < 1) {
                        numSum++;
                        details.append(",").append(twName).append(year).append("年").append(month).append("月").append("支委会未按时进行");

                    }
                    Integer tydhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tydh);
                    if (tydhNumber < 1) {
                        numSum++;
                        details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团员大会未按时进行");
                    }
                }
                if (numSum > 0){
                    ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
                    examAutoEvaluation.setType("1");
                    examAutoEvaluation.setEvalType("3");
                    examAutoEvaluation.setPeriod("1");
                    examAutoEvaluation.setYear(year);
                    examAutoEvaluation.setDetails(details + ",");

                    examAutoEvaluation.setFromSys("智慧政工");
                    examAutoEvaluation.setUnit(twName);
                    examAutoEvaluation.setUnitId(twId);
                    examAutoEvaluation.setAssess("");
                    examAutoEvaluation.setAssessId("");
                    examAutoEvaluation.setModel("南宁公安处月度对派出所2020年度绩效考核评分标准（队伍建设）");
                    examAutoEvaluation.setModelId("ddc6d6dd210e4f428b3a5361f27389fa");
                    examAutoEvaluation.setEvaluationId("");//被考评对象id
                    examAutoEvaluation.setOption("共青团各类月度基础数据信息在智慧政工平台上未按时维护上报或录入出现错误、不及时的");
                    examAutoEvaluation.setOptionId("aed2fe8308484dc493b88c9cd34a42d7");
                    examAutoEvaluation.setScore("2");

                    this.save(examAutoEvaluation);

            }
        }
        for (int p = 0; p < lzcPartyList.size(); p++) {
            AffairTwBase affairTwBase = lzcPartyList.get(p);

            //团委名称
            String twName = affairTwBase.getName();
            //团委id
            String twId = affairTwBaseService.selectId(twName);
            //团委下设角色数量
            Integer number = affairTwBaseSignService.selectNumber(twId);
            StringBuilder details = new StringBuilder();
            int numSum = 0;
            if (number < 3) {
                //角色数量小于3是不设支委的团支部
                Integer tydhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tydh);
                if (tydhNumber < 1) {
                    numSum++;
                    details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团员大会未按时进行");
                }
                Integer tkNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tk);
                if (tkNumber < 1) {
                    numSum++;
                    details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团课未按时进行");
                }
            }else {
                Integer zwhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, zwh);
                if (zwhNumber < 1) {
                    numSum++;
                    details.append(",").append(twName).append(year).append("年").append(month).append("月").append("支委会未按时进行");

                }
                Integer tydhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tydh);
                if (tydhNumber < 1) {
                    numSum++;
                    details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团员大会未按时进行");
                }
            }
            if (numSum > 0){
                ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
                examAutoEvaluation.setType("1");
                examAutoEvaluation.setEvalType("3");
                examAutoEvaluation.setPeriod("1");
                examAutoEvaluation.setYear(year);
                examAutoEvaluation.setDetails(details + ",");

                examAutoEvaluation.setFromSys("智慧政工");
                examAutoEvaluation.setUnit(twName);
                examAutoEvaluation.setUnitId(twId);
                examAutoEvaluation.setAssess("");
                examAutoEvaluation.setAssessId("");
                examAutoEvaluation.setModel("南宁公安处月度对派出所2020年度绩效考核评分标准（队伍建设）");
                examAutoEvaluation.setModelId("ddc6d6dd210e4f428b3a5361f27389fa");
                examAutoEvaluation.setEvaluationId("");//被考评对象id
                examAutoEvaluation.setOption("共青团各类月度基础数据信息在智慧政工平台上未按时维护上报或录入出现错误、不及时的");
                examAutoEvaluation.setOptionId("aed2fe8308484dc493b88c9cd34a42d7");
                examAutoEvaluation.setScore("2");

                this.save(examAutoEvaluation);

            }
        }
        for (int p = 0; p < bhcPartyList.size(); p++) {
            AffairTwBase affairTwBase = bhcPartyList.get(p);

            //团委名称
            String twName = affairTwBase.getName();
            //团委id
            String twId = affairTwBaseService.selectId(twName);
            //团委下设角色数量
            Integer number = affairTwBaseSignService.selectNumber(twId);
            StringBuilder details = new StringBuilder();
            int numSum = 0;
            if (number < 3) {
                //角色数量小于3是不设支委的团支部
                Integer tydhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tydh);
                if (tydhNumber < 1) {
                    numSum++;
                    details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团员大会未按时进行");
                }
                Integer tkNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tk);
                if (tkNumber < 1) {
                    numSum++;
                    details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团课未按时进行");
                }
            }else {
                Integer zwhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, zwh);
                if (zwhNumber < 1) {
                    numSum++;
                    details.append(",").append(twName).append(year).append("年").append(month).append("月").append("支委会未按时进行");

                }
                Integer tydhNumber = affairThreeOneService.selectTydhNum(yearL, yearT, twId, tydh);
                if (tydhNumber < 1) {
                    numSum++;
                    details.append(",").append(twName).append(year).append("年").append(month).append("月").append("团员大会未按时进行");
                }
            }
            if (numSum > 0){
                ExamAutoEvaluation examAutoEvaluation = new ExamAutoEvaluation();
                examAutoEvaluation.setType("1");
                examAutoEvaluation.setEvalType("3");
                examAutoEvaluation.setPeriod("1");
                examAutoEvaluation.setYear(year);
                examAutoEvaluation.setDetails(details + ",");

                examAutoEvaluation.setFromSys("智慧政工");
                examAutoEvaluation.setUnit(twName);
                examAutoEvaluation.setUnitId(twId);
                examAutoEvaluation.setAssess("");
                examAutoEvaluation.setAssessId("");
                examAutoEvaluation.setModel("南宁公安处月度对派出所2020年度绩效考核评分标准（队伍建设）");
                examAutoEvaluation.setModelId("ddc6d6dd210e4f428b3a5361f27389fa");
                examAutoEvaluation.setEvaluationId("");//被考评对象id
                examAutoEvaluation.setOption("共青团各类月度基础数据信息在智慧政工平台上未按时维护上报或录入出现错误、不及时的");
                examAutoEvaluation.setOptionId("aed2fe8308484dc493b88c9cd34a42d7");
                examAutoEvaluation.setScore("2");

                this.save(examAutoEvaluation);

            }
        }
    }


    /*季度考评时间*/
    public Map<String,String> jdTime(String checkTime){

        String year = checkTime.substring(0, 4);
        String month = checkTime.substring(checkTime.length() - 2);

        String yearL = null;
        String yearT = null;
        String jd = null;

        if ("03".equals(month)) {
            int ly = Integer.valueOf(year);
            int lastYear = ly - 1;
            yearL = lastYear + "-12-26";
            yearT = year + "-03-25";
            jd = "01";
        } else if ("06".equals(month)) {
            yearL = year + "-03-26";
            yearT = year + "-06-25";
            jd = "02";
        } else if ("09".equals(month)) {
            yearL = year + "-06-26";
            yearT = year + "-09-25";
            jd = "03";
        } else if ("12".equals(month)) {
            yearL = year + "-09-26";
            yearT = year + "-12-25";
            jd = "04";
        }
        Map<String,String> map = new HashMap<>();
        map.put("yearL",yearL);
        map.put("yearT",yearT);
        map.put("jd",jd);
        return map;
    }

    /*月度考评时间*/
    public Map<String,String> monthTime(String checkTime){

        String year = checkTime.substring(0, 4);
        Integer y = Integer.valueOf(year);
        String month = checkTime.substring(checkTime.length() - 2);
        Integer m = Integer.valueOf(month);
        String yearL = null;
        String yearT = null;
        int lastYear = y - 1;
        int lastMonth = m - 1;
        if (m == 1){
            yearL = lastYear + "-12-26";
        }else {
            if(m < 10){
                yearL = year + "-0" + lastMonth + "-26";
            }else {
                yearL = year + "-" + lastMonth + "-26";
            }
        }
        yearT = year + "-" + month + "-25";
        Map<String,String> map = new HashMap<>();
        map.put("yearL",yearL);
        map.put("yearT",yearT);
        return map;
    }

    //年度考评时间
    public Map<String,String> yearTime(String year){

        Integer y = Integer.valueOf(year);
        Integer lastYear = y - 1;
        String yearL = lastYear + "-" + "12-02";
        String yearT = year + "-" + "12-01";

        Map<String,String>map = new HashMap<>();
        map.put("yearL",yearL);
        map.put("yearT",yearT);
        return map;
    }

}


