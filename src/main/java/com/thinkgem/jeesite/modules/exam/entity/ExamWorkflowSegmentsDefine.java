/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 标准考评流程环节定义Entity
 * @author eav.liu
 * @version 2019-12-09
 */
public class ExamWorkflowSegmentsDefine extends DataEntity<ExamWorkflowSegmentsDefine> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 流程类型
	private String name;		// 环节名称
	private String sort;		// 顺序号
	private String isMust;	//是否必选
	
	public ExamWorkflowSegmentsDefine() {
		super();
	}

	public ExamWorkflowSegmentsDefine(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
}