/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTnActivityEnrollDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTnActivityEnroll;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 团内活动报名Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairTnActivityEnrollService extends CrudService<AffairTnActivityEnrollDao, AffairTnActivityEnroll> {

	public AffairTnActivityEnroll get(String id) {
		return super.get(id);
	}
	
	public List<AffairTnActivityEnroll> findList(AffairTnActivityEnroll affairTnActivityEnroll) {
		return super.findList(affairTnActivityEnroll);
	}
	
	public Page<AffairTnActivityEnroll> findPage(Page<AffairTnActivityEnroll> page, AffairTnActivityEnroll affairTnActivityEnroll) {
		affairTnActivityEnroll.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTnActivityEnroll);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTnActivityEnroll affairTnActivityEnroll) {
		super.save(affairTnActivityEnroll);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTnActivityEnroll affairTnActivityEnroll) {
		super.delete(affairTnActivityEnroll);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}
}