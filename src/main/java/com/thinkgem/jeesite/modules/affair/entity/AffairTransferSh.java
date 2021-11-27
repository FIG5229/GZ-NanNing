/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统内组织关系移交转接关联表Entity
 * @author eav.liu
 * @version 2019-11-12
 */
public class AffairTransferSh extends DataEntity<AffairTransferSh> {
	
	private static final long serialVersionUID = 1L;
	private String transferId;		// 关联id
	private String shPerson;		// 审核人
	private String shOrganization;		// 审核人代表的组织
	private String shOrganizationId;		// 审核人代表的组织id
	private String opinion;		// 审核意见
	private String status;		// 审核状态（通过/不通过）
	private Date date;		// 审批时间
	
	public AffairTransferSh() {
		super();
	}

	public AffairTransferSh(String id){
		super(id);
	}

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	
	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}
	
	public String getShOrganization() {
		return shOrganization;
	}

	public void setShOrganization(String shOrganization) {
		this.shOrganization = shOrganization;
	}
	
	public String getShOrganizationId() {
		return shOrganizationId;
	}

	public void setShOrganizationId(String shOrganizationId) {
		this.shOrganizationId = shOrganizationId;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}