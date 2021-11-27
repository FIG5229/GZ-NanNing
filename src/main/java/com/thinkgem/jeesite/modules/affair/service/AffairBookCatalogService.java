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
import com.thinkgem.jeesite.modules.affair.entity.AffairBookCatalog;
import com.thinkgem.jeesite.modules.affair.dao.AffairBookCatalogDao;

/**
 * 历年书目Service
 * @author alan.wu
 * @version 2020-07-24
 */
@Service
@Transactional(readOnly = true)
public class AffairBookCatalogService extends CrudService<AffairBookCatalogDao, AffairBookCatalog> {

	public AffairBookCatalog get(String id) {
		return super.get(id);
	}
	
	public List<AffairBookCatalog> findList(AffairBookCatalog affairBookCatalog) {
		return super.findList(affairBookCatalog);
	}
	
	public Page<AffairBookCatalog> findPage(Page<AffairBookCatalog> page, AffairBookCatalog affairBookCatalog) {
		affairBookCatalog.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairBookCatalog);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairBookCatalog affairBookCatalog) {
		super.save(affairBookCatalog);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairBookCatalog affairBookCatalog) {
		super.delete(affairBookCatalog);
	}
	
}