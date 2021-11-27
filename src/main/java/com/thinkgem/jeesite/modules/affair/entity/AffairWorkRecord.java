/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 民警记实功能Entity
 * @author tom.fu
 * @version 2020-08-21
 */
public class AffairWorkRecord extends DataEntity<AffairWorkRecord> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "年份",type = 0,align = 2,sort = 1)
	private String years;		// 年份
	@ExcelField(title = "周",type = 0,align = 2,sort = 2)
	private String weeks;		// 周
	@ExcelField(title = "开始时间",type = 0,align = 2,sort = 3)
	private Date beginDate;		// 开始时间
	@ExcelField(title = "结束时间",type = 0,align = 2,sort = 4)
	private Date overDate;		// 结束时间
	@ExcelField(title = "岗位工作完成情况",type = 0,align = 2,sort = 5)
	private String jobCompletionCondition;		// 岗位工作完成情况
	@ExcelField(title = "单位负责人意见",type = 0,align = 2,sort = 6)
	private String unitPrincipalOpinion;		// 单位负责人意见
    private String appendfile;          //附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String name;		//姓名
	private String unit;		//单位
	private String unitId;		//单位id
	private String postNape;		//职务项

	private String juCheckMan;		// 局审核人
	private String chuCheckMan;		// 处审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态
	private String juCheckId;		// 局审核id
	private String chuCheckId;		// 处审核id
	private String submitId;		// 提交人id
	private String opinion;         // 审核意见
	private String evaluate;        //评价
	/*数据过滤使用*/
	private String userId;
	private String officeId;
	private Boolean isPersonnel;		//是否为民警

    public String getAppendfile() {
        return appendfile;
    }

    public void setAppendfile(String appendfile) {
        this.appendfile = appendfile;
    }

    public AffairWorkRecord() {
		super();
	}

	public AffairWorkRecord(String id){
		super(id);
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}
	
	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}
	
	public String getJobCompletionCondition() {
		return jobCompletionCondition;
	}

	public void setJobCompletionCondition(String jobCompletionCondition) {
		this.jobCompletionCondition = jobCompletionCondition;
	}
	
	public String getUnitPrincipalOpinion() {
		return unitPrincipalOpinion;
	}

	public void setUnitPrincipalOpinion(String unitPrincipalOpinion) {
		this.unitPrincipalOpinion = unitPrincipalOpinion;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPostNape() {
		return postNape;
	}

	public void setPostNape(String postNape) {
		this.postNape = postNape;
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

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
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

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
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

	public Boolean getIsPersonnel() {
		return isPersonnel;
	}

	public void setIsPersonnel(Boolean isPersonnel) {
		this.isPersonnel = isPersonnel;
	}
}