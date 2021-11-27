/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwoOne;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * &quot;两学一做&quot;专题学习DAO接口
 * @author mason.xv
 * @version 2019-11-02
 */
@MyBatisDao
public interface AffairTwoOneDao extends CrudDao<AffairTwoOne> {

	List<Map<String, Object>> findInfoByCreateOrgId(@Param(value = "id") String id, @Param("year") Integer year,@Param("month") String month);

	List<Map<String, Object>> findInfoByCreateOrgIds(@Param(value = "ids") List<String> ids,@Param("year")  Integer year, @Param("month")  String month);

    List<String> selectAllYear();

	List<String> selectAllMonth();

    Integer selectNum(@Param(value = "time") String time, @Param(value = "id") String id);

    Integer selectNumYear(@Param(value = "year")String year, @Param(value = "id")String id);
}