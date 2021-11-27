/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 传递单Entity
 * @author mason.xv
 * @version 2019-11-29
 */
public class AffairTransmitForm extends DataEntity<AffairTransmitForm> {
	
	private static final long serialVersionUID = 1L;
	private String handler;		// 经办人
	private Date handleDate;		// 经办日期
	private String handleZi;		// 字
	private String handleDh;		// 第号
	private String receiver;		// 接收人
	private Date receiveDate;		// 收件日期
	private String receiveOrg;		// 收件机关
	private String receiveOrgId;		// 收件机关id
	private String receiveZi;		// 字
	private String receiveDh;		// 第号
	private Integer zNum;		// 档案正本数量
	private Integer fNum;		// 档案副本数量
	private Integer materialNum;		// 材料数量
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginReceiveDate;		// 开始 收件日期
	private Date endReceiveDate;		// 结束 收件日期
	private List<AffairTransmitPerson> affairTransmitPersonList = Lists.newArrayList();		// 子表列表


	public Integer getzNum() {
		return zNum;
	}

	public void setzNum(Integer zNum) {
		this.zNum = zNum;
	}

	public Integer getfNum() {
		return fNum;
	}

	public void setfNum(Integer fNum) {
		this.fNum = fNum;
	}

	public AffairTransmitForm() {
		super();
	}

	public AffairTransmitForm(String id){
		super(id);
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd ")
	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}
	
	public String getHandleZi() {
		return handleZi;
	}

	public void setHandleZi(String handleZi) {
		this.handleZi = handleZi;
	}
	
	public String getHandleDh() {
		return handleDh;
	}

	public void setHandleDh(String handleDh) {
		this.handleDh = handleDh;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd ")
	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	public String getReceiveOrg() {
		return receiveOrg;
	}

	public void setReceiveOrg(String receiveOrg) {
		this.receiveOrg = receiveOrg;
	}
	
	public String getReceiveOrgId() {
		return receiveOrgId;
	}

	public void setReceiveOrgId(String receiveOrgId) {
		this.receiveOrgId = receiveOrgId;
	}
	
	public String getReceiveZi() {
		return receiveZi;
	}

	public void setReceiveZi(String receiveZi) {
		this.receiveZi = receiveZi;
	}
	
	public String getReceiveDh() {
		return receiveDh;
	}

	public void setReceiveDh(String receiveDh) {
		this.receiveDh = receiveDh;
	}
	
	public Integer getZNum() {
		return zNum;
	}

	public void setZNum(Integer zNum) {
		this.zNum = zNum;
	}
	
	public Integer getFNum() {
		return fNum;
	}

	public void setFNum(Integer fNum) {
		this.fNum = fNum;
	}
	
	public Integer getMaterialNum() {
		return materialNum;
	}

	public void setMaterialNum(Integer materialNum) {
		this.materialNum = materialNum;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getCreateIdNo() {
		return createIdNo;
	}

	public void setCreateIdNo(String createIdNo) {
		this.createIdNo = createIdNo;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
	public String getUpdateIdNo() {
		return updateIdNo;
	}

	public void setUpdateIdNo(String updateIdNo) {
		this.updateIdNo = updateIdNo;
	}
	
	public Date getBeginReceiveDate() {
		return beginReceiveDate;
	}

	public void setBeginReceiveDate(Date beginReceiveDate) {
		this.beginReceiveDate = beginReceiveDate;
	}
	
	public Date getEndReceiveDate() {
		return endReceiveDate;
	}

	public void setEndReceiveDate(Date endReceiveDate) {
		this.endReceiveDate = endReceiveDate;
	}
		
	public List<AffairTransmitPerson> getAffairTransmitPersonList() {
		return affairTransmitPersonList;
	}

	public void setAffairTransmitPersonList(List<AffairTransmitPerson> affairTransmitPersonList) {
		this.affairTransmitPersonList = affairTransmitPersonList;
	}
}