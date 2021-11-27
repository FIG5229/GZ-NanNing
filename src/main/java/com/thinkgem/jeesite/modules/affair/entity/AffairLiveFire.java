/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 实弹射击Entity
 * @author cecil.li
 * @version 2020-12-28
 */
public class AffairLiveFire extends DataEntity<AffairLiveFire> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private String yearMonth;  // 时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "身份证", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证
	@ExcelField(title = "年龄", type = 0, align = 2, sort = 3)
	private String age;		// 年龄
	@ExcelField(title = "性别", type = 0, align = 2, sort = 4, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "单位", type = 0, align = 2, sort = 5)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 6)
	private String job;		// 职务
	@ExcelField(title = "枪型", type = 0, align = 2, sort = 7)
	private String gunType;		// 枪型
	@ExcelField(title = "十环", type = 0, align = 2, sort = 8)
	private String tenRings;		// 十环
	@ExcelField(title = "九环", type = 0, align = 2, sort = 9)
	private String nineRings;		// 九环
	@ExcelField(title = "八环", type = 0, align = 2, sort = 10)
	private String eightRings;		// 八环
	@ExcelField(title = "七环", type = 0, align = 2, sort = 11)
	private String sevenRings;		// 七环
	@ExcelField(title = "六环", type = 0, align = 2, sort = 12)
	private String sixRings;		// 六环
	@ExcelField(title = "五环", type = 0, align = 2, sort = 13)
	private String fiveRings;		// 五环
	@ExcelField(title = "总环数", type = 0, align = 2, sort = 14)
	private String sumRings;		// 总环数
	@ExcelField(title = "是否合格", type = 0, align = 2, sort = 15, dictType = "yes_no")
	private String isQualified;		// 是否合格
	@ExcelField(title = "备注", type = 0, align = 2, sort = 16)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairLiveFire() {
		super();
	}

	public AffairLiveFire(String id){
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
	
	public String getGunType() {
		return gunType;
	}

	public void setGunType(String gunType) {
		this.gunType = gunType;
	}
	
	public String getTenRings() {
		return tenRings;
	}

	public void setTenRings(String tenRings) {
		this.tenRings = tenRings;
	}
	
	public String getNineRings() {
		return nineRings;
	}

	public void setNineRings(String nineRings) {
		this.nineRings = nineRings;
	}
	
	public String getEightRings() {
		return eightRings;
	}

	public void setEightRings(String eightRings) {
		this.eightRings = eightRings;
	}
	
	public String getSevenRings() {
		return sevenRings;
	}

	public void setSevenRings(String sevenRings) {
		this.sevenRings = sevenRings;
	}
	
	public String getSixRings() {
		return sixRings;
	}

	public void setSixRings(String sixRings) {
		this.sixRings = sixRings;
	}
	
	public String getFiveRings() {
		return fiveRings;
	}

	public void setFiveRings(String fiveRings) {
		this.fiveRings = fiveRings;
	}
	
	public String getSumRings() {
		return sumRings;
	}

	public void setSumRings(String sumRings) {
		this.sumRings = sumRings;
	}
	
	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
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
}