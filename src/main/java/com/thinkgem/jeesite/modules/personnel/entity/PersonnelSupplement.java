/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 补充信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelSupplement extends DataEntity<PersonnelSupplement> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "曾用名", type = 0, align = 2, sort = 1)
	private String preName;		// 曾用名
	private String personnelName;		// 姓名
	@ExcelField(title = "烈军属标识", type = 0, align = 2, sort = 2)
	private String identification1;		// 烈军属标识
	@ExcelField(title = "伤残军人标识", type = 0, align = 2, sort = 3)
	private String identification2;		// 伤残军人标识
	@ExcelField(title = "侨胞标识", type = 0, align = 2, sort = 4)
	private String identification3;		// 侨胞标识
	@ExcelField(title = "夫妻两地分居起始日期", type = 0, align = 2, sort = 5)
	private Date startDate;		// 夫妻两地分居起始日期
	@ExcelField(title = "夫妻两地分居终止日期", type = 0, align = 2, sort = 6)
	private Date endDate;		// 夫妻两地分居终止日期
	@ExcelField(title = "参加第二党派名称", type = 0, align = 2, sort = 7)
	private String joinName;		// 参加第二党派名称
	@ExcelField(title = "参加第二党派日期", type = 0, align = 2, sort = 8)
	private Date joinDate;		// 参加第二党派日期
	@ExcelField(title = "烈士记载", type = 0, align = 2, sort = 9)
	private String martyrRecord;		// 烈士记载
	@ExcelField(title = "政治面貌变异历史", type = 0, align = 2, sort = 10)
	private String changeHistory;		// 政治面貌变异历史
	@ExcelField(title = "宗教信仰", type = 0, align = 2, sort = 11)
	private String religionBelief;		// 宗教信仰
	@ExcelField(title = "婚姻历史", type = 0, align = 2, sort = 12)
	private String marriageHistory;		// 婚姻历史
	@ExcelField(title = "逝世标识", type = 0, align = 2, sort = 13, dictType="personnel_shishi")
	private String identification4;		// 逝世标识
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelSupplement() {
		super();
	}

	public PersonnelSupplement(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}
	
	public String getIdentification1() {
		return identification1;
	}

	public void setIdentification1(String identification1) {
		this.identification1 = identification1;
	}
	
	public String getIdentification2() {
		return identification2;
	}

	public void setIdentification2(String identification2) {
		this.identification2 = identification2;
	}
	
	public String getIdentification3() {
		return identification3;
	}

	public void setIdentification3(String identification3) {
		this.identification3 = identification3;
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
	
	public String getJoinName() {
		return joinName;
	}

	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	public String getMartyrRecord() {
		return martyrRecord;
	}

	public void setMartyrRecord(String martyrRecord) {
		this.martyrRecord = martyrRecord;
	}
	
	public String getChangeHistory() {
		return changeHistory;
	}

	public void setChangeHistory(String changeHistory) {
		this.changeHistory = changeHistory;
	}
	
	public String getReligionBelief() {
		return religionBelief;
	}

	public void setReligionBelief(String religionBelief) {
		this.religionBelief = religionBelief;
	}
	
	public String getMarriageHistory() {
		return marriageHistory;
	}

	public void setMarriageHistory(String marriageHistory) {
		this.marriageHistory = marriageHistory;
	}
	
	public String getIdentification4() {
		return identification4;
	}

	public void setIdentification4(String identification4) {
		this.identification4 = identification4;
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
}