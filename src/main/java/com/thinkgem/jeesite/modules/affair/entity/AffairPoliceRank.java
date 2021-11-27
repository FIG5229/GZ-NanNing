/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 表Entity
 * @author mason.xv
 * @version 2019-11-05
 */
public class AffairPoliceRank extends DataEntity<AffairPoliceRank> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 警衔类型
	private Date startDate;		// 起算时间
	private Date endDate;		// 终止时间
	private String name;		// 警衔名称
	private String approvalUnit;		// 授衔批准单位名称
	private String lhNo;		// 授衔令号
	private String status;		// 授衔状态
	private String changeReason;		// 警衔变动原因
	private Date grantDate;		// 授衔日期
	private String approvalUnitCode;		// 批准单位机构代码
	private String cancelReason;		// 警衔取消原因
	private String retainDate;		// 警衔保留原因
	private String notRetainDate;		// 警衔不保留原因
	private String source;		// 授衔来源
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginStartDate;		// 开始 起算时间
	private Date endStartDate;		// 结束 起算时间
	private Date beginEndDate;		// 开始 终止时间
	private Date endEndDate;		// 结束 终止时间
	
	public AffairPoliceRank() {
		super();
	}

	public AffairPoliceRank(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getApprovalUnit() {
		return approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getLhNo() {
		return lhNo;
	}

	public void setLhNo(String lhNo) {
		this.lhNo = lhNo;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}
	
	public String getApprovalUnitCode() {
		return approvalUnitCode;
	}

	public void setApprovalUnitCode(String approvalUnitCode) {
		this.approvalUnitCode = approvalUnitCode;
	}
	
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	public String getRetainDate() {
		return retainDate;
	}

	public void setRetainDate(String retainDate) {
		this.retainDate = retainDate;
	}
	
	public String getNotRetainDate() {
		return notRetainDate;
	}

	public void setNotRetainDate(String notRetainDate) {
		this.notRetainDate = notRetainDate;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
	
	public Date getBeginStartDate() {
		return beginStartDate;
	}

	public void setBeginStartDate(Date beginStartDate) {
		this.beginStartDate = beginStartDate;
	}
	
	public Date getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}
		
	public Date getBeginEndDate() {
		return beginEndDate;
	}

	public void setBeginEndDate(Date beginEndDate) {
		this.beginEndDate = beginEndDate;
	}
	
	public Date getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(Date endEndDate) {
		this.endEndDate = endEndDate;
	}
		
}