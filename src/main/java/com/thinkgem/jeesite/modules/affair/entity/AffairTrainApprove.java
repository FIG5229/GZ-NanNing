/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 培训计划审批Entity
 * @author jack.xu
 * @version 2020-07-28
 */
public class AffairTrainApprove extends DataEntity<AffairTrainApprove> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "培训年度",type = 0,align = 2,sort = 0)
	private String trainYear;		// 培训年度
	private String unitId;		// 单位id
	@ExcelField(title = "填报机构", type = 0,align = 2,sort = 1)
	private String unit;		// 填报机构
	@ExcelField(title = "标题", type = 0, align = 2,sort = 2)
	private String title;		// 标题
	@ExcelField(title = "填报人", type = 0,align = 2,sort = 3)
	private String informant;		// 填报人
	@ExcelField(title = "审核人", type = 0,align = 2,sort = 4)
	private String reviewer;		// 审核人
	@ExcelField(title = "提交审批时间", type = 0,align = 2,sort = 5)
	private Date approveDate;		// 提交审批日期
	@ExcelField(title = "审批层级", type = 0, align = 2,sort = 6,dictType = "approve_level")
	private String approveLevel;		// 审批层级
	@ExcelField(title = "审批结果",type = 0,align = 2,sort = 7)
	private String approveResult;		// 审批结果
	@ExcelField(title = "审批状态",type = 0,align = 2,sort = 8,dictType = "approve_status")
	private String approveStatus;		// 审批状态
	@ExcelField(title = "已填报的计划培训班总数",type = 0,align = 2,sort = 8)
	private String filledClassCount;		// 已填报的计划培训班总数
	@ExcelField(title = "已审批通过的计划培训班数量",type = 0,align = 2,sort = 8)
	private String approvedClassCount;		// 已审批通过的计划培训班数量
	@ExcelField(title = "已审批通过的计划培训总人数",type = 0,align = 2,sort = 8)
	private String approvedCount;		// 已审批通过的计划培训总人数
	@ExcelField(title = "未完成审批的计划培训班数量",type = 0,align = 2,sort = 8)
	private String incompleteApprovalCount;		// 未完成审批的计划培训班数量
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateIdNo;		// 更新者身份证号
	
	public AffairTrainApprove() {
		super();
	}

	public AffairTrainApprove(String id){
		super(id);
	}

	public String getTrainYear() {
		return trainYear;
	}

	public void setTrainYear(String trainYear) {
		this.trainYear = trainYear;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}
	
	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	public String getApproveLevel() {
		return approveLevel;
	}

	public void setApproveLevel(String approveLevel) {
		this.approveLevel = approveLevel;
	}
	
	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	
	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	public String getFilledClassCount() {
		return filledClassCount;
	}

	public void setFilledClassCount(String filledClassCount) {
		this.filledClassCount = filledClassCount;
	}
	
	public String getApprovedClassCount() {
		return approvedClassCount;
	}

	public void setApprovedClassCount(String approvedClassCount) {
		this.approvedClassCount = approvedClassCount;
	}
	
	public String getApprovedCount() {
		return approvedCount;
	}

	public void setApprovedCount(String approvedCount) {
		this.approvedCount = approvedCount;
	}
	
	public String getIncompleteApprovalCount() {
		return incompleteApprovalCount;
	}

	public void setIncompleteApprovalCount(String incompleteApprovalCount) {
		this.incompleteApprovalCount = incompleteApprovalCount;
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
	
	public String getCreateIdNo() {
		return createIdNo;
	}

	public void setCreateIdNo(String createIdNo) {
		this.createIdNo = createIdNo;
	}
	
	public String getUpdateIdNo() {
		return updateIdNo;
	}

	public void setUpdateIdNo(String updateIdNo) {
		this.updateIdNo = updateIdNo;
	}
	
}