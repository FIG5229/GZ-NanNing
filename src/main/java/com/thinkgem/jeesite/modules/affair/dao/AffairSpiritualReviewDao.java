/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSpiritualReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 复查情况报告DAO接口
 * @author Alan.wu
 * @version 2020-06-15
 */
@MyBatisDao
public interface AffairSpiritualReviewDao extends CrudDao<AffairSpiritualReview> {

     List<AffairSpiritualReview> findByIds(@Param(value = "ids")List<String> ids);

     void upd(@Param(value = "id")String id, @Param(value = "state")String state, @Param(value = "oneCheckMan")String oneCheckMan, @Param(value = "oneCheckId")String oneCheckId);
}