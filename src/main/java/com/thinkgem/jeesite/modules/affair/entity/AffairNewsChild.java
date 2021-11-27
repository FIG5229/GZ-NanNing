/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 刊搞搞件子表Entity
 * @author daniel.liu
 * @version 2020-09-27
 */
public class AffairNewsChild extends DataEntity<AffairNewsChild> {
	
	private static final long serialVersionUID = 1L;
	private String newsAuthor;		// 刊搞搞件作者
	private String newsId;		// 刊搞搞件主表Id
	private String newsName;		// 刊搞搞件所属人
	private String newsIdNumber;		// 刊搞搞件所输入Id
	private String newsUnit;		// 看稿稿件所属单位
	private String newsUnitId;		// 刊搞搞件所属单位Id
	
	public AffairNewsChild() {
		super();
	}

	public AffairNewsChild(String id){
		super(id);
	}

	public String getNewsAuthor() {
		return newsAuthor;
	}

	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}
	
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	public String getNewsName() {
		return newsName;
	}

	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}
	
	public String getNewsIdNumber() {
		return newsIdNumber;
	}

	public void setNewsIdNumber(String newsIdNumber) {
		this.newsIdNumber = newsIdNumber;
	}
	
	public String getNewsUnit() {
		return newsUnit;
	}

	public void setNewsUnit(String newsUnit) {
		this.newsUnit = newsUnit;
	}
	
	public String getNewsUnitId() {
		return newsUnitId;
	}

	public void setNewsUnitId(String newsUnitId) {
		this.newsUnitId = newsUnitId;
	}
	
}