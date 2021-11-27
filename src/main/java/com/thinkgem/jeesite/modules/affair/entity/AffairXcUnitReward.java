/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 单位集体奖励表Entity
 * @author cecil.li
 * @version 2019-11-02
 */
public class AffairXcUnitReward extends DataEntity<AffairXcUnitReward> {
	
	private static final long serialVersionUID = 1L;
	private String unitName;  // 受奖单位
	@ExcelField(title = "受奖单位名称", type = 0, align = 2, sort = 0)
	private String unit;		// 受奖单位
	private String unitId;		// 受奖单位id
	private String unitCode;   //受奖单位代码
	private String flag;   //建制标志
	@ExcelField(title = "奖励名称", type = 0, align = 2, sort = 1, dictType = "affair_reward_code")
	private String nameCode;   // 奖励名称代码
	@ExcelField(title = "荣誉级别", type = 0, align = 2, sort = 2, dictType = "affair_approval_unitLevel")
	private String level;  // 荣誉称号级别
	@ExcelField(title = "单位警种", type = 0, align = 2, sort = 3, dictType = "affair_unit_police")
	private String unitPolice;   // 单位警种
	private String name;		// 奖励名称
	@ExcelField(title = "批准单位", type = 0, align = 2, sort = 4, dictType = "affair_approval_unit")
	private String approvalUnit;		// 批准单位
	private String approvalUnitId;		// 批准单位id
	private String approvalUnitLevel;   // 批准单位级别
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 5)
	private Date date;		// 批准日期
	@ExcelField(title = "批准文号", type = 0, align = 2, sort = 6)
	private String fileNo;		// 批准文号
	@ExcelField(title = "批准文件名称", type = 0, align = 2, sort = 7)
	private String fileName;  //批准文件名称
	@ExcelField(title = "批准机关类别", type = 0, align = 2, sort = 8, dictType = "affair_approval_unitType")
	private String approvalUnitType;  // 批准机关类别
	@ExcelField(title = "事迹材料", type = 0, align = 2, sort = 10)
	private String deedsMaterial;   // 事迹材料
	@ExcelField(title = "撤销奖励日期", type = 0, align = 2, sort = 9)
	private Date reDate;  // 撤销奖励日期
	@ExcelField(title = "备注", type = 0, align = 2, sort = 11)
	private String remark;		// 备注
	private String reUnit;  // 撤销奖励批准单位
	private String reUnitId;  //  撤销奖励批准单位id
	private String filePath;		// 相关文件
	private String opinion;		// 审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String rewardType;		// 表彰奖励类型
	private String status;		// 审核状态
	private String type;   //奖励类别
	private String jlType;    //奖励情况
	private String wjName;    //文件名称
	//供查询时间区间使用
	private Date startDate;
	//供查询时间区间使用
	private Date endDate;
	private String pushType;    //推送状态

	private String collective;   //获奖集体
	private String nameCodeType;
	private String dateType;
	private Integer year;
	private String month;
	private String label;
	private String typeFlag;

	public AffairXcUnitReward() {
		super();
	}

	public AffairXcUnitReward(String id){
		super(id);
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
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getApprovalUnit() {
		return approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getApprovalUnitId() {
		return approvalUnitId;
	}

	public void setApprovalUnitId(String approvalUnitId) {
		this.approvalUnitId = approvalUnitId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
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
	
	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJlType() {
		return jlType;
	}

	public void setJlType(String jlType) {
		this.jlType = jlType;
	}

	public String getWjName() {
		return wjName;
	}

	public void setWjName(String wjName) {
		this.wjName = wjName;
	}

	public String getCollective() {
		return collective;
	}

	public void setCollective(String collective) {
		this.collective = collective;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUnitPolice() {
		return unitPolice;
	}

	public void setUnitPolice(String unitPolice) {
		this.unitPolice = unitPolice;
	}

	public String getApprovalUnitLevel() {
		return approvalUnitLevel;
	}

	public void setApprovalUnitLevel(String approvalUnitLevel) {
		this.approvalUnitLevel = approvalUnitLevel;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getApprovalUnitType() {
		return approvalUnitType;
	}

	public void setApprovalUnitType(String approvalUnitType) {
		this.approvalUnitType = approvalUnitType;
	}

	public String getDeedsMaterial() {
		return deedsMaterial;
	}

	public void setDeedsMaterial(String deedsMaterial) {
		this.deedsMaterial = deedsMaterial;
	}

	public Date getReDate() {
		return reDate;
	}

	public void setReDate(Date reDate) {
		this.reDate = reDate;
	}

	public String getReUnit() {
		return reUnit;
	}

	public void setReUnit(String reUnit) {
		this.reUnit = reUnit;
	}

	public String getReUnitId() {
		return reUnitId;
	}

	public void setReUnitId(String reUnitId) {
		this.reUnitId = reUnitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getNameCodeType() {
		return nameCodeType;
	}

	public void setNameCodeType(String nameCodeType) {
		this.nameCodeType = nameCodeType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
}