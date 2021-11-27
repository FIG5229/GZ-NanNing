/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 民警思想动态分析Entity
 * @author daniel.liu
 * @version 2020-05-11
 */
public class AffairPoliceThoughtAnalysis extends DataEntity<AffairPoliceThoughtAnalysis> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "报送人", type = 0, align = 2, sort = 1)
	private String personName;		// 报送人
	@ExcelField(title = "报告单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	@ExcelField(title = "报告内容", type = 0, align = 2, sort = 3)
	private String content;		// 简要内容
//	@ExcelField(title = "时间", type = 0, align = 2, sort = 4)
	private String time;		// 时间
	@ExcelField(title = "附件", type = 0, align = 2, sort = 4)
	private String files;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String unitId;		// 单位id

	private Date signInTime;        // 签收时间
	/*暂时不用 签收状态和报告状态和成一个  之前有状态查询分开*/
	private String signInStatus;        // 签收转台
//    @ExcelField(title = "报告类型", type = 0, align = 2, sort = 2, dictType = "report_type")
	/*基层党支部要求用月份 其他用季度 不能再使用字典导入导出了,添加查看分别用各自的字典*/
    @ExcelField(title = "报告类型", type = 0, align = 2, sort = 2)
	private String reportType;        // 报告类型（年度、半年、季度、月份）
	private String reportStatus;        // 上报状态
    @ExcelField(title = "报送时间", type = 0, align = 2, sort = 1)
	private Date reportTime;        // 报送时间

	private Integer year;

	//前端
	private Date startTime;
	private Date endTime;
	//查看数据 进行签收时使用 用户修改数据过滤
	private boolean isSign;

	/*1：基层党支部 2：公安处党委 3：局机关党支部 4：局机关党委 5：公安局党委*/
	private String classify;		//数据分类标识


	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public boolean isSign() {
		return isSign;
	}

	public void setSign(boolean sign) {
		isSign = sign;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public String getSignInStatus() {
		return signInStatus;
	}

	public void setSignInStatus(String signInStatus) {
		this.signInStatus = signInStatus;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public AffairPoliceThoughtAnalysis() {
		super();
	}

	public AffairPoliceThoughtAnalysis(String id){
		super(id);
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
//	@JsonFormat(pattern = "yyyy")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
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
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}