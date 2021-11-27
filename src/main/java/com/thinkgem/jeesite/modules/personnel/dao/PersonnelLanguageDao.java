/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelLanguage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 语言能力信息集DAO接口
 * @author cecil.li
 * @version 2019-11-12
 */
@MyBatisDao
public interface PersonnelLanguageDao extends CrudDao<PersonnelLanguage> {

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);

}