/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocManagement;
import com.thinkgem.jeesite.modules.affair.dao.AffairDocManagementDao;

/**
 * 文档管理Service
 * @author kevin.jia
 * @version 2020-07-30
 */
@Service
@Transactional(readOnly = true)
public class AffairDocManagementService extends CrudService<AffairDocManagementDao, AffairDocManagement> {

	@Autowired
	AffairDocManagementDao affairDocManagementDao;

	public AffairDocManagement get(String id) {
		return super.get(id);
	}
	
	public List<AffairDocManagement> findList(AffairDocManagement affairDocManagement) {
		return super.findList(affairDocManagement);
	}
	
	public Page<AffairDocManagement> findPage(Page<AffairDocManagement> page, AffairDocManagement affairDocManagement) {
		return super.findPage(page, affairDocManagement);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDocManagement affairDocManagement) {
		super.save(affairDocManagement);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDocManagement affairDocManagement) {
		super.delete(affairDocManagement);
	}

	@Transactional(readOnly = false)
	public List<AffairDocManagement> selectAll( ) {
		List<AffairDocManagement> list = affairDocManagementDao.selectAll( );
		return list;
	}

	@Transactional(readOnly = false)
	public  AffairDocManagement selectBeanById(String id) {
		return affairDocManagementDao.selectBeanById(id);
	}



    public List<AffairDocManagement> findByIds(List<String> ids) {
		return affairDocManagementDao.findByIds(ids);
    }
}