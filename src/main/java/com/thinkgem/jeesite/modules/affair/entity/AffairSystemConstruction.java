/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 制度建设Entity
 * @author daniel.liu
 * @version 2020-07-07
 */
public class AffairSystemConstruction extends DataEntity<AffairSystemConstruction> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String filePath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String partyOrganization;		//党组织机构
	private String partyOrganizationId;		//党组织机构Id

	//前端
	private String treeId; 		//组织树Id

	/*新加字段，审核使用*/
	/*1未上报 2待审核 3通过 4不通过*/
	private String examineStatus;	//审核状态
	private String opinion;			//审核意见


	public AffairSystemConstruction() {
		super();
	}

	public AffairSystemConstruction(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
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