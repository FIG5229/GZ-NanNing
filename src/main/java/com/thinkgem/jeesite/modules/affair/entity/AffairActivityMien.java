/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 活动风采Entity
 * @author kevin.jia
 * @version 2020-07-27
 */
public class AffairActivityMien extends DataEntity<AffairActivityMien> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "活动名称",type = 0,align = 2,sort = 1)
	private String name;		// 活动名称
	@ExcelField(title = "举办单位",type = 0,align = 2,sort = 2)
	private String unit;		// 举办单位
	private String unitId;		// 举办单位id
	@ExcelField(title = "开始时间",type = 0,align = 2,sort = 3)
	private Date startDate;		// 开始时间
	@ExcelField(title = "结束时间",type = 0,align = 2,sort = 4)
	private Date endDate;		// 结束时间
	@ExcelField(title = "活动地点",type = 0,align = 2,sort = 5)
	private String place;		// 活动地点
	@ExcelField(title = "参与人员",type = 0,align = 2,sort = 6)
	private String joinPerson;		// 参与人员
	@ExcelField(title = "简要情况",type = 0,align = 2,sort = 7)
	private String brief;		// 简要情况
	private String appendfile;		// 附件
	private String juCheckMan;		// 局审核人
	private String chuCheckMan;		// 处审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态
	private String juCheckId;		// 局审核id
	private String chuCheckId;		// 处审核id
	private String submitId;		// 提交人id
	private String opinion;         // 审核意见
	private String userId;			//记录当前登录用户id
	private String officeId;        //记录当前用户部门id
	@ExcelField(title = "活动内容",type = 0,align = 2,sort = 8)
	private String content;		    // 内容
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	public AffairActivityMien() {
		super();
	}

	public AffairActivityMien(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getJoinPerson() {
		return joinPerson;
	}

	public void setJoinPerson(String joinPerson) {
		this.joinPerson = joinPerson;
	}
	
	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	public String getAppendfile() {
		return appendfile;
	}

	public void setAppendfile(String appendfile) {
		this.appendfile = appendfile;
	}
	
	public String getJuCheckMan() {
		return juCheckMan;
	}

	public void setJuCheckMan(String juCheckMan) {
		this.juCheckMan = juCheckMan;
	}
	
	public String getChuCheckMan() {
		return chuCheckMan;
	}

	public void setChuCheckMan(String chuCheckMan) {
		this.chuCheckMan = chuCheckMan;
	}
	
	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}
	
	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	
	public String getJuCheckId() {
		return juCheckId;
	}

	public void setJuCheckId(String juCheckId) {
		this.juCheckId = juCheckId;
	}
	
	public String getChuCheckId() {
		return chuCheckId;
	}

	public void setChuCheckId(String chuCheckId) {
		this.chuCheckId = chuCheckId;
	}
	
	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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