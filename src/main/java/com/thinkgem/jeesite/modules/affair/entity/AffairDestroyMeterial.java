/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 销毁清册Entity
 * @author mason.xv
 * @version 2019-11-05
 */
public class AffairDestroyMeterial extends DataEntity<AffairDestroyMeterial> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "材料名称", type = 0, align = 2, sort = 1)
	private String materialName;		// 材料名称
	@ExcelField(title = "材料形成时间", type = 0, align = 2, sort = 2)
	private Date formDate;		// 材料形成时间
	@ExcelField(title = "份数", type = 0, align = 2, sort = 3)
	private Integer num;		// 份数
	@ExcelField(title = "销毁原因", type = 0, align = 2, sort = 4)
	private String reason;		// 销毁原因
	@ExcelField(title = "领导审批", type = 0, align = 2, sort = 5)
	private String approval;		// 领导审批
	@ExcelField(title = "销毁时间", type = 0, align = 2, sort = 6)
	private Date destroyDate;		// 销毁时间
	@ExcelField(title = "备注", type = 0, align = 2, sort = 7)
	private String remark;		// 备注

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginFormDate;		// 开始 材料形成时间
	private Date endFormDate;		// 结束 材料形成时间
	private Date beginDestroyDate;		// 开始 销毁时间
	private Date endDestroyDate;		// 结束 销毁时间
	
	public AffairDestroyMeterial() {
		super();
	}

	public AffairDestroyMeterial(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFormDate() {
		return formDate;
	}

	public void setFormDate(Date formDate) {
		this.formDate = formDate;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDestroyDate() {
		return destroyDate;
	}

	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
	
	public Date getBeginFormDate() {
		return beginFormDate;
	}

	public void setBeginFormDate(Date beginFormDate) {
		this.beginFormDate = beginFormDate;
	}
	
	public Date getEndFormDate() {
		return endFormDate;
	}

	public void setEndFormDate(Date endFormDate) {
		this.endFormDate = endFormDate;
	}
		
	public Date getBeginDestroyDate() {
		return beginDestroyDate;
	}

	public void setBeginDestroyDate(Date beginDestroyDate) {
		this.beginDestroyDate = beginDestroyDate;
	}
	
	public Date getEndDestroyDate() {
		return endDestroyDate;
	}

	public void setEndDestroyDate(Date endDestroyDate) {
		this.endDestroyDate = endDestroyDate;
	}
		
}