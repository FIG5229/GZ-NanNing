/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairZxInfoChild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 助学子女信息表DAO接口
 * @author daniel.liu
 * @version 2020-09-07
 */
@MyBatisDao
public interface AffairZxInfoChildDao extends CrudDao<AffairZxInfoChild> {
    public void deleteByMainId(@Param(value = "zxInfoId") String zxInfoId);
    public void deleteByMainIds(@Param(value = "zxInfoIds") List<String> zxInfoIds);
    public List<AffairZxInfoChild> findSomeByZxInfoId(@Param(value = "zxInfoId") String zxInfoId);
}