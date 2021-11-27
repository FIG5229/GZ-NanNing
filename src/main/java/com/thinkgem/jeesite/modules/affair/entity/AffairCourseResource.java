/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 课程资源Entity
 * @author alan.wu
 * @version 2020-07-31
 */
public class AffairCourseResource extends DataEntity<AffairCourseResource> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;		//序号
	@ExcelField(title = "课程名称", type = 0, align = 2, sort = 1)
	private String name;		// 课程名称
	private String code;		// 课程编码
	@ExcelField(title = "课程类型", type = 0, align = 2, sort = 4)
	private String type;		// 课程类型
	private String label;		// 课程标签
	@ExcelField(title = "学时", type = 0, align = 2, sort = 5)
	private String time;		// 学时
	@ExcelField(title = "是否公开课", type = 0, align = 2, sort = 7)
	private String open;		// 是否公开课
	private String teacher;		// 辅导，授权讲师
	@ExcelField(title = "发布状态", type = 0, align = 2, sort = 8)
	private String state;		// 发布状态
	@ExcelField(title = "课程类型", type = 0, align = 2, sort = 2)
	private String classify;		// 课程分类
	private String language;		// 语言
	@ExcelField(title = "作者", type = 0, align = 2, sort = 9)
	private String author;		// 作者
	@ExcelField(title = "作者", type = 0, align = 2, sort = 10)
	private String unit;		// 制作单位
	private String unitId;		// 制作单位id
	@ExcelField(title = "学分", type = 0, align = 2, sort = 6)
	private String learnScore;		//学分

	private String description;		// 课程简介
	private String adjunct;		// 课程封面
	private String terminal;		// 适用终端
	private String formset;		// 表现形式

	@ExcelField(title = "课件标准", type = 0, align = 2, sort = 3)
	private String norm;		// 课件标准
	private String stencil;		// 课件模板
	private String entrance;		// PC端入口地址
	private String swf;		// 当前课件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String classId;			//班级id

	private String	plan;			//学习进度
	private String  learnTime;		//学习时长
	private String	score;			//课后试卷考试成绩
	private String	assess;			//提交课程评估
	private String	scoreSelf;			//课件本身自带测试题成绩
	private String	kqTest;			//课前测试是否启用
	private String	precondition;			//参加考试的前提条件
	private String	schedule;			//前提条件-学习进度
	private String	qtLearnTime;			//前提条件-学习时长
	private String	testTime;			//考试时长
	private String	shape;			//卷面显示形式
	private String	degree;			//允许参加考试次数
	private String	grade;			//通过分数
	private String	paper;			//设置试卷
	private String	khTest;			//课后考试是否启用
	private String	premise;			//参加课后考试前提条件
	private String	premiseSchedule;			//课后考试前提条件—进度
	private String	premiseTime;			//课后考试前提条件—时长
	private String	khTime;					//课后考试时长
	private String	khShape;			//课后卷面显示形式
	private String	khDegree;			//课后允许参加考试次数
	private String	khPaper;	 		//课后设置试卷
	private String 	answer;				//是否启用答疑室

	private String lessonApproval;			//选课审批

	public String getLearnScore() {
		return learnScore;
	}

	public void setLearnScore(String learnScore) {
		this.learnScore = learnScore;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getLessonApproval() {
		return lessonApproval;
	}

	public void setLessonApproval(String lessonApproval) {
		this.lessonApproval = lessonApproval;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(String learnTime) {
		this.learnTime = learnTime;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getAssess() {
		return assess;
	}

	public void setAssess(String assess) {
		this.assess = assess;
	}

	public String getScoreSelf() {
		return scoreSelf;
	}

	public void setScoreSelf(String scoreSelf) {
		this.scoreSelf = scoreSelf;
	}

	public String getKqTest() {
		return kqTest;
	}

	public void setKqTest(String kqTest) {
		this.kqTest = kqTest;
	}

	public String getPrecondition() {
		return precondition;
	}

	public void setPrecondition(String precondition) {
		this.precondition = precondition;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getQtLearnTime() {
		return qtLearnTime;
	}

	public void setQtLearnTime(String qtLearnTime) {
		this.qtLearnTime = qtLearnTime;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public String getKhTest() {
		return khTest;
	}

	public void setKhTest(String khTest) {
		this.khTest = khTest;
	}

	public String getPremise() {
		return premise;
	}

	public void setPremise(String premise) {
		this.premise = premise;
	}

	public String getPremiseSchedule() {
		return premiseSchedule;
	}

	public void setPremiseSchedule(String premiseSchedule) {
		this.premiseSchedule = premiseSchedule;
	}

	public String getPremiseTime() {
		return premiseTime;
	}

	public void setPremiseTime(String premiseTime) {
		this.premiseTime = premiseTime;
	}

	public String getKhTime() {
		return khTime;
	}

	public void setKhTime(String khTime) {
		this.khTime = khTime;
	}

	public String getKhShape() {
		return khShape;
	}

	public void setKhShape(String khShape) {
		this.khShape = khShape;
	}

	public String getKhDegree() {
		return khDegree;
	}

	public void setKhDegree(String khDegree) {
		this.khDegree = khDegree;
	}

	public String getKhPaper() {
		return khPaper;
	}

	public void setKhPaper(String khPaper) {
		this.khPaper = khPaper;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public AffairCourseResource() {
		super();
	}

	public AffairCourseResource(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}
	
	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
	}
	
	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	public String getFormset() {
		return formset;
	}

	public void setFormset(String formset) {
		this.formset = formset;
	}
	
	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}
	
	public String getStencil() {
		return stencil;
	}

	public void setStencil(String stencil) {
		this.stencil = stencil;
	}
	
	public String getEntrance() {
		return entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}
	
	public String getSwf() {
		return swf;
	}

	public void setSwf(String swf) {
		this.swf = swf;
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
	
}