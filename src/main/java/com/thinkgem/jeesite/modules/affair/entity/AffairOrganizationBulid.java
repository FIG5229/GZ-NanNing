/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsAttach;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 组织建设Entity
 * @author cecil.li
 * @version 2019-12-16
 */
public class AffairOrganizationBulid extends DataEntity<AffairOrganizationBulid> {
	
	private static final long serialVersionUID = 1L;
	private Date date;		// 时间
	private String unit;		// 单位
	private String isAssessSys;		// 是否建立会员评价制度
	private String result;		// 会员评价测评结果
	private Integer zghNum;		// 所队支工会数
	private Integer zghrNum;		// 支工会人数
	private Integer satisfyNum;		// 满意数
	private String report;		// 自行自查报告
	private String opinion;		// 审核意见
	private String remark;		// 备注
	private String method;		// 主要做法
	@EsAttach
	private String filePath;		// 文件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String zghzNum;		// 支工会主席
	private String unitId;		// 单位id
	private String shPerson;		// 审核人
	private String status;		// 审核状态
	private String hjDate;		// 换届时间
	private String name;		// 公会名称
	private String orgName;		// 所属上级工会组织
	private Date beginDate;		// 开始 时间
	private Date endDate;		// 结束 时间
	private String parentId;
	private String parentIds;
	private String sort;  //  排序
	@EsIgnore
	private List<AffairOrganziationBuildSign> affairOrganziationBuildSignList = Lists.newArrayList();		// 子表列表
	@EsIgnore
	private List<AffairOrganizationBuildSing2> affairOrganizationBuildSingList2 = Lists.newArrayList();

	private String treeId;		// 接收左侧树的组织id
	
	public AffairOrganizationBulid() {
		super();
	}

	public AffairOrganizationBulid(String id){
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
	
	public String getIsAssessSys() {
		return isAssessSys;
	}

	public void setIsAssessSys(String isAssessSys) {
		this.isAssessSys = isAssessSys;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public Integer getZghNum() {
		return zghNum;
	}

	public void setZghNum(Integer zghNum) {
		this.zghNum = zghNum;
	}
	
	public Integer getZghrNum() {
		return zghrNum;
	}

	public void setZghrNum(Integer zghrNum) {
		this.zghrNum = zghrNum;
	}
	
	public Integer getSatisfyNum() {
		return satisfyNum;
	}

	public void setSatisfyNum(Integer satisfyNum) {
		this.satisfyNum = satisfyNum;
	}
	
	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
	public String getZghzNum() {
		return zghzNum;
	}

	public void setZghzNum(String zghzNum) {
		this.zghzNum = zghzNum;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHjDate() {
		return hjDate;
	}

	public void setHjDate(String hjDate) {
		this.hjDate = hjDate;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
	public List<AffairOrganziationBuildSign> getAffairOrganziationBuildSignList() {
		return affairOrganziationBuildSignList;
	}

	public void setAffairOrganziationBuildSignList(List<AffairOrganziationBuildSign> affairOrganziationBuildSignList) {
		this.affairOrganziationBuildSignList = affairOrganziationBuildSignList;
	}

	public List<AffairOrganizationBuildSing2> getAffairOrganizationBuildSingList2() {
		return affairOrganizationBuildSingList2;
	}

	public void setAffairOrganizationBuildSingList2(List<AffairOrganizationBuildSing2> affairOrganizationBuildSingList2) {
		this.affairOrganizationBuildSingList2 = affairOrganizationBuildSingList2;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}