/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairFocusStudyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairFocusStudy;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党支部集中学习Service
 * @author cecil.li
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairFocusStudyService extends CrudService<AffairFocusStudyDao, AffairFocusStudy> {

	public AffairFocusStudy get(String id) {
		return super.get(id);
	}
	
	public List<AffairFocusStudy> findList(AffairFocusStudy affairFocusStudy) {
		return super.findList(affairFocusStudy);
	}
	
	public Page<AffairFocusStudy> findPage(Page<AffairFocusStudy> page, AffairFocusStudy affairFocusStudy) {
		affairFocusStudy.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairFocusStudy);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairFocusStudy affairFocusStudy) {
		super.save(affairFocusStudy);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairFocusStudy affairFocusStudy) {
		super.delete(affairFocusStudy);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}
	
}