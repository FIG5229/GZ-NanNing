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
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateItemDefine;
import com.thinkgem.jeesite.modules.exam.dao.ExamStandardTemplateItemDefineDao;

/**
 * 模板项管理Service
 * @author bradley.zhao
 * @version 2019-12-12
 */
@Service
@Transactional(readOnly = true)
public class ExamStandardTemplateItemDefineService extends CrudService<ExamStandardTemplateItemDefineDao, ExamStandardTemplateItemDefine> {
	@Autowired
	private ExamStandardTemplateItemDefineDao dao;

	public ExamStandardTemplateItemDefine get(String id) {
		return super.get(id);
	}
	
	public List<ExamStandardTemplateItemDefine> findList(ExamStandardTemplateItemDefine examStandardTemplateItemDefine) {
		return super.findList(examStandardTemplateItemDefine);
	}
	
	public Page<ExamStandardTemplateItemDefine> findPage(Page<ExamStandardTemplateItemDefine> page, ExamStandardTemplateItemDefine examStandardTemplateItemDefine) {
		return super.findPage(page, examStandardTemplateItemDefine);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamStandardTemplateItemDefine examStandardTemplateItemDefine) {
		super.save(examStandardTemplateItemDefine);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamStandardTemplateItemDefine examStandardTemplateItemDefine) {
		super.delete(examStandardTemplateItemDefine);
	}

	//根据模板id查询字段
    public List<Map<String, String>> findByTemplateId(String templateId) {
		return dao.findByTemplateId(templateId);
    }
}