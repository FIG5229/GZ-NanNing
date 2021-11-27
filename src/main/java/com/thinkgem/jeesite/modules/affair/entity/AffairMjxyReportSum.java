/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 民警休养汇总Entity
 * @author mason.xv
 * @version 2020-04-14
 */
public class AffairMjxyReportSum extends DataEntity<AffairMjxyReportSum> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;		//序号
	@ExcelField(title = "休养开始时间", type = 0, align = 2, sort = 1)
	private Date startDate;		// 休养开始时间
	@ExcelField(title = "休养结束时间", type = 0, align = 2, sort = 2)
	private Date endDate;		// 休养结束时间
	private String type;		// 休养类型
	private String isFamily;    //是否带家属
	private String place;		// 休养地点
	private String placeAdd;    //休养地点(补充)
	@ExcelField(title = "单位", type = 0, align = 2, sort = 3)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 4)
	private String name;		// 姓名
	@ExcelField(title = "职务", type = 0, align = 2, sort = 6)
	private String job;		// 职务
	@ExcelField(title = "性别", type = 0, align = 2, sort = 5)
	private String sex;		// 性别
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 7)
	private String idNumber;		// 身份证号
	@ExcelField(title = "免票号", type = 0, align = 2, sort = 8)
	private String freeTicketNo;		// 免票号
	@ExcelField(title = "联系方式", type = 0, align = 2, sort = 9)
	private String contactMethod;		// 联系方式
	private String remark;		// 备注

	private String opinion;		// 审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String threeCheckMan;		// 局审核人
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String twoCheckMan;		// 处审核人
	private String oneCheckMan;		// 所审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态（0-4/提交）
	private String threeCheckId;		// 审核局id
	private String twoCheckId;		// 审核处id
	private String oneCheckId;		// 审核单位id
	private String submitId;		// 提交人id
	private String userId;    //用户id

	/*添加按时间查询*/
	private Date beginDate;
	private Date finishDate;
	private Date beginStartDate;
	private Date finishStartDate;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	private List<AffairMjxyReportSum> childrens;

	public List<AffairMjxyReportSum> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<AffairMjxyReportSum> childrens) {
		this.childrens = childrens;
	}

	public AffairMjxyReportSum() {
		super();
	}

	public AffairMjxyReportSum(String id){
		super(id);
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getFreeTicketNo() {
		return freeTicketNo;
	}

	public void setFreeTicketNo(String freeTicketNo) {
		this.freeTicketNo = freeTicketNo;
	}
	
	public String getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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
	
	public String getIsFamily() {
		return isFamily;
	}

	public void setIsFamily(String isFamily) {
		this.isFamily = isFamily;
	}
	
	public String getPlaceAdd() {
		return placeAdd;
	}

	public void setPlaceAdd(String placeAdd) {
		this.placeAdd = placeAdd;
	}
	
	public String getThreeCheckMan() {
		return threeCheckMan;
	}

	public void setThreeCheckMan(String threeCheckMan) {
		this.threeCheckMan = threeCheckMan;
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
	
	public String getTwoCheckMan() {
		return twoCheckMan;
	}

	public void setTwoCheckMan(String twoCheckMan) {
		this.twoCheckMan = twoCheckMan;
	}
	
	public String getOneCheckMan() {
		return oneCheckMan;
	}

	public void setOneCheckMan(String oneCheckMan) {
		this.oneCheckMan = oneCheckMan;
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
	
	public String getThreeCheckId() {
		return threeCheckId;
	}

	public void setThreeCheckId(String threeCheckId) {
		this.threeCheckId = threeCheckId;
	}
	
	public String getTwoCheckId() {
		return twoCheckId;
	}

	public void setTwoCheckId(String twoCheckId) {
		this.twoCheckId = twoCheckId;
	}
	
	public String getOneCheckId() {
		return oneCheckId;
	}

	public void setOneCheckId(String oneCheckId) {
		this.oneCheckId = oneCheckId;
	}
	
	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getBeginStartDate() {
		return beginStartDate;
	}

	public void setBeginStartDate(Date beginStartDate) {
		this.beginStartDate = beginStartDate;
	}

	public Date getFinishStartDate() {
		return finishStartDate;
	}

	public void setFinishStartDate(Date finishStartDate) {
		this.finishStartDate = finishStartDate;
	}
}