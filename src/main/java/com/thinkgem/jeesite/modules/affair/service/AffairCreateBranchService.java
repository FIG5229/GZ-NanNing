/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCreateBranchDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCreateBranch;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党内创品牌活动Service
 * @author eav.liu
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairCreateBranchService extends CrudService<AffairCreateBranchDao, AffairCreateBranch> {

	public AffairCreateBranch get(String id) {
		return super.get(id);
	}
	
	public List<AffairCreateBranch> findList(AffairCreateBranch affairCreateBranch) {
		return super.findList(affairCreateBranch);
	}
	
	public Page<AffairCreateBranch> findPage(Page<AffairCreateBranch> page, AffairCreateBranch affairCreateBranch) {
		affairCreateBranch.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairCreateBranch);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCreateBranch affairCreateBranch) {
		super.save(affairCreateBranch);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCreateBranch affairCreateBranch) {
		super.delete(affairCreateBranch);
	}
	
}