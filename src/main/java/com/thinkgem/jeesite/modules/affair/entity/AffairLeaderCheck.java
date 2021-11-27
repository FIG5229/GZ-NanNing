/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 领导干部年度考核表Entity
 * @author mason.xv
 * @version 2019-11-05
 */
public class AffairLeaderCheck extends DataEntity<AffairLeaderCheck> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "领领导干部名称", type = 0, align = 2, sort = 0)
	private String name;		// 领领导干部名称
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位机构id
	@ExcelField(title = "诉职年度", type = 0, align = 2, sort = 2)
	private String date;		// 诉职年度
	@ExcelField(title = "参加测评人数", type = 0, align = 2, sort = 3)
	private Integer num;		// 参加测评人数
	@ExcelField(title = "测评等次", type = 0, align = 2, sort = 4, dictType="assessment_level")
	private String grade;		// 测评等次
	@ExcelField(title = "相关材料", type = 0, align = 2, sort = 5)
	private String materialPath;		// 相关材料
	@ExcelField(title = "审核状态", type = 0, align = 2, sort = 6, dictType="current_state")
	private String status;		// 审核状态
    @ExcelField(title = "评测时间", type = 0, align = 2, sort = 7)
    private Date gradeDate;        //评测时间
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	private String hasAuth;       //审核权限
	private String opinion;         //审核原因
	private String shPerson;         //审核人


	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getGradeDate() {
		return gradeDate;
	}

	public void setGradeDate(Date gradeDate) {
		this.gradeDate = gradeDate;
	}


	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public String getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(String hasAuth) {
		this.hasAuth = hasAuth;
	}

	public AffairLeaderCheck() {
		super();
	}

	public AffairLeaderCheck(String id){
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getMaterialPath() {
		return materialPath;
	}

	public void setMaterialPath(String materialPath) {
		this.materialPath = materialPath;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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