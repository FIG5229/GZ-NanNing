/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairQj;
import com.thinkgem.jeesite.modules.affair.entity.AffairQjSum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 请假信息DAO接口
 * @author mason.xv
 * @version 2019-11-27
 */
@MyBatisDao
public interface AffairQjDao extends CrudDao<AffairQj> {

    void leaderShenHe(@Param(value ="affairQj") AffairQj affairQj);

    void depShenHe(@Param(value ="affairQj") AffairQj affairQj);

    void hrShenHe(@Param(value ="affairQj") AffairQj affairQj);

    AffairQjSum findByNowNameUnitId(AffairQj affairQj);

    //请假汇总
   List<AffairQj> countList(AffairQj affairQj);
}