/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 抚恤申报Entity
 * @author mason.xv
 * @version 2019-11-15
 */
public class AffairCasualtyReport extends DataEntity<AffairCasualtyReport> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "种类与性质", type = 0, align = 2, sort = 2)
	private String type;		// 种类与性质
	@ExcelField(title = "部门与警种", type = 0, align = 2, sort = 3)
	private String depPolice;		// 部门与警种
	@ExcelField(title = "伤亡时间", type = 0, align = 2, sort = 4)
	private Date casualtyDate;		// 伤亡时间
	@ExcelField(title = "部门名称", type = 0, align = 2, sort = 5)
	private String affirmDep;		// 部门名称
	private String affirmDepId;		// 部门名称id
	@ExcelField(title = "认定部门", type = 0, align = 2, sort = 6)
	private String depName;		// 认定部门
	private String depNameId;		// 认定部门id
	@ExcelField(title = "认定时间", type = 0, align = 2, sort = 7)
	private Date affirmDate;		// 认定时间
	@ExcelField(title = "复核部门", type = 0, align = 2, sort = 8)
	private String checkDep;		// 复核部门

	private Date checkDepId;		// 复核部门id
	@ExcelField(title = "复核时间", type = 0, align = 2, sort = 9)
	private Date checkDate;		// 复核时间
	@ExcelField(title = "牺牲/病故证明书编号", type = 0, align = 2, sort = 10)
	private String certificateNo1;		// 牺牲/病故证明书编号
	@ExcelField(title = "负伤程度", type = 0, align = 2, sort = 11)
	private String injuryDegree;		// 负伤程度
	@ExcelField(title = "残疾等级", type = 0, align = 2, sort = 12)
	private String level;		// 残疾等级
	@ExcelField(title = "伤残评定机构", type = 0, align = 2, sort = 13)
	private String evaluateOrg;		// 伤残评定机构
	@ExcelField(title = "记录状态", type = 0, align = 2, sort = 14)
	private String jlStatus;		// 记录状态
	@ExcelField(title = "是否自杀", type = 0, align = 2, sort = 15, dictType="yes_no")
	private String isSelfKill;		// 是否自杀（1：是 2：否）
	@ExcelField(title = "伤亡原因", type = 0, align = 2, sort = 16)
	private String reason;		// 伤亡原因
	@ExcelField(title = "被伤害方式", type = 0, align = 2, sort = 17)
	private String method;		// 被伤害方式
	@ExcelField(title = "执行勤务情况", type = 0, align = 2, sort = 18)
	private String zxqwSituation;		// 执行勤务情况
	@ExcelField(title = "伤亡事件实力对比", type = 0, align = 2, sort = 19)
	private String comparison;		// 伤亡事件实力对比
	@ExcelField(title = "使用武器警械", type = 0, align = 2, sort = 20)
	private String useArm;		// 使用武器警械
	@ExcelField(title = "伤亡情节", type = 0, align = 2, sort = 21)
	private String plot;		// 伤亡情节
	@ExcelField(title = "发生日期", type = 0, align = 2, sort = 22)
	private Date happenDate;		// 发生日期
	@ExcelField(title = "事故性质", type = 0, align = 2, sort = 23)
	private String character;		// 事故性质
	@ExcelField(title = "责任认定", type = 0, align = 2, sort = 24)
	private String dutyAffirm;		// 责任认定
	@ExcelField(title = "违章情况", type = 0, align = 2, sort = 25)
	private String wzSituation;		// 违章情况
	@ExcelField(title = "烈士标识", type = 0, align = 2, sort = 26)
	private String martyrLogo;		// 烈士标识
	@ExcelField(title = "证书编号", type = 0, align = 2, sort = 27)
	private String certificateNo2;		// 证书编号
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 28)
	private Date approvalDate;		// 批准日期
	@ExcelField(title = "批准部门", type = 0, align = 2, sort = 29)
	private String approvalDep;		// 批准部门
	@ExcelField(title = "认定因公牺牲文件", type = 0, align = 2, sort = 30)
	private String rdygxsFile;		// 认定因公牺牲文件
	@ExcelField(title = "民政（人社）部门复核认定文件", type = 0, align = 2, sort = 31)
	private String bmfhrdFile;		// 民政（人社）部门复核认定文件
	@ExcelField(title = "烈士批复文件", type = 0, align = 2, sort = 32)
	private String lspfFile;		// 烈士批复文件
	@ExcelField(title = "备注", type = 0, align = 2, sort = 33)
	private String remark;		// 备注
	@ExcelField(title = "人民警察牺牲/病故证明书", type = 0, align = 2, sort = 34)
	private String certificatePath;		// 人民警察牺牲/病故证明书
	@ExcelField(title = "审核意见", type = 0, align = 2, sort = 35)
	private String opinion;		// 审核意见
	@ExcelField(title = "审核状态", type = 0, align = 2, sort = 36, dictType="affair_query_shenhe")
	private String shStatus;		// 审核状态（1：通过 2：不通过）

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginCasualtyDate;		// 开始 伤亡时间
	private Date endCasualtyDate;		// 结束 伤亡时间
	private String hasAuth;        //审核权限

	private String shPerson;    //审核人

	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public AffairCasualtyReport() {
		super();
	}

	public AffairCasualtyReport(String id){
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

	public String getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(String hasAuth) {
		this.hasAuth = hasAuth;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDepPolice() {
		return depPolice;
	}

	public void setDepPolice(String depPolice) {
		this.depPolice = depPolice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCasualtyDate() {
		return casualtyDate;
	}

	public void setCasualtyDate(Date casualtyDate) {
		this.casualtyDate = casualtyDate;
	}
	
	public String getAffirmDep() {
		return affirmDep;
	}

	public void setAffirmDep(String affirmDep) {
		this.affirmDep = affirmDep;
	}
	
	public String getAffirmDepId() {
		return affirmDepId;
	}

	public void setAffirmDepId(String affirmDepId) {
		this.affirmDepId = affirmDepId;
	}
	
	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getAffirmDate() {
		return affirmDate;
	}

	public void setAffirmDate(Date affirmDate) {
		this.affirmDate = affirmDate;
	}
	
	public String getCheckDep() {
		return checkDep;
	}

	public void setCheckDep(String checkDep) {
		this.checkDep = checkDep;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCheckDepId() {
		return checkDepId;
	}

	public void setCheckDepId(Date checkDepId) {
		this.checkDepId = checkDepId;
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
	
	public String getInjuryDegree() {
		return injuryDegree;
	}

	public void setInjuryDegree(String injuryDegree) {
		this.injuryDegree = injuryDegree;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getEvaluateOrg() {
		return evaluateOrg;
	}

	public void setEvaluateOrg(String evaluateOrg) {
		this.evaluateOrg = evaluateOrg;
	}
	
	public String getJlStatus() {
		return jlStatus;
	}

	public void setJlStatus(String jlStatus) {
		this.jlStatus = jlStatus;
	}
	
	public String getIsSelfKill() {
		return isSelfKill;
	}

	public void setIsSelfKill(String isSelfKill) {
		this.isSelfKill = isSelfKill;
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
	
	public String getZxqwSituation() {
		return zxqwSituation;
	}

	public void setZxqwSituation(String zxqwSituation) {
		this.zxqwSituation = zxqwSituation;
	}
	
	public String getComparison() {
		return comparison;
	}

	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	public String getUseArm() {
		return useArm;
	}

	public void setUseArm(String useArm) {
		this.useArm = useArm;
	}
	
	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getHappenDate() {
		return happenDate;
	}

	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}
	
	public String getDutyAffirm() {
		return dutyAffirm;
	}

	public void setDutyAffirm(String dutyAffirm) {
		this.dutyAffirm = dutyAffirm;
	}
	
	public String getWzSituation() {
		return wzSituation;
	}

	public void setWzSituation(String wzSituation) {
		this.wzSituation = wzSituation;
	}
	
	public String getMartyrLogo() {
		return martyrLogo;
	}

	public void setMartyrLogo(String martyrLogo) {
		this.martyrLogo = martyrLogo;
	}
	
	public String getCertificateNo2() {
		return certificateNo2;
	}

	public void setCertificateNo2(String certificateNo2) {
		this.certificateNo2 = certificateNo2;
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
	
	public String getRdygxsFile() {
		return rdygxsFile;
	}

	public void setRdygxsFile(String rdygxsFile) {
		this.rdygxsFile = rdygxsFile;
	}
	
	public String getBmfhrdFile() {
		return bmfhrdFile;
	}

	public void setBmfhrdFile(String bmfhrdFile) {
		this.bmfhrdFile = bmfhrdFile;
	}
	
	public String getLspfFile() {
		return lspfFile;
	}

	public void setLspfFile(String lspfFile) {
		this.lspfFile = lspfFile;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCertificatePath() {
		return certificatePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getShStatus() {
		return shStatus;
	}

	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
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
	
	public Date getBeginCasualtyDate() {
		return beginCasualtyDate;
	}

	public void setBeginCasualtyDate(Date beginCasualtyDate) {
		this.beginCasualtyDate = beginCasualtyDate;
	}
	
	public Date getEndCasualtyDate() {
		return endCasualtyDate;
	}

	public void setEndCasualtyDate(Date endCasualtyDate) {
		this.endCasualtyDate = endCasualtyDate;
	}

	public String getDepNameId() {
		return depNameId;
	}

	public void setDepNameId(String depNameId) {
		this.depNameId = depNameId;
	}
}