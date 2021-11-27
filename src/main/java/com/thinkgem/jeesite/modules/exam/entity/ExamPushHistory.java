/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 绩效考评项推送Entity
 * @author daniel.liu
 * @version 2020-11-04
 */
public class ExamPushHistory extends DataEntity<ExamPushHistory> {
	
	private static final long serialVersionUID = 1L;
	private String workflowId;		// 考评id
	private String workflowDatasId;		// 考评项id
	private String objId;		// 被推送对象的Id
	private String fromId;		// 推送人的Id
	private String rowNum;		// 考评项的行数
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String status;		// 状态，推送到workflowdata表 1:已推送  2：推送成功  3：推送失败
	private String fromName;		// 推送人姓名
	private String objName;		// 被推送人姓名
	private String itemProject;		// 考评项目
	private String itemType;		// 考评类别
	private String itemStanddard;		// 评分标准内容
	private String itemRemark;		// 备注
	private String itemDetail;		// 具体事项
	private String standardId;		// 考评标准模板

	/*1:局考处	2:局考局机关	3：处考所	4：处考处机关及独立单位	5：处领导班子	6：中基层领导	7：民警*/
	private String examType;		// 考评类型

	public ExamPushHistory() {
		super();
	}

	public ExamPushHistory(String id){
		super(id);
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	
	public String getWorkflowDatasId() {
		return workflowDatasId;
	}

	public void setWorkflowDatasId(String workflowDatasId) {
		this.workflowDatasId = workflowDatasId;
	}
	
	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}
	
	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	
	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	
	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}
	
	public String getItemProject() {
		return itemProject;
	}

	public void setItemProject(String itemProject) {
		this.itemProject = itemProject;
	}
	
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	public String getItemStanddard() {
		return itemStanddard;
	}

	public void setItemStanddard(String itemStanddard) {
		this.itemStanddard = itemStanddard;
	}
	
	public String getItemRemark() {
		return itemRemark;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}
	
	public String getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}
	
	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
}