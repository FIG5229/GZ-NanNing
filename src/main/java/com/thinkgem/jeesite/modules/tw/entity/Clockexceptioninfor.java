/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-打卡异常记录Entity
 * @author alan.wu
 * @version 2020-11-19
 */
public class Clockexceptioninfor extends DataEntity<Clockexceptioninfor> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// guid
	private Date createtime;		// createtime
	private String type;		// type
	private String detail;		// detail
	private String pprecordid;		// pprecordid
	
	public Clockexceptioninfor() {
		super();
	}

	public Clockexceptioninfor(String id){
		super(id);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getPprecordid() {
		return pprecordid;
	}

	public void setPprecordid(String pprecordid) {
		this.pprecordid = pprecordid;
	}
	
}