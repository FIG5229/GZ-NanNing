/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 民警记实功能DAO接口
 * @author tom.fu
 * @version 2020-08-21
 */
@MyBatisDao
public interface AffairWorkRecordDao extends CrudDao<AffairWorkRecord> {

    List<AffairWorkRecord> findByIds(@Param("idList") List<String> idList);
}