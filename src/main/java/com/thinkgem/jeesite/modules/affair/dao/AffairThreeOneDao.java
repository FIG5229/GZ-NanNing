/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairThreeOne;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 三会一课DAO接口
 * @author cecil.li
 * @version 2019-11-06
 */
@MyBatisDao
public interface AffairThreeOneDao extends CrudDao<AffairThreeOne> {

    List<String> selectAllYear();

    List<String> selectAllName();

    String selectName(@Param(value = "groupId")String groupId);

    Integer selectTime(@Param(value = "unitId")String unitId, @Param(value = "year")String year, @Param(value = "tzbzwh")String tzbzwh);

    Integer selectNum(@Param(value = "id") String id, @Param(value = "niandu")String niandu,@Param(value = "time")String time);

    List<String> selectallMonth();

    Integer selectTydhNum(@Param(value = "yearL")String yearL,@Param(value = "yearT")String yearT,@Param(value = "twId") String twId,@Param(value = "type")String type);

    Integer selectNumber(@Param(value = "yearL")String yearL,@Param("yearT")String yearT,@Param(value = "twName") String twName, @Param(value = "tnzzshh")String tnzzshh);

    Integer selectNu(@Param("unitId") String unitId,@Param("yearL") String yearL,@Param("yearT")String yearT);

    Integer selectNuJ(@Param("unitId") String unitId,@Param("yearL") String yearL,@Param("yearT")String yearT);

    Integer selectWyhNu(@Param("unitId")String unitId,@Param("yearL") String yearL,@Param("yearT")String yearT);

    Integer selectDkNu(@Param("unitId")String unitId,@Param("yearL") String yearL,@Param("yearT")String yearT);

    Integer selectDkNuY(@Param("unitId")String unitId,@Param("yearL") String yearL,@Param("yearT")String yearT);

    Integer selectDzbdk(@Param("unitId")String unitId,@Param("yearL") String yearL,@Param("yearT")String yearT);

    Integer selectDzbdkYear(@Param("unitId")String unitId,@Param("yearL") String yearL,@Param("yearT")String yearT);

    Integer selectdxzh(@Param("groupName")String groupName,@Param("yearL") String yearL,@Param("yearT")String yearT);
}