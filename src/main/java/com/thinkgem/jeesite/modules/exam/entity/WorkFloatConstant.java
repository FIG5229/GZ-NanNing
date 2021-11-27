package com.thinkgem.jeesite.modules.exam.entity;

public class WorkFloatConstant {
    //status状态-1：流程状态（环节）（-1：未启动，1:系统自评,2:系统初步考核,3:系统公示,4:部门负责人签字,5:分管局领导签字,6:绩效考评领导小组复核及调整,7:局主管领导最终审签,8:最终结果全局公示,99:考评已经结束）
    public  static int NO_START = -1;       //未启动
    public  static int SELF_EXAM = 1;       //系统自评
    public  static int SIMPLE_EXAM = 2;     //系统初步考核
    public  static int EXAM_PUBLIC = 3;     //系统公示
    public  static int DEP_SIGN = 4;        //部门负责人签字
    public  static int PART_SIGN = 5;       //分管局领导签字
    public  static int GROUP_ADJUST = 6;    //绩效考评领导小组复核及调整
    public  static int COMPLET_SIGN = 7;    //局主管领导最终审签
    public  static int ALL_PUBLIC = 8;      //最终结果全局公示
    public  static int EXAM_END = 99;       //考评已经结束

}
