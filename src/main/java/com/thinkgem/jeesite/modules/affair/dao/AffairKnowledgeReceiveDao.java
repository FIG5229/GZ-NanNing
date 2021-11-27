/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairKnowledgeReceive;
import org.apache.ibatis.annotations.Param;

/**
 * 党规党章及党建知识关联DAO接口
 * @author eav.liu
 * @version 2019-11-04
 */
@MyBatisDao
public interface AffairKnowledgeReceiveDao extends CrudDao<AffairKnowledgeReceive> {

    void deleteByNoticeId(@Param(value = "noticeId") String noticeId);
}