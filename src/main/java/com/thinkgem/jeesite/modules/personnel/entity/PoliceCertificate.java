package com.thinkgem.jeesite.modules.personnel.entity;

import com.thinkgem.jeesite.common.elasticsearch.annotation.HighLight;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 需办理警察证人员信息集
 * 单独的javaBean
 * 数据从PersonnelBase 查询
 */

public class PoliceCertificate {


    @ExcelField(title = "序号", type = 0, align = 2, sort = 0)
    private String index;
    @ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
    private String name;		            // 姓名
    @ExcelField(title = "出生日期", type = 0, align = 2, sort = 2)
    private Date birthday;		            // 出生日期
    @ExcelField(title = "警号", type = 0, align = 2, sort = 3)
    @HighLight
    private String policeIdNumber;		    // 警号
    @ExcelField(title = "血型", type = 0, align = 2, sort = 4)
    private String bloodType;		        // 血型
    @HighLight
    @ExcelField(title = "身份证号", type = 0, align = 2, sort = 5)
    private String idNumber;		        // 身份证号
    //警衔
    @ExcelField(title = "警衔", type = 0, align = 2, sort = 6)
    private String rank;		        // 警衔

    @ExcelField(title = "性别", type = 0, align = 2, sort = 7, dictType="sex")
    private String sex;						//

    @ExcelField(title = "工作单位名称", type = 0, align = 2, sort = 8)
    private String workunitName;		    // 工作单位名称
    @ExcelField(title = "职务简称", type = 0, align = 2, sort = 9)
    private String jobAbbreviation;		    // 职务简称

    @ExcelField(title = "机构代码", type = 0, align = 2, sort = 10)
    private String unitCode;		    // 机构代码


    //前段
    private Date startEndDate;
    private Date endEndDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPoliceIdNumber() {
        return policeIdNumber;
    }

    public void setPoliceIdNumber(String policeIdNumber) {
        this.policeIdNumber = policeIdNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWorkunitName() {
        return workunitName;
    }

    public void setWorkunitName(String workunitName) {
        this.workunitName = workunitName;
    }

    public String getJobAbbreviation() {
        return jobAbbreviation;
    }

    public void setJobAbbreviation(String jobAbbreviation) {
        this.jobAbbreviation = jobAbbreviation;
    }

    public Date getStartEndDate() {
        return startEndDate;
    }

    public void setStartEndDate(Date startEndDate) {
        this.startEndDate = startEndDate;
    }

    public Date getEndEndDate() {
        return endEndDate;
    }

    public void setEndEndDate(Date endEndDate) {
        this.endEndDate = endEndDate;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setunitCode(String unitCode) {
        this.unitCode = unitCode;
    }
}
