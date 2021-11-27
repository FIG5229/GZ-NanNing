/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 心谈家访Entity
 * @author daniel.liu
 * @version 2020-05-13
 */
public class AffairHomeVisit extends DataEntity<AffairHomeVisit> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date time;		// 时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "家访人", type = 0, align = 2, sort = 3)
	private String homeVisitors;		// 谈心家访人
	@ExcelField(title = "家访对象", type = 0, align = 2, sort = 4)
	private String visitObject;		// 被谈心家访对象
	@ExcelField(title = "简要内容", type = 0, align = 2, sort = 5)
	private String content;		// 简要内容
	private String filepath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginTime;		// 开始 时间
	private Date endTime;		// 结束 时间

	/*新增字段*/
	@ExcelField(title = "家访结果", type = 0, align = 2, sort = 7, dictType = "talk_visits_abnormal")
	private String evaluate;

	/*前端 查询使用*/
	private String year;
	private String month;
	@ExcelField(title = "家访形式", type = 0, align = 2, sort = 2,dictType="talk_visits_mode")
	private String mode;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public AffairHomeVisit() {
		super();
	}

	public AffairHomeVisit(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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
	
	public String getHomeVisitors() {
		return homeVisitors;
	}

	public void setHomeVisitors(String homeVisitors) {
		this.homeVisitors = homeVisitors;
	}
	
	public String getVisitObject() {
		return visitObject;
	}

	public void setVisitObject(String visitObject) {
		this.visitObject = visitObject;
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

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}