/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-警情信息Entity
 * @author alan.wu
 * @version 2020-11-19
 */
public class AcMobileData extends DataEntity<AcMobileData> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// guid
	private String acType;		// ac_type
	private String acDate;		// ac_date
	private User user;		// user_id
	private String location;		// location
	private String unitCode;		// unit_code
	private String resion;		// resion
	private String result;		// result
	private String person;		// person
	private String hasMedia;		// has_media
	private String odometer;		// 自动生成
	private String railwayName;		// railway_name
	private String x;		// x
	private String y;		// y
	private String code;		// 区分是编辑细项还是添加细项
	private String colType;		// col_type

	private String cjsj;		//审核时间


	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public AcMobileData() {
		super();
	}

	public AcMobileData(String id){
		super(id);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}
	
	public String getAcDate() {
		return acDate;
	}

	public void setAcDate(String acDate) {
		this.acDate = acDate;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public String getResion() {
		return resion;
	}

	public void setResion(String resion) {
		this.resion = resion;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
	
	public String getHasMedia() {
		return hasMedia;
	}

	public void setHasMedia(String hasMedia) {
		this.hasMedia = hasMedia;
	}
	
	public String getOdometer() {
		return odometer;
	}

	public void setOdometer(String odometer) {
		this.odometer = odometer;
	}
	
	public String getRailwayName() {
		return railwayName;
	}

	public void setRailwayName(String railwayName) {
		this.railwayName = railwayName;
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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}
	
}