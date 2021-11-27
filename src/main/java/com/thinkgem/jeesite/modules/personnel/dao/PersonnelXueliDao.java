/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelXueli;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学历信息集DAO接口
 * @author cecil.li
 * @version 2019-11-12
 */
@MyBatisDao
public interface PersonnelXueliDao extends CrudDao<PersonnelXueli> {

    List<PersonnelXueli> findLastByIdNumber(PersonnelXueli personnelXueli);

    List<PersonnelXueli> findLastXueLiInfo(PersonnelXueli personnelXueli);

    List<Map<String, String>> countFullTimeEducation(@Param("id") String id,@Param("year") Integer year, @Param("startDate") Date startDate,
                                                     @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, String>> countMaxEducation(@Param("id") String id,@Param("year") Integer year, @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate, @Param("month") String month);

    List<PersonnelXueli> findFullTimeList(PersonnelXueli personnelXueli);

    List<PersonnelXueli> findMaxList(PersonnelXueli personnelXueli);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);

}