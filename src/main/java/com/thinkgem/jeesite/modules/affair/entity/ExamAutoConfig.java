/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评配置Entity
 * @author danil.liu
 * @version 2020-12-15
 */
public class ExamAutoConfig extends DataEntity<ExamAutoConfig> {
	
	private static final long serialVersionUID = 1L;
	private String kpType;		// 考评类别
	private String cycle;		// 考评周期
	private String standardId;		// 考评标准（standardBaseInfo的Id）
	private String standard;		//考评标准
	private String standardOption;		//评分标准
	private String rowNum;		// 行号
	private String sys;		// 系统
	private String items;		// 考核事项
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	
	public ExamAutoConfig() {
		super();
	}

	public ExamAutoConfig(String id){
		super(id);
	}

	public String getKpType() {
		return kpType;
	}

	public void setKpType(String kpType) {
		this.kpType = kpType;
	}
	
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	
	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	
	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}
	
	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
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

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getStandardOption() {
		return standardOption;
	}

	public void setStandardOption(String standardOption) {
		this.standardOption = standardOption;
	}
}