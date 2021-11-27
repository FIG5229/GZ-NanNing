/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-警情信息Entity
 * @author alan.wu
 * @version 2020-11-19
 */
public class AcReport extends DataEntity<AcReport> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// guid
	private String jbr;		// jbr
	private String hbld;		// hbld
	private Date hbsj;		// hbsj
	private String clfs;		// clfs
	private String jqdj;		// jqdj
	private String cljg;		// cljg
	private String cjr;		// cjr
	private Date cjsj;		// cjsj
	private String fGuid;		// f_guid
	private String clzrr;		// clzrr
	
	public AcReport() {
		super();
	}

	public AcReport(String id){
		super(id);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getJbr() {
		return jbr;
	}

	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
	
	public String getHbld() {
		return hbld;
	}

	public void setHbld(String hbld) {
		this.hbld = hbld;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHbsj() {
		return hbsj;
	}

	public void setHbsj(Date hbsj) {
		this.hbsj = hbsj;
	}
	
	public String getClfs() {
		return clfs;
	}

	public void setClfs(String clfs) {
		this.clfs = clfs;
	}
	
	public String getJqdj() {
		return jqdj;
	}

	public void setJqdj(String jqdj) {
		this.jqdj = jqdj;
	}
	
	public String getCljg() {
		return cljg;
	}

	public void setCljg(String cljg) {
		this.cljg = cljg;
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
	
	public String getClzrr() {
		return clzrr;
	}

	public void setClzrr(String clzrr) {
		this.clzrr = clzrr;
	}
	
}