/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 抚恤伤亡统计Entity
 * @author eav.liu
 * @version 2019-11-29
 */
public class AffairCasualtyReportStatistic extends DataEntity<AffairCasualtyReportStatistic> {

	private static final long serialVersionUID = 1L;

	private String unitName;	//单位
	private String unitId;	//单位机构id
	private Integer xiSheng;	//因公牺牲数量
	private Integer bingGu;	//病故数量
	private Integer shangCan;	//伤残数量
	private Integer shangWang;	//伤亡数量
	private Integer sum;	//合计
	private Integer oneTypeNum;	//某一类的伤亡种类的数量
	private String typeName;	//某一类的伤亡种类的名字

	public AffairCasualtyReportStatistic() {
		super();
	}

	public AffairCasualtyReportStatistic(String id){
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

	public Integer getXiSheng() {
		return xiSheng;
	}

	public void setXiSheng(Integer xiSheng) {
		this.xiSheng = xiSheng;
	}

	public Integer getBingGu() {
		return bingGu;
	}

	public void setBingGu(Integer bingGu) {
		this.bingGu = bingGu;
	}

	public Integer getShangCan() {
		return shangCan;
	}

	public void setShangCan(Integer shangCan) {
		this.shangCan = shangCan;
	}

	public Integer getShangWang() {
		return shangWang;
	}

	public void setShangWang(Integer shangWang) {
		this.shangWang = shangWang;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getOneTypeNum() {
		return oneTypeNum;
	}

	public void setOneTypeNum(Integer oneTypeNum) {
		this.oneTypeNum = oneTypeNum;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}