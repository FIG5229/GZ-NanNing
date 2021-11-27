/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSjPartyCongressRepresentativeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSjPartyCongressRepresentative;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 上级党代会代表Service
 * @author cecil.li
 * @version 2020-06-09
 */
@Service
@Transactional(readOnly = true)
public class AffairSjPartyCongressRepresentativeService extends CrudService<AffairSjPartyCongressRepresentativeDao, AffairSjPartyCongressRepresentative> {

	public AffairSjPartyCongressRepresentative get(String id) {
		return super.get(id);
	}
	
	public List<AffairSjPartyCongressRepresentative> findList(AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative) {
		return super.findList(affairSjPartyCongressRepresentative);
	}
	
	public Page<AffairSjPartyCongressRepresentative> findPage(Page<AffairSjPartyCongressRepresentative> page, AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative) {
		affairSjPartyCongressRepresentative.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSjPartyCongressRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative) {
		super.save(affairSjPartyCongressRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSjPartyCongressRepresentative affairSjPartyCongressRepresentative) {
		super.delete(affairSjPartyCongressRepresentative);
	}
	
}