/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党支部集中学习Entity
 * @author cecil.li
 * @version 2019-11-04
 */
public class AffairFocusStudy extends DataEntity<AffairFocusStudy> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "学习内容", type = 0, align = 2, sort = 0)
	private String content;		// 学习内容
	private String organization;		// 所属组织
	private String organizationId;		// 所属组织id
	@ExcelField(title = "学习时间", type = 0, align = 2, sort = 2)
	private Date date;		// 学习时间
	@ExcelField(title = "学习地点", type = 0, align = 2, sort = 3)
	private String place;		// 学习地点
	@ExcelField(title = "学习形式", type = 0, align = 2, sort = 4, dictType="affair_study_type")
	private String method;		// 学习形式
	@ExcelField(title = "主持人", type = 0, align = 2, sort = 5)
	private String host;		// 主持人
	@ExcelField(title = "中心发言人员", type = 0, align = 2, sort = 6)
	private String zxPerson;		// 中心发言人员
	@ExcelField(title = "补充发言人员", type = 0, align = 2, sort = 7)
	private String bcPerson;		// 补充发言人员
	@ExcelField(title = "记录人", type = 0, align = 2, sort = 8)
	private String recorder;		// 记录人
	@ExcelField(title = "参加人员", type = 0, align = 2, sort = 9)
	private String joinPerson;		// 参加人员
	@ExcelField(title = "相关文件", type = 0, align = 2, sort = 10)
//	@EsAttach
	private String filePath;		// 相关文件

	@ExcelField(title = "所属组织", type = 0, align = 2, sort = 1)
	private String unit;
	private String unitId;
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String otherMethod;  //其他学习形式

	private Date startDate;
	private Date endDate;

	
	public AffairFocusStudy() {
		super();
	}

	public AffairFocusStudy(String id){
		super(id);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public String getZxPerson() {
		return zxPerson;
	}

	public void setZxPerson(String zxPerson) {
		this.zxPerson = zxPerson;
	}
	
	public String getBcPerson() {
		return bcPerson;
	}

	public void setBcPerson(String bcPerson) {
		this.bcPerson = bcPerson;
	}
	
	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	
	public String getJoinPerson() {
		return joinPerson;
	}

	public void setJoinPerson(String joinPerson) {
		this.joinPerson = joinPerson;
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

	public String getOtherMethod() {
		return otherMethod;
	}

	public void setOtherMethod(String otherMethod) {
		this.otherMethod = otherMethod;
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
}