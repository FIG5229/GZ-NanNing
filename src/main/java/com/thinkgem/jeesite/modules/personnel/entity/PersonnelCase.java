/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 案件技术支持信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelCase extends DataEntity<PersonnelCase> {
	
	private static final long serialVersionUID = 1L;
	private String personnelName;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "案件名称", type = 0, align = 2, sort = 1)
	private String name;		// 案件名称
	@ExcelField(title = "案件级别", type = 0, align = 2, sort = 2, dictType="personnel_anjian")
	private String level;		// 案件级别
	@ExcelField(title = "发案日期", type = 0, align = 2, sort = 3)
	private Date date;		// 发案日期
	@ExcelField(title = "涉案人伤亡情况", type = 0, align = 2, sort = 4)
	private String situation;		// 涉案人伤亡情况
	@ExcelField(title = "鉴定结果是否为侦察提供方向或者直接认定犯罪", type = 0, align = 2, sort = 5, dictType="yes_no")
	private String isDirection;		// 鉴定结果是否为侦察提供方向或者直接认定犯罪
	@ExcelField(title = "鉴定书是否被法庭采用为审判证据", type = 0, align = 2, sort = 6, dictType="yes_no")
	private String isEvidence;		// 鉴定书是否被法庭采用为审判证据
	@ExcelField(title = "涉案金额", type = 0, align = 2, sort = 7)
	private Double money;		// 涉案金额
	@ExcelField(title = "证明人", type = 0, align = 2, sort = 8)
	private String witness;		// 证明人
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelCase() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelCase(String id){
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
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getIsDirection() {
		return isDirection;
	}

	public void setIsDirection(String isDirection) {
		this.isDirection = isDirection;
	}
	
	public String getIsEvidence() {
		return isEvidence;
	}

	public void setIsEvidence(String isEvidence) {
		this.isEvidence = isEvidence;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	public String getWitness() {
		return witness;
	}

	public void setWitness(String witness) {
		this.witness = witness;
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