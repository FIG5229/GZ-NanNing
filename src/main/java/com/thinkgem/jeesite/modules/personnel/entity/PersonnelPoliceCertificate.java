/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 人民警察证信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelPoliceCertificate extends DataEntity<PersonnelPoliceCertificate> {
	
	private static final long serialVersionUID = 1L;
	/*更换为原来模板（A59）*/
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	private String personnelName;		// 姓名
	@ExcelField(title = "居民身份号码", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "警察证号", type = 0, align = 2, sort = 2)
	private String certificateNo;		// 警察证号
	@ExcelField(title = "制发原因", type = 0, align = 2, sort = 3, dictType = "certification_type")
	private String createReason;		// 制发原因
	@ExcelField(title = "警察证状态", type = 0, align = 2, sort = 4, dictType="personnel_jcztype")
	private String status;		// 警察证状态
	@ExcelField(title = "发证日期", type = 0, align = 2, sort = 5)
	private Date sendDate;		// 发证日期
	@ExcelField(title = "有效期起始日期", type = 0, align = 2, sort = 6)
	private Date startState;		// 有效期起始日期
	@ExcelField(title = "有效期截至日期", type = 0, align = 2, sort = 7)
	private Date endDate;		// 有效期截至日期
	@ExcelField(title = "发证机关", type = 0, align = 2, sort = 8)
	private String orgName;		// 发证机关
	@ExcelField(title = "收回销毁日期", type = 0, align = 2, sort = 9)
	private Date destroyDate;		// 收回销毁日期
	@ExcelField(title = "收回原因", type = 0, align = 2, sort = 10)
	private String backReason;		// 收回原因
	@ExcelField(title = "审核人", type = 0, align = 2, sort = 11)
	private String examinePerson;		// 审核人
	@ExcelField(title = "备注", type = 0, align = 2, sort = 12)
	private String remark;		// 备注

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String orgNameId;		// 发证机关id

	//新加字段
	/*不在使用,避免信息保存两份导致不一致*/
//	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;
	private String unitId;
//	@ExcelField(title = "部门", type = 0, align = 2, sort = 2)
	private String department;
	private String departmentId;
//	@ExcelField(title = "职务", type = 0, align = 2, sort = 7)
	private String job;
//	@ExcelField(title = "级别", type = 0, align = 2, sort = 8, dictType = "post_level")
	private String jobLevel;
//	@ExcelField(title = "性别", type = 0, align = 2, sort = 4, dictType = "sex")
	private String sex;
//	@ExcelField(title = "出生日期", type = 0, align = 2, sort = 5)
	private Date birthday;

	/*导出使用另一套框架,不在使用实体类*/
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
//	private String index;

	private Date beginDate;
	private Date finishDate;

	//有效期截止起始日期
	private Date startEndDate;
	//有效期截止 截止日期
	private Date endEndDate;


	/*前端  一键回填日期*/
	private Date backFillDate;

	private String blood;

	private String userId;

	private String parentIds;//定时预警三月后警察证到期
	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public PersonnelPoliceCertificate() {
		super();
	}

	public PersonnelPoliceCertificate(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public String getCreateReason() {
		return createReason;
	}

	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartState() {
		return startState;
	}

	public void setStartState(Date startState) {
		this.startState = startState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDestroyDate() {
		return destroyDate;
	}

	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}
	
	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	
	public String getExaminePerson() {
		return examinePerson;
	}

	public void setExaminePerson(String examinePerson) {
		this.examinePerson = examinePerson;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(String orgNameId) {
		this.orgNameId = orgNameId;
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

	public Date getStartEndDate() {
		return startEndDate;
	}

	public void setStartEndDate(Date startEndDate) {
		this.startEndDate = startEndDate;
	}

	public Date getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(Date endEndDate) {
		this.endEndDate = endEndDate;
	}

	public Date getBackFillDate() {
		return backFillDate;
	}

	public void setBackFillDate(Date backFillDate) {
		this.backFillDate = backFillDate;
	}

/*	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}*/

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
}