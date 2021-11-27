/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLedgerOutDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLedgerOut;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 档案台账转出Service
 * @author mason.xv
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairLedgerOutService extends CrudService<AffairLedgerOutDao, AffairLedgerOut> {

	public AffairLedgerOut get(String id) {
		return super.get(id);
	}
	
	public List<AffairLedgerOut> findList(AffairLedgerOut affairLedgerOut) {
		return super.findList(affairLedgerOut);
	}
	
	public Page<AffairLedgerOut> findPage(Page<AffairLedgerOut> page, AffairLedgerOut affairLedgerOut) {
		affairLedgerOut.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLedgerOut);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLedgerOut affairLedgerOut) {
		super.save(affairLedgerOut);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLedgerOut affairLedgerOut) {
		super.delete(affairLedgerOut);
	}
	
}