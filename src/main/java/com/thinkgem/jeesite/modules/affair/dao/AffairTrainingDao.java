/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTraining;
import org.apache.ibatis.annotations.Param;

/**
 * 练兵比武DAO接口
 * @author alan.wu
 * @version 2020-07-15
 */
@MyBatisDao
public interface AffairTrainingDao extends CrudDao<AffairTraining> {

    String findofficeId(@Param("unit") String unit);
}