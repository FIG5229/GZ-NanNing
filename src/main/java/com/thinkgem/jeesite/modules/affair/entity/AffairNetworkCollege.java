/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 中国干部网络学院Entity
 * @author alan.wu
 * @version 2020-08-03
 */
public class AffairNetworkCollege extends DataEntity<AffairNetworkCollege> {

	private static final long serialVersionUID = 1L;

	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位

	private String unitId;		//单位id

	@ExcelField(title = "时间", type = 0, align = 2, sort = 1)
	private String time;		// 时间

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名

	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;			//身份证号

	private Double lastMonthIntegral;		// 上月学习积分

	private Double thisMonthStatistics;		// 本月及上月学习累计积分

	@ExcelField(title = "本月学习积分", type = 0, align = 2, sort = 4)
	private Double thisMonthIntegral;		// 本月学习积分

	private Double thisYearStatistics;		// 本年度学习累计积分

	private String adjunct;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String treeId;


	private Date enterDateStart;
	private Date enterDateEnd;
	private String userId;

	private String createById_pro;//创建者id  -- 问题数据页面
	private String createByName_pro;//创建者用户名  -问题数据页面

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public AffairNetworkCollege() {
		super();
	}

	public AffairNetworkCollege(String id){
		super(id);
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@JsonFormat(pattern = "yyyy-MM")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLastMonthIntegral() {
		return lastMonthIntegral;
	}

	public void setLastMonthIntegral(Double lastMonthIntegral) {
		this.lastMonthIntegral = lastMonthIntegral;
	}

	public Double getThisMonthStatistics() {
		return thisMonthStatistics;
	}

	public void setThisMonthStatistics(Double thisMonthStatistics) {
		this.thisMonthStatistics = thisMonthStatistics;
	}

	public Double getThisMonthIntegral() {
		return thisMonthIntegral;
	}

	public void setThisMonthIntegral(Double thisMonthIntegral) {
		this.thisMonthIntegral = thisMonthIntegral;
	}

	public Double getThisYearStatistics() {
		return thisYearStatistics;
	}

	public void setThisYearStatistics(Double thisYearStatistics) {
		this.thisYearStatistics = thisYearStatistics;
	}

	public String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public Date getEnterDateStart() {
		return enterDateStart;
	}

	public void setEnterDateStart(Date enterDateStart) {
		this.enterDateStart = enterDateStart;
	}

	public Date getEnterDateEnd() {
		return enterDateEnd;
	}

	public void setEnterDateEnd(Date enterDateEnd) {
		this.enterDateEnd = enterDateEnd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateById_pro() {
		return createById_pro;
	}

	public void setCreateById_pro(String createById_pro) {
		this.createById_pro = createById_pro;
	}

	public String getCreateByName_pro() {
		return createByName_pro;
	}

	public void setCreateByName_pro(String createByName_pro) {
		this.createByName_pro = createByName_pro;
	}
}