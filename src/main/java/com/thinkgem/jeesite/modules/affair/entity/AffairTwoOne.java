/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * &quot;两学一做&quot;专题学习Entity
 * @author mason.xv
 * @version 2019-11-02
 */
public class AffairTwoOne extends DataEntity<AffairTwoOne> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "专题名称", type = 0, align = 2, sort = 0)
	private String name;		// 专题名称
	@ExcelField(title = "学习时间", type = 0, align = 2, sort = 1)
	private Date studyDate;		// 学习时间
	@ExcelField(title = "党组织名称", type = 0, align = 2, sort = 2)
	private String partyOrganization;		// 党组织名称
	private String partyOrganizationId;		// 党组织id
	@ExcelField(title = "学习记录", type = 0, align = 2, sort = 4)
	private String record;		// 学习记录
	private String filePath;		// 上传文件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	private Date startDate;
	private Date endDate;
	private String treeId;	//接受前端传来的左侧树的id

	/*新加字段，审核使用*/
	/*1未上报 2待审核 3通过 4不通过*/
	private String examineStatus;	//审核状态
	private String opinion;			//审核意见


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


	public AffairTwoOne() {
		super();
	}

	public AffairTwoOne(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStudyDate() {
		return studyDate;
	}

	public void setStudyDate(Date studyDate) {
		this.studyDate = studyDate;
	}
	
	public String getPartyOrganization() {
		return partyOrganization;
	}

	public void setPartyOrganization(String partyOrganization) {
		this.partyOrganization = partyOrganization;
	}
	
	public String getPartyOrganizationId() {
		return partyOrganizationId;
	}

	public void setPartyOrganizationId(String partyOrganizationId) {
		this.partyOrganizationId = partyOrganizationId;
	}
	
	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}