/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tw.entity.AcMobileData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自动考评-警情信息DAO接口
 * @author alan.wu
 * @version 2020-11-19
 */
@MyBatisDao
public interface AcMobileDataDao extends CrudDao<AcMobileData> {

    List<AcMobileData> selectAllExcption(@Param("unit")String unit, @Param("checkTime") String checkTime);

    Integer selectNum(@Param("idN") String idN,@Param("yearL") String yearL,@Param("yearT") String yearT);
}