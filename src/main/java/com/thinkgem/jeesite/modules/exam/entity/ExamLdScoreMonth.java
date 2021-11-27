/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 月度领导考核评分Entity
 *
 * @author cecil.li
 * @version 2020-02-12
 */
public class ExamLdScoreMonth extends DataEntity<ExamLdScoreMonth> {

    private static final long serialVersionUID = 1L;
    private String unit;        // 单位
    private String unitId;        // 单位id
    private String job;        // 职务
    private String name;        // 姓名
    private String score;        // 分数
    private String matter;        // 事项
    private String createOrgId;        // 创建者机构id
    private String createIdNo;        // 创建者身份证号
    private String updateOrgId;        // 更新者机构id
    private String updateIdNo;        // 更新者身份证号
    private String belongTo;        // 所属班子
    private String workflowId;    //工作流ID
    private String personId;    //人员ID
    private String level;   //等次
    private String dangan;    //档案
    private String ids;
    private String dateType;
    /*dictType*/
    private String grades;
    //统计分析字段
    private String idNumber;         //身份证号
    private String personnelBaseId;  //人员信息表id
    private String time;             // 时间
    private String userId;           //当前用户id
    private String officeId;         //当前用户单位id
    private String examType;       //考评类别
    private String jz;              //警种
    private Integer ageStart;              //年龄1
    private Integer ageEnd;                //年龄2

    private String month;       //绩效等次评定使用

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public ExamLdScoreMonth() {
        super();
    }

    public ExamLdScoreMonth(String id) {
        super(id);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    public String getCreateIdNo() {
        return createIdNo;
    }

    public void setCreateIdNo(String createIdNo) {
        this.createIdNo = createIdNo;
    }

    public String getUpdateOrgId() {
        return updateOrgId;
    }

    public void setUpdateOrgId(String updateOrgId) {
        this.updateOrgId = updateOrgId;
    }

    public String getUpdateIdNo() {
        return updateIdNo;
    }

    public void setUpdateIdNo(String updateIdNo) {
        this.updateIdNo = updateIdNo;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDangan() {
        return dangan;
    }

    public void setDangan(String dangan) {
        this.dangan = dangan;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPersonnelBaseId() {
        return personnelBaseId;
    }

    public void setPersonnelBaseId(String personnelBaseId) {
        this.personnelBaseId = personnelBaseId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getJz() {
        return jz;
    }

    public void setJz(String jz) {
        this.jz = jz;
    }

    public Integer getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Integer ageStart) {
        this.ageStart = ageStart;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}