/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysOffices;
import com.thinkgem.jeesite.modules.sys.dao.SysOfficesDao;

/**
 * 组织关系对应关系Service
 * @author bradley.zhao
 * @version 2020-12-13
 */
@Service
@Transactional(readOnly = true)
public class SysOfficesService extends CrudService<SysOfficesDao, SysOffices> {

	@Autowired
	private  SysOfficesDao sysOfficesDao;

	public SysOffices get(String id) {
		return super.get(id);
	}
	
	public List<SysOffices> findList(SysOffices sysOffices) {
		return super.findList(sysOffices);
	}
	
	public Page<SysOffices> findPage(Page<SysOffices> page, SysOffices sysOffices) {
		return super.findPage(page, sysOffices);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOffices sysOffices) {
		super.save(sysOffices);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOffices sysOffices) {
		super.delete(sysOffices);
	}

	public String findGroupIbById(String id){
		return sysOfficesDao.findGroupIdById(id);
	}

	public String findByGroupName(String groupName){
		return sysOfficesDao.findByGroupName(groupName);
	}

	@Transactional(readOnly = false)
	public int saveNew(SysOffices sysOffices) {
		return sysOfficesDao.saveNew(sysOffices);
	}

	@Transactional(readOnly = false)
	public void deleteById(String Id) {

		sysOfficesDao.deleteById(Id);
	}

	public SysOffices selectBeanById(String Id) {
		return sysOfficesDao.selectBeanById(Id);
	}

	@Transactional(readOnly = false)
	public void saveById(SysOffices sysOffices) {
		sysOfficesDao.saveById(sysOffices);
	}
}