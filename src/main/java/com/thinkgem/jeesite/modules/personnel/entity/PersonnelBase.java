/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsIgnore;
import com.thinkgem.jeesite.common.elasticsearch.annotation.HighLight;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryAction;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalReward;

import java.util.Date;
import java.util.List;

/**
 * 人员基本情况信息Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelBase extends DataEntity<PersonnelBase> {

	private static final long serialVersionUID = 1L;
	@HighLight
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		            // 姓名
	@HighLight
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 17)
	private String idNumber;		        // 身份证号
	@ExcelField(title = "民族", type = 0, align = 2, sort = 2, dictType="nation")
	private String nation;		            // 民族
	@ExcelField(title = "出生日期", type = 0, align = 2, sort = 3)
	private Date birthday;		            // 出生日期
	@ExcelField(title = "人员状态", type = 0, align = 2, sort = 4, dictType = "personnel_status")
	private String status;		            // 人员状态
	@ExcelField(title = "血型", type = 0, align = 2, sort = 5)
	private String bloodType;		        // 血型
    @ExcelField(title = "警员库标志", type = 0, align = 2, sort = 6, dictType="yes_no")
	private String policeDepotSign;		    // 警员库标志
	@ExcelField(title = "警号", type = 0, align = 2, sort = 7)
	@HighLight
	private String policeIdNumber;		    // 警号
	@ExcelField(title = "人员类别", type = 0, align = 2, sort = 8)
	private String personnelType;		    // 人员类别
	@ExcelField(title = "籍贯", type = 0, align = 2, sort = 9)
	private String nativePlace;		        // 籍贯
	@ExcelField(title = "出生地", type = 0, align = 2, sort = 12)
	private String birthPlace;		        // 出生地
	@ExcelField(title = "成长地", type = 0, align = 2, sort = 13)
	private String growPlace;		        // 成长地
	@ExcelField(title = "户口性质", type = 0, align = 2, sort = 14)
	private String populationCharacter;		// 户口性质
	@ExcelField(title = "户籍所在地", type = 0, align = 2, sort = 15)
	private String hjszd;		            // 户籍所在地
	@ExcelField(title = "个人身份", type = 0, align = 2, sort = 16)
	private String identity;		        // 个人身份
    @ExcelField(title = "婚姻", type = 0, align = 2, sort = 18, dictType="affair_marital_status")
	private String marriageStatus;		    // 婚姻状况
    @ExcelField(title = "涉密标志", type = 0, align = 2, sort = 19, dictType="yes_no")
	private String secretStatus;		    // 涉密标志
	@ExcelField(title = "健康状态", type = 0, align = 2, sort = 20)
	private String healthStatus;		    // 健康状态
	@ExcelField(title = "户籍所在地详址", type = 0, align = 2, sort = 21)
	private String hjszdxz;		            // 户籍所在地详址
	@ExcelField(title = "暂缓列入套改实施范围原因类别", type = 0, align = 2, sort = 22)
	private String reason;		            // 暂缓列入套改实施范围原因类别
	@ExcelField(title = "参加工作日期", type = 0, align = 2, sort = 23)
	private Date workDate;		            // 参加工作日期
	@ExcelField(title = "参加公安工作日期", type = 0, align = 2, sort = 24)
	private Date publicSecurityDate;		// 参加公安工作日期
	@ExcelField(title = "基层工作经历时间", type = 0, align = 2, sort = 25)
	private String jcgzjlsj;		        // 基层工作经历时间
	@ExcelField(title = "工龄计算校正值", type = 0, align = 2, sort = 26)
	private String gljsjzz;		            // 工龄计算校正值
	@ExcelField(title = "警衔应加学制年限", type = 0, align = 2, sort = 27)
	private String jxyjxznx;		        // 警衔应加学制年限
    @ExcelField(title = "政治面貌", type = 0, align = 2, sort = 28, dictType="political_status")
	private String politicsFace;		    // 政治面貌
	@ExcelField(title = "参加组织日期", type = 0, align = 2, sort = 29)
	private Date organizationDate;		    // 参加组织日期
	@ExcelField(title = "工作单位代码", type = 0, align = 2, sort = 30)
	private String workunitCode;		    // 工作单位代码
	@ExcelField(title = "工作单位名称", type = 0, align = 2, sort = 31)
	private String workunitName;		    // 工作单位名称
	private String workunitId;              //工作单位ID
	@ExcelField(title = "工作单位（实际）", type = 0, align = 2, sort = 32)
	private String actualUnit;
	private String actualUnitId;
	@ExcelField(title = "单位代码", type = 0, align = 2, sort = 33)
	private String unitCode;		        // 单位代码

	@ExcelField(title = "关系所在单位", type = 0, align = 2, sort = 34)
	private String relationshipUnit;		// 关系所在单位
	@ExcelField(title = "人员所属部门和警种", type = 0, align = 2, sort = 35)
	private String bmhjz;		            // 人员所属部门和警种
	@ExcelField(title = "职务简称", type = 0, align = 2, sort = 36)
	private String jobAbbreviation;		    // 职务简称
	@ExcelField(title = "职务全称", type = 0, align = 2, sort = 37)
	private String jobFullname;		        // 职务全称
    @ExcelField(title = "人员工作岗位", type = 0, align = 2, sort = 38, dictType="personnel_gw")
	private String job;		                // 人员工作岗位
	@ExcelField(title = "协管干部标识", type = 0, align = 2, sort = 39)
	private String xggbbs;		            // 协管干部标识
    @ExcelField(title = "是否是协管干部", type = 0, align = 2, sort = 40, dictType="yes_no")
	private String isXggb;		            // 是否是协管干部
	@ExcelField(title = "管理类别", type = 0, align = 2, sort = 41)
	private String category;		        // 管理类别
	@ExcelField(title = "专长", type = 0, align = 2, sort = 42)
	private String expertise;		        // 专长
	@ExcelField(title = "奖励综述", type = 0, align = 2, sort = 43)
	private String award;		            // 奖励综述
	@ExcelField(title = "年度考核综述", type = 0, align = 2, sort = 44)
	private String assessment;		        // 年度考核综述

	private String photo;		            // 人员照片
	@ExcelField(title = "学历", type = 0, align = 2, sort = 10)
	private String education;               // 学历
	@ExcelField(title = "电话", type = 0, align = 2, sort = 11)
	private String phoneNumber;             // 电话

	private String createOrgId;		        // 创建者机构id

	private String createIdNo;		        // 创建者

	private String updateOrgId;		        // 更新者机构id

	private String updateIdNo;		        // 更新者身份证号
	@ExcelField(title = "备注", type = 0, align = 2, sort = 45)
	private String remarks;
	@ExcelField(title = "性别", type = 0, align = 2, sort = 1, dictType="sex")
	private String sex;						//

	private Date startDate;					//

	private Date endDate;					//

	private String editWorkUnitName;        // 修改工作单位名称

	private String editWorkUnitId;          // 修改工作单位ID

	private String editPersonIds;           // 要修改工作单位的数据Id

	@EsIgnore
	private List<AffairPersonalReward> affairPersonalRewards;		//奖励类list

	@EsIgnore
	private List<AffairDisciplinaryAction> affairDisciplinaryActions;	//惩戒类list


	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;
	private String unitId;
	private String ageType;
	private Integer age;
	private Integer startAge;
	private Integer endAge;

	private String flag;   // 在职非在职标志

	private String officeId;

	private String peopleStatus;

	@ExcelField(title = "排序", type = 0, align = 2, sort = 46)
	private String sort;	//排序

	private String juFlag;

	private String chuFlag;

	private String minFlag;


	public List<AffairPersonalReward> getAffairPersonalRewards() {
		return affairPersonalRewards;
	}

	public void setAffairPersonalRewards(List<AffairPersonalReward> affairPersonalRewards) {
		this.affairPersonalRewards = affairPersonalRewards;
	}

	public List<AffairDisciplinaryAction> getAffairDisciplinaryActions() {
		return affairDisciplinaryActions;
	}

	public void setAffairDisciplinaryActions(List<AffairDisciplinaryAction> affairDisciplinaryActions) {
		this.affairDisciplinaryActions = affairDisciplinaryActions;
	}

	/*private String rewardName;		//奖励名称

	private String rewardApprovalDocument;		//奖励文号

	private String rewardClassify;			//奖励类别*/


	/*private String punishmentApprovalDocument;		//惩戒文号

	private String punishmentClassiry;		//处分类别

	private String problemTypes;			//问题类别*/


	/*public String getPunishmentApprovalDocument() {
		return punishmentApprovalDocument;
	}

	public void setPunishmentApprovalDocument(String punishmentApprovalDocument) {
		this.punishmentApprovalDocument = punishmentApprovalDocument;
	}

	public String getPunishmentClassiry() {
		return punishmentClassiry;
	}

	public void setPunishmentClassiry(String punishmentClassiry) {
		this.punishmentClassiry = punishmentClassiry;
	}

	public String getProblemTypes() {
		return problemTypes;
	}

	public void setProblemTypes(String problemTypes) {
		this.problemTypes = problemTypes;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getRewardApprovalDocument() {
		return rewardApprovalDocument;
	}

	public void setRewardApprovalDocument(String rewardApprovalDocument) {
		this.rewardApprovalDocument = rewardApprovalDocument;
	}

	public String getRewardClassify() {
		return rewardClassify;
	}

	public void setRewardClassify(String rewardClassify) {
		this.rewardClassify = rewardClassify;
	}*/

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public PersonnelBase() {
		super();
	}

	public PersonnelBase(String id){
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

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getPoliceDepotSign() {
		return policeDepotSign;
	}

	public void setPoliceDepotSign(String policeDepotSign) {
		this.policeDepotSign = policeDepotSign;
	}

	public String getPoliceIdNumber() {
		return policeIdNumber;
	}

	public void setPoliceIdNumber(String policeIdNumber) {
		this.policeIdNumber = policeIdNumber;
	}

	public String getPersonnelType() {
		return personnelType;
	}

	public void setPersonnelType(String personnelType) {
		this.personnelType = personnelType;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public String getWorkunitId() {
		return workunitId;
	}

	public void setWorkunitId(String workunitId) {
		this.workunitId = workunitId;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getGrowPlace() {
		return growPlace;
	}

	public void setGrowPlace(String growPlace) {
		this.growPlace = growPlace;
	}

	public String getPopulationCharacter() {
		return populationCharacter;
	}

	public void setPopulationCharacter(String populationCharacter) {
		this.populationCharacter = populationCharacter;
	}

	public String getHjszd() {
		return hjszd;
	}

	public void setHjszd(String hjszd) {
		this.hjszd = hjszd;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public String getSecretStatus() {
		return secretStatus;
	}

	public void setSecretStatus(String secretStatus) {
		this.secretStatus = secretStatus;
	}

	public String getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

	public String getHjszdxz() {
		return hjszdxz;
	}

	public void setHjszdxz(String hjszdxz) {
		this.hjszdxz = hjszdxz;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getJcgzjlsj() {
		return jcgzjlsj;
	}

	public void setJcgzjlsj(String jcgzjlsj) {
		this.jcgzjlsj = jcgzjlsj;
	}

	public String getGljsjzz() {
		return gljsjzz;
	}

	public void setGljsjzz(String gljsjzz) {
		this.gljsjzz = gljsjzz;
	}

	public String getJxyjxznx() {
		return jxyjxznx;
	}

	public void setJxyjxznx(String jxyjxznx) {
		this.jxyjxznx = jxyjxznx;
	}

	public String getPoliticsFace() {
		return politicsFace;
	}

	public void setPoliticsFace(String politicsFace) {
		this.politicsFace = politicsFace;
	}

	public void setOrganizationDate(Date organizationDate) {
		this.organizationDate = organizationDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOrganizationDate() {
		return organizationDate;
	}

	public String getWorkunitCode() {
		return workunitCode;
	}

	public void setWorkunitCode(String workunitCode) {
		this.workunitCode = workunitCode;
	}

	public String getWorkunitName() {
		return workunitName;
	}

	public void setWorkunitName(String workunitName) {
		this.workunitName = workunitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getRelationshipUnit() {
		return relationshipUnit;
	}

	public void setRelationshipUnit(String relationshipUnit) {
		this.relationshipUnit = relationshipUnit;
	}

	public String getBmhjz() {
		return bmhjz;
	}

	public void setBmhjz(String bmhjz) {
		this.bmhjz = bmhjz;
	}

	public String getJobAbbreviation() {
		return jobAbbreviation;
	}

	public void setJobAbbreviation(String jobAbbreviation) {
		this.jobAbbreviation = jobAbbreviation;
	}

	public String getJobFullname() {
		return jobFullname;
	}

	public void setJobFullname(String jobFullname) {
		this.jobFullname = jobFullname;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getXggbbs() {
		return xggbbs;
	}

	public void setXggbbs(String xggbbs) {
		this.xggbbs = xggbbs;
	}

	public String getIsXggb() {
		return isXggb;
	}

	public void setIsXggb(String isXggb) {
		this.isXggb = isXggb;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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
	public Date getPublicSecurityDate() {
		return publicSecurityDate;
	}

	public void setPublicSecurityDate(Date publicSecurityDate) {
		this.publicSecurityDate = publicSecurityDate;
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

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	@Override
	public String getRemarks() {
		return remarks;
	}
	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEditWorkUnitName() {
		return editWorkUnitName;
	}

	public void setEditWorkUnitName(String editWorkUnitName) {
		this.editWorkUnitName = editWorkUnitName;
	}

	public String getEditWorkUnitId() {
		return editWorkUnitId;
	}

	public void setEditWorkUnitId(String editWorkUnitId) {
		this.editWorkUnitId = editWorkUnitId;
	}

	public String getEditPersonIds() {
		return editPersonIds;
	}

	public void setEditPersonIds(String editPersonIds) {
		this.editPersonIds = editPersonIds;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPeopleStatus() {
		return peopleStatus;
	}

	public void setPeopleStatus(String peopleStatus) {
		this.peopleStatus = peopleStatus;
	}

	public String getActualUnit() {
		return actualUnit;
	}

	public void setActualUnit(String actualUnit) {
		this.actualUnit = actualUnit;
	}

	public String getActualUnitId() {
		return actualUnitId;
	}

	public void setActualUnitId(String actualUnitId) {
		this.actualUnitId = actualUnitId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStartAge() {
        return startAge;
    }

    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }

    public Integer getEndAge() {
        return endAge;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }

	public String getJuFlag() {
		return juFlag;
	}

	public void setJuFlag(String juFlag) {
		this.juFlag = juFlag;
	}

	public String getChuFlag() {
		return chuFlag;
	}

	public void setChuFlag(String chuFlag) {
		this.chuFlag = chuFlag;
	}

	public String getMinFlag() {
		return minFlag;
	}

	public void setMinFlag(String minFlag) {
		this.minFlag = minFlag;
	}
}