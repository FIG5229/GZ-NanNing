/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 党组织换届选举Entity
 * @author eav.liu
 * @version 2019-11-05
 */
public class AffairElection extends DataEntity<AffairElection> {
	
	private static final long serialVersionUID = 1L;
	private Integer jc;		// 届次
	private String method;		// 选举方式
	private Date jmDate;		// 该届届满时间
	private Date hjDate;		// 该届换届时间
	private Integer ydhNum;		// 应到会人数
	private Integer sdhNum;		// 实到会人数
	private Integer quota;		// 批准委员会名额
	private String status1;		// 添加时状态
	private String person;  //审核人
	private String status2;		// 审核状态
	private String opinion;		// 审核人意见
	private String partyOrganizationName;		// 党组织名称
	private String partyOrganizationId;		// 党组织机构id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	/*新增字段  */
	private String filePath;		//附件
	private  String electionInformation;		//换届资料录入

	private Date jmStartDate;

	private Date jmEndDate;

	private Date hjStartDate;

	private Date hjEndDate;
	//审核权限
	private boolean hasAuth;

	private String treeId;	//接受前端传来的左侧树的id

	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;

	public AffairElection() {
		super();
	}

	public AffairElection(String id){
		super(id);
	}

	public Integer getJc() {
		return jc;
	}

	public void setJc(Integer jc) {
		this.jc = jc;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getJmDate() {
		return jmDate;
	}

	public void setJmDate(Date jmDate) {
		this.jmDate = jmDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getHjDate() {
		return hjDate;
	}

	public void setHjDate(Date hjDate) {
		this.hjDate = hjDate;
	}
	
	public Integer getYdhNum() {
		return ydhNum;
	}

	public void setYdhNum(Integer ydhNum) {
		this.ydhNum = ydhNum;
	}
	
	public Integer getSdhNum() {
		return sdhNum;
	}

	public void setSdhNum(Integer sdhNum) {
		this.sdhNum = sdhNum;
	}
	
	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}
	
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getPartyOrganizationName() {
		return partyOrganizationName;
	}

	public void setPartyOrganizationName(String partyOrganizationName) {
		this.partyOrganizationName = partyOrganizationName;
	}
	
	public String getPartyOrganizationId() {
		return partyOrganizationId;
	}

	public void setPartyOrganizationId(String partyOrganizationId) {
		this.partyOrganizationId = partyOrganizationId;
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

	public Date getJmStartDate() {
		return jmStartDate;
	}

	public void setJmStartDate(Date jmStartDate) {
		this.jmStartDate = jmStartDate;
	}

	public Date getJmEndDate() {
		return jmEndDate;
	}

	public void setJmEndDate(Date jmEndDate) {
		this.jmEndDate = jmEndDate;
	}

	public Date getHjStartDate() {
		return hjStartDate;
	}

	public void setHjStartDate(Date hjStartDate) {
		this.hjStartDate = hjStartDate;
	}

	public Date getHjEndDate() {
		return hjEndDate;
	}

	public void setHjEndDate(Date hjEndDate) {
		this.hjEndDate = hjEndDate;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getElectionInformation() {
		return electionInformation;
	}

	public void setElectionInformation(String electionInformation) {
		this.electionInformation = electionInformation;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}