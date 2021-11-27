package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

public class AffairNewsCount extends DataEntity<AffairNewsCount> {

    //@ExcelField(title = "单位",type = 0,align = 2,sort = 0)
    private String unit;
    private String unitId;
    //@ExcelField(title = "作者",type = 0,align = 2,sort = 0)
    private String author;
    private String type;
    //@ExcelField(title = "所属人",type = 0,align = 2,sort = 0)
    private String name;
    @ExcelField(title = "单位/作者/所属人",type = 0,align = 2,sort = 0)
    private String joinThree;
    private String idNumber;
    @ExcelField(title = "数量",type = 0,align = 2,sort = 6)
    private Integer sum;
    @ExcelField(title = "中央级（中央电视台、新华社/中新社、中央人民广播电台、中央级报纸）",type = 1,align = 2,sort = 1)
    private String sum1;
    @ExcelField(title = "省部级（省卫视、专题、其他）",type = 1,align = 2,sort = 2)
    private String sum2;
    @ExcelField(title = "地市级",type = 1,align = 2,sort = 3)
    private String sum3;
    @ExcelField(title = "各大网络",type = 1,align = 2,sort = 4)
    private String sum4;
    @ExcelField(title = "新媒体平台",type = 1,align = 2,sort = 5)
    private String sum5;
    private String flag;
    private String flagTwo;
    private String date;

    private String officeId;

    public AffairNewsCount() {
        super();
    }


    public String getSum5() {
        return sum5;
    }

    public void setSum5(String sum5) {
        this.sum5 = sum5;
    }

    public String getJoinThree() {
        return joinThree;
    }

    public void setJoinThree(String joinThree) {
        this.joinThree = joinThree;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlagTwo() {
        return flagTwo;
    }

    public void setFlagTwo(String flagTwo) {
        this.flagTwo = flagTwo;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getSum1() {
        return sum1;
    }

    public void setSum1(String sum1) {
        this.sum1 = sum1;
    }

    public String getSum2() {
        return sum2;
    }

    public void setSum2(String sum2) {
        this.sum2 = sum2;
    }

    public String getSum3() {
        return sum3;
    }

    public void setSum3(String sum3) {
        this.sum3 = sum3;
    }

    public String getSum4() {
        return sum4;
    }

    public void setSum4(String sum4) {
        this.sum4 = sum4;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }
}
