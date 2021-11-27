/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 考核成绩Entity
 * @author cecil.li
 * @version 2020-03-22
 */
public class AffairExamManagement extends DataEntity<AffairExamManagement> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 2)
	private String sex;		// 性别
	@ExcelField(title = "年龄", type = 0, align = 2, sort = 3)
	private String age;		// 年龄
	@ExcelField(title = "单位", type = 0, align = 2, sort = 4)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 5)
	private String job;		// 职务
	@ExcelField(title = "参加考试时间", type = 0, align = 2, sort = 6)
	private Date joinDate;		// 参加考试时间
	@ExcelField(title = "考试内容", type = 0, align = 2, sort = 7)
	private String content;		// 考试内容
	@ExcelField(title = "考试成绩", type = 0, align = 2, sort = 8)
	private String score;		// 考试成绩
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginJoinDate;		// 开始 参加考试时间
	private Date endJoinDate;		// 结束 参加考试时间
	private String qxUnitId;     //权限需要id
	private String lookId;
	
	public AffairExamManagement() {
		super();
	}

	public AffairExamManagement(String id){
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
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
	public Date getBeginJoinDate() {
		return beginJoinDate;
	}

	public void setBeginJoinDate(Date beginJoinDate) {
		this.beginJoinDate = beginJoinDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndJoinDate() {
		return endJoinDate;
	}

	public void setEndJoinDate(Date endJoinDate) {
		this.endJoinDate = endJoinDate;
	}

	public String getQxUnitId() {
		return qxUnitId;
	}

	public void setQxUnitId(String qxUnitId) {
		this.qxUnitId = qxUnitId;
	}

	public String getLookId() {
		return lookId;
	}

	public void setLookId(String lookId) {
		this.lookId = lookId;
	}
}