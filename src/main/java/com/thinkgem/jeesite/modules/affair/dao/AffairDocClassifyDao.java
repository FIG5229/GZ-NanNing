/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文档分类DAO接口
 * @author kevin.jia
 * @version 2020-07-29
 */
@MyBatisDao
public interface AffairDocClassifyDao extends CrudDao<AffairDocClassify> {

    List<AffairDocClassify> findTreeData(AffairDocClassify affairDocClassify);

    AffairDocClassify getParentIdsById(@Param(value = "id") String id);

    String getByTempId(String tempId);
}