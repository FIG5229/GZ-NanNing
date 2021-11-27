/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCommentatorsDeputy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 网评员管理-副表DAO接口
 * @author alan.wu
 * @version 2020-06-22
 */
@MyBatisDao
public interface AffairCommentatorsDeputyDao extends CrudDao<AffairCommentatorsDeputy> {

    List<AffairCommentatorsDeputy> selectDeputyById(String idNumber);

    List<String> selectPlatFormById(String idNumber);

    List<String> selectAccountById(String idNumber);

    List<AffairCommentatorsDeputy> findByParentId(@Param(value = "id")String id);
}