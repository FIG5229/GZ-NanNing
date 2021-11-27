/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 公务员一次性抚恤Entity
 * @author cecil.li
 * @version 2020-07-02
 */
public class AffairGwy extends DataEntity<AffairGwy> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "死亡时间", type = 0, align = 2, sort = 2)
	private Date date;		// 死亡时间
	@ExcelField(title = "是否有遗嘱", type = 0, align = 2, sort = 3, dictType = "yes_no")
	private String isWill;		// 是否有遗嘱
	@ExcelField(title = "基本工资", type = 0, align = 2, sort = 4)
	private Double baseWage;		// 基本工资
	@ExcelField(title = "上一年全国城镇居民可支配收入", type = 0, align = 2, sort = 5)
	private Double lastYear;		// 上一年全国城镇居民可支配收入
	@ExcelField(title = "上一年城镇居民可支配收入的2倍", type = 0, align = 2, sort = 6)
	private Double lastDoule;		// 上一年城镇居民可支配收入的2倍
	@ExcelField(title = "40个月基本工资", type = 0, align = 2, sort = 7)
	private Double frotyMonth;		// 40个月基本工资
	@ExcelField(title = "增发比例", type = 0, align = 2, sort = 8)
	private String issuanceRatio;		// 增发比例
	@ExcelField(title = "增发金额", type = 0, align = 2, sort = 9)
	private Double additionalAmount;		// 增发金额
	@ExcelField(title = "两项合计", type = 0, align = 2, sort = 10)
	private Double sum;		// 两项合计
	@ExcelField(title = "社保遗嘱救济费（月）", type = 0, align = 2, sort = 11)
	private Double relief;		// 社保遗嘱救济费（月）
	@ExcelField(title = "按【2014】101号遗属生活困难补助费（月）", type = 0, align = 2, sort = 12)
	private Double hardFee;		// 按【2014】101号遗属生活困难补助费（月）
	@ExcelField(title = "公务员丧葬补助按桂人社发【2011】186号", type = 0, align = 2, sort = 13)
	private Double gongWuYuan;		// 公务员丧葬补助按桂人社发【2011】186号
	@ExcelField(title = "按民发【2014】101号计发合计", type = 0, align = 2, sort = 14)
	private Double people;		// 按民发【2014】101号计发合计
	@ExcelField(title = "附件", type = 0, align = 2, sort = 15)
	private String annex;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 死亡时间
	private Date endDate;		// 结束 死亡时间
	private String userId;
	
	public AffairGwy() {
		super();
	}

	public AffairGwy(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getIsWill() {
		return isWill;
	}

	public void setIsWill(String isWill) {
		this.isWill = isWill;
	}
	
	public Double getBaseWage() {
		return baseWage;
	}

	public void setBaseWage(Double baseWage) {
		this.baseWage = baseWage;
	}
	
	public Double getLastYear() {
		return lastYear;
	}

	public void setLastYear(Double lastYear) {
		this.lastYear = lastYear;
	}
	
	public Double getLastDoule() {
		return lastDoule;
	}

	public void setLastDoule(Double lastDoule) {
		this.lastDoule = lastDoule;
	}
	
	public Double getFrotyMonth() {
		return frotyMonth;
	}

	public void setFrotyMonth(Double frotyMonth) {
		this.frotyMonth = frotyMonth;
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
	
	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
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
	
	public Double getGongWuYuan() {
		return gongWuYuan;
	}

	public void setGongWuYuan(Double gongWuYuan) {
		this.gongWuYuan = gongWuYuan;
	}
	
	public Double getPeople() {
		return people;
	}

	public void setPeople(Double people) {
		this.people = people;
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
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}