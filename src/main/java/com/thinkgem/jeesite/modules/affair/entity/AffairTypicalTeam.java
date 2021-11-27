/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 典型集体Entity
 * @author daniel.liu
 * @version 2020-06-16
 */
public class AffairTypicalTeam extends DataEntity<AffairTypicalTeam> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位Id
	@ExcelField(title = "培树时间", type = 0, align = 2, sort = 2)
	private Date time;		// 培树时间
	@ExcelField(title = "培树级别", type = 0, align = 2, sort = 3, dictType = "affair_approval_unitLevel")
	private String level;		// 培树级别
	@ExcelField(title = "曾获荣誉", type = 0, align = 2, sort = 4)
	private String wonHonor;		// 曾获荣誉
	@ExcelField(title = "培树目标", type = 0, align = 2, sort = 5)
	private String target;		// 培树目标
	@ExcelField(title = "培树方案", type = 0, align = 2, sort = 11)
	private String programme;		// 培树方案
	@ExcelField(title = "培树部门", type = 0, align = 2, sort = 6)
	private String psDepartment;		// 培树部门
	private String psDepartmentId;		// 培树部门Id
	@ExcelField(title = "联系人", type = 0, align = 2, sort = 7)
	private String contactPerson;		// 联系人
	@ExcelField(title = "走访记录", type = 0, align = 2, sort = 8)
	private String visitRecord;		// 走访记录
	@ExcelField(title = "事迹材料", type = 0, align = 2, sort = 12)
	private String materials;		// 事迹材料
	@ExcelField(title = "宣传情况", type = 0, align = 2, sort = 9, dictType = "publicity_type")
	private String publicity;		// 宣传情况（简报、专题片、新闻报道）
	@ExcelField(title = "推送机构", type = 0, align = 2, sort = 10)
	private String pushOrganization;		// 推送机构（疑似推荐机构）
	private String pushOrganizationId;		// 推送机构Id
	private String filePath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginTime;		// 开始 培树时间
	private Date endTime;		// 结束 培树时间

	//子表
	private List<AffairTypicalTeamVisit> typicalTeamChildList;
//	private List<AffairTypicalTeamChild> typicalTeamChildList;

	/*前端  是否导入子表*/
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


	public List<AffairTypicalTeamVisit> getTypicalTeamChildList() {
		return typicalTeamChildList;
	}

	public void setTypicalTeamChildList(List<AffairTypicalTeamVisit> typicalTeamChildList) {
		this.typicalTeamChildList = typicalTeamChildList;
	}

	public AffairTypicalTeam() {
		super();
	}

	public AffairTypicalTeam(String id){
		super(id);
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getWonHonor() {
		return wonHonor;
	}

	public void setWonHonor(String wonHonor) {
		this.wonHonor = wonHonor;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
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
	
//	public String getStoryMaterials() {
//		return storyMaterials;
//	}

//	public void setStoryMaterials(String storyMaterials) {
//		this.storyMaterials = storyMaterials;
//	}
	
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
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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