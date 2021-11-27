/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 课程信息Entity
 * @author jack.xu
 * @version 2020-07-23
 */
public class AffairClassInformation extends DataEntity<AffairClassInformation> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "培训班名称",type = 0, align = 2, sort = 0)
	private String className;		//培训班名称
	@ExcelField(title = "课程编号",type = 0, align = 2, sort = 1)
	private String number;		// 课程编号
	@ExcelField(title = "课程名称", type = 0, align = 2, sort = 2)
	private String name;		// 课程名称
	@ExcelField(title = "授课方式", type = 0,align = 2, sort = 3,dictType = "way_classify")
	private String way;		// 在线/面授
	private Date beganDate;		// 开始时间
	private Date resultDate;		// 结束时间
	@ExcelField(title = "课时", type = 0, align = 2, sort = 5)
	private String classTime; 		//课时
	@ExcelField(title = "学分", type = 0, align = 2, sort = 6)
	private String score;			//学分
	@ExcelField(title = "教官", type = 0, align = 2, sort = 7)
	private String teacher;			//教官
	private String unit;		// 单位
	private String unitId;		// 单位id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "创建人", type = 0, align = 2, sort = 8)
	private String creator;		// 创建人
	@ExcelField(title = "创建时间", type = 0, align = 2, sort = 9)
	private Date createTime;		// 创建时间
	private String creatorUnit;		// 创建人单位
	private String creatorUnitId;		// 创建人单位id
	private String classManageId;		//培训班id
	@ExcelField(title = "开课时间", type = 0, align = 2, sort = 4)
	private String learnTime;

	public String getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(String learnTime) {
		this.learnTime = learnTime;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassTime() {
		return classTime;
	}

	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getClassManageId() {
		return classManageId;
	}

	public void setClassManageId(String classManageId) {
		this.classManageId = classManageId;
	}

	public AffairClassInformation() {
		super();
	}

	public AffairClassInformation(String id){
		super(id);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeganDate() {
		return beganDate;
	}

	public void setBeganDate(Date beganDate) {
		this.beganDate = beganDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorUnit() {
		return creatorUnit;
	}

	public void setCreatorUnit(String creatorUnit) {
		this.creatorUnit = creatorUnit;
	}

	public String getCreatorUnitId() {
		return creatorUnitId;
	}

	public void setCreatorUnitId(String creatorUnitId) {
		this.creatorUnitId = creatorUnitId;
	}

}