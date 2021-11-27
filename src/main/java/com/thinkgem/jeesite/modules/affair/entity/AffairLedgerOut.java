/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 档案台账转出Entity
 * @author mason.xv
 * @version 2019-11-04
 */
public class AffairLedgerOut extends DataEntity<AffairLedgerOut> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "转出时间", type = 0, align = 2, sort = 0)
	private Date date;		// 转出时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "原单位", type = 0, align = 2, sort = 2)
	private String oldUnit;		// 原单位
	@ExcelField(title = "转出原因", type = 0, align = 2, sort = 3)
	private String reason;		// 转出原因
	@ExcelField(title = "转往部门", type = 0, align = 2, sort = 4)
	private String toDep;		// 转往部门
	@ExcelField(title = "正本数量", type = 0, align = 2, sort = 5)
	private Integer zNum;		// 正本数量
	@ExcelField(title = "副本数量", type = 0, align = 2, sort = 6)
	private Integer fNum;		// 副本数量
	@ExcelField(title = "档案号", type = 0, align = 2, sort = 7)
	private String archiveNo;		// 档案号
	@ExcelField(title = "转递通知单号", type = 0, align = 2, sort = 8)
	private String noticeNo;		// 转递通知单号
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9)
	private String remark;		// 备注


	private String idNumber;		// 身份证号


	private String oldUnitId;		// 原单位机构id
	private String toDepId;		// 转往部门id






	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	//供查询时间区间使用
	private Date startDate;

	//供查询时间区间使用
	private Date endDate;

	public Integer getzNum() {
		return zNum;
	}

	public void setzNum(Integer zNum) {
		this.zNum = zNum;
	}

	public Integer getfNum() {
		return fNum;
	}

	public void setfNum(Integer fNum) {
		this.fNum = fNum;
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

	public AffairLedgerOut() {
		super();
	}

	public AffairLedgerOut(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getArchiveNo() {
		return archiveNo;
	}

	public void setArchiveNo(String archiveNo) {
		this.archiveNo = archiveNo;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getOldUnit() {
		return oldUnit;
	}

	public void setOldUnit(String oldUnit) {
		this.oldUnit = oldUnit;
	}
	
	public String getToDep() {
		return toDep;
	}

	public void setToDep(String toDep) {
		this.toDep = toDep;
	}
	
	public String getOldUnitId() {
		return oldUnitId;
	}

	public void setOldUnitId(String oldUnitId) {
		this.oldUnitId = oldUnitId;
	}
	
	public String getToDepId() {
		return toDepId;
	}

	public void setToDepId(String toDepId) {
		this.toDepId = toDepId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getZNum() {
		return zNum;
	}

	public void setZNum(Integer zNum) {
		this.zNum = zNum;
	}
	
	public Integer getFNum() {
		return fNum;
	}

	public void setFNum(Integer fNum) {
		this.fNum = fNum;
	}
	
	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
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
	
}