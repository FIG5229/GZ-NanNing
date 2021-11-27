/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 党小组Entity
 * @author daniel.liu
 * @version 2020-05-26
 */
public class AffairPoliticalGroup extends DataEntity<AffairPoliticalGroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupName;		// 党小组名称
	private Date time;		// 党小组成立时间
	private String groupHeadman;		// 党小组组长
	private String phoneNumber;		// 联系电话
	private String groupContact;		// 党组织联系人
	private String groupPoliticalNumber;		// 党小组党员数
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginTime;		// 开始 党小组成立时间
	private Date endTime;		// 结束 党小组成立时间
	private String parentId;	//树级目录的父Id
	private String parentIds;	//所有的父id
	private String treeId;	//树级目录中的Id

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public AffairPoliticalGroup() {
		super();
	}

	public AffairPoliticalGroup(String id){
		super(id);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getGroupHeadman() {
		return groupHeadman;
	}

	public void setGroupHeadman(String groupHeadman) {
		this.groupHeadman = groupHeadman;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getGroupContact() {
		return groupContact;
	}

	public void setGroupContact(String groupContact) {
		this.groupContact = groupContact;
	}
	
	public String getGroupPoliticalNumber() {
		return groupPoliticalNumber;
	}

	public void setGroupPoliticalNumber(String groupPoliticalNumber) {
		this.groupPoliticalNumber = groupPoliticalNumber;
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
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
}