/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.org.entity.OrgCommunication;

/**
 * 单位通讯信息DAO接口
 * @author eav.liu
 * @version 2019-11-23
 */
@MyBatisDao
public interface OrgCommunicationDao extends CrudDao<OrgCommunication> {

    void deleteByOrgId(String orgId);
}