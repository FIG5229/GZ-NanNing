/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairApplyPersonnel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报名人员DAO接口
 * @author alan.wu
 * @version 2020-07-10
 */
@MyBatisDao
public interface AffairApplyPersonnelDao extends CrudDao<AffairApplyPersonnel> {

    List<AffairApplyPersonnel> selectAllPersonnelByClassId(@Param("classId") String classId);


    List<AffairApplyPersonnel> findLike(@Param("classId")String classId, @Param("name")String name, @Param("applyState")String applyState);
}