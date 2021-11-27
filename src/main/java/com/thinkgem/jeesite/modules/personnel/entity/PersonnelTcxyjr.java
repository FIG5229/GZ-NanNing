/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 退出现役军人（武警）信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelTcxyjr extends DataEntity<PersonnelTcxyjr> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证
	private String personnelName;		// 姓名
	@ExcelField(title = "退出现役类别", type = 0, align = 2, sort = 1, dictType="personnel_tcxylb")
	private String retiredType;		// 退出现役类别
	@ExcelField(title = "入伍日期", type = 0, align = 2, sort = 2)
	private Date date;		// 入伍日期
	@ExcelField(title = "入伍地", type = 0, align = 2, sort = 3)
	private String place;		// 入伍地
	@ExcelField(title = "批准退伍日期", type = 0, align = 2, sort = 4)
	private Date approveDate;		// 批准退伍日期
	@ExcelField(title = "退役时工作单位名称", type = 0, align = 2, sort = 5)
	private String tyUnitName;		// 退役时工作单位名称
	@ExcelField(title = "退役时工作单位所在政区", type = 0, align = 2, sort = 6)
	private String tyUnitSteer;		// 退役时工作单位所在政区
	@ExcelField(title = "退役时职务代码", type = 0, align = 2, sort = 7)
	private String tyCode;		// 退役时职务代码
	@ExcelField(title = "退役时职务名称", type = 0, align = 2, sort = 8)
	private String tyName;		// 退役时职务名称
	@ExcelField(title = "退役时军衔", type = 0, align = 2, sort = 9)
	private String tyRank;		// 退役时军衔
	@ExcelField(title = "退役时职务层次", type = 0, align = 2, sort = 10)
	private String tyJob;		// 退役时职务层次
	@ExcelField(title = "退役时职务层次起算日期", type = 0, align = 2, sort = 11)
	private Date tyJobDate;		// 退役时职务层次起算日期
	@ExcelField(title = "退役时技术职称", type = 0, align = 2, sort = 12)
	private String tyTitles;		// 退役时技术职称
	@ExcelField(title = "退役时工资类别档次", type = 0, align = 2, sort = 13, dictType="personnel_tygzlevel")
	private String tyMoneyLevel;		// 退役时工资类别档次
	@ExcelField(title = "退役时职务工资金额", type = 0, align = 2, sort = 14)
	private Double tyMoney;		// 退役时职务工资金额
	@ExcelField(title = "是否服预备役", type = 0, align = 2, sort = 15, dictType="yes_no")
	private String isreserve;		// 是否服预备役
	@ExcelField(title = "93年工改时职务", type = 0, align = 2, sort = 16)
	private String job93;		// 93年工改时职务
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelTcxyjr() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelTcxyjr(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getRetiredType() {
		return retiredType;
	}

	public void setRetiredType(String retiredType) {
		this.retiredType = retiredType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	public String getTyUnitName() {
		return tyUnitName;
	}

	public void setTyUnitName(String tyUnitName) {
		this.tyUnitName = tyUnitName;
	}
	
	public String getTyUnitSteer() {
		return tyUnitSteer;
	}

	public void setTyUnitSteer(String tyUnitSteer) {
		this.tyUnitSteer = tyUnitSteer;
	}
	
	public String getTyCode() {
		return tyCode;
	}

	public void setTyCode(String tyCode) {
		this.tyCode = tyCode;
	}
	
	public String getTyName() {
		return tyName;
	}

	public void setTyName(String tyName) {
		this.tyName = tyName;
	}
	
	public String getTyRank() {
		return tyRank;
	}

	public void setTyRank(String tyRank) {
		this.tyRank = tyRank;
	}
	
	public String getTyJob() {
		return tyJob;
	}

	public void setTyJob(String tyJob) {
		this.tyJob = tyJob;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTyJobDate() {
		return tyJobDate;
	}

	public void setTyJobDate(Date tyJobDate) {
		this.tyJobDate = tyJobDate;
	}
	
	public String getTyTitles() {
		return tyTitles;
	}

	public void setTyTitles(String tyTitles) {
		this.tyTitles = tyTitles;
	}
	
	public String getTyMoneyLevel() {
		return tyMoneyLevel;
	}

	public void setTyMoneyLevel(String tyMoneyLevel) {
		this.tyMoneyLevel = tyMoneyLevel;
	}
	
	public Double getTyMoney() {
		return tyMoney;
	}

	public void setTyMoney(Double tyMoney) {
		this.tyMoney = tyMoney;
	}
	
	public String getIsreserve() {
		return isreserve;
	}

	public void setIsreserve(String isreserve) {
		this.isreserve = isreserve;
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

	public String getJob93() {
		return job93;
	}

	public void setJob93(String job93) {
		this.job93 = job93;
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