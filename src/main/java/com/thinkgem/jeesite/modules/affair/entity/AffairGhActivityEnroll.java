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
 * 工会活动报名Entity
 * @author cecil.li
 * @version 2019-11-06
 */
public class AffairGhActivityEnroll extends DataEntity<AffairGhActivityEnroll> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date date;		// 时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String job;		// 职务
	@ExcelField(title = "性别", type = 0, align = 2, sort = 5, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 6)
	private Date birthday;		// 出生年月
	@ExcelField(title = "活动项目", type = 0, align = 2, sort = 7)
	private String project;		// 活动项目
	@ExcelField(title = "审核意见", type = 0, align = 2, sort = 8)
	private String opinion;		// 审核意见
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9)
	private String remark;		// 备注

	private String peopleNum = "0";		// 报名人数 默认0

	//前端页面
	private String isImport; //是否为导入
	private String isAdd; //是否是add页面

	private List<AffairGhActivityEnrollChildren> activityEnrollChildrenList;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String checkType;    //审核状态
	private String threeCheckMan;    //三级审核人
	private String twoCheckMan;        //二级审核人
	private String oneCheckMan;        //一级审核人
	private String submitMan;       //提交人
	private String threeCheckId;     //三级审核人id
	private String twoCheckId;       //二级审核人id
	private String oneCheckId;       //一级审核人id
	private String submitId;      //提交人id
	private String userId;
	private Date startDate;
	private Date endDate;
	private Date startBirthdayDate;
	private Date endBirthdayDate;

	
	public AffairGhActivityEnroll() {
		super();
	}

	public AffairGhActivityEnroll(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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
	public Date getStartBirthdayDate() {
		return startBirthdayDate;
	}

	public void setStartBirthdayDate(Date startBirthdayDate) {
		this.startBirthdayDate = startBirthdayDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndBirthdayDate() {
		return endBirthdayDate;
	}

	public void setEndBirthdayDate(Date endBirthdayDate) {
		this.endBirthdayDate = endBirthdayDate;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getThreeCheckMan() {
		return threeCheckMan;
	}

	public void setThreeCheckMan(String threeCheckMan) {
		this.threeCheckMan = threeCheckMan;
	}

	public String getTwoCheckMan() {
		return twoCheckMan;
	}

	public void setTwoCheckMan(String twoCheckMan) {
		this.twoCheckMan = twoCheckMan;
	}

	public String getOneCheckMan() {
		return oneCheckMan;
	}

	public void setOneCheckMan(String oneCheckMan) {
		this.oneCheckMan = oneCheckMan;
	}

	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}

	public String getThreeCheckId() {
		return threeCheckId;
	}

	public void setThreeCheckId(String threeCheckId) {
		this.threeCheckId = threeCheckId;
	}

	public String getTwoCheckId() {
		return twoCheckId;
	}

	public void setTwoCheckId(String twoCheckId) {
		this.twoCheckId = twoCheckId;
	}

	public String getOneCheckId() {
		return oneCheckId;
	}

	public void setOneCheckId(String oneCheckId) {
		this.oneCheckId = oneCheckId;
	}

	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<AffairGhActivityEnrollChildren> getActivityEnrollChildrenList() {
		return activityEnrollChildrenList;
	}

	public void setActivityEnrollChildrenList(List<AffairGhActivityEnrollChildren> activityEnrollChildrenList) {
		this.activityEnrollChildrenList = activityEnrollChildrenList;
	}

	public String getIsImport() {
		return isImport;
	}

	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}

	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}

	public String getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}
}