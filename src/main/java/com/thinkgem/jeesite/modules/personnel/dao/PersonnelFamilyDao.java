/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelFamily;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 家庭成员及社会关系信息集DAO接口
 * @author cecil.li
 * @version 2019-11-09
 */
@MyBatisDao
public interface PersonnelFamilyDao extends CrudDao<PersonnelFamily> {

    List<PersonnelFamily> getFamilyByIdNumber(PersonnelFamily param);

    void updateDelFlag(PersonnelFamily personnelFamily);

    List<PersonnelFamily> findListByIdNumber(String idNumber);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);
}