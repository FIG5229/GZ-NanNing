/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班级报名Entity
 * @author kevin.jia
 * @version 2020-07-29
 */
public class AffairClassApply extends DataEntity<AffairClassApply> {
	
	private static final long serialVersionUID = 1L;
	private String classId;		// 培训班id
	private String className;		// 培训班名称
	private String stuName;		// 姓名
	private String stuNameId;		// 人员信息id
	private String stuSex;		// 性别
	private String stuNation;		// 民族
	private String stuIdNum;		// 证件号
	private String stuSysPhoneNum;		// 联系电话(系统预留)
	private String stuUnitId;		// 单位id
	private String stuUnitName;		// 单位名称
	private String stuPhoneNum;		// 手机号码
	private Date stuArrivalTime;		// 到达时间
	private String stuArrivalNum;		// 到达车次/航班
	private String stuArrivalSite;		// 到达车站/机场
	private Date stuBackTime;		// 返程时间
	private String stuBackNum;		// 返程车次/航班
	private String stuBackSite;		// 返程车站/机场
	private String stuIsreserve;		// 是否预定返程火车票
	private String stuPubExmpNum;		// 公免号

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairClassApply() {
		super();
	}

	public AffairClassApply(String id){
		super(id);
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	public String getStuSex() {
		return stuSex;
	}

	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}
	
	public String getStuNation() {
		return stuNation;
	}

	public void setStuNation(String stuNation) {
		this.stuNation = stuNation;
	}
	
	public String getStuIdNum() {
		return stuIdNum;
	}

	public void setStuIdNum(String stuIdNum) {
		this.stuIdNum = stuIdNum;
	}
	
	public String getStuSysPhoneNum() {
		return stuSysPhoneNum;
	}

	public void setStuSysPhoneNum(String stuSysPhoneNum) {
		this.stuSysPhoneNum = stuSysPhoneNum;
	}
	
	public String getStuUnitId() {
		return stuUnitId;
	}

	public void setStuUnitId(String stuUnitId) {
		this.stuUnitId = stuUnitId;
	}
	
	public String getStuUnitName() {
		return stuUnitName;
	}

	public void setStuUnitName(String stuUnitName) {
		this.stuUnitName = stuUnitName;
	}
	
	public String getStuPhoneNum() {
		return stuPhoneNum;
	}

	public void setStuPhoneNum(String stuPhoneNum) {
		this.stuPhoneNum = stuPhoneNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStuArrivalTime() {
		return stuArrivalTime;
	}

	public void setStuArrivalTime(Date stuArrivalTime) {
		this.stuArrivalTime = stuArrivalTime;
	}
	
	public String getStuArrivalNum() {
		return stuArrivalNum;
	}

	public void setStuArrivalNum(String stuArrivalNum) {
		this.stuArrivalNum = stuArrivalNum;
	}
	
	public String getStuArrivalSite() {
		return stuArrivalSite;
	}

	public void setStuArrivalSite(String stuArrivalSite) {
		this.stuArrivalSite = stuArrivalSite;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStuBackTime() {
		return stuBackTime;
	}

	public void setStuBackTime(Date stuBackTime) {
		this.stuBackTime = stuBackTime;
	}
	
	public String getStuBackNum() {
		return stuBackNum;
	}

	public void setStuBackNum(String stuBackNum) {
		this.stuBackNum = stuBackNum;
	}
	
	public String getStuBackSite() {
		return stuBackSite;
	}

	public void setStuBackSite(String stuBackSite) {
		this.stuBackSite = stuBackSite;
	}
	
	public String getStuIsreserve() {
		return stuIsreserve;
	}

	public void setStuIsreserve(String stuIsreserve) {
		this.stuIsreserve = stuIsreserve;
	}
	
	public String getStuPubExmpNum() {
		return stuPubExmpNum;
	}

	public void setStuPubExmpNum(String stuPubExmpNum) {
		this.stuPubExmpNum = stuPubExmpNum;
	}

	public String getStuNameId() {
		return stuNameId;
	}

	public void setStuNameId(String stuNameId) {
		this.stuNameId = stuNameId;
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