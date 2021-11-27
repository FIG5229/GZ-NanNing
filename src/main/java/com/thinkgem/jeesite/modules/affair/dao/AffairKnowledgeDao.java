/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairKnowledge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 党章党规及党建知识DAO接口
 * @author eav.liu
 * @version 2019-11-04
 */
@MyBatisDao
public interface AffairKnowledgeDao extends CrudDao<AffairKnowledge> {

    List<AffairKnowledge> findByIds(@Param(value = "ids") List<String> ids);
}