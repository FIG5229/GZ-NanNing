/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 日常学习Entity
 * @author Alan
 * @version 2020-06-11
 */
public class AffairLearnDaily extends DataEntity<AffairLearnDaily> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位

	private String unitId;		// 单位id

	@ExcelField(title = "时间", type = 0, align = 2, sort = 1)
	private Date learnTime;		// 学习时间

	@ExcelField(title = "学习地点", type = 0, align = 2, sort = 2)
	private String site;		// 学习地点

	@ExcelField(title = "主持人", type = 0, align = 2, sort = 3)
	private String compere;		// 主持人

	@ExcelField(title = "参加人员", type = 0, align = 2, sort = 4)
	private String participant;		// 参加人员

	@ExcelField(title = "人数", type = 0, align = 2, sort = 5)
	private String shouldBeTo;		// 应到

	@ExcelField(title = "学习内容", type = 0, align = 2, sort = 6)
	private String content;		// 学习内容


	private String modality;		// 学习形式

	private String centreSpokesman;		// 中心发言人

	private String supplementarySpeaker;		// 补充发言人

	private String registrar;		// 记录人

	private String attendance;		// 实到
	private String adjunct;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date enterDateStart;
	private Date enterDateEnd;
	private String userId;

	//新加字段
	private String participantId;	//参见人员id

	public AffairLearnDaily() {
		super();
	}

	public AffairLearnDaily(String id){
		super(id);
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(Date learnTime) {
		this.learnTime = learnTime;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getCompere() {
		return compere;
	}

	public void setCompere(String compere) {
		this.compere = compere;
	}

	public String getCentreSpokesman() {
		return centreSpokesman;
	}

	public void setCentreSpokesman(String centreSpokesman) {
		this.centreSpokesman = centreSpokesman;
	}

	public String getSupplementarySpeaker() {
		return supplementarySpeaker;
	}

	public void setSupplementarySpeaker(String supplementarySpeaker) {
		this.supplementarySpeaker = supplementarySpeaker;
	}

	public String getRegistrar() {
		return registrar;
	}

	public void setRegistrar(String registrar) {
		this.registrar = registrar;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getShouldBeTo() {
		return shouldBeTo;
	}

	public void setShouldBeTo(String shouldBeTo) {
		this.shouldBeTo = shouldBeTo;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getEnterDateStart() {
		return enterDateStart;
	}

	public void setEnterDateStart(Date enterDateStart) {
		this.enterDateStart = enterDateStart;
	}

	public Date getEnterDateEnd() {
		return enterDateEnd;
	}

	public void setEnterDateEnd(Date enterDateEnd) {
		this.enterDateEnd = enterDateEnd;
	}
}