/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWeightsMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 权重DAO接口
 * @author cecil.li
 * @version 2020-01-17
 */
@MyBatisDao
public interface ExamWeightsMainDao extends CrudDao<ExamWeightsMain> {
    public void deleteByMainId(String mainId);
    public void deleteByMainIds(@Param(value = "mainIds") List<String> mainIds);
    //获取所有工作项及权重-局考处
    List<Map<String, Object>> findWorkNameList();
    //根据exam_workflow_data表中填报人id Fill_Person_Id  查询 工作项
    List<Map<String, Object>> findWorkNameByFillPersonId(@Param("fillPersonId") String fillPersonId,@Param("workflowId") String workflowId,@Param("kpType") String kpType);
    //根据exam_workflow_data表中初审id exam_Person_Id  查询 工作项
    List<Map<String, Object>> findWorkNameByExamPersonId(@Param("examPersonId") String examPersonId,@Param("workflowId") String workflowId);
    //根据处id 查询相应处的权重-处考队所
    List<Map<String, Object>> getWorkNameListByChu(@Param("chuId") String chuId,@Param("unitId") String unitId);
    //重点工作总分
    Integer getZdWeight(@Param("unitId") String unitId,@Param("kpType") String kpType ,@Param("kpUnitId") String kpUnitId);
    //根据workNameType 0常规  1 重点   3  公共加扣分  判断获取重点还是常规
    Double getZdOrCgWeight(@Param("unitId") String unitId,@Param("kpType") String kpType ,@Param("kpUnitId") String kpUnitId,@Param("workNameType") String workNameType);
    //常规
    Integer getCgWeight(@Param("unitId") String unitId,@Param("kpType") String kpType,@Param("kpUnitId") String kpUnitId);

    Double getJKCBaseSumScore();

    Double getCKDSBaseSumScore(@Param("kpChuId") String kpChuId, @Param("kpUnitId")String kpUnitId);

    Double getJorCJGBaseSumScore(@Param("examType") String examType, @Param("kpChuId") String kpChuId, @Param("kpUnitId")String kpUnitId);
    //查询所有工作项及权重，根据考评类别
    List<Map<String, Object>> findWorkNameListByKPType(@Param("examType") String examType, @Param("kpChuId") String kpChuId, @Param("kpUnitId")String kpUnitId);
    //处考处机关权重，根据所属处，当前考评对象
    List<Map<String, Object>> findWorkNameByChu_FillPersonId(@Param("fillPersonId") String fillPersonId, @Param("workflowId") String workflowId, @Param("kpType") String examType, @Param("kpChuId") String kpChuId, @Param("kpUnitId")String kpUnitId);
}