/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.elasticsearch.annotation.EsAttach;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 党建文件通知通报Entity
 * @author eav.liu
 * @version 2019-11-01
 */
public class AffairFileNotice extends DataEntity<AffairFileNotice> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String type;		// 类型
	private String publishDep;		// 发布部门
	private String publisher;		// 发布人
	private String publishOrgId;		// 发布部门机构id
	private String receiveDep;		// 接收部门
	private String receiveDepId;	// 接收部门id
	private String content;		// 主要内容
	@EsAttach
	private String filePath;		// 附件
	private Date publishDate;		// 发布日期
	private String status;		// 发布状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String flag;		// 标识（1：党建党建文件通知通报 2：工团通知通报 3：团委通知通报 4：警营文化（文联）制度和通知通报）
	private String isPush;		//是否推送到主页  1：是

	//供查询时间区间使用
	private Date startDate;
	//供查询时间区间使用
	private Date endDate;
	//是否要发布的权限
	private boolean hasAuth;
	//接收部门签收的日期
	private Date signDate;
	//接收部门签收的状态
	private String signStatus; //1：签收

	private Integer signNum;

	private Integer sumNum;

	private boolean isFront; //是否来自前端

	public AffairFileNotice() {
		super();
	}

	public AffairFileNotice(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPublishDep() {
		return publishDep;
	}

	public void setPublishDep(String publishDep) {
		this.publishDep = publishDep;
	}
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getPublishOrgId() {
		return publishOrgId;
	}

	public void setPublishOrgId(String publishOrgId) {
		this.publishOrgId = publishOrgId;
	}
	
	public String getReceiveDep() {
		return receiveDep;
	}

	public void setReceiveDep(String receiveDep) {
		this.receiveDep = receiveDep;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth) {
		this.hasAuth = hasAuth;
	}

	public Integer getSignNum() {
		return signNum;
	}

	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}

	public Integer getSumNum() {
		return sumNum;
	}

	public void setSumNum(Integer sumNum) {
		this.sumNum = sumNum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getReceiveDepId() {
		return receiveDepId;
	}

	public void setReceiveDepId(String receiveDepId) {
		this.receiveDepId = receiveDepId;
	}

	public String getIsPush() {
		return isPush;
	}

	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}

	public boolean getIsFront() {
		return isFront;
	}

	public void setIsFront(boolean front) {
		isFront = front;
	}
}