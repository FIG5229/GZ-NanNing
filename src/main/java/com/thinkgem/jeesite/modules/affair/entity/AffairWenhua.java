/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 文化人才Entity
 * @author cecil.li
 * @version 2020-03-06
 */
public class AffairWenhua extends DataEntity<AffairWenhua> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名",type = 0,align = 2,sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "单位",type = 0,align = 2,sort = 2)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "特长",type = 0,align = 2,sort = 3,dictType = "affair_wenyi_specialty")
	private String specialty;		// 特长
	@ExcelField(title = "入会情况",type = 0,align = 2,sort = 4,dictType = "affair_ruhui")
	private String joinType;		// 入会情况
	@ExcelField(title = "奖项情况",type = 0,align = 2,sort = 6)
	private String reward;		// 奖项情况
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "作品情况",type = 0,align = 2,sort = 5)
	private String workSituation;	//作品情况
	@ExcelField(title = "宣传情况",type = 0,align = 2,sort = 7)
	private String publicity;		//宣传情况
	private String juCheckMan;		// 局审核人
	private String chuCheckMan;		// 处审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态
	private String juCheckId;		// 局审核id
	private String chuCheckId;		// 处审核id
	private String submitId;		// 提交人id
	private String opinion;         // 审核意见
	private String userId;			//记录当前登录用户id
	private String officeId;        //记录当前用户部门id


	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;
	private Date startDate;
	private Date endDate;

	private List<String> leave;
	private List<String> awards;


	public List<String> getLeave() {
		return leave;
	}

	public void setLeave(List<String> leave) {
		this.leave = leave;
	}

	public List<String> getAwards() {
		return awards;
	}

	public void setAwards(List<String> awards) {
		this.awards = awards;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getJuCheckMan() {
		return juCheckMan;
	}

	public void setJuCheckMan(String juCheckMan) {
		this.juCheckMan = juCheckMan;
	}

	public String getChuCheckMan() {
		return chuCheckMan;
	}

	public void setChuCheckMan(String chuCheckMan) {
		this.chuCheckMan = chuCheckMan;
	}

	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getJuCheckId() {
		return juCheckId;
	}

	public void setJuCheckId(String juCheckId) {
		this.juCheckId = juCheckId;
	}

	public String getChuCheckId() {
		return chuCheckId;
	}

	public void setChuCheckId(String chuCheckId) {
		this.chuCheckId = chuCheckId;
	}

	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getPublicity() {
		return publicity;
	}

	public void setPublicity(String publicity) {
		this.publicity = publicity;
	}

	public String getWorkSituation() {
		return workSituation;
	}

	public void setWorkSituation(String workSituation) {
		this.workSituation = workSituation;
	}

	public AffairWenhua() {
		super();
	}

	public AffairWenhua(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
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
}