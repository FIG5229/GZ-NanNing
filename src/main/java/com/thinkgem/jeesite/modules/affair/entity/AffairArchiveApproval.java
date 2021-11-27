/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 查(借)阅审批Entity
 * @author mason.xv
 * @version 2019-11-30
 */
public class AffairArchiveApproval extends DataEntity<AffairArchiveApproval> {

	private static final long serialVersionUID = 1L;
	private String reason;		// 查档事由
	private String content;		// 查档内容
	private String unitOpinion;		// 查档单位意见内容
	private String unitPerson;		// 查档单位负责人
	private Date unitDate;		// 查档单位负责人签字日期
	private String depOpinion;		// 主管部门意见内容
	private String depPerson;		// 主管部门负责人
	private Date depDate;		// 主管部门负责人签字日期
	private String leaderInstruction;		// 领导批示
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private List<AffairCdObject> affairCdObjectList = Lists.newArrayList();		// 子表列表
	private List<AffairCdPerson> affairCdPersonList = Lists.newArrayList();		// 子表列表
	
	public AffairArchiveApproval() {
		super();
	}

	public AffairArchiveApproval(String id){
		super(id);
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUnitOpinion() {
		return unitOpinion;
	}

	public void setUnitOpinion(String unitOpinion) {
		this.unitOpinion = unitOpinion;
	}
	
	public String getUnitPerson() {
		return unitPerson;
	}

	public void setUnitPerson(String unitPerson) {
		this.unitPerson = unitPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getUnitDate() {
		return unitDate;
	}

	public void setUnitDate(Date unitDate) {
		this.unitDate = unitDate;
	}
	
	public String getDepOpinion() {
		return depOpinion;
	}

	public void setDepOpinion(String depOpinion) {
		this.depOpinion = depOpinion;
	}
	
	public String getDepPerson() {
		return depPerson;
	}

	public void setDepPerson(String depPerson) {
		this.depPerson = depPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDepDate() {
		return depDate;
	}

	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}
	
	public String getLeaderInstruction() {
		return leaderInstruction;
	}

	public void setLeaderInstruction(String leaderInstruction) {
		this.leaderInstruction = leaderInstruction;
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
	
	public List<AffairCdObject> getAffairCdObjectList() {
		return affairCdObjectList;
	}

	public void setAffairCdObjectList(List<AffairCdObject> affairCdObjectList) {
		this.affairCdObjectList = affairCdObjectList;
	}
	public List<AffairCdPerson> getAffairCdPersonList() {
		return affairCdPersonList;
	}

	public void setAffairCdPersonList(List<AffairCdPerson> affairCdPersonList) {
		this.affairCdPersonList = affairCdPersonList;
	}
}