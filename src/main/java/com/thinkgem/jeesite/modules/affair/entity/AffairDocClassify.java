/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 文档分类Entity
 * @author kevin.jia
 * @version 2020-07-29
 */
public class AffairDocClassify extends DataEntity<AffairDocClassify> {
	
	private static final long serialVersionUID = 1L;
	private String classifyCode;		// 分类编码
	private String classifyName;		// 分类名称
	private String parentClassify;		// 父分类
	private String sortNumber;		// 排序号
	private String usableRange;		// 使用范围(全员,指定)
	private String remark;		// 备注
	private String photo;		// 图片
	private String parentId;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String treeId;		// 接收左侧树的组织id
	private String tempId;     //临时id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	public AffairDocClassify() {
		super();
	}

	public AffairDocClassify(String id){
		super(id);
	}

	public String getClassifyCode() {
		return classifyCode;
	}

	public void setClassifyCode(String classifyCode) {
		this.classifyCode = classifyCode;
	}
	
	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	
	public String getParentClassify() {
		return parentClassify;
	}

	public void setParentClassify(String parentClassify) {
		this.parentClassify = parentClassify;
	}
	
	public String getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(String sortNumber) {
		this.sortNumber = sortNumber;
	}
	
	public String getUsableRange() {
		return usableRange;
	}

	public void setUsableRange(String usableRange) {
		this.usableRange = usableRange;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
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