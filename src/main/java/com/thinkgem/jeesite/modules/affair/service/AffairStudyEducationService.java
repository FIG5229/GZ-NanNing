/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairStudyEducationDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairStudyEducation;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学习教育Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairStudyEducationService extends CrudService<AffairStudyEducationDao, AffairStudyEducation> {

	public AffairStudyEducation get(String id) {
		return super.get(id);
	}
	
	public List<AffairStudyEducation> findList(AffairStudyEducation affairStudyEducation) {
		return super.findList(affairStudyEducation);
	}
	
	public Page<AffairStudyEducation> findPage(Page<AffairStudyEducation> page, AffairStudyEducation affairStudyEducation) {
		affairStudyEducation.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairStudyEducation);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairStudyEducation affairStudyEducation) {
		super.save(affairStudyEducation);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairStudyEducation affairStudyEducation) {
		super.delete(affairStudyEducation);
	}
	
}