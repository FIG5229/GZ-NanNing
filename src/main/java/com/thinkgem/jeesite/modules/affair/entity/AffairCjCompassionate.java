/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 参公人员抚恤Entity
 * @author cecil.li
 * @version 2020-07-02
 */
public class AffairCjCompassionate extends DataEntity<AffairCjCompassionate> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "死亡时间", type = 0, align = 2, sort = 2)
	private Date deathDate;		// 死亡时间
	@ExcelField(title = "是否有遗嘱", type = 0, align = 2, sort = 3)
	private String isWill;		// 是否有遗嘱
	@ExcelField(title = "核定的离退休费", type = 0, align = 2, sort = 4)
	private Double fee;		// 核定的离退休费
	@ExcelField(title = "基本离退费（含警衔和历年增加）", type = 0, align = 2, sort = 5)
	private Double baseFee;		// 基本离退费（含警衔和历年增加）
	@ExcelField(title = "上一次全国城镇居民可支配收入", type = 0, align = 2, sort = 6)
	private Double lastFee;		// 上一次全国城镇居民可支配收入
	@ExcelField(title = "丧葬补助金", type = 0, align = 2, sort = 7)
	private Double funeralGrant;		// 丧葬补助金
	@ExcelField(title = "一次性困难补助", type = 0, align = 2, sort = 8)
	private Double hardshipAllowance;		// 一次性困难补助
	@ExcelField(title = "小计", type = 0, align = 2, sort = 9)
	private Double subtotal;		// 小计
	@ExcelField(title = "上一次全国城镇居民可支配收入的2倍", type = 0, align = 2, sort = 10)
	private Double doubleFee;		// 上一次全国城镇居民可支配收入的2倍
	@ExcelField(title = "40个月基本离退费", type = 0, align = 2, sort = 11)
	private Double fortyFee;		// 40个月基本离退费
	@ExcelField(title = "增发比例", type = 0, align = 2, sort = 12)
	private String issuanceRatio;		// 增发比例
	@ExcelField(title = "增发金额", type = 0, align = 2, sort = 13)
	private Double additionalAmount;		// 增发金额
	@ExcelField(title = "社保遗嘱救济费（月）", type = 0, align = 2, sort = 14)
	private Double relief;		// 社保遗嘱救济费（月）
	@ExcelField(title = "按【2014】101号遗属生活困难补助费（月）", type = 0, align = 2, sort = 15)
	private Double hardFee;		// 按【2014】101号遗属生活困难补助费（月）
	@ExcelField(title = "至2017年8月差额", type = 0, align = 2, sort = 16)
	private Double difference;		// 至2017年8月差额
	@ExcelField(title = "公务员丧葬补助按桂人社发【2011】186号", type = 0, align = 2, sort = 17)
	private Double gongWuYuan;		// 公务员丧葬补助按桂人社发【2011】186号
	@ExcelField(title = "三个月工资按桂发【1911】31号", type = 0, align = 2, sort = 19)
	private Double threeMonth;		// 三个月工资按桂发【1911】31号
	@ExcelField(title = "按民发【2014】101号计发合计", type = 0, align = 2, sort = 19)
	private Double people;		// 按民发【2014】101号计发合计
	@ExcelField(title = "合计补差", type = 0, align = 2, sort = 20)
	private Double sumDifference;		// 合计补差
	@ExcelField(title = "附件", type = 0, align = 2, sort = 21)
	private String annex;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDeathDate;		// 开始 死亡时间
	private Date endDeathDate;		// 结束 死亡时间
	private String userId;
	
	public AffairCjCompassionate() {
		super();
	}

	public AffairCjCompassionate(String id){
		super(id);
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	
	public String getIsWill() {
		return isWill;
	}

	public void setIsWill(String isWill) {
		this.isWill = isWill;
	}
	
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	public Double getBaseFee() {
		return baseFee;
	}

	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}
	
	public Double getLastFee() {
		return lastFee;
	}

	public void setLastFee(Double lastFee) {
		this.lastFee = lastFee;
	}
	
	public Double getFuneralGrant() {
		return funeralGrant;
	}

	public void setFuneralGrant(Double funeralGrant) {
		this.funeralGrant = funeralGrant;
	}
	
	public Double getHardshipAllowance() {
		return hardshipAllowance;
	}

	public void setHardshipAllowance(Double hardshipAllowance) {
		this.hardshipAllowance = hardshipAllowance;
	}
	
	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	
	public Double getDoubleFee() {
		return doubleFee;
	}

	public void setDoubleFee(Double doubleFee) {
		this.doubleFee = doubleFee;
	}
	
	public Double getFortyFee() {
		return fortyFee;
	}

	public void setFortyFee(Double fortyFee) {
		this.fortyFee = fortyFee;
	}
	
	public String getIssuanceRatio() {
		return issuanceRatio;
	}

	public void setIssuanceRatio(String issuanceRatio) {
		this.issuanceRatio = issuanceRatio;
	}
	
	public Double getAdditionalAmount() {
		return additionalAmount;
	}

	public void setAdditionalAmount(Double additionalAmount) {
		this.additionalAmount = additionalAmount;
	}
	
	public Double getRelief() {
		return relief;
	}

	public void setRelief(Double relief) {
		this.relief = relief;
	}
	
	public Double getHardFee() {
		return hardFee;
	}

	public void setHardFee(Double hardFee) {
		this.hardFee = hardFee;
	}
	
	public Double getDifference() {
		return difference;
	}

	public void setDifference(Double difference) {
		this.difference = difference;
	}
	
	public Double getGongWuYuan() {
		return gongWuYuan;
	}

	public void setGongWuYuan(Double gongWuYuan) {
		this.gongWuYuan = gongWuYuan;
	}
	
	public Double getThreeMonth() {
		return threeMonth;
	}

	public void setThreeMonth(Double threeMonth) {
		this.threeMonth = threeMonth;
	}
	
	public Double getPeople() {
		return people;
	}

	public void setPeople(Double people) {
		this.people = people;
	}
	
	public Double getSumDifference() {
		return sumDifference;
	}

	public void setSumDifference(Double sumDifference) {
		this.sumDifference = sumDifference;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
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
	
	public Date getBeginDeathDate() {
		return beginDeathDate;
	}

	public void setBeginDeathDate(Date beginDeathDate) {
		this.beginDeathDate = beginDeathDate;
	}
	
	public Date getEndDeathDate() {
		return endDeathDate;
	}

	public void setEndDeathDate(Date endDeathDate) {
		this.endDeathDate = endDeathDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}