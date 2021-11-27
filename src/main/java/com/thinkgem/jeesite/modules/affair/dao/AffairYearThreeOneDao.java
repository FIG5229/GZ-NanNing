/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairYearThreeOne;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * &ldquo;三会一课&rdquo;录入DAO接口
 * @author eav.liu
 * @version 2019-11-08
 */
@MyBatisDao
public interface AffairYearThreeOneDao extends CrudDao<AffairYearThreeOne> {

    void shenHeSave(@Param(value = "affairYearThreeOne") AffairYearThreeOne affairYearThreeOne);

    List<String> selectAllYear();

    Integer selectNum(@Param(value = "year")String year,@Param(value = "time")String time,@Param(value = "id") String id);

    List<String> selectAllUnitId();

    List<String> selectType(@Param(value = "id")String id, @Param(value = "year")String year);

    Integer selectTime(@Param(value = "id")String id, @Param(value = "year")String year, @Param(value = "type")String type);

    String selectName(@Param(value = "unitId")String unitId);

    Integer selectNumYear(@Param(value = "year")String year, @Param(value = "id")String id);

    Integer selectNu(@Param(value = "unitId")String unitId,@Param(value = "checkTime") String checkTime);

    List<AffairYearThreeOne> selectdzzzyThreeOne(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("partyOrganization") String partyOrganization, @Param("type") String type);

    List<AffairYearThreeOne> selectdzbThreeOne(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("partyOrganization") String partyOrganization);

    Integer selectHuiyiNumber(@Param("unitId") String unitId,@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("type")String type);
}