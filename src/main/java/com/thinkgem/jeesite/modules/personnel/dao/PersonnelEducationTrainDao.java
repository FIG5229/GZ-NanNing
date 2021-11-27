/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelEducationTrain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教育培训（进修）信息管理DAO接口
 * @author cecil.li
 * @version 2019-11-09
 */
@MyBatisDao
public interface PersonnelEducationTrainDao extends CrudDao<PersonnelEducationTrain> {
    void deleteByIdNumber(String idNumber);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);

}