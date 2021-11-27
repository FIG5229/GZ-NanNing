/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 档案管理统计台账Entity
 * @author cecil.li
 * @version 2020-02-26
 */
public class AffairCheckCount extends DataEntity<AffairCheckCount> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "副局", type = 0, align = 2, sort = 1)
	private String bjFuju;		// 副局
	@ExcelField(title = "正处", type = 0, align = 2, sort = 2)
	private String bjZhengchu;		// 正处
	@ExcelField(title = "副处", type = 0, align = 2, sort = 3)
	private String bjFuchu;		// 副处
	@ExcelField(title = "正科", type = 0, align = 2, sort = 4)
	private String bjZhengke;		// 正科
	@ExcelField(title = "副科", type = 0, align = 2, sort = 5)
	private String bjFuke;
	@ExcelField(title = "科员", type = 0, align = 2, sort = 6)
	private String bjKeyuan;		// 科员
	@ExcelField(title = "办事员", type = 0, align = 2, sort = 7)
	private String bjBanshiyuan;		// 办事员
	@ExcelField(title = "见习", type = 0, align = 2, sort = 8)
	private String bjJianxi;		// 见习
	@ExcelField(title = "小计", type = 0, align = 2, sort = 8)
	private String bjXiaoji;		// 小计
	@ExcelField(title = "副局", type = 0, align = 2, sort = 10)
	private String sjFuju;		// 副局
	@ExcelField(title = "正处", type = 0, align = 2, sort = 11)
	private String sjZhengchu;		// 正处
	@ExcelField(title = "副处", type = 0, align = 2, sort = 12)
	private String sjFuchu;		// 副处
	@ExcelField(title = "正科", type = 0, align = 2, sort = 13)
	private String sjZhengke;		// 正科
	@ExcelField(title = "小计", type = 0, align = 2, sort = 14)
	private String sjXiaoji;		// 小计
	@ExcelField(title = "备注", type = 0, align = 2, sort = 15)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "档案", type = 0, align = 2, sort = 0)
	private String title;
	
	public AffairCheckCount() {
		super();
	}

	public AffairCheckCount(String id){
		super(id);
	}

	public String getBjFuju() {
		return bjFuju;
	}

	public void setBjFuju(String bjFuju) {
		this.bjFuju = bjFuju;
	}
	
	public String getBjZhengchu() {
		return bjZhengchu;
	}

	public void setBjZhengchu(String bjZhengchu) {
		this.bjZhengchu = bjZhengchu;
	}
	
	public String getBjFuchu() {
		return bjFuchu;
	}

	public void setBjFuchu(String bjFuchu) {
		this.bjFuchu = bjFuchu;
	}
	
	public String getBjZhengke() {
		return bjZhengke;
	}

	public void setBjZhengke(String bjZhengke) {
		this.bjZhengke = bjZhengke;
	}
	
	public String getBjKeyuan() {
		return bjKeyuan;
	}

	public void setBjKeyuan(String bjKeyuan) {
		this.bjKeyuan = bjKeyuan;
	}
	
	public String getBjBanshiyuan() {
		return bjBanshiyuan;
	}

	public void setBjBanshiyuan(String bjBanshiyuan) {
		this.bjBanshiyuan = bjBanshiyuan;
	}
	
	public String getBjJianxi() {
		return bjJianxi;
	}

	public void setBjJianxi(String bjJianxi) {
		this.bjJianxi = bjJianxi;
	}
	
	public String getBjXiaoji() {
		return bjXiaoji;
	}

	public void setBjXiaoji(String bjXiaoji) {
		this.bjXiaoji = bjXiaoji;
	}
	
	public String getSjFuju() {
		return sjFuju;
	}

	public void setSjFuju(String sjFuju) {
		this.sjFuju = sjFuju;
	}
	
	public String getSjZhengchu() {
		return sjZhengchu;
	}

	public void setSjZhengchu(String sjZhengchu) {
		this.sjZhengchu = sjZhengchu;
	}
	
	public String getSjFuchu() {
		return sjFuchu;
	}

	public void setSjFuchu(String sjFuchu) {
		this.sjFuchu = sjFuchu;
	}
	
	public String getSjZhengke() {
		return sjZhengke;
	}

	public void setSjZhengke(String sjZhengke) {
		this.sjZhengke = sjZhengke;
	}
	
	public String getSjXiaoji() {
		return sjXiaoji;
	}

	public void setSjXiaoji(String sjXiaoji) {
		this.sjXiaoji = sjXiaoji;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBjFuke() {
		return bjFuke;
	}

	public void setBjFuke(String bjFuke) {
		this.bjFuke = bjFuke;
	}
}