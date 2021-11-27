/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCheckDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCheck;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查核对干部档案登记Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairCheckService extends CrudService<AffairCheckDao, AffairCheck> {

	public AffairCheck get(String id) {
		return super.get(id);
	}
	
	public List<AffairCheck> findList(AffairCheck affairCheck) {
		return super.findList(affairCheck);
	}
	
	public Page<AffairCheck> findPage(Page<AffairCheck> page, AffairCheck affairCheck) {
		affairCheck.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCheck affairCheck) {
		super.save(affairCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCheck affairCheck) {
		super.delete(affairCheck);
	}
	
}