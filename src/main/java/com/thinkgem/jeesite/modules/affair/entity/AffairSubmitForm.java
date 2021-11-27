/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 档案管理Entity
 * @author mason.xv
 * @version 2019-11-05
 */
public class AffairSubmitForm extends DataEntity<AffairSubmitForm> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "档案卷数", type = 0, align = 2, sort = 1)
	private Integer num;		// 档案卷数
	@ExcelField(title = "单位", type = 0, align = 2, sort = 2)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 3, dictType="affair_job")
	private String job;		// 职务
	@ExcelField(title = "任职开始时间", type = 0, align = 2, sort = 4)
	private Date startDate;		// 任职开始时间
	@ExcelField(title = "任职结束时间", type = 0, align = 2, sort = 5)
	private Date endDate;		// 任职结束时间
	@ExcelField(title = "档案整理人", type = 0, align = 2, sort = 6)
	private String arranger;		// 档案整理人
	@ExcelField(title = "档案审核人", type = 0, align = 2, sort = 7)
	private String checker;		// 档案审核人
	@ExcelField(title = "干部档案遗留问题或需要说明的情况", type = 0, align = 2, sort = 8)
	private String situation;		// 干部档案遗留问题或需要说明的情况
	@ExcelField(title = "报送单位的意见内容", type = 0, align = 2, sort = 9)
	private String content;		// 报送单位的意见内容
	@ExcelField(title = "报送单位签字领导", type = 0, align = 2, sort = 10)
	private String leader;		// 报送单位签字领导
	@ExcelField(title = "签字日期", type = 0, align = 2, sort = 11)
	private Date signDate;		// 签字日期

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairSubmitForm() {
		super();
	}

	public AffairSubmitForm(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
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
	
	public String getArranger() {
		return arranger;
	}

	public void setArranger(String arranger) {
		this.arranger = arranger;
	}
	
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
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
	
}