/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalPerson;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 典型人物DAO接口
 * @author daniel.liu
 * @version 2020-06-16
 */
@MyBatisDao
public interface AffairTypicalPersonDao extends CrudDao<AffairTypicalPerson> {

    List<Map<String,String>> countTypicalPerson(@Param("officeId")String officeId,@Param("year") Integer year, @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairTypicalPerson> findTypicalList(AffairTypicalPerson affairTypicalPerson);
}