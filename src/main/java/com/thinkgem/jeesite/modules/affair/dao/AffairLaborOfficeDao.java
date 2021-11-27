/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborOffice;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 劳资组织树DAO接口
 * @author cecil.li
 * @version 2020-11-08
 */
@MyBatisDao
public interface AffairLaborOfficeDao extends CrudDao<AffairLaborOffice> {
    List<AffairLaborOffice> findByParentIdsLike(AffairLaborOffice affairLaborOffice);
    String findNameById(@Param("id") String id);
    List<String> findListByName(@Param(value = "name") String name);
    List<AffairLaborOffice> findChildById(@Param(value="id") String id);
}