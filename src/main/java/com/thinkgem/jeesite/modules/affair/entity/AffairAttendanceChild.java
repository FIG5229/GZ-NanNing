/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 考勤记录子表Entity
 *
 * @author eav.liu
 * @version 2020-03-18
 */
public class AffairAttendanceChild extends DataEntity<AffairAttendanceChild> {

    private static final long serialVersionUID = 1L;
    private String attId;        // 主表id
    @ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
    private String name;        // 姓名
    @ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
    private String idNumber;        // 身份证号
    @ExcelField(title = "值勤岗位", type = 0, align = 2, sort = 2, dictType = "attendence_police_types")
    private String policeType;        // 值勤岗位
    private String lock32;
    @ExcelField(title = "线路岗位", type = 0, align = 2, sort = 3, dictType = "attendence_police_types")
    private String linePost;    //线路岗位
    private String lock33;
    @ExcelField(title = "用工形式", type = 0, align = 2, sort = 4, dictType = "attendence_work_types")
    private String workType;        // 用工形式
    private String lock34;
    @ExcelField(title = "1", type = 0, align = 2, sort = 5, dictType = "affair_attendence_types")
    private String day1;        // 1
    private String lock1;
    private String[] locks;
    @ExcelField(title = "2", type = 0, align = 2, sort = 6, dictType = "affair_attendence_types")
    private String day2;        // 2
    private String lock2;
    @ExcelField(title = "3", type = 0, align = 2, sort = 7, dictType = "affair_attendence_types")
    private String day3;        // 3
    private String lock3;
    @ExcelField(title = "4", type = 0, align = 2, sort = 8, dictType = "affair_attendence_types")
    private String day4;        // 4
    private String lock4;
    @ExcelField(title = "5", type = 0, align = 2, sort = 9, dictType = "affair_attendence_types")
    private String day5;        // 5
    private String lock5;
    @ExcelField(title = "6", type = 0, align = 2, sort = 10, dictType = "affair_attendence_types")
    private String day6;        // 6
    private String lock6;
    @ExcelField(title = "7", type = 0, align = 2, sort = 11, dictType = "affair_attendence_types")
    private String day7;        // 7
    private String lock7;
    @ExcelField(title = "8", type = 0, align = 2, sort = 12, dictType = "affair_attendence_types")
    private String day8;        // 8
    private String lock8;
    @ExcelField(title = "9", type = 0, align = 2, sort = 13, dictType = "affair_attendence_types")
    private String day9;        // 9
    private String lock9;
    @ExcelField(title = "10", type = 0, align = 2, sort = 14, dictType = "affair_attendence_types")
    private String day10;        // 10
    private String lock10;
    @ExcelField(title = "11", type = 0, align = 2, sort = 15, dictType = "affair_attendence_types")
    private String day11;        // 11
    private String lock11;
    @ExcelField(title = "12", type = 0, align = 2, sort = 16, dictType = "affair_attendence_types")
    private String day12;        // 12
    private String lock12;
    @ExcelField(title = "13", type = 0, align = 2, sort = 17, dictType = "affair_attendence_types")
    private String day13;        // 13
    private String lock13;
    @ExcelField(title = "14", type = 0, align = 2, sort = 18, dictType = "affair_attendence_types")
    private String day14;        // 14
    private String lock14;
    @ExcelField(title = "15", type = 0, align = 2, sort = 19, dictType = "affair_attendence_types")
    private String day15;        // 15
    private String lock15;
    @ExcelField(title = "16", type = 0, align = 2, sort = 20, dictType = "affair_attendence_types")
    private String day16;        // 16
    private String lock16;
    @ExcelField(title = "17", type = 0, align = 2, sort = 21, dictType = "affair_attendence_types")
    private String day17;        // 17
    private String lock17;
    @ExcelField(title = "18", type = 0, align = 2, sort = 22, dictType = "affair_attendence_types")
    private String day18;        // 18
    private String lock18;
    @ExcelField(title = "19", type = 0, align = 2, sort = 23, dictType = "affair_attendence_types")
    private String day19;        // 19
    private String lock19;
    @ExcelField(title = "20", type = 0, align = 2, sort = 24, dictType = "affair_attendence_types")
    private String day20;        // 20
    private String lock20;
    @ExcelField(title = "21", type = 0, align = 2, sort = 25, dictType = "affair_attendence_types")
    private String day21;        // 21
    private String lock21;
    @ExcelField(title = "22", type = 0, align = 2, sort = 26, dictType = "affair_attendence_types")
    private String day22;        // 22
    private String lock22;
    @ExcelField(title = "23", type = 0, align = 2, sort = 27, dictType = "affair_attendence_types")
    private String day23;        // 23
    private String lock23;
    @ExcelField(title = "24", type = 0, align = 2, sort = 28, dictType = "affair_attendence_types")
    private String day24;        // 24
    private String lock24;
    @ExcelField(title = "25", type = 0, align = 2, sort = 29, dictType = "affair_attendence_types")
    private String day25;        // 25
    private String lock25;
    @ExcelField(title = "26", type = 0, align = 2, sort = 30, dictType = "affair_attendence_types")
    private String day26;        // 26
    private String lock26;
    @ExcelField(title = "27", type = 0, align = 2, sort = 31, dictType = "affair_attendence_types")
    private String day27;        // 27
    private String lock27;
    @ExcelField(title = "28", type = 0, align = 2, sort = 32, dictType = "affair_attendence_types")
    private String day28;        // 28
    private String lock28;
    @ExcelField(title = "29", type = 0, align = 2, sort = 33, dictType = "affair_attendence_types")
    private String day29;        // 29
    private String lock29;
    @ExcelField(title = "30", type = 0, align = 2, sort = 34, dictType = "affair_attendence_types")
    private String day30;        // 30
    private String lock30;
    @ExcelField(title = "31", type = 0, align = 2, sort = 35, dictType = "affair_attendence_types")
    private String day31;        // 31
    private String lock31;
    @ExcelField(title = "当月工时", type = 0, align = 2, sort = 36)
    private String monthHours;        // 当月工时
    private String lock35;
    @ExcelField(title = "缺勤", type = 0, align = 2, sort = 37)
    private Integer lackWork;        // 缺勤
    @ExcelField(title = "工伤", type = 0, align = 2, sort = 38)
    private String workInjury;        // 工伤
    @ExcelField(title = "年休", type = 0, align = 2, sort = 39)
    private String yearWeak;        // 年休
    @ExcelField(title = "出差", type = 0, align = 2, sort = 40)
    private String goOut;        // 出差
    @ExcelField(title = "执勤", type = 0, align = 2, sort = 41)
    private String inWork;        // 执勤
    @ExcelField(title = "加班", type = 0, align = 2, sort = 42)
    private String overtime;        // 加班
    @ExcelField(title = "零星加班", type = 0, align = 2, sort = 43)
    private String overtimeChip;        // 零星加班
    @ExcelField(title = "备注", type = 0, align = 2, sort = 44)
    private String beizhu;     //备注
    private String jfDays;  // 加法天数
    private String lock36;
    private String lockFlag;
    private String createOrgId;        // 创建者机构id
    private String updateOrgId;        // 更新者机构id

    //前端使用
    private Integer year;
    private Integer month;
    private String unitId;
    private String unit;

    //绩效
    private Integer kuang;
    private String userId;

    public AffairAttendanceChild() {
        super();
    }

    public AffairAttendanceChild(String id) {
        super(id);
    }

    public String getAttId() {
        return attId;
    }

    public void setAttId(String attId) {
        this.attId = attId;
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

    public String getPoliceType() {
        return policeType;
    }

    public void setPoliceType(String policeType) {
        this.policeType = policeType;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getDay4() {
        return day4;
    }

    public void setDay4(String day4) {
        this.day4 = day4;
    }

    public String getDay5() {
        return day5;
    }

    public void setDay5(String day5) {
        this.day5 = day5;
    }

    public String getDay6() {
        return day6;
    }

    public void setDay6(String day6) {
        this.day6 = day6;
    }

    public String getDay7() {
        return day7;
    }

    public void setDay7(String day7) {
        this.day7 = day7;
    }

    public String getDay8() {
        return day8;
    }

    public void setDay8(String day8) {
        this.day8 = day8;
    }

    public String getDay9() {
        return day9;
    }

    public void setDay9(String day9) {
        this.day9 = day9;
    }

    public String getDay10() {
        return day10;
    }

    public void setDay10(String day10) {
        this.day10 = day10;
    }

    public String getDay11() {
        return day11;
    }

    public void setDay11(String day11) {
        this.day11 = day11;
    }

    public String getDay12() {
        return day12;
    }

    public void setDay12(String day12) {
        this.day12 = day12;
    }

    public String getDay13() {
        return day13;
    }

    public void setDay13(String day13) {
        this.day13 = day13;
    }

    public String getDay14() {
        return day14;
    }

    public void setDay14(String day14) {
        this.day14 = day14;
    }

    public String getDay15() {
        return day15;
    }

    public void setDay15(String day15) {
        this.day15 = day15;
    }

    public String getDay16() {
        return day16;
    }

    public void setDay16(String day16) {
        this.day16 = day16;
    }

    public String getDay17() {
        return day17;
    }

    public void setDay17(String day17) {
        this.day17 = day17;
    }

    public String getDay18() {
        return day18;
    }

    public void setDay18(String day18) {
        this.day18 = day18;
    }

    public String getDay19() {
        return day19;
    }

    public void setDay19(String day19) {
        this.day19 = day19;
    }

    public String getDay20() {
        return day20;
    }

    public void setDay20(String day20) {
        this.day20 = day20;
    }

    public String getDay21() {
        return day21;
    }

    public void setDay21(String day21) {
        this.day21 = day21;
    }

    public String getDay22() {
        return day22;
    }

    public void setDay22(String day22) {
        this.day22 = day22;
    }

    public String getDay23() {
        return day23;
    }

    public void setDay23(String day23) {
        this.day23 = day23;
    }

    public String getDay24() {
        return day24;
    }

    public void setDay24(String day24) {
        this.day24 = day24;
    }

    public String getDay25() {
        return day25;
    }

    public void setDay25(String day25) {
        this.day25 = day25;
    }

    public String getDay26() {
        return day26;
    }

    public void setDay26(String day26) {
        this.day26 = day26;
    }

    public String getDay27() {
        return day27;
    }

    public void setDay27(String day27) {
        this.day27 = day27;
    }

    public String getDay28() {
        return day28;
    }

    public void setDay28(String day28) {
        this.day28 = day28;
    }

    public String getDay29() {
        return day29;
    }

    public void setDay29(String day29) {
        this.day29 = day29;
    }

    public String getDay30() {
        return day30;
    }

    public void setDay30(String day30) {
        this.day30 = day30;
    }

    public String getDay31() {
        return day31;
    }

    public void setDay31(String day31) {
        this.day31 = day31;
    }

    public String getMonthHours() {
        return monthHours;
    }

    public void setMonthHours(String monthHours) {
        this.monthHours = monthHours;
    }

    public Integer getLackWork() {
        return lackWork;
    }

    public void setLackWork(Integer lackWork) {
        this.lackWork = lackWork;
    }

    public String getWorkInjury() {
        return workInjury;
    }

    public void setWorkInjury(String workInjury) {
        this.workInjury = workInjury;
    }

    public String getYearWeak() {
        return yearWeak;
    }

    public void setYearWeak(String yearWeak) {
        this.yearWeak = yearWeak;
    }

    public String getGoOut() {
        return goOut;
    }

    public void setGoOut(String goOut) {
        this.goOut = goOut;
    }

    public String getInWork() {
        return inWork;
    }

    public void setInWork(String inWork) {
        this.inWork = inWork;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getOvertimeChip() {
        return overtimeChip;
    }

    public void setOvertimeChip(String overtimeChip) {
        this.overtimeChip = overtimeChip;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLinePost() {
        return linePost;
    }

    public void setLinePost(String linePost) {
        this.linePost = linePost;
    }

    public String getJfDays() {
        return jfDays;
    }

    public void setJfDays(String jfDays) {
        this.jfDays = jfDays;
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getLock2() {
        return lock2;
    }

    public void setLock2(String lock2) {
        this.lock2 = lock2;
    }

    public String getLock3() {
        return lock3;
    }

    public void setLock3(String lock3) {
        this.lock3 = lock3;
    }

    public String getLock4() {
        return lock4;
    }

    public void setLock4(String lock4) {
        this.lock4 = lock4;
    }

    public String getLock5() {
        return lock5;
    }

    public void setLock5(String lock5) {
        this.lock5 = lock5;
    }

    public String getLock6() {
        return lock6;
    }

    public void setLock6(String lock6) {
        this.lock6 = lock6;
    }

    public String getLock7() {
        return lock7;
    }

    public void setLock7(String lock7) {
        this.lock7 = lock7;
    }

    public String getLock8() {
        return lock8;
    }

    public void setLock8(String lock8) {
        this.lock8 = lock8;
    }

    public String getLock9() {
        return lock9;
    }

    public void setLock9(String lock9) {
        this.lock9 = lock9;
    }

    public String getLock10() {
        return lock10;
    }

    public void setLock10(String lock10) {
        this.lock10 = lock10;
    }

    public String getLock11() {
        return lock11;
    }

    public void setLock11(String lock11) {
        this.lock11 = lock11;
    }

    public String getLock12() {
        return lock12;
    }

    public void setLock12(String lock12) {
        this.lock12 = lock12;
    }

    public String getLock13() {
        return lock13;
    }

    public void setLock13(String lock13) {
        this.lock13 = lock13;
    }

    public String getLock14() {
        return lock14;
    }

    public void setLock14(String lock14) {
        this.lock14 = lock14;
    }

    public String getLock15() {
        return lock15;
    }

    public void setLock15(String lock15) {
        this.lock15 = lock15;
    }

    public String getLock16() {
        return lock16;
    }

    public void setLock16(String lock16) {
        this.lock16 = lock16;
    }

    public String getLock17() {
        return lock17;
    }

    public void setLock17(String lock17) {
        this.lock17 = lock17;
    }

    public String getLock18() {
        return lock18;
    }

    public void setLock18(String lock18) {
        this.lock18 = lock18;
    }

    public String getLock19() {
        return lock19;
    }

    public void setLock19(String lock19) {
        this.lock19 = lock19;
    }

    public String getLock20() {
        return lock20;
    }

    public void setLock20(String lock20) {
        this.lock20 = lock20;
    }

    public String getLock21() {
        return lock21;
    }

    public void setLock21(String lock21) {
        this.lock21 = lock21;
    }

    public String getLock22() {
        return lock22;
    }

    public void setLock22(String lock22) {
        this.lock22 = lock22;
    }

    public String getLock23() {
        return lock23;
    }

    public void setLock23(String lock23) {
        this.lock23 = lock23;
    }

    public String getLock24() {
        return lock24;
    }

    public void setLock24(String lock24) {
        this.lock24 = lock24;
    }

    public String getLock25() {
        return lock25;
    }

    public void setLock25(String lock25) {
        this.lock25 = lock25;
    }

    public String getLock26() {
        return lock26;
    }

    public void setLock26(String lock26) {
        this.lock26 = lock26;
    }

    public String getLock27() {
        return lock27;
    }

    public void setLock27(String lock27) {
        this.lock27 = lock27;
    }

    public String getLock28() {
        return lock28;
    }

    public void setLock28(String lock28) {
        this.lock28 = lock28;
    }

    public String getLock29() {
        return lock29;
    }

    public void setLock29(String lock29) {
        this.lock29 = lock29;
    }

    public String getLock30() {
        return lock30;
    }

    public void setLock30(String lock30) {
        this.lock30 = lock30;
    }

    public String getLock31() {
        return lock31;
    }

    public void setLock31(String lock31) {
        this.lock31 = lock31;
    }

    public String[] getLocks() {
        return locks;
    }

    public void setLocks(String[] locks) {
        this.locks = locks;
    }

    public String getLock1() {
        return lock1;
    }

    public void setLock1(String lock1) {
        this.lock1 = lock1;
    }

    public Integer getKuang() {
        return kuang;
    }

    public void setKuang(Integer kuang) {
        this.kuang = kuang;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLock32() {
        return lock32;
    }

    public void setLock32(String lock32) {
        this.lock32 = lock32;
    }

    public String getLock33() {
        return lock33;
    }

    public void setLock33(String lock33) {
        this.lock33 = lock33;
    }

    public String getLock34() {
        return lock34;
    }

    public void setLock34(String lock34) {
        this.lock34 = lock34;
    }

    public String getLock35() {
        return lock35;
    }

    public void setLock35(String lock35) {
        this.lock35 = lock35;
    }

    public String getLock36() {
        return lock36;
    }

    public void setLock36(String lock36) {
        this.lock36 = lock36;
    }
}