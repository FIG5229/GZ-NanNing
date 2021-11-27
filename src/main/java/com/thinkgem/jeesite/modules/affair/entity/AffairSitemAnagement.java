/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 场地管理Entity
 * @author tom.Fu
 * @version 2020-07-30
 */
public class AffairSitemAnagement extends DataEntity<AffairSitemAnagement> {
	
	private static final long serialVersionUID = 1L;
	//@ExcelField(title = "活动名称",type = 0,align = 2,sort = 1)
	//private String id;			//id编号
	private String province;		// 省
	private String city;		// 市
	private String siteName;		// 场地名称
	private String maxChengxunCapacity;		// 最大承受能力
	private String siteComputerNum;		// 场地电脑个数
	private String site;		// 地址
	private String phone;		// 联系电话
	private String email;		// 邮箱
	private String remark;		// 备注
	private String accessoryFile;		// 附件
	private String siteBedNum;		// 场地床位个数
	private String siteCanteenRepastNum;		// 食堂就餐人数
	private String linkman;		// 联系人
	private String fax;		// 传真
	private String isValid;		// 是否有效
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


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

	public AffairSitemAnagement() {
		super();
	}

	public AffairSitemAnagement(String id){
		super(id);
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public String getMaxChengxunCapacity() {
		return maxChengxunCapacity;
	}

	public void setMaxChengxunCapacity(String maxChengxunCapacity) {
		this.maxChengxunCapacity = maxChengxunCapacity;
	}
	
	public String getSiteComputerNum() {
		return siteComputerNum;
	}

	public void setSiteComputerNum(String siteComputerNum) {
		this.siteComputerNum = siteComputerNum;
	}
	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAccessoryFile() {
		return accessoryFile;
	}

	public void setAccessoryFile(String accessoryFile) {
		this.accessoryFile = accessoryFile;
	}
	
	public String getSiteBedNum() {
		return siteBedNum;
	}

	public void setSiteBedNum(String siteBedNum) {
		this.siteBedNum = siteBedNum;
	}
	
	public String getSiteCanteenRepastNum() {
		return siteCanteenRepastNum;
	}

	public void setSiteCanteenRepastNum(String siteCanteenRepastNum) {
		this.siteCanteenRepastNum = siteCanteenRepastNum;
	}
	
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	
}