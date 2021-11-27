/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnDaily;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日常学习DAO接口
 * @author Alan
 * @version 2020-06-11
 */
@MyBatisDao
public interface AffairLearnDailyDao extends CrudDao<AffairLearnDaily> {

    List<String> selectAllYear();

    List<String> selectAllMonth();

    Integer selectNum(@Param("yearL") String yearL,@Param("yearT")String yearT,@Param("idNumber") String idNumber);

    String selectName(@Param("idNumber") String idNumber);

    Integer selectNumber(@Param("yearL")String yearL,@Param("yearT")String yearT, @Param("idNumber")String idNumber);

    List<String> selectAllName();
}