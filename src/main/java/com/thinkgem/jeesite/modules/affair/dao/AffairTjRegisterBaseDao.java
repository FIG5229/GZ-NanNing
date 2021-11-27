/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegisterBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 团籍注册DAO接口
 * @author mason.xv
 * @version 2020-03-21
 */
@MyBatisDao
public interface AffairTjRegisterBaseDao extends CrudDao<AffairTjRegisterBase> {

    public AffairTjRegisterBase getGroup(String idNumber);

    AffairTjRegisterBase findInfoByIdNumber(@Param(value = "idNumber")String idNumber);

    List<String> findListByIdNo(String idNumber);

    void deleteById(String idNumber);
	
}