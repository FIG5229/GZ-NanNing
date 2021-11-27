/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairChuPartyCongressRepresentativeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairChuPartyCongressRepresentative;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公安处党代会代表Service
 * @author cecil.li
 * @version 2020-06-09
 */
@Service
@Transactional(readOnly = true)
public class AffairChuPartyCongressRepresentativeService extends CrudService<AffairChuPartyCongressRepresentativeDao, AffairChuPartyCongressRepresentative> {

	public AffairChuPartyCongressRepresentative get(String id) {
		return super.get(id);
	}
	
	public List<AffairChuPartyCongressRepresentative> findList(AffairChuPartyCongressRepresentative affairChuPartyCongressRepresentative) {
		return super.findList(affairChuPartyCongressRepresentative);
	}
	
	public Page<AffairChuPartyCongressRepresentative> findPage(Page<AffairChuPartyCongressRepresentative> page, AffairChuPartyCongressRepresentative affairChuPartyCongressRepresentative) {
		affairChuPartyCongressRepresentative.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairChuPartyCongressRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairChuPartyCongressRepresentative affairChuPartyCongressRepresentative) {
		super.save(affairChuPartyCongressRepresentative);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairChuPartyCongressRepresentative affairChuPartyCongressRepresentative) {
		super.delete(affairChuPartyCongressRepresentative);
	}
	
}