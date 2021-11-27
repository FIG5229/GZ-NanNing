/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 通讯员管理Entity
 * @author Alan.wu
 * @version 2020-06-18
 */
public class AffairCorrespondent extends DataEntity<AffairCorrespondent> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名

	@ExcelField(title = "单位", type = 0, align = 2, sort = 2)
	private String unit;		// 单位

	private String unitId;		// 单位id

	@ExcelField(title = "职务", type = 0, align = 2, sort = 3)
	private String duty;		// 职务

	@ExcelField(title = "联系方式", type = 0, align = 2, sort = 4)
	private String phone;		// 联系方式

	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号

	//@ExcelField(title = "通讯稿件",type = 0,align = 2, sort = 4)
	private List<String> affairNews;	//稿件

	@ExcelField(title = "培训情况",type = 0,align = 2, sort = 5)
	private String remarks;

	/*新增字段*/
	@ExcelField(title = "通讯员等级",type = 0,align = 2, sort = 6, dictType = "correspondent_star")
	private String star;		//通讯员等级

	private String adjunct;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getAffairNews() {
		return affairNews;
	}

	public void setAffairNews(List<String> affairNews) {
		this.affairNews = affairNews;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AffairCorrespondent() {
		super();
	}

	public AffairCorrespondent(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}
}