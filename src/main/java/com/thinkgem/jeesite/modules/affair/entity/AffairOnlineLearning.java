/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 线上学习Entity
 * @author cecil.li
 * @version 2020-12-29
 */
public class AffairOnlineLearning extends DataEntity<AffairOnlineLearning> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "机构名称", type = 0, align = 2, sort = 0)
	private String unit;		// 机构名称
	private String unitId;		// 机构id
	@ExcelField(title = "时间", type = 0, align = 2, sort = 1)
	private Date date;		// 时间
	@ExcelField(title = "用户总数", type = 0, align = 2, sort = 2)
	private String totalUsers;		// 用户总数
	@ExcelField(title = "学习总人数", type = 0, align = 2, sort = 3)
	private String totalLearn;		// 学习总人数
	@ExcelField(title = "学习率", type = 0, align = 2, sort = 4)
	private String learnRate;		// 学习率
	@ExcelField(title = "学习总时长（小时）", type = 0, align = 2, sort = 5)
	private String totalStudyTime;		// 学习总时长（小时）
	@ExcelField(title = "参加学习用户平均学习时长（小时）", type = 0, align = 2, sort = 6)
	private String cjxxyhpjxxsc;		// 参加学习用户平均学习时长（小时）
	@ExcelField(title = "全体用户平均学习时长", type = 0, align = 2, sort = 7)
	private String qtyhpjxxsc;		// 全体用户平均学习时长
	@ExcelField(title = "学习总次数", type = 0, align = 2, sort = 8)
	private String totalNumbers;		// 学习总次数
	@ExcelField(title = "参加学习用户平均学习次数", type = 0, align = 2, sort = 9)
	private String cjxxyhpjxxcs;		// 参加学习用户平均学习次数
	@ExcelField(title = "全体用户平均学习次数", type = 0, align = 2, sort = 10)
	private String qtyhpjxxcs;		// 全体用户平均学习次数
	@ExcelField(title = "课程通过总数", type = 0, align = 2, sort = 11)
	private String kctgzs;		// 课程通过总数
	@ExcelField(title = "人均通过课程", type = 0, align = 2, sort = 12)
	private String rjtgkc;		// 人均通过课程
	@ExcelField(title = "备注", type = 0, align = 2, sort = 13)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public AffairOnlineLearning() {
		super();
	}

	public AffairOnlineLearning(String id){
		super(id);
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
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(String totalUsers) {
		this.totalUsers = totalUsers;
	}
	
	public String getTotalLearn() {
		return totalLearn;
	}

	public void setTotalLearn(String totalLearn) {
		this.totalLearn = totalLearn;
	}
	
	public String getLearnRate() {
		return learnRate;
	}

	public void setLearnRate(String learnRate) {
		this.learnRate = learnRate;
	}
	
	public String getTotalStudyTime() {
		return totalStudyTime;
	}

	public void setTotalStudyTime(String totalStudyTime) {
		this.totalStudyTime = totalStudyTime;
	}
	
	public String getCjxxyhpjxxsc() {
		return cjxxyhpjxxsc;
	}

	public void setCjxxyhpjxxsc(String cjxxyhpjxxsc) {
		this.cjxxyhpjxxsc = cjxxyhpjxxsc;
	}
	
	public String getQtyhpjxxsc() {
		return qtyhpjxxsc;
	}

	public void setQtyhpjxxsc(String qtyhpjxxsc) {
		this.qtyhpjxxsc = qtyhpjxxsc;
	}
	
	public String getTotalNumbers() {
		return totalNumbers;
	}

	public void setTotalNumbers(String totalNumbers) {
		this.totalNumbers = totalNumbers;
	}
	
	public String getCjxxyhpjxxcs() {
		return cjxxyhpjxxcs;
	}

	public void setCjxxyhpjxxcs(String cjxxyhpjxxcs) {
		this.cjxxyhpjxxcs = cjxxyhpjxxcs;
	}
	
	public String getQtyhpjxxcs() {
		return qtyhpjxxcs;
	}

	public void setQtyhpjxxcs(String qtyhpjxxcs) {
		this.qtyhpjxxcs = qtyhpjxxcs;
	}
	
	public String getKctgzs() {
		return kctgzs;
	}

	public void setKctgzs(String kctgzs) {
		this.kctgzs = kctgzs;
	}
	
	public String getRjtgkc() {
		return rjtgkc;
	}

	public void setRjtgkc(String rjtgkc) {
		this.rjtgkc = rjtgkc;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}