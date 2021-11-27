/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairYjGoOutReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 民警因私外出报备DAO接口
 * @author kevin.jia
 * @version 2020-11-10
 */
@MyBatisDao
public interface AffairYjGoOutReportDao extends CrudDao<AffairYjGoOutReport> {

    List<AffairYjGoOutReport> findByIds(@Param("idsArray") List<String> idsArray);

    void plshByIds(@Param("ids") List<String> ids);

    void plshthByIds(@Param("ids") List<String> ids);

    List<AffairYjGoOutReport> selectAllBeanTjfx();

    List<AffairYjGoOutReport> selectBeanDetails(AffairYjGoOutReport affairYjGoOutReport);

    void revocation(AffairYjGoOutReport affairYjGoOutReport);
}