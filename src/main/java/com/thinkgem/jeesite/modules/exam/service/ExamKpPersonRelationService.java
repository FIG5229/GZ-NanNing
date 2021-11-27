/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamKpPersonRelation;
import com.thinkgem.jeesite.modules.exam.dao.ExamKpPersonRelationDao;

/**
 * 考评初审人员关系表Service
 * @author kevin.jia
 * @version 2020-11-18
 */
@Service
@Transactional(readOnly = true)
public class ExamKpPersonRelationService extends CrudService<ExamKpPersonRelationDao, ExamKpPersonRelation> {

	public ExamKpPersonRelation get(String id) {
		return super.get(id);
	}
	
	public List<ExamKpPersonRelation> findList(ExamKpPersonRelation examKpPersonRelation) {
		return super.findList(examKpPersonRelation);
	}
	
	public Page<ExamKpPersonRelation> findPage(Page<ExamKpPersonRelation> page, ExamKpPersonRelation examKpPersonRelation) {
		return super.findPage(page, examKpPersonRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamKpPersonRelation examKpPersonRelation) {
		String kpUserId = examKpPersonRelation.getKpUserId();
		String csUserId = examKpPersonRelation.getCsUserId();
		//User kpUser = UserUtils.get(kpUserId);
		//User csUser = UserUtils.get(csUserId);
		//examKpPersonRelation.setUnitName(kpUser.getOffice().getName());
		//examKpPersonRelation.setUnitId(kpUser.getOffice().getId());
		//examKpPersonRelation.setCsUnitName(csUser.getOffice().getName());
		//examKpPersonRelation.setCsUnitId(csUser.getOffice().getId());
		super.save(examKpPersonRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamKpPersonRelation examKpPersonRelation) {
		super.delete(examKpPersonRelation);
	}
	
}