/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 模板项管理Entity
 * @author bradley.zhao
 * @version 2019-12-12
 */
public class ExamStandardTemplateItemDefine extends DataEntity<ExamStandardTemplateItemDefine> {
	
	private static final long serialVersionUID = 1L;
	private String columnName;		// 列名称
	private String columnType;		// 列类型
	private Integer columnOrder;		// 列顺序号
	private String templateId;		// 模板
	
	public ExamStandardTemplateItemDefine() {
		super();
	}

	public ExamStandardTemplateItemDefine(String id){
		super(id);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	@NotNull(message="列顺序号不能为空")
	public Integer getColumnOrder() {
		return columnOrder;
	}

	public void setColumnOrder(Integer columnOrder) {
		this.columnOrder = columnOrder;
	}
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
}