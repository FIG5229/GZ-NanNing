/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainCombat;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实弹训练DAO接口
 * @author jack.xu
 * @version 2020-07-15
 */
@MyBatisDao
public interface AffairTrainCombatDao extends CrudDao<AffairTrainCombat> {
	List<AffairTrainCombat> findByIds(@Param(value = "ids") List<String> ids);

    PersonnelBase findofficeId(@Param("idNumber") String idNumber);
}