/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 党委委员Entity
 * @author daniel.liu
 * @version 2020-06-02
 */
public class AffairMemberPartyCommittee extends DataEntity<AffairMemberPartyCommittee> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 党委委员
	private String partyOrganization;		// 党组织
	private String partyOrganizationId;		// 党组织Id
	private String partyClass;		// 党委委员上党课情况
	private String associatedPiont;		// 落实联系点制度情况
	private String responsibilityReport;		// 落实党建工作责任报告
	//修改附件 只保留rrFilePath,其他隐藏或注释
	//前两个字母为**情况单词缩写
	private String pcFilePath;		// 党委委员上党课情况附件
	private String apFilePath;		// 落实联系点附件
	private String rrFilePath;		// 落实党建工作报告附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String treeId;		//左侧树Id

	/*9.5 问题汇总 新加字段*/
	private String sex;
	private String nation;
	private String age;
	private String job;

	private String status; 		//审核状态
	private String opinion;		//审核意见

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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public AffairMemberPartyCommittee() {
		super();
	}

	public AffairMemberPartyCommittee(String id){
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
	
	public String getPartyClass() {
		return partyClass;
	}

	public void setPartyClass(String partyClass) {
		this.partyClass = partyClass;
	}
	
	public String getAssociatedPiont() {
		return associatedPiont;
	}

	public void setAssociatedPiont(String associatedPiont) {
		this.associatedPiont = associatedPiont;
	}
	
	public String getResponsibilityReport() {
		return responsibilityReport;
	}

	public void setResponsibilityReport(String responsibilityReport) {
		this.responsibilityReport = responsibilityReport;
	}
	
	public String getPcFilePath() {
		return pcFilePath;
	}

	public void setPcFilePath(String pcFilePath) {
		this.pcFilePath = pcFilePath;
	}
	
	public String getApFilePath() {
		return apFilePath;
	}

	public void setApFilePath(String apFilePath) {
		this.apFilePath = apFilePath;
	}
	
	public String getRrFilePath() {
		return rrFilePath;
	}

	public void setRrFilePath(String rrFilePath) {
		this.rrFilePath = rrFilePath;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}


	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
}