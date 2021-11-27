/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairExamManagementDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairExamManagement;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 考核成绩Service
 * @author cecil.li
 * @version 2020-03-22
 */
@Service
@Transactional(readOnly = true)
public class AffairExamManagementService extends CrudService<AffairExamManagementDao, AffairExamManagement> {

	public AffairExamManagement get(String id) {
		return super.get(id);
	}
	
	public List<AffairExamManagement> findList(AffairExamManagement affairExamManagement) {
		return super.findList(affairExamManagement);
	}
	
	public Page<AffairExamManagement> findPage(Page<AffairExamManagement> page, AffairExamManagement affairExamManagement) {
		affairExamManagement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairExamManagement);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairExamManagement affairExamManagement) {
		super.save(affairExamManagement);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairExamManagement affairExamManagement) {
		super.delete(affairExamManagement);
	}
	
}