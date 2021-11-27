/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 团内惩罚Entity
 * @author daniel.liu
 * @version 2020-05-19
 */
public class AffairLeaguePunishment extends DataEntity<AffairLeaguePunishment> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "处分时间", type = 0, align = 2, sort = 1)
	private Date date;		// 处分时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 2)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 3)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 4, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "处分单位", type = 0, align = 2, sort = 5)
	private String punishmentUnit;		// 处分单位
	private String punishmentUnitId;		// 处分单位id
	@ExcelField(title = "处分名称", type = 0, align = 2, sort = 6)
	private String punishment;		// 处分名称
	@ExcelField(title = "处分文号", type = 0, align = 2, sort = 7)
	private String fileNo;		// 处分文号
	@ExcelField(title = "处分级别", type = 0, align = 2, sort = 8 ,dictType = "affair_punishment")
	private String type;		// 处分级别
	private String filePath;		// 文件地址
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginDate;		// 开始 处分时间
	private Date endDate;		// 结束 处分时间
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9 )
	private String remarks;
	
	public AffairLeaguePunishment() {
		super();
	}

	public AffairLeaguePunishment(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPunishmentUnit() {
		return punishmentUnit;
	}

	public void setPunishmentUnit(String punishmentUnit) {
		this.punishmentUnit = punishmentUnit;
	}
	
	public String getPunishmentUnitId() {
		return punishmentUnitId;
	}

	public void setPunishmentUnitId(String punishmentUnitId) {
		this.punishmentUnitId = punishmentUnitId;
	}
	
	public String getPunishment() {
		return punishment;
	}

	public void setPunishment(String punishment) {
		this.punishment = punishment;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}