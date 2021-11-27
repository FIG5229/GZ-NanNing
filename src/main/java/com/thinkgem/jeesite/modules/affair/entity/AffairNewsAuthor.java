/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 刊搞搞件子表作者Entity
 * @author daniel.liu
 * @version 2020-09-27
 */
public class AffairNewsAuthor extends DataEntity<AffairNewsAuthor> {
	
	private static final long serialVersionUID = 1L;
	private String newsAuthor;		// 刊搞搞件作者
	private String newsId;		// 刊搞搞件主表Id
	@NotEmpty
	private String idNumber;
	
	public AffairNewsAuthor() {
		super();
	}

	public AffairNewsAuthor(String id){
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
}