/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 领导干部出国管理表Entity
 * @author mason.xv
 * @version 2019-11-06
 */
public class PersonnelGoAbroadTwo extends DataEntity<PersonnelGoAbroadTwo> {

	private static final long serialVersionUID = 1L;
    @ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
    @ExcelField(title = "出国境时单位及职务", type = 0, align = 2, sort = 1)
	private String unitJob;		// 出国境时单位及职务
    @ExcelField(title = "出国境身份", type = 0, align = 2, sort = 2)
	private String identity;		// 出国境身份
    @ExcelField(title = "出国境性质", type = 0, align = 2, sort = 3)
	private String character;		// 出国境性质
    @ExcelField(title = "出国事由", type = 0, align = 2, sort = 4)
	private String reason;		// 出国事由
    @ExcelField(title = "持有护照（通行证）上交日期", type = 0, align = 2, sort = 5)
	private Date handinDate;		// 持有护照（通行证）上交日期
    @ExcelField(title = "持有护照（通行证）领用日期", type = 0, align = 2, sort = 6)
	private Date receiveDate;		// 持有护照（通行证）领用日期
    @ExcelField(title = "出国（境）日期", type = 0, align = 2, sort = 7)
	private Date goAbroadDate;		// 出国（境）日期
    @ExcelField(title = "回国日期", type = 0, align = 2, sort = 8)
	private Date returnDate;		// 回国日期
    @ExcelField(title = "所至国别(地区)", type = 0, align = 2, sort = 9)
	private String toPlace;		// 所至国别(地区)
    @ExcelField(title = "出国（境）团组名称", type = 0, align = 2, sort = 10)
	private String groupName;		// 出国（境）团组名称
    @ExcelField(title = "出国（境）审批单位名称", type = 0, align = 2, sort = 11)
	private String approvalUnitName;		// 出国（境）审批单位名称
    @ExcelField(title = "出国（境）审批单位代码", type = 0, align = 2, sort = 12)
	private String approvalUnitCode;		// 出国（境）审批单位代码
    @ExcelField(title = "出国（境）审批文号", type = 0, align = 2, sort = 13)
	private String approvalFileNo;		// 出国（境）审批文号
    @ExcelField(title = "派出单位", type = 0, align = 2, sort = 14)
	private String assignUnit;		// 派出单位
    @ExcelField(title = "派出单位代码", type = 0, align = 2, sort = 15)
	private String assignUnitCode;		// 派出单位代码
    @ExcelField(title = "所至单位", type = 0, align = 2, sort = 16)
	private String toUnit;		// 所至单位
    @ExcelField(title = "出国（境）经费来源", type = 0, align = 2, sort = 17)
	private String fundsSource;		// 出国（境）经费来源
    @ExcelField(title = "本次使用护照（通行证）编号", type = 0, align = 2, sort = 18)
	private String passportNo;		// 本次使用护照（通行证）编号
    @ExcelField(title = "附件", type = 0, align = 2, sort = 19)
	private String annex;		// 附件
    @ExcelField(title = "本次组织部门审查意见", type = 0, align = 2, sort = 20)
	private String suggestion;		// 本次组织部门审查意见
    @ExcelField(title = "出国（境）备注", type = 0, align = 2, sort = 21)
    private String beizhu;    //出国（境）备注
    @ExcelField(title = "身份证号", type = 0, align = 2, sort = 22)
    private String idNumber;		// 身份证号


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginGoAbroadDate;		// 开始 出国（境）日期
	private Date endGoAbroadDate;		// 结束 出国（境）日期
	private Date beginReturnDate;		// 开始 回国日期
	private Date endReturnDate;		// 结束 回国日期
	private String userId;         //用户单位id
	private String cardNum;


	/*添加字段*/

	private String juChuCheckMan; //局处审核人
	private String juChuCheckId; //局处审核id
	private String submitMan; //提交人
	private String submitId; //提交id
	private String checkType; //审核状态
	private String opinion; //审核意见

	private String usersId;			//记录当前登录用户id
	private String officeId;        //记录当前用户部门id


	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getJuChuCheckMan() {
		return juChuCheckMan;
	}

	public void setJuChuCheckMan(String juChuCheckMan) {
		this.juChuCheckMan = juChuCheckMan;
	}

	public String getJuChuCheckId() {
		return juChuCheckId;
	}

	public void setJuChuCheckId(String juChuCheckId) {
		this.juChuCheckId = juChuCheckId;
	}

	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}

	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public PersonnelGoAbroadTwo() {
		super();
	}

	public PersonnelGoAbroadTwo(String id){
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
	
	public String getUnitJob() {
		return unitJob;
	}

	public void setUnitJob(String unitJob) {
		this.unitJob = unitJob;
	}
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getHandinDate() {
		return handinDate;
	}

	public void setHandinDate(Date handinDate) {
		this.handinDate = handinDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getGoAbroadDate() {
		return goAbroadDate;
	}

	public void setGoAbroadDate(Date goAbroadDate) {
		this.goAbroadDate = goAbroadDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getApprovalUnitName() {
		return approvalUnitName;
	}

	public void setApprovalUnitName(String approvalUnitName) {
		this.approvalUnitName = approvalUnitName;
	}
	
	public String getApprovalUnitCode() {
		return approvalUnitCode;
	}

	public void setApprovalUnitCode(String approvalUnitCode) {
		this.approvalUnitCode = approvalUnitCode;
	}
	
	public String getApprovalFileNo() {
		return approvalFileNo;
	}

	public void setApprovalFileNo(String approvalFileNo) {
		this.approvalFileNo = approvalFileNo;
	}
	
	public String getAssignUnit() {
		return assignUnit;
	}

	public void setAssignUnit(String assignUnit) {
		this.assignUnit = assignUnit;
	}
	
	public String getAssignUnitCode() {
		return assignUnitCode;
	}

	public void setAssignUnitCode(String assignUnitCode) {
		this.assignUnitCode = assignUnitCode;
	}
	
	public String getToUnit() {
		return toUnit;
	}

	public void setToUnit(String toUnit) {
		this.toUnit = toUnit;
	}
	
	public String getFundsSource() {
		return fundsSource;
	}

	public void setFundsSource(String fundsSource) {
		this.fundsSource = fundsSource;
	}
	
	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}
	
	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
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
	
	public Date getBeginGoAbroadDate() {
		return beginGoAbroadDate;
	}

	public void setBeginGoAbroadDate(Date beginGoAbroadDate) {
		this.beginGoAbroadDate = beginGoAbroadDate;
	}
	
	public Date getEndGoAbroadDate() {
		return endGoAbroadDate;
	}

	public void setEndGoAbroadDate(Date endGoAbroadDate) {
		this.endGoAbroadDate = endGoAbroadDate;
	}
		
	public Date getBeginReturnDate() {
		return beginReturnDate;
	}

	public void setBeginReturnDate(Date beginReturnDate) {
		this.beginReturnDate = beginReturnDate;
	}
	
	public Date getEndReturnDate() {
		return endReturnDate;
	}

	public void setEndReturnDate(Date endReturnDate) {
		this.endReturnDate = endReturnDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
}