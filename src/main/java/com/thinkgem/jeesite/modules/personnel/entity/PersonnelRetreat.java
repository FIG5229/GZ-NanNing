/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 离退信息集Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelRetreat extends DataEntity<PersonnelRetreat> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	private String personnelName;		// 姓名
	@ExcelField(title = "离退类别", type = 0, align = 2, sort = 1, dictType="personnel_lttype")
	private String type;		// 离退类别
	@ExcelField(title = "离退日期", type = 0, align = 2, sort = 2)
	private Date date;		// 离退日期
	@ExcelField(title = "离退前级别", type = 0, align = 2, sort = 3, dictType="personnel_ltqtype")
	private String preLevel;		// 离退前级别
	@ExcelField(title = "离退后现管理单位名称", type = 0, align = 2, sort = 4)
	private String nowUnitName;		// 离退后现管理单位名称
	@ExcelField(title = "曾任最高职务", type = 0, align = 2, sort = 5)
	private String highestJob;		// 曾任最高职务
	@ExcelField(title = "离退休费支付单位名称", type = 0, align = 2, sort = 6)
	private String payOrgName;		// 离退休费支付单位名称
	@ExcelField(title = "离退干部现享受待遇", type = 0, align = 2, sort = 7)
	private String treatment;		// 离退干部现享受待遇
	@ExcelField(title = "离退干部现享受待遇类别", type = 0, align = 2, sort = 8 , dictType="personnel_ltdytype")
	private String treatmentType;		// 离退干部现享受待遇类别
	@ExcelField(title = "离退后现管理单位代码", type = 0, align = 2, sort = 9)
	private String nowUnitCode;		// 离退后现管理单位代码
	@ExcelField(title = "离退后现居住政区", type = 0, align = 2, sort = 10)
	private String liveArea;		// 离退后现居住政区
	@ExcelField(title = "离退休费标准（%）", type = 0, align = 2, sort = 11)
	private Integer standard;		// 离退休费标准（%）
	@ExcelField(title = "离退休费支付单位代码", type = 0, align = 2, sort = 12)
    private String payOrgCode;		// 离退休费支付单位代码

	@ExcelField(title = "离退备注", type = 0, align = 2, sort = 13)
    private String remarks;      //离退备注
	@ExcelField(title = "离退后管理类别", type = 0, align = 2, sort = 14, dictType="personnel_ltgltype")
	private String managerType;		// 离退后管理类别

	@ExcelField(title = "离退休批准文号", type = 0, align = 2, sort = 15)
	private String approvalFileNo;		// 离退休批准文号
	@ExcelField(title = "是否参加93工改", type = 0, align = 2, sort = 16, dictType="yes_no")
	private String is93;		// 是否参加93工改
	@ExcelField(title = "执算参加工作年限", type = 0, align = 2, sort = 17)
	private Integer years;		// 执算参加工作年限
	@ExcelField(title = "是否红军", type = 0, align = 2, sort = 18, dictType="yes_no")
	private String isRedArmy;		// 是否红军
	@ExcelField(title = "是否孤寡", type = 0, align = 2, sort = 19, dictType="yes_no")
	private String isLonely;		// 是否孤寡
	@ExcelField(title = "生活是否能自理", type = 0, align = 2, sort = 20, dictType="yes_no")
	private String isSelfCare;		// 生活是否能自理
	@ExcelField(title = "是否无子女", type = 0, align = 2, sort = 21, dictType="yes_no")
	private String isHasChild;		// 是否无子女
	@ExcelField(title = "离休干部1-2月生活补贴", type = 0, align = 2, sort = 22)
	private String subsidy;		// 离休干部1-2月生活补贴
	@ExcelField(title = "老粮贴", type = 0, align = 2, sort = 23)
	private String lnt;		// 老粮贴
	@ExcelField(title = "离退时职务工资", type = 0, align = 2, sort = 24)
	private Float jobSalary;		// 离退时职务工资
	@ExcelField(title = "离退时级别工资", type = 0, align = 2, sort = 25)
	private Float levelSalary;		// 离退时级别工资
	@ExcelField(title = "联系方式", type = 0, align = 2, sort = 26)
	private String contactMethod;		// 联系方式
	@ExcelField(title = "提前退休标识", type = 0, align = 2, sort = 27)
	private String identification;		// 提前退休标识
	@ExcelField(title = "提前退休原因", type = 0, align = 2, sort = 28)
	private String reason;		// 提前退休原因
	@ExcelField(title = "提前离岗日期", type = 0, align = 2, sort = 29)
	private Date leaveDate;		// 提前离岗日期
	@ExcelField(title = "提前离岗前工作单位名称", type = 0, align = 2, sort = 30)
	private String preUnitName;		// 提前离岗前工作单位名称
	@ExcelField(title = "提前离岗前职务层次", type = 0, align = 2, sort = 31)
	private String preJobLevel;		// 提前离岗前职务层次
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 32)
	private String idNumber;		// 身份证号
	private String sex;            //性别
	private Date birthDate;         //出生日期
    private Integer minAge;           //最小年龄
	private Integer maxAge;         //最大年龄
	private String filePath;       //附件
	private String shPerson;       //审核人
	private String status;          //审核状态
	private String opinion;        //审核意见
    private String isManage;       //审核权限

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号




	private Date startDate;
	private Date endDate;
	private String nowUnitNameId;
	private String nowUnitNameIds;
	private String payOrgNameId;

	private String fileName;//前端-离退休材料的名字

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public PersonnelRetreat() {
		super();
	}

	public PersonnelRetreat(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPreLevel() {
		return preLevel;
	}

	public void setPreLevel(String preLevel) {
		this.preLevel = preLevel;
	}
	
	public String getNowUnitName() {
		return nowUnitName;
	}

	public void setNowUnitName(String nowUnitName) {
		this.nowUnitName = nowUnitName;
	}
	
	public String getHighestJob() {
		return highestJob;
	}

	public void setHighestJob(String highestJob) {
		this.highestJob = highestJob;
	}
	
	public String getPayOrgName() {
		return payOrgName;
	}

	public void setPayOrgName(String payOrgName) {
		this.payOrgName = payOrgName;
	}
	
	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}
	
	public String getNowUnitCode() {
		return nowUnitCode;
	}

	public void setNowUnitCode(String nowUnitCode) {
		this.nowUnitCode = nowUnitCode;
	}
	
	public String getLiveArea() {
		return liveArea;
	}

	public void setLiveArea(String liveArea) {
		this.liveArea = liveArea;
	}
	
	public String getPayOrgCode() {
		return payOrgCode;
	}

	public void setPayOrgCode(String payOrgCode) {
		this.payOrgCode = payOrgCode;
	}
	
	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}
	
	public String getApprovalFileNo() {
		return approvalFileNo;
	}

	public void setApprovalFileNo(String approvalFileNo) {
		this.approvalFileNo = approvalFileNo;
	}
	
	public String getIs93() {
		return is93;
	}

	public void setIs93(String is93) {
		this.is93 = is93;
	}
	
	public String getIsRedArmy() {
		return isRedArmy;
	}

	public void setIsRedArmy(String isRedArmy) {
		this.isRedArmy = isRedArmy;
	}
	
	public String getIsLonely() {
		return isLonely;
	}

	public void setIsLonely(String isLonely) {
		this.isLonely = isLonely;
	}
	
	public String getIsSelfCare() {
		return isSelfCare;
	}

	public void setIsSelfCare(String isSelfCare) {
		this.isSelfCare = isSelfCare;
	}
	
	public String getIsHasChild() {
		return isHasChild;
	}

	public void setIsHasChild(String isHasChild) {
		this.isHasChild = isHasChild;
	}
	
	public String getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(String subsidy) {
		this.subsidy = subsidy;
	}
	
	public String getLnt() {
		return lnt;
	}

	public void setLnt(String lnt) {
		this.lnt = lnt;
	}
	
	public String getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getPreUnitName() {
		return preUnitName;
	}

	public void setPreUnitName(String preUnitName) {
		this.preUnitName = preUnitName;
	}
	
	public String getPreJobLevel() {
		return preJobLevel;
	}

	public void setPreJobLevel(String preJobLevel) {
		this.preJobLevel = preJobLevel;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
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

	public String getNowUnitNameId() {
		return nowUnitNameId;
	}

	public void setNowUnitNameId(String nowUnitNameId) {
		this.nowUnitNameId = nowUnitNameId;
	}

	public String getPayOrgNameId() {
		return payOrgNameId;
	}

	public void setPayOrgNameId(String payOrgNameId) {
		this.payOrgNameId = payOrgNameId;
	}

	public Integer getStandard() {
		return standard;
	}

	public void setStandard(Integer standard) {
		this.standard = standard;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Float getJobSalary() {
		return jobSalary;
	}

	public void setJobSalary(Float jobSalary) {
		this.jobSalary = jobSalary;
	}

	public Float getLevelSalary() {
		return levelSalary;
	}

	public void setLevelSalary(Float levelSalary) {
		this.levelSalary = levelSalary;
	}

	public String getNowUnitNameIds() {
		return nowUnitNameIds;
	}

	public void setNowUnitNameIds(String nowUnitNameIds) {
		this.nowUnitNameIds = nowUnitNameIds;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public String getIsManage() {
		return isManage;
	}

	public void setIsManage(String isManage) {
		this.isManage = isManage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}