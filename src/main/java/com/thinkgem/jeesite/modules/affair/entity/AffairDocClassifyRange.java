/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 使用范围设置Entity
 * @author kevin.jia
 * @version 2020-07-31
 */
public class AffairDocClassifyRange extends DataEntity<AffairDocClassifyRange> {
	
	private static final long serialVersionUID = 1L;
	private String classifyId;		// 文档分类id
	private String audiencesName;		// 受众名称
	private String audiencesType;		// 受众类别
	private String audiencesId;		// 受众id
	private String tempId;		// 临时id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairDocClassifyRange() {
		super();
	}

	public AffairDocClassifyRange(String id){
		super(id);
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	
	public String getAudiencesName() {
		return audiencesName;
	}

	public void setAudiencesName(String audiencesName) {
		this.audiencesName = audiencesName;
	}
	
	public String getAudiencesType() {
		return audiencesType;
	}

	public void setAudiencesType(String audiencesType) {
		this.audiencesType = audiencesType;
	}

	public String getAudiencesId() {
		return audiencesId;
	}

	public void setAudiencesId(String audiencesId) {
		this.audiencesId = audiencesId;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
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