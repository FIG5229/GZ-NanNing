/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoStandardRelevance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自动考评考评标准关联DAO接口
 * @author kevin.jia
 * @version 2021-01-20
 */
@MyBatisDao
public interface ExamAutoStandardRelevanceDao extends CrudDao<ExamAutoStandardRelevance> {

    ExamAutoStandardRelevance getExamAutoStandardRelecance(@Param("evalType") String evalType,@Param("period") String period, @Param("unitId")String unitId, @Param("item")String item);

    List<String> findAllItems();
}