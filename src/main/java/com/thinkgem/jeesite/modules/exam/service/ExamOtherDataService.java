/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamOtherDataChildDao;
import com.thinkgem.jeesite.modules.exam.entity.*;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.dao.ExamOtherDataDao;

/**
 * 其他数据Service
 * @author kevin.jia
 * @version 2020-11-11
 */
@Service
@Transactional(readOnly = true)
public class ExamOtherDataService extends CrudService<ExamOtherDataDao, ExamOtherData> {

	@Autowired
	private ExamStandardBaseInfoService examStandardBaseInfoService;

	@Autowired
	private ExamStandardTemplateDefineService examStandardTemplateDefineService;

	@Autowired
    private ExamOtherDataChildDao examOtherDataChildDao;

	/*public ExamOtherData get(String id) {
		return super.get(id);
	}*/
	public ExamOtherData get(String id) {
        ExamOtherData examOtherData = super.get(id);
        ExamOtherDataChild examOtherDataChild= new ExamOtherDataChild();
        examOtherDataChild.setOtherId(id);
        List<ExamOtherDataChild> childList = examOtherDataChildDao.findList(examOtherDataChild);
        examOtherData.setExamOtherDataList(childList);

        return examOtherData;
	}

	
	public List<ExamOtherData> findList(ExamOtherData examOtherData) {
		return super.findList(examOtherData);
	}
	
	public Page<ExamOtherData> findPage(Page<ExamOtherData> page, ExamOtherData examOtherData) {
		examOtherData.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, examOtherData);
	}
	
	/*@Transactional(readOnly = false)
	public void save(ExamOtherData examOtherData) {
		*//*if (examOtherData.getIsNewRecord()){
			examOtherData.setCreateOrgId(UserUtils.getUser().getOffice().getId());
			examOtherData.setUpdateOrgId(UserUtils.getUser().getOffice().getId());
		}else{
			examOtherData.setUpdateOrgId(UserUtils.getUser().getOffice().getId());
		}*//*
		super.save(examOtherData);
	}*/

    @Transactional(readOnly = false)
    public void save(ExamOtherData examOtherData) {
        super.save(examOtherData);
        //保存子表数据
        for (ExamOtherDataChild examOtherDataChild : examOtherData.getExamOtherDataList()) {
            if (examOtherDataChild.getId() == null) {
                continue;
            }
            if (ExamCheckChild.DEL_FLAG_NORMAL.equals(examOtherDataChild.getDelFlag())) {
                //设置模板名称
                ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(examOtherDataChild.getUseModel());
                if (examStandardBaseInfo != null) {
                    examOtherDataChild.setUseModelName(examStandardBaseInfo.getName());
                }
                if (StringUtils.isBlank(examOtherDataChild.getRowNum())){
                    Map<String,String> param = new HashMap<>();
                    param.put("standardId",examOtherDataChild.getUseModel());
                    param.put("itemValue",examOtherDataChild.getChooseOptions());
                    List<Map<String, String>> resultList = examStandardTemplateDefineService.findTemplateDatasBeta(param);
                    if (resultList!= null && resultList.size()>0){
                        Map<String, String> map = resultList.get(0);
                        examOtherDataChild.setRowNum(map.get("row_num"));
                    }
                }
                if (StringUtils.isBlank(examOtherDataChild.getId())) {
                    examOtherDataChild.setOtherId(examOtherData.getId());
                    examOtherDataChild.preInsert();
                    examOtherDataChild.setCheckDate(examOtherData.getCheckDate());
                    examOtherDataChild.setCheckPerson(examOtherData.getCheckPerson());
                    examOtherDataChild.setCheckUnit(examOtherData.getCheckUnit());
                    examOtherDataChild.setCheckUnitId(examOtherData.getCheckUnitId());
                    examOtherDataChild.setScortSituation("责任领导："+examOtherDataChild.getDutyLeader()+",责任人："+
                            examOtherDataChild.getDutyPerson()+". "+examOtherDataChild.getScortSituation());
                    examOtherDataChildDao.insert(examOtherDataChild);

                    /*关联到问题整改（王俊宇让再保存一份）*//*
                    ExamOtherData problemModify  = new ExamOtherData();
                    problemModify.setDutyUnit(examOtherDataChild.getDutyUnit());
                    problemModify.setDutyUnitId(examOtherDataChild.getDutyUnitId());
                    problemModify.setDutyLeader(examOtherDataChild.getDutyLeader());
                    problemModify.setDutyLeaderId(examOtherDataChild.getDutyLeaderId());
                    problemModify.setDutyPerson(examOtherDataChild.getDutyPerson());
                    problemModify.setDutyPersonId(examOtherDataChild.getDutyPersonId());
                    problemModify.setUseModel(examOtherDataChild.getUseModel());
                    problemModify.setUseModelName(examOtherDataChild.getUseModelName());
                    problemModify.setChooseOptions(examOtherDataChild.getChooseOptions());
                    problemModify.setTestStandart(examOtherDataChild.getTestStandart());
                    problemModify.setScortSituation(examOtherDataChild.getScortSituation());
                    problemModify.setRemark(examOtherDataChild.getRemark());
                    problemModify.setCheckDate(examOtherData.getCheckDate());
                    problemModify.setCheckPerson(examOtherData.getCheckPerson());
                    problemModify.setCheckPersonId(examOtherData.getCheckPersonId());
                    problemModify.setCheckUnit(examOtherData.getCheckUnit());
                    problemModify.setCheckUnitId(examOtherData.getCheckUnitId());
//					examCheckService1.save(problemModify);*/
                } else {
                    examOtherDataChild.preUpdate();
                    examOtherDataChild.setScortSituation("责任领导："+examOtherDataChild.getDutyLeader()+",责任人："+
                            examOtherDataChild.getDutyPerson()+". "+examOtherDataChild.getScortSituation());
                    examOtherDataChildDao.update(examOtherDataChild);
                }
            } else {
                examOtherDataChildDao.delete(examOtherDataChild);
            }
        }
    }
	
	@Transactional(readOnly = false)
	public void delete(ExamOtherData examOtherData) {
		super.delete(examOtherData);
		//删除子表数据
        examOtherDataChildDao.delete(new ExamOtherDataChild(examOtherData.getId()));
    }
	
}