/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyTraining;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 党内培训DAO接口
 * @author freeman
 * @version 2020-06-02
 */
@MyBatisDao
public interface AffairPartyTrainingDao extends CrudDao<AffairPartyTraining> {

    List<AffairPartyTraining> findTreeData();

    List<AffairPartyTraining> findTreeData(AffairPartyTraining affairPartyTraining);

    List<AffairPartyTraining> findByPartyOrganization(String partyOrganization);

    void deleteByPartyOrganization(String partyOrganization);

    Map getOrgInfo(@Param(value = "id") String id);

}