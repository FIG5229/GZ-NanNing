/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCheckCountDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCheckCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 档案管理统计台账Service
 * @author cecil.li
 * @version 2020-02-26
 */
@Service
@Transactional(readOnly = true)
public class AffairCheckCountService extends CrudService<AffairCheckCountDao, AffairCheckCount> {

	@Autowired
	private AffairCheckCountDao affairCheckCountDao;

	public AffairCheckCount get(String id) {
		return super.get(id);
	}
	
	public List<AffairCheckCount> findList(AffairCheckCount affairCheckCount) {
		return super.findList(affairCheckCount);
	}
	
	public Page<AffairCheckCount> findPage(Page<AffairCheckCount> page, AffairCheckCount affairCheckCount) {
		return super.findPage(page, affairCheckCount);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCheckCount affairCheckCount) {
		super.save(affairCheckCount);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCheckCount affairCheckCount) {
		super.delete(affairCheckCount);
	}

	@Transactional(readOnly = false)
	public void deleteAll() {
		affairCheckCountDao.deleteAll();
	}
	
}