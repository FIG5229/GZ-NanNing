/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 廉政教育管理Entity
 * @author cecil.li
 * @version 2020-06-28
 */
public class AffairLzxxjyActivitiesSign extends DataEntity<AffairLzxxjyActivitiesSign> {
	
	private static final long serialVersionUID = 1L;
	private String noticeId;		// 通知通报id
	private String orgId;		// 接受部门id
	private String unit;		// 单位名称
	private Date date;		// 签收时间
	private String sign;		// 签收（0：未签收 1:签收）
	private Integer urge;		// 催办
	private String flag;

	private int pageNo1;

	private int pageNo2;
	
	public AffairLzxxjyActivitiesSign() {
		super();
	}

	public AffairLzxxjyActivitiesSign(String id){
		super(id);
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public Integer getUrge() {
		return urge;
	}

	public void setUrge(Integer urge) {
		this.urge = urge;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getPageNo1() {
		return pageNo1;
	}

	public void setPageNo1(int pageNo1) {
		this.pageNo1 = pageNo1;
	}

	public int getPageNo2() {
		return pageNo2;
	}

	public void setPageNo2(int pageNo2) {
		this.pageNo2 = pageNo2;
	}
}