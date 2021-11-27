/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHome;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 民警小家建设DAO接口
 * @author cecil.li
 * @version 2019-11-28
 */
@MyBatisDao
public interface AffairPoliceHomeDao extends CrudDao<AffairPoliceHome> {

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("mStartDate") Date mStartDate, @Param("mEndDate") Date mEndDate);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year, @Param("mStartDate") Date mStartDate, @Param("mEndDate") Date mEndDate);

    AffairPoliceHome getOne(AffairPoliceHome policeHome);


/*    @Transactional(readOnly = false)
    void deleteInDetail(AffairPoliceHome affairPoliceHome) ;*/


}