/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 基本知识成绩Entity
 * @author cecil.li
 * @version 2020-12-28
 */
public class AffairBasicKnowledge extends DataEntity<AffairBasicKnowledge> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;   // 序号
	@ExcelField(title = "年月", type = 0, align = 2, sort = 0)
	private String yearMonth;   // 年月
	@ExcelField(title = "考试", type = 0, align = 2, sort = 1)
	private String exam;		// 考试
	@ExcelField(title = "警号", type = 0, align = 2, sort = 2)
	private String policeNo;		// 警号
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 3)
	private String name;		// 姓名
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 4)
	private String idNumber;		// 证件号码
	@ExcelField(title = "机构", type = 0, align = 2, sort = 5)
	private String unit;		// 机构
	private String unitId;		// 机构id
	@ExcelField(title = "进入考试时间", type = 0, align = 2, sort = 6)
	private Date enterTime;		// 进入考试时间
	@ExcelField(title = "交卷时间", type = 0, align = 2, sort = 7)
	private Date handoverTime;		// 交卷时间
	@ExcelField(title = "客观成绩", type = 0, align = 2, sort = 8)
	private Double objectiveResults;		// 客观成绩
	@ExcelField(title = "主观成绩", type = 0, align = 2, sort = 9)
	private Double subjectivePerformance;		// 主观成绩
	@ExcelField(title = "最终成绩", type = 0, align = 2, sort = 10)
	private Double finalResult;		// 最终成绩
	@ExcelField(title = "通过状态", type = 0, align = 2, sort = 11, dictType = "pass_status")
	private String status;		// 通过状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 体检时间
	private Date endDate;		// 结束 体检时间

	//统计分析使用
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;
	private String userOffice;
	
	public AffairBasicKnowledge() {
		super();
	}

	public AffairBasicKnowledge(String id){
		super(id);
	}

	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}
	
	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHandoverTime() {
		return handoverTime;
	}

	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
	}
	
	public Double getObjectiveResults() {
		return objectiveResults;
	}

	public void setObjectiveResults(Double objectiveResults) {
		this.objectiveResults = objectiveResults;
	}
	
	public Double getSubjectivePerformance() {
		return subjectivePerformance;
	}

	public void setSubjectivePerformance(Double subjectivePerformance) {
		this.subjectivePerformance = subjectivePerformance;
	}
	
	public Double getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(Double finalResult) {
		this.finalResult = finalResult;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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

	public String getUserOffice() {
		return userOffice;
	}

	public void setUserOffice(String userOffice) {
		this.userOffice = userOffice;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}