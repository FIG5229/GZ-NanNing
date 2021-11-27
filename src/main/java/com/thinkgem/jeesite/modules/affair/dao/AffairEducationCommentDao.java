/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairEducationComment;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 团员教育评议DAO接口
 * @author cecil.li
 * @version 2019-11-20
 */
@MyBatisDao
public interface AffairEducationCommentDao extends CrudDao<AffairEducationComment> {

    List<AffairEducationComment> findByIds(@Param(value = "ids") List<String> ids);

    List<Map<String, String>> countComment(@Param("year") Integer year, @Param("startDate")Date startDate, @Param("endDate")Date endDate,
                                           @Param("month")String month, @Param(value = "officeId") String officeId);

    List<AffairEducationComment> findEducationCommentPage(AffairEducationComment affairEducationComment);

    List<String> selectAllYear();

    String selectUnitNameById(@Param(value = "unit") String unit);

    List<AffairEducationComment> selectBeanById(@Param(value = "idN")String idN,@Param(value = "nowYearDate") Date nowYearDate,@Param(value = "lastYearDate") Date lastYearDate);
}