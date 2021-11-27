/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 培训班学习统计Entity
 * @author alan.wu
 * @version 2020-07-17
 */
public class AffairTrainingStatistics extends DataEntity<AffairTrainingStatistics> {
	
	private static final long serialVersionUID = 1L;

	private String number;		// 编号
	@ExcelField(title = "主办部门", type = 0, align = 2, sort = 0)
	private String unit;		// 主办部门
	private String unitId;		// 部门id
	@ExcelField(title = "培训班名称", type = 0, align = 2, sort = 1)
	private String className;		// 培训班名称
	@ExcelField(title = "培训方式", type = 0, align = 2, sort = 2)
	private String trainingMethod;		// 培训方式
	@ExcelField(title = "培训开始日期", type = 0, align = 2, sort = 3)
	private Date beginTime;		// 培训开始日期
	@ExcelField(title = "培训结束日期", type = 0, align = 2, sort = 4)
	private Date endTime;		// 培训结束日期
	@ExcelField(title = "课程总数", type = 0, align = 2, sort = 5)
	private Double classSum;		// 课程总数
	@ExcelField(title = "开班状态", type = 0, align = 2, sort = 6)
	private String beginState;		// 开班状态
	@ExcelField(title = "结项状态", type = 0, align = 2, sort = 7)
	private String endState;		// 结项状态
	@ExcelField(title = "评估分数", type = 0, align = 2, sort = 8)
	private Double evaluationScore;		// 评估分数
	@ExcelField(title = "应参训人数", type = 0, align = 2, sort = 9)
	private Integer shouldJoin;				//应参训人数
	@ExcelField(title = "实际参训人数", type = 0, align = 2, sort = 10)
	private Integer trueJoin;				//实际参训人数
	@ExcelField(title = "参训率", type = 0, align = 2, sort = 11)
	private Double participationNumber;		// 参训率
	@ExcelField(title = "已通过人数", type = 0, align = 2, sort = 12)
	private Double passedNumber;		// 已通过人数
	@ExcelField(title = "未通过人数", type = 0, align = 2, sort = 13)
	private Double notPassedNumber;		// 未通过人数
	@ExcelField(title = "通过率", type = 0, align = 2, sort = 14)
	private Double passRatio;		// 通过率
	@ExcelField(title = "师资费", type = 0, align = 2, sort = 15)
	private Double teacherCost;		// 师资费
	@ExcelField(title = "住宿费", type = 0, align = 2, sort = 16)
	private Double accommodationCost;		// 住宿费
	@ExcelField(title = "伙食费", type = 0, align = 2, sort = 17)
	private Double foodCost;		// 伙食费
	@ExcelField(title = "场地资料交通费", type = 0, align = 2, sort = 18)
	private Double siteCost;		// 场地资料交通费
	@ExcelField(title = "其他费用", type = 0, align = 2, sort = 19)
	private Double otherCost;		// 其他费用
	@ExcelField(title = "费用总额", type = 0, align = 2, sort = 20)
	private Double costSum;		// 费用总额
	@ExcelField(title = "学习时间总和", type = 0, align = 2, sort = 21)
	private Double learnTimeSum;		// 学习时间总和
	@ExcelField(title = "平均学习时长", type = 0, align = 2, sort = 22)
	private Double learnTimeAvg;		// 平均学习时长
	@ExcelField(title = "学习总次数", type = 0, align = 2, sort = 23)
	private String countSum;		// 学习总次数
	@ExcelField(title = "平均学习次数", type = 0, align = 2, sort = 24)
	private String countAvg;		// 平均学习次数
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String userId;
	private String officeId;

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

	public Integer getShouldJoin() {
		return shouldJoin;
	}

	public void setShouldJoin(Integer shouldJoin) {
		this.shouldJoin = shouldJoin;
	}

	public Integer getTrueJoin() {
		return trueJoin;
	}

	public void setTrueJoin(Integer trueJoin) {
		this.trueJoin = trueJoin;
	}

	public AffairTrainingStatistics() {
		super();
	}

	public AffairTrainingStatistics(String id){
		super(id);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getTrainingMethod() {
		return trainingMethod;
	}

	public void setTrainingMethod(String trainingMethod) {
		this.trainingMethod = trainingMethod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Double getClassSum() {
		return classSum;
	}

	public void setClassSum(Double classSum) {
		this.classSum = classSum;
	}
	
	public String getBeginState() {
		return beginState;
	}

	public void setBeginState(String beginState) {
		this.beginState = beginState;
	}
	
	public String getEndState() {
		return endState;
	}

	public void setEndState(String endState) {
		this.endState = endState;
	}
	
	public Double getEvaluationScore() {
		return evaluationScore;
	}

	public void setEvaluationScore(Double evaluationScore) {
		this.evaluationScore = evaluationScore;
	}
	
	public Double getParticipationNumber() {
		return participationNumber;
	}

	public void setParticipationNumber(Double participationNumber) {
		this.participationNumber = participationNumber;
	}
	
	public Double getPassedNumber() {
		return passedNumber;
	}

	public void setPassedNumber(Double passedNumber) {
		this.passedNumber = passedNumber;
	}
	
	public Double getNotPassedNumber() {
		return notPassedNumber;
	}

	public void setNotPassedNumber(Double notPassedNumber) {
		this.notPassedNumber = notPassedNumber;
	}
	
	public Double getPassRatio() {
		return passRatio;
	}

	public void setPassRatio(Double passRatio) {
		this.passRatio = passRatio;
	}
	
	public Double getTeacherCost() {
		return teacherCost;
	}

	public void setTeacherCost(Double teacherCost) {
		this.teacherCost = teacherCost;
	}
	
	public Double getAccommodationCost() {
		return accommodationCost;
	}

	public void setAccommodationCost(Double accommodationCost) {
		this.accommodationCost = accommodationCost;
	}
	
	public Double getFoodCost() {
		return foodCost;
	}

	public void setFoodCost(Double foodCost) {
		this.foodCost = foodCost;
	}
	
	public Double getSiteCost() {
		return siteCost;
	}

	public void setSiteCost(Double siteCost) {
		this.siteCost = siteCost;
	}
	
	public Double getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}
	
	public Double getCostSum() {
		return costSum;
	}

	public void setCostSum(Double costSum) {
		this.costSum = costSum;
	}
	
	public Double getLearnTimeSum() {
		return learnTimeSum;
	}

	public void setLearnTimeSum(Double learnTimeSum) {
		this.learnTimeSum = learnTimeSum;
	}
	
	public Double getLearnTimeAvg() {
		return learnTimeAvg;
	}

	public void setLearnTimeAvg(Double learnTimeAvg) {
		this.learnTimeAvg = learnTimeAvg;
	}
	
	public String getCountSum() {
		return countSum;
	}

	public void setCountSum(String countSum) {
		this.countSum = countSum;
	}
	
	public String getCountAvg() {
		return countAvg;
	}

	public void setCountAvg(String countAvg) {
		this.countAvg = countAvg;
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