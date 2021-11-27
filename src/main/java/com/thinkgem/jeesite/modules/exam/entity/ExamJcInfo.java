/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 奖惩信息库Entity
 * @author mason.xv
 * @version 2020-01-03
 */
public class ExamJcInfo extends DataEntity<ExamJcInfo> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "奖惩类型", type = 0, align = 2, sort = 0, dictType="jc_types")
	private String jcType;		// 奖惩类型 0：单位	1：个人
	@ExcelField(title = "名称", type = 0, align = 2, sort = 1)
	private String jcTypeName;		// 奖惩类型_名称
	@ExcelField(title = "选择项", type = 0, align = 2, sort = 2)
	private String changeType;   //选择项
	@ExcelField(title = "时间", type = 0, align = 2, sort = 3)
	private Date jcDate ;    //时间
	@ExcelField(title = "加减分情况", type = 0, align = 2, sort = 4)
	private String jcCode;		// 奖惩类型_加减分情况
	private String jcObject;		// 奖惩对象
	@ExcelField(title = "奖惩对象", type = 0, align = 2, sort = 5)
	private String jcObjectPersonnel;		// 奖惩对象_人
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 6)
	private String idNumber;		// 奖惩对象_人
	@ExcelField(title = "单位", type = 0, align = 2, sort = 7)
	private String jcObjectUnit;		// 奖惩对象_单位
	private String jcObjectUnitId;		// 奖惩对象_单位_ID
	@ExcelField(title = "使用模板", type = 0, align = 2, sort = 9)
	private String myUseModel;		// 使用模板id
	@ExcelField(title = "绩效考评标准", type = 0, align = 2, sort = 10)
	private String examStandart;		// 绩效考评标准
	@ExcelField(title = "奖惩文号", type = 0, align = 2, sort = 11)
	private String fileNum;         //文号
	private String myUseModelName;		// 使用模板名称
	/*新增字段*/
	private String rowNum;		//考评标准的行号

	private String pushNum;           //推送数字(用于区分是哪一部分的推送监察信息库)

	private String filePath;		// 相关文件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "奖惩对象_党组织", type = 0, align = 2, sort = 8)
	private String jcOrg;     //奖惩对象_党组织
	private String jcOrgId;   //奖惩对象 _党组织id

	/*绩效考评查询使用*/
	private int month;	//月份
	private int year;	//年度
	private List<String> defineList;	//绩效流程的考评模板id
	private String objId;			//奖惩对象的登录账号Id

	
	public ExamJcInfo() {
		super();
	}

	public ExamJcInfo(String id){
		super(id);
	}

	public String getJcType() {
		return jcType;
	}

	public void setJcType(String jcType) {
		this.jcType = jcType;
	}
	
	public String getJcTypeName() {
		return jcTypeName;
	}

	public void setJcTypeName(String jcTypeName) {
		this.jcTypeName = jcTypeName;
	}
	
	public String getJcCode() {
		return jcCode;
	}

	public void setJcCode(String jcCode) {
		this.jcCode = jcCode;
	}
	
	public String getJcObject() {
		return jcObject;
	}

	public void setJcObject(String jcObject) {
		this.jcObject = jcObject;
	}
	
	public String getJcObjectPersonnel() {
		return jcObjectPersonnel;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public Date getJcDate() {
		return jcDate;
	}

	public void setJcDate(Date jcDate) {
		this.jcDate = jcDate;
	}

	public void setJcObjectPersonnel(String jcObjectPersonnel) {
		this.jcObjectPersonnel = jcObjectPersonnel;
	}
	
	public String getJcObjectUnit() {
		return jcObjectUnit;
	}

	public void setJcObjectUnit(String jcObjectUnit) {
		this.jcObjectUnit = jcObjectUnit;
	}
	
	public String getJcObjectUnitId() {
		return jcObjectUnitId;
	}

	public void setJcObjectUnitId(String jcObjectUnitId) {
		this.jcObjectUnitId = jcObjectUnitId;
	}

	public String getMyUseModel() {
		return myUseModel;
	}

	public void setMyUseModel(String myUseModel) {
		this.myUseModel = myUseModel;
	}

	public String getExamStandart() {
		return examStandart;
	}

	public void setExamStandart(String examStandart) {
		this.examStandart = examStandart;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPushNum() {
		return pushNum;
	}

	public void setPushNum(String pushNum) {
		this.pushNum = pushNum;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getMyUseModelName() {
		return myUseModelName;
	}

	public void setMyUseModelName(String myUseModelName) {
		this.myUseModelName = myUseModelName;
	}

	public String getJcOrg() {
		return jcOrg;
	}

	public void setJcOrg(String jcOrg) {
		this.jcOrg = jcOrg;
	}

	public String getJcOrgId() {
		return jcOrgId;
	}

	public void setJcOrgId(String jcOrgId) {
		this.jcOrgId = jcOrgId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<String> getDefineList() {
		return defineList;
	}

	public void setDefineList(List<String> defineList) {
		this.defineList = defineList;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}
}