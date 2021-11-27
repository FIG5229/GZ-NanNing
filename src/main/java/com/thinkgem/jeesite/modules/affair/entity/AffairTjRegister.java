/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 团籍注册Entity
 * @author cecil.li
 * @version 2019-11-20
 */
public class AffairTjRegister extends DataEntity<AffairTjRegister> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "警号", type = 0, align = 2, sort = 0)
	private String policeNo;		// 警号
	@ExcelField(title = "所在团组织", type = 0, align = 2, sort = 1)
	private String partyBranch;		// 所在团组织
	private String partyBranchId;		// 所在团组织id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;		// 身份证号
	@ExcelField(title = "出生日期", type = 0, align = 2, sort = 5)
	private Date birthday;	//出生日期
//	@ExcelField(title = "注册时间", type = 0, align = 2, sort = 6)
	private Date date;		// 注册时间
	@ExcelField(title = "团内职务", type = 0, align = 2, sort = 6, dictType="affair_tnjob")
	private String job;		// 团内职务

	@ExcelField(title = "备注", type = 0, align = 2, sort = 7)
	private String remark;		// 备注
	@ExcelField(title = "排序", type = 0, align = 2, sort = 8)
	private String sort;
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;

	private Boolean flag;	//true:人才青年库新增的人员在人员信息表中没有 false:人才青年库新增的人员在人员信息表中有

	//统计分析使用 点击图标查看明细
	private String education;
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;

	private String officeId;

	
	public AffairTjRegister() {
		super();
	}

	public AffairTjRegister(String id){
		super(id);
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
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

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}