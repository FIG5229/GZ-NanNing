/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairJobTraining;
import org.apache.ibatis.annotations.Param;

/**
 * 岗位练兵功能DAO接口
 * @author tom.fu
 * @version 2020-08-03
 */
@MyBatisDao
public interface AffairJobTrainingDao extends CrudDao<AffairJobTraining> {

    String findofficeId(@Param("organization") String organization);
}