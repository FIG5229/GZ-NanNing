/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairGroupFeeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGroupFee;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 团费收支Service
 * @author cecil.li
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairGroupFeeService extends CrudService<AffairGroupFeeDao, AffairGroupFee> {

	public AffairGroupFee get(String id) {
		return super.get(id);
	}
	
	public List<AffairGroupFee> findList(AffairGroupFee affairGroupFee) {
		return super.findList(affairGroupFee);
	}
	
	public Page<AffairGroupFee> findPage(Page<AffairGroupFee> page, AffairGroupFee affairGroupFee) {
		affairGroupFee.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairGroupFee);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGroupFee affairGroupFee) {
		super.save(affairGroupFee);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGroupFee affairGroupFee) {
		super.delete(affairGroupFee);
	}
	
}