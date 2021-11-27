/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 绩效组织树表Entity
 * @author daniel.liu
 * @version 2020-11-04
 */
public class ExamOffice extends DataEntity<ExamOffice> {
	
	private static final long serialVersionUID = 1L;
	private String parentId;		// parent_id
	private String parentIds;		// parent_ids
	private String name;		// name
	private String code;		// code
	private String type;		// 1:局机关	2：处机关 3：所队 2：处机关 5：处领导班子 6：中基层领导 7：民警
	private String grade;		// grade
	private String useable;		// useable
	private String fullName;		// full_name
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	
	public ExamOffice() {
		super();
	}

	public ExamOffice(String id){
		super(id);
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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