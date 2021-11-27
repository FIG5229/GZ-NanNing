/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 年度领导考核评分DAO接口
 * @author cecil.li
 * @version 2020-02-11
 */
@MyBatisDao
public interface ExamLdScoreDao extends CrudDao<ExamLdScore> {

    void excellent(ExamLdScore examLdScore);
    void excellentByIds(@Param(value = "ids") List<String> ids);
    //统计分析-年度-人员
    List<ExamLdScore> findPersonnelYearExamList(ExamLdScore examLdScore);
    //统计分析-人员-中基层-局机关及直属支队领导干部考评情况-年度
    List<ExamLdScore> findPersonnelZJCJJGYearExamList(ExamLdScore examLdScore);
    //统计分析-人员-中基层-处队所领导干部考评情况-年度
    List<ExamLdScore> findPersonnelZJCDSYearExamList(ExamLdScore examLdScore);
    //统计分析-人员-中基层-处机关及直属支队领导干部考评情况-年度度
    List<ExamLdScore> findPersonnelZJCCJGYearExamList(ExamLdScore examLdScore);

    ExamLdScore findGradesByWorkflowIdIDnumber(@Param("idNumber") String idNumber, @Param("workflowId") String workflowId);

    void deleteByWorkflowId(String workflowId);
}