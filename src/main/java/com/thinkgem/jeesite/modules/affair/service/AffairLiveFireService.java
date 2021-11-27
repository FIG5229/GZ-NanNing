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
import com.thinkgem.jeesite.modules.affair.entity.AffairLiveFire;
import com.thinkgem.jeesite.modules.affair.dao.AffairLiveFireDao;

/**
 * 实弹射击Service
 * @author cecil.li
 * @version 2020-12-28
 */
@Service
@Transactional(readOnly = true)
public class AffairLiveFireService extends CrudService<AffairLiveFireDao, AffairLiveFire> {

	public AffairLiveFire get(String id) {
		return super.get(id);
	}
	
	public List<AffairLiveFire> findList(AffairLiveFire affairLiveFire) {
		return super.findList(affairLiveFire);
	}
	
	public Page<AffairLiveFire> findPage(Page<AffairLiveFire> page, AffairLiveFire affairLiveFire) {
		affairLiveFire.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLiveFire);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLiveFire affairLiveFire) {
		super.save(affairLiveFire);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLiveFire affairLiveFire) {
		super.delete(affairLiveFire);
	}
	
}