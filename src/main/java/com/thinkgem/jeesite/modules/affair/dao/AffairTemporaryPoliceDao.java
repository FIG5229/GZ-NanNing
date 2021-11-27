/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTemporaryPolice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 临时抽调民警管理DAO接口
 * @author mason.xv
 * @version 2019-11-06
 */
@MyBatisDao
public interface AffairTemporaryPoliceDao extends CrudDao<AffairTemporaryPolice> {
    List<AffairTemporaryPolice> findByIds(@Param(value = "ids") List<String> ids);

    public int returnUnit(@Param("id")String id, @Param("unitId")String unitId);

    void revocation(@Param("id")String  id, @Param("checkType") String checkType);
}