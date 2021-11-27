/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 工作申报Entity
 * @author daniel.liu
 * @version 2020-07-01
 */
public class AffairDeclarationQiyi extends DataEntity<AffairDeclarationQiyi> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名/品牌名
	private String unit;		// 上报单位/所在单位
	private String unitId;		// unit_id
	private String job;		// 职务
	private String mainStory;		// 主要事迹
	private String orgOpinion;		// 组织意见
	private String remark;		// 备注
	private String checkMan;		// 审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态（0-4/提交）
	private String checkId;		// 审核单位id
	private String submitId;		// 提交人id
	private String shOpinion;		// 整改意见
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String type;		// 1、现进党组织2、优秀党员3、党务工作者4、党内品牌
	private String topType;		//1、争先创优申报2、其他申报
	private String filePath;		//附件
	
	public AffairDeclarationQiyi() {
		super();
	}

	public AffairDeclarationQiyi(String id){
		super(id);
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getMainStory() {
		return mainStory;
	}

	public void setMainStory(String mainStory) {
		this.mainStory = mainStory;
	}
	
	public String getOrgOpinion() {
		return orgOpinion;
	}

	public void setOrgOpinion(String orgOpinion) {
		this.orgOpinion = orgOpinion;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
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
	
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	
	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}
	
	public String getShOpinion() {
		return shOpinion;
	}

	public void setShOpinion(String shOpinion) {
		this.shOpinion = shOpinion;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTopType() {
		return topType;
	}

	public void setTopType(String topType) {
		this.topType = topType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}