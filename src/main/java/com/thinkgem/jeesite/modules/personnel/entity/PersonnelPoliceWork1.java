/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 警务技术(专业技术)职务信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelPoliceWork1 extends DataEntity<PersonnelPoliceWork1> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "专业技术职务名称", type = 0, align = 2, sort = 1)
	private String jobName;		// 专业技术职务名称
	@ExcelField(title = "任职方式", type = 0, align = 2, sort = 2)
	private String method;		// 任职方式
	@ExcelField(title = "任职状态", type = 0, align = 2, sort = 3, dictType="personnel_rztype")
	private String status;		// 任职状态
	@ExcelField(title = "专业技术岗位等级", type = 0, align = 2, sort = 4)
	private String grade;		// 专业技术岗位等级
	@ExcelField(title = "任职变动类别", type = 0, align = 2, sort = 5)
	private String changeType;		// 任职变动类别
	@ExcelField(title = "任职起始日期", type = 0, align = 2, sort = 6)
	private Date startDate;		// 任职起始日期
	@ExcelField(title = "多职务主次序号", type = 0, align = 2, sort = 7)
	private String sequenceNo;		// 多职务主次序号
	@ExcelField(title = "集体内排列顺序", type = 0, align = 2, sort = 8)
	private String sort;		// 集体内排列顺序
	@ExcelField(title = "主管工作", type = 0, align = 2, sort = 9)
	private String majorWork;		// 主管工作
	@ExcelField(title = "批准文件名称或文号", type = 0, align = 2, sort = 10)
	private String file;		// 批准文件名称或文号
	@ExcelField(title = "聘任证书编号", type = 0, align = 2, sort = 11)
	private String certificateNo;		// 聘任证书编号
	@ExcelField(title = "任职预定截止日期", type = 0, align = 2, sort = 12)
	private Date endDate;		// 任职预定截止日期
	@ExcelField(title = "多职务名称描述", type = 0, align = 2, sort = 13)
	private String describe;		// 多职务名称描述
	@ExcelField(title = "名称", type = 0, align = 2, sort = 14)
	private String name;		// 名称
	@ExcelField(title = "代码", type = 0, align = 2, sort = 15)
	private String code;		// 代码
	@ExcelField(title = "所在政区", type = 0, align = 2, sort = 16)
	private String area;		// 所在政区
	@ExcelField(title = "隶属关系", type = 0, align = 2, sort = 17)
	private String relationship;		// 隶属关系
	@ExcelField(title = "级别", type = 0, align = 2, sort = 18)
	private String level;		// 级别
	@ExcelField(title = "性质类别", type = 0, align = 2, sort = 19, dictType="personnel_leibie")
	private String type;		// 性质类别
	@ExcelField(title = "所属行业", type = 0, align = 2, sort = 20)
	private String industry;		// 所属行业
	@ExcelField(title = "免职日期", type = 0, align = 2, sort = 21)
	private Date cancelDate;		// 免职日期
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	@ExcelField(title = "免职变动类别", type = 0, align = 2, sort = 22)
	private String mzType;
	private String personName;  // 姓名

	private Date beginDate;
	private Date finishDate;
	
	public PersonnelPoliceWork1() {
		super();
	}

	public PersonnelPoliceWork1(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getMajorWork() {
		return majorWork;
	}

	public void setMajorWork(String majorWork) {
		this.majorWork = majorWork;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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

	public String getMzType() {
		return mzType;
	}

	public void setMzType(String mzType) {
		this.mzType = mzType;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}