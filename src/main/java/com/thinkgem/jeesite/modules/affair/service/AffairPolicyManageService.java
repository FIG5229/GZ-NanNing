/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairPolicyManageDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicyManage;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 政策管理Service
 * @author cecil.li
 * @version 2020-05-29
 */
@Service
@Transactional(readOnly = true)
public class AffairPolicyManageService extends CrudService<AffairPolicyManageDao, AffairPolicyManage> {

	public AffairPolicyManage get(String id) {
		return super.get(id);
	}
	
	public List<AffairPolicyManage> findList(AffairPolicyManage affairPolicyManage) {
		return super.findList(affairPolicyManage);
	}
	
	public Page<AffairPolicyManage> findPage(Page<AffairPolicyManage> page, AffairPolicyManage affairPolicyManage) {
		affairPolicyManage.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPolicyManage);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPolicyManage affairPolicyManage) {
		super.save(affairPolicyManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPolicyManage affairPolicyManage) {
		super.delete(affairPolicyManage);
	}
	
}