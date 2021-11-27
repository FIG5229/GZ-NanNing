/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 年度考核信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
	public class PersonnelYearCheck extends DataEntity<PersonnelYearCheck> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "考核年度", type = 0, align = 2, sort = 1)
	private Integer year;		// 考核年度
	@ExcelField(title = "考核结论", type = 0, align = 2, sort = 2)
	private String conclusion;		// 考核结论
	@ExcelField(title = "考核日期", type = 0, align = 2, sort = 3)
	private Date date;		// 考核日期
	@ExcelField(title = "考核单位名称", type = 0, align = 2, sort = 4)
	private String unit;		// 考核单位名称
	@ExcelField(title = "考核单位代码", type = 0, align = 2, sort = 5)
	private String unitCode;		// 考核单位代码
	@ExcelField(title = "参加考核的应到人数", type = 0, align = 2, sort = 6)
	private Integer shouldNum;		// 参加考核的应到人数
	@ExcelField(title = "实到考核人数", type = 0, align = 2, sort = 7)
	private Integer realNum;		// 实到考核人数
	@ExcelField(title = "考核优秀得票数", type = 0, align = 2, sort = 8)
	private Integer yxVoteNum;		// 考核优秀得票数
	@ExcelField(title = "考核称职(合格)得票数", type = 0, align = 2, sort = 9)
	private Integer czVoteNum;		// 考核称职(合格)得票数
	@ExcelField(title = "考核基本称职(基本合格)得票数", type = 0, align = 2, sort = 10)
	private Integer jbczVoteNum;		// 考核基本称职(基本合格)得票数
	@ExcelField(title = "考核不称职(不合格)得票数", type = 0, align = 2, sort = 11)
	private Integer bczVoteNum;		// 考核不称职(不合格)得票数
	@ExcelField(title = "弃权票数", type = 0, align = 2, sort = 12)
	private Integer qqVoteNum;		// 弃权票数
	@ExcelField(title = "考核组成员", type = 0, align = 2, sort = 13)
	private String member;		// 考核组成员
	@ExcelField(title = "考核备注", type = 0, align = 2, sort = 14)
	private String remark;		// 考核备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String unitId;		// 考核单位id
	private String personName;  // 姓名

	private Date startDate;
	private Date endDate;
	
	public PersonnelYearCheck() {
		super();
	}

	public PersonnelYearCheck(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
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
	
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public Integer getShouldNum() {
		return shouldNum;
	}

	public void setShouldNum(Integer shouldNum) {
		this.shouldNum = shouldNum;
	}
	
	public Integer getRealNum() {
		return realNum;
	}

	public void setRealNum(Integer realNum) {
		this.realNum = realNum;
	}
	
	public Integer getYxVoteNum() {
		return yxVoteNum;
	}

	public void setYxVoteNum(Integer yxVoteNum) {
		this.yxVoteNum = yxVoteNum;
	}
	
	public Integer getCzVoteNum() {
		return czVoteNum;
	}

	public void setCzVoteNum(Integer czVoteNum) {
		this.czVoteNum = czVoteNum;
	}
	
	public Integer getJbczVoteNum() {
		return jbczVoteNum;
	}

	public void setJbczVoteNum(Integer jbczVoteNum) {
		this.jbczVoteNum = jbczVoteNum;
	}
	
	public Integer getBczVoteNum() {
		return bczVoteNum;
	}

	public void setBczVoteNum(Integer bczVoteNum) {
		this.bczVoteNum = bczVoteNum;
	}
	
	public Integer getQqVoteNum() {
		return qqVoteNum;
	}

	public void setQqVoteNum(Integer qqVoteNum) {
		this.qqVoteNum = qqVoteNum;
	}
	
	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
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
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}