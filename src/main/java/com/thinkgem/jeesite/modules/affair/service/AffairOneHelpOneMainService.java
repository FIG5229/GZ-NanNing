/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOneMain;
import com.thinkgem.jeesite.modules.affair.dao.AffairOneHelpOneMainDao;

/**
 * 一帮一明细Service
 * @author daniel.liu
 * @version 2020-06-23
 */
@Service
@Transactional(readOnly = true)
public class AffairOneHelpOneMainService extends CrudService<AffairOneHelpOneMainDao, AffairOneHelpOneMain> {

	public AffairOneHelpOneMain get(String id) {
		return super.get(id);
	}
	
	public List<AffairOneHelpOneMain> findList(AffairOneHelpOneMain affairOneHelpOneMain) {
		return super.findList(affairOneHelpOneMain);
	}
	
	public Page<AffairOneHelpOneMain> findPage(Page<AffairOneHelpOneMain> page, AffairOneHelpOneMain affairOneHelpOneMain) {
		return super.findPage(page, affairOneHelpOneMain);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOneHelpOneMain affairOneHelpOneMain) {
		super.save(affairOneHelpOneMain);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOneHelpOneMain affairOneHelpOneMain) {
		super.delete(affairOneHelpOneMain);
	}

	@Transactional(readOnly = false)
	public void deleteByParentIds(List<String> ids) {
		super.dao.deleteByParentIds(ids);
	}
}