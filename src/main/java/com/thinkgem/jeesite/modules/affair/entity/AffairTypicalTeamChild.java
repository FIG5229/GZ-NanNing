/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 典型集体子表Entity
 * @author daniel.liu
 * @version 2020-06-19
 */
public class AffairTypicalTeamChild extends DataEntity<AffairTypicalTeamChild> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String url;		// 地址
	private String typicalTeamId;		// 主表Id
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	
	public AffairTypicalTeamChild() {
		super();
	}

	public AffairTypicalTeamChild(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTypicalTeamId() {
		return typicalTeamId;
	}

	public void setTypicalTeamId(String typicalTeamId) {
		this.typicalTeamId = typicalTeamId;
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
	
}