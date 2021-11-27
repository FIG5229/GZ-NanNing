/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairHardPolicewomanDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairHardPolicewoman;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 困难女民警申报Service
 * @author cecil.li
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairHardPolicewomanService extends CrudService<AffairHardPolicewomanDao, AffairHardPolicewoman> {

	public AffairHardPolicewoman get(String id) {
		return super.get(id);
	}
	
	public List<AffairHardPolicewoman> findList(AffairHardPolicewoman affairHardPolicewoman) {
		return super.findList(affairHardPolicewoman);
	}
	
	public Page<AffairHardPolicewoman> findPage(Page<AffairHardPolicewoman> page, AffairHardPolicewoman affairHardPolicewoman) {
		affairHardPolicewoman.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairHardPolicewoman);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairHardPolicewoman affairHardPolicewoman) {
		super.save(affairHardPolicewoman);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairHardPolicewoman affairHardPolicewoman) {
		super.delete(affairHardPolicewoman);
	}
	
}