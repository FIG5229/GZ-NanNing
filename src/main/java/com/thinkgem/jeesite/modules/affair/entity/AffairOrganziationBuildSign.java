/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 组织建设关联表Entity
 * @author cecil.li
 * @version 2019-12-16
 */
public class AffairOrganziationBuildSign extends DataEntity<AffairOrganziationBuildSign> {
	
	private static final long serialVersionUID = 1L;
	private String obId;		// 组织建设关联id
	private String committee;		// 委员会情况
	private String committeeName;		// 委员会角色名字
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairOrganziationBuildSign() {
		super();
	}

	public AffairOrganziationBuildSign(String id){
		super(id);
	}

	public String getObId() {
		return obId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}
	
	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}
	
	public String getCommitteeName() {
		return committeeName;
	}

	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
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