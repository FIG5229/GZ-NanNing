/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairYearThreeOnePlanDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairYearThreeOnePlan;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 年度&ldquo;三会一课&rdquo;计划Service
 * @author eav.liu
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairYearThreeOnePlanService extends CrudService<AffairYearThreeOnePlanDao, AffairYearThreeOnePlan> {

	@Autowired
	private AffairYearThreeOnePlanDao affairYearThreeOnePlanDao;

	public AffairYearThreeOnePlan get(String id) {
		return super.get(id);
	}
	
	public List<AffairYearThreeOnePlan> findList(AffairYearThreeOnePlan affairYearThreeOnePlan) {
		return super.findList(affairYearThreeOnePlan);
	}
	
	public Page<AffairYearThreeOnePlan> findPage(Page<AffairYearThreeOnePlan> page, AffairYearThreeOnePlan affairYearThreeOnePlan) {
		affairYearThreeOnePlan.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairYearThreeOnePlan);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairYearThreeOnePlan affairYearThreeOnePlan) {
		affairYearThreeOnePlan.setStatus("3");//未审核
		affairYearThreeOnePlan.setContent(StringEscapeUtils.unescapeHtml4(affairYearThreeOnePlan.getContent()));
		super.save(affairYearThreeOnePlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairYearThreeOnePlan affairYearThreeOnePlan) {
		super.delete(affairYearThreeOnePlan);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairYearThreeOnePlan affairYearThreeOnePlan) {
		affairYearThreeOnePlan.setUpdateDate(new Date());
		affairYearThreeOnePlan.setShPerson(UserUtils.getUser().getName());
		affairYearThreeOnePlanDao.shenHeSave(affairYearThreeOnePlan);
	}
}