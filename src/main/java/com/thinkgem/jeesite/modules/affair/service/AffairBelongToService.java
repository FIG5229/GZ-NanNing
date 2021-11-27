/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairBelongToDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairBelongTo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党员组织隶属Service
 * @author eav.liu
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class AffairBelongToService extends CrudService<AffairBelongToDao, AffairBelongTo> {

	public AffairBelongTo get(String id) {
		return super.get(id);
	}
	
	public List<AffairBelongTo> findList(AffairBelongTo affairBelongTo) {
		return super.findList(affairBelongTo);
	}
	
	public Page<AffairBelongTo> findPage(Page<AffairBelongTo> page, AffairBelongTo affairBelongTo) {
		affairBelongTo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairBelongTo);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairBelongTo affairBelongTo) {
		super.save(affairBelongTo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairBelongTo affairBelongTo) {
		super.delete(affairBelongTo);
	}
	
}