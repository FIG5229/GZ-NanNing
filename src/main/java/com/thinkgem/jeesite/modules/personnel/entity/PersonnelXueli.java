/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 学历信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelXueli extends DataEntity<PersonnelXueli> {
	
	private static final long serialVersionUID = 1L;
    @ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
    @ExcelField(title = "学历名称", type = 0, align = 2, sort = 1)
	private String name;		// 学历名称
    @ExcelField(title = "学校(单位)名称", type = 0, align = 2, sort = 2)
	private String schoolName;		// 学校(单位)名称
    @ExcelField(title = "公安专业技术分类", type = 0, align = 2, sort = 3)
	private String classify;		// 公安专业技术分类
    @ExcelField(title = "入学日期", type = 0, align = 2, sort = 4)
	private Date startDate;		// 入学日期
    @ExcelField(title = "毕(肄)业日期", type = 0, align = 2, sort = 5)
	private Date endDate;		// 毕(肄)业日期
    @ExcelField(title = "学制(年)", type = 0, align = 2, sort = 6)
	private String year;		// 学制(年)
    @ExcelField(title = "所学专业代码", type = 0, align = 2, sort = 7)
	private String majorName;		// 所学专业代码
    @ExcelField(title = "所学专业名称", type = 0, align = 2, sort = 8)
	private String majorCode;		// 所学专业名称
    @ExcelField(title = "从学类别", type = 0, align = 2, sort = 9, dictType="personnel_cxtype")
	private String type1;		// 从学类别
    @ExcelField(title = "最高学历说明", type = 0, align = 2, sort = 10)
	private String explain;		// 最高学历说明
    @ExcelField(title = "记录状态", type = 0, align = 2, sort = 11, dictType="personnel_jltype")
	private String status;		// 记录状态
    @ExcelField(title = "多学历序号", type = 0, align = 2, sort = 12)
	private String sequenceNo;		// 多学历序号
    @ExcelField(title = "学校(单位)所在政区", type = 0, align = 2, sort = 13)
	private String schoolArea;		// 学校(单位)所在政区
    @ExcelField(title = "毕业院校类别", type = 0, align = 2, sort = 14)
	private String type2;		// 毕业院校类别
    @ExcelField(title = "毕(肄)业证书编号", type = 0, align = 2, sort = 15)
	private String certificateNo;		// 毕(肄)业证书编号
    @ExcelField(title = "学习形式", type = 0, align = 2, sort = 16)
	private String modality;		// 学习形式
    @ExcelField(title = "学习完成情况", type = 0, align = 2, sort = 17, dictType="personnel_xuexi")
	private String completeSituation;		// 学习完成情况
    @ExcelField(title = "学历描述", type = 0, align = 2, sort = 18)
	private String describe;		// 学历描述
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Integer level;  // 学历级别
	private String personName;  // 姓名

	private Date beginDate;
	private Date finishDate;

	/*统计分析使用*/
	private String label;
	private String unitId;
	private String unit;


	public PersonnelXueli() {
		super();
	}

	public PersonnelXueli(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
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
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	
	public String getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}
	
	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}
	
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	public String getSchoolArea() {
		return schoolArea;
	}

	public void setSchoolArea(String schoolArea) {
		this.schoolArea = schoolArea;
	}
	
	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}
	
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}
	
	public String getCompleteSituation() {
		return completeSituation;
	}

	public void setCompleteSituation(String completeSituation) {
		this.completeSituation = completeSituation;
	}
	
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}