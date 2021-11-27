/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 工会活动报名人员子表Entity
 * @author kevin.jia
 * @version 2020-09-04
 */
public class AffairGhActivityEnrollChildren extends DataEntity<AffairGhActivityEnrollChildren> {
	
	private static final long serialVersionUID = 1L;
	private String ghActivityEnrollId;		// 主表id
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String job;		// 职务
	@ExcelField(title = "性别", type = 0, align = 2, sort = 5, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 6)
	private Date birthday;		// 出生年月
	@ExcelField(title = "审核意见", type = 0, align = 2, sort = 7)
	private String opinion;		// 审核意见
	@ExcelField(title = "备注", type = 0, align = 2, sort = 8)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	
	public AffairGhActivityEnrollChildren() {
		super();
	}

	public AffairGhActivityEnrollChildren(String id){
		super(id);
	}

	public String getGhActivityEnrollId() {
		return ghActivityEnrollId;
	}

	public void setGhActivityEnrollId(String ghActivityEnrollId) {
		this.ghActivityEnrollId = ghActivityEnrollId;
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
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}