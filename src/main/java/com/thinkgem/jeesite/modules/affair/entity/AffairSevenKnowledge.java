/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelFamily;

/**
 * 七知档案Entity
 * @author daniel.liu
 * @version 2020-07-03
 */
public class AffairSevenKnowledge extends DataEntity<AffairSevenKnowledge> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String sex;		// 性别
	private Date birthday;		// 出生日期
	private String degreeEducation;		// 文化程度
	private String politicsFace;		// 政治面貌
	private String homeAddress;		// 家庭地址
	private String houseArea;		// 住房面积
	private String contactTel;		// 联系方式
	private Date workTime;		// 参加工作时间
	private Date fromPoliceTime;		// 从警时间
	private String policeRank;		// 警衔
	private String maritalStatus;		// 婚姻状态
	private String health;		// 健康状况
	private String medicalHistory;		// 病史
	private String mentalIllness;		// 心里疾病
	private String curriculumVitae;		// 个人简历
	private String income;		// 收入情况
	private String keyExpenditure;		// 重点支出
	private String businessSituation;		// 直系亲属经商情况
	private String otherHouse;		// 有无其他房产（面积）
	private String debtDispute;		// 有无债务纠纷
	private String character;		// 性格
	private String specialty;		// 特长
	private String hobbies;		// 业余爱好
	private String unit;		// 工作单位
	private String unitId;		// 工作单位Id
	private String workJob;		// 工作岗位
	private String job;		// 职务
	private String goodBusiness;		// 擅长业务
	private String insufficientBusiness;		// 业务不足
	private String stockSpeculation;		// 是否炒股（基金）
	private String buyLottery;		// 是否常买彩票
	private String practiceSkills;		// 是否练功法
	private String familyHarmony;		// 家庭和睦情况
	private String neighborhoodRelations;		// 邻里关系
	private String colleagueRelations;		// 同事关系
	private String rewardsPunishments;		// 近期奖惩状况
	private Date gunHoldTime;		// 获持枪资格时间
	private String hasGun;		// 岗位是否配枪
	private String gunSystem;		// 配枪制度落实情况
	private String drink;		// 是否喝酒
	private String greedyCup;		// 是否贪杯
	private String outControlDrink;		// 有无酒后失控情况
	private String driverLicenseType;		// 驾驶证类型
	private Date driverTime;		// 驾照获取时间
	private String driverAgeCar;		// 汽车驾龄
	private String driverAgeMotorcycle;		// 摩托车驾龄
	private Date policeCarTime;		// 获准驾驶警车时间
	private Date longPoliceCarTime;		// 获准长途驾驶警车时间
	private String safeDriver;		// 安全行车记录
	private String mahjong;		// 是否爱打麻将
	private String gambling;		// 是否有赌博迹象
	private String betOther;		// 赌博其他反应
	private String familyMisfortune;		// 家庭重大变故
	private String illegalOrganization;		// 家属有无参与非法组织
	private String badAssociation;		// 有无社区不良交往
	private String addictedInternet;		// 是否沉迷网络
	private String luxuryPlaces;		// 是否经常出入高消费场所
	private String participationBusiness;		// 有无参股经商反应
	private String crruption;		// 以权谋私不良反映
	private String otherAdverseReactions;		// 其他不良反应
	private String offDuty;		// 有无脱岗离岗记录
	private String massComplaints;		// 有无群众投诉
	private String disciplining;		// 上级督察训导记录
	private Date helpEducateTime;		// 列为重点帮教对象时间
	private String suoLeader;		// 包保所领导
	private String chuLeader;		// 包保处领导
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String nation;		// 民族
	private Date beginBirthday;		// 开始 出生日期
	private Date endBirthday;		// 结束 出生日期
	private Date beginWorkTime;		// 开始 参加工作时间
	private Date endWorkTime;		// 结束 参加工作时间
	private Date beginFromPoliceTime;		// 开始 从警时间
	private Date endFromPoliceTime;		// 结束 从警时间

	private String idNumber;		//身份证号

	//家庭关系
	private List<PersonnelFamily> familyList;

	private String officeId;
	private String userId;

	/*新加字段*/
	private Date time;				//时间  进行月度 年度查询
	private String evaluate;		//综合评定	1：黄色代表思想状况稳定:2：橙色代表关注人群:3：红色代表重点关注人群

	private String year;
	private String month;

	private String yearDate;

	/*统计分析使用*/
	private String dateType;
	private String dateStart;
	private String dateEnd;
	private String label;
	private Date startDate;
	private Date endDate;

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*关联人员信息*/
	private PersonnelBase personnelBase;
	
	public AffairSevenKnowledge() {
		super();
	}

	public AffairSevenKnowledge(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getDegreeEducation() {
		return degreeEducation;
	}

	public void setDegreeEducation(String degreeEducation) {
		this.degreeEducation = degreeEducation;
	}
	
	public String getPoliticsFace() {
		return politicsFace;
	}

	public void setPoliticsFace(String politicsFace) {
		this.politicsFace = politicsFace;
	}
	
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}
	
	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFromPoliceTime() {
		return fromPoliceTime;
	}

	public void setFromPoliceTime(Date fromPoliceTime) {
		this.fromPoliceTime = fromPoliceTime;
	}
	
	public String getPoliceRank() {
		return policeRank;
	}

	public void setPoliceRank(String policeRank) {
		this.policeRank = policeRank;
	}
	
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}
	
	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	public String getMentalIllness() {
		return mentalIllness;
	}

	public void setMentalIllness(String mentalIllness) {
		this.mentalIllness = mentalIllness;
	}
	
	public String getCurriculumVitae() {
		return curriculumVitae;
	}

	public void setCurriculumVitae(String curriculumVitae) {
		this.curriculumVitae = curriculumVitae;
	}
	
	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}
	
	public String getKeyExpenditure() {
		return keyExpenditure;
	}

	public void setKeyExpenditure(String keyExpenditure) {
		this.keyExpenditure = keyExpenditure;
	}
	
	public String getBusinessSituation() {
		return businessSituation;
	}

	public void setBusinessSituation(String businessSituation) {
		this.businessSituation = businessSituation;
	}
	
	public String getOtherHouse() {
		return otherHouse;
	}

	public void setOtherHouse(String otherHouse) {
		this.otherHouse = otherHouse;
	}
	
	public String getDebtDispute() {
		return debtDispute;
	}

	public void setDebtDispute(String debtDispute) {
		this.debtDispute = debtDispute;
	}
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}
	
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
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
	
	public String getWorkJob() {
		return workJob;
	}

	public void setWorkJob(String workJob) {
		this.workJob = workJob;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getGoodBusiness() {
		return goodBusiness;
	}

	public void setGoodBusiness(String goodBusiness) {
		this.goodBusiness = goodBusiness;
	}
	
	public String getInsufficientBusiness() {
		return insufficientBusiness;
	}

	public void setInsufficientBusiness(String insufficientBusiness) {
		this.insufficientBusiness = insufficientBusiness;
	}
	
	public String getStockSpeculation() {
		return stockSpeculation;
	}

	public void setStockSpeculation(String stockSpeculation) {
		this.stockSpeculation = stockSpeculation;
	}
	
	public String getBuyLottery() {
		return buyLottery;
	}

	public void setBuyLottery(String buyLottery) {
		this.buyLottery = buyLottery;
	}
	
	public String getPracticeSkills() {
		return practiceSkills;
	}

	public void setPracticeSkills(String practiceSkills) {
		this.practiceSkills = practiceSkills;
	}
	
	public String getFamilyHarmony() {
		return familyHarmony;
	}

	public void setFamilyHarmony(String familyHarmony) {
		this.familyHarmony = familyHarmony;
	}
	
	public String getNeighborhoodRelations() {
		return neighborhoodRelations;
	}

	public void setNeighborhoodRelations(String neighborhoodRelations) {
		this.neighborhoodRelations = neighborhoodRelations;
	}
	
	public String getColleagueRelations() {
		return colleagueRelations;
	}

	public void setColleagueRelations(String colleagueRelations) {
		this.colleagueRelations = colleagueRelations;
	}
	
	public String getRewardsPunishments() {
		return rewardsPunishments;
	}

	public void setRewardsPunishments(String rewardsPunishments) {
		this.rewardsPunishments = rewardsPunishments;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getGunHoldTime() {
		return gunHoldTime;
	}

	public void setGunHoldTime(Date gunHoldTime) {
		this.gunHoldTime = gunHoldTime;
	}
	
	public String getHasGun() {
		return hasGun;
	}

	public void setHasGun(String hasGun) {
		this.hasGun = hasGun;
	}
	
	public String getGunSystem() {
		return gunSystem;
	}

	public void setGunSystem(String gunSystem) {
		this.gunSystem = gunSystem;
	}
	
	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}
	
	public String getGreedyCup() {
		return greedyCup;
	}

	public void setGreedyCup(String greedyCup) {
		this.greedyCup = greedyCup;
	}
	
	public String getOutControlDrink() {
		return outControlDrink;
	}

	public void setOutControlDrink(String outControlDrink) {
		this.outControlDrink = outControlDrink;
	}
	
	public String getDriverLicenseType() {
		return driverLicenseType;
	}

	public void setDriverLicenseType(String driverLicenseType) {
		this.driverLicenseType = driverLicenseType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDriverTime() {
		return driverTime;
	}

	public void setDriverTime(Date driverTime) {
		this.driverTime = driverTime;
	}
	
	public String getDriverAgeCar() {
		return driverAgeCar;
	}

	public void setDriverAgeCar(String driverAgeCar) {
		this.driverAgeCar = driverAgeCar;
	}
	
	public String getDriverAgeMotorcycle() {
		return driverAgeMotorcycle;
	}

	public void setDriverAgeMotorcycle(String driverAgeMotorcycle) {
		this.driverAgeMotorcycle = driverAgeMotorcycle;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPoliceCarTime() {
		return policeCarTime;
	}

	public void setPoliceCarTime(Date policeCarTime) {
		this.policeCarTime = policeCarTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getLongPoliceCarTime() {
		return longPoliceCarTime;
	}

	public void setLongPoliceCarTime(Date longPoliceCarTime) {
		this.longPoliceCarTime = longPoliceCarTime;
	}
	
	public String getSafeDriver() {
		return safeDriver;
	}

	public void setSafeDriver(String safeDriver) {
		this.safeDriver = safeDriver;
	}
	
	public String getMahjong() {
		return mahjong;
	}

	public void setMahjong(String mahjong) {
		this.mahjong = mahjong;
	}
	
	public String getGambling() {
		return gambling;
	}

	public void setGambling(String gambling) {
		this.gambling = gambling;
	}
	
	public String getBetOther() {
		return betOther;
	}

	public void setBetOther(String betOther) {
		this.betOther = betOther;
	}
	
	public String getFamilyMisfortune() {
		return familyMisfortune;
	}

	public void setFamilyMisfortune(String familyMisfortune) {
		this.familyMisfortune = familyMisfortune;
	}
	
	public String getIllegalOrganization() {
		return illegalOrganization;
	}

	public void setIllegalOrganization(String illegalOrganization) {
		this.illegalOrganization = illegalOrganization;
	}
	
	public String getBadAssociation() {
		return badAssociation;
	}

	public void setBadAssociation(String badAssociation) {
		this.badAssociation = badAssociation;
	}
	
	public String getAddictedInternet() {
		return addictedInternet;
	}

	public void setAddictedInternet(String addictedInternet) {
		this.addictedInternet = addictedInternet;
	}
	
	public String getLuxuryPlaces() {
		return luxuryPlaces;
	}

	public void setLuxuryPlaces(String luxuryPlaces) {
		this.luxuryPlaces = luxuryPlaces;
	}
	
	public String getParticipationBusiness() {
		return participationBusiness;
	}

	public void setParticipationBusiness(String participationBusiness) {
		this.participationBusiness = participationBusiness;
	}
	
	public String getCrruption() {
		return crruption;
	}

	public void setCrruption(String crruption) {
		this.crruption = crruption;
	}
	
	public String getOtherAdverseReactions() {
		return otherAdverseReactions;
	}

	public void setOtherAdverseReactions(String otherAdverseReactions) {
		this.otherAdverseReactions = otherAdverseReactions;
	}
	
	public String getOffDuty() {
		return offDuty;
	}

	public void setOffDuty(String offDuty) {
		this.offDuty = offDuty;
	}
	
	public String getMassComplaints() {
		return massComplaints;
	}

	public void setMassComplaints(String massComplaints) {
		this.massComplaints = massComplaints;
	}
	
	public String getDisciplining() {
		return disciplining;
	}

	public void setDisciplining(String disciplining) {
		this.disciplining = disciplining;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getHelpEducateTime() {
		return helpEducateTime;
	}

	public void setHelpEducateTime(Date helpEducateTime) {
		this.helpEducateTime = helpEducateTime;
	}
	
	public String getSuoLeader() {
		return suoLeader;
	}

	public void setSuoLeader(String suoLeader) {
		this.suoLeader = suoLeader;
	}
	
	public String getChuLeader() {
		return chuLeader;
	}

	public void setChuLeader(String chuLeader) {
		this.chuLeader = chuLeader;
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
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public Date getBeginBirthday() {
		return beginBirthday;
	}

	public void setBeginBirthday(Date beginBirthday) {
		this.beginBirthday = beginBirthday;
	}
	
	public Date getEndBirthday() {
		return endBirthday;
	}

	public void setEndBirthday(Date endBirthday) {
		this.endBirthday = endBirthday;
	}
		
	public Date getBeginWorkTime() {
		return beginWorkTime;
	}

	public void setBeginWorkTime(Date beginWorkTime) {
		this.beginWorkTime = beginWorkTime;
	}
	
	public Date getEndWorkTime() {
		return endWorkTime;
	}

	public void setEndWorkTime(Date endWorkTime) {
		this.endWorkTime = endWorkTime;
	}
		
	public Date getBeginFromPoliceTime() {
		return beginFromPoliceTime;
	}

	public void setBeginFromPoliceTime(Date beginFromPoliceTime) {
		this.beginFromPoliceTime = beginFromPoliceTime;
	}
	
	public Date getEndFromPoliceTime() {
		return endFromPoliceTime;
	}

	public void setEndFromPoliceTime(Date endFromPoliceTime) {
		this.endFromPoliceTime = endFromPoliceTime;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public List<PersonnelFamily> getFamilyList() {
		return familyList;
	}

	public void setFamilyList(List<PersonnelFamily> familyList) {
		this.familyList = familyList;
	}

	public PersonnelBase getPersonnelBase() {
		return personnelBase;
	}

	public void setPersonnelBase(PersonnelBase personnelBase) {
		this.personnelBase = personnelBase;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYearDate() {
		return yearDate;
	}

	public void setYearDate(String yearDate) {
		this.yearDate = yearDate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
}