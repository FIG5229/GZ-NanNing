/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoPeopleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 人员身份DAO接口
 * @author cecil.li
 * @version 2020-08-26
 */
@MyBatisDao
public interface ExamAutoPeopleInfoDao extends CrudDao<ExamAutoPeopleInfo> {

    List<String> selectAllLead();

    List<String> selectAllLeadIdNumber();

    List<String> selectAllPoliceIdNumber();

    String selectSJId(@Param("name") String name);

    String selectNameById(@Param("idNumber") String idNumber);
}