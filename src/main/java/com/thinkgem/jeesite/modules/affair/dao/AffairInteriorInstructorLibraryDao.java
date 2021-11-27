/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActive;
import com.thinkgem.jeesite.modules.affair.entity.AffairInteriorInstructorLibrary;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 内部教官DAO接口
 * @author alan.wu
 * @version 2020-07-23
 */
@MyBatisDao
public interface AffairInteriorInstructorLibraryDao extends CrudDao<AffairInteriorInstructorLibrary> {

    AffairInteriorInstructorLibrary selectBeanById(@Param("id") String id);
    // 教官库统计分析
    List<Map<String,String>> countInstructor();
    List<AffairInteriorInstructorLibrary> findJiaoGuanList(AffairInteriorInstructorLibrary affairInteriorInstructorLibrary);
}