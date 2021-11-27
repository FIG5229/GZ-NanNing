/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党组织概况Entity
 * @author eav.liu
 * @version 2019-10-31
 */
public class AffairGeneralSituation extends DataEntity<AffairGeneralSituation> {

	private static final long serialVersionUID = 1L;
	@ExcelField(title = "党组织名称", type = 0, align = 2, sort = 1)
	private String partyOrganization;		// 党组织名称
	private String ofPartyOrganization;		// 所属党组织
	private String ofPartyOrgId;		// 所属党组织机构
	@ExcelField(title = "党组织成立时间", type = 0, align = 2, sort = 9)
	private Date foundDate;		// 党组织成立时间
	@ExcelField(title = "所在单位", type = 0, align = 2, sort = 0)
	private String unit;		// 所在单位
	private String unitId;		// 所在单位id
	@ExcelField(title = "党组织类型", type = 0, align = 2, sort = 2, dictType = "affair_g_s_type1")
	private String type1;		// 党组织类型
	@ExcelField(title = "党组织书记", type = 0, align = 2, sort = 4)
	private String shuji;		// 党组织书记
	//@ExcelField(title = "党组织联系人", type = 0, align = 2, sort = 12)
	private String contactor;		// 党组织联系人
	/*需求删除身份证号*/
	//@ExcelField(title = "党组织联系人身份证号", type = 0, align = 2, sort = 13)
	private String contactorIdNumber;		// 联系人身份证号
//	@ExcelField(title = "党组织书记身份证号", type = 0, align = 2, sort = 5)
	private String shujiIdNumber;		// 党组织书记身份证号
	@ExcelField(title = "联系电话", type = 0, align = 2, sort = 10)
	private String telephone;		// 联系电话
	//@ExcelField(title = "党组织类别", type = 0, align = 2, sort = 16,dictType = "affair_g_s_type2")
	private String type2;		// 党组织类别
	@ExcelField(title = "党员数", type = 0, align = 2, sort = 6)
	private Integer num;		// 党组织党员数
	@ExcelField(title = "当党组织简介", type = 0, align = 2, sort = 11)
	private String introduction;		// 当党组织简介
	/**
	 * 数据库新加几个字段
	 */
	private String isSzwh;	//是否设支委会
	@ExcelField(title = "换届时间", type = 0, align = 2, sort = 3)
	private Date hjDate;	//换届时间
	@ExcelField(title = "专兼职情况", type = 0, align = 2, sort = 5)
	private String zzSituation;	//专兼职情况
	@ExcelField(title = "民警数", type = 0, align = 2, sort = 7)
	private Integer zgNum;	//民警数
	@ExcelField(title = "所在地区", type = 0, align = 2, sort = 8)
	private String area;	//所在地区
	private String isGtly;	//是否是高铁领域党支部

	private String sort;  // 排序

	private String createOrgId;		// 创建者机构
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构
	private String updateIdNo;		// 更新者身份证号
	private String parentId;	//直系父id
	private String parentIds;	//所有的父id
	private String fullName;	///全称

	//供查询时间区间使用
	private Date startDate;

	//供查询时间区间使用
	private Date endDate;
	private String treeId;


	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;

	private String status; 		//审核状态
	private String opinion;		//审核意见

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public AffairGeneralSituation() {
		super();
	}

	public AffairGeneralSituation(String id){
		super(id);
	}

	public String getPartyOrganization() {
		return partyOrganization;
	}

	public void setPartyOrganization(String partyOrganization) {
		this.partyOrganization = partyOrganization;
	}

	public String getOfPartyOrganization() {
		return ofPartyOrganization;
	}

	public void setOfPartyOrganization(String ofPartyOrganization) {
		this.ofPartyOrganization = ofPartyOrganization;
	}

	public String getOfPartyOrgId() {
		return ofPartyOrgId;
	}

	public void setOfPartyOrgId(String ofPartyOrgId) {
		this.ofPartyOrgId = ofPartyOrgId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getShuji() {
		return shuji;
	}

	public void setShuji(String shuji) {
		this.shuji = shuji;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getContactorIdNumber() {
		return contactorIdNumber;
	}

	public void setContactorIdNumber(String contactorIdNumber) {
		this.contactorIdNumber = contactorIdNumber;
	}

	public String getShujiIdNumber() {
		return shujiIdNumber;
	}

	public void setShujiIdNumber(String shujiIdNumber) {
		this.shujiIdNumber = shujiIdNumber;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getIsSzwh() {
		return isSzwh;
	}

	public void setIsSzwh(String isSzwh) {
		this.isSzwh = isSzwh;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getHjDate() {
		return hjDate;
	}

	public void setHjDate(Date hjDate) {
		this.hjDate = hjDate;
	}

	public String getZzSituation() {
		return zzSituation;
	}

	public void setZzSituation(String zzSituation) {
		this.zzSituation = zzSituation;
	}

	public Integer getZgNum() {
		return zgNum;
	}

	public void setZgNum(Integer zgNum) {
		this.zgNum = zgNum;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIsGtly() {
		return isGtly;
	}

	public void setIsGtly(String isGtly) {
		this.isGtly = isGtly;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}