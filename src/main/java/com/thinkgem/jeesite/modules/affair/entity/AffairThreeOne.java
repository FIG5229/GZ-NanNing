/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 三会一课Entity
 * @author cecil.li
 * @version 2019-11-06
 */
public class AffairThreeOne extends DataEntity<AffairThreeOne> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "会议名称", type = 0, align = 2, sort = 0)
	private String name;		// 会议名称
	@ExcelField(title = "团组织", type = 0, align = 2, sort = 1)
	private String organization;		// 团组织
	@ExcelField(title = "会议类型", type = 0, align = 2, sort = 2, dictType="affair_huiyi")
	private String type;		// 会议类型
	private String organizationId;		// 团组织id
	@ExcelField(title = "会议时间", type = 0, align = 2, sort = 5)
	private Date date;		// 会议时间
	@ExcelField(title = "会议地点", type = 0, align = 2, sort = 6)
	private String place;		// 会议地点
	@ExcelField(title = "主持人", type = 0, align = 2, sort = 3)
	private String host;		// 主持人
	@ExcelField(title = "记录人", type = 0, align = 2, sort = 4)
	private String recorder;		// 记录人
	@ExcelField(title = "应到人数", type = 0, align = 2, sort = 7)
	private Integer ydNum;		// 应到人数
	@ExcelField(title = "实到人数", type = 0, align = 2, sort = 8)
	private Integer sdNum;		// 实到人数
	@ExcelField(title = "缺席人员", type = 0, align = 2, sort = 9)
	private String absentPerson;		// 缺席人员
	@ExcelField(title = "会议内容", type = 0, align = 2, sort = 10)
	private String content;		// 会议内容
	@ExcelField(title = "年度", type = 0, align = 2, sort = 11)
	private Integer niandu;		// 年度
	@ExcelField(title = "相关文件", type = 0, align = 2, sort = 12)
	private String filePath;		// 相关文件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	/*新加字段*/
	private String attendants;		//出席人
	private String attendantsId;		//出席人Id
	private String defaulterId;		//缺席人
	private String agenda;			//会议议程
	private Integer absentNum;		// 缺席人数

	private Date startDate;
	private Date endDate;

	public AffairThreeOne() {
		super();
	}

	public AffairThreeOne(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	
	public Integer getYdNum() {
		return ydNum;
	}

	public void setYdNum(Integer ydNum) {
		this.ydNum = ydNum;
	}
	
	public Integer getSdNum() {
		return sdNum;
	}

	public void setSdNum(Integer sdNum) {
		this.sdNum = sdNum;
	}
	
	public String getAbsentPerson() {
		return absentPerson;
	}

	public void setAbsentPerson(String absentPerson) {
		this.absentPerson = absentPerson;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
//	@JsonFormat(pattern = "yyyy")
//	public Date getNiandu() {
//		return niandu;
//	}
//
//	public void setNiandu(Date niandu) {
//		this.niandu = niandu;
//	}
	
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

	public Integer getNiandu() {
		return niandu;
	}

	public void setNiandu(Integer niandu) {
		this.niandu = niandu;
	}

	public String getAttendants() {
		return attendants;
	}

	public void setAttendants(String attendants) {
		this.attendants = attendants;
	}

	public String getDefaulterId() {
		return defaulterId;
	}

	public void setDefaulterId(String defaulterId) {
		this.defaulterId = defaulterId;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getAttendantsId() {
		return attendantsId;
	}

	public void setAttendantsId(String attendantsId) {
		this.attendantsId = attendantsId;
	}

	public Integer getAbsentNum() {
		return absentNum;
	}

	public void setAbsentNum(Integer absentNum) {
		this.absentNum = absentNum;
	}
}