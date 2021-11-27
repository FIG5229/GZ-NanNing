/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairOnlineLearning;
import com.thinkgem.jeesite.modules.affair.dao.AffairOnlineLearningDao;

/**
 * 线上学习Service
 * @author cecil.li
 * @version 2020-12-29
 */
@Service
@Transactional(readOnly = true)
public class AffairOnlineLearningService extends CrudService<AffairOnlineLearningDao, AffairOnlineLearning> {

	public AffairOnlineLearning get(String id) {
		return super.get(id);
	}
	
	public List<AffairOnlineLearning> findList(AffairOnlineLearning affairOnlineLearning) {
		return super.findList(affairOnlineLearning);
	}
	
	public Page<AffairOnlineLearning> findPage(Page<AffairOnlineLearning> page, AffairOnlineLearning affairOnlineLearning) {
		affairOnlineLearning.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairOnlineLearning);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOnlineLearning affairOnlineLearning) {
		super.save(affairOnlineLearning);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOnlineLearning affairOnlineLearning) {
		super.delete(affairOnlineLearning);
	}
	
}