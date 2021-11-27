/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 干部档案材料收集Entity
 * @author mason.xv
 * @version 2019-11-04
 */
public class AffairMaterial extends DataEntity<AffairMaterial> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "材料名称", type = 0, align = 2, sort = 1)
	private String material;		// 材料名称
	@ExcelField(title = "材料份数", type = 0, align = 2, sort = 2)
	private Integer num;		// 材料份数
	@ExcelField(title = "形成材料单位", type = 0, align = 2, sort = 3)
	private String materialUnit;		// 形成材料单位
	@ExcelField(title = "形成材料时间", type = 0, align = 2, sort = 4)
	private Date formDate;		// 形成材料时间
	@ExcelField(title = "移交时间", type = 0, align = 2, sort = 5)
	private Date transferDate;		// 移交时间
	@ExcelField(title = "送交人", type = 0, align = 2, sort = 6)
	private String deliver;		// 送交人
	@ExcelField(title = "接收人", type = 0, align = 2, sort = 7)
	private String receiver;		// 接收人


	private String remark;		// 备注
	private String materialUnitId;		// 形成材料单位机构id
	private String idNumber;		// 身份证号
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginTransferDate;		// 开始 移交时间
	private Date endTransferDate;		// 结束 移交时间
	
	public AffairMaterial() {
		super();
	}

	public AffairMaterial(String id){
		super(id);
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
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}
	
	public String getMaterialUnitId() {
		return materialUnitId;
	}

	public void setMaterialUnitId(String materialUnitId) {
		this.materialUnitId = materialUnitId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFormDate() {
		return formDate;
	}

	public void setFormDate(Date formDate) {
		this.formDate = formDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	
	public String getDeliver() {
		return deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
	
	public Date getBeginTransferDate() {
		return beginTransferDate;
	}

	public void setBeginTransferDate(Date beginTransferDate) {
		this.beginTransferDate = beginTransferDate;
	}
	
	public Date getEndTransferDate() {
		return endTransferDate;
	}

	public void setEndTransferDate(Date endTransferDate) {
		this.endTransferDate = endTransferDate;
	}
		
}