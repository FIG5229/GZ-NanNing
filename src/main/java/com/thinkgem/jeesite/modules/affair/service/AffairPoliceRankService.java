/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceRank;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliceRankDao;

/**
 * 警衔管理表Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairPoliceRankService extends CrudService<AffairPoliceRankDao, AffairPoliceRank> {

	public AffairPoliceRank get(String id) {
		return super.get(id);
	}
	
	public List<AffairPoliceRank> findList(AffairPoliceRank affairPoliceRank) {
		return super.findList(affairPoliceRank);
	}
	
	public Page<AffairPoliceRank> findPage(Page<AffairPoliceRank> page, AffairPoliceRank affairPoliceRank) {
		return super.findPage(page, affairPoliceRank);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPoliceRank affairPoliceRank) {
		super.save(affairPoliceRank);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPoliceRank affairPoliceRank) {
		super.delete(affairPoliceRank);
	}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id, Integer year , Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgId(id, year ,startDate, endDate, month);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids, Integer year ,Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year ,startDate, endDate, month);
	}
	
}