/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 档案台账转入Entity
 * @author mason.xv
 * @version 2019-11-02
 */
public class AffairLedgerInto extends DataEntity<AffairLedgerInto> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "转入时间", type = 0, align = 2, sort = 0)
	private Date date;		// 转入时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "原单位", type = 0, align = 2, sort = 2)
	private String oldUnit;		// 原单位
	@ExcelField(title = "现单位", type = 0, align = 2, sort = 3)
	private String nowUnit;		// 现单位
	@ExcelField(title = "现职务", type = 0, align = 2, sort = 4)
	private String nowJob;		// 现职务
	@ExcelField(title = "转入原因", type = 0, align = 2, sort = 5)
	private String reason;		// 转入原因
	@ExcelField(title = "正本数量", type = 0, align = 2, sort = 6)
	private Integer zhengbenNum;   //正本数量
	@ExcelField(title = "副本数量", type = 0, align = 2, sort = 7)
	private Integer fubenNum;		// 副本数量
	@ExcelField(title = "档案号", type = 0, align = 2, sort = 8)
	private String archiveNo;		// 档案号
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9)
	private String remark;		// 备注

	private String idNumber;		// 身份证号

	private String oldUnitId;		// 原单位机构id
	private String nowUnitId;		// 现单位机构id

	private String receiver;		// 接收人




	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public AffairLedgerInto() {
		super();
	}

	public AffairLedgerInto(String id){
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
	
	public String getNowUnit() {
		return nowUnit;
	}

	public void setNowUnit(String nowUnit) {
		this.nowUnit = nowUnit;
	}
	
	public String getOldUnitId() {
		return oldUnitId;
	}

	public void setOldUnitId(String oldUnitId) {
		this.oldUnitId = oldUnitId;
	}
	
	public String getNowUnitId() {
		return nowUnitId;
	}

	public void setNowUnitId(String nowUnitId) {
		this.nowUnitId = nowUnitId;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public String getNowJob() {
		return nowJob;
	}

	public void setNowJob(String nowJob) {
		this.nowJob = nowJob;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getZhengbenNum() {
		return zhengbenNum;
	}

	public void setZhengbenNum(Integer zhengbenNum) {
		this.zhengbenNum = zhengbenNum;
	}

	public Integer getFubenNum() {
		return fubenNum;
	}

	public void setFubenNum(Integer fubenNum) {
		this.fubenNum = fubenNum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
}