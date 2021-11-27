/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 刊用情况Entity
 * @author cecil.li
 * @version 2019-11-07
 */
public class AffairMediaManagement extends DataEntity<AffairMediaManagement> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "标题", type = 0, align = 2, sort = 0)
	private String headlines;		// 新闻标题
	//@ExcelField(title = "媒体名称", type = 0, align = 2, sort = 1)
	private String mediaName;		// 媒体名称
	//@ExcelField(title = "栏目", type = 0, align = 2, sort = 2)
	private String column1;		// 栏目
	@ExcelField(title = "团组织", type = 0, align = 2, sort = 1)
	private	String unit;    //团组织名称
	private String unitId;    //团组织id
	//@ExcelField(title = "作者", type = 0, align = 2, sort = 4)
	private String author;		// 作者
	//@ExcelField(title = "篇幅字数", type = 0, align = 2, sort = 5)
	private String wordsNum;		// 篇幅字数
	@ExcelField(title = "时间", type = 0, align = 2, sort = 3)
	private Date publicationTime;		// 刊发时间
	@ExcelField(title = "内容", type = 0, align = 2, sort = 4)
	private String content;		// 内容
	//@ExcelField(title = "附件", type = 0, align = 2, sort = 8)
	private String annex;		// 附件
	//@ExcelField(title = "媒体类别", type = 0, align = 2, sort = 9,dictType="affair_mt_type")
	private String type;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String shPerson;         //审核人
	private String status ;      //审核状态
	private String opinion;         //审核意见

	private Date startDate;
	private Date endDate;
	
	public AffairMediaManagement() {
		super();
	}

	public AffairMediaManagement(String id){
		super(id);
	}

	public String getHeadlines() {
		return headlines;
	}

	public void setHeadlines(String headlines) {
		this.headlines = headlines;
	}
	
	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}



	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getWordsNum() {
		return wordsNum;
	}

	public void setWordsNum(String wordsNum) {
		this.wordsNum = wordsNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPublicationTime() {
		return publicationTime;
	}

	public void setPublicationTime(Date publicationTime) {
		this.publicationTime = publicationTime;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}