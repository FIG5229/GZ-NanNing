/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairRqlzIntegrity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任前廉政鉴定DAO接口
 * @author cecil.li
 * @version 2019-11-08
 */
@MyBatisDao
public interface AffairRqlzIntegrityDao extends CrudDao<AffairRqlzIntegrity> {

    List<Map<String, Object>> findInfoByJdType(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairRqlzIntegrity> findDetailInfoByType(AffairRqlzIntegrity affairRqlzIntegrity);
    List<Map<String, Object>> findPieInfoByJdType(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	
}