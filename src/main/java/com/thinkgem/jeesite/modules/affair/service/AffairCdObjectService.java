/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCdObjectDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCdObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 查(借)阅审批Service
 * @author mason.xv
 * @version 2019-11-30
 */
@Service
@Transactional(readOnly = true)
public class AffairCdObjectService extends CrudService<AffairCdObjectDao, AffairCdObject> {


	@Autowired
	AffairCdObjectDao affairCdObjectDao;
	public AffairCdObject get(String id) {
		AffairCdObject affairCdObject = super.get(id);
		return affairCdObject;
	}
	
	public List<AffairCdObject> findList(AffairCdObject affairCdObject) {
		return super.findList(affairCdObject);
	}
	
	public Page<AffairCdObject> findPage(Page<AffairCdObject> page, AffairCdObject affairCdObject) {
		return super.findPage(page, affairCdObject);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCdObject affairCdObject) {
		super.save(affairCdObject);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCdObject affairCdObject) {
		super.delete(affairCdObject);
	}

	@Transactional(readOnly = false)
	public void deleteByApprovelIds(List<String> approvelIds){
		dao.deleteByApprovelIds(approvelIds);
	}

	public List<AffairCdObject> findSomeByApprovelId(String approvelId){
		return affairCdObjectDao.findSomeByApprovelId(approvelId);
	}
}