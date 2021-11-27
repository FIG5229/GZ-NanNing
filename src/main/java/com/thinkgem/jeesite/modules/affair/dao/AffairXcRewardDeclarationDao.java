/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairXcRewardDeclaration;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 单位集体奖励申报DAO接口
 * @author cecil.li
 * @version 2019-12-21
 */
@MyBatisDao
public interface AffairXcRewardDeclarationDao extends CrudDao<AffairXcRewardDeclaration> {

    List<AffairXcRewardDeclaration> findByIds(@Param(value = "ids") List<String> ids);
//    void shenHe(@Param(value = "affairXcRewardDeclaration") AffairXcRewardDeclaration affairXcRewardDeclaration);
}