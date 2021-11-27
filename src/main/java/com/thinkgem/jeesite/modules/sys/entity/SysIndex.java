/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 索引管理Entity
 * @author tom.fu
 * @version 2020-09-16
 */
public class SysIndex extends DataEntity<SysIndex> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "角色编号",type = 0,align = 2,sort = 1)
	private String roleId;		// 角色id
	@ExcelField(title = "名称",type = 0,align = 2,sort = 2)
	private String name;		// name
	@ExcelField(title = "索引",type = 0,align = 2,sort = 3)
	private String indexCode;		// 索引
	private String createOrgId;		// create_org_id
	private String createIdNo;		// create_id_no
	private String updateOrgId;		// update_org_id
	private String updateIdNo;		// update_id_no
	
	public SysIndex() {
		super();
	}

	public SysIndex(String id){
		super(id);
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
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