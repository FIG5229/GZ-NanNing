/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 子女信息Entity
 * @author cecil.li
 * @version 2019-12-02
 */
public class AffairZxInfo extends DataEntity<AffairZxInfo> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date date;		// 时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位机构id
	@ExcelField(title = "民警姓名", type = 0, align = 2, sort = 4)
	private String name;		// 民警姓名
	private String idNumber;		// 身份证号
	private String sex;		// 民警性别
	@ExcelField(title = "补助类型", type = 0, align = 2, sort = 2,dictType = "affair_zxtype")
	private String type;		// 补助类型
	@ExcelField(title = "补助金额（元）", type = 0, align = 2, sort = 13)
	private Double money;		// 补助金额（元）
	@ExcelField(title = "子女姓名", type = 0, align = 2, sort = 6)
	private String childName;    //子女姓名
	@ExcelField(title = "子女性别", type = 0, align = 2, sort = 7,dictType = "sex")
	private String childSex;     //子女性别
//	@ExcelField(title = "子女出生日期", type = 0, align = 2, sort = 9)
	private Date childBirthday;  //子女出生日期
	private String childType;   //补助类型
	@ExcelField(title = "就读学校", type = 0, align = 2, sort = 8)
	private String childSchoolMajor;  //就读学校（专业）
	@ExcelField(title = "学校类别", type = 0, align = 2, sort = 9,dictType = "affair_zxctype")
	private String childSchoolType;    //学校类别
	@ExcelField(title = "学年制", type = 0, align = 2, sort = 11,dictType = "affair_zx_year_system")
	private String childYearSystem;      //学年制
	@ExcelField(title = "就读年级", type = 0, align = 2, sort = 12)
	private String childGrade;       //就读年级

	private List<AffairZxInfoChild> affairZxInfoChildList = Lists.newArrayList();		// 子表列表

	private String userId;         //获取当前账号单位

	private String filePath;       //文件路径
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 时间
	private Date endDate;		// 结束 时间

	/*9.3反馈更新 新加字段*/
	@ExcelField(title = "所学专业", type = 0, align = 2, sort = 10)
	private String childMajor;	//所学专业

	/*9.7按照模板添加字段*/
	@ExcelField(title = "顺号", type = 0, align = 2, sort = 3)
	private String shun;	//顺号（不知道干啥用的）
	@ExcelField(title = "医保号", type = 0, align = 2, sort = 5)
	private String medicaNumber;	//医保号
	@ExcelField(title = "备注", type = 0, align = 2, sort = 14)
	private String remarks;		// 备注
	//统计分析使用
	//统计分析使用
	private String dateType;
	private Integer year;
	private Date dateStart;
	private Date dateEnd;
	private String month;
	private String label;

	/*前端标识 操作里添加子女*/
	private boolean handleAdd;

	public AffairZxInfo() {
		super();
	}

	public AffairZxInfo(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getChildSex() {
		return childSex;
	}

	public void setChildSex(String childSex) {
		this.childSex = childSex;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getChildBirthday() {
		return childBirthday;
	}

	public void setChildBirthday(Date childBirthday) {
		this.childBirthday = childBirthday;
	}

	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}

	public String getChildSchoolMajor() {
		return childSchoolMajor;
	}

	public void setChildSchoolMajor(String childSchoolMajor) {
		this.childSchoolMajor = childSchoolMajor;
	}

	public String getChildSchoolType() {
		return childSchoolType;
	}

	public void setChildSchoolType(String childSchoolType) {
		this.childSchoolType = childSchoolType;
	}

	public String getChildYearSystem() {
		return childYearSystem;
	}

	public void setChildYearSystem(String childYearSystem) {
		this.childYearSystem = childYearSystem;
	}

	public String getChildGrade() {
		return childGrade;
	}

	public void setChildGrade(String childGrade) {
		this.childGrade = childGrade;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
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

	public String getChildMajor() {
		return childMajor;
	}

	public void setChildMajor(String childMajor) {
		this.childMajor = childMajor;
	}

	public List<AffairZxInfoChild> getAffairZxInfoChildList() {
		return affairZxInfoChildList;
	}

	public void setAffairZxInfoChildList(List<AffairZxInfoChild> affairZxInfoChildList) {
		this.affairZxInfoChildList = affairZxInfoChildList;
	}

	public String getShun() {
		return shun;
	}

	public void setShun(String shun) {
		this.shun = shun;
	}

	public String getMedicaNumber() {
		return medicaNumber;
	}

	public void setMedicaNumber(String medicaNumber) {
		this.medicaNumber = medicaNumber;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isHandleAdd() {
		return handleAdd;
	}

	public void setHandleAdd(boolean handleAdd) {
		this.handleAdd = handleAdd;
	}
}