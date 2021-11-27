/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 库房温湿度测试记录Entity
 * @author mason.xv
 * @version 2019-11-05
 */
public class AffairTemHum extends DataEntity<AffairTemHum> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间 年月日", type = 0, align = 2, sort = 0)
	private Date date;		// 时间 年月日
	@ExcelField(title = "库房温度", type = 0, align = 2, sort = 1)
	private String temperature;		// 库房温度
	@ExcelField(title = "库房相对湿度", type = 0, align = 2, sort = 2)
	private String humidity;		// 库房相对湿度
	@ExcelField(title = "状况分析", type = 0, align = 2, sort = 3)
	private String analysis;		// 状况分析
	@ExcelField(title = "控制措施", type = 0, align = 2, sort = 4, dictType="affair_cuoshi")
	private String measure;		// 控制措施
	@ExcelField(title = "记录人", type = 0, align = 2, sort = 5)
	private String recorder;		// 记录人

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 时间 年月日
	private Date endDate;		// 结束 时间 年月日
	
	public AffairTemHum() {
		super();
	}

	public AffairTemHum(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd ")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	
	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}
	
	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
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
		
}