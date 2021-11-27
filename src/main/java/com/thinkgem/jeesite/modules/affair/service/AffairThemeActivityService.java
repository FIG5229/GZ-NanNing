/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairThemeActivityDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairThemeActivity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党内主题实践活动Service
 * @author eav.liu
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairThemeActivityService extends CrudService<AffairThemeActivityDao, AffairThemeActivity> {

	public AffairThemeActivity get(String id) {
		return super.get(id);
	}
	
	public List<AffairThemeActivity> findList(AffairThemeActivity affairThemeActivity) {
		return super.findList(affairThemeActivity);
	}
	
	public Page<AffairThemeActivity> findPage(Page<AffairThemeActivity> page, AffairThemeActivity affairThemeActivity) {
		affairThemeActivity.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairThemeActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairThemeActivity affairThemeActivity) {
		super.save(affairThemeActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairThemeActivity affairThemeActivity) {
		super.delete(affairThemeActivity);
	}
	
}