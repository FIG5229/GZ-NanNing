/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 体格检查信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelBuild extends DataEntity<PersonnelBuild> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "体检日期", type = 0, align = 2, sort = 2)
	private Date date;		// 体检日期
	@ExcelField(title = "血型", type = 0, align = 2, sort = 3)
	private String bloodType;		// 血型
	@ExcelField(title = "身高(cm)", type = 0, align = 2, sort = 4)
	private Float height;		// 身高(cm)
	@ExcelField(title = "左眼视力", type = 0, align = 2, sort = 5)
	private String leftVision;		// 左眼视力
	@ExcelField(title = "右眼视力", type = 0, align = 2, sort = 6)
	private String rightVision;		// 右眼视力
	@ExcelField(title = "脉搏(次/分)", type = 0, align = 2, sort = 7)
	private String pulse;		// 脉搏(次/分)
	@ExcelField(title = "体重(kg)", type = 0, align = 2, sort = 8)
	private Double weight;		// 体重(kg)
	@ExcelField(title = "血压(mmHg)", type = 0, align = 2, sort = 9)
	private String bloodPressure;		// 血压(mmHg)
	@ExcelField(title = "主要既往病史", type = 0, align = 2, sort = 10)
	private String medicalHistory;		// 主要既往病史
	@ExcelField(title = "享受医疗待遇", type = 0, align = 2, sort = 11)
	private String treatment;		// 享受医疗待遇
	@ExcelField(title = "体检医院名称", type = 0, align = 2, sort = 12)
	private String hospitalName;		// 体检医院名称
	@ExcelField(title = "外科", type = 0, align = 2, sort = 13)
	private String surgery;		// 外科
	@ExcelField(title = "内科", type = 0, align = 2, sort = 14)
	private String internalMedicine;		// 内科
	@ExcelField(title = "五官科", type = 0, align = 2, sort = 15)
	private String otorhinolaryngology;		// 五官科
	@ExcelField(title = "妇科", type = 0, align = 2, sort = 16)
	private String gynaecology;		// 妇科
	@ExcelField(title = "生化检查", type = 0, align = 2, sort = 17)
	private String biochemistry;		// 生化检查
	@ExcelField(title = "胸透、B超", type = 0, align = 2, sort = 18)
	private String xRayB;		// 胸透、B超
	@ExcelField(title = "特殊检查", type = 0, align = 2, sort = 19)
	private String specialCheck;		// 特殊检查
	@ExcelField(title = "体检结论", type = 0, align = 2, sort = 20)
	private String conclusion;		// 体检结论
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelBuild() {
		super();
	}

	public PersonnelBuild(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	
	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}
	
	public String getLeftVision() {
		return leftVision;
	}

	public void setLeftVision(String leftVision) {
		this.leftVision = leftVision;
	}
	
	public String getRightVision() {
		return rightVision;
	}

	public void setRightVision(String rightVision) {
		this.rightVision = rightVision;
	}
	
	public String getPulse() {
		return pulse;
	}

	public void setPulse(String pulse) {
		this.pulse = pulse;
	}
	
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	
	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	public String getSurgery() {
		return surgery;
	}

	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}
	
	public String getInternalMedicine() {
		return internalMedicine;
	}

	public void setInternalMedicine(String internalMedicine) {
		this.internalMedicine = internalMedicine;
	}
	
	public String getOtorhinolaryngology() {
		return otorhinolaryngology;
	}

	public void setOtorhinolaryngology(String otorhinolaryngology) {
		this.otorhinolaryngology = otorhinolaryngology;
	}
	
	public String getGynaecology() {
		return gynaecology;
	}

	public void setGynaecology(String gynaecology) {
		this.gynaecology = gynaecology;
	}
	
	public String getBiochemistry() {
		return biochemistry;
	}

	public void setBiochemistry(String biochemistry) {
		this.biochemistry = biochemistry;
	}
	
	public String getSpecialCheck() {
		return specialCheck;
	}

	public void setSpecialCheck(String specialCheck) {
		this.specialCheck = specialCheck;
	}
	
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
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

	public String getXRayB() {
		return xRayB;
	}

	public void setXRayB(String xRayB) {
		this.xRayB = xRayB;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
}