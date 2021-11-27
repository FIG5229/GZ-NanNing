/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-隐患信息Entity
 * @author alan.wu
 * @version 2020-11-19
 */
public class AcSaftyAudit extends DataEntity<AcSaftyAudit> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// guid
	private String shmj;		// shmj
	private String shld;		// shld
	private String sfllyhglxt;		// sfllyhglxt
	private Date shsj;		// shsj
	private String cjr;		// cjr
	private Date cjsj;		// cjsj
	private String fGuid;		// f_guid
	private String shclqk;		// shclqk
	
	public AcSaftyAudit() {
		super();
	}

	public AcSaftyAudit(String id){
		super(id);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getShmj() {
		return shmj;
	}

	public void setShmj(String shmj) {
		this.shmj = shmj;
	}
	
	public String getShld() {
		return shld;
	}

	public void setShld(String shld) {
		this.shld = shld;
	}
	
	public String getSfllyhglxt() {
		return sfllyhglxt;
	}

	public void setSfllyhglxt(String sfllyhglxt) {
		this.sfllyhglxt = sfllyhglxt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}
	
	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	
	public String getFGuid() {
		return fGuid;
	}

	public void setFGuid(String fGuid) {
		this.fGuid = fGuid;
	}
	
	public String getShclqk() {
		return shclqk;
	}

	public void setShclqk(String shclqk) {
		this.shclqk = shclqk;
	}
	
}