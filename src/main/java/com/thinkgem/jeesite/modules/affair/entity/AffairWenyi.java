/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 文艺作品Entity
 * @author kevin.jia
 * @version 2020-08-03
 */
public class AffairWenyi extends DataEntity<AffairWenyi> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "作品名称",type = 0,align = 2,sort = 1)
	private String proName;		// 作品名称
	@ExcelField(title = "类别",type = 0,align = 2,sort = 2,dictType = "affair_wenyi_type")
	private String type;		// 类别
	@ExcelField(title = "作者姓名",type = 0,align = 2,sort = 3)
	private String peoName;		// 作者姓名
	@ExcelField(title = "作品内容",type = 0,align = 2,sort = 4)
	private String proContent;		// 作品内容
	@ExcelField(title = "单位名称",type = 0,align = 2,sort = 5)
	private String unitName;		// 单位名称
	private String unitId;		// 单位id
	@ExcelField(title = "奖项名称",type = 0,align = 2,sort = 6)
	private String awardName;		// 奖项名称
	@ExcelField(title = "奖项级别",type = 0,align = 2,sort = 7,dictType = "affair_wenyi_level")
	private String awardLevel;		// 奖项级别
	@ExcelField(title = "批准单位",type = 0,align = 2,sort = 8)
	private String ratifyUnit;		// 批准单位
	@ExcelField(title = "批准时间",type = 0,align = 2,sort = 9)
	private Date ratifyTime;		// 批准时间
	@ExcelField(title = "采用媒体及版面",type = 0,align = 2,sort = 10)
	private String adoptMedia;		// 采用媒体及版面
	@ExcelField(title = "采用时间",type = 0,align = 2,sort = 11)
	private Date adoptTime;		// 采用时间
	private String appendfile;		// 附件
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

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;
	private Date startDate;
	private Date endDate;

	public AffairWenyi() {
		super();
	}

	public AffairWenyi(String id){
		super(id);
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPeoName() {
		return peoName;
	}

	public void setPeoName(String peoName) {
		this.peoName = peoName;
	}
	
	public String getProContent() {
		return proContent;
	}

	public void setProContent(String proContent) {
		this.proContent = proContent;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	
	public String getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(String awardLevel) {
		this.awardLevel = awardLevel;
	}
	
	public String getRatifyUnit() {
		return ratifyUnit;
	}

	public void setRatifyUnit(String ratifyUnit) {
		this.ratifyUnit = ratifyUnit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRatifyTime() {
		return ratifyTime;
	}

	public void setRatifyTime(Date ratifyTime) {
		this.ratifyTime = ratifyTime;
	}
	
	public String getAdoptMedia() {
		return adoptMedia;
	}

	public void setAdoptMedia(String adoptMedia) {
		this.adoptMedia = adoptMedia;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAdoptTime() {
		return adoptTime;
	}

	public void setAdoptTime(Date adoptTime) {
		this.adoptTime = adoptTime;
	}

	public String getAppendfile() {
		return appendfile;
	}

	public void setAppendfile(String appendfile) {
		this.appendfile = appendfile;
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