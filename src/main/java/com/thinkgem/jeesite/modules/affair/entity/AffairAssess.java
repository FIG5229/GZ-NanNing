/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsAttach;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党委书记述职测评Entity
 * @author eav.liu
 * @version 2019-11-11
 */
public class AffairAssess extends DataEntity<AffairAssess> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "党组织书记", type = 0, align = 2, sort = 1)
	private String shuji;		// 党组织书记
	@ExcelField(title = "党组织书记身份证号", type = 0, align = 2, sort = 2)
	private String shujiIdNo;		// 党组织书记身份证号
	@ExcelField(title = "所属党组织", type = 0, align = 2, sort = 3)
	private String partyOrganization;		// 党组织名称

	private String partyOrganizationId;		// 党组织id
	@ExcelField(title = "测评时间", type = 0, align = 2, sort = 4)
	private Date date;		// 测评时间
	@ExcelField(title = "参加测评人数", type = 0, align = 2, sort = 5)
	private Integer personNum;		// 参加测评人数
	@ExcelField(title = "测评等次", type = 0, align = 2, sort = 6, dictType = "affair_assess_grade")
	private String grade;		// 测评等次
	@ExcelField(title = "述职年度", type = 0, align = 2, sort = 0)
	private String year;		// 述职年度
	@EsAttach
	private String filePath;		// 相关文件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String treeId;	//接受前端传来的左侧树的id

	private String status;   // 审核状态
	private String opinion;  // 审核意见

	/*统计分析使用*/
	private String dateType;
	private Integer years;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;
	private Date startDate;
	private Date endDate;

	public AffairAssess() {
		super();
	}

	public AffairAssess(String id){
		super(id);
	}

	public String getShuji() {
		return shuji;
	}

	public void setShuji(String shuji) {
		this.shuji = shuji;
	}

	public String getShujiIdNo() {
		return shujiIdNo;
	}

	public void setShujiIdNo(String shujiIdNo) {
		this.shujiIdNo = shujiIdNo;
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
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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