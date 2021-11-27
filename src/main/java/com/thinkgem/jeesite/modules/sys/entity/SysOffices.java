/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 组织关系对应关系Entity
 * @author bradley.zhao
 * @version 2020-12-13
 */
public class SysOffices extends DataEntity<SysOffices> {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;		// 单位名称
	private String partyId;		// 党组织ID
	private String partyName;		// 党组织名称
	private String groupId;		// 团组织ID
	private String groupName;		// 团组织名称
	private String unionId;		// 工会组织ID
	private String unionName;		// 工会组织名称


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public SysOffices() {
		super();
	}

	public SysOffices(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	
	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}
	
}