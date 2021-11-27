/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考评管理Entity
 *
 * @author bradley.zhao
 * @version 2019-12-20
 */
public class ExamWorkflow extends DataEntity<ExamWorkflow> {

    private static final long serialVersionUID = 1L;
    private String flowTemplateId;        // 定义流程ID
    private Integer status;        // 流程状态
    private String name;        // 名称
    private Date startDate;        // 流程启动时间
    private Date endDate;        // 流程结束时间
    private List<ExamWorflowSegments> examWorflowSegmentsList = Lists.newArrayList();        // 子表列表
    private ArrayList<ExamWorkflowDatas> datas = Lists.newArrayList();//数据列表
    private ArrayList<ExamWorkflowDatas> standardDatas = Lists.newArrayList();//考评标准项数据列表

    //附加属性
    private String examCycle;       //1月  2年
    private String examType;
    private String examObjectType;
    private String flowType;
    private Integer operationStatus; //操作状态（-1：未处理，0：已保存，1：已提交，2：已退回
    private String time;//考评时间

    /*根据考评标准内容查询考评项时使用,添加到此处改动量小*/
    private String condition;


    private String examStandard;

    public ExamWorkflow() {
        super();
    }

    public ExamWorkflow(String id) {
        super(id);
    }

    public String getFlowTemplateId() {
        return flowTemplateId;
    }

    public void setFlowTemplateId(String flowTemplateId) {
        this.flowTemplateId = flowTemplateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<ExamWorflowSegments> getExamWorflowSegmentsList() {
        return examWorflowSegmentsList;
    }

    public void setExamWorflowSegmentsList(List<ExamWorflowSegments> examWorflowSegmentsList) {
        this.examWorflowSegmentsList = examWorflowSegmentsList;
    }

    public String getExamCycle() {
        return examCycle;
    }

    public void setExamCycle(String examCycle) {
        this.examCycle = examCycle;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamObjectType() {
        return examObjectType;
    }

    public void setExamObjectType(String examObjectType) {
        this.examObjectType = examObjectType;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public ArrayList<ExamWorkflowDatas> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ExamWorkflowDatas> datas) {
        this.datas = datas;
    }

    public Integer getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(Integer operationStatus) {
        this.operationStatus = operationStatus;
    }

    public ArrayList<ExamWorkflowDatas> getStandardDatas() {
        return standardDatas;
    }

    public void setStandardDatas(ArrayList<ExamWorkflowDatas> standardDatas) {
        this.standardDatas = standardDatas;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getExamStandard() {
        return examStandard;
    }

    public void setExamStandard(String examStandard) {
        this.examStandard = examStandard;
    }
}