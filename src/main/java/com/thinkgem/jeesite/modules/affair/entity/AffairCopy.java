/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 复印档案登记Entity
 * @author mason.xv
 * @version 2019-11-05
 */
public class AffairCopy extends DataEntity<AffairCopy> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "复印时间", type = 0, align = 2, sort = 0)
	private Date date;		// 复印时间
	@ExcelField(title = "被复印人姓名", type = 0, align = 2, sort = 1)
	private String bCopyName;		// 被复印人姓名
	@ExcelField(title = "办理人员姓名", type = 0, align = 2, sort = 2)
	private String handleName;		// 办理人员姓名
	@ExcelField(title = "办理人员单位", type = 0, align = 2, sort = 3)
	private String unit;		// 办理人员单位
	@ExcelField(title = "办理人员政治面貌", type = 0, align = 2, sort = 4, dictType="political_status")
	private String face;		// 办理人员政治面貌
	@ExcelField(title = "复印原因", type = 0, align = 2, sort = 6)
	private String reason;		// 复印原因
	@ExcelField(title = "复印内容", type = 0, align = 2, sort = 7)
	private String content;		// 复印内容
	@ExcelField(title = "审批人", type = 0, align = 2, sort = 5)
	private String approver;	// 审批人


	private String remark;		// 备注
	private String unitId;		// 办理人员单位机构id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 复印时间
	private Date endDate;		// 结束 复印时间
	
	public AffairCopy() {
		super();
	}

	public AffairCopy(String id){
		super(id);
	}

	public String getbCopyName() {
		return bCopyName;
	}

	public void setbCopyName(String bCopyName) {
		this.bCopyName = bCopyName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getBCopyName() {
		return bCopyName;
	}

	public void setBCopyName(String bCopyName) {
		this.bCopyName = bCopyName;
	}
	
	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}
}