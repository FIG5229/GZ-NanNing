/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 参与重要活动信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelImportantActivity extends DataEntity<PersonnelImportantActivity> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "参与重要活动起始日期", type = 0, align = 2, sort = 1)
	private Date startDate;		// 参与重要活动起始日期
	@ExcelField(title = "参与重要活动截止日期", type = 0, align = 2, sort = 2)
	private Date endDate;		// 参与重要活动截止日期
	@ExcelField(title = "参与重要活动类别", type = 0, align = 2, sort = 3, dictType="personnel_cyzyhdlb")
	private String type;		// 参与重要活动类别
	@ExcelField(title = "参与重要活动名称", type = 0, align = 2, sort = 4)
	private String name;		// 参与重要活动名称
	@ExcelField(title = "参与时所在部门及警种", type = 0, align = 2, sort = 5)
	private String depPolice;		// 参与时所在部门及警种
	@ExcelField(title = "参与重要警务活动职责分工", type = 0, align = 2, sort = 6)
	private String divisionLabour;		// 参与重要警务活动职责分工
	@ExcelField(title = "备注", type = 0, align = 2, sort = 7)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelImportantActivity() {
		super();
	}

	public PersonnelImportantActivity(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	
	public String getDepPolice() {
		return depPolice;
	}

	public void setDepPolice(String depPolice) {
		this.depPolice = depPolice;
	}
	
	public String getDivisionLabour() {
		return divisionLabour;
	}

	public void setDivisionLabour(String divisionLabour) {
		this.divisionLabour = divisionLabour;
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
}