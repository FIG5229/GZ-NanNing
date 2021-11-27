/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairEmpiricalPracticeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairEmpiricalPractice;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 教育整顿经验做法Service
 * @author cecil.li
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class AffairEmpiricalPracticeService extends CrudService<AffairEmpiricalPracticeDao, AffairEmpiricalPractice> {

	public AffairEmpiricalPractice get(String id) {
		return super.get(id);
	}
	
	public List<AffairEmpiricalPractice> findList(AffairEmpiricalPractice affairEmpiricalPractice) {
		return super.findList(affairEmpiricalPractice);
	}
	
	public Page<AffairEmpiricalPractice> findPage(Page<AffairEmpiricalPractice> page, AffairEmpiricalPractice affairEmpiricalPractice) {
		return super.findPage(page, affairEmpiricalPractice);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairEmpiricalPractice affairEmpiricalPractice) {
		affairEmpiricalPractice.setMainContent(StringEscapeUtils.unescapeHtml4(affairEmpiricalPractice.getMainContent()));
		super.save(affairEmpiricalPractice);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairEmpiricalPractice affairEmpiricalPractice) {
		super.delete(affairEmpiricalPractice);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month){
		return dao.findInfoByCreateOrgId(id, year, startDate, endDate, month);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year, startDate, endDate, month);
	}
}