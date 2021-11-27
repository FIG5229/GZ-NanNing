/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCdPersonDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCdPerson;
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
public class AffairCdPersonService extends CrudService<AffairCdPersonDao, AffairCdPerson> {

	@Autowired
	AffairCdPersonDao affairCdPersonDao;

	public AffairCdPerson get(String id) {
		return super.get(id);
	}
	
	public List<AffairCdPerson> findList(AffairCdPerson affairCdPerson) {
		return super.findList(affairCdPerson);
	}
	
	public Page<AffairCdPerson> findPage(Page<AffairCdPerson> page, AffairCdPerson affairCdPerson) {
		return super.findPage(page, affairCdPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCdPerson affairCdPerson) {
		super.save(affairCdPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCdPerson affairCdPerson) {
		super.delete(affairCdPerson);
	}

	@Transactional(readOnly = false)
	public void deleteByApprovelIds(List<String> approvelIds){
		dao.deleteByApprovelIds(approvelIds);
	}

	public List<AffairCdPerson> findSomeByApprovelId(String approvelId){
		return  affairCdPersonDao.findSomeByApprovelId(approvelId);
	}
}