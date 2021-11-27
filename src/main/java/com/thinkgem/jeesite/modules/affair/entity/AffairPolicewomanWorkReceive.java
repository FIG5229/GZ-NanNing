/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 女警工作管理关联表Entity
 * @author eav.liu
 * @version 2020-03-26
 */
public class AffairPolicewomanWorkReceive extends DataEntity<AffairPolicewomanWorkReceive> {
	
	private static final long serialVersionUID = 1L;
	private String pwWorkId;		// 主表的id
	private String orgId;		// 接收部门id
	
	public AffairPolicewomanWorkReceive() {
		super();
	}

	public AffairPolicewomanWorkReceive(String id){
		super(id);
	}

	public String getPwWorkId() {
		return pwWorkId;
	}

	public void setPwWorkId(String pwWorkId) {
		this.pwWorkId = pwWorkId;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}