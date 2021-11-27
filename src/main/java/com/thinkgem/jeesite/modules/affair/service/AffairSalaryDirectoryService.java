/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSalaryDirectoryDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryDirectory;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工资目录Service
 * @author mason.xv
 * @version 2019-11-27
 */
@Service
@Transactional(readOnly = true)
public class AffairSalaryDirectoryService extends CrudService<AffairSalaryDirectoryDao, AffairSalaryDirectory> {

	public AffairSalaryDirectory get(String id) {
		return super.get(id);
	}
	
	public List<AffairSalaryDirectory> findList(AffairSalaryDirectory affairSalaryDirectory) {
		return super.findList(affairSalaryDirectory);
	}
	
	public Page<AffairSalaryDirectory> findPage(Page<AffairSalaryDirectory> page, AffairSalaryDirectory affairSalaryDirectory) {
		affairSalaryDirectory.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSalaryDirectory);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSalaryDirectory affairSalaryDirectory) {
		super.save(affairSalaryDirectory);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSalaryDirectory affairSalaryDirectory) {
		super.delete(affairSalaryDirectory);
	}
	
}