/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 考勤记录Entity
 * @author mason.xv
 * @version 2020-01-19
 */
public class AffairAttendance extends DataEntity<AffairAttendance> {
	
	private static final long serialVersionUID = 1L;
	private String   unit;        //单位
	private String unitId;     //单位ID
	private Integer year;       //年
	private Integer mouth;        //月
	private String attendancePersonnel;    //考勤员
	private String attendanceIdNumber;     //考勤员身份证号
	private String shPersonnel;       //领导
	private String status;         //审核状态
	private String attendanceId;		// 考勤关联ID
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String userId;

	//前端
	private String name;
	private String IdNumber;
    private List<AffairAttendanceChild> affairAttendanceChildList = Lists.newArrayList();

	public AffairAttendance() {
		super();
	}

	public AffairAttendance(String id){
		super(id);
	}


	public String getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMouth() {
		return mouth;
	}

	public void setMouth(Integer mouth) {
		this.mouth = mouth;
	}

	public String getAttendancePersonnel() {
		return attendancePersonnel;
	}

	public void setAttendancePersonnel(String attendancePersonnel) {
		this.attendancePersonnel = attendancePersonnel;
	}

	public String getAttendanceIdNumber() {
		return attendanceIdNumber;
	}

	public void setAttendanceIdNumber(String attendanceIdNumber) {
		this.attendanceIdNumber = attendanceIdNumber;
	}

	public String getShPersonnel() {
		return shPersonnel;
	}

	public void setShPersonnel(String shPersonnel) {
		this.shPersonnel = shPersonnel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public List<AffairAttendanceChild> getAffairAttendanceChildList() {
        return affairAttendanceChildList;
    }

    public void setAffairAttendanceChildList(List<AffairAttendanceChild> affairAttendanceChildList) {
        this.affairAttendanceChildList = affairAttendanceChildList;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return IdNumber;
	}

	public void setIdNumber(String idNumber) {
		IdNumber = idNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}