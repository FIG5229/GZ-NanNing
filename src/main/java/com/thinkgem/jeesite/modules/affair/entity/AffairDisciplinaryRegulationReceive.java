/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 纪律规定接收单位表Entity
 * @author eav.liu
 * @version 2020-04-09
 */
public class AffairDisciplinaryRegulationReceive extends DataEntity<AffairDisciplinaryRegulationReceive> {
	
	private static final long serialVersionUID = 1L;
	private String disRegId;		// affair_disciplinary_regulation表主键
	private String unitId;		// 接收单位的id
	private String orderId;
	
	public AffairDisciplinaryRegulationReceive() {
		super();
	}

	public AffairDisciplinaryRegulationReceive(String id){
		super(id);
	}

	public String getDisRegId() {
		return disRegId;
	}

	public void setDisRegId(String disRegId) {
		this.disRegId = disRegId;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}