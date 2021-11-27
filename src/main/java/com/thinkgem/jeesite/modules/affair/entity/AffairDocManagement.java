/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 文档管理Entity
 * @author kevin.jia
 * @version 2020-07-30
 */
public class AffairDocManagement extends DataEntity<AffairDocManagement> {
	
	private static final long serialVersionUID = 1L;
	private String docCode;		// 文档编码
	private String docName;		// 文档名称
	private String docClassifyName;		// 文档类别名称
	private String docClassifyId;		// 文档类别id
	private String keyword;		// 关键字
	private String ispublic;		// 是否公开
	private String docLanguage;		// 文档语言(简体中文,繁体中文,英文)
	private String suitableObject;		// 适用对象
	private String mainContent;		// 主要内容
	private String releaseStatus;		// 发布状态（0-未发布,1-已发布）
	private String isdownload;		// 是否可下载（可下载,不可下载）
	private String resourcesNum;		// 下载需要资源数
	private String remark;		// 备注
	private String appendfile;       //附件
	private String checkType;     //审核状态
	private String oneCheckMan;		// 一级审核人
	private String twoCheckMan;		// 二级审核人
	private String submitMan;		// 提交人
	private String oneCheckId;		// 一级审核id
	private String twoCheckId;		// 二级审核id
	private String submitId;		// 提交人id
	private String opinion;         // 审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	public AffairDocManagement() {
		super();
	}

	public AffairDocManagement(String id){
		super(id);
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	
	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	public String getDocClassifyName() {
		return docClassifyName;
	}

	public void setDocClassifyName(String docClassifyName) {
		this.docClassifyName = docClassifyName;
	}

	public String getDocClassifyId() {
		return docClassifyId;
	}

	public void setDocClassifyId(String docClassifyId) {
		this.docClassifyId = docClassifyId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}
	
	public String getDocLanguage() {
		return docLanguage;
	}

	public void setDocLanguage(String docLanguage) {
		this.docLanguage = docLanguage;
	}
	
	public String getSuitableObject() {
		return suitableObject;
	}

	public void setSuitableObject(String suitableObject) {
		this.suitableObject = suitableObject;
	}
	
	public String getMainContent() {
		return mainContent;
	}

	public void setMainContent(String mainContent) {
		this.mainContent = mainContent;
	}
	
	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	
	public String getIsdownload() {
		return isdownload;
	}

	public void setIsdownload(String isdownload) {
		this.isdownload = isdownload;
	}
	
	public String getResourcesNum() {
		return resourcesNum;
	}

	public void setResourcesNum(String resourcesNum) {
		this.resourcesNum = resourcesNum;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAppendfile() {
		return appendfile;
	}

	public void setAppendfile(String appendfile) {
		this.appendfile = appendfile;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getOneCheckMan() {
		return oneCheckMan;
	}

	public void setOneCheckMan(String oneCheckMan) {
		this.oneCheckMan = oneCheckMan;
	}

	public String getTwoCheckMan() {
		return twoCheckMan;
	}

	public void setTwoCheckMan(String twoCheckMan) {
		this.twoCheckMan = twoCheckMan;
	}

	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}

	public String getOneCheckId() {
		return oneCheckId;
	}

	public void setOneCheckId(String oneCheckId) {
		this.oneCheckId = oneCheckId;
	}

	public String getTwoCheckId() {
		return twoCheckId;
	}

	public void setTwoCheckId(String twoCheckId) {
		this.twoCheckId = twoCheckId;
	}

	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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
}