/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 民警休养申报Entity
 * @author cecil.li
 * @version 2019-11-05
 */
public class AffairMjxyReport extends DataEntity<AffairMjxyReport> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;		//序号
	@ExcelField(title = "休养开始时间", type = 0, align = 2, sort = 1)
	private Date startDate;		// 休养开始时间
	@ExcelField(title = "休养结束时间", type = 0, align = 2, sort = 2)
	private Date endDate;		// 休养结束时间
	@ExcelField(title = "休养类型", type = 0, align = 2, sort = 3,dictType = "affair_xiuyang")
	private String type;		// 休养类型
	private String xyName;		//疗(休)养名称
	private String isFamily;    //是否带家属
	@ExcelField(title = "休养地点", type = 0, align = 2, sort = 4)
	private String place;		// 休养地点
	private String placeAdd;    //休养地点(补充)
	@ExcelField(title = "单位", type = 0, align = 2, sort = 5)
	private String unit;		// 单位
	private String unitId;		// 单位id
	private String unitLevel;		// 单位级别
	private String unitClassify;		// 单位类别
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 6)
	private String name;		// 姓名
	@ExcelField(title = "职务", type = 0, align = 2, sort = 8)
	private String job;		// 职务
	@ExcelField(title = "性别", type = 0, align = 2, sort = 7,dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 9)
	private String idNumber;		// 身份证号
	@ExcelField(title = "免票号", type = 0, align = 2, sort = 10)
	private String freeTicketNo;		// 免票号
	@ExcelField(title = "联系方式", type = 0, align = 2, sort = 11)
	private String contactMethod;		// 联系方式
	private String remark;		// 备注
	private String opinion;		// 审核意见

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String checkType;    //审核状态
	private String threeCheckMan;    //三级审核人
	private String twoCheckMan;        //二级审核人
	private String oneCheckMan;        //一级审核人
	private String submitMan;       //提交人
	private String threeCheckId;     //三级审核人id
	private String twoCheckId;       //二级审核人id
	private String oneCheckId;       //一级审核人id
	private String submitId;      //提交人id
	private String userId;

	private Date beginDate;
	private Date finishDate;
	private Date beginStartDate;
	private Date finishStartDate;
	//排序
	private String sort;	//1:升序 2：降序 3：默认

	//统计的人数
	private String num;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getXyName() {
		return xyName;
	}

	public void setXyName(String xyName) {
		this.xyName = xyName;
	}

	public String getUnitLevel() {
		return unitLevel;
	}

	public void setUnitLevel(String unitLevel) {
		this.unitLevel = unitLevel;
	}

	public String getUnitClassify() {
		return unitClassify;
	}

	public void setUnitClassify(String unitClassify) {
		this.unitClassify = unitClassify;
	}

	public AffairMjxyReport() {
		super();
	}

	public AffairMjxyReport(String id){
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

	public String getPlaceAdd() {
		return placeAdd;
	}

	public void setPlaceAdd(String placeAdd) {
		this.placeAdd = placeAdd;
	}

	public String getIsFamily() {
		return isFamily;
	}

	public void setIsFamily(String isFamily) {
		this.isFamily = isFamily;
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
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginStartDate() {
		return beginStartDate;
	}

	public void setBeginStartDate(Date beginStartDate) {
		this.beginStartDate = beginStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishStartDate() {
		return finishStartDate;
	}

	public void setFinishStartDate(Date finishStartDate) {
		this.finishStartDate = finishStartDate;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getThreeCheckMan() {
		return threeCheckMan;
	}

	public void setThreeCheckMan(String threeCheckMan) {
		this.threeCheckMan = threeCheckMan;
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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}