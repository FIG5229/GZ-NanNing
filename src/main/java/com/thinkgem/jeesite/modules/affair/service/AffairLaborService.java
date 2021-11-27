/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLaborDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLabor;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 劳资Service
 * @author cecil.li
 * @version 2020-01-19
 */
@Service
@Transactional(readOnly = true)
public class AffairLaborService extends CrudService<AffairLaborDao, AffairLabor> {

	@Autowired
	AffairLaborDao affairLaborDao;

	public AffairLabor get(String id) {
		return super.get(id);
	}
	
	public List<AffairLabor> findList(AffairLabor affairLabor) {
		return super.findList(affairLabor);
	}
	
	public Page<AffairLabor> findPage(Page<AffairLabor> page, AffairLabor affairLabor) {
		affairLabor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLabor);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLabor affairLabor) {
		super.save(affairLabor);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLabor affairLabor) {
		super.delete(affairLabor);
	}

	@Transactional(readOnly = false)
	public List<String> selectAllId(){
		return affairLaborDao.selectAllId();
	}


}