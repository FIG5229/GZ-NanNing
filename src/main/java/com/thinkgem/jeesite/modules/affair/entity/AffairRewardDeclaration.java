/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 个人奖励申报Entity
 * @author cecil.li
 * @version 2019-12-21
 */
public class AffairRewardDeclaration extends DataEntity<AffairRewardDeclaration> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "申报时间", type = 0, align = 2, sort = 0)
	private Date date;		// 申报时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "申报单位", type = 0, align = 2, sort = 7)
	private String unit;		// 申报单位
	private String unitId;		// 申报单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 6)
	private String job;        //职务
	private String tsStatus;       //推送状态
	@ExcelField(title = "荣誉级别", type = 0, align = 2, sort = 10, dictType = "affair_approval_unitLevel")
	private String type;        //奖励级别
	@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 5, dictType = "political_status")
	private String mianmao;        //政治面貌
	@ExcelField(title = "民族", type = 0, align = 2, sort = 4, dictType = "nation")
	private String nation;           //民族
	@ExcelField(title = "审核单位", type = 0, align = 2, sort = 8)
	private String approvalUnit;		// 审核通过单位
	private String approvalUnitId;		// 审核通过单位id
	@ExcelField(title = "奖励名称", type = 0, align = 2, sort = 9)
	private String rewardName;		// 奖项名称
	private String fileNo;		// 奖项文号
	private String commendationUnit;		// 表彰单位
	@ExcelField(title = "简要事迹", type = 0, align = 2, sort = 11)
	private String remark;		// 简要事迹
	private String filePath;		// 附件
	private String opinion;		// 审核意见
	private String status;		// 审核状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 获奖时间
	private Date endDate;		// 结束 获奖时间
	private String shPerson;
	private String juCheckMan;
	private String chuCheckMan;
	private String submitMan;
	private String sbType;
	private String juCheckId;
	private String chuCheckId;
	private String submitId;
	
	public AffairRewardDeclaration() {
		super();
	}

	public AffairRewardDeclaration(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
	
	public String getApprovalUnit() {
		return approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getApprovalUnitId() {
		return approvalUnitId;
	}

	public void setApprovalUnitId(String approvalUnitId) {
		this.approvalUnitId = approvalUnitId;
	}
	
	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getCommendationUnit() {
		return commendationUnit;
	}

	public void setCommendationUnit(String commendationUnit) {
		this.commendationUnit = commendationUnit;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getTsStatus() {
		return tsStatus;
	}

	public void setTsStatus(String tsStatus) {
		this.tsStatus = tsStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMianmao() {
		return mianmao;
	}

	public void setMianmao(String mianmao) {
		this.mianmao = mianmao;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getJuCheckMan() {
		return juCheckMan;
	}

	public void setJuCheckMan(String juCheckMan) {
		this.juCheckMan = juCheckMan;
	}

	public String getChuCheckMan() {
		return chuCheckMan;
	}

	public void setChuCheckMan(String chuCheckMan) {
		this.chuCheckMan = chuCheckMan;
	}

	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}

	public String getSbType() {
		return sbType;
	}

	public void setSbType(String sbType) {
		this.sbType = sbType;
	}

	public String getJuCheckId() {
		return juCheckId;
	}

	public void setJuCheckId(String juCheckId) {
		this.juCheckId = juCheckId;
	}

	public String getChuCheckId() {
		return chuCheckId;
	}

	public void setChuCheckId(String chuCheckId) {
		this.chuCheckId = chuCheckId;
	}

	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}
}