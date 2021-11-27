/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 新闻宣传Entity
 * @author cecil.li
 * @version 2019-11-02
 */
public class AffairNews extends DataEntity<AffairNews> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "新闻标题", type = 0, align = 2, sort = 0)
	private String title;		// 新闻标题
	@ExcelField(title = "媒体名称", type = 0, align = 2, sort = 1)
	private String mediaName;		// 媒体名称
	@ExcelField(title = "栏目", type = 0, align = 2, sort = 2)
	private String lm;		// 栏目
	@ExcelField(title = "作者", type = 0, align = 2, sort = 7)
	private String author;		// 作者
	@ExcelField(title = "篇幅字数", type = 0, align = 2, sort = 5)
	private Integer wordNum;		// 篇幅字数
	@ExcelField(title = "刊发时间", type = 0, align = 2, sort = 3)
	private Date date;		// 刊发时间
	@ExcelField(title = "刊发类别", type = 0, align = 2, sort = 6,dictType="affair_news")
	private String typr;		// 刊发类别
	@ExcelField(title = "所属单位", type = 0, align = 2, sort = 8)
	private String unit;		// 所属单位
	private String unitId;		// 所属单位id
	@ExcelField(title = "内容", type = 0, align = 2, sort = 10)
	private String content;		// 内容
//	@EsAttach
	//@ExcelField(title = "附件", type = 0, align = 2, sort = 11)
	private String filePath;		// 附件
	@ExcelField(title = "宣传对象", type = 0, align = 2, sort = 4)
	private String object;   //宣传对象
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String unitNameId;		// 单位id

	//新添加字段 用于典型培树事迹材料查找
	@ExcelField(title = "所属人", type = 0, align = 2, sort = 9)
	private String name;
	//@ExcelField(title = "所属人身份证号", type = 0, align = 2, sort = 10)
	private String idNumber;

	/*作者 所属人 所属单位改为字表形式*/
	private List<AffairNewsAuthor> affairNewsAuthorList;
	private List<AffairNewsUnit> affairNewsUnitList;
	private List<AffairNewsName> affairNewsNameList;
	/*不使用此子表 子表分开*/
	private List<AffairNewsChild> newsChildList;


	private Date startDate;
	private Date endDate;
	private Integer year;
	private String month;
	private String dateType;

	private String adopt;
	private String inputAdopt;

	private String isYear;

	public String getIsYear() {
		return isYear;
	}

	public void setIsYear(String isYear) {
		this.isYear = isYear;
	}

	public String getInputAdopt() {
		return inputAdopt;
	}

	public void setInputAdopt(String inputAdopt) {
		this.inputAdopt = inputAdopt;
	}

	public String getAdopt() {
		return adopt;
	}

	public void setAdopt(String adopt) {
		this.adopt = adopt;
	}

	public AffairNews() {
		super();
	}

	public AffairNews(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	
	public String getLm() {
		return lm;
	}

	public void setLm(String lm) {
		this.lm = lm;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Integer getWordNum() {
		return wordNum;
	}

	public void setWordNum(Integer wordNum) {
		this.wordNum = wordNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTypr() {
		return typr;
	}

	public void setTypr(String typr) {
		this.typr = typr;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public String getUnitNameId() {
		return unitNameId;
	}

	public void setUnitNameId(String unitNameId) {
		this.unitNameId = unitNameId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

    public List<AffairNewsAuthor> getAffairNewsAuthorList() {
        return affairNewsAuthorList;
    }

    public void setAffairNewsAuthorList(List<AffairNewsAuthor> affairNewsAuthorList) {
        this.affairNewsAuthorList = affairNewsAuthorList;
    }

    public List<AffairNewsUnit> getAffairNewsUnitList() {
        return affairNewsUnitList;
    }

    public void setAffairNewsUnitList(List<AffairNewsUnit> affairNewsUnitList) {
        this.affairNewsUnitList = affairNewsUnitList;
    }

    public List<AffairNewsName> getAffairNewsNameList() {
        return affairNewsNameList;
    }

    public void setAffairNewsNameList(List<AffairNewsName> affairNewsNameList) {
        this.affairNewsNameList = affairNewsNameList;
    }

	public List<AffairNewsChild> getNewsChildList() {
		return newsChildList;
	}

	public void setNewsChildList(List<AffairNewsChild> newsChildList) {
		this.newsChildList = newsChildList;
	}
}