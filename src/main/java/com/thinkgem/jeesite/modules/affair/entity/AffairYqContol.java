/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 舆情管控Entity
 * @author cecil.li
 * @version 2019-11-02
 */
public class  AffairYqContol extends DataEntity<AffairYqContol> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "舆情标题" , type = 0 , align = 1 ,sort = 0)
	private String title;		// 舆情标题
	@ExcelField(title = "舆情发生日期" , type = 0 , align = 1 ,sort = 1)
	private Date date;		// 舆情日期
	@ExcelField(title = "舆情来源" , type = 0 , align = 1 ,sort = 3 , dictType = "affair_yqly")
	private String source;		// 舆情来源
	@ExcelField(title = "领导批示" , type = 0 , align = 1 ,sort = 5)
	private String leaderPs;		// 领导批示
	@ExcelField(title = "呈办意见" , type = 0 , align = 1 ,sort = 11)
	private String opinion;		// 呈办意见
	@ExcelField(title = "处置情况" , type = 0 , align = 1 ,sort = 12, dictType = "affair_yqcz")
	private String situation;		// 处置情况
	@ExcelField(title = "舆情内容" , type = 0 , align = 1 ,sort = 10)
	private String content;		// 舆情内容
	private String filePath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "处置说明" , type = 0 , align = 1 ,sort = 9)
	private String explanation; //处置说明
	private String unit;
	private String unitId;
	@ExcelField(title = "舆情发现日期" , type = 0 , align = 1 ,sort = 2)
	private Date foundDate;   //舆情发现日期
	@ExcelField(title = "宣教部门意见" , type = 0 , align = 1 ,sort = 6)
	private String xjOpinion;  //宣教部门意见
	@ExcelField(title = "专业部门意见" , type = 0 , align = 1 ,sort = 7)
	private String zyOpinion;   // 专业部门意见
	@ExcelField(title = "责任单位反馈" , type = 0 , align = 1 ,sort = 8)
	private String feedback;   //责任单位反馈
	private String archive;  // 办结归档
	@ExcelField(title = "责任单位" , type = 0 , align = 1 ,sort = 4)
	private String zrUnit;  // 责任单位
	private String zrUnitId;   // 责任单位id
	private String man;
	private String manId;
	private String submitMan;
	private String submitManId;

	private Date startDate;
	private Date endDate;
	private Date beginDate;
	private Date finishDate;
	private String userId;
	
	public AffairYqContol() {
		super();
	}

	public AffairYqContol(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getLeaderPs() {
		return leaderPs;
	}

	public void setLeaderPs(String leaderPs) {
		this.leaderPs = leaderPs;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
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

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getXjOpinion() {
		return xjOpinion;
	}

	public void setXjOpinion(String xjOpinion) {
		this.xjOpinion = xjOpinion;
	}

	public String getZyOpinion() {
		return zyOpinion;
	}

	public void setZyOpinion(String zyOpinion) {
		this.zyOpinion = zyOpinion;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getArchive() {
		return archive;
	}

	public void setArchive(String archive) {
		this.archive = archive;
	}

	public String getZrUnit() {
		return zrUnit;
	}

	public void setZrUnit(String zrUnit) {
		this.zrUnit = zrUnit;
	}

	public String getZrUnitId() {
		return zrUnitId;
	}

	public void setZrUnitId(String zrUnitId) {
		this.zrUnitId = zrUnitId;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}

	public String getManId() {
		return manId;
	}

	public void setManId(String manId) {
		this.manId = manId;
	}

	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}

	public String getSubmitManId() {
		return submitManId;
	}

	public void setSubmitManId(String submitManId) {
		this.submitManId = submitManId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
}