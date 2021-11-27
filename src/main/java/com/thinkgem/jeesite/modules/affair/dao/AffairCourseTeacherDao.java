/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 辅导教官DAO接口
 * @author alan.wu
 * @version 2020-08-05
 */
@MyBatisDao
public interface AffairCourseTeacherDao extends CrudDao<AffairCourseTeacher> {

    List<AffairCourseTeacher> selectBean(@Param("id") String id);
}