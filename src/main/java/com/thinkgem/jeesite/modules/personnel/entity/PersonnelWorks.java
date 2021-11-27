/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 论著信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelWorks extends DataEntity<PersonnelWorks> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "论著名称", type = 0, align = 2, sort = 1)
	private String name;		// 论著名称
	@ExcelField(title = "论著学科领域", type = 0, align = 2, sort = 2)
	private String field;		// 论著学科领域
	@ExcelField(title = "论著完成日期", type = 0, align = 2, sort = 3)
	private Date completeDate;		// 论著完成日期
	@ExcelField(title = "论著出版日期", type = 0, align = 2, sort = 4)
	private Date publishDate;		// 论著出版日期
	@ExcelField(title = "论著出版机构", type = 0, align = 2, sort = 5)
	private String workPublishOffice;		// 论著出版机构
	@ExcelField(title = "论著出版机构隶属层次", type = 0, align = 2, sort = 6)
	private String publishOfficeLevel;		// 论著出版机构隶属层次
	@ExcelField(title = "论著出版形式类别", type = 0, align = 2, sort = 7, dictType="personnel_chubantype")
	private String publishOfficeType;		// 论著出版形式类别
	@ExcelField(title = "作者身份", type = 0, align = 2, sort = 8)
	private String authorIdentity;		// 作者身份
	@ExcelField(title = "作者顺序", type = 0, align = 2, sort = 9)
	private String authorSort;		// 作者顺序
	@ExcelField(title = "发表日期", type = 0, align = 2, sort = 10)
	private Date pubDate;		// 发表日期
	@ExcelField(title = "期刊报纸名称", type = 0, align = 2, sort = 11)
	private String newspaperName;		// 期刊报纸名称
	@ExcelField(title = "期刊发行号码", type = 0, align = 2, sort = 12)
	private String periodicalNo;		// 期刊发行号码
	@ExcelField(title = "论著密级", type = 0, align = 2, sort = 13)
	private String secretGrade;		// 论著密级
	@ExcelField(title = "所著论著的图片资料", type = 0, align = 2, sort = 14)
	private String material;		// 所著论著的图片资料
	@ExcelField(title = "何种索引收录", type = 0, align = 2, sort = 15)
	private String index;		// 何种索引收录
	@ExcelField(title = "论著语种", type = 0, align = 2, sort = 16, dictType="personnel_yuzhong")
	private String language;		// 论著语种
	@ExcelField(title = "主要作者姓名", type = 0, align = 2, sort = 17)
	private String authorName;		// 主要作者姓名
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelWorks() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelWorks(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	public String getWorkPublishOffice() {
		return workPublishOffice;
	}

	public void setWorkPublishOffice(String workPublishOffice) {
		this.workPublishOffice = workPublishOffice;
	}
	
	public String getPublishOfficeLevel() {
		return publishOfficeLevel;
	}

	public void setPublishOfficeLevel(String publishOfficeLevel) {
		this.publishOfficeLevel = publishOfficeLevel;
	}
	
	public String getPublishOfficeType() {
		return publishOfficeType;
	}

	public void setPublishOfficeType(String publishOfficeType) {
		this.publishOfficeType = publishOfficeType;
	}
	
	public String getAuthorIdentity() {
		return authorIdentity;
	}

	public void setAuthorIdentity(String authorIdentity) {
		this.authorIdentity = authorIdentity;
	}
	
	public String getAuthorSort() {
		return authorSort;
	}

	public void setAuthorSort(String authorSort) {
		this.authorSort = authorSort;
	}

	
	public String getNewspaperName() {
		return newspaperName;
	}

	public void setNewspaperName(String newspaperName) {
		this.newspaperName = newspaperName;
	}

	public String getPeriodicalNo() {
		return periodicalNo;
	}

	public void setPeriodicalNo(String periodicalNo) {
		this.periodicalNo = periodicalNo;
	}

	public String getSecretGrade() {
		return secretGrade;
	}

	public void setSecretGrade(String secretGrade) {
		this.secretGrade = secretGrade;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}