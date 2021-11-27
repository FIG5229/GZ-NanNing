/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 党员民主评议Entity
 * @author eav.liu
 * @version 2019-11-11
 */
public class AffairComment extends DataEntity<AffairComment> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 党员姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 1, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "警号", type = 0, align = 2, sort = 2)
	private String policeNo;		// 警号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "评议年度", type = 0, align = 2, sort = 4)
	private String year;		// 评议年度
	@ExcelField(title = "评议时间", type = 0, align = 2, sort = 6)
	private Date date;		// 评议时间
	@ExcelField(title = "参加评议人数", type = 0, align = 2, sort = 7)
	private Integer personNum;		// 参加评议人数
	@ExcelField(title = "评议结果", type = 0, align = 2, sort = 8, dictType = "affair_comment_grade")
	private String grade;		// 评议结果
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9)
	private String result;		// 备注
	@ExcelField(title = "党组织名称", type = 0, align = 2, sort = 5)
	private String partyOrganization;        // 党组织名称
	private String partyOrganizationId;        // 党组织id

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;

	private Date endDate;

	private String treeId;	//接受前端传来的左侧树的id

	private String status;    // 审核状态
	private String opinion;   // 审核意见

	private String flag;

	private String pbId;	// 党组织id

	private List<String> pbIds;

	private String dateType;

	private Integer echartYear;

	private String dateStart;

	private String dateEnd;

	private String month;

	private Date echartStartDate;

	private Date echartEndDate;
	
	public AffairComment() {
		super();
	}

	public AffairComment(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Integer getPersonNum() {
		return personNum;
	}

	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getPartyOrganization() {
		return partyOrganization;
	}

	public void setPartyOrganization(String partyOrganization) {
		this.partyOrganization = partyOrganization;
	}

	public String getPartyOrganizationId() {
		return partyOrganizationId;
	}

	public void setPartyOrganizationId(String partyOrganizationId) {
		this.partyOrganizationId = partyOrganizationId;
	}

	public String getPbId() {
		return pbId;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public List<String> getPbIds() {
		return pbIds;
	}

	public void setPbIds(List<String> pbIds) {
		this.pbIds = pbIds;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getEchartYear() {
		return echartYear;
	}

	public void setEchartYear(Integer echartYear) {
		this.echartYear = echartYear;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getEchartStartDate() {
		return echartStartDate;
	}

	public void setEchartStartDate(Date echartStartDate) {
		this.echartStartDate = echartStartDate;
	}

	public Date getEchartEndDate() {
		return echartEndDate;
	}

	public void setEchartEndDate(Date echartEndDate) {
		this.echartEndDate = echartEndDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}