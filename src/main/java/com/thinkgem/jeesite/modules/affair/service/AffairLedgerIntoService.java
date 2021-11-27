/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLedgerIntoDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLedgerInto;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 档案台账转入Service
 * @author mason.xv
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class AffairLedgerIntoService extends CrudService<AffairLedgerIntoDao, AffairLedgerInto> {

	public AffairLedgerInto get(String id) {
		return super.get(id);
	}
	
	public List<AffairLedgerInto> findList(AffairLedgerInto affairLedgerInto) {
		return super.findList(affairLedgerInto);
	}
	
	public Page<AffairLedgerInto> findPage(Page<AffairLedgerInto> page, AffairLedgerInto affairLedgerInto) {
		affairLedgerInto.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLedgerInto);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLedgerInto affairLedgerInto) {
		super.save(affairLedgerInto);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLedgerInto affairLedgerInto) {
		super.delete(affairLedgerInto);
	}
	
}