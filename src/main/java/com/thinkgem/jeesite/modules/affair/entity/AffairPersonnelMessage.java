/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 查看报名详细Entity
 * @author tom.fu
 * @version 2020-08-12
 */
public class AffairPersonnelMessage extends DataEntity<AffairPersonnelMessage> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "用户姓名", type = 0, align = 2, sort = 1)
	private String username;		// 用户姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3,dictType = "sex")
	private String gender;		// 性别
	private String nation;		// 民族
	private Date birthDate;		// 出生日期
	private String politicsStatus;		// 政治面貌
	private String educationBackground;		// 学历
	private String degree;		// 学位
	@ExcelField(title = "机构", type = 0, align = 2, sort = 4)
	private String organization;		// 机构
	@ExcelField(title = "职务", type = 0, align = 2, sort = 5)
	private String post;		// 职务
	private Date workTime;		// 参加工作时间
	private String phone;		// 手机号
	private Date arrivalTime;		// 到达时间
	private String arrivalTrainNumber;		// 到达车次/航班
	private String arrivalStationAirport;		// 到达车站/机场
	private String officialAccounts;		// 公免号
	private Date returnTime;		// 返程时间
	private String returnTrainNumber;		// 返程车次/航班
	private String returnStationAirport;		// 返程车站/机场
	private String isReserveReturnTicket;		// 是否预定返程车票/机票
	private String createOrgId;		// 创建机构id
	private String createIdNo;		// 创建者身份证
	private String updateOrgId;		// 更新机构id
	private String updateIdNo;		// 更新者身份证号
	private String classManageId;		//培训班id

	public String getClassManageId() {
		return classManageId;
	}

	public void setClassManageId(String classManageId) {
		this.classManageId = classManageId;
	}

	public AffairPersonnelMessage() {
		super();
	}

	public AffairPersonnelMessage(String id){
		super(id);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}
	
	public String getEducationBackground() {
		return educationBackground;
	}

	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}
	
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public String getArrivalTrainNumber() {
		return arrivalTrainNumber;
	}

	public void setArrivalTrainNumber(String arrivalTrainNumber) {
		this.arrivalTrainNumber = arrivalTrainNumber;
	}
	
	public String getArrivalStationAirport() {
		return arrivalStationAirport;
	}

	public void setArrivalStationAirport(String arrivalStationAirport) {
		this.arrivalStationAirport = arrivalStationAirport;
	}
	
	public String getOfficialAccounts() {
		return officialAccounts;
	}

	public void setOfficialAccounts(String officialAccounts) {
		this.officialAccounts = officialAccounts;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	public String getReturnTrainNumber() {
		return returnTrainNumber;
	}

	public void setReturnTrainNumber(String returnTrainNumber) {
		this.returnTrainNumber = returnTrainNumber;
	}
	
	public String getReturnStationAirport() {
		return returnStationAirport;
	}

	public void setReturnStationAirport(String returnStationAirport) {
		this.returnStationAirport = returnStationAirport;
	}
	
	public String getIsReserveReturnTicket() {
		return isReserveReturnTicket;
	}

	public void setIsReserveReturnTicket(String isReserveReturnTicket) {
		this.isReserveReturnTicket = isReserveReturnTicket;
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