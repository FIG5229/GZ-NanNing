/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflow;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScore;
import com.thinkgem.jeesite.modules.exam.dao.ExamLdScoreDao;

/**
 * 年度领导考核评分Service
 *
 * @author cecil.li
 * @version 2020-02-11
 */
@Service
@Transactional(readOnly = true)
public class ExamLdScoreService extends CrudService<ExamLdScoreDao, ExamLdScore> {

    @Autowired
    ExamLdScoreDao examLdScoreDao;

    @Autowired
    private PersonnelBaseDao personnelBaseDao;

    public ExamLdScore get(String id) {
        return super.get(id);
    }

    public List<ExamLdScore> findList(ExamLdScore examLdScore) {
        return super.findList(examLdScore);
    }

    public Page<ExamLdScore> findPage(Page<ExamLdScore> page, ExamLdScore examLdScore) {
            String userId = UserUtils.getUser().getId();
        /*其他人查看时不再查看自己的*/
        examLdScore.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "b"));
        /*if (userId.equals("cca66e1339f14799b01f6db43ed16e16") || userId.equals("978958003ea44a4bba3eed8ee6ceff3c")||
                userId.equals("46c521bf67e24db28772b3eac52dc454")|| userId.equals("6af0e615e9b0477da82eff06ff532c8b")){
            examLdScore.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "b"));
        }else {
            examLdScore.getSqlMap().put("dsf", "and b.id = '"+userId+"'");
        }*/

        return super.findPage(page, examLdScore);
    }

    @Transactional(readOnly = false)
    public void save(ExamLdScore examLdScore) {
        super.save(examLdScore);
    }

    @Transactional(readOnly = false)
    public void delete(ExamLdScore examLdScore) {
        super.delete(examLdScore);
    }

    @Transactional(readOnly = false)
    public void yearPersonSave(List<Map<String, String>> list, ExamWorkflow examWorkflow) {
        if (null != list) {
            for (Map<String, String> data : list) {
                ExamLdScore examLdScore = new ExamLdScore();
                examLdScore.setWorkflowId(examWorkflow.getId());
                examLdScore.setPersonId(data.get("personId"));
                examLdScore.setName(data.get("name"));
                if (null != data.get("m01")) {
                    examLdScore.setJanuaryScore(data.get("m01"));
                }
                if (null != data.get("m02")) {
                    examLdScore.setFebruaryScore(data.get("m01"));
                }
                if (null != data.get("m03")) {
                    examLdScore.setMarchScore(data.get("m03"));
                }
                if (null != data.get("m04")) {
                    examLdScore.setAprilScore(data.get("m04"));
                }
                if (null != data.get("m05")) {
                    examLdScore.setMayScore(data.get("m05"));
                }
                if (null != data.get("m06")) {
                    examLdScore.setJuneScore(data.get("m06"));
                }
                if (null != data.get("m07")) {
                    examLdScore.setJulyScore(data.get("m07"));
                }
                if (null != data.get("m08")) {
                    examLdScore.setAugustScore(data.get("m08"));
                }
                if (null != data.get("m09")) {
                    examLdScore.setSeptemberScore(data.get("m09"));
                }
                if (null != data.get("m10")) {
                    examLdScore.setOctoberScore(data.get("m10"));
                }
                if (null != data.get("m11")) {
                    examLdScore.setNovemberSocre(data.get("m11"));
                }
                if (null != data.get("m12")) {
                    examLdScore.setDecemberScore(data.get("m12"));
                }
                if (null != data.get("dailyScore")) {
                    examLdScore.setDailyScore(data.get("dailyScore"));
                }
                if (null != data.get("unitScore")) {
                    examLdScore.setUnitScore(data.get("unitScore"));
                }
                if (null != data.get("minzhuScore")) {
                    examLdScore.setMinzhuScore(data.get("minzhuScore"));
                }
                if (null != data.get("zongheScore")) {
                    examLdScore.setZongheScore(data.get("zongheScorelyScore"));
                }
                super.save(examLdScore);
            }
        }
    }

    @Transactional(readOnly = false)
    public void yearPersonSaveBeta(List<Map<String, String>> list, ExamWorkflow examWorkflow) {
        String basicScore = DictUtils.getDictValue(examWorkflow.getExamType(),"exam_basicScore","100");
        double baseSum = 100.0;//总得分
        try {
            baseSum = Double.valueOf(basicScore);
        }catch (Exception e){
            e.printStackTrace();
            baseSum = 100.0;
        }
        if (null != list) {
            for (Map<String, String> data : list) {
                String userId = data.get("objId");
                User user = UserUtils.get(userId);
                PersonnelBase personnelBase =personnelBaseDao.findInfoByIdNumber(user.getNo());
                ExamLdScore examLdScore = new ExamLdScore();
                examLdScore.setWorkflowId(examWorkflow.getId());
                examLdScore.setPersonId(data.get("objId"));
                examLdScore.setName(data.get("objName"));
                if(personnelBase!=null){
                    examLdScore.setJob(personnelBase.getJobAbbreviation());
                }
                String finalValue = "0";
                /*没有finalValue，只有origalValue*/
                if(null != data.get("origalValue")){
                    Object obj=data.get("origalValue");
                    finalValue = String.valueOf(Double.parseDouble(obj.toString())+baseSum);
                }else{
                    finalValue = String.valueOf(baseSum);
                }
                examLdScore.setSumScore(finalValue);
                super.save(examLdScore);
            }
        }
    }

    /*评定为优秀*/
    @Transactional(readOnly = false)
    public void excellent(ExamLdScore examLdScore) {
        examLdScoreDao.excellent(examLdScore);
    }

    /*批量评定为优秀*/
    @Transactional(readOnly = false)
    public void excellentByIds(List<String> ids) {
        examLdScoreDao.excellentByIds(ids);
    }

    //统计分析 - 人员   -  年度
    public Map<String,Object> findPersonnelYearExamList(Page<ExamLdScore> page, String year, String reasonType,String selUnitId,String unitId, String jz, String zw, String ageStart, String ageEnd) {
        Map<String,Object> resultMap = new HashMap<>();
        ExamLdScore examLdScore = new ExamLdScore();
        examLdScore.setTime(year);
        examLdScore.setPage(page);
        User user = UserUtils.getUser();
        List<Map<String,String>> unitList ;
        if("1".equals(user.getOffice().getId())){
            examLdScore.setOfficeId(null);
        }else{
            examLdScore.setOfficeId(user.getOffice().getId());
        }
        examLdScore.setUserId(user.getId());
        examLdScore.setJob(zw);//职务
        examLdScore.setJz(jz);//警种
        examLdScore.setOfficeId(unitId);//单位id
        examLdScore.setUnitId(unitId);//当前选择单位id
        Integer ageStartInt;
        try {
            ageStartInt = Integer.valueOf(ageStart);
        }catch (Exception e){
            System.out.println(e.getCause());
            ageStartInt = null;
        }
        Integer ageEndInt;
        try {
            ageEndInt = Integer.valueOf(ageEnd);
        }catch (Exception e){
            System.out.println(e.getCause());
            ageEndInt = null;
        }
        examLdScore.setAgeStart(ageStartInt);//年龄1
        examLdScore.setAgeEnd(ageEndInt);//年龄2
        List<ExamLdScore> examLdScoreList = new ArrayList<>();
        if("1".equals(reasonType)){
            //处领导
            unitList = new ArrayList<>();
            //处领导
            if("1".equals(user.getOffice().getId())){
                Map<String,String> nncMap = new HashMap<>();
                nncMap.put("unitName","南宁处领导班子");
                nncMap.put("unitId","34");
                Map<String,String> lzcMap = new HashMap<>();
                lzcMap.put("unitName","柳州处领导班子");
                lzcMap.put("unitId","95");
                Map<String,String> bhcMap = new HashMap<>();
                bhcMap.put("unitName","北海处领导班子");
                bhcMap.put("unitId","156");
                Map<String,String> zMap = new HashMap<>();
                zMap.put("unitName","公安处领导班子考评情况（总）");
                zMap.put("unitId","1");
                unitList.add(nncMap);
                unitList.add(lzcMap);
                unitList.add(bhcMap);
                unitList.add(zMap);
            }else{
                Office office = user.getOffice();
                Map<String,String> map = new HashMap<>();
                if(office.getId().equals("34")||office.getParentIds().indexOf(",34,")!=-1){
                    map.put("unitName","南宁处领导班子");
                    map.put("unitId","34");
                }else if(office.getId().equals("95")||office.getParentIds().indexOf(",95,")!=-1){
                    map.put("unitName","柳州处领导班子");
                    map.put("unitId","95");
                }else if(office.getId().equals("156")||office.getParentIds().indexOf(",156,")!=-1){
                    map.put("unitName","北海处领导班子");
                    map.put("unitId","156");
                }
                unitList.add(map);
            }
            if(StringUtils.isBlank(selUnitId)){
                examLdScore.setOfficeId(unitList.get(0).get("unitId"));
                selUnitId = unitList.get(0).get("unitId");
            }else{
                examLdScore.setOfficeId(selUnitId);
            }
            resultMap.put("unitList",unitList);
            resultMap.put("selUnitId",selUnitId);
            examLdScore.setExamType("5");
            examLdScoreList = examLdScoreDao.findPersonnelYearExamList(examLdScore);
        }else if("2".equals(reasonType)){
            //中基层领导
            unitList = new ArrayList<>();
            if("1".equals(user.getOffice().getId())){
                Map<String,String> zMap = new HashMap<>();
                zMap.put("unitName","局机关及直属支队领导干部");
                zMap.put("unitId","1");
                Map<String,String> nncMap = new HashMap<>();
                nncMap.put("unitName","南宁处所队领导干部");
                nncMap.put("unitId","34_1");
                Map<String,String> lzcMap = new HashMap<>();
                lzcMap.put("unitName","柳州处所队领导干部");
                lzcMap.put("unitId","95_1");
                Map<String,String> bhcMap = new HashMap<>();
                bhcMap.put("unitName","北海处所队领导干部");
                bhcMap.put("unitId","156_1");
                Map<String,String> nncjgMap = new HashMap<>();
                nncjgMap.put("unitName","南宁处机关及职能支队领导干部");
                nncjgMap.put("unitId","34_2");
                Map<String,String> lzcjgMap = new HashMap<>();
                lzcjgMap.put("unitName","柳州处机关及职能支队领导干部");
                lzcjgMap.put("unitId","95_2");
                Map<String,String> bhcjgMap = new HashMap<>();
                bhcjgMap.put("unitName","北海处机关及职能支队领导干部");
                bhcjgMap.put("unitId","156_2");
                unitList.add(zMap);
                unitList.add(nncMap);
                unitList.add(lzcMap);
                unitList.add(bhcMap);
                unitList.add(nncjgMap);
                unitList.add(lzcjgMap);
                unitList.add(bhcjgMap);
            }
            else{
                Office office = user.getOffice();
                Map<String,String> map = new HashMap<>();
                Map<String,String> jgmap = new HashMap<>();
                if(office.getId().equals("34")||office.getParentIds().indexOf(",34,")!=-1){
                    map.put("unitName","南宁处所队领导干部");
                    map.put("unitId","34_1");
                    jgmap.put("unitName","南宁处机关及职能支队领导干部");
                    jgmap.put("unitId","34_2");
                }else if(office.getId().equals("95")||office.getParentIds().indexOf(",95,")!=-1){
                    map.put("unitName","柳州处领导班子");
                    map.put("unitId","95_1");
                    jgmap.put("unitName","柳州处机关及职能支队领导干部");
                    jgmap.put("unitId","95_2");
                }else if(office.getId().equals("156")||office.getParentIds().indexOf(",156,")!=-1){
                    map.put("unitName","北海处领导班子");
                    map.put("unitId","156_1");
                    jgmap.put("unitName","北海处机关及职能支队领导干部");
                    jgmap.put("unitId","156_2");
                }
                unitList.add(map);
                unitList.add(jgmap);
            }
            if(StringUtils.isBlank(selUnitId)){
                examLdScore.setOfficeId(unitList.get(0).get("unitId"));
                selUnitId = unitList.get(0).get("unitId");
            }
            resultMap.put("unitList",unitList);
            resultMap.put("selUnitId",selUnitId);
            examLdScore.setExamType("6");
            if("1".equals(selUnitId)){
                //局机关及直属支队领导干部考评情况
                examLdScore.setOfficeId("1");
                examLdScoreList = examLdScoreDao.findPersonnelZJCJJGYearExamList(examLdScore);
            }else{
                String[] selUnitIdSplit = selUnitId.split("\\_");
                examLdScore.setExamType("6");
                if("1".equals(selUnitIdSplit[1])){
                    //队所领导
                    examLdScore.setOfficeId(selUnitIdSplit[0]);
                    examLdScoreList = examLdScoreDao.findPersonnelZJCDSYearExamList(examLdScore);
                }else if("2".equals(selUnitIdSplit[1])){
                    //处机关领导
                    examLdScore.setOfficeId(selUnitIdSplit[0]);
                    examLdScoreList = examLdScoreDao.findPersonnelZJCCJGYearExamList(examLdScore);
                }
            }
            //examLdScoreList = examLdScoreDao.findPersonnelYearExamList(examLdScore);
        }else{
            //民警
            examLdScore.setExamType("7");
            examLdScoreList = examLdScoreDao.findPersonnelYearExamList(examLdScore);
        }
        page.setList(examLdScoreList);
        resultMap.put("page",page);
        return  resultMap;
    }
    @Transactional(readOnly = false)
    public void deleteByWorkflowId(String workflowId) {
        dao.deleteByWorkflowId(workflowId);
    }
}