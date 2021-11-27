/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 工资发放信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelSalaryPay extends DataEntity<PersonnelSalaryPay> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	private String personnelName;		// 姓名
	@ExcelField(title = "应发工资", type = 0, align = 2, sort = 1)
	private Double salary1;		// 应发工资
	@ExcelField(title = "实发工资", type = 0, align = 2, sort = 2)
	private Double salary2;		// 实发工资
	@ExcelField(title = "发放日期", type = 0, align = 2, sort = 3)
	private Date payDate;		// 发放日期
	@ExcelField(title = "计算日期", type = 0, align = 2, sort = 4)
	private Date calculateDate;		// 计算日期
	@ExcelField(title = "个人所得税", type = 0, align = 2, sort = 5)
	private Double tax;		// 个人所得税
	@ExcelField(title = "应扣工资", type = 0, align = 2, sort = 6)
	private Double salary3;		// 应扣工资
	@ExcelField(title = "补发工资", type = 0, align = 2, sort = 7)
	private Double salary4;		// 补发工资
	@ExcelField(title = "年终一次性奖金", type = 0, align = 2, sort = 8)
	private Double bonus;		// 年终一次性奖金
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 9)
	private String idNumber;		// 身份证号

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	private Date startDate;
	private Date endDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelSalaryPay() {
		super();
	}

	public PersonnelSalaryPay(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public Double getSalary1() {
		return salary1;
	}

	public void setSalary1(Double salary1) {
		this.salary1 = salary1;
	}
	
	public Double getSalary2() {
		return salary2;
	}

	public void setSalary2(Double salary2) {
		this.salary2 = salary2;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCalculateDate() {
		return calculateDate;
	}

	public void setCalculateDate(Date calculateDate) {
		this.calculateDate = calculateDate;
	}
	
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}
	
	public Double getSalary3() {
		return salary3;
	}

	public void setSalary3(Double salary3) {
		this.salary3 = salary3;
	}
	
	public Double getSalary4() {
		return salary4;
	}

	public void setSalary4(Double salary4) {
		this.salary4 = salary4;
	}
	
	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
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