/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.PersonnelGoAbroad;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 领导干部出国管理表DAO接口
 * @author mason.xv
 * @version 2019-11-06
 */
@MyBatisDao
public interface PersonnelGoAbroadDao extends CrudDao<PersonnelGoAbroad> {

    List<PersonnelGoAbroad> getTodayList(String returnDate);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);
}