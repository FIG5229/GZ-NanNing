/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 推优入党Entity
 * @author cecil.li
 * @version 2019-11-06
 */
public class AffairPushParty extends DataEntity<AffairPushParty> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;     //身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 2, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "民族", type = 0, align = 2, sort = 3, dictType="nation")
	private String nationality;		// 民族
	@ExcelField(title = "文化程度", type = 0, align = 2, sort = 4, dictType="affair_xueli")
	private String eduLevel;		// 文化程度
	@ExcelField(title = "介绍人", type = 0, align = 2, sort = 5)
	private String introducer;		// 介绍人
	@ExcelField(title = "介绍人身份证", type = 0, align = 2, sort = 6)
	private String introducerNum;		// 介绍人身份证
	@ExcelField(title = "入团时间", type = 0, align = 2, sort = 7)
	private Date joinDate;		// 入团时间
	@ExcelField(title = "推荐时间", type = 0, align = 2, sort = 9)
	private Date recommendDate;		// 推荐时间
	@ExcelField(title = "个人情况", type = 0, align = 2, sort = 10)
	private String personalSituation;		// 个人情况
	@ExcelField(title = "团支部大会意见", type = 0, align = 2, sort = 11)
	private String tuanzhibuOpinion;		// 团支部大会意见
//	@ExcelField(title = "团委意见", type = 0, align = 2, sort = 11)
	private String tuanweiOpinion;		// 团委意见
//	@ExcelField(title = "团委意见", type = 0, align = 2, sort = 12)
	private String filePath;    //附件
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

	private String officeId;

	private Date startDate;
	private Date endDate;
	private Date startRecommendDate;
	private Date endRecommendDate;

	private String partyBranch;		// 所在团组织
	private String partyBranchId;		// 所在团组织id


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

	@ExcelField(title = "推优类型", type = 0, align = 2, sort = 8, dictType="recommend_type")
	private String recommendType;		//推优类型


	public String getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(String recommendType) {
		this.recommendType = recommendType;
	}

	public AffairPushParty() {
		super();
	}

	public AffairPushParty(String id){
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
	
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}
	
	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}
	
	public String getIntroducerNum() {
		return introducerNum;
	}

	public void setIntroducerNum(String introducerNum) {
		this.introducerNum = introducerNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getRecommendDate() {
		return recommendDate;
	}

	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}
	
	public String getPersonalSituation() {
		return personalSituation;
	}

	public void setPersonalSituation(String personalSituation) {
		this.personalSituation = personalSituation;
	}
	
	public String getTuanzhibuOpinion() {
		return tuanzhibuOpinion;
	}

	public void setTuanzhibuOpinion(String tuanzhibuOpinion) {
		this.tuanzhibuOpinion = tuanzhibuOpinion;
	}
	
	public String getTuanweiOpinion() {
		return tuanweiOpinion;
	}

	public void setTuanweiOpinion(String tuanweiOpinion) {
		this.tuanweiOpinion = tuanweiOpinion;
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
	public Date getStartRecommendDate() {
		return startRecommendDate;
	}

	public void setStartRecommendDate(Date startRecommendDate) {
		this.startRecommendDate = startRecommendDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndRecommendDate() {
		return endRecommendDate;
	}

	public void setEndRecommendDate(Date endRecommendDate) {
		this.endRecommendDate = endRecommendDate;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
}