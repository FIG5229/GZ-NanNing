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
import com.thinkgem.jeesite.modules.affair.entity.AffairThreeBase;
import com.thinkgem.jeesite.modules.affair.dao.AffairThreeBaseDao;

/**
 * 三基综合Service
 * @author cecil.li
 * @version 2020-12-29
 */
@Service
@Transactional(readOnly = true)
public class AffairThreeBaseService extends CrudService<AffairThreeBaseDao, AffairThreeBase> {

	public AffairThreeBase get(String id) {
		return super.get(id);
	}
	
	public List<AffairThreeBase> findList(AffairThreeBase affairThreeBase) {
		return super.findList(affairThreeBase);
	}
	
	public Page<AffairThreeBase> findPage(Page<AffairThreeBase> page, AffairThreeBase affairThreeBase) {
		affairThreeBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairThreeBase);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairThreeBase affairThreeBase) {
		super.save(affairThreeBase);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairThreeBase affairThreeBase) {
		super.delete(affairThreeBase);
	}
	
}