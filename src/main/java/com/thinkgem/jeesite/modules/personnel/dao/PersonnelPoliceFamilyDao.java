/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamily;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 民警家庭DAO接口
 * @author daniel.liu
 * @version 2020-07-09
 */
@MyBatisDao
public interface PersonnelPoliceFamilyDao extends CrudDao<PersonnelPoliceFamily> {

    PersonnelPoliceFamily findUserMessage(String loginName);

}