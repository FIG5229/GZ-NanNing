/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendance;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceFlag;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考勤记录DAO接口
 * @author mason.xv
 * @version 2020-01-19
 */
@MyBatisDao
public interface AffairAttendanceDao extends CrudDao<AffairAttendance> {

    String getId(@Param(value = "unitId") String unitId, @Param(value = "year") Integer year, @Param(value = "mouth") Integer mouth);

    //根据单位id查找人的信息
    List<PersonnelBase> findPerson(String unitId);
    //查找所有的节假日
    List<AffairAttendanceFlag> findAll(AffairAttendanceFlag affairAttendanceFlag);
    //查找所有的年份
    List<String> selectAllYear();
    //查找所有的月份
    List<String> selectAllMonth();
    //查询主键id
    String selectId(@Param(value = "year")Integer year, @Param(value = "month")Integer month,@Param(value = "idNumber") String idNumber);
    //查询单位
    String selectUnitById(@Param(value = "idNumber")String idNumber);
    //查询单位id
    String selectUnitIdById(@Param(value = "idNumber")String idNumber);
    //查询所有的关联id
    List<String>  selectAllId(@Param(value = "idNumber")String year, @Param(value = "idNumber")String idNumber);

    //单独添加一条
    int addOne(AffairAttendance affairAttendance);

    AffairAttendance findInfoByUnitIdYM(@Param("unitId") String unitId, @Param("year") int year, @Param("month") int month);
}