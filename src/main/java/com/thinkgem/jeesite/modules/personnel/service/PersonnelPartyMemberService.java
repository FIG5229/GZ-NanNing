/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPartyMemberDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPartyMember;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党员信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPartyMemberService extends CrudService<PersonnelPartyMemberDao, PersonnelPartyMember> {

	public PersonnelPartyMember get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPartyMember> findList(PersonnelPartyMember personnelPartyMember) {
		return super.findList(personnelPartyMember);
	}
	
	public Page<PersonnelPartyMember> findPage(Page<PersonnelPartyMember> page, PersonnelPartyMember personnelPartyMember) {
		personnelPartyMember.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPartyMember);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPartyMember personnelPartyMember) {
		super.save(personnelPartyMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPartyMember personnelPartyMember) {
		super.delete(personnelPartyMember);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}