/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.*;
import com.thinkgem.jeesite.modules.exam.entity.*;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 单位-全局公示Service
 * @author eav.liu
 * @version 2020-02-14
 */
@Service
@Transactional(readOnly = true)
public class ExamUnitAllPublicService extends CrudService<ExamUnitAllPublicDao, ExamUnitAllPublic> {

	@Autowired
	private ExamWorkflowDatasDao examWorkflowDatasDao;

	@Autowired
	private ExamUnitAllPublicDao examUnitAllPublicDao;

	@Autowired
	private DictDao dictDao;

	@Autowired
	private ExamWeightsMainDao examWeightsMainDao;

	@Autowired
	private ExamLdScoreMonthDao examLdScoreMonthDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ExamScoreWorkItemDao examScoreWorkItemDao;//得分制工作项维护 DAO接口

	@Autowired
	private ExamWorkflowDatasService examWorkflowDatasService;

	@Autowired
	private ExamWorkflowService examWorkflowService;

	@Autowired
	private ExamWeightsDao examWeightsDao;


	public ExamUnitAllPublic get(String id) {
		return super.get(id);
	}
	
	public List<ExamUnitAllPublic> findList(ExamUnitAllPublic examUnitAllPublic) {
		return super.findList(examUnitAllPublic);
	}
	
	public Page<ExamUnitAllPublic> findPage(Page<ExamUnitAllPublic> page, ExamUnitAllPublic examUnitAllPublic) {
		return super.findPage(page, examUnitAllPublic);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamUnitAllPublic examUnitAllPublic) {
		super.save(examUnitAllPublic);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamUnitAllPublic examUnitAllPublic) {
		super.delete(examUnitAllPublic);
	}

	/**
	 * 单位月度考评数据保存
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void monthOrgDatasSave(List<Map<String,Object>> list, ExamWorkflow examWorkflow){
		String examType = examWorkflow.getExamType();
		Map<String,Object> resultMap = new HashMap<>();
		/*判断按照权重制得分还是非权重制得分*/
		ExamWeights examWeights = new ExamWeights();
		examWeights.setKpType(examType);
//          examWeights.setKpChu(companyId);  //处考处机关可能需要
		examWeights.setKpChu(null);
		if("4".equals(examType)){
			String baseFillPersonId = null;
			List<Map<String,String>> kpUnitList =  examWorkflowDatasDao.getExamUnitList(examWorkflow.getId());
			if(kpUnitList!=null&& kpUnitList.size()>0) {
				baseFillPersonId = kpUnitList.get(0).get("fillPersonId");
			}
			//处考处机关判断各处
			User baseFillPersonUser = null;
			if(StringUtils.isNotBlank(baseFillPersonId)){
				baseFillPersonUser = UserUtils.get(baseFillPersonId)==null? UserUtils.getUser() : UserUtils.get(baseFillPersonId);
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
		if(examWeights==null){
			if(null != list){
				for(Map<String,Object> data: list){
					ExamUnitAllPublic examUnitAllPublic = new ExamUnitAllPublic();
					examUnitAllPublic.setWorkflowId(examWorkflow.getId());
					examUnitAllPublic.setUnitId(String.valueOf(data.get("objId")));
					examUnitAllPublic.setUnitName(String.valueOf(data.get("objName")));
					examUnitAllPublic.setWorkName(data.get("workName")==null?null:String.valueOf(data.get("workName")));
					double origalValue = 0;
					if(null != data.get("origalValue") && !"".equals(data.get("origalValue"))){
						Object origalObj = data.get("origalValue");
						origalValue = Double.parseDouble(String.valueOf(origalObj));
						try {
							examUnitAllPublic.setValueType(Integer.valueOf(data.get("valueType")==null?null:String.valueOf(data.get("valueType"))));
						}catch (Exception e){
							logger.error(getExceptionInfo(e));
							if (origalObj.toString().contains("-")){
								examUnitAllPublic.setValueType(-1);
							}else{
								examUnitAllPublic.setValueType(1);
							}
						}
					}
					examUnitAllPublic.setHundred(origalValue);
					double finalValue =0;
					if(null != data.get("finalValue") && !"".equals(data.get("finalValue"))){
						Object finalObj = data.get("finalValue");
						finalValue = Double.parseDouble(String.valueOf(finalObj));
					}
					examUnitAllPublic.setZsqzhScore(finalValue);
					super.save(examUnitAllPublic);
				}
			}
		}
		else{
			String workflowId = examWorkflow.getId();
			List<Map<String,String>> examUnitList =  examWorkflowDatasDao.getExamUnitList(workflowId);
			List<Map<String, Object>> weigthsList =null;
			if("2".equals(examType)){
				weigthsList =  examWeightsMainDao.findWorkNameListByKPType(examType,null,null);
			}
			if("4".equals(examType)){
				if(examUnitList !=null && examUnitList.size()>0){
					User fillPersonUser = UserUtils.get(examUnitList.get(0).get("fillPersonId"));
					String chuUnitId;
					String kpUnitId ;
					if("34".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",34,") || "34".equals(fillPersonUser.getOffice().getParentId())){
						//南宁处
						chuUnitId = "34";
						kpUnitId = fillPersonUser.getOffice().getId();
					} else if("95".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",95,") || "95".equals(fillPersonUser.getOffice().getParentId())){
						//柳州处
						chuUnitId = "95";
						kpUnitId = fillPersonUser.getOffice().getId();
					}else if("156".equals(fillPersonUser.getOffice().getId()) || fillPersonUser.getOffice().getParentIds().contains(",156,") || "156".equals(fillPersonUser.getOffice().getParentId())){
						//北海处
						chuUnitId = "156";
						kpUnitId = null;
					}else{
						chuUnitId = null;
						kpUnitId = fillPersonUser.getOffice().getId();
					}
//					weigthsList =  examWeightsMainDao.findWorkNameListByKPType(examType,chuUnitId,kpUnitId);
					weigthsList =  examWeightsMainDao.findWorkNameListByKPType(examType,chuUnitId,null);
				}else{
					return;
				}
			}
			for (Map<String, Object> map : weigthsList) {
				String workName = String.valueOf(map.get("value"));
				String workNameType = String.valueOf(map.get("workNameType"));
				//用于判断该工作项是否为得分制工作项
				int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName, examType);
				for (Map<String, String> unitMap : examUnitList) {
					ExamUnitAllPublic examUnitAllPublic = new ExamUnitAllPublic();
					Double weightScore = (Double) map.get("weight");
					String unitId = unitMap.get("fillPersonId");
					String unitName = unitMap.get("fillPerson");
					if(StringUtils.isBlank(unitName)){
						unitName = UserUtils.get(unitId).getName();
					}
					examUnitAllPublic.setUnitId(unitId);//设置单位userId
					examUnitAllPublic.setWorkName(workName);//工作项
					examUnitAllPublic.setWorkflowId(workflowId);//考评id
					examUnitAllPublic.setWeight(weightScore);//权重值
					examUnitAllPublic.setUnitName(unitName);//设置单位名称
					List<ExamWorkflowDatas> workflowDatasList = examWorkflowDatasDao.getMapByOidWFIdWorkName(workflowId, unitId, workName,null);
					Double publicAddScore = 0.0;
					Double publicDeductScore = 0.0;
					Double hundredScore = 100.0;
					if("3".equals(workNameType) || "33".equals(workName)||"32".equals(workName)|| isAddScoreItem>0){
						hundredScore = 0.0;
					}
					if (workflowDatasList != null && workflowDatasList.size() > 0) {

						for (ExamWorkflowDatas examWorkflowDatas : workflowDatasList) {
							if ("32".equals(examWorkflowDatas.getWorkName())) {//公共加扣分项
								if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
									if(examWorkflowDatas.getValue()!=null){
										publicDeductScore += Double.valueOf(examWorkflowDatas.getValue());
									}else{
										publicDeductScore += 0.0;
									}

								}
								if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
									if(examWorkflowDatas.getValue()!=null){
										publicAddScore += Double.valueOf(examWorkflowDatas.getValue());
									}else{
										publicAddScore += 0.0;
									}

								}
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
						}
						if("1".equals(map.get("workNameType"))){
							if("2".equals(workName)){
								//高铁安保,重点工作，但采用常规业务算法算分
								if(hundredScore<0){
									//百分制得分 小于 0;得分不能小于0
									weightScore = 0.0;
									hundredScore = 0.0;
								}else{
									weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
									if(isAddScoreItem>0){
										//得分制工作项
										weightScore = hundredScore;
									}
								}

							}else{
								if(isAddScoreItem>0){
									//得分制工作项
									if(hundredScore<0){
										hundredScore = 0.0;
									}
									weightScore = hundredScore;
								}else{
									if((100-hundredScore)>weightScore){
										weightScore = 0.0;
									}else{
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
							}else{
								weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
								if(isAddScoreItem>0){
									//得分制工作项
									weightScore = hundredScore;
								}
							}
						}

					} else {
						//未考评工作项
						if("33".equals(workName)||"32".equals(workName)){
							publicAddScore = 0.0;
							publicDeductScore = 0.0;
						}else if("1".equals(map.get("workNameType"))){
							//重点工作
							if(isAddScoreItem>0){
								hundredScore = 0.0;
								weightScore = 0.0;
							}else{
								hundredScore = 100.0;
								weightScore = weightScore;
							}

						}else{
							//常规业务
							//得分制工作项
							if(isAddScoreItem>0){
								hundredScore = 0.0;
								weightScore = 0.0;
							}else{
								hundredScore = 100.0;
								weightScore = weightScore;
							}
						}

					}
					if("1".equals(workNameType)&&!"2".equals(workName)){
						examUnitAllPublic.setHundred(weightScore);
						examUnitAllPublic.setZsqzhScore(weightScore);
					}else if("3".equals(workNameType)){
						examUnitAllPublic.setHundred(publicAddScore-publicDeductScore);
						examUnitAllPublic.setZsqzhScore(publicAddScore-publicDeductScore);
					}else{
						examUnitAllPublic.setHundred(hundredScore);
						examUnitAllPublic.setZsqzhScore(weightScore);
					}
					examUnitAllPublic.setValueType(1);
					this.save(examUnitAllPublic);
				}

			}
		}

	}
	/**
	 * 单位月度考评数据保存
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void monthOrgDatasSave2(List<Map<String,Object>> list, ExamWorkflow examWorkflow){
		if(null != list){
			for(Map<String,Object> data: list){
				ExamUnitAllPublic examUnitAllPublic = new ExamUnitAllPublic();
				examUnitAllPublic.setWorkflowId(examWorkflow.getId());
				examUnitAllPublic.setUnitId(data.get("objId").toString());
				examUnitAllPublic.setUnitName(data.get("objName").toString());
				if(data.get("workName")!=null){
					examUnitAllPublic.setWorkName(data.get("workName").toString());
				}
				Double weight;
				if(data.get("weight")!=null){
					weight = (Double)data.get("weight");
				}else{
					weight = 100.0;
				}
				examUnitAllPublic.setWeight(weight);
				double origalValue = 0;
				if(null != data.get("origalValue") && !"".equals(data.get("origalValue"))){
					Object origalObj = data.get("origalValue");//data.get("origalValue") 百分制得分 含正负号
					origalValue = Double.parseDouble(String.valueOf(origalObj));
					if (origalObj.toString().contains("-")){
						examUnitAllPublic.setValueType(-1);
					}else{
						examUnitAllPublic.setValueType(1);
					}
				}
				examUnitAllPublic.setHundred(origalValue);
				double finalValue =0;
				//处理权重分
				if("1".equals(data.get("workNameType"))){
					//重点工作项
					if(data.get("workName")!=null && "2".equals(data.get("workName"))){
						if(null != data.get("finalValue") && !"".equals(data.get("finalValue"))){
							Object finalObj = data.get("finalValue");//权重分，带正否号
							finalValue = Double.parseDouble(String.valueOf(finalObj));
							if(finalObj.toString().contains("-")){
								//扣分
								if(finalValue< -weight){
									finalValue = -weight;
								}
							}

						}
						examUnitAllPublic.setZsqzhScore(finalValue);
					}else{
						if(null != data.get("origalValue") && !"".equals(data.get("origalValue"))){
							finalValue = Double.parseDouble(data.get("origalValue").toString());
							if(finalValue<-weight){
								finalValue = -weight;
							}else if(finalValue>weight){
								finalValue = weight;
							}else{
								finalValue = finalValue;
							}
						}
						examUnitAllPublic.setZsqzhScore(finalValue);
					}
				}else{
					if(null != data.get("finalValue") && !"".equals(data.get("finalValue"))){
						Object finalObj = data.get("finalValue");
						finalValue = Double.parseDouble(String.valueOf(finalObj));
						if(finalObj.toString().contains("-")){
							//扣分
							if(finalValue < -weight){
								finalValue = -weight;
							}
						}
					}
					examUnitAllPublic.setZsqzhScore(finalValue);
				}
				super.save(examUnitAllPublic);
			}
		}
	}

	@Transactional(readOnly = false)
	public void ckdsMonthOrgDatasSaveBeta(List<Map<String,Object>> list, ExamWorkflow examWorkflow){
		/*Map<String, Object> resultMap = examWorkflowDatasService.getIngChuKaoDuisuo2(examWorkflow.getId(),examWorkflow.getStatus());
		List<Map<String,Object>> pcsMapList = (List<Map<String, Object>>) resultMap.get("pcsResultList");
		for(Map<String,Object> pcsMap :pcsMapList){
			Double totalScore = (Double) pcsMap.get("totalScore");
		}*/
		String workflowId = examWorkflow.getId();
		List<Map<String,String>> pcsList =  examWorkflowDatasDao.getExamUnitList(workflowId);
		String unitId = null;
		String baseFillPersonId = null;
		if(pcsList!=null&& pcsList.size()>0){
			unitId = pcsList.get(0).get("officeId");
			baseFillPersonId = pcsList.get(0).get("fillPersonId");
		}
		User createBy = examWorkflow.getCreateBy();
		User updateBy = examWorkflow.getUpdateBy();
		User createUser = UserUtils.get(createBy.getId());
		User updateUser = UserUtils.get(updateBy.getId());
		User fillPersonUser = UserUtils.get(baseFillPersonId);
		if(fillPersonUser==null){
			fillPersonUser = UserUtils.getUser();
		}
		List<Map<String,Object>> workNameList;
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
			workNameList = examWeightsMainDao.getWorkNameListByChu("156",unitId);//21年7月19日，北海处绩效办、局绩效办负责人提出北海处派出所区分高铁、普铁分别进行权重考评
		}else{
			workNameList = new ArrayList<>();
		}
		List<Map<String,Object>> resultList = new ArrayList<>();
		int i = 1;
		if(pcsList!=null && pcsList.size()>0 && workNameList!=null && workNameList.size()>0){
			for(Map<String,String> tempPcsMap : pcsList){
				String fillPersonId = tempPcsMap.get("fillPersonId");//填报人id
				String fillPersonName = tempPcsMap.get("fillPerson");//填报人id
				Double totalScore = 0.0;//考核得分合计
				Double publicAddScore = 0.0;//公共加分
				Double publicDecScore = 0.0;//公共扣分
				for(Map<String,Object> tempWorkName : workNameList){
					ExamUnitAllPublic examUnitAllPublic = new ExamUnitAllPublic();
					examUnitAllPublic.setUnitName(fillPersonName);
					examUnitAllPublic.setUnitId(fillPersonId);
					examUnitAllPublic.setWorkflowId(workflowId);
					String workName_value = (String) tempWorkName.get("value");
					String workName_label = (String) tempWorkName.get("label");
					String workName_type = (String) tempWorkName.get("workNameType");
					Double weight = (Double) tempWorkName.get("weight");
					examUnitAllPublic.setWeight(weight);
					examUnitAllPublic.setWorkName(workName_value);
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
							examUnitAllPublic.setHundred(publicAddScore-publicDecScore);
							examUnitAllPublic.setZsqzhScore(publicAddScore-publicDecScore);
						}
						else {
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
										//TODO 设置百分制分值权重分值
										examUnitAllPublic.setHundred(0.0);
										examUnitAllPublic.setZsqzhScore(0.0);
									}else{
										if(isAddScoreItem>0){
											//得分制工作项
											examUnitAllPublic.setHundred(score);
											examUnitAllPublic.setZsqzhScore(score);
										}else{
											examUnitAllPublic.setHundred(score);
											examUnitAllPublic.setZsqzhScore((double) Math.round((score*(weight/100)) * 100) / 100);
										}
									}
								}else{
									if(score<0){
										totalScore += 0;
									}else{
										if(isAddScoreItem>0){
											//得分制工作项
											examUnitAllPublic.setHundred(score);
											examUnitAllPublic.setZsqzhScore(score);
										}else{
											examUnitAllPublic.setHundred(score);
											examUnitAllPublic.setZsqzhScore(score);
										}
									}
								}
							}else{
								if(score<0){
									examUnitAllPublic.setHundred(0.0);
									examUnitAllPublic.setZsqzhScore(0.0);
								}else{
									if(isAddScoreItem>0){
										//得分制工作项
										examUnitAllPublic.setHundred(score);
										examUnitAllPublic.setZsqzhScore(score);
									}else{
										examUnitAllPublic.setHundred(score);
										examUnitAllPublic.setZsqzhScore((double) Math.round((score*(weight/100)) * 100) / 100);
										//totalScore +=  (double) Math.round((score*(weight/100)) * 100) / 100;
									}
								}
							}
						}
					}
					else{
						//没选
						if(!"32".equals(workName_value)){
							if(isAddScoreItem>0){
								examUnitAllPublic.setHundred(0.0);
								examUnitAllPublic.setZsqzhScore(0.0);
							}else{
								if("1".equals(workName_type)&&"2".equals(workName_value)){
									examUnitAllPublic.setHundred(weight);
								}else{
									examUnitAllPublic.setHundred(100.0);
								}
								examUnitAllPublic.setZsqzhScore(weight);
							}
						}else{
							examUnitAllPublic.setZsqzhScore(publicAddScore-publicDecScore);
							examUnitAllPublic.setHundred(publicAddScore-publicDecScore);
						}
					}
					examUnitAllPublic.setValueType(1);
					this.save(examUnitAllPublic);
				}
			}
		}


	}

	/**
	 * 局考处单位月度考评数据保存beta
	 * @param examWorkflow
	 */
	@Transactional(readOnly = false)
	public void monthOrgDatasSaveBeta(ExamWorkflow examWorkflow){
		String workflowId = examWorkflow.getId();
		String examType = examWorkflow.getExamType();
		List<String> objIds = new ArrayList<>();//各个公安处单位id集合
		List<String> unitNames = new ArrayList<>();//各个公安处名称集合
		objIds.add("34");//南宁处
		unitNames.add("南宁公安处");
		objIds.add("95");//柳州处
		unitNames.add("柳州公安处");
		objIds.add("156");//北海处
		unitNames.add("北海公安处");
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
		List<Map<String, Object>> weigthsList = examWeightsMainDao.findWorkNameList();
		for (Map<String, Object> map : weigthsList) {
			String workName = String.valueOf(map.get("value"));
			String workNameType = String.valueOf(map.get("workNameType"));
			//用于判断该工作项是否为得分制工作项
			int isAddScoreItem = examScoreWorkItemDao.getInfoCount(workName, examType);
			for (Map<String, String> unitMap : unitList) {
				ExamUnitAllPublic examUnitAllPublic = new ExamUnitAllPublic();
				Double weightScore = (Double) map.get("weight");
				String unitId = unitMap.get("unitId");
				String unitName = unitMap.get("unitName");
				examUnitAllPublic.setUnitId(unitId);//设置单位id(局考处单位id其他类型用户id)
				examUnitAllPublic.setWorkName(workName);//工作项
				examUnitAllPublic.setWorkflowId(workflowId);//考评id
				examUnitAllPublic.setWeight(weightScore);//权重值
				examUnitAllPublic.setUnitName(unitName);//设置单位名称(局考处单位名称其他类型用户名称)
				List<ExamWorkflowDatas> workflowDatasList = examWorkflowDatasDao.getMapByOidWFIdWorkName(workflowId, unitId, workName,null);
				Double publicAddScore = 0.0;
				Double publicDeductScore = 0.0;
				Double hundredScore = 100.0;
				if("3".equals(workNameType) || "33".equals(workName)||"32".equals(workName)|| isAddScoreItem>0){
					hundredScore = 0.0;
				}
				if (workflowDatasList != null && workflowDatasList.size() > 0) {

					for (ExamWorkflowDatas examWorkflowDatas : workflowDatasList) {
						if ("32".equals(examWorkflowDatas.getWorkName())) {//公共加扣分项
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == -1) {
								if(examWorkflowDatas.getValue()!=null){
									publicDeductScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									publicDeductScore += 0.0;
								}

							}
							if (examWorkflowDatas.getValueType()!=null && examWorkflowDatas.getValueType() == 1) {
								if(examWorkflowDatas.getValue()!=null){
									publicAddScore += Double.valueOf(examWorkflowDatas.getValue());
								}else{
									publicAddScore += 0.0;
								}

							}
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
					}
					if("1".equals(map.get("workNameType"))){
						if("2".equals(workName)){
							//高铁安保,重点工作，但采用常规业务算法算分
							if(hundredScore<0){
								//百分制得分 小于 0;得分不能小于0
								weightScore = 0.0;
								hundredScore = 0.0;
							}else{
								weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
								if(isAddScoreItem>0){
									//得分制工作项
									weightScore = hundredScore;
								}
							}

						}else{
							if(isAddScoreItem>0){
								//得分制工作项
								if(hundredScore<0){
									hundredScore = 0.0;
								}
								weightScore = hundredScore;
							}else{
								if((100-hundredScore)>weightScore){
									weightScore = 0.0;
								}else{
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
						}else{
							weightScore = (double) Math.round((hundredScore * (weightScore / 100)) * 100) / 100;
							if(isAddScoreItem>0){
								//得分制工作项
								weightScore = hundredScore;
							}
						}
					}

				} else {
					//未考评工作项
					if("33".equals(workName)||"32".equals(workName)){
						publicAddScore = 0.0;
						publicDeductScore = 0.0;
					}else if("1".equals(map.get("workNameType"))){
						//重点工作
						if(isAddScoreItem>0){
							hundredScore = 0.0;
							weightScore = 0.0;
						}else{
							hundredScore = 100.0;
							weightScore = weightScore;
						}

					}else{
						//常规业务
						//得分制工作项
						if(isAddScoreItem>0){
							hundredScore = 0.0;
							weightScore = 0.0;
						}else{
							hundredScore = 100.0;
							weightScore = weightScore;
						}
					}

				}
				if("1".equals(workNameType)&&!"2".equals(workName)){
					examUnitAllPublic.setHundred(weightScore);
					examUnitAllPublic.setZsqzhScore(weightScore);
				}else if("3".equals(workNameType)){
					examUnitAllPublic.setHundred(publicAddScore-publicDeductScore);
					examUnitAllPublic.setZsqzhScore(publicAddScore-publicDeductScore);
				}else{
					examUnitAllPublic.setHundred(hundredScore);
					examUnitAllPublic.setZsqzhScore(weightScore);
				}
				examUnitAllPublic.setValueType(1);
				this.save(examUnitAllPublic);
			}

		}
		}

	/**
	 * 废弃
	 * 月度-局考处
	 *根据workflowId查询三个处的各项工作的名称、权重、业务100分制得分、业务折算权重后得分、扣分详情分析
	 * @param workflowId
	 */
	public Map<String, Object> getScoreList3(String workflowId){
		List<Map<String ,String>> weigthsList = dictDao.findListByType("exam_weigths");
		List list1 = new ArrayList<List<Map<String, Object>>>();
		//业务权重合计得分
		Double nanNingWeightScoreSum = 0.0;
		Double liuZhouWeightScoreSum = 0.0;
		Double beiHaiWeightScoreSum = 0.0;
		/*for (Map<String, String> m: weigthsList){
			//三个处的顺序前端已固定，南宁、柳州、北海，后台顺序也保持一致
			//对工作进行分类，得到归类后的工作字典值，权重，业务100分制得分，业务折算权重后得分,扣分详情分析
			Map<String, Object> nanNingMap = examUnitAllPublicDao.getMapByWorkflowIdAndUnitId(workflowId,"34",m.get("value"));
			List list2 = new ArrayList<Map<String, Object>>();
			if(nanNingMap != null && nanNingMap.size() > 0){
				nanNingMap.put("workName",m.get("label"));
				nanNingWeightScoreSum+= Double.parseDouble(nanNingMap.get("zsqzhScore").toString());
			}
			Map<String, Object> liuZhouMap = examUnitAllPublicDao.getMapByWorkflowIdAndUnitId(workflowId,"95",m.get("value"));
			if(liuZhouMap != null && liuZhouMap.size() > 0 ){
				liuZhouWeightScoreSum+= Double.parseDouble(liuZhouMap.get("zsqzhScore").toString());
			}
			Map<String, Object> beiHaiMap = examUnitAllPublicDao.getMapByWorkflowIdAndUnitId(workflowId,"156",m.get("value"));
			if(beiHaiMap != null && beiHaiMap.size() > 0 ){
				beiHaiWeightScoreSum+= Double.parseDouble(beiHaiMap.get("zsqzhScore").toString());
			}
			if(!(nanNingMap == null && liuZhouMap == null && beiHaiMap == null) ){
				list2.add(nanNingMap);
				list2.add(liuZhouMap);
				list2.add(beiHaiMap);
				list1.add(list2);
			}
		}*/
		Map<String, Object> map = new HashMap<>();
		List<Double> weightSorceList = new ArrayList<>();
		weightSorceList.add(nanNingWeightScoreSum);
		weightSorceList.add(liuZhouWeightScoreSum);
		weightSorceList.add(beiHaiWeightScoreSum);
		//map.put("commonSorceList",commonSorceList);
		map.put("weightSorceList",weightSorceList);
		map.put("conventionScoreList",list1);
		return map;
	}

	/**
	 * 废弃  先不用
	 * @param workflowId
	 * @return
	 */
	public List<String> getSumScoreList(String workflowId){
		Double sum1 = examUnitAllPublicDao.getSumByWorkflowIdAndUnitId(workflowId,"34");
		Double sum2 = examUnitAllPublicDao.getSumByWorkflowIdAndUnitId(workflowId,"95");
		Double sum3 = examUnitAllPublicDao.getSumByWorkflowIdAndUnitId(workflowId,"156");
		List<String> sumList = new ArrayList<>();
		sumList.add(handleAccuracy(sum1));
		sumList.add(handleAccuracy(sum2));
		sumList.add(handleAccuracy(sum3));
		return sumList;
	}
	/**
	 * 处理小数精度
	 * @param number
	 * @return
	 */
	private String handleAccuracy(Double number) {
		if(number == null){
			return "0";
		}
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(4);
		return df.format(number);
	}

	/**
	 * 月度-处考队所  一套
	 *根据workflowId查询各机关单位的部门、扣分项目、加分项目、扣分、加分、总得分
	 * @param workflowId
	 */
	public ArrayList<Map<String, String>> getScoreList2Old(String workflowId){
		//List<String> unitIds = Arrays.asList("2", "3", "4", "5", "6", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
		ArrayList<Map<String, String>> list = new ArrayList<>();
		List<Map<String, String>> unitList = examUnitAllPublicDao.getUnitIdsByWorkflowId(workflowId,"0");
		for (Map<String, String> unitMap:unitList) {
			//扣分
			Map<String, String> deductScore = examUnitAllPublicDao.getDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), -1);
			//加分
			Map<String, String> addScore = examUnitAllPublicDao.getDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), 1);
			Map<String, String> depMap = new HashMap<>();
			if(deductScore == null && addScore == null){
                depMap.put("depItem","无扣分项目");//扣分项目
                depMap.put("depName",unitMap.get("unitName"));
                depMap.put("depId",unitMap.get("unitId"));
                depMap.put("deductSource","0.0");
                depMap.put("addItem","无加分项目");//加分项目
                depMap.put("addSource","0.0");
				//continue;
			}else{
				if(deductScore != null && deductScore.size() > 0){
					List<String> items = examUnitAllPublicDao.selItemByWFIDObjId(workflowId,unitMap.get("unitId"),-1);
					StringBuffer sbf = new StringBuffer();
					if(items!=null && items.size()>0){
						int i = 1;
						for(String tempStr : items){
							sbf.append(i+"."+tempStr+"<br>");
							i++;
						}
					}
					depMap.put("depItem",sbf.toString());//扣分项目
					depMap.put("depName",deductScore.get("depName"));
					depMap.put("depId",deductScore.get("depId"));
					depMap.put("deductSource",deductScore.get("sum"));
				}else{
					depMap.put("deductSource","0");
				}
				if(addScore != null && addScore.size() > 0){
					List<String> items = examUnitAllPublicDao.selItemByWFIDObjId(workflowId,unitMap.get("unitId"),1);
					StringBuffer sbf = new StringBuffer();
					if(items!=null && items.size()>0){
						int i = 1;
						for(String tempStr : items){
							sbf.append(i+"."+tempStr+"<br>");
							i++;
						}
					}
					depMap.put("addItem",sbf.toString());//加分项目
					depMap.put("depName",addScore.get("depName"));
					depMap.put("depId",addScore.get("depId"));
					depMap.put("addSource",addScore.get("sum"));
				}else{
					depMap.put("addSource","0");
				}
			}
			list.add(depMap);
		}
		return list;
	}
	/**
	 * 月度处考队所-最终结果公示
	 * @param workflowId
	 */
	public ArrayList<Map<String, String>> getScoreList2(String workflowId){
		//List<String> unitIds = Arrays.asList("2", "3", "4", "5", "6", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
		ArrayList<Map<String, String>> list = new ArrayList<>();
		List<Map<String, String>> unitList = examUnitAllPublicDao.getUnitIdsByWorkflowId(workflowId,"0");
		for (Map<String, String> unitMap:unitList) {
			//扣分
			//Map<String, String> deductScore = examUnitAllPublicDao.getDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), -1);
			//加分
			//Map<String, String> addScore = examUnitAllPublicDao.getDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), 1);
			Map<String, String> endSumScore = examUnitAllPublicDao.getDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), null);
			Map<String, String> depMap = new HashMap<>();
			depMap.put("depName",unitMap.get("unitName"));
			depMap.put("depId",unitMap.get("unitId"));
			//depMap.put("deductSource",deductScore.get("sum"));//扣分分值
			//depMap.put("addSource",addScore.get("sum"));//加分分值
			depMap.put("endSumScore",endSumScore.get("sum"));//加分分值
			//减分项目
			List<String> decItems = examUnitAllPublicDao.selItemByWFIDObjId(workflowId,unitMap.get("unitId"),-1);
			//扣分项目
			if(decItems!=null && decItems.size()>0){
				int i = 1;
				StringBuffer decSbf = new StringBuffer();
				for(String tempStr : decItems){
					decSbf.append(i+"."+tempStr+"<br>");
					i++;
				}
				depMap.put("depItem",decSbf.toString());//扣分项目
			} else{
				depMap.put("depItem","无扣分项目");//扣分项目
			}
			List<String> addItems = examUnitAllPublicDao.selItemByWFIDObjId(workflowId,unitMap.get("unitId"),1);
			if(addItems!=null && addItems.size()>0){
				StringBuffer addSbf = new StringBuffer();
				int i = 1;
				for(String tempStr : addItems){
					addSbf.append(i+"."+tempStr+"<br>");
					i++;
				}
				depMap.put("addItem",addSbf.toString());//加分项目
			}else{
				depMap.put("addItem","无加分项目");//加分项目
			}

			list.add(depMap);
		}
		return list;
	}
	/**
	 * 月度-处考处机关 局考局机关  一套
	 *根据workflowId查询各机关单位的部门、扣分项目、加分项目、扣分、加分、总得分
	 * @param workflowId
	 */
	public ArrayList<Map<String, String>> getScoreListJG(String workflowId){
		ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);
		String basicScore = DictUtils.getDictValue(examWorkflow.getExamType(),"exam_basicScore","100");
		ArrayList<Map<String, String>> list = new ArrayList<>();
		List<Map<String, String>> unitList = examUnitAllPublicDao.getUnitIdsByWorkflowId(workflowId,"0");
		/*直接根据最终结果表，查询当前考评为权重制 /  非权重制，不再根据权重设置表获取判断*/
		/*ExamWeights examWeights = new ExamWeights();
		examWeights.setKpType(examWorkflow.getExamType());
//          examWeights.setKpChu(companyId);  //处考处机关可能需要
		examWeights.setKpChu(null);
		if("4".equals(examWorkflow.getExamType())){
			String baseFillPersonId = null;
			List<Map<String,String>> kpUnitList =  examWorkflowDatasDao.getExamUnitList(examWorkflow.getId());
			if(kpUnitList!=null&& kpUnitList.size()>0) {
				baseFillPersonId = kpUnitList.get(0).get("fillPersonId");
			}
			//处考处机关判断各处
			User baseFillPersonUser = null;
			if(StringUtils.isNotBlank(baseFillPersonId)){
				baseFillPersonUser = UserUtils.get(baseFillPersonId)==null? UserUtils.getUser() : UserUtils.get(baseFillPersonId);
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
		examWeights = examWeightsDao.getWeightByUnit(examWeights);*/
		Integer count = examUnitAllPublicDao.getWeightIsNull(workflowId);
		if(count!=null && count>0){
			//非权重制
			for (Map<String, String> unitMap:unitList) {
				//扣分
				//Map<String, String> deductScore = examUnitAllPublicDao.getDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), -1);
				Map<String, String> deductScore = examUnitAllPublicDao.getJGDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), -1);
				//加分
				//Map<String, String> addScore = examUnitAllPublicDao.getDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), 1);
				Map<String, String> addScore = examUnitAllPublicDao.getJGDepByWorkflowIdAndUnitId(workflowId, unitMap.get("unitId"), 1);
				Map<String, String> depMap = new HashMap<>();
				double baseSum = 100.0;//总得分
				try {
					baseSum = Double.valueOf(basicScore);
				}catch (Exception e){
					logger.error(getExceptionInfo(e));
					e.printStackTrace();
					baseSum = 100.0;
				}
				depMap.put("baseSum",String.valueOf(baseSum));//基础分值
				if(deductScore == null && addScore == null){
					depMap.put("depItem","无扣分项目");//扣分项目
					depMap.put("depName",unitMap.get("unitName"));
					depMap.put("depId",unitMap.get("unitId"));
					depMap.put("deductSource","0.0");
					depMap.put("addItem","无加分项目");//加分项目
					depMap.put("addSource","0.0");
					//continue;
				}else{
					if(deductScore != null && deductScore.size() > 0){
						List<String> items = examUnitAllPublicDao.selItemByWFIDObjId(workflowId,unitMap.get("unitId"),-1);
						StringBuffer sbf = new StringBuffer();
						if(items!=null && items.size()>0){
							int i = 1;
							for(String tempStr : items){
								sbf.append(i+"."+tempStr+"<br>");
								i++;
							}
						}
						depMap.put("depItem",sbf.toString());//扣分项目
						depMap.put("depName",deductScore.get("depName"));
						depMap.put("depId",deductScore.get("depId"));
						depMap.put("deductSource",deductScore.get("sum"));
					}else{
						depMap.put("deductSource","0");
					}
					if(addScore != null && addScore.size() > 0){
						List<String> items = examUnitAllPublicDao.selItemByWFIDObjId(workflowId,unitMap.get("unitId"),1);
						StringBuffer sbf = new StringBuffer();
						if(items!=null && items.size()>0){
							int i = 1;
							for(String tempStr : items){
								sbf.append(i+"."+tempStr+"<br>");
								i++;
							}
						}
						depMap.put("addItem",sbf.toString());//加分项目
						depMap.put("depName",addScore.get("depName"));
						depMap.put("depId",addScore.get("depId"));
						depMap.put("addSource",addScore.get("sum"));
					}else{
						depMap.put("addSource","0");
					}
				}
				list.add(depMap);
			}
		}
		else{
			//权重制
			for (Map<String, String> unitMap:unitList) {
				Map<String, String> endSumScore = examUnitAllPublicDao.getJGDepByWorkflowIdAndUnitIdWeight(workflowId, unitMap.get("unitId"), null);
				Map<String, String> depMap = new HashMap<>();
				depMap.put("depName",unitMap.get("unitName"));
				depMap.put("depId",unitMap.get("unitId"));
				depMap.put("endSumScore",endSumScore.get("sum"));//最终得分
				depMap.put("isWeightCalculate","isWeightCalculate");//是权重计算
				//减分项目
				List<String> decItems = examUnitAllPublicDao.selItemByWFIDObjId(workflowId,unitMap.get("unitId"),-1);
				//扣分项目
				if(decItems!=null && decItems.size()>0){
					int i = 1;
					StringBuffer decSbf = new StringBuffer();
					for(String tempStr : decItems){
						decSbf.append(i+"."+tempStr+"<br>");
						i++;
					}
					depMap.put("depItem",decSbf.toString());//扣分项目
				} else{
					depMap.put("depItem","无扣分项目");//扣分项目
				}
				List<String> addItems = examUnitAllPublicDao.selItemByWFIDObjId(workflowId,unitMap.get("unitId"),1);
				if(addItems!=null && addItems.size()>0){
					StringBuffer addSbf = new StringBuffer();
					int i = 1;
					for(String tempStr : addItems){
						addSbf.append(i+"."+tempStr+"<br>");
						i++;
					}
					depMap.put("addItem",addSbf.toString());//加分项目
				}else{
					depMap.put("addItem","无加分项目");//加分项目
				}
				list.add(depMap);
			}
		}
		return list;
	}
	/**
	 * 月度-局考处、
	 *根据workflowId查询各个单位的各项工作的名称、权重、业务100分制得分、业务折算权重后得分、扣分详情分析
	 * @param workflowId
	 */
	public Map<String, Object> getScoreList1(String workflowId){
		//根据workflowId去查询该考核的考核对象
		List<Map<String, String>> unitList = examUnitAllPublicDao.getUnitIdsByWorkflowId(workflowId,"1");
		//List<Map<String, String>> weigthsList =examUnitAllPublicDao.findWorkNameList(workflowId);
		List<Map<String, Object>> weigthsList =examWeightsMainDao.findWorkNameList();
		List list1 = new ArrayList<List<Map<String, Object>>>();
		//业务权重合计得分
		for (Map<String, Object> m: weigthsList){
			List list2 = new ArrayList<Map<String, Object>>();
			String workNameType = (String) m.get("workNameType");
			for (Map<String, String> unitMap :unitList) {
				//对工作进行分类，得到归类后的工作字典值，权重，业务100分制得分，业务折算权重后得分,扣分详情分析
				List<Map<String, Object>> mapList = examUnitAllPublicDao.getMapByWorkflowIdAndUnitId2(workflowId,unitMap.get("unitId"),m.get("value").toString());
				//weight hundredSum zsqzhScore
				Map<String, Object> tempUnitInfoMap = new HashMap<>();
				if(mapList != null && mapList.size() > 0) {
					tempUnitInfoMap.put("workName", m.get("label"));
					tempUnitInfoMap.put("unitName", unitMap.get("unitName"));
					tempUnitInfoMap.put("unitId", unitMap.get("unitId"));
					for(Map<String,Object> map :mapList){
						if(tempUnitInfoMap.get("weight")==null){
							tempUnitInfoMap.put("weight", map.get("weight"));
						}
						if(tempUnitInfoMap.get("workNameType")==null){
							tempUnitInfoMap.put("workNameType", map.get("workNameType"));
						}
						if(tempUnitInfoMap.get("hundredSum")==null){
							if(map.get("hundredSum")==null){
								tempUnitInfoMap.put("hundredSum",0.0);
							}else{
								tempUnitInfoMap.put("hundredSum",map.get("hundredSum"));
							}
						}else{
							double hundredSum;
							if(map.get("hundredSum")==null){
								hundredSum = 0.0;
							}else{
								hundredSum = Double.valueOf(map.get("hundredSum").toString());
							}
							double tempHunder = Double.valueOf(tempUnitInfoMap.get("hundredSum").toString());
							tempUnitInfoMap.put("hundredSum",tempHunder+hundredSum);
						}
						if(tempUnitInfoMap.get("zsqzhScore")==null){
							if(map.get("zsqzhScore")==null){
								tempUnitInfoMap.put("zsqzhScore",0.0);
							}else{
								tempUnitInfoMap.put("zsqzhScore",map.get("zsqzhScore"));
							}
						}else{
							double zsqzhScore;
							if(map.get("zsqzhScore")==null){
								zsqzhScore = 0.0;
							}else{
								zsqzhScore = Double.valueOf(map.get("zsqzhScore").toString());
							}
							double tempzsqzhScore = Double.valueOf(tempUnitInfoMap.get("zsqzhScore").toString());
							tempUnitInfoMap.put("zsqzhScore",tempzsqzhScore+zsqzhScore);
						}
					}
					Double weight = Double.valueOf(tempUnitInfoMap.get("weight").toString());
					double tempzsqzhScore = Double.valueOf(tempUnitInfoMap.get("zsqzhScore").toString());
					double tempHunder = Double.valueOf(tempUnitInfoMap.get("hundredSum").toString());
					tempUnitInfoMap.put("workNameType",workNameType);
					//0 常规  1 重点  3 公共加扣分
					if("0".equals(workNameType)){
						tempUnitInfoMap.put("hundredSum",tempHunder);
						tempUnitInfoMap.put("zsqzhScore",tempzsqzhScore);
					}else if("1".equals(workNameType)){
							tempUnitInfoMap.put("hundredSum","-");
							tempUnitInfoMap.put("zsqzhScore",tempzsqzhScore);
					}else{
						tempUnitInfoMap.put("hundredSum",tempzsqzhScore);
						tempUnitInfoMap.put("zsqzhScore",tempzsqzhScore);
					}
					list2.add(tempUnitInfoMap);
				}else{
					Map<String, Object> map2 = new HashMap<>();
					map2.put("workName", m.get("label"));
					map2.put("unitName", unitMap.get("unitName"));
					map2.put("unitId", unitMap.get("unitId"));
					map2.put("weight", m.get("weight"));
					map2.put("workNameType", m.get("workNameType"));
					if("0".equals(workNameType)){
						map2.put("hundredSum","100.0");
						map2.put("zsqzhScore",m.get("weight"));
					} else if("1".equals(workNameType)){
						map2.put("hundredSum","-");
						map2.put("zsqzhScore",m.get("weight"));
					}else{
						map2.put("hundredSum","0.0");
						map2.put("zsqzhScore","0");
					}
					list2.add(map2);
				}
			}
			if(list2 != null && list2.size() > 0){
				list1.add(list2);
			}
		}
		Map<String, Object> map = new HashMap<>();
		List<Double> weightSorceList = new ArrayList<>();//业务权重合计得分
		List<Double> sumSorceList = new ArrayList<>();//总得分
		for (Map<String, String> unitMap :unitList) {
			Double weightScore =  examUnitAllPublicDao.getWeightScore(workflowId,unitMap.get("unitId"),"isYW");
			Double sumScore =  examUnitAllPublicDao.getWeightScore(workflowId,unitMap.get("unitId"),null);
			if(weightScore == null){
				weightScore = 0.0;
			}
			if(sumScore == null){
				sumScore = 0.0;
			}
			weightSorceList.add(weightScore);
			sumSorceList.add(sumScore);
		}
		map.put("weightSorceList",weightSorceList);
		map.put("sumSorceList",sumSorceList);
		map.put("conventionScoreList",list1);
		return map;
	}

	/**
	 * 获取年度所有月份分数
	 * @param param
	 * @return
	 */
	public List<Map<String,String>> findUnitYearScores(Map<String,String> param){
		return examUnitAllPublicDao.findUnitYearScores(param);
	}

	/**
	 *首页绩效单位月度局考处图表显示
	 * @return
	 */
	public Map<String, Object>  indexExamEcharts() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String dateStr = formatter.format(new Date());
		//获得月度倒序排序好的符合条件的局考处流程id
		String workflowId = examUnitAllPublicDao.findWorkflowId(Integer.valueOf(dateStr));
		Map<String, Object> map = new HashMap<>();
		if(workflowId != null){
			List<String> unitNameList = new ArrayList<>();
			List<Double> scoreList = new ArrayList<>();
			List<Map<String, String>> unitList = examUnitAllPublicDao.getUnitIdsByWorkflowId(workflowId,"1");
			for (Map<String, String> unitMap:unitList) {
				Double sumScore =  examUnitAllPublicDao.getWeightScore(workflowId,unitMap.get("unitId"),null);
				unitNameList.add(unitMap.get("unitName"));
				scoreList.add(sumScore);
			}
			map.put("unitNameList",unitNameList);
			map.put("scoreList",scoreList);
		}else{
			return null;
		}
		return map;
	}

	//局考处
	//10.28 添加参数 workName kevin.jia
	public List<Map<String, Object>> findInfoByUnitId(String id, String month,String workName) {
		return dao.findInfoByUnitId(id, month,workName);
	}
	//10.28 添加参数 workName kevin.jia
	public List<Map<String, Object>> findInfoByUnitIds(List<String> ids, String month,String workName) {
		return dao.findInfoByUnitIds(ids, month,workName);
	}

	//局考局
	public List<Map<String, Object>> findJkjInfoByUnitId(String id, String month) {
		String basicScore = DictUtils.getDictValue("2","exam_basicScore","100");
		Double baseSum;
		boolean isWeight;
		//判断权重制得分还是非权重制得分，大于0非权重制
		Integer count = examUnitAllPublicDao.getWeightIsNullByUserIdMonth(id,month,"2");
		if(count!=null && count>0){
			try {
				baseSum = Double.valueOf(basicScore);
			}catch (Exception e){
				logger.error(getExceptionInfo(e));
				e.printStackTrace();
				baseSum = 100.0;
			}
			isWeight = false;
		}else{
			baseSum = 0.0;
			isWeight = true;
		}
		return dao.findJkjInfoByUnitId(id,month,baseSum,isWeight);
	}

	public List<Map<String, Object>> findJkjInfoByUnitIds(List<String> ids, String month) {
		String basicScore = DictUtils.getDictValue("2","exam_basicScore","100");
		Double baseSum;
		boolean isWeight;
		Integer count = examUnitAllPublicDao.getWeightIsNullByUserIdMonth(null,month,"2");//查询该月采取考评方式权重制/非权重制
		if(count!=null && count>0){
			try {
				baseSum = Double.valueOf(basicScore);
			}catch (Exception e){
				logger.error(getExceptionInfo(e));
				e.printStackTrace();
				baseSum = 100.0;
			}
			isWeight = false;
		}else{
			baseSum = 0.0;
			isWeight = true;
		}
		List<String> userIds = userDao.getUserIdByUnitIds(ids);
		return dao.findJkjInfoByUnitIds(ids,userIds,month,baseSum,isWeight);
	}

	//处考队所
	//10.29 添加参数 workName kevin.jia
	public List<Map<String, Object>> findCkdsInfoByUnitId(String id, String month,String workName) {
		return dao.findCkdsInfoByUnitId(id,month,workName);
	}
	//10.29 添加参数 workName kevin.jia
	public List<Map<String, Object>> findCkdsInfoByUnitIds(List<String> ids, String month,String workName) {
		List<String> userIds = userDao.getUserIdByUnitIds(ids);
		return dao.findCkdsInfoByUnitIds(ids,userIds, month,workName);
	}

	//处考处机关
	public List<Map<String, Object>> findCkcjgInfoByUnitId(String id, String month) {
		String basicScore = DictUtils.getDictValue("4","exam_basicScore","100");
		Double baseSum;
		boolean isWeight;
		Integer count = examUnitAllPublicDao.getWeightIsNullByUserIdMonth(id,month,"4");//查询该月采取考评方式权重制/非权重制
		if(count!=null && count>0){
			try {
				baseSum = Double.valueOf(basicScore);
			}catch (Exception e){
				logger.error(getExceptionInfo(e));
				e.printStackTrace();
				baseSum = 100.0;
			}
			isWeight = false;
		}else{
			baseSum = 0.0;
			isWeight = true;
		}
		List<String> ids = new ArrayList<>();
		ids.add(id);
		List<String> userIds = userDao.getUserIdByUnitIds(ids);
		return dao.findCkcjgInfoByUnitId(id,userIds, month,baseSum,isWeight);
	}

	public List<Map<String, Object>> findCkcjgInfoByUnitIds(List<String> ids, String month) {
		String basicScore = DictUtils.getDictValue("4","exam_basicScore","100");
		Double baseSum;
		boolean isWeight;
		Integer count =  examUnitAllPublicDao.getWeightIsNullByUserIdMonth(null,month,"4");//查询该月采取考评方式权重制/非权重制
		if(count!=null && count>0){
			try {
				baseSum = Double.valueOf(basicScore);
			}catch (Exception e){
				logger.error(getExceptionInfo(e));
				e.printStackTrace();
				baseSum = 100.0;
			}
			isWeight = false;
		}else{
			baseSum = 0.0;
			isWeight = true;
		}
		List<String> userIds = userDao.getUserIdByUnitIds(ids);
		return dao.findCkcjgInfoByUnitIds(ids, userIds,month,baseSum,isWeight);
	}
	/*
	* 单位考评档案（弃用）
	* */
	public Page<ExamRecord> findUnitYear(Page<ExamRecord> page,String unitId) {
		List<Map<String,Object>> list = dao.findUnitYear(unitId);
		List<Map<String,Object>> MSlist = new ArrayList<>();
		Set<String> yearSet = new HashSet();
		for(Map<String,Object> map:list){
			String unitName = (String) map.get("unitName");
			String sqlUnitId = (String) map.get("unitId");
			String year = (String) map.get("time");
			String examCycle = (String) map.get("examCycle");//考评周期  1 - 月度 2 - 年度
			year = year.substring(0,4);

			Double sum = (Double) map.get("sum");
			Map<String,Object> tempMap = new HashMap<>();
			tempMap.put("unitId",sqlUnitId);
			tempMap.put("unitName",unitName);
			if("1".equals(examCycle)){
				//月度
				String month = ((String) map.get("time")).substring(4,6);
				int month_int = Integer.parseInt(month);
				tempMap.put("month",month_int);
			}
			tempMap.put("sum",sum);
			tempMap.put("year",year);
			yearSet.add(year);
			MSlist.add(tempMap);
		}
		List<ExamRecord> examRecordList = new ArrayList<>();
		for(String year : yearSet){
			//根据年度生成相应考评档案对象
			ExamRecord record = new ExamRecord();
			record.setYear(year);
			Map<String,Double> monthSumMap = new HashMap<>();
			Double yearSum = 0.0;
			Double yearEndSum = 0.0;//年终
			for(Map<String,Object> map : MSlist){
				if(map.get("year").equals(year)){
					record.setUnitId((String) map.get("unitId"));
					record.setUnit((String) map.get("unitName"));
					//月度分数
					if(map.get("month").toString()!=null){
						monthSumMap.put(map.get("month").toString(),(Double)map.get("sum"));
					}else{
						yearEndSum += (Double)map.get("sum");
					}
					Double sum =(Double)map.get("sum");
					yearSum += sum;
					record.setMonthSumMap(monthSumMap);
				}
			}
			record.setYearEndSum(yearEndSum);
			record.setYearSum(yearSum);
			examRecordList.add(record);
		}
		int count = examRecordList.size();
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		if(list!=null){
			List<ExamRecord> pageList;
			if(pageSize*pageNo<count){
				pageList = examRecordList.subList((pageNo-1)*pageSize,pageSize*pageNo);
			}else{
				pageList = examRecordList.subList((pageNo-1)*pageSize,count);
			}
			page.setCount(examRecordList.size());
			page.setList(pageList);
		}
		return page;
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

	@Transactional(readOnly = false)
	public void deleteByWorkflowId(String workflowId) {
		dao.deleteByWorkflowId(workflowId);
	}

	public Integer getWeightIsNull(String workflowId) {
		return dao.getWeightIsNull(workflowId);
	}
}