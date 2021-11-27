/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairJobDirectoryDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairJobDirectory;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职务目录Service
 * @author mason.xv
 * @version 2019-11-27
 */
@Service
@Transactional(readOnly = true)
public class AffairJobDirectoryService extends CrudService<AffairJobDirectoryDao, AffairJobDirectory> {

	public AffairJobDirectory get(String id) {
		return super.get(id);
	}
	
	public List<AffairJobDirectory> findList(AffairJobDirectory affairJobDirectory) {
		return super.findList(affairJobDirectory);
	}
	
	public Page<AffairJobDirectory> findPage(Page<AffairJobDirectory> page, AffairJobDirectory affairJobDirectory) {
		affairJobDirectory.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairJobDirectory);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairJobDirectory affairJobDirectory) {
		super.save(affairJobDirectory);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairJobDirectory affairJobDirectory) {
		super.delete(affairJobDirectory);
	}
	
}