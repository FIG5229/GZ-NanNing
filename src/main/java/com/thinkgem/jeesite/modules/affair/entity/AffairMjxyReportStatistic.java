/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 民警休养申报统计Entity
 * @author eva.liu
 * @version 2019-11-28
 */
public class AffairMjxyReportStatistic extends DataEntity<AffairMjxyReportStatistic> {
	private static final long serialVersionUID = 1L;

	private String unitName;	//责任单位
	private String unitId;	//责任单位机构id
	private Integer juGuanNei;	//局管内数量
	private Integer juGuanWai;	//局管外数量
	private Integer laoMo;	//劳模数量
	private Integer other;	//其他数量
	private Integer sum;	//合计
	private String type = "1";	//1：局管内 2：局管外 3：劳模

	public AffairMjxyReportStatistic() {
		super();
	}

	public AffairMjxyReportStatistic(String id){
		super(id);
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Integer getJuGuanNei() {
		return juGuanNei;
	}

	public void setJuGuanNei(Integer juGuanNei) {
		this.juGuanNei = juGuanNei;
	}

	public Integer getJuGuanWai() {
		return juGuanWai;
	}

	public void setJuGuanWai(Integer juGuanWai) {
		this.juGuanWai = juGuanWai;
	}

	public Integer getLaoMo() {
		return laoMo;
	}

	public void setLaoMo(Integer laoMo) {
		this.laoMo = laoMo;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOther() {
		return other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}
}