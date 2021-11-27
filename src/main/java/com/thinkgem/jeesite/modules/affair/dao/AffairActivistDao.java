/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivist;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 入党积极分子DAO接口
 * @author eav.liu
 * @version 2019-10-31
 */
@MyBatisDao
public interface AffairActivistDao extends CrudDao<AffairActivist> {

    List<String> findListByIdNo(@Param("idNo") String idNo);

    void deleteByIdNo(@Param("idNo") String idNo);

    List<AffairActivist> finActivistList(AffairActivist affairActivist);
    //积极分子提交思想汇报预警，查询所有积极分子
    List<AffairActivist> findAllActivistList();
}