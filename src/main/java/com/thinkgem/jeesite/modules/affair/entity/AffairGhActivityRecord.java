/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 工会活动记录Entity
 * @author cecil.li
 * @version 2019-11-29
 */
public class AffairGhActivityRecord extends DataEntity<AffairGhActivityRecord> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "标题", type = 0, align = 2, sort = 0)
	private String title;		// 标题
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String partyBranch;		// 单位
	private String partyBranchId;		// 党支部id
	@ExcelField(title = "时间", type = 0, align = 2, sort = 2)
	private Date date;		// 时间
	@ExcelField(title = "推送主页", type = 0, align = 2, sort = 3)
	private String push;		// 推送主页
	@ExcelField(title = "内容", type = 0, align = 2, sort = 4)
	private String content;		// 内容
//	@EsAttach
	private String filePath;		// 附件
	@ExcelField(title = "活动项目", type = 0, align = 2, sort = 5)
	private String activities;		// 活动项目

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String userId;         //获取当前账号单位
	private Date startDate;
	private Date endDate;
	private String threeCheckMan;    //三级审核人
	private String twoCheckMan;        //二级审核人
	private String oneCheckMan;        //一级审核人
	private String submitMan;       //提交人
	private String threeCheckId;     //三级审核人id
	private String twoCheckId;       //二级审核人id
	private String oneCheckId;       //一级审核人id
	private String submitId;      //提交人id
	private String checkType;     //审核状态


	//统计分析使用
	private String dateType;
	private Integer year;
	private Date dateStart;
	private Date dateEnd;
	private String month;
	private String label;
	
	public AffairGhActivityRecord() {
		super();
	}

	public AffairGhActivityRecord(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getPush() {
		return push;
	}

	public void setPush(String push) {
		this.push = push;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getActivities() {
		return activities;
	}

	public void setActivities(String activities) {
		this.activities = activities;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
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