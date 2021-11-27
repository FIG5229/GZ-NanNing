/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 档案目录Entity
 * @author mason.xv
 * @version 2019-11-27
 */
public class AffairArchiveDirectory extends DataEntity<AffairArchiveDirectory> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "材料名称", type = 0, align = 2, sort = 0)
	private String clName;		// 材料名称
	@ExcelField(title = "材料类型", type = 0, align = 2, sort = 1, dictType="material_type")
	private String clType;		// 材料类型
	@ExcelField(title = "材料制成时间", type = 0, align = 2, sort = 2)
	private Date clTime;		// 材料制成时间
	@ExcelField(title = "页码", type = 0, align = 2, sort = 3)
	private String pageNumber;		// 页码
	@ExcelField(title = "备注", type = 0, align = 2, sort = 4)
	private String remark;		// 备注


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginClTime;		// 开始 材料制成时间
	private Date endClTime;		// 结束 材料制成时间
	
	public AffairArchiveDirectory() {
		super();
	}

	public AffairArchiveDirectory(String id){
		super(id);
	}

	public String getClName() {
		return clName;
	}

	public void setClName(String clName) {
		this.clName = clName;
	}
	
	public String getClType() {
		return clType;
	}

	public void setClType(String clType) {
		this.clType = clType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd ")
	public Date getClTime() {
		return clTime;
	}

	public void setClTime(Date clTime) {
		this.clTime = clTime;
	}
	
	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public Date getBeginClTime() {
		return beginClTime;
	}

	public void setBeginClTime(Date beginClTime) {
		this.beginClTime = beginClTime;
	}
	
	public Date getEndClTime() {
		return endClTime;
	}

	public void setEndClTime(Date endClTime) {
		this.endClTime = endClTime;
	}
		
}