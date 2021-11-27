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
import com.thinkgem.jeesite.modules.affair.entity.AffairAdvancedCollective;
import com.thinkgem.jeesite.modules.affair.dao.AffairAdvancedCollectiveDao;

/**
 * 读书先进--集体Service
 * @author alan.wu
 * @version 2020-07-24
 */
@Service
@Transactional(readOnly = true)
public class AffairAdvancedCollectiveService extends CrudService<AffairAdvancedCollectiveDao, AffairAdvancedCollective> {

	public AffairAdvancedCollective get(String id) {
		return super.get(id);
	}
	
	public List<AffairAdvancedCollective> findList(AffairAdvancedCollective affairAdvancedCollective) {
		return super.findList(affairAdvancedCollective);
	}
	
	public Page<AffairAdvancedCollective> findPage(Page<AffairAdvancedCollective> page, AffairAdvancedCollective affairAdvancedCollective) {
		affairAdvancedCollective.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(),"o","a"));
		affairAdvancedCollective.setUnitId(UserUtils.getUser().getOffice().getId());
		affairAdvancedCollective.setUserId(UserUtils.getUser().getId());
		return super.findPage(page, affairAdvancedCollective);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairAdvancedCollective affairAdvancedCollective) {
		super.save(affairAdvancedCollective);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairAdvancedCollective affairAdvancedCollective) {
		super.delete(affairAdvancedCollective);
	}
	
}