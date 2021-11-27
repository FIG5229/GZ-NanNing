/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 民警小家关联表Entity
 * @author daniel.liu
 * @version 2020-07-15
 */
public class AffairPoliceHomeChild extends DataEntity<AffairPoliceHomeChild> {
	
	private static final long serialVersionUID = 1L;

	private String policeHomeId;		// 民警小家主键
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id

	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private Integer orderNum;   //序号
	//@ExcelField(title = "建设时间", type = 0, align = 2, sort = 0)
	private Date pointDate;  //建设时间
	//@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "示范点名称", type = 0, align = 2, sort = 1)
	private String pointUnit;		// 小家建设名称
	private String pointUnitId;        //小家建设单位ID
	@ExcelField(title = "建设项目", type = 0, align = 2, sort = 2, dictType = "affair_jsxm")
	private String project;		// 建设项目
	@ExcelField(title = "所需设备", type = 0, align = 2, sort = 3)
	private String device;		// 所需设备
	@ExcelField(title = "建设数量", type = 0, align = 2, sort = 4)
	private Integer nums;		// 建设数量
	@ExcelField(title = "单价（元）", type = 0, align = 2, sort = 5)
	private Double price;		// 单价（元）
	@ExcelField(title = "小计（元）", type = 0, align = 2, sort = 6)
	private Double sum;		// 小计（元）
	//@ExcelField(title = "附件", type = 0, align = 2, sort = 10)
	private String filePath;    //附件
	//@ExcelField(title = "内容", type = 0, align = 2, sort = 5)
	private String content;    //内容
	//@ExcelField(title = "经办人", type = 0, align = 2, sort = 6)
	private String jingBan;    //经办人
	//@ExcelField(title = "申报单位审核人", type = 0, align = 2, sort = 7)
	private String unitShRen;    //申报单位审核人
	//@ExcelField(title = "处工会审核人", type = 0, align = 2, sort = 8)
	private String chuShOpinion;  //处工会审核人
	//@ExcelField(title = "局工会审核人", type = 0, align = 2, sort = 9)
	private String juShOpinion;   //局工会审核人
	
	public AffairPoliceHomeChild() {
		super();
	}

	public AffairPoliceHomeChild(String id){
		super(id);
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public String getPoliceHomeId() {
		return policeHomeId;
	}

	public void setPoliceHomeId(String policeHomeId) {
		this.policeHomeId = policeHomeId;
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
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getJingBan() {
		return jingBan;
	}

	public void setJingBan(String jingBan) {
		this.jingBan = jingBan;
	}
	
	public String getUnitShRen() {
		return unitShRen;
	}

	public void setUnitShRen(String unitShRen) {
		this.unitShRen = unitShRen;
	}
	
	public String getChuShOpinion() {
		return chuShOpinion;
	}

	public void setChuShOpinion(String chuShOpinion) {
		this.chuShOpinion = chuShOpinion;
	}
	
	public String getJuShOpinion() {
		return juShOpinion;
	}

	public void setJuShOpinion(String juShOpinion) {
		this.juShOpinion = juShOpinion;
	}

	public Date getPointDate() {
		return pointDate;
	}

	public void setPointDate(Date pointDate) {
		this.pointDate = pointDate;
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

	public String getPointUnit() {
		return pointUnit;
	}

	public void setPointUnit(String pointUnit) {
		this.pointUnit = pointUnit;
	}

	public String getPointUnitId() {
		return pointUnitId;
	}

	public void setPointUnitId(String pointUnitId) {
		this.pointUnitId = pointUnitId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}