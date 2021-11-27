/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkLeadDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkLead;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党建工作领导小组成员Service
 * @author alan
 * @version 2020-06-03
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkLeadService extends CrudService<AffairWorkLeadDao, AffairWorkLead> {

	@Autowired
	private AffairWorkLeadDao affairWorkLeadDao;

	public AffairWorkLead get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkLead> findList(AffairWorkLead affairWorkLead) {
		return super.findList(affairWorkLead);
	}
	
	public Page<AffairWorkLead> findPage(Page<AffairWorkLead> page, AffairWorkLead affairWorkLead) {
		affairWorkLead.setUserOffice(UserUtils.getUser().getOffice().getId());
		affairWorkLead.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkLead);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkLead affairWorkLead) {
		super.save(affairWorkLead);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkLead affairWorkLead) {
		super.delete(affairWorkLead);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairWorkLead affairWorkLead) {
		affairWorkLeadDao.shenHeSave(affairWorkLead);
	}
}