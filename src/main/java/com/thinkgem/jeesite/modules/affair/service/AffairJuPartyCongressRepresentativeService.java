/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairJuPartyCongressRepresentativeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairJuPartyCongressRepresentative;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公安局党代会代表Service
 * @author cecil.li
 * @version 2020-06-09
 */
@Service
@Transactional(readOnly = true)
public class AffairJuPartyCongressRepresentativeService extends CrudService<AffairJuPartyCongressRepresentativeDao, AffairJuPartyCongressRepresentative> {

	public AffairJuPartyCongressRepresentative get(String id) {
		return super.get(id);
	}
	
	public List<AffairJuPartyCongressRepresentative> findList(AffairJuPartyCongressRepresentative affairJuPartyCongressRepresentative) {
		return super.findList(affairJuPartyCongressRepresentative);
	}
	
	public Page<AffairJuPartyCongressRepresentative> findPage(Page<AffairJuPartyCongressRepresentative> page, AffairJuPartyCongressRepresentative affairJuPartyCongressRepresentative) {
		affairJuPartyCongressRepresentative.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairJuPartyCongressRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairJuPartyCongressRepresentative affairJuPartyCongressRepresentative) {
		super.save(affairJuPartyCongressRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairJuPartyCongressRepresentative affairJuPartyCongressRepresentative) {
		super.delete(affairJuPartyCongressRepresentative);
	}
	
}