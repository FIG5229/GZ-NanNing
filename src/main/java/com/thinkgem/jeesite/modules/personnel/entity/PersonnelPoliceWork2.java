/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 警务技术(专业技术)任职资格信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelPoliceWork2 extends DataEntity<PersonnelPoliceWork2> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "任职资格名称", type = 0, align = 2, sort = 1)
	private String name;		// 任职资格名称
	@ExcelField(title = "取得资格日期", type = 0, align = 2, sort = 2)
	private Date date;		// 取得资格日期
	@ExcelField(title = "任职资格级别", type = 0, align = 2, sort = 3)
	private String level;		// 任职资格级别
	@ExcelField(title = "取得资格途径", type = 0, align = 2, sort = 4)
	private String channel;		// 取得资格途径
	@ExcelField(title = "批准资格评定文件名称(文号)", type = 0, align = 2, sort = 5)
	private String file;		// 批准资格评定文件名称(文号)
	@ExcelField(title = "证书编号", type = 0, align = 2, sort = 6)
	private String certificateNo;		// 证书编号
	@ExcelField(title = "专业方向", type = 0, align = 2, sort = 7)
	private String direction;		// 专业方向
	@ExcelField(title = "资格考试年度", type = 0, align = 2, sort = 8)
	private String examYear;		// 资格考试年度
	@ExcelField(title = "资格评审年度", type = 0, align = 2, sort = 9)
	private String reviewYear;		// 资格评审年度
	@ExcelField(title = "考试名称", type = 0, align = 2, sort = 10)
	private String examName;		// 考试名称
	@ExcelField(title = "评委会名称", type = 0, align = 2, sort = 11)
	private String juryName;		// 评委会名称
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String personName;  // 姓名

	private Date startDate;
	private Date endDate;
	
	public PersonnelPoliceWork2() {
		super();
	}

	public PersonnelPoliceWork2(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getExamYear() {
		return examYear;
	}

	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}
	
	public String getReviewYear() {
		return reviewYear;
	}

	public void setReviewYear(String reviewYear) {
		this.reviewYear = reviewYear;
	}
	
	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	public String getJuryName() {
		return juryName;
	}

	public void setJuryName(String juryName) {
		this.juryName = juryName;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}