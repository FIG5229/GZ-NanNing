/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 交流锻炼Entity
 * @author jack.xu
 * @version 2020-07-16
 */
public class AffairSwapExerciseDeputy extends DataEntity<AffairSwapExerciseDeputy> {

	private static final long serialVersionUID = 1L;

	@ExcelField(title = "姓名",type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "交流名称",type = 0, align = 2, sort = 1)
	private String swapName;		// 交流名称
	@ExcelField(title = "单位",type = 0, align = 2, sort = 2)
	private String unit;		// 单位
	@ExcelField(title = "开始时间",type = 0, align = 2, sort = 3)
	private Date startDate;
	@ExcelField(title = "结束时间",type = 0, align = 2, sort = 4)
	private Date endDate;


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSwapName() {
		return swapName;
	}

	public void setSwapName(String swapName) {
		this.swapName = swapName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}