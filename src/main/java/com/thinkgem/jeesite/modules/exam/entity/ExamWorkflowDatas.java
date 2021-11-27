/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 考评数据表Entity
 *
 * @author bradley.zhao
 * @version 2019-12-19
 */
public class ExamWorkflowDatas extends DataEntity<ExamWorkflowDatas> {

    private static final long serialVersionUID = 1L;
    private String rowId;        // 考评标准项目ID
    private String type;        // 考评标准项目类型
    private String project;        // 考评标准模板项目
    private String detail;        // 说明
    private String value;        // 值
    private Integer valueType;   // 值类型
    private String path;        // 附件路径
    private String workflowId;        // 考评流程ID
    private String objId;        // 被考评对象ID
    private String objName;        // 被考评对象名称
    private String fillPerson;        // 填报人
    private String examPerson;        // 初步审核人
    private String departSign;        // 部门签字负责人
    private String partBureausSign;        // 分管局签字领导
    private String adjustPerson;        // 绩效调整人
    private String mainBureausSign;        // 局主管领导
    private String fillPersonId;        // 填报人ID
    private String examPersonId;        // 初步审核人ID
    private String departSignId;        // 部门签字负责人ID
    private String partBureausSignId;        // 分管局签字领导ID
    private String adjustPersonId;        // 绩效调整人ID
    private String mainBureausSignId;        // 局主管领导ID
    private String standardId;    //考评标准ID
    private Integer operationStatus; //操作状态
    private Integer status; //流程状态
    private String isSelected; //是否选中 0:未选中 1：选中。3：选中后删除,在初核时显示
    private Double weight; //权重
    private String workName; //工作项名称
    private String condition; //条件
    private String items;  //具体事项
    private String isAlreadySelected;       //9：推送过来的考评项 99：推送失败的考评项  66：推送失败后手动选择的考评项  77：自动考评的考评项

    private ArrayList<ExamWorkflowDatas> datas = Lists.newArrayList();//数据列表
    /*考评项内容 因为多个模板的考评数据在一个列表显示，只显示指定类型的字段，查询到数据后，放到s..字段中显示*/
    private String s1;
    private String s2;
    private String s3;
    private String s4;
    private String s5;
    private String s6;

    private String zhuRen;      //主任
    private String shuJi;       //书记
    private String zhuGuanLingDao;  //主管领导
    private String fuZhi;           //副职
    private String fuSuoZhang;      //副所长
    private String fuChuZhang;      //副处长
    private String fuZhiDuiZhang;       //副支队长
    private String suoZhang;            //所长
    private String jiaoDaoYuan;         //教导员
    private String zhengWei;            //政委
    private String chuZhang;            //处长
    private String zhiDuiZhang;         //支队长
    private String daDuiZhang;          //大队长
    private String zhuXi;               //主席
    private String fenGuanLingDao;      //分管领导
    private String gongAnChu;           //公安处
    private String zeRenDanWei;         //责任单位
    private String fuZhuRen;            //副主任
    private String zhuYaoLingDao;       //主要领导
    private String qiTaFuZhi;           //其他副职
    private String fuDaDuiZhang;        //副大队长
    private String baoBaoZhiDui;        //包保支队领导
    private String qiTaLingDao;         //其他领导
    private String benRen;              //本人
    private String special;

    /*新增字段  考评项推送记录的Id*/
    private String pushHistoryId;
    private String selectPersonId;  //选择此考评项的人
    private String delPersonId;     //删除此考评项的人
    //1:初核人员修改，2：绩效办修改
    private String isAlter;     //是否修改，初核人员和绩效办调整人员手动选择 ，修改后边变色


    /*前端使用*/
    private List<String> rowNums;   //考评项的行号
    private String[] ids;           //考评对象的ID
    private String examType;

    /*移动端使用*/
    private String personId;
    private String personName;
    private String personType;
    private String processType;
    private Integer nextStatus;

    public ExamWorkflowDatas() {
        super();
    }

    public ExamWorkflowDatas(String id) {
        super(id);
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getFillPerson() {
        return fillPerson;
    }

    public void setFillPerson(String fillPerson) {
        this.fillPerson = fillPerson;
    }

    public String getExamPerson() {
        return examPerson;
    }

    public void setExamPerson(String examPerson) {
        this.examPerson = examPerson;
    }

    public String getDepartSign() {
        return departSign;
    }

    public void setDepartSign(String departSign) {
        this.departSign = departSign;
    }

    public String getPartBureausSign() {
        return partBureausSign;
    }

    public void setPartBureausSign(String partBureausSign) {
        this.partBureausSign = partBureausSign;
    }

    public String getAdjustPerson() {
        return adjustPerson;
    }

    public void setAdjustPerson(String adjustPerson) {
        this.adjustPerson = adjustPerson;
    }

    public String getMainBureausSign() {
        return mainBureausSign;
    }

    public void setMainBureausSign(String mainBureausSign) {
        this.mainBureausSign = mainBureausSign;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public Integer getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(Integer operationStatus) {
        this.operationStatus = operationStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getFillPersonId() {
        return fillPersonId;
    }

    public void setFillPersonId(String fillPersonId) {
        this.fillPersonId = fillPersonId;
    }

    public String getExamPersonId() {
        return examPersonId;
    }

    public void setExamPersonId(String examPersonId) {
        this.examPersonId = examPersonId;
    }

    public String getDepartSignId() {
        return departSignId;
    }

    public void setDepartSignId(String departSignId) {
        this.departSignId = departSignId;
    }

    public String getPartBureausSignId() {
        return partBureausSignId;
    }

    public void setPartBureausSignId(String partBureausSignId) {
        this.partBureausSignId = partBureausSignId;
    }

    public String getAdjustPersonId() {
        return adjustPersonId;
    }

    public void setAdjustPersonId(String adjustPersonId) {
        this.adjustPersonId = adjustPersonId;
    }

    public String getMainBureausSignId() {
        return mainBureausSignId;
    }

    public void setMainBureausSignId(String mainBureausSignId) {
        this.mainBureausSignId = mainBureausSignId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getIsAlreadySelected() {
        return isAlreadySelected;
    }

    public void setIsAlreadySelected(String isAlreadySelected) {
        this.isAlreadySelected = isAlreadySelected;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public String getS5() {
        return s5;
    }

    public void setS5(String s5) {
        this.s5 = s5;
    }

    public String getS6() {
        return s6;
    }

    public void setS6(String s6) {
        this.s6 = s6;
    }

    public ArrayList<ExamWorkflowDatas> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ExamWorkflowDatas> datas) {
        this.datas = datas;
    }

    public String getPushHistoryId() {
        return pushHistoryId;
    }

    public void setPushHistoryId(String pushHistoryId) {
        this.pushHistoryId = pushHistoryId;
    }

    public List<String> getRowNums() {
        return rowNums;
    }

    public void setRowNums(List<String> rowNums) {
        this.rowNums = rowNums;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getIsAlter() {
        return isAlter;
    }

    public void setIsAlter(String isAlter) {
        this.isAlter = isAlter;
    }

    public String getZhuRen() {
        return zhuRen;
    }

    public void setZhuRen(String zhuRen) {
        this.zhuRen = zhuRen;
    }

    public String getShuJi() {
        return shuJi;
    }

    public void setShuJi(String shuJi) {
        this.shuJi = shuJi;
    }

    public String getZhuGuanLingDao() {
        return zhuGuanLingDao;
    }

    public void setZhuGuanLingDao(String zhuGuanLingDao) {
        this.zhuGuanLingDao = zhuGuanLingDao;
    }

    public String getFuZhi() {
        return fuZhi;
    }

    public void setFuZhi(String fuZhi) {
        this.fuZhi = fuZhi;
    }

    public String getFuSuoZhang() {
        return fuSuoZhang;
    }

    public void setFuSuoZhang(String fuSuoZhang) {
        this.fuSuoZhang = fuSuoZhang;
    }

    public String getFuChuZhang() {
        return fuChuZhang;
    }

    public void setFuChuZhang(String fuChuZhang) {
        this.fuChuZhang = fuChuZhang;
    }

    public String getFuZhiDuiZhang() {
        return fuZhiDuiZhang;
    }

    public void setFuZhiDuiZhang(String fuZhiDuiZhang) {
        this.fuZhiDuiZhang = fuZhiDuiZhang;
    }

    public String getSuoZhang() {
        return suoZhang;
    }

    public void setSuoZhang(String suoZhang) {
        this.suoZhang = suoZhang;
    }

    public String getJiaoDaoYuan() {
        return jiaoDaoYuan;
    }

    public void setJiaoDaoYuan(String jiaoDaoYuan) {
        this.jiaoDaoYuan = jiaoDaoYuan;
    }

    public String getZhengWei() {
        return zhengWei;
    }

    public void setZhengWei(String zhengWei) {
        this.zhengWei = zhengWei;
    }

    public String getChuZhang() {
        return chuZhang;
    }

    public void setChuZhang(String chuZhang) {
        this.chuZhang = chuZhang;
    }

    public String getZhiDuiZhang() {
        return zhiDuiZhang;
    }

    public void setZhiDuiZhang(String zhiDuiZhang) {
        this.zhiDuiZhang = zhiDuiZhang;
    }

    public String getDaDuiZhang() {
        return daDuiZhang;
    }

    public void setDaDuiZhang(String daDuiZhang) {
        this.daDuiZhang = daDuiZhang;
    }

    public String getZhuXi() {
        return zhuXi;
    }

    public void setZhuXi(String zhuXi) {
        this.zhuXi = zhuXi;
    }

    public String getFenGuanLingDao() {
        return fenGuanLingDao;
    }

    public void setFenGuanLingDao(String fenGuanLingDao) {
        this.fenGuanLingDao = fenGuanLingDao;
    }

    public String getGongAnChu() {
        return gongAnChu;
    }

    public void setGongAnChu(String gongAnChu) {
        this.gongAnChu = gongAnChu;
    }

    public String getZeRenDanWei() {
        return zeRenDanWei;
    }

    public void setZeRenDanWei(String zeRenDanWei) {
        this.zeRenDanWei = zeRenDanWei;
    }

    public String getFuZhuRen() {
        return fuZhuRen;
    }

    public void setFuZhuRen(String fuZhuRen) {
        this.fuZhuRen = fuZhuRen;
    }

    public String getZhuYaoLingDao() {
        return zhuYaoLingDao;
    }

    public void setZhuYaoLingDao(String zhuYaoLingDao) {
        this.zhuYaoLingDao = zhuYaoLingDao;
    }

    public String getQiTaFuZhi() {
        return qiTaFuZhi;
    }

    public void setQiTaFuZhi(String qiTaFuZhi) {
        this.qiTaFuZhi = qiTaFuZhi;
    }

    public String getFuDaDuiZhang() {
        return fuDaDuiZhang;
    }

    public void setFuDaDuiZhang(String fuDaDuiZhang) {
        this.fuDaDuiZhang = fuDaDuiZhang;
    }

    public String getBaoBaoZhiDui() {
        return baoBaoZhiDui;
    }

    public void setBaoBaoZhiDui(String baoBaoZhiDui) {
        this.baoBaoZhiDui = baoBaoZhiDui;
    }

    public String getQiTaLingDao() {
        return qiTaLingDao;
    }

    public void setQiTaLingDao(String qiTaLingDao) {
        this.qiTaLingDao = qiTaLingDao;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public Integer getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(Integer nextStatus) {
        this.nextStatus = nextStatus;
    }

    public String getSelectPersonId() {
        return selectPersonId;
    }

    public void setSelectPersonId(String selectPersonId) {
        this.selectPersonId = selectPersonId;
    }

    public String getDelPersonId() {
        return delPersonId;
    }

    public void setDelPersonId(String delPersonId) {
        this.delPersonId = delPersonId;
    }

    public String getBenRen() {
        return benRen;
    }

    public void setBenRen(String benRen) {
        this.benRen = benRen;
    }
}