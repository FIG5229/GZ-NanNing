/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairNewsName;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 刊搞搞件子所属人DAO接口
 * @author daniel.liu
 * @version 2020-09-27
 */
@MyBatisDao
public interface AffairNewsNameDao extends CrudDao<AffairNewsName> {



    @Override
    void deleteByIds(@Param("ids") List<String> ids);


    List<String> selectAllNewsId(@Param("idNumber") String idNumber);
}