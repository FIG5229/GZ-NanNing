/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 工资信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelSalary extends DataEntity<PersonnelSalary> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	private String personnelName;		// 姓名
	@ExcelField(title = "职务工资", type = 0, align = 2, sort = 1)
	private Double jobSalary;		// 职务工资
	@ExcelField(title = "级别工资", type = 0, align = 2, sort = 2)
	private Double levelSalary;		// 级别工资
	@ExcelField(title = "年终一次性奖金", type = 0, align = 2, sort = 3)
	private Double bonus;		// 年终一次性奖金
	@ExcelField(title = "变动日期", type = 0, align = 2, sort = 4)
	private Date changeDate;		// 变动日期
	@ExcelField(title = "警衔津贴", type = 0, align = 2, sort = 5)
	private Double allowance1;		// 警衔津贴
	@ExcelField(title = "工作性津贴", type = 0, align = 2, sort = 6)
	private Double allowance2;		// 工作性津贴
	@ExcelField(title = "生活性津贴", type = 0, align = 2, sort = 7)
	private Double allowance3;		// 生活性津贴
	@ExcelField(title = "改革性补贴", type = 0, align = 2, sort = 8)
	private Double allowance4;		// 改革性补贴
	@ExcelField(title = "执勤岗位津贴", type = 0, align = 2, sort = 9)
	private Double allowance5;		// 执勤岗位津贴
	@ExcelField(title = "法定工作日外加班补贴", type = 0, align = 2, sort = 10)
	private Double allowance6;		// 法定工作日外加班补贴
	@ExcelField(title = "艰苦边远地区津贴", type = 0, align = 2, sort = 11)
	private Double allowance7;		// 艰苦边远地区津贴
	@ExcelField(title = "国家统一规定的其他津贴", type = 0, align = 2, sort = 12)
	private Double allowance8;		// 国家统一规定的其他津贴
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 13)
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

	public PersonnelSalary() {
		super();
	}

	public PersonnelSalary(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public Double getJobSalary() {
		return jobSalary;
	}

	public void setJobSalary(Double jobSalary) {
		this.jobSalary = jobSalary;
	}
	
	public Double getLevelSalary() {
		return levelSalary;
	}

	public void setLevelSalary(Double levelSalary) {
		this.levelSalary = levelSalary;
	}
	
	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	
	public Double getAllowance1() {
		return allowance1;
	}

	public void setAllowance1(Double allowance1) {
		this.allowance1 = allowance1;
	}
	
	public Double getAllowance2() {
		return allowance2;
	}

	public void setAllowance2(Double allowance2) {
		this.allowance2 = allowance2;
	}
	
	public Double getAllowance3() {
		return allowance3;
	}

	public void setAllowance3(Double allowance3) {
		this.allowance3 = allowance3;
	}
	
	public Double getAllowance4() {
		return allowance4;
	}

	public void setAllowance4(Double allowance4) {
		this.allowance4 = allowance4;
	}
	
	public Double getAllowance5() {
		return allowance5;
	}

	public void setAllowance5(Double allowance5) {
		this.allowance5 = allowance5;
	}
	
	public Double getAllowance6() {
		return allowance6;
	}

	public void setAllowance6(Double allowance6) {
		this.allowance6 = allowance6;
	}
	
	public Double getAllowance7() {
		return allowance7;
	}

	public void setAllowance7(Double allowance7) {
		this.allowance7 = allowance7;
	}
	
	public Double getAllowance8() {
		return allowance8;
	}

	public void setAllowance8(Double allowance8) {
		this.allowance8 = allowance8;
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