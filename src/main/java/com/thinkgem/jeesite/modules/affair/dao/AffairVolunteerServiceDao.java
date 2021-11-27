/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairVolunteerService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 志愿服务活动DAO接口
 * @author eav.liu
 * @version 2019-11-07
 */
@MyBatisDao
public interface AffairVolunteerServiceDao extends CrudDao<AffairVolunteerService> {

    List<String> selectAllYear();

    List<String> selectYearById(@Param("idN") String idN);

    String selectNameById(@Param("idN") String idN);

    AffairVolunteerService selectBeanById(@Param("idN")String idN, @Param("year")String year);

    Integer selectNumByName(@Param("partyId") String partyId,@Param("yearL") String yearL,@Param("yearT") String yearT);

    int selectNumberById(@Param("yearL") String yearL,@Param("yearT") String yearT, @Param("partyOrganizationId")String partyOrganizationId);
}