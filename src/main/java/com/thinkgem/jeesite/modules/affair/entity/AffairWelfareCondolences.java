/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 福利慰问Entity
 * @author daniel.liu
 * @version 2020-05-12
 */
public class AffairWelfareCondolences extends DataEntity<AffairWelfareCondolences> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 个人姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 3)
	private String unit;		// 单位
	private String unitId;		// 单位机构id
	@ExcelField(title = "福利项目", type = 0, align = 2, sort = 4, dictType="affair_benefits")
	private String type;		// 福利项目（1：住院探望 2：民警死亡 3：家属困难 4：受灾 5：直系亲属死亡 6：退休 7：防暑防寒 8：节假日 9：结婚 10：女民警生育 11：其他）
	/*改回String类型，double再导入时报错，未发现使用double的需求*/
	@ExcelField(title = "慰问金（慰问品）", type = 0, align = 2, sort = 5)
	private String money;		// 慰问金（慰问品）

//	@ExcelField(title = "时间", type = 0, align = 2, sort = 7)
//	private Date date;		// 时间

	@ExcelField(title = "内容", type = 0, align = 2, sort = 8)
	private String content;		// 内容
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9)
	private String remark;		// 备注
	@ExcelField(title = "经办人", type = 0, align = 2, sort = 7)
	private String manager;		// 经办人
	@ExcelField(title = "发生日期", type = 0, align = 2, sort = 6)
	private Date occurDate;		// 发生日期

	private String filePath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id

	private Date startDate;
	private Date endDate;
	private String userId;

	/*821问题反馈 删除标题*/
	/*新增字段*/
//	@ExcelField(title = "标题", type = 0, align = 2, sort = 1)
	private String title;		//标题
	private  Date entryTime;		//录入时间

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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}


	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public AffairWelfareCondolences() {
		super();
	}

	public AffairWelfareCondolences(String id){
		super(id);
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
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
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
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}