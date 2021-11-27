/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 模板项数据Entity
 * @author bradley.zhao
 * @version 2019-12-13
 */
public class ExamStandardTemplateData extends DataEntity<ExamStandardTemplateData> {
	
	private static final long serialVersionUID = 1L;
	private String itemId;		// 模板项目ID
	private String itemValue;		// 数据项值
	private Integer rowNum;		// 行号

	/*查询评分标准使用*/
	private String columnType;	//
	
	public ExamStandardTemplateData() {
		super();
	}

	public ExamStandardTemplateData(String id){
		super(id);
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	
	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
}