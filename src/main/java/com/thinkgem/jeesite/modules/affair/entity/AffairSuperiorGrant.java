/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 上级拨款Entity
 * @author cecil.li
 * @version 2019-12-21
 */
public class AffairSuperiorGrant extends DataEntity<AffairSuperiorGrant> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "拨款类型", type = 0, align = 2, sort = 0, dictType="affair_bokuan")
	private String grantType;		// 拨款类型
	@ExcelField(title = "金额", type = 0, align = 2, sort = 1)
	private Integer amount;		// 金额
	@ExcelField(title = "拨款单位", type = 0, align = 2, sort = 2)
	private String appropriationUnit;		// 拨款单位
	@ExcelField(title = "接受组织", type = 0, align = 2, sort = 3)
	private String acceptingUnit;		// 接受组织
	@ExcelField(title = "拨款日期", type = 0, align = 2, sort = 4)
	private Date grantDate;		// 拨款日期
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Integer beginAmount;		// 开始 金额
	private Integer endAmount;		// 结束 金额
	private Date beginGrantDate;		// 开始 拨款日期
	private Date endGrantDate;		// 结束 拨款日期
	
	public AffairSuperiorGrant() {
		super();
	}

	public AffairSuperiorGrant(String id){
		super(id);
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public String getAppropriationUnit() {
		return appropriationUnit;
	}

	public void setAppropriationUnit(String appropriationUnit) {
		this.appropriationUnit = appropriationUnit;
	}
	
	public String getAcceptingUnit() {
		return acceptingUnit;
	}

	public void setAcceptingUnit(String acceptingUnit) {
		this.acceptingUnit = acceptingUnit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
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
	
	public Integer getBeginAmount() {
		return beginAmount;
	}

	public void setBeginAmount(Integer beginAmount) {
		this.beginAmount = beginAmount;
	}
	
	public Integer getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(Integer endAmount) {
		this.endAmount = endAmount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginGrantDate() {
		return beginGrantDate;
	}

	public void setBeginGrantDate(Date beginGrantDate) {
		this.beginGrantDate = beginGrantDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndGrantDate() {
		return endGrantDate;
	}

	public void setEndGrantDate(Date endGrantDate) {
		this.endGrantDate = endGrantDate;
	}
		
}