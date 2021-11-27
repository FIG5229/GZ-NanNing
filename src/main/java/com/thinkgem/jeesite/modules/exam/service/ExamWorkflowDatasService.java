/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.*;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.exam.entity.ExamWeights;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflow;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDatas;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * 考评数据表Service
 * @author bradley.zhao
 * @version 2019-12-19
 */
@Service
@Transactional(readOnly = true)
public class ExamWorkflowDatasService extends CrudService<ExamWorkflowDatasDao, ExamWorkflowDatas> {
	@Autowired
	private ExamWorkflowDatasDao examWorkflowDatasDao;

	@Autowired
	private ExamStandardBaseInfoDao examStandardBaseInfoDao;

	@Autowired
	private ExamWeightsMainDao examWeightsMainDao;

	@Autowired
	private PersonnelBaseDao personnelBaseDao;

	@Autowired
	private ExamWorkflowService examWorkflowService;

	@Autowired
	private ExamScoreWorkItemDao examScoreWorkItemDao;//得分制工作项管理DAO接口

	@Autowired
	private ExamWeightsDao examWeightsDao;

	public ExamWorkflowDatas get(String id) {
		return super.get(id);
	}

	public List<ExamWorkflowDatas> findList(ExamWorkflowDatas examWorkflowDatas) {
		return super.findList(examWorkflowDatas);
	}

	public List<ExamWorkflowDatas> findAboutMeList(ExamWorkflowDatas examWorkflowDatas) {
		return dao.findAboutMeList(examWorkflowDatas);
	}

	public Page<ExamWorkflowDatas> findPage(Page<ExamWorkflowDatas> page, ExamWorkflowDatas examWorkflowDatas) {
		return super.findPage(page, examWorkflowDatas);
	}

	@Transactional(readOnly = false)
	public void save(ExamWorkflowDatas examWorkflowDatas) {
		super.save(examWorkflowDatas);
	}

	@Transactional(readOnly = false)
	public void delete(ExamWorkflowDatas examWorkflowDatas) {
		super.delete(examWorkflowDatas);
	}

	/**
	 * 得到三个处的绩效考评分数
	 * @param workflowId
	 * @return
	 */
	public List<Map<String, Object>> getScore(String workflowId) {
		List<Map<String, Object>> scoreList = examWorkflowDatasDao.findScoreByWorkflowId(workflowId);
		//根据填报人的区分是哪个公安处的（南宁处、柳州处、北海处）
		if(scoreList != null && scoreList.size() > 0){
			for(Map<String, Object> m : scoreList){
				//根据填报人的用户id确定所属的公司（即公安处）从而确定所属的公安处
				String id = (String)m.get("fillperson");
				Map<String, Object> userMap = examWorkflowDatasDao.findByFillPerson(id);
				String company = (String)userMap.get("company");
				if("34".equals(company)){
					m.put("org", "南宁公安处");
				}else if("95".equals(company)){
					m.put("org", "柳州公安处");
				}else if("156".equals(company)){
					m.put("org", "北海公安处");
				}
			}
		}
		return scoreList;
	}

	@Transactional(readOnly = false)
	public void deleteDatas(Map<String,Object> param){
		examWorkflowDatasDao.deleteDatas(param);
	}

	@Transactional(readOnly = false)
	public List<Map<String,Object>> getAppraiseObjList(Map<String,Object> param){
		List<Map<String,Object>> list = examWorkflowDatasDao.getAppraiseObjList(param);
		return list;
	}

	/**
	 * 此方法查询速度慢  需要优化
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Map<String,Object>> getAppraiseObjDataList(Map<String,Object> param){
		List<Map<String,Object>> list = examWorkflowDatasDao.getAppraiseObjDataList(param);
		return list;
	}
	@Transactional(readOnly = false)
	public List<Map<String,Object>> getAppraiseObjDataList2(Map<String,Object> param){
		List<Map<String,Object>> list = examWorkflowDatasDao.getAppraiseObjDataList2(param);
		return list;
	}


	public List<Map<String,Object>> getAppraiseObjScoreList(List<Map<String,Object>> list){
		List<Map<String,Object>> objList = new ArrayList<Map<String,Object>>();
		if(null != list){
			Map<String,Object> objMap = null;
			String objId = "";
			String standardId = "";
			ExamStandardBaseInfo standardInfo = null;
			double conventionWorkScore = 0;//常规业务扣分
			double importWorkScore = 0;//重点业务扣分
			double commonSubstractScore = 0;//公共扣分
			double commonAddScore = 0;//公共加分
			for(int i=0; i < list.size(); i++){
				Map<String,Object> item = list.get(i);
				if(!objId.equals(item.get("objId"))){
					objMap = new HashMap<String,Object>();
					objMap.put("name",item.get("name"));
					objMap.put("objId",item.get("objId"));
					objList.add(objMap);
					objId = item.get("objId").toString();
					conventionWorkScore =0;
					importWorkScore = 0;
					commonSubstractScore =0;
					commonAddScore =0;
				}
				if(!standardId.equals(item.get("standardId"))){
					standardId = item.get("standardId").toString();
					if(null == CacheUtils.get("standard_"+standardId)){
						standardInfo = examStandardBaseInfoDao.get(standardId);
						CacheUtils.put("standard_"+standardId, standardInfo);
					}else {
						standardInfo = (ExamStandardBaseInfo) CacheUtils.get("standard_"+standardId);
					}
				}

				String weight ="100";
				if(null !=item.get("weight") && "".equals(item.get("weight").toString())){
					weight = item.get("weight").toString();
				}
				//0:常规业务,1:重点工作,2:公共加分，3:公共扣分
				if (item.get("value") == null || item.get("value").equals("")){
					item.put("value","0");
				}
				if(standardInfo!=null && "0".equals(standardInfo.getModelType())){
					conventionWorkScore +=Double.parseDouble(item.get("value").toString())*Double.parseDouble(weight)/100;
				}else if(standardInfo!=null && "1".equals(standardInfo.getModelType())){
					importWorkScore +=Double.parseDouble(item.get("value").toString())*Double.parseDouble(weight)/100;
				}else if(standardInfo!=null && "2".equals(standardInfo.getModelType())){
					commonAddScore +=Double.parseDouble(item.get("value").toString())*Double.parseDouble(weight)/100;
				}
				else if(standardInfo!=null && "3".equals(standardInfo.getModelType())){
					commonSubstractScore +=Double.parseDouble(item.get("value").toString())*Double.parseDouble(weight)/100;
				}
				if(i ==list.size()-1 ||(i < list.size()-1 &&!objId.equals(list.get(i+1).get("objId")))){
					objMap.put("conventionWorkScore",String.valueOf(conventionWorkScore));
					objMap.put("importWorkScore",String.valueOf(importWorkScore));
					objMap.put("commonSubstractScore",String.valueOf(commonSubstractScore));
					objMap.put("commonAddScore",String.valueOf(commonAddScore));
				}
			}
		}
		return objList;
	}
	//系统公示
	public List<Map<String,Object>> getAppraiseObjScoreList2(List<Map<String,Object>> list,String workflowId,String fillPersonId,ExamWorkflow examWorkflow){
		List<Map<String,Object>> objList = new ArrayList<Map<String,Object>>();
		if (StringUtils.isBlank(fillPersonId) || fillPersonId.equals("undefined")){
			return new ArrayList<>();
		}
		if(fillPersonId!=null && "1".equals(examWorkflow.getExamType())){
			//局考处
			User user = UserUtils.get(fillPersonId);
			Map<String,Object> objMap = new HashMap<>();
			objMap.put("name",user.getName());
			objMap.put("objId",user.getId());
			List<Map<String, Object>> workNameWeights = examWorkflowDatasDao.findJkCWorkNameWeights(workflowId, null, user.getId());//获取当前单位的权重集合
			List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.findJkcInfos(workflowId,user.getId());//当前单位的选择的考评项
			double conventionWorkScore = 0;
			double importWorkScore = 0;
			double commonSubstractScore = 0;
			double commonAddScore = 0;
			if(workNameWeights!=null && workNameWeights.size()>0){
				for(Map<String,Object> weightMap : workNameWeights){
					if(weightMap!=null){
						String workName = (String) weightMap.get("workName");
						double zdScore = 0.0;
						Double weight = (Double)weightMap.get("weight")==null?100.0:(Double)weightMap.get("weight");
						for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList){
							if(workName.equals(examWorkflowDatas.getWorkName())){
								String standardId = examWorkflowDatas.getStandardId();
								ExamStandardBaseInfo standardInfo = examStandardBaseInfoDao.get(standardId);
								/*if(null == CacheUtils.get("standard_"+standardId)){
									standardInfo = examStandardBaseInfoDao.get(standardId);
									CacheUtils.put("standard_"+standardId, standardInfo);
								}else {
									standardInfo = (ExamStandardBaseInfo) CacheUtils.get("standard_"+standardId);
								}*/
								//0:常规业务,1:重点工作,2:公共加分，3:公共扣分

								double value;
								if(examWorkflowDatas.getValue()!=null){
									value = Double.parseDouble(examWorkflowDatas.getValue());
								}else{
									value = 0.0;
								}
								if(standardInfo!=null && "0".equals(standardInfo.getModelType())){
									conventionWorkScore += value*(weight/100);
								}else if(standardInfo!=null && "1".equals(standardInfo.getModelType())){
									//重点
									if("2".equals(workName)){
										//高铁安保
										zdScore += (double) Math.round((value*(weight/100)) * 100) / 100;
									}else{
										zdScore += value;
									}
								}else if(standardInfo!=null && "2".equals(standardInfo.getModelType())){
									commonAddScore += value;
								}
								else if(standardInfo!=null && "3".equals(standardInfo.getModelType())){
									commonSubstractScore += value;
								}
							}
						}
						if(zdScore>weight){
							importWorkScore += weight;
						}else{
							importWorkScore += zdScore;
						}
						objMap.put("conventionWorkScore",String.valueOf(conventionWorkScore));
						objMap.put("importWorkScore",String.valueOf(importWorkScore));
						objMap.put("commonSubstractScore",String.valueOf(commonSubstractScore));
						objMap.put("commonAddScore",String.valueOf(commonAddScore));
					}

				}
				objList.add(objMap);
			}else{
				objMap.put("conventionWorkScore",0.0);
				objMap.put("importWorkScore",0.0);
				objMap.put("commonSubstractScore",0.0);
				objMap.put("commonAddScore",0.0);
				objList.add(objMap);
			}
		}
		else if(fillPersonId!=null && "3".equals(examWorkflow.getExamType())){
			//处考队所
			User user = UserUtils.get(fillPersonId);
			Map<String,Object> objMap = new HashMap<>();
			objMap.put("name",user.getName());
			objMap.put("objId",user.getId());
			List<Map<String, Object>> workNameWeights;//权重集合，根据所属处及当前单位id从权重设置表中获取
			User createBy = examWorkflow.getCreateBy();
			User updateBy = examWorkflow.getUpdateBy();
			User createUser = UserUtils.get(createBy.getId());
			User updateUser = UserUtils.get(updateBy.getId());
			Double baseSum = 100.0;//基础分值，根据所属处及当前单位id从权重设置表中获取
			if("34".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",34,") || "34".equals(createUser.getOffice().getParentId())
					|| "34".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",34,") || "34".equals(updateUser.getOffice().getParentId())
					|| user.getOffice().getParentIds().contains(",34,")
			){
				//南宁处
				workNameWeights = examWeightsMainDao.getWorkNameListByChu("34",user.getOffice().getId());
				baseSum = examWeightsMainDao.getCKDSBaseSumScore("34",user.getOffice().getId());
			} else if("95".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",95,") || "95".equals(createUser.getOffice().getParentId())
					|| "95".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",95,") || "95".equals(updateUser.getOffice().getParentId())
					|| user.getOffice().getParentIds().contains(",95,")
			){
				//柳州处
				workNameWeights = examWeightsMainDao.getWorkNameListByChu("95",user.getOffice().getId());
				baseSum = examWeightsMainDao.getCKDSBaseSumScore("95",user.getOffice().getId());
			}else if("156".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",156,") || "156".equals(createUser.getOffice().getParentId())
					|| "156".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",156,") || "156".equals(updateUser.getOffice().getParentId())
					|| user.getOffice().getParentIds().contains(",156,")
			){
					//北海处
				//21年7月19日，北海处绩效办、局绩效办负责人提出北海处派出所区分高铁、普铁分别进行权重考评
				workNameWeights = examWeightsMainDao.getWorkNameListByChu("156",user.getOffice().getId());
				baseSum = examWeightsMainDao.getCKDSBaseSumScore("156",user.getOffice().getId());
			}else{
				workNameWeights = new ArrayList<>();
				baseSum = 100.0;
			}
			if(baseSum==null){
				baseSum = 100.0;
			}
			objMap.put("baseSum",baseSum);//基础分
			//List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.findJkcInfos(workflowId,user.getId());
			double conventionWorkScore = 0;//常规业务扣分
			double importWorkScore = 0;//重点工作扣分
			double commonSubstractScore = 0;//公共扣分
			double commonAddScore = 0;//公共加分
			double addWorkItemScore = 0;  //得分制工作项得分
			if(workNameWeights!=null && workNameWeights.size()>0){
				for(Map<String,Object> weightMap : workNameWeights){
					if(weightMap!=null){
						String workName = (String) weightMap.get("value");
						String workNameType = (String) weightMap.get("workNameType");// 0  常规  1 重点 3 公共加扣分
						Integer isAddItem = examScoreWorkItemDao.getInfoCount(workName, "3");//是否为得分制工作项
						double zdScore = 0.0;
						double zdScoreAdd = 0.0;
						double cgScore = 0.0;
						double cgScoreAdd = 0.0;
						Double weight = (Double)weightMap.get("weight") == null ? 100.0:(Double)weightMap.get("weight");
						List<ExamWorkflowDatas> examWorkflowDatasList1 = examWorkflowDatasDao.getMapByOidWFIdWorkName(examWorkflow.getId(), user.getId(), workName, user.getId());
						/*for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList){
							if(workName.equals(examWorkflowDatas.getWorkName())){
								String standardId = examWorkflowDatas.getStandardId();
								ExamStandardBaseInfo standardInfo = null;
								if(null == CacheUtils.get("standard_"+standardId)){
									standardInfo = examStandardBaseInfoDao.get(standardId);
									CacheUtils.put("standard_"+standardId, standardInfo);
								}else {
									standardInfo = (ExamStandardBaseInfo) CacheUtils.get("standard_"+standardId);
								}
								//0:常规业务,1:重点工作,2:公共加分，3:公共扣分
								double value;
								if(examWorkflowDatas.getValue()!=null){
									value = Double.parseDouble(examWorkflowDatas.getValue());
								}else{
									value = 0.0;
								}
								if("0".equals(standardInfo.getModelType())){
									conventionWorkScore += value*(weight/100);
								}else if("1".equals(standardInfo.getModelType())){
									//重点
									if("2".equals(workName)){
										//高铁安保
										zdScore += (double) Math.round((value*(weight/100)) * 100) / 100;
									}else{
										zdScore += value;
									}
								}else if("2".equals(standardInfo.getModelType())){
									commonAddScore += value;
								}
								else if("3".equals(standardInfo.getModelType())){
									commonSubstractScore += value;
								}
							}
						}
						if(zdScore>weight){
							importWorkScore += weight;
						}else{
							importWorkScore += zdScore;
						}
						objMap.put("conventionWorkScore",String.valueOf(conventionWorkScore));
						objMap.put("importWorkScore",String.valueOf(importWorkScore));
						objMap.put("commonSubstractScore",String.valueOf(commonSubstractScore));
						objMap.put("commonAddScore",String.valueOf(commonAddScore));*/
						for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList1){
							//workNameType  0  常规  1 重点 3 公共加扣分  来自考评维护-工作权重设置表
							double value;
							if(examWorkflowDatas.getValue()!=null){
								value = Double.parseDouble(examWorkflowDatas.getValue());
							}else{
								value = 0.0;
							}
							Integer valueType = examWorkflowDatas.getValueType();
							if(valueType!=null){
								if("0".equals(workNameType)){
									if(valueType==1){
										cgScoreAdd += value;
									}else{
										//扣分
										cgScore += value;
									}
								}else if("1".equals(workNameType)){
									//重点
									if(valueType==1){
										zdScoreAdd += value;
									}else{
										//扣分
										zdScore += value;
									}
								}else if("3".equals(workNameType)){
									//公共加扣分
									if(valueType==1){
										commonAddScore += value;
									}else{
										commonSubstractScore += value;
									}

								}
							}


						}
						//conventionWorkScore  常规
						if("0".equals(workNameType)){
							if(isAddItem>0){
								//得分工作项
								conventionWorkScore += 0.0;
								double tempScore = cgScore-cgScoreAdd;
								if(tempScore>0){
									//扣分大于得分
									addWorkItemScore  += 0.0;
								}else{
									addWorkItemScore += -tempScore;
								}

							}else{
								double tempScore = cgScore-cgScoreAdd;
								if(tempScore>100){
									conventionWorkScore += weight;
								}else{
									conventionWorkScore += tempScore*(weight/100);
								}
							}
						}
						//conventionWorkScore += (cgScore-cgScoreAdd);
						//重点
						if("1".equals(workNameType)){
							double tempScore = zdScore-zdScoreAdd;
							if("2".equals(workName)){
								//高铁安保
								if(isAddItem>0){
									//得分工作项
									importWorkScore += 0.0;
									if(tempScore>0){
										//扣分大于得分
										addWorkItemScore  += 0.0;
									}else{
										addWorkItemScore += -tempScore;
									}
								}else{
									if(tempScore>100){
										importWorkScore += weight;
									}else{
										importWorkScore += tempScore*(weight/100);
									}
								}
							}else{
								if(isAddItem>0){
									importWorkScore += 0.0;
									if(tempScore>0){
										//扣分大于得分
										addWorkItemScore  += 0.0;
									}else{
										addWorkItemScore += -tempScore;
									}
								}else{
									if(tempScore >weight){
										importWorkScore += weight;
									}else{
										importWorkScore += tempScore;
									}
								}
							}
						}
						//importWorkScore += (zdScore-zdScoreAdd);
						objMap.put("sumWorkScore",keepTwoDecimal(baseSum-conventionWorkScore-importWorkScore-commonSubstractScore+commonAddScore+addWorkItemScore));//总得分
						objMap.put("conventionWorkScore",keepTwoDecimal(conventionWorkScore));//常规业务扣分
						objMap.put("importWorkScore",keepTwoDecimal(importWorkScore));//重点工作扣分
						objMap.put("commonSubstractScore",keepTwoDecimal(commonSubstractScore));//公共扣分
						objMap.put("commonAddScore",keepTwoDecimal(commonAddScore));//公共加分
						objMap.put("addWorkItemScore",keepTwoDecimal(addWorkItemScore));//得分制工作项得分

					}

				}
				objList.add(objMap);
			}else{
				/*objMap.put("conventionWorkScore",0.0);
				objMap.put("importWorkScore",0.0);
				objMap.put("commonSubstractScore",0.0);
				objMap.put("commonAddScore",0.0);*/
				objMap.put("sumWorkScore",keepTwoDecimal(baseSum-conventionWorkScore-importWorkScore-commonSubstractScore+commonAddScore+addWorkItemScore));//总得分
				objMap.put("conventionWorkScore",keepTwoDecimal(conventionWorkScore));//常规业务扣分
				objMap.put("importWorkScore",keepTwoDecimal(importWorkScore));//重点工作扣分
				objMap.put("commonSubstractScore",keepTwoDecimal(commonSubstractScore));//公共扣分
				objMap.put("commonAddScore",keepTwoDecimal(commonAddScore));//公共加分
				objMap.put("addWorkItemScore",keepTwoDecimal(addWorkItemScore));//得分制工作项得分

				objList.add(objMap);
			}
		}
		else if(fillPersonId!=null &&("2".equals(examWorkflow.getExamType())||"4".equals(examWorkflow.getExamType()))){
			//局考局机关，处考处机关
			/*判断按照权重制得分还是非权重制得分*/
			String  examType = examWorkflow.getExamType();
			ExamWeights examWeights = new ExamWeights();
			examWeights.setKpType(examType);
//          examWeights.setKpChu(companyId);  //处考处机关可能需要
			examWeights.setKpChu(null);
			if("4".equals(examType)){
				User user;
				if(StringUtils.isNotBlank(fillPersonId)){
					user = UserUtils.get(fillPersonId);
				}else{
					user = UserUtils.getUser();
				}
				String companyId = user.getCompany().getId();
				if(!companyId.equals("34") && !companyId.equals("95") && !companyId.equals("156")){
					if(user.getOffice().getParentIds().contains(",34,")){
						companyId = "34";
					}
					if(user.getOffice().getParentIds().contains(",95,")){
						companyId = "95";
					}
					if(user.getOffice().getParentIds().contains(",156,")){
						companyId = "156";
					}
				}
				examWeights.setKpChu(companyId);
			}
			examWeights.setDepartmentId(null);
			examWeights = examWeightsDao.getWeightByUnit(examWeights);
			if(examWeights == null){
				//非权重制得分
				String basicScore = DictUtils.getDictValue(examWorkflow.getExamType(),"exam_basicScore","100");//从字典获取基本分值（目前未使用）
				Double baseSum = 100.0;
				try {
					baseSum = Double.valueOf(basicScore);
				}catch (Exception e){
					logger.error(getExceptionInfo(e));
					e.printStackTrace();
					baseSum = 100.0;
				}
				if(null != list) {
					Map<String, Object> objMap = null;
					String objId = "";
					String standardId = "";
					ExamStandardBaseInfo standardInfo = null;
					double conventionWorkScore = 0;//常规业务扣分
					double importWorkScore = 0;//重点业务扣分
					double commonSubstractScore = 0;//公共扣分
					double commonAddScore = 0;//公共加分
					if(list.size()>0){
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> item = list.get(i);
							if (!objId.equals(item.get("objId"))) {
								objMap = new HashMap<String, Object>();
								objMap.put("baseSum",baseSum);//基础分
								objMap.put("name", item.get("name"));
								objMap.put("objId", item.get("objId"));
								objList.add(objMap);
								objId = item.get("objId").toString();
								conventionWorkScore = 0;
								importWorkScore = 0;
								commonSubstractScore = 0;
								commonAddScore = 0;
							}
							if (!standardId.equals(item.get("standardId"))) {
								if(standardId!=null){
									standardId = item.get("standardId").toString();
									standardInfo = examStandardBaseInfoDao.get(standardId);
								}
								//存在可能将标准模板类型设置错误的，标准更新后该缓存无法获取最新数据
							/*if (null == CacheUtils.get("standard_" + standardId)) {
								standardInfo = examStandardBaseInfoDao.get(standardId);
								CacheUtils.put("standard_" + standardId, standardInfo);
							} else {
								standardInfo = (ExamStandardBaseInfo) CacheUtils.get("standard_" + standardId);
							}*/
							}
							int valueType;
							try {
								valueType = (int) item.get("valueType");
							}catch (Exception e){
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
								valueType = -1;
							}
							String weight = "100";
							if (null != item.get("weight") && "".equals(item.get("weight").toString())) {
								weight = item.get("weight").toString();
							}
							//0:常规业务,1:重点工作,2:公共加分，3:公共扣分
							if (item.get("value") == null || item.get("value").equals("")) {
								item.put("value", "0");
							}
							if (standardInfo!=null &&"0".equals(standardInfo.getModelType())) {
								Double value;
								try {
									if(valueType==-1){
										value = Double.parseDouble(item.get("value").toString());
									}else{
										value = -Double.parseDouble(item.get("value").toString());
									}
								}catch (Exception e){
									value = 0.0;
									logger.error(getExceptionInfo(e));
									e.printStackTrace();
								}
								conventionWorkScore += keepTwoDecimal(value * Double.parseDouble(weight) / 100);
							} else if (standardInfo!=null &&"1".equals(standardInfo.getModelType())) {
								Double value;
								try {
									if(valueType==-1){
										value = Double.parseDouble(item.get("value").toString());
									}else{
										value = -Double.parseDouble(item.get("value").toString());
									}
								}catch (Exception e){
									value = 0.0;
									logger.error(getExceptionInfo(e));
									e.printStackTrace();
								}
								importWorkScore += keepTwoDecimal(value);
							} else if (standardInfo!=null &&"2".equals(standardInfo.getModelType())) {
								Double value;
								try {
									value = Double.parseDouble(item.get("value").toString());
								}catch (Exception e){
									value = 0.0;
									logger.error(getExceptionInfo(e));
									e.printStackTrace();
								}
								commonAddScore += keepTwoDecimal(value * Double.parseDouble(weight) / 100);
							} else if (standardInfo!=null &&"3".equals(standardInfo.getModelType())) {
								Double value;
								try {
									value = Double.parseDouble(item.get("value").toString());
								}catch (Exception e){
									value = 0.0;
									logger.error(getExceptionInfo(e));
									e.printStackTrace();
								}
								commonSubstractScore += keepTwoDecimal(value * Double.parseDouble(weight) / 100);

							}
							if (i == list.size() - 1 || (i < list.size() - 1 && !objId.equals(list.get(i + 1).get("objId")))) {
								objMap.put("conventionWorkScore", String.valueOf(keepTwoDecimal(conventionWorkScore)));
								objMap.put("importWorkScore", String.valueOf(keepTwoDecimal(importWorkScore)));
								objMap.put("commonSubstractScore", String.valueOf(keepTwoDecimal(commonSubstractScore)));
								objMap.put("commonAddScore", String.valueOf(keepTwoDecimal(commonAddScore)));
							}
						}
					}else{
						User user = UserUtils.get(fillPersonId);
						objMap = new HashMap<String, Object>();
						objMap.put("name", user.getName());
						objMap.put("objId", user.getId());
						objList.add(objMap);
						objMap.put("conventionWorkScore", String.valueOf(conventionWorkScore));
						objMap.put("importWorkScore", String.valueOf(importWorkScore));
						objMap.put("commonSubstractScore", String.valueOf(commonSubstractScore));
						objMap.put("commonAddScore", String.valueOf(commonAddScore));
						objMap.put("baseSum", baseSum);//基础分
					}

				}
			}
			else{
				//权重制得分
				User fillPersonUser = UserUtils.get(fillPersonId);
				Map<String,Object> objMap = new HashMap<>();
				objMap.put("name",fillPersonUser.getName());
				objMap.put("objId",fillPersonUser.getId());
				List<Map<String, Object>> workNameWeights = null;
				Double baseSum = 100.0;
				try {
					if("2".equals(examType)){
						//局考局机关
						baseSum = examWeightsMainDao.getJorCJGBaseSumScore(examType,null,null);//基础分,从权重设置子表获取基础分值（去除得分制工作项及公共加扣分）
						workNameWeights = examWeightsMainDao.findWorkNameByFillPersonId(fillPersonId, workflowId,examType);//查询相关工作项及权重集合
					}
					else{
						//处考处机关
						String unitId;
						String kpUnitId ;
						if("34".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",34,") || "34".equals(fillPersonUser.getOffice().getParentId())){
							//南宁处
							unitId = "34";
							kpUnitId = fillPersonUser.getOffice().getId();
						} else if("95".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",95,") || "95".equals(fillPersonUser.getOffice().getParentId())){
							//柳州处
							unitId = "95";
							kpUnitId = fillPersonUser.getOffice().getId();
						}else if("156".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",156,") || "156".equals(fillPersonUser.getOffice().getParentId())){
							//北海处
							unitId = "156";
							kpUnitId = null;
						}else{
							unitId = null;
							kpUnitId = fillPersonUser.getOffice().getId();
						}
//					totalScore = examWeightsMainDao.getJorCJGBaseSumScore(examType,unitId,kpUnitId);//基础分
						baseSum = examWeightsMainDao.getJorCJGBaseSumScore(examType,unitId,null);//基础分 ，从权重设置子表获取基础分值（去除得分制工作项及公共加扣分）
//						workNameWeights = examWeightsMainDao.findWorkNameByFillPersonId(fillPersonId, workflowId,examType);//查询相关工作项及权重集合
						workNameWeights = examWeightsMainDao.findWorkNameByChu_FillPersonId(fillPersonId, workflowId,examType,unitId,null);//21年7月26 查询相关工作项及权重集合，目前需求无需根据选择的单位去匹配权重
					}
				}catch (Exception e){
					baseSum = 100.0;
					logger.error(getExceptionInfo(e));
					e.printStackTrace();
				}
				Double tempTotalScore = baseSum;
				objMap.put("baseSum",baseSum);//基础分
				double conventionWorkScore = 0;//常规业务扣分
				double importWorkScore = 0;//重点工作扣分
				double commonSubstractScore = 0;//公共扣分
				double commonAddScore = 0;//公共加分
				double addWorkItemScore = 0;  //得分制工作项得分
				if(workNameWeights!=null && workNameWeights.size()>0){
					for(Map<String,Object> weightMap : workNameWeights){
						if(weightMap!=null){
							String workName = (String) weightMap.get("value");
							String workNameType = (String) weightMap.get("workNameType");// 0  常规  1 重点 3 公共加扣分
							Integer isAddItem = examScoreWorkItemDao.getInfoCount(workName, examType);//是否为得分制工作项
							double zdScore = 0.0;
							double zdScoreAdd = 0.0;
							double cgScore = 0.0;
							double cgScoreAdd = 0.0;
							Double weight = (Double)weightMap.get("weight") == null ? 100.0:(Double)weightMap.get("weight");
							List<ExamWorkflowDatas> examWorkflowDatasList1 = examWorkflowDatasDao.getMapByOidWFIdWorkName(examWorkflow.getId(), fillPersonUser.getId(), workName, fillPersonUser.getId());
							for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList1){
								//workNameType  0  常规  1 重点 3 公共加扣分  来自考评维护-工作权重设置表
								double value;
								if(examWorkflowDatas.getValue()!=null){
									value = Double.parseDouble(examWorkflowDatas.getValue());
								}else{
									value = 0.0;
								}
								Integer valueType = examWorkflowDatas.getValueType();
								if(valueType!=null){
									if("0".equals(workNameType)){
										if(valueType==1){
											cgScoreAdd += value;
										}else{
											//扣分
											cgScore += value;
										}
									}else if("1".equals(workNameType)){
										//重点
										if(valueType==1){
											zdScoreAdd += value;
										}else{
											//扣分
											zdScore += value;
										}
									}else if("3".equals(workNameType)){
										//公共加扣分
										if(valueType==1){
											commonAddScore += value;
										}else{
											commonSubstractScore += value;
										}

									}
								}


							}
							//conventionWorkScore  常规
							if("0".equals(workNameType)){
								if(isAddItem>0){
									//得分工作项
									conventionWorkScore += 0.0;
									double tempScore = cgScore-cgScoreAdd;
									if(tempScore>0){
										//扣分大于得分
										addWorkItemScore  += 0.0;
									}else{
										addWorkItemScore += -tempScore;
									}

								}else{
									double tempScore = cgScore-cgScoreAdd;
									if(tempScore>100){
										conventionWorkScore += weight;
									}else{
										conventionWorkScore += tempScore*(weight/100);
									}
								}
							}
							//conventionWorkScore += (cgScore-cgScoreAdd);
							//重点
							if("1".equals(workNameType)){
								double tempScore = zdScore-zdScoreAdd;
								if("2".equals(workName)){
									//高铁安保
									if(isAddItem>0){
										//得分工作项
										importWorkScore += 0.0;
										if(tempScore>0){
											//扣分大于得分
											addWorkItemScore  += 0.0;
										}else{
											addWorkItemScore += -tempScore;
										}
									}else{
										if(tempScore>100){
											importWorkScore += weight;
										}else{
											importWorkScore += tempScore*(weight/100);
										}
									}
								}else{
									if(isAddItem>0){
										importWorkScore += 0.0;
										if(tempScore>0){
											//扣分大于得分
											addWorkItemScore  += 0.0;
										}else{
											addWorkItemScore += -tempScore;
										}
									}else{
										if(tempScore >weight){
											importWorkScore += weight;
										}else{
											importWorkScore += tempScore;
										}
									}
								}
							}
							//importWorkScore += (zdScore-zdScoreAdd);
							objMap.put("sumWorkScore",keepTwoDecimal(baseSum-conventionWorkScore-importWorkScore-commonSubstractScore+commonAddScore+addWorkItemScore));//总得分
							objMap.put("conventionWorkScore",keepTwoDecimal(conventionWorkScore));//常规业务扣分
							objMap.put("importWorkScore",keepTwoDecimal(importWorkScore));//重点工作扣分
							objMap.put("commonSubstractScore",keepTwoDecimal(commonSubstractScore));//公共扣分
							objMap.put("commonAddScore",keepTwoDecimal(commonAddScore));//公共加分
							objMap.put("addWorkItemScore",keepTwoDecimal(addWorkItemScore));//得分制工作项得分

						}
					}
					objList.add(objMap);
				}else{
				/*objMap.put("conventionWorkScore",0.0);
				objMap.put("importWorkScore",0.0);
				objMap.put("commonSubstractScore",0.0);
				objMap.put("commonAddScore",0.0);*/
					objMap.put("sumWorkScore",keepTwoDecimal(baseSum-conventionWorkScore-importWorkScore-commonSubstractScore+commonAddScore+addWorkItemScore));//总得分
					objMap.put("conventionWorkScore",keepTwoDecimal(conventionWorkScore));//常规业务扣分
					objMap.put("importWorkScore",keepTwoDecimal(importWorkScore));//重点工作扣分
					objMap.put("commonSubstractScore",keepTwoDecimal(commonSubstractScore));//公共扣分
					objMap.put("commonAddScore",keepTwoDecimal(commonAddScore));//公共加分
					objMap.put("addWorkItemScore",keepTwoDecimal(addWorkItemScore));//得分制工作项得分

					objList.add(objMap);
				}
			}
		}
		else{
			//处领导、中基层、民警考评
			String basicScore = DictUtils.getDictValue(examWorkflow.getExamType(),"exam_basicScore","100");//从字典获取基本分值（目前未使用）
			Double baseSum = 100.0;
			try {
				baseSum = Double.valueOf(basicScore);
			}catch (Exception e){
				logger.error(getExceptionInfo(e));
				e.printStackTrace();
				baseSum = 100.0;
			}
			if(null != list) {
				Map<String, Object> objMap = null;
				String objId = "";
				String standardId = "";
				ExamStandardBaseInfo standardInfo = null;
				double conventionWorkScore = 0;//常规业务扣分
				double importWorkScore = 0;//重点业务扣分
				double commonSubstractScore = 0;//公共扣分
				double commonAddScore = 0;//公共加分
				if(list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> item = list.get(i);
						if (!objId.equals(item.get("objId"))) {
							objMap = new HashMap<String, Object>();
							objMap.put("baseSum",baseSum);//基础分
							objMap.put("name", item.get("name"));
							objMap.put("objId", item.get("objId"));
							objList.add(objMap);
							objId = item.get("objId").toString();
							conventionWorkScore = 0;
							importWorkScore = 0;
							commonSubstractScore = 0;
							commonAddScore = 0;
						}
						if (!standardId.equals(item.get("standardId"))) {
							if(standardId!=null){
								standardId = item.get("standardId").toString();
								standardInfo = examStandardBaseInfoDao.get(standardId);
							}
							//存在可能将标准模板类型设置错误的，标准更新后该缓存无法获取最新数据
							/*if (null == CacheUtils.get("standard_" + standardId)) {
								standardInfo = examStandardBaseInfoDao.get(standardId);
								CacheUtils.put("standard_" + standardId, standardInfo);
							} else {
								standardInfo = (ExamStandardBaseInfo) CacheUtils.get("standard_" + standardId);
							}*/
						}
						int valueType;
						try {
							valueType = (int) item.get("valueType");
						}catch (Exception e){
							logger.error(getExceptionInfo(e));
							e.printStackTrace();
							valueType = -1;
						}
						String weight = "100";
						if (null != item.get("weight") && "".equals(item.get("weight").toString())) {
							weight = item.get("weight").toString();
						}
						//0:常规业务,1:重点工作,2:公共加分，3:公共扣分
						if (item.get("value") == null || item.get("value").equals("")) {
							item.put("value", "0");
						}
						if (standardInfo!=null &&"0".equals(standardInfo.getModelType())) {
							Double value;
							try {
								if(valueType==-1){
									value = Double.parseDouble(item.get("value").toString());
								}else{
									value = -Double.parseDouble(item.get("value").toString());
								}
							}catch (Exception e){
								value = 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
							conventionWorkScore += keepTwoDecimal(value * Double.parseDouble(weight) / 100);
						} else if (standardInfo!=null &&"1".equals(standardInfo.getModelType())) {
							Double value;
							try {
								if(valueType==-1){
									value = Double.parseDouble(item.get("value").toString());
								}else{
									value = -Double.parseDouble(item.get("value").toString());
								}
							}catch (Exception e){
								value = 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
							importWorkScore += keepTwoDecimal(value);
						} else if (standardInfo!=null &&"2".equals(standardInfo.getModelType())) {
							Double value;
							try {
								value = Double.parseDouble(item.get("value").toString());
							}catch (Exception e){
								value = 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
							commonAddScore += keepTwoDecimal(value * Double.parseDouble(weight) / 100);
						} else if (standardInfo!=null &&"3".equals(standardInfo.getModelType())) {
							Double value;
							try {
								value = Double.parseDouble(item.get("value").toString());
							}catch (Exception e){
								value = 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
							commonSubstractScore += keepTwoDecimal(value * Double.parseDouble(weight) / 100);

						}
						if (i == list.size() - 1 || (i < list.size() - 1 && !objId.equals(list.get(i + 1).get("objId")))) {
							objMap.put("conventionWorkScore", String.valueOf(keepTwoDecimal(conventionWorkScore)));
							objMap.put("importWorkScore", String.valueOf(keepTwoDecimal(importWorkScore)));
							objMap.put("commonSubstractScore", String.valueOf(keepTwoDecimal(commonSubstractScore)));
							objMap.put("commonAddScore", String.valueOf(keepTwoDecimal(commonAddScore)));
						}
					}
				}else{
					User user = UserUtils.get(fillPersonId);
					objMap = new HashMap<String, Object>();
					objMap.put("name", user.getName());
					objMap.put("objId", user.getId());
					objList.add(objMap);
					objMap.put("conventionWorkScore", String.valueOf(conventionWorkScore));
					objMap.put("importWorkScore", String.valueOf(importWorkScore));
					objMap.put("commonSubstractScore", String.valueOf(commonSubstractScore));
					objMap.put("commonAddScore", String.valueOf(commonAddScore));
					objMap.put("baseSum", baseSum);//基础分
				}

			}
		}
		return objList;
	}

	@Transactional(readOnly = false)
	public Map<String,Object> selectDatasNumber(ExamWorkflowDatas param){
		return examWorkflowDatasDao.selectDatasNumber(param);
	}

	/**
	 * status为下一个环节状态，不传时将查询考评对象所有的考评项
	 * 根据环节状态 查询所有状态小于下一环节的状态并且下一环节的审核人不为空，查出结果为0，此考评则可进入下一环节
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String,Object> selectDatasNumberBeta(ExamWorkflowDatas param){
		int nextStatus = param.getStatus();
		switch (nextStatus){
			case 2:
				param.setExamPersonId("is not null");
				break;
			case 3:
				break;
			case 4:
				param.setDepartSignId("is not null");
				break;
			case 5:
				param.setPartBureausSignId("is not null");
				break;
			case 6:
				param.setAdjustPersonId("is not null");
				break;
			case 7:
				param.setMainBureausSignId("is not null");
				break;
			case 8:
				;
				break;
		}
		param.setStatus(null);
		return examWorkflowDatasDao.selectDatasNumberBeta(param);
	}

	@Transactional(readOnly = false)
	public Map<String,Object> selectNumberByInfo(Map<String,Object> param){
		return examWorkflowDatasDao.selectNumberByInfo(param);
	}

	@Transactional(readOnly = false)
	public void updateStatus(Map<String,Object> param){
		examWorkflowDatasDao.updateStatus(param);
	}

	@Transactional(readOnly = false)
	public void batchInsert(List<ExamWorkflowDatas> list){
		examWorkflowDatasDao.batchInsert(list);
	}
	//个人考评（处领导、中基层、民警考评）-最终结果公示
	public List<Map<String,String>> calScores(Map<String,String> param){
		return examWorkflowDatasDao.calScores(param);
	}
	//局机关/处机关-最终结果公示
	public List<Map<String,Object>> calScoresJGUnit(Map<String,String> param){
		return examWorkflowDatasDao.calScoresJGUnit(param);
	}
	//处考队所 - 最终结果公示
	public List<Map<String,Object>> calScores2(Map<String,String> param){
		return examWorkflowDatasDao.calScores2(param);
	}
	public List<Map<String,String>> findStandardsDataNum(Map<String,String> param){
		return examWorkflowDatasDao.findStandardsDataNum(param);
	}

	// 绩效自动考评
	public List<Map<String, String>> findAllInfoById(String id) {
		return examWorkflowDatasDao.findAllInfoById(id);
	}

	/*自评选中的考评项*/
	@Transactional(readOnly = false)
    public void selectedWorkflowDatas(ExamWorkflowDatas examWorkflowDatas, List<String> rowId) {
		if (StringUtils.isBlank(examWorkflowDatas.getFillPersonId())){
			examWorkflowDatas.setFillPersonId(UserUtils.getUser().getId());
		}
		examWorkflowDatas.setRowNums(rowId);
		ExamWorkflow examWorkflow = examWorkflowService.get(examWorkflowDatas.getWorkflowId());
		switch (examWorkflow.getStatus()){
			case 1:
				break;
			case 2:
				examWorkflowDatas.setExamPersonId(UserUtils.getUser().getId());
				examWorkflowDatas.setExamPerson(UserUtils.getUser().getName());
				break;
			case 3:
				examWorkflowDatas.setExamPersonId(UserUtils.getUser().getId());
				examWorkflowDatas.setExamPerson(UserUtils.getUser().getName());
				break;
			case 4:
				examWorkflowDatas.setDepartSignId(UserUtils.getUser().getId());
				examWorkflowDatas.setDepartSign(UserUtils.getUser().getName());
				break;
			case 5:
				examWorkflowDatas.setPartBureausSignId(UserUtils.getUser().getId());
				examWorkflowDatas.setPartBureausSign(UserUtils.getUser().getName());
				break;
			case 6:
				examWorkflowDatas.setAdjustPersonId(UserUtils.getUser().getId());
				examWorkflowDatas.setAdjustPerson(UserUtils.getUser().getName());
				break;
			case 7:
				examWorkflowDatas.setMainBureausSignId(UserUtils.getUser().getId());
				examWorkflowDatas.setMainBureausSign(UserUtils.getUser().getName());
				break;
			case 8:
				break;

		}
		examWorkflowDatasDao.selectedWorkflowDatas(examWorkflowDatas);
    }

    /*绩效自评时保存数据*/
	@Transactional(readOnly = false)
	public void saveBeta(ExamWorkflowDatas examWorkflowDatas) {
		examWorkflowDatasDao.saveBeta(examWorkflowDatas);
	}

	/*当前环节推送到下一环节时，把未选择的考评项修改为下一个状态*/
	@Transactional(readOnly = false)
	public void updateStatusBeta(ExamWorkflowDatas datas) {

		dao.updateStatusBeta(datas);
	}
	/**
	 * 获取局考处各个考评项目分数
	 * 生成表格以及最终结果公示查看详情、导出
	 * @param status 流程状态
	 * 此次算分逻辑发生变化，     ExamUnitAllPublicYearService  下 yearUnitSaveBeta（）方法也需要进行相应调整
	 * 此次算分逻辑发生变化，     ExamUnitAllPublicService  下 monthOrgDatasSaveBeta（）方法也需要进行相应调整
	 * 工作项分为 常规业务，重点工作，公共加分，公共扣分，得分制工作项（根据exam_score_work_item 得分制工作项数据查询判断）
	 *  常规业务：初始分值100分，（100+-分值）*(权重分/100) 保留两位小数，最少为0，不能出现负分
	 *  重点工作： 直接由   权重分+-分值  （高铁安保除外，高铁安保采用常规业务算法） 保留两位小数，最少为0，不能出现负分
	 *  公共加分：0 + 分值   保留两位小数  不会出现负分，只有加分项
	 *  公共扣分：0 - 分值   保留两位小数  可以为负分
	 *  得分制工作项：0 +- 分值   保留两位小数，可以为负分  （与局绩效办沟通后，得分制工作项一般为加分制）
	 *  加分、扣分根据value_type 值进行判断，-1 减分 1 加分 该处内容根据创建格式管理选择的分值+-生成
	 */
    public Map<String, Object> getIngScoreList(String workflowId,String objId, Integer status) {
        User user = UserUtils.getUser();
        List<String> objIds = new ArrayList<>();//各个公安处单位id集合
        List<String> unitNames = new ArrayList<>();//各个公安处名称集合
		if(StringUtils.isBlank(objId)){
			//objId为空，说明当前考评尚未结束，通过生成表格进入此方法，根据当前用户所属处，决定显示那些处
			if ("34".equals(user.getOffice().getId()) || user.getOffice().getParentIds().indexOf(",34") != -1) {//当前用户为南宁处账号或当前用户为南宁处单位下的用户
				objIds.add("34");//南宁处
				unitNames.add("南宁公安处");
			} else if ("95".equals(user.getOffice().getId()) || user.getOffice().getParentIds().indexOf(",95") != -1) {//当前用户为柳州处账号或当前用户为柳州处单位下的用户
				objIds.add("95");//柳州处
				unitNames.add("柳州公安处");
			} else if ("156".equals(user.getOffice().getId()) || user.getOffice().getParentIds().indexOf(",156") != -1) {//当前用户为北海处账号或当前用户为北海处单位下的用户
				objIds.add("156");//北海处
				unitNames.add("北海公安处");
			} else {
				//局机关可查看所有处
				objIds.add("34");//南宁处
				unitNames.add("南宁公安处");
				objIds.add("95");//柳州处
				unitNames.add("柳州公安处");
				objIds.add("156");//北海处
				unitNames.add("北海公安处");
			}
		}else{
			//objId 不为空，说明当前流程为 最终结果公示，并通过点击最终结果公示页面中的分值进入该方法，因此只需要根据objId设置要展示的相关处扣分情况即可
			if ("34".equals(objId)) {//当前用户为南宁处账号或当前用户为南宁处单位下的用户
				objIds.add(objId);//南宁处
				unitNames.add("南宁公安处");
			} else if ("95".equals(objId)) {//当前用户为柳州处账号或当前用户为柳州处单位下的用户
				objIds.add("95");//柳州处
				unitNames.add("柳州公安处");
			} else if ("156".equals(objId)) {//当前用户为北海处账号或当前用户为北海处单位下的用户
				objIds.add("156");//北海处
				unitNames.add("北海公安处");
			}
		}

        Map<String, Object> resultMap = new HashMap<>();//返回结果集
        //查询出相关的考评数据   根据 obj_id，workflow_id，is_selected
        //List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.getListByOidWFId(objIds,workflowId);
        //从工作权重表中取出相关工作项及权重
        List<Map<String, Object>> weigthsList;
        String fillPersonId = null;
        int count = examWorkflowDatasDao.findCountByWorkflowIdFillPersonId(workflowId,user.getId());
        if(count>0 && !"46c521bf67e24db28772b3eac52dc454".equals(user.getId())
				&& !"6af0e615e9b0477da82eff06ff532c8b".equals(user.getId())
				&& !"978958003ea44a4bba3eed8ee6ceff3c".equals(user.getId()) ){
        	//流程状态自评且当前用户为填报人，且不是北海处绩效办，柳州处绩效办，南宁处绩效办
			//自评人（北海处绩效办，柳州处绩效办，南宁处绩效办除外）只能查看自己负责自评的工作项
			weigthsList = examWeightsMainDao.findWorkNameByFillPersonId(user.getId(),workflowId,"1");//只查看自己负责自评的工作项
			fillPersonId = user.getId();
			resultMap.put("isFillPerson","isFillPerson");
		}else{
        	int examPersonCount = examWorkflowDatasDao.findCountByWorkflowIdExamPersonId(workflowId,user.getId());
        	if(examPersonCount>0
					&& !"cca66e1339f14799b01f6db43ed16e16".equals(user.getId())
					&& !"46c521bf67e24db28772b3eac52dc454".equals(user.getId())
					&& !"6af0e615e9b0477da82eff06ff532c8b".equals(user.getId())
					&& !"978958003ea44a4bba3eed8ee6ceff3c".equals(user.getId())
			){
        		//当前用户为考评人且不是局绩效办用户,北海处绩效办，柳州处绩效办，南宁处绩效办
				//审核单位只能查看自己负责审核的工作项
				weigthsList = examWeightsMainDao.findWorkNameByExamPersonId(user.getId(),workflowId);//查询自己负责审核的工作项
			}else{
				weigthsList = examWeightsMainDao.findWorkNameList();//查询所有工作项
			}
        }
        //汇总集合 包含处名，分数，工作项，权重，分数（未用）
        //List conventionScoreList = new ArrayList<List<Map<String, Object>>>();
        //单位各处信息（单位名称，单位id，业务权重合计得分，总公共扣分，总公共加分，总得分）集合
        List<Map<String, String>> unitList = new ArrayList<>();
		//遍历各个公安处名称集合，完善该集合信息，单位id以及预设总权重分 totalWeightScore ，总公共加分 totalPublicAddScore，总公共扣分 totalPublicDeductScore，总分 totaltScore
        for (int i = 0; i < unitNames.size(); i++) {
            Map<String, String> unitMap = new HashMap<>();
            unitMap.put("unitName", unitNames.get(i));
            unitMap.put("unitId", objIds.get(i));
            unitMap.put("totalWeightScore", String.valueOf(0.0));
            unitMap.put("totalPublicAddScore", String.valueOf(0.0));
            unitMap.put("totalPublicDeductScore", String.valueOf(0.0));
            unitMap.put("totaltScore", String.valueOf(0.0));
            unitList.add(unitMap);
        }
        List list1 = new ArrayList<List<Map<String, Object>>>();//存储各个工作项下各个公安处各项得分集合（包含 业务100分制得分、业务折算权重后得分、具体事项）
        //业务权重合计得分
        for (Map<String, Object> map : weigthsList) {
            String workName = String.valueOf(map.get("value"));
            String workNameType = String.valueOf(map.get("workNameType"));
            //用于判断该工作项是否为得分制工作项
			int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName,"1");
            List list2 = new ArrayList<Map<String, Object>>();//存储各个公安处各项信息（工作项名称，工作项所属类型，单位名称、id，各个分值，具体事项等）
            for (Map<String, String> unitMap : unitList) {
				Double weightScore = (Double) map.get("weight");
                String unitId = unitMap.get("unitId");
                Map<String, Object> tempUnitInfoMap = new HashMap<>();
                //当前用户为自评人时fillPersonId不为空，根据fillPersonId查询所选择的考评项；fillPersonId为空根据unitId处id查询所有所选择的考评项
                List<ExamWorkflowDatas> workflowDatasList = examWorkflowDatasDao.getMapByOidWFIdWorkName(workflowId, unitId, workName,fillPersonId);
                StringBuffer specificItem = new StringBuffer("业务部分详情：无扣分事项。");//业务部分加、扣分事项
				StringBuffer publiceAddspecificItem = new StringBuffer("公共加分：无加分事项。");//公共加分事项
				StringBuffer publiceDecspecificItem = new StringBuffer("公共扣分：无扣分事项。");//公共扣分事项
                Double publicAddScore = 0.0;//记录公共加分分值
                Double publicDeductScore = 0.0;//记录公共扣分分值
                if (workflowDatasList != null && workflowDatasList.size() > 0) {
                    specificItem = new StringBuffer("业务部分详情：<br>");
					if("33".equals(workName)||"32".equals(workName)){
						//其他公共加扣分 || 公共加扣分
						specificItem = new StringBuffer();
						publiceAddspecificItem = new StringBuffer();
						publiceDecspecificItem = new StringBuffer();
					}
                    Double hundredScore = 100.0;
                    if("3".equals(workNameType) || "33".equals(workName)||"32".equals(workName)|| isAddScoreItem>0){
                    	//公共加扣分、得分制工作项 初始分值为0
                    	hundredScore = 0.0;
					}
                    //加扣分事项前序号
                    int i = 1;
                    //公共加扣分事项前序号
					int addI = 1;
					int decI = 1;
                    for (ExamWorkflowDatas examWorkflowDatas : workflowDatasList) {
						//公共加扣分项
                        if ("32".equals(examWorkflowDatas.getWorkName())) {
                        	//getValueType == -1 扣分事项
                            if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
								if(examWorkflowDatas.getItems()!=null){
									publiceDecspecificItem.append(decI+"、"+examWorkflowDatas.getItems() + "<br>");
									decI++;
								}
                            	if(examWorkflowDatas.getValue()!=null){
									publicDeductScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									publicDeductScore += 0.0;
								}

                            }
							//getValueType == 1 加分事项
                            if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
								if(examWorkflowDatas.getItems()!=null){
									publiceAddspecificItem.append(addI+"、"+examWorkflowDatas.getItems() + "<br>");
									addI++;
								}
                            	if(examWorkflowDatas.getValue()!=null){
									publicAddScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									publicAddScore += 0.0;
								}

                            }
                        }
                        /*else {*/ //局考处生成表格显示公共加扣分具体事项 12.3
                        	if(examWorkflowDatas.getItems()!=null){
								specificItem.append(i+"、"+examWorkflowDatas.getItems() + "<br>");
								i++;
							}
                        	if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
								if(examWorkflowDatas.getValue()!=null){
									hundredScore -= Double.valueOf(examWorkflowDatas.getValue());
								}else{
									hundredScore -= 0.0;
								}
                            }
                            if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
								if(examWorkflowDatas.getValue()!=null){
									hundredScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									hundredScore += 0.0;
								}
                            }
                        /*}*/
                    }
                    Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore")) + publicAddScore;//总公共加分
                    Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore")) + publicDeductScore;//总公共扣分
                    /*if (!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
						//生成的表格，重点工作是直接按照“权重分”分值往下扣，没有100分这种，（7 反恐防爆、29 党建工作、18 信息化建设这三个）
						/*if("7".equals(map.get("value"))||"18".equals(map.get("value"))|| "29".equals(map.get("value"))){*/
						if("1".equals(map.get("workNameType"))){
						    if("2".equals(workName)){
						        //高铁安保,重点工作，但采用常规业务算法算分
								if(hundredScore<0){
									//百分制得分 小于 0;得分不能小于0
									weightScore = 0.0;
									hundredScore = 0.0;
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
								}else{
									weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
									//weightScore = hundredScore * (weightScore / 100);
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
									if(isAddScoreItem>0){
										//得分制工作项
										weightScore = hundredScore;
										tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
										tempUnitInfoMap.put("weightScore", weightScore);//权重分
									}
								}
                               /* weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
                                //weightScore = hundredScore * (weightScore / 100);
                                tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                                tempUnitInfoMap.put("weightScore", weightScore);//权重分*/
                            }else{
                                tempUnitInfoMap.put("hundredScore", "-");//百分制得分
								if(isAddScoreItem>0){
									//得分制工作项
									if(hundredScore<0){
										hundredScore = 0.0;
									}
									weightScore = hundredScore;
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
								}else{
									if((100-hundredScore)>weightScore){
										tempUnitInfoMap.put("weightScore", 0.0);//权重分
										weightScore = 0.0;
									}else{
										tempUnitInfoMap.put("weightScore", weightScore-(100-hundredScore));//权重分
										weightScore = weightScore-(100-hundredScore);
									}
								}

                            }
						}
						else{
							//2.23 权重分设置问题，应当扣完为止，不出现负数
							if(!workName.equals("32")&&hundredScore<0){
								//百分制得分 小于 0;得分不能小于0
								weightScore = 0.0;
								hundredScore = 0.0;
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
							}else{
								weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
								//weightScore = hundredScore * (weightScore / 100);
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
								if(isAddScoreItem>0){
									//得分制工作项
									weightScore = hundredScore;
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
								}
							}
						}
						Double totalWeightScore;
						if(!workName.equals("32")){
							totalWeightScore =  (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore")) * 100)/100 + weightScore;//业务权重合计得分
						}else{
							totalWeightScore = (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore")) *100)/100 + 0.0;
						}
						tempUnitInfoMap.put("workName", map.get("label"));//工作项名称
                        tempUnitInfoMap.put("workNameValue", map.get("value"));//工作项value
                        tempUnitInfoMap.put("workNameType", map.get("workNameType"));//工作项类别  0 常规 1 重点 3 公共加扣分
                        tempUnitInfoMap.put("weight", map.get("weight"));//权重
                        tempUnitInfoMap.put("unitName", unitMap.get("unitName"));//单位名称
                        tempUnitInfoMap.put("unitId", unitMap.get("unitId"));//单位id
						tempUnitInfoMap.put("specificItem", specificItem.toString());//具体项
					if("32".equals(workName)){
						tempUnitInfoMap.put("specificItem", "公共加分：<br>"+publiceAddspecificItem.toString()+"<br>公共扣分：<br>"+publiceDecspecificItem.toString());//具体项
					}
                        unitMap.put("totalWeightScore", String.valueOf((double) Math.round(Double.valueOf(totalWeightScore.toString())*100)/100));//业务权重合计得分
                    /*}*/
                    unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
                    unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分
                } else {
                    Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore")) + publicAddScore;//总公共加分
                    Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore")) + publicDeductScore;//总公共扣分
                    /*if (!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
                        Double totalWeightScore ;//业务权重合计得分
						if(!"32".equals(workName)){
						 if("33".equals(workName)){
								totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) +0.0;
						 }else{
						 	if(isAddScoreItem>0){
								 totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) + 0.0;
						 	}else{
						 		totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) + weightScore;
						 	}
						 }
						} else{
							totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) +0.0;
						}
                        tempUnitInfoMap.put("workName", map.get("label"));
						tempUnitInfoMap.put("workNameValue", map.get("value"));//工作项value
						tempUnitInfoMap.put("workNameType", map.get("workNameType"));//工作项类别 0 常规 1 重点 3公共加扣分表
                        tempUnitInfoMap.put("weight", map.get("weight"));
                        tempUnitInfoMap.put("unitName", unitMap.get("unitName"));
                        tempUnitInfoMap.put("unitId", unitMap.get("unitId"));
                        if("33".equals(workName)||"32".equals(workName)){
							tempUnitInfoMap.put("hundredScore", 0.0);
							tempUnitInfoMap.put("weightScore", 0.0);
						}else if("1".equals(map.get("workNameType"))){
                        	//重点工作
							if(isAddScoreItem>0){
								if("2".equals(workName)){
									//高铁安保
									tempUnitInfoMap.put("hundredScore", "0");//百分制得分
								}else{
									tempUnitInfoMap.put("hundredScore", "-");//百分制得分
								}
								tempUnitInfoMap.put("weightScore", 0.0);
							}else{
								if("2".equals(workName)){
									//高铁安保
									tempUnitInfoMap.put("hundredScore", "100");//百分制得分
								}else{
									tempUnitInfoMap.put("hundredScore", "-");//百分制得分
								}
								tempUnitInfoMap.put("weightScore", weightScore);
							}

                        }else{
                        	//常规业务
							//得分制工作项
							if(isAddScoreItem>0){
								tempUnitInfoMap.put("hundredScore", 0.0);//百分制得分
								tempUnitInfoMap.put("weightScore", 0.0);
							}else{
								tempUnitInfoMap.put("hundredScore", 100.0);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);
							}
                        }
                        tempUnitInfoMap.put("specificItem", specificItem.toString());
					if("32".equals(workName)){
						tempUnitInfoMap.put("specificItem", publiceAddspecificItem.toString()+"<br>"+publiceDecspecificItem.toString());//具体项
					}
                        unitMap.put("totalWeightScore", totalWeightScore.toString());//业务权重合计得分
                   /* }*/
                    unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
                    unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分

                }
                Double totaltScore = (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore"))* 100)/100 + Double.valueOf(unitMap.get("totalPublicAddScore")) - Double.valueOf(unitMap.get("totalPublicDeductScore"));//总得分
                unitMap.put("totaltScore", String.valueOf(keepTwoDecimal(totaltScore)));//总得分
				//if (!"32".equals(map.get("value"))) { //局考处生成表格显示公共加扣分具体事项 12.3
                	list2.add(tempUnitInfoMap);
				//}
            }
            /*if (list2 != null && list2.size() > 0 &&!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
            if (list2 != null && list2.size() > 0) {
                list1.add(list2);
            }
        }
        resultMap.put("unitList", unitList);//单位信息用于显示显示顶部单位信息
        resultMap.put("weigthsList", weigthsList);//工作项集合
        resultMap.put("conventionScoreList", list1);//各个工作项、单位对应分值集合
        return resultMap;
    }

	/**
	 * 局考处 最终结果详情（未使用）
	 */
	public Map<String, Object> jkcAllPublicDetail(String workflowId, String objId,Integer status) {
		User user = UserUtils.getUser();
		List<String> objIds = new ArrayList<>();//各个公安处单位id集合
		List<String> unitNames = new ArrayList<>();//各个公安处名称集合
		if ("34".equals(objId)) {//当前用户为南宁处账号或当前用户为南宁处单位下的用户
			objIds.add(objId);//南宁处
			unitNames.add("南宁公安处");
		} else if ("95".equals(objId)) {//当前用户为柳州处账号或当前用户为柳州处单位下的用户
			objIds.add("95");//柳州处
			unitNames.add("柳州公安处");
		} else if ("156".equals(objId)) {//当前用户为北海处账号或当前用户为北海处单位下的用户
			objIds.add("156");//北海处
			unitNames.add("北海公安处");
		}
		Map<String, Object> resultMap = new HashMap<>();//返回结果集
		//查询出相关的考评数据   根据 obj_id，workflow_id，is_selected
		//List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.getListByOidWFId(objIds,workflowId);
		//从工作权重表中取出相关工作项及权重
		List<Map<String, Object>> weigthsList = examWeightsMainDao.findWorkNameList();
		String fillPersonId = null;
		//单位各处信息（单位名称，单位id，业务权重合计得分，总公共扣分，总公共加分，总得分）集合
		List<Map<String, String>> unitList = new ArrayList<>();
		for (int i = 0; i < unitNames.size(); i++) {
			Map<String, String> unitMap = new HashMap<>();
			unitMap.put("unitName", unitNames.get(i));
			unitMap.put("unitId", objIds.get(i));
			unitMap.put("totalWeightScore", String.valueOf(0.0));
			unitMap.put("totalPublicAddScore", String.valueOf(0.0));
			unitMap.put("totalPublicDeductScore", String.valueOf(0.0));
			unitMap.put("totaltScore", String.valueOf(0.0));
			unitList.add(unitMap);
		}
		List list1 = new ArrayList<List<Map<String, Object>>>();//公安处各项得分集合（包含 业务100分制得分、业务折算权重后得分、具体事项）
		//业务权重合计得分
		for (Map<String, Object> map : weigthsList) {
			String workName = String.valueOf(map.get("value"));
			String workNameType = String.valueOf(map.get("workNameType"));
			//用于判断该工作项是否为得分制工作项
			int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName,"1");
			List list2 = new ArrayList<Map<String, Object>>();
			for (Map<String, String> unitMap : unitList) {
				Double weightScore = (Double) map.get("weight");
				String unitId = unitMap.get("unitId");
				Map<String, Object> tempUnitInfoMap = new HashMap<>();
				List<ExamWorkflowDatas> workflowDatasList = examWorkflowDatasDao.getMapByOidWFIdWorkName(workflowId, unitId, workName,fillPersonId);
				StringBuffer specificItem = new StringBuffer("业务部分详情：无扣分事项。");
				StringBuffer publiceAddspecificItem = new StringBuffer("公共加分：无加分事项。");
				StringBuffer publiceDecspecificItem = new StringBuffer("公共扣分：无扣分事项。");
				Double publicAddScore = 0.0;
				Double publicDeductScore = 0.0;
				if (workflowDatasList != null && workflowDatasList.size() > 0) {
					specificItem = new StringBuffer("业务部分详情：<br>");
					if("33".equals(workName)||"32".equals(workName)){
						specificItem = new StringBuffer();
						publiceAddspecificItem = new StringBuffer();
						publiceDecspecificItem = new StringBuffer();
					}
					Double hundredScore = 100.0;
					if("3".equals(workNameType) || "33".equals(workName)||"32".equals(workName)|| isAddScoreItem>0){
						hundredScore = 0.0;
					}
					int i = 1;
					int addI = 1;
					int decI = 1;
					for (ExamWorkflowDatas examWorkflowDatas : workflowDatasList) {
						if ("32".equals(examWorkflowDatas.getWorkName())) {//公共加扣分项
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
								if(examWorkflowDatas.getItems()!=null){
									publiceDecspecificItem.append(decI+"、"+examWorkflowDatas.getItems() + "<br>");
									decI++;
								}
								if(examWorkflowDatas.getValue()!=null){
									publicDeductScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									publicDeductScore += 0.0;
								}

							}
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
								if(examWorkflowDatas.getItems()!=null){
									publiceAddspecificItem.append(addI+"、"+examWorkflowDatas.getItems() + "<br>");
									addI++;
								}
								if(examWorkflowDatas.getValue()!=null){
									publicAddScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									publicAddScore += 0.0;
								}

							}
						}
						/*else {*/ //局考处生成表格显示公共加扣分具体事项 12.3
						if(examWorkflowDatas.getItems()!=null){
							specificItem.append(i+"、"+examWorkflowDatas.getItems() + "<br>");
							i++;
						}
						if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
							if(examWorkflowDatas.getValue()!=null){
								hundredScore -= Double.valueOf(examWorkflowDatas.getValue());
							}else{
								hundredScore -= 0.0;
							}
						}
						if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
							if(examWorkflowDatas.getValue()!=null){
								hundredScore += Double.valueOf(examWorkflowDatas.getValue());
							}else{
								hundredScore += 0.0;
							}
						}
						/*}*/
					}
					Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore")) + publicAddScore;//总公共加分
					Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore")) + publicDeductScore;//总公共扣分
					/*if (!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
					//生成的表格，重点工作是直接按照“权重分”分值往下扣，没有100分这种，（7 反恐防爆、29 党建工作、18 信息化建设这三个）
					/*if("7".equals(map.get("value"))||"18".equals(map.get("value"))|| "29".equals(map.get("value"))){*/
					if("1".equals(map.get("workNameType"))){
						if("2".equals(workName)){
							//高铁安保,重点工作，但采用常规业务算法算分
							if(hundredScore<0){
								//百分制得分 小于 0;得分不能小于0
								weightScore = 0.0;
								hundredScore = 0.0;
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
							}else{
								weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
								//weightScore = hundredScore * (weightScore / 100);
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
								if(isAddScoreItem>0){
									//得分制工作项
									weightScore = hundredScore;
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
								}
							}
                               /* weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
                                //weightScore = hundredScore * (weightScore / 100);
                                tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
                                tempUnitInfoMap.put("weightScore", weightScore);//权重分*/
						}else{
							tempUnitInfoMap.put("hundredScore", "-");//百分制得分
							if(isAddScoreItem>0){
								//得分制工作项
								if(hundredScore<0){
									hundredScore = 0.0;
								}
								weightScore = hundredScore;
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
							}else{
								if((100-hundredScore)>weightScore){
									tempUnitInfoMap.put("weightScore", 0.0);//权重分
									weightScore = 0.0;
								}else{
									tempUnitInfoMap.put("weightScore", weightScore-(100-hundredScore));//权重分
									weightScore = weightScore-(100-hundredScore);
								}
							}

						}
					}else{
						//2.23 权重分设置问题，应当扣完为止，不出现负数
						if(!workName.equals("32")&&hundredScore<0){
							//百分制得分 小于 0;得分不能小于0
							weightScore = 0.0;
							hundredScore = 0.0;
							tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
							tempUnitInfoMap.put("weightScore", weightScore);//权重分
						}else{
							weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
							//weightScore = hundredScore * (weightScore / 100);
							tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
							tempUnitInfoMap.put("weightScore", weightScore);//权重分
							if(isAddScoreItem>0){
								//得分制工作项
								weightScore = hundredScore;
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
							}
						}
					}
					Double totalWeightScore;
					if(!workName.equals("32")){
						totalWeightScore =  (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore")) * 100)/100 + weightScore;//业务权重合计得分
					}else{
						totalWeightScore = (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore")) *100)/100 + 0.0;
					}
					tempUnitInfoMap.put("workName", map.get("label"));//工作项名称
					tempUnitInfoMap.put("workNameValue", map.get("value"));//工作项value
					tempUnitInfoMap.put("workNameType", map.get("workNameType"));//工作项类别  0 常规 1 重点 3 公共加扣分
					tempUnitInfoMap.put("weight", map.get("weight"));//权重
					tempUnitInfoMap.put("unitName", unitMap.get("unitName"));//单位名称
					tempUnitInfoMap.put("unitId", unitMap.get("unitId"));//单位id
					tempUnitInfoMap.put("specificItem", specificItem.toString());//具体项
					if("32".equals(workName)){
						tempUnitInfoMap.put("specificItem", "公共加分：<br>"+publiceAddspecificItem.toString()+"<br>公共扣分：<br>"+publiceDecspecificItem.toString());//具体项
					}
					unitMap.put("totalWeightScore", String.valueOf((double) Math.round(Double.valueOf(totalWeightScore.toString())*100)/100));//业务权重合计得分
					/*}*/
					unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
					unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分
				} else {
					Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore")) + publicAddScore;//总公共加分
					Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore")) + publicDeductScore;//总公共扣分
					/*if (!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
					Double totalWeightScore ;//业务权重合计得分
					if(!"32".equals(workName)){
						if("33".equals(workName)){
							totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) +0.0;
						}else{
							if(isAddScoreItem>0){
								totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) + 0.0;
							}else{
								totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) + weightScore;
							}
						}
					} else{
						totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) +0.0;
					}
					tempUnitInfoMap.put("workName", map.get("label"));
					tempUnitInfoMap.put("workNameValue", map.get("value"));//工作项value
					tempUnitInfoMap.put("workNameType", map.get("workNameType"));//工作项类别 0 常规 1 重点 3公共加扣分表
					tempUnitInfoMap.put("weight", map.get("weight"));
					tempUnitInfoMap.put("unitName", unitMap.get("unitName"));
					tempUnitInfoMap.put("unitId", unitMap.get("unitId"));
					if("33".equals(workName)||"32".equals(workName)){
						tempUnitInfoMap.put("hundredScore", 0.0);
						tempUnitInfoMap.put("weightScore", 0.0);
					}else if("1".equals(map.get("workNameType"))){
						//重点工作
						if(isAddScoreItem>0){
							if("2".equals(workName)){
								//高铁安保
								tempUnitInfoMap.put("hundredScore", "0");//百分制得分
							}else{
								tempUnitInfoMap.put("hundredScore", "-");//百分制得分
							}
							tempUnitInfoMap.put("weightScore", 0.0);
						}else{
							if("2".equals(workName)){
								//高铁安保
								tempUnitInfoMap.put("hundredScore", "100");//百分制得分
							}else{
								tempUnitInfoMap.put("hundredScore", "-");//百分制得分
							}
							tempUnitInfoMap.put("weightScore", weightScore);
						}

					}else{
						//常规业务
						//得分制工作项
						if(isAddScoreItem>0){
							tempUnitInfoMap.put("hundredScore", 0.0);//百分制得分
							tempUnitInfoMap.put("weightScore", 0.0);
						}else{
							tempUnitInfoMap.put("hundredScore", 100.0);//百分制得分
							tempUnitInfoMap.put("weightScore", weightScore);
						}
					}
					tempUnitInfoMap.put("specificItem", specificItem.toString());
					if("32".equals(workName)){
						tempUnitInfoMap.put("specificItem", publiceAddspecificItem.toString()+"<br>"+publiceDecspecificItem.toString());//具体项
					}
					unitMap.put("totalWeightScore", totalWeightScore.toString());//业务权重合计得分
					/* }*/
					unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
					unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分

				}
				Double totaltScore = (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore"))* 100)/100 + Double.valueOf(unitMap.get("totalPublicAddScore")) - Double.valueOf(unitMap.get("totalPublicDeductScore"));//总得分
				unitMap.put("totaltScore", totaltScore.toString());//总得分
				//if (!"32".equals(map.get("value"))) { //局考处生成表格显示公共加扣分具体事项 12.3
				list2.add(tempUnitInfoMap);
				//}
			}
			/*if (list2 != null && list2.size() > 0 &&!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
			if (list2 != null && list2.size() > 0) {
				list1.add(list2);
			}
		}
		resultMap.put("unitList", unitList);
		resultMap.put("weigthsList", weigthsList);
		resultMap.put("conventionScoreList", list1);
		return resultMap;
	}

	/**
	 * 获取局考处 各处整体情况 系统公示
	 * @param fillPersonId 自评人id
	 */
	public Map<String, Object> getPublicBetaScoreList(String workflowId,String fillPersonId) {
		User user = UserUtils.get(fillPersonId);
		List<String> objIds = new ArrayList<>();//各个公安处单位id集合
		List<String> unitNames = new ArrayList<>();//各个公安处名称集合
		if ("34".equals(user.getOffice().getId()) || user.getOffice().getParentIds().indexOf(",34") != -1) {//当前用户为南宁处账号或当前用户为南宁处单位下的用户
			objIds.add("34");//南宁处
			unitNames.add("南宁公安处");
		} else if ("95".equals(user.getOffice().getId()) || user.getOffice().getParentIds().indexOf(",95") != -1) {//当前用户为柳州处账号或当前用户为柳州处单位下的用户
			objIds.add("95");//柳州处
			unitNames.add("柳州公安处");
		} else if ("156".equals(user.getOffice().getId()) || user.getOffice().getParentIds().indexOf(",156") != -1) {//当前用户为北海处账号或当前用户为北海处单位下的用户
			objIds.add("156");//北海处
			unitNames.add("北海公安处");
		} else {
			objIds.add("34");//南宁处
			unitNames.add("南宁公安处");
			objIds.add("95");//柳州处
			unitNames.add("柳州公安处");
			objIds.add("156");//北海处
			unitNames.add("北海公安处");
		}
		Map<String, Object> resultMap = new HashMap<>();//返回结果集
		//查询出相关的考评数据   根据 obj_id，workflow_id，is_selected
		//List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.getListByOidWFId(objIds,workflowId);
		//从工作权重表中取出相关工作项及权重
		List<Map<String, Object>> weigthsList;
		//流程状态自评且当前用户为填报人
		weigthsList = examWeightsMainDao.findWorkNameByFillPersonId(fillPersonId,workflowId,"1");
		fillPersonId = user.getId();
		//单位各处信息（单位名称，单位id，业务权重合计得分，总公共扣分，总公共加分，总得分）集合
		List<Map<String, String>> unitList = new ArrayList<>();

		for (int i = 0; i < unitNames.size(); i++) {
			Map<String, String> unitMap = new HashMap<>();
			unitMap.put("unitName", unitNames.get(i));
			unitMap.put("unitId", objIds.get(i));
			unitMap.put("totalWeightScore", String.valueOf(0.0));
			unitMap.put("totalPublicAddScore", String.valueOf(0.0));
			unitMap.put("totalPublicDeductScore", String.valueOf(0.0));
			unitMap.put("totaltScore", String.valueOf(0.0));
			unitList.add(unitMap);
		}
		List list1 = new ArrayList<List<Map<String, Object>>>();//公安处各项得分集合（包含 业务100分制得分、业务折算权重后得分、具体事项）
		//业务权重合计得分
		for (Map<String, Object> map : weigthsList) {
			String workName = String.valueOf(map.get("value"));
			String workNameType = String.valueOf(map.get("workNameType"));
			//用于判断该工作项是否为得分制工作项
			int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName,"1");
			List list2 = new ArrayList<Map<String, Object>>();
			for (Map<String, String> unitMap : unitList) {
				Double weightScore = (Double) map.get("weight");
				String unitId = unitMap.get("unitId");
				Map<String, Object> tempUnitInfoMap = new HashMap<>();
				List<ExamWorkflowDatas> workflowDatasList = examWorkflowDatasDao.getMapByOidWFIdWorkName(workflowId, unitId, workName,fillPersonId);
				StringBuffer specificItem = new StringBuffer("业务部分详情：无扣分事项。");
				Double publicAddScore = 0.0;
				Double publicDeductScore = 0.0;
				if (workflowDatasList != null && workflowDatasList.size() > 0) {
					specificItem = new StringBuffer("业务部分详情：<br>");
					Double hundredScore = 100.0;
					if("3".equals(workNameType) || "33".equals(workName)||"32".equals(workName)|| isAddScoreItem>0){
						hundredScore = 0.0;
					}
					int i = 1;
					for (ExamWorkflowDatas examWorkflowDatas : workflowDatasList) {
						if ("32".equals(examWorkflowDatas.getWorkName())) {//公共加扣分项
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
								try {
									publicDeductScore += Double.valueOf(examWorkflowDatas.getValue());
								}catch (Exception e){
									publicDeductScore += 0.0;
									logger.error(getExceptionInfo(e));
									e.printStackTrace();
								}
							}
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
								try {
									publicAddScore += Double.valueOf(examWorkflowDatas.getValue());
								}catch (Exception e){
									publicAddScore += 0.0;
									logger.error(getExceptionInfo(e));
									e.printStackTrace();
								}

							}
						}
						/*else {*/ //局考处生成表格显示公共加扣分具体事项 12.3
						if(examWorkflowDatas.getItems()!=null){
							specificItem.append(i+"."+examWorkflowDatas.getItems() + "<br>");
							i++;
						}
						if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
							try {
								hundredScore -= Double.valueOf(examWorkflowDatas.getValue());
							}catch (Exception e){
								hundredScore -= 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
						}
						if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
							try {
								hundredScore += Double.valueOf(examWorkflowDatas.getValue());
							}catch (Exception e){
								hundredScore += 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
						}
						/*}*/
					}
					Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore")) + publicAddScore;//总公共加分
					Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore")) + publicDeductScore;//总公共扣分
					/*if (!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
					//生成的表格，重点工作是直接按照“权重分”分值往下扣，没有100分这种，（7 反恐防爆、29 党建工作、18 信息化建设这三个）
					/*if("7".equals(map.get("value"))||"18".equals(map.get("value"))|| "29".equals(map.get("value"))){*/
					if("1".equals(map.get("workNameType"))){
						if("2".equals(workName)){
							//高铁安保
							tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
							if(hundredScore<0){
								//百分制得分 小于 0;得分不能小于0
								weightScore = 0.0;
								hundredScore = 0.0;
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
							}else{
								weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
								//weightScore = hundredScore * (weightScore / 100);
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
								if(isAddScoreItem>0){
									//得分制工作项
									weightScore = hundredScore;
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
								}
							}
						}else{
							tempUnitInfoMap.put("hundredScore", "-");//百分制得分
							if(isAddScoreItem>0){
								//得分制工作项
								if(hundredScore<0){
									hundredScore = 0.0;
								}
								weightScore = hundredScore;
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
							}else{
								if((100-hundredScore)>weightScore){
									tempUnitInfoMap.put("weightScore", 0.0);//权重分
									weightScore = 0.0;
								}else{
									tempUnitInfoMap.put("weightScore", weightScore-(100-hundredScore));//权重分
									weightScore = weightScore-(100-hundredScore);
								}
							}
						}
					}else{
						if(!workName.equals("32")&&hundredScore<0){
							//百分制得分 小于 0;得分不能小于0
							weightScore = 0.0;
							hundredScore = 0.0;
							tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
							tempUnitInfoMap.put("weightScore", weightScore);//权重分
						}else{
							weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
							//weightScore = hundredScore * (weightScore / 100);
							tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
							tempUnitInfoMap.put("weightScore", weightScore);//权重分
							if(isAddScoreItem>0){
								//得分制工作项
								weightScore = hundredScore;
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
							}
						}
					}
					Double totalWeightScore;
					if(!workName.equals("32")){
						totalWeightScore =  (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore")) * 100)/100 + weightScore;//业务权重合计得分
					}else{
						totalWeightScore = (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore")) *100)/100 + 0.0;
					}
					tempUnitInfoMap.put("workName", map.get("label"));//工作项名称
					tempUnitInfoMap.put("workNameValue", map.get("value"));//工作项value
					tempUnitInfoMap.put("workNameType", map.get("workNameType"));//工作项类别  0 常规 1 重点 3 公共加扣分
					tempUnitInfoMap.put("weight", map.get("weight"));//权重
					tempUnitInfoMap.put("unitName", unitMap.get("unitName"));//单位名称
					tempUnitInfoMap.put("unitId", unitMap.get("unitId"));//单位id
					tempUnitInfoMap.put("specificItem", specificItem.toString());//具体项
					unitMap.put("totalWeightScore", String.valueOf((double) Math.round(Double.valueOf(totalWeightScore.toString())*100)/100));//业务权重合计得分
					/*}*/
					unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
					unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分
				} else {
					Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore")) + publicAddScore;//总公共加分
					Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore")) + publicDeductScore;//总公共扣分
					/*if (!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
					Double totalWeightScore ;//业务权重合计得分
					if(!"32".equals(workName)){
						if("33".equals(workName)){
							totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) +0.0;
						}else{
							if(isAddScoreItem>0){
								totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) + 0.0;
							}else{
								totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) + weightScore;
							}
						}
					} else{
						totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore")) +0.0;
					}
					tempUnitInfoMap.put("workName", map.get("label"));
					tempUnitInfoMap.put("workNameValue", map.get("value"));//工作项value
					tempUnitInfoMap.put("workNameType", map.get("workNameType"));//工作项类别 0 常规 1 重点 3公共加扣分表
					tempUnitInfoMap.put("weight", map.get("weight"));
					tempUnitInfoMap.put("unitName", unitMap.get("unitName"));
					tempUnitInfoMap.put("unitId", unitMap.get("unitId"));
					if("33".equals(workName)||"32".equals(workName)){
						tempUnitInfoMap.put("hundredScore", 0.0);
						tempUnitInfoMap.put("weightScore", 0.0);
					}else if("1".equals(map.get("workNameType"))){
						//重点工作
						if(isAddScoreItem>0){
							if("2".equals(workName)){
								//高铁安保
								tempUnitInfoMap.put("hundredScore", "0");//百分制得分
							}else{
								tempUnitInfoMap.put("hundredScore", "-");//百分制得分
							}
							tempUnitInfoMap.put("weightScore", 0.0);
						}else{
							if("2".equals(workName)){
								//高铁安保
								tempUnitInfoMap.put("hundredScore", "100");//百分制得分
							}else{
								tempUnitInfoMap.put("hundredScore", "-");//百分制得分
							}
							tempUnitInfoMap.put("weightScore", weightScore);
						}
					}else{
						//得分制工作项
						if(isAddScoreItem>0){
							tempUnitInfoMap.put("hundredScore", 0.0);//百分制得分
							tempUnitInfoMap.put("weightScore", 0.0);
						}else{
							tempUnitInfoMap.put("hundredScore", 100.0);//百分制得分
							tempUnitInfoMap.put("weightScore", weightScore);
						}
					}
					tempUnitInfoMap.put("specificItem", specificItem.toString());
					unitMap.put("totalWeightScore", totalWeightScore.toString());//业务权重合计得分
					/* }*/
					unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
					unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分

				}
				Double totaltScore = (double) Math.round(Double.valueOf(unitMap.get("totalWeightScore"))* 100)/100 + Double.valueOf(unitMap.get("totalPublicAddScore")) - Double.valueOf(unitMap.get("totalPublicDeductScore"));//总得分
				unitMap.put("totaltScore", totaltScore.toString());//总得分
				//if (!"32".equals(map.get("value"))) { //局考处生成表格显示公共加扣分具体事项 12.3
				list2.add(tempUnitInfoMap);
				//}
			}
			/*if (list2 != null && list2.size() > 0 &&!"32".equals(map.get("value"))) {*/ //局考处生成表格显示公共加扣分具体事项 12.3
			if (list2 != null && list2.size() > 0) {
				list1.add(list2);
			}
		}
		resultMap.put("unitList", unitList);
		resultMap.put("weigthsList", weigthsList);
		resultMap.put("conventionScoreList", list1);
		return resultMap;
	}


	/**
	 * 生成表格-局考局机关/处考处机关
	 * @param workflowId
	 * @param userId
	 * @param status
	 */
	public Map<String, Object> getIngJuJiGuanScoreList(String workflowId,String userId, Integer status) {
		ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
		User user;
		if(StringUtils.isBlank(userId)){
			user = UserUtils.getUser();
		}else{
			user = UserUtils.get(userId);
		}
		String examType = examWorkflow.getExamType();
		Map<String,Object> resultMap = new HashMap<>();
		/*判断按照权重制得分还是非权重制得分*/
		ExamWeights examWeights = new ExamWeights();
		examWeights.setKpType(examType);
		examWeights.setKpChu(null);
		if("4".equals(examType)){
			String baseFillPersonId = null;
			List<Map<String,String>> kpUnitList =  examWorkflowDatasDao.getExamUnitList(workflowId);
			if(kpUnitList!=null&& kpUnitList.size()>0) {
				baseFillPersonId = kpUnitList.get(0).get("fillPersonId");
			}
			//处考处机关判断各处
			User baseFillPersonUser = null;
			if(StringUtils.isNotBlank(baseFillPersonId)){
				baseFillPersonUser = UserUtils.get(baseFillPersonId)==null?user : UserUtils.get(baseFillPersonId);
			}
			String companyId = baseFillPersonUser.getCompany().getId();
			if(!companyId.equals("34") && !companyId.equals("95") && !companyId.equals("156")){
				if(baseFillPersonUser.getOffice().getParentIds().contains(",34,")){
					companyId = "34";
				}
				if(baseFillPersonUser.getOffice().getParentIds().contains(",95,")){
					companyId = "95";
				}
				if(baseFillPersonUser.getOffice().getParentIds().contains(",156,")){
					companyId = "156";
				}
			}
			examWeights.setKpChu(companyId);
		}
		examWeights.setDepartmentId(null);
		examWeights = examWeightsDao.getWeightByUnit(examWeights);
		if(examWeights == null){
			//非权重制得分
			List<Map<String,Object>> unitMapList;
			int count = examWorkflowDatasDao.findCountByWorkflowIdFillPersonId(workflowId, user.getId());
			//当前用户为绩效办用户，查看所有信息
			if(count>0 && !user.getName().contains("绩效办")){
				//当前用户为填报人
				unitMapList = new ArrayList<>();
				Map<String,Object> map = new HashMap<>();
				map.put("objId",user.getId());
				map.put("objName",user.getName());
				unitMapList.add(map);
			}else{
				//查询出参加考评的各个部门
				unitMapList = examWorkflowDatasDao.findJJGObjIdAndName(workflowId);
			}
			for(Map<String,Object> tempMap : unitMapList){
				List<String> objId = new ArrayList<String>();
				objId.add(tempMap.get("objId").toString());
				List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.getListByOidWFId(objId, workflowId);
				StringBuffer deductScoreItems ;
				StringBuffer addScoreItems ;
				double deductScore = 0.0;//扣分
				double addScore = 0.0;//加分
				String basicScore = DictUtils.getDictValue(examType,"exam_basicScore","100");
				double totalScore = 100.0;//总得分
				try {
					totalScore = Double.valueOf(basicScore);
				}catch (Exception e){
					logger.error(getExceptionInfo(e));
					e.printStackTrace();
					totalScore = 100.0;
				}
				if(examWorkflowDatasList!=null && examWorkflowDatasList.size()>0){
					deductScoreItems =  new StringBuffer();
					addScoreItems =  new StringBuffer();
					int addI = 1 ;
					int deductI = 1 ;
					for(ExamWorkflowDatas examWorkflowDatas :examWorkflowDatasList){
						if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
							if(examWorkflowDatas.getItems()!=null){
								deductScoreItems.append(deductI+"."+examWorkflowDatas.getItems()+"<br>");
								deductI++;
							}
							//deductScore += Double.valueOf(examWorkflowDatas.getValue());
							try {
								deductScore += Double.valueOf(examWorkflowDatas.getValue());
							}catch (Exception e){
								deductScore += 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
						}
						if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
							if(examWorkflowDatas.getItems()!=null){
								addScoreItems.append(addI+"."+examWorkflowDatas.getItems()+"<br>");
								addI++;
							}
						/*if(examWorkflowDatas.getValue()!=null){
							addScore += Double.valueOf(examWorkflowDatas.getValue());
						}else{
							addScore += 0.0;
						}*/
							try {
								addScore += Double.valueOf(examWorkflowDatas.getValue());
							}catch (Exception e){
								addScore += 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}

						}
					}
					tempMap.put("totalScore",totalScore+addScore-deductScore);//总得分
					tempMap.put("addScore",addScore);//加分
					tempMap.put("deductScore",deductScore);//扣分
					tempMap.put("deductScoreItems",deductScoreItems);//扣分项目
					tempMap.put("addScoreItems",addScoreItems);//加分项目
				}else{
					deductScoreItems = new StringBuffer("无");
					addScoreItems = new StringBuffer("无");
					tempMap.put("totalScore",totalScore);//总得分
					tempMap.put("addScore",addScore);//加分
					tempMap.put("deductScore",deductScore);//扣分
					tempMap.put("deductScoreItems",deductScoreItems);//扣分项目
					tempMap.put("addScoreItems",addScoreItems);//加分项目
				}
			}
			resultMap.put("unitMapList",unitMapList);
		}
		else {
			//权重制得分
			User fillPersonUser;
			List<Map<String, Object>> unitMapList;
			int count = examWorkflowDatasDao.findCountByWorkflowIdFillPersonId(workflowId, user.getId());
			//当前用户为绩效办用户，查看所有信息
			if (count > 0 && !user.getName().contains("绩效办")) {
				//当前用户为填报人，且不不是绩效办账号
				unitMapList = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();
				map.put("objId", user.getId());
				map.put("objName", user.getName());
				fillPersonUser = user;
				unitMapList.add(map);
			} else {
				//查询出参加考评的各个部门集合
				unitMapList = examWorkflowDatasDao.findJJGObjIdAndName(workflowId);
				try {
					fillPersonUser = UserUtils.get((String) unitMapList.get(0).get("objId"));
				}catch (Exception e){
					logger.error(getExceptionInfo(e));
					e.printStackTrace();
					fillPersonUser = user;
				}
			}
			Double totalScore = 100.0;
			String unitId = null;
			String kpUnitId = null;
			try {
				if("2".equals(examType)){
					//局考局机关
					totalScore = examWeightsMainDao.getJorCJGBaseSumScore(examType,null,null);//基础分
				}
				else{
					//处考处机关
					if("34".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",34,") || "34".equals(fillPersonUser.getOffice().getParentId())){
						//南宁处
						unitId = "34";
						kpUnitId = fillPersonUser.getOffice().getId();
					} else if("95".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",95,") || "95".equals(fillPersonUser.getOffice().getParentId())){
						//柳州处
						unitId = "95";
						kpUnitId = fillPersonUser.getOffice().getId();
					}else if("156".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",156,") || "156".equals(fillPersonUser.getOffice().getParentId())){
						//北海处
						unitId = "156";
						kpUnitId = null;
					}else{
						unitId = null;
						kpUnitId = fillPersonUser.getOffice().getId();
					}
//					totalScore = examWeightsMainDao.getJorCJGBaseSumScore(examType,unitId,kpUnitId);//基础分
					totalScore = examWeightsMainDao.getJorCJGBaseSumScore(examType,unitId,null);//基础分   21年7月26处机关考评目前需求无需根据选择的单位（kpUnitId）去匹配权重
				}
			}catch (Exception e){
				totalScore = 100.0;
				logger.error(getExceptionInfo(e));
				e.printStackTrace();
			}
			Double tempTotalScore = totalScore;
			//遍历单位集合，生成各个部门分值
			for (Map<String, Object> tempMap : unitMapList) {
				totalScore = tempTotalScore;
				String fillPersonId = (String) tempMap.get("objId");//当前单位userId
				Map<String, Object> tempUnitInfoMap = new HashMap<>();
				StringBuffer deductScoreItems;
				StringBuffer addScoreItems;
				double deductScore = 0.0;//扣分
				double addScore = 0.0;//加分
				double publicAddScore = 0.0;//公共加分
				double publicDeductScore = 0.0;//公共扣分
				StringBuffer publiceAddspecificItem = new StringBuffer("公共加分：无加分事项。");
				StringBuffer publiceDecspecificItem = new StringBuffer("公共扣分：无扣分事项。");
				List<Map<String, Object>> workNameList;
				if(examType.equals("4")){
					workNameList = examWeightsMainDao.findWorkNameByChu_FillPersonId(fillPersonId,workflowId,examType,unitId,null);//21年7月26目前需求无需根据选择的单位（kpUnitId）去匹配权重
				}else{
					workNameList = examWeightsMainDao.findWorkNameByFillPersonId(fillPersonId, workflowId,examType);//查询相关工作项及权重集合
				}
				if (workNameList != null && workNameList.size() > 0) {
					deductScoreItems = new StringBuffer("业务扣分详情：<br>");//扣分项目
					addScoreItems = new StringBuffer("业务加分详情：<br>");//加分项目
					int addSpecI = 1;//业务部分加分项索引
					int decSpecI = 1;//业务部分扣分项索引
					int addI = 1;//公共加分项索引
					int decI = 1;//公共扣分索引
					for (Map<String, Object> tempWorkName : workNameList) {
						String workName = String.valueOf(tempWorkName.get("value"));
						String workNameType = String.valueOf(tempWorkName.get("workNameType"));//0 常规  1 重点  3 公共加扣分
						Double weightScore = (Double) tempWorkName.get("weight");//权重分
						//用于判断该工作项是否为得分制工作项
						int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName, examType);
						Double hundredScore = 100.0;
						Double addHundredScore = 0.0;//业务加分
						Double decHundredScore = 0.0;//业务扣分
						publicAddScore = 0.0;//公共加分
						publicDeductScore = 0.0;//公共扣分
						if ("3".equals(workNameType) || "33".equals(workName) || "32".equals(workName) || isAddScoreItem > 0) {
							//如果当前工作项为 公共加扣分或者为得分制工作项，初始分数设置为0.0
							hundredScore = 0.0;
						}
						else {
							hundredScore = 100.0;
						}
						List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.findScoresByPcsIdWorkName(workflowId, fillPersonId, workName);
						if(("33".equals(workName)||"32".equals(workName)||"3".equals(workNameType)) &&(publiceAddspecificItem.toString().contains("无加分事项")||publiceDecspecificItem.toString().contains("无加分事项"))){
							publiceAddspecificItem = new StringBuffer("公共加分详情：<br>");
							publiceDecspecificItem = new StringBuffer("公共扣分详情：<br>");
						}
						for (ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList) {
							Integer valueType;
							try {
								valueType = Integer.valueOf(examWorkflowDatas.getValueType());
							} catch (Exception e) {
								//报错默认扣分
								valueType = -1;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
							if ("32".equals(examWorkflowDatas.getWorkName()) || "3".equals(workNameType)) {//公共加扣分项
								if (valueType!=null && valueType == -1) {
									if(examWorkflowDatas.getItems()!=null){
										publiceDecspecificItem.append(decI+"、"+examWorkflowDatas.getItems() + "<br>");
										decI++;
									}
									if(examWorkflowDatas.getValue()!=null){
										publicDeductScore += Double.valueOf(examWorkflowDatas.getValue());
									}else{
										publicDeductScore += 0.0;
									}

								}
								if (valueType!=null && valueType == 1) {
									if(examWorkflowDatas.getItems()!=null){
										publiceAddspecificItem.append(addI+"、"+examWorkflowDatas.getItems() + "<br>");
										addI++;
									}
									if(examWorkflowDatas.getValue()!=null){
										publicAddScore += Double.valueOf(examWorkflowDatas.getValue());
									}else{
										publicAddScore += 0.0;
									}

								}
							}else{
								if(valueType!=null && valueType == -1 && examWorkflowDatas.getItems()!=null){
									deductScoreItems.append(decSpecI+"、"+examWorkflowDatas.getItems() + "<br>");
									decSpecI++;
								}
								if(valueType!=null && valueType == 1 && examWorkflowDatas.getItems()!=null){
									addScoreItems.append(addSpecI+"、"+examWorkflowDatas.getItems() + "<br>");
									addSpecI++;
								}
								if(valueType!=null && valueType == -1) {
									if(examWorkflowDatas.getValue()!=null){
										hundredScore -= Double.valueOf(examWorkflowDatas.getValue());
										decHundredScore += Double.valueOf(examWorkflowDatas.getValue());
									}else{
										hundredScore -= 0.0;
										decHundredScore += 0.0;
									}
								}
								if(valueType!=null && valueType == 1) {
									if(examWorkflowDatas.getValue()!=null){
										hundredScore += Double.valueOf(examWorkflowDatas.getValue());
										addHundredScore += Double.valueOf(examWorkflowDatas.getValue());
									}else{
										hundredScore += 0.0;
										addHundredScore += 0.0;
									}
								}
							}

						}
						//totalScore 总分、publicAddScore 公共加分、publicDeductScore 公共扣分
						//addHundredScore 业务加分、decHundredScore 业务扣分、hundredScore 业务得分
						//分值处理  0 常规业务  1 重点工作  3 公共加扣分
						Double tempWeight = weightScore;//存储权重满分分值
						if("1".equals(workNameType)){
							if("2".equals(workName)){
								//高铁安保,重点工作，但采用常规业务算法算分
								if(hundredScore<0){
									//百分制得分 小于 0;得分不能小于0
									totalScore -= tempWeight;
									weightScore = 0.0;
									hundredScore = 0.0;
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
								}else{
									weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
									//weightScore = hundredScore * (weightScore / 100);
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
									if(isAddScoreItem>0){
										//得分制工作项
										weightScore = hundredScore;
										tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
										tempUnitInfoMap.put("weightScore", weightScore);//权重分
									}
								}
								if(isAddScoreItem>0){
									addScore += addHundredScore;
									deductScore += decHundredScore;
								}else{
									Double addWeightScore = (double) Math.round((addHundredScore * (tempWeight / 100)) * 100) / 100;
									Double decWeightScore = (double) Math.round((decHundredScore * (tempWeight / 100)) * 100) / 100;
									addScore += addWeightScore;
									deductScore += decWeightScore;
								}

							}
							else{
								tempUnitInfoMap.put("hundredScore", "-");//百分制得分
								if(isAddScoreItem>0){
									//得分制工作项
									if(hundredScore<0){
										hundredScore = 0.0;
									}
									weightScore = hundredScore;
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
								}else{
									if((100-hundredScore)>weightScore){
										tempUnitInfoMap.put("weightScore", 0.0);//权重分
										weightScore = 0.0;
									}else{
										tempUnitInfoMap.put("weightScore", weightScore-(100-hundredScore));//权重分
										weightScore = weightScore-(100-hundredScore);
									}
								}
								if(isAddScoreItem>0&&hundredScore<0){
									addScore += 0;
									deductScore += 0;
								}else{
									addScore += addHundredScore;
									deductScore += decHundredScore;
								}

							}
							if(isAddScoreItem>0){
								totalScore += hundredScore;
								/*totalScore += addHundredScore;
								totalScore -= decHundredScore;*/
							}else{
								if(weightScore-tempWeight<0){
									//当前权重得分 小于 满分权重值
									totalScore -= keepTwoDecimal(tempWeight-weightScore);
								}else{
									totalScore += keepTwoDecimal(weightScore-tempWeight);
								}
							}
						}
						else if("3".equals(workNameType)){
							//公共加扣分
							totalScore += publicAddScore;
							addScore += publicAddScore;
							totalScore -= publicDeductScore;
							deductScore += publicDeductScore;
						}
						else{
							//常规业务
							//2.23 权重分设置问题，应当扣完为止，不出现负数
							if(!workName.equals("32")&&hundredScore<0){
								//百分制得分 小于 0;得分不能小于0
								weightScore = 0.0;
								hundredScore = 0.0;
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
							}else{
								weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
								//weightScore = hundredScore * (weightScore / 100);
								tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
								tempUnitInfoMap.put("weightScore", weightScore);//权重分
								if(isAddScoreItem>0){
									//得分制工作项
									weightScore = hundredScore;
									tempUnitInfoMap.put("hundredScore", hundredScore);//百分制得分
									tempUnitInfoMap.put("weightScore", weightScore);//权重分
								}
							}
							if(isAddScoreItem>0){
								addScore += addHundredScore;
								deductScore += decHundredScore;
								/*totalScore += addHundredScore;
								totalScore -= decHundredScore;*/
								totalScore += weightScore;
							}else{
								Double addWeightScore = (double) Math.round((addHundredScore * (tempWeight / 100)) * 100) / 100;
								Double decWeightScore = (double) Math.round((decHundredScore * (tempWeight / 100)) * 100) / 100;
								addScore += addWeightScore;
								deductScore += decWeightScore;
								if(weightScore-tempWeight<0){
									//当前权重得分 小于 满分
									totalScore -= keepTwoDecimal(tempWeight-weightScore);
								}else{
									totalScore += keepTwoDecimal(weightScore-tempWeight);
								}
							}

						}

					}
					if(addSpecI==1){
						addScoreItems = new StringBuffer("业务加分详情：无<br>");//加分项目
					}
					if(decSpecI==1){
						deductScoreItems = new StringBuffer("业务扣分分详情：无<br>");//加分项目
					}
					if(addI==1){
						publiceAddspecificItem = new StringBuffer();//加分项目
					}
					if(decI==1){
						publiceDecspecificItem = new StringBuffer();//加分项目
					}
					tempMap.put("totalScore", keepTwoDecimal(totalScore));//总得分
					tempMap.put("addScore", addScore);//加分
					tempMap.put("deductScore", deductScore);//扣分
					tempMap.put("deductScoreItems", deductScoreItems+""+publiceDecspecificItem);//扣分项目
					tempMap.put("addScoreItems", addScoreItems+""+publiceAddspecificItem);//加分项目

				}else{
					deductScoreItems = new StringBuffer("无");//扣分项目
					addScoreItems = new StringBuffer("无");//加分项目
					tempMap.put("totalScore", totalScore + addScore - deductScore);//总得分
					tempMap.put("addScore", addScore);//加分
					tempMap.put("deductScore", deductScore);//扣分
					tempMap.put("deductScoreItems", deductScoreItems);//扣分项目
					tempMap.put("addScoreItems", addScoreItems);//加分项目
				}
			}
			resultMap.put("unitMapList", unitMapList);

		}
		return resultMap;
	}
	//生成表格-处领导班子考核
	public Map<String, Object> getIngChuLDScoreList(String workflowId,Integer status) {
		Map<String,Object> resultMap = new HashMap<>();
		User user = UserUtils.getUser();
		List<Map<String,Object>> unitList;
		ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
		String examType = examWorkflow.getExamType();
		String basicScore = DictUtils.getDictValue(examType,"exam_basicScore","100");
		boolean isPolice = false;
		if("cca66e1339f14799b01f6db43ed16e16".equals(user.getId())){
			//南宁局绩效办
			unitList = examWorkflowDatasDao.findUnitListByWorkflowId(workflowId);// 存储数据：a.id as "unitId" ,a.name as "unitName"
		}else if("978958003ea44a4bba3eed8ee6ceff3c".equals(user.getId())){
			//南宁处绩效办
			unitList = new ArrayList<>();
			Map<String,Object> unitMap = new HashMap<>();
			unitMap.put("unitName","南宁公安处");
			unitMap.put("unitId","34");
			unitList.add(unitMap);
		} else if("6af0e615e9b0477da82eff06ff532c8b".equals(user.getId())){
			//柳州处绩效办
			unitList = new ArrayList<>();
			Map<String,Object> unitMap = new HashMap<>();
			unitMap.put("unitName","柳州公安处");
			unitMap.put("unitId","95");
			unitList.add(unitMap);
		}else if("46c521bf67e24db28772b3eac52dc454".equals(user.getId())){
			//南宁处绩效办
			unitList = new ArrayList<>();
			Map<String,Object> unitMap = new HashMap<>();
			unitMap.put("unitName","北海公安处");
			unitMap.put("unitId","156");
			unitList.add(unitMap);
		} else{
			int count = examWorkflowDatasDao.findCountByWorkflowIdFillPersonId(workflowId, user.getId());
			if(personnelBaseDao.findInfoByIdNumber(user.getId())!=null && count > 0){
				//确认当前用户为民警
				isPolice = true;
				unitList = new ArrayList<>();
				Map<String,Object> unitMap = new HashMap<>();
				unitMap.put("unitName",user.getOffice().getName());
				unitMap.put("unitId",user.getOffice().getName());
				unitList.add(unitMap);
			}else{
				unitList = examWorkflowDatasDao.findUnitListByWorkflowId(workflowId);
			}
		}
		if(isPolice){
			//当前用户为民警,且当前用户为只需查看本人的
			for(Map<String,Object> tempMap : unitList) {
				List<Map<String, Object>> ldInfoList = new ArrayList<>();
				Map<String, Object> ldInfoMap = new HashMap<>();
				String objId = user.getId();
				String objName = user.getName();
				String job = personnelBaseDao.findInfoByIdNumber(user.getId()).getJobAbbreviation();
				ldInfoMap.put("job", job);
				ldInfoMap.put("objId", objId);
				ldInfoMap.put("objName", objName);
				ldInfoMap.put("unitName", tempMap.get("unitName"));
				StringBuffer items;
				double totalScore = 100.0;
				try {
					totalScore = Double.valueOf(basicScore);
				}catch (Exception e){
					logger.error(getExceptionInfo(e));
					e.printStackTrace();
					totalScore = 100.0;
				}
				List<String> objIds = new ArrayList<String>();
				objIds.add(objId);
				List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.getListByOidWFId(objIds, workflowId);
				if (examWorkflowDatasList != null && examWorkflowDatasList.size() > 0) {
					items = new StringBuffer();
					int i = 1;
					for (ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList) {
						if(examWorkflowDatas.getItems()!=null){
							items.append(i +"."+examWorkflowDatas.getItems()+"<br>");
							i++;
						}
						if (examWorkflowDatas.getValueType() != null && examWorkflowDatas.getValueType() == -1) {
							if(examWorkflowDatas.getValue()!=null){
								totalScore -= Double.valueOf(examWorkflowDatas.getValue());
							}else{
								totalScore -= 0.0;
							}
						}
						if (examWorkflowDatas.getValueType() != null && examWorkflowDatas.getValueType() == 1) {
							if(examWorkflowDatas.getValue()!=null){
								totalScore += Double.valueOf(examWorkflowDatas.getValue());
							}else{
								totalScore += 0.0;
							}

						}
					}
				} else {
					items = new StringBuffer("无加扣分事项");
				}
				ldInfoMap.put("totalScore", totalScore);
				ldInfoMap.put("items", items.toString());
				ldInfoList.add(ldInfoMap);
				tempMap.put("ldInfoMap", ldInfoList);
			}
		}else{
			if (unitList!=null && unitList.size()>0){
				for(Map<String,Object> tempMap : unitList){
					List<Map<String,Object>> mapList = examWorkflowDatasDao.getObjId(tempMap.get("unitId").toString(),workflowId);
					List<Map<String,Object>> ldInfoList = new ArrayList<>();
					if(mapList!=null && mapList.size()>0){
						for(Map<String,Object> objMap : mapList){
							Map<String,Object> ldInfoMap = new HashMap<>();
							String objId = objMap.get("objId").toString();
							String objName = objMap.get("objName").toString();
							String job = objMap.get("jobAbbreviation").toString();//职务
							ldInfoMap.put("job",job);
							ldInfoMap.put("objId",objId);
							ldInfoMap.put("objName",objName);
							ldInfoMap.put("unitName",tempMap.get("unitName"));
							StringBuffer items ;
							double totalScore = 100.0;
							try {
								totalScore = Double.valueOf(basicScore);
							}catch (Exception e){
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
								totalScore = 100.0;
							}
							List<String> objIds = new ArrayList<String>();
							objIds.add(objId);
							List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.getListByOidWFId(objIds, workflowId);
							if(examWorkflowDatasList!=null && examWorkflowDatasList.size()>0){
								items = new StringBuffer();
								int i = 1;
								for(ExamWorkflowDatas examWorkflowDatas :examWorkflowDatasList){
									if(examWorkflowDatas.getItems()!=null){
										items.append(i +"."+examWorkflowDatas.getItems()+"<br>");
										i++;
									}
									if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
										if(examWorkflowDatas.getValue()!=null){
											totalScore -= Double.valueOf(examWorkflowDatas.getValue());
										}else{
											totalScore -= 0.0;
										}
									}
									if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
										if(examWorkflowDatas.getValue()!=null){
											totalScore += Double.valueOf(examWorkflowDatas.getValue());
										}else{
											totalScore += 0.0;
										}
									}
								}
							}else{
								items = new StringBuffer("无加扣分事项");
							}
							ldInfoMap.put("totalScore",totalScore);
							ldInfoMap.put("items",items.toString());
							ldInfoList.add(ldInfoMap);
						}
					}
					tempMap.put("ldInfoMap",ldInfoList);
				}
			}
		}

		resultMap.put("unitList",unitList);
		return resultMap;
	}
	//生成表格 - 中基层考评/民警考评
	public List<Map<String,Object>>  getIngPoliceManExamScoreList(String workflowId) {
		//Map<String,Object> resultMap = new HashMap<>();
		List<Map<String,Object>> personInfoMapList = new ArrayList<>();
		User user = UserUtils.getUser();//获取当前用户
		List<Map<String, Object>> objIds;
		PersonnelBase personnelBase =personnelBaseDao.findInfoByIdNumber(user.getNo());
		int count = examWorkflowDatasDao.findCountByWorkflowIdFillPersonId(workflowId, user.getId());
		if(personnelBase !=null && count>0){//当前登录用户为民警用户,且为自评对象
			Map<String,Object> map = new HashMap<>();
			map.put("objId",user.getId());
			map.put("objName",user.getName());
			map.put("jobAbbreviation",personnelBase.getJobAbbreviation());
			map.put("unitName",personnelBase.getWorkunitName());
			objIds = new ArrayList<>();
			objIds.add(map);
		}else{
			//单位只能查看本单位
			// 6af0e615e9b0477da82eff06ff532c8b 柳州处绩效办   46c521bf67e24db28772b3eac52dc454 北海处绩效办
			// cca66e1339f14799b01f6db43ed16e16 南宁局绩效办   978958003ea44a4bba3eed8ee6ceff3c 南宁处绩效办
			//1.根据workflowId查出该考评流程下的所有被考评人id及name集合
			objIds = examWorkflowDatasDao.findPersonInfo(workflowId,user.getOffice().getId());//obj_id as "objId",obj_name as "objName"
		}
		ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
		String examType = examWorkflow.getExamType();
		String basicScore = DictUtils.getDictValue(examType,"exam_basicScore","100");
		//2.遍历1查询出的list集合，根据被考评人id查询相应考评数据，处理这些数据，最终返回 姓名，单位，职务，得分，加扣分项目
		if(objIds!=null && objIds.size()>0){
			for(Map<String,Object> tempMap : objIds){//被考评人objId集合
				Map<String,Object> personInfoMap = new HashMap<>();
				personInfoMap.put("job",tempMap.get("jobAbbreviation"));
				personInfoMap.put("objId",tempMap.get("objId"));
				personInfoMap.put("objName",tempMap.get("objName"));
				personInfoMap.put("unitName",tempMap.get("unitName"));
				List<String> objIdList = new ArrayList<String>();
				objIdList.add(tempMap.get("objId").toString());
				Double totalScore = 100.0;//总得分
				try {
					totalScore = Double.valueOf(basicScore);
				}catch (Exception e){
					logger.error(getExceptionInfo(e));
					e.printStackTrace();
					totalScore = 100.0;
				}
				StringBuffer items;
				List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.getListByOidWFId(objIdList, workflowId);//考评数据
				if(examWorkflowDatasList != null && examWorkflowDatasList.size()>0){
					items = new StringBuffer();
					int i = 1;
					for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList){
						if(examWorkflowDatas.getItems()!=null){
							items.append(i +"."+examWorkflowDatas.getItems()+"<br>");
							i++;
						}
						if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
							/*if(examWorkflowDatas.getValue()!=null){
								totalScore -= Double.valueOf(examWorkflowDatas.getValue());
							}else{
								totalScore -= 0.0;
							}*/
							try {
								totalScore -= Double.valueOf(examWorkflowDatas.getValue());
							}catch (Exception e){
								totalScore -= 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}

						}
						if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
							/*if(examWorkflowDatas.getValue()!=null){
								totalScore += Double.valueOf(examWorkflowDatas.getValue());
							}else{
								totalScore += 0.0;
							}*/
							try {
								totalScore += Double.valueOf(examWorkflowDatas.getValue());
							}catch (Exception e){
								totalScore += 0.0;
								logger.error(getExceptionInfo(e));
								e.printStackTrace();
							}
						}
					}

				}else{
					items = new StringBuffer("无加扣分事项");
				}
				personInfoMap.put("totalScore",totalScore);
				personInfoMap.put("items",items.toString());
				personInfoMapList.add(personInfoMap);
			}

		}
		//resultMap.put("personInfoMapList",personInfoMapList);
		return  personInfoMapList;
	}
	//局考处-系统公示页面 获取各处总得分-弃用
	public Map<String,Object> getChuScoreListInPublic2(ExamWorkflow examWorkflow) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> objIdAndNames = examWorkflowDatasDao.findObjIdAndName(examWorkflow.getId());
		//根据工作项算分数
		/*for (Map<String, Object> unitMap : objIdAndNames) {
			unitMap.put("totalWeightScore", String.valueOf(0.0));
			unitMap.put("totalPublicAddScore", String.valueOf(0.0));
			unitMap.put("totalPublicDeductScore", String.valueOf(0.0));
			unitMap.put("totaltScore", String.valueOf(0.0));
		}*/

		for(Map<String, Object> unitMap : objIdAndNames){
			List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.findStandardIdByWorkflowIdAndObjId(examWorkflow.getId(),unitMap.get("objId").toString());
			List<Map<String,Object>> weightMapList = examWorkflowDatasDao.findJkCWorkNameWeights(examWorkflow.getId(),unitMap.get("objId").toString(),null);
			double conventionWorkScore = 0;
			double importWorkScore = 0;
			double commonSubstractScore = 0;
			double commonAddScore = 0;
			String objId = unitMap.get("objId").toString();
			Set<String> workNames = new HashSet<>();
			if(weightMapList!=null && weightMapList.size()>0){
				for(Map<String,Object> weightMap : weightMapList){
					if(weightMap!=null){
						String workName = (String) weightMap.get("workName");
						double zdScore = 0.0;
						double cgScore = 0.0;
						Double weight = Double.valueOf((Integer)weightMap.get("weight"))==null?100:Double.valueOf((Integer)weightMap.get("weight"));
						for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList){
							if(workName.equals(examWorkflowDatas.getWorkName())){
								String standardId = examWorkflowDatas.getStandardId();
								ExamStandardBaseInfo standardInfo = null;
								if(null == CacheUtils.get("standard_"+standardId)){
									standardInfo = examStandardBaseInfoDao.get(standardId);
									CacheUtils.put("standard_"+standardId, standardInfo);
								}else {
									standardInfo = (ExamStandardBaseInfo) CacheUtils.get("standard_"+standardId);
								}
								//0:常规业务,1:重点工作,2:公共加分，3:公共扣分
								double value;
								if(examWorkflowDatas.getValue()!=null){
									value = Double.parseDouble(examWorkflowDatas.getValue());
								}else{
									value = 0.0;
								}
								// FIXME: 2020/11/7 系统公示时 此处为空报错
								//double weight = examWorkflowDatas.getWeight()==null?1:examWorkflowDatas.getWeight();
								if("0".equals(standardInfo.getModelType())){
									cgScore += value*(weight/100);
								}else if("1".equals(standardInfo.getModelType())){
									//重点
									zdScore += value;
								}else if("2".equals(standardInfo.getModelType())){
									commonAddScore += value;
								}
								else if("3".equals(standardInfo.getModelType())){
									commonSubstractScore += value;
								}
							}
						}
						//conventionWorkScore  常规
						if(cgScore !=0 && cgScore>weight){
							conventionWorkScore += weight;
						}
						//重点
						if(zdScore>weight){
							importWorkScore += weight;
						}else{
							importWorkScore += zdScore;
						}
						unitMap.put("conventionWorkScore",String.valueOf(conventionWorkScore));
						unitMap.put("importWorkScore",String.valueOf(importWorkScore));
						unitMap.put("commonSubstractScore",String.valueOf(commonSubstractScore));
						unitMap.put("commonAddScore",String.valueOf(commonAddScore));
					}
				}
			}
			else{
				for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList){
					String standardId = examWorkflowDatas.getStandardId();
					ExamStandardBaseInfo standardInfo = null;
					if(null == CacheUtils.get("standard_"+standardId)){
						standardInfo = examStandardBaseInfoDao.get(standardId);
						CacheUtils.put("standard_"+standardId, standardInfo);
					}else {
						standardInfo = (ExamStandardBaseInfo) CacheUtils.get("standard_"+standardId);
					}
					//0:常规业务,1:重点工作,2:公共加分，3:公共扣分
					double value;
					if(examWorkflowDatas.getValue()!=null){
						value = Double.parseDouble(examWorkflowDatas.getValue());
					}else{
						value = 0.0;
					}
					// FIXME: 2020/11/7 系统公示时 此处为空报错
					double weight = examWorkflowDatas.getWeight()==null?1:examWorkflowDatas.getWeight();
					if("0".equals(standardInfo.getModelType())){
						conventionWorkScore += value*(weight/100);
					}else if("1".equals(standardInfo.getModelType())){
						//重点
						if(value>weight){
							importWorkScore+= weight;
						}else{
							importWorkScore += value;
						}
					}else if("2".equals(standardInfo.getModelType())){
						commonAddScore  += value;
					}
					else if("3".equals(standardInfo.getModelType())){
						commonSubstractScore += value;
					}
					unitMap.put("conventionWorkScore",String.valueOf(conventionWorkScore));
					unitMap.put("importWorkScore",String.valueOf(importWorkScore));
					unitMap.put("commonSubstractScore",String.valueOf(commonSubstractScore));
					unitMap.put("commonAddScore",String.valueOf(commonAddScore));

				}
			}

		}

		//根据工作项算分数
		/*//从工作权重表中取出相关工作项及权重
		List<Map<String, Object>> weigthsList = examWeightsMainDao.findWorkNameList();
		//业务权重合计得分
		for (Map<String, Object> map : weigthsList) {
			String workName = String.valueOf(map.get("value"));
			for (Map<String, Object> unitMap : objIdAndNames) {
				Double weightScore = ((Integer) map.get("weight")).doubleValue();
				String unitId = unitMap.get("objId").toString();
				Map<String, Object> tempUnitInfoMap = new HashMap<>();
				List<ExamWorkflowDatas> workflowDatasList = examWorkflowDatasDao.getMapByOidWFIdWorkName(examWorkflow.getId(), unitId, workName);
				Double publicAddScore = 0.0;
				Double publicDeductScore = 0.0;
				if (workflowDatasList != null && workflowDatasList.size() > 0) {
					Double hundredScore;
					if("33".equals(workName)){
						hundredScore = 0.0;
					}else{
						hundredScore = 100.0;
					}
					int i = 1;
					for (ExamWorkflowDatas examWorkflowDatas : workflowDatasList) {
						if ("32".equals(examWorkflowDatas.getWorkName())) {//公共加扣分项
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
								if(examWorkflowDatas.getValue()!=null){
									publicDeductScore -= Double.valueOf(examWorkflowDatas.getValue());
								}else{
									publicDeductScore -= 0.0;
								}
							}
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
								if(examWorkflowDatas.getValue()!=null){
									publicDeductScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									publicDeductScore += 0.0;
								}
							}
						} else {
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
								if(examWorkflowDatas.getValue()!=null){
									hundredScore -= Double.valueOf(examWorkflowDatas.getValue());
								}else{
									hundredScore -= 0.0;
								}
							}
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
								if(examWorkflowDatas.getValue()!=null){
									hundredScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									hundredScore += 0.0;
								}
							}
						}
					}
					Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore").toString()) + publicAddScore;//总公共加分
					Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore").toString()) + publicDeductScore;//总公共扣分
					if (!"32".equals(map.get("value"))) {
						weightScore = hundredScore * (weightScore / 100);
						Double totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore").toString()) + weightScore;//业务权重合计得分
						unitMap.put("totalWeightScore", totalWeightScore.toString());//业务权重合计得分
					}
					unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
					unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分
				} else {
					Double totalPublicAddScore = Double.valueOf(unitMap.get("totalPublicAddScore").toString()) + publicAddScore;//总公共加分
					Double totalPublicDeductScore = Double.valueOf(unitMap.get("totalPublicDeductScore").toString()) + publicDeductScore;//总公共扣分
					if (!"32".equals(map.get("value"))) {
						Double totalWeightScore;
						if("33".equals(workName)){
							totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore").toString()) + 0.0;//业务权重合计得分
						}else{
							totalWeightScore = Double.valueOf(unitMap.get("totalWeightScore").toString()) + weightScore;//业务权重合计得分
						}
						unitMap.put("totalWeightScore", totalWeightScore.toString());//业务权重合计得分
					}
					unitMap.put("totalPublicAddScore", totalPublicAddScore.toString());//总公共加分
					unitMap.put("totalPublicDeductScore", totalPublicDeductScore.toString());//总公共扣分

				}
				Double totaltScore = Double.valueOf(unitMap.get("totalWeightScore").toString()) + Double.valueOf(unitMap.get("totalPublicAddScore").toString()) - Double.valueOf(unitMap.get("totalPublicDeductScore").toString());//总得分
				unitMap.put("totaltScore", totaltScore.toString());//总得分
			}
		}*/
		resultMap.put("resultList",objIdAndNames);
		//resultMap.put("weigthsList",weigthsList);
		return resultMap;
	}
	//局考处-系统公示页面 三个处大致预览
	public Map<String,Object> getChuScoreListInPublic(ExamWorkflow examWorkflow) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> objIdAndNames = examWorkflowDatasDao.findObjIdAndName(examWorkflow.getId());
		Double baseSum = examWeightsMainDao.getJKCBaseSumScore();//基础分
		for(Map<String, Object> unitMap : objIdAndNames){
			List<Map<String,Object>> weightMapList = examWorkflowDatasDao.findJkCWorkNameWeights2(examWorkflow.getId(),unitMap.get("objId").toString(),null);
			double conventionWorkScore = 0;//常规业务扣分
			double importWorkScore = 0;//重点工作扣分
			double commonSubstractScore = 0;//公共扣分
			double commonAddScore = 0;//公共加分
			double addWorkItemScore = 0;  //得分制工作项得分
			String objId = unitMap.get("objId").toString();
			unitMap.put("baseSumJKC",baseSum);
			//Set<String> workNames = new HashSet<>();
			if(weightMapList!=null && weightMapList.size()>0){
				for(Map<String,Object> weightMap : weightMapList){
					if(weightMap!=null){
						String workName = (String) weightMap.get("workName");
						String workNameType = (String) weightMap.get("workNameType");// 0  常规  1 重点 3 公共加扣分
						Integer isAddItem = examScoreWorkItemDao.getInfoCount(workName, "1");//是否为得分制工作项
						double zdScore = 0.0;
						double zdScoreAdd = 0.0;
						double cgScore = 0.0;
						double cgScoreAdd = 0.0;
						Double weight =(Double)weightMap.get("weight")==null?100.0:(Double)weightMap.get("weight");
						List<ExamWorkflowDatas> examWorkflowDatasList1 = examWorkflowDatasDao.getMapByOidWFIdWorkName(examWorkflow.getId(), objId, workName, null);
						for(ExamWorkflowDatas examWorkflowDatas : examWorkflowDatasList1){
							//workNameType  0  常规  1 重点 3 公共加扣分  来自考评维护-工作权重设置表
							double value;
							if(examWorkflowDatas.getValue()!=null){
								value = Double.parseDouble(examWorkflowDatas.getValue());
							}else{
								value = 0.0;
							}
							Integer valueType = examWorkflowDatas.getValueType();
							if(valueType!=null){
								if("0".equals(workNameType)){
									if(valueType==1){
										cgScoreAdd += value;
									}else{
										//扣分
										cgScore += value;
									}
								}else if("1".equals(workNameType)){
									//重点
									if(valueType==1){
										zdScoreAdd += value;
									}else{
										//扣分
										zdScore += value;
									}
								}else if("3".equals(workNameType)){
									//公共加扣分
									if(valueType==1){
										commonAddScore += value;
									}else{
										commonSubstractScore += value;
									}

								}
							}


						}
						//conventionWorkScore  常规
						if("0".equals(workNameType)){
							if(isAddItem>0){
								//得分工作项
								conventionWorkScore += 0.0;
								double tempScore = cgScore-cgScoreAdd;
								if(tempScore>0){
									//扣分大于得分
									addWorkItemScore  += 0.0;
								}else{
									addWorkItemScore += -tempScore;
								}

							}else{
								double tempScore = cgScore-cgScoreAdd;
								if(tempScore>100){
									conventionWorkScore += weight;
								}else{
									//conventionWorkScore += tempScore*(weight/100);
									conventionWorkScore += weight-(double) Math.round(((100-tempScore) * (weight / 100)) * 100) / 100;
								}
							}

						}
						//conventionWorkScore += (cgScore-cgScoreAdd);
						//重点
						if("1".equals(workNameType)){
							double tempScore = zdScore-zdScoreAdd;
							if("2".equals(workName)){
								//高铁安保
								if(isAddItem>0){
									//得分工作项
									importWorkScore += 0.0;
									if(tempScore>0){
										//扣分大于得分
										addWorkItemScore  += 0.0;
									}else{
										addWorkItemScore += -tempScore;
									}
								}else{
									if(tempScore>100){
										importWorkScore += weight;
									}else{
										//importWorkScore += tempScore*(weight/100);
										importWorkScore += weight-((double) Math.round(((100-tempScore) * (weight / 100)) * 100) / 100);
									}
								}
							}else{
								if(isAddItem>0){
									importWorkScore += 0.0;
									if(tempScore>0){
										//扣分大于得分
										addWorkItemScore  += 0.0;
									}else{
										addWorkItemScore += -tempScore;
									}
								}else{
									if(tempScore >weight){
										importWorkScore += weight;
									}else{
										importWorkScore += tempScore;
									}
								}

							}
						}
						//importWorkScore += (zdScore-zdScoreAdd);
						unitMap.put("sumWorkScore",keepTwoDecimal(baseSum-conventionWorkScore-importWorkScore-commonSubstractScore+commonAddScore+addWorkItemScore));//总得分
						unitMap.put("conventionWorkScore",keepTwoDecimal(conventionWorkScore));//常规业务扣分
						unitMap.put("importWorkScore",keepTwoDecimal(importWorkScore));//重点工作扣分
						unitMap.put("commonSubstractScore",keepTwoDecimal(commonSubstractScore));//公共扣分
						unitMap.put("commonAddScore",keepTwoDecimal(commonAddScore));//公共加分
						unitMap.put("addWorkItemScore",keepTwoDecimal(addWorkItemScore));//得分制工作项得分
					}
				}
			}else{
				unitMap.put("sumWorkScore",keepTwoDecimal(baseSum-conventionWorkScore-importWorkScore-commonSubstractScore+commonAddScore+addWorkItemScore));//总得分
				unitMap.put("conventionWorkScore",keepTwoDecimal(conventionWorkScore));//常规业务扣分
				unitMap.put("importWorkScore",keepTwoDecimal(importWorkScore));//重点工作扣分
				unitMap.put("commonSubstractScore",keepTwoDecimal(commonSubstractScore));//公共扣分
				unitMap.put("commonAddScore",keepTwoDecimal(commonAddScore));//公共加分
				unitMap.put("addWorkItemScore",keepTwoDecimal(addWorkItemScore));//得分制工作项得分
			}

		}
		resultMap.put("resultList",objIdAndNames);
		//resultMap.put("weigthsList",weigthsList);
		return resultMap;
	}

	public int getCountByWorkflowIdFillPersonId(String workflowId,String fillPersonId){
		return examWorkflowDatasDao.findCountByWorkflowIdFillPersonId(workflowId,fillPersonId);
	}

	/**
	 * 批量更新考评项的环节状态（更新考评对象的所有状态（datas表中所有考评对象的状态））
	 * @param examWorkflowDatas
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void updateStatusByIdsBeta(ExamWorkflowDatas examWorkflowDatas, String[] ids,String nextStatus) {

		/*更新环节状态只更新自己审核的内容*/
		/*ids[0]  同一个页面的被考评对象，状态必须一样 取一个就好了*/
		int status = getStatusById(ids[0],examWorkflowDatas.getWorkflowId(),examWorkflowDatas.getStatus(),UserUtils.getUser().getId());
		switch (status){
			case 2:
				examWorkflowDatas.setExamPersonId(UserUtils.getUser().getId());
				break;
			case 3:

				break;
			case 4:
				examWorkflowDatas.setDepartSignId(UserUtils.getUser().getId());
				break;
			case 5:
				examWorkflowDatas.setPartBureausSignId(UserUtils.getUser().getId());
				break;
			case 6:
				examWorkflowDatas.setAdjustPersonId(UserUtils.getUser().getId());
				break;
			case 7:
				examWorkflowDatas.setMainBureausSignId(UserUtils.getUser().getId());
				break;
			default:
				break;
		}
		examWorkflowDatas.setStatus(Integer.valueOf(nextStatus));
		examWorkflowDatas.setIds(ids);
		dao.updateStatusByIdsBeta(examWorkflowDatas);

	}

	/**
	 * 更新推送的考评项	标记推送的考评项
	 * @param params
	 * @return
	 */
	@Transactional(readOnly = false)
    public int updatePush(ExamWorkflowDatas params) {

    	return dao.updatePush(params);
    }

    /*查询下一步的审核和人 或 操作人员*/
    public List<Map<String,String>> findNextExamPerson(String id,String nextStatus,String workflowId,int status) {

    	String userId = UserUtils.getUser().getId();
    	return dao.findNextExamPerson(id,nextStatus,workflowId,status,userId);
    }

	/**
	 *  根据考评对象和workflowId,更新所有审核人为空的数据，更新内容为审核人
	 * @param datas
	 */
	@Transactional(readOnly = false)
	public void updatePersonBeta(ExamWorkflowDatas datas) {
		dao.updatePersonBeta(datas);
	}

	/**
	 * 批量更新被考评对象的审核人，只更新下一环节审核人为空的数据,并且当前环节的审核人为登录用户
	 * @param examWorkflowDatas
	 */
	@Transactional(readOnly = false)
    public void updateExamPersonByIds(ExamWorkflowDatas examWorkflowDatas,String nextStatus,String person,String personId) {

		/*更新当前审核的所有数据的  下一环节的审核人*/
		examWorkflowDatas.setCondition(UserUtils.getUser().getId());

		switch (nextStatus){
			case "3":
//				下一环节是3时，设置的下下一环节的人
				break;
			case "4":		//部门负责人签字
				examWorkflowDatas.setDepartSign(person);
				examWorkflowDatas.setDepartSignId(personId);
				break;
			case "5":		//分管局领导
				examWorkflowDatas.setPartBureausSign(person);
				examWorkflowDatas.setPartBureausSignId(personId);
				break;
			case "6":		//绩效考评领导小组复核及调整
				examWorkflowDatas.setAdjustPerson(person);
				examWorkflowDatas.setAdjustPersonId(personId);
				break;
			case "7":		//局主管领导最终审签
				examWorkflowDatas.setMainBureausSign(person);
				examWorkflowDatas.setMainBureausSignId(personId);
				break;
			case "8":
				break;
		}
    	dao.updateExamPersonByIds(examWorkflowDatas);
    }

	public Map<String, Object> getHeadItemScore(ExamWorkflow examWorkflow,String fillPersonId) {
		String examType = examWorkflow.getExamType();//考评类型
		Map<String,Object> resultMap = new HashMap<>();
		String userId = "";
		if(StringUtils.isNotBlank(fillPersonId)){
			userId = fillPersonId;
		}else{
			userId = UserUtils.getUser().getId();
		}
		User user = UserUtils.get(userId);
		if("1".equals(examType) || "3".equals(examType)){
			//局考处 / 处考队所
			// exam_standar_base_info model_type 0 常规业务  1 重点工作  2 公共加分  3 公共扣分
			resultMap = this.getItemLabelScoreInfoHaveWeight(user,examType,examWorkflow,userId);
			/*List<Map<String,Object>> infoMapList = examWorkflowDatasDao.getHeadItemScoreList(examWorkflow.getId(),userId);
			List<Map<String,Object>> cgList = new ArrayList<>();//常规业务 情况
			List<Map<String,Object>> zdList = new ArrayList<>();//重点工作 情况
			List<Map<String,Object>> addList = new ArrayList<>();//公共加分 情况
			List<Map<String,Object>> decList = new ArrayList<>();//公共扣分 情况
			Set<String> cgweightSet = new HashSet<>();//权重集合
			Set<String> zdweightSet = new HashSet<>();//权重集合
			Set<String> addweightSet = new HashSet<>();//权重集合
			Set<String> decweightSet = new HashSet<>();//权重集合
			Double cgScore = 0.0;//常规
			Double zdScore = 0.0;//重点
			Double addScore = 0.0;//公共加分
			Double decScore = 0.0;//公共扣分
			Double nowSocre = 100.0;//当前得分
			try {
				if("1".equals(examType)){
					nowSocre = examWeightsMainDao.getJKCBaseSumScore();//基础分
				}
				if("3".equals(examType)){
					String unitId;
					String kpUnitId ;
					if("34".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",34,") || "34".equals(user.getOffice().getParentId())){
						//南宁处
						unitId = "34";
						kpUnitId = user.getOffice().getId();
					} else if("95".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",95,") || "95".equals(user.getOffice().getParentId())){
						//柳州处
						unitId = "95";
						kpUnitId = user.getOffice().getId();
					}else if("156".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",156,") || "156".equals(user.getOffice().getParentId())){
						//北海处
						unitId = "156";
						kpUnitId = null;
					}else{
						unitId = null;
						kpUnitId = user.getOffice().getId();
					}
					nowSocre = examWeightsMainDao.getCKDSBaseSumScore(unitId,kpUnitId);//基础分
				}
			}catch (Exception e){
				nowSocre = 100.0;
				e.printStackTrace();
			}
			Double nowAddScore = 0.0;//当前加分
			Double nowDecScore = 0.0;//当前扣分
			if(infoMapList!=null && infoMapList.size()>0){
				for(Map<String,Object> tempMap : infoMapList){
					String modelType = (String) tempMap.get("modelType");
					Map<String,Object> itempMap = new HashMap<>();
					itempMap.put("valueType",tempMap.get("valueType"));
					itempMap.put("value",tempMap.get("value"));
					itempMap.put("workName",tempMap.get("workName"));
					itempMap.put("weight",tempMap.get("weight"));
					switch (modelType){
						case "0":
							if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
								if("2".equals(tempMap.get("workName"))&&("1".equals(examType)||"3".equals(examType))){
									//工作项为高铁安保：2 且当前考评类别为局考处
									zdList.add(itempMap);
									zdweightSet.add(tempMap.get("workName").toString());
								}else{
									cgList.add(itempMap);
									cgweightSet.add(tempMap.get("workName").toString());
								}
							}else{
								cgList.add(itempMap);
							}
							break;
						case "1":
							zdList.add(itempMap);
							if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
								zdweightSet.add(tempMap.get("workName").toString());
							}
							break;
						case "2":
							addList.add(itempMap);
							if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
								addweightSet.add(tempMap.get("workName").toString());
							}
							break;
						case "3":
							decList.add(itempMap);
							if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
								decweightSet.add(tempMap.get("workName").toString());
							}
							break;
						default:
							break;
					}
				}
				//常规
				if(cgweightSet!=null && cgweightSet.size()>0){
					if(cgList!=null && cgList.size()>0){
						Map<String,Object> infoMap  = this.getItemLabelScoreInfoJKC("常规业务",cgList,cgweightSet,examType);
						*//*resultMap.put("leftTitle",cgStr.toString());
						resultMap.put("addScore",addScore);
						resultMap.put("decScore",decScore);*//*
						resultMap.put("cgInfo",infoMap.get("leftTitle"));
						nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
						nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
					}
				}
				//重点
				if(zdweightSet!=null && zdweightSet.size()>0) {
					if (zdList != null && zdList.size() > 0) {
						Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("重点工作", zdList, zdweightSet,examType);
						resultMap.put("zdInfo",infoMap.get("leftTitle"));
						nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
						nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
					}
				}
				//公共加分
				if(addweightSet!=null && addweightSet.size()>0) {
					if (addList != null && addList.size() > 0) {
						Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("公共加分", addList, addweightSet,examType);
						//resultMap.put("addInfo", info);
						resultMap.put("addInfo",infoMap.get("leftTitle"));
						nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
						nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
					}
				}
				//公共扣分
				if(decweightSet!=null && decweightSet.size()>0) {
					if (decList != null && decList.size() > 0) {
						Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("公共扣分", decList, decweightSet,examType);
						//resultMap.put("decInfo", info);
						resultMap.put("decInfo",infoMap.get("leftTitle"));
						nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
						nowDecScore += Double.valueOf(infoMap.get("decScore").toString());

					}
				}
				nowDecScore = (double) Math.round(nowDecScore * 100) / 100;
				nowAddScore = (double) Math.round(nowAddScore * 100) / 100;
				if("3".equals(examWorkflow.getExamType())){
					if(resultMap.get("cgInfo")!=null){
						resultMap.put("cgInfo","当前得分："+String.format("%.2f",(nowSocre-nowDecScore+nowAddScore))+"分；  加分："+nowAddScore+"   扣分："+nowDecScore+"  "+resultMap.get("cgInfo"));
					}else{
						resultMap.put("cgInfo","当前得分："+String.format("%.2f",(nowSocre-nowDecScore+nowAddScore))+"分；  加分："+nowAddScore+"   扣分："+nowDecScore);
					}
					//resultMap.put("cgInfo","当前得分："+(100.0-cgScore-zdScore-decScore+addScore)+" "+resultMap.get("cgInfo"));
				}else{
					if(resultMap.get("cgInfo")!=null){
						//resultMap.put("cgInfo","当前得分："+(nowSocre-nowDecScore+nowAddScore)+"  加分："+nowAddScore+"   扣分："+nowDecScore+"  "+resultMap.get("cgInfo"));
						resultMap.put("cgInfo","当前得分："+resultMap.get("cgInfo"));
					}else{
						//resultMap.put("cgInfo","当前得分："+(nowSocre-nowDecScore+nowAddScore)+"  加分："+nowAddScore+"   扣分："+nowDecScore);
						resultMap.put("cgInfo","当前得分：");
					}
					if(resultMap.get("addInfo")==null){
						resultMap.put("addInfo","公共加分：0.0分");
					}
					if(resultMap.get("decInfo")==null){
						resultMap.put("decInfo","公共扣分：0.0分");
					}
					//resultMap.put("cgInfo","当前得分："+(100.0-cgScore-zdScore-decScore+addScore)+" "+resultMap.get("cgInfo"));
				}

			}else{
				//List<Map<String,Object>> noSelectList = examWorkflowDatasDao.getHeadItemScoreNoSelectList(examWorkflow.getId(),userId);
				if("3".equals(examWorkflow.getExamType())){
					resultMap.put("cgInfo","当前得分："+nowSocre+"分");
					resultMap.put("addInfo","加分："+nowAddScore);
					resultMap.put("decInfo","扣分："+nowDecScore);
				}else{
					//局考处默认显示100
					resultMap.put("cgInfo","当前得分：100分");
					resultMap.put("addInfo","公共加分：0.0分");
					resultMap.put("decInfo","公共扣分：0.0分");
				}
				//resultMap.put("addInfo","加分："+nowAddScore);
				//resultMap.put("decInfo","扣分："+nowDecScore);
			}*/
		}
		else if("99".equals(examType)){
			// 未使用
			// exam_standar_base_info model_type 0 常规业务  1 重点工作  2 公共加分  3 公共扣分
			List<Map<String,Object>> infoMapList = examWorkflowDatasDao.getHeadItemScoreList(examWorkflow.getId(),userId);
			List<Map<String,Object>> cgList = new ArrayList<>();//常规业务 情况
			List<Map<String,Object>> zdList = new ArrayList<>();//重点工作 情况
			List<Map<String,Object>> addList = new ArrayList<>();//公共加分 情况
			List<Map<String,Object>> decList = new ArrayList<>();//公共扣分 情况
			Set<String> cgweightSet = new HashSet<>();//权重集合
			Set<String> zdweightSet = new HashSet<>();//权重集合
			Set<String> addweightSet = new HashSet<>();//权重集合
			Set<String> decweightSet = new HashSet<>();//权重集合
			Double cgScore = 0.0;//常规
			Double zdScore = 0.0;//重点
			Double addScore = 0.0;//公共加分
			Double decScore = 0.0;//公共扣分
			Double nowSocre = 100.0;//当前得分
			Double nowAddScore = 0.0;//当前加分
			Double nowDecScore = 0.0;//当前扣分
			if(infoMapList!=null && infoMapList.size()>0){
				for(Map<String,Object> tempMap : infoMapList){
					String modelType = (String) tempMap.get("modelType");
					Map<String,Object> itempMap = new HashMap<>();
					itempMap.put("valueType",tempMap.get("valueType"));
					itempMap.put("value",tempMap.get("value"));
					itempMap.put("workName",tempMap.get("workName"));
					itempMap.put("weight",tempMap.get("weight"));
					switch (modelType){
						case "0":
							cgList.add(itempMap);
							if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
								cgweightSet.add(tempMap.get("workName").toString());
							}
							break;
						case "1":
							zdList.add(itempMap);
							if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
								zdweightSet.add(tempMap.get("workName").toString());
							}
							break;
						case "2":
							addList.add(itempMap);
							if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
								addweightSet.add(tempMap.get("workName").toString());
							}
							break;
						case "3":
							decList.add(itempMap);
							if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
								decweightSet.add(tempMap.get("workName").toString());
							}
							break;
						default:
							break;
					}
				}
				//常规
				if(cgweightSet!=null && cgweightSet.size()>0){
					if(cgList!=null && cgList.size()>0){
						//Map<String,Object> infoMap  = this.getItemLabelScoreInfoNew("常规业务",cgList,cgweightSet);
						Map<String,Object> infoMap  = this.getItemLabelScoreInfoJKC("常规业务",cgList,cgweightSet,examType);
						/*resultMap.put("leftTitle",cgStr.toString());
						resultMap.put("addScore",addScore);
						resultMap.put("decScore",decScore);*/
						resultMap.put("cgInfo",infoMap.get("leftTitle"));
						nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
						nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
					}
				}
				//重点
				if(zdweightSet!=null && zdweightSet.size()>0) {
					if (zdList != null && zdList.size() > 0) {
						//Map<String,Object> infoMap = this.getItemLabelScoreInfoNew("重点工作", zdList, zdweightSet);
						Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("重点工作", zdList, zdweightSet,examType);
						resultMap.put("zdInfo",infoMap.get("leftTitle"));
						nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
						nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
					}
				}
				//公共加分
				if(addweightSet!=null && addweightSet.size()>0) {
					if (addList != null && addList.size() > 0) {
						//Map<String,Object> infoMap = this.getItemLabelScoreInfoNew("公共加分", addList, addweightSet);
						Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("公共加分", addList, addweightSet,examType);
						//resultMap.put("addInfo", info);
						resultMap.put("addInfo",infoMap.get("leftTitle"));
						nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
						nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
					}
				}
				//公共扣分
				if(decweightSet!=null && decweightSet.size()>0) {
					if (decList != null && decList.size() > 0) {
						//Map<String,Object> infoMap = this.getItemLabelScoreInfoNew("公共扣分", decList, decweightSet);
						Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("公共扣分", decList, decweightSet,examType);
						//resultMap.put("decInfo", info);
						resultMap.put("decInfo",infoMap.get("leftTitle"));
						nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
						nowDecScore += Double.valueOf(infoMap.get("decScore").toString());

					}
				}
				nowDecScore = (double) Math.round(nowDecScore * 100) / 100;
				nowAddScore = (double) Math.round(nowAddScore * 100) / 100;
				if(resultMap.get("cgInfo")!=null){
					resultMap.put("cgInfo","当前得分："+(nowSocre-nowDecScore+nowAddScore)+"  加分："+nowAddScore+"   扣分："+nowDecScore+"  "+resultMap.get("cgInfo"));
				}else{
					resultMap.put("cgInfo","当前得分："+(nowSocre-nowDecScore+nowAddScore)+"  加分："+nowAddScore+"   扣分："+nowDecScore);
				}
				//resultMap.put("cgInfo","当前得分："+(100.0-cgScore-zdScore-decScore+addScore)+" "+resultMap.get("cgInfo"));
			}else{
				resultMap.put("cgInfo","当前得分："+nowSocre);
				resultMap.put("addInfo","加分："+nowAddScore);
				resultMap.put("decInfo","扣分："+nowDecScore);

			}
		}
		else if("2".equals(examType) || "4".equals(examType)){
			//局考局机关/处考处机关
			//权重得分判断及处理
			ExamWeights examWeights = new ExamWeights();
			examWeights.setKpType(examType);
			examWeights.setKpChu(null);
			if("4".equals(examType)){
				String companyId = user.getCompany().getId();
				if(!companyId.equals("34") && !companyId.equals("95") && !companyId.equals("156")){
					if(user.getOffice().getParentIds().contains(",34,")){
						companyId = "34";
					}
					if(user.getOffice().getParentIds().contains(",95,")){
						companyId = "95";
					}
					if(user.getOffice().getParentIds().contains(",156,")){
						companyId = "156";
					}
				}
				examWeights.setKpChu(companyId);
			}
			examWeights.setDepartmentId(null);
			examWeights = examWeightsDao.getWeightByUnit(examWeights);
			if(examWeights == null){
				//非权重制得分
				resultMap = this.getItemLabelScoreInfoNoWeight(examType,examWorkflow,userId);
			}else{
				//权重制得分
				resultMap = this.getItemLabelScoreInfoHaveWeight(user,examType,examWorkflow,userId);
			}
		}
		else{
			//当前被考评对象userId或者当前用户userId
			resultMap = this.getItemLabelScoreInfoNoWeight(examType,examWorkflow,userId);
		}

		/*else{
			//处领导班子，中基层领导，民警，
			if("5".equals(examWorkflow.getExamType())||"6".equals(examWorkflow.getExamType())||"7".equals(examWorkflow.getExamType())){

			}
			//一个选择项都未选择
			User user = UserUtils.get(userId);
			Integer cgWeight;
			Integer zdWeight;
			//1 局考处 5 处领导班子考核
			if("1".equals(examWorkflow.getExamType())||"5".equals(examWorkflow.getExamType())){
				//南宁局
				cgWeight = examWeightsMainDao.getCgWeight(null,"1");
				zdWeight = examWeightsMainDao.getZdWeight(null,"1");
			}else{
				if("34".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",34,") || "34".equals(user.getOffice().getParentId())){
					//南宁处
					cgWeight = examWeightsMainDao.getCgWeight("34","3");
					zdWeight = examWeightsMainDao.getZdWeight("34","3");
				} else if("95".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",95,") || "95".equals(user.getOffice().getParentId())){
					//柳州处
					cgWeight = examWeightsMainDao.getCgWeight("95","3");
					zdWeight = examWeightsMainDao.getZdWeight("95","3");
				}else if("156".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",156,") || "156".equals(user.getOffice().getParentId())){
					//北海处
					cgWeight = examWeightsMainDao.getCgWeight("156","3");
					zdWeight = examWeightsMainDao.getZdWeight("156","3");
				}else{
					//南宁局
					cgWeight = examWeightsMainDao.getCgWeight(null,"1");
					zdWeight = examWeightsMainDao.getZdWeight(null,"1");
				}
			}

			//modelType 0  常规  1重点  2 公共加分  3公共扣分
			List<String> modelTypeList = examWorkflowDatasDao.getHeadItemScoreNoSelectList(examWorkflow.getId(),userId);
			if(modelTypeList != null && modelTypeList.size()>0){
				for(String modelType : modelTypeList){
					switch (modelType){
						case "0":
							resultMap.put("cgInfo","常规业务："+cgWeight);
							break;
						case "1":
							resultMap.put("zdInfo","重点工作："+zdWeight);
							break;
						case "2":
							resultMap.put("addInfo","公共加分：0.0");
							break;
						case "3":
							resultMap.put("decInfo","公共扣分：0.0");
							break;
						default:
							break;
					}
				}
			}

		}*/
		return resultMap;
	}


    /*// 左上角得分情况，权重字段为空
	private String getItemLabelScoreInfoNoWeight(String modelType, List<Map<String, Object>> mapList, String examType) {
		StringBuffer leftTitle = new StringBuffer();
		double score;
		if("公共加分".equals(modelType)){
		    score = 0.0;
			leftTitle.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
				Integer valueType = (Integer) cgMap.get("valueType");
				Double value;
				if(StringUtils.isNotBlank((String)cgMap.get("value"))){
					value = Double.valueOf(cgMap.get("value").toString());
				}else {
					value = 0.0;
				}
				score += value;
			}
			leftTitle.append(score+"分 ");
			return leftTitle.toString();
		}else if("公共扣分".equals(modelType)){
			score = 0.0;
			leftTitle.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
				Integer valueType = (Integer) cgMap.get("valueType");
				Double value;
				if(StringUtils.isNotBlank((String)cgMap.get("value"))){
					value = Double.valueOf(cgMap.get("value").toString());
				}else {
					value = 0.0;
				}
				score += value;
			}
			leftTitle.append(score+"分 ");
			return leftTitle.toString();
		}else{
			score = 100.0;
			leftTitle.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
			Integer valueType = (Integer) cgMap.get("valueType");
			Double value;
			if(StringUtils.isNotBlank((String)cgMap.get("value"))){
				value = Double.valueOf(cgMap.get("value").toString());
			}else {
				value = 0.0;
			}
			if(valueType!=null){
				if(valueType == 1){
					score += value;
				}else if(valueType == -1){
					score -= value;
				}
			}else{
				score += 0.0;
			}

		}
		leftTitle.append(score+"分 ");
		}
		return leftTitle.toString();
	}

	public String getItemLabelScoreInfo(String modelType,List<Map<String,Object>> mapList,Set<String> stringSet){
		StringBuffer cgStr = new StringBuffer();
		if("公共加分".equals(modelType)){
			double score = 0.0;
			cgStr.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
				Integer valueType = (Integer) cgMap.get("valueType");
				Double value;
				if(StringUtils.isNotBlank((String)cgMap.get("value"))){
					value = Double.valueOf(cgMap.get("value").toString());
				}else {
					value = 0.0;
				}
				score += value;
			}
			cgStr.append(score+"分 ");
		}else if("公共扣分".equals(modelType)){
			double score = 0.0;
			cgStr.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
				Integer valueType = (Integer) cgMap.get("valueType");
				Double value;
				if(StringUtils.isNotBlank((String)cgMap.get("value"))){
					value = Double.valueOf(cgMap.get("value").toString());
				}else {
					value = 0.0;
				}
				score += value;
			}
			cgStr.append(score+"分 ");
		} else if("重点工作".equals(modelType)){
			cgStr.append(modelType+": (");
			for(String workName : stringSet){
				String workNameLabel = DictUtils.getDictLabels(workName,"exam_weigths","");
				cgStr.append(workNameLabel+":");
				Double score = null;
				Double weight = null;
				for(Map<String,Object> cgMap : mapList){
					String cgWorkName = (String) cgMap.get("workName");
					if(cgWorkName != null && cgWorkName != ""){
						if(cgWorkName.equals(workName)){
							if(score==null){
								if(cgMap.get("weight")!= null ){
									weight = Double.valueOf ((Integer) cgMap.get("weight"));
									score =Double.valueOf ((Integer) cgMap.get("weight"));
								}else{
									score = 100.0;
								}
							}
							Integer valueType = (Integer) cgMap.get("valueType");
							Double value;
							if(StringUtils.isNotBlank((String)cgMap.get("value"))){
								value = Double.valueOf(cgMap.get("value").toString());
								if(valueType!=null){
								if(valueType==1){
									score += value;
								}else if(valueType == -1){
									score -= value;
								}
							}else{
								score += 0.0;
							}
							}
						}
					}
				}
				if(weight!=null && score<0){
					score = 0.0;
				}
				//cgStr.append(score+"分 ");
				cgStr.append(String.format("%.2f",score)+"分 ");
			}
			cgStr.append(")");
		}else{//常规业务
			cgStr.append(modelType+": (");
			for(String workName : stringSet){
				String workNameLabel = DictUtils.getDictLabels(workName,"exam_weigths","");
				cgStr.append(workNameLabel+":");
				Double score = 100.0;
				Double weightScore = null;
				for(Map<String,Object> map : mapList){
					String cgWorkName = (String) map.get("workName");
					if(cgWorkName != null && cgWorkName != ""){
						if(cgWorkName.equals(workName)){
							if(map.get("weight")!= null ){
								weightScore =Double.valueOf ((Integer) map.get("weight"));
							}
							Integer valueType = (Integer) map.get("valueType");
							Double value;
							if(StringUtils.isNotBlank((String)map.get("value"))){
								value = Double.valueOf(map.get("value").toString());
								if(valueType!=null){
									if(valueType==1){
										score += value;
									}else if(valueType == -1){
										score -= value;
									}
								}else{
									score += 0.0;
								}
							}
						}
					}
				}
				if(weightScore!=null && weightScore!=0.0){
					score = (weightScore/100.0) * score;
				}
				//cgStr.append(score+"分 ");
				cgStr.append(String.format("%.2f",score)+"分 ");
			}
			cgStr.append(")");
		}
		return cgStr.toString();
	}*/

	public Map<String,Object> getItemLabelScoreInfoHaveWeight(User user,String examType,ExamWorkflow examWorkflow,String userId){
		Map<String,Object> resultMap = new HashMap<>();
		// exam_standar_base_info model_type 0 常规业务  1 重点工作  2 公共加分  3 公共扣分
		List<Map<String,Object>> infoMapList = examWorkflowDatasDao.getHeadItemScoreList(examWorkflow.getId(),userId);
		List<Map<String,Object>> cgList = new ArrayList<>();//常规业务 情况
		List<Map<String,Object>> zdList = new ArrayList<>();//重点工作 情况
		List<Map<String,Object>> addList = new ArrayList<>();//公共加分 情况
		List<Map<String,Object>> decList = new ArrayList<>();//公共扣分 情况
		Set<String> cgweightSet = new HashSet<>();//权重集合
		Set<String> zdweightSet = new HashSet<>();//权重集合
		Set<String> addweightSet = new HashSet<>();//权重集合
		Set<String> decweightSet = new HashSet<>();//权重集合
		Double cgScore = 0.0;//常规
		Double zdScore = 0.0;//重点
		Double addScore = 0.0;//公共加分
		Double decScore = 0.0;//公共扣分
		Double nowSocre = 100.0;//当前得分
		try {
			if("1".equals(examType)){
				nowSocre = examWeightsMainDao.getJKCBaseSumScore();//基础分
			}
			if("3".equals(examType)){
				String unitId;
				String kpUnitId ;
				if("34".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",34,") || "34".equals(user.getOffice().getParentId())){
					//南宁处
					unitId = "34";
					kpUnitId = user.getOffice().getId();
				} else if("95".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",95,") || "95".equals(user.getOffice().getParentId())){
					//柳州处
					unitId = "95";
					kpUnitId = user.getOffice().getId();
				}else if("156".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",156,") || "156".equals(user.getOffice().getParentId())){
					//北海处
					unitId = "156";
//					kpUnitId = null;
					kpUnitId = user.getOffice().getId();//21年7月19，北海处绩效办、局绩效办负责人提出，北海处开启两类派出所考评（普铁、高铁）
				}else{
					unitId = null;
					kpUnitId = user.getOffice().getId();
				}
				nowSocre = examWeightsMainDao.getCKDSBaseSumScore(unitId,kpUnitId);//基础分
			}
			if("2".equals(examType)){
				//局考局机关
				nowSocre = examWeightsMainDao.getJorCJGBaseSumScore(examType,null,null);//基础分
			}
			if("4".equals(examType)){
				//处考处机关
				String unitId;
				String kpUnitId ;
				if("34".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",34,") || "34".equals(user.getOffice().getParentId())){
					//南宁处
					unitId = "34";
					kpUnitId = user.getOffice().getId();
				} else if("95".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",95,") || "95".equals(user.getOffice().getParentId())){
					//柳州处
					unitId = "95";
					kpUnitId = user.getOffice().getId();
				}else if("156".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",156,") || "156".equals(user.getOffice().getParentId())){
					//北海处
					unitId = "156";
					kpUnitId = null;
				}else{
					unitId = null;
					kpUnitId = user.getOffice().getId();
				}
//					totalScore = examWeightsMainDao.getJorCJGBaseSumScore(examType,unitId,kpUnitId);//基础分
				nowSocre = examWeightsMainDao.getJorCJGBaseSumScore(examType,unitId,null);//基础分
			}
		}catch (Exception e){
			nowSocre = 100.0;
			e.printStackTrace();
		}

		// 防止该单位未在权重表进行分配，导致出现空值异常
		if(nowSocre==null){
			nowSocre = 0.0;
			resultMap.put("cgInfo","该单位所采用的权重制，未在工作权重设置模块进行分配,系统无法判断采用那套算法，请绩效办账号在工作权重设置模块进行调整。");
			return resultMap;
		}

		Double nowAddScore = 0.0;//当前加分
		Double nowDecScore = 0.0;//当前扣分
		if(infoMapList!=null && infoMapList.size()>0){
			for(Map<String,Object> tempMap : infoMapList){
				String modelType = (String) tempMap.get("modelType");
				Map<String,Object> itempMap = new HashMap<>();
				itempMap.put("valueType",tempMap.get("valueType"));
				itempMap.put("value",tempMap.get("value"));
				itempMap.put("workName",tempMap.get("workName"));
				itempMap.put("weight",tempMap.get("weight"));
				switch (modelType){
					case "0":
						if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
							if("2".equals(tempMap.get("workName"))&&("1".equals(examType)||"3".equals(examType))){
								//工作项为高铁安保：2 且当前考评类别为局考处
								zdList.add(itempMap);
								zdweightSet.add(tempMap.get("workName").toString());
							}else{
								cgList.add(itempMap);
								cgweightSet.add(tempMap.get("workName").toString());
							}
						}else{
							cgList.add(itempMap);
						}
						break;
					case "1":
						zdList.add(itempMap);
						if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
							zdweightSet.add(tempMap.get("workName").toString());
						}
						break;
					case "2":
						addList.add(itempMap);
						if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
							addweightSet.add(tempMap.get("workName").toString());
						}
						break;
					case "3":
						decList.add(itempMap);
						if(tempMap.get("workName")!=null && tempMap.get("workName") !=""){
							decweightSet.add(tempMap.get("workName").toString());
						}
						break;
					default:
						break;
				}
			}
			//常规
			if(cgweightSet!=null && cgweightSet.size()>0){
				if(cgList!=null && cgList.size()>0){
					Map<String,Object> infoMap  = this.getItemLabelScoreInfoJKC("常规业务",cgList,cgweightSet,examType);
						/*resultMap.put("leftTitle",cgStr.toString());
						resultMap.put("addScore",addScore);
						resultMap.put("decScore",decScore);*/
					resultMap.put("cgInfo",infoMap.get("leftTitle"));
					nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
					nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
				}
			}
			//重点
			if(zdweightSet!=null && zdweightSet.size()>0) {
				if (zdList != null && zdList.size() > 0) {
					Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("重点工作", zdList, zdweightSet,examType);
					resultMap.put("zdInfo",infoMap.get("leftTitle"));
					nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
					nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
				}
			}
			//公共加分
			if(addweightSet!=null && addweightSet.size()>0) {
				if (addList != null && addList.size() > 0) {
					Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("公共加分", addList, addweightSet,examType);
					//resultMap.put("addInfo", info);
					resultMap.put("addInfo",infoMap.get("leftTitle"));
					nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
					nowDecScore += Double.valueOf(infoMap.get("decScore").toString());
				}
			}
			//公共扣分
			if(decweightSet!=null && decweightSet.size()>0) {
				if (decList != null && decList.size() > 0) {
					Map<String,Object> infoMap = this.getItemLabelScoreInfoJKC("公共扣分", decList, decweightSet,examType);
					//resultMap.put("decInfo", info);
					resultMap.put("decInfo",infoMap.get("leftTitle"));
					nowAddScore += Double.valueOf(infoMap.get("addScore").toString());
					nowDecScore += Double.valueOf(infoMap.get("decScore").toString());

				}
			}
			nowDecScore = (double) Math.round(nowDecScore * 100) / 100;
			nowAddScore = (double) Math.round(nowAddScore * 100) / 100;
			if("1".equals(examWorkflow.getExamType())){
				//局考处
				if(resultMap.get("cgInfo")!=null){
					//resultMap.put("cgInfo","当前得分："+(nowSocre-nowDecScore+nowAddScore)+"  加分："+nowAddScore+"   扣分："+nowDecScore+"  "+resultMap.get("cgInfo"));
					resultMap.put("cgInfo","当前得分："+resultMap.get("cgInfo"));
				}else{
					//resultMap.put("cgInfo","当前得分："+(nowSocre-nowDecScore+nowAddScore)+"  加分："+nowAddScore+"   扣分："+nowDecScore);
					resultMap.put("cgInfo","当前得分：");
				}
				if(resultMap.get("addInfo")==null){
					resultMap.put("addInfo","公共加分：0.0分");
				}
				if(resultMap.get("decInfo")==null){
					resultMap.put("decInfo","公共扣分：0.0分");
				}
				//resultMap.put("cgInfo","当前得分："+(100.0-cgScore-zdScore-decScore+addScore)+" "+resultMap.get("cgInfo"));
			}else{
				//其他考评类型权重得分制考评
				if(resultMap.get("addInfo")!=null){
					resultMap.put("addInfo",resultMap.get("addInfo")+"。");
					if(resultMap.get("decInfo")!=null){
						resultMap.put("decInfo",resultMap.get("decInfo")+"，");
					}
				}else{
					if(resultMap.get("decInfo")!=null){
						resultMap.put("decInfo",resultMap.get("decInfo")+"。");
					}
				}
				if(resultMap.get("cgInfo")!=null){
					resultMap.put("cgInfo","当前得分："+ keepTwoDecimal(nowSocre-nowDecScore+nowAddScore) +"分；  加分："+nowAddScore+"   扣分："+nowDecScore+"。其中，"+resultMap.get("cgInfo"));
				}else{
					resultMap.put("cgInfo","当前得分："+ keepTwoDecimal(nowSocre-nowDecScore+nowAddScore) +"分；  加分："+nowAddScore+"   扣分："+nowDecScore+"。");
					if(resultMap.get("zdInfo")!=null){
						resultMap.put("zdInfo","其中："+resultMap.get("zdInfo"));
					}
				}
				//resultMap.put("cgInfo","当前得分："+(100.0-cgScore-zdScore-decScore+addScore)+" "+resultMap.get("cgInfo"));
			}

		}else{
			//List<Map<String,Object>> noSelectList = examWorkflowDatasDao.getHeadItemScoreNoSelectList(examWorkflow.getId(),userId);
			if("3".equals(examWorkflow.getExamType()) || "2".equals(examWorkflow.getExamType()) || "4".equals(examWorkflow.getExamType())){
				resultMap.put("cgInfo","当前得分："+nowSocre+"分");
				resultMap.put("addInfo","加分："+nowAddScore+"。");
				resultMap.put("decInfo","扣分："+nowDecScore);
			}else{
				//局考处默认显示100
				resultMap.put("cgInfo","当前得分：100分");
				resultMap.put("addInfo","公共加分：0.0分");
				resultMap.put("decInfo","公共扣分：0.0分");
			}
			//resultMap.put("addInfo","加分："+nowAddScore);
			//resultMap.put("decInfo","扣分："+nowDecScore);
		}
		return resultMap;
	}

	//左上角得分情况  局考处/处考派出所
	public Map<String,Object> getItemLabelScoreInfoJKC(String modelType,List<Map<String,Object>> mapList,Set<String> stringSet,String examType){
		Map<String,Object> resultMap = new HashMap<>();
		Double decScore = 0.0;//扣分
		Double addScore = 0.0;//加分
		StringBuffer cgStr = new StringBuffer();
		if("公共加分".equals(modelType)){
			double score = 0.0;
			cgStr.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
				Double value;
				if(StringUtils.isNotBlank((String)cgMap.get("value"))){
					value = Double.valueOf(cgMap.get("value").toString());
				}else {
					value = 0.0;
				}
				score += value;
			}
			addScore += score;
			cgStr.append(this.keepTwoDecimal(score)+"分");
		}
		else if("公共扣分".equals(modelType)){
			double score = 0.0;
			cgStr.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
				Integer valueType = (Integer) cgMap.get("valueType");
				Double value;
				if(StringUtils.isNotBlank((String)cgMap.get("value"))){
					value = Double.valueOf(cgMap.get("value").toString());
				}else {
					value = 0.0;
				}
				score += value;
			}
			decScore += score;
			cgStr.append(this.keepTwoDecimal(score)+"分");
		}
		else if("重点工作".equals(modelType)){
			//3.11 王俊宇微信提出   不用再以 重点工作：xxx工作得分xx来显示了。直接xx工作得分
			//cgStr.append(modelType+": (");
            Double hunderScore = 100.0;
			for(String workName : stringSet){
				//高铁安保，重点工作，但分值算法与常规业务工作项一致，根据权重求值
				Integer isAddItem = examScoreWorkItemDao.getInfoCount(workName, examType);//查询是否为得分制工作项
			    if ("2".equals(workName)){
                    String workNameLabel = DictUtils.getDictLabels(workName,"exam_weigths","");
                    cgStr.append(workNameLabel+"：");
                    Double score = 100.0;
                    if(isAddItem>0){
                    	score = 0.0;
					}
                    Double weightScore = null;
                    for(Map<String,Object> map : mapList){
                        String cgWorkName = (String) map.get("workName");
                        if(cgWorkName != null && cgWorkName != ""){
                            if(cgWorkName.equals(workName)){
                                if(map.get("weight")!= null ){
                                    weightScore =(Double) map.get("weight");
                                }
                                Integer valueType = (Integer) map.get("valueType");
                                Double value;
                                if(StringUtils.isNotBlank((String)map.get("value"))){
                                    value = Double.valueOf(map.get("value").toString());
                                    if(valueType!=null){
                                        if(valueType==1){
                                            score += value;
                                        }else if(valueType == -1){
                                            score -= value;
                                        }
                                    }else{
                                        score += 0.0;
                                    }
                                }
                            }
                        }
                    }
                    if(weightScore!=null && weightScore!=0.0){
						score = score<0?0:score;//2.23 权重分设置问题，应当扣完为止，不出现负数
                        hunderScore = this.keepTwoDecimal(score);
                        if(isAddItem>0){
							addScore += score;
							decScore += 0.0;
						}else{
							score = (weightScore/100.0) * score;
							addScore += score-weightScore<0?0.0 : score-weightScore;
							decScore += weightScore-score>0?weightScore-score : 0.0;
						}
                    }
					if(isAddItem>0){
						//cgStr.append(hunderScore+"分;");
						cgStr.append(this.keepTwoDecimal(score)+"分，");
					}else{
						cgStr.append(this.keepTwoDecimal(hunderScore)+"分,");
						cgStr.append("权重得分:"+this.keepTwoDecimal(score)+"分；");
					}
                }
			    else{
                    String workNameLabel = DictUtils.getDictLabels(workName,"exam_weigths","");
                    cgStr.append(workNameLabel+"：");
                    Double score = null;
                    if(isAddItem>0){
                    	score = 0.0;
					}
                    Double weight = null;
                    for(Map<String,Object> cgMap : mapList){
                        String cgWorkName = (String) cgMap.get("workName");
                        if(cgWorkName != null && cgWorkName != ""){
                            if(cgWorkName.equals(workName)){
                                if(score==null){
                                    if(cgMap.get("weight")!= null ){
                                        weight = (Double) cgMap.get("weight");
                                        score =(Double) cgMap.get("weight");
                                    }else{
                                        score = 100.0;
                                    }
                                }
                                Integer valueType = (Integer) cgMap.get("valueType");
                                Double value;
                                if(StringUtils.isNotBlank((String)cgMap.get("value"))){
                                    value = Double.valueOf(cgMap.get("value").toString());
                                    if(valueType!=null){
                                        if(valueType==1){
                                            score += value;
                                        }else if(valueType == -1){
                                            score -= value;
                                        }
                                    }else{
                                        score += 0.0;
                                    }
                                }
                            }
                        }
                    }
                    //正常重点工作直接由权重分值往下扣即可（高铁安保除外）
					if(isAddItem>0){
						if(score<0){
							score = 0.0;
						}
						addScore += score;
						decScore += 0.0;
					}else{
						if(weight!=null && score<0){
							score = 0.0;
							decScore += weight;
							addScore += 0.0;
						}else{
							addScore += weight-score<0?score-weight:0.0;
							decScore += weight-score>0?weight-score:0.0;
						}
					}
                    //cgStr.append(score+"分 ");
                    cgStr.append(this.keepTwoDecimal(score)+"分，");
                }

			}
			//3.11 王俊宇微信提出   不用再以 重点工作：xxx工作得分xx来显示了。直接xx工作得分
			//cgStr.append(")");
		}
		else{//常规业务
			Double hunderScore = 100.0;
			//3.11 王俊宇微信提出   不用再以 重点工作：xxx工作得分xx来显示了。直接xx工作得分
			//cgStr.append(modelType+": (");
			for(String workName : stringSet){
				Integer isAddItem = examScoreWorkItemDao.getInfoCount(workName, examType);//查询是否为得分制工作项
				String workNameLabel = DictUtils.getDictLabels(workName,"exam_weigths","");
				cgStr.append(workNameLabel+"：");
				Double score = 100.0;
				if(isAddItem>0){
					score = 0.0;
				}
				Double weightScore = null;
				for(Map<String,Object> map : mapList){
					String cgWorkName = (String) map.get("workName");
					if(cgWorkName != null && cgWorkName != ""){
						if(cgWorkName.equals(workName)){
							if(map.get("weight")!= null ){
								weightScore =(Double) map.get("weight");
							}
							Integer valueType = (Integer) map.get("valueType");
							Double value;
							if(StringUtils.isNotBlank((String)map.get("value"))){
								value = Double.valueOf(map.get("value").toString());
								if(valueType!=null){
									if(valueType==1){
										score += value;
									}else if(valueType == -1){
										score -= value;
									}
								}else{
									score += 0.0;
								}
							}else{
								score+=0.0;
							}
						}
					}
				}
				if(weightScore!=null && weightScore!=0.0){
					score = score<0?0:score;//2.23 权重分设置问题，应当扣完为止，不出现负数
					hunderScore = this.keepTwoDecimal(score);
					if(isAddItem>0){
						addScore += score;
						decScore += 0.0;
					}else{
						score = (weightScore/100.0) * score;
						addScore += score-weightScore<0?0.0 : score-weightScore;
						decScore += weightScore-score>0?weightScore-score : 0.0;
					}
				}
				//cgStr.append(score+"分 ");
				if(isAddItem>0){
					//cgStr.append(hunderScore+"分;");
					cgStr.append(this.keepTwoDecimal(score)+"分；");
				}else{
					cgStr.append(this.keepTwoDecimal(hunderScore)+"分,");
					cgStr.append("权重得分:"+this.keepTwoDecimal(score)+"分；");
				}

			}
			//3.11 王俊宇微信提出   不用再以 重点工作：xxx工作得分xx来显示了。直接xx工作得分
			//cgStr.append(");");
		}
		resultMap.put("leftTitle",cgStr.toString());
		resultMap.put("addScore",addScore);
		resultMap.put("decScore",decScore);
		return resultMap;
	}

	/*//左上角得分情况  处考队所
	public Map<String,Object> getItemLabelScoreInfoNew(String modelType,List<Map<String,Object>> mapList,Set<String> stringSet){
		Map<String,Object> resultMap = new HashMap<>();
		Double decScore = 0.0;//扣分
		Double addScore = 0.0;//加分
		StringBuffer cgStr = new StringBuffer();
		if("公共加分".equals(modelType)){
			double score = 0.0;
			cgStr.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
				Double value;
				if(StringUtils.isNotBlank((String)cgMap.get("value"))){
					value = Double.valueOf(cgMap.get("value").toString());
				}else {
					value = 0.0;
				}
				score += value;
			}
			addScore = score;
			cgStr.append(score+"分 ");
		}else if("公共扣分".equals(modelType)){
			double score = 0.0;
			cgStr.append(modelType+": ");
			for(Map<String,Object> cgMap : mapList){
				Integer valueType = (Integer) cgMap.get("valueType");
				Double value;
				if(StringUtils.isNotBlank((String)cgMap.get("value"))){
					value = Double.valueOf(cgMap.get("value").toString());
				}else {
					value = 0.0;
				}
				score += value;
			}
			decScore = score;
			cgStr.append(score+"分 ");
		} else if("重点工作".equals(modelType)){
			cgStr.append(modelType+": (");
			for(String workName : stringSet){
				String workNameLabel = DictUtils.getDictLabels(workName,"exam_weigths","");
				cgStr.append(workNameLabel+":");
				Double score = null;
				Double weight = null;
				for(Map<String,Object> cgMap : mapList){
					String cgWorkName = (String) cgMap.get("workName");
					if(cgWorkName != null && cgWorkName != ""){
						if(cgWorkName.equals(workName)){
							if(score==null){
								if(cgMap.get("weight")!= null ){
									weight = Double.valueOf ((Integer) cgMap.get("weight"));
									score =Double.valueOf ((Integer) cgMap.get("weight"));
								}else{
									score = 100.0;
								}
							}
							Integer valueType = (Integer) cgMap.get("valueType");
							Double value;
							if(StringUtils.isNotBlank((String)cgMap.get("value"))){
								value = Double.valueOf(cgMap.get("value").toString());
								if(valueType!=null){
									if(valueType==1){
										score += value;
									}else if(valueType == -1){
										score -= value;
									}
								}else{
									score += 0.0;
								}
							}
						}
					}
				}
				if(weight!=null && score<0){
					score = 0.0;
					decScore += weight;
					addScore += 0.0;
				}else{
					decScore += weight-score<0?0:weight-score;
					addScore += score-weight<0?0:weight-score;
				}
				//cgStr.append(score+"分 ");
				cgStr.append(String.format("%.2f",score)+"分 ");
			}
			cgStr.append(")");
		}else{//常规业务
			cgStr.append(modelType+": (");
			for(String workName : stringSet){
				String workNameLabel = DictUtils.getDictLabels(workName,"exam_weigths","");
				cgStr.append(workNameLabel+":");
				Double score = 100.0;
				Double weightScore = null;
				for(Map<String,Object> map : mapList){
					String cgWorkName = (String) map.get("workName");
					if(cgWorkName != null && cgWorkName != ""){
						if(cgWorkName.equals(workName)){
							if(map.get("weight")!= null ){
								weightScore =Double.valueOf ((Integer) map.get("weight"));
							}
							Integer valueType = (Integer) map.get("valueType");
							Double value;
							if(StringUtils.isNotBlank((String)map.get("value"))){
								value = Double.valueOf(map.get("value").toString());
								if(valueType!=null){
									if(valueType==1){
										score += value;
									}else if(valueType == -1){
										score -= value;
									}
								}else{
									score += 0.0;
								}
							}
						}
					}
				}
				if(weightScore!=null && weightScore!=0.0){
					score = (weightScore/100.0) * score;
					addScore = score-weightScore<0?0.0 : score-weightScore;
					decScore = weightScore-score;
				}
				//cgStr.append(score+"分 ");
				cgStr.append(String.format("%.2f",score)+"分 ");
			}
			cgStr.append(")");
		}
		resultMap.put("leftTitle",cgStr.toString());
		resultMap.put("addScore",addScore);
		resultMap.put("decScore",decScore);
		return resultMap;
	}
	//生成表格-处考队所
	public Map<String, Object> getIngChuKaoDuisuo(String workflowId, Integer status) {
    	Map<String,Object> resultMap = new HashMap<>();
    	User user =UserUtils.getUser();
    	//判断当前用户是否为填报人
		int count = examWorkflowDatasDao.findCountByWorkflowIdFillPersonId(workflowId, user.getId());
		List<Map<String,String>> pcsList;
		if(count>0){
			pcsList = new ArrayList<>();
			Map<String,String> pcsMap = new HashMap<>();
			pcsMap.put("fillPerson",user.getName());
			pcsMap.put("fillPersonId",user.getId());
			pcsMap.put("officeName",user.getOffice().getName());
			pcsMap.put("officeId",user.getOffice().getId());
			pcsList.add(pcsMap);
		}else{
			pcsList =  examWorkflowDatasDao.getPcsList(workflowId);
		}
		ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
		User createBy = examWorkflow.getCreateBy();
		User updateBy = examWorkflow.getUpdateBy();
		User createUser = UserUtils.get(createBy.getId());
		User updateUser = UserUtils.get(updateBy.getId());
		List<Map<String,Object>> workNameList;
		if("34".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",34,") || "34".equals(createUser.getOffice().getParentId())
		|| "34".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",34,") || "34".equals(updateUser.getOffice().getParentId())
		){
			//南宁处
			workNameList = examWeightsMainDao.getWorkNameListByChu("34",null);
		} else if("95".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",95,") || "95".equals(createUser.getOffice().getParentId())
				|| "95".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",95,") || "95".equals(updateUser.getOffice().getParentId())){
			//柳州处
			workNameList = examWeightsMainDao.getWorkNameListByChu("95",null);
		}else if("156".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",156,") || "156".equals(createUser.getOffice().getParentId())
				|| "156".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",156,") || "156".equals(updateUser.getOffice().getParentId())
		){
			//北海处
			workNameList = examWeightsMainDao.getWorkNameListByChu("156",null);
		}else{
			workNameList = new ArrayList<>();
		}
		List<Map<String,Object>> resultList = new ArrayList<>();
		int i = 1;
		if(pcsList!=null && pcsList.size()>0 && workNameList!=null && workNameList.size()>0){
			for(Map<String,String> tempPcsMap : pcsList){
				String fillPersonId = tempPcsMap.get("fillPersonId");//填报人id
				Map<String,Object> pcsScoreMap = new HashMap<>();
				pcsScoreMap.put("fillPersonName",tempPcsMap.get("fillPerson"));
				pcsScoreMap.put("fillPersonId",tempPcsMap.get("fillPersonId"));
				pcsScoreMap.put("pcsIndex",i);
				i++;
				Double totalScore = 0.0;//考核得分合计
				Double publicAddScore = 0.0;//公共加分
				Double publicDecScore = 0.0;//公共扣分
				for(Map<String,Object> tempWorkName : workNameList){
					String workName_value = (String) tempWorkName.get("value");
					String workName_label = (String) tempWorkName.get("label");
					Double weight = Double.valueOf((Integer) tempWorkName.get("weight"));
					pcsScoreMap.put("weight"+workName_label,weight);//设置权重
					pcsScoreMap.put(workName_label,weight);//设置权重
					List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.findScoresByPcsIdWorkName(workflowId,fillPersonId,workName_value);
					Double score;
					if("高铁安保".equals(workName_label) || "反恐防暴".equals(workName_label) || "党建工作".equals(workName_label) || "信息化建设".equals(workName_label)){
						score = weight;
					}else{
						score = 100.0;
					}
					if(examWorkflowDatasList != null && examWorkflowDatasList.size()>0) {
						if ("32".equals(workName_value)) {
							for (ExamWorkflowDatas workflowDatas : examWorkflowDatasList) {

								if (workflowDatas.getValueType() != null ) {
									Integer valueType = Integer.valueOf(workflowDatas.getValueType());
									double scoreValue;
									try {
										scoreValue = Double.parseDouble(workflowDatas.getValue());
									}catch (Exception e){
										scoreValue = 0.0;
										ByteArrayOutputStream baos = new ByteArrayOutputStream();
										e.printStackTrace(new PrintStream(baos));
										logger.error(baos.toString());
										e.printStackTrace();
									}
									if (valueType == 1) {
										publicAddScore += scoreValue;
									} else if (valueType == -1) {
										publicDecScore -= scoreValue;
									}
								}
							}
							pcsScoreMap.put("Score公共加分", publicAddScore);
							pcsScoreMap.put("Score公共扣分", publicDecScore);
						} else {
							for (ExamWorkflowDatas workflowDatas : examWorkflowDatasList) {
								if (workflowDatas.getValueType() != null) {
									Integer valueType = Integer.valueOf(workflowDatas.getValueType());
									double scoreValue;
									try {
										scoreValue = Double.parseDouble(workflowDatas.getValue());
									}catch (Exception e){
										scoreValue = 0.0;
										ByteArrayOutputStream baos = new ByteArrayOutputStream();
										e.printStackTrace(new PrintStream(baos));
										logger.error(baos.toString());
										e.printStackTrace();
									}
									if (valueType == 1) {
										score += scoreValue;
									} else if (valueType == -1) {
										score -= scoreValue;
									}
								}
							}
							pcsScoreMap.put("Score" + workName_label, score);
							if("高铁安保".equals(workName_label) || "反恐防暴".equals(workName_label) || "党建工作".equals(workName_label) || "信息化建设".equals(workName_label)) {
								pcsScoreMap.put("weightScore" + workName_label, score);
								totalScore += score;
							}else{
								pcsScoreMap.put("weightScore" + workName_label, score*(weight/100));
								totalScore +=  score*(weight/100);
							}
						}
					}else{
						//没选
						pcsScoreMap.put("Score" + workName_label, 100);
						//权重
						if(!"32".equals(workName_value)){
							pcsScoreMap.put("weightScore" + workName_label, weight);
							totalScore += weight;
						}
						pcsScoreMap.put("Score公共加分", 0.0);
						pcsScoreMap.put("Score公共扣分", 0.0);
					}
				}
				totalScore = totalScore+publicAddScore-publicDecScore;
				pcsScoreMap.put("totalScore",totalScore);
				resultList.add(pcsScoreMap);
			}
		}
		resultMap.put("pcsResultList",resultList);
    	return resultMap;
	}*/

	/**
	 * 左上角得分情况，非权重制得分
	 * @param examType 考评类别
	 * @param examWorkflow  考评实体类
	 * @param userId 当前被考评对象userId或者当前用户userId
	 */
	public Map<String,Object> getItemLabelScoreInfoNoWeight(String examType,ExamWorkflow examWorkflow,String userId){
		Map<String,Object> resultMap = new HashMap<>();
		String basicScore = DictUtils.getDictValue(examType,"exam_basicScore","100");
		Double nowScore;//当前得分
		try {
			nowScore = Double.valueOf(basicScore);
		}catch (Exception e){
			logger.error(getExceptionInfo(e));
			e.printStackTrace();
			nowScore = 100.0;
		}
		Double nowDecScore = 0.0;//当前扣分
		Double nowAddScore = 0.0;//当前加分
		List<Map<String,Object>> infoMapList = examWorkflowDatasDao.getHeadItemScoreList(examWorkflow.getId(),userId);
		if(infoMapList!=null && infoMapList.size()>0){
			for(Map<String,Object> tempMap : infoMapList){
				if(tempMap.get("valueType")!=null && tempMap.get("valueType").toString()!=""){
					Integer valueType = Integer.valueOf(tempMap.get("valueType").toString());//1 加分/ -1  扣分
					Double value;
					try {
						value = Double.parseDouble(tempMap.get("value").toString());
					}catch (Exception e){
						value = 0.0;
						logger.error(getExceptionInfo(e));
						e.printStackTrace();
					}

					/*if(tempMap.get("value")!=null && tempMap.get("value") != ""){
						value = Double.valueOf(tempMap.get("value").toString());//分值
					}else{
						value = 0.0;
					}*/

					if(valueType==1){
						nowAddScore += value;
					}else{
						nowDecScore += value;
					}
				}
			}
			nowAddScore = keepTwoDecimal(nowAddScore);
			nowDecScore = keepTwoDecimal(nowDecScore);
			nowScore = nowScore + nowAddScore - nowDecScore;
			nowScore = keepTwoDecimal(nowScore);
			resultMap.put("cgInfo","当前得分："+nowScore);
			resultMap.put("addInfo","加分："+nowAddScore);
			resultMap.put("decInfo","扣分："+nowDecScore);
		}else{
			resultMap.put("cgInfo","当前得分："+nowScore);
			resultMap.put("addInfo","加分："+nowAddScore);
			resultMap.put("decInfo","扣分："+nowDecScore);
		}
		return resultMap;
	}
	/*
	 * 生成表格-处考队所 - 权重不同
	 * */
	public Map<String, Object> getIngChuKaoDuisuo2(String workflowId, Integer status,String httpFillPersonId) {
		Map<String,Object> resultMap = new HashMap<>();
		User user =UserUtils.getUser();
		if(httpFillPersonId!=null){
			user = UserUtils.get(httpFillPersonId);
		}
		//判断当前用户是否为填报人
		int count = examWorkflowDatasDao.findCountByWorkflowIdFillPersonId(workflowId, user.getId());
		List<Map<String,String>> pcsList;
		String unitId = null;
		String baseFillPersonId = null;
		if(count>0){
			pcsList = new ArrayList<>();
			Map<String,String> pcsMap = new HashMap<>();
			pcsMap.put("fillPerson",user.getName());
			pcsMap.put("fillPersonId",user.getId());
			pcsMap.put("officeName",user.getOffice().getName());
			pcsMap.put("officeId",user.getOffice().getId());
			pcsList.add(pcsMap);
			unitId = user.getOffice().getId();
			baseFillPersonId = user.getId();
		}else{
			pcsList =  examWorkflowDatasDao.getExamUnitList(workflowId);
			if(pcsList!=null&& pcsList.size()>0){
				try {
					for(Map<String,String> pcs : pcsList){
						if(pcs!=null && pcs.get("fillPerson")!=null && pcs.get("fillPerson").contains("派出所")){
							unitId = pcs.get("officeId");
							baseFillPersonId = pcs.get("fillPersonId");
							break;
						}
					}
					if(unitId==null || baseFillPersonId==null){
						unitId = pcsList.get(0).get("officeId");
						baseFillPersonId = pcsList.get(0).get("fillPersonId");
					}
				}catch (Exception e){
					logger.error(getExceptionInfo(e));
					e.printStackTrace();
					unitId = pcsList.get(0).get("officeId");
					baseFillPersonId = pcsList.get(0).get("fillPersonId");
				}
			}

		}
		ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
		User createBy = examWorkflow.getCreateBy();
		User updateBy = examWorkflow.getUpdateBy();
		User createUser = UserUtils.get(createBy.getId());
		User updateUser = UserUtils.get(updateBy.getId());
		List<Map<String,Object>> workNameList;
		User fillPersonUser = UserUtils.get(baseFillPersonId);
		if(fillPersonUser==null){
			fillPersonUser = UserUtils.getUser();
		}
		if("34".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",34,") || "34".equals(createUser.getOffice().getParentId())
				|| "34".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",34,") || "34".equals(updateUser.getOffice().getParentId())
				|| "34".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",34,") || "34".equals(fillPersonUser.getOffice().getParentId())
		){
			//南宁处
			workNameList = examWeightsMainDao.getWorkNameListByChu("34",unitId);
		} else if("95".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",95,") || "95".equals(createUser.getOffice().getParentId())
				|| "95".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",95,") || "95".equals(updateUser.getOffice().getParentId())
				|| "95".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",95,") || "95".equals(fillPersonUser.getOffice().getParentId())
		){
			//柳州处
			workNameList = examWeightsMainDao.getWorkNameListByChu("95",unitId);
		}else if("156".equals(createUser.getOffice().getId()) || createUser.getOffice().getParentIds().contains(",156,") || "156".equals(createUser.getOffice().getParentId())
				|| "156".equals(updateUser.getOffice().getId()) || updateUser.getOffice().getParentIds().contains(",156,") || "156".equals(updateUser.getOffice().getParentId())
				|| "156".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",156,") || "156".equals(fillPersonUser.getOffice().getParentId())
		){
			//北海处
//			workNameList = examWeightsMainDao.getWorkNameListByChu("156",null);
			workNameList = examWeightsMainDao.getWorkNameListByChu("156",unitId);//21年7月19，北海处绩效办、局绩效办负责人提出，北海处开启两类派出所考评（普铁、高铁）
		}else{
			workNameList = new ArrayList<>();
		}
		List<Map<String,Object>> resultList = new ArrayList<>();
		int i = 1;
		List<String> zdNameList=new ArrayList<>();
		List<Double> zdWeightList=new ArrayList<>();
		List<String> cgNameList=new ArrayList<>();
		List<Double> cgWeightList=new ArrayList<>();
		List<String> cgScoreNameList=new ArrayList<>();
		List<String> zdScoreNameList=new ArrayList<>();
		if(pcsList!=null && pcsList.size()>0 && workNameList!=null && workNameList.size()>0){
			for(Map<String,Object> workNameMap : workNameList){
				String workNameType = (String) workNameMap.get("workNameType");//0 常规业务  1 重点工作  3 公共加扣分
				if(workNameType.equals("0")){
					cgNameList.add((String) workNameMap.get("label"));
					cgScoreNameList.add("Score"+(String) workNameMap.get("label"));
					cgWeightList.add((Double) workNameMap.get("weight"));
					continue;
				}
				if(workNameType.equals("1")){
					zdNameList.add((String) workNameMap.get("label"));
                    zdScoreNameList.add("Score"+(String) workNameMap.get("label"));
					zdWeightList.add((Double) workNameMap.get("weight"));
					continue;
				}
			}
			resultMap.put("cgNameList",cgNameList);
			resultMap.put("cgScoreNameList",cgScoreNameList);
			resultMap.put("zdScoreNameList",zdScoreNameList);
			resultMap.put("cgWeightList",cgWeightList);
			resultMap.put("zdNameList",zdNameList);
			resultMap.put("zdWeightList",zdWeightList);
			for(Map<String,String> tempPcsMap : pcsList){
				String fillPersonId = tempPcsMap.get("fillPersonId");//填报人id
				Map<String,Object> pcsScoreMap = new HashMap<>();
				pcsScoreMap.put("fillPersonName",tempPcsMap.get("fillPerson"));
				pcsScoreMap.put("fillPersonId",tempPcsMap.get("fillPersonId"));
				pcsScoreMap.put("pcsIndex",i);
				i++;
				Double totalScore = 0.0;//考核得分合计
				Double publicAddScore = 0.0;//公共加分
				Double publicDecScore = 0.0;//公共扣分
				for(Map<String,Object> tempWorkName : workNameList){
					String workName_value = (String) tempWorkName.get("value");
					String workName_label = (String) tempWorkName.get("label");
					String workName_type = (String) tempWorkName.get("workNameType");
					Double weight = (Double) tempWorkName.get("weight");
					pcsScoreMap.put("weight"+workName_label,weight);//设置权重
					pcsScoreMap.put(workName_label,weight);//设置权重
					List<ExamWorkflowDatas> examWorkflowDatasList = examWorkflowDatasDao.findScoresByPcsIdWorkName(workflowId,fillPersonId,workName_value);
					int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName_value,"3");
					Double score;
					if(isAddScoreItem>0){
						score = 0.0;
					}else{
						if("1".equals(workName_type)){
							if("2".equals(workName_value)){
								//高铁安保
								score = 100.0;
							}else{
								score = weight;
							}
						}else{
							score = 100.0;
						}
					}

					if(examWorkflowDatasList != null && examWorkflowDatasList.size()>0) {
						if ("32".equals(workName_value)) {
							for (ExamWorkflowDatas workflowDatas : examWorkflowDatasList) {

								if (workflowDatas.getValueType() != null ) {
									Integer valueType = Integer.valueOf(workflowDatas.getValueType());
									double scoreValue;
									try {
										scoreValue = Double.parseDouble(workflowDatas.getValue());
									}catch (Exception e){
										scoreValue = 0.0;
										logger.error(getExceptionInfo(e));
										e.printStackTrace();
									}
									if (valueType == 1) {
										publicAddScore += scoreValue;
									} else if (valueType == -1) {
										publicDecScore += scoreValue;
									}
								}
							}
							pcsScoreMap.put("Score公共加分", keepTwoDecimal(publicAddScore));
							pcsScoreMap.put("Score公共扣分", keepTwoDecimal(publicDecScore));
						} else {
							for (ExamWorkflowDatas workflowDatas : examWorkflowDatasList) {
								if (workflowDatas.getValueType() != null ) {
									Integer valueType = Integer.valueOf(workflowDatas.getValueType());
									double scoreValue;
									/*if (StringUtils.isNotBlank(workflowDatas.getValue())) {*/
										try {
											scoreValue = Double.parseDouble(workflowDatas.getValue());
										}catch (Exception e){
											scoreValue = 0.0;
											logger.error(getExceptionInfo(e));
											e.printStackTrace();
										}
									/*} else {
										scoreValue = 0.0;
									}*/
									if (valueType == 1) {
										score += scoreValue;
									} else if (valueType == -1) {
										score -= scoreValue;
									}
								}
							}
							if("1".equals(workName_type)) {
							    if("2".equals(workName_value)){
							        //高铁安保
									if(score<0){
										pcsScoreMap.put(workName_label, 0.0);
										pcsScoreMap.put("Score"+workName_label, 0.0);//考核原始分
										totalScore +=  0.0;
									}else{
										if(isAddScoreItem>0){
											//得分制工作项
											pcsScoreMap.put(workName_label, keepTwoDecimal(score));
											pcsScoreMap.put("Score"+workName_label, keepTwoDecimal(score));//考核原始分
											totalScore +=  score;
										}else{
											//pcsScoreMap.put(workName_label, score*(weight/100));
											pcsScoreMap.put(workName_label, keepTwoDecimal(score*(weight/100)));
											pcsScoreMap.put("Score"+workName_label, keepTwoDecimal(score));//考核原始分
											//totalScore +=  score*(weight/100);
											totalScore +=  keepTwoDecimal(score*(weight/100));
										}
									}
							    }else{
                                    if(score<0){
                                        pcsScoreMap.put(workName_label, 0);
                                        pcsScoreMap.put("Score"+workName_label, 0);//考核原始分
                                        totalScore += 0;
                                    }else{
										if(isAddScoreItem>0){
											//得分制工作项
											pcsScoreMap.put(workName_label, keepTwoDecimal(score));
											pcsScoreMap.put("Score"+workName_label, keepTwoDecimal(score));//考核原始分
											totalScore +=  keepTwoDecimal(score);
										}else{
											pcsScoreMap.put(workName_label, keepTwoDecimal(score));
											pcsScoreMap.put("Score"+workName_label, weight);//考核原始分
											totalScore += keepTwoDecimal(score);
										}
                                    }
                                }
							}else{
								if(score<0){
									pcsScoreMap.put(workName_label, 0.0);
									pcsScoreMap.put("Score"+workName_label, 0.0);//百分
									totalScore +=  0.0;
								}else{
									if(isAddScoreItem>0){
										//得分制工作项
										pcsScoreMap.put(workName_label, keepTwoDecimal(score));
										pcsScoreMap.put("Score"+workName_label, keepTwoDecimal(score));//考核原始分
										totalScore +=  keepTwoDecimal(score);
									}else{
										//pcsScoreMap.put(workName_label, score*(weight/100));
										pcsScoreMap.put(workName_label, keepTwoDecimal(score*(weight/100)));
										pcsScoreMap.put("Score"+workName_label, keepTwoDecimal(score));//百分
										//totalScore +=  score*(weight/100);
										//totalScore +=  (double) Math.round((score*(weight/100)) * 100) / 100;
										totalScore +=  keepTwoDecimal(score*(weight/100));
									}
								}
							}
						}
					}else{
						//没选
						if(isAddScoreItem>0){
							pcsScoreMap.put("Score"+workName_label, 0);
						}else{
							pcsScoreMap.put("Score"+workName_label, 100);
						}
						//权重
						if(!"32".equals(workName_value)){
							if(isAddScoreItem>0){
								if("1".equals(workName_type) && !"2".equals(workName_value)){
									pcsScoreMap.put("Score"+workName_label, 0.0);
								}
								pcsScoreMap.put(workName_label, 0.0);
								totalScore += 0.0;
							}else{
								if("1".equals(workName_type) && !"2".equals(workName_value)){
									pcsScoreMap.put("Score"+workName_label, weight);
								}
								pcsScoreMap.put(workName_label, weight);
								totalScore += weight;
							}

						}
						if(publicAddScore==0.0){
							pcsScoreMap.put("Score公共加分", 0.0);
						}
						if(publicDecScore == 0.0){
							pcsScoreMap.put("Score公共扣分", 0.0);
						}
					}
				}
				totalScore = totalScore+publicAddScore-publicDecScore;
				pcsScoreMap.put("totalScore",keepTwoDecimal(totalScore));
				resultList.add(pcsScoreMap);
			}
		}
		resultMap.put("pcsResultList",resultList);
		return resultMap;
	}


	/**
	 * 处考队所-生成表格-详情
	 * @param workflowId
	 * @param fillPersonId
	 */
	public Map<String, Object> getCkDSResultMap(String workflowId, String fillPersonId) {
		Map<String,Object> resultMap = new HashMap<>();
		User user = UserUtils.get(fillPersonId);
		resultMap.put("fillPersonName",user.getOffice().getName());
		String kpUnitId = null;//自评人员单位id
		String unitId = null;
		if("34".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",34,") || "34".equals(user.getOffice().getParentId())){
			//南宁处
			unitId = "34";
			kpUnitId = user.getOffice().getId();
		} else if("95".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",95,") || "95".equals(user.getOffice().getParentId())){
			//柳州处
			unitId = "95";
			kpUnitId = user.getOffice().getId();
		}else if("156".equals(user.getOffice().getId()) || user.getOffice().getParentIds().contains(",156,") || "156".equals(user.getOffice().getParentId())){
			//北海处
			unitId = "156";
			kpUnitId = user.getOffice().getId();//21年7月19，北海处绩效办、局绩效办负责人提出，北海处开启两类派出所考评（普铁、高铁）
		}else{
			unitId = null;
			kpUnitId = user.getOffice().getId();
		}
		//Integer sumZd = examWeightsMainDao.getZdWeight(unitId,"3",kpUnitId);//根据处id，考评类型，考评单位id 获取相应重点工作权重标准总分值
		Double sumZd = examWeightsMainDao.getZdOrCgWeight(unitId,"3",kpUnitId,"1");//根据处id，考评类型，考评单位id 工作类别 获取相应重点工作权重标准总分值
		sumZd = sumZd==null?0.0:sumZd;
		Double sumZd_do = Double.valueOf(sumZd);
		//Integer sumCg = examWeightsMainDao.getCgWeight(unitId,"3",kpUnitId);//根据处id，考评类型，考评单位id 获取相应常规业务权重标准总分值
		Double sumCg = examWeightsMainDao.getZdOrCgWeight(unitId,"3",kpUnitId,"0");//根据处id，考评类型，考评单位id 工作类别 获取相应常规业务权重标准总分值
		sumCg = sumCg==null?0.0:sumCg;
		Double sumCg_do = Double.valueOf(sumCg);
		Double zdDec = 0.0;//重点扣分
		Double cgDec = 0.0;//常规扣分
		Double publicADD = 0.0;//公共扣分
		Double publicDec = 0.0;//公共扣分
		Double totalScore = 0.0;//公共扣分
		StringBuffer pulicDecItems = new StringBuffer();//公共扣分事项
		StringBuffer pulicAddItems = new StringBuffer();//公共加分事项
		StringBuffer zdDecItems = new StringBuffer();//重点扣分事项
		StringBuffer cgDecItems = new StringBuffer();//常规扣分事项
		List<ExamWorkflowDatas> cgExamWorkflowDatasList = examWorkflowDatasDao.findCkPcsScoreDetail(workflowId,fillPersonId,"0");//常规
		List<ExamWorkflowDatas> zdExamWorkflowDatasList = examWorkflowDatasDao.findCkPcsScoreDetail(workflowId,fillPersonId,"1");//重点
		List<ExamWorkflowDatas> addExamWorkflowDatasList = examWorkflowDatasDao.findCkPcsScoreDetail(workflowId,fillPersonId,"2");//加分
		List<ExamWorkflowDatas> decExamWorkflowDatasList = examWorkflowDatasDao.findCkPcsScoreDetail(workflowId,fillPersonId,"3");//扣分
		//常规
		if(cgExamWorkflowDatasList!=null && cgExamWorkflowDatasList.size()>0){
			Set<String> workNameSet = new HashSet<>();
			for (ExamWorkflowDatas examWorkflowDatas : cgExamWorkflowDatasList){
				workNameSet.add(examWorkflowDatas.getWorkName());
			}
			/*for (ExamWorkflowDatas examWorkflowDatas : cgExamWorkflowDatasList){
				if(examWorkflowDatas.getWeight()!=null){
					Double weightScore = Double.valueOf(examWorkflowDatas.getWeight());
					if (examWorkflowDatas.getValueType() != null ) {
						Integer valueType = Integer.valueOf(examWorkflowDatas.getValueType());
						double scoreValue;
						if (examWorkflowDatas.getValue() != null && examWorkflowDatas.getValue() != "") {
						try {
							scoreValue = Double.parseDouble(examWorkflowDatas.getValue());
						}catch (Exception e){
							scoreValue = 0.0;
							logger.error(getExceptionInfo(e));
							e.printStackTrace();
						}

					} else {
						scoreValue = 0.0;
					}
						if(weightScore!=null && weightScore!=0.0){
							scoreValue = (weightScore/100.0) * scoreValue;
						}
						if (valueType == 1) {
							sumCg_do += scoreValue;
						} else if (valueType == -1) {
							cgDec += scoreValue;
							sumCg_do -= scoreValue;
							cgDecItems.append(examWorkflowDatas.getItems()+";");
						}
					}
				}

			}*/
			for(String workName : workNameSet){
				Double weight = null;
				Double deccgTempScore = 0.0;//扣分汇总
				Double addcgTempScore = 0.0;//加分
				int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName,"3");
				for (ExamWorkflowDatas examWorkflowDatas : cgExamWorkflowDatasList){
					if(StringUtils.isNotBlank(workName)){
						if(workName.equals(examWorkflowDatas.getWorkName())){
							if (weight==null){
								weight = Double.valueOf(examWorkflowDatas.getWeight());
							}
							if (examWorkflowDatas.getValueType() != null ) {
								Integer valueType = Integer.valueOf(examWorkflowDatas.getValueType());
								double scoreValue;
								try {
									scoreValue = Double.parseDouble(examWorkflowDatas.getValue());
								}catch (Exception e){
									scoreValue = 0.0;
									logger.error(getExceptionInfo(e));
									e.printStackTrace();
								}
								if (valueType == 1) {
									addcgTempScore += scoreValue;
								} else if (valueType == -1) {
									deccgTempScore += scoreValue;
									cgDecItems.append(examWorkflowDatas.getItems()+";");
								}
							}
						}
					}
				}
				if(weight!=null){
					if(isAddScoreItem>0){
						//得分制工作项
						if(deccgTempScore-addcgTempScore>0){
							//扣分大于加分
							sumCg_do += 0;
							cgDec += 0;
						}else{
							sumCg_do += keepTwoDecimal(addcgTempScore-deccgTempScore);
							cgDec += 0;
						}
					}else{
						if(100-(deccgTempScore-addcgTempScore)<0){
							sumCg_do -= weight;
							cgDec += weight;
						}else{
							sumCg_do += keepTwoDecimal((addcgTempScore-deccgTempScore) * (weight/100));
							cgDec += keepTwoDecimal((deccgTempScore-addcgTempScore) * (weight/100));
						}
					}

				}
			}
			resultMap.put("sumCg",sumCg_do);//得分
			resultMap.put("cgDec",cgDec);//扣分
			resultMap.put("cgDecItems",cgDecItems);//扣分理由
		}else{
			resultMap.put("sumCg",sumCg);
			resultMap.put("cgDec",0.0);
			resultMap.put("cgDecItems","无");
		}
		//重点
		if(zdExamWorkflowDatasList!=null && zdExamWorkflowDatasList.size()>0){
			Set<String> workNameSet = new HashSet<>();
			for (ExamWorkflowDatas examWorkflowDatas : zdExamWorkflowDatasList){
				workNameSet.add(examWorkflowDatas.getWorkName());
			}
			for(String workName : workNameSet){
			    Double weight = null;
				int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName,"3");
				Double deczdTempScore = 0.0;//扣分汇总
				Double addzdTempScore = 0.0;//加分
				for (ExamWorkflowDatas examWorkflowDatas : zdExamWorkflowDatasList){
					if(StringUtils.isNotBlank(workName)){
						if(workName.equals(examWorkflowDatas.getWorkName())){
							if (weight==null){
								weight = Double.valueOf(examWorkflowDatas.getWeight());
							}
							if (examWorkflowDatas.getValueType() != null ) {
								Integer valueType = Integer.valueOf(examWorkflowDatas.getValueType());
								double scoreValue;
								try {
									scoreValue = Double.parseDouble(examWorkflowDatas.getValue());
								}catch (Exception e){
									scoreValue = 0.0;
									logger.error(getExceptionInfo(e));
									e.printStackTrace();
								}
								if (valueType == 1) {
									addzdTempScore += scoreValue;
								} else if (valueType == -1) {
									deczdTempScore += scoreValue;
									zdDecItems.append(examWorkflowDatas.getItems()+";");
								}
							}
						}
					}
				}
				if(weight!=null){
                    if("2".equals(workName)){
						if(isAddScoreItem>0){
							//得分制工作项
							if(deczdTempScore-addzdTempScore>0){
								//扣分大于加分
								sumZd_do += 0;
								zdDec += 0;
							}else{
								sumZd_do += keepTwoDecimal(addzdTempScore-deczdTempScore);
								zdDec += 0;
							}
						}else{
							if(100-(deczdTempScore-addzdTempScore)<0){
								sumZd_do -= weight;
								zdDec += weight;
							}else{
								sumZd_do +=keepTwoDecimal( (addzdTempScore-deczdTempScore) * (weight/100));
								zdDec += keepTwoDecimal((deczdTempScore-addzdTempScore) * (weight/100));
							}
						}
                    }else{
						if(isAddScoreItem>0){
							//得分制工作项
							if(deczdTempScore-addzdTempScore>0){
								//扣分大于加分
								sumZd_do += 0;
								zdDec += 0;
							}else{
								sumZd_do += keepTwoDecimal(addzdTempScore-deczdTempScore);
								zdDec += 0;
							}
						}else{
							if(addzdTempScore-deczdTempScore<0){
								if((addzdTempScore-deczdTempScore)<-weight){
									zdDec += weight;
									sumZd_do -= weight;
								}else{
									zdDec += keepTwoDecimal(deczdTempScore-addzdTempScore);
									sumZd_do += keepTwoDecimal(addzdTempScore-deczdTempScore);
								}
							}
						}

                    }

				}
			}
			resultMap.put("sumZd",sumZd_do);
			resultMap.put("zdDec",zdDec);
			resultMap.put("zdDecItems",zdDecItems);
		}else{
			resultMap.put("sumZd",sumZd_do);
			resultMap.put("zdDec",0.0);
			resultMap.put("zdDecItems","无");
		}
		//公共加分
		if(addExamWorkflowDatasList!=null && addExamWorkflowDatasList.size()>0){
			for (ExamWorkflowDatas examWorkflowDatas : addExamWorkflowDatasList){
				if (examWorkflowDatas.getValue() != null && examWorkflowDatas.getValue() != "") {
					Integer valueType = Integer.valueOf(examWorkflowDatas.getValueType());
					double scoreValue;
					/*if (examWorkflowDatas.getValue() != null && examWorkflowDatas.getValue() != "") {
						scoreValue = Double.parseDouble(examWorkflowDatas.getValue());
					} else {
						scoreValue = 0.0;
					}*/
					try {
						scoreValue = Double.parseDouble(examWorkflowDatas.getValue());
					}catch (Exception e){
						scoreValue = 0.0;
						logger.error(getExceptionInfo(e));
						e.printStackTrace();
					}
					publicADD += scoreValue;
					pulicAddItems.append(examWorkflowDatas.getItems()+";");
				}
			}
			resultMap.put("publicADD",publicADD);
			resultMap.put("pulicAddItems",pulicAddItems);
		}else{
			resultMap.put("publicADD",0.0);
			resultMap.put("pulicAddItems","无");
		}
		//公共扣分
		if(decExamWorkflowDatasList!=null && decExamWorkflowDatasList.size()>0){
			for (ExamWorkflowDatas examWorkflowDatas : decExamWorkflowDatasList){
				if (examWorkflowDatas.getValue() != null && examWorkflowDatas.getValue() != "") {
					Integer valueType = Integer.valueOf(examWorkflowDatas.getValueType());
					double scoreValue;
					/*if (examWorkflowDatas.getValue() != null && examWorkflowDatas.getValue() != "") {
						scoreValue = Double.parseDouble(examWorkflowDatas.getValue());
					} else {
						scoreValue = 0.0;
					}*/
					try {
						scoreValue = Double.parseDouble(examWorkflowDatas.getValue());
					}catch (Exception e){
						scoreValue = 0.0;
						logger.error(getExceptionInfo(e));
						e.printStackTrace();
					}
					publicDec += scoreValue;
					pulicDecItems.append(examWorkflowDatas.getItems()+";");
				}
			}
			resultMap.put("publicDec",publicDec);
			resultMap.put("pulicDecItems",pulicDecItems);
		}
		else{
			resultMap.put("publicDec",0.0);
			resultMap.put("pulicDecItems","无");
		}
		totalScore = keepTwoDecimal(sumCg_do + sumZd_do+publicADD-publicDec);
		resultMap.put("totalScore",totalScore);
		return resultMap;
	}

	/**
	 * 根据Id获取考评对象的流程状态
	 * @param fillPersonId	被考评对象
	 * @param workflowId	考评流程Id
	 * @return	被考评对象的状态
	 */
    public int getStatusById(String fillPersonId, String workflowId,int status,String userId) {
    	if (fillPersonId.equals(userId)){
    		userId="";
		}
    	List<Integer> resultList = dao.findStatusById(fillPersonId,workflowId,userId,status);
		try {
			resultList.removeIf(item -> item == null);
			if (resultList == null || resultList.size() ==0) {
				return status;
			} else if (resultList.size() ==1){
				return resultList.get(0);
			}else {
				Integer result = status;
//				result = resultList.get(0);
				Optional<Integer> res = resultList.stream().min((o1, o2) -> o1 > o2 ? 1 : -1);
				if (res.isPresent()) {
					result = res.get();
					return result;
				}else {
					logger.error("被考评对象的状态是空的");
					return status;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}

    }

	public String findFirstFillPersonId(String id) {
    	return examWorkflowDatasDao.findFirstFillPersonId(id);
	}

	@Transactional(readOnly = false)
	public void deleteByWorkflowId(ExamWorkflowDatas examWorkflowDatas) {
		dao.deleteByWorkflowId(examWorkflowDatas);
	}

	//获取相应阶段操作评对象集合
	public List<Map<String, String>> getCheckObjectList(String workFlowId, String type, Integer status) {
    	return dao.getCheckObjectList(workFlowId,type,status);
	}

	//异常信息打印到日志
	public static String getExceptionInfo(Exception e){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(baos));
			return baos.toString();
		}catch (Exception e2){
			return e.toString();
		}

	}

	public List<Map<String, String>> findNoExamList(String workflowId, String fillPersonId) {
		return dao.findNoExamList(workflowId,fillPersonId);
	}

	//保留两位小数
	public double keepTwoDecimal(double score){
    	try {
			return (double)Math.round(score*100)/100;
		}catch (Exception e){
    		logger.error(getExceptionInfo(e));
    		return score;
    	}

	}

	/**
	 * 删除别人选择的考评项，在页面显示已删除
	 * @param itemId
	 * @param delPersonId
	 */
	@Transactional(readOnly = false)
	public void othersRemoveById(String itemId,String delPersonId) {
		dao.othersRemoveById(itemId,delPersonId);

	}

	/**
	 * 自己删除自己选择的考评项，和没选过一样
	 * @param itemId
	 */
	@Transactional(readOnly = false)
	public void selfRemoveById(String itemId) {
		dao.selfRemoveById(itemId);
	}


	public List<ExamWorkflowDatas> findUnExamList(String workflowId) {
		return  dao.findUnExamList(workflowId);
	}
}
