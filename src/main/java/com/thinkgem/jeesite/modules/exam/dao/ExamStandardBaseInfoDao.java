/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;

import java.util.List;
import java.util.Map;

/**
 * 考评标准管理DAO接口
 * @author bradley.zhao
 * @version 2019-12-12
 */
@MyBatisDao
public interface ExamStandardBaseInfoDao extends CrudDao<ExamStandardBaseInfo> {
    List<Map<String, String>> findTabList(List<String> list);
    List<ExamStandardBaseInfo> findStandardList();
    List<ExamStandardBaseInfo> findStandardListFilter(ExamStandardBaseInfo examStandardBaseInfo);

    String findIdByName(String name);

    List<ExamStandardBaseInfo> findPublicInList(ExamStandardBaseInfo examStandardBaseInfo);
}