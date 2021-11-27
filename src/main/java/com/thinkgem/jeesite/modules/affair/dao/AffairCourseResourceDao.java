/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程资源DAO接口
 * @author alan.wu
 * @version 2020-07-31
 */
@MyBatisDao
public interface AffairCourseResourceDao extends CrudDao<AffairCourseResource> {

    String findType(@Param("id") String id);

    String findClassId(@Param("id") String id);

}