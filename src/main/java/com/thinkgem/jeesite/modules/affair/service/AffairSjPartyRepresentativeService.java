/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSjPartyRepresentativeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairSjPartyRepresentative;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 上级党代表Service
 * @author cecil.li
 * @version 2020-06-09
 */
@Service
@Transactional(readOnly = true)
public class AffairSjPartyRepresentativeService extends CrudService<AffairSjPartyRepresentativeDao, AffairSjPartyRepresentative> {

	@Autowired
	private AffairSjPartyRepresentativeDao affairSjPartyRepresentativeDao;

	public AffairSjPartyRepresentative get(String id) {
		return super.get(id);
	}
	
	public List<AffairSjPartyRepresentative> findList(AffairSjPartyRepresentative affairSjPartyRepresentative) {
		return super.findList(affairSjPartyRepresentative);
	}
	
	public Page<AffairSjPartyRepresentative> findPage(Page<AffairSjPartyRepresentative> page, AffairSjPartyRepresentative affairSjPartyRepresentative) {
		affairSjPartyRepresentative.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSjPartyRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSjPartyRepresentative affairSjPartyRepresentative) {
		super.save(affairSjPartyRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSjPartyRepresentative affairSjPartyRepresentative) {
		super.delete(affairSjPartyRepresentative);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairSjPartyRepresentative affairSjPartyRepresentative) {
		affairSjPartyRepresentativeDao.shenHeSave(affairSjPartyRepresentative);
	}
}