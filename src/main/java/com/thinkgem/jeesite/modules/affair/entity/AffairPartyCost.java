/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 党费管理Entity
 * @author eav.liu
 * @version 2019-11-11
 */
public class AffairPartyCost extends DataEntity<AffairPartyCost> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "党员姓名", type = 0, align = 2, sort = 0)
	private String name;		// 党员姓名
	@ExcelField(title = "党员身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 党员身份证号
	@ExcelField(title = "缴存基数", type = 0, align = 2, sort = 5)
	private Double baseNum;		// 缴存基数
	@ExcelField(title = "交纳党费比例", type = 0, align = 2, sort = 6)
	private Double proportion;		// 交纳党费比例
	@ExcelField(title = "应交党费", type = 0, align = 2, sort = 7)
	private Double cost1;		// 应交党费（元）
	@ExcelField(title = "实交党费", type = 0, align = 2, sort = 8)
	private Double cost2;		// 实交党费（元）

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	@ExcelField(title = "党员类别", type = 0, align = 2, sort = 4 ,dictType="affair_df_type")
	private String type;   //党员类别
	@ExcelField(title = "缴费年度", type = 0, align = 2, sort = 3)
	private String year;		//缴费年度

	private String month;  //缴费月份
	private Double zwMoney;//职务工资
	private Double jbMoney;//级别工资
	private Double gzjtMoney;//上年度工作性津贴平均数
	private Double sdsMoney;//上年的个人所得税平均数
	private Double ylMoney;//本年度养老金
	private Double ybMoney;//本年度医保金
	private Double zyMoney;//本年度职业年金
	private Double gjjMoney;//本年度公积金
	private Double jishu;//缴费基数
	private String bili;//应缴比例
	private Double sjMoney;//月实际党费

	//前端
	private String treeId;
	private String officeNmae;
	private AffairPartyMember affairPartyMember;
	@ExcelField(title = "所在党组织", type = 0, align = 2, sort = 2)
	private String partyBranch;		// 所在党组织
	private String partyBranchId;		// 所在党组织id
	
	public AffairPartyCost() {
		super();
	}

	public AffairPartyCost(String id){
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
	
	public Double getBaseNum() {
		return baseNum;
	}

	public void setBaseNum(Double baseNum) {
		this.baseNum = baseNum;
	}
	
	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}
	
	public Double getCost1() {
		return cost1;
	}

	public void setCost1(Double cost1) {
		this.cost1 = cost1;
	}
	
	public Double getCost2() {
		return cost2;
	}

	public void setCost2(Double cost2) {
		this.cost2 = cost2;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPartyBranch() {
		return partyBranch;
	}

	public void setPartyBranch(String partyBranch) {
		this.partyBranch = partyBranch;
	}

	public String getPartyBranchId() {
		return partyBranchId;
	}

	public void setPartyBranchId(String partyBranchId) {
		this.partyBranchId = partyBranchId;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public AffairPartyMember getAffairPartyMember() {
		return affairPartyMember;
	}

	public void setAffairPartyMember(AffairPartyMember affairPartyMember) {
		this.affairPartyMember = affairPartyMember;
	}

	public String getOfficeNmae() {
		return officeNmae;
	}

	public void setOfficeNmae(String officeNmae) {
		this.officeNmae = officeNmae;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getZwMoney() {
		return zwMoney;
	}

	public void setZwMoney(Double zwMoney) {
		this.zwMoney = zwMoney;
	}

	public Double getJbMoney() {
		return jbMoney;
	}

	public void setJbMoney(Double jbMoney) {
		this.jbMoney = jbMoney;
	}

	public Double getGzjtMoney() {
		return gzjtMoney;
	}

	public void setGzjtMoney(Double gzjtMoney) {
		this.gzjtMoney = gzjtMoney;
	}

	public Double getSdsMoney() {
		return sdsMoney;
	}

	public void setSdsMoney(Double sdsMoney) {
		this.sdsMoney = sdsMoney;
	}

	public Double getYlMoney() {
		return ylMoney;
	}

	public void setYlMoney(Double ylMoney) {
		this.ylMoney = ylMoney;
	}

	public Double getYbMoney() {
		return ybMoney;
	}

	public void setYbMoney(Double ybMoney) {
		this.ybMoney = ybMoney;
	}

	public Double getZyMoney() {
		return zyMoney;
	}

	public void setZyMoney(Double zyMoney) {
		this.zyMoney = zyMoney;
	}

	public Double getGjjMoney() {
		return gjjMoney;
	}

	public void setGjjMoney(Double gjjMoney) {
		this.gjjMoney = gjjMoney;
	}

	public Double getJishu() {
		return jishu;
	}

	public void setJishu(Double jishu) {
		this.jishu = jishu;
	}

	public String getBili() {
		return bili;
	}

	public void setBili(String bili) {
		this.bili = bili;
	}

	public Double getSjMoney() {
		return sjMoney;
	}

	public void setSjMoney(Double sjMoney) {
		this.sjMoney = sjMoney;
	}
}