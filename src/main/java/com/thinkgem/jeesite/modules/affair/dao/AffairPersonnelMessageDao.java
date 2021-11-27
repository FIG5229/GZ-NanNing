package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonnelMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface AffairPersonnelMessageDao extends CrudDao<AffairPersonnelMessage> {
    List<AffairPersonnelMessage> findPageTwo(@Param(value = "classManageId") String classManageId);
}
