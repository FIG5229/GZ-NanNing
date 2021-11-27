/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 伤亡特殊补助金Entity
 * @author cecil.li
 * @version 2020-07-03
 */
public class AffairDeathMoney extends DataEntity<AffairDeathMoney> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 4)
	private Date birth;		// 出生年月
	@ExcelField(title = "行政/技术职务职级", type = 0, align = 2, sort = 5)
	private String level;		// 行政/技术职务职级
	@ExcelField(title = "参加工作时间", type = 0, align = 2, sort = 6)
	private Date workDate;		// 参加工作时间
	@ExcelField(title = "伤亡情况", type = 0, align = 2, sort = 7)
	private String casualties;		// 伤亡情况
	@ExcelField(title = "评定时间", type = 0, align = 2, sort = 8)
	private Date assessDate;		// 评定时间
	@ExcelField(title = "评定情况", type = 0, align = 2, sort = 9)
	private String assessMain;		// 评定情况
	@ExcelField(title = "审批情况", type = 0, align = 2, sort = 10)
	private String approval;		// 审批情况
	@ExcelField(title = "备注", type = 0, align = 2, sort = 11)
	private String remark;		// 备注
	@ExcelField(title = "附件", type = 0, align = 2, sort = 12)
	private String annex;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginAssessDate;		// 开始 评定时间
	private Date endAssessDate;		// 结束 评定时间
	private String userId;
	
	public AffairDeathMoney() {
		super();
	}

	public AffairDeathMoney(String id){
		super(id);
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	
	public String getCasualties() {
		return casualties;
	}

	public void setCasualties(String casualties) {
		this.casualties = casualties;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getAssessDate() {
		return assessDate;
	}

	public void setAssessDate(Date assessDate) {
		this.assessDate = assessDate;
	}
	
	public String getAssessMain() {
		return assessMain;
	}

	public void setAssessMain(String assessMain) {
		this.assessMain = assessMain;
	}
	
	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
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
	
	public Date getBeginAssessDate() {
		return beginAssessDate;
	}

	public void setBeginAssessDate(Date beginAssessDate) {
		this.beginAssessDate = beginAssessDate;
	}
	
	public Date getEndAssessDate() {
		return endAssessDate;
	}

	public void setEndAssessDate(Date endAssessDate) {
		this.endAssessDate = endAssessDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}