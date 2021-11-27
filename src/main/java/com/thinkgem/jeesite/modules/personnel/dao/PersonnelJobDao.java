/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelJob;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 职务层次信息集DAO接口
 * @author cecil.li
 * @version 2019-11-11
 */
@MyBatisDao
public interface PersonnelJobDao extends CrudDao<PersonnelJob> {
    /*
    * 根据身份证号查询最新职务层次,且状态为当前任职
    * */
    PersonnelJob findNewJobByIdNumber(String idNumber);
    //查询最新职务集合
    List<Map<String,Object>> getNewestJobList();

    List<Map<String, String>> countPersonnelJob(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate, @Param("month") String month);

    List<PersonnelJob> findChartsPersonnelJobList(PersonnelJob personnelJob);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);
}