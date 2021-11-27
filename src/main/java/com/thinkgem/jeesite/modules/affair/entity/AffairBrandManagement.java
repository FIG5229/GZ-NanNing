/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 品牌创建Entity
 * @author cecil.li
 * @version 2019-11-07
 */
public class AffairBrandManagement extends DataEntity<AffairBrandManagement> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "品牌创建时间", type = 0, align = 2, sort = 0)
	private Date createTime;		// 品牌创建时间
	@ExcelField(title = "品牌类型", type = 0, align = 2, sort = 1, dictType="affair_pinpai")
	private String brandType;		// 品牌类型
	@ExcelField(title = "人员组成", type = 0, align = 2, sort = 3)
	private String personnel;		// 人员
	@ExcelField(title = "基本情况", type = 0, align = 2, sort = 4)
	private String basicSituation;		// 基本情况
	@ExcelField(title = "所在团组织", type = 0, align = 2, sort = 2)
	private String unit;    //所在团组织
	private String unitId;        //所在团组织id
	private String annex;   //附件
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
	private String ctwOpinion;
	private String opinion;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;

	private String officeId;
	
	public AffairBrandManagement() {
		super();
	}

	public AffairBrandManagement(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getBrandType() {
		return brandType;
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}
	
	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}
	
	public String getBasicSituation() {
		return basicSituation;
	}

	public void setBasicSituation(String basicSituation) {
		this.basicSituation = basicSituation;
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

	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
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

	public String getCtwOpinion() {
		return ctwOpinion;
	}

	public void setCtwOpinion(String ctwOpinion) {
		this.ctwOpinion = ctwOpinion;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
}