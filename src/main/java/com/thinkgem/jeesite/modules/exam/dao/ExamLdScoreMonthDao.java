/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScoreMonth;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 月度领导考核评分DAO接口
 * @author cecil.li
 * @version 2020-02-12
 */
@MyBatisDao
public interface ExamLdScoreMonthDao extends CrudDao<ExamLdScoreMonth> {
   public List<Map<String,String>> findYearMonthScores(Map<String,String> param);
   //普通民警
   List<Map<String, Object>> findInfoByUnitId(@Param("id") String id,@Param("month") String month);

   List<Map<String, Object>> findInfoByUnitIds(@Param("ids") List<String> ids,@Param("month") String month);

    List<ExamLdScoreMonth> findListInfo(ExamLdScoreMonth examLdScoreMonth);
    //统计分析-人员-月度
    List<ExamLdScoreMonth> findPersonnelMonthExamList(ExamLdScoreMonth examLdScoreMonth);
    //统计分析-人员-中基层-局机关及直属支队领导干部考评情况-月度
    List<ExamLdScoreMonth> findPersonnelZJCJJGMonthExamList(ExamLdScoreMonth examLdScoreMonth);
    //统计分析-人员-中基层-处机关及直属支队领导干部考评情况-月度
    List<ExamLdScoreMonth> findPersonnelZJCCJGMonthExamList(ExamLdScoreMonth examLdScoreMonth);
    //统计分析-人员-中基层-处队所领导干部考评情况-月度
    List<ExamLdScoreMonth> findPersonnelZJCDSMonthExamList(ExamLdScoreMonth examLdScoreMonth);

    List<ExamLdScoreMonth> findMonthList(ExamLdScoreMonth examLdScoreMonth);

    void deleteByWorkflowId(String workflowId);

//   List<String> findAllChildIdById(String id);

//    Page<ExamLdScoreMonth> findPageInfo(Page<ExamLdScoreMonth> page, ExamLdScoreMonth examLdScoreMonth);

}