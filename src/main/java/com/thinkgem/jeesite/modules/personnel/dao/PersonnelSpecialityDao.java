/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSkill;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSpeciality;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelTalents;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专长信息集DAO接口
 * @author cecil.li
 * @version 2019-11-11
 */
@MyBatisDao
public interface PersonnelSpecialityDao extends CrudDao<PersonnelSpeciality> {

    PersonnelSpeciality selectIdNumber(@Param("id") String id);

    PersonnelSpeciality selectBean(@Param("id") String id);

    void deleteByIdNumber(String idNumber);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);


}