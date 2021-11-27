/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRetreat;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRetreatSum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 离退信息集DAO接口
 * @author cecil.li
 * @version 2019-11-09
 */
@MyBatisDao
public interface PersonnelRetreatDao extends CrudDao<PersonnelRetreat> {
    void shenHe(@Param(value ="personnelRetreat") PersonnelRetreat personnelRetreat);

    List<PersonnelRetreat> findByIds(@Param(value = "ids") List<String> ids);


    PersonnelRetreatSum findByNowNameUnitId(PersonnelRetreat personnelRetreat);


    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);

}