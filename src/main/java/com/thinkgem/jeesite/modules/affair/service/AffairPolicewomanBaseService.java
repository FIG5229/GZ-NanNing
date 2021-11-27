/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPolicewomanBaseDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanBase;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 女警基本情况Service
 * @author cecil.li
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairPolicewomanBaseService extends CrudService<AffairPolicewomanBaseDao, AffairPolicewomanBase> {

	public AffairPolicewomanBase get(String id) {
		return super.get(id);
	}
	
	public List<AffairPolicewomanBase> findList(AffairPolicewomanBase affairPolicewomanBase) {
		return super.findList(affairPolicewomanBase);
	}
	
	public Page<AffairPolicewomanBase> findPage(Page<AffairPolicewomanBase> page, AffairPolicewomanBase affairPolicewomanBase) {
		affairPolicewomanBase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPolicewomanBase);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPolicewomanBase affairPolicewomanBase) {
		super.save(affairPolicewomanBase);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPolicewomanBase affairPolicewomanBase) {
		super.delete(affairPolicewomanBase);
	}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}
	
}