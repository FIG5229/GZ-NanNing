/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSendTeacher;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainOutsource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 送教上门DAO接口
 * @author jack.xu
 * @version 2020-07-20
 */
@MyBatisDao
public interface AffairSendTeacherDao extends CrudDao<AffairSendTeacher> {
    List<AffairSendTeacher> findByIds(@Param(value = "ids") List<String> ids);

    String findofficeId(@Param("unit") String unit);
}