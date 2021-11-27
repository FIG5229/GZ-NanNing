/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 领导廉政干部档案表Entity
 * @author eav.liu
 * @version 2020-03-04
 */
public class AffairLdgblzFile extends DataEntity<AffairLdgblzFile> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 2, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "工作单位", type = 0, align = 2, sort = 3)
	private String unit;   //工作单位
	private String unitId;   //工作单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String workUnitJob;		// 职务
	@ExcelField(title = "职务层次", type = 0, align = 2, sort = 5)
	private String jobLevel;  // 职务层次
	@ExcelField(title = "职级", type = 0, align = 2, sort = 6)
	private String level;		// 职级
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 7)
	private Date birthday;		// 出生年月
	@ExcelField(title = "入党时间", type = 0, align = 2, sort = 8)
	private Date rdDate;		// 入党时间
	@ExcelField(title = "学历", type = 0, align = 2, sort = 9)
	private String education;		// 学历
	@ExcelField(title = "籍贯", type = 0, align = 2, sort = 10)
	private String nativePlace;		// 籍贯

	@ExcelField(title = "惩处情况", type = 0, align = 2, sort = 11)
	private String chchqk;		// 惩处情况
	@ExcelField(title = "拒礼拒贿、上交礼金礼品情况", type = 0, align = 2, sort = 12)
	private String jlqk;		// 拒礼拒贿、上交礼金礼品情况
	@ExcelField(title = "操办婚丧喜庆事宜情况", type = 0, align = 2, sort = 13)
	private String cbqk;		// 操办婚丧喜庆事宜情况
	@ExcelField(title = "个人事项报告情况", type = 0, align = 2, sort = 14)
	private String grqk;		// 个人事项报告情况
	@ExcelField(title = "学廉考廉情况", type = 0, align = 2, sort = 15)
	private String xlqk;		// 学廉考廉情况
	@ExcelField(title = "落实党风廉政建设责任制考核情况", type = 0, align = 2, sort = 16)
	private String lshqk;		// 落实党风廉政建设责任制考核情况
	@ExcelField(title = "述职述廉情况", type = 0, align = 2, sort = 17)
	private String shzhqk;		// 述职述廉情况
	@ExcelField(title = "民主评议和民主测评情况", type = 0, align = 2, sort = 18)
	private String mzhqk;		// 民主评议和民主测评情况
	@ExcelField(title = "审计结论", type = 0, align = 2, sort = 19)
	private String shjjl;		// 审计结论
	@ExcelField(title = "问题线索查核情况", type = 0, align = 2, sort = 20)
	private String wtqk;		// 问题线索查核情况
	@ExcelField(title = "其他情况", type = 0, align = 2, sort = 21)
	private String qtqk;		// 其他情况
	private String createOrgId;		// 创建者机构id
    private String updateOrgId;		// 更新者机构id
	private String annex;     //附件

	//前端
	private Date birthdayStart;
	private Date birthdayEnd;
	private Date rdDateStart;
	private Date rdDateEnd;
	public AffairLdgblzFile() {
		super();
	}

	public AffairLdgblzFile(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getWorkUnitJob() {
		return workUnitJob;
	}

	public void setWorkUnitJob(String workUnitJob) {
		this.workUnitJob = workUnitJob;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getRdDate() {
		return rdDate;
	}

	public void setRdDate(Date rdDate) {
		this.rdDate = rdDate;
	}
	
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getChchqk() {
		return chchqk;
	}

	public void setChchqk(String chchqk) {
		this.chchqk = chchqk;
	}

	public String getJlqk() {
		return jlqk;
	}

	public void setJlqk(String jlqk) {
		this.jlqk = jlqk;
	}
	
	public String getCbqk() {
		return cbqk;
	}

	public void setCbqk(String cbqk) {
		this.cbqk = cbqk;
	}
	
	public String getGrqk() {
		return grqk;
	}

	public void setGrqk(String grqk) {
		this.grqk = grqk;
	}
	
	public String getXlqk() {
		return xlqk;
	}

	public void setXlqk(String xlqk) {
		this.xlqk = xlqk;
	}
	
	public String getLshqk() {
		return lshqk;
	}

	public void setLshqk(String lshqk) {
		this.lshqk = lshqk;
	}
	
	public String getShzhqk() {
		return shzhqk;
	}

	public void setShzhqk(String shzhqk) {
		this.shzhqk = shzhqk;
	}
	
	public String getMzhqk() {
		return mzhqk;
	}

	public void setMzhqk(String mzhqk) {
		this.mzhqk = mzhqk;
	}

	public String getShjjl() {
		return shjjl;
	}

	public void setShjjl(String shjjl) {
		this.shjjl = shjjl;
	}

	public String getWtqk() {
		return wtqk;
	}

	public void setWtqk(String wtqk) {
		this.wtqk = wtqk;
	}
	
	public String getQtqk() {
		return qtqk;
	}

	public void setQtqk(String qtqk) {
		this.qtqk = qtqk;
	}

	public Date getBirthdayStart() {
		return birthdayStart;
	}

	public void setBirthdayStart(Date birthdayStart) {
		this.birthdayStart = birthdayStart;
	}

	public Date getBirthdayEnd() {
		return birthdayEnd;
	}

	public void setBirthdayEnd(Date birthdayEnd) {
		this.birthdayEnd = birthdayEnd;
	}

	public Date getRdDateStart() {
		return rdDateStart;
	}

	public void setRdDateStart(Date rdDateStart) {
		this.rdDateStart = rdDateStart;
	}

	public Date getRdDateEnd() {
		return rdDateEnd;
	}

	public void setRdDateEnd(Date rdDateEnd) {
		this.rdDateEnd = rdDateEnd;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

    public String getUpdateOrgId() {
        return updateOrgId;
    }

    public void setUpdateOrgId(String updateOrgId) {
        this.updateOrgId = updateOrgId;
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

	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
}