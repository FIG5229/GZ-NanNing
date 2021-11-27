/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 典型人物Entity
 * @author daniel.liu
 * @version 2020-06-16
 */
public class AffairTypicalPerson extends DataEntity<AffairTypicalPerson> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 2, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "民族", type = 0, align = 2, sort = 3, dictType = "nation")
	private String nation;		// 民族
	@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 5, dictType = "political_status")
	private String politicsFace;		// 政治面貌
	@ExcelField(title = "单位", type = 0, align = 2, sort = 6)
	private String unit;		// 单位
	private String unitId;		// 单位Id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 7)
	private String job;		// 职务
	@ExcelField(title = "培树时间", type = 0, align = 2, sort = 8)
	private Date psTime;		// 培树时间
	@ExcelField(title = "培树级别", type = 0, align = 2, sort = 9, dictType = "affair_approval_unitLevel")
	private String psLevel;		// 培树级别
	@ExcelField(title = "曾获荣誉", type = 0, align = 2, sort = 10)
	private String wonHonor;		// 曾获荣誉
	@ExcelField(title = "培树目标", type = 0, align = 2, sort = 11)
	private String psTarget;		// 培树目标
//	@ExcelField(title = "培树方案", type = 0, align = 2, sort = 11)
	private String psProgramme;		// 培树方案
	@ExcelField(title = "培树部门", type = 0, align = 2, sort = 12)
	private String psDepartment;		// 培树部门
	private String psDepartmentId;		// 培树部门Id
	@ExcelField(title = "联系人", type = 0, align = 2, sort = 13)
	private String contactPerson;		// 联系人
	@ExcelField(title = "走访记录", type = 0, align = 2, sort = 14)
	private String visitRecord;		// 走访记录
//	@ExcelField(title = "事迹材料", type = 0, align = 2, sort = 15)
	private String materials;		// 事迹材料
	@ExcelField(title = "宣传情况", type = 0, align = 2, sort = 15, dictType = "publicity_type")
	private String publicity;		// 宣传情况（简报、专题片、新闻报道）
	@ExcelField(title = "推送机构", type = 0, align = 2, sort = 16)
	private String pushOrganization;		// 推送机构（疑似推荐机构）
	private String pushOrganizationId;		// 推送机构Id
	@ExcelField(title = "附件", type = 0, align = 2, sort = 17)
	private String filePath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginPsTime;		// 开始 培树时间
	private Date endPsTime;		// 结束 培树时间

//	新加字段  查找相关稿件宣传事迹
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;	//身份证号

	//走访记录改为字表
	private List<AffairTypicalPersonVisit> personNewsList;
//	改为附件
//	private List<AffairTypicalPersonNews> personNewsList;

	/*前端  导入子表时使用*/
	private String isImport;

	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;

	private String changeType; //判断是否有修改权限
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public List<AffairTypicalPersonVisit> getPersonNewsList() {
		return personNewsList;
	}

	public void setPersonNewsList(List<AffairTypicalPersonVisit> personNewsList) {
		this.personNewsList = personNewsList;
	}

	public AffairTypicalPerson() {
		super();
	}

	public AffairTypicalPerson(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getPoliticsFace() {
		return politicsFace;
	}

	public void setPoliticsFace(String politicsFace) {
		this.politicsFace = politicsFace;
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPsTime() {
		return psTime;
	}

	public void setPsTime(Date psTime) {
		this.psTime = psTime;
	}
	
	public String getPsLevel() {
		return psLevel;
	}

	public void setPsLevel(String psLevel) {
		this.psLevel = psLevel;
	}
	
	public String getWonHonor() {
		return wonHonor;
	}

	public void setWonHonor(String wonHonor) {
		this.wonHonor = wonHonor;
	}
	
	public String getPsTarget() {
		return psTarget;
	}

	public void setPsTarget(String psTarget) {
		this.psTarget = psTarget;
	}
	
	public String getPsProgramme() {
		return psProgramme;
	}

	public void setPsProgramme(String psProgramme) {
		this.psProgramme = psProgramme;
	}
	
	public String getPsDepartment() {
		return psDepartment;
	}

	public void setPsDepartment(String psDepartment) {
		this.psDepartment = psDepartment;
	}

	public String getPsDepartmentId() {
		return psDepartmentId;
	}

	public void setPsDepartmentId(String psDepartmentId) {
		this.psDepartmentId = psDepartmentId;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	public String getVisitRecord() {
		return visitRecord;
	}

	public void setVisitRecord(String visitRecord) {
		this.visitRecord = visitRecord;
	}
	

	
	public String getPublicity() {
		return publicity;
	}

	public void setPublicity(String publicity) {
		this.publicity = publicity;
	}
	
	public String getPushOrganization() {
		return pushOrganization;
	}

	public void setPushOrganization(String pushOrganization) {
		this.pushOrganization = pushOrganization;
	}
	
	public String getPushOrganizationId() {
		return pushOrganizationId;
	}

	public void setPushOrganizationId(String pushOrganizationId) {
		this.pushOrganizationId = pushOrganizationId;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
	public Date getBeginPsTime() {
		return beginPsTime;
	}

	public void setBeginPsTime(Date beginPsTime) {
		this.beginPsTime = beginPsTime;
	}
	
	public Date getEndPsTime() {
		return endPsTime;
	}

	public void setEndPsTime(Date endPsTime) {
		this.endPsTime = endPsTime;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIsImport() {
		return isImport;
	}

	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}