/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-打卡信息Entity
 * @author alan.wu
 * @version 2020-11-19
 */
public class Punchingpointinfor extends DataEntity<Punchingpointinfor> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// guid
	private String name;		// name
	private String policeboxid;		// policeboxid
	private String clockorder;		// clockorder
	private String maxdistance;		// 打卡时打卡点与打卡位置之间距离超出最大距离则产生打卡异常
	private Date cjsj;		// cjsj
	private String cjr;		// cjr
	private String cjdwdm;		// cjdwdm
	private String xgr;		// xgr
	private Date xgsj;		// xgsj
	private String xgdwdm;		// xgdwdm
	
	public Punchingpointinfor() {
		super();
	}

	public Punchingpointinfor(String id){
		super(id);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPoliceboxid() {
		return policeboxid;
	}

	public void setPoliceboxid(String policeboxid) {
		this.policeboxid = policeboxid;
	}
	
	public String getClockorder() {
		return clockorder;
	}

	public void setClockorder(String clockorder) {
		this.clockorder = clockorder;
	}
	
	public String getMaxdistance() {
		return maxdistance;
	}

	public void setMaxdistance(String maxdistance) {
		this.maxdistance = maxdistance;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	
	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	
	public String getCjdwdm() {
		return cjdwdm;
	}

	public void setCjdwdm(String cjdwdm) {
		this.cjdwdm = cjdwdm;
	}
	
	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}
	
	public String getXgdwdm() {
		return xgdwdm;
	}

	public void setXgdwdm(String xgdwdm) {
		this.xgdwdm = xgdwdm;
	}
	
}