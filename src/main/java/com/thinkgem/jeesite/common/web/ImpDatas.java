package com.thinkgem.jeesite.common.web;

import com.google.common.collect.Maps;

import java.util.Map;

public class ImpDatas {
    private ImpDatas() {
        this.init();
    }

    private static ImpDatas datas = null;
    private Map<String, Object> map = Maps.newHashMap();

    public static synchronized ImpDatas getInstance() {
        if (datas == null) {
            synchronized (ImpDatas.class) {
                if (datas == null)
                    datas = new ImpDatas();
            }
        }
        return datas;
    }

    public Map<String, String> getValue(String key) {
        return (Map<String, String>) map.get(key);
    }

    private void init() {
        //人员信息管理-基本信息
        Map<String, String> personBaseMap = Maps.newHashMap();
        personBaseMap.put("url", "/personnel/personnelBase/import");
        personBaseMap.put("template", "基本信息集.xlsx");
        map.put("personnel_personnelBase", personBaseMap);

        //人员信息管理-学位信息集
        Map<String, String> personnelAcademicDegreeMap = Maps.newHashMap();
        personnelAcademicDegreeMap.put("url", "/personnel/personnelAcademicDegree/import");
        personnelAcademicDegreeMap.put("template", "学位信息集.xlsx");
        map.put("personnel_personnelAcademicDegree", personnelAcademicDegreeMap);

        //人员信息管理-专业技术工作及成果信息集
        Map<String, String> personnelAchievementMap = Maps.newHashMap();
        personnelAchievementMap.put("url", "/personnel/personnelAchievement/import");
        personnelAchievementMap.put("template", "专业技术工作及成果信息集.xlsx");
        map.put("personnel_personnelAchievement", personnelAchievementMap);

        //人员信息管理-增员信息集
        Map<String, String> personnelAddPersonMap = Maps.newHashMap();
        personnelAddPersonMap.put("url", "/personnel/personnelAddPerson/import");
        personnelAddPersonMap.put("template", "增员信息集.xlsx");
        map.put("personnel_personnelAddPerson", personnelAddPersonMap);

        //人员信息管理-住址通信信息集
        Map<String, String> personnelAddressMap = Maps.newHashMap();
        personnelAddressMap.put("url", "/personnel/personnelAddress/import");
        personnelAddressMap.put("template", "住址通信信息集.xlsx");
        map.put("personnel_personnelAddress", personnelAddressMap);

        //人员信息管理-行政职务信息集
        Map<String, String> personnelAdministrationMap = Maps.newHashMap();
        personnelAdministrationMap.put("url", "/personnel/personnelAdministration/import");
        personnelAdministrationMap.put("template", "行政职务信息集.xlsx");
        map.put("personnel_personnelAdministration", personnelAdministrationMap);

        //人员信息管理-专业技术工作获奖信息集
        Map<String, String> personnelAwardMap = Maps.newHashMap();
        personnelAwardMap.put("url", "/personnel/personnelAward/import");
        personnelAwardMap.put("template", "专业技术工作获奖信息集.xlsx");
        map.put("personnel_personnelAward", personnelAwardMap);


        //人员信息管理-体格检查信息集
        Map<String, String> personnelBuildMap = Maps.newHashMap();
        personnelBuildMap.put("url", "/personnel/personnelBuild/import");
        personnelBuildMap.put("template", "体格检查信息集.xlsx");
        map.put("personnel_personnelBuild", personnelBuildMap);

        //人员信息管理-案件技术支持信息集
        Map<String, String> personnelCaseMap = Maps.newHashMap();
        personnelCaseMap.put("url", "/personnel/personnelCase/import");
        personnelCaseMap.put("template", "案件技术支持信息集.xlsx");
        map.put("personnel_personnelCase", personnelCaseMap);

        //人员信息管理-伤亡信息集
        Map<String, String> personnelCasualtyMap = Maps.newHashMap();
        personnelCasualtyMap.put("url", "/personnel/personnelCasualty/import");
        personnelCasualtyMap.put("template", "伤亡信息集.xlsx");
        map.put("personnel_personnelCasualty", personnelCasualtyMap);

        //人员信息管理-公务员登记信息集
        Map<String, String> personnelCivilServantMap = Maps.newHashMap();
        personnelCivilServantMap.put("url", "/personnel/personnelCivilServant/import");
        personnelCivilServantMap.put("template", "公务员登记信息集.xlsx");
        map.put("personnel_personnelCivilServant", personnelCivilServantMap);

        //人员信息管理-交流信息集
        Map<String, String> personnelCommunicationMap = Maps.newHashMap();
        personnelCommunicationMap.put("url", "/personnel/personnelCommunication/import");
        personnelCommunicationMap.put("template", "交流信息集.xlsx");
        map.put("personnel_personnelCommunication", personnelCommunicationMap);

        //人员信息管理-日常联系情况信息集
        Map<String, String> personnelDailyContactMap = Maps.newHashMap();
        personnelDailyContactMap.put("url", "/personnel/personnelDailyContact/import");
        personnelDailyContactMap.put("template", "日常联系情况信息集.xlsx");
        map.put("personnel_personnelDailyContact", personnelDailyContactMap);

        //人员信息管理-教育培训（进修）信息集
        Map<String, String> personnelEducationTrainMap = Maps.newHashMap();
        personnelEducationTrainMap.put("url", "/personnel/personnelEducationTrain/import");
        personnelEducationTrainMap.put("template", "教育培训（进修）信息集.xlsx");
        map.put("personnel_personnelEducationTrain", personnelEducationTrainMap);

        //人员信息管理-聘用信息集
        Map<String, String> personnelEmployMap = Maps.newHashMap();
        personnelEmployMap.put("url", "/personnel/personnelEmploy/import");
        personnelEmployMap.put("template", "聘用信息集.xlsx");
        map.put("personnel_personnelEmploy", personnelEmployMap);

        //人员信息管理-录（入）警信息集
        Map<String, String> personnelEnterPoliceMap = Maps.newHashMap();
        personnelEnterPoliceMap.put("url", "/personnel/personnelEnterPolice/import");
        personnelEnterPoliceMap.put("template", "录（入）警信息集.xlsx");
        map.put("personnel_personnelEnterPolice", personnelEnterPoliceMap);

        //人员信息管理-执法资格等级考试情况信息集
        Map<String, String> personnelExamMap = Maps.newHashMap();
        personnelExamMap.put("url", "/personnel/personnelExam/import");
        personnelExamMap.put("template", "执法资格等级考试情况信息集.xlsx");
        map.put("personnel_personnelExam", personnelExamMap);

        //人员信息管理-评审专家经历信息集
        Map<String, String> personnelExpertMap = Maps.newHashMap();
        personnelExpertMap.put("url", "/personnel/personnelExpert/import");
        personnelExpertMap.put("template", "评审专家经历信息集.xlsx");
        map.put("personnel_personnelExpert", personnelExpertMap);

        //人员信息管理-家庭成员及社会关系信息集
        Map<String, String> personnelFamilyMap = Maps.newHashMap();
        personnelFamilyMap.put("url", "/personnel/personnelFamily/import");
        personnelFamilyMap.put("template", "家庭成员及社会关系信息集.xlsx");
        map.put("personnel_personnelFamily", personnelFamilyMap);

        //人员信息管理-高层次人才信息集
        Map<String, String> personnelHighLevelMap = Maps.newHashMap();
        personnelHighLevelMap.put("url", "/personnel/personnelHighLevel/import");
        personnelHighLevelMap.put("template", "高层次人才信息集.xlsx");
        map.put("personnel_personnelHighLevel", personnelHighLevelMap);

        //人员信息管理-人事档案管理信息集
        Map<String, String> personnelHrRecordMap = Maps.newHashMap();
        personnelHrRecordMap.put("url", "/personnel/personnelHrRecord/import");
        personnelHrRecordMap.put("template", "人事档案管理信息集.xlsx");
        map.put("personnel_personnelHrRecord", personnelHrRecordMap);

        //人员信息管理-参与重要活动信息集
        Map<String, String> personnelImportantActivityMap = Maps.newHashMap();
        personnelImportantActivityMap.put("url", "/personnel/personnelImportantActivity/import");
        personnelImportantActivityMap.put("template", "参与重要活动信息集.xlsx");
        map.put("personnel_personnelImportantActivity", personnelImportantActivityMap);

        //人员信息管理-籍贯地址
        Map<String, String> personnelHometownMap = Maps.newHashMap();
        personnelHometownMap.put("url", "/personnel/personnelHometown/import");
        personnelHometownMap.put("template", "籍贯地址.xlsx");
        map.put("personnel_personnelHometown", personnelHometownMap);

        //人员信息管理-教官信息集
        Map<String, String> personnelInstructorMap = Maps.newHashMap();
        personnelInstructorMap.put("url", "/personnel/personnelInstructor/import");
        personnelInstructorMap.put("template", "教官信息集.xlsx");
        map.put("personnel_personnelInstructor", personnelInstructorMap);

        //人员信息管理-社会保险信息集
        Map<String, String> personnelInsuranceMap = Maps.newHashMap();
        personnelInsuranceMap.put("url", "/personnel/personnelInsurance/import");
        personnelInsuranceMap.put("template", "社会保险信息集.xlsx");
        map.put("personnel_personnelInsurance", personnelInsuranceMap);

        //人员信息管理-涉密信息集
        Map<String, String> personnelInvolveSecretMap = Maps.newHashMap();
        personnelInvolveSecretMap.put("url", "/personnel/personnelInvolveSecret/import");
        personnelInvolveSecretMap.put("template", "涉密信息集.xlsx");
        map.put("personnel_personnelInvolveSecret", personnelInvolveSecretMap);

        //人员信息管理-职务层次信息集
        Map<String, String> personnelJobMap = Maps.newHashMap();
        personnelJobMap.put("url", "/personnel/personnelJob/import");
        personnelJobMap.put("template", "职务层次信息集.xlsx");
        map.put("personnel_personnelJob", personnelJobMap);

        //人员信息管理-语言能力信息集
        Map<String, String> personnelLanguageMap = Maps.newHashMap();
        personnelLanguageMap.put("url", "/personnel/personnelLanguage/import");
        personnelLanguageMap.put("template", "语言能力信息集.xlsx");
        map.put("personnel_personnelLanguage", personnelLanguageMap);

        //人员信息管理-组织考核考察（审查）信息集
        Map<String, String> personnelOrgCheckMap = Maps.newHashMap();
        personnelOrgCheckMap.put("url", "/personnel/personnelOrgCheck/import");
        personnelOrgCheckMap.put("template", "组织考核考察（审查）信息集.xlsx");
        map.put("personnel_personnelOrgCheck", personnelOrgCheckMap);

        //人员信息管理-党员信息集
        Map<String, String> personnelPartyMemberMap = Maps.newHashMap();
        personnelPartyMemberMap.put("url", "/personnel/personnelPartyMember/import");
        personnelPartyMemberMap.put("template", "党员信息集.xlsx");
        map.put("personnel_personnelPartyMember", personnelPartyMemberMap);

        //人员信息管理-获取专利信息集
        Map<String, String> personnelPatentMap = Maps.newHashMap();
        personnelPatentMap.put("url", "/personnel/personnelPatent/import");
        personnelPatentMap.put("template", "获取专利信息集.xlsx");
        map.put("personnel_personnelPatent", personnelPatentMap);

        //人员信息管理-抚恤信息集
        Map<String, String> personnelPensionMap = Maps.newHashMap();
        personnelPensionMap.put("url", "/personnel/personnelPension/import");
        personnelPensionMap.put("template", "抚恤信息集.xlsx");
        map.put("personnel_personnelPension", personnelPensionMap);

        //人员信息管理-抚恤金发放记录信息集
        Map<String, String> personnelPensionRecordMap = Maps.newHashMap();
        personnelPensionRecordMap.put("url", "/personnel/personnelPensionRecord/import");
        personnelPensionRecordMap.put("template", "抚恤金发放记录信息集.xlsx");
        map.put("personnel_personnelPensionRecord", personnelPensionRecordMap);

        //人员信息管理-人员编制信息集
        Map<String, String> personnelPersonBianzhiMap = Maps.newHashMap();
        personnelPersonBianzhiMap.put("url", "/personnel/personnelPersonBianzhi/import");
        personnelPersonBianzhiMap.put("template", "人员编制信息集.xlsx");
        map.put("personnel_personnelPersonBianzhi", personnelPersonBianzhiMap);

        //人员信息管理-人民警察证信息集
        Map<String, String> personnelPoliceCertificateMap = Maps.newHashMap();
        personnelPoliceCertificateMap.put("url", "/personnel/personnelPoliceCertificate/import");
        personnelPoliceCertificateMap.put("template", "人民警察证信息集.xlsx");
        map.put("personnel_personnelPoliceCertificate", personnelPoliceCertificateMap);

        //人员信息管理-衔称信息集
        Map<String, String> personnelPoliceRankMap = Maps.newHashMap();
        personnelPoliceRankMap.put("url", "/personnel/personnelPoliceRank/import");
        personnelPoliceRankMap.put("template", "衔称信息集.xlsx");
        map.put("personnel_personnelPoliceRank", personnelPoliceRankMap);

        //人员信息管理-警务技术(专业技术)职务信息集
        Map<String, String> personnelPoliceWork1Map = Maps.newHashMap();
        personnelPoliceWork1Map.put("url", "/personnel/personnelPoliceWork1/import");
        personnelPoliceWork1Map.put("template", "警务技术(专业技术)职务信息集.xlsx");
        map.put("personnel_personnelPoliceWork1", personnelPoliceWork1Map);

        //人员信息管理-警务技术(专业技术)任职资格信息集
        Map<String, String> personnelPoliceWork2Map = Maps.newHashMap();
        personnelPoliceWork2Map.put("url", "/personnel/personnelPoliceWork2/import");
        personnelPoliceWork2Map.put("template", "警务技术(专业技术)任职资格信息集.xlsx");
        map.put("personnel_personnelPoliceWork2", personnelPoliceWork2Map);

        //人员信息管理-岗位变动信息集
        Map<String, String> personnelPostChangeMap = Maps.newHashMap();
        personnelPostChangeMap.put("url", "/personnel/personnelPostChange/import");
        personnelPostChangeMap.put("template", "岗位变动信息集.xlsx");
        map.put("personnel_personnelPostChange", personnelPostChangeMap);

        //人员信息管理-惩戒信息集
        Map<String, String> personnelPunishMap = Maps.newHashMap();
        personnelPunishMap.put("url", "/personnel/personnelPunish/import");
        personnelPunishMap.put("template", "惩戒信息集.xlsx");
        map.put("personnel_personnelPunish", personnelPunishMap);

        //人员信息管理-疗（休）养信息集
        Map<String, String> personnelRecuperateMap = Maps.newHashMap();
        personnelRecuperateMap.put("url", "/personnel/personnelRecuperate/import");
        personnelRecuperateMap.put("template", "疗（休）养信息集.xlsx");
        map.put("personnel_personnelRecuperate", personnelRecuperateMap);

        //人员信息管理-减员信息集
        Map<String, String> personnelReducePersonMap = Maps.newHashMap();
        personnelReducePersonMap.put("url", "/personnel/personnelReducePerson/import");
        personnelReducePersonMap.put("template", "减员信息集.xlsx");
        map.put("personnel_personnelReducePerson", personnelReducePersonMap);

        //人员信息管理-后备干部信息集
        Map<String, String> personnelReserveCadreMap = Maps.newHashMap();
        personnelReserveCadreMap.put("url", "/personnel/personnelReserveCadre/import");
        personnelReserveCadreMap.put("template", "后备干部信息集.xlsx");
        map.put("personnel_personnelReserveCadre", personnelReserveCadreMap);

        //人员信息管理-履历信息集
        Map<String, String> personnelResumeMap = Maps.newHashMap();
        personnelResumeMap.put("url", "/personnel/personnelResume/import");
        personnelResumeMap.put("template", "履历信息集.xlsx");
        map.put("personnel_personnelResume", personnelResumeMap);

        //人员信息管理-离退信息集
        Map<String, String> personnelRetreatMap = Maps.newHashMap();
        personnelRetreatMap.put("url", "/personnel/personnelRetreat/import");
        personnelRetreatMap.put("template", "离退信息集.xlsx");
        map.put("personnel_personnelRetreat", personnelRetreatMap);

        //人员信息管理-奖励信息集
        Map<String, String> personnelRewardMap = Maps.newHashMap();
        personnelRewardMap.put("url", "/personnel/personnelReward/import");
        personnelRewardMap.put("template", "奖励信息集.xlsx");
        map.put("personnel_personnelReward", personnelRewardMap);

        //人员信息管理-工资信息集
        Map<String, String> personnelSalaryMap = Maps.newHashMap();
        personnelSalaryMap.put("url", "/personnel/personnelSalary/import");
        personnelSalaryMap.put("template", "工资信息集.xlsx");
        map.put("personnel_personnelSalary", personnelSalaryMap);

        //人员信息管理-工资发放信息集
        Map<String, String> personnelSalaryPayMap = Maps.newHashMap();
        personnelSalaryPayMap.put("url", "/personnel/personnelSalaryPay/import");
        personnelSalaryPayMap.put("template", "工资发放信息集.xlsx");
        map.put("personnel_personnelSalaryPay", personnelSalaryPayMap);

        //人员信息管理-业绩信息集
        Map<String, String> personnelScoreMap = Maps.newHashMap();
        personnelScoreMap.put("url", "/personnel/personnelScore/import");
        personnelScoreMap.put("template", "业绩信息集.xlsx");
        map.put("personnel_personnelScore", personnelScoreMap);

        //人员信息管理-专长信息集
        Map<String, String> personnelSkillMap = Maps.newHashMap();
        personnelSkillMap.put("url", "/personnel/personnelSkill/import");
        personnelSkillMap.put("template", "高层次人才表.xlsx");
        map.put("personnel_personnelSkill", personnelSkillMap);

        //人员信息管理-高层次人才表
        Map<String, String> personnelSpecialityMap = Maps.newHashMap();
        personnelSpecialityMap.put("url", "/personnel/personnelSpeciality/import");
        personnelSpecialityMap.put("template", "专长信息集.xlsx");
        map.put("personnel_personnelSpeciality", personnelSpecialityMap);




        //人员信息管理-社会团体任职信息集
        Map<String, String> personnelSocialGroupMap = Maps.newHashMap();
        personnelSocialGroupMap.put("url", "/personnel/personnelSocialGroup/import");
        personnelSocialGroupMap.put("template", "社会团体任职信息集.xlsx");
        map.put("personnel_personnelSocialGroup", personnelSocialGroupMap);

        //人员信息管理-补充信息集
        Map<String, String> personnelSupplementMap = Maps.newHashMap();
        personnelSupplementMap.put("url", "/personnel/personnelSupplement/import");
        personnelSupplementMap.put("template", "补充信息集.xlsx");
        map.put("personnel_personnelSupplement", personnelSupplementMap);

        //人员信息管理-退出现役军人（武警）信息集
        Map<String, String> personnelTcxyjrMap = Maps.newHashMap();
        personnelTcxyjrMap.put("url", "/personnel/personnelTcxyjr/import");
        personnelTcxyjrMap.put("template", "退出现役军人（武警）信息集.xlsx");
        map.put("personnel_personnelTcxyjr", personnelTcxyjrMap);

        //人员信息管理-公务员枪持枪证信息集
        Map<String, String> personnelUseGunMap = Maps.newHashMap();
        personnelUseGunMap.put("url", "/personnel/personnelUseGun/import");
        personnelUseGunMap.put("template", "公务员枪持枪证信息集.xlsx");
        map.put("personnel_personnelUseGun", personnelUseGunMap);

        //人员信息管理-休假信息集
        Map<String, String> personnelVacationMap = Maps.newHashMap();
        personnelVacationMap.put("url", "/personnel/personnelVacation/import");
        personnelVacationMap.put("template", "休假信息集.xlsx");
        map.put("personnel_personnelVacation", personnelVacationMap);

        //人员信息管理-工人技术等级信息集
        Map<String, String> personnelWorkerTechGradeMap = Maps.newHashMap();
        personnelWorkerTechGradeMap.put("url", "/personnel/personnelWorkerTechGrade/import");
        personnelWorkerTechGradeMap.put("template", "工人技术等级信息集.xlsx");
        map.put("personnel_personnelWorkerTechGrade", personnelWorkerTechGradeMap);

        //人员信息管理-论著信息集
        Map<String, String> personnelWorksMap = Maps.newHashMap();
        personnelWorksMap.put("url", "/personnel/personnelWorks/import");
        personnelWorksMap.put("template", "论著信息集.xlsx");
        map.put("personnel_personnelWorks", personnelWorksMap);

        //人员信息管理-协管干部信息集
        Map<String, String> personnelXieguanCadreMap = Maps.newHashMap();
        personnelXieguanCadreMap.put("url", "/personnel/personnelXieguanCadre/import");
        personnelXieguanCadreMap.put("template", "协管干部信息集.xlsx");
        map.put("personnel_personnelXieguanCadre", personnelXieguanCadreMap);

        //人员信息管理-学历信息集
        Map<String, String> personnelXueliMap = Maps.newHashMap();
        personnelXueliMap.put("url", "/personnel/personnelXueli/import");
        personnelXueliMap.put("template", "学历信息集.xlsx");
        map.put("personnel_personnelXueli", personnelXueliMap);

        //人员信息管理-年度考核信息集
        Map<String, String> personnelYearCheckMap = Maps.newHashMap();
        personnelYearCheckMap.put("url", "/personnel/personnelYearCheck/import");
        personnelYearCheckMap.put("template", "年度考核信息集.xlsx");
        map.put("personnel_personnelYearCheck", personnelYearCheckMap);

        //人员信息管理-优抚信息集
        Map<String, String> personnelYoufuMap = Maps.newHashMap();
        personnelYoufuMap.put("url", "/personnel/personnelYoufu/import");
        personnelYoufuMap.put("template", "优抚信息集.xlsx");
        map.put("personnel_personnelYoufu", personnelYoufuMap);

        //人员信息管理-出国（境）信息集
        Map<String, String> personnelGoAbroadMap = Maps.newHashMap();
        personnelGoAbroadMap.put("url", "/affair/personnelGoAbroad/import");
        personnelGoAbroadMap.put("template", "出国（境）信息集.xlsx");
        map.put("personnel_personnelGoAbroad", personnelGoAbroadMap);

        //人员信息管理-护照（通行证）信息集
        Map<String, String> personnelPassportMap = Maps.newHashMap();
        personnelPassportMap.put("url", "/affair/personnelPassport/import");
        personnelPassportMap.put("template", "护照（通行证）信息集.xlsx");
        map.put("personnel_personnelPassport", personnelPassportMap);

        //测试-人员信息导入
        Map<String, String> testMap = Maps.newHashMap();
        testMap.put("url", "/sys/user/import");
        testMap.put("template", "用户数据导出模板.xlsx");
        map.put("test", testMap);

        //机构信息管理-单位编制信息
        Map<String, String> orgBianzhiMap = Maps.newHashMap();
        orgBianzhiMap.put("url", "/org/orgBianzhi/import");
        orgBianzhiMap.put("template", "单位编制信息集.xlsx");
        map.put("org_orgBianzhi", orgBianzhiMap);

        //机构信息管理-单位职数信息
        Map<String, String> orgJobNumberMap = Maps.newHashMap();
        orgJobNumberMap.put("url", "/org/orgJobNumber/import");
        orgJobNumberMap.put("template", "单位职数信息集.xlsx");
        map.put("org_orgJobNumber", orgJobNumberMap);

        //机构信息管理-单位奖励信息
        Map<String, String> orgRewardMap = Maps.newHashMap();
        orgRewardMap.put("url", "/org/orgReward/import");
        orgRewardMap.put("template", "单位奖励信息集.xlsx");
        map.put("org_orgReward", orgRewardMap);

        //机构信息管理-单位通讯信息
        Map<String, String> orgCommunicationMap = Maps.newHashMap();
        orgCommunicationMap.put("url", "/org/orgCommunication/import");
        orgCommunicationMap.put("template", "单位通讯信息集.xlsx");
        map.put("org_orgCommunication", orgCommunicationMap);

        //事务信息管理-干部-党组织概况信息
        Map<String, String> affairGeneralSituationMap = Maps.newHashMap();
        affairGeneralSituationMap.put("url", "/affair/affairGeneralSituation/import");
        affairGeneralSituationMap.put("template", "党组织概况表.xlsx");
        map.put("affair_affairGeneralSituation", affairGeneralSituationMap);



        //事务信息管理-干部人事-社保管理-养老保险
        Map<String, String> personnelEndowmentInsuranceMap = Maps.newHashMap();
        personnelEndowmentInsuranceMap.put("url", "/personnel/personnelEndowmentInsurance/import");
        personnelEndowmentInsuranceMap.put("template", "养老保险表.xlsx");
        map.put("personnel_personnelEndowmentInsurance", personnelEndowmentInsuranceMap);

        //事务信息管理-干部人事-社保管理-商业保险
        Map<String, String> affairCommercialInsurance = Maps.newHashMap();
        affairCommercialInsurance.put("url", "/affair/affairCommercialInsurance/import");
        affairCommercialInsurance.put("template", "商业保险表.xlsx");
        map.put("affair_affairCommercialInsurance", affairCommercialInsurance);


        //事务信息管理-党建概况-党员花名册
        Map<String, String> affairPartyMemberMap = Maps.newHashMap();
        affairPartyMemberMap.put("url", "/affair/affairPartyMember/import");
        affairPartyMemberMap.put("template", "党员花名册表.xlsx");
        map.put("affair_affairPartyMember", affairPartyMemberMap);

        //事务信息管理-党组织综合信息-入党申请人
        Map<String, String> affairApplicantMap = Maps.newHashMap();
        affairApplicantMap.put("url", "/affair/affairApplicant/import");
        affairApplicantMap.put("template", "入党申请人表.xlsx");
        map.put("affair_affairApplicant", affairApplicantMap);

        //事务信息管理-党组织综合信息-入党积极分子
        Map<String, String> affairActivistMap = Maps.newHashMap();
        affairActivistMap.put("url", "/affair/affairActivist/import");
        affairActivistMap.put("template", "入党积极分子表.xlsx");
        map.put("affair_affairActivist", affairActivistMap);

        //事务信息管理-党组织综合信息-发展对象
        Map<String, String> affairDevelopObjectMap = Maps.newHashMap();
        affairDevelopObjectMap.put("url", "/affair/affairDevelopObject/import");
        affairDevelopObjectMap.put("template", "发展对象表.xlsx");
        map.put("affair_affairDevelopObject", affairDevelopObjectMap);

        //事务信息管理-党委书记述职测评
        Map<String, String> affairAssessMap = Maps.newHashMap();
        affairAssessMap.put("url", "/affair/affairAssess/import");
        affairAssessMap.put("template", "党委书记述职测评表.xlsx");
        map.put("affair_affairAssess", affairAssessMap);

        //事务信息管理-党委书记述职测评
        Map<String, String> affairPartyCostMap = Maps.newHashMap();
        affairPartyCostMap.put("url", "/affair/affairPartyCost/import");
        affairPartyCostMap.put("template", "党费管理表.xlsx");
        map.put("affair_affairPartyCost", affairPartyCostMap);

        //事务信息管理-党员管理-民主评议
        Map<String, String> affairCommentMap = Maps.newHashMap();
        affairCommentMap.put("url", "/affair/affairComment/import");
        affairCommentMap.put("template", "民主评议表.xlsx");
        map.put("affair_affairComment", affairCommentMap);

        //事务信息管理-党员管理-组织隶属
        Map<String, String> affairBelongToMap = Maps.newHashMap();
        affairBelongToMap.put("url", "/affair/affairBelongTo/import");
        affairBelongToMap.put("template", "组织隶属表.xlsx");
        map.put("affair_affairBelongTo", affairBelongToMap);

        //事务信息管理-党员管理-奖惩信息
        Map<String, String> affairPartyRewardPunishMap = Maps.newHashMap();
        affairPartyRewardPunishMap.put("url", "/affair/affairPartyRewardPunish/import");
        affairPartyRewardPunishMap.put("template", "党员奖惩信息表.xlsx");
        map.put("affair_affairPartyRewardPunish", affairPartyRewardPunishMap);

        //事务信息管理-党组织综合信息-组织奖惩信息
        Map<String, String> affairOrgRewardPunishMap = Maps.newHashMap();
        affairOrgRewardPunishMap.put("url", "/affair/affairOrgRewardPunish/import");
        affairOrgRewardPunishMap.put("template", "党组织奖惩信息表.xlsx");
        map.put("affair_affairOrgRewardPunish", affairOrgRewardPunishMap);

        //事务信息管理-支部会议活动信息-两学一做学习教育-月度学习计划
        Map<String, String> affairMonthStudyMap = Maps.newHashMap();
        affairMonthStudyMap.put("url", "/affair/affairMonthStudy/import");
        affairMonthStudyMap.put("template", "月度学习计划表.xlsx");
        map.put("affair_affairMonthStudy", affairMonthStudyMap);

        //事务信息管理-支部会议活动信息-两学一做学习教育-月度学习计划
        Map<String, String> affairTwoOneMap = Maps.newHashMap();
        affairTwoOneMap.put("url", "/affair/affairTwoOne/import");
        affairTwoOneMap.put("template", "两学一做专题学习.xlsx");
        map.put("affair_affairTwoOne", affairTwoOneMap);

        //事务信息管理-支部会议活动信息-两学一做学习教育-月度学习计划
        Map<String, String> affairThemeActivityMap = Maps.newHashMap();
        affairThemeActivityMap.put("url", "/affair/affairThemeActivity/import");
        affairThemeActivityMap.put("template", "党内主题实践活动表.xlsx");
        map.put("affair_affairThemeActivity", affairThemeActivityMap);

        //事务信息管理-党组织综合信息-班子成员
        Map<String, String> affairClassMemberMap = Maps.newHashMap();
        affairClassMemberMap.put("url", "/affair/affairClassMember/import");
        affairClassMemberMap.put("template", "班子成员表.xlsx");
        map.put("affair_affairClassMember", affairClassMemberMap);

        //事务信息管理-支部会议活动信息-党支部载体运用-党内创品牌活动
        Map<String, String> affairCreateBranchMap = Maps.newHashMap();
        affairCreateBranchMap.put("url", "/affair/affairCreateBranch/import");
        affairCreateBranchMap.put("template", "党内创品牌活动表.xlsx");
        map.put("affair_affairCreateBranch", affairCreateBranchMap);

        //事物信息管理-宣传教育管理-党委中心组学习
        Map<String, String> affairExamManagementMap = Maps.newHashMap();
        affairExamManagementMap.put("url", "/affair/affairExamManagement/import");
        affairExamManagementMap.put("template", "考核成绩表.xlsx");
        map.put("affair_affairExamManagement", affairExamManagementMap);

        //事物信息管理-宣传教育管理-党委中心组学习
        Map<String, String> affairGroupStudyMap = Maps.newHashMap();
        affairGroupStudyMap.put("url", "/affair/affairGroupStudy/import");
        affairGroupStudyMap.put("template", "2.1党委中心组学习.xlsx");
        map.put("affair_affairGroupStudy", affairGroupStudyMap);

        //事物信息管理-宣传教育管理-党支部集中学习
        Map<String, String> affairFocusStudyMap = Maps.newHashMap();
        affairFocusStudyMap.put("url", "/affair/affairFocusStudy/import");
        affairFocusStudyMap.put("template", "党支部集中学习表.xlsx");
        map.put("affair_affairFocusStudy", affairFocusStudyMap);

        //事物信息管理-宣传信息管理-网评员管理
        Map<String, String> affairCommentatorsMap = Maps.newHashMap();
        affairCommentatorsMap.put("url", "/affair/affairCommentators/import");
        affairCommentatorsMap.put("template", "6.4网评员.xlsx");
        map.put("affair_affairCommentators", affairCommentatorsMap);

        //事物信息管理-宣传信息管理-网评员管理
        Map<String, String> affairBookCatalogMap = Maps.newHashMap();
        affairBookCatalogMap.put("url", "/affair/affairBookCatalog/import");
        affairBookCatalogMap.put("template", "历年书目表.xlsx");
        map.put("affair_affairBookCatalog", affairBookCatalogMap);

        //事物信息管理-宣传信息管理-读书先进个人表
        Map<String, String> affairAdvancedPersonMap = Maps.newHashMap();
        affairAdvancedPersonMap.put("url", "/affair/affairAdvancedPerson/import");
        affairAdvancedPersonMap.put("template", "读书先进个人表.xlsx");
        map.put("affair_affairAdvancedPerson", affairAdvancedPersonMap);

        //事物信息管理-宣传信息管理-读书先进集体表
        Map<String, String> affairAdvancedCollectiveMap = Maps.newHashMap();
        affairAdvancedCollectiveMap.put("url", "/affair/affairAdvancedCollective/import");
        affairAdvancedCollectiveMap.put("template", "读书先进集体表.xlsx");
        map.put("affair_affairAdvancedCollective", affairAdvancedCollectiveMap);

        //事物信息管理-宣传信息管理-读书先进集体表
        Map<String, String> affairActiveMap = Maps.newHashMap();
        affairActiveMap.put("url", "/affair/affairActive/import");
        affairActiveMap.put("template", "5.2读书活动活动情况.xlsx");
        map.put("affair_affairActive", affairActiveMap);

        //事物信息管理-纪检监察信息管理-投诉举报管理
        Map<String, String> affairTousujubaoguanliMap = Maps.newHashMap();
        affairTousujubaoguanliMap.put("url", "/affair/affairTousujubaoguanli/import");
        affairTousujubaoguanliMap.put("template", "信访举报投诉表.xlsx");
        map.put("affair_affairTousujubaoguanli", affairTousujubaoguanliMap);

        //事物信息管理-工团工作信息管理-纪律规定
        Map<String, String> affairDisciplinaryRegulationMap = Maps.newHashMap();
        affairDisciplinaryRegulationMap .put("url", "/affair/ affairDisciplinaryRegulation/import");
        affairDisciplinaryRegulationMap .put("template", "纪律规定.xlsx");
        map.put("affair_affairDisciplinaryRegulation", affairDisciplinaryRegulationMap);

        //事物信息管理-工团工作信息管理-纪律处分
        Map<String, String> affairDisciplinaryActionMap = Maps.newHashMap();
        affairDisciplinaryActionMap .put("url", "/affair/ affairDisciplinaryAction/import");
        affairDisciplinaryActionMap .put("template", "纪律处分表.xlsx");
        map.put("affair_affairDisciplinaryAction", affairDisciplinaryActionMap);

        //事物信息管理-工团工作信息管理-党组织纪律处分
        Map<String, String> affairDisciplinaryActionDzzMap = Maps.newHashMap();
        affairDisciplinaryActionDzzMap .put("url", "/affair/ affairDisciplinaryActionDzz/import");
        affairDisciplinaryActionDzzMap .put("template", "党组织纪律处分表.xlsx");
        map.put("affair_affairDisciplinaryActionDzz", affairDisciplinaryActionDzzMap);

        //事物信息管理-组干信息管理-请销假信息管理
        Map<String, String> affairQjMap = Maps.newHashMap();
        affairQjMap.put("url", "/affair/affairQj/import");
        affairQjMap.put("template", "请销假记录表.xlsx");
        map.put("affair_affairQj", affairQjMap);

        //事物信息管理-纪检监察信息管理-谈话函询管理
        Map<String, String> affairTalkManagementMap = Maps.newHashMap();
        affairTalkManagementMap.put("url", "/affair/affairTalkManagement/import");
        affairTalkManagementMap.put("template", "谈话函询管理表.xlsx");
        map.put("affair_affairTalkManagement", affairTalkManagementMap);

        //事物信息管理-干部工作管理-考核
        Map<String, String> affairLeaderCheckMap = Maps.newHashMap();
        affairLeaderCheckMap.put("url", "/affair/affairLeaderCheck/import");
        affairLeaderCheckMap.put("template", "领导干部年度考核.xlsx");
        map.put("affair_affairLeaderCheck", affairLeaderCheckMap);

        //事物信息管理-纪检监察-廉政鉴定
        Map<String, String> affairRqlzIntegrityMap = Maps.newHashMap();
        affairRqlzIntegrityMap.put("url", "/affair/affairRqlzIntegrity/import");
        affairRqlzIntegrityMap.put("template", "廉政鉴定.xlsx");
        map.put("affair_affairRqlzIntegrity", affairRqlzIntegrityMap);

        //事物信息管理-纪检监察-廉政监督
        Map<String, String> affairLianzhengSuperviseMap = Maps.newHashMap();
        affairLianzhengSuperviseMap.put("url", "/affair/affairLianzhengSupervise/import");
        affairLianzhengSuperviseMap.put("template", "廉政监督.xlsx");
        map.put("affair_affairLianzhengSupervise", affairLianzhengSuperviseMap);

        //事物信息管理-监督问题库
        Map<String, String> affairDcwtLibraryMap = Maps.newHashMap();
        affairDcwtLibraryMap.put("url", "/affair/affairDcwtLibrary/import");
        affairDcwtLibraryMap.put("template", "督察问题库表.xlsx");
        map.put("affair_affairDcwtLibrary", affairDcwtLibraryMap);

        //事物信息管理-工团工作信息管理-助医管理
        Map<String, String> affairZyInfoMap = Maps.newHashMap();
        affairZyInfoMap.put("url", "/affair/affairZyInfo/import");
        affairZyInfoMap.put("template", "助医管理表.xlsx");
        map.put("affair_affairZyInfo", affairZyInfoMap);

        //事物信息管理-工团工作信息管理-助学管理
        Map<String, String> affairZxInfoMap = Maps.newHashMap();
        affairZxInfoMap.put("url", "/affair/affairZxInfo/import");
        affairZxInfoMap.put("template", "金秋助学.xlsx");
        map.put("affair_affairZxInfo", affairZxInfoMap);

        //事物信息管理-工团工作信息管理-助困管理
        Map<String, String> affairZkInfoMap = Maps.newHashMap();
        affairZkInfoMap.put("url", "/affair/affairZkInfo/import");
        affairZkInfoMap.put("template", "助困管理表.xlsx");
        map.put("affair_affairZkInfo", affairZkInfoMap);

        //事物信息管理-工团工作信息管理-固资管理
        Map<String, String> affairGzMap = Maps.newHashMap();
        affairGzMap.put("url", "/affair/affairGz/import");
        affairGzMap.put("template", "固资管理表.xlsx");
        map.put("affair_affairGz", affairGzMap);

        //事物信息管理-工团工作信息管理-民警休养申报
        Map<String, String> affairMjxyReportMap = Maps.newHashMap();
        affairMjxyReportMap.put("url", "/affair/affairMjxyReport/import");
        affairMjxyReportMap.put("template", "休养信息.xlsx");
        map.put("affair_affairMjxyReport", affairMjxyReportMap);

        //事物信息管理-工团工作信息管理-工会集体表彰
        Map<String, String> affairCollectiveAwardMap = Maps.newHashMap();
        affairCollectiveAwardMap.put("url", "/affair/affairCollectiveAward/import");
        affairCollectiveAwardMap.put("template", "工会集体表彰表.xlsx");
        map.put("affair_affairCollectiveAward", affairCollectiveAwardMap);

        //事物信息管理-工团工作信息管理-健康体检
        Map<String, String> affairHealthCheckupMap = Maps.newHashMap();
        affairHealthCheckupMap.put("url", "/affair/affairHealthCheckup/import");
        affairHealthCheckupMap.put("template", "健康体检表.xlsx");
        map.put("affair_affairHealthCheckup", affairHealthCheckupMap);

        //事物信息管理-工团工作信息管理-工会名册
        Map<String, String> affairGonghuiPersonnelMap = Maps.newHashMap();
        affairGonghuiPersonnelMap .put("url", "/affair/ affairGonghuiPersonnel/import");
        affairGonghuiPersonnelMap .put("template", "工会名册.xlsx");
        map.put("affair_affairGonghuiPersonnel", affairGonghuiPersonnelMap);


        //事物信息管理-工团工作信息管理-困难女警申报
        Map<String, String> affairHardPolicewomanMap = Maps.newHashMap();
        affairHardPolicewomanMap.put("url", "/affair/affairHardPolicewoman/import");
        affairHardPolicewomanMap.put("template", "困难女警申报.xlsx");
        map.put("affair_affairHardPolicewoman", affairHardPolicewomanMap);

        //事物信息管理-工团工作信息管理-幼儿补助
        Map<String, String> affairChildSubsidyMap = Maps.newHashMap();
        affairChildSubsidyMap.put("url", "/affair/affairChildSubsidy/import");
        affairChildSubsidyMap.put("template", "幼儿补助表.xlsx");
        map.put("affair_affairChildSubsidy", affairChildSubsidyMap);

       /* //事物信息管理-工团工作信息管理-各类特长人才
        Map<String, String> affairPolicewomanTalentMap = Maps.newHashMap();
        affairPolicewomanTalentMap .put("url", "/affair/ affairPolicewomanTalent/import");
        affairPolicewomanTalentMap.put("template", "幼儿补助表.xlsx");
        map.put("affair_affairPolicewomanTalent", affairPolicewomanTalentMap);
*/
        //事物信息管理-工团工作信息管理-体协章程制度
        Map<String, String> affairTixieConstitutionalSystemMap = Maps.newHashMap();
        affairTixieConstitutionalSystemMap .put("url", "/affair/ affairTixieConstitutionalSystem/import");
        affairTixieConstitutionalSystemMap .put("template", "体协章程制度.xlsx");
        map.put("affair_affairTixieConstitutionalSystem", affairTixieConstitutionalSystemMap);

        //事物信息管理-工团工作信息管理-体协活动风采
        Map<String, String> affairTixieActivityStyleMap = Maps.newHashMap();
        affairTixieActivityStyleMap .put("url", "/affair/ affairTixieActivityStyle/import");
        affairTixieActivityStyleMap .put("template", "体协活动风采.xlsx");
        map.put("affair_affairTixieActivityStyle", affairTixieActivityStyleMap);

        //事物信息管理-工团工作信息管理-警官艺术团活动风采
        Map<String, String> affairActivityStyleMap = Maps.newHashMap();
        affairActivityStyleMap .put("url", "/affair/ affairActivityStyle/import");
        affairActivityStyleMap .put("template", "警官艺术团活动风采.xlsx");
        map.put("affair_affairActivityStyle", affairActivityStyleMap);

        //事物信息管理-工团工作信息管理-警官艺术团章程制度
        Map<String, String> affairConstitutionalSystemMap = Maps.newHashMap();
        affairConstitutionalSystemMap .put("url", "/affair/ affairConstitutionalSystem/import");
        affairConstitutionalSystemMap .put("template", "警官艺术团章程制度.xlsx");
        map.put("affair_affairConstitutionalSystem", affairConstitutionalSystemMap);
        
        //事物信息管理-工团工作信息管理-工会活动记录
        Map<String, String> affairGhActivityRecordMap = Maps.newHashMap();
        affairGhActivityRecordMap .put("url", "/affair/ affairGhActivityRecord/import");
        affairGhActivityRecordMap .put("template", "工会活动记录表.xlsx");
        map.put("affair_affairGhActivityRecord", affairGhActivityRecordMap);

        //事物信息管理-工团工作信息管理-困难女警申报
        Map<String, String> affairGhActivityEnrollMap = Maps.newHashMap();
        affairGhActivityEnrollMap .put("url", "/affair/ affairGhActivityEnroll/import");
        affairGhActivityEnrollMap .put("template", "工会活动报名表.xlsx");
        map.put("affair_affairGhActivityEnroll", affairGhActivityEnrollMap);

        //事物信息管理-工团工作信息管理-工会个人表彰
        Map<String, String> affairPersonalAwardMap = Maps.newHashMap();
        affairPersonalAwardMap.put("url", "/affair/affairPersonalAward/import");
        affairPersonalAwardMap.put("template", "工会个人表彰表.xlsx");
        map.put("affair_affairPersonalAward", affairPersonalAwardMap);

        //事物信息管理-工团工作信息管理-女警基本情况
        Map<String, String> affairPolicewomanBaseMap = Maps.newHashMap();
        affairPolicewomanBaseMap.put("url", "/affair/affairPolicewomanBase/import");
        affairPolicewomanBaseMap.put("template", "女警基本情况.xlsx");
        map.put("affair_affairPolicewomanBase", affairPolicewomanBaseMap);

        //事物信息管理-工团工作信息管理-团委（支部）基本情况
        Map<String, String> affairTwBaseMap = Maps.newHashMap();
        affairTwBaseMap.put("url", "/affair/affairTwBase/import");
        affairTwBaseMap.put("template", "团委（支部）基本情况表.xlsx");
        map.put("affair_affairTwBase", affairTwBaseMap);

        //事物信息管理-工团工作信息管理-青年人才库
        Map<String, String> affairYouthTalentMap = Maps.newHashMap();
        affairYouthTalentMap.put("url", "/affair/affairYouthTalent/import");
        affairYouthTalentMap.put("template", "青年人才库表.xlsx");
        map.put("affair_affairYouthTalent", affairYouthTalentMap);

        //事物信息管理-工团工作信息管理-团内活动报名
        Map<String, String> affairTnActivityEnrollMap = Maps.newHashMap();
        affairTnActivityEnrollMap .put("url", "/affair/ affairTnActivityEnroll/import");
        affairTnActivityEnrollMap .put("template", "团内活动报名.xlsx");
        map.put("affair_affairTnActivityEnroll", affairTnActivityEnrollMap);

        //事物信息管理-工团工作信息管理-团员花名册
        Map<String, String> affairTjRegisterMap = Maps.newHashMap();
        affairTjRegisterMap.put("url", "/affair/affairTjRegister/import");
        affairTjRegisterMap.put("template", "团员名册表.xlsx");
        map.put("affair_affairTjRegister", affairTjRegisterMap);

        //事物信息管理-工团工作信息管理-团员花名册
        Map<String, String> affairTjRegisterBaseMap = Maps.newHashMap();
        affairTjRegisterBaseMap.put("url", "/affair/affairTjRegisterBase/import");
        affairTjRegisterBaseMap.put("template", "团籍注册表.xlsx");
        map.put("affair_affairTjRegisterBase", affairTjRegisterBaseMap);


        //事物信息管理-工团工作信息管理-团内活动记录表
        Map<String, String> affairTnActivityRecordMap = Maps.newHashMap();
        affairTnActivityRecordMap .put("url", "/affair/ affairTnActivityRecord/import");
        affairTnActivityRecordMap .put("template", "团内活动记录表.xlsx");
        map.put("affair_affairTnActivityRecord", affairTnActivityRecordMap);

        //事物信息管理-工团工作信息管理-刊用情况
        Map<String, String> affairMediaManagementMap = Maps.newHashMap();
        affairMediaManagementMap .put("url", "/affair/ affairMediaManagement/import");
        affairMediaManagementMap .put("template", "刊用情况.xlsx");
        map.put("affair_affairMediaManagement", affairMediaManagementMap);
        
        //事物信息管理-工团工作信息管理-思想状况分析
        Map<String, String> affairThoughtAnalysisMap = Maps.newHashMap();
        affairThoughtAnalysisMap .put("url", "/affair/ affairThoughtAnalysis/import");
        affairThoughtAnalysisMap .put("template", "思想状况分析.xlsx");
        map.put("affair_affairThoughtAnalysis", affairThoughtAnalysisMap);

        //事物信息管理-工团工作信息管理-团员教育评议
        Map<String, String> affairEducationCommentMap = Maps.newHashMap();
        affairEducationCommentMap.put("url", "/affair/affairEducationComment/import");
        affairEducationCommentMap.put("template", "团员教育评议表.xlsx");
        map.put("affair_affairEducationComment", affairEducationCommentMap);

        //事物信息管理-工团工作信息管理-事迹简报
        Map<String, String> affairDeedsBriefingMap = Maps.newHashMap();
        affairDeedsBriefingMap .put("url", "/affair/ affairDeedsBriefing/import");
        affairDeedsBriefingMap .put("template", "事迹简报.xlsx");
        map.put("affair_affairDeedsBriefing", affairDeedsBriefingMap);

        //事物信息管理-工团工作信息管理-团组织调研文章
        Map<String, String> affairTuanzuzhiResearchArticleMap = Maps.newHashMap();
        affairTuanzuzhiResearchArticleMap .put("url", "/affair/ affairTuanzuzhiResearchArticle/import");
        affairTuanzuzhiResearchArticleMap .put("template", "团组织调研文章.xlsx");
        map.put("affair_affairTuanzuzhiResearchArticle", affairTuanzuzhiResearchArticleMap);

        //事物信息管理-工团工作信息管理-工委工作信息
        Map<String, String> affairWorkInfoMap = Maps.newHashMap();
        affairWorkInfoMap .put("url", "/affair/ affairWorkInfo/import");
        affairWorkInfoMap .put("template", "工委工作信息.xlsx");
        map.put("affair_affairWorkInfo", affairWorkInfoMap);

        //事物信息管理-工团工作信息管理-工委工作总结
        Map<String, String> affairWorkSummaryMap = Maps.newHashMap();
        affairWorkSummaryMap .put("url", "/affair/ affairWorkSummary/import");
        affairWorkSummaryMap .put("template", "工委工作总结.xlsx");
        map.put("affair_affairWorkSummary", affairWorkSummaryMap);


        //事物信息管理-工团工作信息管理-三会一课
        Map<String, String> affairThreeOneMap = Maps.newHashMap();
        affairThreeOneMap.put("url", "/affair/affairThreeOne/import");
        affairThreeOneMap.put("template", "团组织三会一课表.xlsx");
        map.put("affair_affairThreeOne", affairThreeOneMap);

        //事物信息管理-工团工作信息管理-学习教育
        Map<String, String> affairStudyEducationMap = Maps.newHashMap();
        affairStudyEducationMap.put("url", "/affair/affairStudyEducation/import");
        affairStudyEducationMap.put("template", "学习教育表.xlsx");
        map.put("affair_affairStudyEducation", affairStudyEducationMap);

        //事物信息管理-工团工作信息管理-团费收缴
        Map<String, String> affairGroupManagementMap = Maps.newHashMap();
        affairGroupManagementMap.put("url", "/affair/affairGroupManagement/import");
        affairGroupManagementMap.put("template", "团费收缴表.xlsx");
        map.put("affair_affairGroupManagement", affairGroupManagementMap);

        //事物信息管理-工团工作信息管理-上级拨款
        Map<String, String> affairSuperiorGrantMap = Maps.newHashMap();
        affairSuperiorGrantMap.put("url", "/affair/affairSuperiorGrant/import");
        affairSuperiorGrantMap.put("template", "上级拨款表.xlsx");
        map.put("affair_affairSuperiorGrant", affairSuperiorGrantMap);

        //事物信息管理-工团工作信息管理-团费收支
        Map<String, String> affairGroupFeeMap = Maps.newHashMap();
        affairGroupFeeMap.put("url", "/affair/affairGroupFee/import");
        affairGroupFeeMap.put("template", "团费收支表.xlsx");
        map.put("affair_affairGroupFee", affairGroupFeeMap);

        //事物信息管理-工团工作信息管理-推优入党
        Map<String, String> affairPushPartyMap = Maps.newHashMap();
        affairPushPartyMap.put("url", "/affair/affairPushParty/import");
        affairPushPartyMap.put("template", "推优入党表.xlsx");
        map.put("affair_affairPushParty", affairPushPartyMap);

        //事物信息管理-工团工作信息管理-一般慰问
        Map<String, String> affairConsolationWorkInfoMap = Maps.newHashMap();
        affairConsolationWorkInfoMap.put("url", "/affair/affairConsolationWorkInfo/import");
        affairConsolationWorkInfoMap.put("template", "一般慰问.xlsx");
        map.put("affair_affairConsolationWorkInfo", affairConsolationWorkInfoMap);

        //事物信息管理-工团工作信息管理-一帮一
        Map<String, String> affairOneHelpOneMap = Maps.newHashMap();
        affairOneHelpOneMap.put("url", "/affair/affairOneHelpOne/import");
        affairOneHelpOneMap.put("template", "全局“一帮一”重困民警慰问情况.xlsx");
        map.put("affair_affairOneHelpOne", affairOneHelpOneMap);

        //事物信息管理-工团工作信息管理-民警小家建设
        Map<String, String> affairPoliceHomeMap = Maps.newHashMap();
        affairPoliceHomeMap.put("url", "/affair/affairPoliceHome/import");
        //affairPoliceHomeMap.put("template", "民警小家建设管理表.xlsx");
        affairPoliceHomeMap.put("template", "民警小家建设表格.xlsx");
        map.put("affair_affairPoliceHome", affairPoliceHomeMap);

        //事物信息管理-工团工作信息管理-小种养
        Map<String, String> affairXzyMap = Maps.newHashMap();
        affairXzyMap.put("url", "/affair/affairXzy/import");
        affairXzyMap.put("template", "小种养统计表.xlsx");
        map.put("affair_affairXzy", affairXzyMap);

        //事物信息管理-工团工作信息管理-文体人才库
        Map<String, String> affairWentiTalentMap = Maps.newHashMap();
        affairWentiTalentMap.put("url", "/affair/affairWentiTalent/import");
        affairWentiTalentMap.put("template", "体育人才库表.xlsx");
        map.put("affair_affairWentiTalent", affairWentiTalentMap);

        //事物信息管理-工团工作信息管理-文艺人才库
        Map<String, String> affairWentiTalentNextMap = Maps.newHashMap();
        affairWentiTalentNextMap.put("url", "/affair/affairWentiTalentNext/import");
        affairWentiTalentNextMap.put("template", "文艺人才库表.xlsx");
        map.put("affair_affairWentiTalentNext", affairWentiTalentNextMap);

        //事物信息管理-工团工作信息管理-团委单位获奖
        Map<String, String> affairTwUnitAwardMap = Maps.newHashMap();
        affairTwUnitAwardMap.put("url", "/affair/affairTwUnitAward/import");
        affairTwUnitAwardMap.put("template", "团委单位表彰奖励表.xlsx");
        map.put("affair_affairTwUnitAward", affairTwUnitAwardMap);

        //事物信息管理-工团工作信息管理-团委个人获奖
        Map<String, String>affairTwPersonalAwardMap = Maps.newHashMap();
       affairTwPersonalAwardMap .put("url", "/affair/affairTwPersonalAward/import");
       affairTwPersonalAwardMap .put("template", "团委个人表彰奖励表.xlsx");
        map.put("affair_affairTwPersonalAward",affairTwPersonalAwardMap);

        //事物信息管理-工团工作信息管理-品牌创建
        Map<String, String>affairBrandManagementMap = Maps.newHashMap();
       affairBrandManagementMap .put("url", "/affair/affairBrandManagement/import");
       affairBrandManagementMap .put("template", "品牌创建.xlsx");
        map.put("affair_affairBrandManagement",affairBrandManagementMap);

        //组织干部信息管理-档案管理统计台账
        Map<String, String> affairCheckCountMap = Maps.newHashMap();
        affairCheckCountMap.put("url","/affair/affairCheckCount/import");
        affairCheckCountMap.put("template", "档案管理统计台帐.xlsx");
        map.put("affair_affairCheckCount", affairCheckCountMap);

        //组织干部信息管理-档案台账管理
        Map<String, String> affairItemReportMap = Maps.newHashMap();
        affairItemReportMap.put("url","/affair/affairItemReport/import");
        affairItemReportMap.put("template", "个人事项报告.xlsx");
        map.put("affair_affairItemReport", affairItemReportMap);

        //组织干部信息管理-考勤管理
        Map<String, String> affairAttendanceMap = Maps.newHashMap();
        affairAttendanceMap.put("url","/affair/affairAttendance/import");
        affairAttendanceMap.put("template", "考勤记录.xlsx");
        map.put("affair_affairAttendance", affairAttendanceMap);

        //组织干部信息管理-干部工作管理-个人事项报告
        Map<String, String> affairLedgerIntoMap = Maps.newHashMap();
        affairLedgerIntoMap.put("url","/affair/affairLedgerInto/import");
        affairLedgerIntoMap.put("template", "干部档案台账.xlsx");
        map.put("affair_affairLedgerInto", affairLedgerIntoMap);

        //组织干部信息管理-干部工作管理-临时抽调民警
        Map<String, String> affairTemporaryPoliceMap = Maps.newHashMap();
        affairTemporaryPoliceMap.put("url","/affair/affairTemporaryPolice/import");
        affairTemporaryPoliceMap.put("template", "个人事项报告.xlsx");
        map.put("affair_affairTemporaryPolice", affairTemporaryPoliceMap);

        //组织干部信息管理-干部工作管理-警务技术任职资格
        Map<String, String> affairQualificationMap = Maps.newHashMap();
        affairQualificationMap.put("url","/affair/affairQualification/import");
        affairQualificationMap.put("template", "警务技术任职资格.xlsx");
        map.put("affair_affairQualification", affairQualificationMap);

        //组织干部信息管理-抚恤管理-抚恤申报
        Map<String, String> affairCasualtyReportMap = Maps.newHashMap();
        affairCasualtyReportMap.put("url","/affair/affairCasualtyReport/import");
        affairCasualtyReportMap.put("template", "抚恤申报.xlsx");
        map.put("affair_affairCasualtyReport", affairCasualtyReportMap);

        //组织干部信息管理-目录管理-档案目录
        Map<String, String> affairArchiveDirectoryMap = Maps.newHashMap();
        affairArchiveDirectoryMap.put("url","/affair/affairArchiveDirectory/import");
        affairArchiveDirectoryMap.put("template", "档案目录.xlsx");
        map.put("affair_affairArchiveDirectory", affairArchiveDirectoryMap);

        //组织干部信息管理-目录管理-工资目录
        Map<String, String> affairSalaryDirectoryMap = Maps.newHashMap();
        affairSalaryDirectoryMap.put("url","/affair/affairSalaryDirectory/import");
        affairSalaryDirectoryMap.put("template", "工资目录.xlsx");
        map.put("affair_affairSalaryDirectory", affairSalaryDirectoryMap);

        //组织干部信息管理-目录管理-职务目录
        Map<String, String> affairJobDirectoryMap = Maps.newHashMap();
        affairJobDirectoryMap.put("url","/affair/affairJobDirectory/import");
        affairJobDirectoryMap.put("template", "职务目录.xlsx");
        map.put("affair_affairJobDirectory", affairJobDirectoryMap);

        //组织干部信息管理-考勤信息管理-职务目录
        Map<String, String> affairWorkAttendanceMap = Maps.newHashMap();
        affairWorkAttendanceMap.put("url","/affair/affairWorkAttendance/import");
        affairWorkAttendanceMap.put("template", "考勤信息.xlsx");
        map.put("affair_affairWorkAttendance", affairWorkAttendanceMap);

        //组织干部信息管理-考勤信息管理-档案报送单
        Map<String, String> affairSubmitFormMap = Maps.newHashMap();
        affairSubmitFormMap.put("url","/affair/affairSubmitForm/import");
        affairSubmitFormMap.put("template", "档案报送单.xlsx");
        map.put("affair_affairSubmitForm", affairSubmitFormMap);

        //绩效考评-奖惩信息库
        Map<String, String> examJcInfoMap = Maps.newHashMap();
        examJcInfoMap.put("url","/affair/examJcInfoMap/import");
        examJcInfoMap.put("template", "奖惩信息库.xlsx");
        map.put("exam_examJcInfo", examJcInfoMap);

        //组织干部信息管理-劳资表
        Map<String, String> affairLaborMap = Maps.newHashMap();
        affairLaborMap.put("url","/affair/affairLabor/import");
        affairLaborMap.put("template", "劳资表.xlsx");
        map.put("affair_affairLabor", affairLaborMap);

        //组织干部信息管理-劳资表
        Map<String, String> affairSalaryGatherMap = Maps.newHashMap();
        affairSalaryGatherMap.put("url","/affair/affairSalaryGather/import");
        affairSalaryGatherMap.put("template", "个人工资汇总表.xlsx");
        map.put("affair_affairSalaryGather", affairLaborMap);

        //宣传教育信息管理-集体奖励表
        Map<String, String> affairXcUnitRewardMap = Maps.newHashMap();
        affairXcUnitRewardMap.put("url","/affair/affairXcUnitReward/import");
        affairXcUnitRewardMap.put("template", "4.1集体奖励.xlsx");
        map.put("affair_affairXcUnitReward", affairXcUnitRewardMap);

        //宣传教育信息管理-个人奖励表
        Map<String, String> affairPersonalRewardMap = Maps.newHashMap();
        affairPersonalRewardMap.put("url","/affair/affairPersonalReward/import");
        affairPersonalRewardMap.put("template", "4.2个人奖励.xlsx");
        map.put("affair_affairPersonalReward", affairPersonalRewardMap);

        //宣传教育信息管理-新闻宣传表
        Map<String, String> affairNewsMap = Maps.newHashMap();
        affairNewsMap.put("url","/affair/affairNews/import");
        affairNewsMap.put("template", "6.1刊稿稿件 .xlsx");
        map.put("affair_affairNews", affairNewsMap);

        //宣传教育信息管理-复查情况表
        Map<String, String> affairSpiritualMap = Maps.newHashMap();
        affairSpiritualMap.put("url","/affair/affairSpiritualReview/import");
        affairSpiritualMap.put("template", "7.1复查情况表.xlsx");
        map.put("affair_affairSpiritualReview", affairSpiritualMap);

        //宣传教育信息管理-复查表
        Map<String, String> affairSpiritualTableMap = Maps.newHashMap();
        affairSpiritualTableMap.put("url","/affair/affairSpiritualTable/import");
        affairSpiritualTableMap.put("template", "复查表.xlsx");
        map.put("affair_affairSpiritualTable", affairSpiritualTableMap);

        //宣传教育信息管理-通讯员管理表
        Map<String, String> affairCorrespondentMap = Maps.newHashMap();
        affairCorrespondentMap.put("url","/affair/affairCorrespondent/import");
        affairCorrespondentMap.put("template", "6.3通讯员.xlsx");
        map.put("affair_affairCorrespondent", affairCorrespondentMap);

        //宣传教育信息管理-学习强国表
        Map<String, String> affairLearnPowerMap = Maps.newHashMap();
        affairLearnPowerMap.put("url","/affair/affairLearnPower/import");
        affairLearnPowerMap.put("template", "2.3月度学习强国.xlsx");
        map.put("affair_affairLearnPower", affairLearnPowerMap);

        //宣传教育信息管理-中国干部网络学院
        Map<String, String> affairNetworkCollegeMap = Maps.newHashMap();
        affairNetworkCollegeMap.put("url","/affair/affairNetworkCollege/import");
        affairNetworkCollegeMap.put("template", "中国干部网络学院表.xlsx");
        map.put("affair_affairNetworkCollege", affairNetworkCollegeMap);

        //宣传教育信息管理-日常学习表
        Map<String, String> affairLearnDailyMap = Maps.newHashMap();
        affairLearnDailyMap.put("url","/affair/affairLearnDaily/import");
        affairLearnDailyMap.put("template", "单位政治理论学习.xlsx");
        map.put("affair_affairLearnDaily", affairLearnDailyMap);

        //纪检监察信息管理-领导廉政干部档案导入表
        Map<String, String> affairLdgblzFileMap = Maps.newHashMap();
        affairLdgblzFileMap.put("url","/affair/affairLdgblzFile/import");
        affairLdgblzFileMap.put("template", "领导廉政干部档案表.xlsx");
        map.put("affair_affairLdgblzFile", affairLdgblzFileMap);


        //纪检监察信息管理-经常性政治工作-民警思想动态分析表
        Map<String, String> thoughtAnalysisJu = Maps.newHashMap();
        thoughtAnalysisJu.put("url","/affair/affairPoliceThoughtAnalysis/importJu");
        thoughtAnalysisJu.put("template", "3.3思想分析.xlsx");
        map.put("affair_affairPoliceThoughtAnalysisJu", thoughtAnalysisJu);

        //纪检监察信息管理-经常性政治工作-民警思想动态分析表
        Map<String, String> thoughtAnalysisJiGuanDangWei = Maps.newHashMap();
        thoughtAnalysisJiGuanDangWei.put("url","/affair/affairPoliceThoughtAnalysis/importJiGuanDangWei");
        thoughtAnalysisJiGuanDangWei.put("template", "3.3思想分析.xlsx");
        map.put("affair_affairPoliceThoughtAnalysisJiGuanDangWei", thoughtAnalysisJiGuanDangWei);

        //纪检监察信息管理-经常性政治工作-民警思想动态分析表
        Map<String, String> thoughtAnalysisChu = Maps.newHashMap();
        thoughtAnalysisChu.put("url","/affair/affairPoliceThoughtAnalysis/importChu");
        thoughtAnalysisChu.put("template", "3.3思想分析.xlsx");
        map.put("affair_affairPoliceThoughtAnalysisChu", thoughtAnalysisChu);

        //纪检监察信息管理-经常性政治工作-民警思想动态分析表
        Map<String, String> thoughtAnalysisJiGuanZhiBu = Maps.newHashMap();
        thoughtAnalysisJiGuanZhiBu.put("url","/affair/affairPoliceThoughtAnalysis/importJiGuanZhiBu");
        thoughtAnalysisJiGuanZhiBu.put("template", "3.3思想分析.xlsx");
        map.put("affair_affairPoliceThoughtAnalysisJiGuanZhiBu", thoughtAnalysisJiGuanZhiBu);

        //纪检监察信息管理-经常性政治工作-民警思想动态分析表
        Map<String, String> thoughtAnalysisJiCeng = Maps.newHashMap();
        thoughtAnalysisJiCeng.put("url","/affair/affairPoliceThoughtAnalysis/importJiCeng");
        thoughtAnalysisJiCeng.put("template", "3.3思想分析.xlsx");
        map.put("affair_affairPoliceThoughtAnalysisJiCeng", thoughtAnalysisJiCeng);

        //福利慰问-会员福利表
        Map<String, String> benefits = Maps.newHashMap();
        benefits.put("url","/affair/affairWelfareCondolences/import");
        benefits.put("template", "会员福利信息.xlsx");
        map.put("affair_affairBenefits", benefits);

        //宣传教育信息管理-经常性政治工作-谈心信息表
        Map<String, String> talkHeart = Maps.newHashMap();
        talkHeart.put("url","/affair/affairTalkHeart/import");
        talkHeart.put("template", "3.1谈心.xlsx");
        map.put("affair_affairTalkHeart", talkHeart);

        //宣传教育信息管理-经常性政治工作-家访信息表
        Map<String, String> homeVisit = Maps.newHashMap();
        homeVisit.put("url","/affair/affairHomeVisit/import");
        homeVisit.put("template", "3.2家访.xlsx");
        map.put("affair_affairHomeVisit", homeVisit);

        //宣传教育信息管理-经常性政治工作-民警帮教
        Map<String, String> helpEducate = Maps.newHashMap();
        helpEducate.put("url","/affair/affairPoliceHelpEducate/import");
        helpEducate.put("template", "3.4帮教互助.xlsx");
        map.put("affair_affairPoliceHelpEducate", helpEducate);


        //青年团建-团内奖励-团内惩罚
        Map<String, String> leaguePunishment = Maps.newHashMap();
        leaguePunishment.put("url","/affair/affairLeaguePunishment/import");
        leaguePunishment.put("template", "团内惩罚信息表.xlsx");
        map.put("affair_affairLeaguePunishment", leaguePunishment);

        //组干-工资标准
        Map<String, String> affairBasicWage = Maps.newHashMap();
        affairBasicWage.put("url","/affair/affairBasicWage/import");
        affairBasicWage.put("template", "工资标准.xlsx");
        map.put("affair_affairBasicWage", affairBasicWage);

        //组干-级别工资标准
        Map<String, String> affairSalaryLevel = Maps.newHashMap();
        affairSalaryLevel.put("url","/affair/affairSalaryLevel/import");
        affairSalaryLevel.put("template", "级别工资标准.xlsx");
        map.put("affair_affairSalaryLevel", affairSalaryLevel);

        //宣教-典型培树-典型人物
        Map<String, String> affairTypicalPerson = Maps.newHashMap();
        affairTypicalPerson.put("url","/affair/affairTypicalPerson/import");
        affairTypicalPerson.put("template", "4.5典型人物.xlsx");
        map.put("affair_affairTypicalPerson", affairTypicalPerson);

        //宣教-典型培树-典型集体
        Map<String, String> affairTypicalTeam = Maps.newHashMap();
        affairTypicalTeam.put("url","/affair/affairTypicalTeam/import");
        affairTypicalTeam.put("template", "4.6典型集体.xlsx");
        map.put("affair_affairTypicalTeam", affairTypicalTeam);

        //宣教-意识形态
        Map<String, String> affairIdeology = Maps.newHashMap();
        affairIdeology.put("url","/affair/affairIdeology/import");
        affairIdeology.put("template", "意识形态.xlsx");
        map.put("affair_affairIdeology", affairIdeology);

        //党代表-上级党代会代表
        Map<String, String> affairSjPartyCongressRepresentative = Maps.newHashMap();
        affairSjPartyCongressRepresentative.put("url","/affair/affairSjPartyCongressRepresentative/import");
        affairSjPartyCongressRepresentative.put("template", "上级党代会代表.xlsx");
        map.put("affair_affairSjPartyCongressRepresentative", affairSjPartyCongressRepresentative);

        //党代表-公安局党代会代表
        Map<String, String> affairJuPartyCongressRepresentative = Maps.newHashMap();
        affairJuPartyCongressRepresentative.put("url","/affair/affairJuPartyCongressRepresentative/import");
        affairJuPartyCongressRepresentative.put("template", "公安局党代会代表.xlsx");
        map.put("affair_affairJuPartyCongressRepresentative", affairJuPartyCongressRepresentative);

        //党代表-公安处党代会代表
        Map<String, String> affairChuPartyCongressRepresentative = Maps.newHashMap();
        affairChuPartyCongressRepresentative.put("url","/affair/affairChuPartyCongressRepresentative/import");
        affairChuPartyCongressRepresentative.put("template", "公安处党代会代表.xlsx");
        map.put("affair_affairChuPartyCongressRepresentative", affairChuPartyCongressRepresentative);

        //党代表-其他党代会代表
        Map<String, String> affairOtherPartyRepresentative = Maps.newHashMap();
        affairOtherPartyRepresentative.put("url","/affair/affairOtherPartyRepresentative/import");
        affairOtherPartyRepresentative.put("template", "其他党代会代表.xlsx");
        map.put("affair_affairOtherPartyRepresentative", affairOtherPartyRepresentative);

        //社保-退休
        Map<String, String> affairTuixiu = Maps.newHashMap();
        affairTuixiu.put("url","/affair/affairTuixiu/import");
        affairTuixiu.put("template", "退休表.xlsx");
        map.put("affair_affairTuixiu", affairTuixiu);

        //抚恤待遇-参公人员抚恤
        Map<String, String> affairCjCompassionate = Maps.newHashMap();
        affairCjCompassionate.put("url","/affair/affairCjCompassionate/import");
        affairCjCompassionate.put("template", "参公人员抚恤表.xlsx");
        map.put("affair_affairCjCompassionate", affairCjCompassionate);

        //精神文明建设-测评标准
        Map<String, String> affairEvaluationCriteria = Maps.newHashMap();
        affairEvaluationCriteria.put("url","/affair/affairEvaluationCriteria/import");
        affairEvaluationCriteria.put("template", "7.3测评标准.xlsx");
        map.put("affair_affairEvaluationCriteria", affairEvaluationCriteria);


        //抚恤待遇-公务员一次性抚恤
        Map<String, String> affairGwy = Maps.newHashMap();
        affairGwy.put("url","/affair/affairGwy/import");
        affairGwy.put("template", "公务员一次性抚恤表.xlsx");
        map.put("affair_affairGwy", affairGwy);

        //教育训练-培训计划
        Map<String, String> trainPlan = Maps.newHashMap();
        trainPlan.put("url","/affair/affairEducationTrain/import");
        trainPlan.put("template", "教育训练培训计划表.xlsx");
        map.put("affair_affairEducationTrain", trainPlan);

        //教育训练-班主任培训班
        Map<String, String> affairTeacherClassMap = Maps.newHashMap();
        affairTeacherClassMap.put("url","/affair/affairTeacherClass/import");
        affairTeacherClassMap.put("template", "班主任培训班表.xlsx");
        map.put("affair_affairTeacherClass", affairTeacherClassMap);

        //教育训练-练兵比武
        Map<String, String> affairTrainingMap = Maps.newHashMap();
        affairTrainingMap.put("url","/affair/affairTraining/import");
        affairTrainingMap.put("template", "练兵比武表.xlsx");
        map.put("affair_affairTraining", affairTrainingMap);

        //教育训练-外部教官
        Map<String, String> affairInstructorLibraryMap = Maps.newHashMap();
        affairInstructorLibraryMap.put("url","/affair/affairInstructorLibrary/import");
        affairInstructorLibraryMap.put("template", "外部教官表.xlsx");
        map.put("affair_affairInstructorLibrary", affairInstructorLibraryMap);

        //教育训练-内部教官
        Map<String, String> affairInteriorInstructorLibraryMap = Maps.newHashMap();
        affairInteriorInstructorLibraryMap.put("url","/affair/affairInteriorInstructorLibrary/import");
        affairInteriorInstructorLibraryMap.put("template", "内部教官表.xlsx");
        map.put("affair_affairInteriorInstructorLibrary", affairInteriorInstructorLibraryMap);

        //教育训练-教官晋级审批表
        Map<String, String> affairTeacherApprovalMap = Maps.newHashMap();
        affairTeacherApprovalMap.put("url","/affair/affairTeacherApproval/import");
        affairTeacherApprovalMap.put("template", "教官晋级审批表.xlsx");
        map.put("affair_affairTeacherApproval", affairTeacherApprovalMap);

        //教育训练-交流锻炼统计表
        Map<String, String> affairExchangeStatisticsMap = Maps.newHashMap();
        affairExchangeStatisticsMap.put("url","/affair/affairExchangeStatistics/import");
        affairExchangeStatisticsMap.put("template", "交流锻炼统计表.xlsx");
        map.put("affair_affairExchangeStatistics", affairExchangeStatisticsMap);

        //教育训练-成绩管理员练兵比武
        Map<String, String> affairTrainingManageMap = Maps.newHashMap();
        affairTrainingManageMap.put("url","/affair/affairTrainingManage/import");
        affairTrainingManageMap.put("template", "成绩管理员练兵比武表.xlsx");
        map.put("affair_affairTrainingManage", affairTrainingManageMap);

        //教育训练-管理员信息统计
        Map<String, String> affairAdminStatisticsMap = Maps.newHashMap();
        affairAdminStatisticsMap.put("url","/affair/affairAdminStatistics/import");
        affairAdminStatisticsMap.put("template", "管理员信息统计表.xlsx");
        map.put("affair_affairAdminStatistics", affairAdminStatisticsMap);

        //教育训练-民警课程学习统计
        Map<String, String> affairPoliceStatisticsMap = Maps.newHashMap();
        affairPoliceStatisticsMap.put("url","/affair/affairPoliceStatistics/import");
        affairPoliceStatisticsMap.put("template", "民警课程学习统计表.xlsx");
        map.put("affair_affairPoliceStatistics", affairPoliceStatisticsMap);

        //教育训练-民警课程学习统计
        Map<String, String> affairOrganzationStatisticsMap = Maps.newHashMap();
        affairOrganzationStatisticsMap.put("url","/affair/affairOrganzationStatistics/import");
        affairOrganzationStatisticsMap.put("template", "机构部门学习统计表.xlsx");
        map.put("affair_affairOrganzationStatistics", affairOrganzationStatisticsMap);

        //教育训练-民警课程学习统计
        Map<String, String> affairTrainPlanMap = Maps.newHashMap();
        affairTrainPlanMap.put("url","/affair/affairTrainPlan/import");
        affairTrainPlanMap.put("template", "培训计划报表.xlsx");
        map.put("affair_affairTrainPlan", affairTrainPlanMap);

        //教育训练-考试成绩录入   affair_affairExamEnteringTwo
        Map<String, String> affairExamEnteringMap = Maps.newHashMap();
        affairExamEnteringMap.put("url","/affair/affairExamEntering/import");
        affairExamEnteringMap.put("template", "考试成绩录入管理表.xlsx");
        map.put("affair_affairExamEntering", affairExamEnteringMap);

        //教育训练-报名管理
        Map<String, String> affairApplyManageMap = Maps.newHashMap();
        affairApplyManageMap.put("url","/affair/affairApplyManage/import");
        affairApplyManageMap.put("template", "报名管理表.xlsx");
        map.put("affair_affairApplyManage", affairApplyManageMap);

        //教育训练-报名人员管理
        Map<String, String> affairApplyPersonnelMap = Maps.newHashMap();
        affairApplyPersonnelMap.put("url","/affair/affairApplyPersonnel/import");
        affairApplyPersonnelMap.put("template", "报名人员管理表.xlsx");
        map.put("affair_affairApplyPersonnel", affairApplyPersonnelMap);


        //抚恤待遇-特别抚恤金
        Map<String, String> affairSpecial = Maps.newHashMap();
        affairSpecial.put("url","/affair/affairSpecial/import");
        affairSpecial.put("template", "特别抚恤金表.xlsx");
        map.put("affair_affairSpecial", affairSpecial);

        //抚恤待遇-伤亡特殊补助金
        Map<String, String> affairDeathMoney = Maps.newHashMap();
        affairDeathMoney.put("url","/affair/affairDeathMoney/import");
        affairDeathMoney.put("template", "伤亡特殊补助金表.xlsx");
        map.put("affair_affairDeathMoney", affairDeathMoney);

        //社保-因公负伤
        Map<String, String> affairInjuries = Maps.newHashMap();
        affairInjuries.put("url","/affair/affairInjuries/import");
        affairInjuries.put("template", "因公负伤表.xlsx");
        map.put("affair_affairInjuries", affairInjuries);

        //社保-医疗保险
        Map<String, String> affairMedicalInsurance = Maps.newHashMap();
        affairMedicalInsurance.put("url","/affair/affairMedicalInsurance/import");
        affairMedicalInsurance.put("template", "医疗保险表.xlsx");
        map.put("affair_affairMedicalInsurance", affairMedicalInsurance);

        //精神文明-创建工作
        Map<String, String> affairCreateWork = Maps.newHashMap();
        affairCreateWork.put("url","/affair/affairCreateWork/import");
        affairCreateWork.put("template", "7.2创建工作 .xlsx");
        map.put("affair_affairCreateWork", affairCreateWork);

        //社保-医保关系变动
        Map<String, String> affairMedicareChange = Maps.newHashMap();
        affairMedicareChange.put("url","/affair/affairMedicareChange/import");
        affairMedicareChange.put("template", "医保关系变动表.xlsx");
        map.put("affair_affairMedicareChange", affairMedicareChange);

        //人员-民警家庭
        Map<String, String> personnelPoliceFamily = Maps.newHashMap();
        personnelPoliceFamily.put("url","/personnel/personnelPoliceFamily/import");
        personnelPoliceFamily.put("template", "民警家庭.xlsx");
        map.put("personnel_personnelPoliceFamily", personnelPoliceFamily);

        //教育训练-培训班管理1
       /* Map<String, String> classManage = Maps.newHashMap();
        classManage.put("url","/affair/affairClassManage/import");
        classManage.put("template", "培训班详情表.xlsx");
        map.put("affair_affairClassManage", classManage);*/

        //教育训练-培训班管理
        Map<String, String> classManage = Maps.newHashMap();
        classManage.put("url","/affair/affairClassManage/import");
        classManage.put("template", "培训班详情表.xlsx");
        map.put("affair_affairClass", classManage);

        //教育训练-培训班学习统计
        Map<String, String> affairTrainingStatisticsMap = Maps.newHashMap();
        affairTrainingStatisticsMap.put("url","/affair/affairTrainingStatistics/import");
        affairTrainingStatisticsMap.put("template", "培训班学习统计表.xlsx");
        map.put("affair_affairTrainingStatistics", affairTrainingStatisticsMap);

        //教育训练-实弹训练
        Map<String, String> trainCombat = Maps.newHashMap();
        trainCombat.put("url","/affair/affairTrainCombat/import");
        trainCombat.put("template", "实弹训练表.xlsx");
        map.put("affair_affairTrainCombat", trainCombat);

        //教育训练-交流锻炼
        Map<String, String> swapExercise = Maps.newHashMap();
        swapExercise.put("url","/affair/affairSwapExercise/import");
        swapExercise.put("template", "交流锻炼表.xlsx");
        map.put("affair_affairSwapExercise", swapExercise);

        //教育训练-委外培训
        Map<String, String> trainOutsource = Maps.newHashMap();
        trainOutsource.put("url","/affair/affairTrainOutsource/import");
        trainOutsource.put("template", "委外培训表.xlsx");
        map.put("affair_affairTrainOutsource", trainOutsource);

        //教育训练-委外培训统计
        Map<String, String> affairTrainOutsourceStatisticsMap = Maps.newHashMap();
        affairTrainOutsourceStatisticsMap.put("url","/affair/affairTrainOutsourceStatistics/import");
        affairTrainOutsourceStatisticsMap.put("template", "委外培训统计表.xlsx");
        map.put("affair_affairTrainOutsourceStatistics", affairTrainOutsourceStatisticsMap);

        //教育训练-练习报表
        Map<String, String> affairExerciseChart = Maps.newHashMap();
        affairExerciseChart.put("url","/affair/affairExerciseChart/import");
        affairExerciseChart.put("template", "练习报表.xlsx");
        map.put("affair_affairExerciseChart", affairExerciseChart);

        //教育训练-课程资源
        Map<String, String> affairCourseResource = Maps.newHashMap();
        affairCourseResource.put("url","/affair/affairCourseResource/import");
        affairCourseResource.put("template", "课程资源表.xlsx");
        map.put("affair_affairCourseResource", affairCourseResource);

        //档案台账-在职
        Map<String, String> affairArchiveRegister = Maps.newHashMap();
        affairArchiveRegister.put("url","/affair/affairArchiveRegister/import");
        affairArchiveRegister.put("template", "在职.xlsx");
        map.put("affair_affairArchiveRegister", affairArchiveRegister);

        //档案台账-离退
        Map<String, String> affairRetire = Maps.newHashMap();
        affairRetire.put("url","/affair/affairRetire/import");
        affairRetire.put("template", "离退.xlsx");
        map.put("affair_affairRetire", affairRetire);

        //档案台账-死亡
        Map<String, String> affairDeath = Maps.newHashMap();
        affairDeath.put("url","/affair/affairDeath/import");
        affairDeath.put("template", "死亡.xlsx");
        map.put("affair_affairDeath", affairDeath);

        //档案台账-转入
        Map<String, String> affairLedgerInto = Maps.newHashMap();
        affairLedgerInto.put("url","/affair/affairLedgerInto/import");
        affairLedgerInto.put("template", "转入.xlsx");
        map.put("affair_affairLedgerInto", affairLedgerInto);

        //档案台账-转出
        Map<String, String> affairLedgerOut = Maps.newHashMap();
        affairLedgerOut.put("url","/affair/affairLedgerOut/import");
        affairLedgerOut.put("template", "转出.xlsx");
        map.put("affair_affairLedgerOut", affairLedgerOut);

        //教育训练-送教上门
        Map<String, String> sendTeacher = Maps.newHashMap();
        sendTeacher.put("url","/affair/affairSendTeacher/import");
        sendTeacher.put("template", "送教上门表.xlsx");
        map.put("affair_affairSendTeacher", sendTeacher);

        //警察证-制作申请
        Map<String, String> policeCertificate = Maps.newHashMap();
        policeCertificate.put("url","/personnel/personnelPoliceCertificate/makeCertificate");
        policeCertificate.put("template", "申请办理警察证人员信息.xlsx");
        map.put("personnel_makePoliceCertificate", policeCertificate);

        //教育训练-证书详情
        Map<String, String> certificate = Maps.newHashMap();
        certificate.put("url","/affair/affairCertificate/import");
        certificate.put("template", "证书详情表.xlsx");
        map.put("affair_affairCertificate", certificate);

        //教育训练-证书管理
        Map<String, String> certificateManagement = Maps.newHashMap();
        certificateManagement.put("url","/affair/affairCertificateManagement/import");
        certificateManagement.put("template", "证书模板表.xlsx");
        map.put("affair_affairCertificateManagement", certificateManagement);

        //教育训练-课程信息
        Map<String, String> classInformation = Maps.newHashMap();
        classInformation.put("url","/affair/affairClassInformation/import");
        classInformation.put("template", "课程信息表.xlsx");
        map.put("affair_affairClassInformation", classInformation);

        //教育训练-证书管理员
        Map<String, String> certificateAdministrator = Maps.newHashMap();
        certificateAdministrator.put("url","/affair/affairCertificateAdministrator/import");
        certificateAdministrator.put("template", "证书管理员表.xlsx");
        map.put("affair_affairCertificateAdministrator", certificateAdministrator);

        //教育训练-培训计划审批
        Map<String, String> trainApprove = Maps.newHashMap();
        trainApprove.put("url","/affair/affairTrainApprove/import");
        trainApprove.put("template", "培训计划审批表.xlsx");
        map.put("affair_affairTrainApprove", trainApprove);

        //宣传信息-警营文化--文联艺术团-活动风采
        Map<String,String> affairActivityMien = Maps.newHashMap();
        affairActivityMien.put("url","/affair/affairActivityMien/import");
        affairActivityMien.put("template","5.5活动风采.xlsx");
        map.put("affair_activityMien",affairActivityMien);

        //宣传信息-警营文化--文联艺术团-文艺作品
        Map<String,String> affairWenyi = Maps.newHashMap();
        affairWenyi.put("url","/affair/affairWenyi/import");
        affairWenyi.put("template","5.3文艺作品.xlsx");
        map.put("affair_wenyi",affairWenyi);

        //教育训练-培训实施-实战训练-岗位练兵
        Map<String,String> affairJobTraining = Maps.newHashMap();
        affairJobTraining.put("url","/affair/affairJobTraining/import");
        affairJobTraining.put("template","岗位练兵.xlsx");
        map.put("affair_affairJobTraining",affairJobTraining);
        //宣传信息-警营文化-硬件建设
        Map<String,String> affairYjBuild = Maps.newHashMap();
        affairYjBuild.put("url","/affair/affairYjBuild/import");
        affairYjBuild.put("template","5.1硬件建设 .xlsx");
        map.put("affair_yjBuild",affairYjBuild);

        //教育训练-教官管理-教官特长统计报表
        Map<String,String> instructorSpecStatistics = Maps.newHashMap();
        instructorSpecStatistics.put("url","/affair/affairInstructorSpecStatistics/import");
        instructorSpecStatistics.put("template","教官特长统计报表.xlsx");
        map.put("affair_instructorSpecStatistics",instructorSpecStatistics);

        //教育训练-培训统计-民警学习统计报表
        Map<String,String> policeStudyStatistics = Maps.newHashMap();
        policeStudyStatistics.put("url","/affair/affairPoliceStudyStatistics/import");
        policeStudyStatistics.put("template","民警学习统计报表.xlsx");
        map.put("affair_policeStudyStatistics",policeStudyStatistics);


        //宣传信息-警营文化-文化人才
        Map<String,String> affairWenhua = Maps.newHashMap();
        affairWenhua.put("url","/affair/affairWenhua/import");
        affairWenhua.put("template","5.4文化人才.xlsx");
        map.put("affair_Wenhua",affairWenhua);
        //教育训练-教官管理-教官授课统计报表
        Map<String,String> instructorLessonsStatistics = Maps.newHashMap();
        instructorLessonsStatistics.put("url","/affair/affairInstructorLessonsStatistics/import");
        instructorLessonsStatistics.put("template","教官授课统计报表.xlsx");
        map.put("affair_instructorLessonsStatistics",instructorLessonsStatistics);

        //教育训练-培训实施-人员信息
        Map<String,String> affairPersonnelMessage = Maps.newHashMap();
        affairPersonnelMessage.put("url","/affair/affairPersonnelMessage/import");
        affairPersonnelMessage.put("template","人员信息.xlsx");
        map.put("affair_affairPersonnelMessage",affairPersonnelMessage);

        //教育训练-训厉管理-考试成绩   affair_affairExamEnteringTwo    affairExamEntering  affairXunLi  affair_affairWorkRecord
        Map<String, String> affairExamEnteringTwoMap = Maps.newHashMap();
        affairExamEnteringTwoMap.put("url","/affair/affairXunLi/import");
        affairExamEnteringTwoMap.put("template", "考试成绩管理表.xlsx");
        map.put("affair_affairExamEnteringTwo", affairExamEnteringTwoMap);

        //教育训练-训厉管理-考试成绩   affair_affairExamEnteringTwo    affairExamEntering  affairXunLi  affair_affairWorkRecord
        Map<String, String> affairWorkRecord = Maps.newHashMap();
        affairWorkRecord.put("url","/affair/affairWorkRecord/import");
        affairWorkRecord.put("template", "民警纪实报表.xlsx");
        map.put("affair_affairWorkRecord", affairWorkRecord);

        //教育训练-培训统计-铁路公安机关课程建设情况统计
        Map<String, String> courseBuildStatistical = Maps.newHashMap();
        courseBuildStatistical.put("url","/affair/affairCourseBuildStatistical/import");
        courseBuildStatistical.put("template", "铁路公安机关课程建设情况统计表.xlsx");
        map.put("affair_courseBuildStatistical", courseBuildStatistical);

        //干部人事-考勤数据统计
        Map<String,String> affairAttendanceCount = Maps.newHashMap();
        affairAttendanceCount.put("url","/affair/affairAttendanceCount/import");
        affairAttendanceCount.put("template","考勤数据统计表.xlsx");
        map.put("affair_affairAttendanceCount",affairAttendanceCount);

        //干部人事-考勤数据统计
        Map<String,String> affairAttendanceSummary = Maps.newHashMap();
        affairAttendanceSummary.put("url","/affair/affairAttendanceSummary/import");
        affairAttendanceSummary.put("template","考勤关联计算表.xlsx");
        map.put("affair_affairAttendanceSummary",affairAttendanceSummary);

        //干部人事-节假日
        Map<String,String> affairAttendanceFlag = Maps.newHashMap();
        affairAttendanceFlag.put("url","/affair/affairAttendanceFlag/import");
        affairAttendanceFlag.put("template","节假日表.xlsx");
        map.put("affair_affairAttendanceFlag",affairAttendanceFlag);

        //系统管理-索引管理
        Map<String, String> sys_sysIndex = Maps.newHashMap();
        sys_sysIndex.put("url","/sys/sysIndex/import");
        sys_sysIndex.put("template", "索引管理表.xlsx");
        map.put("sys_sysIndex", sys_sysIndex);

        //宣传教育-奖励查询
        Map<String,String> affairRewardInquire = Maps.newHashMap();
        affairRewardInquire.put("url","/affair/affairRewardInquire/import");
        affairRewardInquire.put("template","奖励查询表.xlsx");
        map.put("affair_affairRewardInquire",affairRewardInquire);

        //教育训练-培训统计-民警个人训历档案
        Map<String,String> policePersonalTrainingFile = Maps.newHashMap();
        policePersonalTrainingFile.put("url","/affair/affairPolicePersonalTrainingFile/import");
        policePersonalTrainingFile.put("template","民警个人训历档案.xlsx");
        map.put("affair_policePersonalTrainingFile",policePersonalTrainingFile);

        //宣传思想-经常性思想工作
        Map<String,String> affairSevenKnowledge = Maps.newHashMap();
        affairSevenKnowledge.put("url","/affair/affairSevenKnowledge/import");
        affairSevenKnowledge.put("template","3.5七知档案-导入.xlsx");
        map.put("affair_affairSevenKnowledge",affairSevenKnowledge);

        //宣传思想-立功创模-奖励申报-集体
        Map<String,String> xcRewardDeclaration = Maps.newHashMap();
        xcRewardDeclaration.put("url","/affair/affairXcRewardDeclaration/import");
        xcRewardDeclaration.put("template","4.3单位集体奖励申报表 .xlsx");
        map.put("affair_XcRewardDeclaration",xcRewardDeclaration);

        //宣传思想-立功创模-奖励申报-个人
        Map<String,String> rewardDeclaration = Maps.newHashMap();
        rewardDeclaration.put("url","/affair/affairRewardDeclaration/import");
        rewardDeclaration.put("template","4.4个人奖励申报表.xlsx");
        map.put("affair_rewardDeclaration",rewardDeclaration);

        //宣传思想-新闻宣传-舆情处置
      Map<String,String> yqContol = Maps.newHashMap();
        yqContol.put("url","/affair/affairYqContol/import");
        yqContol.put("template","6.2舆情处置.xlsx");
        map.put("affair_yqContol",yqContol);

        //组干-职务工资标准
        Map<String,String> affairJobSalaryStandard = Maps.newHashMap();
        affairJobSalaryStandard.put("url","/affair/affairJobSalaryStandard/import");
        affairJobSalaryStandard.put("template","职务工资标准.xlsx");
        map.put("affair_affairJobSalaryStandard",affairJobSalaryStandard);

        //组干-考勤录入人员排序
        Map<String,String> affairLaborSort = Maps.newHashMap();
        affairLaborSort.put("url","/affair/affairLaborSort/import");
        affairLaborSort.put("template","人员排序.xlsx");
        map.put("affair_affairLaborSort",affairLaborSort);

        //教育训练-基本知识成绩
        Map<String,String> affairBasicKnowledge = Maps.newHashMap();
        affairBasicKnowledge.put("url","/affair/affairBasicKnowledge/import");
        affairBasicKnowledge.put("template","基本知识成绩.xlsx");
        map.put("affair_affairBasicKnowledge",affairBasicKnowledge);

        //教育训练-基本体能成绩
        Map<String,String> affairBasicFitness = Maps.newHashMap();
        affairBasicFitness.put("url","/affair/affairBasicFitness/import");
        affairBasicFitness.put("template","基本体能成绩.xlsx");
        map.put("affair_affairBasicFitness",affairBasicFitness);

        //教育训练-基本技能成绩
        Map<String,String> affairBaseSkill = Maps.newHashMap();
        affairBaseSkill.put("url","/affair/affairBaseSkill/import");
        affairBaseSkill.put("template","基本技能成绩.xlsx");
        map.put("affair_affairBaseSkill",affairBaseSkill);

        //教育训练-基本枪支安全操作
        Map<String,String> affairBaseGunSafety = Maps.newHashMap();
        affairBaseGunSafety.put("url","/affair/affairBaseGunSafety/import");
        affairBaseGunSafety.put("template","基本枪支安全操作.xlsx");
        map.put("affair_affairBaseGunSafety",affairBaseGunSafety);

        //教育训练-实弹射击
        Map<String,String> affairLiveFire = Maps.newHashMap();
        affairLiveFire.put("url","/affair/affairLiveFire/import");
        affairLiveFire.put("template","实弹射击.xlsx");
        map.put("affair_affairLiveFire",affairLiveFire);

        //教育训练-台账录入次数
        Map<String,String> affairLedgerEntryTimes = Maps.newHashMap();
        affairLedgerEntryTimes.put("url","/affair/affairLedgerEntryTimes/import");
        affairLedgerEntryTimes.put("template","台账录入次数.xlsx");
        map.put("affair_affairLedgerEntryTimes",affairLedgerEntryTimes);

        //教育训练-线上学习
        Map<String,String> affairOnlineLearning = Maps.newHashMap();
        affairOnlineLearning.put("url","/affair/affairOnlineLearning/import");
        affairOnlineLearning.put("template","线上学习.xlsx");
        map.put("affair_affairOnlineLearning",affairOnlineLearning);

        //教育训练-线上考试
        Map<String,String> affairOnlineExam = Maps.newHashMap();
        affairOnlineExam.put("url","/affair/affairOnlineExam/import");
        affairOnlineExam.put("template","线上考试.xlsx");
        map.put("affair_affairOnlineExam",affairOnlineExam);

        //教育训练-信息上报
        Map<String,String> affairInformationReport = Maps.newHashMap();
        affairInformationReport.put("url","/affair/affairInformationReport/import");
        affairInformationReport.put("template","信息上报.xlsx");
        map.put("affair_affairInformationReport",affairInformationReport);

        //教育训练-信息采用
        Map<String,String> affairInformationAdoption = Maps.newHashMap();
        affairInformationAdoption.put("url","/affair/affairInformationAdoption/import");
        affairInformationAdoption.put("template","信息采用.xlsx");
        map.put("affair_affairInformationAdoption",affairInformationAdoption);

        //教育训练-比武团体成绩
        Map<String,String> affairTeamPerformance = Maps.newHashMap();
        affairTeamPerformance.put("url","/affair/affairTeamPerformance/import");
        affairTeamPerformance.put("template","比武团体成绩.xlsx");
        map.put("affair_affairTeamPerformance",affairTeamPerformance);

        //教育训练-个人比武成绩
        Map<String,String> affairIndividualResults = Maps.newHashMap();
        affairIndividualResults.put("url","/affair/affairIndividualResults/import");
        affairIndividualResults.put("template","个人比武成绩.xlsx");
        map.put("affair_affairIndividualResults",affairIndividualResults);

        //教育训练-三基综合
        Map<String,String> affairThreeBase = Maps.newHashMap();
        affairThreeBase.put("url","/affair/affairThreeBase/import");
        affairThreeBase.put("template","三基综合.xlsx");
        map.put("affair_affairThreeBase",affairThreeBase);

        //教育训练-枪支理论
        Map<String,String> affairGunTheory = Maps.newHashMap();
        affairGunTheory.put("url","/affair/affairGunTheory/import");
        affairGunTheory.put("template","枪支理论.xlsx");
        map.put("affair_affairGunTheory",affairGunTheory);
    }
}
