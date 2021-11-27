/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 单位职数信息Entity
 * @author eav.liu
 * @version 2019-11-22
 */
public class OrgJobNumber extends DataEntity<OrgJobNumber> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// 机构id
	@ExcelField(title = "批准时间", type = 0, align = 2, sort = 0)
	private Date date;		// 批准时间
	@ExcelField(title = "批准单位", type = 0, align = 2, sort = 1)
	private String unit;		// 批准单位
	@ExcelField(title = "批准文号", type = 0, align = 2, sort = 2)
	private String fileNo;		// 批准文号
	@ExcelField(title = "批准文号", type = 0, align = 2, sort = 3)
	private Integer bzNum;		// 批准文号
	@ExcelField(title = "部级副职领导职数", type = 0, align = 2, sort = 4)
	private Integer bfNum;		// 部级副职领导职数
	@ExcelField(title = "厅级正职领导职数", type = 0, align = 2, sort = 5)
	private Integer tzNum;		// 厅级正职领导职数
	@ExcelField(title = "厅级副职领导职数", type = 0, align = 2, sort = 6)
	private Integer tfNum;		// 厅级副职领导职数
	@ExcelField(title = "厅级正职非领导职数", type = 0, align = 2, sort = 7)
	private Integer tzfNum;		// 厅级正职非领导职数
	@ExcelField(title = "厅级副职非领导职数", type = 0, align = 2, sort = 8)
	private Integer tffNum;		// 厅级副职非领导职数
	@ExcelField(title = "厅级副职非领导职数", type = 0, align = 2, sort = 9)
	private Integer czNum;		// 厅级副职非领导职数
	@ExcelField(title = "处级副职领导职数", type = 0, align = 2, sort = 10)
	private Integer cfNum;		// 处级副职领导职数
	@ExcelField(title = "处级正职非领导职数", type = 0, align = 2, sort = 11)
	private Integer czfNum;		// 处级正职非领导职数
	@ExcelField(title = "处级副职非领导职数", type = 0, align = 2, sort = 12)
	private Integer cffNum;		// 处级副职非领导职数
	@ExcelField(title = "科级正职领导职数", type = 0, align = 2, sort = 13)
	private Integer kzNum;		// 科级正职领导职数
	@ExcelField(title = "科级副职领导职数", type = 0, align = 2, sort = 14)
	private Integer kfNum;		// 科级副职领导职数
	@ExcelField(title = "科级正职非领导职数", type = 0, align = 2, sort = 15)
	private Integer kzfNum;		// 科级正职非领导职数
	@ExcelField(title = "科级副职非领导职数", type = 0, align = 2, sort = 16)
	private Integer kffNum;		// 科级副职非领导职数
	@ExcelField(title = "股级正职领导职数", type = 0, align = 2, sort = 17)
	private Integer gzNum;		// 股级正职领导职数
	@ExcelField(title = "股级副职领导职数", type = 0, align = 2, sort = 18)
	private Integer gfNum;		// 股级副职领导职数
	@ExcelField(title = "备注", type = 0, align = 2, sort = 19)
	private String remark;		// 备注

	private Date startDate;

	private Date endDate;

	public OrgJobNumber() {
		super();
	}

	public OrgJobNumber(String id){
		super(id);
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public Integer getBzNum() {
		return bzNum;
	}

	public void setBzNum(Integer bzNum) {
		this.bzNum = bzNum;
	}
	
	public Integer getBfNum() {
		return bfNum;
	}

	public void setBfNum(Integer bfNum) {
		this.bfNum = bfNum;
	}
	
	public Integer getTzNum() {
		return tzNum;
	}

	public void setTzNum(Integer tzNum) {
		this.tzNum = tzNum;
	}
	
	public Integer getTfNum() {
		return tfNum;
	}

	public void setTfNum(Integer tfNum) {
		this.tfNum = tfNum;
	}
	
	public Integer getTzfNum() {
		return tzfNum;
	}

	public void setTzfNum(Integer tzfNum) {
		this.tzfNum = tzfNum;
	}
	
	public Integer getTffNum() {
		return tffNum;
	}

	public void setTffNum(Integer tffNum) {
		this.tffNum = tffNum;
	}
	
	public Integer getCzNum() {
		return czNum;
	}

	public void setCzNum(Integer czNum) {
		this.czNum = czNum;
	}
	
	public Integer getCfNum() {
		return cfNum;
	}

	public void setCfNum(Integer cfNum) {
		this.cfNum = cfNum;
	}
	
	public Integer getCzfNum() {
		return czfNum;
	}

	public void setCzfNum(Integer czfNum) {
		this.czfNum = czfNum;
	}
	
	public Integer getCffNum() {
		return cffNum;
	}

	public void setCffNum(Integer cffNum) {
		this.cffNum = cffNum;
	}
	
	public Integer getKzNum() {
		return kzNum;
	}

	public void setKzNum(Integer kzNum) {
		this.kzNum = kzNum;
	}
	
	public Integer getKfNum() {
		return kfNum;
	}

	public void setKfNum(Integer kfNum) {
		this.kfNum = kfNum;
	}
	
	public Integer getKzfNum() {
		return kzfNum;
	}

	public void setKzfNum(Integer kzfNum) {
		this.kzfNum = kzfNum;
	}
	
	public Integer getKffNum() {
		return kffNum;
	}

	public void setKffNum(Integer kffNum) {
		this.kffNum = kffNum;
	}
	
	public Integer getGzNum() {
		return gzNum;
	}

	public void setGzNum(Integer gzNum) {
		this.gzNum = gzNum;
	}
	
	public Integer getGfNum() {
		return gfNum;
	}

	public void setGfNum(Integer gfNum) {
		this.gfNum = gfNum;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}