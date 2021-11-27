/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamCheckChildDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamCheckDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheck;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheck1;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheckChild;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查法相情况录入Service
 * @author mason.xv
 * @version 2019-12-09
 */
@Service
@Transactional(readOnly = true)
public class ExamCheckService extends CrudService<ExamCheckDao, ExamCheck> {
	@Autowired
	private ExamStandardBaseInfoService examStandardBaseInfoService;

	@Autowired
	private ExamCheckChildDao examCheckChildDao;

	@Autowired
	private ExamStandardTemplateDefineService examStandardTemplateDefineService;

	@Autowired
	private ExamCheckService1 examCheckService1;

	public ExamCheck get(String id) {
		ExamCheck examCheck = super.get(id);
		ExamCheckChild examCheckChild = new ExamCheckChild();
		examCheckChild.setCheckId(id);
		/*if ("cca66e1339f14799b01f6db43ed16e16".equals(UserUtils.getUser().getId()) || "978958003ea44a4bba3eed8ee6ceff3c".equals(UserUtils.getUser().getId()) || "6af0e615e9b0477da82eff06ff532c8b".equals(UserUtils.getUser().getId()) || "46c521bf67e24db28772b3eac52dc454".equals(UserUtils.getUser().getId())){
			examCheckChild.setUserOffice(UserUtils.getUser().getCompany().getId());
		}else {
			examCheckChild.setUserOffice(UserUtils.getUser().getOffice().getId());
		}*/
		List<ExamCheckChild> childList = examCheckChildDao.findList2(examCheckChild);
		examCheck.setExamCheckChildList(childList);

		return examCheck;
	}
	
	public List<ExamCheck> findList(ExamCheck examCheck) {
		return super.findList(examCheck);
	}
	
	public Page<ExamCheck> findPage(Page<ExamCheck> page, ExamCheck examCheck) {
		examCheck.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, examCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamCheck examCheck) {
		super.save(examCheck);
		//保存子表数据
		for (ExamCheckChild examCheckChild : examCheck.getExamCheckChildList()) {
			if (examCheckChild.getId() == null) {
				continue;
			}
			if (ExamCheckChild.DEL_FLAG_NORMAL.equals(examCheckChild.getDelFlag())) {
				//设置模板名称
				ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(examCheckChild.getUseModel());
				if (examStandardBaseInfo != null) {
					examCheckChild.setUseModelName(examStandardBaseInfo.getName());
				}
				/*修改时也修改行号*/
				/*if (StringUtils.isBlank(examCheckChild.getRowNum())){*/
					Map<String,String> param = new HashMap<>();
					param.put("standardId",examCheckChild.getUseModel());
					//param.put("itemValue",examCheckChild.getChooseOptions());
					param.put("optionId",examCheckChild.getChooseOptions());
					List<Map<String, String>> resultList = examStandardTemplateDefineService.findTemplateDatasBeta(param);
					if (resultList!= null && resultList.size()>0){
						Map<String, String> map = resultList.get(0);
						examCheckChild.setRowNum(map.get("row_num"));
						examCheckChild.setChooseOptionsName(map.get("item_value"));
					}
				/*}*/
				if (StringUtils.isBlank(examCheckChild.getId())) {
					examCheckChild.setCheckId(examCheck.getId());
					examCheckChild.preInsert();
					examCheckChild.setCheckDate(examCheck.getCheckDate());
					examCheckChild.setCheckPerson(examCheck.getCheckPerson());
					examCheckChild.setCheckPersonId(examCheck.getCheckPersonId());
					examCheckChild.setCheckUnit(examCheck.getCheckUnit());
					examCheckChild.setCheckUnitId(examCheck.getCheckUnitId());
					examCheckChild.setScortSituation("责任领导："+examCheckChild.getDutyLeader()+",责任人："+
							examCheckChild.getDutyPerson()+". "+examCheckChild.getScortSituation());
					examCheckChildDao.insert(examCheckChild);

					/*关联到问题整改（王俊宇让再保存一份）*/
					ExamCheck1 problemModify  = new ExamCheck1();
					problemModify.setDutyUnit(examCheckChild.getDutyUnit());
					problemModify.setDutyUnitId(examCheckChild.getDutyUnitId());
					problemModify.setDutyLeader(examCheckChild.getDutyLeader());
					problemModify.setDutyLeaderId(examCheckChild.getDutyLeaderId());
					problemModify.setDutyPerson(examCheckChild.getDutyPerson());
					problemModify.setDutyPersonId(examCheckChild.getDutyPersonId());
					problemModify.setUseModel(examCheckChild.getUseModel());
					problemModify.setUseModelName(examCheckChild.getUseModelName());
					problemModify.setChooseOptions(examCheckChild.getChooseOptions());
					problemModify.setTestStandart(examCheckChild.getTestStandart());
					problemModify.setScortSituation(examCheckChild.getScortSituation());
					problemModify.setRemark(examCheckChild.getRemark());
					problemModify.setCheckDate(examCheck.getCheckDate());
					problemModify.setCheckPerson(examCheck.getCheckPerson());
					problemModify.setCheckPersonId(examCheck.getCheckPersonId());
					problemModify.setCheckUnit(examCheck.getCheckUnit());
					problemModify.setCheckUnitId(examCheck.getCheckUnitId());
//					examCheckService1.save(problemModify);
				} else {
					examCheckChild.preUpdate();
					examCheckChild.setScortSituation("责任领导："+examCheckChild.getDutyLeader()+",责任人："+
							examCheckChild.getDutyPerson()+". "+examCheckChild.getScortSituation());
					examCheckChildDao.update(examCheckChild);
				}
			} else {
				examCheckChildDao.delete(examCheckChild);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamCheck examCheck) {
		super.delete(examCheck);
		//删除子表据
		examCheckChildDao.deleteByCheckId(examCheck.getId());
	}
}