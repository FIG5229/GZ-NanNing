/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;

import java.util.List;

/**
 * 班子成员DAO接口
 * @author eav.liu
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairClassMemberDao extends CrudDao<AffairClassMember> {

    List<String> findListByPartyOrganization(String partyOrganization);

    void deleteByPartyOrganization(String partyOrganization);

    void shenHeSave(AffairClassMember affairClassMember);
}
