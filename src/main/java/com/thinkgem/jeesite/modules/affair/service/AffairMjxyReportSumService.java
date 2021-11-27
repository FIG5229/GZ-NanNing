/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHome;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReportSum;
import com.thinkgem.jeesite.modules.affair.dao.AffairMjxyReportSumDao;

/**
 * 民警休养汇总Service
 * @author mason.xv
 * @version 2020-04-14
 */
@Service
@Transactional(readOnly = true)
public class AffairMjxyReportSumService extends CrudService<AffairMjxyReportSumDao, AffairMjxyReportSum> {

	public AffairMjxyReportSum get(String id) {
		AffairMjxyReportSum entity =super.get(id);
		AffairMjxyReportSum param = new AffairMjxyReportSum();
		param.setStartDate(entity.getStartDate());
		param.setEndDate(entity.getEndDate());
		param.setType(entity.getType());
		param.setPlace(entity.getPlace());
		param.setUserId(UserUtils.getUser().getId());
		List<AffairMjxyReportSum> children = super.findList(param);
		entity.setChildrens(children);
		return entity;
	}
	
	public List<AffairMjxyReportSum> findList(AffairMjxyReportSum affairMjxyReportSum) {
		affairMjxyReportSum.setUserId(UserUtils.getUser().getId());
		return super.findList(affairMjxyReportSum);
	}
	
	public Page<AffairMjxyReportSum> findPage(Page<AffairMjxyReportSum> page, AffairMjxyReportSum affairMjxyReportSum) {
		affairMjxyReportSum.setUserId(UserUtils.getUser().getId());
		return super.findInPage(page, affairMjxyReportSum);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairMjxyReportSum affairMjxyReportSum) {
		super.save(affairMjxyReportSum);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairMjxyReportSum affairMjxyReportSum) {
		super.delete(affairMjxyReportSum);
	}
	
}