/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 社会保险信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelInsurance extends DataEntity<PersonnelInsurance> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String personName;   //姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "保险种类", type = 0, align = 2, sort = 2, dictType="personnel_baoxian")
	private String type;		// 保险种类
	@ExcelField(title = "保险名称", type = 0, align = 2, sort = 3)
	private String name;		// 保险名称
	@ExcelField(title = "保险机构名称", type = 0, align = 2, sort = 4)
	private String orgName;		// 保险机构名称
	@ExcelField(title = "保险费", type = 0, align = 2, sort = 5)
	private Double cost;		// 保险费
	@ExcelField(title = "保险金额", type = 0, align = 2, sort = 6)
	private Integer money;		// 保险金额
	@ExcelField(title = "保险起始日期", type = 0, align = 2, sort = 7)
	private Date startDate;		// 保险起始日期
	@ExcelField(title = "保险终止日期", type = 0, align = 2, sort = 8)
	private Date endDate;		// 保险终止日期
	@ExcelField(title = "保险赔付日期", type = 0, align = 2, sort = 9)
	private Date payDate;		// 保险赔付日期
	@ExcelField(title = "保险赔付事由", type = 0, align = 2, sort = 10)
	private String payReason;		// 保险赔付事由
	@ExcelField(title = "保险终止事由", type = 0, align = 2, sort = 11)
	private String endReason;		// 保险终止事由
	@ExcelField(title = "保险备注", type = 0, align = 2, sort = 12)
	private String remark;		// 保险备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;
	private Date pfStartDate;
	private Date pfEndDate;
	
	public PersonnelInsurance() {
		super();
	}


	public PersonnelInsurance(String id){
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	public String getPayReason() {
		return payReason;
	}

	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}
	
	public String getEndReason() {
		return endReason;
	}

	public void setEndReason(String endReason) {
		this.endReason = endReason;
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

	public Date getPfStartDate() {
		return pfStartDate;
	}

	public void setPfStartDate(Date pfStartDate) {
		this.pfStartDate = pfStartDate;
	}

	public Date getPfEndDate() {
		return pfEndDate;
	}

	public void setPfEndDate(Date pfEndDate) {
		this.pfEndDate = pfEndDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}