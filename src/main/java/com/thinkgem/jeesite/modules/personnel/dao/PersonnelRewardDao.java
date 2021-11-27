/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelReward;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 奖励信息集DAO接口
 * @author cecil.li
 * @version 2019-11-09
 */
@MyBatisDao
public interface PersonnelRewardDao extends CrudDao<PersonnelReward> {

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);
}