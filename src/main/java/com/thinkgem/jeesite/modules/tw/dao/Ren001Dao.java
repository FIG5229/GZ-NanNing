/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tw.entity.Ren001;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自动考评-精神病患者基本信息DAO接口
 * @author alan.wu
 * @version 2020-11-20
 */
@MyBatisDao
public interface Ren001Dao extends CrudDao<Ren001> {

    List<Ren001> selectAllPeople(@Param("code") String code,@Param("dj") String dj);
}