/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 警务技术任职资格表Entity
 * @author mason.xv
 * @version 2019-11-07
 */
public class AffairQualification extends DataEntity<AffairQualification> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "任职资格名称", type = 0, align = 2, sort = 2)
	private String jobName;		// 任职资格名称
	@ExcelField(title = "取得资格日期", type = 0, align = 2, sort = 3)
	private Date date;		// 取得资格日期
	@ExcelField(title = "任职资格级别", type = 0, align = 2, sort = 4, dictType="police_technical_qualification_level")
	private String level;		// 任职资格级别
	@ExcelField(title = "取得资格途径", type = 0, align = 2, sort = 5)
	private String channel;		// 取得资格途径
	@ExcelField(title = "准资格评定文件名称（文号）", type = 0, align = 6, sort = 0)
	private String fileNo;		// 准资格评定文件名称（文号）
	@ExcelField(title = "证书编号", type = 0, align = 2, sort = 7)
	private String certificateNo;		// 证书编号
	@ExcelField(title = "专业方向", type = 0, align = 2, sort = 8)
	private String direction;		// 专业方向
	@ExcelField(title = "资格考试年度", type = 0, align = 2, sort = 9)
	private String examYear;		// 资格考试年度
	@ExcelField(title = "资格评审年度", type = 0, align = 2, sort = 10)
	private String reviewYear;		// 资格评审年度
	@ExcelField(title = "考试名称", type = 0, align = 2, sort = 11)
	private String examName;		// 考试名称
	@ExcelField(title = "评委会名称", type = 0, align = 2, sort = 12)
	private String juryName;		// 评委会名称

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 取得资格日期
	private Date endDate;		// 结束 取得资格日期
	
	public AffairQualification() {
		super();
	}

	public AffairQualification(String id){
		super(id);
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
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
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
		
}