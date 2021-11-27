/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 典型培树事迹Entity
 * 典型人物 子表
 * @author daniel.liu
 * @version 2020-06-18
 */
public class AffairTypicalPersonNews extends DataEntity<AffairTypicalPersonNews> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String url;		// 事迹材料链接
	//未用
	private String newsId;		// 事迹Id
	private String typicalPersonId;		// 典型人物表Id
	private String idNumber;		// 身份证号
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	
	public AffairTypicalPersonNews() {
		super();
	}

	public AffairTypicalPersonNews(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	public String getTypicalPersonId() {
		return typicalPersonId;
	}

	public void setTypicalPersonId(String typicalPersonId) {
		this.typicalPersonId = typicalPersonId;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	
}