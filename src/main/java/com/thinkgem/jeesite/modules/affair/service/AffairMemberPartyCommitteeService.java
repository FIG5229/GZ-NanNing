/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairMemberPartyCommittee;
import com.thinkgem.jeesite.modules.affair.dao.AffairMemberPartyCommitteeDao;

/**
 * 党委委员Service
 * @author daniel.liu
 * @version 2020-06-02
 */
@Service
@Transactional(readOnly = true)
public class AffairMemberPartyCommitteeService extends CrudService<AffairMemberPartyCommitteeDao, AffairMemberPartyCommittee> {

	@Autowired
	private AffairMemberPartyCommitteeDao affairMemberPartyCommitteeDao;

	public AffairMemberPartyCommittee get(String id) {
		return super.get(id);
	}
	
	public List<AffairMemberPartyCommittee> findList(AffairMemberPartyCommittee affairMemberPartyCommittee) {
		return super.findList(affairMemberPartyCommittee);
	}
	
	public Page<AffairMemberPartyCommittee> findPage(Page<AffairMemberPartyCommittee> page, AffairMemberPartyCommittee affairMemberPartyCommittee) {
		affairMemberPartyCommittee.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairMemberPartyCommittee);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairMemberPartyCommittee affairMemberPartyCommittee) {
		affairMemberPartyCommittee.setPartyClass(StringEscapeUtils.unescapeHtml4(affairMemberPartyCommittee.getPartyClass()));
		affairMemberPartyCommittee.setAssociatedPiont(StringEscapeUtils.unescapeHtml4(affairMemberPartyCommittee.getAssociatedPiont()));
		affairMemberPartyCommittee.setResponsibilityReport(StringEscapeUtils.unescapeHtml4(affairMemberPartyCommittee.getResponsibilityReport()));
		super.save(affairMemberPartyCommittee);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairMemberPartyCommittee affairMemberPartyCommittee) {
		super.delete(affairMemberPartyCommittee);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairMemberPartyCommittee affairMemberPartyCommittee) {
		 affairMemberPartyCommitteeDao.shenHeSave(affairMemberPartyCommittee);
	}
}