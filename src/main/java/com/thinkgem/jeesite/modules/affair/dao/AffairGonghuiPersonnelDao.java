/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGonghuiPersonnel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工会名册DAO接口
 * @author mason.xv
 * @version 2019-12-30
 */
@MyBatisDao
public interface AffairGonghuiPersonnelDao extends CrudDao<AffairGonghuiPersonnel> {

    List<AffairGonghuiPersonnel> findByIds(@Param(value = "ids") List<String> ids);

}