/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程研发DAO接口
 * @author alan.wu
 * @version 2020-08-06
 */
@MyBatisDao
public interface AffairCourseClassDao extends CrudDao<AffairCourseClass> {

    AffairCourseClass selectBean(@Param("id") String id);

    List<AffairCourseClass> selectByClassId(@Param("classId") String classId);

    List<AffairCourseClass> selectByClassDeputyId(@Param("classId") String ci);

    List<AffairCourseClass> selectBeanById(@Param("id")String id);
}