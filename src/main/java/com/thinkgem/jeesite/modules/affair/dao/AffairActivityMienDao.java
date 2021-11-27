/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动风采DAO接口
 * @author kevin.jia
 * @version 2020-07-27
 */
@MyBatisDao
public interface AffairActivityMienDao extends CrudDao<AffairActivityMien> {

    List<AffairActivityMien> findByIds(@Param(value = "ids") List<String> ids);
}