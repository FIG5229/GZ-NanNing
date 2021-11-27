/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairRetireDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairRetire;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 离退干部档案登记花名册Service
 * @author mason.xv
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairRetireService extends CrudService<AffairRetireDao, AffairRetire> {

	public AffairRetire get(String id) {
		return super.get(id);
	}
	
	public List<AffairRetire> findList(AffairRetire affairRetire) {
		return super.findList(affairRetire);
	}
	
	public Page<AffairRetire> findPage(Page<AffairRetire> page, AffairRetire affairRetire) {
		affairRetire.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairRetire);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairRetire affairRetire) {
		super.save(affairRetire);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairRetire affairRetire) {
		super.delete(affairRetire);
	}
	
}