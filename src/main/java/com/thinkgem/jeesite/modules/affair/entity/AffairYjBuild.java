/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 硬件建设Entity
 * @author kevin.jia
 * @version 2020-08-04
 */
public class AffairYjBuild extends DataEntity<AffairYjBuild> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "建设时间",type = 0,align = 2,sort = 2)
	private Date date;		// 建设时间
	@ExcelField(title = "建设内容",type = 0,align = 2,sort = 3)
	private String content;		// 建设内容
	@ExcelField(title = "项目名称",type = 0,align = 2,sort = 1)
	private String proName;		// 项目名称
	@ExcelField(title = "建设单位",type = 0,align = 2,sort = 4)
	private String buildUnit;		// 建设单位
	private String appendfile;		// 附件
	private String oneCheckMan;		// 一级审核人
	private String twoCheckMan;		// 二级审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态
	private String oneCheckId;		// 一级审核id
	private String twoCheckId;		// 二级审核id
	private String submitId;		// 提交人id
	private String opinion;		// 审核意见
	private Date beginDate;		// 开始 建设时间
	private Date endDate;		// 结束 建设时间
	private String userId;			//记录当前登录用户id
	private String officeId;        //记录当前用户部门id

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairYjBuild() {
		super();
	}

	public AffairYjBuild(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	public String getBuildUnit() {
		return buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}
	
	public String getAppendfile() {
		return appendfile;
	}

	public void setAppendfile(String appendfile) {
		this.appendfile = appendfile;
	}
	
	public String getOneCheckMan() {
		return oneCheckMan;
	}

	public void setOneCheckMan(String oneCheckMan) {
		this.oneCheckMan = oneCheckMan;
	}
	
	public String getTwoCheckMan() {
		return twoCheckMan;
	}

	public void setTwoCheckMan(String twoCheckMan) {
		this.twoCheckMan = twoCheckMan;
	}
	
	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}
	
	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	
	public String getOneCheckId() {
		return oneCheckId;
	}

	public void setOneCheckId(String oneCheckId) {
		this.oneCheckId = oneCheckId;
	}
	
	public String getTwoCheckId() {
		return twoCheckId;
	}

	public void setTwoCheckId(String twoCheckId) {
		this.twoCheckId = twoCheckId;
	}
	
	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
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

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
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