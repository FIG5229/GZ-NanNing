/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 离退干部档案登记花名册Entity
 * @author mason.xv
 * @version 2019-11-04
 */
public class AffairRetire extends DataEntity<AffairRetire> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 原任单位
	@ExcelField(title = "职务", type = 0, align = 2, sort = 2)
	private String job;		// 原任职务
	@ExcelField(title = "级别", type = 0, align = 2, sort = 3)
	private String level;		// 原任级别
	@ExcelField(title = "离丶退休时间", type = 0, align = 2, sort = 4)
	private Date outDate;		// 离丶退休时间
	private String outContent;  //离丶退休原因
	@ExcelField(title = "正本数量", type = 0, align = 2, sort = 5)
	private Integer zNum;		// 正本数量
	@ExcelField(title = "副本数量", type = 0, align = 2, sort = 6)
	private Integer fNum;		// 副本数量
	@ExcelField(title = "档案号", type = 0, align = 2, sort = 7)
	private String archiveNo;		// 档案号
	@ExcelField(title = "备注", type = 0, align = 2, sort = 8)
	private String remark;		// 备注
	private String fileUnit;   // 档案管理单位




	private String idNumber;		// 身份证号
	private String unitId;		// 单位机构id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginOutDate;		// 开始 离丶退休时间
	private Date endOutDate;		// 结束 离丶退休时间

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

	public AffairRetire() {
		super();
	}

	public AffairRetire(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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
	
	public String getArchiveNo() {
		return archiveNo;
	}

	public void setArchiveNo(String archiveNo) {
		this.archiveNo = archiveNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getOutContent() {
		return outContent;
	}

	public void setOutContent(String outContent) {
		this.outContent = outContent;
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
	
	public Date getBeginOutDate() {
		return beginOutDate;
	}

	public void setBeginOutDate(Date beginOutDate) {
		this.beginOutDate = beginOutDate;
	}
	
	public Date getEndOutDate() {
		return endOutDate;
	}

	public void setEndOutDate(Date endOutDate) {
		this.endOutDate = endOutDate;
	}

	public String getFileUnit() {
		return fileUnit;
	}

	public void setFileUnit(String fileUnit) {
		this.fileUnit = fileUnit;
	}
}