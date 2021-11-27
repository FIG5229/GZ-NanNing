/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 个人奖励Entity
 * @author cecil.li
 * @version 2019-11-02
 */
public class AffairPersonalReward extends DataEntity<AffairPersonalReward> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String approvalUnit;
	private String approvalUnitId;
	@ExcelField(title = "性别", type = 0, align = 2, sort = 2, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 4)
	private Date birthday;  // 出生年月
	@ExcelField(title = "民族", type = 0, align = 2, sort = 3, dictType = "nation")
	private String nation;  //民族
	//@ExcelField(title = "奖励名称代码", type = 0, align = 2, sort = 5)
	private String nameCode;  // 奖励名称代码
	@ExcelField(title = "批准文件文号", type = 0, align = 2, sort = 5)
	private String fileNo;   // 批准文件文号
	@ExcelField(title = "批准文件名称", type = 0, align = 2, sort = 6)
	private String fileName;   // 批准文件名称
	@ExcelField(title = "奖励名称", type = 0, align = 2, sort = 7, dictType = "affair_personnel_rewardCode")
	private String rewardName;  // 奖励名称
	//12.18 调整为与批准机关层次字典一样
	//@ExcelField(title = "荣誉级别", type = 0, align = 2, sort = 8, dictType = "affair_chenghao_level")
	@ExcelField(title = "荣誉级别", type = 0, align = 2, sort = 8, dictType = "affair_approval_unitLevel")
	private String level;  // 荣誉称号级别
	@ExcelField(title = "受奖励时职务", type = 0, align = 2, sort = 9)
	private String job;  // 受奖励时职务
	//@ExcelField(title = "受奖励时职务层次", type = 0, align = 2, sort = 10)
	private String jobLevel;  // 受奖励时职务层次
	@ExcelField(title = "追授标志", type = 0, align = 2, sort = 10, dictType = "yes_no")
	private String flag;  // 追授标志
	//@ExcelField(title = "享受待遇类别", type = 0, align = 2, sort = 13)
	private String daiyuType;   // 享受待遇类别
	//@ExcelField(title = "奖励原因", type = 0, align = 2, sort = 14)
	private String yuanyin;   // 奖励原因
	@ExcelField(title = "批准机关名称", type = 0, align = 2, sort = 11)
	private String unit;		// 批准机关名称
	private String unitId;		// 批准机关名称id
	//@ExcelField(title = "批准机关代码", type = 0, align = 2, sort = 16)
	private String unitCode;  // 批准机关代码
	@ExcelField(title = "批准机关类别", type = 0, align = 2, sort = 12, dictType = "affair_approval_unitType")
	private String unitType;  // 批准机关类别
	@ExcelField(title = "批准机关层次", type = 0, align = 2, sort = 13, dictType = "affair_approval_unitLevel")
	private String unitLevel;   // 批准机关层次
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 14)
	private Date date;		// 批准日期
	@ExcelField(title = "事迹材料", type = 0, align = 2, sort = 15)
	private String deedsMaterial;   // 事迹材料
	@ExcelField(title = "撤销标志", type = 0, align = 2, sort = 16, dictType = "yes_no")
	private String chexiaoFlag;  // 撤销标志
	@ExcelField(title = "撤销日期", type = 0, align = 2, sort = 17)
	private Date reDate;  // 撤销日期
	@ExcelField(title = "撤销原因", type = 0, align = 2, sort = 18)
	private String cxYuanyin;  // 撤销原因
	private String type;		//奖励类别



	private String idNumber;		// 身份证号
	private String remark;		// 备注
	private String filePath;		// 相关文件
	private String opinion;		// 审核意见
	private String status;		// 审核状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	private String pushType;       //推送状态

	private String month;
	private Integer year;
	private String dateType;
	private String nameCodeType;
	private String typeFlag;

	private String inputUnit;		//手动输入批准机关名称

	private String inputReward;		//手动输入奖励名称
	private String isPeople;

	public String getInputReward() {
		return inputReward;
	}

	public void setInputReward(String inputReward) {
		this.inputReward = inputReward;
	}

	public String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AffairPersonalReward() {
		super();
	}

	public AffairPersonalReward(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
	
	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
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

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDaiyuType() {
		return daiyuType;
	}

	public void setDaiyuType(String daiyuType) {
		this.daiyuType = daiyuType;
	}

	public String getYuanyin() {
		return yuanyin;
	}

	public void setYuanyin(String yuanyin) {
		this.yuanyin = yuanyin;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitLevel() {
		return unitLevel;
	}

	public void setUnitLevel(String unitLevel) {
		this.unitLevel = unitLevel;
	}

	public String getDeedsMaterial() {
		return deedsMaterial;
	}

	public void setDeedsMaterial(String deedsMaterial) {
		this.deedsMaterial = deedsMaterial;
	}

	public String getChexiaoFlag() {
		return chexiaoFlag;
	}

	public void setChexiaoFlag(String chexiaoFlag) {
		this.chexiaoFlag = chexiaoFlag;
	}

	public Date getReDate() {
		return reDate;
	}

	public void setReDate(Date reDate) {
		this.reDate = reDate;
	}

	public String getCxYuanyin() {
		return cxYuanyin;
	}

	public void setCxYuanyin(String cxYuanyin) {
		this.cxYuanyin = cxYuanyin;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNameCodeType() {
		return nameCodeType;
	}

	public void setNameCodeType(String nameCodeType) {
		this.nameCodeType = nameCodeType;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	public String getIsPeople() {
		return isPeople;
	}

	public void setIsPeople(String isPeople) {
		this.isPeople = isPeople;
	}
}