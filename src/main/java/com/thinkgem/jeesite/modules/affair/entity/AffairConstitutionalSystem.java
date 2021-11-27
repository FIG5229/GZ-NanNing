/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 警官艺术团章程制度Entity
 * @author cecil.li
 * @version 2019-11-07
 */
public class AffairConstitutionalSystem extends DataEntity<AffairConstitutionalSystem> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "标题", type = 0, align = 2, sort = 0)
	private String title;		// 标题
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1,dictType = "affair_yishutuan_unit")
	private String ownGroupOrganization;		// 单位
	private String ownGroupOrganizationId;		// 所属团组织id
	@ExcelField(title = "发布时间", type = 0, align = 2, sort = 2)
	private Date releaseTime;		// 发布时间
	@ExcelField(title = "主要内容", type = 0, align = 2, sort = 3)
	private String mainContent;		// 主要内容
	@ExcelField(title = "附件", type = 0, align = 2, sort = 4)
	private String annec;		// 附件

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public AffairConstitutionalSystem() {
		super();
	}

	public AffairConstitutionalSystem(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getOwnGroupOrganization() {
		return ownGroupOrganization;
	}

	public void setOwnGroupOrganization(String ownGroupOrganization) {
		this.ownGroupOrganization = ownGroupOrganization;
	}
	
	public String getOwnGroupOrganizationId() {
		return ownGroupOrganizationId;
	}

	public void setOwnGroupOrganizationId(String ownGroupOrganizationId) {
		this.ownGroupOrganizationId = ownGroupOrganizationId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	public String getMainContent() {
		return mainContent;
	}

	public void setMainContent(String mainContent) {
		this.mainContent = mainContent;
	}
	
	public String getAnnec() {
		return annec;
	}

	public void setAnnec(String annec) {
		this.annec = annec;
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
}