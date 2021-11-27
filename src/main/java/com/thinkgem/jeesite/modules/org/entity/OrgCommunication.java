/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 单位通讯信息Entity
 * @author eav.liu
 * @version 2019-11-23
 */
public class OrgCommunication extends DataEntity<OrgCommunication> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// 机构id
	@ExcelField(title = "单位邮政编码", type = 0, align = 2, sort = 0)
	private String postCode;		// 单位邮政编码
	@ExcelField(title = "单位地址", type = 0, align = 2, sort = 1)
	private String address;		// 单位地址
	@ExcelField(title = "单位电话号码", type = 0, align = 2, sort = 2)
	private String telephone;		// 单位电话号码
	@ExcelField(title = "单位传真号码", type = 0, align = 2, sort = 3)
	private String faxNumber;		// 单位传真号码
	@ExcelField(title = "单位网址", type = 0, align = 2, sort = 4)
	private String website;		// 单位网址
	@ExcelField(title = "单位E_MAIL地址", type = 0, align = 2, sort = 5)
	private String email;		// 单位E_MAIL地址
	
	public OrgCommunication() {
		super();
	}

	public OrgCommunication(String id){
		super(id);
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}