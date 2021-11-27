/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairBorrowDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairBorrow;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 档案台账查阅表Service
 * @author mason.xv
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairBorrowService extends CrudService<AffairBorrowDao, AffairBorrow> {

	public AffairBorrow get(String id) {
		return super.get(id);
	}
	
	public List<AffairBorrow> findList(AffairBorrow affairBorrow) {
		return super.findList(affairBorrow);
	}
	
	public Page<AffairBorrow> findPage(Page<AffairBorrow> page, AffairBorrow affairBorrow) {
		affairBorrow.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairBorrow);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairBorrow affairBorrow) {
		super.save(affairBorrow);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairBorrow affairBorrow) {
		super.delete(affairBorrow);
	}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}
	
}