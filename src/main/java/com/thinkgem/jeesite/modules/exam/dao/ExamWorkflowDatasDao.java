/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDatas;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考评数据表DAO接口
 * @author bradley.zhao
 * @version 2019-12-19
 */
@MyBatisDao
public interface ExamWorkflowDatasDao extends CrudDao<ExamWorkflowDatas> {
    List<Map<String, Object>> findScoreByWorkflowId(String workflowId);
    Map<String, Object> findByFillPerson(String fillPerson);
	void deleteDatas(Map<String,Object> param);
	List<Map<String,Object>> getAppraiseObjList(Map<String,Object> param);
	List<Map<String,Object>> getAppraiseObjDataList(Map<String,Object> param);
	List<Map<String,Object>> getAppraiseObjDataList2(Map<String,Object> param);
	Map<String,Object> selectDatasNumber(ExamWorkflowDatas param);
	Map<String,Object> selectDatasNumberBeta(ExamWorkflowDatas param);
	Map<String,Object> selectNumberByInfo(Map<String,Object> param);
	void updateStatus(Map<String,Object> param);
	void batchInsert(List<ExamWorkflowDatas> list);
	List<ExamWorkflowDatas> findListByWorkflowId(String workflowId);
	//最终结果计算
	List<Map<String,String>> calScores(Map<String, String> param);
	//局机关/处机关
	List<Map<String,Object>> calScoresJGUnit(Map<String, String> param);
	//局考处/处考派出所
	List<Map<String,Object>> calScores2(Map<String, String> param);
	List<Map<String,String>> findStandardsDataNum(Map<String, String> param);

	// 绩效自动考评
	List<Map<String,String>> findAllInfoById(@Param(value = "id")String id);
	//考评档案 根据objId 查询 workflow_id
    List<String> findWorkflowIdByObjId(String personId);

    //根据objId，workflowId查询最终结果公示、结束数据 --- 考评档案
    List<ExamWorkflowDatas> findInfosByOIdWFId(@Param("objId") String objId, @Param("workflowId")String workflowId);
	//根据objId，workflowIds查询最终结果公示、结束数据  --- 考评档案
    List<ExamWorkflowDatas> findIsSelListByWFIdsObjId(@Param("workflowIds")List<String> workflowIdList, @Param("objId") String objId);

    //考评流程-生成表格 根据objId 及 workflowId 查询出相关数据
    List<ExamWorkflowDatas> getListByOidWFId(@Param("objIds")List<String> objIds, @Param("workflowId")String workflowId);


//    void selectedWorkflowDatas(@Param("userId")String userId,@Param("standardId") String standardId, @Param("workflowId") String workflowId, @Param("rowNums") List<String> rowId);
    void selectedWorkflowDatas(ExamWorkflowDatas examWorkflowDatas);

    void saveBeta(ExamWorkflowDatas examWorkflowDatas);

	void updateStatusBeta(ExamWorkflowDatas datas);

	List<ExamWorkflowDatas> getMapByOidWFIdWorkName(@Param("workflowId")String workflowId, @Param("objId") String objId, @Param("workName") String workName,@Param("fillPersonId") String fillPersonId);
	//查询局考局机关 被考评单位id及名称
	List<Map<String, Object>> findJJGObjIdAndName(String workflowId);
	//查询局考处 被考评单位id及名称
    List<Map<String, Object>> findObjIdAndName(String workflowId);
	//根据考评流程id关联user表及office表查询出单位集合
    List<Map<String, Object>> findUnitListByWorkflowId(String workflowId);
	//获取被考评人id及名称 - 处领导考核生成表格
	List<Map<String, Object>> getObjId(@Param("unitId")String unitId,@Param("workflowId")String workflowId);

	//中基层-民警考核生成表格 根据workflowId  查询返回objId、objName、单位，职务
	List<Map<String, Object>> findPersonInfo(@Param("workflowId")String workflowId,@Param("officeId") String officeId);
	//局考处，自评 判断当前用户是否为填报人
    int findCountByWorkflowIdFillPersonId(@Param("workflowId") String workflowId, @Param("fillPersonId") String fillPersonId);
	//局考处，自评 判断当前用户是否为初审人
    int findCountByWorkflowIdExamPersonId(@Param("workflowId") String workflowId, @Param("examPersonId") String examPersonId);

    void updateStatusByIdsBeta(ExamWorkflowDatas examWorkflowDatas);

	List<ExamWorkflowDatas> findStandardIdByWorkflowIdAndObjId(@Param("workflowId") String workflowId, @Param("objId") String objId);

    int updatePush(ExamWorkflowDatas params);

    List<Map<String, String>> findNextExamPerson(@Param("fillPersonId") String id,@Param("nextStatus") String nextStatus,@Param("workflowId")String workflowId,
												 @Param("status")int status,@Param("userId")String userId);

    List<ExamWorkflowDatas> findAboutMeList(ExamWorkflowDatas examWorkflowDatas);

	void updatePersonBeta(ExamWorkflowDatas datas);

    void updateExamPersonByIds(ExamWorkflowDatas examWorkflowDatas);
	//表头部分页面
    List<Map<String, Object>> getHeadItemScoreList(@Param("workflowId") String workflowId, @Param("userId") String userId);
	//获取参与考评的单位
    List<Map<String, String>> getExamUnitList(String workflowId);

	List<ExamWorkflowDatas> findScoresByPcsIdWorkName(@Param("workflowId") String workflowId ,@Param("fillPersonId") String fillPersonId,@Param("workName") String workName);
	//处考派出所-生成表格-详情 modelType 0  常规  1重点  2 公共加分  3公共扣分
    List<ExamWorkflowDatas> findCkPcsScoreDetail(@Param("workflowId") String workflowId, @Param("fillPersonId") String fillPersonId ,@Param("modelType") String modelType);
    // 未选择
    List<Map<String,Object>> getHeadItemScoreNoSelectList(@Param("workflowId")String workflowId, @Param("userId")String userId);

    List<Integer> findStatusById(@Param("fillPersonId") String fillPersonId, @Param("workflowId") String workflowId,
														 @Param("userId") String userId, @Param("status") int status);
	//局考处获取工作项，根据workflowId   objId或fillPersonnelId
	List<Map<String, Object>> findJkCWorkNameWeights(@Param("workflowId") String workflowId, @Param("objId") String objId,@Param("fillPersonId") String fillPersonId);

	List<Map<String, Object>> findJkCWorkNameWeights2(@Param("workflowId") String workflowId, @Param("objId") String objId,@Param("fillPersonId") String fillPersonId);

	List<ExamWorkflowDatas> findJkcInfos(@Param("workflowId")String workflowId, @Param("fillPersonId")String fillPersonId);

	String findFirstFillPersonId(String workflowId);

    void deleteByWorkflowId(ExamWorkflowDatas examWorkflowDatas);
	////获取相应阶段操作评对象集合
    List<Map<String, String>> getCheckObjectList(@Param("workFlowId") String workFlowId, @Param("type") String type, @Param("status") Integer status);

	List<Map<String, String>> findNoExamList(@Param("workflowId") String workflowId, @Param("fillPersonId") String fillPersonId);
	//根据考评id，及被考评人员id查询考评事项
	List<String> selPersonItems(@Param("workflowId") String workflowId, @Param("objId")String objId);

	void selfRemoveById(@Param("itemId") String itemId);

	void othersRemoveById(@Param("itemId") String itemId, @Param("delPersonId") String delPersonId);

	List<ExamWorkflowDatas> findUnExamList(@Param("workflowId") String workflowId);
}