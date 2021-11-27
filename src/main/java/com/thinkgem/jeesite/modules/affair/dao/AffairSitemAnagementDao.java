/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.entity.AffairSitemAnagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 场地管理DAO接口
 * @author tom.Fu
 * @version 2020-07-30
 */
@MyBatisDao
public interface AffairSitemAnagementDao extends CrudDao<AffairSitemAnagement> {

    /**
     * 根据id查询数据
     * @param id
     *
     * @return
     */
    public AffairClassManage findOne(String id);

}