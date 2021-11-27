/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 班子成员Entity
 * @author eav.liu
 * @version 2019-11-05
 */
public class AffairClassMember extends DataEntity<AffairClassMember> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "党组织名称", type = 0, align = 2, sort = 0)
	private String partyOrganization;		// 党组织名称
	private String partyOrganizationId;		// 党组织id
	@ExcelField(title = "党组织书记", type = 0, align = 2, sort = 1)
	private String shuji;		// 党组织书记
	@ExcelField(title = "党组织副书记", type = 0, align = 2, sort = 2)
	private String fushuji;		// 党组织副书记
	@ExcelField(title = "组织委员", type = 0, align = 2, sort = 3)
	private String zzwy;		// 组织委员
	@ExcelField(title = "纪检委员", type = 0, align = 2, sort = 4)
	private String jjwy;		// 纪检委员
	@ExcelField(title = "宣传委员", type = 0, align = 2, sort = 5)
	private String xcwy;		// 宣传委员
	@ExcelField(title = "青年委员", type = 0, align = 2, sort = 6)
	private String yqwy;		// 青年委员
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String filePath;		//附件
	@ExcelField(title = "联系包保情况", type = 0, align = 2, sort = 7)
	private String situation;		//联系包保情况

	private String treeId;	//接受前端传来的左侧树的id


	private String status; 		//审核状态
	private String opinion;		//审核意见

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public AffairClassMember() {
		super();
	}

	public AffairClassMember(String id){
		super(id);
	}

	public String getPartyOrganization() {
		return partyOrganization;
	}

	public void setPartyOrganization(String partyOrganization) {
		this.partyOrganization = partyOrganization;
	}
	
	public String getPartyOrganizationId() {
		return partyOrganizationId;
	}

	public void setPartyOrganizationId(String partyOrganizationId) {
		this.partyOrganizationId = partyOrganizationId;
	}
	
	public String getShuji() {
		return shuji;
	}

	public void setShuji(String shuji) {
		this.shuji = shuji;
	}
	
	public String getFushuji() {
		return fushuji;
	}

	public void setFushuji(String fushuji) {
		this.fushuji = fushuji;
	}
	
	public String getZzwy() {
		return zzwy;
	}

	public void setZzwy(String zzwy) {
		this.zzwy = zzwy;
	}
	
	public String getJjwy() {
		return jjwy;
	}

	public void setJjwy(String jjwy) {
		this.jjwy = jjwy;
	}
	
	public String getXcwy() {
		return xcwy;
	}

	public void setXcwy(String xcwy) {
		this.xcwy = xcwy;
	}
	
	public String getYqwy() {
		return yqwy;
	}

	public void setYqwy(String yqwy) {
		this.yqwy = yqwy;
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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
}