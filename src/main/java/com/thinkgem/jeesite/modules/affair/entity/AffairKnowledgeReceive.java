/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 党规党章及党建知识关联Entity
 * @author eav.liu
 * @version 2019-11-04
 */
public class AffairKnowledgeReceive extends DataEntity<AffairKnowledgeReceive> {
	
	private static final long serialVersionUID = 1L;
	private String noticeId;		// 党章党规及党建知识id
	private String orgId;		// 接收部门id
	
	public AffairKnowledgeReceive() {
		super();
	}

	public AffairKnowledgeReceive(String id){
		super(id);
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}