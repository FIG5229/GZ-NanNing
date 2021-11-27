/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 预备党员转正Entity
 * @author freeman
 * @version 2020-06-02
 */
public class AffairProbationaryParty extends DataEntity<AffairProbationaryParty> {
	
	private static final long serialVersionUID = 1L;
	private String identityCardNumber;	// 身份证号
	private String name;				// 姓名
	private String gender;				// 性别
	private String educational;			// 学历
	private String personnelCategory;	// 人员类别
	private String partyBranch;			// 所在党支部
	private Date joiningPartyDate;		// 加入党组织日期
	private String creator;				// 创建者
	private String creatorOrgId;		// 创建者机构ID
	private String creatorIdNo;			// 创建者身份证号
	private Date createTime;			// 创建时间
	private String updateOrgId;			// 更新者机构id
	private String updateIdNo;			// 更新者身份证号
	private String treeId;
	
	public AffairProbationaryParty() {
		super();
	}

	public AffairProbationaryParty(String id){
		super(id);
	}

	public String getIdentityCardNumber() {
		return identityCardNumber;
	}

	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getEducational() {
		return educational;
	}

	public void setEducational(String educational) {
		this.educational = educational;
	}
	
	public String getPersonnelCategory() {
		return personnelCategory;
	}

	public void setPersonnelCategory(String personnelCategory) {
		this.personnelCategory = personnelCategory;
	}
	
	public String getPartyBranch() {
		return partyBranch;
	}

	public void setPartyBranch(String partyBranch) {
		this.partyBranch = partyBranch;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJoiningPartyDate() {
		return joiningPartyDate;
	}

	public void setJoiningPartyDate(Date joiningPartyDate) {
		this.joiningPartyDate = joiningPartyDate;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreatorOrgId() {
		return creatorOrgId;
	}

	public void setCreatorOrgId(String creatorOrgId) {
		this.creatorOrgId = creatorOrgId;
	}
	
	public String getCreatorIdNo() {
		return creatorIdNo;
	}

	public void setCreatorIdNo(String creatorIdNo) {
		this.creatorIdNo = creatorIdNo;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getTreeId(){
		return treeId;
	}

	public void setTreeId(String treeId){
		this.treeId = treeId;
	}

}