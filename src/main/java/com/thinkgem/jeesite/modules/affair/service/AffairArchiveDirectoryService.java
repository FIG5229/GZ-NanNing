/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairArchiveDirectoryDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairArchiveDirectory;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 档案目录Service
 * @author mason.xv
 * @version 2019-11-27
 */
@Service
@Transactional(readOnly = true)
public class AffairArchiveDirectoryService extends CrudService<AffairArchiveDirectoryDao, AffairArchiveDirectory> {

	public AffairArchiveDirectory get(String id) {
		return super.get(id);
	}
	
	public List<AffairArchiveDirectory> findList(AffairArchiveDirectory affairArchiveDirectory) {
		return super.findList(affairArchiveDirectory);
	}
	
	public Page<AffairArchiveDirectory> findPage(Page<AffairArchiveDirectory> page, AffairArchiveDirectory affairArchiveDirectory) {
		affairArchiveDirectory.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairArchiveDirectory);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairArchiveDirectory affairArchiveDirectory) {
		super.save(affairArchiveDirectory);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairArchiveDirectory affairArchiveDirectory) {
		super.delete(affairArchiveDirectory);
	}
	
}