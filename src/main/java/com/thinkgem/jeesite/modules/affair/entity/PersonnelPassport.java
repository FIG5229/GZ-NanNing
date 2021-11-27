/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 领导干部护照(通行证)管理表Entity
 * @author mason.xv
 * @version 2019-11-06
 */
public class PersonnelPassport extends DataEntity<PersonnelPassport> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "证照名称", type = 0, align = 2, sort = 0)
	private String name;		// 证照名称
	@ExcelField(title = "证照姓名", type = 0, align = 2, sort = 1)
	private String personName;		// 证照姓名
	@ExcelField(title = "签发地", type = 0, align = 2, sort = 2)
	private String place;		// 签发地
	@ExcelField(title = "签发日期", type = 0, align = 2, sort = 3)
	private Date issueDate;		// 签发日期
	@ExcelField(title = "证照有效截止日期", type = 0, align = 2, sort = 4)
	private Date toDate;		// 证照有效截止日期
	@ExcelField(title = "证件编号", type = 0, align = 2, sort = 5)
	private String certificateNo;		// 证件编号
	@ExcelField(title = "办理证件事由", type = 0, align = 2, sort = 6)
	private String reason;		// 办理证件事由
	@ExcelField(title = "证件状态", type = 0, align = 2, sort = 7, dictType="personnel_zjzt")
	private String status;		// 证件状态
	@ExcelField(title = "证件领用日期", type = 0, align = 2, sort = 8)
	private Date receiveDate;		// 证件领用日期
	@ExcelField(title = "证件交还日期", type = 0, align = 2, sort = 9)
	private Date returnDate;		// 证件交还日期
	@ExcelField(title = "证件存档编号", type = 0, align = 2, sort = 10)
	private String fileNo;		// 证件存档编号
	@ExcelField(title = "撤销备案日期", type = 0, align = 2, sort = 11)
	private Date cancelDate;		// 撤销备案日期
	@ExcelField(title = "证件多媒体信息", type = 0, align = 2, sort = 12)
	private String media;		// 证件多媒体信息
	@ExcelField(title = "签发机关", type = 0, align = 2, sort = 13)
	private String issueOrg;		// 签发机关
	@ExcelField(title = "保管机关名称及人员", type = 0, align = 2, sort = 14)
	private String saveOrgPer;		// 保管机关名称及人员
	@ExcelField(title = "身份证号/证件编号", type = 0, align = 2, sort = 15)
	private String idNumber;		// 身份证号/证件编号

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginIssueDate;		// 开始 签发日期
	private Date endIssueDate;		// 结束 签发日期
	private Date beginReceiveDate;		// 开始 证件领用日期
	private Date endReceiveDate;		// 结束 证件领用日期


	
	public PersonnelPassport() {
		super();
	}

	public PersonnelPassport(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}
	
	public String getIssueOrg() {
		return issueOrg;
	}

	public void setIssueOrg(String issueOrg) {
		this.issueOrg = issueOrg;
	}
	
	public String getSaveOrgPer() {
		return saveOrgPer;
	}

	public void setSaveOrgPer(String saveOrgPer) {
		this.saveOrgPer = saveOrgPer;
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
	
	public Date getBeginIssueDate() {
		return beginIssueDate;
	}

	public void setBeginIssueDate(Date beginIssueDate) {
		this.beginIssueDate = beginIssueDate;
	}
	
	public Date getEndIssueDate() {
		return endIssueDate;
	}

	public void setEndIssueDate(Date endIssueDate) {
		this.endIssueDate = endIssueDate;
	}
		
	public Date getBeginReceiveDate() {
		return beginReceiveDate;
	}

	public void setBeginReceiveDate(Date beginReceiveDate) {
		this.beginReceiveDate = beginReceiveDate;
	}
	
	public Date getEndReceiveDate() {
		return endReceiveDate;
	}

	public void setEndReceiveDate(Date endReceiveDate) {
		this.endReceiveDate = endReceiveDate;
	}
		
}