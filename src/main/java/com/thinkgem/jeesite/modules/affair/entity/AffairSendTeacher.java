/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 送教上门Entity
 * @author jack.xu
 * @version 2020-07-20
 */
public class AffairSendTeacher extends DataEntity<AffairSendTeacher> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "用户名", type = 0, align = 2, sort = 0)
	private String userName;		// 用户名
	@ExcelField(title = "警号", type = 0, align = 2, sort = 1)
	private String number;		// 警号
	@ExcelField(title = "受训民警", type = 0, align = 2, sort = 2)
	private String name;		// 受训民警
	private String nameId;		// 受训民警id
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "受教单位", type = 0, align = 2, sort = 4)
	private String sendUnit;		// 受教单位
	//@ExcelField(title = "警种", type = 0, align = 2,sort = 5,dictType = "police_classification")
	private String policeClassification;		// 警种
	/*@ExcelField(title = "警衔", type = 0, align = 2,sort = 6,dictType = "police_rank")*/
	@ExcelField(title = "警衔", type = 0, align = 2,sort = 5,dictType = "police_rank_level")
	private String policeRank;		// 警衔
	@ExcelField(title = "人员类别", type = 0, align = 2,sort = 6,dictType = "person_type")
	private String personType;		// 人员类别
	@ExcelField(title = "管理类别", type = 0, align = 2,sort = 7,dictType = "management_type")
	private String managementType;		// 管理类别
	@ExcelField(title = "行政职务", type = 0, align = 2,sort = 8,dictType = "administration_post")
	private String post;		// 行政职务
	@ExcelField(title = "职务级别", type = 0, align = 2,sort = 9,dictType = "post_level")
	private String postLevel;		// 职务级别
	@ExcelField(title = "主办单位", type = 0, align = 2, sort = 10)
	private String unit;		// 主办单位
	private String unitId;		// 主办单位id
	@ExcelField(title = "域", type = 0, align = 2, sort = 11)
	private String region;		// 域
	private String regionId;		// 域id
	@ExcelField(title = "培训开始时间", type = 0, align = 2, sort = 12)
	private Date beganDate;		// 开始时间
	@ExcelField(title = "培训结束时间", type = 0, align = 2, sort = 13)
	private Date resultDate;		// 结束时间
	@ExcelField(title = "送教时长(天)", type = 0, align = 2, sort = 14)
	private String sendDay;		// 送教时长(天)
	/*@ExcelField(title = "送教市场", type = 0, align = 2, sort = 15)
	private String sendMarket;		// 送教市场*/
	@ExcelField(title = "送教场次", type = 0, align = 2, sort = 15)
	private String sendPeriod;		// 送教场次
	@ExcelField(title = "受训人数", type = 0, align = 2, sort = 16)
	private String count;		// 受训人数
	@ExcelField(title = "送教内容", type = 0, align = 2, sort = 17)
	private String sendContent;		// 送教内容
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String sendUnitId;		//受教单位id
	
	public AffairSendTeacher() {
		super();
	}

	public AffairSendTeacher(String id){
		super(id);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPoliceClassification() {
		return policeClassification;
	}

	public void setPoliceClassification(String policeClassification) {
		this.policeClassification = policeClassification;
	}
	
	public String getPoliceRank() {
		return policeRank;
	}

	public void setPoliceRank(String policeRank) {
		this.policeRank = policeRank;
	}
	
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}
	
	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}
	
	public String getManagementType() {
		return managementType;
	}

	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}
	
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
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
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeganDate() {
		return beganDate;
	}

	public void setBeganDate(Date beganDate) {
		this.beganDate = beganDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	
	public String getSendDay() {
		return sendDay;
	}

	public void setSendDay(String sendDay) {
		this.sendDay = sendDay;
	}
	
	/*public String getSendMarket() {
		return sendMarket;
	}

	public void setSendMarket(String sendMarket) {
		this.sendMarket = sendMarket;
	}*/
	
	public String getSendPeriod() {
		return sendPeriod;
	}

	public void setSendPeriod(String sendPeriod) {
		this.sendPeriod = sendPeriod;
	}
	
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	public String getSendUnit() {
		return sendUnit;
	}

	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}
	
	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
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

	public String getSendUnitId() {
		return sendUnitId;
	}

	public void setSendUnitId(String sendUnitId) {
		this.sendUnitId = sendUnitId;
	}
}