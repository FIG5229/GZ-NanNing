/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 警情预警-自动考评Entity
 * @author alan.wu
 * @version 2020-10-16
 */
public class TPoliceMessage extends DataEntity<TPoliceMessage> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String idCard;		// 身份证号
	private String iphoneNumber;		// 手机号
	private String wechatId;		// 微信号
	private String policeAddress;		// 报警地点(1 :列车上'| 2' : '火车站')
	private String piliceIp;		// 报警人ip
	private String trainNumber;		// 车次
	private String coach;		// 车厢
	private String stationName;		// 车站
	private String stationSite;		// 车站位置
	private String policeMessage;		// 报警信息
	private String createTime;		// 创建时间
	private String organizationId;		// 组织机构
	private String policeWay;		// 报警方式（1 :电话报警；2 :语音报警；3：视频报警；4：文字报警）
	private Date policeDate;		// 报警时间
	private String seatNumber;		// 座位号
	private String caseStatus;		// 警情状态
	private Date disposeTime;		// 处理时间
	private String processTime;		// 受理时间
	private String receiptMessage;		// 回执消息
	private String receiptPeople;		// 回执民警
	private Date receiptTime;		// 回执时间
	private String status1;		// 市级是否指派（0，1   表示）
	private String status2;		// 处级是否指派（0，1   表示）
	private String status3;		// 代表派出所和列车民警的状态（0，1   表示）
	private String receiptStatus;		// 回执状态
	private String feedbackStatus;		// 受理状态
	private String district;		// 所属区域，用于统计
	private String caseMessage;		// 无效原因
	private String receiptPeopleId;		// 回执民警id
	private Date caseTime;		// 添加无效信息的时间
	private String processOrganization;		// 受理单位
	private String processMessage;		// 处理结果(新增加的字段)
	private String alertName;		// 警情类别
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String policeType;		// 报警类别（0表示是一件报警内容，1表示是一键救助）
	private String superviseOrganization;		// 督促单位
	private Date superviseTime;		// 督促时间
	private String superviseStatus;		// 督促状态（1表是受理超时，2表示回执超时）
	private String superviseName;		// 督促人（获取的是当前登录人姓名）
	private String overtimeStatus;		// 超时状态（1表示超时数据）
	private String lineName;		// 线路名称
	private String linePosition;		// 线路区段
	private String processPeople;		// 受理人(获取当前登录人姓名)
	private String processType;		// process_type
	private String processCase;		// process_case
	private String receiptType;		// receipt_type
	private String fromStation;		// 发站
	private String toStation;		// 到站
	private String districtName;		// 区域名称
	private String interiorAreaName;		// 内部单位区域名称
	
	public TPoliceMessage() {
		super();
	}

	public TPoliceMessage(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public String getIphoneNumber() {
		return iphoneNumber;
	}

	public void setIphoneNumber(String iphoneNumber) {
		this.iphoneNumber = iphoneNumber;
	}
	
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	
	public String getPoliceAddress() {
		return policeAddress;
	}

	public void setPoliceAddress(String policeAddress) {
		this.policeAddress = policeAddress;
	}
	
	public String getPiliceIp() {
		return piliceIp;
	}

	public void setPiliceIp(String piliceIp) {
		this.piliceIp = piliceIp;
	}
	
	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
	
	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}
	
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	public String getStationSite() {
		return stationSite;
	}

	public void setStationSite(String stationSite) {
		this.stationSite = stationSite;
	}
	
	public String getPoliceMessage() {
		return policeMessage;
	}

	public void setPoliceMessage(String policeMessage) {
		this.policeMessage = policeMessage;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String  createTime) {
		this.createTime = createTime;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	public String getPoliceWay() {
		return policeWay;
	}

	public void setPoliceWay(String policeWay) {
		this.policeWay = policeWay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPoliceDate() {
		return policeDate;
	}

	public void setPoliceDate(Date policeDate) {
		this.policeDate = policeDate;
	}
	
	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDisposeTime() {
		return disposeTime;
	}

	public void setDisposeTime(Date disposeTime) {
		this.disposeTime = disposeTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}
	
	public String getReceiptMessage() {
		return receiptMessage;
	}

	public void setReceiptMessage(String receiptMessage) {
		this.receiptMessage = receiptMessage;
	}
	
	public String getReceiptPeople() {
		return receiptPeople;
	}

	public void setReceiptPeople(String receiptPeople) {
		this.receiptPeople = receiptPeople;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}
	
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	
	public String getStatus3() {
		return status3;
	}

	public void setStatus3(String status3) {
		this.status3 = status3;
	}
	
	public String getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}
	
	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
	
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getCaseMessage() {
		return caseMessage;
	}

	public void setCaseMessage(String caseMessage) {
		this.caseMessage = caseMessage;
	}
	
	public String getReceiptPeopleId() {
		return receiptPeopleId;
	}

	public void setReceiptPeopleId(String receiptPeopleId) {
		this.receiptPeopleId = receiptPeopleId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCaseTime() {
		return caseTime;
	}

	public void setCaseTime(Date caseTime) {
		this.caseTime = caseTime;
	}
	
	public String getProcessOrganization() {
		return processOrganization;
	}

	public void setProcessOrganization(String processOrganization) {
		this.processOrganization = processOrganization;
	}
	
	public String getProcessMessage() {
		return processMessage;
	}

	public void setProcessMessage(String processMessage) {
		this.processMessage = processMessage;
	}
	
	public String getAlertName() {
		return alertName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getPoliceType() {
		return policeType;
	}

	public void setPoliceType(String policeType) {
		this.policeType = policeType;
	}
	
	public String getSuperviseOrganization() {
		return superviseOrganization;
	}

	public void setSuperviseOrganization(String superviseOrganization) {
		this.superviseOrganization = superviseOrganization;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuperviseTime() {
		return superviseTime;
	}

	public void setSuperviseTime(Date superviseTime) {
		this.superviseTime = superviseTime;
	}
	
	public String getSuperviseStatus() {
		return superviseStatus;
	}

	public void setSuperviseStatus(String superviseStatus) {
		this.superviseStatus = superviseStatus;
	}
	
	public String getSuperviseName() {
		return superviseName;
	}

	public void setSuperviseName(String superviseName) {
		this.superviseName = superviseName;
	}
	
	public String getOvertimeStatus() {
		return overtimeStatus;
	}

	public void setOvertimeStatus(String overtimeStatus) {
		this.overtimeStatus = overtimeStatus;
	}
	
	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	public String getLinePosition() {
		return linePosition;
	}

	public void setLinePosition(String linePosition) {
		this.linePosition = linePosition;
	}
	
	public String getProcessPeople() {
		return processPeople;
	}

	public void setProcessPeople(String processPeople) {
		this.processPeople = processPeople;
	}
	
	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	public String getProcessCase() {
		return processCase;
	}

	public void setProcessCase(String processCase) {
		this.processCase = processCase;
	}
	
	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	
	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}
	
	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public String getInteriorAreaName() {
		return interiorAreaName;
	}

	public void setInteriorAreaName(String interiorAreaName) {
		this.interiorAreaName = interiorAreaName;
	}
	
}