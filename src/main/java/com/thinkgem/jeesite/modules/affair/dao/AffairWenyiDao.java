/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenyi;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文艺作品DAO接口
 * @author cecil.li
 * @version 2020-03-06
 */
@MyBatisDao
public interface AffairWenyiDao extends CrudDao<AffairWenyi> {

    List<AffairWenyi> findByIds(@Param(value = "ids") List<String> ids);

    /*局统计处*/
    List<Map<String, String>> countJuLiteraryWorks(@Param("officeId") String officeId, @Param("year")Integer year,
                                                 @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);
    /*处统计所*/
    List<Map<String, String>> countChuLiteraryWorks(@Param("officeId") String officeId, @Param("year")Integer year,
                                                 @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    List<AffairWenyi> findLiteraryWorkList(AffairWenyi affairWenyi);

}