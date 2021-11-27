/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCultureActivityDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCultureActivity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 警营文化活动Service
 * @author cecil.li
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairCultureActivityService extends CrudService<AffairCultureActivityDao, AffairCultureActivity> {

	public AffairCultureActivity get(String id) {
		return super.get(id);
	}
	
	public List<AffairCultureActivity> findList(AffairCultureActivity affairCultureActivity) {
		return super.findList(affairCultureActivity);
	}
	
	public Page<AffairCultureActivity> findPage(Page<AffairCultureActivity> page, AffairCultureActivity affairCultureActivity) {
		affairCultureActivity.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairCultureActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCultureActivity affairCultureActivity) {
		super.save(affairCultureActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCultureActivity affairCultureActivity) {
		super.delete(affairCultureActivity);
	}
	
}