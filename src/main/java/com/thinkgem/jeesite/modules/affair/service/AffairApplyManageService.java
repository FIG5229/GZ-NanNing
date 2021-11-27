/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairApplyManage;
import com.thinkgem.jeesite.modules.affair.dao.AffairApplyManageDao;

/**
 * 报名管理Service
 * @author alan.wu
 * @version 2020-07-07
 */
@Service
@Transactional(readOnly = true)
public class AffairApplyManageService extends CrudService<AffairApplyManageDao, AffairApplyManage> {

	public AffairApplyManage get(String id) {
		return super.get(id);
	}
	
	public List<AffairApplyManage> findList(AffairApplyManage affairApplyManage) {
		return super.findList(affairApplyManage);
	}
	
	public Page<AffairApplyManage> findPage(Page<AffairApplyManage> page, AffairApplyManage affairApplyManage) {
		return super.findPage(page, affairApplyManage);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairApplyManage affairApplyManage) {
		super.save(affairApplyManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairApplyManage affairApplyManage) {
		super.delete(affairApplyManage);
	}
	
}