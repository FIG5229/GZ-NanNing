package com.thinkgem.jeesite.modules.warn.service;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceChildDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairElectionDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairGeneralSituationDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairActivistService;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyMemberService;
import com.thinkgem.jeesite.modules.affair.service.AffairYearThreeOneService;
import com.thinkgem.jeesite.modules.affair.service.PersonnelGoAbroadService;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflow;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowDatasService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowSegmentsService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceRankDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelJob;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceCertificate;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceRank;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelJobService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceCertificateService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceRankService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.dao.WarnReceiveDao;
import com.thinkgem.jeesite.modules.warn.entity.WarnReceive;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
@Lazy(false)
public class WarningTask {

    private static final Logger log = LoggerFactory.getLogger(WarningTask.class);

    @Autowired
    private WarnReceiveDao warnReceiveDao; //预警接收部门DAO接口

    @Autowired
    private UserDao userDao;        //用户DAO接口

    @Autowired
    private WarnReceiveService warnReceiveService;  //预警接收部门Service

    @Autowired
    private AffairAttendanceChildDao affairAttendanceChildDao; //考勤记录子表DAO接口

    @Autowired
    private WarningService warningService;         //预警service

    @Autowired
    private OfficeService officeService;          //机构service

    @Autowired
    private PersonnelBaseService personnelBaseService;// 人员基本信息Service

    @Autowired
    private AffairElectionDao affairElectionDao;   //党组织换届选举 DAO接口

    @Autowired
    private AffairGeneralSituationDao affairGeneralSituationDao;   //党组织概况DAO接口


    @Autowired
    private PersonnelPoliceRankService personnelPoliceRankService; //警衔信息管理Service

    @Autowired
    private PersonnelPoliceRankDao personnelPoliceRankDao;//警衔信息管理Dao

    @Autowired
    private PersonnelJobService personnelJobService;   //职务层次信息集Service

    @Autowired
    private PersonnelPoliceCertificateService personnelPoliceCertificateService;//警察证Service

    @Autowired
    private PersonnelGoAbroadService personnelGoAbroadService;//出国境service

    @Autowired
    private AffairPartyMemberService affairPartyMemberService;//党员花名册service

    @Autowired
    private ExamWorkflowService examWorkflowService;//考评管理Service

    @Autowired
    private ExamWorkflowSegmentsService examWorkflowSegmentsService;//考评环节Service

    @Autowired
    private ExamWorkflowDatasService examWorkflowDatasService;//考评数据表Service

    @Autowired
    private AffairActivistService affairActivistService;//入党积极分子Service

    @Autowired
    private DictDao dictDao;

    @Autowired
    private AffairGeneralSituationService affairGeneralSituationService;

    @Autowired
    private AffairYearThreeOneService affairYearThreeOneService;

    //@Scheduled(fixedRate = 1000 * 60 * 5) // 5分钟执行一次
    public void doTasks() {
        log.info("WarningTask定时任务开始执行");

//        nextRepeatTimeTask();
    }

    @Scheduled(cron = "0 57 9 1 11 ?")//每年11月1号上午9点55分开始执行
    public void doNJTask() {
        log.info("WarningTask年假通知抓取数据定时任务开始执行");
        nJTask();
        log.info("WarningTask年假通知抓取数据定时任务执行结束");
    }

    //查询考勤情况，将某个人全年缺勤累计即将达到6个月保存至预警数据表中，累积达到五个月时
    //每周一 00:00:00  暂定
    @Scheduled(cron = "0 0 0 ? * MON")
    public void selKQSaveWarn(){
        try {
            log.info("预警：缺勤累计即将达到6个月数据抓取定时任务开始");
            Integer year = Integer.valueOf(DateUtils.getYear());
            List<Map<String, String>> list = affairAttendanceChildDao.findSumLackDay(year);//获得到所有的缺勤天数并判断是否超过150天
            Warning warning = new Warning();
            for (int i = 0; i < list.size(); i++) {
                if (Integer.valueOf(String.valueOf(list.get(i).get("sumday"))) >= 150){
                    Warning oldWarning = warningService.findKQOldWarnByAlertContent(list.get(i).get("unit")+"单位下"+ list.get(i).get("name")+ year+"年累计病事假已达5个月");
                    if(oldWarning != null){
                        warningService.save(oldWarning);
                    }else{
                        //warning.setName(list.get(i).get("unit")+"单位下"+ list.get(i).get("name")+"累计病事假达5个月");//设置预警名称
                        warning.setName("缺勤累计即将达到6个月预警");//设置预警名称
                        //warning.setReceivePerName("劳资信息管理,"+list.get(i).get("unit")+","+list.get(i).get("name"));//设置提醒人员/部门名称   劳资信息管理  人员所属部门  该人员
                        warning.setReceivePerName(list.get(i).get("name"));//设置提醒人员
                        //String unitUserId = officeService.findUserByName(list.get(i).get("unit"));
                        //warning.setReceivePerId("a58a91c7d4db4cd4b639c863c0e48832,"+unitUserId+","+list.get(i).get("id_number"));//设置提醒人员/部门id
                        warning.setReceivePerId(list.get(i).get("id_number"));//设置提醒人员id
                        warning.setRepeatCycle("4");//设置重复周期  4 : 永不
                        Calendar c =Calendar.getInstance();//设置提醒时间 八点开始提醒
                        c.set(Calendar.HOUR_OF_DAY,8);
                        c.set(Calendar.MINUTE,0);
                        c.set(Calendar.SECOND,0);
                        c.set(Calendar.MILLISECOND,0);
                        warning.setDate(c.getTime());
                        warning.setRemind("10");//设置当天重复提醒间隔
                        warning.setIsAlert("1");//设置选择弹窗展示
                        warning.setAlertContent(list.get(i).get("unit")+"单位下"+ list.get(i).get("name")+ year+"年累计病事假已达5个月");//设置弹窗内容
                        warning.setAlertDegree("1");//设置弹窗紧急程度 1 : 红  2 : 黄  3 : 蓝
                        warning.setContinueDay("7");//设置持续时间 7天

                        //设置创建人及更新人信息
                        User user = new User();
                        user.setId("1");
                        Office office = new Office();
                        office.setId("1");
                        user.setOffice(office);
                        warning.setCreateBy(user);
                        warning.setUpdateBy(user);
                        warning.setCreateOrgId("1");
                        warning.setUpdateOrgId("1");
                        warningService.save(warning);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(DateUtils.formatDate(new Date())+"缺勤累计即将达到6个月数据抓取定时任务发生错误，"+e.getCause());
        }

    }

    /*查询差一个月达到退休年龄的人员，并生成预警任务
    * 暂定 每月1号 00:00:00
    * 预警提醒开始时间 8:00:00
    * */
    @Scheduled(cron = "0 0 0 1 * ? ")
    public void selOneMonthTXWarn(){
        try {
            log.info("预警：对差一个月达到退休年龄的人员信息数据抓取定时任务开始");
            //查询出还差一个月达到退休年龄的人员
            List<PersonnelBase> list = personnelBaseService.findOneMonthTX();
            for (PersonnelBase p : list){
                Warning warning = new Warning();
                warning.setName("离退休一月预警");//设置预警名称
                String receivePerName = "";
                String receivePerId = "";
                String workUnitId = p.getWorkunitId();
                if(StringUtils.isNotBlank(workUnitId)){
                    Office office = officeService.get(workUnitId);
                    String userId = userDao.getIdByNo(office.getCode());
                    receivePerName = office.getName();
                    receivePerId = userId;
                }
                try {
                    String personnelUserId = userDao.getIdByNo(p.getIdNumber());
                    if(StringUtils.isNotBlank(personnelUserId)){
                        warning.setReceivePerName(receivePerName+","+p.getName());//设置预警接受人员姓名
                        warning.setReceivePerId(receivePerId+","+personnelUserId);//设置预警接受人员id
                    }else{
                        warning.setReceivePerName(receivePerName);//设置预警接受人员姓名
                        warning.setReceivePerId(receivePerId);//设置预警接受人员id
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("离退休一月预警，获取当前人员userId发生错误，"+e.getMessage());
                    warning.setReceivePerName(receivePerName);//设置预警接受人员姓名
                    warning.setReceivePerId(receivePerId);//设置预警接受人员id
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                warning.setDate(calendar.getTime());//设置预警开始提醒时间 8:00:00
                warning.setRemind("20");//设置重复提醒时间 每隔20分钟提醒一次
                warning.setRepeatCycle("4"); //设置重复周期 4 : 永不
                warning.setContinueDay("7");//设置持续时间 7天
                warning.setIsAlert("1");//设置选择弹窗显示
                warning.setAlertContent(p.getWorkunitName()+"单位下："+p.getName()+"(警号:"+p.getPoliceIdNumber()+")，一月后将达到退休年龄");//设置弹窗内容
                warning.setAlertDegree("1");//设置弹窗紧急程度

                //设置创建人及更新人信息
                User user = new User();
                user.setId("1");
                Office office = new Office();
                office.setId("1");
                user.setOffice(office);
                warning.setCreateBy(user);
                warning.setUpdateBy(user);
                warning.setCreateOrgId("1");
                warning.setUpdateOrgId("1");
                //添加至数据库
                warningService.save(warning);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(DateUtils.formatDate(new Date())+"对差一个月达到退休年龄的人员信息数据抓取定时任務发生错误，"+e.getCause());
        }

    }
    //每天执行的定时任務
    //每天 00：00：00 执行
    @Scheduled(cron = "0 0 0 * * ? ")
    public void dailyTasks(){
        log.info("每天执行的定时任務"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss")+"开始");
        partyOrgChange();//党组织换届提前半年预警
        promoteBeforeTrimesterTask();//职务晋升三月前预警
        selTwoMonthTXWarnTask();//查询差两个月达到退休年龄的人员
        selTreeMonthPoliceCertificateWarnTask();//差三个月到期的警察证
        setReturnGuoDateWarnTask();//回收持有护照（通行证）预警
        politicalBirthdayTask();//政治过生预警
        examWarnTask();//考评预警
        if("25".equals(DateUtils.getDay())){
            submitThinkingReportWarnTask();//提交思想汇报预警
        }
        log.info("每天执行的定时任務"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss")+"结束");
    }

    /*
    * 党组织换届提前半年预警
    * 暂定每天凌晨 00:20:00
    * 预警提醒开始时间 8:00:00
    * */
    //@Scheduled(cron = "0 20 0 * * ? ")
    public void partyOrgChange(){
        try {
            log.info("预警：党组织换届提前半年预警数据抓取定时任务开始");
            List<AffairElection> list = affairElectionDao.findAllSHTGList(); //查询所有的党组织换届选举信息
            for(AffairElection affairElection : list){
                Date hjDate = affairElection.getHjDate();
                Date nowDate = new Date();

                if(isDiffSixMonth(nowDate,hjDate)){
                    String partyOrganizationId = affairElection.getPartyOrganizationId();//党组织id
                    String partyOrganizationName = affairElection.getPartyOrganizationName();//党组织名称
                    AffairGeneralSituation affairGeneralSituation = affairGeneralSituationDao.get(partyOrganizationId);//根据党组织id查询党组织信息
                    User user = userDao.findDJUserByOfficeId(affairGeneralSituation.getUnitId());
                    Warning  warning = new Warning();
                    warning.setName("党组织换届提前半年预警");//设置预警名称
                    warning.setReceivePerName(user.getName());//接受人员名称
                    warning.setReceivePerId(user.getId());//接受人员ID
                    //设置开始时间  8:00:00
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,8);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);
                    warning.setDate(calendar.getTime());
                    warning.setRemind("20");//设置重复提醒时间 每隔20分钟提醒一次
                    warning.setRepeatCycle("4"); //设置重复周期 4 : 永不
                    warning.setContinueDay("0");//设置持续时间 7天
                    warning.setIsAlert("1");//设置选择弹窗显示
                    warning.setAlertContent(affairGeneralSituation.getUnit()+"单位下："+affairElection.getPartyOrganizationName()+"将于"+DateUtils.formatDate(affairElection.getHjDate(),"yyyy-MM-dd")+"进行党组织换届选举");//设置弹窗内容
                    warning.setAlertDegree("1");//设置弹窗紧急程度

                    //设置创建人及更新人信息
                    User creatUser = new User();
                    creatUser.setId("1");
                    Office office = new Office();
                    office.setId("1");
                    creatUser.setOffice(office);
                    warning.setCreateBy(creatUser);
                    warning.setUpdateBy(creatUser);
                    warning.setCreateOrgId("1");
                    warning.setUpdateOrgId("1");
                    warningService.save(warning);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(DateUtils.formatDate(new Date())+"党组织换届提前半年预警数据抓取定时任务开始，"+e.getCause());
        }
    }


    /*
     * 晋升三月前预警
     * 预警提醒开始时间 8:00:00
     * 按照系统已设定的晋升规则
     * 目前未使用 2021.1.12
     * */
    //@Scheduled(cron = "0 0/5 * * * ? ")
    public void promoteBeforeTrimesterTask2(){
        try {
            log.info("预警：距离晋升相差三月人员信息抓取并生成预警任务");
            //List<PersonnelBase> list = personnelBaseService.findList(new PersonnelBase());
            List<PersonnelPoliceRank> personnelPoliceRankList = personnelPoliceRankDao.findLastList(new PersonnelPoliceRank());
            Calendar threeMonth =  Calendar.getInstance();
            threeMonth.add(Calendar.MONTH,3);
            Date endDate = threeMonth.getTime();//截至时间设置为三月后
            List<Dict> typeRankList = DictUtils.getDictList("type_rank_calculation");
            List<Map<String, String>> map = dictDao.findListByType("type_rank_calculation");
//            for(Map<String, String> itemMap : map){
            for(Dict dict : typeRankList){
                String calculateType = dict.getValue();
//                String calculateType = itemMap.get("value");
                personnelPoliceRankList.forEach(personnelPoliceRank -> {
                    if (StringUtils.isNotBlank(personnelPoliceRank.getIdNumber())){
                        String nowjxyjxznx = personnelPoliceRank.getNowRank();//获取当前警衔
                        /*
                         * calculateType  值及代表类型
                         * 1 ：首评警督    从警司晋升为警督
                         * 2 ：首评警司    从警员晋升为警司
                         * 3 ：选升警监    据选升条件（包含首评、晋升）
                         * 4 ：晋升警督    三级或二级晋升上一级
                         * 5 ：晋升警司    三级或二级晋升上一级
                         * 6 ：晋督培训
                         * 7 ：晋司培训
                         * 警衔级别：
                         * 1：二级警员  2：一级警员  3：三级警司  4：二级警司  5：一级警司
                         * 6：三级警督  7：二级警督  8：一级警督  9：三级警监  10：二级警监
                         * 11：一级警监  12：副总警监  13：总警监
                         * */
                        /*String calculateType = "";  //晋升类型
                        if(nowjxyjxznx==null||nowjxyjxznx.isEmpty()){
                            calculateType = "";
                        }
                        else{
                            //根据当前警衔  设置晋升类型
                            switch (nowjxyjxznx){
                                case "二级警员":
                                    calculateType="2";
                                    break;
                                case "一级警员":
                                    calculateType="2";
                                    break;
                                case "三级警司":
                                    calculateType="5";
                                    break;
                                case "二级警司":
                                    calculateType="5";
                                    break;
                                case "一级警司":
                                    calculateType = "1";
                                    break;
                                case "三级警督":
                                    calculateType = "4";
                                    break;
                                case "二级警督":
                                    calculateType = "4";
                                    break;
                                case "一级警督":
                                    calculateType = "3";
                                    break;
                                case "三级警监":
                                    calculateType = "3";
                                    break;
                                case "二级警监":
                                    calculateType = "3";
                                    break;
                                default:
                                    calculateType = "";
                                    break;
                            }

                        }*/
                        if(!calculateType.isEmpty()){
                            PersonnelBase personnelBase = personnelPoliceRankService.promoteBeforeTrimester2(personnelPoliceRank, calculateType,endDate);
                            if(personnelBase!=null){
                                //符合晋升要求的生成预警任务
                                Warning warning = new Warning();
//                                warning.setName(personnelBase.getName()+","+itemMap.get("label")+",晋升预警");//设置预警名称
                                warning.setName(personnelBase.getName()+","+dict.getLabel()+",晋升预警");//设置预警名称
                                String unitName = "";
                                String unitUserId ="";
                                if(officeService.findUserByName(personnelBase.getWorkunitName())==null || officeService.findUserByName(personnelBase.getWorkunitName()).isEmpty()){
                                    String code = officeService.findCodeById(personnelBase.getWorkunitId());
                                    String id = userDao.getIdByNo(code);
                                    User sqlUser = userDao.get(id);
                                    unitName = sqlUser.getName();
                                    unitUserId = sqlUser.getId();
                                }else{
                                    unitName = personnelBase.getWorkunitName();
                                    unitUserId = officeService.findUserByName(personnelPoliceRank.getUnit());
                                }

                                warning.setReceivePerName(personnelBase.getName()+","+unitName);//接受人员/部门
                                warning.setReceivePerId(personnelBase.getIdNumber()+","+unitUserId);//接受人员/部门 Id
                                //设置开始时间  8:00:00
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.HOUR_OF_DAY,8);
                                calendar.set(Calendar.MINUTE,0);
                                calendar.set(Calendar.SECOND,0);
                                calendar.set(Calendar.MILLISECOND,0);
                                warning.setDate(calendar.getTime());
                                warning.setRemind("20");//设置重复提醒时间 每隔20分钟提醒一次
                                warning.setRepeatCycle("4"); //设置重复周期 4 : 永不
                                warning.setContinueDay("0");//设置持续时间 收到后停止
                                warning.setIsAlert("1");//设置选择弹窗显示
                                //DictUtils.getDictLabels(personnelPoliceRank.getPoliceRankLevel(),"police_rank_level","")
                                warning.setAlertContent(personnelBase.getWorkunitName()+"单位下："+personnelBase.getName()+"(身份证号为"+personnelBase.getIdNumber()+"),当前警衔为："+DictUtils.getDictLabels(personnelPoliceRank.getPoliceRankLevel(),"police_rank_level","")+"将于三月后达到"+dict.getLabel()+"晋升要求");//设置弹窗内容
//                                warning.setAlertContent(personnelBase.getWorkunitName()+"单位下："+personnelBase.getName()+"(身份证号为"+personnelBase.getIdNumber()+"),当前警衔为："+dictDao.findLabelByValueType(personnelPoliceRank.getPoliceRankLevel(),"police_rank_level")+"将于三月后达到"+itemMap.get("label")+"晋升要求");//设置弹窗内容
                                warning.setAlertDegree("1");//设置弹窗紧急程度
                                //设置创建人及更新人信息
                                User creatUser = new User();
                                creatUser.setId("1");
                                Office office = new Office();
                                office.setId("1");
                                creatUser.setOffice(office);
                                warning.setCreateBy(creatUser);
                                warning.setUpdateBy(creatUser);
                                warning.setCreateOrgId("1");
                                warning.setUpdateOrgId("1");
                                warningService.save(warning);
                            }

                        }
                    }

                });
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error(DateUtils.formatDate(new Date())+"距离晋升相差三月人员信息抓取并生成预警任务，"+e.getCause());
        }

    }
    /*
     * 职务晋升三月前预警
     * 预警提醒开始时间 8:00:00
     * 按照12月    预警表格给出的规则进行预警
     * */
    //@Scheduled(cron = "0 0/5 * * * ? ")
    public void promoteBeforeTrimesterTask(){
        try {
            log.info("预警：距离职务晋升相差三月人员信息抓取并生成预警任务");
            // 提醒各级组干部门，按照职级晋升既定的基本条件,测算每个人的晋升时间点。
            // jobLevel 职务层次
            // 规则：按照上次晋升时间，处特殊情况外，年限为2年；
            // 特殊情况：二级高级警长、警务技术二级主任、县处级正职晋升上级职级年限3年（提前3个月）；
            //           12                             30
            // 一级高级警长、警务技术一级主任、二级警务专员、警务技术二级总监、厅局级副职往上晋升需要4年。
            //      32                                                      28
            // 状态  status  1 当前任职职级  2 职级终止   3  职务变动、职级不变    4  职务已免、职级有效
            List<Map<String,Object>> mapList = personnelJobService.getNewestJobList();
            if(mapList!=null && mapList.size()>0){
                StringBuffer nncAlertContent = null;
                StringBuffer lzcAlertContent = null;
                StringBuffer bhcAlertContent = null;
                StringBuffer nnjAlertContent = null;
                StringBuffer noUnitAlertContent = null;
                boolean isNNc = false;
                boolean isLzc = false;
                boolean isBhc = false;
                boolean isNNj = false;
                boolean isNoUnit = false;
                for (Map<String,Object> map : mapList){
                    // 单元测试
                    //Date date = DateUtils.parseDate("2022-03-11");
                    Calendar calendar =  Calendar.getInstance();
                    //calendar.setTime(date);
                    calendar.add(Calendar.MONTH,3);
                    //00:00:00.000
                    calendar.set(Calendar.HOUR_OF_DAY,0);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);
                    String jsDate = DateUtils.formatDate(calendar.getTime());//应晋升时间
                    String name = (String) map.get("name");
                    String idNumber = (String) map.get("idNumber");
                    String policeIdNumber = (String) map.get("policeIdNumber");
                    String workunitId = (String) map.get("workunitId");
                    String workunitName = (String) map.get("workunitName");
                    String unitCode = (String) map.get("unitCode");
                    String parentIds = (String) map.get("parentIds");
                    String jobLevel = (String) map.get("jobLevel");
                    Date startDate = (Date) map.get("startDate");
                    if("12".equals(jobLevel)||"警务技术二级主任".equals(jobLevel)||"30".equals(jobLevel)){
                        // 12 二级高级警长、警务技术二级主任、30 县处级正职上级职级年限3年
                       calendar.add(Calendar.YEAR,-3);
                       if(startDate.equals(calendar.getTime())){
                           //相等
                           if(parentIds!=null){
                               if(parentIds.contains(",34,")){
                                   isNNc = true;
                                   if(nncAlertContent==null){
                                       nncAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       nncAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else if(parentIds.contains(",95,")){
                                   isLzc = true;
                                   if(lzcAlertContent==null){
                                       lzcAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       lzcAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else if(parentIds.contains(",156,")){
                                   isBhc = true;
                                   if(bhcAlertContent==null){
                                       bhcAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       bhcAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else{
                                   isNNj = true;
                                   if(nnjAlertContent==null){
                                       nnjAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       nnjAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }
                           }
                           else{
                               // 单位id为空
                               isNoUnit = true;
                               if(noUnitAlertContent==null){
                                   noUnitAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                               }else{
                                   noUnitAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                               }
                           }
                       }
                   }else if("32".equals(jobLevel) || "警务技术一级主任".equals(jobLevel) || "二级警务专员".equals(jobLevel) || "警务技术二级总监".equals(jobLevel) || "28".equals(jobLevel)){
                        //32 一级高级警长、警务技术一级主任、二级警务专员、警务技术二级总监、28  厅局级副职往上晋升需要4年。
                       calendar.add(Calendar.YEAR,-4);
                       if(startDate.equals(calendar.getTime())){
                           //相等
                           if(parentIds!=null){
                               if(parentIds.contains(",34,")){
                                   isNNc = true;
                                   if(nncAlertContent==null){
                                       nncAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       nncAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else if(parentIds.contains(",95,")){
                                   isLzc = true;
                                   if(lzcAlertContent==null){
                                       lzcAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       lzcAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else if(parentIds.contains(",156,")){
                                   isBhc = true;
                                   if(bhcAlertContent==null){
                                       bhcAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       bhcAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else{
                                   isNNj = true;
                                   if(nnjAlertContent==null){
                                       nnjAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       nnjAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }
                           }
                           else{
                               // 单位id为空
                               // 单位id为空
                               isNoUnit = true;
                               if(noUnitAlertContent==null){
                                   noUnitAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                               }else{
                                   noUnitAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                               }
                           }
                       }
                   }else{
                       //除特殊情况外，年限为2年；
                       calendar.add(Calendar.YEAR,-2);
                       if(startDate.equals(calendar.getTime())){
                           //相等
                           if(parentIds!=null){
                               if(parentIds.contains(",34,")){
                                   isNNc = true;
                                   if(nncAlertContent==null){
                                       nncAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       nncAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else if(parentIds.contains(",95,")){
                                   isLzc = true;
                                   if(lzcAlertContent==null){
                                       lzcAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       lzcAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else if(parentIds.contains(",156,")){
                                   isBhc = true;
                                   if(bhcAlertContent==null){
                                       bhcAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       bhcAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }else{
                                   isNNj = true;
                                   if(nnjAlertContent==null){
                                       nnjAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }else{
                                       nnjAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                                   }
                               }
                           }
                           else{
                               // 单位id为空
                               isNoUnit = true;
                               if(noUnitAlertContent==null){
                                   noUnitAlertContent = new StringBuffer(workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                               }else{
                                   noUnitAlertContent.append("<br>"+workunitName+"单位下："+name+"(警号："+policeIdNumber+")，当前职务层次："+DictUtils.getDictLabels(jobLevel,"personnel_zwcc","")+"，任职时间："+startDate+"将于"+jsDate+"达到晋升基本要求");
                               }
                           }
                       }
                   }

                }
                Warning warning = new Warning();
                warning.setName("职务晋升预警");
                Calendar startCalender = Calendar.getInstance();
                startCalender.set(Calendar.HOUR_OF_DAY,8);
                startCalender.set(Calendar.MINUTE,0);
                startCalender.set(Calendar.SECOND,0);
                startCalender.set(Calendar.MILLISECOND,0);
                warning.setDate(startCalender.getTime());
                warning.setRemind("20");//设置重复提醒时间 每隔20分钟提醒一次
                warning.setRepeatCycle("4"); //设置重复周期 4 : 永不
                warning.setContinueDay("0");//设置持续时间 收到后停止
                warning.setIsAlert("1");//是否使用弹窗
                warning.setAlertDegree("1");//设置弹窗紧急程度
                User creatUser = new User();
                creatUser.setId("1");
                Office office = new Office();
                office.setId("1");
                creatUser.setOffice(office);
                warning.setCreateBy(creatUser);
                warning.setUpdateBy(creatUser);
                warning.setCreateOrgId("1");
                warning.setUpdateOrgId("1");
                if(nncAlertContent!=null){
                    warning.setName("南宁处人员职务晋升预警");
                    warning.setAlertContent("晋升名单如下："+nncAlertContent.toString());
                    warning.setReceivePerId("34e8d855cf6b4b1ab5e7e23e7aaba658,bfdf74f010c9466dba12c1589ecab7f3");
                    warning.setReceivePerName("南宁处政治处组织干部室,南宁局政治部组织干部处");
                    warning.setId("");
                    warning.setIsNewRecord(false);
                    warningService.save(warning);
                }
                if(lzcAlertContent!=null){
                    warning.setName("柳州处人员职务晋升预警");
                    warning.setAlertContent("晋升名单如下："+lzcAlertContent.toString());
                    warning.setReceivePerId("ec3ba2efdd404f2faa520f6e8a71ec4c,bfdf74f010c9466dba12c1589ecab7f3");
                    warning.setReceivePerName("柳州处政治处组织干部室,南宁局政治部组织干部处");
                    warning.setId("");
                    warning.setIsNewRecord(false);
                    warningService.save(warning);
                }
                if(bhcAlertContent!=null){
                    warning.setName("北海处人员职务晋升预警");
                    warning.setAlertContent("晋升名单如下："+bhcAlertContent.toString());
                    warning.setReceivePerId("c90918faf2614baa8fa85230482bd43e,bfdf74f010c9466dba12c1589ecab7f3");
                    warning.setReceivePerName("北海处政治处组织干部室,南宁局政治部组织干部处");
                    warning.setId("");
                    warning.setIsNewRecord(false);
                    warningService.save(warning);
                }
                if(nnjAlertContent!=null){
                    warning.setName("局机关人员职务晋升预警");
                    warning.setAlertContent("晋升名单如下："+nnjAlertContent.toString());
                    warning.setReceivePerId("bfdf74f010c9466dba12c1589ecab7f3");
                    warning.setReceivePerName("南宁局政治部组织干部处");
                    warning.setId("");
                    warning.setIsNewRecord(false);
                    warningService.save(warning);
                }
                if(noUnitAlertContent!=null){
                    warning.setName("单位信息不全人员职务晋升预警");
                    warning.setAlertContent("晋升名单如下："+nnjAlertContent.toString());
                    warning.setReceivePerId("bfdf74f010c9466dba12c1589ecab7f3");
                    warning.setReceivePerName("南宁局政治部组织干部处");
                    warning.setId("");
                    warning.setIsNewRecord(false);
                    warningService.save(warning);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error(DateUtils.formatDate(new Date())+"距离职务晋升相差三月人员信息抓取并生成预警任务发生错误，"+e.getCause());
        }

    }

    /* 查询差两个月达到退休年龄的人员，并生成预警任务
     * 暂定 每天 00:00:00
     * 预警提醒开始时间 8:00:00
     * */
    //@Scheduled(cron = "0 0 0 * * ? ")
    public void selTwoMonthTXWarnTask(){
        try {
            log.info("预警：查询差两个月达到退休年龄的人员预警定时任务开始");
            //查询出今年，年龄达到60 ，59 的男士人员信息
            List<PersonnelBase> manList = personnelBaseService.findManTXList();
            //查询出年龄大于等于54 的女士人员信息
            List<PersonnelBase> womanList = personnelBaseService.findWomanList();
            //局处部门id  1 南宁铁路公安局 34  南宁公安处 95 柳州公安处 156 北海公安处
            String juTXName = "";  //南宁局退休人员
            String nncTXName = ""; //南宁处退休人员
            String lzcTXName = ""; //柳州处退休人员
            String bhcTXName = ""; //北海处退休人员
            for(PersonnelBase p : manList){
                if(isReachTX(p)){
                    Office office = officeService.get(p.getWorkunitId());
                    if("156".equals(office.getId()) || office.getParentIds().indexOf("156")!= -1){
                        bhcTXName += p.getName()+"(性别:男 警号为:"+p.getPoliceIdNumber()+"),";
                    }else if(("95".equals(office.getId()) || office.getParentIds().indexOf("95")!= -1)){
                        lzcTXName += p.getName()+"(性别:男 警号为:"+p.getPoliceIdNumber()+"),";
                    }else if(("34".equals(office.getId()) || office.getParentIds().indexOf("34")!= -1)){
                        nncTXName += p.getName()+"(性别:男 警号为:"+p.getPoliceIdNumber()+"),";
                    }else{
                        juTXName += p.getName()+"(性别:男 警号为:"+p.getPoliceIdNumber()+"),";
                    }
                }
            }
            for(PersonnelBase p : womanList){

                if(isReachTX(p)){
                    Office office = officeService.get(p.getWorkunitId());
                    if("156".equals(office.getId()) || office.getParentIds().indexOf("156")!= -1){
                        bhcTXName += p.getName()+"(性别:女 警号为:"+p.getPoliceIdNumber()+"),";
                    }else if(("95".equals(office.getId()) || office.getParentIds().indexOf("95")!= -1)){
                        lzcTXName += p.getName()+"(性别:女 警号为:"+p.getPoliceIdNumber()+"),";
                    }else if(("34".equals(office.getId()) || office.getParentIds().indexOf("34")!= -1)){
                        nncTXName += p.getName()+"(性别:女 警号为:"+p.getPoliceIdNumber()+"),";
                    }else{
                        juTXName += p.getName()+"(性别:女 警号为:"+p.getPoliceIdNumber()+"),";
                    }
                }

            }
            Warning warning = new Warning();
            warning.setName("退休预警");
            warning.setRepeatCycle("4");//设置重复周期  4 : 永不
            warning.setRemind("30"); //设置重复提醒时间  每半小时提醒一次
            //设置开始时间  8:00:00
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,8);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            warning.setDate(calendar.getTime());//设置预警提醒开始时间
            warning.setContinueDay("0");//收到后停止
            warning.setIsAlert("1");//选择使用弹窗
            warning.setAlertDegree("1");//紧急程度

            //设置创建人及更新人信息
            User creatUser = new User();
            creatUser.setId("1");
            Office office = new Office();
            office.setId("1");
            creatUser.setOffice(office);
            warning.setCreateBy(creatUser);
            warning.setUpdateBy(creatUser);
            warning.setCreateOrgId("1");
            warning.setUpdateOrgId("1");
            //弹窗内容 ，接受人员名称  接受人员id
            //公安局政治部组织干部处
            if(!juTXName.isEmpty()){
                Warning juWarning = new Warning();
                BeanUtils.copyProperties(warning,juWarning);
                juWarning.setIsNewRecord(false);
                juWarning.setReceivePerName("公安局政治部组织干部处");
                juWarning.setReceivePerId("bfdf74f010c9466dba12c1589ecab7f3");
                juWarning.setAlertContent("南宁公安局单位下："+juTXName+"以上人员两月后将达到退休年龄");
                //System.out.println("公安局政治部组织干部处"+juWarning);
                warningService.save(juWarning);
            }
            //南宁处政治处组织干部室
            if(!nncTXName.isEmpty()){
                Warning nncWarning = new Warning();
                BeanUtils.copyProperties(warning,nncWarning);
                nncWarning.setIsNewRecord(false);
                nncWarning.setReceivePerName("南宁处政治处组织干部室");
                nncWarning.setReceivePerId("34e8d855cf6b4b1ab5e7e23e7aaba658");
                nncWarning.setAlertContent("南宁公安处单位下："+nncTXName+"以上人员两月后将达到退休年龄");
                //System.out.println("南宁处政治处组织干部室"+nncWarning);
                warningService.save(nncWarning);
            }
            //柳州处政治处组织干部室
            if(!lzcTXName.isEmpty()){
                Warning lzcWarning = new Warning();
                BeanUtils.copyProperties(warning,lzcWarning);
                lzcWarning.setIsNewRecord(false);
                lzcWarning.setReceivePerName("柳州处政治处组织干部室");
                lzcWarning.setReceivePerId("ec3ba2efdd404f2faa520f6e8a71ec4c");
                lzcWarning.setAlertContent("柳州公安处单位下："+lzcTXName+"以上人员两月后将达到退休年龄");
                //System.out.println("柳州处政治处组织干部室"+lzcWarning);
                warningService.save(lzcWarning);
            }
            //北海处处政治处组织干部室
            if(!bhcTXName.isEmpty()){
                Warning bhcWarning = new Warning();
                BeanUtils.copyProperties(warning,bhcWarning);
                bhcWarning.setIsNewRecord(false);
                bhcWarning.setReceivePerName("北海处政治处组织干部室");
                bhcWarning.setReceivePerId("c90918faf2614baa8fa85230482bd43e");
                bhcWarning.setAlertContent("北海公安处单位下："+bhcTXName+"以上人员两月后将达到退休年龄");
                //System.out.println("北海处政治处组织干部室"+bhcWarning);
                warningService.save(bhcWarning);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(DateUtils.formatDate(new Date())+"查询差两个月达到退休年龄的人员预警定时任務发生错误，"+e.getCause());
        }
    }

    /* 查询差三个月到期的警察证，并生成预警任务
     * 暂定 每天 00:5:00
     * 预警提醒开始时间 8:00:00
     * */
    //@Scheduled(cron = "0 5 0 * * ? ")
    public void selTreeMonthPoliceCertificateWarnTask(){
        try {
            log.info("预警定时任务：查询差三个月到期的警察证开始");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,3);
            Date date = calendar.getTime();
            String treeMonthDate = DateUtils.formatDate(date,"yyyy-MM-dd");
            //设置开始时间  8:00:00
            Calendar nowCalendar = Calendar.getInstance();
            nowCalendar.set(Calendar.HOUR_OF_DAY,8);
            nowCalendar.set(Calendar.MINUTE,0);
            nowCalendar.set(Calendar.SECOND,0);
            nowCalendar.set(Calendar.MILLISECOND,0);
            User creatUser = new User();
            creatUser.setId("1");
            Office office = new Office();
            office.setId("1");
            creatUser.setOffice(office);
            List<PersonnelPoliceCertificate> list = personnelPoliceCertificateService.selTreeMonthPoliceCertificateInfo(treeMonthDate);
            for(PersonnelPoliceCertificate policeCertificate : list){
                String userId = policeCertificate.getUserId();
                String parentIds = policeCertificate.getParentIds();
                String chuUserId = "";
                String chuUserName = "";
                String juUserId = "96d26e10bd074eecbc6b8b3a619bec1d";
                String juUserName = "档案信息管理";
                if(parentIds.contains(",34,")){
                    chuUserId = "4103b50669e9422391fb70aa704266b1";
                    chuUserName = "南宁处档案信息管理";
                }else if(parentIds.contains(",95,")){
                    chuUserId = "3404d05bf9054a51ae9afbe40e44a718";
                    chuUserName = "柳州处档案信息管理";
                } else if(parentIds.contains(",95,")){
                    chuUserId = "957de2956a384bad96adbaa35cb05520";
                    chuUserName = "北海处档案信息管理";
                }else{
                    chuUserId = null;
                    chuUserName = null;
                }
                Warning warning = new Warning();
                warning.setName(policeCertificate.getName()+"警察证到期预警");
                //开始时间 8：00：00
                warning.setDate(nowCalendar.getTime());
                warning.setContinueDay("0");//收到后停止
                warning.setIsAlert("1");//选择使用弹窗
                warning.setAlertDegree("1");//紧急程度
                warning.setAlertContent(DateUtils.formatDate(nowCalendar.getTime(),"yyyy-MM-dd")+","+policeCertificate.getName()+"(警察证号："+policeCertificate.getCertificateNo()+")警察证将于三月后到期");
                warning.setRepeatCycle("4");//设置重复周期  4 : 永不
                warning.setRemind("20"); //设置重复提醒时间  每20分钟提醒一次
                warning.setCreateBy(creatUser);
                warning.setUpdateBy(creatUser);
                warning.setCreateOrgId("1");
                warning.setUpdateOrgId("1");
                if(StringUtils.isNotBlank(chuUserId)){
                    warning.setReceivePerId(userId+","+juUserId+","+chuUserId);
                    warning.setReceivePerName(policeCertificate.getName()+","+juUserName+","+chuUserName);
                }else{
                    warning.setReceivePerId(userId+","+juUserId);
                    warning.setReceivePerName(policeCertificate.getName()+","+juUserName);
                }
                warningService.save(warning);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(DateUtils.formatDate(new Date())+"差三个月到期的警察证预警定时任務发生错误，"+e.getCause());
        }

    }

    /**
     * 利用出国境信息管理中的“回国日期”信息项，
     * 增加回收持有护照（通行证）预警提醒，“回国日期”后10天
     * 12.29 kevin.jia
     * 每天执行，获取当天回国人员信息
     * 设置回国日期十天后预警
     */
    //@Scheduled(cron = "0 0/5 * * * ? ")
    public void setReturnGuoDateWarnTask(){
        try {
            log.info("预警定时任务：回收持有护照（通行证）预警提醒，“回国日期”后10天");
            List<PersonnelGoAbroad> list = personnelGoAbroadService.getTodayList(DateUtils.getDate());//获取今天回国人员
            if(list!=null && list.size()>0){
                for(PersonnelGoAbroad personnelGoAbroad : list){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(personnelGoAbroad.getReturnDate());//回国日期
                    calendar.add(Calendar.DATE,10);
                    calendar.set(Calendar.HOUR_OF_DAY,8);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);
                    Date warnStartTime = calendar.getTime();
                    Warning warning = new Warning();
                    warning.setName("回收持有护照（通行证）预警");
                    warning.setDate(warnStartTime);
                    warning.setContinueDay("0");//持续时间
                    warning.setRepeatCycle("4");//周期
                    warning.setIsAlert("1");//使用弹窗
                    warning.setAlertDegree("1");//紧急程度
                    warning.setRemind("20");//重复提醒
                    warning.setAlertContent("回收持有护照（通行证）预警,"+personnelGoAbroad.getName()+"系统所存储，回国日期为"+ DateUtils.formatDate(personnelGoAbroad.getReturnDate()));
                    User creatUser = new User();
                    creatUser.setId("1");
                    Office office = new Office();
                    office.setId("1");
                    creatUser.setOffice(office);
                    warning.setCreateBy(creatUser);
                    warning.setUpdateBy(creatUser);
                    warning.setCreateOrgId("1");
                    warning.setUpdateOrgId("1");
                    String idNumber = personnelGoAbroad.getIdNumber();
                    User user = UserUtils.get(userDao.getIdByNo(idNumber));
                    if(user.getOffice().getParentIds().contains(",34,")){
                        warning.setReceivePerName(user.getName()+",南宁处人事信息管理,南宁局人事信息管理");//接受人员名
                        warning.setReceivePerId(user.getId()+",69f857c3e1854021b5dee55c514026e3,bd42300887ad417fa3f2fa9f554e6419");//接受人员Id
                    }else if(user.getOffice().getParentIds().contains(",95,")){
                        warning.setReceivePerName(user.getName()+",柳州处人事信息管理,南宁局人事信息管理");//接受人员名
                        warning.setReceivePerId(user.getId()+",3850cecf34be44188f94b0edc552aff3,bd42300887ad417fa3f2fa9f554e6419");//接受人员Id
                    }else if(user.getOffice().getParentIds().contains(",156,")){
                        warning.setReceivePerName(user.getName()+",北海处人事信息管理,南宁局人事信息管理");//接受人员名
                        warning.setReceivePerId(user.getId()+",1c19f6cc935f430f9f27295b761b1236,bd42300887ad417fa3f2fa9f554e6419");//接受人员Id
                    }else{
                        warning.setReceivePerName(user.getName()+",南宁局人事信息管理");//接受人员名
                        warning.setReceivePerId(user.getId()+",bd42300887ad417fa3f2fa9f554e6419");//接受人员Id
                    }
                    warningService.save(warning);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(DateUtils.formatDate(new Date())+"回收持有护照（通行证）预警定时任務发生错误，"+e.getCause());
        }
    }


    /**
     * 党员正式生日提前三天预警
     * 以入党日期为准，提醒党支部（单位）为每名党员过政治生日。
     * 达成及提醒，收到后停止
     */
    public void politicalBirthdayTask(){
        try {
            log.info("政治生日预警定时任务开始");
            Calendar oneDayAfterCalendar = Calendar.getInstance();//当前日期
            oneDayAfterCalendar.add(Calendar.DAY_OF_MONTH,1);//往后推一天
            String threeDayAfter = DateUtils.formatDate(oneDayAfterCalendar.getTime(),"MM-dd");//一天后时间
            List<AffairPartyMember> affairPartyMemberList = affairPartyMemberService.selThreeDayAfterBirthday(threeDayAfter);//查询一天天后过政治生日人员信息
            if(affairPartyMemberList !=null && affairPartyMemberList.size()>0){
                for(AffairPartyMember partyMember : affairPartyMemberList){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,8);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);
                    Date warnStartTime = calendar.getTime();
                    Warning warning = new Warning();
                    warning.setName("党员政治生日预警");
                    warning.setDate(warnStartTime);
                    warning.setContinueDay("0");//持续时间
                    warning.setRepeatCycle("4");//周期
                    warning.setIsAlert("1");//使用弹窗
                    warning.setAlertDegree("1");//紧急程度
                    warning.setRemind("20");//重复提醒
                    warning.setAlertContent(partyMember.getPartyBranch()+","+partyMember.getName()+"入党日期："+DateUtils.formatDate(partyMember.getJoinDate())+",将于"+DateUtils.formatDate(oneDayAfterCalendar.getTime())+"过政治生日");
                    User creatUser = new User();
                    creatUser.setId("1");
                    Office office = new Office();
                    office.setId("1");
                    creatUser.setOffice(office);
                    warning.setCreateBy(creatUser);
                    warning.setUpdateBy(creatUser);
                    warning.setCreateOrgId("1");
                    warning.setUpdateOrgId("1");
                    List<User> userList = userDao.getUserByPartyId(partyMember.getPartyBranchId());//单位用户list
                    StringBuffer receivePerName =null;
                    StringBuffer receivePerId = null;
                    for(User user : userList){
                        if(receivePerName==null){
                            receivePerName= new StringBuffer(user.getName());
                        }else{
                            receivePerName.append(","+user.getName());
                        }
                        if(receivePerId==null){
                            receivePerId= new StringBuffer(user.getId());
                        }else{
                            receivePerId.append(","+user.getId());
                        }
                    }
                    if(receivePerId!=null && receivePerName!=null){
                        warning.setReceivePerName(receivePerName.toString());//接受人员名称
                        warning.setReceivePerId(receivePerId.toString());//接受人员id
                        warningService.save(warning);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("政治生日预警定时任务，发生错误"+e.getCause());
        }

    }

    /**
     * 根据设置的自评、审核等各环节截止时间，提前一天提醒账号进行操作
     * 绩效考评预警
     */
    //@Scheduled(cron = "0 0/5 * * * ? ")
    public void examWarnTask(){
        //获取未结束的考评流程
        try {
            log.info("考评截至预警定时任务开始");
            List<ExamWorkflow> workflowList = examWorkflowService.getNoEndExamList();
            Calendar oneDayAfter = Calendar.getInstance();//一天后时间
            oneDayAfter.add(Calendar.DATE,1);
            if(workflowList!=null && workflowList.size()>0){
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                Date warnStartTime = calendar.getTime();
                for(ExamWorkflow examWorkflow : workflowList){
                    Integer status = examWorkflow.getStatus();
                    String workFlowId = examWorkflow.getId();
                    String examName = examWorkflow.getName();
                    //String examTime = examWorkflow.getTime();
                    String examTypeLabel = DictUtils.getDictLabel(examWorkflow.getExamType(),	"exam_type","");//考评类型
                    String examCycleLabel = DictUtils.getDictLabel(examWorkflow.getExamCycle(),	"exam_cycle","");//周期
                    Warning warning = new Warning();
                    warning.setName(examTypeLabel+"-"+examCycleLabel+","+examName+"绩效考评结束预警");
                    warning.setDate(warnStartTime);
                    warning.setContinueDay("0");//持续时间
                    warning.setRepeatCycle("4");//周期
                    warning.setIsAlert("1");//使用弹窗
                    warning.setAlertDegree("1");//紧急程度
                    warning.setRemind("20");//重复提醒
                    //设置创建人，修改人
                    User creatUser = new User();
                    creatUser.setId("1");
                    Office office = new Office();
                    office.setId("1");
                    creatUser.setOffice(office);
                    warning.setCreateBy(creatUser);
                    warning.setUpdateBy(creatUser);
                    //流程状态（环节）（-1：未启动，1:系统自评,2:系统初步考核,3:系统公示,4:部门负责人签字,5:分管局领导签字,6:绩效考评领导小组复核及调整,7:局主管领导最终审签,8:最终结果全局公示,99:考评已经结束）
                    if(status!=null && status!=3){
                        Map<String,Object> examMap = examWorkflowSegmentsService.findExamWorkflowIdEndTime(workFlowId,String.valueOf(status),DateUtils.formatDate(oneDayAfter.getTime()));
                        if(examMap!=null){
                            List<Map<String,String>> examWorkflowDataMapList =  examWorkflowDatasService.getCheckObjectList(workFlowId,String.valueOf(status),status);//获取未操作用户的集合
                            String dictStatusLabel = DictUtils.getDictLabels(String.valueOf(status),"workflow_status","负责");//字典label标签值
                            String examEndTime = DateUtils.formatDate((Date)examMap.get("endTime"),"yyyy-MM-dd HH:mm:ss");//结束时间
                            warning.setName(examTypeLabel+"-"+examCycleLabel+","+examName+"绩效考评"+dictStatusLabel+"阶段结束预警");
                            warning.setAlertContent(examName+"绩效考评将于"+examEndTime+"结束"+dictStatusLabel+"阶段，请及时进行考评");
                        /* switch (status){
                            case 1:
                                //自评
                                break;
                            case 2:
                                //初核
                                break;
                            case 3:
                                //系统公示
                                break;
                            case 4:
                                //部门负责人
                                break;
                            case 5:
                                //分管局领导签字
                                break;
                            case 6:
                                //绩效考评领导小组复核及调整
                                break;
                            case 7:
                                //局主管领导最终审签
                                break;
                            default:
                                break;
                        }*/
                            if(examWorkflowDataMapList!=null && examWorkflowDataMapList.size()>0){
                                StringBuffer receivePerName = null;
                                StringBuffer receivePerId = null;
                                for(Map<String,String> tempMap : examWorkflowDataMapList){
                                    String examTempPerson = tempMap.get("examTempPerson");
                                    String examTempPersonId = tempMap.get("examTempPersonId");
                                    //设置预警接受人员
                                    if(StringUtils.isNotBlank(examTempPerson) && StringUtils.isNotBlank(examTempPersonId)){
                                        if(receivePerName==null){
                                            receivePerName= new StringBuffer(examTempPerson);
                                        }else{
                                            receivePerName.append(","+examTempPerson);
                                        }
                                        if(receivePerId==null){
                                            receivePerId= new StringBuffer(examTempPersonId);
                                        }else{
                                            receivePerId.append(","+examTempPersonId);
                                        }
                                    }
                                }
                                warning.setReceivePerName(receivePerName.toString());
                                warning.setReceivePerId(receivePerId.toString());
                                warning.setId("");
                                warning.setIsNewRecord(false);
                                warningService.save(warning);
                            }
                        }
                    }



                }
            }
        }catch (Exception e){
            log.error("绩效考评预警-定时任务，发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }

    }

    /**
     * 每季度提醒积极分子提交思想汇报
     */
    //@Scheduled(cron = "0 20 0 25  * ? ")
    public void submitThinkingReportWarnTask(){
        try {
            log.info("预警定时任务：每季度提醒积极分子提交思想汇报开始");
            String month = DateUtils.getMonth();
            if("3".equals(month) || "6".equals(month) || "9".equals(month) || "12".equals(month)){
            //判断当前月份是否为 3 、6 、9 、12月
            List<AffairActivist> affairActivistList = affairActivistService.findAllActivistList();//查询所有的入党积极分子
            if(affairActivistList!=null && affairActivistList.size()>0){
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                Date warnStartTime = calendar.getTime();
                Warning warning = new Warning();
                warning.setName("积极分子思想汇报提交预警");
                warning.setDate(warnStartTime);
                warning.setContinueDay("0");//持续时间
                warning.setRepeatCycle("4");//周期
                warning.setIsAlert("1");//使用弹窗
                warning.setAlertDegree("1");//紧急程度
                warning.setRemind("20");//重复提醒
                //设置创建人，修改人
                User creatUser = new User();
                creatUser.setId("1");
                Office office = new Office();
                office.setId("1");
                creatUser.setOffice(office);
                warning.setCreateBy(creatUser);
                warning.setUpdateBy(creatUser);
                warning.setAlertContent("请根据规定按时提交思想汇报");
                StringBuffer receivePerName = null;
                StringBuffer receivePerId = null;
                for(AffairActivist affairActivist : affairActivistList){
                    String name = affairActivist.getName();
                    String userId  = userDao.getIdByNo(affairActivist.getIdNumber());
                    //设置预警接受人员
                    if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(userId)){
                        if(receivePerName==null){
                            receivePerName= new StringBuffer(name);
                        }else{
                            receivePerName.append(","+name);
                        }
                        if(receivePerId==null){
                            receivePerId= new StringBuffer(userId);
                        }else{
                            receivePerId.append(","+userId);
                        }
                    }
                }
                warning.setReceivePerName(receivePerName.toString());
                warning.setReceivePerId(receivePerId.toString());
                warningService.save(warning);
            }
            }
        }catch (Exception e){
            log.error("每季度提醒积极分子提交思想汇报预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }


    //判断当前距离党组织换届时间是否只剩六个月，是 返回 true  否 返回 false
    public static boolean isDiffSixMonth(Date nowDate, Date hjDate) {

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(nowDate);
        Calendar hjCalendar = Calendar.getInstance();
        hjCalendar.setTime(hjDate);
        int diffMonth = hjCalendar.get(Calendar.MONTH)
                - nowCalendar.get(Calendar.MONTH);
        int diffYear = hjCalendar.get(Calendar.YEAR)
                - nowCalendar.get(Calendar.YEAR);
        if(hjCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR) ){
            if(hjCalendar.get(Calendar.MONTH) - nowCalendar.get(Calendar.MONTH)==6 && hjCalendar.get(Calendar.DAY_OF_MONTH) == nowCalendar.get(Calendar.DAY_OF_MONTH))
            return true;

        }else{
            if(diffYear>0 && (diffYear * 12 + diffMonth)==6 && hjCalendar.get(Calendar.DAY_OF_MONTH) == nowCalendar.get(Calendar.DAY_OF_MONTH)){
                return true;
            }
        }
        return false;
    }


    //查询是否达到退休标准
    public boolean isReachTX(PersonnelBase personnelBase) {
        Date birthday = personnelBase.getBirthday();
        String sex = personnelBase.getSex();
        if("1".equals(sex)){
            //男
            Calendar nowC = Calendar.getInstance();//当前日期
            Calendar birthC = Calendar.getInstance();
            birthC.setTime(birthday);//出生日期
            birthC.add(Calendar.MONTH,-2);//出生年月往前推两月
            if(nowC.get(Calendar.YEAR)-birthC.get(Calendar.YEAR)==60
                    && nowC.get(Calendar.MONTH) == birthC.get(Calendar.MONTH)
                    && nowC.get(Calendar.DAY_OF_MONTH) == birthC.get(Calendar.DAY_OF_MONTH)){
                return true;
            }
        }
        if("2".equals(sex)){
            //女
            Calendar nowC = Calendar.getInstance();//当前日期
            Calendar birthC = Calendar.getInstance();
            birthC.setTime(birthday);//出生日期
            birthC.add(Calendar.MONTH,-2);//出生年月往前推两月
            //获取职务层次
            PersonnelJob personnelJob = new PersonnelJob();
            personnelJob.setIdNumber(personnelBase.getIdNumber());
            List<PersonnelJob> jobList = personnelJobService.findList(personnelJob);
            PersonnelJob job = null;
            if (jobList!=null && jobList.size()>0){
                job=jobList.get(0);
            }else {
                job = new PersonnelJob();
                job.setJobLevel("0");
            }
            int jobLevel = 0;//默认为0
            /*  17、28 厅局副职 18、29 县处级副职 19、30 县处级正职
             *  副处级（具体分3中具体情形）以上女民警在55岁时需要组干备注是否退休，填写否的应60岁时再次提醒
             * */
            if(job.getJobLevel()!=null && !job.getJobLevel().isEmpty()){
                jobLevel = Integer.parseInt(job.getJobLevel());//职级
            }
                //副处级及以上
            if(nowC.get(Calendar.YEAR)-birthC.get(Calendar.YEAR)==60
                        && nowC.get(Calendar.MONTH) == birthC.get(Calendar.MONTH)
                        && nowC.get(Calendar.DAY_OF_MONTH) == birthC.get(Calendar.DAY_OF_MONTH)){
                //60岁
                if(jobLevel==17||jobLevel==28 || jobLevel == 18 || jobLevel == 29 || jobLevel == 19 || jobLevel == 30) {
                    //副处级（具体分3中具体情形）以上女民警在55岁时需要组干备注是否退休，填写否的应60岁时再次提醒
                   // System.out.println("副处级及以上");
                    int status = Integer.valueOf(personnelBase.getStatus());
                    if (status>6 && status!=19) {
                        //不在职状态
                        return false;
                    }else{
                        return true;
                    }
                }
            }
            if(nowC.get(Calendar.YEAR)-birthC.get(Calendar.YEAR)==55 && nowC.get(Calendar.MONTH) == birthC.get(Calendar.MONTH) && nowC.get(Calendar.DAY_OF_MONTH) == birthC.get(Calendar.DAY_OF_MONTH)){
                //55岁
                int status = Integer.valueOf(personnelBase.getStatus());
                if (status>6 && status!=19) {
                    //不在职状态
                    return false;
                }else{
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 下次预警时间字段、是否处理字段定时维护
     * @return
     */
    @Transactional(readOnly = false)
    public void nextRepeatTimeTask() {
        List<WarnReceive> list = warnReceiveDao.findAll();
        if(list != null && list.size() >0){
            for (WarnReceive wr : list) {
                /**
                 * 重复周期1:每周 2:每月 3:每年 4:永不
                 * 查出所有关联表的数据
                 * 1、预警已处理（is_handel为1）
                 *      更新next_repeat_time为null，is_handel为0
                 * 2、预警没处理（is_handel为0）
                 *  （1）、next_repeat_time为null，且当前时间大于预警时间，更新next_repeat_time字段
                 *  （2）、如果next_repeat_time字段有值，且next_repeat_time小于当前时间，更新next_repeat_time字段
                 *
                 */
                if("1".equals(wr.getIsHandle())){
                    //更新next_repeat_time字段为null、更新is_handle字段为0
                    warnReceiveDao.taskHandelById(wr.getId());
                }else{
                    if (wr.getNextRepeatTime() == null) {
                        if ("1".equals(wr.getRepeatCycle())) {
                            Date today = new Date();
                            Calendar c = Calendar.getInstance();
                            c.setTime(today);
                            //周日：1 周一：2 周三：2 ......周六：7
                            Integer weekday = c.get(Calendar.DAY_OF_WEEK);
                            if (weekday.toString().equals(wr.getWeek())) {
                                //处理提醒时间
                                String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
                                String handleStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
                                Date handleDate = DateUtils.parseDate(handleStr);
                                Date date = TimeUtils.setSecondMILLISECOND(new Date());
                                if (handleDate.before(date)) {
                                    Date nextDate = new Date(handleDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
                                    warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
                                }
                            }
                        } else if ("2".equals(wr.getRepeatCycle())) {
                            if (Integer.valueOf(wr.getDay()) <= 28) {//29 30 31号无效
                                //处理提醒时间
                                String nowDateStr = DateUtils.getDate("yyyy-MM");
                                String handleStr = nowDateStr + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
                                Date handleDate = DateUtils.parseDate(handleStr);
                                Date date = TimeUtils.setSecondMILLISECOND(new Date());
                                if (handleDate.before(date)) {
                                    Date nextDate = new Date(handleDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
                                    warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
                                }
                            }
                        } else if ("3".equals(wr.getRepeatCycle())) {
                            String handleStr = DateUtils.getYear() + "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
                            Date handleDate = DateUtils.parseDate(handleStr);
                            Date date = TimeUtils.setSecondMILLISECOND(new Date());
                            if (handleDate.before(date)) {
                                Date nextDate = new Date(handleDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
                                warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
                            }
                        }else{
                            Date date = TimeUtils.setSecondMILLISECOND(new Date());
                            if (wr.getDate().before(date)) {
                                Date nextDate = new Date(wr.getDate().getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
                                warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
                            }
                        }
                    }else{
                        if ("1".equals(wr.getRepeatCycle())) {
                            long time = wr.getNextRepeatTime().getTime();
                            String dateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm");
                            Date date = DateUtils.parseDate(dateStr);
                            if (wr.getNextRepeatTime().before(date)) {
                                Date nextDate = new Date(time + Long.parseLong(wr.getRemind()) * 1000 * 60);
                                warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
                            }
                        } else if ("2".equals(wr.getRepeatCycle())) {
                            if (Integer.valueOf(wr.getDay()) <= 28) {
                                long time = wr.getNextRepeatTime().getTime();
                                String dateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm");
                                Date date = DateUtils.parseDate(dateStr);
                                if (wr.getNextRepeatTime().before(date)) {
                                    Date nextDate = new Date(time + Long.parseLong(wr.getRemind()) * 1000 * 60);
                                }
                            }
                        } else if ("3".equals(wr.getRepeatCycle())) {
                            long time = wr.getNextRepeatTime().getTime();
                            String dateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm");
                            Date date = DateUtils.parseDate(dateStr);
                            if (wr.getNextRepeatTime().before(date)) {
                                Date nextDate = new Date(time + Long.parseLong(wr.getRemind()) * 1000 * 60);
                                warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
                            }
                        }else{
                            long time = wr.getDate().getTime();
                            String dateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm");
                            Date date = DateUtils.parseDate(dateStr);
                            if (wr.getDate().before(date)) {
                                Date nextDate = new Date(time + Long.parseLong(wr.getRemind()) * 1000 * 60);
                                warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 需求：每年的11月1号上午十点提醒所有账号休年假
     * 提前5分钟做以下准备:
     * 删除去年的数据，从用户表抓取用户数据插入warn_receive表（考虑每年会新增或删除用户，保证每年通知时用户数量一致）
     */
    private void nJTask() {
       List<Map<String, String>> list = userDao.getAllUser();
        warnReceiveService.deleteByWarnId("64b36e323ba64908b47961a9fbc28373");
        for (Map<String, String> m: list) {
            WarnReceive warnReceive = new WarnReceive();
            warnReceive.setWarnId("64b36e323ba64908b47961a9fbc28373");
            warnReceive.setPersonId(m.get("id"));
            warnReceive.setPersonName(m.get("name"));
            //管理员admin
            User user = new User();
            user.setId("1");
            Office office = new Office();
            office.setId("1");
            user.setOffice(office);
            warnReceive.setCreateBy(user);
            warnReceive.setUpdateBy(user);
            warnReceiveService.save(warnReceive);
        }
    }

    /**
     * 三会一课需求：
     *
     *      三会一课未完成的，进行提醒。
     *          党总支党员大会每半年至少召开1次；
     *          党支部党员大会每季度至少召开1次；
     *          党支部（总支）委员会每月至少召开1次会议；
     *          党小组，每月至少召开1次党小组会；
     *          党总支每半年至少上1次党课；
     *          党支部每季度至少上1次党课；
     *          党支部（总支）委员会每年至少召开1次）
     */

    @Scheduled(cron = "0 0 1 25 * ?")
    public void monthThreeMeetOneTasks(){
        log.info("每月执行的定时任務"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss")+"开始");
        //党支部党员大会每季度至少召开1次
        dzbThreeMeetOneClassTask();
        //党支部（总支）委员会每月至少召开1次会议；
        dzbwyhdkThreeMeetOneClassTask();
        //党小组，每月至少召开1次党小组会；
        dxzThreeMeetOneClassTask();
        //党支部每季度至少上1次党课；
        dzbdkThreeMeetOneClassTask();
        log.info("每季度执行的定时任務"+DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss")+"结束");
    }
    /**
     *
     * 党支部党员大会每季度至少召开1次
     *
     * 每月25号1点  0 0 1 25 * ?
     */
    public void dzbThreeMeetOneClassTask(){
        try {
            String month = DateUtils.getMonth();
            String day = DateUtils.getDay();
            String type = "1";
            if("03".equals(month) && "25".equals(day)) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                Date endDate = df.parse(df.format(new Date()));
                Date startDate = df.parse(year + "-" + "01-01");
                //查询所有的党支部
                List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
                for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                    String partyOrganization = affairGeneralSituation.getPartyOrganization();
                    String partyOrganizationId = affairGeneralSituation.getId();
                    List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate,endDate,partyOrganization,type);
                    if(affairYearThreeOneList == null || affairYearThreeOneList.size() == 0){
                        Warning warning = new Warning();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY,8);
                        calendar1.set(Calendar.MINUTE,0);
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);
                        Date warnStartTime = calendar1.getTime();
                        warning.setName("党支部党员大会预警");
                        warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                        warning.setDate(warnStartTime);
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertContent("请按时召开党支部党员大会");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("5");
                        List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                        StringBuffer receivePerName = null;
                        StringBuffer receivePerId = null;
                        if(userByPartyId.size() > 0 && userByPartyId != null) {
                            for (User user : userByPartyId) {
                                //设置预警接受人员
                                if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                    if(receivePerName==null){
                                        receivePerName= new StringBuffer(user.getName());
                                    }else{
                                        receivePerName.append(","+user.getName());
                                    }
                                    if(receivePerId==null){
                                        receivePerId= new StringBuffer(user.getId());
                                    }else{
                                        receivePerId.append(","+user.getId());
                                    }
                                }
                            }
                        }
                        if(receivePerName == null) {
                            warning.setReceivePerName("");
                        }else{
                            warning.setReceivePerName(receivePerName.toString());
                        }
                        if(receivePerId == null) {
                            warning.setReceivePerId("");
                        }else{
                            warning.setReceivePerId(receivePerId.toString());
                        }
                        warningService.save(warning);
                    }
                }
            }else if("06".equals(month) && "25".equals(day)){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                Date endDate = df.parse(df.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                Date startDate = df.parse(year +"-"+ "04-01");
                //查询所有的党支部
                List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
                for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                    String partyOrganization = affairGeneralSituation.getPartyOrganization();
                    String partyOrganizationId = affairGeneralSituation.getId();
                    List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization,type);
                    if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                        Warning warning = new Warning();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY,8);
                        calendar1.set(Calendar.MINUTE,0);
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);
                        Date warnStartTime = calendar1.getTime();
                        warning.setName("党支部党员大会预警");
                        warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                        warning.setDate(warnStartTime);
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertContent("请按时召开党支部党员大会");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("5");
                        List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                        StringBuffer receivePerName = null;
                        StringBuffer receivePerId = null;
                        if(userByPartyId.size() > 0 && userByPartyId != null) {
                            for (User user : userByPartyId) {
                                //设置预警接受人员
                                if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                    if(receivePerName==null){
                                        receivePerName= new StringBuffer(user.getName());
                                    }else{
                                        receivePerName.append(","+user.getName());
                                    }
                                    if(receivePerId==null){
                                        receivePerId= new StringBuffer(user.getId());
                                    }else{
                                        receivePerId.append(","+user.getId());
                                    }
                                }
                            }
                        }
                        if(receivePerName == null) {
                            warning.setReceivePerName("");
                        }else{
                            warning.setReceivePerName(receivePerName.toString());
                        }
                        if(receivePerId == null) {
                            warning.setReceivePerId("");
                        }else{
                            warning.setReceivePerId(receivePerId.toString());
                        }
                        warningService.save(warning);
                    }
                }
            }else if("09".equals(month) && "25".equals(day)){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                Date endDate = df.parse(df.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                Date startDate = df.parse(year +"-"+ "07-01");
                //查询所有的党支部
                List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
                for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                    String partyOrganization = affairGeneralSituation.getPartyOrganization();
                    String partyOrganizationId = affairGeneralSituation.getId();
                    List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization,type);
                    if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                        Warning warning = new Warning();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY,8);
                        calendar1.set(Calendar.MINUTE,0);
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);
                        Date warnStartTime = calendar1.getTime();
                        warning.setName("党支部党员大会预警");
                        warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                        warning.setDate(warnStartTime);
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertContent("请按时召开党支部党员大会");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("5");
                        List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                        StringBuffer receivePerName = null;
                        StringBuffer receivePerId = null;
                        if(userByPartyId.size() > 0 && userByPartyId != null) {
                            for (User user : userByPartyId) {
                                //设置预警接受人员
                                if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                    if(receivePerName==null){
                                        receivePerName= new StringBuffer(user.getName());
                                    }else{
                                        receivePerName.append(","+user.getName());
                                    }
                                    if(receivePerId==null){
                                        receivePerId= new StringBuffer(user.getId());
                                    }else{
                                        receivePerId.append(","+user.getId());
                                    }
                                }
                            }
                        }
                        if(receivePerName == null) {
                            warning.setReceivePerName("");
                        }else{
                            warning.setReceivePerName(receivePerName.toString());
                        }
                        if(receivePerId == null) {
                            warning.setReceivePerId("");
                        }else{
                            warning.setReceivePerId(receivePerId.toString());
                        }
                        warningService.save(warning);
                    }
                }

            }else if("12".equals(month) && "25".equals(day)){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                Date endDate = df.parse(df.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                Date startDate = df.parse(year +"-"+ "10-01");
                //查询所有的党支部
                List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
                for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                    String partyOrganization = affairGeneralSituation.getPartyOrganization();
                    String partyOrganizationId = affairGeneralSituation.getId();
                    List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization,type);
                    if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                        Warning warning = new Warning();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY,8);
                        calendar1.set(Calendar.MINUTE,0);
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);
                        Date warnStartTime = calendar1.getTime();
                        warning.setName("党支部党员大会预警");
                        warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                        warning.setDate(warnStartTime);
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertContent("请按时召开党支部党员大会");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("5");
                        List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                        StringBuffer receivePerName = null;
                        StringBuffer receivePerId = null;
                        if(userByPartyId.size() > 0 && userByPartyId != null) {
                            for (User user : userByPartyId) {
                                //设置预警接受人员
                                if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                    if(receivePerName==null){
                                        receivePerName= new StringBuffer(user.getName());
                                    }else{
                                        receivePerName.append(","+user.getName());
                                    }
                                    if(receivePerId==null){
                                        receivePerId= new StringBuffer(user.getId());
                                    }else{
                                        receivePerId.append(","+user.getId());
                                    }
                                }
                            }
                        }
                        if(receivePerName == null) {
                            warning.setReceivePerName("");
                        }else{
                            warning.setReceivePerName(receivePerName.toString());
                        }
                        if(receivePerId == null) {
                            warning.setReceivePerId("");
                        }else{
                            warning.setReceivePerId(receivePerId.toString());
                        }
                        warningService.save(warning);
                    }
                }
            }
        }catch (Exception e){
            //党支部党员大会每季度至少召开1次
            log.error("每季度提醒召开党支部党员大会预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }

    }

    /**
     *
     * 党支部（总支）委员会每月至少召开1次会议；
     *
     * 每月25号1点  0 0 1 25 * ?
     */
    public void dzbwyhdkThreeMeetOneClassTask(){
        try {
            String type = "3";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date endDate = df.parse(df.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int mon = calendar.get(Calendar.MONTH);
            String month = "";
            if(Integer.toString((mon + 1)).length() < 2){
                month = "0" + (mon + 1);
            }
            Date startDate = df.parse(year+"-"+month+"-"+ "01");
            //查询所有的党支部
            List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllParty();
            for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                String partyOrganization = affairGeneralSituation.getPartyOrganization();
                String partyOrganizationId = affairGeneralSituation.getId();
                List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization, type);
                if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                    Warning warning = new Warning();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR_OF_DAY, 8);
                    calendar1.set(Calendar.MINUTE, 0);
                    calendar1.set(Calendar.SECOND, 0);
                    calendar1.set(Calendar.MILLISECOND, 0);
                    Date warnStartTime = calendar1.getTime();
                    warning.setName("党支部（总支）委员会预警");
                    warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                    warning.setDate(warnStartTime);
                    warning.setRemind("20");
                    warning.setIsAlert("1");
                    warning.setAlertContent("请按时召开党支部（总支）委员会");
                    warning.setAlertDegree("1");
                    warning.setContinueDay("5");
                    List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                    StringBuffer receivePerName = null;
                    StringBuffer receivePerId = null;
                    if (userByPartyId.size() > 0 && userByPartyId != null) {
                        for (User user : userByPartyId) {
                            //设置预警接受人员
                            if (StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())) {
                                if (receivePerName == null) {
                                    receivePerName = new StringBuffer(user.getName());
                                } else {
                                    receivePerName.append("," + user.getName());
                                }
                                if (receivePerId == null) {
                                    receivePerId = new StringBuffer(user.getId());
                                } else {
                                    receivePerId.append("," + user.getId());
                                }
                            }
                        }
                    }
                    if(receivePerName == null) {
                        warning.setReceivePerName("");
                    }else{
                        warning.setReceivePerName(receivePerName.toString());
                    }
                    if(receivePerId == null) {
                        warning.setReceivePerId("");
                    }else{
                        warning.setReceivePerId(receivePerId.toString());
                    }
                    warningService.save(warning);
                }
            }
        }catch (Exception e){
            //党支部（总支）委员会每月至少召开1次会议
            log.error("每月提醒召开党支部（总支）委员会预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }

    /**
     * 党小组，每月至少召开1次党小组会；
     *
     * 每月25号1点  0 0 1 25 * ?
     */
    public void dxzThreeMeetOneClassTask(){
        try {
            String type = "4";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date endDate = df.parse(df.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int mon = calendar.get(Calendar.MONTH);
            String month = "";
            if(Integer.toString((mon + 1)).length() < 2){
                month = "0" + (mon + 1);
            }
            Date startDate = df.parse(year+"-"+month+"-"+ "01");
            //获取所有的党小组
            List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartydxz();
            for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                String partyOrganization = affairGeneralSituation.getPartyOrganization();
                String partyOrganizationId = affairGeneralSituation.getId();
                List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization, type);
                if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                    Warning warning = new Warning();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR_OF_DAY, 8);
                    calendar1.set(Calendar.MINUTE, 0);
                    calendar1.set(Calendar.SECOND, 0);
                    calendar1.set(Calendar.MILLISECOND, 0);
                    Date warnStartTime = calendar1.getTime();
                    warning.setName("党小组会议预警");
                    warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                    warning.setDate(warnStartTime);
                    warning.setRemind("20");
                    warning.setIsAlert("1");
                    warning.setAlertContent("请按时召开党小组会议");
                    warning.setAlertDegree("1");
                    warning.setContinueDay("5");
                    List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                    StringBuffer receivePerName = null;
                    StringBuffer receivePerId = null;
                    if (userByPartyId.size() > 0 && userByPartyId != null) {
                        for (User user : userByPartyId) {
                            //设置预警接受人员
                            if (StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())) {
                                if (receivePerName == null) {
                                    receivePerName = new StringBuffer(user.getName());
                                } else {
                                    receivePerName.append("," + user.getName());
                                }
                                if (receivePerId == null) {
                                    receivePerId = new StringBuffer(user.getId());
                                } else {
                                    receivePerId.append("," + user.getId());
                                }
                            }
                        }
                    }
                    if(receivePerName == null) {
                        warning.setReceivePerName("");
                    }else{
                        warning.setReceivePerName(receivePerName.toString());
                    }
                    if(receivePerId == null) {
                        warning.setReceivePerId("");
                    }else{
                        warning.setReceivePerId(receivePerId.toString());
                    }
                    warningService.save(warning);
                }
            }
        }catch (Exception e){
            //党小组，每月至少召开1次党小组会；
            log.error("每月提醒党小组召开党小组会预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }

    /**
     *
     * 党支部每季度至少上1次党课；
     *
     * 每月25号1点  0 0 1 25 * ?
     */
    public void dzbdkThreeMeetOneClassTask(){
        try {
            String month = DateUtils.getMonth();
            String day = DateUtils.getDay();
            String type = "5";
            if("03".equals(month) && "25".equals(day)) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                Date endDate = df.parse(df.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                Date startDate = df.parse(year +"-"+ "01-01");
                //查询所有的党支部
                List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
                for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                    String partyOrganization = affairGeneralSituation.getPartyOrganization();
                    String partyOrganizationId = affairGeneralSituation.getId();
                    List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate,endDate,partyOrganization,type);
                    if(affairYearThreeOneList == null || affairYearThreeOneList.size() == 0){
                        Warning warning = new Warning();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY,8);
                        calendar1.set(Calendar.MINUTE,0);
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);
                        Date warnStartTime = calendar1.getTime();
                        warning.setName("党支部党课预警");
                        warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                        warning.setDate(warnStartTime);
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertContent("请按时召开党支部党课");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("5");
                        List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                        StringBuffer receivePerName = null;
                        StringBuffer receivePerId = null;
                        if(userByPartyId.size() > 0 && userByPartyId != null) {
                            for (User user : userByPartyId) {
                                //设置预警接受人员
                                if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                    if(receivePerName==null){
                                        receivePerName= new StringBuffer(user.getName());
                                    }else{
                                        receivePerName.append(","+user.getName());
                                    }
                                    if(receivePerId==null){
                                        receivePerId= new StringBuffer(user.getId());
                                    }else{
                                        receivePerId.append(","+user.getId());
                                    }
                                }
                            }
                        }
                        if(receivePerName == null) {
                            warning.setReceivePerName("");
                        }else{
                            warning.setReceivePerName(receivePerName.toString());
                        }
                        if(receivePerId == null) {
                            warning.setReceivePerId("");
                        }else{
                            warning.setReceivePerId(receivePerId.toString());
                        }
                        warningService.save(warning);
                    }
                }
            }else if("06".equals(month) && "25".equals(day)){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                Date endDate = df.parse(df.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                Date startDate = df.parse(year +"-"+ "04-01");
                //查询所有的党支部
                List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
                for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                    String partyOrganization = affairGeneralSituation.getPartyOrganization();
                    String partyOrganizationId = affairGeneralSituation.getId();
                    List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization,type);
                    if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                        Warning warning = new Warning();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY,8);
                        calendar1.set(Calendar.MINUTE,0);
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);
                        Date warnStartTime = calendar1.getTime();
                        warning.setName("党支部党课预警");
                        warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                        warning.setDate(warnStartTime);
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertContent("请按时召开党支部党课");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("5");
                        List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                        StringBuffer receivePerName = null;
                        StringBuffer receivePerId = null;
                        if(userByPartyId.size() > 0 && userByPartyId != null) {
                            for (User user : userByPartyId) {
                                //设置预警接受人员
                                if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                    if(receivePerName==null){
                                        receivePerName= new StringBuffer(user.getName());
                                    }else{
                                        receivePerName.append(","+user.getName());
                                    }
                                    if(receivePerId==null){
                                        receivePerId= new StringBuffer(user.getId());
                                    }else{
                                        receivePerId.append(","+user.getId());
                                    }
                                }
                            }
                        }
                        if(receivePerName == null) {
                            warning.setReceivePerName("");
                        }else{
                            warning.setReceivePerName(receivePerName.toString());
                        }
                        if(receivePerId == null) {
                            warning.setReceivePerId("");
                        }else{
                            warning.setReceivePerId(receivePerId.toString());
                        }
                        warningService.save(warning);
                    }
                }
            }else if("09".equals(month) && "25".equals(day)){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                Date endDate = df.parse(df.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                Date startDate = df.parse(year +"-"+ "07-01");
                //查询所有的党支部
                List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
                for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                    String partyOrganization = affairGeneralSituation.getPartyOrganization();
                    String partyOrganizationId = affairGeneralSituation.getId();
                    List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization,type);
                    if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                        Warning warning = new Warning();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY,8);
                        calendar1.set(Calendar.MINUTE,0);
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);
                        Date warnStartTime = calendar1.getTime();
                        warning.setName("党支部党课预警");
                        warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                        warning.setDate(warnStartTime);
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertContent("请按时召开党支部党课");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("5");
                        List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                        StringBuffer receivePerName = null;
                        StringBuffer receivePerId = null;
                        if(userByPartyId.size() > 0 && userByPartyId != null) {
                            for (User user : userByPartyId) {
                                //设置预警接受人员
                                if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                    if(receivePerName==null){
                                        receivePerName= new StringBuffer(user.getName());
                                    }else{
                                        receivePerName.append(","+user.getName());
                                    }
                                    if(receivePerId==null){
                                        receivePerId= new StringBuffer(user.getId());
                                    }else{
                                        receivePerId.append(","+user.getId());
                                    }
                                }
                            }
                        }
                        if(receivePerName == null) {
                            warning.setReceivePerName("");
                        }else{
                            warning.setReceivePerName(receivePerName.toString());
                        }
                        if(receivePerId == null) {
                            warning.setReceivePerId("");
                        }else{
                            warning.setReceivePerId(receivePerId.toString());
                        }
                        warningService.save(warning);
                    }
                }

            }else if("12".equals(month) && "25".equals(day)){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                Date endDate = df.parse(df.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                Date startDate = df.parse(year +"-"+ "10-01");
                //查询所有的党支部
                List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
                for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                    String partyOrganization = affairGeneralSituation.getPartyOrganization();
                    String partyOrganizationId = affairGeneralSituation.getId();
                    List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization,type);
                    if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                        Warning warning = new Warning();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR_OF_DAY,8);
                        calendar1.set(Calendar.MINUTE,0);
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);
                        Date warnStartTime = calendar1.getTime();
                        warning.setName("党支部党课预警");
                        warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                        warning.setDate(warnStartTime);
                        warning.setRemind("20");
                        warning.setIsAlert("1");
                        warning.setAlertContent("请按时召开党支部党课");
                        warning.setAlertDegree("1");
                        warning.setContinueDay("5");
                        List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                        StringBuffer receivePerName = null;
                        StringBuffer receivePerId = null;
                        if(userByPartyId.size() > 0 && userByPartyId != null) {
                            for (User user : userByPartyId) {
                                //设置预警接受人员
                                if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                    if(receivePerName==null){
                                        receivePerName= new StringBuffer(user.getName());
                                    }else{
                                        receivePerName.append(","+user.getName());
                                    }
                                    if(receivePerId==null){
                                        receivePerId= new StringBuffer(user.getId());
                                    }else{
                                        receivePerId.append(","+user.getId());
                                    }
                                }
                            }
                        }
                        if(receivePerName == null) {
                            warning.setReceivePerName("");
                        }else{
                            warning.setReceivePerName(receivePerName.toString());
                        }
                        if(receivePerId == null) {
                            warning.setReceivePerId("");
                        }else{
                            warning.setReceivePerId(receivePerId.toString());
                        }
                        warningService.save(warning);
                    }
                }
            }
        }catch (Exception e){
            //党支部每季度至少上1次党课
            log.error("每季度提醒党支部上党课预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }

    /**
     *  党总支党员大会每半年至少召开1次；
     *
     *  每年6月25日8时执行
     */
    @Scheduled(cron = "0 0 1 25 6 ?")
    public void dzzThreeMeetOneClassTask(){
        try {
            /*//查询所有的党支部
            List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllPartyBranch();
            for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                String partyOrganization = affairGeneralSituation.getPartyOrganization();
            }*/
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date endDate = df.parse(df.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            Date startDate = df.parse((year - 1) +"-"+"12-26");
            String type = "1";
            //查询党总支的党支部
            List<AffairGeneralSituation> selectAlldzzList = affairGeneralSituationService.selectAlldzz();
            for (AffairGeneralSituation affairGeneralSituation : selectAlldzzList) {
                String partyOrganization = affairGeneralSituation.getPartyOrganization();
                String partyOrganizationId = affairGeneralSituation.getId();
                List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate,endDate,partyOrganization,type);
                //查询userId
                if(affairYearThreeOneList == null || affairYearThreeOneList.size() == 0){
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR_OF_DAY,8);
                    calendar1.set(Calendar.MINUTE,0);
                    calendar1.set(Calendar.SECOND,0);
                    calendar1.set(Calendar.MILLISECOND,0);
                    Date warnStartTime = calendar1.getTime();
                    Warning warning = new Warning();
                    warning.setName("党总支党员大会预警");
                    warning.setRepeatCycle("4");
                    warning.setDate(warnStartTime);
                    warning.setRemind("20");
                    warning.setIsAlert("1");
                    warning.setAlertContent("请按时召开党总支党员大会");
                    warning.setAlertDegree("1");
                    warning.setContinueDay("5");
                    List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                    StringBuffer receivePerName = null;
                    StringBuffer receivePerId = null;
                    if(userByPartyId.size() > 0 && userByPartyId != null) {
                        for (User user : userByPartyId) {
                            //设置预警接受人员
                            if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                if(receivePerName==null){
                                    receivePerName= new StringBuffer(user.getName());
                                }else{
                                    receivePerName.append(","+user.getName());
                                }
                                if(receivePerId==null){
                                    receivePerId= new StringBuffer(user.getId());
                                }else{
                                    receivePerId.append(","+user.getId());
                                }
                            }
                        }
                    }
                    if(receivePerName == null) {
                        warning.setReceivePerName("");
                    }else{
                        warning.setReceivePerName(receivePerName.toString());
                    }
                    if(receivePerId == null) {
                        warning.setReceivePerId("");
                    }else{
                        warning.setReceivePerId(receivePerId.toString());
                    }
                    warningService.save(warning);
                }
            }
        }catch (Exception e){
            //党总支党员大会每半年至少召开1次
            log.error("每半年提醒召开党总支党员大会预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }

    /**
     *  党总支党员大会每半年至少召开1次；
     *  每年12月25日8时执行
     */
    @Scheduled(cron = "0 0 1 25 12 ?")
    public void dzzThreeMeetOneClassTaskTwo(){
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date endDate = df.parse(df.format(new Date()));
            String type = "1";
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            Date startDate = df.parse(year +"-"+ "06-26");
            //查询党总支的党支部
            List<AffairGeneralSituation> selectAlldzzList = affairGeneralSituationService.selectAlldzz();
            for (AffairGeneralSituation affairGeneralSituation : selectAlldzzList) {
                String partyOrganization = affairGeneralSituation.getPartyOrganization();
                String partyOrganizationId = affairGeneralSituation.getId();
                List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate,endDate,partyOrganization,type);
                //查询userId
                if(affairYearThreeOneList == null || affairYearThreeOneList.size() == 0){
                    Warning warning = new Warning();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR_OF_DAY,8);
                    calendar1.set(Calendar.MINUTE,0);
                    calendar1.set(Calendar.SECOND,0);
                    calendar1.set(Calendar.MILLISECOND,0);
                    Date warnStartTime = calendar1.getTime();
                    warning.setName("党总支党员大会预警");
                    warning.setRepeatCycle("4");
                    warning.setDate(warnStartTime);
                    warning.setRemind("20");
                    warning.setIsAlert("1");
                    warning.setAlertContent("请按时召开党总支党员大会");
                    warning.setAlertDegree("1");
                    warning.setContinueDay("5");
                    List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                    StringBuffer receivePerName = null;
                    StringBuffer receivePerId = null;
                    if(userByPartyId.size() > 0 && userByPartyId != null) {
                        for (User user : userByPartyId) {
                            //设置预警接受人员
                            if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                if(receivePerName==null){
                                    receivePerName= new StringBuffer(user.getName());
                                }else{
                                    receivePerName.append(","+user.getName());
                                }
                                if(receivePerId==null){
                                    receivePerId= new StringBuffer(user.getId());
                                }else{
                                    receivePerId.append(","+user.getId());
                                }
                            }
                        }
                    }
                    if(receivePerName == null) {
                        warning.setReceivePerName("");
                    }else{
                        warning.setReceivePerName(receivePerName.toString());
                    }
                    if(receivePerId == null) {
                        warning.setReceivePerId("");
                    }else{
                        warning.setReceivePerId(receivePerId.toString());
                    }
                    warningService.save(warning);
                }
            }
        }catch (Exception e){
            //党总支党员大会每半年至少召开1次
            log.error("每半年提醒召开党总支党员大会预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }

    /**
     * 党总支每半年至少上1次党课；
     *
     * 每年的6月25号1点执行
     */
    @Scheduled(cron = "0 0 1 25 6 ?")
    public void dzzdkThreeMeetOneClassTask(){
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date endDate = df.parse(df.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            Date startDate = df.parse((year - 1) +"-"+"12-26");
            String type = "5";
            //查询党总支的党支部
            List<AffairGeneralSituation> selectAlldzzList = affairGeneralSituationService.selectAlldzz();
            for (AffairGeneralSituation affairGeneralSituation : selectAlldzzList) {
                String partyOrganization = affairGeneralSituation.getPartyOrganization();
                String partyOrganizationId = affairGeneralSituation.getId();
                List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate,endDate,partyOrganization,type);
                if(affairYearThreeOneList == null || affairYearThreeOneList.size() == 0){
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR_OF_DAY,8);
                    calendar1.set(Calendar.MINUTE,0);
                    calendar1.set(Calendar.SECOND,0);
                    calendar1.set(Calendar.MILLISECOND,0);
                    Date warnStartTime = calendar1.getTime();
                    Warning warning = new Warning();
                    warning.setName("党总支党课预警");
                    warning.setRepeatCycle("4");
                    warning.setDate(warnStartTime);
                    warning.setRemind("20");
                    warning.setIsAlert("1");
                    warning.setAlertContent("请按时召开党总支党课");
                    warning.setAlertDegree("1");
                    warning.setContinueDay("5");
                    List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                    StringBuffer receivePerName = null;
                    StringBuffer receivePerId = null;
                    if(userByPartyId.size() > 0 && userByPartyId != null) {
                        for (User user : userByPartyId) {
                            //设置预警接受人员
                            if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                if(receivePerName==null){
                                    receivePerName= new StringBuffer(user.getName());
                                }else{
                                    receivePerName.append(","+user.getName());
                                }
                                if(receivePerId==null){
                                    receivePerId= new StringBuffer(user.getId());
                                }else{
                                    receivePerId.append(","+user.getId());
                                }
                            }
                        }
                    }
                    if(receivePerName == null) {
                        warning.setReceivePerName("");
                    }else{
                        warning.setReceivePerName(receivePerName.toString());
                    }
                    if(receivePerId == null) {
                        warning.setReceivePerId("");
                    }else{
                        warning.setReceivePerId(receivePerId.toString());
                    }
                    warningService.save(warning);
                }
            }
        }catch (Exception e){
            //党总支党员大会每半年至少召开1次
            log.error("每半年提醒党总支上党课预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }

    /**
     *  党总支每半年至少上1次党课；
     *  每年12月25日1时执行
     */
    @Scheduled(cron = "0 0 1 25 12 ?")
    public void dzzdkThreeMeetOneClassTaskTwo(){
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date endDate = df.parse(df.format(new Date()));
            String type = "5";
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            Date startDate = df.parse(year +"-"+ "06-26");
            //查询党总支的党支部
            List<AffairGeneralSituation> selectAlldzzList = affairGeneralSituationService.selectAlldzz();
            for (AffairGeneralSituation affairGeneralSituation : selectAlldzzList) {
                String partyOrganization = affairGeneralSituation.getPartyOrganization();
                String partyOrganizationId = affairGeneralSituation.getId();
                List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate,endDate,partyOrganization,type);
                if(affairYearThreeOneList == null || affairYearThreeOneList.size() == 0){
                    Warning warning = new Warning();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR_OF_DAY,8);
                    calendar1.set(Calendar.MINUTE,0);
                    calendar1.set(Calendar.SECOND,0);
                    calendar1.set(Calendar.MILLISECOND,0);
                    Date warnStartTime = calendar1.getTime();
                    warning.setName("党总支党课预警");
                    warning.setRepeatCycle("4");
                    warning.setDate(warnStartTime);
                    warning.setRemind("20");
                    warning.setIsAlert("1");
                    warning.setAlertContent("请按时召开党总支党课");
                    warning.setAlertDegree("1");
                    warning.setContinueDay("5");
                    List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                    StringBuffer receivePerName = null;
                    StringBuffer receivePerId = null;
                    if(userByPartyId.size() > 0 && userByPartyId != null) {
                        for (User user : userByPartyId) {
                            //设置预警接受人员
                            if(StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())){
                                if(receivePerName==null){
                                    receivePerName= new StringBuffer(user.getName());
                                }else{
                                    receivePerName.append(","+user.getName());
                                }
                                if(receivePerId==null){
                                    receivePerId= new StringBuffer(user.getId());
                                }else{
                                    receivePerId.append(","+user.getId());
                                }
                            }
                        }
                    }
                    if(receivePerName == null) {
                        warning.setReceivePerName("");
                    }else{
                        warning.setReceivePerName(receivePerName.toString());
                    }
                    if(receivePerId == null) {
                        warning.setReceivePerId("");
                    }else{
                        warning.setReceivePerId(receivePerId.toString());
                    }
                    warningService.save(warning);
                }
            }
        }catch (Exception e){
            log.error("每半年提醒党总支上党课预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }

    /**
     *  党支部（总支）委员会每年至少召开1次
     *  每年的11月25号1点执行
     */
    @Scheduled(cron = "0 0 1 25 11 ?")
    public void dzbwyhThreeMeetOneTask(){
        try{
            String type = "3";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date endDate = df.parse(df.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            Date startDate = df.parse(year+"-"+"01"+"-"+ "01");
            //查询所有的党支部
            List<AffairGeneralSituation> affairGeneralSituationsList = affairGeneralSituationService.selectAllParty();
            for (AffairGeneralSituation affairGeneralSituation : affairGeneralSituationsList) {
                String partyOrganization = affairGeneralSituation.getPartyOrganization();
                String partyOrganizationId = affairGeneralSituation.getId();
                List<AffairYearThreeOne> affairYearThreeOneList = affairYearThreeOneService.selectdzzzyThreeOne(startDate, endDate, partyOrganization, type);
                if (affairYearThreeOneList == null || affairYearThreeOneList.size() == 0) {
                    Warning warning = new Warning();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR_OF_DAY, 8);
                    calendar1.set(Calendar.MINUTE, 0);
                    calendar1.set(Calendar.SECOND, 0);
                    calendar1.set(Calendar.MILLISECOND, 0);
                    Date warnStartTime = calendar1.getTime();
                    warning.setName("年度党支部（总支）委员会召开预警");
                    warning.setRepeatCycle("4");    // 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
                    warning.setDate(warnStartTime);
                    warning.setRemind("20");
                    warning.setIsAlert("1");
                    warning.setAlertContent("请按时召开年度党支部（总支）委员会");
                    warning.setAlertDegree("1");
                    warning.setContinueDay("5");
                    List<User> userByPartyId = userDao.getUserByPartyId(partyOrganizationId);
                    StringBuffer receivePerName = null;
                    StringBuffer receivePerId = null;
                    if (userByPartyId.size() > 0 && userByPartyId != null) {
                        for (User user : userByPartyId) {
                            //设置预警接受人员
                            if (StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getId())) {
                                if(receivePerName==null){
                                    receivePerName= new StringBuffer(user.getName());
                                }else{
                                    receivePerName.append(","+user.getName());
                                }
                                if(receivePerId==null){
                                    receivePerId= new StringBuffer(user.getId());
                                }else{
                                    receivePerId.append(","+user.getId());
                                }
                            }
                        }
                    }
                    if(receivePerName == null) {
                        warning.setReceivePerName("");
                    }else{
                        warning.setReceivePerName(receivePerName.toString());
                    }
                    if(receivePerId == null) {
                        warning.setReceivePerId("");
                    }else{
                        warning.setReceivePerId(receivePerId.toString());
                    }
                    warningService.save(warning);
                }
            }
        }catch (Exception e){
            //党支部（总支）委员会每年至少召开1次
            log.error("每年提醒召开党支部（总支）委员会预警定时任务发生错误，错误信息："+e.getCause());
            e.printStackTrace();
        }
    }

}
