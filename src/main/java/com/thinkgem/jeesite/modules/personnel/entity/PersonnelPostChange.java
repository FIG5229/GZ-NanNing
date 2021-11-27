/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 岗位变动信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelPostChange extends DataEntity<PersonnelPostChange> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "岗位名称", type = 0, align = 2, sort = 1)
	private String name;		// 岗位名称
	@ExcelField(title = "岗位类别", type = 0, align = 2, sort = 2, dictType="personnel_gwlb")
	private String type;		// 岗位类别
	@ExcelField(title = "是否在保密岗位", type = 0, align = 2, sort = 3, dictType="yes_no")
	private String isSecretPost;		// 是否在保密岗位
	@ExcelField(title = "工作单位名称", type = 0, align = 2, sort = 4)
	private String unitName;		// 工作单位名称
	@ExcelField(title = "起始日期", type = 0, align = 2, sort = 5)
	private Date startDate;		// 起始日期
	@ExcelField(title = "终止日期", type = 0, align = 2, sort = 6)
	private Date endDate;		// 终止日期
	@ExcelField(title = "岗位A角色", type = 0, align = 2, sort = 7)
	private String postA;		// 岗位A角色
	@ExcelField(title = "岗位B角色", type = 0, align = 2, sort = 8)
	private String postB;		// 岗位B角色
	@ExcelField(title = "多岗位描述", type = 0, align = 2, sort = 9)
	private String describe;		// 多岗位描述
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String personName;   // 姓名

	private Date beginDate;
	private Date finishDate;
	
	public PersonnelPostChange() {
		super();
	}

	public PersonnelPostChange(String id){
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIsSecretPost() {
		return isSecretPost;
	}

	public void setIsSecretPost(String isSecretPost) {
		this.isSecretPost = isSecretPost;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
	
	public String getPostA() {
		return postA;
	}

	public void setPostA(String postA) {
		this.postA = postA;
	}
	
	public String getPostB() {
		return postB;
	}

	public void setPostB(String postB) {
		this.postB = postB;
	}
	
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}