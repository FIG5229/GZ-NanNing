/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 信息上报Entity
 * @author cecil.li
 * @version 2020-12-29
 */
public class AffairInformationReport extends DataEntity<AffairInformationReport> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "时间", type = 0, align = 2, sort = 1)
	private Date date;		// 时间
	@ExcelField(title = "信息动态（篇）", type = 0, align = 2, sort = 2)
	private String informationDynamic;		// 信息动态（篇）
	@ExcelField(title = "工作简报（篇）", type = 0, align = 2, sort = 3)
	private String briefingOnWork;		// 工作简报（篇）
	@ExcelField(title = "调研文章（篇）", type = 0, align = 2, sort = 4)
	private String researchArticles;		// 调研文章（篇）
	@ExcelField(title = "总结（篇）", type = 0, align = 2, sort = 5)
	private String summary;		// 总结（篇）
	@ExcelField(title = "其它", type = 0, align = 2, sort = 6)
	private String other;		// 其它
	@ExcelField(title = "排名", type = 0, align = 2, sort = 7)
	private String rank;		// 排名
	@ExcelField(title = "备注", type = 0, align = 2, sort = 8)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public AffairInformationReport() {
		super();
	}

	public AffairInformationReport(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getInformationDynamic() {
		return informationDynamic;
	}

	public void setInformationDynamic(String informationDynamic) {
		this.informationDynamic = informationDynamic;
	}
	
	public String getBriefingOnWork() {
		return briefingOnWork;
	}

	public void setBriefingOnWork(String briefingOnWork) {
		this.briefingOnWork = briefingOnWork;
	}
	
	public String getResearchArticles() {
		return researchArticles;
	}

	public void setResearchArticles(String researchArticles) {
		this.researchArticles = researchArticles;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}