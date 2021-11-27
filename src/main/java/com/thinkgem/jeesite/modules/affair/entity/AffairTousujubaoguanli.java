/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 投诉举报Entity
 * @author cecil.li
 * @version 2019-11-07
 */
public class AffairTousujubaoguanli extends DataEntity<AffairTousujubaoguanli> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "反映人", type = 0, align = 2, sort = 0)
	private String informer;		// 反映人
	@ExcelField(title = "反映人身份证号", type = 0, align = 2, sort = 1)
	private String informerIdNumber;		// 反映人身份证号码
	@ExcelField(title = "反映人单位", type = 0, align = 2, sort = 2)
	private String informerUnit;		// 举报人单位
	private String informerUnitId;		// 举报人单位id
	@ExcelField(title = "来源渠道", type = 0, align = 2, sort = 3, dictType="affair_source")
	private String source;  //来源渠道
	@ExcelField(title = "线索形式", type = 0, align = 2, sort = 5, dictType="affair_clue")
	private String clue;   //线索形式
	@ExcelField(title = "媒介形式", type = 0, align = 2, sort = 4, dictType="affair_tousu")
	private String complaintWay;		// 媒介形式
	@ExcelField(title = "被反映人", type = 0, align = 2, sort = 6)
	private String repoter;		// 被反映人
	@ExcelField(title = "被反映人身份证号", type = 0, align = 2, sort = 7)
	private String repoterIdNumber;		// 被反映人身份证号
	@ExcelField(title = "被反映人单位", type = 0, align = 2, sort = 8)
	private String repoterUnit;		// 被举报人单位
	private String repoterUnitId;		// 被举报人单位id
	@ExcelField(title = "其他单位", type = 0, align = 2, sort = 9)
	private String otherRepoterUnit;	//	被举报人单位（手填的其他单位）
	@ExcelField(title = "问题性质", type = 0, align = 2, sort = 10, dictType="affair_qtType")
	private String questionType;		// 问题类型
	@ExcelField(title = "违反党政纪行为", type = 0, align = 2, sort = 11, dictType="affair_zjType")
	private String zjType;
	@ExcelField(title = "涉法行为", type = 0, align = 2, sort = 12, dictType="affair_sfType")
	private String sfType;
	@ExcelField(title = "违反警纪行为", type = 0, align = 2, sort = 13, dictType="affair_jjType")
	private String jjType;
	@ExcelField(title = "简要情况", type = 0, align = 2, sort = 14)
	private String situation;		// 简要情况
	@ExcelField(title = "收到单位", type = 0, align = 2, sort = 15, dictType="affair_xfjb_unit")
	private String sdUnit;        //收到单位
	@ExcelField(title = "收到时间", type = 0, align = 2, sort = 16)
	private Date receiveTime;		// 收到时间
	@ExcelField(title = "是否重复", type = 0, align = 2, sort = 17, dictType="yes_no")
	private String isrepeat;		// 是否重复
	@ExcelField(title = "查处单位", type = 0, align = 2, sort = 18, dictType="affair_xfjb_unit")
	private String ccUnit;      //查处单位
	@ExcelField(title = "查办状态", type = 0, align = 2, sort = 19, dictType="affair_cbtype")
	private String ischeck;		// 查办状态
	private String subOption;	//查办状态的子选项
	@ExcelField(title = "查处结果", type = 0, align = 2, sort = 20, dictType="affair_bjtype_sub")
	private String bjType;  //办结子选项(查处结果)
	@ExcelField(title = "查办结果", type = 0, align = 2, sort = 21)
	private String result;		// 查办结果（待后期查办结束后，在系统中“查办状态”中填入查办结果）
	@ExcelField(title = "是否纪律处分", type = 0, align = 2, sort = 22, dictType="yes_no")
	private String isdispose;		// 是否纪律处分
	@ExcelField(title = "处理情况", type = 0, align = 2, sort = 23, dictType="affair_no_punish_type")
	private String noPunish;	//处理情况，无纪律处分时填写 1：谈话提醒 2：批评教育 3：责令检查 4：诫勉谈话 5：其他方式
	@ExcelField(title = "其他方式", type = 0, align = 2, sort = 24)
	private String otherMethod;		// 其他方式（当是否纪律处分选择了否，noPunish选择其他方式时，手动填写otherMethod）
	@ExcelField(title = "转办状态", type = 0, align = 2, sort = 25, dictType="zb_type")
	private String zbStatus; //转办状态
	@ExcelField(title = "转办单位", type = 0, align = 2, sort = 26)
	private String zbUnit; //转办单位
	private String zbUnitId; //转办单位id
	@ExcelField(title = "转办意见", type = 0, align = 2, sort = 27)
	private String zbOption; //转办意见
	private String forwardUnit;		// 批转单位
	private String forwardUnitId;		// 批转单位id
	private Date entryTime;		// 录入时间

	private String approvalStatus;		// 审核状态
	private String revierwe;		// 审核人
	private String auditOpinion;		// 审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String forwardType ;       //审批形式

	private boolean hasAuth;        //权限管理

	private Date startEntryDate;
	private Date endEntryDate;
	private String sort;	//排序 1：升序 2：降序 3：默认
	private String isBanJie;	//是否办结 1：是 0：否
	private String juCheckMan;
	private String chuCheckMan;
	private String submitMan;
	private String juCheckId;
	private String chuCheckId;
	private String submitId;

	private String unit;
	private String unitId;

	private	String annex;     //附件
	private Date startYear;

	private String dateType;

	private Integer year;

	private String dateStart;

	private String dateEnd;

	private String month;

	private String checkType;



	public boolean isHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth) {
		this.hasAuth = hasAuth;
	}

	private Date startDate;
	private Date endDate;
	
	public AffairTousujubaoguanli() {
		super();
	}

	public AffairTousujubaoguanli(String id){
		super(id);
	}

	public String getInformer() {
		return informer;
	}

	public void setInformer(String informer) {
		this.informer = informer;
	}
	
	public String getInformerIdNumber() {
		return informerIdNumber;
	}

	public String getForwardType() {
		return forwardType;
	}

	public void setForwardType(String forwardType) {
		this.forwardType = forwardType;
	}

	public void setInformerIdNumber(String informerIdNumber) {
		this.informerIdNumber = informerIdNumber;
	}
	
	public String getInformerUnit() {
		return informerUnit;
	}

	public void setInformerUnit(String informerUnit) {
		this.informerUnit = informerUnit;
	}
	
	public String getInformerUnitId() {
		return informerUnitId;
	}

	public void setInformerUnitId(String informerUnitId) {
		this.informerUnitId = informerUnitId;
	}
	
	public String getRepoter() {
		return repoter;
	}

	public void setRepoter(String repoter) {
		this.repoter = repoter;
	}
	
	public String getRepoterIdNumber() {
		return repoterIdNumber;
	}

	public void setRepoterIdNumber(String repoterIdNumber) {
		this.repoterIdNumber = repoterIdNumber;
	}
	
	public String getRepoterUnit() {
		return repoterUnit;
	}

	public void setRepoterUnit(String repoterUnit) {
		this.repoterUnit = repoterUnit;
	}
	
	public String getRepoterUnitId() {
		return repoterUnitId;
	}

	public void setRepoterUnitId(String repoterUnitId) {
		this.repoterUnitId = repoterUnitId;
	}
	
	public String getComplaintWay() {
		return complaintWay;
	}

	public void setComplaintWay(String complaintWay) {
		this.complaintWay = complaintWay;
	}
	
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	public String getForwardUnit() {
		return forwardUnit;
	}

	public void setForwardUnit(String forwardUnit) {
		this.forwardUnit = forwardUnit;
	}
	
	public String getForwardUnitId() {
		return forwardUnitId;
	}

	public void setForwardUnitId(String forwardUnitId) {
		this.forwardUnitId = forwardUnitId;
	}
	
	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	public String getIsdispose() {
		return isdispose;
	}

	public void setIsdispose(String isdispose) {
		this.isdispose = isdispose;
	}
	
	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getIsrepeat() {
		return isrepeat;
	}

	public void setIsrepeat(String isrepeat) {
		this.isrepeat = isrepeat;
	}
	
	public String getRevierwe() {
		return revierwe;
	}

	public void setRevierwe(String revierwe) {
		this.revierwe = revierwe;
	}
	
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartEntryDate() {
		return startEntryDate;
	}

	public void setStartEntryDate(Date startEntryDate) {
		this.startEntryDate = startEntryDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndEntryDate() {
		return endEntryDate;
	}

	public void setEndEntryDate(Date endEntryDate) {
		this.endEntryDate = endEntryDate;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSubOption() {
		return subOption;
	}

	public void setSubOption(String subOption) {
		this.subOption = subOption;
	}


	public String getBjType() {
		return bjType;
	}

	public void setBjType(String bjType) {
		this.bjType = bjType;
	}

	public String getZbStatus() {
		return zbStatus;
	}

	public void setZbStatus(String zbStatus) {
		this.zbStatus = zbStatus;
	}

	public String getZbUnit() {
		return zbUnit;
	}

	public void setZbUnit(String zbUnit) {
		this.zbUnit = zbUnit;
	}

	public String getZbOption() {
		return zbOption;
	}

	public void setZbOption(String zbOption) {
		this.zbOption = zbOption;
	}

	public String getZbUnitId() {
		return zbUnitId;
	}

	public void setZbUnitId(String zbUnitId) {
		this.zbUnitId = zbUnitId;
	}

	public String getNoPunish() {
		return noPunish;
	}

	public void setNoPunish(String noPunish) {
		this.noPunish = noPunish;
	}

	public String getOtherMethod() {
		return otherMethod;
	}

	public void setOtherMethod(String otherMethod) {
		this.otherMethod = otherMethod;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOtherRepoterUnit() {
		return otherRepoterUnit;
	}

	public void setOtherRepoterUnit(String otherRepoterUnit) {
		this.otherRepoterUnit = otherRepoterUnit;
	}

	public String getIsBanJie() {
		return isBanJie;
	}

	public void setIsBanJie(String isBanJie) {
		this.isBanJie = isBanJie;
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

	public String getSdUnit() {
		return sdUnit;
	}

	public void setSdUnit(String sdUnit) {
		this.sdUnit = sdUnit;
	}

	public String getCcUnit() {
		return ccUnit;
	}

	public void setCcUnit(String ccUnit) {
		this.ccUnit = ccUnit;
	}

	public String getJuCheckMan() {
		return juCheckMan;
	}

	public void setJuCheckMan(String juCheckMan) {
		this.juCheckMan = juCheckMan;
	}

	public String getChuCheckMan() {
		return chuCheckMan;
	}

	public void setChuCheckMan(String chuCheckMan) {
		this.chuCheckMan = chuCheckMan;
	}

	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}

	public String getJuCheckId() {
		return juCheckId;
	}

	public void setJuCheckId(String juCheckId) {
		this.juCheckId = juCheckId;
	}

	public String getChuCheckId() {
		return chuCheckId;
	}

	public void setChuCheckId(String chuCheckId) {
		this.chuCheckId = chuCheckId;
	}

	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getClue() {
		return clue;
	}

	public void setClue(String clue) {
		this.clue = clue;
	}

	public String getZjType() {
		return zjType;
	}

	public void setZjType(String zjType) {
		this.zjType = zjType;
	}

	public String getSfType() {
		return sfType;
	}

	public void setSfType(String sfType) {
		this.sfType = sfType;
	}

	public String getJjType() {
		return jjType;
	}

	public void setJjType(String jjType) {
		this.jjType = jjType;
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

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
}