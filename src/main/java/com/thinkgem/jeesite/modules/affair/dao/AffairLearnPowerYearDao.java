/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import org.apache.ibatis.annotations.Param;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPowerYear;

import java.util.List;

/**
 * 学习强国--年度统计DAO接口
 * @author Alan
 * @version 2020-06-08
 */
@MyBatisDao
public interface AffairLearnPowerYearDao extends CrudDao<AffairLearnPowerYear> {

    void creat(@Param("affairLearnPowerYear") AffairLearnPowerYear affairLearnPowerYear);

    List<AffairLearnPowerYear> findListByYear(AffairLearnPowerYear affairLearnPowerYear);

    Double findlastYearIntegral(AffairLearnPowerYear learnPowerYear);
}