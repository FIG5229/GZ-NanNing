/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 意识形态Entity
 * @author daniel.liu
 * @version 2020-06-22
 */
public class AffairIdeology extends DataEntity<AffairIdeology> {
	
	private static final long serialVersionUID = 1L;
    @ExcelField(title = "报告单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位Id
    @ExcelField(title = "报告类型", type = 0, align = 2, sort = 2, dictType = "report_tale")
	private String reportType;		// 报告类型
	private Date time;		// 时间
    @ExcelField(title = "报告内容", type = 0, align = 2, sort = 4)
	private String content;		// 报送内容
	@ExcelField(title = "报告内容", type = 0, align = 2, sort = 5)
	private String filePath;		// 附件
	private String reportStatus;		// 报送状态
    @ExcelField(title = "报送时间", type = 0, align = 2, sort = 3)
	private Date reportTime;		// 报送时间
	private Date signInTime;		// 签收时间
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginReportTime;		// 开始 报送时间
	private Date endReportTime;		// 结束 报送时间
	private Integer year;

	//宣教账号则查询 本公司及以下数据
	private boolean hasAuth;

	private String classify;		//数据分类标识


	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public AffairIdeology() {
		super();
	}

	public AffairIdeology(String id){
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
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
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
	
	public Date getBeginReportTime() {
		return beginReportTime;
	}

	public void setBeginReportTime(Date beginReportTime) {
		this.beginReportTime = beginReportTime;
	}
	
	public Date getEndReportTime() {
		return endReportTime;
	}

	public void setEndReportTime(Date endReportTime) {
		this.endReportTime = endReportTime;
	}

	public boolean getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth) {
		this.hasAuth = hasAuth;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}