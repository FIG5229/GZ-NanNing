/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-打卡预警Entity
 * @author alan.wu
 * @version 2020-11-19
 */
public class Dutywarning extends DataEntity<Dutywarning> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// guid
	private Date createtime;		// createtime
	private String warningtype;		// warningtype
	private String content;		// content
	private String userid;		// userid
	
	public Dutywarning() {
		super();
	}

	public Dutywarning(String id){
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
	
	public String getWarningtype() {
		return warningtype;
	}

	public void setWarningtype(String warningtype) {
		this.warningtype = warningtype;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}