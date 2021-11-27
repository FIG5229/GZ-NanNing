/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 铁路公安机关课程建设情况统计Entity
 * @author kevin.jia
 * @version 2020-08-13
 */
public class AffairCourseBuildStatistical extends DataEntity<AffairCourseBuildStatistical> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位名称" , type = 0 , align = 1 , sort = 1)
	private String unitName;		// 单位名称
	private String unitId;		// 单位id
	@ExcelField(title = "面授课程" , type = 0 , align = 1, sort = 2)
	private String msCourse;		// 面授课程
	@ExcelField(title = "在线课程" , type = 0 , align = 1, sort = 3)
	private String zxCourse;		// 在线课程
	@ExcelField(title = "合计" , type = 0 , align = 1, sort = 4)
	private String total;		// 合计
	@ExcelField(title = "备注" , type = 0 , align = 1, sort = 4)
	private String remarks;    //备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairCourseBuildStatistical() {
		super();
	}

	public AffairCourseBuildStatistical(String id){
		super(id);
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getMsCourse() {
		return msCourse;
	}

	public void setMsCourse(String msCourse) {
		this.msCourse = msCourse;
	}
	
	public String getZxCourse() {
		return zxCourse;
	}

	public void setZxCourse(String zxCourse) {
		this.zxCourse = zxCourse;
	}
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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