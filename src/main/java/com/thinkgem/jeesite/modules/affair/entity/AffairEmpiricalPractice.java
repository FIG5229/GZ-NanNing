/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 教育整顿经验做法Entity
 * @author cecil.li
 * @version 2019-11-08
 */
public class AffairEmpiricalPractice extends DataEntity<AffairEmpiricalPractice> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String unit;		// 发布部门
	private String unitId;		// 发布部门id
	private String publisher;		// 发布人
	private String publisherNum;		// 发布人身份证
	private String mainContent;		// 主要内容
	private String annex;		// 附件
	private String postStatus;		// 发布状态
	private Date releaseDate;		// 发布时间
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	private String qxUnitId;
	private Date startYear;    //本年度
	private String otherYear;  //其他年份
	
	public AffairEmpiricalPractice() {
		super();
	}

	public AffairEmpiricalPractice(String id){
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
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getPublisherNum() {
		return publisherNum;
	}

	public void setPublisherNum(String publisherNum) {
		this.publisherNum = publisherNum;
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
	
	public String getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
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

	public String getQxUnitId() {
		return qxUnitId;
	}

	public void setQxUnitId(String qxUnitId) {
		this.qxUnitId = qxUnitId;
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
}