/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 执法资格等级考试情况信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelExam extends DataEntity<PersonnelExam> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "执法资格类别", type = 0, align = 2, sort = 1, dictType="personnel_zfzglb")
	private String type;		// 执法资格类别
	@ExcelField(title = "执法资格等级", type = 0, align = 2, sort = 2, dictType="personnel_zfzgdj")
	private String grade;		// 执法资格等级
	@ExcelField(title = "报考时警种", type = 0, align = 2, sort = 3, dictType="personnel_jingzhong")
	private String policeType;		// 报考时警种
	@ExcelField(title = "执法资格考核结果", type = 0, align = 2, sort = 4, dictType="personnel_khjg")
	private String result;		// 执法资格考核结果
	@ExcelField(title = "执法资格考试通过日期", type = 0, align = 2, sort = 5)
	private Date passDate;		// 执法资格考试通过日期
	@ExcelField(title = "执法资格考试成绩", type = 0, align = 2, sort = 6)
	private Double score;		// 执法资格考试成绩
	@ExcelField(title = "执法资格考核通过日期", type = 0, align = 2, sort = 7)
	private Date tgDate;
	@ExcelField(title = "执法资格等级确认日期", type = 0, align = 2, sort = 8)
	private Date confirmDate;		// 执法资格等级确认日期
	@ExcelField(title = "记录状态", type = 0, align = 2, sort = 9)
	private String status;		// 记录状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public PersonnelExam() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelExam(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getPoliceType() {
		return policeType;
	}

	public void setPoliceType(String policeType) {
		this.policeType = policeType;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public Date getTgDate() {
		return tgDate;
	}

	public void setTgDate(Date tgDate) {
		this.tgDate = tgDate;
	}
}