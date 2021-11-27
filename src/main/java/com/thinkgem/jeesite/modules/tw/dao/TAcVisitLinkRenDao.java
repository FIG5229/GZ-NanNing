/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tw.entity.TAcVisitLinkRen;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自动考评-回访记录和人员关联信息DAO接口
 * @author alan.wu
 * @version 2020-11-20
 */
@MyBatisDao
public interface TAcVisitLinkRenDao extends CrudDao<TAcVisitLinkRen> {

    List<String> selectAllRecode(@Param("id") String id);
}