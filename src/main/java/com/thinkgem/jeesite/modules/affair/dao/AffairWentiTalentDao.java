/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWentiTalent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文体人才库DAO接口
 * @author cecil.li
 * @version 2019-11-29
 */
@MyBatisDao
public interface AffairWentiTalentDao extends CrudDao<AffairWentiTalent> {

	public AffairWentiTalent findInfoByIdNumber(@Param(value = "idNumber") String idNumber);

    List<AffairWentiTalent> selectSpeciality(@Param(value = "idNumber")String idNumber);
}