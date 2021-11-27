/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairClassMemberDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAssess;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 班子成员Service
 * @author eav.liu
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairClassMemberService extends CrudService<AffairClassMemberDao, AffairClassMember> {

	@Autowired
	private AffairClassMemberDao affairClassMemberDao;

	public AffairClassMember get(String id) {
		return super.get(id);
	}
	
	public List<AffairClassMember> findList(AffairClassMember affairClassMember) {
		return super.findList(affairClassMember);
	}
	
	public Page<AffairClassMember> findPage(Page<AffairClassMember> page, AffairClassMember affairClassMember) {
		affairClassMember.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairClassMember);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairClassMember affairClassMember) {
		super.save(affairClassMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairClassMember affairClassMember) {
		super.delete(affairClassMember);
	}

	public List<String> findListByPartyOrganization(String  partyOrganization) {
		return affairClassMemberDao.findListByPartyOrganization(partyOrganization);
	}

	@Transactional(readOnly = false)
	public void deleteByPartyOrganization(String  partyOrganization) {
		affairClassMemberDao.deleteByPartyOrganization(partyOrganization);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairClassMember affairClassMember) {
		affairClassMemberDao.shenHeSave(affairClassMember);
	}
}