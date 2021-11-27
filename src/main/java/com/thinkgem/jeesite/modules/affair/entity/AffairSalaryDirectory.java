/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 工资目录Entity
 * @author mason.xv
 * @version 2019-11-27
 */
public class AffairSalaryDirectory extends DataEntity<AffairSalaryDirectory> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "工作部门及职务", type = 0, align = 2, sort = 1)
	private String department;		// 工作部门及职务
	@ExcelField(title = "执行的职务工资标准", type = 0, align = 2, sort = 2)
	private String gzStandard;		// 执行的职务工资标准
	@ExcelField(title = "职务工资额", type = 0, align = 2, sort = 3)
	private Double zwSalary;		// 职务工资额
	@ExcelField(title = "批准时间", type = 0, align = 2, sort = 4)
	private Date approveTime;		// 批准时间
	@ExcelField(title = "批准机关及文号", type = 0, align = 2, sort = 5)
	private String approveAuthority;		// 批准机关及文号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 6)
	private String idNumber;		// 身份证号


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginApproveTime;		// 开始 批准时间
	private Date endApproveTime;		// 结束 批准时间

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public AffairSalaryDirectory() {
		super();
	}

	public AffairSalaryDirectory(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getGzStandard() {
		return gzStandard;
	}

	public void setGzStandard(String gzStandard) {
		this.gzStandard = gzStandard;
	}
	
	public Double getZwSalary() {
		return zwSalary;
	}

	public void setZwSalary(Double zwSalary) {
		this.zwSalary = zwSalary;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd ")
	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	
	public String getApproveAuthority() {
		return approveAuthority;
	}

	public void setApproveAuthority(String approveAuthority) {
		this.approveAuthority = approveAuthority;
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
	
	public Date getBeginApproveTime() {
		return beginApproveTime;
	}

	public void setBeginApproveTime(Date beginApproveTime) {
		this.beginApproveTime = beginApproveTime;
	}
	
	public Date getEndApproveTime() {
		return endApproveTime;
	}

	public void setEndApproveTime(Date endApproveTime) {
		this.endApproveTime = endApproveTime;
	}
		
}