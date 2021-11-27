/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairSpiritualTable;
import com.thinkgem.jeesite.modules.affair.dao.AffairSpiritualTableDao;

/**
 * 复查表Service
 * @author alan.wu
 * @version 2020-08-03
 */
@Service
@Transactional(readOnly = true)
public class AffairSpiritualTableService extends CrudService<AffairSpiritualTableDao, AffairSpiritualTable> {

	public AffairSpiritualTable get(String id) {
		return super.get(id);
	}
	
	public List<AffairSpiritualTable> findList(AffairSpiritualTable affairSpiritualTable) {
		return super.findList(affairSpiritualTable);
	}
	
	public Page<AffairSpiritualTable> findPage(Page<AffairSpiritualTable> page, AffairSpiritualTable affairSpiritualTable) {
		affairSpiritualTable.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairSpiritualTable);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSpiritualTable affairSpiritualTable) {
		super.save(affairSpiritualTable);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSpiritualTable affairSpiritualTable) {
		super.delete(affairSpiritualTable);
	}
	
}