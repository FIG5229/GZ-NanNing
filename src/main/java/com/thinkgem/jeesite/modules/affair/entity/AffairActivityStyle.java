/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 警官艺术团活动风采Entity
 * @author cecil.li
 * @version 2019-11-07
 */
public class AffairActivityStyle extends DataEntity<AffairActivityStyle> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "标题", type = 0, align = 2, sort = 0)
	private String title;		// 标题
	@ExcelField(title = "类别", type = 0, align = 2, sort = 1, dictType="affair_huodong")
	private String category;		// 类别
	@ExcelField(title = "所属团组织", type = 0, align = 2, sort = 2)
	private String ownGroupOrganization;		// 所属团组织
	private String ownGroupOrganizationId;		// 所属团组织id
	@ExcelField(title = "发布时间", type = 0, align = 2, sort = 3)
	private Date releaseTime;		// 发布时间
	@ExcelField(title = "主要内容", type = 0, align = 2, sort = 4)
	private String mainContent;		// 主要内容
	@ExcelField(title = "附件", type = 0, align = 2, sort = 5)
	private String annex;		// 附件

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public AffairActivityStyle() {
		super();
	}

	public AffairActivityStyle(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
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