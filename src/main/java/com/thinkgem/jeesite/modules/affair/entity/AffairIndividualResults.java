/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 个人比武成绩Entity
 * @author cecil.li
 * @version 2020-12-29
 */
public class AffairIndividualResults extends DataEntity<AffairIndividualResults> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "比武开始时间", type = 0, align = 2, sort = 0)
	private Date date;		// 时间
	@ExcelField(title = "比武结束时间", type = 0, align = 2, sort = 0)
	private Date finishDate;  // 比武结束时间
	@ExcelField(title = "比武名称", type = 0, align = 2, sort = 0)
	private String itemName;  // 比武名称
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	private String idNumber;		// 身份证
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 3)
	private String certificate;		// 证件号码
	@ExcelField(title = "项目一", type = 0, align = 2, sort = 4)
	private String itemOne;		// 项目一
	@ExcelField(title = "项目二", type = 0, align = 2, sort = 5)
	private String itemTwo;		// 项目二
	@ExcelField(title = "项目三", type = 0, align = 2, sort = 6)
	private String itemThree;		// 项目三
	@ExcelField(title = "项目四", type = 0, align = 2, sort = 7)
	private String itemFour;		// 项目四
	@ExcelField(title = "项目五", type = 0, align = 2, sort = 8)
	private String itemFive;		// 项目五
	@ExcelField(title = "项目六", type = 0, align = 2, sort = 9)
	private String itemSix;		// 项目六
	@ExcelField(title = "个人总成绩", type = 0, align = 2, sort = 10)
	private String score;		// 个人总成绩
	@ExcelField(title = "个人排名", type = 0, align = 2, sort = 11)
	private String rank;		// 个人排名
	@ExcelField(title = "备注", type = 0, align = 2, sort = 12)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public AffairIndividualResults() {
		super();
	}

	public AffairIndividualResults(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	public String getItemOne() {
		return itemOne;
	}

	public void setItemOne(String itemOne) {
		this.itemOne = itemOne;
	}
	
	public String getItemTwo() {
		return itemTwo;
	}

	public void setItemTwo(String itemTwo) {
		this.itemTwo = itemTwo;
	}
	
	public String getItemThree() {
		return itemThree;
	}

	public void setItemThree(String itemThree) {
		this.itemThree = itemThree;
	}
	
	public String getItemFour() {
		return itemFour;
	}

	public void setItemFour(String itemFour) {
		this.itemFour = itemFour;
	}
	
	public String getItemFive() {
		return itemFive;
	}

	public void setItemFive(String itemFive) {
		this.itemFive = itemFive;
	}
	
	public String getItemSix() {
		return itemSix;
	}

	public void setItemSix(String itemSix) {
		this.itemSix = itemSix;
	}
	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}