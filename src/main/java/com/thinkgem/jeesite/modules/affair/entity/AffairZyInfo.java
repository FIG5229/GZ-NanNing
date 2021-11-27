/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 助医管理Entity
 * @author cecil.li
 * @version 2019-11-05
 */
public class AffairZyInfo extends DataEntity<AffairZyInfo> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date date;		// 时间
	private Date finishDate;        //结束时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位机构id
	/*修改排序使其与模板一致*/
	@ExcelField(title = "补助类型", type = 0, align = 2, sort = 5, dictType="affair_zybuzhu")
	private String type;		// 补助类型
	@ExcelField(title = "补助机构", type = 0, align = 2, sort = 7, dictType="bz_jigou")
	private String bzJigou;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 4, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "补助金额", type = 0, align = 2, sort = 6)
	private Float money;		// 补助金额
	@ExcelField(title = "申请事由", type = 0, align = 2, sort = 8)
	private String application;      //申请事由
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
    private String userId;         //获取当前账号单位
	private Date startDate;
	private Date endDate;
	private Integer minMoney;
	private Integer maxMoney;

	private String tabFlag;		// 标识二级tab页
	@EsIgnore
	private List<String> officeIds;		// 存放二级tag对应的机构id集合
	@EsIgnore
	private List<AffairZyInfo> childrens;


	//统计分析使用
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;


	public List<AffairZyInfo> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<AffairZyInfo> childrens) {
		this.childrens = childrens;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Integer minMoney) {
		this.minMoney = minMoney;
	}

	public Integer getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Integer maxMoney) {
		this.maxMoney = maxMoney;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getBzJigou() {
		return bzJigou;
	}

	public void setBzJigou(String bzJigou) {
		this.bzJigou = bzJigou;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTabFlag() {
		return tabFlag;
	}

	public void setTabFlag(String tabFlag) {
		this.tabFlag = tabFlag;
	}

	public List<String> getOfficeIds() {
		return officeIds;
	}

	public void setOfficeIds(List<String> officeIds) {
		this.officeIds = officeIds;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}