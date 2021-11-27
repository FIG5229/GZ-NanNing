/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 小种养Entity
 * @author cecil.li
 * @version 2019-11-29
 */
public class AffairXzy extends DataEntity<AffairXzy> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;		//序号
	@ExcelField(title = "建设时间", type = 0, align = 2, sort = 13)
	private Date Date;             //建设时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "种养点名称", type = 0, align = 2, sort = 2)
	private String name;		// 种养点名称
	@ExcelField(title = "小菜园面积（㎡）", type = 0, align = 2, sort = 3)
	private Integer vgArea;		// 小菜园面积（㎡）
	@ExcelField(title = "小果园面积（㎡）", type = 0, align = 2, sort = 4)
	private Integer orArea;		// 小果园面积（㎡）
	@ExcelField(title = "果树数量（棵）", type = 0, align = 2, sort = 5)
	private Integer orNum;		// 果树数量（棵）
	private Integer yzArea;		// 小养殖园面积
	@ExcelField(title = "鸡（只）", type = 0, align = 2, sort = 6)
	private Integer ckNum;		// 鸡（只）
	@ExcelField(title = "鸭（只）", type = 0, align = 2, sort = 7)
	private Integer dkNum;		// 鸭（只）
	@ExcelField(title = "鹅（只）", type = 0, align = 2, sort = 8)
	private Integer geNum;		// 鹅（只）
	@ExcelField(title = "猪（头）", type = 0, align = 2, sort = 9)
	private Integer pgNum;		// 猪（头）
	@ExcelField(title = "其他家禽家畜", type = 0, align = 2, sort = 10)
	private Integer otherNum;		// 其他家禽家畜
	@ExcelField(title = "鱼（尾）", type = 0, align = 2, sort = 11)
	private Integer fhNum;		// 鱼（尾）
	@ExcelField(title = "鱼塘面积（㎡）", type = 0, align = 2, sort = 12)
	private Integer fpArea;		// 鱼塘面积（㎡）
	private String content;		// 内容
	private String filePath;		// 附件
	private String jingBan;    //经办人
	private String unitShRen;    //申报单位审核人
	private String chuShOpinion;  //处工会审核意见
	private String juShOpinion;   //局工会审核意见

	private String userId;         //获取当前账号单位

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public java.util.Date getDate() {
		return Date;
	}

	public void setDate(java.util.Date date) {
		Date = date;
	}

	public AffairXzy() {
		super();
	}

	public AffairXzy(String id){
		super(id);
	}

	public String getContent() {
		return content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getVgArea() {
		return vgArea;
	}

	public void setVgArea(Integer vgArea) {
		this.vgArea = vgArea;
	}
	
	public Integer getOrArea() {
		return orArea;
	}

	public void setOrArea(Integer orArea) {
		this.orArea = orArea;
	}
	
	public Integer getOrNum() {
		return orNum;
	}

	public void setOrNum(Integer orNum) {
		this.orNum = orNum;
	}
	
	public Integer getCkNum() {
		return ckNum;
	}

	public void setCkNum(Integer ckNum) {
		this.ckNum = ckNum;
	}
	
	public Integer getDkNum() {
		return dkNum;
	}

	public void setDkNum(Integer dkNum) {
		this.dkNum = dkNum;
	}
	
	public Integer getGeNum() {
		return geNum;
	}

	public void setGeNum(Integer geNum) {
		this.geNum = geNum;
	}
	
	public Integer getPgNum() {
		return pgNum;
	}

	public void setPgNum(Integer pgNum) {
		this.pgNum = pgNum;
	}
	
	public Integer getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(Integer otherNum) {
		this.otherNum = otherNum;
	}
	
	public Integer getFhNum() {
		return fhNum;
	}

	public void setFhNum(Integer fhNum) {
		this.fhNum = fhNum;
	}
	
	public Integer getFpArea() {
		return fpArea;
	}

	public void setFpArea(Integer fpArea) {
		this.fpArea = fpArea;
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

	public Integer getYzArea() {
		return yzArea;
	}

	public void setYzArea(Integer yzArea) {
		this.yzArea = yzArea;
	}

	public String getJingBan() {
		return jingBan;
	}

	public void setJingBan(String jingBan) {
		this.jingBan = jingBan;
	}

	public String getUnitShRen() {
		return unitShRen;
	}

	public void setUnitShRen(String unitShRen) {
		this.unitShRen = unitShRen;
	}

	public String getChuShOpinion() {
		return chuShOpinion;
	}

	public void setChuShOpinion(String chuShOpinion) {
		this.chuShOpinion = chuShOpinion;
	}

	public String getJuShOpinion() {
		return juShOpinion;
	}

	public void setJuShOpinion(String juShOpinion) {
		this.juShOpinion = juShOpinion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}