/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivitiesSign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 廉政教育管理DAO接口
 * @author cecil.li
 * @version 2020-06-28
 */
@MyBatisDao
public interface AffairLzxxjyActivitiesSignDao extends CrudDao<AffairLzxxjyActivitiesSign> {

    List<AffairLzxxjyActivitiesSign> findSign(@Param(value = "affairLzxxjyActivitiesSign") AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign);

    List<AffairLzxxjyActivitiesSign> findNotSign(@Param(value = "affairLzxxjyActivitiesSign")AffairLzxxjyActivitiesSign affairLzxxjyActivitiesSign);

    void deleteByNoticeId(String noticeId);
}