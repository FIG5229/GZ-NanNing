/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 离退信息集Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelRetreatSum extends DataEntity<PersonnelRetreatSum> {
    private static final long serialVersionUID = 1L;

    private Integer nowMan;        //现员
    private Integer man ;          //其中男
    private Integer woman;          //其中女
    private Integer avgAge;       //平均年龄
    private Integer age1;              //55-60岁
    private Integer age2;    //61-65岁
    private Integer age3;     //66-70岁
    private Integer age4;     //71-75岁
    private Integer age5;     //76-80岁
    private Integer age6;     //81岁以上
    private Integer liXiu;   //离休
    private Integer tuiXiu;   //退休
    private Integer sum;          //合计
    private Date startDate;        //开始时间
    private Date endDate;         //结束时间
    private String unitName;      //单位名字
    private String unitId;	//单位机构id
    public Integer getNowMan() {
        return nowMan;
    }

    public void setNowMan(Integer nowMan) {
        this.nowMan = nowMan;
    }

    public Integer getMan() {
        return man;
    }

    public void setMan(Integer man) {
        this.man = man;
    }

    public Integer getWoman() {
        return woman;
    }

    public void setWoman(Integer woman) {
        this.woman = woman;
    }

    public Integer getAvgAge() {
        return avgAge;
    }

    public void setAvgAge(Integer avgAge) {
        this.avgAge = avgAge;
    }

    public Integer getAge1() {
        return age1;
    }

    public void setAge1(Integer age1) {
        this.age1 = age1;
    }

    public Integer getAge2() {
        return age2;
    }

    public void setAge2(Integer age2) {
        this.age2 = age2;
    }

    public Integer getAge3() {
        return age3;
    }

    public void setAge3(Integer age3) {
        this.age3 = age3;
    }

    public Integer getAge4() {
        return age4;
    }

    public void setAge4(Integer age4) {
        this.age4 = age4;
    }

    public Integer getAge5() {
        return age5;
    }

    public void setAge5(Integer age5) {
        this.age5 = age5;
    }

    public Integer getAge6() {
        return age6;
    }

    public void setAge6(Integer age6) {
        this.age6 = age6;
    }

    public Integer getLiXiu() {
        return liXiu;
    }

    public void setLiXiu(Integer liXiu) {
        this.liXiu = liXiu;
    }

    public Integer getTuiXiu() {
        return tuiXiu;
    }

    public void setTuiXiu(Integer tuiXiu) {
        this.tuiXiu = tuiXiu;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}