/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyRepresentativeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyRepresentative;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党代表Service
 * @author cecil.li
 * @version 2020-06-09
 */
@Service
@Transactional(readOnly = true)
public class AffairPartyRepresentativeService extends CrudService<AffairPartyRepresentativeDao, AffairPartyRepresentative> {

	@Autowired
	private AffairPartyRepresentativeDao affairPartyRepresentativeDao;

	public AffairPartyRepresentative get(String id) {
		return super.get(id);
	}
	
	public List<AffairPartyRepresentative> findList(AffairPartyRepresentative affairPartyRepresentative) {
		return super.findList(affairPartyRepresentative);
	}
	
	public Page<AffairPartyRepresentative> findPage(Page<AffairPartyRepresentative> page, AffairPartyRepresentative affairPartyRepresentative) {
		affairPartyRepresentative.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPartyRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPartyRepresentative affairPartyRepresentative) {
		super.save(affairPartyRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPartyRepresentative affairPartyRepresentative) {
		super.delete(affairPartyRepresentative);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairPartyRepresentative affairPartyRepresentative) {
		affairPartyRepresentativeDao.shenHeSave(affairPartyRepresentative);
	}
}