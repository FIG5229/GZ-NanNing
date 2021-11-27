/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheckChild;
import com.thinkgem.jeesite.modules.exam.entity.ExamOtherDataChild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 其他数据录入子表DAO接口
 *
 * @author eav.liu
 * @version 2020-03-11
 */
@MyBatisDao
public interface ExamOtherDataChildDao extends CrudDao<ExamOtherDataChild> {

    List<ExamOtherDataChild> findSomeByExamCheckId(String examCheckId);

    //考评档案根据年开始月、结束月查询该时间段检查扣分情况
    List<ExamOtherDataChild> findUnitListByYearMonth(@Param("yearNum") int yearNum, @Param("startMonthNum") int startMonthNum, @Param("endMonthNum") int endMonthNum, @Param("unitId") String unitId);

    List<ExamOtherDataChild> findExamCheckList(ExamOtherDataChild examOtherDataChild);
}