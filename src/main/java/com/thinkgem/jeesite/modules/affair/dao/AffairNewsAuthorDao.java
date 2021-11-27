/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairNewsAuthor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 刊搞搞件子表作者DAO接口
 * @author daniel.liu
 * @version 2020-09-27
 */
@MyBatisDao
public interface AffairNewsAuthorDao extends CrudDao<AffairNewsAuthor> {


    @Override
    void deleteByIds(@Param("ids") List<String> ids);
}