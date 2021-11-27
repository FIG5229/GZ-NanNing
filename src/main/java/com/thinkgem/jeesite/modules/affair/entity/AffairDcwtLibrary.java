/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 督察问题管理Entity
 * @author cecil.li
 * @version 2019-11-08
 */
public class AffairDcwtLibrary extends DataEntity<AffairDcwtLibrary> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "开始时间", type = 0, align = 2, sort = 0)
	private Date time;		// 开始时间
	@ExcelField(title = "结束时间", type = 0, align = 2, sort = 1)
	private Date finishDate;      //结束时间
	@ExcelField(title = "责任单位", type = 0, align = 2, sort = 2)
	private String responsibleUnit;		// 责任单位
	private String responsibleUnitId;		// 责任单位id
	@ExcelField(title = "监督单位", type = 0, align = 2, sort = 3, dictType="affair_jdunit")
	private String supervisoryUnit;		// 监督单位
	@ExcelField(title = "督察方式", type = 0, align = 2, sort = 4, dictType="affair_dc_type")
	private String dcType;     //督察方式
	/*@ExcelField(title = "存在不足", type = 0, align = 2, sort = 5, dictType="affair_wtlb")*/
	private String problemCategory;		// 存在不足
	@ExcelField(title = "问题概述", type = 0, align = 2, sort = 6)
	private String foundProblem;		// 问题概述
	@ExcelField(title = "整改情况", type = 0, align = 2, sort = 7, dictType="affair_zhenggai")
	private String rectification;		// 整改情况
	@ExcelField(title = "整改说明", type = 0, align = 2, sort = 8)
	private String processingSituation;		// 整改说明
	private String remark;		// 备注
	@ExcelField(title = "附件", type = 0, align = 2, sort = 9)
	private String annex;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	private String month;
	private Integer year;
	private String flag;
	private String zgType;
	private String dateType;
	private String jdUnit;

	@EsIgnore
	private String[] problemCategoryArr;
	@ExcelField(title = "存在不足", type = 0, align = 2, sort = 5)
	private String type; // 导入导出存放存在不足
	private String userId;

	
	public AffairDcwtLibrary() {
		super();
	}

	public AffairDcwtLibrary(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getResponsibleUnit() {
		return responsibleUnit;
	}

	public void setResponsibleUnit(String responsibleUnit) {
		this.responsibleUnit = responsibleUnit;
	}
	
	public String getProblemCategory() {
		return problemCategory;
	}

	public void setProblemCategory(String problemCategory) {
		this.problemCategory = problemCategory;
	}
	
	public String getFoundProblem() {
		return foundProblem;
	}

	public void setFoundProblem(String foundProblem) {
		this.foundProblem = foundProblem;
	}
	
	public String getProcessingSituation() {
		return processingSituation;
	}

	public void setProcessingSituation(String processingSituation) {
		this.processingSituation = processingSituation;
	}
	
	public String getRectification() {
		return rectification;
	}

	public void setRectification(String rectification) {
		this.rectification = rectification;
	}
	
	public String getSupervisoryUnit() {
		return supervisoryUnit;
	}

	public void setSupervisoryUnit(String supervisoryUnit) {
		this.supervisoryUnit = supervisoryUnit;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getResponsibleUnitId() {
		return responsibleUnitId;
	}

	public void setResponsibleUnitId(String responsibleUnitId) {
		this.responsibleUnitId = responsibleUnitId;
	}

	public String getDcType() {
		return dcType;
	}

	public void setDcType(String dcType) {
		this.dcType = dcType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String[] getProblemCategoryArr() {
		return problemCategoryArr;
	}

	public void setProblemCategoryArr(String[] problemCategoryArr) {
		this.problemCategoryArr = problemCategoryArr;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getZgType() {
		return zgType;
	}

	public void setZgType(String zgType) {
		this.zgType = zgType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getJdUnit() {
		return jdUnit;
	}

	public void setJdUnit(String jdUnit) {
		this.jdUnit = jdUnit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}