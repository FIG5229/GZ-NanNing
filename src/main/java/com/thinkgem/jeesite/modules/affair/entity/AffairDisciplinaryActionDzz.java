/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党组织纪律处分Entity
 * @author cecil.li
 * @version 2020-04-09
 */
public class AffairDisciplinaryActionDzz extends DataEntity<AffairDisciplinaryActionDzz> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date date;		// 时间
	@ExcelField(title = "党组织", type = 0, align = 2, sort = 1)
	private String org;		// 党组织
	private String orgId;		// 党组织id
	@ExcelField(title = "问题性质", type = 0, align = 2, sort = 2,dictType="affair_wenti")
	private String nature;		// 问题性质
	@ExcelField(title = "简要问题", type = 0, align = 2, sort = 3)
	private String violations;		// 简要问题
	@ExcelField(title = "方式", type = 0, align = 2, sort = 4,dictType="affair_dj_sub")
	private String disciplinaryType;		// 方式
	@ExcelField(title = "主办单位", type = 0, align = 2, sort = 5,dictType="affair_cf_unit")
	private String unit;		// 主办单位
	@ExcelField(title = "文号", type = 0, align = 2, sort = 6)
	private String fileNum;		// 文号
	private String annex;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 时间
	private Date endDate;		// 结束 时间
	private String pushType;   //推送状态

	private String qxUnit;
	private String qxUnitId;
	private Date startYear;    //本年度
	private String otherYear;  //其他年份
	
	public AffairDisciplinaryActionDzz() {
		super();
	}

	public AffairDisciplinaryActionDzz(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}
	
	public String getViolations() {
		return violations;
	}

	public void setViolations(String violations) {
		this.violations = violations;
	}
	
	public String getDisciplinaryType() {
		return disciplinaryType;
	}

	public void setDisciplinaryType(String disciplinaryType) {
		this.disciplinaryType = disciplinaryType;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public String getQxUnit() {
		return qxUnit;
	}

	public void setQxUnit(String qxUnit) {
		this.qxUnit = qxUnit;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public String getQxUnitId() {
		return qxUnitId;
	}

	public void setQxUnitId(String qxUnitId) {
		this.qxUnitId = qxUnitId;
	}

	@JsonFormat(pattern = "yyyy")
	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	public String getOtherYear() {
		return otherYear;
	}

	public void setOtherYear(String otherYear) {
		this.otherYear = otherYear;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
}