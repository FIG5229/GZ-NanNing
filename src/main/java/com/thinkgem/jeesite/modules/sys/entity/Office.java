/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.elasticsearch.annotation.HighLight;
import com.thinkgem.jeesite.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 机构Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Office extends TreeEntity<Office> {

	private static final long serialVersionUID = 1L;
//	private Office parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	private Area area;		// 归属区域
	private String code; 	// 机构编码
//	private String name; 	// 机构名称
//	private Integer sort;		// 排序
	private String type; 	// 机构类型（1：公司；2：部门；3：小组）
	private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱
	private String useable;//是否可用
	private User primaryPerson;//主负责人
	private User deputyPerson;//副负责人
	private List<String> childDeptList;//快速添加子部门

	/**
	 * 以下为数据库新加入的字段
	 */
	@HighLight
	private String fullName;		// 单位全称
	private String abbreviation;		// 单位简称
	private String relationship;		// 单位隶属关系
	private String characterType;		// 单位性质类别
	private String level;		// 单位级别
	private String unitArea;		// 单位所在政区
	private String hierarchy1;		// 单位所在层级
	private String superUnitName;		// 上级单位名称
	private String superUnitCode;		// 上级单位代码
	private String depPolic;		// 单位所属部门与警种
	private String unitType;		// 单位类别
	private String leader;		// 单位负责人
	private Date foundDate;		// 单位成立批准时间
	private String approvalFileNo;		// 单位成立批准文号
	private Date cancelDate;		// 单位撤消批准时间
	private String cancelFileNo;		// 单位撤消批准文号
	private String cancelOrg;		// 单位撤消批准机关名称
	private String unitDm;		// 单位代码
	private String cncelIdentification;		// 撤销标识
	private String policeStationType;		// 派出所类型
	private Integer huNum;		// 派出所管理户数
	private String hierarchy2;		// 单位层次
	private String emptyUnit;		// 虚单位
	private Integer personNum;		// 派出所人数
	private String sysCode;		// 公务员系统编码
	private String legalIdentification;		// 法人单位标识
	private String registerOrg;		// 登记管理机关
	private String scope;		// 业务范围
	private String source;		// 经费来源
	private String approvalOrg;		// 单位成立批准机关名称
	private Date orgUpdate;		// 机构更新时间
	private String buildSituation;		// 单位建立党组织情况


	public Office(){
		super();
//		this.sort = 30;
		this.type = "2";
	}

	public Office(String id){
		super(id);
	}
	
	public List<String> getChildDeptList() {
		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {
		this.childDeptList = childDeptList;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public User getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(User primaryPerson) {
		this.primaryPerson = primaryPerson;
	}

	public User getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(User deputyPerson) {
		this.deputyPerson = deputyPerson;
	}

//	@JsonBackReference
//	@NotNull
	public Office getParent() {
		return parent;
	}

	public void setParent(Office parent) {
		this.parent = parent;
	}
//
//	@Length(min=1, max=2000)
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}

	//@NotNull
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
//
//	@Length(min=1, max=100)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}
	
	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=1, max=1)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min=0, max=255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min=0, max=100)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min=0, max=100)
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Length(min=0, max=200)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min=0, max=200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}
	
	@Override
	public String toString() {
		return name;
	}

	/**
	 * 以下为数据库新加入的字段
	 */

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getCharacterType() {
		return characterType;
	}

	public void setCharacterType(String characterType) {
		this.characterType = characterType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUnitArea() {
		return unitArea;
	}

	public void setUnitArea(String unitArea) {
		this.unitArea = unitArea;
	}

	public String getHierarchy1() {
		return hierarchy1;
	}

	public void setHierarchy1(String hierarchy1) {
		this.hierarchy1 = hierarchy1;
	}

	public String getSuperUnitName() {
		return superUnitName;
	}

	public void setSuperUnitName(String superUnitName) {
		this.superUnitName = superUnitName;
	}

	public String getSuperUnitCode() {
		return superUnitCode;
	}

	public void setSuperUnitCode(String superUnitCode) {
		this.superUnitCode = superUnitCode;
	}

	public String getDepPolic() {
		return depPolic;
	}

	public void setDepPolic(String depPolic) {
		this.depPolic = depPolic;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getApprovalFileNo() {
		return approvalFileNo;
	}

	public void setApprovalFileNo(String approvalFileNo) {
		this.approvalFileNo = approvalFileNo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelFileNo() {
		return cancelFileNo;
	}

	public void setCancelFileNo(String cancelFileNo) {
		this.cancelFileNo = cancelFileNo;
	}

	public String getCancelOrg() {
		return cancelOrg;
	}

	public void setCancelOrg(String cancelOrg) {
		this.cancelOrg = cancelOrg;
	}

	public String getUnitDm() {
		return unitDm;
	}

	public void setUnitDm(String unitDm) {
		this.unitDm = unitDm;
	}

	public String getCncelIdentification() {
		return cncelIdentification;
	}

	public void setCncelIdentification(String cncelIdentification) {
		this.cncelIdentification = cncelIdentification;
	}

	public String getPoliceStationType() {
		return policeStationType;
	}

	public void setPoliceStationType(String policeStationType) {
		this.policeStationType = policeStationType;
	}

	public Integer getHuNum() {
		return huNum;
	}

	public void setHuNum(Integer huNum) {
		this.huNum = huNum;
	}

	public String getHierarchy2() {
		return hierarchy2;
	}

	public void setHierarchy2(String hierarchy2) {
		this.hierarchy2 = hierarchy2;
	}

	public String getEmptyUnit() {
		return emptyUnit;
	}

	public void setEmptyUnit(String emptyUnit) {
		this.emptyUnit = emptyUnit;
	}

	public Integer getPersonNum() {
		return personNum;
	}

	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getLegalIdentification() {
		return legalIdentification;
	}

	public void setLegalIdentification(String legalIdentification) {
		this.legalIdentification = legalIdentification;
	}

	public String getRegisterOrg() {
		return registerOrg;
	}

	public void setRegisterOrg(String registerOrg) {
		this.registerOrg = registerOrg;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getApprovalOrg() {
		return approvalOrg;
	}

	public void setApprovalOrg(String approvalOrg) {
		this.approvalOrg = approvalOrg;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOrgUpdate() {
		return orgUpdate;
	}

	public void setOrgUpdate(Date orgUpdate) {
		this.orgUpdate = orgUpdate;
	}

	public String getBuildSituation() {
		return buildSituation;
	}

	public void setBuildSituation(String buildSituation) {
		this.buildSituation = buildSituation;
	}
}