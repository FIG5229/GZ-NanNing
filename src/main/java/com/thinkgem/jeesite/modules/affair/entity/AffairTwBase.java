/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 团委（支部）基本信息Entity
 * @author cecil.li
 * @version 2019-12-04
 */
public class AffairTwBase extends DataEntity<AffairTwBase> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 组织名称
	private Integer num;		// 团（支）委人数
	private String orgName;     //所属上级团组织
	private String unit;     //所辖单位
	private String unitId;    //所辖单位id
	private String hjDate;     //上一次换届改选时间
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String parentId;
	private String parentIds;
	private Integer partyNum;   //团员人数
	private String childId;    //团员注册ID
	private String sort;  // 排序

	private List<AffairTwBaseSign> affairTwBaseSignList = Lists.newArrayList();		// 子表列表

	private String treeId;		// 接收左侧树的组织id
	
	public AffairTwBase() {
		super();
	}

	public AffairTwBase(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

	public List<AffairTwBaseSign> getAffairTwBaseSignList() {
		return affairTwBaseSignList;
	}

	public void setAffairTwBaseSignList(List<AffairTwBaseSign> affairTwBaseSignList) {
		this.affairTwBaseSignList = affairTwBaseSignList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getHjDate() {
		return hjDate;
	}

	public void setHjDate(String hjDate) {
		this.hjDate = hjDate;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}


	public Integer getPartyNum() {
		return partyNum;
	}

	public void setPartyNum(Integer partyNum) {
		this.partyNum = partyNum;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
