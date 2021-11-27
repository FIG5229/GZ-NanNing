/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryRegulationReceive;

/**
 * 纪律规定接收单位表DAO接口
 * @author eav.liu
 * @version 2020-04-09
 */
@MyBatisDao
public interface AffairDisciplinaryRegulationReceiveDao extends CrudDao<AffairDisciplinaryRegulationReceive> {

    void deleteByDisRegId(String disRegId);

}