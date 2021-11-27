/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 健康体检Entity
 * @author mason.xv
 * @version 2020-03-23
 */
public class AffairHealthCheckup extends DataEntity<AffairHealthCheckup> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位ID
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "体检时间", type = 0, align = 2, sort = 2)
	private Date tjDate;		// 体检时间
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "年龄", type = 0, align = 2, sort = 4)
	private String age;		// 年龄
	@ExcelField(title = "体检情况", type = 0, align = 2, sort = 6)
	private String tjQingkuang;		// 体检情况
	@ExcelField(title = "复检原因", type = 0, align = 2, sort = 7)
	private String fjReason;		// 复检原因
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "患病史", type = 0, align = 2, sort = 5)
	private String type;			//患病史种类
	private String userId;         //获取当前账号单位
	private Date beginTjDate;		// 开始 体检时间
	private Date endTjDate;		// 结束 体检时间

	//新加字段
	private String idNumber;
	private String userOffice;


	//统计分析使用
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;

	@EsIgnore
	private String[] typeArr;
	
	public AffairHealthCheckup() {
		super();
	}

	public AffairHealthCheckup(String id){
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTjDate() {
		return tjDate;
	}

	public void setTjDate(Date tjDate) {
		this.tjDate = tjDate;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getTjQingkuang() {
		return tjQingkuang;
	}

	public void setTjQingkuang(String tjQingkuang) {
		this.tjQingkuang = tjQingkuang;
	}
	
	public String getFjReason() {
		return fjReason;
	}

	public void setFjReason(String fjReason) {
		this.fjReason = fjReason;
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
	
	public Date getBeginTjDate() {
		return beginTjDate;
	}

	public void setBeginTjDate(Date beginTjDate) {
		this.beginTjDate = beginTjDate;
	}
	
	public Date getEndTjDate() {
		return endTjDate;
	}

	public void setEndTjDate(Date endTjDate) {
		this.endTjDate = endTjDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getTypeArr() {
		return typeArr;
	}

	public void setTypeArr(String[] typeArr) {
		this.typeArr = typeArr;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}


	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getUserOffice() {
		return userOffice;
	}

	public void setUserOffice(String userOffice) {
		this.userOffice = userOffice;
	}
}