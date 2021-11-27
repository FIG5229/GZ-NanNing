/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceChild;
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborSort;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.security.access.method.P;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 考勤记录子表DAO接口
 *
 * @author eav.liu
 * @version 2020-03-18
 */
@MyBatisDao
public interface AffairAttendanceChildDao extends CrudDao<AffairAttendanceChild> {

    void deleteByAttId(String attId);

    List<AffairAttendanceChild> findChildListByAttId(String id);

    List<PersonnelBase> findPerson(String unitId);
    //从人员排序获取人员基本信息
    List<AffairLaborSort> findPersonInfo(String unitId);

    //解锁
    int unlock(@Param(value = "unitId") String unitId, @Param("year")String year, @Param("month") String month, @Param("userOffice")String userOffice);

    //加锁
    int locked(@Param(value = "unitId") String unitId, @Param("year")String year, @Param("month") String month, @Param("userOffice")String userOffice);

    List<String> findLock(String id);

    //获得到所有缺勤的天数
    List<Map<String, String>> findSumLackDay(@Param("year") Integer year);

    List<Map<String, String>> findSumLackDayByUnitId(@Param("unitId") String unitId,@Param("year") Integer year);

    //查询所有的身份证号
    List<String> selectAllIdNumber();

    //根据id查询缺勤次数
    Integer selectDegree(@Param("keyId") String keyId);

    //查询考勤次数
    List<Map<String,Object>> selectNum(Map<String, Object> param);

    //根据身份证号查询姓名
    String selectNameById(@Param("idNumber") String idNumber);

    Date selectHappenTimeById(@Param("idNumber") String idNumber);

    Date selectTimeById(@Param("idNumber") String idNumber);

    //根据身份证号、单位id、年份、月份查询考评信息
    AffairAttendanceChild findPersonAttendanceByIdNumber(@Param("idNumber") String idNumber, @Param("unitId") String unitId, @Param("year") int year, @Param("month") int month);

    List<AffairAttendanceChild> findPersonById(@Param("idNumber") String idNumber, @Param("unitId") String unitId, @Param("year") int year, @Param("month") int month);

    List<AffairAttendanceChild> selectNumberUnit(@Param("keyId") String keyId,@Param("status")String status);

    List<AffairAttendanceChild> selectBean(@Param("idNumber") String idNumber,@Param("year") int year,@Param("month") int month,@Param("state") String state);

    List<AffairAttendanceChild> selectBeanLastForeDay(@Param("idNumber") String idNumber,@Param("year") int year,@Param("month") int month,@Param("state") String state);

    Integer selectBeanYear(@Param("idNumber")String idNumber, @Param("year")int year, @Param("state")  String state);

    Integer selectLaNumber(@Param("idNumber")String idNumber, @Param("year")int year, @Param("state")  String state);

    Integer selectBeanTnumber(@Param("idNumber")String idNumber, @Param("year")int year, @Param("state")  String state);

    List<AffairAttendanceChild> selectMjBeanYear(@Param("idNumber")String idNumber, @Param("year")int year, @Param("one")int one, @Param("elev")int elev, @Param("state")  String state);

    List<AffairAttendanceChild> selectMjLastBeanYear(@Param("idNumber")String idNumber, @Param("year")int year, @Param("one")int one, @Param("elev")int elev, @Param("state")  String state);

    List<AffairAttendanceChild> selectLastBean(@Param("idNumber") String idNumber,@Param("lastYear") int lastYear,@Param("state") String state);

    List<AffairAttendanceChild> selectThisYearBean(@Param("idNumber") String idNumber, @Param("y") int y, @Param("state") String state);

    List<AffairAttendanceChild> selectMjThisMonth(@Param("roid")String roid,@Param("year") int y, @Param("month")int m, @Param("state")String state);

    List<AffairAttendanceChild> selectMjLastMonth(@Param("roid")String roid,@Param("year") int y, @Param("month")int m, @Param("state")String state);

    List<AffairAttendanceChild> selectMjLastYear(@Param("roid")String policeRoleId,@Param("lastYear") int lastYear, @Param("state")String state);

    List<AffairAttendanceChild> selectMjThisYear(@Param("roid") String policeRoleId,@Param("year") int y,@Param("state") String state);

    List<AffairAttendanceChild> selectHaveBugsIdNumber(@Param("policeRoleId") String policeRoleId, @Param("lastYear")int lastYear, @Param("year")int year);

    List<AffairAttendanceChild> selectOTBeanTnumber(@Param("idNumber") String idNumber,@Param("year") int y2, @Param("state") String state);

    List<AffairAttendanceChild> selectZjcBean(@Param("idNumber")String idNumber,@Param("year") int year, @Param("month")int month, @Param("state")String state);
}
