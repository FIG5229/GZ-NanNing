/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairProbationaryMember;
import com.thinkgem.jeesite.modules.affair.dao.AffairProbationaryMemberDao;

/**
 * 预备党员Service
 * @author cecil.li
 * @version 2020-12-21
 */
@Service
@Transactional(readOnly = true)
public class AffairProbationaryMemberService extends CrudService<AffairProbationaryMemberDao, AffairProbationaryMember> {

	@Autowired
	private AffairProbationaryMemberDao affairProbationaryMemberDao;

	public AffairProbationaryMember get(String id) {
		return super.get(id);
	}
	
	public List<AffairProbationaryMember> findList(AffairProbationaryMember affairProbationaryMember) {
		return super.findList(affairProbationaryMember);
	}
	
	public Page<AffairProbationaryMember> findPage(Page<AffairProbationaryMember> page, AffairProbationaryMember affairProbationaryMember) {
		return super.findPage(page, affairProbationaryMember);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairProbationaryMember affairProbationaryMember) {
		super.save(affairProbationaryMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairProbationaryMember affairProbationaryMember) {
		super.delete(affairProbationaryMember);
	}

	//预备党员审核
	@Transactional(readOnly = false)
	public void shenHe(AffairProbationaryMember affairProbationaryMember) {
		affairProbationaryMember.setUpdateDate(new Date());
		affairProbationaryMember.setPerson(UserUtils.getUser().getName());
		affairProbationaryMemberDao.shenHe(affairProbationaryMember);
	}
	
}