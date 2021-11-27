/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 单位奖励信息Entity
 * @author eav.liu
 * @version 2019-11-22
 */
public class OrgReward extends DataEntity<OrgReward> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// 机构id
	@ExcelField(title = "受奖单位名称", type = 0, align = 2, sort = 0)
	private String winUnitName;		// 受奖单位名称
	@ExcelField(title = "受奖单位代码", type = 0, align = 2, sort = 1)
	private String winUnitCode;		// 受奖单位代码
	@ExcelField(title = "建制标志", type = 0, align = 2, sort = 2)
	private String sign;		// 建制标志
	@ExcelField(title = "奖励名称代码", type = 0, align = 2, sort = 3)
	private String rewardNameCode;		// 奖励名称代码
	@ExcelField(title = "荣誉称号级别", type = 0, align = 2, sort = 4)
	private String level;		// 荣誉称号级别
	@ExcelField(title = "单位警种", type = 0, align = 2, sort = 5)
	private String policeType;		// 单位警种
	@ExcelField(title = "奖励名称", type = 0, align = 2, sort = 6)
	private String rewardName;		// 奖励名称
	@ExcelField(title = "批准单位", type = 0, align = 2, sort = 7)
	private String approvalUnit;		// 批准单位
	@ExcelField(title = "批准单位级别", type = 0, align = 2, sort = 8, dictType="org_approval_unit_level")
	private String approvalUnitLevel;		// 批准单位级别
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 9)
	private Date approvalDate;		// 批准日期
	@ExcelField(title = "批准文号", type = 0, align = 2, sort = 10)
	private String fileNo;		// 批准文号
	@ExcelField(title = "批准文件名称", type = 0, align = 2, sort = 11)
	private String fileName;		// 批准文件名称
	@ExcelField(title = "批准机关类别", type = 0, align = 2, sort = 12, dictType="org_approval_org_type")
	private String approvalOrgType;		// 批准机关类别
	@ExcelField(title = "撤销奖励日期", type = 0, align = 2, sort = 13)
	private Date cancelDate;		// 撤销奖励日期
	@ExcelField(title = "撤销奖励批准单位", type = 0, align = 2, sort = 15)
	private String cancelApprovalUnit;		// 撤销奖励批准单位
	@ExcelField(title = "备注", type = 0, align = 2, sort = 14)
	private String remark;		// 备注
	@ExcelField(title = "事迹材料", type = 0, align = 2, sort = 13)
	private String material;		// 事迹材料

	private String rewardType;		//奖励类型


	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public OrgReward() {
		super();
	}

	public OrgReward(String id){
		super(id);
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getWinUnitName() {
		return winUnitName;
	}

	public void setWinUnitName(String winUnitName) {
		this.winUnitName = winUnitName;
	}
	
	public String getWinUnitCode() {
		return winUnitCode;
	}

	public void setWinUnitCode(String winUnitCode) {
		this.winUnitCode = winUnitCode;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getRewardNameCode() {
		return rewardNameCode;
	}

	public void setRewardNameCode(String rewardNameCode) {
		this.rewardNameCode = rewardNameCode;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getPoliceType() {
		return policeType;
	}

	public void setPoliceType(String policeType) {
		this.policeType = policeType;
	}
	
	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	
	public String getApprovalUnit() {
		return approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getApprovalUnitLevel() {
		return approvalUnitLevel;
	}

	public void setApprovalUnitLevel(String approvalUnitLevel) {
		this.approvalUnitLevel = approvalUnitLevel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getApprovalOrgType() {
		return approvalOrgType;
	}

	public void setApprovalOrgType(String approvalOrgType) {
		this.approvalOrgType = approvalOrgType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	public String getCancelApprovalUnit() {
		return cancelApprovalUnit;
	}

	public void setCancelApprovalUnit(String cancelApprovalUnit) {
		this.cancelApprovalUnit = cancelApprovalUnit;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
}