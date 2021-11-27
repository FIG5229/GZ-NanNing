/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 请假信息Entity
 * @author mason.xv
 * @version 2019-11-27
 */
public class AffairQj extends DataEntity<AffairQj> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "年份", type = 0, align = 2, sort = 0)
	private Integer year;   // 年份
	@ExcelField(title = "月份", type = 0, align = 2, sort = 1)
	private Integer month;   //  月份
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 4)
	private String unit;		// 单位
	private String unitId;		// 单位id
	/*@ExcelField(title = "部门", type = 0, align = 2, sort = 3)*/
	private String department;		// 部门
	private String departmentId;		// 部门id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 5)
	private String job;		// 职务
	@ExcelField(title = "请假日期", type = 0, align = 2, sort = 6)
	private Date qjDate;		// 请假日期
	@ExcelField(title = "休假种类", type = 0, align = 2, sort = 7, dictType="personnel_xjtype")
	private String type;		// 休假种类
	@ExcelField(title = "休假开始时间", type = 0, align = 2, sort = 8)
	private Date startTime;		// 休假开始时间
	@ExcelField(title = "休假结束时间", type = 0, align = 2, sort = 9)
	private Date endTime;		// 休假结束时间
	@ExcelField(title = "实际休假天数", type = 0, align = 2, sort = 10)
	private Double qjDay;		// 实际休假天数
	@ExcelField(title = "请假备注", type = 0, align = 2, sort = 11)
	private String qjRemark;		// 请假备注
	private String ljLeave;
	private Double summary;

	private String status;		// 审核状态
	private String depOpinion;		// 局、处领导意见
	private String hrOpinion;		// 人事意见
	private String leaderOpinion;		// 部门意见
	private Date xjDate;		// 销假日期
	private Date resumeTime;		// 恢复工作时间
	private Double ghycDay;		// 提前归还或延长之日数
	private String xjRemark;		// 销假备注
	private String explain;		// 销假说明
	private String depStatus;		// 部门审核是否通过（1：通过 0：不通过）
	private String hrStatus;		// 人事审核是否通过（1：通过 0：不通过）
	private String leaderStatus;		// 局、处领导审核是否通过（1：通过 0：不通过）
    private String shPerson;       //审核人
	private String userId;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;      //统计查询用开始时间
	private Date endDate;        //统计查询用结束时间
	private String mType;         //用来过滤查询条件
	private Date startQjDate;   //请假开始时间
	private Date endQjDate;    // 请假结束时间
	private String flag;
	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public Double getQjDay() {
		return qjDay;
	}

	public void setQjDay(Double qjDay) {
		this.qjDay = qjDay;
	}

	public Double getGhycDay() {
		return ghycDay;
	}

	public void setGhycDay(Double ghycDay) {
		this.ghycDay = ghycDay;
	}

	public AffairQj() {
		super();
	}

	public AffairQj(String id){
		super(id);
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
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd hh-mm-ss")
	public Date getQjDate() {
		return qjDate;
	}

	public void setQjDate(Date qjDate) {
		this.qjDate = qjDate;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd hh-mm-ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd hh-mm-ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getQjRemark() {
		return qjRemark;
	}

	public void setQjRemark(String qjRemark) {
		this.qjRemark = qjRemark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepOpinion() {
		return depOpinion;
	}

	public void setDepOpinion(String depOpinion) {
		this.depOpinion = depOpinion;
	}
	
	public String getHrOpinion() {
		return hrOpinion;
	}

	public void setHrOpinion(String hrOpinion) {
		this.hrOpinion = hrOpinion;
	}
	
	public String getLeaderOpinion() {
		return leaderOpinion;
	}

	public void setLeaderOpinion(String leaderOpinion) {
		this.leaderOpinion = leaderOpinion;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd hh-mm-ss")
	public Date getXjDate() {
		return xjDate;
	}

	public void setXjDate(Date xjDate) {
		this.xjDate = xjDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd hh-mm-ss")
	public Date getResumeTime() {
		return resumeTime;
	}

	public void setResumeTime(Date resumeTime) {
		this.resumeTime = resumeTime;
	}

	public String getXjRemark() {
		return xjRemark;
	}

	public void setXjRemark(String xjRemark) {
		this.xjRemark = xjRemark;
	}
	
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public String getDepStatus() {
		return depStatus;
	}

	public void setDepStatus(String depStatus) {
		this.depStatus = depStatus;
	}
	
	public String getHrStatus() {
		return hrStatus;
	}

	public void setHrStatus(String hrStatus) {
		this.hrStatus = hrStatus;
	}
	
	public String getLeaderStatus() {
		return leaderStatus;
	}

	public void setLeaderStatus(String leaderStatus) {
		this.leaderStatus = leaderStatus;
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

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getLjLeave() {
		return ljLeave;
	}

	public void setLjLeave(String ljLeave) {
		this.ljLeave = ljLeave;
	}

	public Date getStartQjDate() {
		return startQjDate;
	}

	public void setStartQjDate(Date startQjDate) {
		this.startQjDate = startQjDate;
	}

	public Date getEndQjDate() {
		return endQjDate;
	}

	public void setEndQjDate(Date endQjDate) {
		this.endQjDate = endQjDate;
	}

	public Double getSummary() {
		return summary;
	}

	public void setSummary(Double summary) {
		this.summary = summary;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}