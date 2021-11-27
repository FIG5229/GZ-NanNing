/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamAutoEvaluationDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoEvaluation;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoStandardRelevance;
import com.thinkgem.jeesite.modules.exam.dao.ExamAutoStandardRelevanceDao;

/**
 * 自动考评考评标准关联Service
 * @author kevin.jia
 * @version 2021-01-20
 */
@Service
@Transactional(readOnly = true)
public class ExamAutoStandardRelevanceService extends CrudService<ExamAutoStandardRelevanceDao, ExamAutoStandardRelevance> {

	@Autowired
	private ExamStandardBaseInfoService examStandardBaseInfoService;

	@Autowired
	private ExamStandardTemplateDataService examStandardTemplateDataService;

	public ExamAutoStandardRelevance get(String id) {
		return super.get(id);
	}
	
	public List<ExamAutoStandardRelevance> findList(ExamAutoStandardRelevance examAutoStandardRelevance) {
		return super.findList(examAutoStandardRelevance);
	}
	
	public Page<ExamAutoStandardRelevance> findPage(Page<ExamAutoStandardRelevance> page, ExamAutoStandardRelevance examAutoStandardRelevance) {
		User user =  UserUtils.getUser();
		if("34".equals(user.getOffice().getId())||user.getOffice().getParentIds().contains(",34,")){
			examAutoStandardRelevance.setUserId("34");
		}else if("95".equals(user.getOffice().getId())||user.getOffice().getParentIds().contains(",95,")){
			examAutoStandardRelevance.setUserId("95");
		}else if("156".equals(user.getOffice().getId())||user.getOffice().getParentIds().contains(",156,")){
			examAutoStandardRelevance.setUserId("156");
		}else{
			examAutoStandardRelevance.setUserId(null);
		}
		return super.findPage(page, examAutoStandardRelevance);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamAutoStandardRelevance examAutoStandardRelevance) {

		ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(examAutoStandardRelevance.getModelId());
		examAutoStandardRelevance.setModel(examStandardBaseInfo.getName());
		ExamStandardTemplateData examStandardTemplateData = examStandardTemplateDataService.get(examAutoStandardRelevance.getOptionId());
		examAutoStandardRelevance.setOption(examStandardTemplateData.getItemValue());
		if(StringUtils.isNotBlank(examAutoStandardRelevance.getChuId())){
			switch (examAutoStandardRelevance.getChuId()){
				case "34":
					examAutoStandardRelevance.setChu("南宁处");
					break;
				case "95":
					examAutoStandardRelevance.setChu("柳州处");
					break;
				case "156":
					examAutoStandardRelevance.setChu("北海处");
					break;
				default:
					break;
			}
		}
		if("".equals(examAutoStandardRelevance.getId()) || examAutoStandardRelevance.getId() == null){
			examAutoStandardRelevance.setNewModel(examAutoStandardRelevance.getModel());
			examAutoStandardRelevance.setNewModelId(examAutoStandardRelevance.getModelId());
			examAutoStandardRelevance.setNewOption(examAutoStandardRelevance.getOption());
			examAutoStandardRelevance.setNewOptionId(examAutoStandardRelevance.getOptionId());
		}
		ExamStandardBaseInfo examStandardBaseInfoOne = examStandardBaseInfoService.get(examAutoStandardRelevance.getNewModelId());
		examAutoStandardRelevance.setNewModel(examStandardBaseInfoOne.getName());
		ExamStandardTemplateData examStandardTemplateDataNew = examStandardTemplateDataService.get(examAutoStandardRelevance.getNewOptionId());
		examAutoStandardRelevance.setNewOption(examStandardTemplateDataNew.getItemValue());
		super.save(examAutoStandardRelevance);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamAutoStandardRelevance examAutoStandardRelevance) {
		super.delete(examAutoStandardRelevance);
	}
	/**
	 * @param examAutoEvaluation 自动考评实体类  period考评周期，考evalType评类别必须有值
	 * @param unitId 34 95 156 除局考处，局考局机关，处领导考评外必须有值
	 * @param item 考评事项简称 目前还未定义完全
	 *
	 */
	public ExamAutoEvaluation setExamAutoModelOption(ExamAutoEvaluation examAutoEvaluation,String unitId,String item){
		try {
			ExamAutoStandardRelevance examAutoStandardRelecance = dao.getExamAutoStandardRelecance(examAutoEvaluation.getEvalType(),examAutoEvaluation.getPeriod(),unitId,item.trim());
			if(examAutoStandardRelecance!=null){
				if(examAutoStandardRelecance.getNewModel() != null && examAutoStandardRelecance.getNewModelId() != null
						&& examAutoStandardRelecance.getNewOption() != null && examAutoStandardRelecance.getNewOptionId() != null){

					examAutoEvaluation.setModel(examAutoStandardRelecance.getNewModel());
					examAutoEvaluation.setModelId(examAutoStandardRelecance.getNewModelId());
					examAutoEvaluation.setOption(examAutoStandardRelecance.getNewOption());
					examAutoEvaluation.setOptionId(examAutoStandardRelecance.getNewOptionId());
				}else {
					examAutoEvaluation.setModel(examAutoStandardRelecance.getModel());
					examAutoEvaluation.setModelId(examAutoStandardRelecance.getModelId());
					examAutoEvaluation.setOption(examAutoStandardRelecance.getOption());
					examAutoEvaluation.setOptionId(examAutoStandardRelecance.getOptionId());
				}
			}
		}catch (Exception e){
			logger.error(getExceptionInfo(e));
		}

		return examAutoEvaluation;
	}

    public List<String> findAllItems() {
		return dao.findAllItems();
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
}