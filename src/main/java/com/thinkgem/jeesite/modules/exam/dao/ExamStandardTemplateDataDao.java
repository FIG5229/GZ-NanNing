/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

/**
 * 模板项数据DAO接口
 * @author bradley.zhao
 * @version 2019-12-13
 */
@MyBatisDao
public interface ExamStandardTemplateDataDao extends CrudDao<ExamStandardTemplateData> {
	void deleteByExamStandardId(@Param(value = "examStandId") String examStandId);
	List<Map<String ,Object>> findWorkTypeByStandardId(String  str);
	List<Map<String,Object>> findExamItemNumList(@Param(value = "standardIds") String[] standardIds);

	List<Map<String,String>> findRowList(ExamStandardTemplateData standardTemplateData);

    void deleteLine(ExamStandardTemplateData examStandardTemplateData);

	Integer getMaxRowNum(String itemId);

	//根据考评人员信息表id及工作流程业务数据表rowId 查询考评标准模板数据
	ExamStandardTemplateData findInfoByStandardBaseIdRowId(@Param("id")String id, @Param("rowId")int rowId);

    String selectRowById(@Param("optionId")String optionId);

    List<ExamStandardTemplateData> findStandardOptionList(@Param("standard") String standard,@Param("rowNum") String rowNum);
}