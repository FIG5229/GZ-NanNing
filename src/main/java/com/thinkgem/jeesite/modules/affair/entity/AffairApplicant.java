/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 入党申请人表Entity
 *
 * @author eav.liu
 * @version 2020-03-12
 */
public class AffairApplicant extends DataEntity<AffairApplicant> {

    private static final long serialVersionUID = 1L;
    @ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
    private String name;        // 姓名
    @ExcelField(title = "警号", type = 0, align = 2, sort = 2)
    private String policeNum;        // 警号
    @ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
    private String idNumber;        // 身份证号
    @ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType = "sex")
    private String sex;        // 性别（1：男 2：女）
    @ExcelField(title = "所在党组织", type = 0, align = 2, sort = 4)
    private String partyBranch;        // 所在党组织
    private String partyBranchId;        // 所在党组织id
    @ExcelField(title = "职务", type = 0, align = 2, sort = 5)
    private String job;        // 职务
    @ExcelField(title = "民族", type = 0, align = 2, sort = 6, dictType = "nation")
    private String nation;        // 民族
    @ExcelField(title = "文化程度", type = 0, align = 2, sort = 7)
    private String educationDegree;        // 文化程度
    @ExcelField(title = "出生年月", type = 0, align = 2, sort = 8)
    private Date birthday;        // 出生年月
    @ExcelField(title = "申请入党时间", type = 0, align = 2, sort = 9)
    private Date enterDate;        // 申请入党时间
    private String createOrgId;        // 创建者机构id
    private String createIdNo;        // 创建者身份证号
    private String updateOrgId;        // 更新者机构id
    private String updateIdNo;        // 更新者身份证号

    //前端查询接受参数
    private Date birthdayStart;
    private Date birthdayEnd;
    private Date enterDateStart;
    private Date enterDateEnd;
    private String treeId;
    //推送状态
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AffairApplicant() {
        super();
    }

    public AffairApplicant(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPartyBranch() {
        return partyBranch;
    }

    public void setPartyBranch(String partyBranch) {
        this.partyBranch = partyBranch;
    }

    public String getPartyBranchId() {
        return partyBranchId;
    }

    public void setPartyBranchId(String partyBranchId) {
        this.partyBranchId = partyBranchId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(String educationDegree) {
        this.educationDegree = educationDegree;
    }

    @JsonFormat(pattern = "yyyy-MM")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
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

    public Date getBirthdayStart() {
        return birthdayStart;
    }

    public void setBirthdayStart(Date birthdayStart) {
        this.birthdayStart = birthdayStart;
    }

    public Date getBirthdayEnd() {
        return birthdayEnd;
    }

    public void setBirthdayEnd(Date birthdayEnd) {
        this.birthdayEnd = birthdayEnd;
    }

    public Date getEnterDateStart() {
        return enterDateStart;
    }

    public void setEnterDateStart(Date enterDateStart) {
        this.enterDateStart = enterDateStart;
    }

    public Date getEnterDateEnd() {
        return enterDateEnd;
    }

    public void setEnterDateEnd(Date enterDateEnd) {
        this.enterDateEnd = enterDateEnd;
    }

    public String getPoliceNum() {
        return policeNum;
    }

    public void setPoliceNum(String policeNum) {
        this.policeNum = policeNum;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }
}