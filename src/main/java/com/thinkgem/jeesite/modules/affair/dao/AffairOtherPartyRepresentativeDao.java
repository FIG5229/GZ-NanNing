/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOtherPartyRepresentative;

import java.util.List;

/**
 * 其他党代表会DAO接口
 * @author daniel.liu
 * @version 2020-06-30
 */
@MyBatisDao
public interface AffairOtherPartyRepresentativeDao extends CrudDao<AffairOtherPartyRepresentative> {

    List<AffairOtherPartyRepresentative> findRepresentativeList(AffairOtherPartyRepresentative representative);
}