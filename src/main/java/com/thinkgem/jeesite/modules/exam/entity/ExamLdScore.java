/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 绩效等次Entity
 * @author daniel.liu
 * @version 2020-05-25
 */
public class ExamLdScore extends DataEntity<ExamLdScore> {
	
	private static final long serialVersionUID = 1L;
	private String job;		// 职位
	private String name;		// 姓名
	private String januaryScore;		// 一月分数
	private String februaryScore;		// 二月分数
	private String marchScore;		// 三月分数
	private String aprilScore;		// 四月分数
	private String mayScore;		// 五月分数
	private String juneScore;		// 六月分数
	private String julyScore;		// 七月分数
	private String augustScore;		// 八月分数
	private String septemberScore;		// 九月分数
	private String octoberScore;		// 十月分数
	private String novemberSocre;		// 十一月分数
	private String decemberScore;		// 十二月分数
	private String dailyScore;		// 日常分数
	private String unitScore;		// 单位得分
	private String minzhuScore;		// 民主测评分数（5分）
	private String zongheScore;		// 综合履历得分（5分）
	private String sumScore;		// 最终得分
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private int status;		// status
	private String workflowId;		// 工作流ID
	private String personId;		// 被考评人ID
	private String grades;		// 绩效等次评定
	private String beginSumScore;		// 开始 最终得分
	private String endSumScore;		// 结束 最终得分

	//统计分析字段
	private String idNumber;         //身份证号
	private String personnelBaseId;  //人员信息表id
	private String time;             // 时间
	private String userId;           //当前用户id
	private String officeId;         //当前用户单位id
	private String unitId;         //当前选择单位id
	private String examType;       //考评类别
	private String jz;              //警种
	private Integer ageStart;              //年龄1
	private Integer ageEnd;                //年龄2


	public ExamLdScore() {
		super();
	}

	public ExamLdScore(String id){
		super(id);
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getJanuaryScore() {
		return januaryScore;
	}

	public void setJanuaryScore(String januaryScore) {
		this.januaryScore = januaryScore;
	}
	
	public String getFebruaryScore() {
		return februaryScore;
	}

	public void setFebruaryScore(String februaryScore) {
		this.februaryScore = februaryScore;
	}
	
	public String getMarchScore() {
		return marchScore;
	}

	public void setMarchScore(String marchScore) {
		this.marchScore = marchScore;
	}
	
	public String getAprilScore() {
		return aprilScore;
	}

	public void setAprilScore(String aprilScore) {
		this.aprilScore = aprilScore;
	}
	
	public String getMayScore() {
		return mayScore;
	}

	public void setMayScore(String mayScore) {
		this.mayScore = mayScore;
	}
	
	public String getJuneScore() {
		return juneScore;
	}

	public void setJuneScore(String juneScore) {
		this.juneScore = juneScore;
	}
	
	public String getJulyScore() {
		return julyScore;
	}

	public void setJulyScore(String julyScore) {
		this.julyScore = julyScore;
	}
	
	public String getAugustScore() {
		return augustScore;
	}

	public void setAugustScore(String augustScore) {
		this.augustScore = augustScore;
	}
	
	public String getSeptemberScore() {
		return septemberScore;
	}

	public void setSeptemberScore(String septemberScore) {
		this.septemberScore = septemberScore;
	}
	
	public String getOctoberScore() {
		return octoberScore;
	}

	public void setOctoberScore(String octoberScore) {
		this.octoberScore = octoberScore;
	}
	
	public String getNovemberSocre() {
		return novemberSocre;
	}

	public void setNovemberSocre(String novemberSocre) {
		this.novemberSocre = novemberSocre;
	}
	
	public String getDecemberScore() {
		return decemberScore;
	}

	public void setDecemberScore(String decemberScore) {
		this.decemberScore = decemberScore;
	}
	
	public String getDailyScore() {
		return dailyScore;
	}

	public void setDailyScore(String dailyScore) {
		this.dailyScore = dailyScore;
	}
	
	public String getUnitScore() {
		return unitScore;
	}

	public void setUnitScore(String unitScore) {
		this.unitScore = unitScore;
	}
	
	public String getMinzhuScore() {
		return minzhuScore;
	}

	public void setMinzhuScore(String minzhuScore) {
		this.minzhuScore = minzhuScore;
	}
	
	public String getZongheScore() {
		return zongheScore;
	}

	public void setZongheScore(String zongheScore) {
		this.zongheScore = zongheScore;
	}
	
	public String getSumScore() {
		return sumScore;
	}

	public void setSumScore(String sumScore) {
		this.sumScore = sumScore;
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
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}
	
	public String getBeginSumScore() {
		return beginSumScore;
	}

	public void setBeginSumScore(String beginSumScore) {
		this.beginSumScore = beginSumScore;
	}
	
	public String getEndSumScore() {
		return endSumScore;
	}

	public void setEndSumScore(String endSumScore) {
		this.endSumScore = endSumScore;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPersonnelBaseId() {
		return personnelBaseId;
	}

	public void setPersonnelBaseId(String personnelBaseId) {
		this.personnelBaseId = personnelBaseId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getJz() {
		return jz;
	}

	public void setJz(String jz) {
		this.jz = jz;
	}

	public Integer getAgeStart() {
		return ageStart;
	}

	public void setAgeStart(Integer ageStart) {
		this.ageStart = ageStart;
	}

	public Integer getAgeEnd() {
		return ageEnd;
	}

	public void setAgeEnd(Integer ageEnd) {
		this.ageEnd = ageEnd;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}