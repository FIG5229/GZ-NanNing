/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTuixiu;

import java.util.List;
import java.util.Map;

/**
 * 退休DAO接口
 * @author cecil.li
 * @version 2020-07-02
 */
@MyBatisDao
public interface AffairTuixiuDao extends CrudDao<AffairTuixiu> {

    public List<Map<String,String>> tuiList();
    public int queDing(String id);
    public List<String> findAllId();
	
}