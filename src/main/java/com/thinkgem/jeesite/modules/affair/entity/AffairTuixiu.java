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
 * 退休Entity
 * @author cecil.li
 * @version 2020-07-02
 */
public class AffairTuixiu extends DataEntity<AffairTuixiu> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "证书编号", type = 0, align = 2, sort = 0)
	private String number;		// 证书编号
	@ExcelField(title = "发证日期", type = 0, align = 2, sort = 1)
	private Date date;		// 发证日期
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType = "sex")
	private String sex;  // 性别
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;		// 身份证号
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 5)
	private Date birth;		// 出生年月
	@ExcelField(title = "民族", type = 0, align = 2, sort = 6, dictType = "nation")
	private String nation;		// 民族
	@ExcelField(title = "籍贯", type = 0, align = 2, sort = 7)
	private String hometown;		// 籍贯
	@ExcelField(title = "参加工作日期", type = 0, align = 2, sort = 8)
	private Date workDate;		// 参加工作日期
	@ExcelField(title = "退休时单位", type = 0, align = 2, sort = 9)
	private String unitName;   // 退休时单位
	private String unitNameId;
	@ExcelField(title = "退休时间", type = 0, align = 2, sort = 10)
	private Date retirementTime;		// 退休时间
	@ExcelField(title = "退休时职务", type = 0, align = 2, sort = 11)
	private String retirementJob;		// 退休时职务
	@ExcelField(title = "批准退休单位", type = 0, align = 2, sort = 12)
	private String unit;		// 批准退休单位
	private String unitId;		// 批准退休单位id
	@ExcelField(title = "退休状态", type = 0, align = 2, sort = 13)
	private String status;  // 退休状态
	@ExcelField(title = "备注", type = 0, align = 2, sort = 14)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 发证日期
	private Date endDate;		// 结束 发证日期

	private String userId;         //获取当前账号单位
	private String tabFlag;		// 标识二级tab页
	private List<String> officeIds;		// 存放二级tag对应的机构id集合


	
	public AffairTuixiu() {
		super();
	}

	public AffairTuixiu(String id){
		super(id);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getRetirementTime() {
		return retirementTime;
	}

	public void setRetirementTime(Date retirementTime) {
		this.retirementTime = retirementTime;
	}
	
	public String getRetirementJob() {
		return retirementJob;
	}

	public void setRetirementJob(String retirementJob) {
		this.retirementJob = retirementJob;
	}
	
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitNameId() {
		return unitNameId;
	}

	public void setUnitNameId(String unitNameId) {
		this.unitNameId = unitNameId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTabFlag() {
		return tabFlag;
	}

	public void setTabFlag(String tabFlag) {
		this.tabFlag = tabFlag;
	}

	public List<String> getOfficeIds() {
		return officeIds;
	}

	public void setOfficeIds(List<String> officeIds) {
		this.officeIds = officeIds;
	}
}