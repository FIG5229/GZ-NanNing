/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 举报投诉Entity
 * @author cecil.li
 * @version 2020-06-16
 */
public class AffairReportComplaints extends DataEntity<AffairReportComplaints> {
	
	private static final long serialVersionUID = 1L;
	private Date date;		// 时间
	private String title;		// 标题
	private String unit;		// 责任单位
	private String mainContent;		// 主要内容
	private String annex;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String range;		// 查询范围
	private String responsibleUnit;		// 单位
	private String responsibleUnitId;		// 单位id
	private Date beginDate;		// 开始 时间
	private Date endDate;		// 结束 时间
	private String isTrue;   // 是否属实

	private String[] problemCategoryArr;
	
	public AffairReportComplaints() {
		super();
	}

	public AffairReportComplaints(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}
	
	public String getResponsibleUnit() {
		return responsibleUnit;
	}

	public void setResponsibleUnit(String responsibleUnit) {
		this.responsibleUnit = responsibleUnit;
	}
	
	public String getResponsibleUnitId() {
		return responsibleUnitId;
	}

	public void setResponsibleUnitId(String responsibleUnitId) {
		this.responsibleUnitId = responsibleUnitId;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String[] getProblemCategoryArr() {
		return problemCategoryArr;
	}

	public void setProblemCategoryArr(String[] problemCategoryArr) {
		this.problemCategoryArr = problemCategoryArr;
	}

	public String getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(String isTrue) {
		this.isTrue = isTrue;
	}
}