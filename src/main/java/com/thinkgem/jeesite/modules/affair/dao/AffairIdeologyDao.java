/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairIdeology;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 意识形态DAO接口
 * @author daniel.liu
 * @version 2020-06-22
 */
@MyBatisDao
public interface AffairIdeologyDao extends CrudDao<AffairIdeology> {

    //绩效自动考评
    int findCountByYear(@Param(value = "year")String year, @Param(value = "unit")String unit,@Param(value = "unitId")String unitId);

    Integer selectNumber(@Param(value = "yearL")String yearL,@Param(value = "yearT")String yearT, @Param(value = "unitId")String unitId);

    List<AffairIdeology> juList(AffairIdeology affairIdeology);

    List<AffairIdeology> chuList(AffairIdeology affairIdeology);
}