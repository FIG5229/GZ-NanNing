/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDatas;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考评模板DAO接口
 * @author bradley.zhao
 * @version 2019-12-12
 */
@MyBatisDao
public interface ExamStandardTemplateDefineDao extends CrudDao<ExamStandardTemplateDefine> {
	public List<Map<String,String>> selectTemplateDatas(Map<String,String> param);
	/*一直没用 废弃*/
//	 List<Map<String,String>> findTemplateDatas(ExamWorkflowDatas examWorkflowDatas);
	 List<Map<String,String>> findTemplateDatasBeta(Map<String, String> param);
	/*按照跟新时间排序查询*/
	public List<ExamStandardTemplateDefine> getNew(Map<String,String> param);
	public void updateExamStardardId(@Param(value="id") String id, @Param(value = "examStardardId") String examStardardId);
	public void clearExamStardardId(@Param(value = "examStardardId") String examStardardId);

	List<Map<String, String>> findTemplateDatasByOption(@Param("optionId") String optionId, @Param("standardId") String standardId);
}