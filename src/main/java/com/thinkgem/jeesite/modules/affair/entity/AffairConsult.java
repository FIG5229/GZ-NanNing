/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 台账查阅表Entity
 * @author mason.xv
 * @version 2019-11-05
 */
public class AffairConsult extends DataEntity<AffairConsult> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "查阅时间", type = 0, align = 2, sort = 0)
	private Date time;		// 查阅时间
	@ExcelField(title = "被查阅人姓名", type = 0, align = 2, sort = 1)
	private String bcyrName;		// 被查阅人姓名
	@ExcelField(title = "查阅人姓名", type = 0, align = 2, sort = 2)
	private String cyrName;		// 查阅人姓名
	@ExcelField(title = "查阅人单位", type = 0, align = 2, sort = 3)
	private String cyrUnit;		// 查阅人单位
	@ExcelField(title = "查阅人政治面貌", type = 0, align = 2, sort = 4, dictType="political_status")
	private String cyrFace;		// 查阅人政治面貌
	@ExcelField(title = "备注", type = 0, align = 2, sort = 6)
	private String remark;		// 备注
	@ExcelField(title = "查档目的", type = 0, align = 2, sort = 5)
	private String purpose;		//查档目的




	private String cyrUnitId;		// 借阅人单位id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginTime;		// 开始 查阅时间
	private Date endTime;		// 结束 查阅时间


	public AffairConsult() {
		super();
	}

	public AffairConsult(String id){
		super(id);
	}

	public String getCyrName() {
		return cyrName;
	}

	public void setCyrName(String cyrName) {
		this.cyrName = cyrName;
	}

	public String getBcyrName() {
		return bcyrName;
	}

	public void setBcyrName(String bcyrName) {
		this.bcyrName = bcyrName;
	}
	

	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getCyrUnit() {
		return cyrUnit;
	}

	public void setCyrUnit(String cyrUnit) {
		this.cyrUnit = cyrUnit;
	}
	
	public String getCyrFace() {
		return cyrFace;
	}

	public void setCyrFace(String cyrFace) {
		this.cyrFace = cyrFace;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCyrUnitId() {
		return cyrUnitId;
	}

	public void setCyrUnitId(String cyrUnitId) {
		this.cyrUnitId = cyrUnitId;
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

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
}