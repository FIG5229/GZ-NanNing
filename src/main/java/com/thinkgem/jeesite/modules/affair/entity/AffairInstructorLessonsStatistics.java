/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 教官授课统计Entity
 * @author kevin.jia
 * @version 2020-08-11
 */
public class AffairInstructorLessonsStatistics extends DataEntity<AffairInstructorLessonsStatistics> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0 , align = 1 , sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "教官姓名", type = 0, align = 1,sort = 2)
	private String name;		// 教官姓名
	@ExcelField(title = "教官类型", type = 0, align = 1,sort = 3)
	private String instructorType;		// 教官类型
	private String unitId;		// 所属机构id
	@ExcelField(title = "所属机构", type = 0, align = 1,sort = 4)
	private String unitName;		// 所属机构
	@ExcelField(title = "授课总数", type = 0, align = 1,sort = 5)
	private String giveLessonsTotal;		// 授课总数
	@ExcelField(title = "课时总数", type = 0, align = 1,sort = 6)
	private String classHourTotal;		// 课时总数
	@ExcelField(title = "授课总时长(小时)", type = 0, align = 1,sort = 5)
	private String giveLessonsTotalHour;		// 授课总时长(小时)
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairInstructorLessonsStatistics() {
		super();
	}

	public AffairInstructorLessonsStatistics(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getInstructorType() {
		return instructorType;
	}

	public void setInstructorType(String instructorType) {
		this.instructorType = instructorType;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getGiveLessonsTotal() {
		return giveLessonsTotal;
	}

	public void setGiveLessonsTotal(String giveLessonsTotal) {
		this.giveLessonsTotal = giveLessonsTotal;
	}
	
	public String getClassHourTotal() {
		return classHourTotal;
	}

	public void setClassHourTotal(String classHourTotal) {
		this.classHourTotal = classHourTotal;
	}
	
	public String getGiveLessonsTotalHour() {
		return giveLessonsTotalHour;
	}

	public void setGiveLessonsTotalHour(String giveLessonsTotalHour) {
		this.giveLessonsTotalHour = giveLessonsTotalHour;
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
}