/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityEnrollChildren;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工会活动报名人员子表DAO接口
 * @author kevin.jia
 * @version 2020-09-04
 */
@MyBatisDao
public interface AffairGhActivityEnrollChildrenDao extends CrudDao<AffairGhActivityEnrollChildren> {

    List<AffairGhActivityEnrollChildren> findByGhActivityEnrollId(String enrollId);

    void deleteByGhActivityEnrolId(String ghActivityEnrolId);

    void deleteByGhActivityEnrolIds(@Param("ghActivityEnrolIds") List<String> ghActivityEnrolIds);
}