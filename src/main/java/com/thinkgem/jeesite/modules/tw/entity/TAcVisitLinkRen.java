/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-回访记录和人员关联信息Entity
 * @author alan.wu
 * @version 2020-11-20
 */
public class TAcVisitLinkRen extends DataEntity<TAcVisitLinkRen> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// 主键
	private String acId;		// 采集主键
	private String renId;		// 人员主键
	
	public TAcVisitLinkRen() {
		super();
	}

	public TAcVisitLinkRen(String id){
		super(id);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getAcId() {
		return acId;
	}

	public void setAcId(String acId) {
		this.acId = acId;
	}
	
	public String getRenId() {
		return renId;
	}

	public void setRenId(String renId) {
		this.renId = renId;
	}
	
}