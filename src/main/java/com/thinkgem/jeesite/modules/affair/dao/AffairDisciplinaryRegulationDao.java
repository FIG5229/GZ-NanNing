/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryRegulation;
import org.apache.ibatis.annotations.Param;

/**
 * 纪律规定DAO接口
 * @author cecil.li
 * @version 2019-11-19
 */
@MyBatisDao
public interface AffairDisciplinaryRegulationDao extends CrudDao<AffairDisciplinaryRegulation> {
	public int updateOrderId(@Param(value = "id") String id, @Param(value = "unitId") String unitId);
	public int reUpdateOrderId(@Param(value = "id")String id, @Param(value = "unitId") String unitId);
	public String isExist(@Param(value = "unitId") String unitId, @Param(value = "disId") String disId);
}