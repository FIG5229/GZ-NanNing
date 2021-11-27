/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 基本体能成绩Entity
 * @author cecil.li
 * @version 2020-12-28
 */
public class AffairBasicFitness extends DataEntity<AffairBasicFitness> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private String yearMonth;  // 时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "证件号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 证件号
	@ExcelField(title = "年龄", type = 0, align = 2, sort = 3)
	private String age;		// 年龄
	@ExcelField(title = "性别", type = 0, align = 2, sort = 4, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "单位", type = 0, align = 2, sort = 5)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "身高", type = 0, align = 2, sort = 6)
	private String height;  // 身高
	@ExcelField(title = "体重", type = 0, align = 2, sort = 7)
	private String weight;  // 体重
	@ExcelField(title = "数值", type = 0, align = 2, sort = 8)
	private String value;   // 数值
	@ExcelField(title = "评定", type = 0, align = 2, sort = 9, dictType = "fitness_assess")
	private String assess;  // 评定
	private String job;		// 职务
	@ExcelField(title = "50米跑成绩（秒）", type = 0, align = 2, sort = 10)
	private String fiftyRunScore;		// 50米跑成绩（秒）
	@ExcelField(title = "50米跑达标情况", type = 0, align = 2, sort = 10, dictType = "yes_no")
	private String fiftyRunStatus;		// 50米跑达标情况
	@ExcelField(title = "立定跳远成绩（米）", type = 0, align = 2, sort = 11)
	private String jumpScore;		// 立定跳远成绩（米）
	@ExcelField(title = "立定跳远达标情况", type = 0, align = 2, sort = 11, dictType = "yes_no")
	private String jumpStatus;		// 立定跳远达标情况
	@ExcelField(title = "2000米跑成绩（分+秒）", type = 0, align = 2, sort = 12)
	private String twokmRunScore;		// 2000米跑成绩（分+秒）
	@ExcelField(title = "2000米跑达标情况", type = 0, align = 2, sort = 12, dictType = "yes_no")//16
	private String twokmRunStatus;		// 2000米跑达标情况
	@ExcelField(title = "女子1分钟仰卧起坐成绩（个）", type = 0, align = 2, sort = 13)
	private String nvSitUpsScore;		// 女子1分钟仰卧起坐成绩（个）
	@ExcelField(title = "女子1分钟仰卧起坐达标情况", type = 0, align = 2, sort = 13, dictType = "yes_no")
	private String nvSitUpsStatus;		// 女子1分钟仰卧起坐达标情况
	@ExcelField(title = "男子引体向上成绩（个）", type = 0, align = 2, sort = 14)
	private String nanPullUpsScore;		// 男子引体向上成绩（个）
	@ExcelField(title = "男子引体向上达标情况", type = 0, align = 2, sort = 14, dictType = "yes_no")
	private String nanPullUpsStatus;		// 男子引体向上达标情况
	@ExcelField(title = "男子1分钟俯卧撑成绩（个）", type = 0, align = 2, sort = 15)
	private String nanPushUpsScore;		// 男子1分钟俯卧撑成绩（个）
	@ExcelField(title = "男子1分钟俯卧撑达标情况", type = 0, align = 2, sort = 15, dictType = "yes_no")
	private String nanPushIpsStatus;		// 男子1分钟俯卧撑达标情况
	@ExcelField(title = "握力成绩（公斤）", type = 0, align = 2, sort = 16)
	private String gripScore;		// 握力成绩（公斤）
	@ExcelField(title = "握力达标情况", type = 0, align = 2, sort = 16, dictType = "yes_no")
	private String gripStatus;		// 握力达标情况
	@ExcelField(title = "坐位体前屈成绩（厘米）", type = 0, align = 2, sort = 17)
	private String sittingForwardBendingScore;		// 坐位体前屈成绩（厘米）
	@ExcelField(title = "坐位体前屈达标情况", type = 0, align = 2, sort = 17, dictType = "yes_no")
	private String sittingForwardBendingStatus;		// 坐位体前屈达标情况
	@ExcelField(title = "综合评定", type = 0, align = 2, sort = 18, dictType = "pass_status")
	private String comprehensiveAssessment;		// 综合评定
	@ExcelField(title = "备注", type = 0, align = 2, sort = 19)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairBasicFitness() {
		super();
	}

	public AffairBasicFitness(String id){
		super(id);
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
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
	
	public String getFiftyRunScore() {
		return fiftyRunScore;
	}

	public void setFiftyRunScore(String fiftyRunScore) {
		this.fiftyRunScore = fiftyRunScore;
	}
	
	public String getFiftyRunStatus() {
		return fiftyRunStatus;
	}

	public void setFiftyRunStatus(String fiftyRunStatus) {
		this.fiftyRunStatus = fiftyRunStatus;
	}
	
	public String getNvSitUpsScore() {
		return nvSitUpsScore;
	}

	public void setNvSitUpsScore(String nvSitUpsScore) {
		this.nvSitUpsScore = nvSitUpsScore;
	}
	
	public String getNvSitUpsStatus() {
		return nvSitUpsStatus;
	}

	public void setNvSitUpsStatus(String nvSitUpsStatus) {
		this.nvSitUpsStatus = nvSitUpsStatus;
	}
	
	public String getNanPullUpsScore() {
		return nanPullUpsScore;
	}

	public void setNanPullUpsScore(String nanPullUpsScore) {
		this.nanPullUpsScore = nanPullUpsScore;
	}
	
	public String getNanPullUpsStatus() {
		return nanPullUpsStatus;
	}

	public void setNanPullUpsStatus(String nanPullUpsStatus) {
		this.nanPullUpsStatus = nanPullUpsStatus;
	}
	
	public String getNanPushUpsScore() {
		return nanPushUpsScore;
	}

	public void setNanPushUpsScore(String nanPushUpsScore) {
		this.nanPushUpsScore = nanPushUpsScore;
	}
	
	public String getNanPushIpsStatus() {
		return nanPushIpsStatus;
	}

	public void setNanPushIpsStatus(String nanPushIpsStatus) {
		this.nanPushIpsStatus = nanPushIpsStatus;
	}
	
	public String getGripScore() {
		return gripScore;
	}

	public void setGripScore(String gripScore) {
		this.gripScore = gripScore;
	}
	
	public String getGripStatus() {
		return gripStatus;
	}

	public void setGripStatus(String gripStatus) {
		this.gripStatus = gripStatus;
	}
	
	public String getJumpScore() {
		return jumpScore;
	}

	public void setJumpScore(String jumpScore) {
		this.jumpScore = jumpScore;
	}
	
	public String getJumpStatus() {
		return jumpStatus;
	}

	public void setJumpStatus(String jumpStatus) {
		this.jumpStatus = jumpStatus;
	}
	
	public String getTwokmRunScore() {
		return twokmRunScore;
	}

	public void setTwokmRunScore(String twokmRunScore) {
		this.twokmRunScore = twokmRunScore;
	}
	
	public String getTwokmRunStatus() {
		return twokmRunStatus;
	}

	public void setTwokmRunStatus(String twokmRunStatus) {
		this.twokmRunStatus = twokmRunStatus;
	}
	
	public String getSittingForwardBendingScore() {
		return sittingForwardBendingScore;
	}

	public void setSittingForwardBendingScore(String sittingForwardBendingScore) {
		this.sittingForwardBendingScore = sittingForwardBendingScore;
	}
	
	public String getSittingForwardBendingStatus() {
		return sittingForwardBendingStatus;
	}

	public void setSittingForwardBendingStatus(String sittingForwardBendingStatus) {
		this.sittingForwardBendingStatus = sittingForwardBendingStatus;
	}
	
	public String getComprehensiveAssessment() {
		return comprehensiveAssessment;
	}

	public void setComprehensiveAssessment(String comprehensiveAssessment) {
		this.comprehensiveAssessment = comprehensiveAssessment;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAssess() {
		return assess;
	}

	public void setAssess(String assess) {
		this.assess = assess;
	}
}