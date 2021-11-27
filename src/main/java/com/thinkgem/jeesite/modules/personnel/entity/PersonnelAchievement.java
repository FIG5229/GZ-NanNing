/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 专业技术工作及成果信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelAchievement extends DataEntity<PersonnelAchievement> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "项目名称", type = 0, align = 2, sort = 0)
	private String projectName;		// 项目名称
	private String personnelName;		// 姓名
	@ExcelField(title = "项目编号", type = 0, align = 2, sort = 1)
	private String projectNo;		// 项目编号
	@ExcelField(title = "项目类别", type = 0, align = 2, sort = 2, dictType="personnel_xmtype")
	private String type;		// 项目类别
	@ExcelField(title = "项目领域", type = 0, align = 2, sort = 3)
	private String filed;		// 项目领域
	@ExcelField(title = "项目下达单位名称", type = 0, align = 2, sort = 4)
	private String unitName;		// 项目下达单位名称
	@ExcelField(title = "项目下达单位代码", type = 0, align = 2, sort = 5)
	private String unitCode;
	@ExcelField(title = "项目密级", type = 0, align = 2, sort = 6, dictType="personnel_xmmj")
	private String secretLevel;		// 项目密级
	@ExcelField(title = "项目起始日期", type = 0, align = 2, sort = 7)
	private Date startDate;		// 项目起始日期
	@ExcelField(title = "项目截止日期", type = 0, align = 2, sort = 8)
	private Date endDate;		// 项目截止日期
	@ExcelField(title = "参与该项目起始日期", type = 0, align = 2, sort = 9)
	private Date joinStartDate;		// 参与该项目起始日期
	@ExcelField(title = "参与该项目截止日期", type = 0, align = 2, sort = 10)
	private Date joinEndDate;		// 参与该项目截止日期
	@ExcelField(title = "在项目中担任角色", type = 0, align = 2, sort = 11)
	private String role;		// 在项目中担任角色
	@ExcelField(title = "在项目中担任角色的排序", type = 0, align = 2, sort = 12)
	private String roleSort;		// 在项目中担任角色的排序
	@ExcelField(title = "项目支持单位", type = 0, align = 2, sort = 13)
	private String supportUnit;		// 项目支持单位
	@ExcelField(title = "成果名称", type = 0, align = 2, sort = 14)
	private String achievementName;		// 成果名称
	@ExcelField(title = "成果编号", type = 0, align = 2, sort = 15)
	private String achievementNo;		// 成果编号
	@ExcelField(title = "成果类别", type = 0, align = 2, sort = 16, dictType="personnel_cgtype")
	private String achievementType;		// 成果类别
	@ExcelField(title = "成果水平", type = 0, align = 2, sort = 17, dictType="personnel_cgsp")
	private String achievementLevel;		// 成果水平
	@ExcelField(title = "成果鉴定单位名称", type = 0, align = 2, sort = 18)
	private String appraiseUnitName;		// 成果鉴定单位名称
	@ExcelField(title = "成果鉴定单位代码", type = 0, align = 2, sort = 19)
	private String appraiseUnitCode;		// 成果鉴定单位代码
	@ExcelField(title = "成果评述", type = 0, align = 2, sort = 20)
	private String comment;		// 成果评述
	@ExcelField(title = "成果创新性标识", type = 0, align = 2, sort = 21)
	private String identification;		// 成果创新性标识
	@ExcelField(title = "所需上报材料的图片资料", type = 0, align = 2, sort = 22)
	private String material;		// 所需上报材料的图片资料
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 23)
	private String idNumber;		// 身份证号

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;

	public String getUnitCode() {
		return unitCode;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public PersonnelAchievement() {
		super();
	}

	public PersonnelAchievement(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getSecretLevel() {
		return secretLevel;
	}

	public void setSecretLevel(String secretLevel) {
		this.secretLevel = secretLevel;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getJoinStartDate() {
		return joinStartDate;
	}

	public void setJoinStartDate(Date joinStartDate) {
		this.joinStartDate = joinStartDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getJoinEndDate() {
		return joinEndDate;
	}

	public void setJoinEndDate(Date joinEndDate) {
		this.joinEndDate = joinEndDate;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(String roleSort) {
		this.roleSort = roleSort;
	}
	
	public String getSupportUnit() {
		return supportUnit;
	}

	public void setSupportUnit(String supportUnit) {
		this.supportUnit = supportUnit;
	}
	
	public String getAchievementName() {
		return achievementName;
	}

	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}
	
	public String getAchievementNo() {
		return achievementNo;
	}

	public void setAchievementNo(String achievementNo) {
		this.achievementNo = achievementNo;
	}
	
	public String getAchievementType() {
		return achievementType;
	}

	public void setAchievementType(String achievementType) {
		this.achievementType = achievementType;
	}
	
	public String getAchievementLevel() {
		return achievementLevel;
	}

	public void setAchievementLevel(String achievementLevel) {
		this.achievementLevel = achievementLevel;
	}
	
	public String getAppraiseUnitName() {
		return appraiseUnitName;
	}

	public void setAppraiseUnitName(String appraiseUnitName) {
		this.appraiseUnitName = appraiseUnitName;
	}
	
	public String getAppraiseUnitCode() {
		return appraiseUnitCode;
	}

	public void setAppraiseUnitCode(String appraiseUnitCode) {
		this.appraiseUnitCode = appraiseUnitCode;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
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