/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 刊搞搞件子所属人Entity
 * @author daniel.liu
 * @version 2020-09-27
 */
public class AffairNewsName extends DataEntity<AffairNewsName> {
	
	private static final long serialVersionUID = 1L;
	private String newsName;		// 刊搞搞件所属人
	@NotEmpty
	private String newsIdNumber;		// 刊搞搞件所输入Id
	private String newsId;		// 刊搞搞件主表Id
	private String company;
	private String companyId;
	
	public AffairNewsName() {
		super();
	}

	public AffairNewsName(String id){
		super(id);
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
	
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}