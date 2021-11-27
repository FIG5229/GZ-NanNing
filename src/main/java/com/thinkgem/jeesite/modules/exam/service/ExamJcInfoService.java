/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamJcInfoDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamJcInfo;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateItemDefine;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奖惩信息库Service
 * @author mason.xv
 * @version 2020-01-03
 */
@Service
@Transactional(readOnly = true)
public class ExamJcInfoService extends CrudService<ExamJcInfoDao, ExamJcInfo> {

	@Autowired
	private ExamJcInfoDao examJcInfoDao;

	@Autowired
	private ExamStandardTemplateDefineService examStandardTemplateDefineService;


	public ExamJcInfo get(String id) {
		return super.get(id);
	}
	
	public List<ExamJcInfo> findList(ExamJcInfo examJcInfo) {
		return super.findList(examJcInfo);
	}
	
	public Page<ExamJcInfo> findPage(Page<ExamJcInfo> page, ExamJcInfo examJcInfo) {
		examJcInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, examJcInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamJcInfo examJcInfo) {
		if (StringUtils.isBlank(examJcInfo.getRowNum())){
			Map<String,String> param = new HashMap<>();
			param.put("standardId",examJcInfo.getMyUseModel());
			param.put("itemValue",examJcInfo.getChangeType());
			List<Map<String, String>> resultList = examStandardTemplateDefineService.findTemplateDatasBeta(param);
			if (resultList!= null && resultList.size()>0){
				Map<String, String> map = resultList.get(0);
				examJcInfo.setRowNum(map.get("row_num"));
			}
		}

		ExamJcInfo examJcInfoFromDb = examJcInfoDao.findInfoByFileNum(examJcInfo.getFileNum());
		if(examJcInfoFromDb == null){
			super.save(examJcInfo);
		}else{
			examJcInfo.setId(examJcInfoFromDb.getId());
			super.save(examJcInfo);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamJcInfo examJcInfo) {
		super.delete(examJcInfo);
	}

	/**
	 * 绩效自动考评查询奖惩信息库
	 * @param examJcInfo
	 * @return
	 */
	public List<ExamJcInfo> findExamList(ExamJcInfo examJcInfo) {
		return dao.findExamList(examJcInfo);
	}
}