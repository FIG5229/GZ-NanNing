package com.thinkgem.jeesite.modules.exam.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 考评档案
 * @author kevin.jia,
 * @version 2020/9/16
 */
public class ExamRecord extends DataEntity<ExamRecord> {
    private static final long serialVersionUID = 1L;
    private String year;    //筛选所用字段  年度
    private String startMonth; //筛选所用字段  开始月份
    private String endMonth; //筛选所用字段  结束月份
   // private Set yearSet;     //当前单位 年度集合
    private Map<String,Double> monthSumMap;   //当前单位  各个月度分数  key  月份   value  分数
    private String unitId;   //单位id
    private String unit;     //单位名称
    private List<String> monthList = new ArrayList<>();  // 月度集合  默认长度12个月，前台页面用于遍历展示各个月度分数
    private Double yearSum;           //年度最终分数
    private Double yearEndSum;        //年终分数
    private String grades;            //等次
    public ExamRecord() {
        super();
        for(int i = 1;i<13 ;i++){
            monthList.add(String.valueOf(i));
        }
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

   /* public Set getYearSet() {
        return yearSet;
    }

    public void setYearSet(Set yearSet) {
        this.yearSet = yearSet;
    }*/

    public Map<String, Double> getMonthSumMap() {
        return monthSumMap;
    }

    public void setMonthSumMap(Map<String, Double> monthSumMap) {
        this.monthSumMap = monthSumMap;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<String> monthList) {
        this.monthList = monthList;
    }

    public Double getYearSum() {
        return yearSum;
    }

    public void setYearSum(Double yearSum) {
        this.yearSum = yearSum;
    }

    public Double getYearEndSum() {
        return yearEndSum;
    }

    public void setYearEndSum(Double yearEndSum) {
        this.yearEndSum = yearEndSum;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }
}
