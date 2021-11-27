/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 档案台账查阅表Entity
 * @author mason.xv
 * @version 2019-11-04
 */
public class AffairBorrow extends DataEntity<AffairBorrow> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "借阅时间", type = 0, align = 2, sort = 0)
	private Date time;		// 借阅时间
	@ExcelField(title = "被借阅人姓名", type = 0, align = 2, sort = 1)
	private String bjyrName;		// 被借阅人姓名
	@ExcelField(title = "借阅人姓名", type = 0, align = 2, sort = 2)
	private String jyrName;		// 借阅人姓名
	@ExcelField(title = "借阅人单位", type = 0, align = 2, sort = 3)
	private String jyrUnit;		// 借阅人单位
	@ExcelField(title = "借阅人政治面貌", type = 0, align = 2, sort = 4, dictType="political_status")
	private String jyrFace;		// 借阅人政治面貌
	@ExcelField(title = "批准人", type = 0, align = 2, sort = 5)
	private String approver;		// 批准人
	@ExcelField(title = "归还日期", type = 0, align = 2, sort = 6)
	private Date situation;		// 归还日期
	@ExcelField(title = "经手人", type = 0, align = 2, sort = 7)
	private String handler;		// 经手人

	private String remark;		// 备注
	private String jyrIdNumber;		// 借阅人身份证号
	private String bjyrIdNumber;		// 被借阅人身份证号
	private String jyrUnitId;		// 借阅人单位id

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginTime;		// 开始 借阅时间
	private Date endTime;		// 结束 借阅时间
	
	public AffairBorrow() {
		super();
	}

	public AffairBorrow(String id){
		super(id);
	}

	public String getJyrName() {
		return jyrName;
	}

	public void setJyrName(String jyrName) {
		this.jyrName = jyrName;
	}

	public String getJyrIdNumber() {
		return jyrIdNumber;
	}

	public void setJyrIdNumber(String jyrIdNumber) {
		this.jyrIdNumber = jyrIdNumber;
	}

	public String getBjyrIdNumber() {
		return bjyrIdNumber;
	}

	public void setBjyrIdNumber(String bjyrIdNumber) {
		this.bjyrIdNumber = bjyrIdNumber;
	}

	public String getBjyrName() {
		return bjyrName;
	}

	public void setBjyrName(String bjyrName) {
		this.bjyrName = bjyrName;
	}
	

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getJyrUnit() {
		return jyrUnit;
	}

	public void setJyrUnit(String jyrUnit) {
		this.jyrUnit = jyrUnit;
	}
	
	public String getJyrFace() {
		return jyrFace;
	}

	public void setJyrFace(String jyrFace) {
		this.jyrFace = jyrFace;
	}
	
	public String getJyrUnitId() {
		return jyrUnitId;
	}

	public void setJyrUnitId(String jyrUnitId) {
		this.jyrUnitId = jyrUnitId;
	}
	
	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSituation() {
		return situation;
	}

	public void setSituation(Date situation) {
		this.situation = situation;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
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
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
		
}