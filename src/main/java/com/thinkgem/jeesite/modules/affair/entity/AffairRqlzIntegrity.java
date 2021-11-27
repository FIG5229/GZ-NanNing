/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 任前廉政鉴定Entity
 * @author cecil.li
 * @version 2019-11-08
 */
public class AffairRqlzIntegrity extends DataEntity<AffairRqlzIntegrity> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;           //姓名
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String lzUnit;     //单位
	private String lzUnitId;     //单位id
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 2)
	private String job;        //职务
	@ExcelField(title = "鉴定类型", type = 0, align = 2, sort = 3,dictType="affair_lzjd")
	private String type;          //鉴定类型
	@ExcelField(title = "鉴定时间", type = 0, align = 2, sort = 4)
	private Date foundDate;		// 鉴定时间
	@ExcelField(title = "鉴定单位", type = 0, align = 2, sort = 5,dictType="affair_xfjb_unit")
	private String jdUnit;        //鉴定单位
	@ExcelField(title = "鉴定结论", type = 0, align = 2, sort = 6)
	private String mainContent;		// 鉴定结论
	@ExcelField(title = "是否同意", type = 0, align = 2, sort = 7,dictType="yes_no")
	private String isAgree;      //是否同意
	private String title;		// 标题
	private String annex;		// 附件
	private String approvalStatus;		// 审核状态
	private String reviewer;		// 审核人
	private String auditOpinion;		// 审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	private Date startYear;   //本年度
	private String otherYear;  //其他年份

	private String dateType;

	private Integer year;

	private String dateStart;

	private String dateEnd;

	private String month;

	private String jdType;
	
	public AffairRqlzIntegrity() {
		super();
	}

	public AffairRqlzIntegrity(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}
	
	public String getMainContent() {
		return mainContent;
	}

	public void setMainContent(String mainContent) {
		this.mainContent = mainContent;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}
	
	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
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
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJdUnit() {
		return jdUnit;
	}

	public void setJdUnit(String jdUnit) {
		this.jdUnit = jdUnit;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getLzUnit() {
		return lzUnit;
	}

	public void setLzUnit(String lzUnit) {
		this.lzUnit = lzUnit;
	}

	public String getLzUnitId() {
		return lzUnitId;
	}

	public void setLzUnitId(String lzUnitId) {
		this.lzUnitId = lzUnitId;
	}

	@JsonFormat(pattern = "yyyy")
	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	public String getOtherYear() {
		return otherYear;
	}

	public void setOtherYear(String otherYear) {
		this.otherYear = otherYear;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getJdType() {
		return jdType;
	}

	public void setJdType(String jdType) {
		this.jdType = jdType;
	}
}