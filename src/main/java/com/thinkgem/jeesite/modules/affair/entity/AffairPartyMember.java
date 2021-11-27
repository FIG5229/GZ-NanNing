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
 * 党员花名册Entity
 * @author eav.liu
 * @version 2019-11-05
 */
public class AffairPartyMember extends DataEntity<AffairPartyMember> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号码", type = 0, align = 2, sort = 1)
	private String cardNum;		// 身份证号码
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "所在党支部", type = 0, align = 2, sort = 10)
	private String partyBranch;		// 所在党支部

	private String partyBranchId;		// 所在党支部id
	@ExcelField(title = "人员类别", type = 0, align = 2, sort = 9, dictType = "affair_personnel_category")
	private String personnelCategory;		// 人员类别
	private String personnelCategory2;		// 人员类别
	private String personnelType;		// 人员类别
	@ExcelField(title = "民族", type = 0, align = 2, sort = 4, dictType = "nation")
	private String nation;		// 民族
	@ExcelField(title = "籍贯", type = 0, align = 2, sort = 5)
	private String birthplace;		// 籍贯
	@ExcelField(title = "是否台湾省籍", type = 0, align = 2, sort = 6, dictType = "yes_no")
	private String isTaiwan;		// 是否台湾省籍
	@ExcelField(title = "出生日期", type = 0, align = 2, sort = 7)
	private Date birth;		// 出生日期
	@ExcelField(title = "学历", type = 0, align = 2, sort = 8, dictType = "affair_party_member_xueli")
	private String education;		// 学历
	@ExcelField(title = "加入党组织日期", type = 0, align = 2, sort = 11)
	private Date joinDate;		// 加入党组织日期
	@ExcelField(title = "转为正式党员日期", type = 0, align = 2, sort = 12)
	private Date turnDate;		// 转为正式党员日期
	@ExcelField(title = "工作岗位", type = 0, align = 2, sort = 13)
	private String workPlace;		// 工作岗位
	@ExcelField(title = "参加工作时间", type = 0, align = 2, sort = 14)
	private Date workDate;		// 参加工作时间
	@ExcelField(title = "联系电话", type = 0, align = 2, sort = 16)
	private String phoneNum;		// 联系电话
	@ExcelField(title = "固定电话", type = 0, align = 2, sort = 17)
	private String telephone;		// 固定电话
	@ExcelField(title = "家庭住址", type = 0, align = 2, sort = 15)
	private String homeAddress;		// 家庭住址
	@ExcelField(title = "婚姻状况", type = 0, align = 2, sort = 18, dictType = "affair_marital_status")
	private String maritalStatus;		// 婚姻状况
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "减少原因", type = 0, align = 2, sort = 19, dictType = "delete_reason")
	private String deleteReason;      //减少原因
	@ExcelField(title = "排序", type = 0, align = 2, sort = 20)
	private String sort;
	@ExcelField(title = "警号", type = 0, align = 2, sort = 2)
	private String policeNo;		// 警号

	private Date joinStartDate;

	private Date joinEndDate;

	private Date turnStartDate;

	private Date turnEndDate;
	//是否是详情页面
	private String flag = "2";

	private String pbId;	// 党组织id

	private List<String> pbIds;

	private String dateType;

	private Integer year;

	private String dateStart;

	private String dateEnd;

	private String month;

	private Date startDate;

	private Date endDate;

	private	String treeId;

	private String status2;
	private String person;



	public AffairPartyMember() {
		super();
	}

	public AffairPartyMember(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPartyBranch() {
		return partyBranch;
	}

	public void setPartyBranch(String partyBranch) {
		this.partyBranch = partyBranch;
	}
	
	public String getPartyBranchId() {
		return partyBranchId;
	}

	public void setPartyBranchId(String partyBranchId) {
		this.partyBranchId = partyBranchId;
	}
	
	public String getPersonnelCategory() {
		return personnelCategory;
	}

	public void setPersonnelCategory(String personnelCategory) {
		this.personnelCategory = personnelCategory;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	
	public String getIsTaiwan() {
		return isTaiwan;
	}

	public void setIsTaiwan(String isTaiwan) {
		this.isTaiwan = isTaiwan;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTurnDate() {
		return turnDate;
	}

	public void setTurnDate(Date turnDate) {
		this.turnDate = turnDate;
	}
	
	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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

	public Date getJoinStartDate() {
		return joinStartDate;
	}

	public void setJoinStartDate(Date joinStartDate) {
		this.joinStartDate = joinStartDate;
	}

	public Date getJoinEndDate() {
		return joinEndDate;
	}

	public void setJoinEndDate(Date joinEndDate) {
		this.joinEndDate = joinEndDate;
	}

	public Date getTurnStartDate() {
		return turnStartDate;
	}

	public void setTurnStartDate(Date turnStartDate) {
		this.turnStartDate = turnStartDate;
	}

	public Date getTurnEndDate() {
		return turnEndDate;
	}

	public void setTurnEndDate(Date turnEndDate) {
		this.turnEndDate = turnEndDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getPbId() {
		return pbId;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public List<String> getPbIds() {
		return pbIds;
	}

	public void setPbIds(List<String> pbIds) {
		this.pbIds = pbIds;
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

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getPersonnelCategory2() {
		return personnelCategory2;
	}

	public void setPersonnelCategory2(String personnelCategory2) {
		this.personnelCategory2 = personnelCategory2;
	}

	public String getPersonnelType() {
		return personnelType;
	}

	public void setPersonnelType(String personnelType) {
		this.personnelType = personnelType;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}