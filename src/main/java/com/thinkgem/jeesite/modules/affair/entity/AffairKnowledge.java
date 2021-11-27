/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 党章党规及党建知识Entity
 * @author eav.liu
 * @version 2019-11-04
 */
public class AffairKnowledge extends DataEntity<AffairKnowledge> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String type;		// 类型
	private String publishDep;		// 发布部门
	private String publishOrgId;		// 发布部门机构id
	private String publisher;		// 发布人
	private String receiveDep;		// 接收部门
	private String receiveDepId;		// 接收部门id
	private String content;		// 主要内容
//	@EsAttach
	private String filePath;		// 附件
	private Date publishDate;		// 发布日期
	private String status;		// 状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;

	private Date endDate;
	//是否要发布的权限
	private boolean hasAuth;
	
	public AffairKnowledge() {
		super();
	}

	public AffairKnowledge(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPublishDep() {
		return publishDep;
	}

	public void setPublishDep(String publishDep) {
		this.publishDep = publishDep;
	}
	
	public String getPublishOrgId() {
		return publishOrgId;
	}

	public void setPublishOrgId(String publishOrgId) {
		this.publishOrgId = publishOrgId;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
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

}