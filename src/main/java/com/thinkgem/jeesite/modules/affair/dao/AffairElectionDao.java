/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairElection;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党组织换届选举DAO接口
 * @author eav.liu
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairElectionDao extends CrudDao<AffairElection> {

    void shenHe(@Param(value = "affairElection") AffairElection affairElection);

    List<AffairElection> findByIds(@Param(value = "ids") List<String> ids);

    List<Map<String, String>> countElection(@Param("year") Integer year, @Param("startDate") Date startDate,@Param("endDate") Date endDate,
                                            @Param("month") String month);

    List<AffairElection> findElectionDetailList(AffairElection affairElection);

    List<AffairElection> findAllInfo(@Param("year") String year, @Param("startYear") String startYear, @Param("endYear") String endYear);
    int isElection(@Param(value = "partyOrganizationName")String partyOrganizationName, @Param(value = "partyOrganizationId")String partyOrganizationId, @Param(value = "hjDate")Date hjDate, @Param(value = "year")String year);
    //获取单位id
    List<String> findUnitId(@Param(value = "partyOrganizationId")String partyOrganizationId);
    //获取被考评对象id
    String findUserId(@Param(value = "id")String id);

    //查询所有审核通过
    List<AffairElection> findAllSHTGList();
}