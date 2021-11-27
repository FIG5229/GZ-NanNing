package com.thinkgem.jeesite.modules.personnel.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 人才库Entity
 * @author tomfu
 * @version 2020-10-09
 */
public class PersonnelTalents extends DataEntity<PersonnelTalents> {
    //public class PersonnelSupplement   {

    private String unit;       //单位
    private String name;        //姓名
    private String skill;       //特长
    private String idNumber;       //身份证号
    private String birthday;       //生日
    private String sex;       //性别
    private String personnelName;       //性别
    private String unitId;     //单位id


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
