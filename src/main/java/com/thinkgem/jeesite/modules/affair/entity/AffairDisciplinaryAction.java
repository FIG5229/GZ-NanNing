/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 纪律处分Entity
 * @author cecil.li
 * @version 2019-11-08
 */
public class AffairDisciplinaryAction extends DataEntity<AffairDisciplinaryAction> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "发生时间", type = 0, align = 2, sort = 0)
	private Date lrDate;	//发生时间
	@ExcelField(title = "受理时间", type = 0, align = 2, sort = 1)
	private Date chfDate;	//受理时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3,dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;		// 身份证号
	@ExcelField(title = "警号", type = 0, align = 2, sort = 5)
	private String siren;		// 警号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 6)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 7)
	private String job;         //职务
	@ExcelField(title = "职级", type = 0, align = 2, sort = 8)
	private String jobLevel;      //职级
	@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 9,dictType="political_status")
	private String zhengzhiFace; //政治面貌
	@ExcelField(title = "问题类型", type = 0, align = 2, sort = 10,dictType="affair_wenti")
	private String nature;		// 问题类型
	@ExcelField(title = "简要问题", type = 0, align = 2, sort = 11)
	private String violations;		// 简要问题
	@ExcelField(title = "处分种类", type = 0, align = 2, sort = 12,dictType="affair_xzchufen")
	private String disciplinaryType;		// 处分种类
	private String subOption;	//处分种类对应的子选项
	@ExcelField(title = "行政处分", type = 0, align = 2, sort = 13,dictType="affair_xz_sub_option")
	private String xzSubOption;//前端行政处分子选项
	@ExcelField(title = "党纪处分", type = 0, align = 2, sort = 14,dictType="affair_dj_type")
	private String djSubOption;//前端党纪处分子选项
	private String zzSubOption;	//党纪处分党组织对应的子选项
	@ExcelField(title = "党纪人员处分", type = 0, align = 2, sort = 15,dictType="affair_dj_sub_option")
	private String rySubOption; //党纪处分人员子选项
	/*@ExcelField(title = "党纪党组织处分", type = 0, align = 2, sort = 16,dictType="affair_dj_sub")
	private String dzzSubOption; //党纪处分党组织子选项*/
	@ExcelField(title = "司法处理", type = 0, align = 2, sort = 16)
	private String sifa;      //司法处理
	@ExcelField(title = "组织处理", type = 0, align = 2, sort = 17)
	private String zuzhi;       //组织处理
	@ExcelField(title = "其他方式", type = 0, align = 2, sort = 18)
	private String other;         //其他方式
	@ExcelField(title = "处分单位", type = 0, align = 2, sort = 19,dictType="affair_cf_unit")
	private String cfUnit;     //处分单位
	@ExcelField(title = "文号", type = 0, align = 2, sort = 20)
	private String fileNum;     //文号
	@ExcelField(title = "党组织处理", type = 0, align = 2, sort = 21)
	private String chuli;		// 党组织处理
	@ExcelField(title = "处分决定时间", type = 0, align = 2, sort = 22)
	private Date approvalDate;		//处分决定时间
	@ExcelField(title = "处分期满时间", type = 0, align = 2, sort = 23)
	private Date influencePeriod;	//（处分期满时间）1、行政处分的影响期  警告－6个月、记过－12个月、记大过－18个月、降级、撤职－24个月、开除  2、党纪处分的影响期 自行填写影响期
	@ExcelField(title = "是否立案", type = 0, align = 2, sort = 24,dictType="yes_no")
	private String isLian;      //是否立案
	private Date date;    //时间
	private String org;    //党组织名称
	private String orgId;   //党组织id

	private String annex;		// 附件
	private String reviewer;		// 审核人
	private String audirOpinion;		// 审核人意见
	private String approvalStatus;		// 审核状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String personalNum;		// 身份证号码

	private String pushType; //推送状态

	private Date startApprovalDate;
	private Date endApprovalDate;
	private Date startInfluencePeriod;
	private Date endInfluencePeriod;



	private String qxUnit;
	private String qxUnitId;
	private Date startYear; //本年度
	private String otherYear;  //其他年份

	private String dateType;

	private Integer year;

	private String dateStart;

	private String dateEnd;

	private String month;

	private String flag;

	private Date startDate;
	private Date endDate;
	private String natureType;
	private String unitType;
	private String chuFenType;
	private String djChuFen;
	private String xzChuFen;


	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getZhengzhiFace() {
		return zhengzhiFace;
	}

	public void setZhengzhiFace(String zengzhiFace) {
		this.zhengzhiFace = zengzhiFace;
	}

	public AffairDisciplinaryAction() {
		super();
	}

	public AffairDisciplinaryAction(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getDisciplinaryType() {
		return disciplinaryType;
	}

	public void setDisciplinaryType(String disciplinaryType) {
		this.disciplinaryType = disciplinaryType;
	}
	
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
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
	
	public String getSiren() {
		return siren;
	}

	public void setSiren(String siren) {
		this.siren = siren;
	}
	
	public String getViolations() {
		return violations;
	}

	public void setViolations(String violations) {
		this.violations = violations;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}
	
	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	public String getAudirOpinion() {
		return audirOpinion;
	}

	public void setAudirOpinion(String audirOpinion) {
		this.audirOpinion = audirOpinion;
	}
	
	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
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
	
	public String getPersonalNum() {
		return personalNum;
	}

	public void setPersonalNum(String personalNum) {
		this.personalNum = personalNum;
	}
	
	public String getChuli() {
		return chuli;
	}

	public void setChuli(String chuli) {
		this.chuli = chuli;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getSubOption() {
		return subOption;
	}

	public void setSubOption(String subOption) {
		this.subOption = subOption;
	}

	public String getXzSubOption() {
		return xzSubOption;
	}

	public void setXzSubOption(String xzSubOption) {
		this.xzSubOption = xzSubOption;
	}

	public String getDjSubOption() {
		return djSubOption;
	}

	public void setDjSubOption(String djSubOption) {
		this.djSubOption = djSubOption;
	}

	public String getZzSubOption() {
		return zzSubOption;
	}

	public void setZzSubOption(String zzSubOption) {
		this.zzSubOption = zzSubOption;
	}

/*	public String getDzzSubOption() {
		return dzzSubOption;
	}

	public void setDzzSubOption(String dzzSubOption) {
		this.dzzSubOption = dzzSubOption;
	}*/

	public String getRySubOption() {
		return rySubOption;
	}

	public void setRySubOption(String rySubOption) {
		this.rySubOption = rySubOption;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getInfluencePeriod() {
		return influencePeriod;
	}

	public void setInfluencePeriod(Date influencePeriod) {
		this.influencePeriod = influencePeriod;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getLrDate() {
		return lrDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public void setLrDate(Date lrDate) {
		this.lrDate = lrDate;
	}

	public Date getChfDate() {
		return chfDate;
	}

	public void setChfDate(Date chfDate) {
		this.chfDate = chfDate;
	}


	public String getQxUnitId() {
		return qxUnitId;
	}

	public void setQxUnitId(String qxUnitId) {
		this.qxUnitId = qxUnitId;
	}

	public String getQxUnit() {
		return qxUnit;
	}

	public void setQxUnit(String qxUnit) {
		this.qxUnit = qxUnit;
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

	public String getCfUnit() {
		return cfUnit;
	}

	public void setCfUnit(String cfUnit) {
		this.cfUnit = cfUnit;
	}

	public String getIsLian() {
		return isLian;
	}

	public void setIsLian(String isLian) {
		this.isLian = isLian;
	}

	public Date getStartApprovalDate() {
		return startApprovalDate;
	}

	public void setStartApprovalDate(Date startApprovalDate) {
		this.startApprovalDate = startApprovalDate;
	}

	public Date getEndApprovalDate() {
		return endApprovalDate;
	}

	public void setEndApprovalDate(Date endApprovalDate) {
		this.endApprovalDate = endApprovalDate;
	}

	public Date getStartInfluencePeriod() {
		return startInfluencePeriod;
	}

	public void setStartInfluencePeriod(Date startInfluencePeriod) {
		this.startInfluencePeriod = startInfluencePeriod;
	}

	public Date getEndInfluencePeriod() {
		return endInfluencePeriod;
	}

	public void setEndInfluencePeriod(Date endInfluencePeriod) {
		this.endInfluencePeriod = endInfluencePeriod;
	}

	public String getSifa() {
		return sifa;
	}

	public void setSifa(String sifa) {
		this.sifa = sifa;
	}

	public String getZuzhi() {
		return zuzhi;
	}

	public void setZuzhi(String zuzhi) {
		this.zuzhi = zuzhi;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}


	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonFormat(pattern = "yyyy")
	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	public String getOtherYear() {
		return otherYear;
	}

	public void setOtherYear(String otherYear) {
		this.otherYear = otherYear;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public String getNatureType() {
		return natureType;
	}

	public void setNatureType(String natureType) {
		this.natureType = natureType;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getChuFenType() {
		return chuFenType;
	}

	public void setChuFenType(String chuFenType) {
		this.chuFenType = chuFenType;
	}

	public String getDjChuFen() {
		return djChuFen;
	}

	public void setDjChuFen(String djChuFen) {
		this.djChuFen = djChuFen;
	}

	public String getXzChuFen() {
		return xzChuFen;
	}

	public void setXzChuFen(String xzChuFen) {
		this.xzChuFen = xzChuFen;
	}
}