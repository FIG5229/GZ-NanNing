/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSuperiorGrantDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSuperiorGrant;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 上级拨款Service
 * @author cecil.li
 * @version 2019-12-21
 */
@Service
@Transactional(readOnly = true)
public class AffairSuperiorGrantService extends CrudService<AffairSuperiorGrantDao, AffairSuperiorGrant> {

	public AffairSuperiorGrant get(String id) {
		return super.get(id);
	}
	
	public List<AffairSuperiorGrant> findList(AffairSuperiorGrant affairSuperiorGrant) {
		return super.findList(affairSuperiorGrant);
	}
	
	public Page<AffairSuperiorGrant> findPage(Page<AffairSuperiorGrant> page, AffairSuperiorGrant affairSuperiorGrant) {
		affairSuperiorGrant.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSuperiorGrant);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSuperiorGrant affairSuperiorGrant) {
		super.save(affairSuperiorGrant);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSuperiorGrant affairSuperiorGrant) {
		super.delete(affairSuperiorGrant);
	}
	
}