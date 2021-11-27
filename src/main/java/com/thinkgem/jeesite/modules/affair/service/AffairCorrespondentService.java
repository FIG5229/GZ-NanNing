/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCorrespondent;
import com.thinkgem.jeesite.modules.affair.dao.AffairCorrespondentDao;

/**
 * 通讯员管理Service
 * @author Alan.wu
 * @version 2020-06-18
 */
@Service
@Transactional(readOnly = true)
public class AffairCorrespondentService extends CrudService<AffairCorrespondentDao, AffairCorrespondent> {

	@Autowired
	private AffairCorrespondentDao affairCorrespondentDao;

	public AffairCorrespondent get(String id) {
		return super.get(id);
	}
	
	public List<AffairCorrespondent> findList(AffairCorrespondent affairCorrespondent) {
		return super.findList(affairCorrespondent);
	}
	
	public Page<AffairCorrespondent> findPage(Page<AffairCorrespondent> page, AffairCorrespondent affairCorrespondent) {
		/*affairCorrespondent.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));*/
		affairCorrespondent.setUnitId(UserUtils.getUser().getOffice().getId());
		affairCorrespondent.setUserId(UserUtils.getUser().getId());
		return super.findPage(page, affairCorrespondent);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCorrespondent affairCorrespondent) {
		super.save(affairCorrespondent);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCorrespondent affairCorrespondent) {
		super.delete(affairCorrespondent);
	}

	public List<AffairCorrespondent> findPerson(String name){
		return affairCorrespondentDao.findPerson(name);
	}
}