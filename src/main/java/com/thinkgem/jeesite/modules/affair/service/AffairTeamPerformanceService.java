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
import com.thinkgem.jeesite.modules.affair.entity.AffairTeamPerformance;
import com.thinkgem.jeesite.modules.affair.dao.AffairTeamPerformanceDao;

/**
 * 比武团体成绩Service
 * @author cecil.li
 * @version 2020-12-29
 */
@Service
@Transactional(readOnly = true)
public class AffairTeamPerformanceService extends CrudService<AffairTeamPerformanceDao, AffairTeamPerformance> {

	public AffairTeamPerformance get(String id) {
		return super.get(id);
	}
	
	public List<AffairTeamPerformance> findList(AffairTeamPerformance affairTeamPerformance) {
		return super.findList(affairTeamPerformance);
	}
	
	public Page<AffairTeamPerformance> findPage(Page<AffairTeamPerformance> page, AffairTeamPerformance affairTeamPerformance) {
		affairTeamPerformance.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTeamPerformance);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTeamPerformance affairTeamPerformance) {
		super.save(affairTeamPerformance);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTeamPerformance affairTeamPerformance) {
		super.delete(affairTeamPerformance);
	}
	
}