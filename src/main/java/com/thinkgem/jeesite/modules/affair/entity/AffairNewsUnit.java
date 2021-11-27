/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 刊搞搞件子所属单位Entity
 * @author daniel.liu
 * @version 2020-09-27
 */
public class AffairNewsUnit extends DataEntity<AffairNewsUnit> {
	
	private static final long serialVersionUID = 1L;
	private String newsUnit;		// 看稿稿件所属单位
	private String newsUnitId;		// 刊搞搞件所属单位Id
	private String newsId;		// 刊搞搞件主表Id
	
	public AffairNewsUnit() {
		super();
	}

	public AffairNewsUnit(String id){
		super(id);
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
	
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
}