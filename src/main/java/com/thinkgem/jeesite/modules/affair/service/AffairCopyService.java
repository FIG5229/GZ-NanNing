/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCopyDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCopy;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 复印档案登记Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairCopyService extends CrudService<AffairCopyDao, AffairCopy> {

	public AffairCopy get(String id) {
		return super.get(id);
	}
	
	public List<AffairCopy> findList(AffairCopy affairCopy) {
		return super.findList(affairCopy);
	}
	
	public Page<AffairCopy> findPage(Page<AffairCopy> page, AffairCopy affairCopy) {
		affairCopy.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairCopy);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCopy affairCopy) {
		super.save(affairCopy);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCopy affairCopy) {
		super.delete(affairCopy);
	}
	
}