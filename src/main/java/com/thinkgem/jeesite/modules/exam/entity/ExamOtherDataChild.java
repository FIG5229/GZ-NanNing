/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 检查发现情况录入子表Entity
 *
 * @author eav.liu
 * @version 2020-03-11
 */
public class ExamOtherDataChild extends DataEntity<ExamOtherDataChild> {

    private static final long serialVersionUID = 1L;
    private String otherId;        // 检查id（exam_other_data表的主键）
    private String useModelName;        // 使用模板的名称
    private String useModel;        // 使用模板id
    private String chooseOptions;        // 选择项
    private String dutyUnit;        // 责任单位
    private String dutyUnitId;        // 责任单位ID
    private String dutyLeader;        // 责任领导
    private String dutyLeaderId;        // 责任领导id
    private String dutyPerson;        // 责任人
    private String dutyPersonId;        // 责任人的id
    private String testStandart;        // 绩效考评标准
    private String scortSituation;        // 扣分情况
    private String remark;        // 备注
    private String createOrgId;        // 创建者机构id
    private String updateOrgId;        // 更新者机构id
    private String dutyUnitScore;    //责任单位扣分
    private String dutyLeaderScore;        //责任领导扣分
    private String dutyPersonScore;        //责任人扣分

    private List<String> chooseOptionsList;

    /*新增字段*/
    @DateTimeFormat
    private Date time;

    /*新增字段 绩效考评使用*/
    private String rowNum;      //选择项行号
    private String type;        //三级关联 选择考评标准
//    private Date checkDate;		// 时间
    private String checkUnit;		// 单位
    private String checkUnitId;		// 单位ID
    private String checkPerson;		// 人姓名
    private String checkPersonId;		// id

    /*绩效考评查询使用*/
    private Date checkDate;		// 时间
    private int month;          //时间 年
    private int year;           //时间 月
    private String examCycle;       //考评周期
    private String examType;        //考评类型
    private String objId;           //被考评对象userId
    private List<String> standardList;



    private Date beginCheckDate;		// 开始 检查时间
    private Date endCheckDate;		// 结束 检查时间


    public ExamOtherDataChild() {
        super();
    }

    public ExamOtherDataChild(String id) {
        super(id);
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public String getUseModelName() {
        return useModelName;
    }

    public void setUseModelName(String useModelName) {
        this.useModelName = useModelName;
    }

    public String getUseModel() {
        return useModel;
    }

    public void setUseModel(String useModel) {
        this.useModel = useModel;
    }

    public String getChooseOptions() {
        return chooseOptions;
    }

    public void setChooseOptions(String chooseOptions) {
        this.chooseOptions = chooseOptions;
    }

    public String getDutyUnit() {
        return dutyUnit;
    }

    public void setDutyUnit(String dutyUnit) {
        this.dutyUnit = dutyUnit;
    }

    public String getDutyUnitId() {
        return dutyUnitId;
    }

    public void setDutyUnitId(String dutyUnitId) {
        this.dutyUnitId = dutyUnitId;
    }

    public String getDutyLeader() {
        return dutyLeader;
    }

    public void setDutyLeader(String dutyLeader) {
        this.dutyLeader = dutyLeader;
    }

    public String getDutyLeaderId() {
        return dutyLeaderId;
    }

    public void setDutyLeaderId(String dutyLeaderId) {
        this.dutyLeaderId = dutyLeaderId;
    }

    public String getDutyPerson() {
        return dutyPerson;
    }

    public void setDutyPerson(String dutyPerson) {
        this.dutyPerson = dutyPerson;
    }

    public String getDutyPersonId() {
        return dutyPersonId;
    }

    public void setDutyPersonId(String dutyPersonId) {
        this.dutyPersonId = dutyPersonId;
    }

    public String getTestStandart() {
        return testStandart;
    }

    public void setTestStandart(String testStandart) {
        this.testStandart = testStandart;
    }

    public String getScortSituation() {
        return scortSituation;
    }

    public void setScortSituation(String scortSituation) {
        this.scortSituation = scortSituation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    public String getUpdateOrgId() {
        return updateOrgId;
    }

    public void setUpdateOrgId(String updateOrgId) {
        this.updateOrgId = updateOrgId;
    }

    public List<String> getChooseOptionsList() {
        return chooseOptionsList;
    }

    public void setChooseOptionsList(List<String> chooseOptionsList) {
        this.chooseOptionsList = chooseOptionsList;
    }

    public String getDutyUnitScore() {
        return dutyUnitScore;
    }

    public void setDutyUnitScore(String dutyUnitScore) {
        this.dutyUnitScore = dutyUnitScore;
    }

    public String getDutyLeaderScore() {
        return dutyLeaderScore;
    }

    public void setDutyLeaderScore(String dutyLeaderScore) {
        this.dutyLeaderScore = dutyLeaderScore;
    }

    public String getDutyPersonScore() {
        return dutyPersonScore;
    }

    public void setDutyPersonScore(String dutyPersonScore) {
        this.dutyPersonScore = dutyPersonScore;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getExamCycle() {
        return examCycle;
    }

    public void setExamCycle(String examCycle) {
        this.examCycle = examCycle;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<String> standardList) {
        this.standardList = standardList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckUnit() {
        return checkUnit;
    }

    public void setCheckUnit(String checkUnit) {
        this.checkUnit = checkUnit;
    }

    public String getCheckUnitId() {
        return checkUnitId;
    }

    public void setCheckUnitId(String checkUnitId) {
        this.checkUnitId = checkUnitId;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(String checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public Date getBeginCheckDate() {
        return beginCheckDate;
    }

    public void setBeginCheckDate(Date beginCheckDate) {
        this.beginCheckDate = beginCheckDate;
    }

    public Date getEndCheckDate() {
        return endCheckDate;
    }

    public void setEndCheckDate(Date endCheckDate) {
        this.endCheckDate = endCheckDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}