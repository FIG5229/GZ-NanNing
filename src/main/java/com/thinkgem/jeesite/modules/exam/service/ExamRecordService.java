package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.*;
import com.thinkgem.jeesite.modules.exam.entity.*;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 考评档案service
 *
 * @author kevin.jia,
 * @version 2020/10/20
 */
@Service
@Transactional(readOnly = true)
public class ExamRecordService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ExamCheckChildDao examCheckChildDao;

    @Autowired
    private ExamJcInfoDao examJcInfoDao;

    @Autowired
    private ExamWorkflowDefineDao examWorkflowDefineDao;

    @Autowired
    private ExamWorkflowDao examWorkflowDao;
    @Autowired
    private ExamWorkflowDatasDao examWorkflowDatasDao;
    @Autowired
    private ExamStandardBaseInfoService examStandardBaseInfoService;
    @Autowired
    private ExamStandardTemplateDataDao examStandardTemplateDataDao;
    @Autowired
    private ExamLdScoreDao examLdScoreDao;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ExamWeightsMainDao examWeightsMainDao;
    @Autowired
    private ExamWorkflowService examWorkflowService;

    @Autowired
    private ExamScoreWorkItemDao examScoreWorkItemDao;

    @Autowired
    private ExamWorkflowDatasService examWorkflowDatasService;

    @Autowired
    private ExamUnitAllPublicService examUnitAllPublicService;

    @Autowired
    private ExamUnitAllPublicYearService examUnitAllPublicYearService;


    /**
     * 查询单位/民警各个月份得分情况
     *
     * @param name
     * @param id
     * 当为人员时 id 人员身份证号  当为单位时 id 为 单位id
     */
    public Page<ExamRecord> findPersonScore(Page<ExamRecord> page, String name, String id) {
        List<String> workflowIds = examWorkflowDatasDao.findWorkflowIdByObjId(id); //根据人员id从工作流程业务表中查询出已完成的考评流程
        List<ExamWorkflow> examWorkflowList = new ArrayList<ExamWorkflow>();

        if (workflowIds.size() > 0) {
            examWorkflowList = examWorkflowDao.findInfosByIds(workflowIds);//根据上方查询出的考评流程ID,查询考评流程详细信息
        }
        Set<String> yearSet = new HashSet();
        List<Map<String, Object>> examCycleList = new ArrayList<>();// examWorkflow 对象集合
        List<ExamRecord> examRecordList = new ArrayList<>();
        Map<String,Object> yearWorkflowIdMap = new HashMap<>();
        if (examWorkflowList.size() > 0) {
            for (ExamWorkflow examWorkflow : examWorkflowList) {
                String time = examWorkflow.getTime();
                String year = time.substring(0, 4);
                Map<String, Object> tempMap = new HashMap<>();
                if("1".equals(examWorkflow.getExamCycle())){
                    //月度
                    String month = time.substring(4, 6);
                    int month_int = Integer.parseInt(month);
                    //List<ExamWorkflowDatas> examWorkflowDataList = examWorkflowDatasDao.findInfosByOIdWFId(id,examWorkflow.getId());
                    tempMap.put("month", month_int);
                }else if ("2".equals(examWorkflow.getExamCycle())){
                    //年度
                    String workflowId = examWorkflow.getId();
                    yearWorkflowIdMap.put("workflowId"+year,workflowId);
                }
                yearSet.add(year);
                tempMap.put("year", year);
                tempMap.put("examType", examWorkflow.getExamType());
                List<ExamWorkflowDatas> examWorkflowDataList = examWorkflowDatasDao.findInfosByOIdWFId(id, examWorkflow.getId());
                tempMap.put("examWorkflowDataList", examWorkflowDataList);
                examCycleList.add(tempMap);

            }
            List<String> yearList = new ArrayList<>(yearSet);
            Collections.sort(yearList);
            for (String year : yearList) {
                //根据年度生成相应考评档案对象
                ExamRecord record = new ExamRecord();
                record.setYear(year);
                String workflowId = (String) yearWorkflowIdMap.get("workflowId"+year);
                if(workflowId!=null){
                    ExamLdScore examLdScore = examLdScoreDao.findGradesByWorkflowIdIDnumber(id,workflowId);
                    record.setGrades(examLdScore.getGrades());
                    try {
                        record.setYearSum(Double.valueOf(examLdScore.getSumScore()));
                        record.setYearEndSum(Double.valueOf(examLdScore.getSumScore()));
                    }catch (Exception e){
                        e.printStackTrace();
                        record.setYearSum(0.0);
                        record.setYearEndSum(0.0);
                    }

                }
                Map<String, Double> monthSumMap = new HashMap<>();
                for (Map<String, Object> map : examCycleList) {
                    if (map.get("year").equals(year)) {
                        if (map.get("month")!= null) {
                            String examType = (String) map.get("examType");
                            String basicScore = DictUtils.getDictValue(examType,"exam_basicScore","100");
                            List<ExamWorkflowDatas> examWorkflowDataList = (List<ExamWorkflowDatas>) map.get("examWorkflowDataList");
                            Double score = 100.0;
                            try {
                                score = Double.valueOf(basicScore);
                            }catch (Exception e){
                                logger.error(getExceptionInfo(e));
                                e.printStackTrace();
                                score = 100.0;
                            }
                            for (ExamWorkflowDatas examWorkflowDatas : examWorkflowDataList) {
                                if(examWorkflowDatas.getValue()!=null && examWorkflowDatas.getValueType()!= null){
                                    Double value = Double.valueOf((examWorkflowDatas.getValue()));//值
                                    Integer valueType = examWorkflowDatas.getValueType();//值类型 -1 扣分 1 加分
                                    if (valueType == 1) {
                                        score += value;
                                    }
                                    if (valueType == -1) {
                                        score -= value;
                                    }
                                }
                            }
                            monthSumMap.put(map.get("month").toString(), score);

                        }
                    }
                }
                record.setMonthSumMap(monthSumMap);
                examRecordList.add(record);
            }
        }
        int count = examRecordList.size();
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        if (workflowIds != null) {
            List<ExamRecord> pageList;
            if (pageSize * pageNo < count) {
                pageList = examRecordList.subList((pageNo - 1) * pageSize, pageSize * pageNo);
            } else {
                pageList = examRecordList.subList((pageNo - 1) * pageSize, count);
            }
            page.setCount(examRecordList.size());
            page.setList(pageList);
        }
        return page;
    }


    public Page<ExamRecord> findUnitScore(Page<ExamRecord> page, String name, String id,String unitType) {
        String userId;
        if(StringUtils.isNotBlank(unitType)){
            if("isNNC".equals(unitType) || "isLZC".equals(unitType) || "isBHC".equals(unitType)){
                //三个处
                userId = id;
            }else if ("isNNCpcs".equals(unitType) || "isLZCpcs".equals(unitType) || "isBHCpcs".equals(unitType)){
                //三个处下派出所
                userId = userDao.selectUserId(officeService.get(id).getCode());//根据单位代码userId
            }else{
               //三个处下派出所及局机关
                userId = userDao.selectUserId(officeService.get(id).getCode());//根据单位代码userId
            }
        }else{
            userId = userDao.selectUserId(officeService.get(id).getCode());//根据单位代码userId
        }

        List<String> workflowIds = examWorkflowDatasDao.findWorkflowIdByObjId(userId); //根据人员id从工作流程业务表中查询出已完成的考评流程
        List<ExamWorkflow> examWorkflowList = new ArrayList<ExamWorkflow>();

        if (workflowIds.size() > 0) {
            examWorkflowList = examWorkflowDao.findInfosByIds(workflowIds);//根据上方查询出的考评流程ID,查询考评流程详细信息

            Set<String> yearSet = new HashSet();
            List<Map<String, Object>> examCycleList = new ArrayList<>();// examWorkflow 对象集合
            List<ExamRecord> examRecordList = new ArrayList<>();
            Map<String, Object> yearWorkflowIdMap = new HashMap<>();
            if (examWorkflowList.size() > 0) {
                for (ExamWorkflow examWorkflow : examWorkflowList) {
                    String time = examWorkflow.getTime();
                    String year = time.substring(0, 4);
                    Map<String, Object> tempMap = new HashMap<>();
                    if ("1".equals(examWorkflow.getExamCycle())) {
                        //月度
                        String month = time.substring(4, 6);
                        int month_int = Integer.parseInt(month);
                        //List<ExamWorkflowDatas> examWorkflowDataList = examWorkflowDatasDao.findInfosByOIdWFId(id,examWorkflow.getId());
                        tempMap.put("month", month_int);
                    } else if ("2".equals(examWorkflow.getExamCycle())) {
                        //年度
                        String workflowId = examWorkflow.getId();
                        yearWorkflowIdMap.put("workflowId" + year, workflowId);
                    }
                    yearSet.add(year);
                    tempMap.put("year", year);
                    List<ExamWorkflowDatas> examWorkflowDataList = examWorkflowDatasDao.findInfosByOIdWFId(userId, examWorkflow.getId());
                    tempMap.put("examWorkflowDataList", examWorkflowDataList);
                    examCycleList.add(tempMap);

                }
                List<String> yearList = new ArrayList<>(yearSet);
                Collections.sort(yearList);
                for (String year : yearSet) {
                    //根据年度生成相应考评档案对象
                    ExamRecord record = new ExamRecord();
                    record.setYear(year);
                    String workflowId = (String) yearWorkflowIdMap.get("workflowId" + year);
                    Map<String, Double> monthSumMap = new HashMap<>();
                    Double yearSum = 0.0;
                    Double yearEndSum = 100.0;//年终
//                    Integer yearNum = Integer.valueOf(year);
                    List<ExamWorkflow> examWorkflowYearList = examWorkflowDao.findYearInfosByIds(workflowIds, year);
                    if (examWorkflowYearList != null && examWorkflowYearList.size() > 0) {
                        Double score = 100.0;
                        for (ExamWorkflow examWorkflow : examWorkflowList) {
                            List<ExamWorkflowDatas> examWorkflowDataList = examWorkflowDatasDao.findInfosByOIdWFId(id, examWorkflow.getId());
                            for (ExamWorkflowDatas examWorkflowDatas : examWorkflowDataList) {
                                if (examWorkflowDatas.getValue() != null && examWorkflowDatas.getValueType() != null) {
                                    Double value = Double.valueOf((examWorkflowDatas.getValue()));//值
                                    Integer valueType = examWorkflowDatas.getValueType();//值类型 -1 扣分 1 加分
                                    if (valueType == 1) {
                                        score += value;
                                    }
                                    if (valueType == -1) {
                                        score -= value;
                                    }
                                }
                            }

                        }
                        yearSum = score;
                    }
                    for (Map<String, Object> map : examCycleList) {
                        if (map.get("year").equals(year)) {
                            if (map.get("month") != null) {
                                List<ExamWorkflowDatas> examWorkflowDataList = (List<ExamWorkflowDatas>) map.get("examWorkflowDataList");
                                Double score = 100.0;
                                for (ExamWorkflowDatas examWorkflowDatas : examWorkflowDataList) {
                                    if (examWorkflowDatas.getValue() != null && examWorkflowDatas.getValueType() != null) {
                                        Double value = Double.valueOf((examWorkflowDatas.getValue()));//值
                                        Integer valueType = examWorkflowDatas.getValueType();//值类型 -1 扣分 1 加分
                                        if (valueType == 1) {
                                            score += value;
                                        }
                                        if (valueType == -1) {
                                            score -= value;
                                        }
                                    }
                                }
                                monthSumMap.put(map.get("month").toString(), score);

                            }
                       /* else {
                            List<ExamWorkflowDatas> examWorkflowDataList = (List<ExamWorkflowDatas>) map.get("examWorkflowDataList");

                            for (ExamWorkflowDatas examWorkflowDatas : examWorkflowDataList) {
                                if (examWorkflowDatas.getValue() != null && examWorkflowDatas.getValueType() != null) {
                                    int value = Integer.parseInt(examWorkflowDatas.getValue());//值
                                    Integer valueType = examWorkflowDatas.getValueType();//值类型 -1 扣分 1 加分
                                    if (valueType == 1) {
                                        yearEndSum += value;
                                    }
                                    if (valueType == -1) {
                                        yearEndSum -= value;
                                    }
                                }
                            }
                            yearSum += yearEndSum;
                        }*/
                        }
                    }
                    yearEndSum = yearSum;
                    record.setMonthSumMap(monthSumMap);
                    record.setYearEndSum(yearEndSum);
                    record.setYearSum(yearSum);
                    examRecordList.add(record);
                }
            }
            int count = examRecordList.size();
            int pageNo = page.getPageNo();
            int pageSize = page.getPageSize();
            if (workflowIds != null) {
                List<ExamRecord> pageList;
                if (pageSize * pageNo < count) {
                    pageList = examRecordList.subList((pageNo - 1) * pageSize, pageSize * pageNo);
                } else {
                    pageList = examRecordList.subList((pageNo - 1) * pageSize, count);
                }
                page.setCount(examRecordList.size());
                page.setList(pageList);
            }
        }

        return page;
    }

    public Page<ExamRecord> findUnitScore2(Page<ExamRecord> page, String name, String id,String unitType) {
        String userId;
        String  examType;//考评类别
        if(StringUtils.isNotBlank(unitType)){
            if("isNNC".equals(unitType) || "isLZC".equals(unitType) || "isBHC".equals(unitType)){
                //三个处
                userId = id;
                examType = "1";//局考处
            }else if ("isNNCpcs".equals(unitType) || "isLZCpcs".equals(unitType) || "isBHCpcs".equals(unitType)){
                //三个处下派出所
                examType = "3";//处考派出所
                userId = userDao.selectUserId(officeService.get(id).getCode());//根据单位代码userId
            }else{
                //三个处下派出所及局机关
                userId = userDao.selectUserId(officeService.get(id).getCode());//根据单位代码userId
                examType = "2_4";//局考局机关_处考处机关
            }
        }else{
            userId = userDao.selectUserId(officeService.get(id).getCode());//根据单位代码userId
            examType = "0";
        }

        List<String> workflowIds = examWorkflowDatasDao.findWorkflowIdByObjId(userId); //根据人员id从工作流程业务表中查询出已完成的考评流程

        if (workflowIds.size() > 0) {
            List<ExamWorkflow> examWorkflowList = examWorkflowDao.findInfosByIds(workflowIds);//根据上方查询出的考评流程ID,查询考评流程详细信息

            Set<String> yearSet = new HashSet();
            List<Map<String, Object>> examCycleList = new ArrayList<>();// examWorkflow 对象集合
            List<ExamRecord> examRecordList = new ArrayList<>();
            Map<String, Object> yearWorkflowIdMap = new HashMap<>();
            if (examWorkflowList.size() > 0) {
                for (ExamWorkflow examWorkflow : examWorkflowList) {
                    String time = examWorkflow.getTime();
                    String year = time.substring(0, 4);
                    Map<String, Object> tempMap = new HashMap<>();
                    if ("1".equals(examWorkflow.getExamCycle())) {
                        //月度
                        String month = time.substring(4, 6);
                        int month_int = Integer.parseInt(month);
                        //List<ExamWorkflowDatas> examWorkflowDataList = examWorkflowDatasDao.findInfosByOIdWFId(id,examWorkflow.getId());
                        tempMap.put("month", month_int);
                    } else if ("2".equals(examWorkflow.getExamCycle())) {
                        //年度
                        String workflowId = examWorkflow.getId();
                        yearWorkflowIdMap.put("workflowId" + year, workflowId);
                    }
                    yearSet.add(year);
                    tempMap.put("year", year);
                    //List<ExamWorkflowDatas> examWorkflowDataList = examWorkflowDatasDao.findInfosByOIdWFId(userId, examWorkflow.getId());
                    tempMap.put("workflowId", examWorkflow.getId());
                    examCycleList.add(tempMap);

                }
                List<String> yearList = new ArrayList<>(yearSet);
                Collections.sort(yearList);
                for (String year : yearList) {
                    //根据年度生成相应考评档案对象
                    ExamRecord record = new ExamRecord();
                    record.setYear(year);
                    String workflowId = (String) yearWorkflowIdMap.get("workflowId" + year);
                    Map<String, Double> monthSumMap = new HashMap<>();
                    Double yearSum = 0.0;
                    Double yearEndSum = 100.0;//年终
//                    Integer yearNum = Integer.valueOf(year);
                    List<ExamWorkflow> examWorkflowYearList = examWorkflowDao.findYearInfosByIds(workflowIds, year);
                    if (examWorkflowYearList != null && examWorkflowYearList.size() > 0) {
                        Double score = 100.0;
                        for (ExamWorkflow examWorkflow : examWorkflowYearList) {
                            Map<String, Object> resultMap;
                            if("1".equals(examType)){
                                resultMap = this.getJKCRecordScoreList(examWorkflow.getId(), userId);
                                try {
                                    score = Double.valueOf(((List<Map<String, String>>)resultMap.get("unitList")).get(0).get("totaltScore"));
                                }catch (Exception e){
                                    logger.error(getExceptionInfo(e));
                                    System.out.println("发生错误："+e.getCause());
                                }
                            }else if ("3".equals(examType)){
                                resultMap = this.getCKDSRecordScoreList(examWorkflow.getId(), userId);
                                try {
                                    score = Double.valueOf(((List<Map<String,Object>>)resultMap.get("pcsResultList")).get(0).get("totalScore").toString());
                                }catch (Exception e){
                                    logger.error(getExceptionInfo(e));
                                    System.out.println("发生错误："+e.getCause());
                                }
                            }else{
                                resultMap =this.getJGRecordScoreList(examWorkflow.getId(),userId);
                                try {
                                    score = (Double)((List<Map<String, Object>>)resultMap.get("unitMapList")).get(0).get("totalScore");
                                }catch (Exception e){
                                    logger.error(getExceptionInfo(e));
                                    System.out.println("发生错误："+e.getCause());
                                }
                            }

                        }
                        yearSum = score;
                    }
                    for (Map<String, Object> map : examCycleList) {
                        if (map.get("year").equals(year)) {
                            if (map.get("month") != null) {
                                String  tempWorkflowId =  (String)map.get("workflowId");
                                Double score = 100.0;
                                Map<String, Object> resultMap;
                                if("1".equals(examType)){
                                    resultMap = this.getJKCRecordScoreList(tempWorkflowId, userId);
                                    try {
                                        score = Double.valueOf(((List<Map<String, String>>)resultMap.get("unitList")).get(0).get("totaltScore"));
                                    }catch (Exception e){
                                        logger.error(getExceptionInfo(e));
                                        System.out.println("发生错误："+e.getCause());
                                    }
                                }else if ("3".equals(examType)){
                                    resultMap = this.getCKDSRecordScoreList(tempWorkflowId, userId);
                                    try {
//                                        score = (Double)((List<Map<String,Object>>)resultMap.get("pcsResultList")).get(0).get("totalScore");
                                        score = Double.valueOf(((List<Map<String,Object>>)resultMap.get("pcsResultList")).get(0).get("totalScore").toString());
                                    }catch (Exception e){
                                        logger.error(getExceptionInfo(e));
                                        System.out.println("发生错误："+e.getCause());
                                    }
                                }else{
                                    resultMap =this.getJGRecordScoreList(tempWorkflowId,userId);
                                    try {
                                        score =(Double)((List<Map<String, Object>>)resultMap.get("unitMapList")).get(0).get("totalScore");
                                    }catch (Exception e){
                                        logger.error(getExceptionInfo(e));
                                        System.out.println("发生错误："+e.getCause());
                                    }
                                }
                                monthSumMap.put(map.get("month").toString(), score);

                            }
                        }
                    }
                    yearEndSum = yearSum;
                    record.setMonthSumMap(monthSumMap);
                    record.setYearEndSum(yearEndSum);
                    record.setYearSum(yearSum);
                    examRecordList.add(record);
                }
            }
            int count = examRecordList.size();
            int pageNo = page.getPageNo();
            int pageSize = page.getPageSize();
            if (workflowIds != null) {
                List<ExamRecord> pageList;
                if (pageSize * pageNo < count) {
                    pageList = examRecordList.subList((pageNo - 1) * pageSize, pageSize * pageNo);
                } else {
                    pageList = examRecordList.subList((pageNo - 1) * pageSize, count);
                }
                page.setCount(examRecordList.size());
                page.setList(pageList);
            }
        }

        return page;
    }
    //查询检查情况
    public Map<String, Object> findScoreInfoList(int yearNum, int startMonthNum, int endMonthNum, String personId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> minusPointsList = new ArrayList<>();//惩戒情况集合
        List<String> bonusPointsList = new ArrayList<>();//奖励情况集合
        List<String> otherPointsList = new ArrayList<>();//其他情况集合
        List<ExamWorkflow> examWorkflowList = examWorkflowDao.findListByYear(yearNum);//查询出选择年度的所有的考评流程信息
        if (examWorkflowList.size() > 0) {//在工作流程表中存在数据
            List<String> workflowIdList = new ArrayList<>();
            for (ExamWorkflow examWorkflowTemp : examWorkflowList) {
                String time = examWorkflowTemp.getTime();
                if (time != null && time.length() == 6) {
                    //月度
                    String month = time.substring(4, 6);
                    int month_int = Integer.parseInt(month);
                    if(endMonthNum==0 && startMonthNum==0){
                        //本年所有
                        String workflowId = examWorkflowTemp.getId();
                        workflowIdList.add(workflowId);
                    }else if (month_int <= endMonthNum && month_int >= startMonthNum) {
                        //月份符合查询月份间
                        String workflowId = examWorkflowTemp.getId();
                        workflowIdList.add(workflowId);
                    }
                }
                if(time!=null && time.length()==4){
                    //年度
                    String workflowId = examWorkflowTemp.getId();
                    workflowIdList.add(workflowId);
                }
            }
            if(workflowIdList.size()>0){
                List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.findIsSelListByWFIdsObjId(workflowIdList,personId);
                if(examWorkflowDatasList!=null && examWorkflowDatasList.size()>0){
                    for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList){
                        String standardId = examWorkflowDatas.getStandardId();
                        int rowId = Integer.parseInt(examWorkflowDatas.getRowId());
                        String detail = examWorkflowDatas.getDetail();
                        String items = examWorkflowDatas.getItems();
                        ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
                        String modelType = examStandardBaseInfo.getModelType();
                        ExamStandardTemplateData examStandardTemplateData = examStandardTemplateDataDao.findInfoByStandardBaseIdRowId(examStandardBaseInfo.getId(),rowId);
                        if(modelType!=null ){
                            if("2".equals(modelType)){
                                //公共加分
                                bonusPointsList.add(examWorkflowDatas.getItems());
                                //bonusPointsList.add(examStandardTemplateData.getItemValue());
                            } else if( "3".equals(modelType)){
                                //公共扣分
                                minusPointsList.add(examWorkflowDatas.getItems());
                                //minusPointsList.add(examStandardTemplateData.getItemValue());
                            }else{
                                //其他
                                otherPointsList.add(examWorkflowDatas.getItems());
                            }
                        }

                    }
                }
            }
        }
        resultMap.put("minusPoints", minusPointsList);//减分
        resultMap.put("bonusPoints", bonusPointsList);//加分
        resultMap.put("otherPoints", otherPointsList);//其他
        return resultMap;
    }

    //局考处
    public Map<String, Object> getJKCRecordScoreList_old(String workflowId, String officeId) {
        List<String> objIds = new ArrayList<>();//各个公安处单位id集合
        List<String> unitNames = new ArrayList<>();//各个公安处名称集合
        objIds.add(officeId);
        unitNames.add(officeService.findNameById(officeId));
        Map<String, Object> resultMap = new HashMap<>();//返回结果集
        //从工作权重表中取出相关工作项及权重
        List<Map<String, Object>> weigthsList = examWeightsMainDao.findWorkNameList();
        String fillPersonId = null;
        //单位各处信息（单位名称，单位id，业务权重合计得分，总公共扣分，总公共加分，总得分）集合
        List<Map<String, String>> unitList = new ArrayList<>();

        for (int i = 0; i < unitNames.size(); i++) {
            Map<String, String> unitMap = new HashMap<>();
            unitMap.put("unitName", unitNames.get(i));
            unitMap.put("unitId", objIds.get(i));
            unitMap.put("totalWeightScore", String.valueOf(0.0));
            unitMap.put("totalPublicAddScore", String.valueOf(0.0));
            unitMap.put("totalPublicDeductScore", String.valueOf(0.0));
            unitMap.put("totalScore", String.valueOf(0.0));
            unitList.add(unitMap);
        }
        List list1 = new ArrayList<List<Map<String, Object>>>();//公安处各项得分集合（包含 业务100分制得分、业务折算权重后得分、具体事项）
        //业务权重合计得分
        for (Map<String, Object> map : weigthsList) {
            String workName = String.valueOf(map.get("value"));
            String workNameType = String.valueOf(map.get("workNameType"));
            //用于判断该工作项是否为得分制工作项
            int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName,"1");
            List list2 = new ArrayList<Map<String, Object>>();
            for (Map<String, String> unitMap : unitList) {
                Double weightScore = (Double) map.get("weight");
                String unitId = unitMap.get("unitId");
                Map<String, Object> tempUnitInfoMap = new HashMap<>();
                List<ExamWorkflowDatas> workflowDatasList = examWorkflowDatasDao.getMapByOidWFIdWorkName(workflowId, unitId, workName,fillPersonId);
                StringBuffer specificItem = new StringBuffer("业务部分详情：无扣分事项。");
                Double publicAddScore = 0.0;
                Double publicDeductScore = 0.0;
                if (workflowDatasList != null && workflowDatasList.size() > 0) {
                    specificItem = new StringBuffer("业务部分详情：<br>");
                    Double hundredScore = 100.0;
                    if("3".equals(workNameType) || "33".equals(workName)||"32".equals(workName)|| isAddScoreItem>0){
                        hundredScore = 0.0;
                    }
                    int i = 1;
                    for (ExamWorkflowDatas examWorkflowDatas : workflowDatasList) {
                        if ("32".equals(examWorkflowDatas.getWorkName())) {//公共加扣分项
                            if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
                                if(examWorkflowDatas.getValue()!=null){
                                    publicDeductScore += Double.valueOf(examWorkflowDatas.getValue());
                                }else{
                                    publicDeductScore += 0.0;
                                }

                            }
                            if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
                                if(examWorkflowDatas.getValue()!=null){
                                    publicAddScore += Double.valueOf(examWorkflowDatas.getValue());
                                }else{
                                    publicAddScore += 0.0;
                                }

                            }
                        }
                        if(examWorkflowDatas.getItems()!=null){
                            specificItem.append(i+"."+examWorkflowDatas.getItems() + "<br>");
                            i++;
                        }
                        if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
                            if(examWorkflowDatas.getValue()!=null){
                                hundredScore -= Double.valueOf(examWorkflowDatas.getValue());
                            }else{
                                hundredScore -= 0.0;
                            }
                        }
                        if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
                            if(examWorkflowDatas.getValue()!=null){
                                hundredScore += Double.valueOf(examWorkflowDatas.getValue());
                            }else{
                                hundredScore += 0.0;
                            }
                        }

                    }
                    Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore")) + publicAddScore;//总公共加分
                    Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore")) + publicDeductScore;//总公共扣分
                    if("1".equals(map.get("workNameType"))){
                        if("2".equals(workName)){
                            //高铁安保,重点工作，但采用常规业务算法算分
                            if(hundredScore<0){
                                //百分制得分 小于 0;得分不能小于0
                                weightScore = 0.0;
                                hundredScore = 0.0;
                                tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                                tempUnitInfoMap.put("weightScore", weightScore);//权重分
                            }else{
                                weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
                                //weightScore = hundredScore * (weightScore / 100);
                                tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                                tempUnitInfoMap.put("weightScore", weightScore);//权重分
                                if(isAddScoreItem>0){
                                    //得分制工作项
                                    weightScore = hundredScore;
                                    tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                                    tempUnitInfoMap.put("weightScore", weightScore);//权重分
                                }
                            }
                               /* weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
                                //weightScore = hundredScore * (weightScore / 100);
                                tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                                tempUnitInfoMap.put("weightScore", weightScore);//权重分*/
                        }else{
                            tempUnitInfoMap.put("hundredScore", "-");//百分制得分
                            if(isAddScoreItem>0){
                                //得分制工作项
                                if(hundredScore<0){
                                    hundredScore = 0.0;
                                }
                                weightScore = hundredScore;
                                tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                                tempUnitInfoMap.put("weightScore", weightScore);//权重分
                            }else{
                                if((100-hundredScore)>weightScore){
                                    tempUnitInfoMap.put("weightScore", 0.0);//权重分
                                    weightScore = 0.0;
                                }else{
                                    tempUnitInfoMap.put("weightScore", weightScore-(100-hundredScore));//权重分
                                    weightScore = weightScore-(100-hundredScore);
                                }
                            }

                        }
                    }else{
                        //2.23 权重分设置问题，应当扣完为止，不出现负数
                        if(!workName.equals("32")&&hundredScore<0){
                            //百分制得分 小于 0;得分不能小于0
                            weightScore = 0.0;
                            hundredScore = 0.0;
                            tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                            tempUnitInfoMap.put("weightScore", weightScore);//权重分
                        }else{
                            weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
                            //weightScore = hundredScore * (weightScore / 100);
                            tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                            tempUnitInfoMap.put("weightScore", weightScore);//权重分
                            if(isAddScoreItem>0){
                                //得分制工作项
                                weightScore = hundredScore;
                                tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                                tempUnitInfoMap.put("weightScore", weightScore);//权重分
                            }
                        }
                    }
                    Double totalWeightScore;
                    if(!workName.equals("32")){
                        totalWeightScore =  (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore")) * 100)/100 + weightScore;//业务权重合计得分
                    }else{
                        totalWeightScore = (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore")) *100)/100 + 0.0;
                    }
                    tempUnitInfoMap.put("workName", map.get("label"));//工作项名称
                    tempUnitInfoMap.put("workNameValue", map.get("value"));//工作项value
                    tempUnitInfoMap.put("workNameType", map.get("workNameType"));//工作项类别  0 常规 1 重点 3 公共加扣分
                    tempUnitInfoMap.put("weight", map.get("weight"));//权重
                    tempUnitInfoMap.put("unitName", unitMap.get("unitName"));//单位名称
                    tempUnitInfoMap.put("unitId", unitMap.get("unitId"));//单位id
                    tempUnitInfoMap.put("specificItem", specificItem.toString());//具体项
                    unitMap.put("totalWeightScore", String.valueOf((double) Math.round(Double.valueOf(totalWeightScore.toString())*100)/100));//业务权重合计得分
                    unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
                    unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分
                } else {
                    Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore")) + publicAddScore;//总公共加分
                    Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore")) + publicDeductScore;//总公共扣分
                    Double totalWeightScore ;//业务权重合计得分
                    if(!"32".equals(workName)){
                        if("33".equals(workName)){
                            totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) +0.0;
                        }else{
                            if(isAddScoreItem>0){
                                totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) + 0.0;
                            }else{
                                totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) + weightScore;
                            }
                        }
                    } else{
                        totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) +0.0;
                    }
                    tempUnitInfoMap.put("workName", map.get("label"));
                    tempUnitInfoMap.put("workNameValue", map.get("value"));//工作项value
                    tempUnitInfoMap.put("workNameType", map.get("workNameType"));//工作项类别 0 常规 1 重点 3公共加扣分表
                    tempUnitInfoMap.put("weight", map.get("weight"));
                    tempUnitInfoMap.put("unitName", unitMap.get("unitName"));
                    tempUnitInfoMap.put("unitId", unitMap.get("unitId"));
                    if("33".equals(workName)||"32".equals(workName)){
                        tempUnitInfoMap.put("hundredScore", 0.0);
                        tempUnitInfoMap.put("weightScore", 0.0);
                    }else if("1".equals(map.get("workNameType"))){
                        //重点工作
                        if(isAddScoreItem>0){
                            if("2".equals(workName)){
                                //高铁安保
                                tempUnitInfoMap.put("hundredScore", "0");//百分制得分
                            }else{
                                tempUnitInfoMap.put("hundredScore", "-");//百分制得分
                            }
                            tempUnitInfoMap.put("weightScore", 0.0);
                        }else{
                            if("2".equals(workName)){
                                //高铁安保
                                tempUnitInfoMap.put("hundredScore", "100");//百分制得分
                            }else{
                                tempUnitInfoMap.put("hundredScore", "-");//百分制得分
                            }
                            tempUnitInfoMap.put("weightScore", weightScore);
                        }

                    }else{
                        //常规业务
                        //得分制工作项
                        if(isAddScoreItem>0){
                            tempUnitInfoMap.put("hundredScore", 0.0);//百分制得分
                            tempUnitInfoMap.put("weightScore", 0.0);
                        }else{
                            tempUnitInfoMap.put("hundredScore", 100.0);//百分制得分
                            tempUnitInfoMap.put("weightScore", weightScore);
                        }
                    }
                    tempUnitInfoMap.put("specificItem", specificItem.toString());
                    unitMap.put("totalWeightScore", totalWeightScore.toString());//业务权重合计得分
                    unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
                    unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分

                }
                Double totaltScore = (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore"))* 100)/100 + Double.valueOf(unitMap.get("totalPublicAddScore")) - Double.valueOf(unitMap.get("totalPublicDeductScore"));//总得分
                unitMap.put("totalScore", totaltScore.toString());//总得分
                list2.add(tempUnitInfoMap);

            }
            if (list2 != null && list2.size() > 0) {
                list1.add(list2);
            }
        }
        resultMap.put("unitList", unitList);
        resultMap.put("weigthsList", weigthsList);
        resultMap.put("conventionScoreList", list1);
        return resultMap;
    }

    public Map<String, Object> getJKCRecordScoreList(String workflowId, String officeId) {
        Map<String, Object> resultMap = new HashMap<>();//返回结果集
        //resultMap = examWorkflowDatasService.jkcAllPublicDetail(workflowId,officeId,8);
        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
        String examCycle =  examWorkflow.getExamCycle();//1月 2年
        if(StringUtils.isNotBlank(examCycle)){
            List<Map<String, Object>> list = null;
            if("1".equals(examCycle)){
                list = examUnitAllPublicService.findInfoByUnitId(officeId, examWorkflow.getTime(),null);
            }else{
                list = examUnitAllPublicYearService.findInfoByUnitId(officeId, examWorkflow.getTime(),null);
            }
            if(list!=null && list.size()>0){
                String sum = ((BigDecimal) list.get(0).remove("sum")).toString();
                list.get(0).put("totaltScore",sum);
                resultMap.put("unitList",list);
            }
        }
        if(resultMap.get("unitList")==null){
            resultMap = examWorkflowDatasService.getIngScoreList(workflowId,officeId,8);
        }
        //resultMap = this.getJKCRecordScoreList_old(workflowId,officeId);
        return resultMap;
    }
    /*
     * 考评档案-处考队所 - 权重不同
     * */
    public Map<String, Object> getCKDSRecordScoreList_old(String workflowId,String userId) {
        Map<String,Object> resultMap = new HashMap<>();
        User user =UserUtils.get(userId);
        List<Map<String,String>> pcsList = new ArrayList<>();
        Map<String,String> pcsMap = new HashMap<>();
        pcsMap.put("fillPerson",user.getName());
        pcsMap.put("fillPersonId",user.getId());
        pcsMap.put("officeName",user.getOffice().getName());
        pcsMap.put("officeId",user.getOffice().getId());
        pcsList.add(pcsMap);
        String unitId = user.getOffice().getId();
        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
        User createBy = examWorkflow.getCreateBy();
        User updateBy = examWorkflow.getUpdateBy();
        User createUser = UserUtils.get(createBy.getId());
        User updateUser = UserUtils.get(updateBy.getId());
        List<Map<String,Object>> workNameList;
        if("34".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",34,") || "34".equals(createUser.getOffice().getParentId())
                || "34".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",34,") || "34".equals(updateUser.getOffice().getParentId())
                || user.getOffice().getParentIds().contains(",34,")
        ){
            //南宁处
            workNameList = examWeightsMainDao.getWorkNameListByChu("34",unitId);
        } else if("95".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",95,") || "95".equals(createUser.getOffice().getParentId())
                || "95".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",95,") || "95".equals(updateUser.getOffice().getParentId())
                || user.getOffice().getParentIds().contains(",95,")){
            //柳州处
            workNameList = examWeightsMainDao.getWorkNameListByChu("95",unitId);
        }else if("156".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",156,") || "156".equals(createUser.getOffice().getParentId())
                || "156".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",156,") || "156".equals(updateUser.getOffice().getParentId())
                || user.getOffice().getParentIds().contains(",156,")
        ){
            //北海处
            workNameList = examWeightsMainDao.getWorkNameListByChu("156",null);
        }else{
            workNameList = new ArrayList<>();
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        int i = 1;
        List<String> zdNameList=new ArrayList<>();
        List<Double> zdWeightList=new ArrayList<>();
        List<String> cgNameList=new ArrayList<>();
        List<Double> cgWeightList=new ArrayList<>();
        List<String> cgScoreNameList=new ArrayList<>();
        List<String> zdScoreNameList=new ArrayList<>();
        if(pcsList!=null && pcsList.size()>0 && workNameList!=null && workNameList.size()>0){
            for(Map<String,Object> workNameMap : workNameList){
                String workNameType = (String) workNameMap.get("workNameType");//0 常规业务  1 重点工作  3 公共加扣分
                if(workNameType.equals("0")){
                    cgNameList.add((String) workNameMap.get("label"));
                    cgScoreNameList.add("Score"+(String) workNameMap.get("label"));
                    cgWeightList.add((Double) workNameMap.get("weight"));
                    continue;
                }
                if(workNameType.equals("1")){
                    zdNameList.add((String) workNameMap.get("label"));
                    zdScoreNameList.add("Score"+(String) workNameMap.get("label"));
                    zdWeightList.add((Double) workNameMap.get("weight"));
                    continue;
                }
            }
            resultMap.put("cgNameList",cgNameList);
            resultMap.put("cgScoreNameList",cgScoreNameList);
            resultMap.put("zdScoreNameList",zdScoreNameList);
            resultMap.put("cgWeightList",cgWeightList);
            resultMap.put("zdNameList",zdNameList);
            resultMap.put("zdWeightList",zdWeightList);
            for(Map<String,String> tempPcsMap : pcsList){
                String fillPersonId = tempPcsMap.get("fillPersonId");//填报人id
                Map<String,Object> pcsScoreMap = new HashMap<>();
                pcsScoreMap.put("fillPersonName",tempPcsMap.get("fillPerson"));
                pcsScoreMap.put("fillPersonId",tempPcsMap.get("fillPersonId"));
                pcsScoreMap.put("pcsIndex",i);
                i++;
                Double totalScore = 0.0;//考核得分合计
                Double publicAddScore = 0.0;//公共加分
                Double publicDecScore = 0.0;//公共扣分
                for(Map<String,Object> tempWorkName : workNameList){
                    String workName_value = (String) tempWorkName.get("value");
                    String workName_label = (String) tempWorkName.get("label");
                    String workName_type = (String) tempWorkName.get("workNameType");
                    Double weight = (Double) tempWorkName.get("weight");
                    pcsScoreMap.put("weight"+workName_label,weight);//设置权重
                    pcsScoreMap.put(workName_label,weight);//设置权重
                    List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.findScoresByPcsIdWorkName(workflowId,fillPersonId,workName_value);
                    Double score;
                    if("1".equals(workName_type)){
                        if("2".equals(workName_value)){
                            //高铁安保
                            score = 100.0;
                        }else{
                            score = weight;
                        }
                    }else{
                        score = 100.0;
                    }
                    if(examWorkflowDatasList != null && examWorkflowDatasList.size()>0) {
                        if ("32".equals(workName_value)) {
                            for (ExamWorkflowDatas workflowDatas : examWorkflowDatasList) {

                                if (workflowDatas.getValueType() != null ) {
                                    Integer valueType = Integer.valueOf(workflowDatas.getValueType());
                                    double scoreValue;
                                    try {
                                        scoreValue = Double.parseDouble(workflowDatas.getValue());
                                    }catch (Exception e){
                                        scoreValue = 0.0;
                                        logger.error(getExceptionInfo(e));
                                        e.printStackTrace();
                                    }
                                    if (valueType == 1) {
                                        publicAddScore += scoreValue;
                                    } else if (valueType == -1) {
                                        publicDecScore += scoreValue;
                                    }
                                }
                            }
                            pcsScoreMap.put("Score公共加分", publicAddScore);
                            pcsScoreMap.put("Score公共扣分", publicDecScore);
                        } else {
                            for (ExamWorkflowDatas workflowDatas : examWorkflowDatasList) {
                                if (workflowDatas.getValueType() != null ) {
                                    Integer valueType = Integer.valueOf(workflowDatas.getValueType());
                                    double scoreValue;
                                    if (StringUtils.isNotBlank(workflowDatas.getValue())) {
                                    try {
                                        scoreValue = Double.parseDouble(workflowDatas.getValue());
                                    }catch (Exception e){
                                        scoreValue = 0.0;
                                        logger.error(getExceptionInfo(e));
                                        e.printStackTrace();
                                    }
									} else {
										scoreValue = 0.0;
									}
                                    if (valueType == 1) {
                                        score += scoreValue;
                                    } else if (valueType == -1) {
                                        score -= scoreValue;
                                    }
                                }
                            }
                            //pcsScoreMap.put("Score"+workName_label, score);//百分
                            if("1".equals(workName_type)) {
                                if("2".equals(workName_value)){
                                    //高铁安保
                                    if(score<0){
                                        pcsScoreMap.put(workName_label, 0.0);
                                        pcsScoreMap.put("Score"+workName_label, 0.0);//考核原始分
                                        totalScore +=  0.0;
                                    }else{
                                        pcsScoreMap.put(workName_label, score*(weight/100));
                                        pcsScoreMap.put("Score"+workName_label, score);//考核原始分
                                        totalScore +=  score*(weight/100);
                                    }
                                }else{
                                    if(score<0){
                                        pcsScoreMap.put(workName_label, 0);
                                        pcsScoreMap.put("Score"+workName_label, 0);//考核原始分
                                        totalScore += 0;
                                    }else{
                                        pcsScoreMap.put(workName_label, score);
                                        pcsScoreMap.put("Score"+workName_label, weight);//考核原始分
                                        totalScore += score;
                                    }
                                }
                            }else{
                                if(score<0){
                                    pcsScoreMap.put(workName_label, 0.0);
                                    pcsScoreMap.put("Score"+workName_label, 0.0);//考核原始分
                                    totalScore +=  0.0;
                                }else{
                                    pcsScoreMap.put(workName_label, score*(weight/100));
                                    pcsScoreMap.put("Score"+workName_label, score);//考核原始分
                                    totalScore +=  score*(weight/100);
                                }
                            }
                        }
                    }else{
                        //没选
                        pcsScoreMap.put("Score"+workName_label, 100);
                        //权重
                        if(!"32".equals(workName_value)){
                            if("1".equals(workName_type) && !"2".equals(workName_value)){
                                pcsScoreMap.put("Score"+workName_label, weight);
                            }
                            pcsScoreMap.put(workName_label, weight);
                            totalScore += weight;
                        }
                        if(publicAddScore==0.0){
                            pcsScoreMap.put("Score公共加分", 0.0);
                        }
                        if(publicDecScore == 0.0){
                            pcsScoreMap.put("Score公共扣分", 0.0);
                        }
                    }
                }
                totalScore = totalScore+publicAddScore-publicDecScore;
                pcsScoreMap.put("totalScore",String.format("%.2f",totalScore));
                resultList.add(pcsScoreMap);
            }
        }
        resultMap.put("pcsResultList",resultList);
        return resultMap;
    }
    public Map<String, Object> getCKDSRecordScoreList(String workflowId,String userId) {
        Map<String,Object> resultMap = new HashMap<>();
        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
        String examCycle =  examWorkflow.getExamCycle();//1月 2年
        if(StringUtils.isNotBlank(examCycle)){
            List<Map<String, Object>> list = null;
            if("1".equals(examCycle)){
                list = examUnitAllPublicService.findCkdsInfoByUnitId(userId, examWorkflow.getTime(),null);
            }else{
                list = examUnitAllPublicYearService.findCkdsInfoByUnitId(userId, examWorkflow.getTime(),null);
            }
            if(list!=null && list.size()>0){
                Double sum = ((BigDecimal) list.get(0).get("sum")).doubleValue();
                list.get(0).put("totalScore",sum);
                resultMap.put("pcsResultList",list);
            }
        }
        if(resultMap.get("pcsResultList")==null){
            resultMap = examWorkflowDatasService.getIngChuKaoDuisuo2(workflowId,8,userId);
        }
        return resultMap;
    }

    // 局考局机关/处考处机关  考评档案
    public Map<String, Object> getJGRecordScoreList(String workflowId, String userId) {
        Map<String,Object> resultMap = new HashMap<>();
        Integer monthCount = examUnitAllPublicService.getWeightIsNull(workflowId);
        Integer yearCount = examUnitAllPublicYearService.getWeightIsNull(workflowId);
        if((monthCount!= null && monthCount>0)||(yearCount!= null && yearCount>0)){
            //非权重制
               User user = UserUtils.get(userId);
        List<Map<String,Object>> unitMapList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("objId",user.getId());
        map.put("objName",user.getName());
        unitMapList.add(map);
        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
        String examType = examWorkflow.getExamType();
        String basicScore = DictUtils.getDictValue(examType,"exam_basicScore","100");
        for(Map<String,Object> tempMap : unitMapList){
            List<String> objId = new ArrayList<String>();
            objId.add(tempMap.get("objId").toString());
            List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.getListByOidWFId(objId, workflowId);
            StringBuffer deductScoreItems ;
            StringBuffer addScoreItems ;
            double deductScore = 0.0;//扣分
            double addScore = 0.0;//加分
            double totalScore = 100.0;//总得分
            try {
                totalScore = Double.valueOf(basicScore);
            }catch (Exception e){
                logger.error(getExceptionInfo(e));
                e.printStackTrace();
                totalScore = 100.0;
            }
            if(examWorkflowDatasList!=null && examWorkflowDatasList.size()>0){
                deductScoreItems =  new StringBuffer();
                addScoreItems =  new StringBuffer();
                int addI = 1 ;
                int deductI = 1 ;
                for(ExamWorkflowDatas examWorkflowDatas :examWorkflowDatasList){
                    if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
                        if(examWorkflowDatas.getItems()!=null){
                            deductScoreItems.append(deductI+"."+examWorkflowDatas.getItems()+"<br>");
                            deductI++;
                        }
                        try {
                            deductScore += Double.valueOf(examWorkflowDatas.getValue());
                        }catch (Exception e){
                            deductScore += 0.0;
                            logger.error(getExceptionInfo(e));
                            e.printStackTrace();
                        }
                    }
                    if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
                        if(examWorkflowDatas.getItems()!=null){
                            addScoreItems.append(addI+"."+examWorkflowDatas.getItems()+"<br>");
                            addI++;
                        }
                        try {
                            addScore += Double.valueOf(examWorkflowDatas.getValue());
                        }catch (Exception e){
                            addScore += 0.0;
                            logger.error(getExceptionInfo(e));
                            e.printStackTrace();
                        }

                    }
                }
                tempMap.put("totalScore",totalScore+addScore-deductScore);//总得分
                tempMap.put("addScore",addScore);//加分
                tempMap.put("deductScore",deductScore);//扣分
                tempMap.put("deductScoreItems",deductScoreItems);//扣分项目
                tempMap.put("addScoreItems",addScoreItems);//加分项目
            }else{
                deductScoreItems = new StringBuffer("无");
                addScoreItems = new StringBuffer("无");
                tempMap.put("totalScore",totalScore);//总得分
                tempMap.put("addScore",addScore);//加分
                tempMap.put("deductScore",deductScore);//扣分
                tempMap.put("deductScoreItems",deductScoreItems);//扣分项目
                tempMap.put("addScoreItems",addScoreItems);//加分项目
            }
        }
        resultMap.put("unitMapList",unitMapList);
        }else{
            //权重制
            //score =(Double)((List<Map<String, Object>>)resultMap.get("unitMapList")).get(0).get("totalScore");
            ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
            String examCycle =  examWorkflow.getExamCycle();//1月 2年
            if(StringUtils.isNotBlank(examCycle)){
                List<Map<String, Object>> list = null;
                if("2".equals(examWorkflow.getExamType())){
                    if("1".equals(examCycle)){
                        list = examUnitAllPublicService.findJkjInfoByUnitId(userId, examWorkflow.getTime());
                    }else{
                        list = examUnitAllPublicYearService.findJkjInfoByUnitId(userId, examWorkflow.getTime());
                    }
                }else{
                    if("1".equals(examCycle)){
                        list = examUnitAllPublicService.findCkcjgInfoByUnitId(userId, examWorkflow.getTime());
                    }else{
                        list = examUnitAllPublicYearService.findCkcjgInfoByUnitId(userId, examWorkflow.getTime());
                    }
                }
                if(list!=null && list.size()>0){
                    Double sum = ((BigDecimal) list.get(0).remove("sum")).doubleValue();
                    list.get(0).put("totalScore",sum);
                    resultMap.put("unitMapList",list);
                }
            }
            if(resultMap.get("unitMapList")==null){
                resultMap = examWorkflowDatasService.getIngJuJiGuanScoreList(workflowId,userId,8);
            }

        }
        return resultMap;
    }

    //异常信息打印到日志
    public static String getExceptionInfo(Exception e){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            return baos.toString();
        }catch (Exception e2){
            return e.toString();
        }

    }
}
