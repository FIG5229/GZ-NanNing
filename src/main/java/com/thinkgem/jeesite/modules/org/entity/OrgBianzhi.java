/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 单位编制Entity
 * @author eav.liu
 * @version 2019-11-22
 */
public class OrgBianzhi extends DataEntity<OrgBianzhi> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// 机构id
	@ExcelField(title = "批准时间", type = 0, align = 2, sort = 0)
	private Date date;		// 批准时间
	@ExcelField(title = "批准单位", type = 0, align = 2, sort = 1)
	private String unit;		// 批准单位
	@ExcelField(title = "批准文号", type = 0, align = 2, sort = 2)
	private String fileNo;		// 批准文号
	@ExcelField(title = "政法专项编", type = 0, align = 2, sort = 3)
	private Integer zfzxb;		// 政法专项编
	@ExcelField(title = "参公事业编", type = 0, align = 2, sort = 4)
	private Integer cgsyb;		// 参公事业编
	@ExcelField(title = "事业编", type = 0, align = 2, sort = 5)
	private Integer syb;		// 事业编
	@ExcelField(title = "其他编制数", type = 0, align = 2, sort = 6)
	private Integer otherNum;		// 其他编制数
	@ExcelField(title = "全额拨款编制数", type = 0, align = 2, sort = 7)
	private Integer qeNum;		// 全额拨款编制数
	@ExcelField(title = "差额拨款编制数", type = 0, align = 2, sort = 8)
	private Integer ceNum;		// 差额拨款编制数
	@ExcelField(title = "经费自理编制数", type = 0, align = 2, sort = 9)
	private Integer jfzlNum;		// 经费自理编制数
	@ExcelField(title = "编制总数", type = 0, align = 2, sort = 10)
	private Integer sum;		// 编制总数
	@ExcelField(title = "备注", type = 0, align = 2, sort = 11)
	private String remark;		// 备注

	private Date startDate;

	private Date endDate;
	
	public OrgBianzhi() {
		super();
	}

	public OrgBianzhi(String id){
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

	public Integer getZfzxb() {
		return zfzxb;
	}

	public void setZfzxb(Integer zfzxb) {
		this.zfzxb = zfzxb;
	}

	public Integer getCgsyb() {
		return cgsyb;
	}

	public void setCgsyb(Integer cgsyb) {
		this.cgsyb = cgsyb;
	}

	public Integer getSyb() {
		return syb;
	}

	public void setSyb(Integer syb) {
		this.syb = syb;
	}

	public Integer getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(Integer otherNum) {
		this.otherNum = otherNum;
	}
	
	public Integer getQeNum() {
		return qeNum;
	}

	public void setQeNum(Integer qeNum) {
		this.qeNum = qeNum;
	}
	
	public Integer getCeNum() {
		return ceNum;
	}

	public void setCeNum(Integer ceNum) {
		this.ceNum = ceNum;
	}
	
	public Integer getJfzlNum() {
		return jfzlNum;
	}

	public void setJfzlNum(Integer jfzlNum) {
		this.jfzlNum = jfzlNum;
	}
	
	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
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