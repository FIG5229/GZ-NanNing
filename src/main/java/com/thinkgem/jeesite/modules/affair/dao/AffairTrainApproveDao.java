/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairEducationTrain;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainApprove;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 培训计划审批DAO接口
 * @author jack.xu
 * @version 2020-07-28
 */
@MyBatisDao
public interface AffairTrainApproveDao extends CrudDao<AffairTrainApprove> {
    List<AffairTrainApprove> findByIds(@Param(value = "ids") List<String> ids);
}