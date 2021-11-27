/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairConsultDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairConsult;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 台账查阅表Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairConsultService extends CrudService<AffairConsultDao, AffairConsult> {

	public AffairConsult get(String id) {
		return super.get(id);
	}
	
	public List<AffairConsult> findList(AffairConsult affairConsult) {
		return super.findList(affairConsult);
	}
	
	public Page<AffairConsult> findPage(Page<AffairConsult> page, AffairConsult affairConsult) {
		affairConsult.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairConsult);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairConsult affairConsult) {
		super.save(affairConsult);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairConsult affairConsult) {
		super.delete(affairConsult);
	}
	
}