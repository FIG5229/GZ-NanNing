/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 考评模板Entity
 * 考评模板的表头，
 * @author bradley.zhao
 * @version 2019-12-17
 */
public class ExamStandardTemplateDefine extends DataEntity<ExamStandardTemplateDefine> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private Integer startrowNum;		// 开始行号
	private Integer endrowNum;		// 结束行号
	private String examStardardId;		// 考评标准ID
	private List<ExamStandardTemplateItemDefine> examStandardTemplateItemDefineList = Lists.newArrayList();		// 子表列表
	private String cycle;
	private String objectCategory;
	private String kpType;
	
	public ExamStandardTemplateDefine() {
		super();
	}

	public ExamStandardTemplateDefine(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="开始行号不能为空")
	public Integer getStartrowNum() {
		return startrowNum;
	}

	public void setStartrowNum(Integer startrowNum) {
		this.startrowNum = startrowNum;
	}

	public Integer getEndrowNum() {
		return endrowNum;
	}

	public void setEndrowNum(Integer endrowNum) {
		this.endrowNum = endrowNum;
	}
	
	public String getExamStardardId() {
		return examStardardId;
	}

	public void setExamStardardId(String examStardardId) {
		this.examStardardId = examStardardId;
	}
	
	public List<ExamStandardTemplateItemDefine> getExamStandardTemplateItemDefineList() {
		return examStandardTemplateItemDefineList;
	}

	public void setExamStandardTemplateItemDefineList(List<ExamStandardTemplateItemDefine> examStandardTemplateItemDefineList) {
		this.examStandardTemplateItemDefineList = examStandardTemplateItemDefineList;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public String getKpType() {
		return kpType;
	}

	public void setKpType(String kpType) {
		this.kpType = kpType;
	}
}