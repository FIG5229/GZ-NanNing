/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 民主（组织）生活会Entity
 * @author eav.liu
 * @version 2019-11-09
 */
public class AffairLifeMeet extends DataEntity<AffairLifeMeet> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 会议名称
	private String partyOrganization;		// 党组织名称
	private String partyOrganizationId;		// 党组织id
	private Date date;		// 召开时间
	private String hold;		// 主持人
	private String noteTaker;		// 记录人
	private String agenda;		// 会议议程
	private String person1;		// 应参会人员
	private String person2;		// 已会人员
	private String person3;		// 未会人员
//	@EsAttach
	private String filePath;		// 相关文件
	private String addStatus;		// 添加时的状态（1：保存 2：提交）
	private String shPerson;	//审核人
	private String status;		// 审核状态（1：通过 2：未通过 3：未审核）
	private String reason;		// 审核不通过原因
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;

	private Date endDate;

	private boolean hasAuth;

	private String treeId;	//接受前端传来的左侧树的id

	//新加字段
	private String person2Id;	//已会人员id
	private String person3Id;	//未会人员id

	public AffairLifeMeet() {
		super();
	}

	public String getPerson2Id() {
		return person2Id;
	}

	public void setPerson2Id(String person2Id) {
		this.person2Id = person2Id;
	}

	public String getPerson3Id() {
		return person3Id;
	}

	public void setPerson3Id(String person3Id) {
		this.person3Id = person3Id;
	}

	public AffairLifeMeet(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getHold() {
		return hold;
	}

	public void setHold(String hold) {
		this.hold = hold;
	}
	
	public String getNoteTaker() {
		return noteTaker;
	}

	public void setNoteTaker(String noteTaker) {
		this.noteTaker = noteTaker;
	}
	
	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}
	
	public String getPerson1() {
		return person1;
	}

	public void setPerson1(String person1) {
		this.person1 = person1;
	}
	
	public String getPerson2() {
		return person2;
	}

	public void setPerson2(String person2) {
		this.person2 = person2;
	}
	
	public String getPerson3() {
		return person3;
	}

	public void setPerson3(String person3) {
		this.person3 = person3;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getAddStatus() {
		return addStatus;
	}

	public void setAddStatus(String addStatus) {
		this.addStatus = addStatus;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
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

	public boolean isHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth) {
		this.hasAuth = hasAuth;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
}