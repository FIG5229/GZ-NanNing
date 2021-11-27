/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 报名人员Entity
 * @author alan.wu
 * @version 2020-07-10
 */
public class AffairApplyPersonnel extends DataEntity<AffairApplyPersonnel> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位

	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 2)
	private String job;		// 职务
	@ExcelField(title = "报名状态", type = 0, align = 2, sort = 3)
	private String applyState;		// 报名状态
	@ExcelField(title = "审批人", type = 0, align = 2, sort = 4)
	private String approver;		// 审批人6
	@ExcelField(title = "审批意见", type = 0, align = 2, sort = 5)
	private String approvalOpinion;		// 审批意见
	@ExcelField(title = "手机号", type = 0, align = 2, sort = 6)
	private String phone;		// 手机号
	@ExcelField(title = "车次", type = 0, align = 2, sort = 7)
	private String trip;		// 车次
	@ExcelField(title = "车站", type = 0, align = 2, sort = 8)
	private String station;		//车站
	@ExcelField(title = "公免号", type = 0, align = 2, sort = 9)
	private String adFree;		// 公免号
	private String classId;		// 班级id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public AffairApplyPersonnel() {
		super();
	}

	public AffairApplyPersonnel(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getApplyState() {
		return applyState;
	}

	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}
	
	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}
	
	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getTrip() {
		return trip;
	}

	public void setTrip(String trip) {
		this.trip = trip;
	}
	
	public String getAdFree() {
		return adFree;
	}

	public void setAdFree(String adFree) {
		this.adFree = adFree;
	}
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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
	
}