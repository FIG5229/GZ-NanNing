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
import com.thinkgem.jeesite.modules.affair.entity.AffairIndividualResults;
import com.thinkgem.jeesite.modules.affair.dao.AffairIndividualResultsDao;

/**
 * 个人比武成绩Service
 * @author cecil.li
 * @version 2020-12-29
 */
@Service
@Transactional(readOnly = true)
public class AffairIndividualResultsService extends CrudService<AffairIndividualResultsDao, AffairIndividualResults> {

	public AffairIndividualResults get(String id) {
		return super.get(id);
	}
	
	public List<AffairIndividualResults> findList(AffairIndividualResults affairIndividualResults) {
		return super.findList(affairIndividualResults);
	}
	
	public Page<AffairIndividualResults> findPage(Page<AffairIndividualResults> page, AffairIndividualResults affairIndividualResults) {
		affairIndividualResults.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairIndividualResults);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairIndividualResults affairIndividualResults) {
		super.save(affairIndividualResults);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairIndividualResults affairIndividualResults) {
		super.delete(affairIndividualResults);
	}
	
}