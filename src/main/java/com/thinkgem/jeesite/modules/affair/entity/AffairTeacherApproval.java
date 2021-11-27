/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 教官晋级审批Entity
 * @author alan.wu
 * @version 2020-07-27
 */
public class AffairTeacherApproval extends DataEntity<AffairTeacherApproval> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "教官类别", type = 0, align = 2, sort = 2,dictType = "Instructor_category")
	private String instructorCategory;		// 教官类别
	@ExcelField(title = "教官级别", type = 0, align = 2, sort = 3,dictType = "instructor_level")
	private String instructorLevel;		// 教官级别
	@ExcelField(title = "申报级别", type = 0, align = 2, sort = 4,dictType = "instructor_level")
	private String applyLevel;		// 申报级别
	@ExcelField(title = "申报类别", type = 0, align = 2, sort = 5,dictType = "Instructor_category")
	private String applyCategory;		// 申报类别
	private String state;		// 审批状态
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String userId;
	private String officeId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public AffairTeacherApproval() {
		super();
	}

	public AffairTeacherApproval(String id){
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
	
	public String getInstructorCategory() {
		return instructorCategory;
	}

	public void setInstructorCategory(String instructorCategory) {
		this.instructorCategory = instructorCategory;
	}
	
	public String getInstructorLevel() {
		return instructorLevel;
	}

	public void setInstructorLevel(String instructorLevel) {
		this.instructorLevel = instructorLevel;
	}
	
	public String getApplyLevel() {
		return applyLevel;
	}

	public void setApplyLevel(String applyLevel) {
		this.applyLevel = applyLevel;
	}
	
	public String getApplyCategory() {
		return applyCategory;
	}

	public void setApplyCategory(String applyCategory) {
		this.applyCategory = applyCategory;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
}