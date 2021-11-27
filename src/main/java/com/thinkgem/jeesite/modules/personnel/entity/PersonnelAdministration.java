/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 行政职务信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelAdministration extends DataEntity<PersonnelAdministration> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "职务名称代码", type = 0, align = 2, sort = 1)
	private String code;		// 职务名称代码
	@ExcelField(title = "职务名称", type = 0, align = 2, sort = 2)
	private String name;		// 职务名称
	@ExcelField(title = "任职状态", type = 0, align = 2, sort = 3, dictType="personnel_rztype")
	private String status;		// 任职状态
	@ExcelField(title = "职务类别", type = 0, align = 2, sort = 4, dictType="personnel_zwlb")
	private String jobType;		// 职务类别
	@ExcelField(title = "职务层次", type = 0, align = 2, sort = 5, dictType="personnel_zwcc")
	private String level;		// 职务层次
	@ExcelField(title = "多职务主次序号", type = 0, align = 2, sort = 6)
	private String sequenceNo;		// 多职务主次序号
	@ExcelField(title = "连续任该职起始日期", type = 0, align = 2, sort = 7)
	private Date startDate;		// 连续任该职起始日期
	@ExcelField(title = "职务说明", type = 0, align = 2, sort = 8)
	private String explain;		// 职务说明
	@ExcelField(title = "批准机关名称", type = 0, align = 2, sort = 9)
	private String approvalOrgName;		// 批准机关名称
	@ExcelField(title = "批准机关代码", type = 0, align = 2, sort = 10)
	private String approvalOrgCode;		// 批准机关代码
	@ExcelField(title = "批准文号", type = 0, align = 2, sort = 11)
	private String fileNo;		// 批准文号
	@ExcelField(title = "任职日期", type = 0, align = 2, sort = 12)
	private Date workDate;		// 任职日期
	@ExcelField(title = "任职变动类别", type = 0, align = 2, sort = 13, dictType="personnel_rzbdlb")
	private String changeType;		// 任职变动类别
	@ExcelField(title = "任职方式", type = 0, align = 2, sort = 14, dictType="personnel_rzfs")
	private String method;		// 任职方式
	@ExcelField(title = "是否班子成员", type = 0, align = 2, sort = 15, dictType="yes_no")
	private String isMember;		// 是否班子成员
	@ExcelField(title = "班子成员类别", type = 0, align = 2, sort = 16, dictType="personnel_bzcylb")
	private String memberType;		// 班子成员类别
	@ExcelField(title = "是否破格提拔", type = 0, align = 2, sort = 17, dictType="yes_no")
	private String isBreakRule;		// 是否破格提拔
	@ExcelField(title = "分管工作", type = 0, align = 2, sort = 18)
	private String fgWork;		// 分管工作
	@ExcelField(title = "岗位类别", type = 0, align = 2, sort = 19, dictType="personnel_gwlb")
	private String jobCategory;		// 岗位类别
	@ExcelField(title = "集体内排列顺序", type = 0, align = 2, sort = 20)
	private String setOrder;		// 集体内排列顺序
	@ExcelField(title = "股级任职时间", type = 0, align = 2, sort = 21)
	private Date gjrzDate;		// 股级任职时间
	@ExcelField(title = "股级", type = 0, align = 2, sort = 22, dictType="personnel_guji")
	private String stockLevel;		// 股级
	@ExcelField(title = "任职机构名称", type = 0, align = 2, sort = 23)
	private String rzjgName;		// 任职机构名称
	@ExcelField(title = "任职机构代码", type = 0, align = 2, sort = 24)
	private String rzjgCode;		// 任职机构代码
	@ExcelField(title = "任职机构所在政区", type = 0, align = 2, sort = 25)
	private String rzjgSteer;		// 任职机构所在政区
	@ExcelField(title = "任职机构隶属关系", type = 0, align = 2, sort = 26)
	private String rzjgAffiliation;		// 任职机构隶属关系
	@ExcelField(title = "任职机构名称类别", type = 0, align = 2, sort = 27)
	private String rzjgNameType;		// 任职机构名称类别
	@ExcelField(title = "任职机构性质类别", type = 0, align = 2, sort = 28, dictType="personnel_rzjgmclb")
	private String rzjgNatureType;		// 任职机构性质类别
	@ExcelField(title = "任职机构所属行业", type = 0, align = 2, sort = 29)
	private String rzjgIndustry;		// 任职机构所属行业
	@ExcelField(title = "任职机构级别", type = 0, align = 2, sort = 30)
	private String rzjgLevel;		// 任职机构级别
	@ExcelField(title = "批准免职机关名称", type = 0, align = 2, sort = 31)
	private String pzmzjgName;		// 批准免职机关名称
	@ExcelField(title = "批准免职机关代码", type = 0, align = 2, sort = 32)
	private String pzmzjgCode;		// 批准免职机关代码
	@ExcelField(title = "批准免职文号", type = 0, align = 2, sort = 33)
	private String pzmzNo;		// 批准免职文号
	@ExcelField(title = "免职原因类别", type = 0, align = 2, sort = 34)
	private String mzyyType;		// 免职原因类别
	@ExcelField(title = "批准免职日期", type = 0, align = 2, sort = 35)
	private Date pzmzDate;		// 批准免职日期
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String personName;  // 姓名

	private Date beginDate;
	private Date finishDate;

	public PersonnelAdministration() {
		super();
	}

	public PersonnelAdministration(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public String getApprovalOrgName() {
		return approvalOrgName;
	}

	public void setApprovalOrgName(String approvalOrgName) {
		this.approvalOrgName = approvalOrgName;
	}
	
	public String getApprovalOrgCode() {
		return approvalOrgCode;
	}

	public void setApprovalOrgCode(String approvalOrgCode) {
		this.approvalOrgCode = approvalOrgCode;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getIsMember() {
		return isMember;
	}

	public void setIsMember(String isMember) {
		this.isMember = isMember;
	}
	
	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
	public String getIsBreakRule() {
		return isBreakRule;
	}

	public void setIsBreakRule(String isBreakRule) {
		this.isBreakRule = isBreakRule;
	}
	
	public String getFgWork() {
		return fgWork;
	}

	public void setFgWork(String fgWork) {
		this.fgWork = fgWork;
	}
	
	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}
	
	public String getSetOrder() {
		return setOrder;
	}

	public void setSetOrder(String setOrder) {
		this.setOrder = setOrder;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getGjrzDate() {
		return gjrzDate;
	}

	public void setGjrzDate(Date gjrzDate) {
		this.gjrzDate = gjrzDate;
	}
	
	public String getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(String stockLevel) {
		this.stockLevel = stockLevel;
	}
	
	public String getRzjgName() {
		return rzjgName;
	}

	public void setRzjgName(String rzjgName) {
		this.rzjgName = rzjgName;
	}
	
	public String getRzjgCode() {
		return rzjgCode;
	}

	public void setRzjgCode(String rzjgCode) {
		this.rzjgCode = rzjgCode;
	}
	
	public String getRzjgSteer() {
		return rzjgSteer;
	}

	public void setRzjgSteer(String rzjgSteer) {
		this.rzjgSteer = rzjgSteer;
	}
	
	public String getRzjgAffiliation() {
		return rzjgAffiliation;
	}

	public void setRzjgAffiliation(String rzjgAffiliation) {
		this.rzjgAffiliation = rzjgAffiliation;
	}
	
	public String getRzjgNameType() {
		return rzjgNameType;
	}

	public void setRzjgNameType(String rzjgNameType) {
		this.rzjgNameType = rzjgNameType;
	}
	
	public String getRzjgNatureType() {
		return rzjgNatureType;
	}

	public void setRzjgNatureType(String rzjgNatureType) {
		this.rzjgNatureType = rzjgNatureType;
	}
	
	public String getRzjgIndustry() {
		return rzjgIndustry;
	}

	public void setRzjgIndustry(String rzjgIndustry) {
		this.rzjgIndustry = rzjgIndustry;
	}
	
	public String getRzjgLevel() {
		return rzjgLevel;
	}

	public void setRzjgLevel(String rzjgLevel) {
		this.rzjgLevel = rzjgLevel;
	}
	
	public String getPzmzjgName() {
		return pzmzjgName;
	}

	public void setPzmzjgName(String pzmzjgName) {
		this.pzmzjgName = pzmzjgName;
	}
	
	public String getPzmzjgCode() {
		return pzmzjgCode;
	}

	public void setPzmzjgCode(String pzmzjgCode) {
		this.pzmzjgCode = pzmzjgCode;
	}
	
	public String getPzmzNo() {
		return pzmzNo;
	}

	public void setPzmzNo(String pzmzNo) {
		this.pzmzNo = pzmzNo;
	}
	
	public String getMzyyType() {
		return mzyyType;
	}

	public void setMzyyType(String mzyyType) {
		this.mzyyType = mzyyType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPzmzDate() {
		return pzmzDate;
	}

	public void setPzmzDate(Date pzmzDate) {
		this.pzmzDate = pzmzDate;
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
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}