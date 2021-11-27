/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 临时抽调民警管理Entity
 * @author mason.xv
 * @version 2019-11-06
 */
public class AffairTemporaryPolice extends DataEntity<AffairTemporaryPolice> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "警号", type = 0, align = 2, sort = 1)
	private String policeNo;		// 警号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "所属单位", type = 0, align = 2, sort = 4)
	private String ofUnit;		// 所属单位
	@ExcelField(title = "抽调单位", type = 0, align = 2, sort = 5)
	private String cdUnit;		// 抽调单位
	private String ofUnitId;		// 所属单位id
	private String cdUnitId;		// 抽调单位id
	@ExcelField(title = "抽调时间", type = 0, align = 2, sort = 6)
	private Date cdDate;		// 抽调时间
	@ExcelField(title = "截至时间", type = 0, align = 2, sort = 7)
	private Date endDate;		// 截至时间
	@ExcelField(title = "抽调事由", type = 0, align = 2, sort = 8)
	private String reason;		// 抽调事由

	private String samplingForm;


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String forthCheckMan;
	private String threeCheckMan;
	private String twoCheckMan;
	private String oneCheckMan;
	private String submitMan;
	private String checkType;
	private String forthCheckId;
	private String threeCheckId;
	private String twoCheckId;
	private String oneCheckId;
	private String submitId;
	private String chuOpinion;
	private String juOpinion;
	private String chuLdOpinion;
	private String juLdOpinion;
	private String userId;
	private String fiveCheckId;
	private String fiveCheckMan;

	private String officeId;
	private boolean isLd;


	
	public AffairTemporaryPolice() {
		super();
	}

	public AffairTemporaryPolice(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getOfUnit() {
		return ofUnit;
	}

	public void setOfUnit(String ofUnit) {
		this.ofUnit = ofUnit;
	}
	
	public String getCdUnit() {
		return cdUnit;
	}

	public void setCdUnit(String cdUnit) {
		this.cdUnit = cdUnit;
	}
	
	public String getOfUnitId() {
		return ofUnitId;
	}

	public void setOfUnitId(String ofUnitId) {
		this.ofUnitId = ofUnitId;
	}
	
	public String getCdUnitId() {
		return cdUnitId;
	}

	public void setCdUnitId(String cdUnitId) {
		this.cdUnitId = cdUnitId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCdDate() {
		return cdDate;
	}

	public void setCdDate(Date cdDate) {
		this.cdDate = cdDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getForthCheckMan() {
		return forthCheckMan;
	}

	public void setForthCheckMan(String forthCheckMan) {
		this.forthCheckMan = forthCheckMan;
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

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getForthCheckId() {
		return forthCheckId;
	}

	public void setForthCheckId(String forthCheckId) {
		this.forthCheckId = forthCheckId;
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

	public String getChuOpinion() {
		return chuOpinion;
	}

	public void setChuOpinion(String chuOpinion) {
		this.chuOpinion = chuOpinion;
	}

	public String getJuOpinion() {
		return juOpinion;
	}

	public void setJuOpinion(String juOpinion) {
		this.juOpinion = juOpinion;
	}

	public String getChuLdOpinion() {
		return chuLdOpinion;
	}

	public void setChuLdOpinion(String chuLdOpinion) {
		this.chuLdOpinion = chuLdOpinion;
	}

	public String getJuLdOpinion() {
		return juLdOpinion;
	}

	public void setJuLdOpinion(String juLdOpinion) {
		this.juLdOpinion = juLdOpinion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSamplingForm() {
		return samplingForm;
	}

	public void setSamplingForm(String samplingForm) {
		this.samplingForm = samplingForm;
	}

	public String getFiveCheckId() {
		return fiveCheckId;
	}

	public void setFiveCheckId(String fiveCheckId) {
		this.fiveCheckId = fiveCheckId;
	}

	public String getFiveCheckMan() {
		return fiveCheckMan;
	}

	public void setFiveCheckMan(String fiveCheckMan) {
		this.fiveCheckMan = fiveCheckMan;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public boolean isLd() {
		return isLd;
	}

	public void setLd(boolean ld) {
		isLd = ld;
	}

}