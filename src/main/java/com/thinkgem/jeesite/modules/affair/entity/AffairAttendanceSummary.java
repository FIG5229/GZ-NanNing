/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 考勤关联计算Entity
 * @author cecil.li
 * @version 2020-06-02
 */
public class AffairAttendanceSummary extends DataEntity<AffairAttendanceSummary> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "月份", type = 0, align = 2, sort = 1)
	private Integer month;		// 月份
	@ExcelField(title = "年份", type = 0, align = 2, sort = 0)
	private Integer year;		// 年份
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	private String idNumber;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 3)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "值勤岗位类别", type = 0, align = 2, sort = 4, dictType = "attendence_police_types")
	private String zqType;		// 值勤岗位类别
	@ExcelField(title = "值勤天数", type = 0, align = 2, sort = 5)
	private String zqDays;		// 值勤天数
	@ExcelField(title = "值勤津贴金额", type = 0, align = 2, sort = 6)
	private String zqMoney;		// 值勤津贴金额
	@ExcelField(title = "加班天数", type = 0, align = 2, sort = 10)
	private String jbDays;		// 加班天数
	@ExcelField(title = "加法天数", type = 0, align = 2, sort = 11)
	private String jfDays;		// 加法天数
	@ExcelField(title = "加班补贴金额", type = 0, align = 2, sort = 12)
	private String jbMoney;		// 加班补贴金额
	@ExcelField(title = "线路岗位类别", type = 0, align = 2, sort = 7, dictType = "attendence_police_types")
	private String xlType;		// 线路岗位类别
	@ExcelField(title = "缺勤天数", type = 0, align = 2, sort = 8)
	private String qqDays;		// 缺勤天数
	@ExcelField(title = "线路津贴", type = 0, align = 2, sort = 9)
	private String xlMoney;		// 线路津贴
	@ExcelField(title = "汇总", type = 0, align = 2, sort = 13)
	private String summary;		// 汇总
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String userId;
	
	public AffairAttendanceSummary() {
		super();
	}

	public AffairAttendanceSummary(String id){
		super(id);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getZqType() {
		return zqType;
	}

	public void setZqType(String zqType) {
		this.zqType = zqType;
	}
	
	public String getZqDays() {
		return zqDays;
	}

	public void setZqDays(String zqDays) {
		this.zqDays = zqDays;
	}
	
	public String getZqMoney() {
		return zqMoney;
	}

	public void setZqMoney(String zqMoney) {
		this.zqMoney = zqMoney;
	}
	
	public String getJbDays() {
		return jbDays;
	}

	public void setJbDays(String jbDays) {
		this.jbDays = jbDays;
	}
	
	public String getJfDays() {
		return jfDays;
	}

	public void setJfDays(String jfDays) {
		this.jfDays = jfDays;
	}
	
	public String getJbMoney() {
		return jbMoney;
	}

	public void setJbMoney(String jbMoney) {
		this.jbMoney = jbMoney;
	}
	
	public String getXlType() {
		return xlType;
	}

	public void setXlType(String xlType) {
		this.xlType = xlType;
	}
	
	public String getQqDays() {
		return qqDays;
	}

	public void setQqDays(String qqDays) {
		this.qqDays = qqDays;
	}
	
	public String getXlMoney() {
		return xlMoney;
	}

	public void setXlMoney(String xlMoney) {
		this.xlMoney = xlMoney;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
}