/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 伤亡信息集Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelCasualty extends DataEntity<PersonnelCasualty> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "种类与性质", type = 0, align = 2, sort = 1)
	private String type;		// 种类与性质
	@ExcelField(title = "部门与警种", type = 0, align = 2, sort = 2)
	private String departmentPolice;		// 部门与警种
	@ExcelField(title = "伤亡时间", type = 0, align = 2, sort = 3)
	private Date date;		// 伤亡时间
	@ExcelField(title = "认定部门", type = 0, align = 2, sort = 4)
	private String affirmDepartment;		// 认定部门
	@ExcelField(title = "部门名称", type = 0, align = 2, sort = 5)
	private String department;		// 部门名称
	@ExcelField(title = "认定时间", type = 0, align = 2, sort = 6)
	private Date affirmDate;		// 认定时间
	@ExcelField(title = "复核部门", type = 0, align = 2, sort = 7)
	private String checkDepartment;		// 复核部门
	@ExcelField(title = "复核时间", type = 0, align = 2, sort = 8)
	private Date checkDate;		// 复核时间
	@ExcelField(title = "牺牲/病故证明书编号", type = 0, align = 2, sort = 9)
	private String certificateNo1;		// 牺牲/病故证明书编号
	@ExcelField(title = "负伤程度", type = 0, align = 2, sort = 10)
	private String injuredDegree;		// 负伤程度
	@ExcelField(title = "致残等级", type = 0, align = 2, sort = 11, dictType="personnel_zclevel")
	private String disabilityLevel;		// 致残等级
	@ExcelField(title = "伤残评定机构", type = 0, align = 2, sort = 12)
	private String evaluateOrg;		// 伤残评定机构
	@ExcelField(title = "记录状态", type = 0, align = 2, sort = 13)
	private String recordState;		// 记录状态
	@ExcelField(title = "是否自杀", type = 0, align = 2, sort = 14, dictType="yes_no")
	private String isKill;		// 是否自杀
	@ExcelField(title = "伤亡原因", type = 0, align = 2, sort = 15)
	private String reason;		// 伤亡原因
	@ExcelField(title = "被伤害方式", type = 0, align = 2, sort = 16)
	private String method;		// 被伤害方式
	@ExcelField(title = "执行勤务情况", type = 0, align = 2, sort = 17)
	private String dutySituation;		// 执行勤务情况
	@ExcelField(title = "伤亡事件实力对比", type = 0, align = 2, sort = 18)
	private String comparison;		// 伤亡事件实力对比
	@ExcelField(title = "使用武器警械", type = 0, align = 2, sort =19 )
	private String weaponry;		// 使用武器警械
	@ExcelField(title = "伤亡情节", type = 0, align = 2, sort = 20)
	private String plot;		// 伤亡情节
	@ExcelField(title = "发生日期", type = 0, align = 2, sort = 21)
	private Date occurDate;		// 发生日期
	@ExcelField(title = "事故性质", type = 0, align = 2, sort = 22)
	private String character;		// 事故性质
	@ExcelField(title = "责任认定", type = 0, align = 2, sort = 23)
	private String decide;		// 责任认定
	@ExcelField(title = "违章情况", type = 0, align = 2, sort = 24)
	private String situation;		// 违章情况
	@ExcelField(title = "烈士标识", type = 0, align = 2, sort = 25)
	private String identification;		// 烈士标识
	@ExcelField(title = "证书编号", type = 0, align = 2, sort = 26)
	private String certificate2No;		// 证书编号
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 27)
	private Date approvalDate;		// 批准日期
	@ExcelField(title = "批准部门", type = 0, align = 2, sort = 28)
	private String approvalDep;		// 批准部门
	@ExcelField(title = "备注", type = 0, align = 2, sort =29 )
	private String remark;		// 备注
	@ExcelField(title = "认定因公牺牲文件", type = 0, align = 2, sort = 30)
	private String sacrificeFile;		// 认定因公牺牲文件
	@ExcelField(title = "民政(人社)部门复核认定文件", type = 0, align = 2, sort =31 )
	private String checkFile;		// 民政(人社)部门复核认定文件
	@ExcelField(title = "烈士批复文件", type = 0, align = 2, sort =32 )
	private String replyFile;		// 烈士批复文件
	@ExcelField(title = "人民警察牺牲/病故证明书", type = 0, align = 2, sort = 33)
	private String proveBook;		// 人民警察牺牲/病故证明书
	private String status;		// 审核状态
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 34)
	private String idNumber;		// 身份证号

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号



	private Date startDate;
	private Date endDate;

	public PersonnelCasualty() {
		super();
	}

	public PersonnelCasualty(String id){
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
	
	public String getDepartmentPolice() {
		return departmentPolice;
	}

	public void setDepartmentPolice(String departmentPolice) {
		this.departmentPolice = departmentPolice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getAffirmDepartment() {
		return affirmDepartment;
	}

	public void setAffirmDepartment(String affirmDepartment) {
		this.affirmDepartment = affirmDepartment;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getAffirmDate() {
		return affirmDate;
	}

	public void setAffirmDate(Date affirmDate) {
		this.affirmDate = affirmDate;
	}
	
	public String getCheckDepartment() {
		return checkDepartment;
	}

	public void setCheckDepartment(String checkDepartment) {
		this.checkDepartment = checkDepartment;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getCertificateNo1() {
		return certificateNo1;
	}

	public void setCertificateNo1(String certificateNo1) {
		this.certificateNo1 = certificateNo1;
	}
	
	public String getInjuredDegree() {
		return injuredDegree;
	}

	public void setInjuredDegree(String injuredDegree) {
		this.injuredDegree = injuredDegree;
	}
	
	public String getDisabilityLevel() {
		return disabilityLevel;
	}

	public void setDisabilityLevel(String disabilityLevel) {
		this.disabilityLevel = disabilityLevel;
	}
	
	public String getEvaluateOrg() {
		return evaluateOrg;
	}

	public void setEvaluateOrg(String evaluateOrg) {
		this.evaluateOrg = evaluateOrg;
	}
	
	public String getRecordState() {
		return recordState;
	}

	public void setRecordState(String recordState) {
		this.recordState = recordState;
	}
	
	public String getIsKill() {
		return isKill;
	}

	public void setIsKill(String isKill) {
		this.isKill = isKill;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getDutySituation() {
		return dutySituation;
	}

	public void setDutySituation(String dutySituation) {
		this.dutySituation = dutySituation;
	}
	
	public String getComparison() {
		return comparison;
	}

	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	public String getWeaponry() {
		return weaponry;
	}

	public void setWeaponry(String weaponry) {
		this.weaponry = weaponry;
	}
	
	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}
	
	public String getDecide() {
		return decide;
	}

	public void setDecide(String decide) {
		this.decide = decide;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getCertificate2No() {
		return certificate2No;
	}

	public void setCertificate2No(String certificate2No) {
		this.certificate2No = certificate2No;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public String getApprovalDep() {
		return approvalDep;
	}

	public void setApprovalDep(String approvalDep) {
		this.approvalDep = approvalDep;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getSacrificeFile() {
		return sacrificeFile;
	}

	public void setSacrificeFile(String sacrificeFile) {
		this.sacrificeFile = sacrificeFile;
	}
	
	public String getCheckFile() {
		return checkFile;
	}

	public void setCheckFile(String checkFile) {
		this.checkFile = checkFile;
	}
	
	public String getReplyFile() {
		return replyFile;
	}

	public void setReplyFile(String replyFile) {
		this.replyFile = replyFile;
	}
	
	public String getProveBook() {
		return proveBook;
	}

	public void setProveBook(String proveBook) {
		this.proveBook = proveBook;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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