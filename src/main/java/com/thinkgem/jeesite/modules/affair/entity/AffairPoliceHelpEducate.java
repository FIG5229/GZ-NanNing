/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 民警帮教Entity
 * @author daniel.liu
 * @version 2020-05-14
 */
public class AffairPoliceHelpEducate extends DataEntity<AffairPoliceHelpEducate> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "帮教时间", type = 0, align = 2, sort = 2)
	private Date time;		// 帮教时间
	private String place;		// 帮教地点
	@ExcelField(title = "帮教人", type = 0, align = 2, sort = 3)
	private String helpers;		// 帮教人
	@ExcelField(title = "帮教对象", type = 0, align = 2, sort = 4)
	private String helpobject;		// 帮教对象
	private String question;		// 对象存在问题
	private String measures;		// 帮教措施
	@ExcelField(title = "帮教成效", type = 0, align = 2, sort = 5)
	private String results;		// 帮教成效
	@ExcelField(title = "简要内容", type = 0, align = 2, sort = 6)
	private String content;		// 简要内容
	private String filepath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginTime;		// 开始 帮教时间
	private Date endTime;		// 结束 帮教时间

	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;			//单位
	private String unitId;			//单位id


	private String officeId;
	private String userId;


	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public AffairPoliceHelpEducate() {
		super();
	}

	public AffairPoliceHelpEducate(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getHelpers() {
		return helpers;
	}

	public void setHelpers(String helpers) {
		this.helpers = helpers;
	}
	
	public String getHelpobject() {
		return helpobject;
	}

	public void setHelpobject(String helpobject) {
		this.helpobject = helpobject;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getMeasures() {
		return measures;
	}

	public void setMeasures(String measures) {
		this.measures = measures;
	}
	
	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
		
}