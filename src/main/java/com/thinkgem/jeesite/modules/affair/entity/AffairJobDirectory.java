/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 职务目录Entity
 * @author mason.xv
 * @version 2019-11-27
 */
public class AffairJobDirectory extends DataEntity<AffairJobDirectory> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "批准时间", type = 0, align = 2, sort = 0)
	private Date approveTime;		// 批准时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "任职时间", type = 0, align = 2, sort = 2)
	private String workTime;		// 任职时间
	@ExcelField(title = "免职时间", type = 0, align = 2, sort = 3)
	private Date removalTime;		// 免职时间
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String position;		// 职务
	@ExcelField(title = "批准文电号", type = 0, align = 2, sort = 5)
	private String pzNum;		// 批准文电号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 5)
	private String idNumber;		// 身份证号

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginApproveTime;		// 开始 批准时间
	private Date endApproveTime;		// 结束 批准时间

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public AffairJobDirectory() {
		super();
	}

	public AffairJobDirectory(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getRemovalTime() {
		return removalTime;
	}

	public void setRemovalTime(Date removalTime) {
		this.removalTime = removalTime;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPzNum() {
		return pzNum;
	}

	public void setPzNum(String pzNum) {
		this.pzNum = pzNum;
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
	
	public Date getBeginApproveTime() {
		return beginApproveTime;
	}

	public void setBeginApproveTime(Date beginApproveTime) {
		this.beginApproveTime = beginApproveTime;
	}
	
	public Date getEndApproveTime() {
		return endApproveTime;
	}

	public void setEndApproveTime(Date endApproveTime) {
		this.endApproveTime = endApproveTime;
	}
		
}