/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程信息DAO接口
 *
 * @author jack.xu
 * @version 2020-07-23
 */
@MyBatisDao
public interface AffairClassInformationDao extends CrudDao<AffairClassInformation> {
    List<AffairClassInformation> findByIds(@Param(value = "ids") List<String> ids);

    List<AffairClassInformation> findPageThree(@Param(value = "classManageId") String classManageId);
}