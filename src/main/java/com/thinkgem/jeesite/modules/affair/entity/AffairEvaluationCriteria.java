/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 测评标准Entity
 * @author daniel.liu
 * @version 2020-07-02
 */
public class AffairEvaluationCriteria extends DataEntity<AffairEvaluationCriteria> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "测评项目", type = 0, align = 2, sort = 1)
	private String title;		// 测评项目名
	@ExcelField(title = "层级", type = 0, align = 2, sort = 3, dictType = "evaluation_level")
	private String level;		// 层级（全国、广西壮族自治区、全国铁路、集团公司、其他自定义）
	@ExcelField(title = "年度", type = 0, align = 2, sort = 2)
	private String year;		// 年度
	@ExcelField(title = "测评标准", type = 0, align = 2, sort = 4)
	private String standard;		// 测评标准
	@ExcelField(title = "测评方法", type = 0, align = 2, sort = 5, dictType = "evaluation_method")
	private String method;		// 测评方法
	@ExcelField(title = "分数值", type = 0, align = 2, sort = 6)
	private String score;		// 分数值
	//@ExcelField(title = "考核目标", type = 0, align = 2, sort = 7)
	private String unit;		// 考核目标单位
	private String unitId;		// 考核目标Id
	@ExcelField(title = "附件", type = 0, align = 2, sort = 7)
	private String filePath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	
	public AffairEvaluationCriteria() {
		super();
	}

	public AffairEvaluationCriteria(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
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
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
}