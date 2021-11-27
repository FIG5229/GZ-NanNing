/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 职务工资标准Entity
 * @author cecil.li
 * @version 2020-07-01
 */
public class AffairJobSalaryStandard extends DataEntity<AffairJobSalaryStandard> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "年度", type = 0, align = 2, sort = 0)
	private String year;		// 年度
	@ExcelField(title = "职级", type = 0, align = 2, sort = 1)
	private String level;		// 职级
	@ExcelField(title = "职务工资", type = 0, align = 2, sort = 2)
	private Integer levelMoney;		// 职务工资
	@ExcelField(title = "工作补贴", type = 0, align = 2, sort = 3)
	private Integer workMoney;		// 工作补贴
	@ExcelField(title = "生活补贴", type = 0, align = 2, sort = 4)
	private Integer lifeMoney;		// 生活补贴
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairJobSalaryStandard() {
		super();
	}

	public AffairJobSalaryStandard(String id){
		super(id);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public Integer getLevelMoney() {
		return levelMoney;
	}

	public void setLevelMoney(Integer levelMoney) {
		this.levelMoney = levelMoney;
	}
	
	public Integer getWorkMoney() {
		return workMoney;
	}

	public void setWorkMoney(Integer workMoney) {
		this.workMoney = workMoney;
	}
	
	public Integer getLifeMoney() {
		return lifeMoney;
	}

	public void setLifeMoney(Integer lifeMoney) {
		this.lifeMoney = lifeMoney;
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
	
}