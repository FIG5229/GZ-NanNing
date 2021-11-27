/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTalkHeart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 谈心DAO接口
 * @author daniel.liu
 * @version 2020-06-11
 */
@MyBatisDao
public interface AffairTalkHeartDao extends CrudDao<AffairTalkHeart> {

    List<String> selectAllYear();

    List<String> selectAllMonth();

    Integer selectNumber(@Param("time") String time,@Param("idNumber") String idNumber);

    List<String> selectAllName();
}