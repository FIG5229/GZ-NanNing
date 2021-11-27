/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import com.thinkgem.jeesite.modules.exam.dao.ExamStandardTemplateDataDao;

/**
 * 模板项数据Service
 * @author bradley.zhao
 * @version 2019-12-13
 */
@Service
@Transactional(readOnly = true)
public class ExamStandardTemplateDataService extends CrudService<ExamStandardTemplateDataDao, ExamStandardTemplateData> {
	@Autowired
	ExamStandardTemplateDataDao examStandardTemplateDataDao;

	public ExamStandardTemplateData get(String id) {
		return super.get(id);
	}
	
	public List<ExamStandardTemplateData> findList(ExamStandardTemplateData examStandardTemplateData) {
		return super.findList(examStandardTemplateData);
	}
	
	public Page<ExamStandardTemplateData> findPage(Page<ExamStandardTemplateData> page, ExamStandardTemplateData examStandardTemplateData) {
		return super.findPage(page, examStandardTemplateData);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamStandardTemplateData examStandardTemplateData) {
		super.save(examStandardTemplateData);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamStandardTemplateData examStandardTemplateData) {
		super.delete(examStandardTemplateData);
	}

	@Transactional(readOnly = false)
	public void deleteByExamStandardId(String examStandId){
		examStandardTemplateDataDao.deleteByExamStandardId(examStandId);
	}

	public List<Map<String,Object>> findExamItemNumList(String[] standardIds){
		return examStandardTemplateDataDao.findExamItemNumList(standardIds);
	}

	public List<Map<String,String>> findRow(ExamStandardTemplateData standardTemplateData) {
		return dao.findRowList(standardTemplateData);
	}

	@Transactional(readOnly = false)
	public void deleteLine(ExamStandardTemplateData examStandardTemplateData) {
		dao.deleteLine(examStandardTemplateData);
	}

	public Integer getMaxRowNum(String itemId) {
		return dao.getMaxRowNum(itemId);
	}

	/**
	 * 根据考评标准和行号获取一行的内容
	 * @param standard
	 * @param rowNum
	 * @return
	 */
	public List<ExamStandardTemplateData> getStandardRow(String standard, String rowNum) {

		List<ExamStandardTemplateData> list = dao.findStandardOptionList(standard,rowNum);

		return list;
	}
}