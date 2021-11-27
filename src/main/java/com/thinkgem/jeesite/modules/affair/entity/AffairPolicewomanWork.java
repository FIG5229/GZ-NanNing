/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 女警工作管理Entity
 * @author eav.liu
 * @version 2020-03-26
 */
public class AffairPolicewomanWork extends DataEntity<AffairPolicewomanWork> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String publishDep;		// 发布部门
	private String publishDepId;		// 发布部门机构id
	private String publisher;		// 发布人
	private String receiveDep;		// 接收部门
	private String receiveDepId;		// 接收部门id
	private String content;		// 主要内容
	private String filePath;		// 附件
	private Date publishDate;		// 发布日期
	private String status;		// 状态（1：保存/未发布 2：发布 3：取消发布/作废 ）
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id

	// 前端
	private Date startDate;
	private Date endDate;
	private boolean hasAuth;// 是否有管理权限
	private String userId;


	public AffairPolicewomanWork() {
		super();
	}

	public AffairPolicewomanWork(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPublishDep() {
		return publishDep;
	}

	public void setPublishDep(String publishDep) {
		this.publishDep = publishDep;
	}

	public String getPublishDepId() {
		return publishDepId;
	}

	public void setPublishDepId(String publishDepId) {
		this.publishDepId = publishDepId;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getReceiveDep() {
		return receiveDep;
	}

	public void setReceiveDep(String receiveDep) {
		this.receiveDep = receiveDep;
	}
	
	public String getReceiveDepId() {
		return receiveDepId;
	}

	public void setReceiveDepId(String receiveDepId) {
		this.receiveDepId = receiveDepId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth) {
		this.hasAuth = hasAuth;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}