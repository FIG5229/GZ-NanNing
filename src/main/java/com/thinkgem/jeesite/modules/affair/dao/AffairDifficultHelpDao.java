/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDifficultHelp;
import com.thinkgem.jeesite.modules.affair.entity.AffairTnActivityRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 困难帮扶申报DAO接口
 * @author daniel.liu
 * @version 2020-06-03
 */
@MyBatisDao
public interface AffairDifficultHelpDao extends CrudDao<AffairDifficultHelp> {

    List<AffairDifficultHelp> findByIds(@Param(value = "ids") List<String> ids);
	
}