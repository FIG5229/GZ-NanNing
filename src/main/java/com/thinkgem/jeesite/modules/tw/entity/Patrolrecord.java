/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-打卡异常信息Entity
 * @author alan.wu
 * @version 2020-11-19
 */
public class Patrolrecord extends DataEntity<Patrolrecord> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// guid
	private String userid;		// userid
	private String imei;		// imei
	private String date;		// date
	private String punchingpointid;		// punchingpointid
	private String x;		// x
	private String y;		// y
	private String reviewuserid;		// reviewuserid
	private String reviewtime;		// reviewtime
	private String ispass;		// ispass
	private String isjoinstatistics;		// isjoinstatistics

	public Patrolrecord() {
		super();
	}

	public Patrolrecord(String id){
		super(id);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getPunchingpointid() {
		return punchingpointid;
	}

	public void setPunchingpointid(String punchingpointid) {
		this.punchingpointid = punchingpointid;
	}
	
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}
	
	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
	public String getReviewuserid() {
		return reviewuserid;
	}

	public void setReviewuserid(String reviewuserid) {
		this.reviewuserid = reviewuserid;
	}
	
	public String getReviewtime() {
		return reviewtime;
	}

	public void setReviewtime(String reviewtime) {
		this.reviewtime = reviewtime;
	}
	
	public String getIspass() {
		return ispass;
	}

	public void setIspass(String ispass) {
		this.ispass = ispass;
	}
	
	public String getIsjoinstatistics() {
		return isjoinstatistics;
	}

	public void setIsjoinstatistics(String isjoinstatistics) {
		this.isjoinstatistics = isjoinstatistics;
	}
	
}