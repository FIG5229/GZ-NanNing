/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamUnitAllPublicYear;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 单位年度考评结果DAO接口
 * @author bradley.zhao
 * @version 2020-02-19
 */
@MyBatisDao
public interface ExamUnitAllPublicYearDao extends CrudDao<ExamUnitAllPublicYear> {
    /**
     * 根据考评流程id获取被考评对象集合
     * @param workflowId 考评流程id
     * @param isJKC 是否为局考处流程 0 不是，1 是 根据此字段进行排序，局考处采用obj_id排序，不是关联sys_user表，采用office_id排序
     */
    List<Map<String, String>> getUnitIdsByWorkflowId(@Param(value ="workflowId") String workflowId,@Param("isJKC") String isJKC);

    List<Map<String, String>> findWorkNameList(String workflowId);

    Map<String, Object> getMapByWorkflowIdAndUnitId(@Param(value = "workflowId") String workflowId, @Param(value = "unitId") String unitId, @Param(value = "workName") String workName);
    List<Map<String, Object>> getMapByWorkflowIdAndUnitId2(@Param(value = "workflowId") String workflowId, @Param(value = "unitId") String unitId, @Param(value = "workName") String workName);

    Double getWeightScore(@Param(value = "workflowId") String workflowId, @Param(value = "unitId") String unitId,@Param(value = "isYW") String isYW);
    //处考队所 年度 最终结果展示
    Map<String, String> getDepByWorkflowIdAndUnitId(@Param(value = "workflowId") String workflowId, @Param(value = "unitId") String unitId, @Param(value = "valueType") Integer valueType);
    //处考处机关、局考局机关 年度 最终结果展示
    Map<String, String> getJGDepByWorkflowIdAndUnitId(@Param(value = "workflowId") String workflowId, @Param(value = "unitId") String unitId, @Param(value = "valueType") Integer valueType);

    //局考处
    //10.29 添加参数 workName kevin.jia
    List<Map<String, Object>> findInfoByUnitId(@Param("id") String id,@Param("year") String year,@Param("workName") String workName);
    //10.29 添加参数 workName kevin.jia
    List<Map<String, Object>> findInfoByUnitIds(@Param("ids") List<String> ids,@Param("year") String year,@Param("workName") String workName);

    //局考局
    List<Map<String, Object>> findJkjInfoByUnitId(@Param("id") String id, @Param("year") String year, @Param("baseSum") Double baseSum, @Param("isWeight") boolean isWeight);

    List<Map<String, Object>> findJkjInfoByUnitIds(@Param("ids") List<String> ids, @Param("year") String year, @Param("baseSum") Double baseSum, @Param("isWeight") boolean isWeight);

    //处考队所
    //10.29 添加参数 workName kevin.jia
    List<Map<String, Object>> findCkdsInfoByUnitId(@Param("id") String id,@Param("year") String year,@Param("workName") String workName);
    //10.29 添加参数 workName kevin.jia
    List<Map<String, Object>> findCkdsInfoByUnitIds(@Param("ids") List<String> ids,@Param("year") String year,@Param("workName") String workName);

    //处考处机关
    List<Map<String, Object>> findCkcjgInfoByUnitId(@Param("id") String id, @Param("year") String year, @Param("baseSum") Double baseSum, @Param("isWeight") boolean isWeight);

    List<Map<String, Object>> findCkcjgInfoByUnitIds(@Param("ids") List<String> ids, @Param("year") String year, @Param("baseSum") Double baseSum,  @Param("isWeight") boolean isWeight);
    //根据workflow_id  objId ,valueType 查询具体事项
    List<String> selItemByWFIDObjId(@Param("workflowId") String workflowId, @Param("objId") String unitId,@Param("valueType") Integer valueType);

    Integer getWeightIsNull(String workflowId);

    Integer getWeightIsNullByUserIdYear(@Param("userId") String userId, @Param("year") String year,@Param("examType") String examType);

    void deleteByWorkflowId(String workflowId);
}