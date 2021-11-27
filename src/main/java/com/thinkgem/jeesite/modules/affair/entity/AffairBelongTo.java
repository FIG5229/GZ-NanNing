/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党员组织隶属Entity
 * @author eav.liu
 * @version 2019-11-11
 */
public class AffairBelongTo extends DataEntity<AffairBelongTo> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "党员姓名", type = 0, align = 2, sort = 0)
	private String name;		// 党员姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 1, dictType = "sex")
	private String sex;		// 性别
	private String policeNo;		// 警号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "所在党支部", type = 0, align = 2, sort = 3)
	private String partyBranch;		// 所在党支部

	private String partyBranchId;		// 所在党支部机构id

	@ExcelField(title = "进入支部时间", type = 0, align = 2, sort = 4)
	private Date enterDate;		// 进入支部时间
	@ExcelField(title = "进入支部时间", type = 0, align = 2, sort = 5)
	private Date leaveDate;		// 进入支部时间
	@ExcelField(title = "是否转离支部", type = 0, align = 2, sort = 6, dictType = "yes_no")
	private String isLeave;		// 是否转离支部
	@ExcelField(title = "转离支部类型", type = 0, align = 2, sort = 7, dictType = "affair_leave_type")
	private String leaveType;		// 转离支部类型

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date enterStartDate;

	private Date enterEndDate;

	private Date leaveStartDate;

	private Date leaveEndDate;

	private String treeId;	//接受前端传来的左侧树的id
	
	public AffairBelongTo() {
		super();
	}

	public AffairBelongTo(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	
	public String getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}
	
	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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

	public Date getEnterStartDate() {
		return enterStartDate;
	}

	public void setEnterStartDate(Date enterStartDate) {
		this.enterStartDate = enterStartDate;
	}

	public Date getEnterEndDate() {
		return enterEndDate;
	}

	public void setEnterEndDate(Date enterEndDate) {
		this.enterEndDate = enterEndDate;
	}

	public Date getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(Date leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public Date getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(Date leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
}