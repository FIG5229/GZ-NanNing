/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 固资管理Entity
 * @author cecil.li
 * @version 2019-11-05
 */
public class AffairGz extends DataEntity<AffairGz> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "使用时间", type = 0, align = 2, sort = 0)
	private Date date;		// 使用时间
	@ExcelField(title = "使用单位", type = 0, align = 2, sort = 1)
	private String unit;		// 使用单位
	private String unitId;		// 使用单位机构id
	@ExcelField(title = "固资名称", type = 0, align = 2, sort = 2)
	private String name;		// 固资名称
	@ExcelField(title = "规格", type = 0, align = 2, sort = 5)
	private String specification;		// 规格
	@ExcelField(title = "型号", type = 0, align = 2, sort = 6)
	private String model;		// 型号
	@ExcelField(title = "数量", type = 0, align = 2, sort = 7)
	private Integer num;		// 数量
	@ExcelField(title = "单价", type = 0, align = 2, sort = 8)
	private Double price;		// 单价
	@ExcelField(title = "合计总价", type = 0, align = 2, sort = 9)
	private Double totalPrice;		// 合计总价
	@ExcelField(title = "预计使用年限", type = 0, align = 2, sort = 10)
	private String userYear;		// 预计使用年限
	@ExcelField(title = "保管人", type = 0, align = 2, sort = 11)
	private String bgPerson;		// 保管人
	@ExcelField(title = "验收人员", type = 0, align = 2, sort = 12)
	private String ysPerson;		// 验收人员
	@ExcelField(title = "验收意见", type = 0, align = 2, sort = 13)
	private String ysOpinion;		// 验收意见
	@ExcelField(title = "审核意见", type = 0, align = 2, sort = 14)
	private String shOpinion;		// 审核意见
	@ExcelField(title = "备注", type = 0, align = 2, sort = 15)
	private String remark;		// 备注
	@ExcelField(title = "固定资产编号", type = 0, align = 2, sort = 3)
	private String serialNumber;        // 固定资产编号
	@ExcelField(title = "固定资产条形码", type = 0, align = 2, sort = 4)
	private String barCode;        // 固定资产条形码

	private String filePath;    //上传附件地址
	private String userId;         //获取当前账号单位
    private String shType; //审核状态
	private String shPerson;  //审核人
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	private Double minPrice;
	private Double maxPrice;

	private String tabFlag;		// 标识二级tab页
	private List<String> officeIds;		// 存放二级tag对应的机构id集合
	
	public AffairGz() {
		super();
	}

	public AffairGz(String id){
		super(id);
	}

	public String getShType() {
		return shType;
	}

	public void setShType(String shType) {
		this.shType = shType;
	}

	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
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
	
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getUserYear() {
		return userYear;
	}

	public void setUserYear(String userYear) {
		this.userYear = userYear;
	}
	
	public String getBgPerson() {
		return bgPerson;
	}

	public void setBgPerson(String bgPerson) {
		this.bgPerson = bgPerson;
	}
	
	public String getYsPerson() {
		return ysPerson;
	}

	public void setYsPerson(String ysPerson) {
		this.ysPerson = ysPerson;
	}
	
	public String getYsOpinion() {
		return ysOpinion;
	}

	public void setYsOpinion(String ysOpinion) {
		this.ysOpinion = ysOpinion;
	}
	
	public String getShOpinion() {
		return shOpinion;
	}

	public void setShOpinion(String shOpinion) {
		this.shOpinion = shOpinion;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
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

	public String getCreateIdNo() {
		return createIdNo;
	}

	public void setCreateIdNo(String createIdNo) {
		this.createIdNo = createIdNo;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
}