/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 信息采用Entity
 * @author cecil.li
 * @version 2020-12-29
 */
public class AffairInformationAdoption extends DataEntity<AffairInformationAdoption> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "时间", type = 0, align = 2, sort = 1)
	private Date date;		// 时间
	@ExcelField(title = "公安处信息动态（篇）", type = 0, align = 2, sort = 2)
	private String xxChu;		// 公安处信息动态（篇）
	@ExcelField(title = "公安局信息动态（篇）", type = 0, align = 2, sort = 3)
	private String xxJu;		// 公安局信息动态（篇）
	@ExcelField(title = "部局信息动态（篇）", type = 0, align = 2, sort = 4)
	private String xxBuJu;		// 部局信息动态（篇）
	@ExcelField(title = "公安部信息动态（篇）", type = 0, align = 2, sort = 5)
	private String xxBu;		// 公安部信息动态（篇）
	@ExcelField(title = "公安处工作简报（篇）", type = 0, align = 2, sort = 6)
	private String gzChu;		// 公安处工作简报（篇）
	@ExcelField(title = "公安局工作简报（篇）", type = 0, align = 2, sort = 7)
	private String gzJu;		// 公安局工作简报（篇）
	@ExcelField(title = "部局工作简报（篇）", type = 0, align = 2, sort = 8)
	private String gzBuJu;		// 部局工作简报（篇）
	@ExcelField(title = "公安部工作简报（篇）", type = 0, align = 2, sort = 9)
	private String gzBu;		// 公安部工作简报（篇）
	@ExcelField(title = "公安处调研文章（篇）", type = 0, align = 2, sort = 10)
	private String dyChu;		// 公安处调研文章（篇）
	@ExcelField(title = "公安局调研文章（篇）", type = 0, align = 2, sort = 11)
	private String dyJu;		// 公安局调研文章（篇）
	@ExcelField(title = "部局调研文章（篇）", type = 0, align = 2, sort = 12)
	private String dyBuJu;		// 部局调研文章（篇）
	@ExcelField(title = "公安部调研文章（篇）", type = 0, align = 2, sort = 13)
	private String dyBu;		// 公安部调研文章（篇）
	@ExcelField(title = "公安处领导批示", type = 0, align = 2, sort = 14)
	private String psChu;		// 公安处领导批示
	@ExcelField(title = "公安局领导批示", type = 0, align = 2, sort = 15)
	private String psJu;		// 公安局领导批示
	@ExcelField(title = "部局领导批示", type = 0, align = 2, sort = 16)
	private String psBuJu;		// 部局领导批示
	@ExcelField(title = "公安部领导批示", type = 0, align = 2, sort = 17)
	private String psBu;		// 公安部领导批示
	@ExcelField(title = "地市级宣传报道", type = 0, align = 2, sort = 18)
	private String xcDs;		// 地市级宣传报道
	@ExcelField(title = "省部级宣传报道", type = 0, align = 2, sort = 19)
	private String xcSb;		// 省部级宣传报道
	@ExcelField(title = "中央级宣传报道", type = 0, align = 2, sort = 20)
	private String xcZy;		// 中央级宣传报道
	@ExcelField(title = "公安处其他材料（篇）", type = 0, align = 2, sort = 21)
	private String otherChu;		// 公安处其他材料（篇）
	@ExcelField(title = "公安局其他材料（篇）", type = 0, align = 2, sort = 22)
	private String otherJu;		// 公安局其他材料（篇）
	@ExcelField(title = "部局其他材料（篇）", type = 0, align = 2, sort = 23)
	private String otherBuJu;		// 部局其他材料（篇）
	@ExcelField(title = "公安部其他材料（篇）", type = 0, align = 2, sort = 24)
	private String otherBu;		// 公安部其他材料（篇）
	@ExcelField(title = "得分", type = 0, align = 2, sort = 25)
	private String score;		// 得分
	@ExcelField(title = "排名", type = 0, align = 2, sort = 26)
	private String rank;		// 排名
	@ExcelField(title = "备注", type = 0, align = 2, sort = 27)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public AffairInformationAdoption() {
		super();
	}

	public AffairInformationAdoption(String id){
		super(id);
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getXxChu() {
		return xxChu;
	}

	public void setXxChu(String xxChu) {
		this.xxChu = xxChu;
	}
	
	public String getXxJu() {
		return xxJu;
	}

	public void setXxJu(String xxJu) {
		this.xxJu = xxJu;
	}
	
	public String getXxBuJu() {
		return xxBuJu;
	}

	public void setXxBuJu(String xxBuJu) {
		this.xxBuJu = xxBuJu;
	}
	
	public String getXxBu() {
		return xxBu;
	}

	public void setXxBu(String xxBu) {
		this.xxBu = xxBu;
	}
	
	public String getGzChu() {
		return gzChu;
	}

	public void setGzChu(String gzChu) {
		this.gzChu = gzChu;
	}
	
	public String getGzJu() {
		return gzJu;
	}

	public void setGzJu(String gzJu) {
		this.gzJu = gzJu;
	}
	
	public String getGzBuJu() {
		return gzBuJu;
	}

	public void setGzBuJu(String gzBuJu) {
		this.gzBuJu = gzBuJu;
	}
	
	public String getGzBu() {
		return gzBu;
	}

	public void setGzBu(String gzBu) {
		this.gzBu = gzBu;
	}
	
	public String getDyChu() {
		return dyChu;
	}

	public void setDyChu(String dyChu) {
		this.dyChu = dyChu;
	}
	
	public String getDyJu() {
		return dyJu;
	}

	public void setDyJu(String dyJu) {
		this.dyJu = dyJu;
	}
	
	public String getDyBuJu() {
		return dyBuJu;
	}

	public void setDyBuJu(String dyBuJu) {
		this.dyBuJu = dyBuJu;
	}
	
	public String getDyBu() {
		return dyBu;
	}

	public void setDyBu(String dyBu) {
		this.dyBu = dyBu;
	}
	
	public String getPsChu() {
		return psChu;
	}

	public void setPsChu(String psChu) {
		this.psChu = psChu;
	}
	
	public String getPsJu() {
		return psJu;
	}

	public void setPsJu(String psJu) {
		this.psJu = psJu;
	}
	
	public String getPsBuJu() {
		return psBuJu;
	}

	public void setPsBuJu(String psBuJu) {
		this.psBuJu = psBuJu;
	}
	
	public String getPsBu() {
		return psBu;
	}

	public void setPsBu(String psBu) {
		this.psBu = psBu;
	}
	
	public String getXcDs() {
		return xcDs;
	}

	public void setXcDs(String xcDs) {
		this.xcDs = xcDs;
	}
	
	public String getXcSb() {
		return xcSb;
	}

	public void setXcSb(String xcSb) {
		this.xcSb = xcSb;
	}
	
	public String getXcZy() {
		return xcZy;
	}

	public void setXcZy(String xcZy) {
		this.xcZy = xcZy;
	}
	
	public String getOtherChu() {
		return otherChu;
	}

	public void setOtherChu(String otherChu) {
		this.otherChu = otherChu;
	}
	
	public String getOtherJu() {
		return otherJu;
	}

	public void setOtherJu(String otherJu) {
		this.otherJu = otherJu;
	}
	
	public String getOtherBuJu() {
		return otherBuJu;
	}

	public void setOtherBuJu(String otherBuJu) {
		this.otherBuJu = otherBuJu;
	}
	
	public String getOtherBu() {
		return otherBu;
	}

	public void setOtherBu(String otherBu) {
		this.otherBu = otherBu;
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
}