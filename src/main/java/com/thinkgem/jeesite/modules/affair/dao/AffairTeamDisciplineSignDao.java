/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTeamDisciplineSign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 纪律作风教育整顿DAO接口
 * @author cecil.li
 * @version 2020-06-16
 */
@MyBatisDao
public interface AffairTeamDisciplineSignDao extends CrudDao<AffairTeamDisciplineSign> {
    List<AffairTeamDisciplineSign> findSign(@Param(value = "affairTeamDisciplineSign") AffairTeamDisciplineSign affairTeamDisciplineSign);

    List<AffairTeamDisciplineSign> findNotSign(@Param(value = "affairTeamDisciplineSign")AffairTeamDisciplineSign affairTeamDisciplineSign);

    void deleteByNoticeId(String noticeId);
}