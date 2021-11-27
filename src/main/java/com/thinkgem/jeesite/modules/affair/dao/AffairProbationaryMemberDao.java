/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairProbationaryMember;
import org.apache.ibatis.annotations.Param;

/**
 * 预备党员DAO接口
 * @author cecil.li
 * @version 2020-12-21
 */
@MyBatisDao
public interface AffairProbationaryMemberDao extends CrudDao<AffairProbationaryMember> {
    void shenHe(@Param(value = "affairProbationaryMember") AffairProbationaryMember affairProbationaryMember);
}