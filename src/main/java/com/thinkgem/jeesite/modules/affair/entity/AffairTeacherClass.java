/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 班主任培训班管理Entity
 * @author alan.wu
 * @version 2020-07-08
 */
public class AffairTeacherClass extends DataEntity<AffairTeacherClass> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "培训班名称", type = 0, align = 2, sort = 0)
	private String className;		// 培训班名称

	private String classId;		// 培训班id

	@ExcelField(title = "渠道", type = 0, align = 2, sort = 1)
	private String way;		// 渠道
	@ExcelField(title = "培训层次", type = 0, align = 2, sort = 2)
	private String type;		// 培训层次
	@ExcelField(title = "培训项目", type = 0, align = 2, sort = 3)
	private String project;		// 培训项目
	@ExcelField(title = "报名开始时间", type = 0, align = 2, sort = 4)
	private Date applyBeginTime;		// 报名开始时间
	@ExcelField(title = "报名结束时间", type = 0, align = 2, sort = 5)
	private Date applyEndTime;		// 报名结束时间
	@ExcelField(title = "主办部门", type = 0, align = 2, sort = 6)
	private String department;		// 主办部门
	@ExcelField(title = "培训班状态", type = 0, align = 2, sort = 7)
	private String state;		// 培训班状态
	@ExcelField(title = "培训开始时间", type = 0, align = 2, sort = 8)
	private Date trainBeginTime;		//培训开始时间
	@ExcelField(title = "培训结束时间", type = 0, align = 2, sort = 9)
	private Date trainEndTime;			//培训结束时间
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTrainBeginTime() {
		return trainBeginTime;
	}

	public void setTrainBeginTime(Date trainBeginTime) {
		this.trainBeginTime = trainBeginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTrainEndTime() {
		return trainEndTime;
	}

	public void setTrainEndTime(Date trainEndTime) {
		this.trainEndTime = trainEndTime;
	}

	public AffairTeacherClass() {
		super();
	}

	public AffairTeacherClass(String id){
		super(id);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyBeginTime() {
		return applyBeginTime;
	}

	public void setApplyBeginTime(Date applyBeginTime) {
		this.applyBeginTime = applyBeginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}





	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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