/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 谈心Entity
 * @author daniel.liu
 * @version 2020-06-11
 */
public class AffairTalkHeart extends DataEntity<AffairTalkHeart> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date time;		// 时间
	@ExcelField(title = "谈心对象政治面貌", type = 0, align = 2, sort = 1, dictType = "politic_countenance")
	private String politicCountenance;		// 谈心对象政治面貌（党员、团员、群众）
	@ExcelField(title = "单位", type = 0, align = 2, sort = 2)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "谈心人", type = 0, align = 2, sort = 3)
	private String heartTalker;		// 谈心人
	@ExcelField(title = "谈心对象", type = 0, align = 2, sort = 4)
	private String heartTo;		// 谈心对象
	@ExcelField(title = "谈心方式", type = 0, align = 2, sort = 5, dictType = "talk_visits_mode")
	private String mode;		// 谈心方式（面对面、电话、微信、其他）
	@ExcelField(title = "谈心内容", type = 0, align = 2, sort = 6)
	private String content;		// 谈心内容
	private String filePath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginTime;		// 开始 时间
	private Date endTime;		// 结束 时间

	/*新增字段*/
	@ExcelField(title = "谈心结果", type = 0, align = 2, sort = 7, dictType = "talk_visits_abnormal")
	private String evaluate;

	/*前端查询使用*/
	private String year;
	private String month;

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

	public AffairTalkHeart() {
		super();
	}

	public AffairTalkHeart(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getPoliticCountenance() {
		return politicCountenance;
	}

	public void setPoliticCountenance(String politicCountenance) {
		this.politicCountenance = politicCountenance;
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
	
	public String getHeartTalker() {
		return heartTalker;
	}

	public void setHeartTalker(String heartTalker) {
		this.heartTalker = heartTalker;
	}
	
	public String getHeartTo() {
		return heartTo;
	}

	public void setHeartTo(String heartTo) {
		this.heartTo = heartTo;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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