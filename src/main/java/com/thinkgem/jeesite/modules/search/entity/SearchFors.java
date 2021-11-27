package com.thinkgem.jeesite.modules.search.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

public class SearchFors extends DataEntity<SearchFors> {
    private String sex;
    private String nation;
    private String mianmao;
    private String unitId;
    private String approcalOrgType;
    private String rewardName;
    private String cjType;
    private String cjName;
    private String cjOrgType;
    private String year;
    private String conclusion;
    private String xlName;
    private String schoolName;
    private Date age;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMianmao() {
        return mianmao;
    }

    public void setMianmao(String mianmao) {
        this.mianmao = mianmao;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getApprocalOrgType() {
        return approcalOrgType;
    }

    public void setApprocalOrgType(String approcalOrgType) {
        this.approcalOrgType = approcalOrgType;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getCjType() {
        return cjType;
    }

    public void setCjType(String cjType) {
        this.cjType = cjType;
    }

    public String getCjName() {
        return cjName;
    }

    public void setCjName(String cjName) {
        this.cjName = cjName;
    }

    public String getCjOrgType() {
        return cjOrgType;
    }

    public void setCjOrgType(String cjOrgType) {
        this.cjOrgType = cjOrgType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getXlName() {
        return xlName;
    }

    public void setXlName(String xlName) {
        this.xlName = xlName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }
}
